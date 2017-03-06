/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.baidu.bce.mkt.framework.mvc.ControllerHelper;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on 2017/3/3 by sunfangyuan@baidu.com .
 */
@ControllerHelper
@ConfigurationProperties(prefix = "param.title", locations = "classpath:params.properties")
public class ParamProperties {
    @Getter
    @Setter
    private Map<String, String> map;
}
