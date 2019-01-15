package com.thoughtworks.nnapi.service.tensor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class RPCArgBuilder {

    private List<Object> args;

    private RPCArgBuilder(List<Object> args) {
        this.args = args;
    }

    public RPCArgBuilder() {
        args = new ArrayList<>();
    }

    public RPCArgBuilder add(Object obj) {
        args.add(obj);
        return new RPCArgBuilder(args);
    }

    public String build() {
        String requestParamJSONString = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            requestParamJSONString = objectMapper.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            return null;
        }

        return requestParamJSONString;
    }
}
