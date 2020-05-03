package fr.ing.interview.bankaccount.service.impl;

import fr.ing.interview.bankaccount.Exception.AccountNotFoundException;
import fr.ing.interview.bankaccount.Exception.AmountLimitException;
import fr.ing.interview.bankaccount.domain.Account;
import fr.ing.interview.bankaccount.domain.Transaction;
import fr.ing.interview.bankaccount.repository.AccountRepository;
import fr.ing.interview.bankaccount.repository.TransactionRepository;
import fr.ing.interview.bankaccount.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class AccountServiceImpl implements AccountService {

    public static final double DEPOSIT_AMOUNT_LIMIT = 0.01;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private TransactionRepository transactionRepository;


    /**
     * Deposit money to a given bank account
     * @param accountId the unique bank account identifier
     * @param amount the deposit amount
     * @return a new Transaction
     */
    @Override
    @Transactional
    public Transaction deposit(Integer accountId, Double amount) {
        Transaction savedTransaction;
        if (amount < DEPOSIT_AMOUNT_LIMIT) {
            throw new AmountLimitException("deposit money from a customer to his account, is allowed when superior to €0.01");
        } else {
            Account account = getAccount(accountId);
            account.setCurrentBalance(account.getCurrentBalance() + amount);

            accountRepository.save(account);

            savedTransaction = transactionRepository.save(buildTransaction(amount, account, "DEPOSIT"));
        }
        return savedTransaction;
    }


    /**
     * With money from a given bank account
     * @param accountId the unique bank account identifier
     * @param amount the deposit amount
     * @return a new Transaction
     */
    @Override
    @Transactional
    public Transaction withdraw(Integer accountId, Double amount) {

        Transaction savedTransaction;

        Account account = getAccount(accountId);

        if (amount - account.getCurrentBalance() > account.getOverdraft()) {
            throw new AmountLimitException("You cannot withdraw money, overdraft is used");
        } else{
            account.setCurrentBalance(account.getCurrentBalance() - amount);
            accountRepository.save(account);
            savedTransaction = transactionRepository.save(buildTransaction(amount, account, "WITHDRAW"));
        }

        return savedTransaction;

    }

    /**
     * Print  balance for a given account
     * @param accountId the unique bank account identifier
     * @return current account balance
     */
    @Override
    public Double printBalance(Integer accountId) {
        return getAccount(accountId).getCurrentBalance();
    }

    /**
     * Print transactions History for a give account
     *
     * @param accountId the unique bank account identifier
     * @return a list of transactions
     */
    public List<Transaction> printTransactionsHistory(Integer accountId) {
        getAccount(accountId);
        return transactionRepository.findByAccountId(accountId);
    }

    /**
     *
     * @param accountId the unique bank account identifier
     * @return the bank account if exists
     * @throws AccountNotFoundException if the bank account id  does not exist
     */
    public Account getAccount(Integer accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (!accountOptional.isPresent())
            throw new AccountNotFoundException("Bank Account Not Found");
        return accountOptional.get();
    }

    /**
     *
     * @param amount operation amount
     * @param account the bank account
     * @param transactionType
     * @return
     */
    public Transaction buildTransaction(Double amount, Account account, String transactionType) {

        return Transaction.builder()
                .amount(amount)
                .transactionType(transactionType)
                .balanceAfterTransaction(account.getCurrentBalance())
                .operationDate(new java.sql.Timestamp(System.currentTimeMillis()))
                .account(account)
                .build();
    }

}
