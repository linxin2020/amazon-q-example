package com.anycompany.demo.jumping.message.consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.anycompany.demo.jumping.message.model.GiftMessage;
import com.anycompany.demo.jumping.message.model.SqsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SqsMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SqsMessageConsumer.class);
    
    private final AmazonSQS amazonSQS;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;
    
    @Value("${aws.sqs.endpoint}")
    private String queueUrl;
    
    @Value("${app.message.consumer.enabled:true}")
    private boolean consumerEnabled;
    
    private volatile boolean running = true;
    
    @Autowired
    public SqsMessageConsumer(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
        this.objectMapper = new ObjectMapper();
        this.executorService = Executors.newSingleThreadExecutor();
    }
    
    @PostConstruct
    public void start() {
        if (!consumerEnabled) {
            logger.info("SQS消息消费者已禁用，不会启动");
            return;
        }
        
        logger.info("启动SQS消息消费者，队列URL：{}", queueUrl);
        
        executorService.submit(() -> {
            while (running) {
                try {
                    ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                            .withQueueUrl(queueUrl)
                            .withMaxNumberOfMessages(10)
                            .withWaitTimeSeconds(10);
                    
                    List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
                    
                    for (Message message : messages) {
                        try {
                            // 尝试将消息解析为我们期望的格式
                            try {
                                SqsMessage sqsMessage = objectMapper.readValue(message.getBody(), SqsMessage.class);
                                logger.info("收到SQS消息：{}", sqsMessage);
                                
                                // 在这里处理消息
                                processMessage(sqsMessage);
                            } catch (Exception e) {
                                // 如果解析失败，尝试直接处理原始消息
                                logger.info("收到非标准格式SQS消息：{}", message.getBody());
                                processRawMessage(message.getBody());
                            }
                            
                            // 处理完成后删除消息
                            amazonSQS.deleteMessage(new DeleteMessageRequest()
                                    .withQueueUrl(queueUrl)
                                    .withReceiptHandle(message.getReceiptHandle()));
                            
                        } catch (Exception e) {
                            logger.error("处理SQS消息失败", e);
                        }
                    }
                } catch (Exception e) {
                    logger.error("接收SQS消息失败", e);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }
    
    // 处理消息的具体逻辑
    private void processMessage(SqsMessage message) {
        // 对于测试目的，暂时空实现，只要能收到消息即可
        logger.info("正在处理消息：{}", message.getContent());
        
        try {
            // 尝试将content解析为GiftMessage
            GiftMessage giftMessage = objectMapper.readValue(message.getContent(), GiftMessage.class);
            logger.info("解析出礼物消息：userId={}, giftId={}, giftValue={}, amount={}, activityId={}", 
                    giftMessage.getUserId(), giftMessage.getGiftId(), 
                    giftMessage.getGiftValue(), giftMessage.getAmount(), giftMessage.getActivityId());
            
            // 这里可以添加处理礼物消息的业务逻辑
            
        } catch (Exception e) {
            logger.warn("无法将消息内容解析为GiftMessage: {}", e.getMessage());
        }
    }
    
    // 处理原始格式消息
    private void processRawMessage(String rawMessage) {
        // 对于测试目的，暂时空实现，只要能收到消息即可
        logger.info("正在处理原始消息：{}", rawMessage);
    }
    
    public void stop() {
        running = false;
        executorService.shutdown();
    }
} 