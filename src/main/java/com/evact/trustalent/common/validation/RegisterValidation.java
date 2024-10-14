package com.evact.trustalent.common.validation;

import com.evact.trustalent.common.enums.Role;
import com.evact.trustalent.dto.request.RegisterRequest;
import com.evact.trustalent.entity.UserEntity;
import com.evact.trustalent.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RequiredArgsConstructor
public class RegisterValidation implements ConstraintValidator<ValidRegister, RegisterRequest> {

    private final UserRepository userRepository;

    private static final String INVALID_ROLE_REGISTER_USER = "User cannot register another user";
    private static final String INVALID_SUPER_ADMIN_REGISTER = "Cannot register Super Admin";

    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            overrideValidationMessage(context, "User not authenticated");
            return false;
        }

        String userRole = authentication.getAuthorities().stream()
                .map(Object::toString)
                .findFirst()
                .orElse(Role.USER.name());

        if (userRole.equals(Role.USER.name())) {
            overrideValidationMessage(context, INVALID_ROLE_REGISTER_USER);
            return false;
        }

        if (value.getRoles().contains(Role.SUPER_ADMIN)) {
            overrideValidationMessage(context, INVALID_SUPER_ADMIN_REGISTER);
            return false;
        }

        Optional<UserEntity> user = userRepository.findByEmail(value.getEmail());

        if (user.isPresent()) {
            overrideValidationMessage(context, "Email already registered");
            return false;
        }

        return true;
    }

    private void overrideValidationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
