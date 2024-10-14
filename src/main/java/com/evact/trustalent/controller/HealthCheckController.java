package com.evact.trustalent.controller;

import com.evact.trustalent.common.dto.ResponseDTO;
import com.evact.trustalent.common.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-checker")
public class HealthCheckController {

	@GetMapping()
	public ResponseEntity<ResponseDTO<String>> healthCheck() {
		return ResponseUtil.generateResponseSuccess("@evact.id", "Trustalent network is running well :))");
	}

}
