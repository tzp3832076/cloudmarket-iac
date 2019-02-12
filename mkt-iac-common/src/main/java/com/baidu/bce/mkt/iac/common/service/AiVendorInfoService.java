/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.model.AiVendorListFilter;
import com.baidu.bce.mkt.iac.common.model.AiVendorListModel;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.mapper.AiVendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.utils.MBeanUtils;
import com.baidu.bce.plat.webframework.exception.BceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenxiang05@baidu.com on 2018/8/16.
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AiVendorInfoService {

    private final AiVendorInfoMapper aiVendorInfoMapper;

    private static final String AI_PREFIX = "AI_";

    public String newVendor(AiVendorInfo vendorInfo) {

        if (aiVendorInfoMapper.getByCompanyName(vendorInfo.getCompany()) != null) {
            throw new BceException("服务商已经存在");
        }

        if (aiVendorInfoMapper.getByUserTypeAndUserId(vendorInfo.getUserType(), vendorInfo.getUserId()) != null) {
            throw new BceException("用户已经入驻");
        }

        String vendorId = AI_PREFIX + IdUtils.generateUUID();
        vendorInfo.setVendorId(vendorId);

        int result = aiVendorInfoMapper.save(vendorInfo);

        if (result != 1) {
            throw new BceException("保存服务商信息失败");
        }

        return vendorId;
    }



    public void updateVendorInfo(AiVendorInfo vendorInfo) {

        AiVendorInfo newVendorInfo = aiVendorInfoMapper.getByVendorId(vendorInfo.getVendorId());
        if (newVendorInfo == null) {
            log.warn("no vendor info found for vendorId : {}", vendorInfo.getVendorId());
            throw new BceException("服务商不存在");
        }
        MBeanUtils.applyProperties(newVendorInfo, vendorInfo, AiVendorInfo.class);
        int updated = aiVendorInfoMapper.update(newVendorInfo);
        if (updated == 0) {
            log.warn("updated records is 0");
        }
    }

    public AiVendorInfo getByVendorId(String vendorId) {
        return aiVendorInfoMapper.getByVendorId(vendorId);
    }

    public AiVendorListModel getVendorListByIds(List<String> vendorIds) {
        if (CollectionUtils.isEmpty(vendorIds)) {
            return new AiVendorListModel(new ArrayList<>(), 0);
        }
        List<AiVendorInfo> vendorInfos = aiVendorInfoMapper.getVendorListByIds(vendorIds);
        return new AiVendorListModel(vendorInfos, vendorInfos.size());
    }

    public AiVendorListModel getApplicantList(String keyword, String order, String orderBy,
                                              int pageNo, int pageSize) {
        AiVendorListFilter filter = new AiVendorListFilter(keyword, order, orderBy, pageNo, pageSize);
        int totalCount = aiVendorInfoMapper.getAiVendorCount(filter);
        AiVendorListModel aiVendorListModel = new AiVendorListModel();

        if (totalCount > 0) {
            List<AiVendorInfo> aiVendorInfos = aiVendorInfoMapper.getAiVendorList(filter);
            aiVendorListModel.setTotalCount(totalCount);
            aiVendorListModel.setAiVendorInfo(aiVendorInfos);
        }
        return aiVendorListModel;
    }

}
