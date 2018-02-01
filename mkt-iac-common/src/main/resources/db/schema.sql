CREATE TABLE mkt_account (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
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
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    resource VARCHAR(32) NOT NULL COMMENT '资源类型名称',
    operation VARCHAR(32) NOT NULL COMMENT '操作',
    description VARCHAR(128) NOT NULL COMMENT '权限描述',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_resource_operation (resource, operation)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '权限表';

CREATE TABLE mkt_role_permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
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
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role VARCHAR(32) NOT NULL COMMENT '角色',
    description VARCHAR(128) NOT NULL COMMENT '角色描述',
    sub_roles VARCHAR(128) NOT NULL DEFAULT '' COMMENT '当前角色包含的子角色，只能包含一层子角色',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role (role)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '角色表';

CREATE TABLE mkt_resource_system (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    resource VARCHAR(32) NOT NULL COMMENT '资源类型名称',
    system VARCHAR(32) NOT NULL COMMENT '系统名称，MKT-IAC | MKT-AUDIT | MKT-OSS',
    PRIMARY KEY (id),
    UNIQUE KEY uk_resource (resource)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '资源所在系统关系表';

CREATE TABLE mkt_vendor_info (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    bce_user_id VARCHAR(36) NOT NULL COMMENT 'BCE 关联ID',
    status VARCHAR(32) NOT NULL COMMENT '总控状态 INIT | ONLINE | OFFLINE',
    company VARCHAR(256) NOT NULL COMMENT '公司名称',
    website VARCHAR(256) NOT NULL COMMENT '公司网站',
    capital BIGINT(20) NOT NULL COMMENT '注册资本',
    headcount BIGINT(20) NOT NULL DEFAULT 0 COMMENT '雇员人数',
    address VARCHAR(256) NOT NULL COMMENT '公司地址',
    telephone VARCHAR(32) NOT NULL COMMENT '公司状态',
    service_category VARCHAR(512) NOT NULL COMMENT '服务类别 叶子节点，分割 ',
    hotline VARCHAR(256) NOT NULL COMMENT '热线电话',
    other_market VARCHAR(256) NOT NULL COMMENT '已加入提前的云市场',
    contact_info VARCHAR(512) NOT NULL COMMENT '服务商联系人信息 json',
    email VARCHAR(64) NOT NULL DEFAULT '' COMMENT '联系人邮箱',
    service_illustration VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '服务说明',
    wallet_id VARCHAR(64) NOT NULL COMMENT '百度钱包ID',
    agreement_status VARCHAR(32) NOT NULL DEFAULT 'TODO' COMMENT '电子协议签署状态 TODO|DONE',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_info_vendor_id (vendor_id),
    UNIQUE KEY uk_vendor_info_bce_user_id (bce_user_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商信息表';

CREATE TABLE mkt_vendor_shop (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
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
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    content VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '全部待审核信息 json',
    status VARCHAR(64) NOT NULL COMMENT '状态 EDIT|AUDIT|PASS|REJECT',
    audit_id VARCHAR(36) NOT NULL DEFAULT '' COMMENT '审核ID',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_shop_draft_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '商品信息草稿表';

CREATE TABLE mkt_vendor_deposit (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    target_value DECIMAL(10,2) NOT NULL DEFAULT 10000.00 COMMENT '保证金的设定金额',
    pay_value DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '已经缴纳的金额',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_deposit_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商保证金表';

CREATE TABLE mkt_vendor_contract (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    contract_num VARCHAR(128) NOT NULL DEFAULT '' COMMENT '协议号',
    contract_digest VARCHAR(256) NOT NULL DEFAULT '' COMMENT '协议内容摘要',
    begin_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '有效起始时间',
    end_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '有效结束时间',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_delete TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0未删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vendor_contarct_vendor_id (vendor_id, contract_num)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商协议号表';


CREATE TABLE mkt_vendor_payee (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    vendor_id VARCHAR(36) NOT NULL COMMENT '服务商云市场ID',
    company_location_province VARCHAR(64) NOT NULL COMMENT '服务商公司所在省份',
    company_location_city VARCHAR(64) NOT NULL COMMENT '服务商公司所在城市',
    bank_name VARCHAR(64) NOT NULL COMMENT '开户行名称',
    branch_bank_name VARCHAR(64) NOT NULL COMMENT '开户支行名称',
    bank_card_number VARCHAR(30) NOT NULL COMMENT '银行卡号',
    bank_location_province VARCHAR(64) NOT NULL COMMENT '开户行所在省份',
    bank_location_city VARCHAR(64) NOT NULL COMMENT '开户行所在城市',
    valid TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否有效标记位',
    create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_mkt_vendor_payee_vendor_id (vendor_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '服务商收款人信息';