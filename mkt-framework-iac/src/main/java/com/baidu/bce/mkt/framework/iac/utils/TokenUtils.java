// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.utils;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.TokenHolder;
import com.baidu.bce.plat.webframework.iam.service.UserService;

/**
 * token utils
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class TokenUtils {
    public static AuthorizedToken getAuthorizedToken() {
        AuthorizedToken authorizedToken = TokenHolder.getAuthorizedToken();
        if (authorizedToken == null) {
            Token token = UserService.getSubjectToken();
            if (token != null) {
                authorizedToken = new ReceivedAuthorizedToken(token, null);
            }
        }
        return authorizedToken;
    }

    public static String getUserId() {
        AuthorizedToken authorizedToken = getAuthorizedToken();
        return authorizedToken == null ? null : authorizedToken.getUserId();
    }

    public static String getMainUserId() {
        AuthorizedToken authorizedToken = getAuthorizedToken();
        return authorizedToken == null ? null : authorizedToken.getMainUserId();
    }
}
