// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac;

import java.util.Arrays;
import java.util.List;

import com.baidu.bce.internalsdk.core.BceInternalRequest;
import com.baidu.bce.internalsdk.core.Entity;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorListRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAiVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.GetShowMenuResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.IdResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorAmountResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoBriefResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorListResponseV2;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorPayeeResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorPayeeSyncRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorPhoneAndEmailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopResponse;
import com.baidu.bce.mkt.framework.sdk.BaseClient;
import com.baidu.bce.mkt.framework.sdk.utils.RequestUtils;

import endpoint.EndpointManager;

/**
 * mkt iac client
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktIacClient extends BaseClient {
    private static String TYPE_APPLICATION = "APPLICATION";
    private static String TYPE_VENDOR_SHOP = "VENDOR_SHOP";

    public MktIacClient(String accessKey, String secretKey) {
        super(EndpointManager.getEndpoint("MKT_IAC"), accessKey, secretKey);
    }

    public MktIacClient(String endpoint, String accessKey, String secretKey) {
        super(endpoint, accessKey, secretKey);
    }

    public void auditResultNotice(String type, String vendorId, String status,
                                  AuditNoticeRequest request) {
        BceInternalRequest bceInternalRequest = createMktAuthorizedRequest()
                .path("/v1/notice/audit");
        RequestUtils.safeAddQueryParam(bceInternalRequest, "vendorId", vendorId);
        RequestUtils.safeAddQueryParam(bceInternalRequest, "status", status);

        if (TYPE_APPLICATION.equals(type)) {
            bceInternalRequest.queryParam("type", "application").post(Entity.json(request));
        }
        if (TYPE_VENDOR_SHOP.equals(type)) {
            bceInternalRequest.queryParam("type", "vendorShop").put();
        }
    }

    public void saveVendorShopDraft(ShopDraftSaveRequest request) {
        createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .post(Entity.json(request));
    }

    public void submitVendorShopDraft(ShopDraftSaveRequest request) {
        createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .keyOnlyQueryParam("submit")
                .post(Entity.json(request));
    }

    public ShopDraftDetailResponse getShopDraftDetail() {
        return createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .get(ShopDraftDetailResponse.class);
    }

    public void cancelShopDraftAudit() {
        createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .keyOnlyQueryParam("cancelAudit")
                .put();
    }

    public void editShopDraft() {
        createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/shopDraft")
                .keyOnlyQueryParam("edit")
                .put();
    }

    public VendorInfoDetailResponse getVendorInfoDetail() {
        return createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/vendorInfo").get(VendorInfoDetailResponse.class);
    }

    public VendorInfoDetailResponse getVendorInfoDetail(String vendorId) {
        BceInternalRequest request = createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/vendorInfo");
        addHeaderTargetVendor(request, Arrays.asList(vendorId));
        return request.get(VendorInfoDetailResponse.class);
    }

    public VendorInfoDetailResponse getVendorInfoByUserId(String userId) {
        BceInternalRequest request = createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/vendorInfo");
        RequestUtils.safeAddQueryParam(request, "bceUserId", userId);

        return request.get(VendorInfoDetailResponse.class);
    }

    public void submitContractsAndDeposit(String vendorId,
                                          ContractAndDepositSubmitRequest request) {
        createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path(vendorId)
                .path("/contractAndDeposit")
                .post(Entity.json(request));
    }

    public ContractAndDepositResponse getContractsAndDeposit(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path(vendorId)
                .path("/contractAndDeposit")
                .get(ContractAndDepositResponse.class);
    }

    public VendorOverviewResponse getVendorOverview() {
        return createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/overview")
                .get(VendorOverviewResponse.class);
    }

    public VendorOverviewResponse getVendorOverview(String targetVendorId) {
        BceInternalRequest request = createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/overview");
        addHeaderTargetVendor(request, Arrays.asList(targetVendorId));
        return request.get(VendorOverviewResponse.class);
    }

    public VendorAmountResponse getVendorAmountStatistics() {
        return createMktAuthorizedRequest()
                .path("/v1/vendor")
                .path("/amountStatistics")
                .get(VendorAmountResponse.class);

    }

    // 添加ai服务商合同协议
    public void addAiVendorContract(ContractRequest request) {
        createMktAuthorizedRequest()
                .path("/v1/ai/vendor/contract")
                .post(Entity.json(request));
    }

    // 回显ai服务商合同协议
    public AiVendorContractResponse getAiVendorContract(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/ai/vendor/")
                .path(vendorId)
                .path("/contract")
                .get(AiVendorContractResponse.class);
    }

    // 查看ai服务商是否存在合同协议信息
    public ContractAiVendorIdListResponse getContractAiVendorIds(List<String> vendorIds) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/ai/vendor")
                .path("/contract");
        addHeaderTargetVendor(request, vendorIds);
        return request.get(ContractAiVendorIdListResponse.class);
    }

    // ai服务商管理信息列表页
    public AiVendorListResponse getAiVendorInfoList(AiVendorListRequest request) {
        BceInternalRequest bceInternalRequest = createMktAuthorizedRequest()
                .path("/v1/ai/vendor/api_vendor_list")
                .queryParam("keyword", request.getKeyword())
                .queryParam("order", request.getOrder())
                .queryParam("orderBy", request.getOrderBy())
                .queryParam("pageNo", request.getPageNo())
                .queryParam("pageSize", request.getPageSize());
        return bceInternalRequest.get(AiVendorListResponse.class);
    }

    public AiVendorListResponse getAiVendorList(String company, int pageNo, int pageSize) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/ai/vendor").path("/list")
                .queryParam("pageNo", pageNo)
                .queryParam("pageSize", pageSize);
        RequestUtils.safeAddQueryParam(request, "companyName", company);
        return request.get(AiVendorListResponse.class);
    }

    public VendorListResponse getVendorList(String company, String bceUserId, int pageNo,
                                            int pageSize) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/vendor").path("/list")
                .queryParam("pageNo", pageNo)
                .queryParam("pageSize", pageSize);
        RequestUtils.safeAddQueryParam(request, "companyName", company);
        RequestUtils.safeAddQueryParam(request, "bceUserId", bceUserId);
        return request.get(VendorListResponse.class);
    }

    public VendorListResponseV2 getVendorListV2(String company, String bceUserId, String business, int pageNo,
                                                int pageSize) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/vendor").path("/api_vendor_list")
                                             .queryParam("pageNo", pageNo)
                                             .queryParam("pageSize", pageSize);
        RequestUtils.safeAddQueryParam(request, "business", business);
        RequestUtils.safeAddQueryParam(request, "companyName", company);
        RequestUtils.safeAddQueryParam(request, "bceUserId", bceUserId);
        return request.get(VendorListResponseV2.class);
    }

    public VendorListResponse getSearchVendor(String company) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/vendor")
                .path("/searchList");
        RequestUtils.safeAddQueryParam(request, "companyName", company);
        return request.get(VendorListResponse.class);
    }

    public VendorListResponse getVendorListByIds(List<String> vendorIds) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/vendor")
                .path("/listByIds");
        addHeaderTargetVendor(request, vendorIds);
        return request.get(VendorListResponse.class);
    }

    public AiVendorListResponse getAiVendorListByIds(List<String> vendorIds) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/ai/vendor")
                .path("/listByIds");
        request.queryParam("vendorIds", vendorIds);
        return request.get(AiVendorListResponse.class);
    }

    public void updateVendorStatus(String vendorId, String status) {
        createMktAuthorizedRequest().path("/v1/vendor/")
                .path(vendorId)
                .queryParam("status", status)
                .put();
    }

    public VendorBaseContactResponse getVendorBaseContactByBceId(String bceUserId) {
        return createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path(bceUserId)
                .path("/baseContact")
                .queryParam("type", "BCE_ID")
                .get(VendorBaseContactResponse.class);
    }

    public VendorContractResponse getVendorContract(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path(vendorId)
                .path("/contract")
                .get(VendorContractResponse.class);
    }

    public void addVendorContract(ContractRequest request) {
        createMktAuthorizedRequest()
                .path("/v1/vendor/contract")
                .post(Entity.json(request));
    }

    public ContractVendorIdListResponse getContractVendorIds(List<String> vendorIds) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/vendor")
                .path("/contract");
        addHeaderTargetVendor(request, vendorIds);
        return request.get(ContractVendorIdListResponse.class);
    }

    public VendorShopResponse getVendorShopResponse(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path(vendorId)
                .path("/shopInfo").get(VendorShopResponse.class);
    }

    public VendorShopInfoDetailResponse getVendorShopOnlineInfo(String vendorId) {
        BceInternalRequest request = createMktAuthorizedRequest().path("/v1/vendor/shopInfo");
        addHeaderTargetVendor(request, Arrays.asList(vendorId));
        return request.get(VendorShopInfoDetailResponse.class);
    }

    public void signAgreement() {
        createMktAuthorizedRequest().path("/v1/vendor/agreement").put();
    }

    public GetShowMenuResponse getAccountShowMenu(String accountId) {
        return createMktAuthorizedRequest()
                .path("/v1/account")
                .path("/menu/")
                .path(accountId).get(GetShowMenuResponse.class);
    }

    public VendorPhoneAndEmailResponse getPhoneAndEmail(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path(vendorId)
                .path("/phoneAndEmail")
                .get(VendorPhoneAndEmailResponse.class);
    }

    public void syncVendorPayee(VendorPayeeSyncRequest request) {
        createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path("payee")
                .keyOnlyQueryParam("sync")
                .post(Entity.json(request));
    }

    public void doInvalidVendorPayee(String vendorId) {
        createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path("payee/")
                .path(vendorId)
                .keyOnlyQueryParam("invalid")
                .post();
    }

    public VendorPayeeResponse getVendorPayee(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path("payee/")
                .path(vendorId)
                .get(VendorPayeeResponse.class);
    }

    public void updateVendorTaxFlag(String vendorId, String taxFlag) {

        VendorPayeeSyncRequest request = new VendorPayeeSyncRequest();
        request.setVendorId(vendorId);
        request.setTaxFlag(taxFlag);
        createMktAuthorizedRequest()
                .path("/v1/vendor/")
                .path("payee/")
                .keyOnlyQueryParam("taxFlag")
                .put(Entity.json(request));
    }

    public IdResponse addAiVendorInfo(AiVendorInfoRequest request) {
        return createMktAuthorizedRequest()
                .path("/v1/ai/vendor")
                .post(Entity.json(request), IdResponse.class);
    }

    public AiVendorInfoResponse getAiVendorInfo(String vendorId) {
        return createMktAuthorizedRequest()
                .path("/v1/ai/vendor/")
                .path(vendorId)
                .get(AiVendorInfoResponse.class);
    }

    public void updateAiVendorInfo(AiVendorInfoRequest request) {
        createAuthorizedRequest()
                .path("/v1/ai/vendor/")
                .put(Entity.json(request));
    }

    public VendorInfoBriefResponse getVendorInfoBrief(String vendorId) {
        return createAuthorizedRequest()
                .path("/v1/vendor/vendorInfoBrief/")
                .path(vendorId)
                .get(VendorInfoBriefResponse.class);
    }
}
