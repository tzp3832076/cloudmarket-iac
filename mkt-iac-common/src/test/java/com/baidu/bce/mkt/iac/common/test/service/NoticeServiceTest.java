/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.mkt.audit.internal.sdk.model.response.SubmitAuditResponse;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopMapper;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.common.service.NoticeService;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;
import com.baidu.bce.plat.webframework.exception.BceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
public class NoticeServiceTest extends BaseCommonServiceTest {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorInfoMapper vendorInfoMapper;
    @Autowired
    private VendorShopMapper vendorShopMapper;
    @Autowired
    private VendorShopDraftMapper vendorShopDraftMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void auditNotice() throws Exception {
        VendorInfo vendorInfo = new VendorInfo("test", "test", VendorStatus.FROZEN,
                                                      "test", "website", 1000, 100, "address",
                                                      "tel", "test@baidu.com", "test", "test-test",
                                                      "hotline", "othermarket", "contact_info");
        noticeService.auditNoticeApplication("PASS", vendorInfo);
        VendorInfo test = vendorInfoMapper.getVendorInfoByVendorId(vendorInfo.getVendorId());
        Assert.assertNotNull(test);
        Account account = accountMapper.getByAccountId(test.getBceUserId());
        Assert.assertNotNull(account);

        try {
            vendorInfo.setBceUserId(IdUtils.generateUUID());
            noticeService.auditNoticeApplication("PASS", vendorInfo);
            Assert.fail("no exception");
        } catch (BceException e) {
            Assert.assertEquals(e.getCode(), "CompanyNameRepeat");
        }
    }

    @Test
    public void auditNoticeVendorShop() {
        String vendorId = "vendor_2";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Assert.assertEquals(vendorId, invocationOnMock.getArguments()[0].toString());
                return null;
            }
        }).when(mktInternalClient).syncVendorInfo(anyString());
        VendorShopAuditContent content = new VendorShopAuditContent();
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setCompanyName("test");
        shopDraft.setBceAccount("test");
        shopDraft.setBaiduWalletAccount("test");
        List<OnlineSupport> onlineSupportList = new ArrayList<>();
        onlineSupportList.add(new OnlineSupport("test", "test"));
        shopDraft.setBaiduQiaos(onlineSupportList);
        shopDraft.setCompanyDescription("test");
        shopDraft.setServiceEmail("test");
        shopDraft.setServiceAvailTime("test");
        shopDraft.setServicePhone("test");
        content.setData(shopDraft);
        when(auditClient.auditSubmit(any())).thenReturn(new SubmitAuditResponse("test"));
        vendorService.submitShopDraft(vendorId, content);
        VendorShopDraft vendorShopDraft = vendorShopDraftMapper.getShopDraftByVendorId(vendorId);
        Assert.assertNotNull(vendorShopDraft);
        Assert.assertEquals(vendorShopDraft.getStatus(), InfoStatus.AUDIT);
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getWalletId(), "wallet_id");


        noticeService.auditNoticeVendorShop(InfoStatus.PASS.name(), vendorId);
        vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getWalletId(), "test");
        vendorShopDraft = vendorShopDraftMapper.getShopDraftByVendorId(vendorId);
        Assert.assertNotNull(vendorShopDraft);
        Assert.assertEquals(vendorShopDraft.getStatus(), InfoStatus.PASS);
        VendorShop vendorShop = vendorShopMapper.getVendorShopByVendorId(vendorId);
        Assert.assertNotNull(vendorShop);
        log.info("auditNoticeVendorShop {}", vendorShop);
    }

}