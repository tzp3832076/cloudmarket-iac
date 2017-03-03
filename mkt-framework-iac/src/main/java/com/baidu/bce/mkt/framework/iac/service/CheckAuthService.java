// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.service;

import java.util.List;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.BceAuthContextWrapper;
import com.baidu.bce.mkt.framework.iac.model.HeadUser;

/**
 * check auth service interface
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface CheckAuthService {
    String BEAN_NAME = "checkAuthService";

    AuthorizedToken checkAuth(BceAuthContextWrapper bceAuthContextWrapper, HeadUser headUser,
                              String resource, String operation, List<String> instances);
}
