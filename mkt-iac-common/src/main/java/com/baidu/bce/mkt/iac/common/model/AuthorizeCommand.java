// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * authorize command
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
@AllArgsConstructor
public class AuthorizeCommand {
    private String bceUsername;
    private String bceUserId;
    private String bceMainUserId;
    private boolean serviceAccount;
    private String requestedUserId;
    private List<String> targetVendorList;
    private String resource;
    private String operation;
    private List<String> instances;
}
