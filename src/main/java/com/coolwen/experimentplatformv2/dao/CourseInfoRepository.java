package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.CourseInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseInfoRepository extends BaseRepository<CourseInfo, Integer>, JpaSpecificationExecutor<CourseInfo> {

}
