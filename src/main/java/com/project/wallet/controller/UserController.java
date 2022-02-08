package com.project.wallet.controller;





import com.project.wallet.entity.TransactionEntity;
import com.project.wallet.exception.LackOfResourceException;
import com.project.wallet.exception.ResourceAlreadyExistsException;
import com.project.wallet.exception.ResourceNotFoundException;
import com.project.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;


@RestController
public class UserController {



    @Autowired
    private UserService userservice;

    @GetMapping("/transaction")
    public ResponseEntity getTransactionDetails(@RequestParam int userId)  {


        try {
           return userservice.getDetails(userId);
            }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }



    @PostMapping("/wallet")
    public ResponseEntity insertUserdetails( @RequestParam String phoneNumber)  {

        try {
           return userservice.createWallet(phoneNumber);
        }
        catch(ResourceAlreadyExistsException e) {

            throw new ResourceAlreadyExistsException(e.getMessage());

        }
    }


    @PostMapping("/transaction")
   public ResponseEntity insertTransactionDetails(@RequestBody TransactionEntity transaction)  {

        try {
        return userservice.doTransaction(transaction);
        }
        catch(ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException(e.getMessage());
        }
        catch(LackOfResourceException e) {

            throw new LackOfResourceException(e.getMessage());
         }
    }


}
