package com.santander.homebanking.services;

import com.santander.homebanking.dtos.ClientDTO;
import com.santander.homebanking.models.Account;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.utils.ResponseUtils;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public interface ClientService {
    List<ClientDTO> getClients();
    ClientDTO getClientByID(Long id);

    ClientDTO getClientByEmail(String email);

    List<ClientDTO> getClientsByFirstName(String firstName);
    Set<ClientDTO> getClientsHasTypeCards(String cardColor) throws IllegalArgumentException;

    Set<ClientDTO> getClientsLoansAmountGreater(Double amount);
    Set<ClientDTO> getClientsLoansPaymentsGreater(Integer amount);

    ResponseUtils register(
            String firstName, String lastName,
            String email, String password);

    ClientDTO getCurrent(HttpSession session);

    void updateClientSession(HttpSession session);
}
