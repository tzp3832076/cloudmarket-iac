package com.baidu.bce.mkt.iac.common.test.service

import com.baidu.bce.mkt.iac.common.service.VendorPayeeService
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest
import com.baidu.bce.mkt.iac.common.test.utils.getPayeePrototype
import com.baidu.bce.mkt.iac.common.test.utils.objEquals
import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */

class VendorPayeeServiceTest : BaseCommonServiceTest() {

    @Autowired
    private lateinit var vendorPayeeService : VendorPayeeService;

    @Test
    fun testSyncPayeeInfoToDb() {
        val prototype = getPayeePrototype()
        Assert.assertNull(vendorPayeeService.getVendorPayee(prototype.vendorId))
        vendorPayeeService.syncPayeeInfoToDb(prototype)
        Assert.assertNotNull(vendorPayeeService.getVendorPayee(prototype.vendorId))

        var vendorPayee = vendorPayeeService.getVendorPayee("vendor2")
        prototype.vendorId = vendorPayee.vendorId
        Assert.assertNotNull(vendorPayee)
        Assert.assertFalse(objEquals(prototype, vendorPayee, emptyList()))
        vendorPayeeService.syncPayeeInfoToDb(prototype)
        vendorPayee = vendorPayeeService.getVendorPayee(vendorPayee.vendorId)
        Assert.assertTrue(objEquals(vendorPayee, prototype, emptyList()))
    }

}