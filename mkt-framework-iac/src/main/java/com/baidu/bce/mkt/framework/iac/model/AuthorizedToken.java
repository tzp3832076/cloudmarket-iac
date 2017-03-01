// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * authorizedToken
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedToken {
    @Getter
    private Token token;
    private MktToken mktToken;

    public String getUserId() {
        return mktToken != null ? mktToken.getUserId() : token.getUser().getId();
    }

    public String getMainUserId() {
        return mktToken != null ? mktToken.getMainUserId() : token.getUser().getDomain().getId();
    }

    public String getVendorId() {
        return mktToken.getVendorId();
    }
}
