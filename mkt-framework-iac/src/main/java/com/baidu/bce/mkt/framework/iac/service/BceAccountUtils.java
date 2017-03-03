// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.service;

import java.util.Set;

import com.baidu.bce.plat.webframework.iam.model.BceAuthContext;
import com.baidu.bce.plat.webframework.iam.model.BceRole;

/**
 * bce account utils
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class BceAccountUtils {
    public static boolean isServiceAccount(BceAuthContext bceAuthContext) {
        Set<BceRole> roles = bceAuthContext.getBceRoles();
        return roles.contains(BceRole.SERVICE)
                || roles.contains(BceRole.PRIVILEGED_SERVICE)
                || roles.contains(BceRole.CLOUD_ADMIN);
    }
}
