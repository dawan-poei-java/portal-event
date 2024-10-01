package fr.dawan.portal_event.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{


    private List<GrantedAuthority> authorities;


    private User user;

    public CustomUserDetails(User user){
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority(user.getUserRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    @Override
    public String getPassword() {
        return user.getPassword();
    }

}
