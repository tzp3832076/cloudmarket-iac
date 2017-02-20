// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.Account;

/**
 * account mapper
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface AccountMapper {
    String TABLE = "mkt_account";
    String SELECT_COLUMNS = "id, account_id, account_type, role, vendor_id, create_time, update_time";
    String INSERT_COLUMNS = "account_id, account_type, role, vendor_id, create_time";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";
    String UPDATE_SQL_PREFIX = "UPDATE " + TABLE + " ";

    @Insert(INSERT_SQL_PREFIX
            + "("
            + "  @{accountId},"
            + "  @{accountType},"
            + "  @{role},"
            + "  @{vendorId},"
            + "  NOW()"
            + ")")
    @Options(useGeneratedKeys = true)
    int save(Account account);

    @Select(SELECT_SQL_PREFIX + "WHERE account_id = @{accountId}")
    Account getByAccountId(@Param("accountId") String accountId);
}
