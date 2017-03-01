// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baidu.bce.mkt.framework.iac.annotation.VendorId;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.TokenHolder;

/**
 * resolver support vendor id
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class VendorIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(VendorId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        AuthorizedToken authorizedToken = TokenHolder.getAuthorizedToken();
        if (authorizedToken != null) {
            String vendorId = authorizedToken.getVendorId();
            if (StringUtils.isNotEmpty(vendorId)) {
                return vendorId;
            }
        }
        throw new MissingServletRequestParameterException(methodParameter.getParameterName(),
                methodParameter.getParameterType().getName());
    }
}
