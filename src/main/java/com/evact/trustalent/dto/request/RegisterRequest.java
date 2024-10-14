package com.evact.trustalent.dto.request;

import com.evact.trustalent.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.evact.trustalent.common.Constants;
import com.evact.trustalent.common.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotNull
	@NotBlank
	@JsonProperty("username")
	private String username;

	@JsonProperty("client_id")
	private Long clientId;

	@NotNull
	@NotBlank
	@Email
	@JsonProperty("email")
	private String email;

	@NotNull
	@NotBlank
	@Size(min = 6, max = 20)
	@JsonProperty("password")
	private String password;

	@NotNull
	@NotBlank
	@JsonProperty("name")
	private String name;

	@NotNull
	@JsonProperty("gender")
	private Gender gender;

	@NotNull
	@NotBlank
	@JsonProperty("place_of_birth")
	private String placeOfBirth;

	@NotNull
	@JsonProperty("date_of_birth")
	@JsonFormat(pattern = Constants.DATE_FORMAT)
	private Date dateOfBirth;

	@NotNull
	@NotBlank
	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("avatar")
	private String avatar;

	@JsonProperty("roles")
	private List<Role> roles;
}
