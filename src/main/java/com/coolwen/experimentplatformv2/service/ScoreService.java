package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.DTO.PScoreDto;

import java.util.List;

public interface ScoreService {
    List<PScoreDto> listScorerDTOBystudentId(int stuId, int mid);
}
