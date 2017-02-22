// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;

import com.baidu.bce.mkt.framework.test.service.BaseServiceTest;
import com.baidu.bce.mkt.iac.common.client.AuthClient;
import com.baidu.bce.mkt.iac.common.client.IacClientFactory;

/**
 * base common service test with mocked client factory
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@SpringBootTest(classes = {DBInitializer.class, BaseServiceTest.ServiceTestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class BaseCommonServiceTest extends BaseServiceTest {
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @MockBean(name = "iacClientFactory")
    protected IacClientFactory clientFactory;

    protected AuthClient authClient;

    @Before
    public void initClient() {
        authClient = mock(AuthClient.class);
        when(clientFactory.createAuthClient(anyString())).thenReturn(authClient);
    }
}
