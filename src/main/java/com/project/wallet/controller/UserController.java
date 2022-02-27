package com.project.wallet.controller;





import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.entity.UserEntity;
import com.project.wallet.exception.LackOfResourceException;
import com.project.wallet.exception.ResourceAlreadyExistsException;
import com.project.wallet.exception.ResourceNotFoundException;
import com.project.wallet.model.JwtRequest;
import com.project.wallet.model.JwtResponse;
import com.project.wallet.service.UserService;
import com.project.wallet.utility.JWTUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import javax.validation.Valid;


@RestController
public class UserController {

    private static final Logger LOGGER =  LoggerFactory.getLogger(UserController.class);



    @Autowired
    private UserService userservice;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/")
    public String home() {
        return "Welcome to Daily Code Buffer!!";
    }



    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userservice.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }





    @GetMapping("/transaction")
    public ResponseEntity getTransactionDetails(@RequestParam int userId)  {


        try {
              List<TransactionEntity> finallist = userservice.getDetails(userId);
            return new ResponseEntity(finallist,HttpStatus.OK);
            }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }



    @PostMapping("/wallet")
    public ResponseEntity insertUserdetails( @RequestParam String phoneNumber)  {

        try {
            userservice.createWallet(phoneNumber);
            LOGGER.info("Successfully added user to the database");
            return new ResponseEntity("Successfully added user to the database", HttpStatus.CREATED);

        }
        catch(ResourceAlreadyExistsException e) {

            throw new ResourceAlreadyExistsException(e.getMessage());

        }

    }


    @PostMapping("/transaction")
   public ResponseEntity insertTransactionDetails(@RequestBody TransactionEntity transaction)  {

        try {
         userservice.doTransaction(transaction);
            LOGGER.info("Successfully added transaction to the database");

            return new ResponseEntity("Successfully added transaction to the database", HttpStatus.CREATED);
        }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }
        catch(LackOfResourceException e) {

            throw new LackOfResourceException(e.getMessage());
         }
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity getTransactionIdDetails(@PathVariable int transactionId)  {


        try {
                 TransactionEntity transaction= userservice.getTransactionDetails(transactionId);
            return new ResponseEntity(transaction,HttpStatus.OK);
        }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }





}
