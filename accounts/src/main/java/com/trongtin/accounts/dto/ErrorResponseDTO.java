package com.trongtin.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ErrorResponseDTO {
    @Schema(
            description = "API Path invoked by client"
    )
    private String apiPath;
    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus status;
    @Schema(
            description = "Error message representing the error happened"
    )
    private String errorMessage;
    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;
}
