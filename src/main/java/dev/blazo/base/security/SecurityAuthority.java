package dev.blazo.base.security;

import org.springframework.security.core.GrantedAuthority;

import dev.blazo.base.entities.Authority;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName().toString();
    }

}
