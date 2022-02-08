package com.project.wallet.repository;


import com.project.wallet.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    TransactionEntity findBytransactionID(int userId);

    TransactionEntity findBypayerUser(int userId);

    List<TransactionEntity> findAllBypayerUser(String phonenumber);

    List<TransactionEntity> findAllBypayeeUser(String phonenumber);



}
