package com.evact.trustalent.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.evact.trustalent.common.Constants;
import com.evact.trustalent.common.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	@JsonProperty("client_id")
	private Long clientId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("gender")
	private Gender gender;

	@JsonProperty("place_of_birth")
	private String placeOfBirth;

	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private Date dateOfBirth;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("avatar")
	private String avatar;

	@JsonProperty("is_active")
	private Boolean isActive;
}
