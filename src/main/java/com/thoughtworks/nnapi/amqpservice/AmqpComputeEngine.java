package com.thoughtworks.nnapi.amqpservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.nnapi.tensorbroker.ComputeEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

@Component
public class AmqpComputeEngine implements ComputeEngine {

    @Autowired
    AmqpTemplate template;


    @Value("${amqp.exchangename}")
    private String amqpTopicExchangeName;

    @Value("${amqp.queuename}")
    private String amqpQueueName;

    private static Map<String, byte[]> results = new Hashtable<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpComputeEngine.class);

    @Override
    public String commitCalculate(String data, String routeKey) {

        String id = UUID.randomUUID().toString();

        template.convertAndSend(amqpTopicExchangeName, routeKey, data,
                message -> {
                    message.getMessageProperties().setReplyTo(amqpQueueName);
                    message.getMessageProperties().setCorrelationId(id);

                    return message;
                });

        return id;
    }

    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${amqp.queuename}", durable = "false", exclusive = "true"),
            exchange = @Exchange(value = "${amqp.queuename}")
    )
    )
    public void resultCallback(Message message) {
        results.put(message.getMessageProperties().getCorrelationId(), message.getBody());
    }

    @Override
    public <T> T retriveResult(String id, Class<T> type, long timeout) {
        byte data[] = null;

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            data = results.get(id);

            if (data != null) {
                break;
            }
            // TODO: if timeout, something will leave in map forever
            Thread.yield();
        }

        if (data == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        T result = null;

        try {
            result = objectMapper.readValue(data, type);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }

        results.remove(id);

        return result;
    }
}
