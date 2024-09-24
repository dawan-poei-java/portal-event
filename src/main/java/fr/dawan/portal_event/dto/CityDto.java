package fr.dawan.portal_event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private Long id;
    private String name;
    
    public CityDto(String name) {
        this.name = name;
    }
}
