/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiVendorInfoResponse extends AiVendorInfoBase {

}
