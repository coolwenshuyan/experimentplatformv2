package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArrangeClassRepository extends BaseRepository<ArrangeClass, Integer>, JpaSpecificationExecutor<ArrangeClass> {
}
