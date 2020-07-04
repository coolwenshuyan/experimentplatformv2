package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author CoolWen
 * @version 2018-10-31 8:25
 */
public interface RoleRepository extends BaseRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
}
