package com.jdaw.sms.service;

import com.jdaw.sms.bean.Grade;

import java.util.List;

/**
 * @project: sms
 * @description: 业务层-操控年级信息
 */
public interface GradeService {

    List<Grade> selectList(Grade gradename);

    List<Grade> selectAll();

    Grade findByName(String gradename);

    int insert(Grade grade);

    int update(Grade grade);

    int deleteById(Integer[] ids);
}
