// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service.checker;

import static com.baidu.bce.mkt.iac.common.constant.IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT;
import static com.baidu.bce.mkt.iac.common.constant.IacConstants.RESOURCE_VENDOR_INFO;
import static com.baidu.bce.mkt.iac.common.constant.IacConstants.RESOURCE_VENDOR_SHOP;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.mkt.iac.common.model.UserIdentity;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class VendorRelatedInstanceChecker implements LocalInstanceChecker {
    @Override
    public String[] supportResources() {
        return new String[]{RESOURCE_VENDOR_INFO, RESOURCE_VENDOR_SHOP, RESOURCE_VENDOR_CONTRACT_DEPOSIT};
    }

    @Override
    public boolean doCheck(UserIdentity userIdentity, List<String> instances) {
        String vendorId = userIdentity.getVendorId();
        if (StringUtils.isEmpty(vendorId)) {
            log.info("no vendor id in user identity");
            return false;
        }
        for (String instance : instances) {
            if (!vendorId.equals(instance)) {
                log.info("current instance = {} not equal vendor id = {}", instance, vendorId);
                return false;
            }
        }
        return true;
    }
}
