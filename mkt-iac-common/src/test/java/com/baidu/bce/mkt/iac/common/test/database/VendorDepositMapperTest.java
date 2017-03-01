/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorDepositMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@InitDatabase(tables = "mkt_vendor_deposit")
public class VendorDepositMapperTest extends BaseMapperTest {
    @Autowired
    private VendorDepositMapper vendorDepositMapper;

    @Test
    public void add() throws Exception {
        VendorDeposit vendorDeposit = new VendorDeposit(
                "test", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
        int res = vendorDepositMapper.add(vendorDeposit);
        Assert.assertEquals(1, res);
    }

    @Test
    public void getVendorDeposit() throws Exception {
        VendorDeposit vendorDeposit = vendorDepositMapper.getVendorDeposit("vendor_1");
        Assert.assertNotNull(vendorDeposit);
        Assert.assertTrue(vendorDeposit.isPayOff());
        vendorDeposit = vendorDepositMapper.getVendorDeposit("vendor_2");
        Assert.assertNotNull(vendorDeposit);
        Assert.assertFalse(vendorDeposit.isPayOff());
        vendorDeposit = vendorDepositMapper.getVendorDeposit("vendor_XXX");
        Assert.assertNull(vendorDeposit);
    }

    @Test
    public void update() throws Exception {
        VendorDeposit vendorDeposit = vendorDepositMapper.getVendorDeposit("vendor_1");
        Assert.assertNotNull(vendorDeposit);
        Assert.assertTrue(vendorDeposit.isPayOff());
        vendorDeposit.setPayValue(BigDecimal.valueOf(200));
        vendorDepositMapper.update(vendorDeposit);
        vendorDeposit = vendorDepositMapper.getVendorDeposit("vendor_1");
        Assert.assertNotNull(vendorDeposit);
        Assert.assertFalse(vendorDeposit.isPayOff());
    }

}