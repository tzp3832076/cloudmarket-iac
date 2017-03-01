// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac;

import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.baidu.bce.mkt.framework.iac.aop.AuthorizationAopConfiguration;
import com.baidu.bce.mkt.framework.iac.api.CheckInstanceOwnerController;
import com.baidu.bce.mkt.framework.iac.web.AuthorizationWebConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * import authorization
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class AuthorizationImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        boolean useAop = (boolean) importingClassMetadata
                .getAnnotationAttributes(EnableMktAuthorization.class.getName()).get("useAop");
        String mode = useAop ? "AOP" : "WEB";
        log.info("enable mkt authorization in mode = {}", mode);
        if (useAop) {
            return new String[]{AuthorizationAopConfiguration.class.getName(),
                    CheckInstanceOwnerController.class.getName()};
        } else {
            return new String[]{AuthorizationWebConfiguration.class.getName(),
                    CheckInstanceOwnerController.class.getName()};
        }
    }
}
