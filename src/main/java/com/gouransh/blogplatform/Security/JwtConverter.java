package com.gouransh.blogplatform.Security;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
        Stream<String> realmRoles = extractRealmAccess(source);
        Stream<String> clientRoles = extractResourceAccess(source);

        return Stream.concat(realmRoles, clientRoles)
                .map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
                .collect(Collectors.toSet());
    }

    private Stream<String> extractRealmAccess(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess == null) return Stream.empty();

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        return (roles != null) ? roles.stream() : Stream.empty();
    }

    private Stream<String> extractResourceAccess(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess == null) return Stream.empty();

        return resourceAccess.values().stream()
                .filter(values -> values instanceof Map)
                .flatMap(resources -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> access = (Map<String, Object>) resources;
                    @SuppressWarnings("unchecked")
                    List<String> roles = (List<String>) access.get("roles");
                    return (roles != null) ? roles.stream() : Stream.empty();
                });
    }

}
