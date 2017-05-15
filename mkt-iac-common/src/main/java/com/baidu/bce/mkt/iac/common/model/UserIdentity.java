/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.AccountType;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * user identity model for authorized user info
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class UserIdentity {


    @Getter
    private String userId;
    private Account account;
    @Getter
    private String role = IacConstants.DEFAULT_ROLE;

    public UserIdentity(String userId, Account account) {
        this.userId = userId;
        if (account != null) {
            this.account = account;
            this.role = account.getRole();
            // 加个账号类型的检查，避免人为添加数据库的记录的时候出错
            if (userId.startsWith(AccountType.UUAP.name())
                    && AccountType.UUAP != account.getAccountType()) {
                log.warn("current user id = {}, type not uuap, need manual check", userId);
            }
        }
    }

    public String getVendorId() {
        if (account == null) {
            return null;
        }
        String vendorId = account.getVendorId();
        return StringUtils.isEmpty(vendorId) ? null : vendorId;
    }

    public boolean hasPrivilege() {
        return role.startsWith(IacConstants.ROLE_OP) || IacConstants.ROLE_SERVICE.equals(role);
    }

    public String getAccountType() {
        if (account == null) {
            if (userId.startsWith(AccountType.UUAP.name())) {
                return AccountType.UUAP.name();
            } else {
                return AccountType.BCE.name();
            }
        } else {
            return account.getAccountType().name();
        }
    }

    public String getMainUserId() {
        // 临时使用uerid， 后续再改
        return getUserId();
    }
}
