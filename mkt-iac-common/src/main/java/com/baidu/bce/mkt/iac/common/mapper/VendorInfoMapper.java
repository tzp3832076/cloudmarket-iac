/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListFilter;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
public interface VendorInfoMapper {
    String TABLE = "mkt_vendor_info";
    String INSERT_COLUMNS = " vendor_id, bce_user_id, status, company, website, "
                                    + "capital, address, telephone, service_category, hotline, "
                                    + "other_market, contact_info, wallet_id, "
                                    + "create_time ";
    String SELECT_COLUMNS = INSERT_COLUMNS + ", update_time, agreement_status ";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String COUNT_SQL_PREFIX = "SELECT count(1) FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";
    String DYNAMIC_SEARCH_SQL = " #where()"
                                        + " #if ($_parameter.filter.bceUserId)"
                                        + " AND bce_user_id = @{filter.bceUserId} "
                                        + " #end "
                                        + " #if ($_parameter.filter.companyName)"
                                        + " AND company like @{filter.companyName} "
                                        + " #end "
                                        + " #if ($_parameter.filter.status)"
                                        + " AND status = @{filter.status} "
                                        + " #end "
                                        + " #end ";

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

    @Select(SELECT_SQL_PREFIX + "where company = @{companyName}")
    List<VendorInfo> getVendorInfoByCompanyName(@Param("companyName") String companyName);

    @Update(UPDATE_SQL_PREFIX + " set status = @{status} where vendor_id = @{vendorId}")
    int updateVendorStatus(@Param("vendorId") String vendorId, @Param("status")VendorStatus status);

    @Update(UPDATE_SQL_PREFIX + " set agreement_status = @{status} where vendor_id = @{vendorId}")
    int updateAgreementStatus(@Param("vendorId") String vendorId, @Param("status")ProcessStatus status);

    @Select(SELECT_SQL_PREFIX + "where bce_user_id = @{bceUserId}")
    VendorInfo getVendorInfoByBceUserId(@Param("bceUserId") String bceUserId);

    @Update(UPDATE_SQL_PREFIX + " set wallet_id = @{walletId} where vendor_id = @{vendorId}")
    int updateWalletId(@Param("vendorId") String vendorId, @Param("walletId")String walletId);

    @Select(COUNT_SQL_PREFIX + " WHERE status = @{status}")
    int getVendorCountByStatus(@Param("status") VendorStatus status);

    @Select(SELECT_SQL_PREFIX + DYNAMIC_SEARCH_SQL + " ORDER BY create_time DESC"
                    + "  #if ($_parameter.start >= 0 && $_parameter.limit > 0)"
                    + "    LIMIT @{start}, @{limit}"
                    + "  #end")
    List<VendorInfo> getVendorList(@Param("filter") VendorListFilter filter,
                                   @Param("start") int start,
                                   @Param("limit") int limit);

    @Select(SELECT_SQL_PREFIX + " #where()"
                    + "  #in( $_parameter.vendorIds $vendorId \"vendor_id\")"
                    + "   @{vendorId}"
                    + "  #end"
                    + " #end")
    List<VendorInfo> getVendorListByIds(@Param("vendorIds") List<String> vendorIds);

    @Select(COUNT_SQL_PREFIX + DYNAMIC_SEARCH_SQL)
    int getVendorCount(@Param("filter") VendorListFilter filter);
}
