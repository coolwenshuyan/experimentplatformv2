package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.DTO.KaoHeModuleInfo;
import com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO;
import com.coolwen.experimentplatformv2.model.KaoheModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KaoheModelRepository extends BaseRepository<KaoheModel, Integer>, JpaSpecificationExecutor<KaoheModel> {


    @Query("select u from KaoheModel u where u.id = ?1") //这里的User对应Model层
    public KaoheModel findbyid(int id);

//    @Query("select u from User u")
//    public List<KaoheModel> list();

    @Query("select res from Role role,Resource res,RoleResource rr where " +
            "role.id=rr.roleId and res.id=rr.resId and role.id=?1")
    List<KaoheModel> listKaoheModel(int mId);

//    @Query("select ExpKaohe from ExpModel em,KaoheModel km where em.m_id=km.id")
//    List<ExpKaohe> loadallmodel(int mId);

    @Query("select k.m_id from KaoheModel as k where k.arrange_id = ?1")
    List<Integer> findAllMid(int arrangeId);

    @Query("select count(k) from KaoheModel k")
    Integer findKaoheNum();

    @Query("select a.id from KaoheModel a where a.m_id = ?1")
    Integer findByMid(int mid);

    @Query("select a from KaoheModel a where a.m_id = ?1")
    KaoheModel findKaoheModelByMid(int mid);

    @Modifying
    @Transactional(readOnly = false)
    @Query("update KaoheModel k set k.kaohe_baifenbi= ?1,k.test_baifenbi= ?2")
    void updateAllGreatestWeight(float kaoheBaifenbi, float testBaifenbi);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO " +
            "(khm.id,khm.m_id,khm.m_order,khm.m_scale,khm.m_test_baifenbi,khm.m_report_baifenbi,em.m_name,em.classhour,em.purpose,em.m_type,khm.kaohe_starttime,khm.kaohe_endtime) " +
            "from KaoheModel khm left join ExpModel em " +
            "on khm.m_id = em.m_id " +
            "where khm.id = ?1")
    KaoheModelAndExpInfoDTO findKaoheModelAndExpInfoDTOByKaoheid(int Kaoheid);


    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO " +
            "(khm.id,khm.m_id,khm.m_order,khm.m_scale,khm.m_test_baifenbi,khm.m_report_baifenbi,em.m_name,em.classhour,em.purpose,em.m_type,khm.kaohe_starttime,khm.kaohe_endtime) " +
            "from KaoheModel khm left join ExpModel em " +
            "on khm.m_id = em.m_id ")
    Page<KaoheModelAndExpInfoDTO> findAllKaoheModelAndExpInfoDTO(Pageable pageable);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO " +
            "(khm.id,khm.m_id,khm.m_order,khm.m_scale,khm.m_test_baifenbi,khm.m_report_baifenbi,em.m_name,em.classhour,em.purpose,em.m_type,khm.kaohe_starttime,khm.kaohe_endtime) " +
            "from KaoheModel khm left join ExpModel em " +
            "on khm.m_id = em.m_id where khm.arrange_id in(:ids)")
    Page<KaoheModelAndExpInfoDTO> findByArrange_idIn(Pageable pageable, List<Integer> ids);

    @Query("select khm.m_id from KaoheModel khm where khm.arrange_id = ?1")
    List<Integer> findKaoheModelByArrangeId(int arrangeId);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoheModelAndExpInfoDTO " +
            "(khm.id,khm.m_id,khm.m_order,khm.m_scale,khm.m_test_baifenbi,khm.m_report_baifenbi,em.m_name,em.classhour,em.purpose,em.m_type,khm.kaohe_starttime,khm.kaohe_endtime) " +
            "from KaoheModel khm left join ExpModel em " +
            "on khm.m_id = em.m_id " +
            "where khm.arrange_id = ?1")
    Page<KaoheModelAndExpInfoDTO> findAllKaoheModelAndExpInfoDTOByArrangeId(int arrangeId, Pageable pageable);

    @Query("select khm from KaoheModel khm where khm.arrange_id = ?1")
    List<KaoheModel> findKaoheModelByArrangeId2(int arrageid);

    @Query("select count(khm) from KaoheModel khm where khm.arrange_id = ?1")
    int countByArrangeId(int arrangeId);

    @Modifying
    @Transactional(readOnly = false)
    @Query("update KaoheModel k set k.kaohe_baifenbi= ?1,k.test_baifenbi= ?2 where k.arrange_id=?3")
//    @Query("update MoneyPO m set m.isDeleted=?2 where  m.money=?1")
    void updateAllGreatestWeight(float kaoheBaifenbi, float testBaifenbi, int arrangeId);


    @Query("select khm from KaoheModel khm where khm.arrange_id  in(:ids)")
    List<KaoheModel> findByArrange_idIn(List<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from KaoheModel k where k.arrange_id = ?1")
    void deleteByArrangeId(int id);

    @Query("select khm from KaoheModel khm where khm.arrange_id = ?1 and khm.m_id=?2")
    List<KaoheModel> findKaoHeModelByArrangeidAndMid(int arrangeId, Integer mid);


    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoHeModuleInfo(e.m_name,kh.kaohe_starttime,kh.kaohe_endtime) from KaoheModel kh left join ExpModel e on kh.m_id = e.m_id where kh.arrange_id = ?1")
    List<KaoHeModuleInfo> findKaoheModelByArrange_id(int arrangeid);

    @Query("select kh from Student s,ArrangeClass ac,KaoheModel kh where s.classId = ac.classId and ac.courseId = ?2 and ac.id = kh.arrange_id and kh.m_id = ?1 and s.id = ?3")
    KaoheModel findKaoheModelByIsKaohe(int mid,int courseid,int sid);
}
