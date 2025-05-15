package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.mapper.GameCellMapper;
import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.model.GameCell;
import com.anycompany.demo.jumping.model.User;
import com.anycompany.demo.jumping.model.UserGameData;
import com.anycompany.demo.jumping.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * GameController 测试类
 */
@AutoConfigureMockMvc
public class GameControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private GameCellMapper gameCellMapper;
    
    private ActivityConfig testActivity;
    private User testUser;
    private List<GameCell> testGameCells;
    
    /**
     * 测试前准备数据
     */
    @BeforeEach
    public void setUp() {
        // 创建测试活动
        testActivity = createAndSaveTestActivity("游戏控制器测试活动");
        
        // 创建测试用户
        testUser = createAndSaveTestUser("game_controller_test");
        
        // 创建测试游戏格子
        testGameCells = createTestGameCells(testActivity.getId());
    }
    
    /**
     * 测试获取游戏数据接口
     * 正向测试：验证接口返回正确的游戏数据
     */
    @Test
    public void testGetGameData() throws Exception {
        // 执行请求
        MvcResult result = mockMvc.perform(get("/api/game/data")
                .param("activityId", testActivity.getId().toString())
                .header("USER_ID", "10001") // 添加USER_ID请求头
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.progress").exists())
                .andExpect(jsonPath("$.data.cells").exists())
                .andExpect(jsonPath("$.data.progress.activityId").value(testActivity.getId()))
                .andExpect(jsonPath("$.data.progress.userId").value(10001)) // 固定的用户ID
                .andExpect(jsonPath("$.data.progress.currentPosition").exists())
                .andExpect(jsonPath("$.data.progress.remainingChances").exists())
                .andExpect(jsonPath("$.data.progress.dailyChancesUsed").exists())
                .andExpect(jsonPath("$.data.progress.userPoints").exists())
                .andExpect(jsonPath("$.data.progress.gameDate").exists())
                .andExpect(jsonPath("$.data.cells", hasSize(testGameCells.size())))
                .andExpect(jsonPath("$.data.cells[0].cellIndex").value(0))
                .andExpect(jsonPath("$.data.cells[0].cellType").value(1)) // START 类型转换为 1
                .andExpect(jsonPath("$.data.cells[0].rewardType").value(1)) // POINTS 类型转换为 1
                .andExpect(jsonPath("$.data.cells[0].description").value("起点"))
                .andReturn();
        
        // 打印响应内容，便于调试
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("响应内容: " + responseContent);
        
        // 验证最后一个格子是终点
        mockMvc.perform(get("/api/game/data")
                .param("activityId", testActivity.getId().toString())
                .header("USER_ID", "10001") // 添加USER_ID请求头
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.cells[" + (testGameCells.size() - 1) + "].cellType").value(5)) // END 类型转换为 5
                .andExpect(jsonPath("$.data.cells[" + (testGameCells.size() - 1) + "].description").value("终点"));
        
        System.out.println("获取游戏数据接口测试通过");
    }
    
    /**
     * 创建测试游戏格子
     * 
     * @param activityId 活动ID
     * @return 游戏格子列表
     */
    private List<GameCell> createTestGameCells(Long activityId) {
        List<GameCell> cells = new ArrayList<>();
        
        // 创建起点格子
        GameCell startCell = createTestGameCellObject(activityId, 0, "START");
        startCell.setRewardAmount(0);
        startCell.setRewardDesc("起点");
        gameCellMapper.insert(startCell);
        cells.add(startCell);
        
        // 创建中间格子
        for (int i = 1; i < 9; i++) {
            String cellType = "NORMAL";
            if (i % 3 == 0) {
                cellType = "GIFT";
            } else if (i % 5 == 0) {
                cellType = "CHANCE";
            }
            
            GameCell cell = createTestGameCellObject(activityId, i, cellType);
            cell.setRewardAmount(10 * i);
            cell.setRewardDesc("测试格子_" + i);
            gameCellMapper.insert(cell);
            cells.add(cell);
        }
        
        // 创建终点格子
        GameCell endCell = createTestGameCellObject(activityId, 9, "END");
        endCell.setRewardAmount(100);
        endCell.setRewardDesc("终点");
        gameCellMapper.insert(endCell);
        cells.add(endCell);
        
        return cells;
    }
}
