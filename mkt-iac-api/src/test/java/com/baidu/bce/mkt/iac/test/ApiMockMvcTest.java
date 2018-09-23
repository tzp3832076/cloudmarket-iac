// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.test;

import org.junit.Before;
import org.junit.Rule;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.baidu.bce.internalsdk.mkt.iac.MktIacAuthorizationClient;
import com.baidu.bce.internalsdk.mkt.iac.MktIacClient;
import com.baidu.bce.mkt.framework.configuration.common.CrmConfiguration;
import com.baidu.bce.mkt.framework.crm.CrmService;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;
import com.baidu.bce.mkt.framework.test.iam.IamRule;
import com.baidu.bce.mkt.framework.test.mvc.BaseMockMvcTest;
import com.baidu.bce.mkt.framework.test.mvc.MockEndpoint;
import com.baidu.bce.mkt.iac.common.client.IacClientFactory;
import com.baidu.bce.mkt.iac.common.handler.CategoryHandler;
import com.baidu.bce.mkt.iac.common.service.AccountService;
import com.baidu.bce.mkt.iac.common.service.AiVendorInfoService;
import com.baidu.bce.mkt.iac.common.service.AuthorizationService;
import com.baidu.bce.mkt.iac.common.service.ContractAndDepositService;
import com.baidu.bce.mkt.iac.common.service.NoticeService;
import com.baidu.bce.mkt.iac.common.service.VendorPayeeService;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.plat.webframework.iam.service.IAMService;

/**
 * mock mvc test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public abstract class ApiMockMvcTest extends BaseMockMvcTest {
    @MockBean(name = "IAMService")
    private IAMService iamService;
    @MockBean(name = CrmConfiguration.BEAN_NAME_CRM_SERVICE)
    private CrmService crmService;
    @MockBean(name = "authorizationService")
    protected AuthorizationService authorizationService;
    @MockBean(name = "noticeService")
    protected NoticeService noticeService;
    @MockBean(name = "vendorService")
    protected VendorService vendorService;
    @MockBean(name = "contractAndDepositService")
    protected ContractAndDepositService contractAndDepositService;
    @MockBean(name = "accountService")
    protected AccountService accountService;
    @MockBean(name = CheckAuthService.BEAN_NAME)
    protected CheckAuthService checkAuthService;
    @MockBean(name = "categoryHandler")
    protected CategoryHandler categoryHandler;
    @MockBean(name = "vendorPayeeService")
    protected VendorPayeeService vendorPayeeService;
    @MockBean(name = "aiVendorInfoService")
    protected AiVendorInfoService  aiVendorInfoService;
    @MockBean(name = "iacClientFactory")
    protected IacClientFactory iacClientFactory;

    @Rule
    public IamRule iamRule = new IamRule();

    protected MktIacAuthorizationClient mktIacAuthorizationClient;
    protected MktIacClient mktIacClient;

    @Before
    public void setUp() {
        mktIacAuthorizationClient = new MktIacAuthorizationClient(MockEndpoint.DEFAULT_TEST_ENDPOINT);
        mktIacClient = new MktIacClient(MockEndpoint.DEFAULT_TEST_ENDPOINT, "test", "test");
        iamRule.initialize(iamService, crmService);
    }
}
