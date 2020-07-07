package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.Effect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EffectRepository extends BaseRepository<Effect,Integer>, JpaSpecificationExecutor<Effect> {

    @Query(value="select * from t_effect where id = ?",nativeQuery=true)
    Effect findeffectById(int id);

    @Query("select e from Effect e,CourseInfo c where e.course_id = c.id and c.teacherId = ?1")
    Page<Effect> findAllByUid(int id, Pageable pageable);
}
