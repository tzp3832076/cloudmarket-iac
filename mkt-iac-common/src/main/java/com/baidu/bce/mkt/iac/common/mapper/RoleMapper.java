// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.Role;

/**
 * role mapper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface RoleMapper {
    String TABLE = "mkt_role";
    String SELECT_COLUMNS = "id, role, description, create_time, update_time";
    String INSERT_COLUMNS = "role, description, create_time";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";

    @Insert(INSERT_SQL_PREFIX
            + "("
            + "  @{role},"
            + "  @{description},"
            + "  NOW()"
            + ")")
    @Options(useGeneratedKeys = true)
    int save(Role role);

    @Select(SELECT_SQL_PREFIX + "WHERE role = @{role}")
    Role getByRole(@Param("role") String role);
}
