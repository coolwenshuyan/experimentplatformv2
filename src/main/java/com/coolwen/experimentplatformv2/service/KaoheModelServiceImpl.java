package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.*;
import com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO;
import com.coolwen.experimentplatformv2.model.DTO.KaoHeModuleInfo;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import com.coolwen.experimentplatformv2.model.ModuleTestQuest;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("kaoheModelService")
public class KaoheModelServiceImpl implements KaoheModelService {

    @Autowired
    private KaoheModelRepository kaoheModelRepository;
    @Autowired
    ExpModelRepository expModelRepository;

    @Autowired
    KaoHeModelScoreRepository kaoHeModelScoreRepository;

    @Autowired
    TotalScoreCurrentRepository totalScoreCurrentRepository;

    @Autowired
    ModuleTestQuestRepository moduleTestQuestRepository;

    @Autowired
    ModuleTestAnswerStuRepository moduleTestAnswerStuRepository;


    @Override
    public void add(KaoheModel kaoheModel) {
        kaoheModelRepository.save(kaoheModel);

    }

    @Override
    public void update(KaoheModel kaoheModel) {
        kaoheModelRepository.save(kaoheModel);

    }

    @Override
    public void delete(int id) {
        kaoheModelRepository.deleteById(id);
    }

    @Override
    public List<KaoheModel> listKaoheModel() {
        return kaoheModelRepository.findAll();
    }

    @Override
    public KaoheModel findById(int id) {
        return kaoheModelRepository.findbyid(id);
    }

    @Override
    public Page<KaoheModel> findAll(Pageable pageable) {
        return kaoheModelRepository.findAll(pageable);
    }

//    @Override
//    public boolean isItInKaohe(int mid) {
//        int countIn=kaoheModelRepository.countAllByM_id(mid);
//        if (countIn>0){
//            return true;
//        }else {
//            return false;
//        }
//    }

    @Override
    public List<Integer> inKaoheList(int arrangeId) {
        return kaoheModelRepository.findAllMid(arrangeId);
    }

    @Override
    public long countKaoheModel() {
        return kaoheModelRepository.count();
    }

    @Override
    public List<KaoheModel> findAll() {
        return kaoheModelRepository.findAll();
    }

    @Override
    public Integer findKaoheNum() {
        return kaoheModelRepository.findKaoheNum();
    }


    @Override
    public Page<KaoHeModelStuDTO> findKaoheModelStuDto(int stu_id, int pageNum,int arrangeID) {
        PageRequest pageRequest = PageRequest.of(pageNum, 6);
        return kaoHeModelScoreRepository.findKaoHeModelStuDTOByStuId(stu_id,arrangeID, pageRequest);
    }

    @Override
    public KaoHeModelStuDTO findKaoHeModelStuDTOByStuId(int stu_id, int mid) {
        return kaoHeModelScoreRepository.findKaoHeModelStuDTOByStuId(stu_id, mid);
    }

    @Override
    public KaoheModel findKaoheModelByMid(int mid) {
        return kaoheModelRepository.findKaoheModelByMid(mid);
    }

    @Override
    public void deleteKaoHeModuleByMid(KaoheModel kaoheModel) {
        kaoheModelRepository.delete(kaoheModel);
    }

//    @Override
//    public void deleteByMid(int mid) {
//
//        KaoheModel km = kaoheModelRepository.findKaoheModelByMid(mid);
//        List<KaoHeModelScore> khms = kaoHeModelScoreRepository.findKaoHeModelScoreByKaoheid(km.getId());
//        // 移除模块时，删除表12中该模块学生成绩记录，更新13模块数量
//        for (KaoHeModelScore i : khms) {
//            TotalScoreCurrent tsc = totalScoreCurrentRepository.findTotalScoreCurrentByStuId(i.getStuId());
//            tsc.setKaoheNum(tsc.getKaoheNum() - 1);
//            totalScoreCurrentRepository.save(tsc);
//            kaoHeModelScoreRepository.delete(i);
//        }


//        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+mid);
//        KaoheModel km = kaoheModelRepository.findKaoheModelByMid(mid);
//        logger.debug("km000000000"+km);
//
//        // 整体理论测试占比和模块考核成绩占比存放在-1的记录中(!!已经废除!!)
////        KaoheModel akm = kaoheModelRepository.findKaoheModelByMid(-1);
////        logger.debug("akm000000000"+akm);
//
//        List<KaoHeModelScore> khms = kaoHeModelScoreRepository.findKaoHeModelScoreByKaoheid(km.getId());
//
//        logger.debug("khms000000000"+khms);
//
//        // 遍历该模块所有学生的模块成绩,进行修改
//        for (KaoHeModelScore i : khms){
//            logger.debug(">>>>>>>>>"+i);
//            TotalScoreCurrent tsc = totalScoreCurrentRepository.findTotalScoreCurrentByStuId(i.getStuId());
//            logger.debug(">>>>>>>>>"+tsc);
//            tsc.setKaoheNum(tsc.getKaoheNum()-1);
//            float msc = i.getmScore();
//            float mtsc = tsc.getmTotalScore();
//            logger.debug("mtsc>>>>>>>>>>>>>>>>>>>>>>>>>>"+mtsc);
//            float newmtsc = mtsc - msc * km.getM_scale();
//            logger.debug("newmtsc>>>>>>>>>>>>>>>>>>>>>>>>>>"+newmtsc);
//            tsc.setmTotalScore(newmtsc);
//
//            float old_total_score = tsc.getTotalScore();
//            float new_total_score = old_total_score - newmtsc*km.getKaohe_baifenbi();
//            tsc.setmTotalScore(newmtsc);
//            tsc.setTotalScore(new_total_score);
//            totalScoreCurrentRepository.save(tsc);
//
//            kaoHeModelScoreRepository.delete(i);
//        }


//    }

