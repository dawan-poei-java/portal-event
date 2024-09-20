package fr.dawan.portal_event.dto;

import fr.dawan.portal_event.entities.Event;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class TypeEventDto {
    private long id;
    private  String name;
    private List<Event> events;
}
