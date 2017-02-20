// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.database;

import org.springframework.boot.test.context.SpringBootTest;

import com.baidu.bce.mkt.framework.test.database.BaseDBTest;
import com.baidu.bce.mkt.iac.common.database.MapperConfiguration;

/**
 * base mapper test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@SpringBootTest(classes = MapperConfiguration.class)
public abstract class BaseMapperTest extends BaseDBTest {
}
