package com.baidu.bce.mkt.iac.common.test.service;

import com.baidu.bce.mkt.iac.common.model.db.AiVendorContract;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.service.AiVendorExtraService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-28.
 */
@Slf4j
public class AiVendorExtraServiceTest extends BaseCommonServiceTest {

    @Autowired
    private AiVendorExtraService aiVendorExtraService;

    @Test
    public void testAiVendorContractList()  throws Exception {
        aiVendorExtraService.addContract("vendor_3", "123", "23",
                null, null);
        List<AiVendorContract> contractList = aiVendorExtraService.getAiVendorContractList("vendor_3");
        System.err.println(contractList);
        Assert.assertEquals(1, contractList.size());
    }

    @Test
    public void testAddContract() throws Exception {
        aiVendorExtraService.addContract("vendor_4", "test", "testCustomerNum",
                Timestamp.valueOf("2018-12-20 00:00:00"), Timestamp.valueOf("2018-12-20 00:00:00"));
        List<AiVendorContract> contracts = aiVendorExtraService.getAiVendorContractList("vendor_4");
        Assert.assertNotNull(contracts);
        Assert.assertEquals(contracts.size(), 1);

        aiVendorExtraService.addContract("vendor_4", "test2", "testCustomerNum",
                Timestamp.valueOf("2018-12-20 00:00:00"), Timestamp.valueOf("2017-12-20 00:00:00"));
        contracts = aiVendorExtraService.getAiVendorContractList("vendor_4");
        Assert.assertNotNull(contracts);
        Assert.assertEquals(contracts.size(), 2);
    }
}
