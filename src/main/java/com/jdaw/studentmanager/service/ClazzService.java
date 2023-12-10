package com.jdaw.studentmanager.service;

import com.jdaw.studentmanager.domain.Clazz;
import com.jdaw.studentmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname ClazzService
 * @Description None
 * @Date 2023/11/26 10:14
 * @Created by jdaw
 */
public interface ClazzService {
    PageBean<Clazz> queryPage(Map<String, Object> paramMap);

    int addClazz(Clazz clazz);

    int deleteClazz(List<Integer> ids);

    int editClazz(Clazz clazz);

    Clazz findByName(String clazzName);

}
