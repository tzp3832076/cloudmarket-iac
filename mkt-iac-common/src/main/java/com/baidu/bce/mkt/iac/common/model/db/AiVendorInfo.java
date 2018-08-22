/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by chenxiang05@baidu.com on 2018/8/6.
 */

@Data
public class AiVendorInfo {
    private Long id;
    private String vendorId;
    private String userId;
    private String userType;
    private String company;
    private String website;
    private Long capital;
    private Long headcount;
    private String enterpriseEmail;
    private String businessContact;
    private String mobile;
    private String email;
    private String emergencyContact;
    private String telephone;
    private String serviceType;
    private String applicationArea;
    private String businessIntroduction;
    private Timestamp createTime;
    private Timestamp updateTime;
}
