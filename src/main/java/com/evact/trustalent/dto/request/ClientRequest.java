package com.evact.trustalent.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

	@JsonProperty("client_id")
	private Long clientId;

	@JsonProperty("client_name")
	private String clientName;

	@JsonProperty("is_active")
	private boolean active;
}
