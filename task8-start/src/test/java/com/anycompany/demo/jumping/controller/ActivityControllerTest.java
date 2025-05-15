package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.common.exception.ActivityErrorCode;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.service.ActivityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 活动控制器测试类
 */
@AutoConfigureMockMvc
public class ActivityControllerTest extends BaseTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ActivityService activityService;
    
    /**
     * 测试获取活动信息 - 成功场景
     */
    @Test
    public void testGetActivityInfo_Success() throws Exception {
        // 创建测试活动
        String activityName = "测试活动_" + System.currentTimeMillis();
        Date startTime = getDateAfterDays(-1); // 昨天开始
        Date endTime = getDateAfterDays(7);    // 一周后结束
        Integer totalCells = 30;
        Integer dailyGameLimit = 120;
        Integer maxDicePerTime = 10;
        
        Long activityId = activityService.createActivity(
                activityName, startTime, endTime, totalCells, dailyGameLimit, maxDicePerTime);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/activity/info")
                .param("activityId", activityId.toString())
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
        
        // 验证返回的活动信息
        Map<String, Object> activityInfo = response.getData();
        assertEquals(activityId.intValue(), activityInfo.get("activityId"));
        assertEquals(activityName, activityInfo.get("activityName"));
        
        // 验证活动状态
        assertEquals(1, activityInfo.get("status")); // 应该是进行中状态
        
        // 验证日期 (不直接比较Date对象，因为JSON序列化会改变格式)
        assertNotNull(activityInfo.get("startTime"));
        assertNotNull(activityInfo.get("endTime"));
    }
    
    /**
     * 测试获取活动信息 - 活动不存在
     */
    @Test
    public void testGetActivityInfo_NotFound() throws Exception {
        // 使用一个不存在的活动ID
        Long nonExistentActivityId = 999999L;
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/activity/info")
                .param("activityId", nonExistentActivityId.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 业务异常也返回200状态码
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<Map<String, Object>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Map<String, Object>>>() {});
        
        // 验证响应是错误的
        assertNotEquals(200, response.getCode());
        assertEquals(ActivityErrorCode.ACTIVITY_NOT_FOUND.getCode(), response.getCode()); // ACTIVITY_NOT_FOUND错误码
        assertNull(response.getData());
        assertTrue(response.getMessage().contains("活动不存在"));
    }
    
    /**
     * 测试获取活动信息 - 参数校验失败
     */
    @Test
    public void testGetActivityInfo_InvalidParam() throws Exception {
        // 执行测试 - 不提供activityId参数
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/activity/info")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // 参数缺失应该返回400
                .andReturn();
        
        // 执行测试 - 提供无效的activityId参数
        result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/activity/info")
                .param("activityId", "0") // 无效ID (小于等于0)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 业务异常返回200
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<Map<String, Object>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Map<String, Object>>>() {});
        
        // 验证响应是错误的
        assertNotEquals(200, response.getCode());
        assertNull(response.getData());
        assertTrue(response.getMessage().contains("活动不存在"));
    }
    
    /**
     * 测试获取活动信息 - 不同状态的活动
     */
    @Test
    public void testGetActivityInfo_DifferentStatus() throws Exception {
        // 创建未开始的活动
        String futureName = "未开始活动_" + System.currentTimeMillis();
        Date futureStart = getDateAfterDays(1); // 明天开始
        Date futureEnd = getDateAfterDays(8);   // 8天后结束
        Long futureActivityId = activityService.createActivity(
                futureName, futureStart, futureEnd, 30, 120, 10);
        
        // 创建已结束的活动
        String pastName = "已结束活动_" + System.currentTimeMillis();
        Date pastStart = getDateAfterDays(-10); // 10天前开始
        Date pastEnd = getDateAfterDays(-1);    // 昨天结束
        Long pastActivityId = activityService.createActivity(
                pastName, pastStart, pastEnd, 30, 120, 10);
        
        // 测试未开始的活动
        MvcResult futureResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/activity/info")
                .param("activityId", futureActivityId.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        String futureJson = futureResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<Map<String, Object>> futureResponse = objectMapper.readValue(
                futureJson, new TypeReference<ResponseResult<Map<String, Object>>>() {});
        
        assertEquals(ErrorCode.SUCCESS.getCode(), futureResponse.getCode());
        assertEquals(0, futureResponse.getData().get("status")); // 未开始状态
        
        // 测试已结束的活动
        MvcResult pastResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/activity/info")
                .param("activityId", pastActivityId.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        String pastJson = pastResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<Map<String, Object>> pastResponse = objectMapper.readValue(
                pastJson, new TypeReference<ResponseResult<Map<String, Object>>>() {});
        
        assertEquals(ErrorCode.SUCCESS.getCode(), pastResponse.getCode());
        assertEquals(2, pastResponse.getData().get("status")); // 已结束状态
    }
    
    /**
     * 获取指定天数后的日期
     * 
     * @param days 天数，可以为负数表示过去的日期
     * @return 计算后的日期
     */
    private Date getDateAfterDays(int days) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}
