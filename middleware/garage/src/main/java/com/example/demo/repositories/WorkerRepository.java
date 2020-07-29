package com.example.demo.repositories;

import com.example.demo.models.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface WorkerRepository extends MongoRepository<Worker, String> {
}
