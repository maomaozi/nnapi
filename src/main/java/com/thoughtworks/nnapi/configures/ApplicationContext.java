package com.thoughtworks.nnapi.configures;


import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {

    private static final String RECV_METHOD_NAME = "receiveMessage";

    @Value("${amqp.queuename}")
    private String amqpQueueName;

    @Value("${amqp.exchangename}")
    private String amqpDirectExchangeName;

    @Value("${amqp.connect.host}")
    private String amqpHost;

    @Value("${amqp.connect.port}")
    private int amqpPort;

    @Value("${amqp.connect.user}")
    private String amqpUser;

    @Value("${amqp.connect.password}")
    private String amqpPassword;


    /*
     * 配置MQ连接信息
     * */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(amqpHost, amqpPort);
        cachingConnectionFactory.setUsername(amqpUser);
        cachingConnectionFactory.setPassword(amqpPassword);

        return cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);

        // 在RPC异步接收的过程中消费者的数量
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);

        return factory;
    }
}
