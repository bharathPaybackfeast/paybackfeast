package com.dev.paybackfeast.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dev.paybackfeast.entity.PayLater;

public interface PayLaterRepo extends MongoRepository<PayLater, String> {
    
    @Query("{'mail' : ?0}")
    PayLater FindByMail(String mail);
}
