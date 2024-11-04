package com.trongtin.accounts.controller;


import com.trongtin.accounts.constants.AccountsConstants;
import com.trongtin.accounts.dto.CustomerDTO;
import com.trongtin.accounts.dto.ResponseDTO;
import com.trongtin.accounts.exception.ResouceNotFoundException;
import com.trongtin.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
   public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {
        iAccountsService.createAccount(customerDTO);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

   }

   @GetMapping("/fetch")
   public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDTO customerDTO = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
   }

   @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody CustomerDTO customerDTO) throws ResouceNotFoundException {
        boolean isUpdated = iAccountsService.updateAccount(customerDTO);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_200));

        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
   }

   @DeleteMapping("/delete")
   public ResponseEntity<ResponseDTO> deleteAccount(@RequestBody String mobileNumber) throws ResouceNotFoundException {
       boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
       if (isDeleted) {
           return ResponseEntity.status(HttpStatus.OK)
                   .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));

       } else {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ResponseDTO(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
       }
   }
}
