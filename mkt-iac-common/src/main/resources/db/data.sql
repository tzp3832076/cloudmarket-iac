INSERT INTO bce_cloudmarket_iac.mkt_vendor_info
(vendor_id, bce_user_id, status, company, website, capital,address,telephone,service_category,hotline, other_market, contact_info, wallet_id)
(select v.id, uuid(), ''HOSTED'', v.name, v.website, 0, "" as ''address'', v.telephone, '''' as ''service_category'', v.hotline, v.other_mkt, "" as ''contact_info'', "" as ''wallet_id'' from
bce_cloudmarket.mkt_vendor as v);

UPDATE bce_cloudmarket_iac.mkt_vendor_info  SET bce_user_id = 'b1f91fbe6fe54d2eaf70ef0025f1c3c2' WHERE  vendor_id = 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae';

INSERT INTO mkt_role (role, description) VALUES ('OP', '运营'), ('VENDOR','服务商');
INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('b1f91fbe6fe54d2eaf70ef0025f1c3c2', 'BCE', 'VENDOR', 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae');
INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('UUAP:wujinlin', 'UUAP', 'OP', '');

INSERT INTO mkt_permission (resource, operation, description) VALUES
('product', 'read', '获取商品'),
('vendorOverview', 'read', '服务商概览'),
('vendorInfo', 'read', '服务商基本信息'),
('vendorShop', 'read', '商铺草稿信息获取'),
('vendorShop', 'submitDraft', '商铺草稿信息提交'),
('vendorShop', 'saveDraft', '商铺草稿信息保存'),
('vendorContractAndDeposit', 'update', '合同list和保证金更新'),
('vendorContractAndDeposit', 'read', '读取合同list');

INSERT INTO  mkt_role_permission (role, resource, operation, action) VALUES
('VENDOR', 'vendorOverview', 'read', 'ALLOW'),
('VENDOR', 'vendorInfo', 'read', 'ALLOW'),
('VENDOR', 'vendorShop', 'read', 'ALLOW'),
('VENDOR', 'vendorShop', 'submitDraft', 'ALLOW'),
('VENDOR', 'vendorShop', 'saveDraft', 'ALLOW'),
('OP', 'vendorContractAndDeposit', 'update', 'ALLOW'),
('OP', 'vendorContractAndDeposit', 'read', 'ALLOW'),
('OP', 'vendorInfo', 'update', 'ALLOW'),
('OP', 'vendorInfo', 'read', 'ALLOW');

INSERT INTO mkt_resource_system (resource, system) VALUES
('audit', 'MKT_AUDIT'),
('vendorprocess', 'MKT_AUDIT'),
('product', 'MKT_PRODUCT');

