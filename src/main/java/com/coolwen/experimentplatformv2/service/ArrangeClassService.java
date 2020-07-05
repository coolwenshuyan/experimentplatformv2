package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.ArrangeClass;

public interface ArrangeClassService {

    void add(ArrangeClass effect);

    ArrangeClass findById(int id);

    void delete(int id);
}
