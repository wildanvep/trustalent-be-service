package com.evact.trustalent.common.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSearchRequest {

	@JsonProperty("page_no")
	private Integer pageNo = 1;

	@JsonProperty("page_size")
	private Integer pageSize = 10;

	@JsonProperty("sort_by")
	private String sortBy;

}
