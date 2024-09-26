package fr.dawan.portal_event.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.CustomUserDetails;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.repositories.UserRepository;

//@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UsernameNotFoundException("User not found with email: " + email + " .");

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().toString());

        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), Collections.singleton(authority));
    }
}