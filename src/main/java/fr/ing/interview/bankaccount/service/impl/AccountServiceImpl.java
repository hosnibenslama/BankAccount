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

import static fr.ing.interview.bankaccount.util.ConstantUtil.*;


@Service
public class AccountServiceImpl implements AccountService {

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
            throw new AmountLimitException(DEPOSIT_LIMIT_ERROR_MESSAGE);
        } else {
            Account account = getAccount(accountId);
            account.setCurrentBalance(account.getCurrentBalance() + amount);

            accountRepository.save(account);

            savedTransaction = transactionRepository.save(buildTransaction(amount, account, DEPOSIT));
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
            throw new AmountLimitException(OVERDRAFT_USED_ERROR_MESSAGE);
        } else{
            account.setCurrentBalance(account.getCurrentBalance() - amount);
            accountRepository.save(account);
            savedTransaction = transactionRepository.save(buildTransaction(amount, account, WITHDRAW));
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
            throw new AccountNotFoundException(BANK_ACCOUNT_NOT_FOUND_ERROR_MESSAGE);
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
