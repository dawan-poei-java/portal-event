package fr.dawan.portal_event.dto;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.dawan.portal_event.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
    public UserRole getRole(){
        String authority = authorities.iterator().next().getAuthority();
        switch (authority) {
            case "ROLE_USER":
                return UserRole.USER;

            case "ROLE_ORGANIZER":
                return UserRole.ORGANIZER;

            case "ROLE_ADMIN":
                return UserRole.ADMIN;

            default:
                return UserRole.USER;
        }
    }

}
