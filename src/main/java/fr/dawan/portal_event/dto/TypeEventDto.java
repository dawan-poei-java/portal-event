package fr.dawan.portal_event.dto;

import fr.dawan.portal_event.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeEventDto {
    private long id;
    private  String name;
    //private List<Event> events;
}
