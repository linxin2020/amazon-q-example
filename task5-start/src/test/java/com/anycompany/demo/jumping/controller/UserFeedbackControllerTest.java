package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.model.FeedbackCategory;
import com.anycompany.demo.jumping.model.User;
import com.anycompany.demo.jumping.model.UserFeedback;
import com.anycompany.demo.jumping.service.FeedbackCategoryService;
import com.anycompany.demo.jumping.service.UserFeedbackService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 用户反馈控制器测试类
 */
@AutoConfigureMockMvc
public class UserFeedbackControllerTest extends BaseTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserFeedbackService userFeedbackService;
    
    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    
    /**
     * 测试获取反馈详情
     */
    @Test
    public void testGetFeedbackById() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("测试类别");
        UserFeedback feedback = createAndSaveTestFeedback("测试反馈", category.getId(), null);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/feedback/{id}", feedback.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应，使用UTF-8编码
        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResponseResult<UserFeedback> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<UserFeedback>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertEquals(feedback.getId(), response.getData().getId());
        assertEquals(feedback.getTitle(), response.getData().getTitle());
        assertEquals(feedback.getContent(), response.getData().getContent());
        assertEquals(feedback.getEmail(), response.getData().getEmail());
        assertEquals(feedback.getCategoryId(), response.getData().getCategoryId());
    }
    
    /**
     * 测试搜索反馈列表
     */
    @Test
    public void testSearchFeedbacks() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("搜索测试类别");
        
        // 创建唯一标识符
        String uniqueKey = "特定关键词" + System.currentTimeMillis();
        
        // 创建测试反馈
        String title = "测试反馈-" + uniqueKey;
        String content = "反馈内容";
        String email = "test" + System.currentTimeMillis() + "@example.com";
        
        UserFeedback feedback = createTestFeedback(title, category.getId(), null);
        feedback.setEmail(email);
        Long feedbackId = userFeedbackService.createFeedback(
                feedback.getCategoryId(),
                feedback.getTitle(),
                feedback.getContent(),
                feedback.getEmail(),
                feedback.getUserId()
        );
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/feedback/search")
                .param("categoryId", category.getId().toString())
                .param("title", uniqueKey)
                .param("email", email)
                .param("status", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<List<UserFeedback>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<List<UserFeedback>>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().stream().anyMatch(f -> f.getId().equals(feedbackId)));
    }
    
    /**
     * 测试根据类别搜索反馈
     */
    @Test
    public void testSearchFeedbacksByCategory() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("类别搜索测试");
        UserFeedback feedback = createAndSaveTestFeedback("类别搜索反馈", category.getId(), null);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/feedback/category/{categoryId}", category.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<List<UserFeedback>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<List<UserFeedback>>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertTrue(response.getData().stream().anyMatch(f -> f.getId().equals(feedback.getId())));
    }
    
    /**
     * 测试创建反馈
     */
    @Test
    public void testCreateFeedback() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("创建反馈测试类别");
        String title = "新建测试反馈_" + System.currentTimeMillis();
        String content = "测试反馈内容";
        String email = "test" + System.currentTimeMillis() + "@example.com";
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/feedback")
                .param("categoryId", category.getId().toString())
                .param("title", title)
                .param("content", content)
                .param("email", email)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        
        // 验证数据是否正确保存
        UserFeedback feedback = userFeedbackService.loadFeedbackById(response.getData());
        assertNotNull(feedback);
        assertEquals(category.getId(), feedback.getCategoryId());
        assertEquals(title, feedback.getTitle());
        assertEquals(content, feedback.getContent());
        assertEquals(email, feedback.getEmail());
    }
    
    /**
     * 测试创建带用户ID的反馈
     */
    @Test
    public void testCreateFeedbackWithUserId() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("创建带用户反馈测试类别");
        User user = createAndSaveTestUser("feedback_user");
        String title = "新建用户反馈_" + System.currentTimeMillis();
        String content = "用户反馈内容";
        String email = "user" + System.currentTimeMillis() + "@example.com";
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/feedback")
                .param("categoryId", category.getId().toString())
                .param("title", title)
                .param("content", content)
                .param("email", email)
                .param("userId", user.getId().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        
        // 验证数据是否正确保存
        UserFeedback feedback = userFeedbackService.loadFeedbackById(response.getData());
        assertNotNull(feedback);
        assertEquals(category.getId(), feedback.getCategoryId());
        assertEquals(title, feedback.getTitle());
        assertEquals(content, feedback.getContent());
        assertEquals(email, feedback.getEmail());
        assertEquals(user.getId(), feedback.getUserId());
    }
    
    /**
     * 测试更新反馈
     */
    @Test
    public void testUpdateFeedback() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("更新反馈测试类别");
        FeedbackCategory newCategory = createAndSaveTestCategory("更新后的类别");
        UserFeedback feedback = createAndSaveTestFeedback("待更新反馈", category.getId(), null);
        
        // 更新数据
        String newTitle = "更新后的反馈_" + System.currentTimeMillis();
        String newContent = "更新后的内容";
        String newEmail = "updated" + System.currentTimeMillis() + "@example.com";
        Integer newStatus = 1;
        User user = createAndSaveTestUser("update_user");
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/feedback/{id}", feedback.getId())
                .param("categoryId", newCategory.getId().toString())
                .param("title", newTitle)
                .param("content", newContent)
                .param("email", newEmail)
                .param("status", newStatus.toString())
                .param("userId", user.getId().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertEquals(feedback.getId(), response.getData());
        
        // 验证数据是否正确更新
        UserFeedback updatedFeedback = userFeedbackService.loadFeedbackById(feedback.getId());
        assertNotNull(updatedFeedback);
        assertEquals(newCategory.getId(), updatedFeedback.getCategoryId());
        assertEquals(newTitle, updatedFeedback.getTitle());
        assertEquals(newContent, updatedFeedback.getContent());
        assertEquals(newEmail, updatedFeedback.getEmail());
        assertEquals(newStatus, updatedFeedback.getStatus());
        assertEquals(user.getId(), updatedFeedback.getUserId());
    }
    
    /**
     * 测试更新反馈状态
     */
    @Test
    public void testUpdateFeedbackStatus() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("状态更新测试类别");
        UserFeedback feedback = createAndSaveTestFeedback("待更新状态反馈", category.getId(), null);
        
        // 初始状态应为0
        assertEquals(0, feedback.getStatus());
        
        // 更新状态
        Integer newStatus = 1;
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/feedback/{id}/status", feedback.getId())
                .param("status", newStatus.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertEquals(feedback.getId(), response.getData());
        
        // 验证状态是否正确更新
        UserFeedback updatedFeedback = userFeedbackService.loadFeedbackById(feedback.getId());
        assertNotNull(updatedFeedback);
        assertEquals(newStatus, updatedFeedback.getStatus());
    }
    
    /**
     * 测试删除反馈
     */
    @Test
    public void testDeleteFeedback() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("删除测试类别");
        UserFeedback feedback = createAndSaveTestFeedback("待删除反馈", category.getId(), null);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/feedback/{id}", feedback.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertEquals(feedback.getId(), response.getData());
        
        // 验证数据是否已删除
        UserFeedback deletedFeedback = userFeedbackService.loadFeedbackById(feedback.getId());
        assertNull(deletedFeedback);
    }
    
    /**
     * 测试批量删除反馈
     */
    @Test
    public void testBatchDeleteFeedbacks() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("批量删除测试类别");
        UserFeedback feedback1 = createAndSaveTestFeedback("待批量删除反馈1", category.getId(), null);
        UserFeedback feedback2 = createAndSaveTestFeedback("待批量删除反馈2", category.getId(), null);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/feedback/batch")
                .param("ids", feedback1.getId().toString(), feedback2.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应
        String responseJson = result.getResponse().getContentAsString();
        ResponseResult<Long[]> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long[]>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().length);
        
        // 验证数据是否已删除
        assertNull(userFeedbackService.loadFeedbackById(feedback1.getId()));
        assertNull(userFeedbackService.loadFeedbackById(feedback2.getId()));
    }
} 