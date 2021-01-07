package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.model.DTO.StudentLogDTO;
import com.coolwen.experimentplatformv2.model.ExpLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Artell
 * @version 2020/12/30 10:47
 */

public interface ExpLogDao extends JpaRepository<ExpLog,Integer> {

    @Query("select new com.coolwen.experimentplatformv2.model.DTO.StudentLogDTO(st.stuXuehao, st.stuName, el.logTime, el.ip, el.actionType, el.arrangeId, el.expId,em.m_name) " +
            "from ExpLog el left join Student st on el.studentId = st.id left join ExpModel em on em.m_id = el.expId")
    Page<StudentLogDTO> pageStudentLogDTO(Pageable pageable);
}

