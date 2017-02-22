// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.mkt.iac.common.mapper.ResourceSystemMapper;
import com.baidu.bce.mkt.iac.common.model.db.ResourceSystem;

import lombok.extern.slf4j.Slf4j;

/**
 * resource system cache
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Component
@Slf4j
public class ResourceSystemCache implements InitializingBean {
    @Autowired
    private ResourceSystemMapper resourceSystemMapper;

    private volatile Map<String, String> resourceSystemMap;

    public String getSystem(String resource) {
        return resourceSystemMap.get(resource);
    }

    public void doRefresh() {
        List<ResourceSystem> resourceSystemList = resourceSystemMapper.getAll();
        if (CollectionUtils.isEmpty(resourceSystemList)) {
            String msg = "no resource system found";
            log.error(msg);
            throw new IllegalStateException(msg);
        } else {
            Map<String, String> map = new HashMap<>();
            for (ResourceSystem resourceSystem : resourceSystemList) {
                map.put(resourceSystem.getResource(), resourceSystem.getSystem());
            }
            this.resourceSystemMap = map;
            log.info("resource system map refreshed");
        }
    }

    /**
     * 10min定时更新一次
     */
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void schedulerRefresh() {
        try {
            doRefresh();
        } catch (Exception e) {
            log.error("refresh failed", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        doRefresh();
    }
}
