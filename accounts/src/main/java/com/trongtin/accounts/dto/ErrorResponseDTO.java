package com.trongtin.accounts.dto;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ErrorResponseDTO {

    private String apiPath;
    private HttpStatus status;
    private String errorMessage;
    private LocalDateTime errorTime;
}
