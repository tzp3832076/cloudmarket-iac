/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.VendorShop;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
public interface VendorShopMapper {
    String TABLE = "mkt_vendor_shop";
    String INSERT_COLUMNS = " vendor_id, name, intro, email, cellphone, "
                                    + "service_info, "
                                    + "create_time ";
    String SELECT_COLUMNS = INSERT_COLUMNS + ", update_time ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX + "( "
                    + " @{vendorId},"
                    + " @{name},"
                    + " @{intro},"
                    + " @{email},"
                    + " @{cellphone},"
                    + " @{serviceInfo},"
                    + " NOW() "
                    + " )")
    int add(VendorShop vendorShop);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId}")
    VendorShop getVendorShopByVendorId(@Param("vendorId") String vendorId);

}
