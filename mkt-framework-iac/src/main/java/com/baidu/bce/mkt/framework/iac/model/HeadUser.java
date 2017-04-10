// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import java.util.List;

import lombok.Data;

/**
 * head user
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class HeadUser {
    private String userId;
    private List<String> targetVendorList;
}
