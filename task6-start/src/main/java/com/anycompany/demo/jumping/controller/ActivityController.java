package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.common.exception.ActivityErrorCode;
import com.anycompany.demo.jumping.common.exception.BusinessException;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动控制器
 */
@RestController
@RequestMapping("/api/activity")
@Validated
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    /**
     * 获取活动信息
     *
     * @param activityId 活动ID
     * @return 活动信息
     */
    @GetMapping("/info")
    public ResponseResult<Map<String, Object>> getActivityInfo(@RequestParam Long activityId) {
        // 参数校验
        if (activityId == null || activityId <= 0) {
            throw new BusinessException(ActivityErrorCode.ACTIVITY_NOT_FOUND.getCode(), "活动不存在");
        }
        
        // 获取活动信息
        ActivityConfig activityConfig = activityService.loadActivityById(activityId);
        if (activityConfig == null) {
            throw new BusinessException(ActivityErrorCode.ACTIVITY_NOT_FOUND.getCode(), "活动不存在");
        }
        
        // 构建响应数据
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("activityId", activityConfig.getId());
        responseData.put("activityName", activityConfig.getName());
        responseData.put("startTime", activityConfig.getStartTime());
        responseData.put("endTime", activityConfig.getEndTime());
        responseData.put("status", activityConfig.getStatus());
        
        return ResponseResult.success(responseData);
    }
}
