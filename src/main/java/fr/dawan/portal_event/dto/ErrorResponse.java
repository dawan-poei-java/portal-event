package fr.dawan.portal_event.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    @Schema(description = "Error Message")
    private String message;
    @Schema(description = "Error Code")
    private int code;
    @Schema(description = "Error details")
    private List<String> details;
}
