package com.geekshubs.consumer.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
public class KafkaService {

    Logger log = LoggerFactory.getLogger(KafkaService.class);

    @KafkaListener(topics="prueba")
    public void consume( String message){
        log.info("Mensaje consumido: " + message);
    }


}