    @Override
    public void updateAllGreatestWeight(float kaoheBaifenbi, float testBaifenbi) {
        kaoheModelRepository.updateAllGreatestWeight(kaoheBaifenbi, testBaifenbi);
    }

    @Override
    public void deleteMTestAnswerByMid(int mid) {
        List<ModuleTestQuest> moduleTestQuests = moduleTestQuestRepository.findAllByMid(mid);

        for (ModuleTestQuest i : moduleTestQuests) {
//            logger.debug("ModuleTestQuest>>>>>>>>>>>>>>"+i.getQuestId());
            moduleTestAnswerStuRepository.deleteAllByQuest_id(i.getQuestId());
        }

    }

    @Override
    public KaoheModelAndExpInfoDTO findKaoheModelAndExpInfoDTOByKaoheid(int kaoheid) {
        return kaoheModelRepository.findKaoheModelAndExpInfoDTOByKaoheid(kaoheid);
    }

    @Override
    public Page<KaoheModelAndExpInfoDTO> findAllKaoheModelAndExpInfoDTO(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return kaoheModelRepository.findAllKaoheModelAndExpInfoDTO(pageable);
    }

    @Override
    public Page<KaoheModelAndExpInfoDTO> findByArrange_idIn(int pageNum, List<Integer> ids) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return kaoheModelRepository.findByArrange_idIn(pageable, ids);
    }

    @Override
    public List<Integer> findKaoheModelByArrangeId(int arrangeId) {
        return kaoheModelRepository.findKaoheModelByArrangeId(arrangeId);
    }

    @Override
    public Page<KaoheModelAndExpInfoDTO> findAllKaoheModelAndExpInfoDTOByArrangeId(int arrangeId, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        return kaoheModelRepository.findAllKaoheModelAndExpInfoDTOByArrangeId(arrangeId, pageable);
    }

    @Override
    public List<KaoheModel> findKaoheModelByArrangeId2(int arrageid) {
        return kaoheModelRepository.findKaoheModelByArrangeId2(arrageid);
    }

    @Override
    public void updateAllGreatestWeight(float kaoheBaifenbi, float testBaifenbi, int arrageId) {
        kaoheModelRepository.updateAllGreatestWeight(kaoheBaifenbi, testBaifenbi, arrageId);
    }

    @Override
    public List<KaoheModel> findAllByArrageId(int arrageId) {
        return kaoheModelRepository.findAll(
                new SimpleSpecificationBuilder("arrange_id", "=", arrageId).generateSpecification()
        );
    }

    @Override
    public List<KaoheModel> findByArrange_idIn(List<Integer> ids) {
        return kaoheModelRepository.findByArrange_idIn(ids);
    }

    @Override
    public void deleteByArrangeId(int id) {
        kaoheModelRepository.deleteByArrangeId(id);
    }

    @Override
    public List<KaoheModel> findKaoHeModelByArrangeidAndMid(int arrangeId, Integer mid) {
        return kaoheModelRepository.findKaoHeModelByArrangeidAndMid(arrangeId, mid);
    }

    @Override
    public List<KaoHeModuleInfo> findKaoheModelByArrange_id(int arrangeid) {
        return kaoheModelRepository.findKaoheModelByArrange_id(arrangeid);
    }

    @Override
    public KaoheModel findKaoheModelByIsKaohe(int mid, int courseid,int sid) {
        return kaoheModelRepository.findKaoheModelByIsKaohe(mid, courseid,sid);
    }


}
