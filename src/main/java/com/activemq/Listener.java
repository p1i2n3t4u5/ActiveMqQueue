package com.activemq;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class Listener {
	
	  private CountDownLatch latch = new CountDownLatch(1);

	  public CountDownLatch getLatch() {
	    return latch;
	  }

	@JmsListener(destination = "inbound.queue")
	@SendTo("outbound.queue")
	public String receiveMessage(final Message jsonMessage) throws JMSException {
		String messageData = null;
		System.out.println("Received message " + jsonMessage);
		String response = null;
		if (jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) jsonMessage;
			messageData = textMessage.getText();
			Map<String, String> map = new Gson().fromJson(messageData, Map.class);
			response = "Hello This is a Queue " + map.get("name");
			System.out.println(response);
			latch.countDown();
		}
		return response;
	}
	
	

}
