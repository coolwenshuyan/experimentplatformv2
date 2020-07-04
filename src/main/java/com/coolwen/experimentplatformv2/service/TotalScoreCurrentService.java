package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.DTO.ModuleGradesDto;
import com.coolwen.experimentplatformv2.model.TotalScoreCurrent;

import java.util.List;

/**
 * @author Artell
 * @date 2020/5/12 18:04
 */
public interface TotalScoreCurrentService {
    public void add(TotalScoreCurrent res);

    public void update(TotalScoreCurrent res);

    public void delete(int id);

    public TotalScoreCurrent load(int id);

    public List<TotalScoreCurrent> listTotalScoreCurrent();

    public List<TotalScoreCurrent> findeAllBystuid(int id);

    List<ModuleGradesDto> ModuleGrade(int id);

    TotalScoreCurrent findTotalScoreCurrentByStuID(int stuId);

    TotalScoreCurrent findTotalScoreCurrentByStuId(int stuid);

    void deleteTotalScoreCurrentByStuId(int id);

    List<TotalScoreCurrent> findAll();
}
