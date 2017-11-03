/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationNoticeBody {
    private String bceUserId;
    private String company;
    private String website;
    private int capital;
    @Deprecated
    private String sale;
    private int headcount;
    private String address;
    @Deprecated
    private String telephone;
    private String contactInfo;
    private String email;
    private String serviceIllustration;
    private String serviceCategory;
    private String hotline;
    private String market;
    private String auditId;
    private String vendorId;
}
