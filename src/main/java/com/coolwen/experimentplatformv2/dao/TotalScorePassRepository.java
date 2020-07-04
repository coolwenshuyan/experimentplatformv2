package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.TotalScorePass;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TotalScorePassRepository extends BaseRepository<TotalScorePass,Integer> {
    @Query("select t from TotalScorePass t where t.stuId = ?1")
    TotalScorePass findTotalScorePassByStuId(int id);

    List<TotalScorePass> findAll();

    List<TotalScorePass> findByStuId(int stuId);

}
