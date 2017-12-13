// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * owner info for redis
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class OwnerInfoForRedis {
    private String userId;
    private String vendorId;

    public OwnerInfoForRedis(UserIdentity userIdentity) {
        this.userId = userIdentity.getUserId();
        this.vendorId = userIdentity.getVendorId();
    }

    @JsonIgnore
    public boolean isOwnerEqualTo(UserIdentity userIdentity) {
        return StringUtils.equals(userId, userIdentity.getUserId())
                && StringUtils.equals(vendorId, userIdentity.getVendorId());
    }
}
