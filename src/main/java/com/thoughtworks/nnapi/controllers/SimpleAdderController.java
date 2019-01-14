package com.thoughtworks.nnapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SimpleAdderController extends RPCControllerBase {

    @RequestMapping(method = RequestMethod.GET, path = "/add")
    HttpEntity<String> sendMsg(
            @RequestParam(name = "lhs") int lhs,
            @RequestParam(name = "rhs") int rhs
    ) {

        ObjectMapper objectMapper = new ObjectMapper();

        int[] requestParamArray = {lhs, rhs};

        String requestParamJSONString;

        try {
            requestParamJSONString = objectMapper.writeValueAsString(requestParamArray);
        } catch (JsonProcessingException e) {
            return null;
        }

        String id = computeEngine.commitCalculate(requestParamJSONString, "add");

        return getCrossOriginResponse(computeEngine.retriveResult(id, String.class, MAX_TIME_OUT));
    }
}
