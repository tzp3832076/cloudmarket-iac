/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.bce.internalsdk.core.BceInternalResponseException;
import com.baidu.bce.internalsdk.mkt.iac.model.ApplicationNoticeBody;
import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.mkt.framework.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/9 by sunfangyuan@baidu.com .
 */
@Slf4j
public class MktIacClientTest {
//    private MktIacClient mktIacClient = new MktIacClient("http://10.99.206.142:8105",
//                                                                "00d3ffe2a47d4ddb83c8144b84230aae",
//                                                                "15b3a284ee0245689776b1b1af63178c");

    private MktIacClient mktIacClient = new MktIacClient("http://10.99.206.142:8105",
                                                                "9c535320a79d4903aca96e6ea739e36c",
                                                                "c68dc782e86c44c699619bfd3752fd68");

    @Test
    public void getVendorInfo() {
        mktIacClient.getVendorInfoDetail();
    }

    @Test
    public void getVendorShopDraft() {
        mktIacClient.getShopDraftDetail();
    }

    @Test
    public void saveShopDraft() {
        ShopDraftSaveRequest request = new ShopDraftSaveRequest();
        request.setBaiduWalletAccount("test");
        List<OnlineSupport> onlineSupportList = new ArrayList<>();
        onlineSupportList.add(new OnlineSupport("test", "test"));
        request.setBaiduQiaos(onlineSupportList);
        request.setServiceEmail("test@baidu.com");
        mktIacClient.saveVendorShopDraft(request);
    }

    @Test
    public void submitVendorShopDraft() {
        ShopDraftSaveRequest request = new ShopDraftSaveRequest();
        request.setBaiduWalletAccount("test");
        List<OnlineSupport> onlineSupportList = new ArrayList<>();
        onlineSupportList.add(new OnlineSupport("test", "test"));
        request.setBaiduQiaos(onlineSupportList);
        request.setServiceEmail("test@baidu.com");
        request.setServiceAvailTime("周一到周二");
        request.setCompanyDescription("开心就好");
        request.setServicePhone("17710655555");
        mktIacClient.submitVendorShopDraft(request);
    }

    @Test
    public void getVendorOverview() {
        mktIacClient.getVendorOverview();
    }

    @Test
    public void cancelAudit() {
        mktIacClient.cancelShopDraftAudit();
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
        AuditNoticeRequest request = new AuditNoticeRequest();
        request.setContent(JsonUtils.toJson(body));
        try {
            mktIacClient.auditResultNotice("APPLICATION", "22222", "PASS", request);
            Assert.fail("no exception");
        } catch (BceInternalResponseException e) {
            Assert.assertEquals(e.getCode(), "NoPermission");
        }
    }

}
