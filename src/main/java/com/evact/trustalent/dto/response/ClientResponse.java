package com.evact.trustalent.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientResponse {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("is_active")
    private boolean active;
}
