// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac;

import com.baidu.bce.internalsdk.core.BceClient;
import com.baidu.bce.internalsdk.core.BceInternalRequest;
import com.baidu.bce.internalsdk.core.Entity;
import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;

import endpoint.EndpointManager;

/**
 * mkt iac client
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktIacClient extends BceClient {
    private static String TYPE_APPLICATION = "APPLICATION";
    private static String TYPE_VENDOR_SHOP = "VENDOR_SHOP";

    public MktIacClient(String accessKey, String secretKey) {
        super(EndpointManager.getEndpoint("MKT-IAC"), accessKey, secretKey);
    }

    public MktIacClient(String endpoint, String accessKey, String secretKey) {
        super(endpoint, accessKey, secretKey);
    }

    private BceInternalRequest createMktRequest() {
        return createAuthorizedRequest();
    }

    public void auditResultNotice(String type, String id, String status, AuditNoticeRequest request) {

        if (TYPE_APPLICATION.equals(type)) {
            createMktRequest()
                    .path("/v1/notice/audit")
                    .queryParam("type", "application")
                    .queryParam("id", id)
                    .queryParam("status", status)
                    .post(Entity.json(request));
        }
        if (TYPE_VENDOR_SHOP.equals(type)) {
            createMktRequest()
                    .path("/v1/notice/audit")
                    .queryParam("type", "vendorShop")
                    .queryParam("id", id)
                    .queryParam("status", status)
                    .put();
        }
    }

    public void saveVendorShopDraft( ShopDraftSaveRequest request) {
        createMktRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .post(Entity.json(request));
    }

    public void submitVendorShopDraft( ShopDraftSaveRequest request) {
        createMktRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .keyOnlyQueryParam("submit")
                .post(Entity.json(request));
    }

    public ShopDraftDetailResponse getShopDraftDetail() {
        return createMktRequest()
                       .path("/v1/vendor")
                       .path("/shopDraft")
                       .get(ShopDraftDetailResponse.class);
    }

    public VendorInfoDetailResponse getVendorInfoDetail() {
        return createMktRequest()
                       .path("/v1/vendor")
                       .path("/vendorInfo").get(VendorInfoDetailResponse.class);
    }

    public void submitContractsAndDeposit(String vendorId,
                                          ContractAndDepositSubmitRequest request) {
        createMktRequest()
                .path("/v1/vendor/")
                .path(vendorId)
                .path("/contractAndDeposit")
                .post(Entity.json(request));
    }

    public VendorOverviewResponse getVendorOverview() {
        return createMktRequest()
                       .path("/v1/vendor")
                       .path("/overview")
                       .get(VendorOverviewResponse.class);
    }

    public void updateVendorStatus(String vendorId, String status) {
        createMktRequest().path("/v1/vendor/")
                .path(vendorId)
                .keyOnlyQueryParam("status")
                .queryParam("status", status)
                .put();
    }

    public VendorBaseContactResponse getVendorBaseContactByBceId(String bceUserId) {
        return createMktRequest()
                       .path("/v1/vendor/")
                       .path(bceUserId)
                       .path("/baseContact")
                       .queryParam("type", "BCE_ID")
                       .get(VendorBaseContactResponse.class);
    }

    public VendorContractResponse getVendorContractToOss(String vendorId) {
        return createMktRequest()
                       .path("/v1/vendor/")
                       .path(vendorId)
                       .path("/contractToOss")
                       .get(VendorContractResponse.class);
    }
}
