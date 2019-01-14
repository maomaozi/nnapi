package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.Exceptions.RequestParamError;
import com.thoughtworks.nnapi.tensorbroker.RPCArgBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;

@RestController
public class HWGanController extends RPCControllerBase {

    @CrossOrigin
    @RequestMapping(path = "/hwgan", method = RequestMethod.POST)
    public HttpEntity<String> sendMessage(@RequestBody ArrayList<Float> paramList) {

        if (paramList.size() != 132) {
            throw new RequestParamError();
        }

        String requestParamJSONString = new RPCArgBuilder().add(paramList).build();

        String id = computeEngine.commitCalculate(requestParamJSONString, "hwgan");

        return getCrossOriginResponse(computeEngine.retriveResult(id, String.class, MAX_TIME_OUT));
    }
}
