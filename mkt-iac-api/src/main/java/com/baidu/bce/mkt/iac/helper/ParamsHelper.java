/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import java.util.Map;

import com.baidu.bce.internalsdk.mkt.iac.model.ParamMapResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;

/**
 * Created on 2017/3/3 by sunfangyuan@baidu.com .
 */
@ControllerHelper
public class ParamsHelper {
    public ParamMapResponse toParamMapResponse(Map<String, String> paramMap) {
        ParamMapResponse response = new ParamMapResponse();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            response.put(entry.getKey(), entry.getValue());
        }
        return response;
    }
}
