/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorPhoneAndEmailResponse;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Slf4j
public class VendorExtraControllerTest extends ApiMockMvcTest {

    @Before
    public void initOpId() {
        MktToken mktToken = new MktToken();
        mktToken.setRole("OP");
        mktToken.setUserId(IdUtils.generateShortUUID());
        AuthorizedToken authorizedToken = new ReceivedAuthorizedToken(new Token(), mktToken);
        when(checkAuthService.checkAuth(any(), any(), anyString(), anyString(), anyList())).thenReturn(authorizedToken);
    }

    @Test
    public void contractAndDepositSubmit() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("updateVendorContentList success.");
                return null;
            }
        }).when(contractAndDepositService).updateVendorContentList(anyString(), any());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("updateVendorDeposit success.");
                return null;
            }
        }).when(contractAndDepositService).updateVendorDeposit(anyString(), any());
        ContractAndDepositSubmitRequest request = new ContractAndDepositSubmitRequest();
        List<ContractAndDepositSubmitRequest.Contract> contracts = new ArrayList<>();
        contracts.add(new ContractAndDepositSubmitRequest.Contract("mum1",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00"), "test", true));
        request.setContractList(contracts);
        request.setDeposit(new BigDecimal(10000));
        mktIacClient.submitContractsAndDeposit("test", request);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getVendorContracts() {
        List<VendorContract> contractList = new ArrayList<>();
        contractList.add(new VendorContract("test", "test", "test",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00")));
        when(contractAndDepositService.getVendorContractList(anyString())).thenReturn(contractList);
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setCompany("test");
        when(vendorService.getVendorInfoByVendorId(anyString())).thenReturn(vendorInfo);
        VendorContractResponse response = mktIacClient.getVendorContract("test");
        log.info("getVendorContracts {}", response);

    }

    @Test
    public void getVendorContractsAndDeposit() {
        List<VendorContract> contractList = new ArrayList<>();
        contractList.add(new VendorContract("test", "test", "test",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00")));
        when(contractAndDepositService.getVendorContractList(anyString())).thenReturn(contractList);
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setCompany("test");
        when(vendorService.getVendorInfoByVendorId(anyString())).thenReturn(vendorInfo);
        when(contractAndDepositService.getVendorDeposit(anyString())).thenReturn(null);
        ContractAndDepositResponse response = mktIacClient.getContractsAndDeposit("test");
        log.info("getVendorContractsAndDeposit {}", response);

    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void testGetPhoneAndEmail() {
        String vendorId = "test";
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setBceUserId(IdUtils.generateShortUUID());
        when(vendorService.getValidVendorInfo(vendorId)).thenReturn(vendorInfo);
        VendorShop vendorShop = new VendorShop();
        vendorShop.setEmail("123@baidu.com");
        when(vendorService.getVendorShopByVendorId(vendorId)).thenReturn(vendorShop);
        VendorPhoneAndEmailResponse response = mktIacClient.getPhoneAndEmail(vendorId);
        log.info("response = {}", response);
        Assert.assertEquals("13012345678", response.getPhone());
        Assert.assertEquals("123@baidu.com", response.getEmail());
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void testGetContractVendorIds() {
        when(contractAndDepositService.getContractedVendorIdList(anyList()))
                .thenReturn(Arrays.asList("vendorId_1", "vendor_2"));
        ContractVendorIdListResponse response = mktIacClient.getContractVendorIds(Arrays.asList("vendorId_1"));
        Assert.assertNotNull(response);
        log.info("response ={}", response);
        Assert.assertEquals(response.getVendorIds().size(), 2);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void testAddVendorContract() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("addContract success.");
                return null;
            }
        }).when(contractAndDepositService).addContract(anyString(), any(), any(), any());
        ContractRequest request = new ContractRequest("vendor_1", "test",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00"));
        try {
            mktIacClient.addVendorContract(request);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), MktIacExceptions.inValidContractTime().getMessage());
        }
        request.setContractEndTime(Timestamp.valueOf("2017-12-20 00:00:00"));
    }

}