package com.example.order_service.messagequeue;

import com.example.order_service.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    List<Field> fields = Arrays.asList(new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("string", true, "product_id"),
            new Field("int32", true, "qty"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price"));
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public OrderDto sendOrderSink(String topic, OrderDto orderDto) {

        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);
        String messageJson = "";

        try {
            messageJson = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, messageJson);
        log.info("Order Producer Sent SinkMessage From Order MicroService : -> " + kafkaOrderDto);

        return orderDto;
    }
}
