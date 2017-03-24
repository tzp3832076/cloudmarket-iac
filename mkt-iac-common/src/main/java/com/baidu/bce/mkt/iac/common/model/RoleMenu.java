/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/24 by sunfangyuan@baidu.com .
 */
@Slf4j
public enum RoleMenu {
    USER(new MenuShowModel(false, false)),
    VENDOR(new MenuShowModel(true, true)),
    INIT_VENDOR(new MenuShowModel(true, false));

    @Getter
    private MenuShowModel menuShowModel;

    private RoleMenu(MenuShowModel showModel) {
        this.menuShowModel = showModel;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuShowModel {
        private boolean showVendor;
        private boolean showProduct;
    }

    public static RoleMenu getRoleMenu(String role) {
        try {
            return RoleMenu.valueOf(role);
        } catch (Exception e) {
            log.warn("transfer role to menu failed. role {}", role);
            return RoleMenu.USER;
        }
    }
}
