/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorInfoDetailResponse {
    private VendorInfoDetail data;
    private ParamMapModel map;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class VendorInfoDetail {
        private String companyName;
        private String serviceHotline;
        private String bizContact;
        private String emerContact;
        private String techContact;
        private String companyPhone;
        private String joinedOtherMarkets;
        private String bizContactPhone;
        private String emerContactPhone;
        private String techContactPhone;
        private String companySite;
        private int companyCapital;
        private String companyAddress;
        private String bizContactEmail;
    }
}
