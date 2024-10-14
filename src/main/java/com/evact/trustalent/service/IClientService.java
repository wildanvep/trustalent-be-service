package com.evact.trustalent.service;

import com.evact.trustalent.common.base.IBaseService;
import com.evact.trustalent.dto.request.ClientRequest;
import com.evact.trustalent.dto.request.ClientSearchRequest;
import com.evact.trustalent.entity.ClientEntity;
import org.springframework.data.domain.Page;

public interface IClientService extends IBaseService {

	ClientEntity createClient(ClientRequest request);

	Page<ClientEntity> findAllClients(ClientSearchRequest request);

	ClientEntity getClientById(Long id);

	ClientEntity updateClient(ClientRequest request, Long id);

	void deleteClientById(Long id);

}
