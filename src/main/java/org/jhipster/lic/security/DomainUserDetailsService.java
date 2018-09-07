package org.jhipster.lic.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

//    private final UserRepository userRepository;

    //    UserRepository userRepository
    public DomainUserDetailsService() {
//        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
//        log.debug("Authenticating {}", login);
//        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
//        Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
//        return userFromDatabase.map(user -> {
//            if (!user.getActivated()) {
//                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//            }
//            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
//                .collect(Collectors.toList());
//            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
//                user.getPassword(),
//                grantedAuthorities);
//        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
//        "database"));

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };

        return userDetails;
    }
}
