// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
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
            VendorId vendorId = methodParameter.getParameterAnnotation(VendorId.class);
            Class clazz = methodParameter.getParameterType();
            if (clazz.equals(String.class)) {
                return mergeOneVendorId(authorizedToken, vendorId.required());
            } else {
                List<String> vendors = mergeVendorIds(authorizedToken, vendorId.required());
                if (clazz.equals(List.class)) {
                    return vendors;
                } else if (clazz.isArray()) {
                    return CollectionUtils.isEmpty(vendors) ? null : vendors.toArray();
                }
            }
        }
        throw new MissingServletRequestParameterException(methodParameter.getParameterName(),
                methodParameter.getParameterType().getName());
    }

    private String mergeOneVendorId(AuthorizedToken authorizedToken, boolean required) {
        String vendorId = authorizedToken.getVendorId();
        if (StringUtils.isBlank(vendorId)) {
            List<String> vendors = authorizedToken.getTargetVendorList();
            if (CollectionUtils.isEmpty(vendors)) {
                if (required) {
                    throw new IllegalArgumentException("no target vendor list or vendor id found");
                } else {
                    vendorId = null;
                }
            } else {
                if (vendors.size() > 1) {
                    throw new IllegalArgumentException("target vendor list more than 1");
                }
                vendorId = vendors.get(0);
            }
        }
        return vendorId;
    }

    private List<String> mergeVendorIds(AuthorizedToken authorizedToken, boolean required) {
        List<String> vendors = authorizedToken.getTargetVendorList();
        if (CollectionUtils.isEmpty(vendors)) {
            String vendorId = authorizedToken.getVendorId();
            if (StringUtils.isBlank(vendorId)) {
                if (required) {
                    throw new IllegalArgumentException("no target vendor list or vendor id found");
                }
            } else {
                vendors = Arrays.asList(vendorId);
            }
        }
        return vendors;
    }
}
