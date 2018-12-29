/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.AiVendorContract;

import java.util.List;

/**
 * ai服务商合同协议信息
 *
 * Created by v_zhouhuikun@baidu.com on 2018-12-25.
 */
public interface AiVendorContractMapper {
    String TABLE = "mkt_ai_vendor_contract";
    String INSERT_COLUMNS = " vendor_id, contract_num, customer_num, contract_digest, begin_time, end_time, "
            + "create_time ";
    String SELECT_COLUMNS = " id, " + INSERT_COLUMNS + ", update_time ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX + "("
                    + " @{vendorId},"
                    + " @{contractNum},"
                    + " @{customerNum},"
                    + " @{contractDigest},"
                    + " @{beginTime},"
                    + " @{endTime},"
                    + " Now()"
                    + ")")
    int add(AiVendorContract vendorContract);

    @Select(SELECT_SQL_PREFIX + " WHERE vendor_id = @{vendorId} AND is_delete = 0 "
            + "ORDER BY create_time DESC")
    List<AiVendorContract> getAiVendorContractListById(@Param("vendorId") String vendorId);

    @Select(SELECT_SQL_PREFIX + "WHERE vendor_id = @{vendorId} "
            + " AND contract_num = @{contractNum}")
    AiVendorContract getAiVendorContract(@Param("vendorId") String vendorId,
                                     @Param("contractNum") String contractNum);

    @Select(SELECT_SQL_PREFIX + " #where()"
            + "  #in( $_parameter.vendorIds $vendorId \"vendor_id\")"
            + "   @{vendorId}"
            + "  #end"
            + " #end")
    List<AiVendorContract> getAiVendorContractListByIds(@Param("vendorIds") List<String> vendorIds);

    @Update(UPDATE_SQL_PREFIX + " SET is_delete = 1 WHERE id = @{id}")
    int delete(@Param("id") int id);
}
