package fr.dawan.portal_event.services;

import java.util.List;

import fr.dawan.portal_event.dto.UserDto;

public interface IUserService {
    List<UserDto> getAllBy(int page, int size, String search);
    UserDto saveOrUpdate(UserDto dto);
    void deleteById(long id);
    UserDto getById(long id);
}
