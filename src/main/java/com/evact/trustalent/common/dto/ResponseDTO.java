package com.evact.trustalent.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {

	private int status;
	private String message;
	private T data;

}
