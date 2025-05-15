package com.anycompany.demo.jumping.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.message.model.GiftMessage;
import com.anycompany.demo.jumping.message.sender.GiftMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 礼物控制器，处理礼物相关的请求
 */
@RestController
@RequestMapping("/api/v1/gifts")
public class GiftController {

    private static final Logger logger = LoggerFactory.getLogger(GiftController.class);
    
    private final GiftMessageSender giftMessageSender;
    
    @Autowired
    public GiftController(GiftMessageSender giftMessageSender) {
        this.giftMessageSender = giftMessageSender;
    }
    
    /**
     * 模拟送礼接口
     * 
     * @param giftRequest 包含送礼信息的请求体
     * @return 响应结果
     */
    @PostMapping("/send")
    public ResponseResult<Map<String, Object>> sendGift(@RequestBody Map<String, Object> giftRequest) {
        
        Long userId = getLongValue(giftRequest, "user_id");
        String giftId = giftRequest.get("gift_id") != null ? giftRequest.get("gift_id").toString() : null;
        Integer giftValue = getIntegerValue(giftRequest, "gift_value");
        Integer amount = getIntegerValue(giftRequest, "amount");
        Long activityId = getLongValue(giftRequest, "activity_id");
        
        logger.info("收到送礼请求: userId={}, giftId={}, giftValue={}, amount={}, activityId={}", 
                userId, giftId, giftValue, amount, activityId);
        
        // 参数验证
        if (userId == null || userId <= 0) {
            return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), "用户ID不能为空且必须大于0");
        }
        if (giftId == null || giftId.isEmpty()) {
            return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), "礼物ID不能为空");
        }
        if (giftValue == null || giftValue <= 0) {
            return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), "礼物价值不能为空且必须大于0");
        }
        if (amount == null || amount <= 0) {
            return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), "礼物数量不能为空且必须大于0");
        }
        if (activityId == null || activityId <= 0) {
            return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), "活动ID不能为空且必须大于0");
        }
        
        try {
            // 创建礼物消息
            GiftMessage giftMessage = new GiftMessage(userId, giftId, giftValue, amount, activityId);
            
            // 发送到SQS队列
            SendMessageResult result = giftMessageSender.sendGiftMessage(giftMessage);
            
            // 构建响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("message_id", result.getMessageId());
            responseData.put("user_id", userId);
            responseData.put("gift_id", giftId);
            responseData.put("activity_id", activityId);
            responseData.put("timestamp", giftMessage.getTimestamp());
            
            logger.info("礼物消息发送成功: messageId={}", result.getMessageId());
            return ResponseResult.success(responseData);
            
        } catch (Exception e) {
            logger.error("送礼失败", e);
            return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), "送礼失败: " + e.getMessage());
        }
    }
    
    /**
     * 从请求体中获取Long类型的值
     */
    private Long getLongValue(Map<String, Object> requestMap, String key) {
        Object value = requestMap.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 从请求体中获取Integer类型的值
     */
    private Integer getIntegerValue(Map<String, Object> requestMap, String key) {
        Object value = requestMap.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
