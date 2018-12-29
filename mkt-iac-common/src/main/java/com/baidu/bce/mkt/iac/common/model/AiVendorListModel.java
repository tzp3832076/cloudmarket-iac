/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiVendorListModel {
    private List<AiVendorInfo> aiVendorInfo;
    private int totalCount;
}
