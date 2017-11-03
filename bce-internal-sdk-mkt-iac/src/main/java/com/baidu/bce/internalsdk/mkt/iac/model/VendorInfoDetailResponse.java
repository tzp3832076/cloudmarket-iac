/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac.model;

import java.util.List;

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
        private String bizContact;
        private String companyAddress;
        private String bizContactPhone;
        private int companyHeadcount;
        private String bizContactEmail;
        private int companyCapital;
        private String emerContact;
        private String companyEmail;
        private String emerContactPhone;
        private String companySite;
        private String serviceIllustration;
        private List<String> serviceCategory;
        private String serviceHotline;
        private String joinedOtherMarkets;
    }
}
