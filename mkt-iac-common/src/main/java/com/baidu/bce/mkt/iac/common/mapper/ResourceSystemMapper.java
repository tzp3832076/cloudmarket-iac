// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baidu.bce.mkt.iac.common.model.db.ResourceSystem;

/**
 * resource system mapper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public interface ResourceSystemMapper {
    String TABLE = "mkt_resource_system";
    String SELECT_COLUMNS = "id, resource, system";
    String INSERT_COLUMNS = "resource, system";
    String SELECT_SQL_PREFIX = "SELECT " + SELECT_COLUMNS + " FROM " + TABLE + " ";
    String INSERT_SQL_PREFIX = "INSERT INTO " + TABLE + " (" + INSERT_COLUMNS + ") VALUES ";

    @Insert(INSERT_SQL_PREFIX
            + "("
            + "  @{resource},"
            + "  @{system}"
            + ")")
    @Options(useGeneratedKeys = true)
    int save(ResourceSystem resourceSystem);

    @Select(SELECT_SQL_PREFIX + " WHERE resource = @{resource}")
    ResourceSystem getByResource(@Param("resource") String resource);

    @Select(SELECT_SQL_PREFIX)
    List<ResourceSystem> getAll();
}
