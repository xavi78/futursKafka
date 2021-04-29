package com.geekshubs.consumer.application.service;

import com.geekshubs.consumer.domain.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Service
public class KafkaService {

    Logger log = LoggerFactory.getLogger(KafkaService.class);
    public static final String DLQ_TOPIC= "dlq_topic";
    public static final String ORIGINAL_TOPIC_HEADER_KEY="prueba";
    public static final String RETRY_COUNT_HEADER_KEY= "retryCount";

    @KafkaListener(topics=ORIGINAL_TOPIC_HEADER_KEY, groupId ="group_id")
    public void consume(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack){
        String json = consumerRecord.value().toString();
        try{
            log.info("Mensaje consumdio {}", json);
            //**PROCESAMIENTO DEL MENSAJE**/
            //throw new RuntimeException();
        }catch ( Exception e)
        {

            //EL REENVIO DEL MENSAJE ERROR**/
            log.error("Error en el envio"+ e.getMessage() );
            ProducerRecord<String, String> record = new ProducerRecord<>(DLQ_TOPIC, json);
            String originalTopic = consumerRecord.topic();
            record.headers().add(ORIGINAL_TOPIC_HEADER_KEY, originalTopic.getBytes(StandardCharsets.UTF_8) );
            Header retryCount = consumerRecord.headers().lastHeader(RETRY_COUNT_HEADER_KEY);
            if (retryCount!=null){
                record.headers().add(retryCount);
            }
            //kafkaTemplate.send(record);
        }finally{
         ack.acknowledge();
        }




    }


}
