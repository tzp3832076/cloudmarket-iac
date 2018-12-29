package com.baidu.bce.internalsdk.mkt.iac.model;

import com.baidu.bce.internalsdk.core.BceConstant;
import com.baidu.bce.mkt.framework.sdk.utils.PageResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-24.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiVendorListResponse extends PageResult<AiVendorListResponse.AiVendorInfoListModel> {

    private List<AiVendorInfoListModel> aiVendorInfoListModel;
    private int totalCount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AiVendorInfoListModel {
        private String vendorId;
        private String companyName;
        private String bceAccount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
        private Timestamp auditTime;
        private String area;
        private String status;
        boolean contracted;
    }

}
