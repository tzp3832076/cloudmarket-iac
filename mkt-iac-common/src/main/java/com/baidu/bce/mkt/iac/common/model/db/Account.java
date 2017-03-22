// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * account model
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long id;
    private String accountId;
    private AccountType accountType;
    private String role;
    private String vendorId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Account(String accountId, AccountType type, String role, String vendorId) {
        this.accountId = accountId;
        this.accountType = type;
        this.role = role;
        this.vendorId = vendorId;
    }
}
