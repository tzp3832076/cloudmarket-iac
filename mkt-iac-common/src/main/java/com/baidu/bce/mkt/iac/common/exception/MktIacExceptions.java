// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.exception;

import com.baidu.bce.plat.webframework.exception.BceException;

/**
 * exception collection for mkt iac
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktIacExceptions {
    public static BceException noPermission() {
        return new BceException("没有相应权限", 403, "NoPermission");
    }

    public static BceException statusInAudit() {
        return new BceException("处于待审核状态，不可以编辑", 403, "StatusInAudit");
    }

    public static BceException noVendorInfo() {
        return new BceException("无服务商信息", 403, "NoVendorInfo");
    }

    public static BceException notJsonFormat() {
        return new BceException("JSON 转化失败", 403, "NotJsonFormat");
    }
}
