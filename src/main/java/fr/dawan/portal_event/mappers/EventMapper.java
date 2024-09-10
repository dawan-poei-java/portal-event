package fr.dawan.portal_event.mappers;

import org.mapstruct.Mapper;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(Event event);
    Event toEntity(EventDto eventDto);
}
