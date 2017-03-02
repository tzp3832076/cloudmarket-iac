// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import java.util.Set;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.plat.webframework.iam.model.BceAuthContext;
import com.baidu.bce.plat.webframework.iam.model.BceRole;

/**
 * wrap bce auth context
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class BceAuthContextWrapper extends BceAuthContext {
    public BceAuthContextWrapper() {
        super();
    }

    public BceAuthContextWrapper(Token token) {
        super(token);
    }

    public boolean isServiceAccount() {
        Set<BceRole> roles = getBceRoles();
        return roles.contains(BceRole.SERVICE)
                || roles.contains(BceRole.PRIVILEGED_SERVICE)
                || roles.contains(BceRole.CLOUD_ADMIN);
    }
}
