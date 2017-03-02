// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.mkt.framework.iac.annotation.Subject;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.TokenHolder;
import com.baidu.bce.plat.webframework.iam.service.UserService;

/**
 * subject method argument resolver
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class SubjectMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Subject.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        AuthorizedToken authorizedToken = TokenHolder.getAuthorizedToken();
        if (authorizedToken == null) {
            Token token = UserService.getSubjectToken();
            if (token != null) {
                authorizedToken = new ReceivedAuthorizedToken(token, null);
            }
        }
        for (SupportedParameter supportedParameter : SupportedParameter.values()) {
            if (supportedParameter.supportsParameter(methodParameter)) {
                Object value = supportedParameter.resolveArgument(methodParameter, authorizedToken);
                if (value != null) {
                    return value;
                }
            }
        }
        throw new MissingServletRequestParameterException(methodParameter.getParameterName(),
                methodParameter.getParameterType().getName());
    }

    private enum SupportedParameter {
        SUBJECT_AUTHORIZED_TOKEN {
            @Override
            protected boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType() == AuthorizedToken.class;
            }

            @Override
            protected Object resolveArgument(MethodParameter parameter, AuthorizedToken authorizedToken) {
                return authorizedToken;
            }
        },
        SUBJECT_IAM_TOKEN {
            @Override
            protected boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType() == Token.class;
            }

            @Override
            protected Object resolveArgument(MethodParameter parameter, AuthorizedToken authorizedToken) {
                if (authorizedToken == null) {
                    return null;
                }
                if (authorizedToken instanceof  ReceivedAuthorizedToken) {
                    return ((ReceivedAuthorizedToken) authorizedToken).getToken();
                } else {
                    return null;
                }
            }
        },
        SUBJECT_USER_ID {
            @Override
            protected boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType() == String.class;
            }

            @Override
            protected Object resolveArgument(MethodParameter parameter, AuthorizedToken authorizedToken) {
                return authorizedToken == null ? null : authorizedToken.getUserId();
            }
        };

        protected abstract boolean supportsParameter(MethodParameter parameter);

        protected abstract Object resolveArgument(MethodParameter parameter, AuthorizedToken authorizedToken);
    }
}
