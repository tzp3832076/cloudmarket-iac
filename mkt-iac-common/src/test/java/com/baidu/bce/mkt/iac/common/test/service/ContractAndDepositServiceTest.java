/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.mapper.VendorContractMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;
import com.baidu.bce.mkt.iac.common.service.ContractAndDepositService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Slf4j
public class ContractAndDepositServiceTest extends BaseCommonServiceTest {
    @Autowired
    private ContractAndDepositService service;
    @Autowired
    private VendorContractMapper contractMapper;

    @Test
    public void updateVendorDeposit() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("confirmVendorDeposit success");
                return null;
            }
        }). when(auditClient).confirmVendorMargin(anyString());
        service.updateVendorDeposit("vendor_1", new BigDecimal(300));
        VendorDeposit deposit = service.getVendorDeposit("vendor_1");
        Assert.assertFalse(deposit.isPayOff());
    }

    @Test
    public void getVendorDeposit() throws Exception {
        VendorDeposit deposit = service.getVendorDeposit("vendor_1");
        Assert.assertNotNull(deposit);
    }

    @Test
    public void updateVendorContentList() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("confirmVendorContract success");
                return null;
            }
        }). when(auditClient).confirmVendorContract(anyString());
        String vendorId = "vendor_2";
        List<VendorContract> vendorContractList = contractMapper.getVendorContractList(vendorId);
        Assert.assertEquals(vendorContractList.size(), 1);
        vendorContractList.add(new VendorContract(vendorId, "test", "test"));
        service.updateVendorContentList("vendor_1", vendorContractList);
        vendorContractList = contractMapper.getVendorContractList(vendorId);
        Assert.assertEquals(vendorContractList.size(), 2);
    }

    @Test
    public void addContract() throws Exception {
        VendorContract contract = contractMapper.getVendorContract("test", "test");
        Assert.assertNull(contract);
        service.addContract(new VendorContract("test", "test", "test"));
        contract = contractMapper.getVendorContract("test", "test");
        Assert.assertNotNull(contract);
    }

}