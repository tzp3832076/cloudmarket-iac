/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.bce.mkt.iac.common.client.IacClientFactory;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Component
public class ProductHandler {
    @Autowired
    private IacClientFactory clientFactory;

    public int getProductsOnSaleCount(String vendorId) {
        // todo 调用商品模块的接口
        return 0;
    }
    public int getProductsAuditingCount(String vendorId) {
        // todo 调用商品模块的接口
        return 0;
    }
}
