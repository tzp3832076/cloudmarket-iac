/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return;
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

}
