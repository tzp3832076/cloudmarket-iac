/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.m .
 */
@Slf4j
@Service
public class NoticeService {
    @Autowired
    private VendorInfoMapper vendorInfoMapper;

    public void auditNoticeApplication(String status, VendorInfo vendorInfo) {
        if (InfoStatus.PASS.name().equals(status)) {
            VendorInfo temp = vendorInfo == null
                                      ? null : vendorInfoMapper.getVendorInfoByVendorId(vendorInfo.getVendorId());
            if (temp == null) {
                vendorInfoMapper.add(vendorInfo);
            } else {
                log.warn("auditNoticeApplication is exist. vendorId {}", vendorInfo.getVendorId());
            }
        }
    }
}
