package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.KaoHeModelScoreRepository;
import com.coolwen.experimentplatformv2.dao.KaoheModelRepository;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Artell
 * @date 2020/5/12 18:04
 */
@Service
public class KaoHeModelScoreServiceImpl implements KaoHeModelScoreService {

    protected static final Logger logger = LoggerFactory.getLogger(KaoHeModelScoreServiceImpl.class);
    @Autowired
    private KaoHeModelScoreRepository kaoHeModelScoreRepository;

    @Autowired
    private KaoheModelRepository kaoheModelRepository;

    @Override
    public void add(KaoHeModelScore res) {
        kaoHeModelScoreRepository.save(res);
    }

    @Override
    public void update(KaoHeModelScore res) {
        kaoHeModelScoreRepository.save(res);
    }

    @Override
    public void delete(int id) {
        kaoHeModelScoreRepository.deleteById(id);
    }

    @Override
    public void deleteAllByKaohemId(Integer kaohemid) {
        kaoHeModelScoreRepository.deleteByTKaohemodleId(kaohemid);
        logger.debug("删除所有成绩记录成功!");
    }


    @Override
    public void deleteByStuId(int sid) {
        kaoHeModelScoreRepository.deleteById(sid);
    }

    @Override
    public KaoHeModelScore load(int id) {
        return kaoHeModelScoreRepository.findById(id).get();
    }

    @Override
    public List<KaoHeModelScore> listKaoHeModleScore() {
        return kaoHeModelScoreRepository.findAll();
    }

    @Override
    public KaoHeModelScore findKaoheModelScoreByMid(int mid, int stu) {
        Integer a = kaoheModelRepository.findByMid(mid);
        return kaoHeModelScoreRepository.findByStuIdAndTKaohemodleId(a, stu);
    }

    @Override
    public List<KaoHeModelScore> findKaoHeModelScoreByTKaohemodleIdAndStuId(int kaohemodeleid) {
        return kaoHeModelScoreRepository.findKaoHeModelScoreByTKaohemodleIdAndStuId(kaohemodeleid);
    }

    @Override
    public void deleteAllKaohe(List<KaoHeModelScore> kaoHeModelScores) {
        kaoHeModelScoreRepository.deleteAll(kaoHeModelScores);
    }

    @Override
    public void deleteKaoheModuleScoreByStuId(int id) {
        List<KaoHeModelScore> list = kaoHeModelScoreRepository.findKaoHeModelScoreByStuId(id);
        kaoHeModelScoreRepository.deleteAll(list);
    }

    @Override
    public KaoHeModelScore findKaoHeModelScoreByStuIdAndId(int stuid, int id) {
        return kaoHeModelScoreRepository.findKaoHeModelScoreByStuIdAndId(stuid, id);
    }

    @Override
    public List<KaoHeModelScore> findKaoheModuleScoreByStuId(int stuid) {
        return kaoHeModelScoreRepository.findKaoHeModelScoreByStuId(stuid);
    }

    @Override
    public Integer findmTestFalseByClassIdAndMid(int classId, int Mid) {
        return kaoHeModelScoreRepository.findmTestFalseByClassIdAndMid(classId,Mid);
    }

    @Override
    public Integer findmReportFalseByClassIdAndMid(int classId, int Mid) {
        return kaoHeModelScoreRepository.findmReportFalseByClassIdAndMid(classId, Mid);
    }
}
