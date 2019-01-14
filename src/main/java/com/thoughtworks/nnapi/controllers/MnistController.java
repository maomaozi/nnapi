package com.thoughtworks.nnapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MnistController extends RPCControllerBase {

    // 1MB
    private static final long MAX_IMAGE_SIZE_IN_BYTE = 1024 * 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(MnistController.class);


    @RequestMapping(method = RequestMethod.POST, path = "/recognize")
    ResponseEntity<String> sendMsg(
            @RequestParam(name = "image") MultipartFile image
    ) {
        if (image.getSize() > MAX_IMAGE_SIZE_IN_BYTE) {
            return getCrossOriginResponse(null);
        }

        byte[] data;

        try {
            data = image.getBytes();
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
            return getCrossOriginResponse(null);
        }

        byte[][] params = {data};

        String requestParamJSONString;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            requestParamJSONString = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            return null;
        }

        String id = computeEngine.commitCalculate(requestParamJSONString, "mnrc");

        return getCrossOriginResponse(computeEngine.retriveResult(id, String.class, MAX_TIME_OUT));
    }
}
