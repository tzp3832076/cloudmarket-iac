alter table mkt_vendor_info modify column service_category VARCHAR(512) NOT NULL COMMENT '服务类别用，分割';

ALTER TABLE mkt_role ADD sub_roles VARCHAR(128) NOT NULL DEFAULT '' COMMENT '当前角色包含的子角色，只能包含一层子角色' AFTER description;