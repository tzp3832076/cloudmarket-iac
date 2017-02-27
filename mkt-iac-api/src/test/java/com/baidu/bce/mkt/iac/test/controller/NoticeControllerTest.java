/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
public class NoticeControllerTest extends ApiMockMvcTest {

    @Test
    public void auditNotice() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("auditNotice success.");
                return null;
            }
        }).when(noticeService).auditNotice(anyString(), anyString(), anyString(), anyString());
        mktIacClient.noticeAudit("APPLICATION", "id", "PASS", null);
    }

}