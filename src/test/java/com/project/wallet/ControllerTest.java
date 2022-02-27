package com.project.wallet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wallet.controller.UserController;
import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ControllerTest {

    @Mock
    private UserService userService;
    private UserEntity user;
    private List<UserEntity> userList;
    private TransactionEntity transaction;
    private List<TransactionEntity> transactionlist;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUser()
    {
        user=new UserEntity(5,"9109549374",300);
        transaction=new TransactionEntity(1,"9109549374","9988223344",30);
        mockMvc= MockMvcBuilders.standaloneSetup(userController).build();

    }
    @AfterEach
    public void cutUser()
    {
        user=null;

        transaction=null;


    }

    @Test
    public void createWalletTest()throws Exception
    {
        when(userService.createWallet("155")).thenReturn(user);
        mockMvc.perform(post("/wallet?phoneNumber=155").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user))).andExpect(status().isCreated());
        verify(userService,times(1)).createWallet("155");

    }



    @Test
    public void getTransactionById()throws Exception
    {
        when(userService.getTransactionDetails(1)).thenReturn(transaction);
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction))).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void transactionTest()throws Exception
    {
        when(userService.doTransaction(any())).thenReturn(transaction);
        mockMvc.perform(post("/transaction").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction))).andExpect(status().isCreated());
        verify(userService,times(1)).doTransaction(any());

    }

    @Test
    public void getAllTransactionForUser()throws Exception
    {

        when(userService.getDetails(5)).thenReturn(transactionlist);
        mockMvc.perform(get("/transaction?userId=5").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction))).andExpect(status().isOk());
        verify(userService).getDetails(5);
        verify(userService,times(1)).getDetails(5);

    }


    public static String asJsonString(final Object obj)
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}