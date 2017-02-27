/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service.notice.model;

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
    private String capital;
    private String sale;
    private String headcount;
    private String address;
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
