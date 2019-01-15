package com.thoughtworks.nnapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.nnapi.controllers.Exceptions.InternalIOError;
import org.springframework.web.bind.annotation.*;


@RestController
public class SimpleAdderController extends RPCControllerBase {

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/add")
    String sendMsg(
            @RequestParam(name = "lhs") int lhs,
            @RequestParam(name = "rhs") int rhs
    ) {

        ObjectMapper objectMapper = new ObjectMapper();

        int[] requestParamArray = {lhs, rhs};

        String requestParamJSONString;

        try {
            requestParamJSONString = objectMapper.writeValueAsString(requestParamArray);
        } catch (JsonProcessingException e) {
            throw new InternalIOError();
        }

        String id = computeEngineService.commitCalculate(requestParamJSONString, "add");

        return computeEngineService.retriveResult(id, String.class, MAX_TIME_OUT);
    }
}
