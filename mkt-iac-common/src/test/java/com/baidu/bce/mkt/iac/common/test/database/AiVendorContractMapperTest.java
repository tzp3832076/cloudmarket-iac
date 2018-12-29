package com.baidu.bce.mkt.iac.common.test.database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.AiVendorContractMapper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorContract;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-28.
 */

@InitDatabase(tables = "mkt_ai_vendor_contract")
public class AiVendorContractMapperTest extends BaseMapperTest{

    @Autowired
    private AiVendorContractMapper aiVendorContractMapper;

    @Test
    public void add() {
        AiVendorContract aiVendorContract = new AiVendorContract("test", "testContractNum", "testCustomerNum",
                "test", Timestamp.valueOf("2018-12-28 00:00:00"), Timestamp.valueOf("2018-12-28 00:00:00"));

        int res = aiVendorContractMapper.add(aiVendorContract);
        Assert.assertEquals(1, res);
    }

    @Test
    public void getAiVendorContract() throws Exception {
        List<AiVendorContract> vendorContracts = aiVendorContractMapper.getAiVendorContractListById("vendor_1");
        Assert.assertEquals(2, vendorContracts.size());
    }

    @Test
    public void testGetAiVendorContractList() throws Exception {
        String[] vendorIds = {"vendor_1", "vendor_2"};
        List<AiVendorContract> vendorContracts = aiVendorContractMapper
                .getAiVendorContractListByIds(Arrays.asList(vendorIds));
        Assert.assertNotNull(vendorContracts);
        Assert.assertEquals(vendorContracts.size(), 3);
        vendorContracts = aiVendorContractMapper.getAiVendorContractListByIds(new ArrayList<>());
        Assert.assertNotNull(vendorContracts);
        Assert.assertEquals(vendorContracts.size(), 3);
        vendorContracts = aiVendorContractMapper.getAiVendorContractListByIds(Arrays.asList("vendor_3"));
        Assert.assertNotNull(vendorContracts);
        Assert.assertEquals(vendorContracts.size(), 0);
    }
}
