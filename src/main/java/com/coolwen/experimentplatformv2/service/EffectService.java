package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.Effect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EffectService {

    void add(Effect effect);

    Effect findById(int id);

    void delete(int id);

    Page<Effect> findAllByUid(int id, Pageable pageable);

    Page<Effect> findAllByCourseId(Integer pageNum, int courseId);
}
