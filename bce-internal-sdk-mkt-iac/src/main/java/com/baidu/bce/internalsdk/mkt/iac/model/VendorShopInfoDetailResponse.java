/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created on 2017/4/11 by sunfangyuan@baidu.com .
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorShopInfoDetailResponse {
    private String vendorId;
    private String bceAccount;
    private String companyName;
    private String companyDescription;
    private String baiduWalletAccount;
    private String serviceEmail;
    private String servicePhone;
    private String serviceAvailTime;
    private List<OnlineSupport> baiduQiaos;
}
