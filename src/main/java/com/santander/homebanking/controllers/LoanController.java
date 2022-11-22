package com.santander.homebanking.controllers;

import com.santander.homebanking.dtos.ClientLoanDTO;
import com.santander.homebanking.dtos.LoanApplicationDTO;
import com.santander.homebanking.dtos.LoanDTO;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.services.implement.LoanImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanImplService loanImplService;

    @GetMapping(value = "/loans")
    public Set<LoanDTO> getLoans() {
        return loanImplService.getLoans();
    }

    @GetMapping(value = "/loans/name/{name}")
    public Set<LoanDTO> getLoansByNameStartingWith(@PathVariable String name) {
        return loanImplService.getLoansByNameStartingWith(name);
    }

    @GetMapping(value = "/loans/max-amount/less-than/{number}")
    public Set<LoanDTO> getLoansByMaxAmountLessThan(@PathVariable Double number) {
        return loanImplService.getLoansByMaxAmountLessThan(number);
    }

    @PostMapping(value = "/loans")
    public ResponseEntity<Object> newLoan(@Valid @RequestBody LoanApplicationDTO loanApplicationDTO,
                                          BindingResult result, HttpSession session){
        if (result.hasErrors()){
            return new ResponseEntity<>("No paso la validacion", HttpStatus.FORBIDDEN);
        }

        if (loanImplService.newLoan(loanApplicationDTO, (Client) session.getAttribute("client"))){
            return new ResponseEntity<>("Prestamo creado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El prestamo no se pudo crear", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/clients/current/loans")
    public Set<ClientLoanDTO> getLoansCurrentClient(HttpSession session){
        return loanImplService.getLoansCurrentClient(session);
    }




}