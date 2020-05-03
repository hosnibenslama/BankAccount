package fr.ing.interview.bankaccount.controlller;


import fr.ing.interview.bankaccount.domain.Transaction;
import fr.ing.interview.bankaccount.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankAccountController {


    @Autowired
    AccountServiceImpl accountService;

    // US 1
    @PostMapping(path="/deposit/{accountId}")
    public Transaction depositMoney(@PathVariable("accountId") Integer accountId, @RequestParam Double amount)
    {
        return accountService.deposit(accountId, amount);
    }

    // US 2
    @PostMapping(path="/withdraw/{accountId}")
    public Transaction withdrawMoney(@PathVariable("accountId") Integer accountId, @RequestParam Double amount)
    {
        return accountService.withdraw(accountId, amount);
    }


    // US 3
    @GetMapping(path="/balance/{accountId}")
    public Double displayAccountBalance(@PathVariable("accountId") Integer accountId)
    {
        return accountService.printBalance(accountId);
    }

    // US 4
    @GetMapping(path="/accountHistory/{accountId}")
    public List<Transaction> getTransactionsHistory(@PathVariable("accountId") Integer accountId)
    {
       return accountService.printTransactionsHistory(accountId);
    }


}
