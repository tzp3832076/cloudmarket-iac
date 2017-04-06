/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import java.util.List;

import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2017/3/13 by sunfangyuan@baidu.com .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorListModel {
    private List<VendorInfo> vendorInfoList;
    private int totalCount;
}
