// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.test;

import org.junit.Before;
import org.junit.Rule;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.baidu.bce.internalsdk.mkt.iac.MktIacAuthorizationClient;
import com.baidu.bce.internalsdk.mkt.iac.MktIacClient;
import com.baidu.bce.mkt.framework.test.iam.IamRule;
import com.baidu.bce.mkt.framework.test.mvc.BaseMockMvcTest;
import com.baidu.bce.mkt.framework.test.mvc.MockEndpoint;
import com.baidu.bce.mkt.iac.common.service.AuthorizationService;
import com.baidu.bce.mkt.iac.common.service.ContractAndDepositService;
import com.baidu.bce.mkt.iac.common.service.NoticeService;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.plat.webframework.iam.service.IAMService;

/**
 * mock mvc test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public abstract class ApiMockMvcTest extends BaseMockMvcTest {
    @MockBean(name = "IAMService")
    private IAMService iamService;
    @MockBean(name = "authorizationService")
    protected AuthorizationService authorizationService;
    @MockBean(name = "noticeService")
    protected NoticeService noticeService;
    @MockBean(name = "vendorService")
    protected VendorService vendorService;
    @MockBean(name = "contractAndDepositService")
    protected ContractAndDepositService contractAndDepositService;

    @Rule
    public IamRule iamRule = new IamRule();

    protected MktIacAuthorizationClient mktIacAuthorizationClient;
    protected MktIacClient mktIacClient;

    @Before
    public void setUp() {
        mktIacAuthorizationClient = new MktIacAuthorizationClient(MockEndpoint.DEFAULT_TEST_ENDPOINT);
        mktIacClient = new MktIacClient(MockEndpoint.DEFAULT_TEST_ENDPOINT, "test", "test");
        iamRule.initialize(iamService);
    }
}
