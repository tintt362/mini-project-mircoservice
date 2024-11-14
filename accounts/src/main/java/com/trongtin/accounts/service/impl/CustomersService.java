package com.trongtin.accounts.service.impl;

import com.trongtin.accounts.dto.AccountsDTO;
import com.trongtin.accounts.dto.CardsDto;
import com.trongtin.accounts.dto.CustomerDetailsDto;
import com.trongtin.accounts.dto.LoansDto;
import com.trongtin.accounts.entity.Accounts;
import com.trongtin.accounts.entity.Customer;
import com.trongtin.accounts.exception.ResouceNotFoundException;
import com.trongtin.accounts.mapper.AccountsMapper;
import com.trongtin.accounts.mapper.CustomerMapper;
import com.trongtin.accounts.repository.AccountsRepository;
import com.trongtin.accounts.repository.CustomerRepository;
import com.trongtin.accounts.service.client.CardsFeignClient;
import com.trongtin.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersService implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> {
            try {
                throw new ResouceNotFoundException("Customer", "mobileNumber", mobileNumber);
            } catch (ResouceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> {
            try {
                throw new ResouceNotFoundException("Account", "customerId ", mobileNumber);
            } catch (ResouceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));

        ResponseEntity<LoansDto> loansDto =  loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDto.getBody());

        ResponseEntity<CardsDto> cardsDto =  cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDto.getBody());
        return  customerDetailsDto;
    }
}
