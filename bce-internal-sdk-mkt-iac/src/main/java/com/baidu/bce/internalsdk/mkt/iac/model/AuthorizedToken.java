// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac.model;

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
public class AuthorizedToken {
    private TargetIdentity targetIdentity;
    private OriginalIdentity originalIdentity;
    private String role;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TargetIdentity {
        // bce user id or vendor id
        private String id;
        // VENDOR_ID | VENDOR_USER | NORMAL_USER | AGENT_USER
        private String type;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OriginalIdentity {
        // bce user id or uuap name
        private String userId;
        // BCE | UUAP
        private String type;
        // bce账号的主账号，不是子用户时或是uuap账号时，该值为null
        private String mainUserId;
    }
}
