package org.example.gfgspringboot.pubsub;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "gfg-demo", groupId = "my-group") // Kafka topic and group
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message);
    }
}