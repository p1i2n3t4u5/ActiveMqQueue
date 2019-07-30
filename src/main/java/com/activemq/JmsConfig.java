package com.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	
	@Value("${spring.activemq.user}")
	private String username;
	
	@Value("${spring.activemq.password}")
	private String password;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setPassword(password);
		connectionFactory.setUserName(username);
		return connectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		//below property determines where to send the message topic or queue
		//template.setPubSubDomain(true);
		return template;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		//below property determines where to send the message topic or queue
		//factory.setPubSubDomain(true);
		factory.setConcurrency("1-1");
		return factory;
	}

}