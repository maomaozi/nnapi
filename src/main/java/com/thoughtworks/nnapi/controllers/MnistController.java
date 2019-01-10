package com.thoughtworks.nnapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.nnapi.model.IntegerResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MnistController extends RPCControllerBase {

    // 1MB
    private final static long MAX_IMAGE_SIZE_IN_BYTE = 1024 * 1024;

    @RequestMapping(method = RequestMethod.POST, path = "/recognize")
    String sendMsg(
            @RequestParam(name = "image") MultipartFile image
    ) {

        ObjectMapper objectMapper = new ObjectMapper();

        if (image.getSize() > MAX_IMAGE_SIZE_IN_BYTE) {
            return null;
        }

        byte data[] = null;

        try {
            data = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data == null) {
            return null;
        }

        byte params[][] = {data};

        String requestParamJSONString;

        try {
            requestParamJSONString = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            return null;
        }

        String id = computeEngine.commitCalculate(requestParamJSONString, "mnrc");

        return computeEngine.retriveResult(id, String.class, MAX_TIME_OUT);
    }
}
