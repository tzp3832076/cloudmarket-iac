/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListFilter;
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
                                                      "test", "website", 1000, 100, "address",
                                                      "tel", "test@baidu.com", "test", "test-test",
                                                      "hotline", "othermarket", "contact_info");
        int res = vendorInfoMapper.add(vendorInfo);
        VendorInfo vendorInfo1 = vendorInfoMapper.getVendorInfoByVendorId("test");
        Assert.assertEquals(vendorInfo.getCompany(), vendorInfo1.getCompany());
    }

    @Test
    public void add2() throws Exception {
        VendorInfo vendorInfo = new VendorInfo("test", "test", VendorStatus.FROZEN,
                                                      "test", "website", 1000, 100, "address",
                                                      "tel", "test@baidu.com", "test", "test-test",
                                                      "hotline", "othermarket", "contact_info", "BIG_DATA");
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
    public void getVendorInfoByCompanyName() throws Exception {
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId("vendor_1");
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorInfoByCompanyName(vendorInfo
                                                                                           .getCompany());
        Assert.assertNotNull(vendorInfos);
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
        VendorListFilter filter = new VendorListFilter(null, null, VendorStatus.PROCESSING);
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(2, vendorInfos.size());
        filter = new VendorListFilter(null, null, VendorStatus.FROZEN);
        vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(0, vendorInfos.size());
        filter = new VendorListFilter(null, "_2", null);
        vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(1, vendorInfos.size());
        filter = new VendorListFilter("bce_user_1", null, null);
        vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(1, vendorInfos.size());
    }

    @Test
    public void getVendorList2() {
        VendorListFilter filter = new VendorListFilter(null, null, "BIG_DATA", VendorStatus.PROCESSING);
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(1, vendorInfos.size());
        filter = new VendorListFilter(null, null, "BIG_DATA", VendorStatus.FROZEN);
        vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(0, vendorInfos.size());
        filter = new VendorListFilter(null, "_2", "BIG_DATA", null);
        vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(0, vendorInfos.size());
        filter = new VendorListFilter("bce_user_1", null, "API", null);
        vendorInfos = vendorInfoMapper.getVendorList(filter, 0, 10);
        Assert.assertEquals(0, vendorInfos.size());
    }

    @Test
    public void getVendorCount() {
        VendorListFilter filter = new VendorListFilter(null, null, VendorStatus.FROZEN);
        int res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(0, res);
        filter = new VendorListFilter(null, null, VendorStatus.PROCESSING);
        res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(2, res);
        filter = new VendorListFilter(null, "_2", null);
        res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(1, res);
        filter = new VendorListFilter("bce_user_1", null, null);
        res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(1, res);
    }

    @Test
    public void getVendorCount2() {
        VendorListFilter filter = new VendorListFilter(null, null, "BIG_DATA", VendorStatus.FROZEN);
        int res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(0, res);
        filter = new VendorListFilter(null, null, "BIG_DATA", VendorStatus.PROCESSING);
        res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(1, res);
        filter = new VendorListFilter(null, "_2", "API", null);
        res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(1, res);
        filter = new VendorListFilter("bce_user_1", null, "API", null);
        res = vendorInfoMapper.getVendorCount(filter);
        Assert.assertEquals(0, res);
    }

    @Test
    public void updateAgreementStatus() {
        String vendorId = "vendor_1";
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertNotNull(vendorInfo);
        Assert.assertEquals(vendorInfo.getAgreementStatus(), ProcessStatus.TODO);
        int res = vendorInfoMapper.updateAgreementStatus(vendorId, ProcessStatus.DONE);
        vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        Assert.assertNotNull(vendorInfo);
        Assert.assertEquals(vendorInfo.getAgreementStatus(), ProcessStatus.DONE);
    }

    @Test
    public void getVendorListByIds() {
        List<String> vendorIds = new ArrayList<>();
        vendorIds.add("vendor_1");
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorListByIds(vendorIds);
        Assert.assertNotNull(vendorInfos);
    }
}