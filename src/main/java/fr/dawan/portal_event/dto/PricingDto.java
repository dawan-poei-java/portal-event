package fr.dawan.portal_event.dto;

import fr.dawan.portal_event.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingDto {
    private long id;
    private Double price;
    private String name;
    private EventDto event;


    @Data
    public static class EventDto{
        private Long id;
        private String title;
        private String description;
        private fr.dawan.portal_event.dto.EventDto.TypeEventDto typeEvent;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private String location;
        private String address;
        private String addressComplement;
        private CityDto city;
        private String zipCode;
        private fr.dawan.portal_event.dto.EventDto.UserDto organizer;
        private List<String> images;
    }
}

