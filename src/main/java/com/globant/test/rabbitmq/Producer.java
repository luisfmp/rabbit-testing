/**
 * 
 */
package com.globant.test.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * @author luis.martinez
 *
 */
public class Producer {
	private String queue_name;
	private ConnectionFactory factory;
	
	/**
	 * @param queue_name
	 * @param host
	 */
	public Producer(String queue_name, String host) {
		super();
		this.queue_name = queue_name;
		factory = new ConnectionFactory();
		factory.setHost(host);
	}
	
	/**
	 * Envia un mensaje a la cola de RabbitMQ
	 * @param message
	 * @throws IOException
	 */
	public void send(String message) throws IOException{
		Connection connection = this.factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue_name,false,false,false,null);
		channel.basicPublish("", queue_name, null, message.getBytes());
		System.out.println("Mensaje enviado:" + message);
		channel.close();
		connection.close();
	}
}
