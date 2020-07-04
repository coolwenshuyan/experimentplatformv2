package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.Docker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DockerRepository extends BaseRepository<Docker, Integer>, JpaSpecificationExecutor<Docker> {

    @Query("select d from Docker d where d.dc_state = false")
    List<Docker> findDockersByTenData(Pageable pageable);

    @Query("select d from Docker d where d.dc_url = ?1")
    Docker findDockerByDc_url(String url);

    @Query("select d from Docker d where d.stu_id = ?1")
    Docker findDockerByStu_id(int stuid);
}
