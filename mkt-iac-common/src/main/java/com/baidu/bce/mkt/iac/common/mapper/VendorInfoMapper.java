/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
public interface VendorInfoMapper {
    String TABLE = "mkt_vendor_info";
    String INSERT_COLUMNS = " vendor_id, bce_user_id, status, company, website, "
                                    + "capital, address, telephone, service_category, hotline, "
                                    + "other_market, contact_info, wallet_id, "
                                    + "create_time ";
    String SELECT_COLUMNS = INSERT_COLUMNS + ", update_time ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX + "( "
                    + " @{vendorId},"
                    + " @{bceUserId},"
                    + " @{status},"
                    + " @{company},"
                    + " @{website},"
                    + " @{capital},"
                    + " @{address},"
                    + " @{telephone},"
                    + " @{serviceCategory},"
                    + " @{hotline},"
                    + " @{otherMarket},"
                    + " @{contactInfo},"
                    + " @{walletId},"
                    + " NOW() "
                    + " )")
    int add(VendorInfo vendorInfo);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId}")
    VendorInfo getVendorInfoByVendorId(@Param("vendorId") String vendorId);

}
