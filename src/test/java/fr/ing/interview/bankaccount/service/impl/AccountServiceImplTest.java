package fr.ing.interview.bankaccount.service.impl;

import fr.ing.interview.bankaccount.Exception.AmountLimitException;
import fr.ing.interview.bankaccount.domain.Account;
import fr.ing.interview.bankaccount.domain.Transaction;
import fr.ing.interview.bankaccount.repository.AccountRepository;
import fr.ing.interview.bankaccount.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountServiceImplTest {

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @TestConfiguration
    static class AccountServiceImplTestContextConfiguration {
        @Bean
        public AccountService employeeService() {
            return new AccountServiceImpl();
        }
    }

    @Test
    public void whenFindById_thenReturnAccount() {
        // Given
        Account account = Account.builder()
                .accountName("John Smith")
                .currentBalance(1200.0)
                .overdraft(100.0)
                .build();

        Account savedAccount = accountRepository.save(account);

        // When
        Account foundAccount = accountRepository.findById(savedAccount.getId()).get();

        // Then
        assertThat(foundAccount.getAccountName()).isEqualTo(savedAccount.getAccountName());
    }

    @Test
    void givenAnAccount_whenDepositMoney_thenSuccess() {

        //Given
        Account account = Account.builder()
                .overdraft(200.0)
                .currentBalance(120.0)
                .accountName("JOHN SMITH")
                .build();

        Account savedAccount = accountRepository.save(account);

        //When
        accountService.deposit(savedAccount.getId(), 30.0);

        //Then
        Account foundAccount = accountRepository.findById(savedAccount.getId()).get();
        assertThat(foundAccount.getCurrentBalance()).isEqualTo(150.0);

    }

    @Test
    void givenAnAccount_whenDepositMoneyUnderDepositLimit_thenFailure() {

        //Given
        Account account = Account.builder()
                .overdraft(200.0)
                .currentBalance(120.0)
                .accountName("JOHN SMITH")
                .build();

        Account savedAccount = accountRepository.save(account);

        //When&Then
        Throwable exception = Assertions.assertThrows(AmountLimitException.class,
                ()->{        accountService.deposit(savedAccount.getId(), 0.005);
                } );

        assertTrue(exception.getMessage().contains("is allowed when superior to €0.01"));

    }

    @Test
    void givenAnAccount_whenWithdrawMoney_thenSuccess() {
        //Given
        Account account = Account.builder()
                .overdraft(200.0)
                .currentBalance(120.0)
                .accountName("JOHN SMITH")
                .build();

        Account savedAccount = accountRepository.save(account);

        //When
        accountService.withdraw(savedAccount.getId(), 30.0);

        //Then
        Account foundAccount = accountRepository.findById(savedAccount.getId()).get();
        assertThat(foundAccount.getCurrentBalance()).isEqualTo(90.0);
    }

    @Test
    void givenAnAccount_whenWithdrawMoneyAndOverdraftIsUsed_thenFailure() {
        //Given
        Account account = Account.builder()
                .overdraft(100.0)
                .currentBalance(120.0)
                .accountName("JOHN SMITH")
                .build();

        Account savedAccount = accountRepository.save(account);

        //When&Then
        Throwable exception = Assertions.assertThrows(AmountLimitException.class,
                ()->{        accountService.withdraw(savedAccount.getId(), 230.0);;
                } );

        assertTrue(exception.getMessage().contains("overdraft is used"));
    }

    @Test
    void giveAnAccount_whenPrintBalance_thenAmountBalanceRetrieved() {
        //Given
        Account account = Account.builder()
                .overdraft(100.0)
                .currentBalance(1300.0)
                .accountName("JOHN SMITH")
                .build();

        Account savedAccount = accountRepository.save(account);

        //When
        Double balance = accountService.printBalance(savedAccount.getId());

        //Then
        assertThat(savedAccount.getCurrentBalance()).isEqualTo(balance);
    }

    @Test
    void giveAnAccount_whenPrintTransactionsHistory_thenTransactionsHistoryRetrieved() {
        //Given
        Account account = Account.builder()
                .overdraft(100.0)
                .currentBalance(1300.0)
                .accountName("JOHN SMITH")
                .build();

        Account savedAccount = accountRepository.save(account);

        //When
        accountService.deposit(savedAccount.getId(), 30.0);
        accountService.deposit(savedAccount.getId(), 50.0);
        accountService.withdraw(savedAccount.getId(), 20.0);

        List<Transaction> transactions = accountService.printTransactionsHistory(savedAccount.getId());

        //Then
        assertThat(transactions.size()).isEqualTo(3);
    }
}