package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.EffectRepository;
import com.coolwen.experimentplatformv2.model.Effect;
import com.coolwen.experimentplatformv2.model.Teacher;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EffectServiceImpl implements EffectService {

    @Autowired
    EffectRepository effectRepository;

    @Value("${SimplePageBuilder.pageSize}")
    int size;

    @Override
    public void add(Effect effect) {
        effectRepository.save(effect);
    }

    @Override
    public Effect findById(int id) {
        Effect effect = new Effect();
        effect = effectRepository.findeffectById(id);
        return effect;
    }

    @Override
    public void delete(int id) {
        effectRepository.deleteById(id);
    }

    @Override
    public Page<Effect> findAllByUid(int id, Pageable pageable) {
        return effectRepository.findAllByUid(id,pageable);
    }

    @Override
    public Page<Effect> findAllByCourseId(Integer pageNum, int courseId) {
        Pageable pager = PageRequest.of(pageNum, size);
        Page<Effect> effectPage= effectRepository.findAll(new SimpleSpecificationBuilder<Teacher>(
                "course_id", "=", courseId)
                .generateSpecification(), pager);
        return effectPage;
    }

    @Override
    public List<Effect> findByCourseId(int id) {
        return effectRepository.findByCourseId(id);
    }
}
