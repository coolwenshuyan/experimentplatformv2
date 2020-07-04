package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KaoheModelService {
    //添加
    public void add(KaoheModel kaoheModel);
    //修改
    public void update(KaoheModel kaoheModel);

    public void delete(int id);

    public List<KaoheModel> listKaoheModel();

    public KaoheModel findById(int id);

    Page<KaoheModel> findAll(Pageable pageable);

//    boolean isItInKaohe(int mid);

    public List<Integer> inKaoheList();

    public long countKaoheModel();

    List<KaoheModel> findAll();

    Integer findKaoheNum();

    Page<KaoHeModelStuDTO> findKaoheModelStuDto(int stu_id, int pageNum);

    KaoHeModelStuDTO findKaoHeModelStuDTOByStuId(int stu_id, int mid);


    KaoheModel findKaoheModelByMid(int mid);

    void deleteKaoHeModuleByMid(KaoheModel kaoheModel);

    public void deleteByMid(int mid);

    void updateAllGreatestWeight(float kaoheBaifenbi, float testBaifenbi);

    void deleteMTestAnswerByMid(int mid);

    KaoheModelAndExpInfoDTO findKaoheModelAndExpInfoDTOByKaoheid(int kaoheid);

    Page<KaoheModelAndExpInfoDTO> findAllKaoheModelAndExpInfoDTO(int pageNum);
}
