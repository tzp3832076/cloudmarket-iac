CREATE TABLE mkt_account (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    account_id VARCHAR(64) NOT NULL COMMENT '账号id，bce user id或者uuap name',
    account_type VARCHAR(16) NOT NULL COMMENT 'BCE 或 UUAP',
    role VARCHAR(32) NOT NULL COMMENT '角色',
    vendor_id VARCHAR(64) NOT NULL COMMENT '服务商id，冗余字段',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_account_id (account_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '账号信息表';

CREATE TABLE mkt_permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    resource VARCHAR(32) NOT NULL COMMENT '资源类型名称',
    operation VARCHAR(32) NOT NULL COMMENT '操作',
    description VARCHAR(128) NOT NULL COMMENT '权限描述',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_resource_operation (resource, operation)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '权限表';

CREATE TABLE mkt_role_permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    role VARCHAR(32) NOT NULL COMMENT '角色',
    resource VARCHAR(32) NOT NULL COMMENT '资源类型名称',
    operation VARCHAR(32) NOT NULL COMMENT '操作',
    action VARCHAR(16) NOT NULL COMMENT '动作，ALLOW | DENY',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_resource_operation (role, resource, operation)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色权限关系表';

CREATE TABLE mkt_role (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    role VARCHAR(32) NOT NULL COMMENT '角色',
    description VARCHAR(128) NOT NULL COMMENT '角色描述',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role (role)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色表';

CREATE TABLE mkt_resource_system (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    resource VARCHAR(32) NOT NULL COMMENT '资源类型名称',
    system VARCHAR(32) NOT NULL COMMENT '系统名称，MKT-IAC | MKT-AUDIT | MKT-OSS',
    PRIMARY KEY (id),
    UNIQUE KEY uk_resource (resource)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '资源所在系统关系表';