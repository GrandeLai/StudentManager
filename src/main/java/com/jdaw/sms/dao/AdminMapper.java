package com.jdaw.sms.dao;

import com.jdaw.sms.bean.Admin;
import com.jdaw.sms.bean.LoginForm;

import java.util.List;

/**
 * @project: sms
 * @description: 数据访问层-操控管理员信息
 * @version: 1.0
 */
public interface AdminMapper {

    Admin login(LoginForm loginForm);

    Admin findByName(String name);

    int insert(Admin admin);

    List<Admin> selectList(Admin admin);

    int update(Admin admin);

    int updatePassword(Admin admin);

    int deleteById(Integer[] ids);

}