package fr.ing.interview.bankaccount.repository;

import fr.ing.interview.bankaccount.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {


}


