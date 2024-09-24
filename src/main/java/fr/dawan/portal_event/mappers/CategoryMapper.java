package fr.dawan.portal_event.mappers;

import fr.dawan.portal_event.dto.EventDto;
import fr.dawan.portal_event.entities.Event;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    EventDto toDto(Event event);
    Event toEntity(EventDto eventDto);
}
