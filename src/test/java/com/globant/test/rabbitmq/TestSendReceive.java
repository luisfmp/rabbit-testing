package com.globant.test.rabbitmq;

import java.io.IOException;

import junit.framework.Assert;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class TestSendReceive {

	private String message = "prueba";
	private String queue = "prueba";
	private String host = "localhost";

	public void test_send_receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Producer sender = new Producer(queue, host);
		Consumer reader = new Consumer(queue, host);
		sender.send(this.message);
		String messageRead = reader.getMessage();
		Assert.assertEquals(message, messageRead);
	}
}
