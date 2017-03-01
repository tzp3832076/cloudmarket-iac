// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.TokenHolder;
import com.baidu.bce.mkt.framework.iac.service.AuthorizationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * check auth web interceptor
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
@Slf4j
public class CheckAuthWebInterceptor extends HandlerInterceptorAdapter {
    private Map<Method, Context> methodCache;
    private AuthorizationService authorizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Context context = methodCache.get(method);
            if (context != null) {
                log.debug("[CHECK AUTH WEB]begin to check auth");
                AuthorizedToken authorizedToken = authorizationService.checkAuth(context.getResource(),
                        context.getOperation(), context.getInstanceExtractor().extract(request));
                TokenHolder.setAuthorizedToken(authorizedToken);
            }
            return true;
        } else {
            throw new IllegalArgumentException("handler not support");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        TokenHolder.removeAuthorizedToken();
    }
}
