/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * user identity model for authorized user info
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class UserIdentity {
    public static final String DEFAULT_ACCOUNT_TYPE = "BCE";
    public static final String DEFAULT_ROLE = "USER";
    public static final String ROLE_OP = "OP";

    @Getter
    private String userId;
    private Account account;
    @Getter
    private String role = DEFAULT_ROLE;

    public UserIdentity(String userId, Account account) {
        this.userId = userId;
        if (account != null) {
            this.account = account;
            this.role = account.getRole();
        }
    }

    public String getVendorId() {
        if (account == null) {
            return null;
        }
        String vendorId = account.getVendorId();
        return StringUtils.isEmpty(vendorId) ? null : vendorId;
    }

    public boolean isOp() {
        return ROLE_OP.equals(role);
    }

    public String getAccountType() {
        return account == null ? DEFAULT_ACCOUNT_TYPE : account.getAccountType().name();
    }

    public String getMainUserId() {
        // 临时使用uerid， 后续再改
        return getUserId();
    }
}
