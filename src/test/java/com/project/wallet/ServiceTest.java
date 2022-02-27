package com.project.wallet;


import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.repository.TransactionRepository;
import com.project.wallet.repository.UserRepository;
import com.project.wallet.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.JsonPathAssertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {



    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;



    @Test
    public void getUserTest()
    {
        when(userRepository.findAll()).thenReturn((List<UserEntity>) Stream.of(new UserEntity(1,
                "9876654",
                (int) 30.0)).collect(Collectors.toList()));

        assertEquals(1,userService.findAllUser().size());

    }
    @Test
    public void getUserByPhoneTest()
    {
        UserEntity user=new UserEntity();

        user.setPhoneNumber("15616519");
        user.setAmount(70);
        when(userRepository.findByphoneNumber("15616519")).thenReturn(user);
        Assertions.assertThat(userService.findByNumber("15616519")).isEqualTo(user);
    }


    @Test
    public void addUserTest()
    {
        UserEntity user=new UserEntity(2,"98765432",150);
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user,userService.add(user));
    }

    @Test
    public void getTransactionByIdtest()
    {
        TransactionEntity transaction=new TransactionEntity();
        transaction.setTransactionID(75);
        transaction.setPayeeUser("782365489");
        transaction.setPayerUser("286854854");
        transaction.setTransactionAmount(450);


        when(transactionRepository.findBytransactionID(75)).thenReturn(transaction);
        System.out.println(userService.getById(75));
        Assertions.assertThat(userService.getById(75)).isEqualTo(transaction);
    }
    @Test
    public void getAllTransactionTest()
    {
        TransactionEntity transaction1=new TransactionEntity();
        transaction1.setTransactionID(91);
        transaction1.setPayerUser("541615");
        transaction1.setPayeeUser("45678449");
        transaction1.setTransactionAmount(45);



        TransactionEntity transaction2=new TransactionEntity();
        transaction1.setTransactionID(92);
        transaction1.setPayerUser("541615455");
        transaction1.setPayeeUser("4567844944");
        transaction1.setTransactionAmount(85);





        List<TransactionEntity>transactionList=new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactionList);
        Assertions.assertThat(userService.findTransaction()).isEqualTo(transactionList);


    }



}
