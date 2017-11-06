/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baidu.bce.mkt.iac.common.client.IacClientFactory;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.internalsdk.model.CategoryTreeModel;
import com.baidu.bce.mkt.internalsdk.model.response.CategoryTreeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/11/3 by kuangzhen@baidu.com .
 */

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryHandler {
    private final IacClientFactory clientFactory;

    public List<CategoryTreeModel> getCategoryTree() {
        CategoryTreeResponse response = clientFactory.createMktClient().getCategoryTree(false);
        return response.getRoots();
    }

    // 获取['镜像环境:基础环境', ‘建站推广:速成建站']形式对应的map
    public Map<String, String> getCategoryNameMap(String cids) {
        Map<String, String> categoryNameMap = new HashedMap();
        if (StringUtils.isEmpty(cids)) {
            return categoryNameMap;
        }
        Map<Integer, CategoryTreeModel> categoryTreeModelMap = getCategoryTreeModelMap();
        Arrays.asList(cids.split(",")).forEach(cid -> {
            CategoryTreeModel treeModel = categoryTreeModelMap.get(Integer.valueOf(cid));
            if (treeModel == null) {
                log.warn("unvalid cid:{}",cid);
                throw MktIacExceptions.inValidCid();
            }
            CategoryTreeModel rootCategory = getRootCategory(categoryTreeModelMap, categoryTreeModelMap.get(treeModel.getParentId()));
            if (rootCategory == null){
                log.warn("unvalid cid:{}",cid);
                throw MktIacExceptions.inValidCid();
            }
            String childrenNames = categoryNameMap.get(rootCategory.getName());
            if (childrenNames == null) {
                categoryNameMap.put(rootCategory.getName(), treeModel.getName());
            } else {
                childrenNames = childrenNames + "," + treeModel.getName();
                categoryNameMap.put(rootCategory.getName(), childrenNames);
            }
        });
        return categoryNameMap;
    }


    private CategoryTreeModel getRootCategory(Map<Integer, CategoryTreeModel> categoryTreeModelMap, CategoryTreeModel treeModel) {
        if (treeModel == null) {
            return null;
        }
        if (treeModel.getParentId() == 0) {
            return treeModel;
        }
        return getRootCategory(categoryTreeModelMap, categoryTreeModelMap.get(treeModel.getParentId()));
    }

    public Map<Integer, CategoryTreeModel> getCategoryTreeModelMap() {
        Map<Integer, CategoryTreeModel> categoryTreeModelMap = new HashMap<>();
        generateCategoryTreeModelMap(getCategoryTree(), categoryTreeModelMap);
        return categoryTreeModelMap;
    }

    private void generateCategoryTreeModelMap(List<CategoryTreeModel> categoryTreeModelList,
                                              Map<Integer, CategoryTreeModel> categoryTreeModelMap) {
        if (CollectionUtils.isEmpty(categoryTreeModelList)) {
            return;
        }
        for (CategoryTreeModel categoryTreeModel : categoryTreeModelList) {
            categoryTreeModelMap.put(categoryTreeModel.getCid(), categoryTreeModel);
            generateCategoryTreeModelMap(categoryTreeModel.getChildren(), categoryTreeModelMap);
        }
    }
}

