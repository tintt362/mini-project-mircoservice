package com.trongtin.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account,  information"
)
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer", example = "Trong Tin"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tin@gmail.com"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDTO  accountsDTO;

    @Schema(
            description = "Loans details of the customer"
    )
    private LoansDto  loansDto;

    @Schema(
            description = "Card details of the customer"
    )
    private CardsDto  cardsDto;
}
