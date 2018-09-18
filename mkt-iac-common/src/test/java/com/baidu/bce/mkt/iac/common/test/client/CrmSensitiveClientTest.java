/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.client;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.baidu.bce.mkt.framework.iac.client.model.response.SensitiveListResponse;
import com.baidu.bce.mkt.iac.common.client.CrmSensitiveClient;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by wangbin33@baidu.com on 2018/9/18.
 */
@Slf4j
public class CrmSensitiveClientTest {

    private CrmSensitiveClient crmSensitiveClient;

    // 沙盒
    @Before
    public void init() {
        crmSensitiveClient = new CrmSensitiveClient("http://nmg02-bce-test11.nmg02.baidu.com:8531/v1",
                                                           "34bc44d2cafc4e409863eeba35e6791b",
                                                           "72091ec9e8b046c98c79fc9e31eb1431");
    }

    @Test
    public void listSensitiveByAccountIdsTest() {
        List<String> accountIds = Arrays.asList("f168739fc9c5473ab798a39c3db446b6");
        SensitiveListResponse response = crmSensitiveClient.listSensitiveByAccountIds(accountIds);
        log.info("{}", response);
    }
}
