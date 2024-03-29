package com.jdaw.studentmanager.mapper;

import com.jdaw.studentmanager.domain.Course;

import java.util.List;
import java.util.Map;

/**
 * @Classname CourseMapper
 * @Description None
 * @Date 2023/11/29 20:34
 * @Created by jdaw
 */
public interface CourseMapper {
    List<Course> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addCourse(Course course);

    int editCourse(Course course);

    int deleteCourse(List<Integer> ids);

    int addStudentNum(Integer courseId);

    void deleteStudentNum(Integer courseId);

    List<Course> getCourseById(List<Integer> ids);

    int findByName(String name);
}
