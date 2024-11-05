package com.trongtin.accounts.service.impl;

import com.trongtin.accounts.constants.AccountsConstants;
import com.trongtin.accounts.dto.AccountsDTO;
import com.trongtin.accounts.dto.CustomerDTO;
import com.trongtin.accounts.entity.Accounts;
import com.trongtin.accounts.entity.Customer;
import com.trongtin.accounts.exception.CustomerAlreadyExistsException;
import com.trongtin.accounts.exception.ResouceNotFoundException;
import com.trongtin.accounts.mapper.AccountsMapper;
import com.trongtin.accounts.mapper.CustomerMapper;
import com.trongtin.accounts.repository.AccountsRepository;
import com.trongtin.accounts.repository.CustomerRepository;
import com.trongtin.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class  AccountsServiceImpl implements IAccountsService {

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

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
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

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));

        return customerDTO;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = null;
        try {
            customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                    () -> new ResouceNotFoundException("Customer", "mobileNumber", mobileNumber)
            );
        } catch (ResouceNotFoundException e) {
            throw new RuntimeException(e);
        }
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) throws ResouceNotFoundException {
        boolean isUpdated = false;
        AccountsDTO accountsDTO = customerDTO.getAccountsDTO();
        if (accountsDTO != null) {
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResouceNotFoundException("Account", "AccountNumber", accountsDTO.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accountsDTO, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResouceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
         return isUpdated;
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
