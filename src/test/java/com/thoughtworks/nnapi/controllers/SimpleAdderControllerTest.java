package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.amqpservice.AmqpComputeEngine;
import com.thoughtworks.nnapi.model.IntegerResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;


public class SimpleAdderControllerTest {

    private static final String returnId = "asc123";

    @InjectMocks
    SimpleAdderController simpleAdderController;

    @Mock
    AmqpComputeEngine computeEngine;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMsgShouldCommitData() {


        Mockito.doReturn(returnId).when(computeEngine).commitCalculate(
                Mockito.anyString(), Mockito.anyString());

        simpleAdderController.sendMsg(1, 2);

        Mockito.verify(computeEngine, Mockito.times(1)).commitCalculate(
                Mockito.eq("[1,2]"), Mockito.eq("add"));
    }

    @Test
    public void sendMsgShouldEventuallyReceiveData() {
//        ArgumentCaptor<String> idCapture = ArgumentCaptor.forClass(String.class);

        Mockito.doReturn(returnId).when(computeEngine).commitCalculate(
                Mockito.anyString(), Mockito.anyString());

        Mockito.doReturn(null).when(computeEngine).retriveResult(
                Mockito.anyString(), Mockito.any(Class.class), Mockito.anyLong());

        simpleAdderController.sendMsg(1, -2);

//        Mockito.verify(computeEngine, Mockito.times(1)).commitCalculate(
//                Mockito.eq("[1,-2]"), Mockito.eq("add"), idCapture.capture());
//
//        String id = idCapture.getValue();

        Mockito.verify(computeEngine, Mockito.times(1)).retriveResult(
                Mockito.eq(returnId), Mockito.eq(IntegerResult.class), Mockito.longThat(x -> x < 10000));
    }

}