package com.jdaw.studentmanager.service;

import com.jdaw.studentmanager.domain.Admin;

/**
 * @Classname AdminService
 * @Description None
 * @Date 2023/11/25 11:07
 * @Created by jdaw
 */
public interface AdminService {

    Admin findByAdmin(Admin admin);


    int editPswdByAdmin(Admin admin);
}
