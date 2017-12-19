/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
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
        }).when(auditClient).syncVendorContract(anyString(), anyString());
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
        }).when(auditClient).syncVendorContract(anyString(), anyString());
        String vendorId = "vendor_2";
        List<VendorContract> vendorContractList = contractMapper.getVendorContractListById(vendorId);
        Assert.assertEquals(vendorContractList.size(), 1);
        vendorContractList.add(new VendorContract(vendorId, "test", "test",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00")));
        service.updateVendorContentList("vendor_1", vendorContractList);
        vendorContractList = contractMapper.getVendorContractListById(vendorId);
        Assert.assertEquals(vendorContractList.size(), 2);
    }

    @Test
    public void addContract() throws Exception {
        VendorContract contract = contractMapper.getVendorContract("test", "test");
        Assert.assertNull(contract);
        service.addContract(new VendorContract("test", "test", "test",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00")));
        contract = contractMapper.getVendorContract("test", "test");
        Assert.assertNotNull(contract);
    }

    @Test
    public void testAddContract() throws Exception {
        service.addContract("vendor_1", "test", Timestamp.valueOf("2017-02-20 00:00:00"),
                Timestamp.valueOf("2017-02-20 00:00:00"));
        List<VendorContract> contracts = service.getVendorContractList("vendor_1");
        Assert.assertNotNull(contracts);
        Assert.assertEquals(contracts.size(), 3);
        service.addContract("vendor_1", "test2",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00"));
        contracts = service.getVendorContractList("vendor_1");
        Assert.assertNotNull(contracts);
        Assert.assertEquals(contracts.size(), 4);
        try {
            service.addContract("test", "test",
                    Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00"));
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), MktIacExceptions.inValidVendorStatus().getMessage());
        }
    }

    @Test
    public void getContractList() {
        List<VendorContract> vendorContractList = service.getVendorContractList("vendor_1");
        Assert.assertNotNull(vendorContractList);
        vendorContractList = service.getVendorContractList("vendor_XX");
        Assert.assertNotNull(vendorContractList);
        Assert.assertEquals(vendorContractList.size(), 0);
    }

    @Test
    public void testGetVendorContractLis() {
        List<VendorContract> vendorContractList = service.getVendorContractList(Arrays.asList("vendor_1", "vendor_2"));
        Assert.assertNotNull(vendorContractList);
        Assert.assertEquals(vendorContractList.size(), 3);
        vendorContractList = service.getVendorContractList(Arrays.asList("vendor_XX"));
        Assert.assertNotNull(vendorContractList);
        Assert.assertEquals(vendorContractList.size(), 0);
    }

    @Test
    public void testGetDistinctVendorContractList() {
        List<String> vendorContractList = service.getContractedVendorIdList(Arrays.asList("vendor_1", "vendor_2"));
        Assert.assertNotNull(vendorContractList);
        Assert.assertEquals(vendorContractList.size(), 2);
        vendorContractList = service.getContractedVendorIdList(Arrays.asList("vendor_XX"));
        Assert.assertNotNull(vendorContractList);
        Assert.assertEquals(vendorContractList.size(), 0);
        vendorContractList = service.getContractedVendorIdList(new ArrayList<>());
        Assert.assertNotNull(vendorContractList);
        Assert.assertEquals(vendorContractList.size(), 0);
    }

}