/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Data
@NoArgsConstructor
@Slf4j
public class VendorInfo {
    private String vendorId;
    private String bceUserId;
    private VendorStatus status;
    private String company;
    private String website;
    private int capital;
    private int headcount;
    private String address;
    private String telephone;
    private String email;
    private String serviceIllustration;
    private String serviceCategory;
    private String hotline;
    private String otherMarket;
    private String contactInfo;
    private String walletId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private ProcessStatus agreementStatus;


    public VendorInfo(String vendorId, String bceUserId, VendorStatus status, String company,
                      String website, int capital, int headcount, String address, String telephone, String email,
                      String serviceIllustration, String serviceCategory,
                      String hotline, String otherMarket, String contactInfo) {
        this.vendorId = vendorId;
        this.bceUserId = bceUserId;
        this.status = status;
        this.company = company;
        this.website = website;
        this.capital = capital;
        this.headcount = headcount;
        this.email = email;
        this.serviceIllustration = serviceIllustration;
        this.address = address;
        this.telephone = telephone;
        this.serviceCategory = serviceCategory;
        this.hotline = hotline;
        this.otherMarket = otherMarket;
        this.contactInfo = contactInfo;
        this.walletId = "";
    }
}
