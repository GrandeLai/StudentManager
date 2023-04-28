package com.jdaw.sms.service;

import com.jdaw.sms.bean.LoginForm;
import com.jdaw.sms.bean.Teacher;

import java.util.List;

/**
 * @project: sms
 * @description: 业务层-操控教师信息
 */
public interface TeacherService {

    Teacher login(LoginForm loginForm);

    List<Teacher> selectList(Teacher teacher);

    Teacher findByTno(Teacher teacher);

    int insert(Teacher teacher);

    int update(Teacher teacher);

    int deleteById(Integer[] ids);

    int updatePassowrd(Teacher teacher);

}
