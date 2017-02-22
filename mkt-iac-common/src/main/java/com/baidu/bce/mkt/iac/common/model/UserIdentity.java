// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.mkt.iac.common.model.db.Account;

import lombok.Getter;

/**
 * user identity model for authorized user info
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class UserIdentity {
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
}
