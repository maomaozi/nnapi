package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.amqpservice.AmqpComputeEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RPCControllerBase {

    /*
     * three sec
     * */
    protected static final long MAX_TIME_OUT = 1000L * 3;


    @Autowired
    protected AmqpComputeEngine computeEngine;
}
