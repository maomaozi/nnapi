package com.thoughtworks.nnapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.nnapi.controllers.Exceptions.InternalIOError;
import com.thoughtworks.nnapi.controllers.Exceptions.RequestParamError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MnistController extends RPCControllerBase {

    // 1MB
    private static final long MAX_IMAGE_SIZE_IN_BYTE = 1024 * 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(MnistController.class);

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/recognize")
    String recognize(
            @RequestParam(name = "image") MultipartFile image
    ) {
        if (image.getSize() > MAX_IMAGE_SIZE_IN_BYTE) {
            throw new RequestParamError();
        }

        byte[] data;

        try {
            data = image.getBytes();
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
            throw new RequestParamError();
        }

        byte[][] params = {data};

        String requestParamJSONString;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            requestParamJSONString = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new InternalIOError();
        }

        String id = computeEngineService.commitCalculate(requestParamJSONString, "mnrc");

        return computeEngineService.retriveResult(id, String.class, MAX_TIME_OUT);
    }
}
