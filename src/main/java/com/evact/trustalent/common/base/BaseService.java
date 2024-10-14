package com.evact.trustalent.common.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BaseService implements IBaseService {

	public PageRequest getPageable(BaseSearchRequest request) {

		String sortKey = "createdDt";

		if (StringUtils.hasLength(request.getSortBy())) {
			sortKey = request.getSortBy();
		}

		return PageRequest.of(request.getPageNo() - 1, request.getPageSize(), Sort.by(sortKey).descending());
	}

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
