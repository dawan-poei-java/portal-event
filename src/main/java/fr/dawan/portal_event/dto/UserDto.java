package fr.dawan.portal_event.dto;

import java.time.LocalDateTime;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.utils.DtoTool;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    //Test pour convertir un UserRequestDto en UserDto
    public UserDto(UserRequestDto userRequestDto) {
        this.firstName = userRequestDto.getFirstName();
        this.lastName = userRequestDto.getLastName();
        this.email = userRequestDto.getEmail();
        this.phoneNumber = "";
        this.address = userRequestDto.getAddress();
        this.addressComplement = "";
        this.city = DtoTool.convert(userRequestDto.getCity(), CityDto.class);
        this.zipCode = userRequestDto.getZipCode();
        this.password = userRequestDto.getPassword();
    }


    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    @Nullable
    private String addressComplement;
    private CityDto city;
    private String zipCode;
    private String password;
    private LocalDateTime createdAt;
    private UserRole role;

}
