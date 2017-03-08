INSERT INTO bce_cloudmarket_iac.mkt_vendor_info
(vendor_id, bce_user_id, status, company, website, capital,address,telephone,service_category,hotline, other_market, contact_info, wallet_id)
(select v.id, uuid(), ''HOSTED'', v.name, v.website, 0, "" as ''address'', v.telephone, '''' as ''service_category'', v.hotline, v.other_mkt, "" as ''contact_info'', "" as ''wallet_id'' from
bce_cloudmarket.mkt_vendor as v);

UPDATE bce_cloudmarket_iac.mkt_vendor_info  SET bce_user_id = 'b1f91fbe6fe54d2eaf70ef0025f1c3c2' WHERE  vendor_id = 'ec282cd2-3ca3-4ca0-8c5d-f1d7c8c0abae';