package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.TotalScorePassRepository;
import com.coolwen.experimentplatformv2.model.TotalScorePass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class TotalScorePassServiceImpl implements TotalScorePassService {
    protected static final Logger logger = LoggerFactory.getLogger(TotalScorePassServiceImpl.class);
    @Autowired
    TotalScorePassRepository totalScorePassRepository;
    @Override
    public void delteTotalScorePassByStuId(int id) {
        TotalScorePass totalScorePass = totalScorePassRepository.findTotalScorePassByStuId(id);
        if(totalScorePass != null){
            totalScorePassRepository.delete(totalScorePass);

        }
    }

    @Override
    public void save(TotalScorePass totalScorePass) {
        totalScorePassRepository.save(totalScorePass);
    }

    @Override
    public Page<TotalScorePass> findAll(int pageNum) {
        logger.debug("成功进入");
        Pageable pageable  = PageRequest.of(pageNum,10);

        return totalScorePassRepository.findAll(pageable);
    }


    @Override
    public Page<TotalScorePass> findAllByClassId(int classId) {
        return null;
    }

    @Override
    public List<TotalScorePass> findByStuId(int stuId) {
        return totalScorePassRepository.findByStuId(stuId);
    }
}
