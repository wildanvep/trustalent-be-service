package com.evact.trustalent.repository;

import com.evact.trustalent.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

	@Query(value = " FROM UserEntity WHERE username = :username and isActive = true ")
	Optional<UserEntity> findByUsername(@Param("username") String username);

	Optional<UserEntity> findByEmail(String email);

}
