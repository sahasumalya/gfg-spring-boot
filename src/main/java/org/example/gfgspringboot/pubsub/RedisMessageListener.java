package org.example.gfgspringboot.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
       System.out.println("recieved:"+ new String(message.getBody())+" "+Thread.currentThread().getName());
    }
}
