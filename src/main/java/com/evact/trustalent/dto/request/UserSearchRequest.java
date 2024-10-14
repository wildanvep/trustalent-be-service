package com.evact.trustalent.dto.request;

import com.evact.trustalent.common.base.BaseSearchRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchRequest extends BaseSearchRequest {

	private String username;
	private String name;
	private String email;

}
