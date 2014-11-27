package com.globant.test.rabbitmq;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class TestSendReceive {

	private String message = "prueba";
	private String queue = "prueba";
	private String host = "localhost";

	@Test
	public void test_send_receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Producer sender = new Producer(queue, host);
		Consumer reader = new Consumer(queue, host);
		sender.send(this.message);
		String messageReaded = reader.getMessage();
		Assert.assertEquals(message, messageReaded);
	}
}
