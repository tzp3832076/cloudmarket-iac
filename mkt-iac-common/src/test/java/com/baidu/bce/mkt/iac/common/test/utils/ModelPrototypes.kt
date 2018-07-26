/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.test.utils;

import com.baidu.bce.mkt.iac.common.model.db.VendorPayee
import org.springframework.cglib.core.ReflectUtils

/**
 *
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */

fun getPayeePrototype(): VendorPayee {
    var vendorPayee = VendorPayee()
    vendorPayee.vendorId = "SOAR"
    vendorPayee.bankCardNumber = "123456"
    vendorPayee.bankLocationCity = "hd"
    vendorPayee.bankLocationProvince = "bj"
    vendorPayee.branchBankName = "zllll"
    vendorPayee.bankName = "hahah"
    vendorPayee.isValid = true
    vendorPayee.companyLocationProvince = "gz"
    vendorPayee.companyLocationCity = "sz"
    vendorPayee.taxFlag = "SPECIAL"
    return vendorPayee
}

fun objEquals( obj1 : Any, obj2 : Any, ignored : Collection<String>) : Boolean {

    if (!obj1.javaClass.equals(obj2.javaClass)) {
        return false
    }
    val ignoredFields = HashSet<String>()
    ignoredFields.addAll(ignored)
    ignoredFields.add("createTime")
    ignoredFields.add("updateTime")

    val javaClass = obj1.javaClass

    val getters = ReflectUtils.getBeanGetters(javaClass)

    for (getter in getters) {
        val getterName = getter.name
        if(! ignoredFields.contains(getterName)) {
            val val1 = getter.readMethod.invoke(obj1)
            val val2 = getter.readMethod.invoke(obj2)
            if (val1 == null) {
                if (val2 != null) {
                    return false
                }
            }
            else if (!val1.equals(val2)) {
                return false
            }
        }
    }
    return true
}

