/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
public interface VendorShopDraftMapper {
    String TABLE = "mkt_vendor_shop_draft";
    String INSERT_COLUMNS = " vendor_id, content, status, audit_id, create_time ";
    String SELECT_COLUMNS = INSERT_COLUMNS + ", update_time ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX + "( "
                    + " @{vendorId},"
                    + " @{content},"
                    + " @{status},"
                    + " @{auditId},"
                    + " NOW() "
                    + " )")
    int add(VendorShopDraft vendorShopDraft);

    @Select(SELECT_SQL_PREFIX + "where vendor_id = @{vendorId}")
    VendorShopDraft getShopDraftByVendorId(@Param("vendorId") String vendorId);

    @Update(UPDATE_SQL_PREFIX + "SET content = @{content}, status = @{status} "
                    + " WHERE vendor_id = @{vendorId}")
    int updateShopDraftContentAndStatus(@Param("vendorId") String vendorId,
                                          @Param("content") String content,
                                          @Param("status") InfoStatus status);

    @Update(UPDATE_SQL_PREFIX + "SET audit_id = @{auditId}, status = @{status} "
                    + " WHERE vendor_id = @{vendorId}")
    int updateShopAuditIdAndStatus(@Param("vendorId") String vendorId,
                                   @Param("auditId") String auditId,
                                   @Param("status") InfoStatus status);
}
