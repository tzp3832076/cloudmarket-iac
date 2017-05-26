// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.database;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baidu.bae.commons.db.CustomSqlSessionFactoryBean;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;

/**
 * mapper configuration
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Configuration
public class MapperConfiguration {
    @Configuration
    @MapperScan(basePackageClasses = AccountMapper.class)
    public static class MapperScanConfiguration {

    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
