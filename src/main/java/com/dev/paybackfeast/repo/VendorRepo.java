package com.dev.paybackfeast.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dev.paybackfeast.entity.Vendor;

public interface VendorRepo extends MongoRepository<Vendor, String> {
    @Query("{'vdr_id' : ?0}")
    Vendor FindByVendorId(String edu_id);

    @Query("{'mail' : ?0}")
    Vendor FindByVendorEmail(String mail);
    
}
