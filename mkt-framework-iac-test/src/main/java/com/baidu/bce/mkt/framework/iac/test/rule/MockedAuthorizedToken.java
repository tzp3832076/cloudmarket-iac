// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.test.rule;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.BceAuthContextWrapper;
import com.baidu.bce.mkt.framework.iac.model.HeadUser;

import lombok.AllArgsConstructor;

/**
 * mocked authorized token
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
public class MockedAuthorizedToken implements AuthorizedToken {
    private BceAuthContextWrapper bceAuthContextWrapper;
    private HeadUser headUser;
    private CurrentVendor currentVendor;

    @Override
    public String getUserId() {
        if (headUser != null && StringUtils.isNotBlank(headUser.getUserId())) {
            if (!bceAuthContextWrapper.isServiceAccount()) {
                throw new IllegalStateException("current user is not service account user");
            }
            return headUser.getUserId();
        } else {
            return bceAuthContextWrapper.getUserId();
        }
    }

    @Override
    public String getMainUserId() {
        if (headUser != null && StringUtils.isNotBlank(headUser.getUserId())) {
            return null;
        } else {
            return bceAuthContextWrapper.getDomainId();
        }
    }

    @Override
    public String getVendorId() {
        if (currentVendor == null || !currentVendor.hasId()) {
            return null;
        } else {
            String vendorId = currentVendor.vendorId();
            if (StringUtils.isBlank(vendorId)) {
                vendorId = UUID.randomUUID().toString();
            }
            return vendorId;
        }
    }
}
