/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.handler;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.handler.CategoryHandler;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;
import com.baidu.bce.mkt.internalsdk.model.CategoryTreeModel;
import com.baidu.bce.mkt.internalsdk.model.response.CategoryTreeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryHandlerTest extends BaseCommonServiceTest {
    @Autowired
    private CategoryHandler categoryHandler;

    @Test
    public void testGetCategoryNameMap() {
        List<CategoryTreeModel> categoryTreeChildren = new ArrayList<>();
        categoryTreeChildren.add(generateCategoryTreeModel(101011, "test_101011", 101));
        categoryTreeChildren.add(generateCategoryTreeModel(101022, "test_101022", 101));
        List<CategoryTreeModel> categoryTreeChildren2 = new ArrayList<>();
        categoryTreeChildren2.add(generateCategoryTreeModel(102011, "test_102011", 102));
        categoryTreeChildren2.add(generateCategoryTreeModel(102022, "test_102022", 102));
        List<CategoryTreeModel> categoryTreeRoot = new ArrayList<>();
        CategoryTreeModel root1 = generateCategoryTreeModel(101, "test_101", 0);
        root1.setChildren(categoryTreeChildren);
        categoryTreeRoot.add(root1);
        CategoryTreeModel root2 = generateCategoryTreeModel(102, "test_102", 0);
        root2.setChildren(categoryTreeChildren2);
        categoryTreeRoot.add(root2);
        CategoryTreeResponse response = new CategoryTreeResponse();
        response.setRoots(categoryTreeRoot);
        when(mktClient.getCategoryTree(anyBoolean())).thenReturn(response);
        Map<String, String> categoryNameMap = categoryHandler.getCategoryNameMap("101011,101022,102022");
        String children1 = categoryNameMap.get("test_101");
        Assert.assertNotNull(children1);
        Assert.assertEquals(children1, "test_101011,test_101022");
        String children2 = categoryNameMap.get("test_102");
        Assert.assertNotNull(children2);
        Assert.assertEquals(children2, "test_102022");
        try {
            categoryNameMap = categoryHandler.getCategoryNameMap("101013");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), MktIacExceptions.inValidCid().getMessage());
        }

    }

    private CategoryTreeModel generateCategoryTreeModel(int cid, String name, int parentId) {
        CategoryTreeModel treeModel = new CategoryTreeModel();
        treeModel.setName(name);
        treeModel.setCid(cid);
        treeModel.setParentId(parentId);
        return treeModel;
    }

}
