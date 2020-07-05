package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ArrangeClass;
import com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ArrangeClassRepository extends BaseRepository<ArrangeClass, Integer>, JpaSpecificationExecutor<ArrangeClass> {


    @Query(value = "select  new com.coolwen.experimentplatformv2.model.DTO.ArrangeClassDto(a.id,cin.courseName,u.username,c.className,a.arrangeStart,a.arrangeEnd) from ArrangeClass a,CourseInfo cin,ClassModel c,User u where a.teacherId = u.id and a.classId=c.classId and a.courseId=cin.id")
    Page<ArrangeClassDto> findByAll(Pageable pageable);
}
