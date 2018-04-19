/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.internalsdk.mkt.iac.model;

import java.util.Date;
import java.util.List;

import com.baidu.bce.internalsdk.core.BceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangbin33@baidu.com on 2018/4/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorListResponseV2 {
    private List<VendorBaseInfo> vendorBaseInfoList;
    private int totalCount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VendorBaseInfo {
        private String companyName;
        private String bceAccount;
        private String business;
        private String vendorId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT,
                timezone = "UTC")
        private Date hostedTime;
    }
}
