package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.service.ComputeEngineServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class SimpleAdderControllerTest {

    private static final String RETURN_ID = "asc123";

    @InjectMocks
    private SimpleAdderController simpleAdderController;

    @Mock
    private ComputeEngineServiceImpl computeEngine;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMsgShouldCommitData() {


        Mockito.doReturn(RETURN_ID).when(computeEngine).commitCalculate(
                Mockito.anyString(), Mockito.anyString());

        simpleAdderController.sendMsg(1, 2);

        Mockito.verify(computeEngine, Mockito.times(1)).commitCalculate(
                Mockito.eq("[1,2]"), Mockito.eq("add"));
    }

    @Test
    public void sendMsgShouldEventuallyReceiveData() {

        Mockito.doReturn(RETURN_ID).when(computeEngine).commitCalculate(
                Mockito.anyString(), Mockito.anyString());

        Mockito.doReturn(null).when(computeEngine).retriveResult(
                Mockito.anyString(), Mockito.any(Class.class), Mockito.anyLong());

        simpleAdderController.sendMsg(1, -2);

        Mockito.verify(computeEngine, Mockito.times(1)).retriveResult(
                Mockito.eq(RETURN_ID), Mockito.eq(String.class), Mockito.longThat(x -> x < 10000));
    }

}