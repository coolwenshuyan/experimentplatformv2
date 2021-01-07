package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.CourseInfo;
import com.coolwen.experimentplatformv2.model.DTO.ExpModelDto;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModuleProgressDTO;
import com.coolwen.experimentplatformv2.model.ExpModel;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import com.coolwen.experimentplatformv2.model.ModuleTestQuest;
import org.springframework.data.domain.Page;

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


    Page<ExpModel> findOneCourseModelList(int courseId ,Integer pageNum);

    Page<ExpModel> findKaoheProgressMainByCourseId(int courseId,int PageNum);

    Page<ExpModel> findAllByTeacher(Integer pageNum, int id);

    List<ExpModel> findByCourseId(int id);

    void deleteAll(List<ExpModel> expModels);

    Page<ExpModel> findOneCourseModelList2(int courseId, int pageNum, int size);

    CourseInfo findCourseNameByMid(int mid);

    List<ExpModel> findExpModelByArrangeId(int arrangeId);

    Integer findOneCourseModelList2(int courseId,int m_id);

    Integer findOneCourseKaoHeModelList(int courseId,int m_id);

    List<ExpModel> findOneCourseModel(int id);

    List<ExpModelDto> findExpModelDtoByID(int courseId);

    int findKaoheNumByMid(int mid);

    KaoheModel findIsKaohe(int mid, int id);

    int findExpModelStuPassNum(String mName);

}
