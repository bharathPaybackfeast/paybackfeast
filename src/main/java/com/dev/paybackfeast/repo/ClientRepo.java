package com.dev.paybackfeast.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.paybackfeast.entity.Client;

@Repository
public interface ClientRepo extends MongoRepository<Client, String> {
    
    @Query("{'clt_id' : ?0 }")
    Client FindByClientId(String clt_id);

    @Query("{ 'mail' : ?0 }")
    Client FindByClientEmail(String mail);

}
