/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorPayeeMapper;
import com.baidu.bce.mkt.iac.common.test.utils.getPayeePrototype
import com.baidu.bce.mkt.iac.common.test.utils.objEquals

@InitDatabase(tables = ["mkt_vendor_payee"])
class VendorPayeeMapperTest() : BaseMapperTest() {

    @Autowired
    private lateinit var vendorPayeeMapper : VendorPayeeMapper

    @Test
    fun testSave() {
        val prototype =  getPayeePrototype()
        var vendorPayee = vendorPayeeMapper.getVendorPayee(prototype.vendorId)
        Assert.assertNull(vendorPayee)
        vendorPayeeMapper.save(prototype)
        vendorPayee = vendorPayeeMapper.getVendorPayee(prototype.vendorId)
        Assert.assertNotNull(vendorPayee)
        Assert.assertTrue(objEquals(prototype, vendorPayee, emptyList()))
    }

    @Test
    fun testGetValidPayee() {
        var vendorPayee = vendorPayeeMapper.getValidVendorPayee("vendor1")
        Assert.assertNotNull(vendorPayee)
        vendorPayee  = vendorPayeeMapper.getValidVendorPayee("vendor2")
        Assert.assertNull(vendorPayee)
    }

    @Test
    fun testUpdateValidFlag() {
        var vendorPayee = vendorPayeeMapper.getValidVendorPayee("vendor1");
        Assert.assertTrue(vendorPayee.isValid);
        vendorPayeeMapper.updateValidFlag(false, vendorPayee.vendorId);
        vendorPayee = vendorPayeeMapper.getVendorPayee("vendor1");
        Assert.assertFalse(vendorPayee.isValid)
    }

    @Test
    fun testUpdateVendorPayee() {
        var vendorPayee = vendorPayeeMapper.getVendorPayee("vendor2")
        val prototype = getPayeePrototype()
        prototype.vendorId = "vendor2"
        Assert.assertFalse(objEquals(prototype, vendorPayee, emptyList()))
        vendorPayeeMapper.updateVendorPayee(prototype)
        vendorPayee  = vendorPayeeMapper.getVendorPayee("vendor2")
        Assert.assertTrue(objEquals(prototype, vendorPayee, emptyList()))

    }

    @Test
    fun testUpdateTaxFlag()  {
        var vendorPayee = vendorPayeeMapper.getVendorPayee("vendor2")
        Assert.assertEquals("SPECIAL", vendorPayee.taxFlag)
        vendorPayeeMapper.updateTaxFlag("NORMAL",  "vendor2")
        vendorPayee = vendorPayeeMapper.getVendorPayee("vendor2")
        Assert.assertEquals("NORMAL",  vendorPayee.taxFlag)
    }
}
