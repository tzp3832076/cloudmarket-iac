// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service.checker;

import java.util.List;

import com.baidu.bce.mkt.iac.common.model.UserIdentity;

/**
 * local instance checker
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface LocalInstanceChecker {
    String[] supportResources();

    boolean doCheck(UserIdentity userIdentity, List<String> instances);
}
