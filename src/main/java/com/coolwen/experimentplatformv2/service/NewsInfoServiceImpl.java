package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.NewsInfoRepository;
import com.coolwen.experimentplatformv2.model.NewsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsInfoServiceImpl implements NewsInfoService {

    @Autowired
    NewsInfoRepository newsInfoRepository;

    @Override
    public void add(NewsInfo newsInfo) {
        newsInfoRepository.save(newsInfo);
    }

    @Override
    public NewsInfo findById(int id) {
        return newsInfoRepository.findByInfo(id);
    }

    @Override
    public void delete(int id) {
        newsInfoRepository.deleteById(id);
    }

    @Override
    public int findAllmodelpeople() {
        return newsInfoRepository.findAllmodelpeople()+newsInfoRepository.findAllmodelpeople1();
    }

    @Override
    public int findAllPass() {
        return newsInfoRepository.findAllPass()+newsInfoRepository.findAllPass1();
    }

    @Override
    public int findAllpasspeople() {
        return newsInfoRepository.findAllmodelpeople1();
    }

    @Override
    public int findExcellentpeople() {
        return newsInfoRepository.findExcellentpeople();
    }

    @Override
    public int findQualifiedpeople() {
        return newsInfoRepository.findQualifiedpeople();
    }

    @Override
    public int findUnqualifiedpeople() {
        return newsInfoRepository.findUnqualifiedpeople();
    }

}
