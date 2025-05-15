package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.common.exception.ActivityErrorCode;
import com.anycompany.demo.jumping.common.exception.BusinessException;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.common.util.UserContext;
import com.anycompany.demo.jumping.service.ActivityService;
import com.anycompany.demo.jumping.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 游戏控制器
 */
@RestController
@RequestMapping("/api/game")
@Validated
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private ActivityService activityService;
    
    /**
     * 获取游戏数据
     * 
     * @param activityId 活动ID
     * @return 游戏数据，包括用户进度和格子信息
     */
    @GetMapping("/data")
    public ResponseResult<Map<String, Object>> getGameData(@RequestParam Long activityId, HttpServletRequest request) {
        // 参数校验
        if (activityId == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "活动ID不能为空");
        }
        
        // 验证活动是否存在
        if (activityService.loadActivityById(activityId) == null) {
            throw new BusinessException(ActivityErrorCode.ACTIVITY_NOT_FOUND.getCode(), "请求的活动ID不存在");
        }
        
        // 从请求头中获取用户ID
        Long userId = UserContext.getUserId(request);
        
        // 获取游戏数据
        Map<String, Object> gameData = gameService.getGameData(activityId, userId);
        
        return ResponseResult.success(gameData);
    }
}
