INSERT INTO bce_cloudmarket_iac.mkt_vendor_info
(vendor_id, bce_user_id, status, company, website, capital,address,telephone,service_category,hotline, other_market, contact_info, wallet_id)
(select v.id, uuid(), ''HOSTED'', v.name, v.website, 0, "" as ''address'', v.telephone, '''' as ''service_category'', v.hotline, v.other_mkt, "" as ''contact_info'', "" as ''wallet_id'' from
bce_cloudmarket.mkt_vendor as v);

UPDATE bce_cloudmarket_iac.mkt_vendor_info  SET bce_user_id = 'b1f91fbe6fe54d2eaf70ef0025f1c3c2' WHERE  vendor_id = 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae';

INSERT INTO mkt_role (role, description, create_time) VALUES ('OP', '运营', NOW()),('VENDOR','服务商',
 NOW());
INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('b1f91fbe6fe54d2eaf70ef0025f1c3c2', 'BCE', 'VENDOR', 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae');
INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('UUAP:wujinlin', 'UUAP', 'OP', '');

INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('f168739fc9c5473ab798a39c3db446b6', 'BCE', 'INIT_VENDOR', '999608cc-d257-48af-99d2-27c983be8201');

INSERT INTO mkt_permission (resource, operation, description, create_time) VALUES
('product', 'read', '获取商品', NOW()),
('vendorOverview', 'read', '服务商概览', NOW()),
('vendorInfo', 'read', '服务商基本信息', NOW()),
('vendorShop', 'read', '商铺草稿信息获取', NOW()),
('vendorShop', 'submitDraft', '商铺草稿信息提交', NOW()),
('vendorShop', 'saveDraft', '商铺草稿信息保存', NOW()),
('vendorContractAndDeposit', 'update', '合同list和保证金更新', NOW()),
('vendorContractAndDeposit', 'read', '读取合同list', NOW());

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('VENDOR', 'vendorOverview', 'read', 'ALLOW', NOW()),
('VENDOR', 'vendorInfo', 'read', 'ALLOW', NOW()),
('VENDOR', 'vendorShop', 'read', 'ALLOW', NOW()),
('VENDOR', 'vendorShop', 'submitDraft', 'ALLOW', NOW()),
('VENDOR', 'vendorShop', 'saveDraft', 'ALLOW', NOW()),
('OP', 'vendorContractAndDeposit', 'update', 'ALLOW', NOW()),
('OP', 'vendorContractAndDeposit', 'read', 'ALLOW', NOW()),
('OP', 'vendorInfo', 'update', 'ALLOW', NOW()),
('OP', 'vendorInfo', 'read', 'ALLOW', NOW());

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('VENDOR', 'audit', 'submit', 'ALLOW', NOW()),
('VENDOR', 'audit', 'read', 'ALLOW', NOW()),
('VENDOR', 'audit', 'cancel', 'ALLOW', NOW()),
('OP', 'audit', 'read', 'ALLOW', NOW()),
('OP', 'audit', 'update', 'ALLOW', NOW()),
('OP', 'vendorprocess', 'update', 'ALLOW', NOW()),
('OP', 'vendorprocess', 'read', 'ALLOW', NOW());

INSERT INTO mkt_resource_system (resource, system) VALUES
('audit', 'MKT_AUDIT'),
('vendorprocess', 'MKT_AUDIT'),
('product', 'MKT_PRODUCT');

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('INIT_VENDOR', 'vendorOverview', 'read', 'ALLOW', NOW()),
('INIT_VENDOR', 'vendorInfo', 'read', 'ALLOW', NOW()),
('INIT_VENDOR', 'vendorShop', 'read', 'ALLOW', NOW()),
('INIT_VENDOR', 'vendorShop', 'submitDraft', 'ALLOW', NOW()),
('INIT_VENDOR', 'vendorShop', 'saveDraft', 'ALLOW', NOW()),
('INIT_VENDOR', 'audit', 'submit', 'ALLOW', NOW()),
('INIT_VENDOR', 'audit', 'read', 'ALLOW', NOW()),
('INIT_VENDOR', 'audit', 'cancel', 'ALLOW', NOW());

