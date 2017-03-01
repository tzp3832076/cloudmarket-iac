// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.test.interceptor;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;
import com.baidu.bce.mkt.framework.bootstrap.ServiceApp;
import com.baidu.bce.mkt.framework.iac.EnableMktAuthorization;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.annotation.Subject;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.service.AuthorizationService;
import com.baidu.bce.mkt.framework.iac.web.AuthorizationWebConfiguration;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.framework.utils.JsonUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * web interceptor test
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@SpringBootTest(classes = {WebInterceptorTest.Config.class, WebInterceptorTest.TargetController.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@Slf4j
public class WebInterceptorTest {
    @MockBean(name = AuthorizationWebConfiguration.BEAN_NAME_AUTHORIZATION_SERVICE)
    private AuthorizationService authorizationService;

    @Autowired
    private WebApplicationContext applicationContext;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    private MockMvc mockMvc;

    @Test
    public void testCalculateAdd() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        MktToken mktToken = new MktToken();
        mktToken.setUserId(IdUtils.generateUUID());
        AuthorizedToken authorizedToken = new AuthorizedToken(new Token(), mktToken);
        when(authorizationService.checkAuth(anyString(), anyString(), anyList())).thenReturn(authorizedToken);
        String ret = mockMvc.perform(MockMvcRequestBuilders
                .request(HttpMethod.POST, "/v1/calculate/add/1/2"))
                .andReturn().getResponse().getContentAsString();
        Result result = JsonUtils.fromJson(ret, Result.class);
        Assert.assertEquals(3, result.getResult());
        Assert.assertTrue(outputCapture.toString().contains("user id in calculate add = "
                + mktToken.getUserId()));
    }

    @RestController
    public static class TargetController {
        @RequestMapping(value = "/v1/calculate/add/{a}/{b}", method = RequestMethod.POST)
        @CheckAuth(resource = "calculate", operation = "add", instanceParameterName = "a")
        public Result calculateAdd(@PathVariable int a, @PathVariable int b,
                                   @Subject AuthorizedToken authorizedToken) {
            log.info("user id in calculate add = {}", authorizedToken.getUserId());
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
    @EnableMktAuthorization
    public static class Config {

    }
}
