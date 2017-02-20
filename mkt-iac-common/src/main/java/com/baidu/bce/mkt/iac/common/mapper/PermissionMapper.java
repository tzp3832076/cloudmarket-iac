// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.Permission;

/**
 * permission mapper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface PermissionMapper {
    String TABLE = "mkt_permission";
    String SELECT_COLUMNS = "id, resource, operation, description, create_time, update_time";
    String INSERT_COLUMNS = "resource, operation, description, create_time";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";

    @Insert(INSERT_SQL_PREFIX
            + "("
            + "  @{resource},"
            + "  @{operation},"
            + "  @{description},"
            + "  NOW()"
            + ")")
    @Options(useGeneratedKeys = true)
    int save(Permission permission);

    @Select(SELECT_SQL_PREFIX + " WHERE resource = @{resource} AND operation = @{operation}")
    Permission getByResourceAndOperation(@Param("resource") String resource, @Param("operation") String operation);
}
