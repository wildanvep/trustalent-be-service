package com.evact.trustalent.dto.request;

import com.evact.trustalent.common.base.BaseSearchRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSearchRequest extends BaseSearchRequest {

	@JsonProperty("name")
	private String name;

	@JsonProperty("is_active")
	private Boolean active;
}
