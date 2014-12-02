package com.globant.test.rabbitmq.publishsuscribe;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLog {

  public void main(String exchange_type, String exchange_name, String message, String severity) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

   	channel.exchangeDeclare(exchange_name, exchange_type);

    channel.basicPublish(exchange_name, severity, null, message.getBytes());
    System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

    channel.close();
    connection.close();
  }
  
}

