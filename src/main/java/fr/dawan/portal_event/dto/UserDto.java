package fr.dawan.portal_event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.utils.DtoTool;
import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    //Test pour convertir un UserRequestDto en UserDto
    public UserDto(UserRequestDto userRequestDto) {
        this.firstName = userRequestDto.getFirstName();
        this.lastName = userRequestDto.getLastName();
        this.email = userRequestDto.getEmail();
        this.phoneNumber = userRequestDto.getPhoneNumber();
        this.address = userRequestDto.getAddress();
        this.addressComplement = userRequestDto.getAddressComplement();
        this.city = DtoTool.convert(userRequestDto.getCity(), CityDto.class);
        this.zipCode = userRequestDto.getZipCode();
        this.password = userRequestDto.getPassword();
        this.birthDate = userRequestDto.getBirthDate();
        this.createdAt = LocalDateTime.now();
        this.role = userRequestDto.getRole();
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
    @Nullable
    private LocalDateTime createdAt;
    private UserRole role;
    private LocalDate birthDate;

@Override
public String toString() {
    return "UserDto{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", address='" + address + '\'' +
            ", addressComplement='" + addressComplement + '\'' +
            ", city=" + city +
            ", zipCode='" + zipCode + '\'' +
            ", password='[PROTÉGÉ]'" +
            ", createdAt=" + createdAt +
            ", role=" + role +
            ", birthDate=" + birthDate +
            '}';
}
}
