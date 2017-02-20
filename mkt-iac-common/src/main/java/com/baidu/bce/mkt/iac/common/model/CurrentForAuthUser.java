// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * current user, bce user or uuap user
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class CurrentForAuthUser {
    @Getter
    private String userId;
    @Getter
    private String mainUserId;

    public CurrentForAuthUser(String bceUserId, String bceMainUserId, boolean bceServiceAccount,
                              String requestedUserId) {
        if (StringUtils.isNotEmpty(requestedUserId)) {
            if (!bceServiceAccount) {
                log.info("requested user id = {}, is not empty but bce user is not service user",
                        requestedUserId);
                throw MktIacExceptions.noPermission();
            }
            this.userId = requestedUserId;
        } else {
            this.userId = bceUserId;
            this.mainUserId = bceMainUserId;
        }
    }
}
