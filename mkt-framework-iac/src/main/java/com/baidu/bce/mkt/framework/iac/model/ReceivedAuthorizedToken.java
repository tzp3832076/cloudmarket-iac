// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * authorized token impl for receive from response
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedAuthorizedToken implements AuthorizedToken {
    @Getter
    private Token token;
    private MktToken mktToken;

    @Override
    public String getUserId() {
        return mktToken != null ? mktToken.getUserId() : token.getUser().getId();
    }

    @Override
    public String getMainUserId() {
        return mktToken != null ? mktToken.getMainUserId() : token.getUser().getDomain().getId();
    }

    @Override
    public String getUserType() {
        String type = mktToken.getType();
        if (StringUtils.isBlank(type)) {
            throw new InvalidUserTypeException();
        }
        return type;
    }

    @Override
    public String getVendorId() {
        return mktToken.getVendorId();
    }

    @Override
    public List<String> getTargetVendorList() {
        return mktToken.getTargetVendorList();
    }
}
