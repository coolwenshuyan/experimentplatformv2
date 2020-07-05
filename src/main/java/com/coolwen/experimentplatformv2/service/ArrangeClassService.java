package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArrangeClassService {

    void add(ArrangeClass effect);

    ArrangeClass findById(int id);

    void delete(int id);

    Page<ArrangeClassDto> findByAll(Pageable pageable);
}
