/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac.model;

import java.sql.Timestamp;
import java.util.List;

import com.baidu.bce.internalsdk.core.BceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2017/3/6 by sunfangyuan@baidu.com .
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorContractResponse {
    private String vendorName;
    private List<ContractInfo> contractInfoList;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContractInfo {
        private String contractNum;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
        private Timestamp contractBeginTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
        private Timestamp contractEndTime;
    }
}
