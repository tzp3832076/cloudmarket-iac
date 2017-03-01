// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

/**
 * token holder
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class TokenHolder {
    private static ThreadLocal<AuthorizedToken> tokenThreadLocal = new ThreadLocal<>();

    public static AuthorizedToken getAuthorizedToken() {
        return tokenThreadLocal.get();
    }

    public static void setAuthorizedToken(AuthorizedToken authorizedToken) {
        tokenThreadLocal.set(authorizedToken);
    }

    public static void removeAuthorizedToken() {
        tokenThreadLocal.remove();
    }
}
