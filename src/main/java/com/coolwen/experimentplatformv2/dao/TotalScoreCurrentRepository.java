package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.DTO.ModuleGradesDto;
import com.coolwen.experimentplatformv2.model.TotalScoreCurrent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Artell
 * @date 2020/5/12 18:04
 */
public interface TotalScoreCurrentRepository extends BaseRepository<TotalScoreCurrent, Integer>, JpaSpecificationExecutor<TotalScoreCurrent> {

    @Modifying
    @Transactional
    @Query(value = "select * from t_totalscore_current where stu_id = ?", nativeQuery = true)
    List<TotalScoreCurrent> findeAllBystuid(int uid);

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.ModuleGradesDto(t3.m_id,t3.m_name,t1.mTestScore,t2.m_test_baifenbi,t1.mReportScore,t2.m_report_baifenbi,t1.mScore) from KaoheModel t2 left join ExpModel t3 on t2.m_id=t3.m_id left join KaoHeModelScore t1 on t2.id= t1.tKaohemodleId where t1.stuId = ?1 and t2.arrange_id=?2")
    List<ModuleGradesDto> ModuleGrade(int uid,int arrangeID);

    @Query("select t from TotalScoreCurrent t where t.stuId = ?1")
    TotalScoreCurrent findTotalScoreCurrentByStuID(int stuId);

    @Query("select t from TotalScoreCurrent t where t.stuId = ?1")
    List<TotalScoreCurrent> findTotalScoreCurrentByStuId(int stuid);

    List<TotalScoreCurrent> findAll();

    @Query("select t from TotalScoreCurrent t where t.stuId = ?1 and t.arrageId=?2")
    TotalScoreCurrent findTotalScoreCurrentByStuId2(int stuid, int arrageid);

    TotalScoreCurrent findTotalScoreCurrentByStuIdAndArrageId(int stuid,int arrangeid);
}
