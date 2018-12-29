// Copyright 2018 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model;

import com.baidu.bce.mkt.framework.utils.OrderByUtils;
import com.baidu.bce.mkt.framework.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * ai vendor list filter
 *
 * @author Created by v_zhouhuikun@baidu.com on 2018/12/24.
 */
@Data
public class AiVendorListFilter {
    private String bceUserId;
    private String companyName;
    private String order;
    private String orderBy;
    private int start;
    private int limit;

    public static final String DEFAULT_ORDER_BY = "create_time";
    public static final String DESC = "desc";

    public AiVendorListFilter(String bceUserId, String keyword) {
        if (StringUtils.isNotBlank(bceUserId)) {
            this.bceUserId = bceUserId;
        }
        if (StringUtils.isNotBlank(keyword)) {
            this.companyName = "%" + SecurityUtils.stripSqlAndXss(keyword) + "%";
        }
    }

    public AiVendorListFilter(String keyword, String order, String orderBy, int pageNo, int pageSize) {
        if (StringUtils.isNotBlank(keyword)) {
            this.companyName = "%" + SecurityUtils.stripSqlAndXss(keyword) + "%";
        }
        this.start = pageNo < 1 ? 0 : (pageNo - 1) * pageSize;
        this.limit = pageSize <= 0 ? 10 : pageSize;

        if (StringUtils.isNotBlank(order)) {
            OrderByUtils.checkOrder(order);
            this.order = order.toUpperCase();
        }

        this.order = StringUtils.isBlank(order) ? DESC : order;
        this.orderBy = DEFAULT_ORDER_BY;
    }
}
