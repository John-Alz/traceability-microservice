package com.microservice.traceability.infrastructure.out.mongo.adapter;

import com.microservice.traceability.domain.spi.IUserSessionPort;
import com.microservice.traceability.infrastructure.dto.AuthInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserSessionAdapter implements IUserSessionPort {
    @Override
    public Long getUserLoggedId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthInfoDto authInfo = (AuthInfoDto) authentication.getPrincipal();
        return authInfo.id();
    }
}
