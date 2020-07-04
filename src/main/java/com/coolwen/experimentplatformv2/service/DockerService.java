package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.model.Docker;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DockerService {

    List<Docker> findDockersByTenData();

    Docker findDockerByDc_url(String url);

    void addDocker(Docker docker);
    Docker findDockerByStu_id(int stuid);

    Page<Docker> findAll(int pageNum);

    Docker findByid(int id);

    void delDocker(int id);


}
