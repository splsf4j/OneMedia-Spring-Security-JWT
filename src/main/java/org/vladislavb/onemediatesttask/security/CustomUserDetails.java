package org.vladislavb.onemediatesttask.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.vladislavb.onemediatesttask.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails is a custom implementation of the Spring Security UserDetails interface.
 * It wraps a {@link User} entity and provides Spring Security with user-specific data such as password, username,
 * and account status. This class is used to authenticate and authorize the user within the Spring Security framework.
 *
 * @author Vladislav Baryshev
 */
public record CustomUserDetails(User user) implements UserDetails {

    /**
     * Returns a list of authorities granted to the user. In this implementation, no authorities are assigned.
     *
     * @return an empty list of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the user's password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the username of the user, which in this case is the user's email.
     *
     * @return the user's email address.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Checks if the user's account is expired. By default, the account is not expired.
     *
     * @return true if the account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Checks if the user's account is locked. By default, the account is not locked.
     *
     * @return true if the account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Checks if the user's credentials (password) are expired. By default, the credentials are not expired.
     *
     * @return true if the credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Checks if the user's account is enabled. By default, the account is enabled.
     *
     * @return true if the account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
