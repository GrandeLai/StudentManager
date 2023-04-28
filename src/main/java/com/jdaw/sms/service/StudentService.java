package com.jdaw.sms.service;

import com.jdaw.sms.bean.LoginForm;
import com.jdaw.sms.bean.Student;

import java.util.List;

/**
 * @project: sms
 * @description: 业务层-操控学生信息
 */
public interface StudentService {

    Student login(LoginForm loginForm);

    List<Student> selectList(Student student);

    Student fingBySno(Student student);

    int insert(Student student);

    int update(Student student);

    int updatePassowrd(Student student);

    int deleteById(Integer[] ids);

}

