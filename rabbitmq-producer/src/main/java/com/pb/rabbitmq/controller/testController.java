package com.pb.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController  //代表controller
@RequestMapping("/testC")
public class testController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public void test(@RequestBody JSONObject jsonObject) {

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);


        System.out.println("ok");
        JSONObject json = new JSONObject();
        json.put("a","pb");
        json.put("b","1995");
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("order-exchange","order.abcd",json,correlationData);
        System.out.println("ok");
    }



    /**
     * 回调函数: confirm确认
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("发送成功");
        }
    };

    /**
     * 回调函数: return返回
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            System.out.println("发送失败");
        }
    };
}
