package com.dgc.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

	@Value("${kafka.group.id}")
	private String groupId;

	@Value("${kafka.reply.topic}")
	private String replyTopic;

	@Bean
	public ReplyingKafkaTemplate<String, Result, Result> replyingKafkaTemplate(ProducerFactory<String, Result> pf,
			ConcurrentKafkaListenerContainerFactory<String, Result> factory) {
		ConcurrentMessageListenerContainer<String, Result> replyContainer = factory.createContainer(replyTopic);
		replyContainer.getContainerProperties().setMissingTopicsFatal(false);
		replyContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<>(pf, replyContainer);
	}

}