/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.internalsdk.mkt.iac.model;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotBlank;

import com.baidu.bce.internalsdk.core.BceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kuangzhen on 2017/12/19.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest {
    @NotBlank(message = "vendorId不能为空")
    private String vendorId;
    @NotBlank(message = "合同号不能为空")
    private String contract;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
    private Timestamp contractBeginTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
    private Timestamp contractEndTime;
}
