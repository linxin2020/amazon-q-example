package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 礼物控制器测试类
 */
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
public class GiftControllerTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(GiftControllerTest.class);
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * 测试送礼接口
     * 验证消息是否成功发送到SQS队列并被消费者处理
     */
    @Test
    public void testSendGift(CapturedOutput output) throws Exception {
        // 测试参数
        Long userId = 1001L;
        String giftId = "2001";
        Integer giftValue = 100;
        Integer amount = 5;
        Integer activityId = 1;

        // 构建JSON请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);
        requestBody.put("gift_id", giftId);
        requestBody.put("gift_value", giftValue);
        requestBody.put("activity_id", activityId);
        requestBody.put("amount", amount);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/gifts/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应，使用UTF-8编码
        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<Map<String, Object>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Map<String, Object>>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        
        // 验证响应数据包含必要字段
        Map<String, Object> responseData = response.getData();
        assertNotNull(responseData.get("message_id"));
        assertEquals(userId.intValue(), responseData.get("user_id")); // 修正类型比较
        assertEquals(giftId, responseData.get("gift_id")); // 修正类型比较
        assertNotNull(responseData.get("timestamp"));
        
        // 获取消息ID
        String messageId = responseData.get("message_id").toString();
        
        // 验证日志输出包含发送消息的记录
        String expectedSendLogMessage = "礼物消息发送成功: messageId=" + messageId;
        
        // 等待足够的时间确保消息被消费者处理
        // 这里增加等待时间，确保SqsMessageConsumer有足够时间处理消息
        Thread.sleep(5000);
        
        // 检查日志输出是否包含消息发送记录
        boolean messageSendLogged = output.getOut().contains(expectedSendLogMessage);
        
        // 检查日志输出是否包含消息处理记录
        // 使用更精确的验证方式，确保处理的是我们发送的特定消息
        boolean messageProcessLogged = false;
        
        // 验证userId和giftId是否在日志中被正确处理
        String expectedProcessLogPattern = String.format("解析出礼物消息：userId=%d, giftId=%s", userId, giftId);
        messageProcessLogged = output.getOut().contains(expectedProcessLogPattern);
        
        // 如果测试失败，输出完整日志以便调试
        if (!messageSendLogged || !messageProcessLogged) {
            logger.error("未找到预期的日志记录");
            logger.debug("捕获的输出内容: {}", output.getOut());
            logger.debug("期望找到的发送记录: {}", expectedSendLogMessage);
            logger.debug("期望找到的处理记录: {}", expectedProcessLogPattern);
        }
        
        // 验证消息发送和处理都成功记录
        assertTrue(messageSendLogged, "日志中应包含消息发送记录");
        assertTrue(messageProcessLogged, "日志中应包含特定消息的处理记录，包含正确的userId和giftId");
        
        logger.info("成功验证礼物消息已发送并被处理: messageId={}, userId={}, giftId={}", 
                messageId, userId, giftId);
    }
    
    /**
     * 测试送礼接口参数验证
     * 验证当参数无效时是否返回适当的错误响应
     */
    @Test
    public void testSendGiftWithInvalidParams() throws Exception {
        // 测试无效的用户ID
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", 0);
        requestBody.put("gift_id", "2001");
        requestBody.put("gift_value", 100);
        requestBody.put("amount", 5);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/gifts/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 返回200状态码，但响应中包含错误信息
                .andReturn();
                
        // 解析响应，验证错误消息
        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<?> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<?>>() {});
                
        // 验证是错误响应
        assertEquals(ErrorCode.PARAMETER_ERROR.getCode(), response.getCode(), "应返回参数错误码");
        assertNull(response.getData());
        assertTrue(response.getMessage().contains("用户ID"));
    }
}
