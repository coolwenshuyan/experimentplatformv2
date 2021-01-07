package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.LearningTime;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Artell
 * @version 2020/11/5 2:57
 */


public interface LearningDao extends BaseRepository<LearningTime, Integer>, JpaSpecificationExecutor<LearningTime> {
    LearningTime findBySid(int sid);
}
