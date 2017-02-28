/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.VendorContract;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
public interface VendorContractMapper {
    String TABLE = "mkt_vendor_contract";
    String INSERT_COLUMNS = " vendor_id, contract_num, contract_digest, create_time ";
    String SELECT_COLUMNS = " id, " + INSERT_COLUMNS + ", update_time ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX + "("
                    + " @{vendorId},"
                    + " @{contractNum},"
                    + " @{contractDigest},"
                    + " Now()"
                    + ")")
    int add(VendorContract vendorContract);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId} and is_delete = 0")
    List<VendorContract> getVendorContractList(@Param("vendorId") String vendorId);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId} "
                    + " and contract_num = @{contractNum}")
    VendorContract getVendorContract(@Param("vendorId") String vendorId,
                                     @Param("contractNum") String contractNum);


    @Update(UPDATE_SQL_PREFIX + " SET is_delete = 1 WHERE id = @{id}")
    int delete(@Param("id") int id);
}
