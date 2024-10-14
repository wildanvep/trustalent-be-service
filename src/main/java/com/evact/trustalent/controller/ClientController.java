package com.evact.trustalent.controller;

import com.evact.trustalent.common.dto.ResponseDTO;
import com.evact.trustalent.common.dto.SearchResponseDTO;
import com.evact.trustalent.common.utils.ResponseUtil;
import com.evact.trustalent.dto.request.ClientRequest;
import com.evact.trustalent.dto.request.ClientSearchRequest;
import com.evact.trustalent.entity.ClientEntity;
import com.evact.trustalent.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<ClientEntity>> createClient(@RequestBody ClientRequest request) {

        ClientEntity result = service.createClient(request);

        return ResponseUtil.generateResponseSuccess(result, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<SearchResponseDTO<ClientEntity>> searchClients(ClientSearchRequest request) {

        Page<ClientEntity> results = service.findAllClients(request);

        return ResponseUtil.generateSearchResponseSuccess(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ClientEntity>> getClientById(@PathVariable Long id) {

        ClientEntity result = service.getClientById(id);

        return ResponseUtil.generateResponseSuccess(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ClientEntity>> updateClient(@PathVariable Long id, @RequestBody ClientRequest request) {

        ClientEntity result = service.updateClient(request, id);

        return ResponseUtil.generateResponseSuccess(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<ClientEntity>> deleteClientById(@PathVariable Long id) {

        service.deleteClientById(id);

        return ResponseUtil.generateResponseSuccess(null, HttpStatus.NO_CONTENT);
    }

}
