package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.model.FeedbackCategory;
import com.anycompany.demo.jumping.service.FeedbackCategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 反馈类别控制器测试类
 */
@AutoConfigureMockMvc
public class FeedbackCategoryControllerTest extends BaseTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    
    /**
     * 测试获取类别详情
     */
    @Test
    public void testGetCategoryById() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("测试类别");
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/feedback/categories/{id}", category.getId())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<FeedbackCategory> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<FeedbackCategory>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertEquals(category.getId(), response.getData().getId());
        assertEquals(category.getName(), response.getData().getName());
    }
    
    /**
     * 测试获取所有类别
     */
    @Test
    public void testGetAllCategories() throws Exception {
        // 创建测试数据
        FeedbackCategory category1 = createAndSaveTestCategory("测试类别1");
        FeedbackCategory category2 = createAndSaveTestCategory("测试类别2");
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/feedback/categories")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<List<FeedbackCategory>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<List<FeedbackCategory>>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertTrue(response.getData().stream().anyMatch(c -> c.getId().equals(category1.getId())));
        assertTrue(response.getData().stream().anyMatch(c -> c.getId().equals(category2.getId())));
    }
    
    /**
     * 测试获取所有启用的类别
     */
    @Test
    public void testGetEnabledCategories() throws Exception {
        // 创建测试数据 - 启用状态
        FeedbackCategory enabledCategory = createAndSaveTestCategory("启用类别");
        
        // 创建测试数据 - 禁用状态
        FeedbackCategory category = createTestCategory("禁用类别");
        category.setStatus(0);
        Long disabledId = feedbackCategoryService.createCategory(
                category.getName(), category.getSortOrder(), category.getStatus());
        FeedbackCategory disabledCategory = feedbackCategoryService.loadCategoryById(disabledId);
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/feedback/categories/enabled")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<List<FeedbackCategory>> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<List<FeedbackCategory>>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertTrue(response.getData().stream().anyMatch(c -> c.getId().equals(enabledCategory.getId())));
        assertFalse(response.getData().stream().anyMatch(c -> c.getId().equals(disabledCategory.getId())));
    }
    
    /**
     * 测试创建类别
     */
    @Test
    public void testCreateCategory() throws Exception {
        // 准备测试数据
        String name = "新建测试类别_" + System.currentTimeMillis();
        Integer sortOrder = 100;
        Integer status = 1;
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/feedback/categories")
                .param("name", name)
                .param("sortOrder", sortOrder.toString())
                .param("status", status.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        
        // 验证数据是否正确保存
        FeedbackCategory category = feedbackCategoryService.loadCategoryById(response.getData());
        assertNotNull(category);
        assertEquals(name, category.getName());
        assertEquals(sortOrder, category.getSortOrder());
        assertEquals(status, category.getStatus());
    }
    
    /**
     * 测试更新类别
     */
    @Test
    public void testUpdateCategory() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("待更新类别");
        
        // 更新数据
        String newName = "更新后的类别_" + System.currentTimeMillis();
        Integer newSortOrder = 200;
        Integer newStatus = 0;
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/feedback/categories/{id}", category.getId())
                .param("name", newName)
                .param("sortOrder", newSortOrder.toString())
                .param("status", newStatus.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertEquals(category.getId(), response.getData());
        
        // 验证数据是否正确更新
        FeedbackCategory updatedCategory = feedbackCategoryService.loadCategoryById(category.getId());
        assertNotNull(updatedCategory);
        assertEquals(newName, updatedCategory.getName());
        assertEquals(newSortOrder, updatedCategory.getSortOrder());
        assertEquals(newStatus, updatedCategory.getStatus());
    }
    
    /**
     * 测试删除类别
     */
    @Test
    public void testDeleteCategory() throws Exception {
        // 创建测试数据
        FeedbackCategory category = createAndSaveTestCategory("待删除类别");
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/feedback/categories/{id}", category.getId())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<Long> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertEquals(category.getId(), response.getData());
        
        // 验证数据是否已删除
        FeedbackCategory deletedCategory = feedbackCategoryService.loadCategoryById(category.getId());
        assertNull(deletedCategory);
    }
    
    /**
     * 测试批量删除类别
     */
    @Test
    public void testBatchDeleteCategories() throws Exception {
        // 创建测试数据
        FeedbackCategory category1 = createAndSaveTestCategory("待批量删除类别1");
        FeedbackCategory category2 = createAndSaveTestCategory("待批量删除类别2");
        
        // 执行测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/feedback/categories/batch")
                .param("ids", category1.getId().toString(), category2.getId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();
        
        // 解析响应 - 使用UTF-8编码读取响应内容
        String responseJson = result.getResponse().getContentAsString(java.nio.charset.StandardCharsets.UTF_8);
        ResponseResult<Long[]> response = objectMapper.readValue(
                responseJson, new TypeReference<ResponseResult<Long[]>>() {});
        
        // 验证响应
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getCode());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().length);
        
        // 验证数据是否已删除
        assertNull(feedbackCategoryService.loadCategoryById(category1.getId()));
        assertNull(feedbackCategoryService.loadCategoryById(category2.getId()));
    }
} 