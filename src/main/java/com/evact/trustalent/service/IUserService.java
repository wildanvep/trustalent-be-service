package com.evact.trustalent.service;

import com.evact.trustalent.common.base.IBaseService;
import com.evact.trustalent.dto.request.RegisterRequest;
import com.evact.trustalent.dto.request.UserRequest;
import com.evact.trustalent.dto.request.UserSearchRequest;
import com.evact.trustalent.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface IUserService extends IBaseService {

	UserEntity createUser(RegisterRequest request);

	Page<UserEntity> findAllUsers(UserSearchRequest request);

	UserEntity getUserById(Long id);

	UserEntity updateUser(UserRequest request, Long id);

	void deleteUserById(Long id);

}
