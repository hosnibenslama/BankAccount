package fr.ing.interview.bankaccount.service;

import fr.ing.interview.bankaccount.domain.Transaction;

import java.util.List;

public interface AccountService {

    Transaction deposit(Integer accountId, Double amount);

    Transaction withdraw(Integer accountId, Double amount);

    Double printBalance(Integer accountId);

    List<Transaction> printTransactionsHistory(Integer accountId);


}
