package fr.dawan.portal_event.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import fr.dawan.portal_event.entities.City;
import fr.dawan.portal_event.enums.UserRole;
import fr.dawan.portal_event.validations.OnLogin;
import fr.dawan.portal_event.validations.OnRegister;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    private String address;

    @NotNull(groups = {OnRegister.class}, message = "City must be specified")
    private City city;

    private String zipCode;

    public boolean isPasswordConfirmed() {
        return password.equals(confirmedPassword);
    }
}
    
