package com.geekshubs.consumer.application.service;

import com.geekshubs.consumer.domain.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KafkaService {

    Logger log = LoggerFactory.getLogger(KafkaService.class);

    @KafkaListener(topics="prueba",containerFactory = "kafkaListenerContainerFactory")
    public void consume(List<ConsumerRecord<String, String>> messages){

       /* messages.forEach(
                valor->log.info(valor.key())
        );*/
        for(ConsumerRecord<String, String> message:messages){
            log.info("Información mensaje -> Offset {} Particion={} key={} Value={}",
                    message.offset(), message.partition(),message.key(), message.value());
        }
    }


}
