alter table mkt_vendor_info modify column service_category VARCHAR(512) NOT NULL COMMENT '服务类别用，分割';

ALTER TABLE mkt_role ADD sub_roles VARCHAR(128) NOT NULL DEFAULT '' COMMENT '当前角色包含的子角色，只能包含一层子角色' AFTER description;

INSERT INTO mkt_role (role, description, sub_roles, create_time) VALUES
('OP_ADMIN', '云市场管理员', 'OP,OP_FINANCE', NOW());

UPDATE mkt_account SET role = 'OP_ADMIN' where account_id in ('UUAP:wujinlin','UUAP:zhangmengmeng01','UUAP:sunfangyuan','UUAP:pengliangyou','UUAP:zhanghui13');

INSERT INTO mkt_role_permission (role, resource, operation, action, create_time) VALUES
('OP', 'vendorProduct', 'productToPreOnline', 'ALLOW', NOW());

INSERT INTO mkt_role_permission (role, resource, operation, action, create_time) VALUES
('VENDOR', 'vendorOrder', 'closeOrder', 'ALLOW', NOW()),
('OP', 'vendorOrder', 'closeOrder', 'ALLOW', NOW());

INSERT INTO mkt_role_permission (role, resource, operation, action, create_time) VALUES
('VENDOR', 'produceTest', 'isExist', 'ALLOW', NOW()),
('VENDOR', 'produceTest', 'apiList', 'ALLOW', NOW()),
('VENDOR', 'produceTest', 'apiTest', 'ALLOW', NOW()),
('VENDOR', 'produceTest', 'preOnline', 'ALLOW', NOW()),
('OP', 'produceTest', 'isExist', 'ALLOW', NOW()),
('OP', 'produceTest', 'apiList', 'ALLOW', NOW()),
('OP', 'produceTest', 'apiTest', 'ALLOW', NOW()),
('OP', 'produceTest', 'preOnline', 'ALLOW', NOW());

INSERT INTO mkt_role_permission (role, resource, operation, action, create_time) VALUES
('VENDOR', 'vendorProduct', 'readStable', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'readStable', 'ALLOW', NOW()),
('SERVICE', 'vendorProduct', 'productToPreOnline', 'ALLOW', NOW());

UPDATE mkt_account SET account_type = 'SERVICE' where account_id = '53bc63587f2642e4897ac3305873a956';