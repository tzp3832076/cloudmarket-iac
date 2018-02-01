///*
// * Copyright 2017 Baidu Inc. All rights reserved.
// */
//package com.baidu.bce.mkt.iac.common.test.database;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.baidu.bae.commons.test.InitDatabase;
//import com.baidu.bce.mkt.iac.common.mapper.VendorPayeeMapper;
//import com.baidu.bce.mkt.iac.common.model.db.VendorPayee;
//
///**
// * Created by chenxiang05@baidu.com on 2018/2/1.
// */
//@InitDatabase(tables = "mkt_vendor_payee")
//public class VendorPayeeMapperTest extends BaseMapperTest{
//
//    @Autowired
//    private VendorPayeeMapper vendorPayeeMapper;
//
//    @Test
//    public void testSave() {
//        VendorPayee vendorPayee = vendorPayeeMapper.getVendorPayee("SOAR");
//        Assert.assertNull(vendorPayee);
//        vendorPayee = new VendorPayee();
//        vendorPayee.setVendorId("SOAR");
//        vendorPayee.setBankCardNumber("123456");
//        vendorPayee.setBankLocationCity("bj");
//        vendorPayee.setBankLocationProvince("hd");
//        vendorPayee.setBranchBankName("zllll");
//        vendorPayee.setBankName("hahah");
//        vendorPayee.setValid(true);
//        vendorPayee.setCompanyLocationProvince("gz");
//        vendorPayee.setCompanyLocationCity("sz");
//        vendorPayeeMapper.save(vendorPayee);
//        vendorPayee = vendorPayeeMapper.getVendorPayee("SOAR");
//        Assert.assertNotNull(vendorPayee);
//        Assert.assertEquals("SOAR", vendorPayee.getVendorId());
//        Assert.assertEquals("123456", vendorPayee.getBankCardNumber());
//        Assert.assertEquals("bj", vendorPayee.getBankLocationProvince());
//        Assert.assertEquals("hd", vendorPayee.getBankLocationCity());
//        Assert.assertEquals("zllll", vendorPayee.getBranchBankName());
//        Assert.assertEquals("hahah", vendorPayee.getBankName());
//        Assert.assertEquals(true, vendorPayee.isValid());
//        Assert.assertEquals("gz", vendorPayee.getCompanyLocationProvince());
//        Assert.assertEquals("sz", vendorPayee.getCompanyLocationCity());
//    }
//
//}
