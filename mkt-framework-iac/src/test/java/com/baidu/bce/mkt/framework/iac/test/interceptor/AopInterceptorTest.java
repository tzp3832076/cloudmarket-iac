// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.test.interceptor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;
import com.baidu.bce.mkt.framework.bootstrap.ServiceApp;
import com.baidu.bce.mkt.framework.iac.EnableMktAuthorization;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.aop.AuthorizationAopConfiguration;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;
import com.baidu.bce.mkt.framework.iac.service.RemoteCheckAuthService;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.framework.utils.JsonUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * aop interceptor test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@SpringBootTest(classes = {AopInterceptorTest.Config.class, AopInterceptorTest.Target.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
public class AopInterceptorTest {
    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @MockBean(name = CheckAuthService.BEAN_NAME)
    private CheckAuthService checkAuthService;

    @Autowired
    private Target target;

    @Test
    public void testCalculateAdd() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        MktToken mktToken = new MktToken();
        mktToken.setUserId(IdUtils.generateUUID());
        AuthorizedToken authorizedToken = new ReceivedAuthorizedToken(new Token(), mktToken);
        when(checkAuthService.checkAuth(any(), any(), anyString(), anyString(), anyList()))
                .thenReturn(authorizedToken);
        String ret = mockMvc.perform(MockMvcRequestBuilders
                .request(HttpMethod.POST, "/v1/calculate/add")
                        .param("a", "1")
                        .param("b", "2"))
                .andReturn().getResponse().getContentAsString();
        WebInterceptorTest.Result result = JsonUtils.fromJson(ret, WebInterceptorTest.Result.class);
        Assert.assertEquals(3, result.getResult());
    }

    @RestController
    public static class Target {
        @RequestMapping(value = "/v1/calculate/add", method = RequestMethod.POST)
        @CheckAuth(resource = "calculate", operation = "add", instanceParameterName = "a")
        public Result calculateAdd(@RequestParam int a, @RequestParam int b) {
            return new Result(a + b);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private int result;
    }

    @ServiceApp
    @EnableMktAuthorization(useAop = true)
    public static class Config {

    }
}
