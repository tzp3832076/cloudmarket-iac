package com.baidu.bce.mkt.iac.test.controller;

import java.sql.Timestamp;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAiVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-29.
 */
@Slf4j
public class AiVendorExtraControllerTest extends ApiMockMvcTest {

    @Before
    public void initOpId() {
        MktToken mktToken = new MktToken();
        mktToken.setRole("OP");
        mktToken.setUserId(IdUtils.generateShortUUID());
        AuthorizedToken authorizedToken = new ReceivedAuthorizedToken(new Token(), mktToken);
        when(checkAuthService.checkAuth(any(), any(), anyString(), anyString(), anyList())).thenReturn(authorizedToken);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void testGetContractVendorIds() {
        when(aiVendorExtraService.getContractedAiVendorIdList(anyList()))
                .thenReturn(Arrays.asList("vendorId_1", "vendor_2"));
        ContractAiVendorIdListResponse response = mktIacClient.getContractAiVendorIds(Arrays.asList("vendorId_1"));
        Assert.assertNotNull(response);
        log.info("response ={}", response);
        Assert.assertEquals(response.getVendorIds().size(), 2);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void testAddAiVendorContract() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("addContract success.");
                return null;
            }
        }).when(aiVendorExtraService).addContract(anyString(), anyString(), anyString(), any(), any());
        ContractRequest request = new ContractRequest("vendor_1", "test", "testCustomerNum",
                Timestamp.valueOf("2017-02-20 00:00:00"), Timestamp.valueOf("2017-02-20 00:00:00"));
        try {
            mktIacClient.addAiVendorContract(request);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), MktIacExceptions.inValidContractTime().getMessage());
        }
        request.setContractEndTime(Timestamp.valueOf("2017-12-20 00:00:00"));
    }
}
