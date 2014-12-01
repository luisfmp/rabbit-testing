package com.globant.test.rabbitmq;

import junit.framework.TestCase;

import com.globant.test.rabbitmq.publishsuscribe.EmitLog;
import com.globant.test.rabbitmq.publishsuscribe.ReceiveLogs;

public class TestPublishSuscribe extends TestCase {
	
	EmitLog emisor;
	ReceiveLogs receptor1;
	ReceiveLogs receptor2;
	
	protected void setUp() throws Exception {
		emisor = new EmitLog();
		receptor1 = new ReceiveLogs();
		receptor2 = new ReceiveLogs();
	}
	
	public void testPublishSuscribe() throws Exception{
		receptor1.start();
		receptor2.start();
		
		for (int i=0; i<20;i++){
			emisor.main("Log" + i);
			Thread.sleep(1000);
		}
	}

}
