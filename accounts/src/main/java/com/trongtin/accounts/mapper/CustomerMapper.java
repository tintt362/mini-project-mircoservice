package com.trongtin.accounts.mapper;

import com.trongtin.accounts.dto.CustomerDTO;
import com.trongtin.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO mapToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setName(customer.getName());
        customerDTO.setMobileNumber(String.valueOf(customer.getMobileNumber()));
        return customerDTO;
    }

    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer) {
        customer.setEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        customer.setMobileNumber(String.valueOf(customerDTO.getMobileNumber()));
        return customer;
    }
}
