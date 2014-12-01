package com.globant.test.rabbitmq.workqueue;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {

	private final static String QUEUE_NAME = "hello";

	private void execute(String param) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = param;
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

	public void start(final String param) {
		Thread thread = new Thread() {
			public void run() {
				Send sender = new Send();
				try {
					sender.execute(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}