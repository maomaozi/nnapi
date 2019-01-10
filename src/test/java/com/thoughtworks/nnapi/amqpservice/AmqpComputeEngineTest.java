package com.thoughtworks.nnapi.amqpservice;

import com.thoughtworks.nnapi.tensorbroker.ComputeEngine;
import com.thoughtworks.nnapi.tensorbroker.TensorService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.AmqpTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AmqpComputeEngineTest {

    @Mock
    AmqpTemplate template;

    @Test
    public void addDataTest() throws Exception {
        ComputeEngine ce = TensorService.getInstance("com.thoughtworks.nnapi.amqpservice.AmqpComputeEngine");

        String id = ce.commitCalculate("[1, 2]", "foo");

        ce.retriveResult(id, String.class, 3000);
    }

    @Test(timeout = 3000)
    public void commitAndCalculateTest() throws Exception {
        ComputeEngine ce = TensorService.getInstance("com.thoughtworks.nnapi.amqpservice.AmqpComputeEngine");

        String id = ce.commitCalculate("[1, 2]", "foo");

        Thread.sleep(500);
        Integer result = ce.retriveResult(id, Integer.class, 3000);

        assertThat(result, is(3));
    }
}