package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ExpModelRepository;
import com.coolwen.experimentplatformv2.dao.ModuleTestAnswerStuRepository;
import com.coolwen.experimentplatformv2.dao.ModuleTestQuestRepository;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModuleProgressDTO;
import com.coolwen.experimentplatformv2.model.ExpModel;
import com.coolwen.experimentplatformv2.model.ModuleTestAnswerStu;
import com.coolwen.experimentplatformv2.model.ModuleTestQuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpModelServiceImpl implements ExpModelService {
    @Autowired
    ExpModelRepository expModelRepository;
    @Autowired
    ModuleTestQuestRepository moduleTestQuestRepository;
    @Autowired
    ModuleTestAnswerStuRepository moduleTestAnswerStuRepository;

    @Value("${SimplePageBuilder.pageSize}")
    private int pageSize;
    @Override
    public void save(ExpModel expModel) {
        expModelRepository.save(expModel);
    }

    @Override
    public ExpModel findExpModelByID(int id) {
        return expModelRepository.findById(id).get();
    }

    @Override
    public void deleteExpModelById(int id) {
        ExpModel expModel = findExpModelByID(id);
        expModelRepository.delete(expModel);
    }

    @Override
    public Page<ExpModel> findModelList(int pageNum) {
        Pageable pageable  = PageRequest.of(pageNum,pageSize);
        return expModelRepository.findAll(pageable);
    }

    @Override
    public List<ExpModel> findExpModelsBym_name(String m_name) {
        return expModelRepository.findExpModelsByM_name(m_name);
    }

    @Override
    public List<ExpModel> findAll() {
        return expModelRepository.findAll();
    }

    @Override
    public Page<ExpModel> finExpAll(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,6);
        return expModelRepository.findExpModels(pageable);
    }

    @Override
    public List<ModuleTestQuest> findModuleTestQuestByMId(int mid) {
        return moduleTestQuestRepository.findModuleTestQuestByMId(mid);
    }

    @Override
    public void deleteModuleTestAnswerStuByQuestId(int questid) {
        List<ModuleTestAnswerStu> list = moduleTestAnswerStuRepository.findModuleTestAnswerStuByQuest_id(questid);
        moduleTestAnswerStuRepository.deleteAll(list);
    }

    @Override
    public int findByMid(int m_id) {
        return expModelRepository.findByM_id(m_id);
    }

    @Override
    public ExpModel findExpModelsByKaoheMid(int mid) {
        return expModelRepository.findExpModelsByKaoheMid(mid);
    }

    @Override
    public Page<KaoheModuleProgressDTO> findExpModels(int course_id, int class_id, int m_id,int pageNum) {
        return expModelRepository.findExpModels(course_id,class_id,m_id,PageRequest.of(pageNum,pageSize));
    }

    @Override
    public Page<ExpModel> findOneCourseModelList(int courseId,Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum,100);
        return expModelRepository.findOneCourseModelList(courseId,pageable);
    }

    @Override
    public Page<ExpModel> findKaoheProgressMainByCourseId(int courseId, int PageNum) {
        return expModelRepository.findExpModelsByCourse_id(courseId,PageRequest.of(PageNum,6));
    }

    @Override
    public Page<ExpModel> findAllByTeacher(Integer pageNum,int id) {
        Pageable pageable = PageRequest.of(pageNum,10);
        return expModelRepository.findAllByTeacher(pageable,id);
    }

    @Override
    public List<ExpModel> findByCourseId(int id) {
        return expModelRepository.findByCourseId(id);
    }

    @Override
    public void deleteAll(List<ExpModel> expModels) {
        expModelRepository.deleteAll(expModels);
    }

    @Override
    public Page<ExpModel> findOneCourseModelList2(int courseId, int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum,size);
        return expModelRepository.findOneCourseModelList(courseId,pageable);
    }
}
