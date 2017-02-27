SET MODE MySQL;
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

CREATE TABLE mkt_vendor_info (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    bce_user_id VARCHAR(36) NOT NULL COMMENT 'BCE 关联ID',
    status VARCHAR(32) NOT NULL COMMENT '总控状态 INIT | ONLINE | OFFLINE',
    company VARCHAR(256) NOT NULL COMMENT '公司名称',
    website VARCHAR(256) NOT NULL COMMENT '公司网站',
    capital VARCHAR(32) NOT NULL COMMENT '注册资本',
    address VARCHAR(256) NOT NULL COMMENT '公司地址',
    telephone VARCHAR(32) NOT NULL COMMENT '公司状态',
    service_category VARCHAR(256) NOT NULL COMMENT '服务类别 一级-二级 ',
    hotline VARCHAR(256) NOT NULL COMMENT '热线电话',
    other_market VARCHAR(256) NOT NULL COMMENT '已加入提前的云市场',
    contact_info VARCHAR(512) NOT NULL COMMENT '服务商联系人信息 json',
    wallet_id VARCHAR(64) NOT NULL COMMENT '百度钱包ID',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_info_vendor_id (vendor_id),
    UNIQUE KEY uk_vendor_info_bce_user_id (bce_user_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商信息表';

CREATE TABLE mkt_vendor_shop (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    name VARCHAR(128) NOT NULL DEFAULT '' COMMENT '服务商名称',
    intro VARCHAR(256) NOT NULL DEFAULT '' COMMENT '服务商介绍',
    email VARCHAR(64) NOT NULL DEFAULT '' COMMENT '邮件，用于给服务商发邮件',
    cellphone VARCHAR(32) NOT NULL DEFAULT '' COMMENT '手机，用于给服务商发短信',
    service_info VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '客服信息 json',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_shop_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '线上商铺信息表';

CREATE TABLE mkt_vendor_shop_draft (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    content VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '全部待审核信息 json',
    status VARCHAR(64) NOT NULL COMMENT '状态 EDIT|AUDIT|PASS|REJECT',
    audit_id VARCHAR(36) NOT NULL DEFAULT '' COMMENT '审核ID',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_shop_draft_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '商品信息草稿表';

CREATE TABLE mkt_vendor_margin (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    target_value DECIMAL(10,2) NOT NULL DEFAULT 10000.00 COMMENT '保证金的设定金额',
    pay_value DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '已经缴纳的金额',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_margin_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商保证金表';

CREATE TABLE mkt_vendor_contract (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    contract_num VARCHAR(128) NOT NULL DEFAULT '' COMMENT '协议号',
    contract_digest VARCHAR(256) NOT NULL DEFAULT '' COMMENT '协议内容摘要',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_vendor_contarct_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商协议号表';


