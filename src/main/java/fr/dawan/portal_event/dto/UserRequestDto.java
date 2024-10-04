package fr.dawan.portal_event.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.validations.OnLogin;
import fr.dawan.portal_event.validations.OnRegister;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(groups = {OnLogin.class, OnRegister.class},message = "Email cannot be empty")
    @Email(groups = {OnLogin.class, OnRegister.class}, message = "Invalid email address")
    private String email;

    @NotBlank(groups = {OnLogin.class, OnRegister.class}, message = "Password cannot be empty")
    @Size(groups = {OnLogin.class, OnRegister.class}, min = 6, message = "Password should be at least 6 characters long")
    private String password;

    @NotBlank(groups = {OnRegister.class}, message = "Confirmed password cannot be empty")
    private String confirmedPassword;
    
    @NotBlank(groups = {OnRegister.class}, message = "First name cannot be empty")
    private String firstName;

    @NotBlank(groups = {OnRegister.class}, message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(groups = {OnRegister.class}, message = "Role must be specified")
    private UserRole role;

    @NotBlank(groups = {OnRegister.class}, message = "Address must be specified")
    private String address;

    @Nullable
    private String addressComplement;

    @NotNull(groups = {OnRegister.class}, message = "City must be specified")
    @NumberFormat
    private City city;

    @NotBlank(groups = {OnRegister.class}, message = "Zip code must be specified")
    private String zipCode;

    @NotBlank(groups = {OnRegister.class}, message = "Birth date must be specified")
    private LocalDate birthDate;

    @NotBlank(groups = {OnRegister.class}, message = "Phone number must be specified")
    private String phoneNumber;

    public boolean isPasswordConfirmed() {
        return password.equals(confirmedPassword);
    }

@Override
public String toString() {
    return "UserRequestDto{" +
            "email='" + email + '\'' +
            ", password='[PROTECTED]'" +
            ", confirmedPassword='[PROTECTED]'" +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", role=" + role +
            ", address='" + address + '\'' +
            ", addressComplement='" + addressComplement + '\'' +
            ", city=" + city +
            ", zipCode='" + zipCode + '\'' +
            ", birthDate=" + birthDate +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
}
}
    
