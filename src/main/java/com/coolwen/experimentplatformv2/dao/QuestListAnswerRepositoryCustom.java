package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.model.DTO.QuestListAnswerAndStuScoreDto;
import com.coolwen.experimentplatformv2.model.DTO.QuestListAnswerDto;

import java.util.List;

/**
 * @author CoolWen
 * @version 2020-05-17 22:24
 */
public interface QuestListAnswerRepositoryCustom {

    /**
     * 查询问题dto
     *
     * @return
     */
    public List<QuestListAnswerDto> listQuestAnswerDto(String type, int mId);

    public List<QuestListAnswerAndStuScoreDto> listQuestListAnswerAndStuScoreDto(String type, int mId, int stuId);


}
