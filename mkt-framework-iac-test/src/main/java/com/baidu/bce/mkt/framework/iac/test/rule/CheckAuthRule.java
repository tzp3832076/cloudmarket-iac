// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.test.rule;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.BceAuthContextWrapper;
import com.baidu.bce.mkt.framework.iac.model.HeadUser;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;

/**
 * check auth rule
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class CheckAuthRule implements TestRule {
    private CurrentVendor currentVendor;

    @Override
    public Statement apply(Statement statement, Description description) {
        this.currentVendor = null;
        CurrentVendor currentVendor = description.getAnnotation(CurrentVendor.class);
        if (currentVendor == null) {
            currentVendor = description.getTestClass().getAnnotation(CurrentVendor.class);
        }
        this.currentVendor = currentVendor;
        return statement;
    }

    public void initialize(CheckAuthService checkAuthService) {
        when(checkAuthService.checkAuth(any(), any(), anyString(), anyString(), any())).thenAnswer(
                new Answer<AuthorizedToken>() {
                    @Override
                    public AuthorizedToken answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Object[] args = invocationOnMock.getArguments();
                        BceAuthContextWrapper bceAuthContextWrapper = (BceAuthContextWrapper) args[0];
                        HeadUser headUser = (HeadUser) args[1];
                        return bceAuthContextWrapper == null || bceAuthContextWrapper.getToken() == null ? null :
                                new MockedAuthorizedToken(bceAuthContextWrapper, headUser, currentVendor);
                    }
                });
    }
}
