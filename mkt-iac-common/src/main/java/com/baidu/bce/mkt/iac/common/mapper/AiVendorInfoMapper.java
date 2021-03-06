/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.mapper;

import com.baidu.bce.mkt.iac.common.model.AiVendorListFilter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;

import java.util.List;

/**
 * Created by chenxiang05@baidu.com on 2018/8/6.
 */
public interface AiVendorInfoMapper {

    String TABLE = "mkt_ai_vendor_info";

    String SELECT_COLUMNS = "id, vendor_id, user_id, user_type, company, website, capital,  headcount, "
            + "enterprise_email, business_contact, emergency_contact, mobile, telephone, email, service_type,"
            + "application_area, business_introduction, create_time, update_time";

    String INSERT_COLUMNS = "vendor_id, user_id, user_type, company, website, capital,  headcount, "
            + "enterprise_email, business_contact, emergency_contact, mobile, telephone, email, service_type,"
            + "application_area, business_introduction, create_time";

    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";

    String COUNT_SQL_PREFIX = "SELECT count(1) FROM " + TABLE + " ";

    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";

    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    String DYNAMIC_SEARCH_SQL = " #where()"
            + " #if ($_parameter.filter.companyName)"
            + " AND company like @{filter.companyName} "
            + " #end "
            + " #end ";

    @Insert(INSERT_SQL_PREFIX
            + "("
            + " @{vendorId},"
            + " @{userId},"
            + " @{userType},"
            + " @{company},"
            + " @{website},"
            + " @{capital},"
            + " @{headcount},"
            + " @{enterpriseEmail},"
            + " @{businessContact},"
            + " @{emergencyContact},"
            + " @{mobile},"
            + " @{telephone},"
            + " @{email},"
            + " @{serviceType},"
            + " @{applicationArea},"
            + " @{businessIntroduction},"
            + " now()"
            + ")")
    int save(AiVendorInfo aiVendorInfo);

    @Update(UPDATE_SQL_PREFIX
            + " SET "
            + " vendor_id = @{vendorId},"
            + " user_type = @{userType},"
            + " company = @{company},"
            + " website = @{website},"
            + " capital = @{capital},"
            + " headcount = @{headcount},"
            + " enterprise_email = @{enterpriseEmail},"
            + " business_contact = @{businessContact},"
            + " emergency_contact = @{emergencyContact},"
            + " mobile = @{mobile},"
            + " telephone = @{telephone},"
            + " email = @{email},"
            + " service_type = @{serviceType},"
            + " application_area = @{applicationArea},"
            + " business_introduction  = @{businessIntroduction}"
            + " WHERE vendor_id = @{vendorId} AND update_time = @{updateTime}")
    int update(AiVendorInfo aiVendorInfo);

    @Select(SELECT_SQL_PREFIX + " WHERE vendor_id = @{vendorId}")
    AiVendorInfo getByVendorId(@Param("vendorId") String vendorId);

    @Select(SELECT_SQL_PREFIX + "WHERE company = @{company}")
    AiVendorInfo getByCompanyName(@Param("company") String company);

    @Select(SELECT_SQL_PREFIX + "WHERE user_type = @{userType} AND user_id = @{userId}")
    AiVendorInfo getByUserTypeAndUserId(@Param("userType") String userType,
                                        @Param("userId") String userId);


    @Select(COUNT_SQL_PREFIX + DYNAMIC_SEARCH_SQL)
    int getAiVendorCount(@Param("filter") AiVendorListFilter filter);

    @Select(SELECT_SQL_PREFIX + DYNAMIC_SEARCH_SQL + " ORDER BY create_time "
            + "  #if ($_parameter.filter.start >= 0 && $_parameter.filter.limit > 0)"
            + "    LIMIT @{filter.start}, @{filter.limit}"
            + "  #end")
    List<AiVendorInfo> getAiVendorList(@Param("filter") AiVendorListFilter filter);

    @Select(SELECT_SQL_PREFIX + " #where()"
            + "  #in( $_parameter.vendorIds $vendorId \"vendor_id\")"
            + "   @{vendorId}"
            + "  #end"
            + " #end")
    List<AiVendorInfo> getVendorListByIds(@Param("vendorIds") List<String> vendorIds);
}
