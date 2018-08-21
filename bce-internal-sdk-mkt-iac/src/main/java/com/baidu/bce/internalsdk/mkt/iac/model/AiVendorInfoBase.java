/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiVendorInfoBase {
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

}
