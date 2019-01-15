package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.service.tensor.ComputeEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RPCControllerBase {

    /*
     * three sec
     * */
    protected static final long MAX_TIME_OUT = 1000L * 3;

    @Autowired
    protected ComputeEngineService computeEngineService;


    protected ResponseEntity<String> getCrossOriginResponse(String responseObject) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        if (responseObject == null) {
            return new ResponseEntity<>("", headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(responseObject, headers, HttpStatus.OK);
    }
}
