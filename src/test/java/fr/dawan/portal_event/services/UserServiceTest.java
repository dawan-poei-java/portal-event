package fr.dawan.portal_event.services;


import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.entities.User;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.repositories.UserRepository;
import fr.dawan.portal_event.utils.DtoTool;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

//    @Mock
//    private DtoTool dtoTool;

    @InjectMocks
    private UserService service;
    private User user;
    private UserDto userDto;
    private List<User> users;
    private List<UserDto> userDtos;

    @BeforeEach
    public void setUp() {
        City city = new City();
        users = new ArrayList<>();
        userDtos = new ArrayList<>();
        user = new User(1L, "John", "Doe", "john.doe@example.com", "password123", LocalDateTime.of(2024, 9, 23, 11, 0), "0123456789", "123 Rue Exemple", "Appartement 4B", city, "75001", UserRole.ORGANIZER);
        userDto = new UserDto(1L, "John", "Doe", "john.doe@example.com", "password123", LocalDateTime.of(2024, 9, 23, 11, 0), "0123456789", "123 Rue Exemple", "Appartement 4B", city, "75001", UserRole.ORGANIZER);
        users.add(user);
        userDtos.add(userDto);

    }
    @Test
    public void getAllByTest(){
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> result = service.getAllBy(0, 10, "");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(result.get(0), Matchers.samePropertyValuesAs(userDtos.get(0)));
        verify(userRepository).findAll();
    }

//    @Test
//    public void saveOrUpdateTest() {
//        // Stubbing des méthodes
//        when(DtoTool.convert(userDto, User.class)).thenReturn(user); // Convertir UserDto en User
//        when(userRepository.saveAndFlush(user)).thenReturn(user); // Sauvegarder l'utilisateur
//        when(DtoTool.convert(user, UserDto.class)).thenReturn(userDto); // Convertir User en UserDto
//
//        // Appeler la méthode à tester
//        UserDto result = service.saveOrUpdate(userDto);
//
//        // Vérifications
//        assertNotNull(result);
//        assertEquals(userDto.getEmail(), result.getEmail());
//        assertEquals(userDto.getFirstName(), result.getFirstName());
//
//        // Vérifier que les méthodes du mock ont bien été appelées
//        verify(DtoTool.convert(user, UserDto.class));
//        verify(userRepository).saveAndFlush(user);
//        verify(DtoTool.convert(user, UserDto.class));
//    }

    @Test
    public void deleteByIdTest(){
        service.deleteById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void getByIdTest(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDto result = service.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
    }

}
