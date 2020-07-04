package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.ClassModel;
import org.springframework.data.jpa.repository.Query;

/**
 * @ProjectName: experimentplatform
 * @Package: com.coolwen.experimentplatformv2.dao
 * @ClassName: ClassDao
 * @Author: Txc
 * @Description: 班级持久层
 * @Date: 2020/5/15 0015 14:41
 * @Version: 1.0
 */
public interface ClassDao extends BaseRepository<ClassModel, Integer> {
    ClassModel findByClassId(int id);

    @Query(value = "select class_name from t_class where class_id = ?",nativeQuery = true)
    String findByClassName(int id);
}
