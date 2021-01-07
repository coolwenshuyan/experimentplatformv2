package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.NewsInfo;

import java.util.List;

public interface NewsInfoService {
    void add(NewsInfo newsInfo);

    NewsInfo findById(int id);

    void delete(int id);

    int findAllmodelpeople();

    int findAllPass();

    int findAllpasspeople();

    int findExcellentpeople();

    int findQualifiedpeople();

    int findUnqualifiedpeople();

    int findAllpasspeopleByCourseId(int id);

    int findExcellentpeopleByCourseId(int id);

    int findQualifiedpeopleByCourseId(int id);

    int findUnqualifiedpeopleByCourseId(int id);

    List findScoreRanking();

    List findClassScoreRanking(int courseId);
}
