package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.ModuleTestAnswerStu;

import java.util.List;

/**
 * @author Artell
 * @version 2020/5/18 18:11
 */


public interface ModuleTestAnswerStuService {

    void add(ModuleTestAnswerStu mtastu);

    List<ModuleTestAnswerStu> findAllModuleTestAnswerStuByStuidAndQuestId(int Stuid, int QuestId);

    void deleteModuleTestAnswerStuByStuId(int id);

    ModuleTestAnswerStu findModuleTestAnswerStuByStu_idAndQuest_id(int stuid, int questid);

    void deleteByQuestId(int questid);


    List<ModuleTestAnswerStu> findStudentAnswbyStuidAndMid(int stuId, Integer mid);


    List<ModuleTestAnswerStu> findByQuest_id(int questid);

    void deleteByStuIdModelId(int m_id, int id);
}
