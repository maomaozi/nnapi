package com.thoughtworks.nnapi.tensorbroker;

import org.springframework.amqp.core.Message;

public interface ComputeEngine {
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
