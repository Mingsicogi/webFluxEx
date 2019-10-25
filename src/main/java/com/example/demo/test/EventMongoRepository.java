package com.example.demo.test;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMongoRepository extends ReactiveMongoRepository<Welcome.Event, String> {

}
