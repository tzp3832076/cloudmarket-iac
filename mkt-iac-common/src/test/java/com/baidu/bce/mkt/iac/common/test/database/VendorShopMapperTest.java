/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@InitDatabase(tables = "mkt_vendor_shop")
public class VendorShopMapperTest extends BaseMapperTest {
    @Autowired
    private VendorShopMapper shopMapper;

    @Test
    public void add() throws Exception {
        VendorShop vendorShop = new VendorShop("test", "test", "test", "test", "test", "test");
        int res = shopMapper.add(vendorShop);
        Assert.assertEquals(res, 1);
    }

    @Test
    public void getVendorShopByVendorId() throws Exception {
        VendorShop vendorShop = shopMapper.getVendorShopByVendorId("vendor_1");
        Assert.assertNotNull(vendorShop);
    }

}