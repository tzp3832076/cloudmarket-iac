/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.baidu.bce.internalsdk.mkt.iac.model.ApplicationNoticeBody;
import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.framework.utils.JsonUtils;

/**
 * Created on 2017/3/9 by sunfangyuan@baidu.com .
 */
public class MktIacClientOspTest {
    private MktIacClient mktIacClient = new MktIacClient("http://10.99.206.142:8105",
                                                                "34bc44d2cafc4e409863eeba35e6791b",
                                                                "72091ec9e8b046c98c79fc9e31eb1431");

    @Before
    public void init() {
        mktIacClient.setHeaderUserId("UUAP:wujinlin");
    }

    @Test
    public void auditResultApplication() {
        ApplicationNoticeBody body = new ApplicationNoticeBody();
        body.setEmail("test@baidu.com");
        body.setCompany("test");
        body.setWebsite("http://baidu.com");
        body.setCapital(100);
        body.setSale("11");
        body.setHeadcount("100");
        body.setAddress("test");
        body.setTelephone("17710655555");

        body.setContactInfo(" {\"contactList\":[{\"type\":\"Technical\",\"name\":\"张三\","
                                    + "\"phone\":\"17710655555\"},{\"type\":\"Business\","
                                    + "\"name\":\"李四\",\"phone\":\"18010655555\"},"
                                    + "{\"type\":\"Emergency\",\"name\":\"王五\","
                                    + "\"phone\":\"18910655555\"}]}");
        body.setAuditId("audit_1");
        body.setBceUserId("1111");
        body.setHotline("1111111");
        body.setMarket("");
        body.setServiceCategory("101-101001");
        body.setServiceIllustration("test");
        body.setVendorId("vendor_1");
        AuditNoticeRequest request = new AuditNoticeRequest();
        request.setContent(JsonUtils.toJson(body));
        mktIacClient.auditResultNotice("APPLICATION", "22222", "PASS", request);
    }

    @Test
    public void getVendorBaseContactByBceId() {
        mktIacClient.getVendorBaseContactByBceId("b1f91fbe6fe54d2eaf70ef0025f1c3c2");
    }

    @Test
    public void submitContractsAndDeposit() {
        ContractAndDepositSubmitRequest request = new ContractAndDepositSubmitRequest();
        List<ContractAndDepositSubmitRequest.Contract> contracts = new ArrayList<>();
        contracts.add(new ContractAndDepositSubmitRequest.Contract("number1", "test", false));
        request.setContractList(contracts);
        request.setDeposit(new BigDecimal(0));
        mktIacClient.submitContractsAndDeposit("ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae", request);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getVendorContract() {
        mktIacClient.getVendorContract("ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae");
    }
}
