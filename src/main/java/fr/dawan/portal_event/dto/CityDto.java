package fr.dawan.portal_event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CityDto {
    private Long id;
    private String name;

}
