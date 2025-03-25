package de.ait.javalessons.security;

import de.ait.javalessons.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Custom implementation of the {@link UserDetails} interface.
 * This class serves as a bridge between the application's {@link User} entity
 * and Spring Security, providing user-specific logic for authentication and authorization.
 *
 * Key Responsibilities:
 * - Wraps a {@link User} entity to adapt its properties
 *   for the {@link UserDetails} interface.
 * - Provides user authorities by converting the roles associated with the {@link User}
 *   entity into a {@link Collection} of {@link GrantedAuthority}.
 * - Supplies user credentials, including username and password, as required by the
 *   Spring Security framework during the authentication process.
 *
 * Usage:
 * This class is instantiated and used by {@link CustomUserDetailsService}
 * when loading user details from the database for authentication.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
