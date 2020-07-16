package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author Artell
 * @date 2020/5/12 18:04
 */
public interface KaoHeModelScoreRepository extends BaseRepository<KaoHeModelScore, Integer>, JpaSpecificationExecutor<KaoHeModelScore> {

    @Query("delete from KaoHeModelScore khms where khms.tKaohemodleId = ?1 ")
    void deleteByTKaohemodleId(int tkid);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO(kh.m_id,khs.stuId,khs.mTeststate,khs.mReportstate,khs.mScale,khs.mScore,e.m_name,e.imageurl,e.m_inurl,e.report_type,kh.kaohe_starttime,kh.kaohe_endtime) from KaoheModel kh left join ExpModel e on kh.m_id = e.m_id left join KaoHeModelScore khs on khs.tKaohemodleId = kh.id where khs.stuId = ?1 and kh.arrange_id =?2 order by kh.m_id")
    Page<KaoHeModelStuDTO> findKaoHeModelStuDTOByStuId(int stu_id,int arrangeID, PageRequest pageRequest);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO(kh.m_id,khs.stuId,khs.mTeststate,khs.mReportstate,khs.mScale,khs.mScore,e.m_name,e.imageurl,e.m_inurl,e.report_type,kh.kaohe_starttime,kh.kaohe_endtime) from KaoheModel kh left join ExpModel e on kh.m_id = e.m_id left join KaoHeModelScore khs on khs.tKaohemodleId = kh.id where khs.stuId = ?1 and e.m_id = ?2")
    KaoHeModelStuDTO findKaoHeModelStuDTOByStuId(int stu_id, int mid);

    @Query("select k from KaoHeModelScore k where k.tKaohemodleId=?1 and k.stuId = ?2")
    KaoHeModelScore findByStuIdAndTKaohemodleId(int kaoHeModleId, int stu);

    @Query("select khs from KaoHeModelScore khs,Student s,ClassModel cm where khs.stuId = s.id and s.classId = cm.classId and cm.classIscurrent = false and khs.tKaohemodleId = ?1")
    List<KaoHeModelScore> findKaoHeModelScoreByTKaohemodleIdAndStuId(int kaoheid);

    @Query("select khs from KaoHeModelScore khs where khs.tKaohemodleId = ?1")
    List<KaoHeModelScore> findKaoHeModelScoreByKaoheid(int kaoheid);

    @Query("select khs from KaoHeModelScore khs where khs.stuId = ?1")
    List<KaoHeModelScore> findKaoHeModelScoreByStuId(int id);

    @Query("select k from KaoHeModelScore k where k.stuId = ?1 and k.tKaohemodleId = ?2")
    KaoHeModelScore findKaoHeModelScoreByStuIdAndId(int stuid, int id);

    @Query("select count(khs)from KaoheModel kh left join KaoHeModelScore khs on kh.id = khs.tKaohemodleId left join Student s on khs.stuId = s.id left join ClassModel cm on cm.classId = s.classId where cm.classId = ?1 and kh.m_id = ?2 and khs.mTeststate = false ")
    Integer findmTestFalseByClassIdAndMid(int classId, int Mid);

    @Query("select count(khs)from KaoheModel kh left join KaoHeModelScore khs on kh.id = khs.tKaohemodleId left join Student s on khs.stuId = s.id left join ClassModel cm on cm.classId = s.classId where cm.classId = ?1 and kh.m_id = ?2 and khs.mReportstate = false ")
    Integer findmReportFalseByClassIdAndMid(int classId, int Mid);

    @Query("select khs from KaoHeModelScore khs,KaoheModel k where k.id=khs.tKaohemodleId and khs.stuId = ?1 and k.arrange_id = ?2")
    List<KaoHeModelScore> findKaoheModuleScoreByStuIdAndArrangeId(int studentId, int arrangeId);

    //
    @Query("select khs from KaoHeModelScore khs where khs.tKaohemodleId = ?1 and khs.stuId=?2")
    KaoHeModelScore findByTKaohemodleIdAndStuId(int kaoHeModleId, int stu);

    @Query("select khs from KaoHeModelScore khs,KaoheModel k where k.id=khs.tKaohemodleId and k.m_id = ?1")
    List<KaoHeModelScore> findKaoheModuleScoreByModelId(int mid);
}
