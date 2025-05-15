package com.anycompany.demo.jumping.message.sender;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.anycompany.demo.jumping.message.model.SqsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(SqsMessageSender.class);
    
    private final AmazonSQS amazonSQS;
    private final ObjectMapper objectMapper;
    
    @Value("${aws.sqs.endpoint}")
    private String queueUrl;
    
    @Autowired
    public SqsMessageSender(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
        this.objectMapper = new ObjectMapper();
    }
    
    public SendMessageResult sendMessage(SqsMessage message) {
        try {
            String messageBody = objectMapper.writeValueAsString(message);
            
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);
            
            SendMessageResult result = amazonSQS.sendMessage(sendMessageRequest);
            logger.info("消息已发送到SQS, MessageId: {}", result.getMessageId());
            
            message.setMessageId(result.getMessageId());
            return result;
        } catch (JsonProcessingException e) {
            logger.error("消息序列化失败", e);
            throw new RuntimeException("消息序列化失败", e);
        }
    }
} 