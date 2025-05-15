package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.FeedbackCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FeedbackCategoryMapper 测试类
 */
@SpringBootTest
@Transactional
public class FeedbackCategoryMapperTest {

    @Autowired
    private FeedbackCategoryMapper feedbackCategoryMapper;
    
    /**
     * 测试插入反馈类别表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        FeedbackCategory feedbackCategory = createTestFeedbackCategory("测试插入");
        
        // 执行插入
        int rows = feedbackCategoryMapper.insert(feedbackCategory);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(feedbackCategory.getId());
        
        // 验证能否查询到
        FeedbackCategory savedFeedbackCategory = feedbackCategoryMapper.getById(feedbackCategory.getId());
        assertNotNull(savedFeedbackCategory);
        
        System.out.println("插入反馈类别表成功: " + savedFeedbackCategory);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        FeedbackCategory feedbackCategory = createTestFeedbackCategory("测试ID查询");
        feedbackCategoryMapper.insert(feedbackCategory);
        
        // 执行查询
        FeedbackCategory result = feedbackCategoryMapper.getById(feedbackCategory.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(feedbackCategory.getId(), result.getId());
        
        System.out.println("根据ID查询反馈类别表: " + result);
    }
    
    /**
     * 测试根据类别名称查询
     */
    @Test
    public void testGetByName() {
        // 创建测试数据
        FeedbackCategory feedbackCategory = createTestFeedbackCategory("测试类别名称查询");
        feedbackCategoryMapper.insert(feedbackCategory);
        
        // 执行查询
        FeedbackCategory result = feedbackCategoryMapper.getByName(feedbackCategory.getName());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(feedbackCategory.getId(), result.getId());
        assertEquals(feedbackCategory.getName(), result.getName());
        
        System.out.println("根据类别名称查询反馈类别表: " + result);
    }
    
    /**
     * 测试根据状态(0:禁用 1:启用)查询列表
     */
    @Test
    public void testFindByStatus() {
        // 创建测试数据
        FeedbackCategory feedbackCategory1 = createTestFeedbackCategory("测试状态(0:禁用 1:启用)查询1");
        FeedbackCategory feedbackCategory2 = createTestFeedbackCategory("测试状态(0:禁用 1:启用)查询2");
        // 设置相同的状态(0:禁用 1:启用)，确保能查到多条记录
        feedbackCategory1.setStatus(feedbackCategory2.getStatus());
        feedbackCategoryMapper.insert(feedbackCategory1);
        feedbackCategoryMapper.insert(feedbackCategory2);
        
        // 执行查询
        List<FeedbackCategory> results = feedbackCategoryMapper.findByStatus(feedbackCategory1.getStatus());
        
        // 验证结果
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        
        // 验证包含测试数据
        boolean containsFeedbackCategory1 = results.stream().anyMatch(c -> c.getId().equals(feedbackCategory1.getId()));
        boolean containsFeedbackCategory2 = results.stream().anyMatch(c -> c.getId().equals(feedbackCategory2.getId()));
        assertTrue(containsFeedbackCategory1, "结果中不包含反馈类别表1");
        assertTrue(containsFeedbackCategory2, "结果中不包含反馈类别表2");
        
        System.out.println("根据状态(0:禁用 1:启用)查询反馈类别表列表: " + results.size() + "条记录");
    }
    
