package com.evact.trustalent.service.impl;

import com.evact.trustalent.common.base.BaseService;
import com.evact.trustalent.common.exception.BadRequestException;
import com.evact.trustalent.dto.request.RegisterRequest;
import com.evact.trustalent.dto.request.UserRequest;
import com.evact.trustalent.dto.request.UserSearchRequest;
import com.evact.trustalent.entity.ClientEntity;
import com.evact.trustalent.entity.UserEntity;
import com.evact.trustalent.entity.UserRolesEntity;
import com.evact.trustalent.repository.ClientRepository;
import com.evact.trustalent.repository.TokenRepository;
import com.evact.trustalent.repository.UserRepository;
import com.evact.trustalent.repository.UserRolesRepository;
import com.evact.trustalent.service.IUserService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final ClientRepository clientRepository;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final UserRolesRepository userRolesRepository;

    private static final String USER_NOT_FOUND = "UserEntity with ID [%s] not found!";

    @Override
    public Page<UserEntity> findAllUsers(UserSearchRequest request) {

        return repository.findAll(defineSpec(request), getPageable(request));
    }

    @Transactional
    public UserEntity createUser(RegisterRequest request) {

        var user = UserEntity.builder()
                .clientId(request.getClientId())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .gender(request.getGender())
                .placeOfBirth(request.getPlaceOfBirth())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .avatar(request.getAvatar())
                .isActive(false)
                .createdBy(this.getAuthentication() != null && this.getAuthentication().getName() != null ?
                        this.getAuthentication().getName() : "SYSTEM")
                .createdDt(new Timestamp(System.currentTimeMillis()))
                .isActive(true)
                .superAdmin(true)
                .build();


        UserEntity savedUser = repository.save(user);

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            List<UserRolesEntity> userRoles = request.getRoles().stream()
                    .map(role -> new UserRolesEntity(null, savedUser, role))
                    .collect(Collectors.toList());

            userRolesRepository.saveAll(userRoles);
            savedUser.setUserRoles(userRoles);
        }

        return savedUser;
    }

    @Override
    public UserEntity getUserById(Long id) {
        Optional<UserEntity> result = repository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new BadRequestException(String.format(USER_NOT_FOUND, id));
        }
    }

    @Transactional
    @Override
    public UserEntity updateUser(UserRequest request, Long id) {
        Optional<UserEntity> user = repository.findById(id);

        if (user.isPresent()) {

            Optional<ClientEntity> clientEntity = clientRepository.findById(request.getClientId());

            if (clientEntity.isEmpty()) {
                throw new BadRequestException(String.format("Client with ID [%s] not found!", request.getClientId()));
            }

            user.get().setClientId(request.getClientId());
            user.get().setName(request.getName());
            user.get().setGender(request.getGender());
            user.get().setPlaceOfBirth(request.getPlaceOfBirth());
            user.get().setDateOfBirth(request.getDateOfBirth());
            user.get().setPhoneNumber(request.getPhoneNumber());
            user.get().setAvatar(request.getAvatar());
            user.get().setIsActive(request.getIsActive());
            user.get().setChangedBy(this.getAuthentication().getName());
            user.get().setChangedDt(new Timestamp(System.currentTimeMillis()));

            if (Boolean.FALSE.equals(user.get().getIsActive())) {
                var validUserTokens = tokenRepository.findAllValidTokenByUser(id);
                if (!validUserTokens.isEmpty()) {
                    validUserTokens.forEach(token -> {
                        token.setExpired(true);
                        token.setRevoked(true);
                    });
                    tokenRepository.saveAll(validUserTokens);
                }
            }

            repository.save(user.get());

            return user.get();
        } else {
            throw new BadRequestException(String.format(USER_NOT_FOUND, id));
        }
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        Optional<UserEntity> result = repository.findById(id);

        if (result.isPresent()) {
            tokenRepository.deleteAllByUserId(id);
            repository.deleteById(id);
        } else {
            throw new BadRequestException(String.format(USER_NOT_FOUND, id));
        }
    }

    private Specification<UserEntity> defineSpec(UserSearchRequest request) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
                String pattern = "%" + request.getUsername().trim().toLowerCase() + "%";
                predicates.add(builder.like(builder.lower(root.get("username")), pattern));
            }
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                String pattern = "%" + request.getName().trim().toLowerCase() + "%";
                predicates.add(builder.like(builder.lower(root.get("name")), pattern));
            }
            if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                String pattern = "%" + request.getEmail().trim().toLowerCase() + "%";
                predicates.add(builder.like(builder.lower(root.get("email")), pattern));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
