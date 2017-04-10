// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.mkt.framework.iac.HeaderConstants;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthFailedException;
import com.baidu.bce.plat.webframework.iam.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * utils for create models
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class ModelUtils {
    public static BceAuthContextWrapper createCurrentBceAuthContextWrapper() {
        Token token = UserService.getSubjectToken();
        return new BceAuthContextWrapper(token);
    }

    public static HeadUser createHeaderUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || !(requestAttributes instanceof ServletRequestAttributes)) {
            log.error("get ServletRequestAttributes failed");
            throw new CheckAuthFailedException();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        return createHeaderUser(httpServletRequest);
    }

    public static HeadUser createHeaderUser(HttpServletRequest httpServletRequest) {
        String userId = httpServletRequest.getHeader(HeaderConstants.CURRENT_USER_ID);
        String targetVendor = httpServletRequest.getHeader(HeaderConstants.TARGET_VENDOR);
        HeadUser headUser = new HeadUser();
        headUser.setUserId(userId);
        if (StringUtils.isNotBlank(targetVendor)) {
            String[] vendors = targetVendor.split(",");
            headUser.setTargetVendorList(Arrays.asList(vendors));
        }
        return headUser;
    }
}
