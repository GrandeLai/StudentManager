package com.jdaw.studentmanager.service.Impl;

import com.jdaw.studentmanager.domain.Attendance;
import com.jdaw.studentmanager.mapper.AttendanceMapper;
import com.jdaw.studentmanager.service.AttendanceService;
import com.jdaw.studentmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname AttendanceServiceImpl
 * @Description None
 * @Date 2019/7/1 15:47
 * @Created by jdaw
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public PageBean<Attendance> queryPage(Map<String, Object> paramMap) {
        PageBean<Attendance> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Attendance> datas = attendanceMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = attendanceMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public boolean isAttendance(Attendance attendance) {
        Attendance at = attendanceMapper.isAttendance(attendance);
        if(at != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int addAtendance(Attendance attendance) {
        return attendanceMapper.addAtendance(attendance);
    }

    @Override
    public int deleteAttendance(Integer id) {
        return attendanceMapper.deleteAttendance(id);
    }
}
