package com.ssafy.fittapet.backend.domain.dto.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final UserDto userDTO;

    public CustomOAuth2User(UserDto userDto) {
        this.userDTO = userDto;
    }

    @Override
    public Map<String, Object> getAttributes() {

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", userDTO.getUserId());
        attributes.put("username", userDTO.getUserUniqueName());
        attributes.put("name", userDTO.getUserName());
        attributes.put("role", userDTO.getRole());

        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) userDTO::getRole);

        return collection;
    }

    @Override
    public String getPassword() {
        return "";
    }

    public Long getId() {
        return userDTO.getUserId();
    }

    @Override
    public String getUsername() {
        return userDTO.getUserUniqueName();
    }

    @Override
    public String getName() {
        return userDTO.getUserName();
    }

    public String getRole() {
        return userDTO.getRole();
    }
}
