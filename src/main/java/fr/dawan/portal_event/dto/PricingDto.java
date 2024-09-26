package fr.dawan.portal_event.dto;

import fr.dawan.portal_event.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingDto {
    private long id;
    private Double price;
    private String name;
    private Event event;
}
