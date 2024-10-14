package com.evact.trustalent.controller;

import com.evact.trustalent.common.dto.ResponseDTO;
import com.evact.trustalent.common.dto.SearchResponseDTO;
import com.evact.trustalent.common.utils.ResponseUtil;
import com.evact.trustalent.common.validation.ValidRegister;
import com.evact.trustalent.dto.request.RegisterRequest;
import com.evact.trustalent.dto.request.UserRequest;
import com.evact.trustalent.dto.request.UserSearchRequest;
import com.evact.trustalent.entity.UserEntity;
import com.evact.trustalent.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

	private final IUserService service;

	@GetMapping()
	public ResponseEntity<SearchResponseDTO<UserEntity>> searchUsers(UserSearchRequest request) {

		Page<UserEntity> results = service.findAllUsers(request);

		return ResponseUtil.generateSearchResponseSuccess(results);
	}

	@PostMapping()
	public ResponseEntity<ResponseDTO<UserEntity>> register(@ValidRegister @Valid @RequestBody RegisterRequest request) {
		return ResponseUtil.generateResponseSuccess(service.createUser(request), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<UserEntity>> getUserById(@PathVariable Long id) {

		UserEntity result = service.getUserById(id);

		return ResponseUtil.generateResponseSuccess(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO<UserEntity>> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {

		UserEntity result = service.updateUser(request, id);

		return ResponseUtil.generateResponseSuccess(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO<UserEntity>> deleteUserById(@PathVariable Long id) {

		service.deleteUserById(id);

		return ResponseUtil.generateResponseSuccess(null, HttpStatus.NO_CONTENT);
	}

}
