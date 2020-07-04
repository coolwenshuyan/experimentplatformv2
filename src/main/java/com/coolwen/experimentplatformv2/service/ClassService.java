package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ClassDao;
import com.coolwen.experimentplatformv2.model.ClassModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: experimentplatform
 * @Package: com.coolwen.experimentplatformv2.service
 * @ClassName: ClassService
 * @Author: Txc
 * @Description: 班级业务层
 * @Date: 2020/5/15 0015 14:45
 * @Version: 1.0
 */
@Service
public class ClassService {
    @Autowired
    ClassDao classDao;

    public ClassModel findClassById(int classId){
        return classDao.findByClassId(classId);
    }
}
