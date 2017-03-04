/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Slf4j
public class VendorExtraControllerTest extends ApiMockMvcTest {
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
        contracts.add(new ContractAndDepositSubmitRequest.Contract("mum1", "test", true));
        request.setContractList(contracts);
        request.setDeposit(new BigDecimal(10000));
        mktIacClient.submitContractsAndDeposit(request);
    }

}