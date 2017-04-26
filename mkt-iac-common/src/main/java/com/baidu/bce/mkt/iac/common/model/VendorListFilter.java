// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;

import lombok.Data;

/**
 * vendor list filter
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class VendorListFilter {
    private String bceUserId;
    private String companyName;
    private VendorStatus status;

    public VendorListFilter(String bceUserId, String companyName, VendorStatus status) {
        if (StringUtils.isNotBlank(bceUserId)) {
            this.bceUserId = bceUserId;
        }
        if (StringUtils.isNotBlank(companyName)) {
            this.companyName = "%" + companyName + "%";
        }
        this.status = status;
    }
}
