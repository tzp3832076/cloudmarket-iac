// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.instance;

import java.util.List;

/**
 * resolve object to List<String> type
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface Resolver {
    List<String> resolve(Object object);
}
