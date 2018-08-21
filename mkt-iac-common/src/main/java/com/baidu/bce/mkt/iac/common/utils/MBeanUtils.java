/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import com.baidu.bce.plat.webframework.exception.BceException;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.ReflectUtils;

/**
 * Created by chenxiang05@baidu.com on 2018/8/16.
 */
@Slf4j
public class MBeanUtils {


    public static <T>void applyProperties(T prototype, T modifies, Class<T> type) {
        PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(type);
        PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(type);
        Map<String, PropertyDescriptor> setterMap = new HashMap<>();
        for (PropertyDescriptor setter : setters) {
            setterMap.put(setter.getName(), setter);
        }
        for (PropertyDescriptor getter : getters) {
            try {
                Object propertyVal = getter.getReadMethod().invoke(modifies);
                if (propertyVal != null && setterMap.containsKey(getter.getName())) {
                    setterMap.get(getter.getName()).getWriteMethod().invoke(prototype, propertyVal);
                }
            } catch (Exception e)  {
                log.error("{}",e);
                throw new BceException("更新失败");
            }

        }
    }



    public static void applyProperties(Object target, Object source) {
        PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source.getClass());
        PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(target.getClass());
        Map<String, PropertyDescriptor> setterMap = new HashMap<>();
        for (PropertyDescriptor setter : setters) {
            setterMap.put(setter.getName(), setter);
        }
        for (PropertyDescriptor getter : getters) {
            try {
                Object propertyVal = getter.getReadMethod().invoke(source);
                if (propertyVal != null && setterMap.containsKey(getter.getName())) {
                    setterMap.get(getter.getName()).getWriteMethod().invoke(target, propertyVal);
                }
            } catch (Exception e)  {
                log.error("{}",e);
                throw new BceException("更新失败" );
            }
        }
    }



}
