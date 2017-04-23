 INSERT INTO bce_cloudmarket_iac.mkt_vendor_info
(vendor_id, bce_user_id, status, company, website, capital,address,telephone,service_category,hotline, other_market, contact_info, wallet_id)
values
('11cb92aa-03be-49f3-b746-e0c0a1617502' , 'bb6f424f-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '上海英方软件股份有限公司','www.info2soft.com' , 0 , '', '18664388667' ,'102001', '4006178601', 'other_mkt','',''),
('17b74c0f-f140-406f-a165-10643baf2c03' , 'bb6f4279-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '广东耐思尼克信息技术有限公司','http://www.iisp.com', 0 , '', '15819444425' ,'101001,101003', '400-622-8200'  , 'other_mkt','',''),
('5ff0543e-2d65-420b-a6cc-d40c119733bf' , 'bb6f4291-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '徐州八方网络科技有限公司','http://www.admin5.com', 0 , '', '15150000521' ,'102001,102002,102003,102004', '13305205345'   , 'other_mkt','',''),
('641bc961-8807-43c4-92a1-e432ca6719e4' , 'bb6f42a9-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '北京新网互联软件服务有限公司', 'www.dns.com.cn', 0 , '', '18610456002' ,'101004', '95105612' , 'other_mkt' ,'',''),
('a513f315-b746-485a-9fc6-e4192ff10b94' , 'bb6f42f7-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '北京新网数码信息技术有限公司','www.xinnet.com', 0 , '', '13811858192' ,'101004', '400 818 2233'  , 'other_mkt','',''),
('b3799622-5f1f-4506-ba85-83bf6cfa0565' , 'bb6f4308-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '上海美橙科技信息发展有限公司','www.cndns.com' , 0 , '', '18017288801' ,'101001', '400-892-9900'  , 'other_mkt' ,'',''),
('cab62e18-816a-11e6-b605-f80f41f769c2' , 'bb6f4316-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '北京云梦网络科技有限公司',  'www.clouddream.net' , 0 , '', '18600769651' ,'101001', '400-6655-312'  , 'other_mkt' ,'',''),
('ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae' , 'bb6f4323-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '百度云测试服务商', 'cloud.baidu.com'    , 0 , '', '13000000000' ,'101001,101002,101003', '13000000000', 'other_mkt' ,'',''),
('f6781f00-7e8b-493a-ac43-75eb369927e7' , 'bb6f4331-12be-11e7-b635-6c0b8483288e' , 'HOSTED', '常州市青之峰网络科技有限公司','http://www.jsmyqingfeng.cn/' , 0 , '', '18637363300' ,'101001', '0519-68020394' ,'other_mkt','','');

UPDATE bce_cloudmarket_iac.mkt_vendor_info SET address = '', service_category = '101001' WHERE
vendor_id = 'f6781f00-7e8b-493a-ac43-75eb369927e7';

UPDATE bce_cloudmarket_iac.mkt_vendor_info  SET bce_user_id = 'b1f91fbe6fe54d2eaf70ef0025f1c3c2' WHERE  vendor_id = 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae';

INSERT INTO mkt_role (role, description, create_time) VALUES ('OP', '运营', NOW()),('VENDOR','服务商',
 NOW());
INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('b1f91fbe6fe54d2eaf70ef0025f1c3c2', 'BCE', 'VENDOR', 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae'
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

# 服务商商品相关权限数据
INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('VENDOR', 'vendorProduct', 'readList', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'readNewDraft', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'readDetail', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'readForEdit', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'saveDraft', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'submitAudit', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'productToOnline', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'productToOffline', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'cancelAudit', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'getUploadInfo', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'readList', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'readDetail', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'productToOnline', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'productToOffline', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'getUploadInfo', 'ALLOW', NOW());

INSERT INTO mkt_permission (resource, operation, description, create_time) VALUES
('vendorProduct', 'readList', '获取商品列表', NOW()),
('vendorProduct', 'readNewDraft', '读取草稿', NOW()),
('vendorProduct', 'readDetail', '读取商品详情', NOW()),
('vendorProduct', 'readForEdit', '读取商品编辑详情', NOW()),
('vendorProduct', 'saveDraft', '保存草稿', NOW()),
('vendorProduct', 'submitAudit', '提交审核', NOW()),
('vendorProduct', 'productToOnline', '商品上架', NOW()),
('vendorProduct', 'productToOffline', '商品下架', NOW()),
('vendorProduct', 'cancelAudit', '商品撤销审核', NOW()),
('vendorProduct', 'getUploadInfo', '上传文件', NOW());

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('INIT_VENDOR', 'vendorProduct', 'statCount', 'ALLOW', NOW()),
('VENDOR', 'vendorProduct', 'statCount', 'ALLOW', NOW()),
('OP', 'vendorProduct', 'statCount', 'ALLOW', NOW()),
('OP', 'order', 'statCount', 'ALLOW', NOW());

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('INIT_VENDOR', 'vendorShop', 'edit', 'ALLOW', NOW()),
('VENDOR', 'vendorShop', 'edit', 'ALLOW', NOW());

INSERT INTO mkt_resource_system (resource, system) VALUES ('vendorProduct', 'MKT');


# 服务商店铺信息的导入
#vendor 上海英方软件股份有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('11cb92aa-03be-49f3-b746-e0c0a1617502', 'PASS', '',
 '{\"companyName\":\"上海英方软件股份有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"英方是一家专注于提供容灾及业务高可用解决方案的专业技术厂商。公司以保证企业业务连续性为首要目标，以实时数据复制、持续数据保护技术，为各类企业在物理/虚拟/云计算平台的关键业务数据和应用，提供连续保护极快速恢复，彻底解决传统数据保护方式的各类难题，最大限度的减少各类系统因停机所带来的巨大损失\",baiduWalletAccount\":\"\",\"serviceEmail\":\"zhaoc@info2soft.com\",\"servicePhone\":\"18664388667\",\"serviceAvailTime\":\"周一到周五9:00-18:00\",\"baiduQiaos\":[{\"name\":\"客服咨询\",\"link\":\"http://p.qiao.baidu.com//im/index?siteid=8152259&ucid=19123718\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('11cb92aa-03be-49f3-b746-e0c0a1617502', '上海英方软件股份有限公司',
'zhaoc@info2soft.com','18664388667',
'英方是一家专注于提供容灾及业务高可用解决方案的专业技术厂商。公司以保证企业业务连续性为首要目标，以实时数据复制、持续数据保护技术，为各类企业在物理/虚拟/云计算平台的关键业务数据和应用，提供连续保护极快速恢复，彻底解决传统数据保护方式的各类难题，最大限度的减少各类系统因停机所带来的巨大损失',
'{\"serviceAvailTime\":\"周一到周五9:00-18:00\",\"onlineSupports\":[{\"name\":\"客服咨询\",\"link\":\"http://p.qiao.baidu.com//im/index?siteid=8152259&ucid=19123718\"}]}');

#vendor 广东耐思尼克信息技术有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('17b74c0f-f140-406f-a165-10643baf2c03', 'PASS', '',
 '{\"companyName\":\"广东耐思尼克信息技术有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"广东耐思尼克信息技术有限公司，国内十强知名的互联网应用服务提供商。公司自2006年成立以来，一直专注于中国E网络体系建设，致力于现代网络科技研发推广，为企业和个人提供专业的域名、网站建设、虚拟主机、服务器、企业邮局、网站建设、网站推广等服务\",baiduWalletAccount\":\"\",\"serviceEmail\":\"admin@nicenic.net\",\"servicePhone\":\"15819444425\",\"serviceAvailTime\":\"周一到周五9:00-18:00\",\"baiduQiaos\":[{\"name\":\"综合咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9932347&userId=3837672\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('17b74c0f-f140-406f-a165-10643baf2c03', '广东耐思尼克信息技术有限公司',
'admin@nicenic.net','15819444425',
'广东耐思尼克信息技术有限公司，国内十强知名的互联网应用服务提供商。公司自2006年成立以来，一直专注于中国E网络体系建设，致力于现代网络科技研发推广，为企业和个人提供专业的域名、网站建设、虚拟主机、服务器、企业邮局、网站建设、网站推广等服务',
'{\"serviceAvailTime\":\"7*24小时\",\"onlineSupports\":[{\"name\":\"综合咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9932347&userId=3837672\"},{\"name\":\"建站咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9932356&userId=3837672\"}]}');

#vendor 徐州八方网络科技有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('5ff0543e-2d65-420b-a6cc-d40c119733bf', 'PASS', '',
 '{\"companyName\":\"徐州八方网络科技有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"A5站长网成立于2005年，现已成为国内最大的站长信息服务及交易平台。我们关注并投入云计算，提供运维、工具、镜像、开发等服务，让使用云计算更简单便捷。\",baiduWalletAccount\":\"\",\"serviceEmail\":\"baidubce@admin5.com\",\"servicePhone\":\"15150000521\",\"serviceAvailTime\":\"每周一至周日早9：00-晚18：00\",\"baiduQiaos\":[{\"name\":\"运维服务01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9831602&userId=10149296\"},{\"name\":\"运维服务02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9831657&userId=10149296\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('5ff0543e-2d65-420b-a6cc-d40c119733bf', '徐州八方网络科技有限公司',
'baidubce@admin5.com','15150000521',
'A5站长网成立于2005年，现已成为国内最大的站长信息服务及交易平台。我们关注并投入云计算，提供运维、工具、镜像、开发等服务，让使用云计算更简单便捷。',
'{\"serviceAvailTime\":\"每周一至周日早9：00-晚18：00\",\"onlineSupports\":[{\"name\":\"运维服务01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9831602&userId=10149296\"},{\"name\":\"运维服务02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9831657&userId=10149296\"}]}');

#vendor 北京新网互联软件服务有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('641bc961-8807-43c4-92a1-e432ca6719e4', 'PASS', '',
 '{\"companyName\":\"北京新网互联软件服务有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"北京新网互联软件服务有限公司专注基础互联网应用服务，是目前中国地区深受用户好评的企业互联网服务提供商，已经为数十万家企业和个人用户提供域名注册、虚拟主机、云主机和企业邮箱等互联网应用解决方案。\",baiduWalletAccount\":\"\",\"serviceEmail\":\"email@dns.com.cn\",\"servicePhone\":\"18610456002\",\"serviceAvailTime\":\"09：00~18：00（周一至周五）\",\"baiduQiaos\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=10222604&userId=22762270\"},{\"name\":\"售前咨询02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=10224033&userId=22885235\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('641bc961-8807-43c4-92a1-e432ca6719e4', '北京新网互联软件服务有限公司',
'email@dns.com.cn','18610456002',
'北京新网互联软件服务有限公司专注基础互联网应用服务，是目前中国地区深受用户好评的企业互联网服务提供商，已经为数十万家企业和个人用户提供域名注册、虚拟主机、云主机和企业邮箱等互联网应用解决方案。',
'{\"serviceAvailTime\":\"09：00~18：00（周一至周五）\",\"onlineSupports\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=10222604&userId=22762270\"},{\"name\":\"售前咨询02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=10224033&userId=22885235\"}]}');

#vendor 北京新网数码信息技术有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('a513f315-b746-485a-9fc6-e4192ff10b94', 'PASS', '',
 '{\"companyName\":\"北京新网数码信息技术有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"专注企业邮箱19年，在全球部署34个海外转发节点，国内全网覆盖，全终端适应全面支持多样化移动办公，已为超过50万家企事业单位用户提供高效稳定专业的邮箱服务，在全国拥有14家分支机构为客户提供优异的本地化服务，全国统一售后热线：400 818 2233\",baiduWalletAccount\":\"\",\"serviceEmail\":\"bdservice@xinnet.com\",\"servicePhone\":\"13811858192\",\"serviceAvailTime\":\"09：00~18：00（周一至周五）\",\"baiduQiaos\":[{\"name\":\"售前01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859090&userId=22212773\"},{\"name\":\"售前02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859765&userId=22212773\"},{\"name\":\"售前03\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859782&userId=22212773\"},{\"name\":\"售前04\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859785&userId=22212773\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('a513f315-b746-485a-9fc6-e4192ff10b94', '北京新网数码信息技术有限公司',
'bdservice@xinnet.com','13811858192',
'专注企业邮箱19年，在全球部署34个海外转发节点，国内全网覆盖，全终端适应全面支持多样化移动办公，已为超过50万家企事业单位用户提供高效稳定专业的邮箱服务，在全国拥有14家分支机构为客户提供优异的本地化服务，全国统一售后热线：400 818 2233',
'{\"serviceAvailTime\":\"09：00~18：00（周一至周五）\",\"onlineSupports\":[{\"name\":\"售前01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859090&userId=22212773\"},{\"name\":\"售前02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859765&userId=22212773\"},{\"name\":\"售前03\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859782&userId=22212773\"},{\"name\":\"售前04\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9859785&userId=22212773\"}]}');


#vendor 上海美橙科技信息发展有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('b3799622-5f1f-4506-ba85-83bf6cfa0565', 'PASS', '',
 '{\"companyName\":\"上海美橙科技信息发展有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"美橙互联于2006年成立，上海为运营总部，并相继在北京、广州、深圳、香港、杭州、成都成立服务中心；目前已自主开发多项互联网应用实例，如多线DNS系统、CDN、建站之星智能建站系统、企业邮局、橙云主机等。凭借专业的技术和售后队伍，美橙互联不断优化提升服务水平，斥资建立的专业级400全国统一呼叫中心，提供7X24小时，全年365天不间断快速响应服务。\",baiduWalletAccount\":\"\",\"serviceEmail\":\"service@cndns.com\",\"servicePhone\":\"18017288801\",\"serviceAvailTime\":\"9:00-18:00\",\"baiduQiaos\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9846450&userId=22204980\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('b3799622-5f1f-4506-ba85-83bf6cfa0565', '上海美橙科技信息发展有限公司',
'service@cndns.com','18017288801',
'美橙互联于2006年成立，上海为运营总部，并相继在北京、广州、深圳、香港、杭州、成都成立服务中心；目前已自主开发多项互联网应用实例，如多线DNS系统、CDN、建站之星智能建站系统、企业邮局、橙云主机等。凭借专业的技术和售后队伍，美橙互联不断优化提升服务水平，斥资建立的专业级400全国统一呼叫中心，提供7X24小时，全年365天不间断快速响应服务。',
'{\"serviceAvailTime\":\"9:00-18:00\",\"onlineSupports\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9846450&userId=22204980\"}]}');

#vendor 北京云梦网络科技有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('cab62e18-816a-11e6-b605-f80f41f769c2', 'PASS', '',
 '{\"companyName\":\"北京云梦网络科技有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"北京云梦网络科技有限公司，成立于2013年8月，致力于通过云计算和智能化的技术平台为企业客户提供标准化的网站建设服务。让企业更好地应用互联网，是云梦网络的使命，迄今为止已为国内超过100,000家企业提供官网建设服务 \",baiduWalletAccount\":\"\",\"serviceEmail\":\"order@clouddream.net\",\"servicePhone\":\"18600769651\",\"serviceAvailTime\":\"每周一至周五早9：00-晚18：00\",\"baiduQiaos\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9823518&userId=22110132\"},{\"name\":\"售前咨询02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9827657&userId=22128963\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('cab62e18-816a-11e6-b605-f80f41f769c2', '北京云梦网络科技有限公司',
'order@clouddream.net','18600769651',
'北京云梦网络科技有限公司，成立于2013年8月，致力于通过云计算和智能化的技术平台为企业客户提供标准化的网站建设服务。让企业更好地应用互联网，是云梦网络的使命，迄今为止已为国内超过100,000家企业提供官网建设服务 ',
'{\"serviceAvailTime\":\"每周一至周五早9：00-晚18：00\",\"onlineSupports\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9823518&userId=22110132\"},{\"name\":\"售前咨询02\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9827657&userId=22128963\"}]}');

#vendor 测试
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae', 'PASS', '',
 '{\"companyName\":\"百度云测试服务商\",\"bceAccount\":\"\",\"companyDescription\":\"百度云市场测试服务商，提供测试商品\",baiduWalletAccount\":\"\",\"serviceEmail\":\"bch-eco-rd@baidu.com\",\"servicePhone\":\"13000000000\",\"serviceAvailTime\":\"每周一至周五早9：00-晚18：00\",\"baiduQiaos\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9823518&userId=22110132\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae', '百度云测试服务商',
'bch-eco-rd@baidu.com','13000000000',
'百度云市场测试服务商，提供测试商品',
'{\"serviceAvailTime\":\"每周一至周五早9：00-晚18：00\",\"onlineSupports\":[{\"name\":\"售前咨询01\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9823518&userId=22110132\"}]}');

#vendor 常州市青之峰网络科技有限公司
INSERT INTO mkt_vendor_shop_draft (vendor_id, status, audit_id, content)
VALUES ('f6781f00-7e8b-493a-ac43-75eb369927e7', 'PASS', '',
 '{\"companyName\":\"常州市青之峰网络科技有限公司\",\"bceAccount\":\"\",\"companyDescription\":\"青峰公司成立于2002年9月10日，是国内早先以网站建设和推广为主营业务的高科技公司，专业从事网站制作、网络营销策划、软件开发。总部设在江苏常州，旗下目前设有河南、浙江、江苏有9家分公司。公司自主研发的智美云网站建设系统，获得了高新技术产品认证，目前有3万多家企业、政府机构、事业单位使用。\",baiduWalletAccount\":\"\",\"serviceEmail\":\"bce@myqingfeng.cn\",\"servicePhone\":\"18637363300\",\"serviceAvailTime\":\"08：30~17：30（周一至周五）\",\"baiduQiaos\":[{\"name\":\"售前咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9830716&userId=22165696\"},{\"name\":\"售前咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9830597&userId=22165467\"}]}');

INSERT INTO mkt_vendor_shop (vendor_id, name,email,cellphone,intro,service_info)
VALUES ('f6781f00-7e8b-493a-ac43-75eb369927e7', '常州市青之峰网络科技有限公司',
'bce@myqingfeng.cn','18637363300',
'青峰公司成立于2002年9月10日，是国内早先以网站建设和推广为主营业务的高科技公司，专业从事网站制作、网络营销策划、软件开发。总部设在江苏常州，旗下目前设有河南、浙江、江苏有9家分公司。公司自主研发的智美云网站建设系统，获得了高新技术产品认证，目前有3万多家企业、政府机构、事业单位使用。',
'{\"serviceAvailTime\":\"08：30~17：30（周一至周五）\",\"onlineSupports\":[{\"name\":\"售前咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9830716&userId=22165696\"},{\"name\":\"售前咨询\",\"link\":\"http://p.qiao.baidu.com/cps/chat?siteId=9830597&userId=22165467\"}]}');

update mkt_vendor_info set create_time = '2017-03-24 09:29:34' where vendor_id='11cb92aa-03be-49f3-b746-e0c0a1617502';
update mkt_vendor_info set create_time = '2016-10-11 16:45:01' where vendor_id='17b74c0f-f140-406f-a165-10643baf2c03';
update mkt_vendor_info set create_time = '2016-10-11 16:45:01' where vendor_id='5ff0543e-2d65-420b-a6cc-d40c119733bf';
update mkt_vendor_info set create_time = '2016-12-14 11:41:50' where vendor_id='641bc961-8807-43c4-92a1-e432ca6719e4';
update mkt_vendor_info set create_time = '2016-10-11 16:45:01' where vendor_id='a513f315-b746-485a-9fc6-e4192ff10b94';
update mkt_vendor_info set create_time = '2017-01-03 16:17:45' where vendor_id='b3799622-5f1f-4506-ba85-83bf6cfa0565';
update mkt_vendor_info set create_time = '2016-10-11 16:45:01' where vendor_id='cab62e18-816a-11e6-b605-f80f41f769c2';
update mkt_vendor_info set create_time = '2016-12-26 19:59:38' where vendor_id='ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae';
update mkt_vendor_info set create_time = '2016-10-11 16:45:01' where vendor_id='f6781f00-7e8b-493a-ac43-75eb369927e7';

# 添加服务账号 和权限
INSERT INTO mkt_role (role, description, create_time) VALUES ('SERVICE', '服务账户', NOW());
INSERT INTO mkt_account (account_id, account_type, role, vendor_id ) VALUES
('53bc63587f2642e4897ac3305873a956', 'BCE', 'SERVICE', '');
INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('SERVICE', 'vendorInfo', 'readListByIds', 'ALLOW', NOW());

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('OP', 'vendorOverview', 'read', 'ALLOW', NOW());
INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('OP', 'vendorShop', 'read', 'ALLOW', NOW());

update mkt_vendor_info set bce_user_id = '623c570048d24211a2e7eafb9ef0f1c4' where vendor_id='11cb92aa-03be-49f3-b746-e0c0a1617502';
update mkt_vendor_info set bce_user_id = 'fed1b9c0ec7649a386565517636d2890' where vendor_id='17b74c0f-f140-406f-a165-10643baf2c03';
update mkt_vendor_info set bce_user_id = '1e8a99184f0a4c2bbd6673f63002ab60' where vendor_id='5ff0543e-2d65-420b-a6cc-d40c119733bf';
update mkt_vendor_info set bce_user_id = 'c058360dba6742c1b67db5af7fa5333a' where vendor_id='641bc961-8807-43c4-92a1-e432ca6719e4';
update mkt_vendor_info set bce_user_id = '93ee94f3a37a4c33aa368de35c6f0e1f' where vendor_id='a513f315-b746-485a-9fc6-e4192ff10b94';
update mkt_vendor_info set bce_user_id = '7d39f0a00c1a458d851f11390e0c7d80' where vendor_id='b3799622-5f1f-4506-ba85-83bf6cfa0565';
update mkt_vendor_info set bce_user_id = '0abcc27b8a8342c6ab39b2cb3dd1d4f7' where vendor_id='f6781f00-7e8b-493a-ac43-75eb369927e7';

INSERT INTO  mkt_role_permission (role, resource, operation, action, create_time) VALUES
('SERVICE', 'vendorInfo', 'read', 'ALLOW', NOW());


