package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.NewsInfoRepository;
import com.coolwen.experimentplatformv2.model.NewsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public int findAllpasspeopleByCourseId(int id) {
        return newsInfoRepository.findAllpasspeopleByCourseId(id);
    }

    @Override
    public int findExcellentpeopleByCourseId(int id) {
        return newsInfoRepository.findExcellentpeopleByCourseId(id);
    }

    @Override
    public int findQualifiedpeopleByCourseId(int id) {
        return newsInfoRepository.findQualifiedpeopleByCourseId(id);
    }

    @Override
    public int findUnqualifiedpeopleByCourseId(int id) {
        return newsInfoRepository.findUnqualifiedpeopleByCourseId(id);
    }

    @Override
    public List findScoreRanking() {
        return newsInfoRepository.findScoreRanking();
    }

    @Override
    public List findClassScoreRanking(int courseId) {
        return newsInfoRepository.findClassScoreRanking(courseId);
    }

}
