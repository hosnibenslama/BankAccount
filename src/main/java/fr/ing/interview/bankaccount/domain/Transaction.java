package fr.ing.interview.bankaccount.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@Table(name = "B_Transaction")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;

    @Column
    private String transactionType;

    @Column
    private double amount;

    @Column
    private Timestamp operationDate;

    @Column
    private double balanceAfterTransaction;

    @ManyToOne
    @JoinColumn(name="account_id")
    Account account;



}
