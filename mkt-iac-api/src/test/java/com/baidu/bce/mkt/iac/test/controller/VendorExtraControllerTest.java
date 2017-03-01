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

import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndMarginSubmitRequest;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Slf4j
public class VendorExtraControllerTest extends ApiMockMvcTest {
    @Test
    public void contractAndMarginSubmit() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("updateVendorContentList success.");
                return null;
            }
        }).when(contractAndMarginService).updateVendorContentList(anyString(), any());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("updateVendorMargin success.");
                return null;
            }
        }).when(contractAndMarginService).updateVendorMargin(anyString(), any());
        ContractAndMarginSubmitRequest request = new ContractAndMarginSubmitRequest();
        List<ContractAndMarginSubmitRequest.Contract> contracts = new ArrayList<>();
        contracts.add(new ContractAndMarginSubmitRequest.Contract("mum1", "test", true));
        request.setContractList(contracts);
        request.setMargin(new BigDecimal(10000));
        mktIacClient.submitContractsAndMargin("test", request);
    }

}