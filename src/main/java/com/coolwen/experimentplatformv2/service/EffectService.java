package com.coolwen.experimentplatformv2.service;


import com.coolwen.experimentplatformv2.model.Effect;


public interface EffectService {

    void add(Effect effect);

    Effect findById(int id);

    void delete(int id);
}
