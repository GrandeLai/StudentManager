package com.jdaw.sms.service.impl;

import com.jdaw.sms.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jdaw.sms.bean.Clazz;
import com.jdaw.sms.dao.ClazzMapper;

import java.util.List;

/**
 * @project: sms
 * @description: 业务层实现类-操控班级信息
 */
@Service
@Transactional
public class ClazzServiceImpl implements ClazzService {

    //注入Mapper接口对象
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public List<Clazz> selectList(Clazz clazz) { return clazzMapper.selectList(clazz); }

    @Override
    public List<Clazz> selectAll() {
        return clazzMapper.selectAll();
    }

    @Override
    public Clazz findByName(String name) {
        return clazzMapper.findByName(name);
    }

    @Override
    public int insert(Clazz clazz) {
        return clazzMapper.insert(clazz);
    }

    @Override
    public int update(Clazz clazz) {
        return clazzMapper.update(clazz);
    }

    @Override
    public int deleteById(Integer[] ids) {
        return clazzMapper.deleteById(ids);
    }
}
