/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.VendorPayee;

/**
 * 服务商收款信息数据表 mapper
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */
public interface VendorPayeeMapper {

    String TABLE = "mkt_vendor_payee";

    String INSERT_COLUMNS =
            " vendor_id, company_location_province, company_location_city, bank_name, branch_bank_name, "
                    + "bank_card_number, bank_location_province, bank_location_city, valid, create_time";

    String SELECT_COLUMNS = INSERT_COLUMNS + ", update_time";

    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";

    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";

    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX
            + " ("
            + " @{vendorId},"
            + " @{companyLocationProvince},"
            + " @{companyLocationCity},"
            + " @{bankName},"
            + " @{branchBankName},"
            + " @{bankCardNumber},"
            + " @{bankLocationProvince},"
            + " @{bankLocationCity},"
            + " @{valid},"
            + " NOW()"
            + " )")
    int save(VendorPayee vendorPayee);

    @Select(SELECT_SQL_PREFIX
            + " WHERE vendor_id = @{vendorId} and valid = 1")
    VendorPayee  getValidVendorPayee(@Param("vendorId") String vendorId);

    @Select(SELECT_SQL_PREFIX
            + " WHERE vendor_id = @{vendorId} ")
    VendorPayee  getVendorPayee(@Param("vendorId") String vendorId);

    @Update(UPDATE_SQL_PREFIX
            + " SET valid = @{valid} WHERE vendor_id = @{vendorId}")
    int updateValidFlag(@Param("valid") boolean valid, @Param("vendorId") String vendorId);

    @Update(UPDATE_SQL_PREFIX
            + " SET "
            + " company_location_province = @{companyLocationProvince}, "
            + " company_location_city = @{companyLocationCity},"
            + " bank_name = @{bankName},"
            + " branch_bank_name = @{branchBankName},"
            + " bank_card_number = @{bankCardNumber},"
            + " bank_location_province = @{bankLocationProvince},"
            + " bank_location_city = @{bankLocationCity},"
            + " valid = @{valid}"
            + " WHERE vendor_id = @{vendorId}")
    int updateVendorPayee(VendorPayee vendorPayee);

}
