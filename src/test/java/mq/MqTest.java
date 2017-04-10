package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by lupf on 2016/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/application.xml")
public class MqTest {

    /**
     * ????mq????????queue?????exchange?routingKey
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testConsumer() throws IOException, InterruptedException {


        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.200.51");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("oriental");
        connectionFactory.setPassword("oriental");
        connectionFactory.setVirtualHost("oriental_webgate");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
//        channel.exchangeDeclare("e_settlement_notify_cache", "fanout");
        /**
         * ????????
         */
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "e_notify_cache", "");


        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(queueName, true, queueingConsumer);

        System.out.println("--------------wait-----------------");
        //????
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            String message = new String(delivery.getBody());

            System.out.println("--------------------" + message);
        }

    }
}
