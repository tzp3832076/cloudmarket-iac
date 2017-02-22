// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * properties center
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Component
@Data
public class ConfigProperties {
    @Value("${mkt.service.ak:}")
    private String mktServiceAk;
    @Value("${mkt.service.sk:}")
    private String mktServiceSk;
}
