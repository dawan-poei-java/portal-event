package fr.dawan.portal_event.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.dto.LoginResponse;
import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.repositories.UserRepository;
import fr.dawan.portal_event.utils.DtoTool;

@Service
public class UserService implements IUserService{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllBy(int page, int size, String search) {
        List<UserDto> result = new ArrayList<>();
        List<User> entities = userRepository.findAll();

        for(User user: entities){
            UserDto dto = DtoTool.convert(user, UserDto.class);
            result.add(dto);
        }

        return result;
    }

    @Override
    public UserDto saveOrUpdate(UserDto dto) {
        User user = DtoTool.convert(dto, User.class);

        User savedUser = userRepository.saveAndFlush(user);
        UserDto savedDto = DtoTool.convert(savedUser,UserDto.class);

        return savedDto;
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            User user = optional.get();
            return DtoTool.convert(user, UserDto.class);
        }
        return null;
    }

    @Override
    public LoginResponse register(UserDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Cet utilisateur existe déjà !");
        }

        // Config dto
        dto.setRole(dto.getRole() == null ? UserRole.USER : dto.getRole());
        User user = DtoTool.convert(dto, User.class);

        userRepository.save(user);

        //Generate Authentication object
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + dto.getRole()));

        LoginResponse response = new LoginResponse(
            jwtService.generateToken(new UsernamePasswordAuthenticationToken(dto.getEmail(), null, authorities)),
            dto.getEmail(),
            dto.getRole(),
            dto.getFirstName(),
            dto.getLastName()
        );

        return response;
    }
}
