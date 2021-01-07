package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.DTO.KaoHeModelStuDTO;
import com.coolwen.experimentplatformv2.model.KaoHeModelScore;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Artell
 * @date 2020/5/12 18:04
 */
public interface KaoHeModelScoreService {

    public void add(KaoHeModelScore res);

    public void update(KaoHeModelScore res);

    public void delete(int id);

    public void deleteAllByKaohemId(Integer kaohemid);

    public void deleteByStuId(int sid);

    public KaoHeModelScore load(int id);

    public List<KaoHeModelScore> listKaoHeModleScore();

    KaoHeModelScore findKaoheModelScoreByMid(int mid, int stu);

    List<KaoHeModelScore> findKaoHeModelScoreByTKaohemodleIdAndStuId(int kaohemodeleid);

    void deleteAllKaohe(List<KaoHeModelScore> kaoHeModelScores);

    void deleteKaoheModuleScoreByStuId(int id);

    KaoHeModelScore findKaoHeModelScoreByStuIdAndId(int stuid, int id);

    List<KaoHeModelScore> findKaoheModuleScoreByStuId(int stuid);

    Integer findmTestFalseByClassIdAndMid(int classId,int Mid);

    Integer findmReportFalseByClassIdAndMid(int classId,int Mid);

    Page<KaoHeModelStuDTO> findKaoHeModelStuDTOByStuIdAll(int stu_id, int arrangeID,int pageNum);

    Integer countNotFinishedModule(int stuid , int arrangeid);

    Integer countAllModule(int stuid,int arrrangid);





}
