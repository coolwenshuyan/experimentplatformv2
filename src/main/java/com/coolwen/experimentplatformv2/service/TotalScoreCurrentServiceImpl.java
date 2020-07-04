package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.TotalScoreCurrentRepository;
import com.coolwen.experimentplatformv2.model.DTO.ModuleGradesDto;
import com.coolwen.experimentplatformv2.model.TotalScoreCurrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Artell
 * @version 2020/5/12 18:12
 */
@Service
public class TotalScoreCurrentServiceImpl implements TotalScoreCurrentService {

    @Autowired
    private TotalScoreCurrentRepository totalScoreCurrentRepository;

    @Override
    public void add(TotalScoreCurrent res) {
        totalScoreCurrentRepository.save(res);
    }

    @Override
    public void update(TotalScoreCurrent res) {
        totalScoreCurrentRepository.save(res);
    }

    @Override
    public void delete(int id) {
        totalScoreCurrentRepository.deleteById(id);
    }

    @Override
    public TotalScoreCurrent load(int id) {
        return totalScoreCurrentRepository.findById(id).get();
    }

    @Override
    public List<TotalScoreCurrent> listTotalScoreCurrent() {
        return totalScoreCurrentRepository.findAll();
    }

    @Override
    public List<TotalScoreCurrent> findeAllBystuid(int id) {
        return totalScoreCurrentRepository.findeAllBystuid(id);
    }

    @Override
    public List<ModuleGradesDto> ModuleGrade(int id) {
        return totalScoreCurrentRepository.ModuleGrade(id);
    }

    @Override
    public TotalScoreCurrent findTotalScoreCurrentByStuID(int stuId) {
        return totalScoreCurrentRepository.findTotalScoreCurrentByStuID(stuId);
    }

    @Override
    public TotalScoreCurrent findTotalScoreCurrentByStuId(int stuid) {
        return totalScoreCurrentRepository.findTotalScoreCurrentByStuId(stuid);
    }

    @Override
    public void deleteTotalScoreCurrentByStuId(int id) {
        TotalScoreCurrent totalScoreCurrent = totalScoreCurrentRepository.findTotalScoreCurrentByStuId(id);
        totalScoreCurrentRepository.delete(totalScoreCurrent);
    }

    @Override
    public List<TotalScoreCurrent> findAll() {
        return totalScoreCurrentRepository.findAll();
    }

}
