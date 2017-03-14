/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@InitDatabase(tables = "mkt_vendor_info")
public class VendorInfoMapperTest extends BaseMapperTest {
    @Autowired
    private VendorInfoMapper vendorInfoMapper;

    @Test
    public void add() throws Exception {
        VendorInfo vendorInfo = new VendorInfo("test", "test", VendorStatus.FROZEN,
                                                      "test", "website", 1000, "address",
                                                      "tel", "test-test", "hotline", "othermarket",
                                                      "contact_info");
        int res = vendorInfoMapper.add(vendorInfo);
        VendorInfo vendorInfo1 = vendorInfoMapper.getVendorInfoByVendorId("test");
        Assert.assertEquals(vendorInfo.getCompany(), vendorInfo1.getCompany());
    }

    @Test
    public void getVendorInfoByVendorId() throws Exception {
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId("vendor_1");
        Assert.assertNotNull(vendorInfo);
    }

    @Test
    public void updateVendorStatus() {
        String vendorId = "vendor_1";
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getStatus(), VendorStatus.PROCESSING);
        vendorInfoMapper.updateVendorStatus(vendorId, VendorStatus.HOSTED);
        vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getStatus(), VendorStatus.HOSTED);
    }

    @Test
    public void getVendorInfoByBceUserId() {
        String bceUserId = "bce_user_1";
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByBceUserId(bceUserId);
        Assert.assertNotNull(vendorInfo);
    }

    @Test
    public void updateWalletId() {
        String vendorId = "vendor_1";
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertNotNull(vendorInfo);
        Assert.assertEquals(vendorInfo.getWalletId(), "wallet_id");
        vendorInfoMapper.updateWalletId(vendorId, "test");
        vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertNotNull(vendorInfo);
        Assert.assertEquals(vendorInfo.getWalletId(), "test");
    }

    @Test
    public void getVendorCountByStatus() {
        int res = vendorInfoMapper.getVendorCountByStatus(VendorStatus.PROCESSING);
        Assert.assertEquals(res, 2);
        res = vendorInfoMapper.getVendorCountByStatus(VendorStatus.HOSTED);
        Assert.assertEquals(res, 0);

    }

    @Test
    public void getVendorList() {
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorList(VendorStatus.PROCESSING,
                null, null, 0, 10);
        Assert.assertTrue(vendorInfos.size() > 0);
        vendorInfos = vendorInfoMapper.getVendorList(VendorStatus.FROZEN,
                null, null, 0, 10);
        Assert.assertTrue(vendorInfos.size() == 0);
    }

    @Test
    public void getVendorCount() {
        int res = vendorInfoMapper.getVendorCount(VendorStatus.FROZEN, null, null);
        Assert.assertEquals(0, res);
        res = vendorInfoMapper.getVendorCount(VendorStatus.PROCESSING, null, null);
        Assert.assertTrue(res > 0);
    }

}