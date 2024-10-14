package com.evact.trustalent.common.utils;

import com.evact.trustalent.common.dto.ResponseDTO;
import com.evact.trustalent.common.dto.SearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

	private static final String SUCCESS_MESSAGE = "Success!";

	public static <T> ResponseEntity<SearchResponseDTO<T>> generateSearchResponseSuccess(Page<T> data) {

		SearchResponseDTO<T> response = new SearchResponseDTO<>();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setMessage(SUCCESS_MESSAGE);

		response.setPageNo(data.getPageable().getPageNumber() + 1);
		response.setPageSize(data.getPageable().getPageSize());
		response.setTotalDataInPage(data.getContent().size());
		response.setTotalData(data.getTotalElements());
		response.setTotalPages(data.getTotalPages());
		response.setData(data.getContent());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ResponseDTO<T>> generateResponseSuccess(T data) {

		ResponseDTO<T> response = new ResponseDTO<>();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(SUCCESS_MESSAGE);
		response.setData(data);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public static <T> ResponseEntity<ResponseDTO<T>> generateResponseSuccess(T data, HttpStatus status) {

		ResponseDTO<T> response = new ResponseDTO<>();
		response.setStatus(status.value());
		response.setMessage(SUCCESS_MESSAGE);
		response.setData(data);

		return new ResponseEntity<>(response, status);
	}

	public static <T> ResponseEntity<ResponseDTO<T>> generateResponseSuccess(T data, String message) {

		ResponseDTO<T> response = new ResponseDTO<>();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(message);
		response.setData(data);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private ResponseUtil() {
		throw new IllegalStateException("Response Utility class");
	}

}
