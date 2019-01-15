package com.thoughtworks.nnapi.service;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class AmqpComputeEngineServiceTest {
    @Mock
    private AmqpTemplate template;

    @InjectMocks
    private ComputeEngineServiceImpl amqpComputeEngine;


    @Before
    public void init() throws IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        FieldUtils.writeField(amqpComputeEngine, "amqpTopicExchangeName", "", true);
        FieldUtils.writeField(amqpComputeEngine, "amqpQueueName", "", true);
    }

    @Test(timeout = 3000)
    public void commitCalculateTest() {
        Mockito.doNothing().when(template).convertAndSend(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(MessagePostProcessor.class));

        String id = amqpComputeEngine.commitCalculate("[1, 2]", "foo");

        Mockito.verify(template, Mockito.times(1)).convertAndSend(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(MessagePostProcessor.class));

        assertThat(id, is(not(nullValue())));
    }
}