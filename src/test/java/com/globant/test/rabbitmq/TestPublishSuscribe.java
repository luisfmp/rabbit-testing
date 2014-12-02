package com.globant.test.rabbitmq;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.globant.test.rabbitmq.publishsuscribe.EmitLog;
import com.globant.test.rabbitmq.publishsuscribe.ReceiveLogs;

public class TestPublishSuscribe extends TestCase {
	
	EmitLog emisor;
	ReceiveLogs receptor1;
	ReceiveLogs receptor2;

	public void testPublishSuscribe() throws Exception{
		String exchange_name = "ps";
		String exchange_type = "fanout";
		emisor = new EmitLog();
		receptor1 = new ReceiveLogs();
		receptor2 = new ReceiveLogs();
		String[] blank = {""};
		receptor1.start(exchange_type,exchange_name,blank);
		receptor2.start(exchange_type,exchange_name,blank);
		
		for (int i=0; i<10;i++){
			emisor.main(exchange_type,exchange_name,"Log" + i,"");
			Thread.sleep(1000);
		}
	}
	
	public void testRouting() throws Exception{
		String exchange_name = "routing";
		String exchange_type = "direct";
		emisor = new EmitLog();
		receptor1 = new ReceiveLogs();
		receptor2 = new ReceiveLogs();
		String[] severities1 = {"info","warning"};
		String[] severities2 = {"warning","error"};
		receptor1.start(exchange_type,exchange_name,severities1);
		receptor2.start(exchange_type,exchange_name,severities2);
		ArrayList<String> severities = new ArrayList<String>();
		severities.add("info");
		severities.add("warning");
		severities.add("error");
		
		for (int i=1; i<=10;i++){
			emisor.main(exchange_type,exchange_name,"Log" + i,severities.get(i%3));
			Thread.sleep(1000);
		}
	}

	public void testTopic() throws Exception{
		String exchange_name = "topics";
		String exchange_type = "topic";
		String[] routingKey1 = {"kern.*"};
		String[] routingKey2 = {"kern.*", "*.critical"};
		emisor = new EmitLog();
		receptor1 = new ReceiveLogs();
		receptor2 = new ReceiveLogs();
		receptor1.start(exchange_type,exchange_name,routingKey1);
		receptor2.start(exchange_type,exchange_name,routingKey2);
		receptor2.start(exchange_type,exchange_name,new String[]{"#"});
		
		Thread.sleep(1000);
		emisor.main(exchange_type,exchange_name,"A critical kernel error","kern.critical");
		Thread.sleep(1000);
		emisor.main(exchange_type,exchange_name,"General Error","bla.bla.bla");
		Thread.sleep(1000);
		emisor.main(exchange_type,exchange_name,"A critical browser error","browser.critical");
		Thread.sleep(1000);
		emisor.main(exchange_type,exchange_name,"A normal X11 error","x11.error");
	}
}
