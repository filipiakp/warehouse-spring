package com.filipiakp.warehousespring.services;

import com.filipiakp.warehousespring.entities.User;
import com.filipiakp.warehousespring.model.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);

    if (!user.isPresent()) {
      if (username.equals("admin")) {
        User admin =
            User.builder()
                .username(username)
                .password(passwordEncoder.encode(username))
                .role(username)
                .build();
        com.filipiakp.warehousespring.entities.UserDetails adminDetails =
            new com.filipiakp.warehousespring.entities.UserDetails(admin);
        userRepository.save(admin);
        return adminDetails;
      }

      throw new UsernameNotFoundException("Could not find user");
    }

    return new com.filipiakp.warehousespring.entities.UserDetails(user.get());
  }
}