    /**
     * 测试查询所有反馈类别表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        FeedbackCategory feedbackCategory1 = createTestFeedbackCategory("测试查询所有1");
        FeedbackCategory feedbackCategory2 = createTestFeedbackCategory("测试查询所有2");
        feedbackCategoryMapper.insert(feedbackCategory1);
        feedbackCategoryMapper.insert(feedbackCategory2);
        
        // 执行查询
        List<FeedbackCategory> feedbackCategoryList = feedbackCategoryMapper.findAll();
        
        // 验证结果
        assertNotNull(feedbackCategoryList);
        assertFalse(feedbackCategoryList.isEmpty());
        
        // 验证包含测试数据
        boolean containsFeedbackCategory1 = feedbackCategoryList.stream().anyMatch(c -> c.getId().equals(feedbackCategory1.getId()));
        boolean containsFeedbackCategory2 = feedbackCategoryList.stream().anyMatch(c -> c.getId().equals(feedbackCategory2.getId()));
        assertTrue(containsFeedbackCategory1, "结果中不包含反馈类别表1");
        assertTrue(containsFeedbackCategory2, "结果中不包含反馈类别表2");
        
        System.out.println("查询所有反馈类别表数量: " + feedbackCategoryList.size());
    }
    
    /**
     * 测试查询所有启用的反馈类别表
     */
    @Test
    public void testFindAllEnabled() {
        // 创建启用的测试数据
        FeedbackCategory enabledFeedbackCategory = createTestFeedbackCategory("测试启用反馈类别表");
        enabledFeedbackCategory.setStatus(1);
        feedbackCategoryMapper.insert(enabledFeedbackCategory);
        
        // 创建禁用的测试数据
        FeedbackCategory disabledFeedbackCategory = createTestFeedbackCategory("测试禁用反馈类别表");
        disabledFeedbackCategory.setStatus(0);
        feedbackCategoryMapper.insert(disabledFeedbackCategory);
        
        // 执行查询
        List<FeedbackCategory> feedbackCategoryList = feedbackCategoryMapper.findAllEnabled();
        
        // 验证结果
        assertNotNull(feedbackCategoryList);
        assertFalse(feedbackCategoryList.isEmpty());
        
        // 验证所有结果都是启用状态且包含启用测试数据
        boolean containsEnabledFeedbackCategory = false;
        for (FeedbackCategory feedbackCategory : feedbackCategoryList) {
            assertEquals(1, feedbackCategory.getStatus(), "查询到非启用状态的反馈类别表");
            if (feedbackCategory.getId().equals(enabledFeedbackCategory.getId())) {
                containsEnabledFeedbackCategory = true;
            }
        }
        assertTrue(containsEnabledFeedbackCategory, "结果中不包含测试创建的启用反馈类别表");
        
        // 验证不包含禁用的测试数据
        boolean containsDisabledFeedbackCategory = feedbackCategoryList.stream()
                .anyMatch(c -> c.getId().equals(disabledFeedbackCategory.getId()));
        assertFalse(containsDisabledFeedbackCategory, "结果中不应包含禁用反馈类别表");
        
        System.out.println("查询所有启用反馈类别表数量: " + feedbackCategoryList.size());
    }
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        FeedbackCategory feedbackCategory = createTestFeedbackCategory("测试条件查询");
        feedbackCategoryMapper.insert(feedbackCategory);
        
        // 创建查询条件
        FeedbackCategory query = new FeedbackCategory();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<FeedbackCategory> feedbackCategoryList = feedbackCategoryMapper.findList(query);
        
        // 验证结果
        assertNotNull(feedbackCategoryList);
        
        System.out.println("条件查询结果数量: " + feedbackCategoryList.size());
    }
    
    /**
     * 测试更新反馈类别表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        FeedbackCategory feedbackCategory = createTestFeedbackCategory("测试更新");
        feedbackCategoryMapper.insert(feedbackCategory);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = feedbackCategoryMapper.update(feedbackCategory);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        FeedbackCategory updatedFeedbackCategory = feedbackCategoryMapper.getById(feedbackCategory.getId());
        assertNotNull(updatedFeedbackCategory);
        
        System.out.println("更新反馈类别表成功: " + updatedFeedbackCategory);
    }
    
    /**
     * 测试删除反馈类别表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        FeedbackCategory feedbackCategory = createTestFeedbackCategory("测试删除");
        feedbackCategoryMapper.insert(feedbackCategory);
        
        // 验证创建成功
        assertNotNull(feedbackCategoryMapper.getById(feedbackCategory.getId()));
        
        // 执行删除
        int rows = feedbackCategoryMapper.deleteById(feedbackCategory.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(feedbackCategoryMapper.getById(feedbackCategory.getId()));
        
        System.out.println("删除反馈类别表成功, ID: " + feedbackCategory.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        FeedbackCategory feedbackCategory1 = createTestFeedbackCategory("测试批量删除1");
        FeedbackCategory feedbackCategory2 = createTestFeedbackCategory("测试批量删除2");
        feedbackCategoryMapper.insert(feedbackCategory1);
        feedbackCategoryMapper.insert(feedbackCategory2);
        
        // 验证创建成功
        assertNotNull(feedbackCategoryMapper.getById(feedbackCategory1.getId()));
        assertNotNull(feedbackCategoryMapper.getById(feedbackCategory2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { feedbackCategory1.getId(), feedbackCategory2.getId() };
        int rows = feedbackCategoryMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(feedbackCategoryMapper.getById(feedbackCategory1.getId()));
        assertNull(feedbackCategoryMapper.getById(feedbackCategory2.getId()));
        
        System.out.println("批量删除反馈类别表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用反馈类别表（不保存到数据库）
     */
    private FeedbackCategory createTestFeedbackCategory(String testName) {
        FeedbackCategory feedbackCategory = new FeedbackCategory();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        // 唯一索引字段，确保值唯一
        feedbackCategory.setName(testName + "_name" + randomSuffix);
        feedbackCategory.setSortOrder((int)(Math.random() * 100) + 1);
        feedbackCategory.setStatus(1); // 默认启用状态
        return feedbackCategory;
    }
}
