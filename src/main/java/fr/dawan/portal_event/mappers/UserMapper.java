package fr.dawan.portal_event.mappers;

import org.mapstruct.Mapper;

import fr.dawan.portal_event.dto.UserDto;
import fr.dawan.portal_event.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
