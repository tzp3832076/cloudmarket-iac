// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

    List<String> getTargetVendorList();

    default String getValidVendorId() {
        String vendorId = getVendorId();
        if (StringUtils.isBlank(vendorId)) {
            throw new InvalidVendorException();
        }
        return vendorId;
    }
}
