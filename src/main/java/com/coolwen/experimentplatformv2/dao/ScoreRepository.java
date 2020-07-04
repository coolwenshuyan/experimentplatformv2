package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.DTO.PScoreDto;
import com.coolwen.experimentplatformv2.model.ReportAnswer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends BaseRepository<ReportAnswer,Integer>, JpaSpecificationExecutor<ReportAnswer> {

    //查询排序
    @Query("select new com.coolwen.experimentplatformv2.model.DTO.PScoreDto " +
            "(ra.reportId,r.reportDescribe,r.reportScore,ra.stuReportAnswer,ra.score) " +
            " from Report r  left join ReportAnswer ra  on r.reportId=ra.reportId " +
            "where  ra.stuId=?1 and r.mId=?2 order by r.reportOrder"
            )
//    Page<PScoreDto> findStudentsByStuCheckstate(Pageable pageable);
    public List<PScoreDto> listScorerDTOBystudentId(int stuId, int mid);
}
