package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.ClientDTO;
import com.santander.homebanking.services.ClientService;
import com.santander.homebanking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/clients")
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MessageSource messages;

    @ExceptionHandler(ConstraintViolationException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e)
    {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping(value = "/{id}")
    public ClientDTO getClientByID(@PathVariable Long id){
        return clientService.getClientByID(id);
    }

    @GetMapping(value = "/email/{email}")
    public ClientDTO getClientByEmail(@PathVariable String email){
        return clientService.getClientByEmail(email);
    }

    @GetMapping(value = "/firstName/{firstName}")
    public List<ClientDTO> getClientsByFirstName(@PathVariable String firstName){
        return clientService.getClientsByFirstName(firstName);
    }

    @GetMapping(value = "/cards/{cardColor}")
    public Set<ClientDTO> getClientsHasTypeCards(@PathVariable String cardColor){
        try {
            return clientService.getClientsHasTypeCards(cardColor);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @GetMapping(value = "/loans/amount/greater/{amount}")
    public Set<ClientDTO> getClientsLoansAmountGreater(@PathVariable Double amount){
        return clientService.getClientsLoansAmountGreater(amount);
    }

    @GetMapping(value = "/loans/payments/greater/{amount}")
    public Set<ClientDTO> getClientsLoansPaymentsGreater(@PathVariable Integer amount){
        return clientService.getClientsLoansPaymentsGreater(amount);
    }

    @PostMapping("")
    public ResponseEntity<Object> register(
            @RequestParam @NotBlank String firstName, @RequestParam @NotBlank String lastName,
            @RequestParam @NotBlank @Email String email, @RequestParam @NotBlank String password){

        ResponseUtils res = clientService.register(firstName, lastName, email, password);

        if (res.getDone()){
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), null, LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        } else{
            return new ResponseEntity<>(
                    messages.getMessage(res.getMessage(), res.getArgs(), LocaleContextHolder.getLocale()),
                    HttpStatus.valueOf(res.getStatusCode()));
        }
    }

    @GetMapping(value = "/current")
    public ClientDTO getCurrent(HttpSession session){
        return clientService.getCurrent(session);
    }
}
