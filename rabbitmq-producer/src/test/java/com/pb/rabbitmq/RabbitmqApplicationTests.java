package com.pb.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.pb.start.springbootstartautoconfigurae.HelloService;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@SpringBootTest
class RabbitmqApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void test() {
        System.out.println("ok1");
        JSONObject json = new JSONObject();
        json.put("a","pb");
        json.put("b","1995");
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("order-exchange","order.abcd",json,correlationData);
    }


}
