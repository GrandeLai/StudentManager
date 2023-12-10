package com.jdaw.studentmanager.service;

import com.jdaw.studentmanager.domain.SelectedCourse;
import com.jdaw.studentmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname SelectedCourseService
 * @Description None
 * @Date 2023/11/30 10:48
 * @Created by jdaw
 */
public interface SelectedCourseService {
    PageBean<SelectedCourse> queryPage(Map<String, Object> paramMap);

    int addSelectedCourse(SelectedCourse selectedCourse);

    int deleteSelectedCourse(Integer id);

    boolean isStudentId(int id);

    List<SelectedCourse> getAllBySid(int studentid);
}
