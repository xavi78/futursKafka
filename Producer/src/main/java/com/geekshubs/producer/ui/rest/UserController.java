package com.geekshubs.producer.ui.rest;

import com.geekshubs.producer.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("kafka")
public class UserController {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;
    private static final String TOPIC= "mierder";

    Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/publish/{name}")
    public String get(@PathVariable("name") final String name){

        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(TOPIC,new User(name, "TIC", 12000l));
        future.addCallback(new KafkaSendCallback<String, User>(){

            @Override
            public void onSuccess(SendResult<String, User> stringUserSendResult) {
                log.info("Message Sended");
            }

            @Override
            public void onFailure(Throwable ex) {
                KafkaSendCallback.super.onFailure(ex);
                log.error("Error in Sended"+ ex.getMessage());

            }

            @Override
            public void onFailure(KafkaProducerException e) {
                log.error("Error Producer message"+ e.getMessage());
            }
        });


        return "Published successfully";
    }





}
