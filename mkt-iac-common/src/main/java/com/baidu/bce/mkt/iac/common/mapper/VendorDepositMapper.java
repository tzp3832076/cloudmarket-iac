/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
public interface VendorDepositMapper {
    String TABLE = "mkt_vendor_deposit";
    String INSERT_COLUMNS = " vendor_id, target_value, pay_value, create_time ";
    String SELECT_COLUMNS = INSERT_COLUMNS + ", update_time ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX + "("
                    + " @{vendorId},"
                    + " @{targetValue},"
                    + " @{payValue},"
                    + " Now()"
                    + ")")
    int add(VendorDeposit vendorDeposit);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId}")
    VendorDeposit getVendorDeposit(@Param("vendorId") String vendorId);

    @Update(UPDATE_SQL_PREFIX + " SET target_value = @{targetValue},"
                    + " pay_value = @{payValue} "
                    + " WHERE vendor_id = @{vendorId}")
    int update(VendorDeposit vendorDeposit);
}
