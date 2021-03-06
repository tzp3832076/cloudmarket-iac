/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import com.baidu.bce.mkt.iac.common.model.AiVendorListModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.mapper.AiVendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.service.AiVendorInfoService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;
import com.baidu.bce.mkt.iac.common.test.TestDataGenerator;
import com.baidu.bce.plat.webframework.exception.BceException;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */
@Slf4j
public class AiVendorInfoServiceTest extends BaseCommonServiceTest {

    @Autowired
    private AiVendorInfoService aiVendorInfoService;

    @Autowired
    private AiVendorInfoMapper aiVendorInfoMapper;

    @Test
    public void testNewVendor() {
        AiVendorInfo aiVendorInfo = TestDataGenerator.generateAiVendorInfo();
        aiVendorInfoService.newVendor(aiVendorInfo);
        AiVendorInfo aiVendorInfo1 = aiVendorInfoMapper.getByVendorId(aiVendorInfo.getVendorId());
        Assert.assertNotNull(aiVendorInfo1);

    }

    @Test
    public void testUpdateVendorInfo() {
        AiVendorInfo aiVendorInfo = TestDataGenerator.generateAiVendorInfo();
        aiVendorInfoService.newVendor(aiVendorInfo);
        AiVendorInfo aiVendorInfo1 = new AiVendorInfo();
        aiVendorInfo1.setWebsite("www.baidu.com");
        aiVendorInfo1.setUserType("UC");
        aiVendorInfo1.setCompany("百度网讯");
        aiVendorInfo1.setVendorId(aiVendorInfo.getVendorId());
        aiVendorInfoService.updateVendorInfo(aiVendorInfo1);
        AiVendorInfo aiVendorInfo2 = aiVendorInfoMapper.getByVendorId(aiVendorInfo.getVendorId());
        Assert.assertNotNull(aiVendorInfo2);
        log.info("{}", aiVendorInfo2);

        aiVendorInfo1.setVendorId("123123123123");
        try {
            aiVendorInfoService.updateVendorInfo(aiVendorInfo1);
            Assert.fail();
        } catch (BceException e) {
            Assert.assertEquals("服务商不存在", e.getMessage());
        }

    }

    @Test
    public void testAiVendorList() {
        AiVendorListModel listModel = aiVendorInfoService.getApplicantList("股份", null,
                null, 0, 10);
        Assert.assertEquals(1, listModel.getTotalCount());
        Assert.assertEquals("vendor_2", listModel.getAiVendorInfo().get(0).getVendorId());

        listModel = aiVendorInfoService.getApplicantList(null, "desc", "createTime",
                0, 10);
        log.info("listmodel: {}", listModel);
        Assert.assertEquals(2, listModel.getTotalCount());
    }

    @Test
    public void testVendorListByIds() {
        List<String> vendorIds = new ArrayList<>();
        AiVendorListModel vendorListByIds = aiVendorInfoService.getVendorListByIds(vendorIds);
        Assert.assertEquals(0, vendorListByIds.getTotalCount());

        vendorIds.add("vendor_2");
        vendorListByIds = aiVendorInfoService.getVendorListByIds(vendorIds);
        Assert.assertEquals(1, vendorListByIds.getTotalCount());
        Assert.assertEquals("vendor_2", vendorListByIds.getAiVendorInfo().get(0).getVendorId());
    }

}
