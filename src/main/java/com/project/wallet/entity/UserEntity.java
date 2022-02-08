package com.project.wallet.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {

    @Id
    private int userID;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "amount")
    private int amount;




}
