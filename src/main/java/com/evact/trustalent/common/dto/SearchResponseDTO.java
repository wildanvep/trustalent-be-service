package com.evact.trustalent.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponseDTO<T> {

	private String status;
	private String message;

	private int pageNo;
	private int pageSize;
	private int totalDataInPage;
	private long totalData;
	private int totalPages;

	private List<T> data;
}
