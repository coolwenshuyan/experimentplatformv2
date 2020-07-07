package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.DTO.KaoheModuleProgressDTO;
import com.coolwen.experimentplatformv2.model.ExpModel;
import com.coolwen.experimentplatformv2.model.ModuleTestQuest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ExpModelService {
    public void save(ExpModel expModel);

    public ExpModel findExpModelByID(int id);

    public void deleteExpModelById(int id);

    public Page<ExpModel> findModelList(int pageNum);

    public List<ExpModel> findExpModelsBym_name(String m_name);

    public List<ExpModel> findAll();

    public Page<ExpModel> finExpAll(int pageNum);

    List<ModuleTestQuest> findModuleTestQuestByMId(int mid);

    void deleteModuleTestAnswerStuByQuestId(int questid);

    int findByMid(int m_id);

    ExpModel findExpModelsByKaoheMid(int mid);

    Page<KaoheModuleProgressDTO> findExpModels(int course_id, int class_id, int m_id,int pageNum);



}
