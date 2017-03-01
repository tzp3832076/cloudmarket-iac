// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.instance;

import java.util.ArrayList;
import java.util.List;

/**
 * resolve list type
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class ListResolver implements Resolver {
    @Override
    public List<String> resolve(Object value) {
        List<String> instances = new ArrayList<>();
        for (Object o : (List) value) {
            instances.add(String.valueOf(o));
        }
        return instances;
    }
}
