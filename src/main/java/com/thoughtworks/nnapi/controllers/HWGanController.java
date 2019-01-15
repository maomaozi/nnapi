package com.thoughtworks.nnapi.controllers;

import com.thoughtworks.nnapi.controllers.Exceptions.RequestParamError;
import com.thoughtworks.nnapi.service.tensor.RPCArgBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class HWGanController extends RPCControllerBase {

    private static final int PARAM_VALID_LENGTH = 132;

    @CrossOrigin
    @RequestMapping(path = "/hwgan", method = RequestMethod.POST)
    public String hwgan(@RequestBody ArrayList<Float> paramList) {

        if (paramList.size() != PARAM_VALID_LENGTH) {
            throw new RequestParamError();
        }

        String requestParamJSONString = new RPCArgBuilder().add(paramList).build();

        String id = computeEngineService.commitCalculate(requestParamJSONString, "hwgan");

        return computeEngineService.retriveResult(id, String.class, MAX_TIME_OUT);
    }
}
