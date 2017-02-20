// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.RolePermission;

/**
 * role permission mapper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface RolePermissionMapper {
    String TABLE = "mkt_role_permission";
    String SELECT_COLUMNS = "id, role, resource, operation, action, create_time, update_time";
    String INSERT_COLUMNS = "role, resource, operation, action, create_time";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";

    @Insert(INSERT_SQL_PREFIX
            + "("
            + "  @{role},"
            + "  @{resource},"
            + "  @{operation},"
            + "  @{action},"
            + "  NOW()"
            + ")")
    @Options(useGeneratedKeys = true)
    int save(RolePermission rolePermission);

    @Select(SELECT_SQL_PREFIX
            + " WHERE role = @{role} AND resource = @{resource} AND operation = @{operation}")
    RolePermission getByRoleResourceOperation(@Param("role") String role,
                                              @Param("resource") String resource,
                                              @Param("operation") String operation);
}
