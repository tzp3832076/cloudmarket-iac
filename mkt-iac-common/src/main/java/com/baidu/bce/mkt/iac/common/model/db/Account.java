// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import java.security.Timestamp;

import lombok.Data;

/**
 * account model
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class Account {
    private long id;
    private String accountId;
    private AccountType accountType;
    private String role;
    private String vendorId;
    private Timestamp createTime;
    private Timestamp updateTime;
}
