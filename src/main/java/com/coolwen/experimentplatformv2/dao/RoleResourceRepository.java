package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.RoleResource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author CoolWen
 * @version 2018-11-02 10:15
 */
public interface RoleResourceRepository extends BaseRepository<RoleResource, Integer>, JpaSpecificationExecutor<RoleResource> {
    RoleResource findByRoleIdAndResId(int roleId, int resId);
}
