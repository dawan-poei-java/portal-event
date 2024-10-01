package fr.dawan.portal_event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long id;
    private EventDto event;
    private UserDto user;
    private PricingDto pricing;
    private LocalDateTime date;


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

    @Data
    public static class UserDto{
        private Long id;
        private String firstName;
        private String lastName;
    }

    @Data
    public static class PricingDto{
        private long id;
        private Double price;
        private String name;
    }
}

