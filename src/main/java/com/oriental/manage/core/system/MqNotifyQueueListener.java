package com.oriental.manage.core.system;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by lupf on 2016/11/23.
 */
@Component
public class MqNotifyQueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        String table = null;
        try {
            table = new String(message.getBody(), "UTF-8");
            System.out.println(table);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
