package com.geekshubs.consumer.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KafkaService {

    Logger log = LoggerFactory.getLogger(KafkaService.class);

    @KafkaListener(topics="prueba", containerFactory = "kafkaListenerContainerFactory",groupId="group_id"
    , properties = {"max.poll.interval.ms:8000","max.poll.records:25"})
    public void consume( List<String> messages){
        log.info("Iniciando lectura");
       for(String message:messages){
           log.info("Mensaje consumido = {} ", message);
       }

        log.info("Finalizada lecutra");
    }


}
