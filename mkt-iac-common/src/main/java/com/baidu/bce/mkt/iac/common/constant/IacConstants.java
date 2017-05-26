// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.constant;

import java.math.BigDecimal;

/**
 * iac constants
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class IacConstants {
    public static final BigDecimal DEFAULT_MARGIN = BigDecimal.valueOf(10000);
    public static final String INFO_EMPTY = "信息为空";
    public static final String FORMAT_ERROR = "格式有误";

    public static final String RESOURCE_VENDOR_OVERVIEW = "vendorOverview";
    public static final String RESOURCE_VENDOR_INFO = "vendorInfo";
    public static final String RESOURCE_VENDOR_SHOP = "vendorShop";
    public static final String RESOURCE_VENDOR_CONTRACT_DEPOSIT = "vendorContractAndDeposit";

    public static final String AUDIT_VENDOR_SHOP = "VENDOR_SHOP";

    public static final String ROLE_INIT_VENDOR = "INIT_VENDOR";
    public static final String ROLE_VENDOR = "VENDOR";
    public static final String ROLE_OP = "OP";
    public static final String ROLE_SERVICE = "SERVICE";

    public static final String DEFAULT_ROLE = "USER";
    public static final String SUPPORT_URL_PRE = "https://ikefu.baidu.com";
}
