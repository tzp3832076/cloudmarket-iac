// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * authentication request model
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationRequest {
    @NotNull
    @Valid
    private BceToken bceToken;
    private HeaderUser headerUser;
    @NotBlank
    private String operation;
    @NotBlank
    private String resource;
    private List<String> instances;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BceToken {
        private String username;
        @NotBlank
        private String userId;
        private String mainUserId;
        private boolean serviceAccount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HeaderUser {
        private String userId;
        private List<String> targetVendorList;
    }
}
