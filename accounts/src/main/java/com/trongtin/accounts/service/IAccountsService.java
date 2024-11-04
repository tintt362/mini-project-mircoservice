package com.trongtin.accounts.service;

import com.trongtin.accounts.dto.CustomerDTO;

import com.trongtin.accounts.exception.ResouceNotFoundException;
import org.springframework.stereotype.Service;

public interface IAccountsService {

    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccount(String mobileNumber);

    boolean deleteAccount(String mobileNumber);
    boolean updateAccount(CustomerDTO customerDTO) throws ResouceNotFoundException;
}
