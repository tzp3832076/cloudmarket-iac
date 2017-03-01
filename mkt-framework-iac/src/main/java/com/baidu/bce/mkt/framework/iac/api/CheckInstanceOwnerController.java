// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.mkt.framework.iac.client.model.CheckInstanceOwnerRequest;
import com.baidu.bce.mkt.framework.iac.client.model.CheckInstanceOwnerResponse;

/**
 * controller for owner check
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@RestController
@RequestMapping(value = "/mkt/auth")
public class CheckInstanceOwnerController {
    @Autowired(required = false)
    private CheckInstanceOwnerService checkInstanceOwnerService;

    @RequestMapping(method = RequestMethod.POST, params = "checkOwner")
    public CheckInstanceOwnerResponse checkInstanceOwner(@RequestBody CheckInstanceOwnerRequest request) {
        if (checkInstanceOwnerService == null) {
            return new CheckInstanceOwnerResponse(false);
        } else {
            boolean ret = checkInstanceOwnerService.isOwner(request.getUserInfo(),
                    request.getResource(), request.getInstances());
            return new CheckInstanceOwnerResponse(ret);
        }
    }
}
