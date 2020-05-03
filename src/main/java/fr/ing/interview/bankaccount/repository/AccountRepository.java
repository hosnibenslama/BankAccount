package fr.ing.interview.bankaccount.repository;

import fr.ing.interview.bankaccount.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


}
