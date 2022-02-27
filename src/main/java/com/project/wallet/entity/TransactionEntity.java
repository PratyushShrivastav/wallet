package com.project.wallet.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    private int transactionID;

    @Column(name = "payerUser")
    private String payerUser;

    @Column(name = "payeeUser")
    private String payeeUser;

    @Column(name = "transactionAmount")
    private int transactionAmount;




}
