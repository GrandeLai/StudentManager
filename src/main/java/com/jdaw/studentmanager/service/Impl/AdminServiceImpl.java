package com.jdaw.studentmanager.service.Impl;

import com.jdaw.studentmanager.domain.Admin;
import com.jdaw.studentmanager.mapper.AdminMapper;
import com.jdaw.studentmanager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname UserServiceImpl
 * @Description None
 * @Date 2023/11/25 11:08
 * @Created by jdaw
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByAdmin(Admin admin) {
        return adminMapper.findByAdmin(admin);
    }

    @Override
    public int editPswdByAdmin(Admin admin) {
        return adminMapper.editPswdByAdmin(admin);
    }

}
