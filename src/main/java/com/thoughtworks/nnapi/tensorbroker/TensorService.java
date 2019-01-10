package com.thoughtworks.nnapi.tensorbroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TensorService {

    private static Map<String, Class> registedClz = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TensorService.class);

    public static ComputeEngine getInstance(String serviceName) throws ClassNotFoundException {

        Class clz = registedClz.get(serviceName);

        if (clz == null) {
            clz = Class.forName(serviceName);
            regist(clz);
        }

        try {
            return (ComputeEngine) clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw new ClassNotFoundException(String.format("Error while instantiation class %s", serviceName));
        }
    }

    private static void regist(Class clz) throws ClassNotFoundException {
        if (Arrays.asList(clz.getInterfaces()).contains(ComputeEngine.class)) {
            registedClz.put(clz.getName(), clz);
        } else {
            throw new ClassNotFoundException(String.format("%s is not an implement of ComputeEngine", clz.getName()));
        }
    }

    private static void unregist(Class clz) {
        registedClz.remove(clz.getName());
    }
}
