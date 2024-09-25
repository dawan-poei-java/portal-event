package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value="", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    public ResponseEntity<List<UserDto>> getAllUsers() throws Exception{
        List<UserDto> users = userService.getAllBy(0, 0, "");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value="/{id}", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception{
        UserDto dto = userService.getById(id);
        if(dto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id +" not found.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @Operation(summary = "Create a new user", description = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the user"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value="", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    public ResponseEntity<UserDto> saveOrUpdate(@RequestBody UserDto dto) throws Exception{
        UserDto createdUser = userService.saveOrUpdate(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a user", description = "Delete a user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(value="/{id}", produces = "text/plain")
    @PreAuthorize("hasRole('ROLE_ADMIN')")    
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) throws Exception{
        UserDto dto = userService.getById(id);
        if(dto!=null) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User with id = " + id + " deleted.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id + " is not found.");
        }
    }


}
