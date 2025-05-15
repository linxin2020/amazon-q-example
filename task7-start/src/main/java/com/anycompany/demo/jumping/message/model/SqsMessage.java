package com.anycompany.demo.jumping.message.model;

import java.io.Serializable;

public class SqsMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String messageId;
    private String content;
    private long timestamp;
    
    public SqsMessage() {
    }
    
    public SqsMessage(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "SqsMessage{" +
                "messageId='" + messageId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
} 