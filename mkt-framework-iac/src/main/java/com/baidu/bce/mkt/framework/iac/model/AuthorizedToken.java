// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * authorizedToken interface
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface AuthorizedToken {
    String getUserId();

    String getMainUserId();

    String getVendorId();
}
