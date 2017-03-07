/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import java.util.List;

import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ParamMapModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorShopAuditContent {
    private ShopDraft data;
    private ParamMapModel map;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ShopDraft {
        private String companyName;
        private String bceAccount;
        private String companyDescription;
        private String baiduWalletAccount;
        private String serviceEmail;
        private String servicePhone;

        private String serviceAvailTime;
        private List<OnlineSupport> baiduQiaos;
    }
}
