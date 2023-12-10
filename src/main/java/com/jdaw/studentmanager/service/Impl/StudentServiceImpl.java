package com.jdaw.studentmanager.service.Impl;

import com.jdaw.studentmanager.domain.Student;
import com.jdaw.studentmanager.mapper.StudenetMapper;
import com.jdaw.studentmanager.service.StudentService;
import com.jdaw.studentmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname StudentServiceImpl
 * @Description None
 * @Date 2023/11/27 14:12
 * @Created by jdaw
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudenetMapper studenetMapper;

    @Override
    public PageBean<Student> queryPage(Map<String, Object> paramMap) {
        PageBean<Student> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Student> datas = studenetMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = studenetMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int deleteStudent(List<Integer> ids) {
        return studenetMapper.deleteStudent(ids);
    }

    @Override
    public int addStudent(Student student) {
        return studenetMapper.addStudent(student);
    }

    @Override
    public Student findById(Integer sid) {
        return studenetMapper.findById(sid);
    }

    @Override
    public int editStudent(Student student) {
        return studenetMapper.editStudent(student);
    }

    @Override
    public Student findByStudent(Student student) {
        return studenetMapper.findByStudent(student);
    }

    @Override
    public boolean isStudentByClazzId(Integer id) {
        List<Student> studentList = studenetMapper.isStudentByClazzId(id);
        if (studentList.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int editPswdByStudent(Student student) {
        return studenetMapper.editPswdByStudent(student);
    }

    @Override
    public int findByName(String name) {
        return studenetMapper.findByName(name);
    }
}
