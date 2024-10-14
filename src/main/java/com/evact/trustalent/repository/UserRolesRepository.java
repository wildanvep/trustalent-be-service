package com.evact.trustalent.repository;

import com.evact.trustalent.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRolesRepository extends JpaRepository<UserRolesEntity, Long>, JpaSpecificationExecutor<UserRolesEntity> {
}
