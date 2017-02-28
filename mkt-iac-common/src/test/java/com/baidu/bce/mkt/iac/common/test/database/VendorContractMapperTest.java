/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

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
        List<VendorContract> vendorContracts = vendorContractMapper.getVendorContractList("vendor_1");
        Assert.assertEquals(2, vendorContracts.size());
    }

    @Test
    public void delete() throws Exception {
        String vendorId = "test";
        VendorContract contract = new VendorContract(vendorId, "testNum", "test");
        vendorContractMapper.add(contract);
        List<VendorContract>  contracts = vendorContractMapper.getVendorContractList(vendorId);
        Assert.assertEquals(contracts.size(), 1);
        vendorContractMapper.delete(contracts.get(0).getId());
        contracts = vendorContractMapper.getVendorContractList(vendorId);
        Assert.assertEquals(contracts.size(), 0);
    }

}