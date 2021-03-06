// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * identity token model
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MktToken {
    // bce user id or uuap name
    private String userId;
    // BCE | UUAP
    private String type;
    private String vendorId;
    private List<String> targetVendorList;
    // bce账号的主账号，不是子用户时或是uuap账号时，该值和userId相同
    private String mainUserId;
    private String role;
}
