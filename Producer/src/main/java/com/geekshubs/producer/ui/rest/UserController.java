package com.geekshubs.producer.ui.rest;

import com.geekshubs.producer.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class UserController {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;
    private static final String TOPIC= "prueba";


    @GetMapping("/publish/{name}")
    public String get(@PathVariable("name") final String name){

        kafkaTemplate.send(TOPIC,"1",new User(name, "TIC", 12000l));
        return "Published successfully";
    }





}
