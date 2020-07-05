package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ArrangeClassRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/4 23:44
 **/
@Service
public class ArrangeClassServiceImpl implements ArrangeClassService {

    @Autowired
    ArrangeClassRepository arrangeClassRepository;

    @Override
    public void add(ArrangeClass effect) {

    }

    @Override
    public ArrangeClass findById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Page<ArrangeClassDto> findByAll(Pageable pageable) {
        return arrangeClassRepository.findByAll(pageable);
    }
}
