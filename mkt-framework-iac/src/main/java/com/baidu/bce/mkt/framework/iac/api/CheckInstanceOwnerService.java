// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.api;

import java.util.List;

import com.baidu.bce.mkt.framework.iac.client.model.CheckInstanceOwnerRequest;

/**
 * interface for business module to impl
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface CheckInstanceOwnerService {
    boolean isOwner(CheckInstanceOwnerRequest.UserInfo userInfo, String resource, List<String> instances);
}
