/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorMarginMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorMargin;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@InitDatabase(tables = "mkt_vendor_margin")
public class VendorMarginMapperTest extends BaseMapperTest {
    @Autowired
    private VendorMarginMapper vendorMarginMapper;

    @Test
    public void add() throws Exception {
        VendorMargin vendorMargin = new VendorMargin(
                "test", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
        int res = vendorMarginMapper.add(vendorMargin);
        Assert.assertEquals(1, res);
    }

    @Test
    public void getVendorMargin() throws Exception {
        VendorMargin vendorMargin = vendorMarginMapper.getVendorMargin("vendor_1");
        Assert.assertNotNull(vendorMargin);
        Assert.assertTrue(vendorMargin.isPayOff());
        vendorMargin = vendorMarginMapper.getVendorMargin("vendor_2");
        Assert.assertNotNull(vendorMargin);
        Assert.assertFalse(vendorMargin.isPayOff());
        vendorMargin = vendorMarginMapper.getVendorMargin("vendor_XXX");
        Assert.assertNull(vendorMargin);
    }

    @Test
    public void update() throws Exception {
        VendorMargin vendorMargin = vendorMarginMapper.getVendorMargin("vendor_1");
        Assert.assertNotNull(vendorMargin);
        Assert.assertTrue(vendorMargin.isPayOff());
        vendorMargin.setPayValue(BigDecimal.valueOf(200));
        vendorMarginMapper.update(vendorMargin);
        vendorMargin = vendorMarginMapper.getVendorMargin("vendor_1");
        Assert.assertNotNull(vendorMargin);
        Assert.assertFalse(vendorMargin.isPayOff());
    }

}