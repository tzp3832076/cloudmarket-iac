package com.baidu.bce.internalsdk.mkt.iac.model;

import com.baidu.bce.internalsdk.core.BceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-26.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiVendorContractResponse {
    private String vendorName;
    private List<AiContractInfo> contractInfoList;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AiContractInfo {
        private String contractNum;
        private String customerNum;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
        private Timestamp contractBeginTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BceConstant.DATETIME_FORMAT, timezone = "UTC")
        private Timestamp contractEndTime;
    }
}
