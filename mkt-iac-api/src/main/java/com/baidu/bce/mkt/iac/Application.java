// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac;

import com.baidu.bce.mkt.framework.bootstrap.Bootstrap;
import com.baidu.bce.mkt.framework.bootstrap.ServiceApp;
import com.baidu.bce.mkt.framework.iac.EnableMktAuthorization;

/**
 * iac application bootstrap
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@EnableMktAuthorization
@ServiceApp
public class Application {
    public static void main(String[] args) {
        Bootstrap.run(Application.class, args);
    }
}
