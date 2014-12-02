package com.globant.test.rabbitmq.publishsuscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogs {

  public void execute(String exchange_type, String exchange_name, String[] severities) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

   	channel.exchangeDeclare(exchange_name, exchange_type);
    
    String queueName = channel.queueDeclare().getQueue();
    
    for(String severity : severities){
        channel.queueBind(queueName, exchange_name, severity);
    }
    
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName, true, consumer);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      String routingKey = delivery.getEnvelope().getRoutingKey();

      System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
    }
  }
  
  public void start(final String exchange_type,final String exchange_name, final String[] severities) {
		Thread thread = new Thread() {
			public void run() {
				ReceiveLogs receiver = new ReceiveLogs();
				try {
					receiver.execute(exchange_type, exchange_name,severities);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}
