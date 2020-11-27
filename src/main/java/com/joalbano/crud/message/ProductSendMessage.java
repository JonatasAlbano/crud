package com.joalbano.crud.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.joalbano.crud.dto.ProductDTO;

@Component
public class ProductSendMessage {
	
	@Value("${crud.rabbitmq.exchange}")
	String exchange;
	
	@Value("${crud.rabbitmq.routingkey}")
	String routingkey;
	
	public final RabbitTemplate rabbitTemplate;
	
	@Autowired
	public ProductSendMessage(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(ProductDTO productDTO) {
		rabbitTemplate.convertAndSend(exchange, routingkey, productDTO);
	}

}
