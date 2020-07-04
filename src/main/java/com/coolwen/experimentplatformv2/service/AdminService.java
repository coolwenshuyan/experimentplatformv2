package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.Admin;

public interface AdminService {

    //查找
    Admin findById(int id);

    Admin findByUname(String uName);

    // 增加
    void add(Admin admin);

    //删除
    void delete(int id);

}
