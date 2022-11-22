package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.TransactionDTO;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.services.implement.TransactionImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    @Autowired
    TransactionImplService transactionImplService;

    @ExceptionHandler(ConstraintViolationException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e)
    {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "/{description}/{amount}")
    public Set<TransactionDTO> getTransactionByDescriptionOrAmount(@PathVariable String description, @PathVariable Long amount){
        return transactionImplService.getTransactionByDescriptionOrAmount(description, amount);
    }

    @GetMapping(value = "/transaction-type/{transactionType}")
    public Set<TransactionDTO> getTransactionByTransactionType(@PathVariable String transactionType){
        try {
            return transactionImplService.getTransactionByTransactionType(transactionType);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addTransactions(@RequestParam @Min(10L) @Positive Double amount,
                                                  @RequestParam @NotBlank @Size(min = 6, max = 6) String fromAccountNumber,
                                                  @RequestParam @NotBlank @Size(min = 6, max = 6) String toAccountNumber,
                                                  @RequestParam @NotBlank @Size(min = 1, max = 200) String description,
                                                  HttpSession session){
        if (transactionImplService.addTransactions(amount, fromAccountNumber, toAccountNumber, description,
                ((Client)session.getAttribute("client")).getEmail())){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}
