package org.vladislavb.onemediatesttask.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vladislavb.onemediatesttask.repository.UserRepository;

/**
 * CustomUserServiceImpl is an implementation of the Spring Security {@link UserDetailsService} interface.
 * It is responsible for loading a user by their username (email in this case) and returning a {@link CustomUserDetails} object.
 * The service fetches the user details from the database via the {@link UserRepository}.
 *
 * @author Vladislav Baryshev
 */
@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads the user details by username (email) from the database.
     * If the user is not found, a {@link UsernameNotFoundException} is thrown.
     *
     * @param username the email address of the user to load.
     * @return a {@link CustomUserDetails} object representing the user.
     * @throws UsernameNotFoundException if the user with the given email address is not found.
     */
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
