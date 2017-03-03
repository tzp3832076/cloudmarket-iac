// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.exception;

import com.baidu.bce.plat.webframework.exception.BceException;

/**
 * exception collection for mkt iac
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktIacExceptions {
    public static BceException noPermission() {
        return new BceException("No Permission", 403, "NoPermission");
    }

    public static BceException statusInAudit() {
        return new BceException("处于待审核状态，不可以编辑", 403, "StatusInAudit");
    }

    public static BceException noVendorInfo() {
        return new BceException("No Vendor Info", 403, "NoVendorInfo");
    }

    public static BceException emailNotValid() {
        return new BceException("邮箱信息不合法", 403, "EmailNotValid");
    }

    public static BceException cellphoneNotValid() {
        return new BceException("手机信息不合法", 403, "CellphoneNotValid");
    }

    public static BceException infoEmpty() {
        return new BceException("信息不可以为空", 403, "infoEmpty");
    }
}
