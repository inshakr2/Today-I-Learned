package com.example.order_service.messagequeue;

import com.example.order_service.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public OrderDto sendOrderMessage(String topic, OrderDto orderDto) {
        String messageJson = "";

        try {
            messageJson = mapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, messageJson);
        log.info("Kafka Producer Sent Message From Order MicroService : -> " + messageJson);

        return orderDto;
    }
}
