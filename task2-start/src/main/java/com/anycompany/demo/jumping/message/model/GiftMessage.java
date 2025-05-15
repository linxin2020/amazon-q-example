package com.anycompany.demo.jumping.message.model;

import java.io.Serializable;

/**
 * 礼物消息模型，用于发送礼物相关的SQS消息
 */
public class GiftMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String giftId;
    private Integer giftValue;
    private Integer amount;
    private Long activityId;
    private long timestamp;
    private String messageId;

    public GiftMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public GiftMessage(Long userId, String giftId, Integer giftValue, Integer amount, Long activityId) {
        this.userId = userId;
        this.giftId = giftId;
        this.giftValue = giftValue;
        this.amount = amount;
        this.activityId = activityId;
        this.timestamp = System.currentTimeMillis();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public Integer getGiftValue() {
        return giftValue;
    }

    public void setGiftValue(Integer giftValue) {
        this.giftValue = giftValue;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "GiftMessage{" +
                "userId=" + userId +
                ", giftId='" + giftId + '\'' +
                ", giftValue=" + giftValue +
                ", amount=" + amount +
                ", activityId=" + activityId +
                ", timestamp=" + timestamp +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
