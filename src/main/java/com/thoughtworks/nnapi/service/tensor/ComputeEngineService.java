package com.thoughtworks.nnapi.service.tensor;

import org.springframework.amqp.core.Message;

public interface ComputeEngineService {
    /*
     * Commit calculate request and return string id
     * */
    String commitCalculate(String data, String routeKey);

    /*
     *
     * */
    void resultCallback(Message message);

    /*
     *
     * */
    <T> T retriveResult(String id, Class<T> type, long timeout);
}
