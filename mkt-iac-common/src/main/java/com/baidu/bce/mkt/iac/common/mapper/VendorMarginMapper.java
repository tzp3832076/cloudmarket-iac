/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.VendorMargin;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
public interface VendorMarginMapper {
    String TABLE = "mkt_vendor_margin";
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
    int add(VendorMargin vendorMargin);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId}")
    VendorMargin getVendorMargin(@Param("vendorId") String vendorId);

    @Update(UPDATE_SQL_PREFIX + " SET target_value = @{targetValue},"
                    + " pay_value = @{payValue} "
                    + " WHERE vendor_id = @{vendorId}")
    int update(VendorMargin vendorMargin);
}
