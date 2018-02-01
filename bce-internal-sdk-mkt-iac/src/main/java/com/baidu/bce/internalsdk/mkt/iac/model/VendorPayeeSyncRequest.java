/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorPayeeSyncRequest {
    private String vendorId;
    private Location companyLocation;
    private String bankName;
    private String branchBankName;
    private String bankCardNumber;
    private Location bankLocation;

    @Data
    public static class Location {
        private String province;
        private String city;

        public Location(String province, String city) {
            this.province = province;
            this.city = city;
        }

        public Location() {

        }
    }
}
