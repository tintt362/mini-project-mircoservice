package com.trongtin.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDTO {
    @Schema(
            description = "Account Type of EZ Bank account", example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;
    @Schema(
            description = "Branch Address of EZ Bank account", example = "121 Ben Tre"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;

    @Schema(
            description = "Account Number of EZ Bank account", example = "0987654321"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    private Long accountNumber;
}
