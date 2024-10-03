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
    private UserDto user;
    private PricingDto pricing;
    private LocalDateTime date = LocalDateTime.now();




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

