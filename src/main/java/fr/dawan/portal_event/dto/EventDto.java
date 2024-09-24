package fr.dawan.portal_event.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private TypeEventDto typeEvent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private String address;
    private String addressComplement;
    private CityDto city;
    private String zipCode;
    private List<PricingDto> pricings;
    private UserDto organizer;
}
