/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.AiVendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.test.TestDataGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenxiang05@baidu.com on 2018/8/15.
 */
@InitDatabase(tables = "mkt_ai_vendor_info")
@Slf4j
public class AiVendorInfoMapperTest extends BaseMapperTest {

    @Autowired
    private AiVendorInfoMapper aiVendorInfoMapper;

    @Test
    public void testSave() {
        AiVendorInfo aiVendorInfo = TestDataGenerator.generateAiVendorInfo();
        aiVendorInfoMapper.save(aiVendorInfo);

        AiVendorInfo aiVendorInfo1 = aiVendorInfoMapper.getByVendorId(aiVendorInfo.getVendorId());
        log.info("{}", aiVendorInfo1);

        aiVendorInfo1.setWebsite("www.baidu.com");

        int affects = aiVendorInfoMapper.update(aiVendorInfo);

        Assert.assertEquals(1, affects);

    }

}
