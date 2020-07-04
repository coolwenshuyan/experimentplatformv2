package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.SetInfo;

public interface SetInfoService {


    void add(SetInfo setInfo);

    SetInfo findById(int i);

    String findexpimg(int id);
}
