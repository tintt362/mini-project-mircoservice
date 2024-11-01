package com.trongtin.accounts.dto;

import jakarta.persistence.Column;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDTO {

    private String accountType;
    private String branchAddress;
    private Long accountNumber;
}
