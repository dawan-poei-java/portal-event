package fr.dawan.portal_event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CategoryDto {
    private long id;
    private String name;
}
