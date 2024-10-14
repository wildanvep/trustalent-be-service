package com.evact.trustalent.dto.response;

import com.evact.trustalent.common.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AuthenticationResponse {

	@JsonProperty("token")
	private String token;

	@JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
	@JsonProperty("token_expired_at")
	private Date tokenExpiredAt;

	@JsonProperty("user_info")
	private UserInfo userInfo;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UserInfo {

		@JsonProperty("username")
		private String username;

		@JsonProperty("email")
		private String email;

		@JsonProperty("name")
		private String name;

		@JsonProperty("roles")
		private List<String> roles;
	}
}
