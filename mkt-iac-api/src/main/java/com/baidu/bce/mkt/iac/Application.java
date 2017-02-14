// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac;

import com.baidu.bce.mkt.framework.bootstrap.Bootstrap;
import com.baidu.bce.mkt.framework.bootstrap.ServiceApp;

/**
 * iac application bootstrap
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@ServiceApp
public class Application {
    public static void main(String[] args) {
        Bootstrap.run(Application.class, args);
    }
}
