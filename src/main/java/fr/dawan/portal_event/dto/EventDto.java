package fr.dawan.portal_event.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import fr.dawan.portal_event.enums.EventState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<String> images;
    private EventState state;

    @Data
    public static class TypeEventDto {
        private long id;
        private String name;

    }

    @Data
    public static class UserDto {
        private long id;
        private String firstName;
        private String lastName;
    }

    @Data
    public static class PricingDto {
        private long id;
        private String name;
        private double price;
    }

}
