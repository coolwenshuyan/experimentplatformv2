package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.NewsInfo;

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
}
