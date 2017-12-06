/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorContractMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@InitDatabase(tables = "mkt_vendor_contract")
public class VendorContractMapperTest extends BaseMapperTest {
    @Autowired
    private VendorContractMapper vendorContractMapper;

    @Test
    public void add() throws Exception {
        VendorContract contract = new VendorContract("test", "testNum", "test");
        int res = vendorContractMapper.add(contract);
        Assert.assertEquals(1, res);
    }

    @Test
    public void getVendorContract() throws Exception {
        List<VendorContract> vendorContracts = vendorContractMapper.getVendorContractListById("vendor_1");
        Assert.assertEquals(2, vendorContracts.size());
    }

    @Test
    public void testGetVendorContractList() throws Exception {
        String[] vendorIds = {"vendor_1", "vendor_2"};
        List<VendorContract> vendorContracts = vendorContractMapper
                .getVendorContractListByIds(Arrays.asList(vendorIds));
        Assert.assertNotNull(vendorContracts);
        Assert.assertEquals(vendorContracts.size(), 3);
        vendorContracts = vendorContractMapper.getVendorContractListByIds(new ArrayList<>());
        Assert.assertNotNull(vendorContracts);
        Assert.assertEquals(vendorContracts.size(), 3);
        vendorContracts = vendorContractMapper.getVendorContractListByIds(Arrays.asList("vendor_3"));
        Assert.assertNotNull(vendorContracts);
        Assert.assertEquals(vendorContracts.size(), 0);
    }

    @Test
    public void delete() throws Exception {
        String vendorId = "test";
        VendorContract contract = new VendorContract(vendorId, "testNum", "test");
        vendorContractMapper.add(contract);
        List<VendorContract> contracts = vendorContractMapper.getVendorContractListById(vendorId);
        Assert.assertEquals(contracts.size(), 1);
        vendorContractMapper.delete(contracts.get(0).getId());
        contracts = vendorContractMapper.getVendorContractListById(vendorId);
        Assert.assertEquals(contracts.size(), 0);
    }

}