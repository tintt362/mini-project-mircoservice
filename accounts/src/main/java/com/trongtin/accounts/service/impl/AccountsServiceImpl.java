package com.trongtin.accounts.service.impl;

import com.trongtin.accounts.constants.AccountsConstants;
import com.trongtin.accounts.dto.CustomerDTO;
import com.trongtin.accounts.entity.Accounts;
import com.trongtin.accounts.entity.Customer;
import com.trongtin.accounts.exception.CustomerAlreadyExistsException;
import com.trongtin.accounts.mapper.CustomerMapper;
import com.trongtin.accounts.repository.AccountsRepository;
import com.trongtin.accounts.repository.CustomerRepository;
import com.trongtin.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            try {
                throw new CustomerAlreadyExistsException("Customer already registered with give mobileNumber" + customerDTO.getMobileNumber());
            } catch (CustomerAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100000000L + new Random().nextInt(900000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
