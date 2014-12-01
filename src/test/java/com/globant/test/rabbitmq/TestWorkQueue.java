package com.globant.test.rabbitmq;

import junit.framework.TestCase;

import com.globant.test.rabbitmq.workqueue.Recv;
import com.globant.test.rabbitmq.workqueue.Send;

public class TestWorkQueue extends TestCase {
	
	Send sender;
	Recv receiver;
	
	protected void setUp() throws Exception {
		sender = new Send();
		receiver = new Recv();
	}
	
	public void testWorkQueue() throws InterruptedException{
		receiver.start();
		receiver.start();
		sender.start("Message...");
		sender.start("Message.....");
		sender.start("Message.....");
		sender.start("Message.");
		sender.start("Message..");
		Thread.sleep(20000);
	}
}
