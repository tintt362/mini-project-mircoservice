package com.trongtin.accounts.service;

import com.trongtin.accounts.dto.CustomerDTO;

import org.springframework.stereotype.Service;

public interface IAccountsService {

    void createAccount(CustomerDTO customerDTO);
}
