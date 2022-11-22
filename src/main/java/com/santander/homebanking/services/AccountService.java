package com.santander.homebanking.services;

import com.santander.homebanking.dtos.AccountDTO;
import com.santander.homebanking.models.Client;
import com.santander.homebanking.utils.ResponseUtils;

import javax.servlet.http.HttpSession;
import java.util.Set;

public interface AccountService {
    Set<AccountDTO> getAccounts();

    AccountDTO getAccount(Long id);

    AccountDTO getAccountByNumber(String number);

    Set<AccountDTO> getAccountByCreationDate(String creationDate);

    Set<AccountDTO> getAccountByBalanceGreaterThan(Long balance);

    Set<AccountDTO> getAccountByBalance(Long balance);

    Set<AccountDTO> getAccountByNumberInj(String number);

    Set<AccountDTO> getCurrentAccounts(HttpSession session);

    ResponseUtils addAccount(Client client);

    AccountDTO getAccountByIdCurrentClient(Long id, Client client);
}
