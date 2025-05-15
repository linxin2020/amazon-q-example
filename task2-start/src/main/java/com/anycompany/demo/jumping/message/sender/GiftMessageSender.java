package com.anycompany.demo.jumping.message.sender;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.anycompany.demo.jumping.message.model.GiftMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 礼物消息发送器，负责将礼物消息发送到SQS队列
 */
@Component
public class GiftMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(GiftMessageSender.class);
    
    private final AmazonSQS amazonSQS;
    private final ObjectMapper objectMapper;
    
    @Value("${aws.sqs.endpoint}")
    private String queueUrl;
    
    @Autowired
    public GiftMessageSender(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * 发送礼物消息到SQS队列
     * 
     * @param message 礼物消息对象
     * @return SQS发送结果
     */
    public SendMessageResult sendGiftMessage(GiftMessage message) {
        try {
            // 将GiftMessage序列化为JSON字符串
            String giftMessageJson = objectMapper.writeValueAsString(message);
            
            // 创建SqsMessage对象并包装GiftMessage
            com.anycompany.demo.jumping.message.model.SqsMessage sqsMessage = new com.anycompany.demo.jumping.message.model.SqsMessage();
            sqsMessage.setContent(giftMessageJson);
            sqsMessage.setTimestamp(System.currentTimeMillis());
            
            // 序列化SqsMessage对象
            String messageBody = objectMapper.writeValueAsString(sqsMessage);
            
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);
            
            SendMessageResult result = amazonSQS.sendMessage(sendMessageRequest);
            logger.info("礼物消息已发送到SQS, MessageId: {}, 用户ID: {}, 礼物ID: {}", 
                    result.getMessageId(), message.getUserId(), message.getGiftId());
            
            message.setMessageId(result.getMessageId());
            sqsMessage.setMessageId(result.getMessageId());
            return result;
        } catch (JsonProcessingException e) {
            logger.error("礼物消息序列化失败", e);
            throw new RuntimeException("礼物消息序列化失败", e);
        }
    }
}
