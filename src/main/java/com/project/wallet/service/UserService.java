package com.project.wallet.service;



import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.exception.LackOfResourceException;
import com.project.wallet.exception.ResourceAlreadyExistsException;
import com.project.wallet.exception.ResourceNotFoundException;
import com.project.wallet.repository.TransactionRepository;
import com.project.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TransactionRepository transactionrepository;


    public ResponseEntity getDetails(int userId) {


        UserEntity user= userRepository.findByuserID(userId);

        String phonenumber = user.getPhoneNumber();
       List<TransactionEntity> payeruserdetails =  transactionrepository.findAllBypayerUser(phonenumber);

        List<TransactionEntity> payeeuserdetails =  transactionrepository.findAllBypayeeUser(phonenumber);

        List<TransactionEntity> finaluserdetails = new ArrayList<TransactionEntity>();
        finaluserdetails.addAll(payeruserdetails);
        finaluserdetails.addAll(payeeuserdetails);


        if (finaluserdetails.isEmpty())
            throw new ResourceNotFoundException("No user present with the id " + userId+ "   "+ phonenumber);
          return new ResponseEntity(finaluserdetails,HttpStatus.OK);



    }

    public ResponseEntity createWallet( String phoneNumber) {



        UserEntity userphonenumber= userRepository.findByphoneNumber(phoneNumber);

        if(userphonenumber!=null)
            throw new ResourceAlreadyExistsException("The user already exists");



        UserEntity user = new UserEntity();
        user.setPhoneNumber(phoneNumber);
        user.setAmount(1000);


        userRepository.save(user);
        return new ResponseEntity("Successfully added user to the database", HttpStatus.CREATED);


    }


    public ResponseEntity doTransaction(TransactionEntity transaction) {


        int amount= transaction.getTransactionAmount();


        String payer = transaction.getPayerUser();
        UserEntity user2 = userRepository.findByphoneNumber(payer);
        if(user2==null)
            throw new ResourceNotFoundException("Payer with this phone number does not exist");


        if(user2.getAmount()<amount)
            throw new LackOfResourceException("The payer does not have enough money");

        user2.setAmount(user2.getAmount()-amount);




        String payee = transaction.getPayeeUser();
        UserEntity user1 = userRepository.findByphoneNumber(payee);
        if(user1==null)
            throw new ResourceNotFoundException("Payee with this phone number does not exist");



        user1.setAmount(user1.getAmount()+amount);



        transactionrepository.save(transaction);


        return new ResponseEntity("Successfully added transaction to the database", HttpStatus.CREATED);


    }


}
