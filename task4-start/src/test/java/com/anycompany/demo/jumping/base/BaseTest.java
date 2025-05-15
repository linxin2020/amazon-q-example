package com.anycompany.demo.jumping.base;

import com.anycompany.demo.jumping.model.FeedbackCategory;
import com.anycompany.demo.jumping.model.User;
import com.anycompany.demo.jumping.model.UserFeedback;
import com.anycompany.demo.jumping.service.UserService;
import com.anycompany.demo.jumping.service.FeedbackCategoryService;
import com.anycompany.demo.jumping.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 测试基础类
 * 提供通用的测试数据创建和辅助方法
 * 用于服务层和控制器层测试
 */
@SpringBootTest
@Transactional
public abstract class BaseTest {

    @Autowired
    protected UserService userService;
    
    @Autowired
    protected FeedbackCategoryService feedbackCategoryService;
    
    @Autowired
    protected UserFeedbackService userFeedbackService;
        
    /**
     * 创建测试用户对象（不保存到数据库）
     * 
     * @param prefix 用户名前缀
     * @return 创建的用户对象
     */
    protected User createTestUser(String prefix) {
        User user = new User();
        user.setUsername(prefix + "_" + System.currentTimeMillis());
        user.setPassword("test123");
        user.setNickname("测试用户" + prefix);
        user.setEmail(prefix + "@example.com");
        user.setPhone("13900000000");
        user.setAvatarUrl("/avatar/generate?letter1=T&letter2=T");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return user;
    }
    
    /**
     * 创建并保存测试用户到数据库
     * 用于测试前创建所需的测试数据
     * 
     * @param prefix 用户名前缀
     * @return 创建并保存的用户对象
     */
    protected User createAndSaveTestUser(String prefix) {
        User user = createTestUser(prefix);
        Long userId = userService.createUser(
                user.getUsername(),
                user.getPassword(),
                user.getNickname(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl()
        );
        assertNotNull(userId, "创建用户失败");
        return userService.loadUserById(userId);
    }
    
    /**
     * 创建测试反馈类别对象（不保存到数据库）
     * 
     * @param namePrefix 名称前缀
     * @return 创建的反馈类别对象
     */
    protected FeedbackCategory createTestCategory(String namePrefix) {
        FeedbackCategory category = new FeedbackCategory();
        category.setName(namePrefix + "_" + System.currentTimeMillis());
        category.setSortOrder(new Random().nextInt(100));
        category.setStatus(1);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        return category;
    }
    
    /**
     * 创建并保存测试反馈类别到数据库
     * 用于测试前创建所需的测试数据
     * 
     * @param namePrefix 名称前缀
     * @return 创建并保存的反馈类别对象
     */
    protected FeedbackCategory createAndSaveTestCategory(String namePrefix) {
        FeedbackCategory category = createTestCategory(namePrefix);
        Long categoryId = feedbackCategoryService.createCategory(
                category.getName(),
                category.getSortOrder(),
                category.getStatus()
        );
        assertNotNull(categoryId, "创建反馈类别失败");
        return feedbackCategoryService.loadCategoryById(categoryId);
    }
    
    /**
     * 创建测试用户反馈对象（不保存到数据库）
     * 
     * @param titlePrefix 标题前缀
     * @param categoryId 类别ID
     * @param userId 用户ID（可选）
     * @return 创建的用户反馈对象
     */
    protected UserFeedback createTestFeedback(String titlePrefix, Long categoryId, Long userId) {
        UserFeedback feedback = new UserFeedback();
        feedback.setCategoryId(categoryId);
        feedback.setTitle(titlePrefix + "_" + System.currentTimeMillis());
        feedback.setContent("这是测试反馈内容" + System.currentTimeMillis());
        feedback.setEmail("test_" + System.currentTimeMillis() + "@example.com");
        feedback.setStatus(0);
        feedback.setUserId(userId);
        feedback.setCreateTime(new Date());
        feedback.setUpdateTime(new Date());
        return feedback;
    }
    
    /**
     * 创建并保存测试用户反馈到数据库
     * 用于测试前创建所需的测试数据
     * 
     * @param titlePrefix 标题前缀
     * @param categoryId 类别ID
     * @param userId 用户ID（可选）
     * @return 创建并保存的用户反馈对象
     */
    protected UserFeedback createAndSaveTestFeedback(String titlePrefix, Long categoryId, Long userId) {
        UserFeedback feedback = createTestFeedback(titlePrefix, categoryId, userId);
        Long feedbackId = userFeedbackService.createFeedback(
                feedback.getCategoryId(),
                feedback.getTitle(),
                feedback.getContent(),
                feedback.getEmail(),
                feedback.getUserId()
        );
        assertNotNull(feedbackId, "创建用户反馈失败");
        return userFeedbackService.loadFeedbackById(feedbackId);
    }
    
}
