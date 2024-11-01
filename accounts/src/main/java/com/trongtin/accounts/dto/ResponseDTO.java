package com.trongtin.accounts.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ResponseDTO {

    private String statusCode;
    private String statusMessage;
}
