/**
 * 
 */
package com.globant.test.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * @author luis
 *
 */
public class Consumer {
	private String queue_name;
	private ConnectionFactory factory;

	/**
	 * @param queue_name
	 */
	public Consumer(String queue_name, String host) {
		super();
		this.queue_name = queue_name;
		factory = new ConnectionFactory();
		factory.setHost(host);
	}

	public String getMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connection = this.factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue_name, false, false, false, null);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queue_name, true, consumer);
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		String message = new String(delivery.getBody());
		System.out.println("Mensaje leído: " + message);
		return message;
	}
}
