/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.bce.internalsdk.mkt.iac.model.VendorPayeeSyncRequest;
import com.baidu.bce.mkt.iac.common.model.db.VendorPayee;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */
@Slf4j
public class VendorPayeeControllerTest extends ApiMockMvcTest {


    @Test
    public void testSyncVendorPayeeInfo() {
        String vendorId = "vendorID";
        String companyLocationProvince = "gd";
        String companyLocationCity = "gz";
        String bankName = "ABC";
        String branchBankName = "ABCBJ";
        String bankCardNumber = "123123";
        String bankLocationProvince = "hb";
        String bankLocationCity = "wh";
        doAnswer(invocationOnMock -> {
            VendorPayee vendorPayee = (VendorPayee) invocationOnMock.getArguments()[0];
            log.info("{}", vendorPayee);
            Assert.assertEquals(vendorId, vendorPayee.getVendorId());
            Assert.assertEquals(companyLocationProvince, vendorPayee.getCompanyLocationProvince());
            Assert.assertEquals(companyLocationCity, vendorPayee.getCompanyLocationCity());
            Assert.assertEquals(bankName, vendorPayee.getBankName());
            Assert.assertEquals(branchBankName, vendorPayee.getBranchBankName());
            Assert.assertEquals(bankCardNumber, vendorPayee.getBankCardNumber());
            Assert.assertEquals(bankLocationProvince, vendorPayee.getBankLocationProvince());
            Assert.assertEquals(bankLocationCity, vendorPayee.getBankLocationCity());
            Assert.assertEquals(true, vendorPayee.isValid());
            return (Void) null;
        }).when(vendorPayeeService).syncPayeeInfoToDb(any());
        VendorPayeeSyncRequest request = new VendorPayeeSyncRequest();
        request.setBankCardNumber(bankCardNumber);
        request.setBankName(bankName);
        request.setBranchBankName(branchBankName);
        request.setVendorId(vendorId);
        request.setBankLocation(new VendorPayeeSyncRequest.Location(bankLocationProvince, bankLocationCity));
        request.setCompanyLocation(new VendorPayeeSyncRequest.Location(companyLocationProvince, companyLocationCity));
        mktIacClient.syncVendorPayee(request);
        verify(vendorPayeeService, times(1)).syncPayeeInfoToDb(any());

    }

    @Test
    public void testDoInvalid() {
        String vendorId= "HAHAHAHA";
        doAnswer(invocationOnMock -> {
            Assert.assertEquals(vendorId, invocationOnMock.getArguments()[0]);
            return (Void)null;
        }).when(vendorPayeeService).doInvalid(anyString());
        mktIacClient.doInvalidVendorPayee(vendorId);
        verify(vendorPayeeService, times(1)).doInvalid(any());

    }

}
