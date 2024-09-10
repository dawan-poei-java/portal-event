package fr.dawan.portal_event.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<List<UserDto>> getAllUsers() throws Exception{
        List<UserDto> users = userService.getAllBy(0, 0, "");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) throws Exception{
        UserDto dto = userService.getById(id);
        if(dto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + id +" not found.");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @PostMapping(value="", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> saveOrUpdate(@RequestBody UserDto dto) throws Exception{
        UserDto createdUser = userService.saveOrUpdate(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/{id}", produces = "text/plain")
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
