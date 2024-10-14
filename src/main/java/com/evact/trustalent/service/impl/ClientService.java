package com.evact.trustalent.service.impl;

import com.evact.trustalent.common.base.BaseService;
import com.evact.trustalent.common.exception.BadRequestException;
import com.evact.trustalent.dto.request.ClientRequest;
import com.evact.trustalent.dto.request.ClientSearchRequest;
import com.evact.trustalent.entity.ClientEntity;
import com.evact.trustalent.repository.ClientRepository;
import com.evact.trustalent.service.IClientService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService extends BaseService implements IClientService {

	private final ClientRepository repository;

	private static final String CLIENT_NOT_FOUND = "Client  with ID [%s] not found!";

	@Override
	public ClientEntity createClient(ClientRequest request) {
		ClientEntity client = ClientEntity.builder()
				.clientName(request.getClientName())
				.active(request.isActive())
				.createdBy(this.getAuthentication().getName())
				.createdDt(new Timestamp(System.currentTimeMillis()))
				.build();

		return repository.save(client);
	}

	@Override
	public Page<ClientEntity> findAllClients(ClientSearchRequest request) {

		return repository.findAll(defineSpec(request), getPageable(request));
	}

	@Override
	public ClientEntity getClientById(Long id) {
		Optional<ClientEntity> result = repository.findById(id);

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new BadRequestException(String.format(CLIENT_NOT_FOUND, id));
		}
	}

	@Transactional
	@Override
	public ClientEntity updateClient(ClientRequest request, Long id) {
		Optional<ClientEntity> client = repository.findById(id);

		if (client.isPresent()) {

			Optional<ClientEntity> clientEntity = repository.findById(request.getClientId());

			if (clientEntity.isEmpty()) {
				throw new BadRequestException(String.format("Client with ID [%s] not found!", request.getClientId()));
			}

			client.get().setClientName(request.getClientName());
			client.get().setActive(request.isActive());
			client.get().setChangedBy(this.getAuthentication().getName());
			client.get().setChangedDt(new Timestamp(System.currentTimeMillis()));

			repository.save(client.get());

			return client.get();
		} else {
			throw new BadRequestException(String.format(CLIENT_NOT_FOUND, id));
		}
	}

	@Transactional
	@Override
	public void deleteClientById(Long id) {
		Optional<ClientEntity> result = repository.findById(id);

		if (result.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new BadRequestException(String.format(CLIENT_NOT_FOUND, id));
		}
	}

	private Specification<ClientEntity> defineSpec(ClientSearchRequest request) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (request.getName() != null && !request.getName().trim().isEmpty()) {
				String pattern = "%" + request.getName().trim().toLowerCase() + "%";
				predicates.add(builder.like(builder.lower(root.get("name")), pattern));
			}

			if (request.getActive() != null) {
				predicates.add(builder.equal(root.get("isActive"), request.getActive()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
