// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test;

import javax.sql.DataSource;

import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.framework.test.database.BaseDBTest;
import com.baidu.bce.mkt.framework.test.database.initialize.BaseDBInitializer;
import com.baidu.bce.plat.webframework.system.UTCTimeZoneConfiguration;

/**
 * db initializer for resource system table
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class DBInitializer extends BaseDBInitializer {
    @Autowired
    public DBInitializer(DataSource dataSource, IDataTypeFactory dataTypeFactory,
                         UTCTimeZoneConfiguration utcTimeZoneConfiguration) {
        super(dataSource, dataTypeFactory, utcTimeZoneConfiguration);
    }

    @Override
    protected String getDataSetPath() {
        return BaseDBTest.DATA_SET_DEFAULT_PATH;
    }

    @Override
    protected String[] getTableNames() {
        return new String[] {"mkt_resource_system"};
    }
}
