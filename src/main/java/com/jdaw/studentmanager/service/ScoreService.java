package com.jdaw.studentmanager.service;

import com.jdaw.studentmanager.domain.Score;
import com.jdaw.studentmanager.domain.ScoreStats;
import com.jdaw.studentmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname ScoreService
 * @Description None
 * @Date 2019/7/3 11:45
 * @Created by jdaw
 */
public interface ScoreService {
    PageBean<Score> queryPage(Map<String, Object> paramMap);

    boolean isScore(Score score);

    int addScore(Score score);

    int editScore(Score score);

    int deleteScore(Integer id);

    List<Score> getAll(Score score);

    ScoreStats getAvgStats(Integer courseid);
}
