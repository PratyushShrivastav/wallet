package com.project.wallet.repository;



import com.project.wallet.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {



    UserEntity findByuserID(int userID);

     UserEntity findByphoneNumber(String phoneNumber);


}
