package com.trongtin.accounts.dto;

import jakarta.persistence.Column;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String name;
    private String email;
    private String mobileNumber;
}
