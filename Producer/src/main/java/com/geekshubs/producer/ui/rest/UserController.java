package com.geekshubs.producer.ui.rest;

import com.geekshubs.producer.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("kafka")
public class UserController {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;
    private static final String TOPIC= "prueba";


    @GetMapping("/publish/{name}")
    public String get(@PathVariable("name") final String name) throws ExecutionException, InterruptedException, TimeoutException {

        kafkaTemplate.send(TOPIC,new User(name, "TIC", 12000l)).get(10, TimeUnit.SECONDS);
        return "Published successfully";
    }





}
