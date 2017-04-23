// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import java.util.List;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;

import lombok.AllArgsConstructor;

/**
 * local token wrap UserIdentity
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
public class LocalAuthorizedToken implements AuthorizedToken {
    private UserIdentity userIdentity;
    private List<String> targetVendorList;

    @Override
    public String getUserId() {
        return userIdentity.getUserId();
    }

    @Override
    public String getMainUserId() {
        return userIdentity.getMainUserId();
    }

    @Override
    public String getUserType() {
        return userIdentity.getAccountType();
    }

    @Override
    public String getVendorId() {
        return userIdentity.getVendorId();
    }

    @Override
    public List<String> getTargetVendorList() {
        return targetVendorList;
    }
}
