package ru.itis.kpfu.sweater.domains;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;

public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority(){
        return name();
    }
}
