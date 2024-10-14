package com.evact.trustalent.common.base;

import com.evact.trustalent.common.Constants;
import com.evact.trustalent.common.enums.Gender;
import com.evact.trustalent.common.enums.Role;
import com.evact.trustalent.dto.request.RegisterRequest;
import com.evact.trustalent.entity.UserEntity;
import com.evact.trustalent.repository.UserRepository;
import com.evact.trustalent.service.IUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InitializationService {

    private final IUserService userService;

    private final UserRepository userRepository;

    @Value("${super.admin.username}")
    private String superAdminUsername;

    @Value("${super.admin.password}")
    private String superAdminPassword;

    @Value("${super.admin.email}")
    private String superAdminEmail;

    @Value("${super.admin.name}")
    private String superAdminName;

    @Value("${super.admin.gender}")
    private Gender superAdminGender;

    @Value("${super.admin.pob}")
    private String superAdminPlaceOfBirth;

    @Value("${super.admin.dob}")
    private String superAdminDateOfBirth;

    @Value("${super.admin.phone}")
    private String phoneNumber;

    @Value("${super.admin.avatar}")
    private String avatar;


    @PostConstruct
    public void initializeSuperAdmin() throws ParseException {

        Optional<UserEntity> isSuperAdminExist = userRepository.findByUsername(this.superAdminUsername);

        if (isSuperAdminExist.isEmpty()) {
            RegisterRequest request = RegisterRequest.builder()
                    .username(this.superAdminUsername)
                    .email(this.superAdminEmail)
                    .password(this.superAdminPassword)
                    .name(this.superAdminName)
                    .gender(this.superAdminGender)
                    .placeOfBirth(this.superAdminPlaceOfBirth)
                    .dateOfBirth(new SimpleDateFormat(Constants.DATE_FORMAT).parse(this.superAdminDateOfBirth))
                    .phoneNumber(this.phoneNumber)
                    .avatar(this.avatar)
                    .roles(new ArrayList<>(List.of(Role.SUPER_ADMIN)))
                    .build();

            userService.createUser(request);
        }
    }
}
