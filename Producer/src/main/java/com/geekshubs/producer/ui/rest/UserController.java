package com.geekshubs.producer.ui.rest;

import com.geekshubs.producer.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/publish/{name}")
    public String get(@PathVariable("name") final String name){

        log.info("Start sended messages");

        for(int i = 0 ; i<1000; i++){
            log.info("Posición -> "+ i );
            kafkaTemplate.send(TOPIC,new User(name+i, "TIC", 12000l));
        }

        return "Published successfully";
    }





}
