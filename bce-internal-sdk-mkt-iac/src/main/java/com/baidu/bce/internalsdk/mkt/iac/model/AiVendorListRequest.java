package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-25.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AiVendorListRequest {

    private String type;
    private String keyword;
    private int pageNo;
    private int pageSize;
    private String order;
    private String orderBy;
}
