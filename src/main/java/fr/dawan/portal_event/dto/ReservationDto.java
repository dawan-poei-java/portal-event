package fr.dawan.portal_event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long id;
    private EventDto event;
    private UserDto user;
    private PricingDto pricing;
}
