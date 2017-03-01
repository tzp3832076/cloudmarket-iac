// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.instance;

import java.util.Arrays;
import java.util.List;

/**
 * resolve string type argument
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class StringResolver implements Resolver {

    @Override
    public List<String> resolve(Object value) {
        return Arrays.asList(String.valueOf(value));
    }
}
