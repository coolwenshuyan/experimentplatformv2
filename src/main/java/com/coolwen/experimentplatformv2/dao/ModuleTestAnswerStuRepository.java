package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ModuleTestAnswerStu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModuleTestAnswerStuRepository extends BaseRepository<ModuleTestAnswerStu,Integer> {

    @Query("select a from ModuleTestAnswerStu as a where a.stu_id=?1 and a.quest_id = ?2")
    List<ModuleTestAnswerStu> findModuleTestAnswerStusByStu_idAndQuest_id(int Stuid, int QuestId);


    @Query("select a from ModuleTestAnswerStu as a where a.stu_id=?1 and a.quest_id = ?2")
    ModuleTestAnswerStu findModuleTestAnswerStuByStu_idAndQuest_id(int stuid, int questid);

    @Query("select mtas from ModuleTestAnswerStu mtas where mtas.quest_id = ?1")
    List<ModuleTestAnswerStu> findModuleTestAnswerStuByQuest_id(int questid);

    @Query("select mtas from ModuleTestAnswerStu mtas where mtas.stu_id = ?1")
    List<ModuleTestAnswerStu> findModuleTestAnswerStuByStu_id(int id);

    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from ModuleTestAnswerStu mtas where mtas.quest_id = ?1")
    void deleteAllByQuest_id(int quset_id);

    @Query("select mtas from ModuleTestAnswerStu mtas, ModuleTestQuest mtq where  mtas.quest_id = mtq.questId and mtas.stu_id=?1 and mtq.mId=?2 ")
    List<ModuleTestAnswerStu> findStudentAnswbyStuidAndMid(int stuId, Integer mid);

    @Query("select m from ModuleTestAnswerStu m where m.quest_id = ?1")
    List<ModuleTestAnswerStu> findByQuest_id(int questid);

    @Modifying
    @Transactional
    @Query(value="DELETE t_mtest_answer_stu FROM t_mtest_quest,t_mtest_answer_stu WHERE t_mtest_quest.quest_id=t_mtest_answer_stu.quest_id and t_mtest_quest.m_id = ? and t_mtest_answer_stu.stu_id=?",nativeQuery=true)
    void deleteByStuIdModelId(int m_id, int id);
}
