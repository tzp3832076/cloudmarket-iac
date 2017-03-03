// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;

import lombok.AllArgsConstructor;

/**
 * local token wrap UserIdentity
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
public class LocalAuthorizedToken implements AuthorizedToken {
    private UserIdentity userIdentity;

    @Override
    public String getUserId() {
        return userIdentity.getUserId();
    }

    @Override
    public String getMainUserId() {
        return userIdentity.getMainUserId();
    }

    @Override
    public String getVendorId() {
        return userIdentity.getVendorId();
    }
}
