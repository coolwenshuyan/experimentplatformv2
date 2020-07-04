package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.SetInfoRepository;
import com.coolwen.experimentplatformv2.model.SetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetInfoServiceImpl implements SetInfoService {
    @Autowired
    SetInfoRepository setInfoRepository;

    @Override
    public void add(SetInfo setInfo) {
        setInfoRepository.save(setInfo);
    }

    @Override
    public SetInfo findById(int i) {
        SetInfo setInfo = setInfoRepository.findById(i);
        return setInfo;
    }

    @Override
    public String findexpimg(int id) {
        return setInfoRepository.findexpimg(id);
    }

}
