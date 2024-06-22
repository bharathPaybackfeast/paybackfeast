package com.dev.paybackfeast.repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dev.paybackfeast.entity.EndUser;


public interface EndUserRepo extends MongoRepository<EndUser, String>{
    
    @Query("{'edu_id' : ?0}")
    EndUser FindByEndUserId(String edu_id);

    @Query("{'mail' : ?0}")
    EndUser FindByEndUserEmail(String mail);
    
} 
    