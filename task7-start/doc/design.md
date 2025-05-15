# 跳跳棋活动系统架构设计

## 目录

1. [系统概述](#1-系统概述)
2. [架构设计](#2-架构设计)
3. [数据库设计](#3-数据库设计)
   - [3.1 核心实体关系](#31-核心实体关系)
   - [3.2 游戏模块实体](#32-游戏模块实体)
   - [3.3 奖励模块实体](#33-奖励模块实体)
   - [3.4 排行榜模块实体](#34-排行榜模块实体)
4. [核心业务流程](#4-核心业务流程)
   - [4.1 游戏机会获取流程](#41-游戏机会获取流程)
   - [4.2 游戏进行流程](#42-游戏进行流程)
   - [4.3 宝箱兑换流程](#43-宝箱兑换流程)
   - [4.4 排行榜更新流程](#44-排行榜更新流程)
5. [多活动支持与配置管理](#5-多活动支持与配置管理)
   - [5.1 多活动并存机制](#51-多活动并存机制)
   - [5.2 格子奖励配置机制](#52-格子奖励配置机制)
   - [5.3 奖励领取记录机制](#53-奖励领取记录机制)

## 1. 系统概述

跳跳棋活动系统是一个基于Java微服务架构开发的营销活动平台，旨在提升用户送礼消耗和平台活跃度。系统实现了一个互动性强的跳跳棋游戏，用户通过赠送特定礼物参与活动，获得游戏机会和丰富奖励。

系统主要包含三个核心功能模块：
- **跳跳棋游戏**：用户通过送礼获得跳跃机会，掷骰子决定前进步数，在不同格子获得奖励
- **宝箱兑换**：用户使用游戏中获得的积分兑换不同等级的宝箱奖励
- **排行榜系统**：展示活动期间获得积分最多的用户，前三名将获得额外奖励

## 2. 架构设计

跳跳棋活动系统采用微服务架构，主要包含以下几个部分：

```mermaid
graph TD
    Client[客户端] --> Gateway[API网关]
    Gateway --> GameService[游戏服务]
    Gateway --> RewardService[奖励服务]
    Gateway --> LeaderboardService[排行榜服务]
    
    GameService --> DB[(数据库)]
    RewardService --> DB
    LeaderboardService --> DB
    
    GameService --> MQ[消息队列]
    MQ --> RewardService
    MQ --> LeaderboardService
    
    ExternalGiftService[外部礼物服务] --> MQ
```

系统分为三个核心微服务：
- **游戏服务**：负责跳跳棋游戏核心逻辑，处理用户游戏机会获取、使用，管理游戏进度和状态
- **奖励服务**：管理宝箱兑换逻辑，处理积分累计和消费
- **排行榜服务**：维护用户积分排行榜，定期更新排行数据

## 3. 数据库设计

### 3.1 核心实体关系

系统核心实体关系如下：

```mermaid
erDiagram
    ACTIVITY ||--o{ USER_GAME_PROGRESS : "包含"
    ACTIVITY ||--o{ GAME_GRID : "配置"
    ACTIVITY ||--o{ USER_POINT : "记录"
    ACTIVITY ||--o{ TREASURE_BOX : "包含"
    ACTIVITY ||--o{ LEADERBOARD : "包含"
    
    ACTIVITY {
        bigint id PK "活动ID"
        varchar name "活动名称"
        datetime start_time "开始时间"
        datetime end_time "结束时间"
        int status "活动状态"
    }
```

**关系说明**：
- 活动(ACTIVITY)是系统的核心实体，一个活动包含多个用户游戏进度、游戏格子配置、用户积分记录、宝箱配置和排行榜数据
- 活动具有明确的时间范围和状态，控制整个游戏流程

### 3.2 游戏模块实体

游戏模块的核心实体关系：

```mermaid
erDiagram
    USER_GAME_PROGRESS ||--o{ GAME_RECORD : "产生"
    GAME_GRID ||--o{ GAME_RECORD : "关联"
    GIFT_CHANCE_CONFIG ||--o{ USER_GAME_PROGRESS : "增加机会"
    GAME_GRID ||--o{ USER_REWARD_RECORD : "记录领取"
    
    USER_GAME_PROGRESS {
        bigint id PK "进度ID"
        bigint user_id "用户ID"
        bigint activity_id FK "活动ID"
        int current_position "当前位置"
        int chances_remaining "剩余游戏机会"
        int daily_chances_used "当日已使用游戏机会"
        date game_date "游戏日期"
    }
    
    GAME_GRID {
        bigint id PK "格子ID"
        bigint activity_id FK "活动ID"
        int position "格子位置"
        int reward_type "奖励类型：1-积分，2-道具"
        int reward_amount "奖励数量"
        varchar reward_item_id "奖励道具ID"
        varchar reward_item_name "奖励道具名称"
        int fallback_points "道具已领取后的积分"
        varchar grid_name "格子名称"
        varchar grid_description "格子描述"
    }
    
    GAME_RECORD {
        bigint id PK "游戏记录ID"
        bigint user_id "用户ID"
        bigint activity_id FK "活动ID"
        int chances_used "使用机会数"
        varchar dice_results "骰子结果(JSON)"
        int start_position "起始位置"
        int end_position "结束位置"
        int reward_points "获得积分"
        varchar reward_items "获得道具(JSON)"
        datetime play_time "游戏时间"
    }
    
    GIFT_CHANCE_CONFIG {
        bigint id PK "配置ID"
        bigint activity_id FK "活动ID"
        varchar gift_id "礼物ID"
        int gift_amount "礼物数量"
        int chance_amount "获得机会数"
    }
    
    USER_REWARD_RECORD {
        bigint id PK "记录ID"
        bigint user_id "用户ID"
        bigint activity_id FK "活动ID"
        int grid_position "格子位置"
        varchar reward_item_id "已领取道具ID"
        datetime received_time "领取时间"
    }
```

**关系说明**：
- 用户游戏进度(USER_GAME_PROGRESS)记录用户在游戏中的当前位置和剩余机会
- 游戏格子(GAME_GRID)定义了游戏路径上各个位置的奖励配置
  - 每个活动可以有自己独立的格子配置(通过activity_id关联)
  - reward_type区分奖励类型：1-积分，2-道具
  - 当reward_type=2时，reward_item_id和reward_item_name有效
  - fallback_points字段定义了道具已被领取后的替代积分奖励
- 游戏记录(GAME_RECORD)记录每次游戏的详细信息，包括骰子点数和移动结果
- 礼物机会配置(GIFT_CHANCE_CONFIG)定义了不同礼物可以获得的游戏机会数量
- 用户奖励记录(USER_REWARD_RECORD)记录用户已领取的道具奖励，用于判断一次性道具是否已被领取

### 3.3 奖励模块实体

奖励模块的核心实体关系：

```mermaid
erDiagram
    USER_POINT ||--o{ USER_BOX_EXCHANGE : "消费"
    TREASURE_BOX ||--o{ USER_BOX_EXCHANGE : "兑换"
    
    USER_POINT {
        bigint id PK "积分记录ID"
        bigint user_id "用户ID"
        bigint activity_id FK "活动ID"
        int total_points "累计积分"
        int points_remaining "剩余积分"
    }
    
    TREASURE_BOX {
        bigint id PK "宝箱ID"
        bigint activity_id FK "活动ID"
        varchar box_name "宝箱名称"
        int required_points "所需积分"
        int box_level "宝箱等级"
        varchar reward_details "奖励详情"
    }
    
    USER_BOX_EXCHANGE {
        bigint id PK "兑换记录ID"
        bigint user_id "用户ID"
        bigint box_id FK "宝箱ID"
        int points_cost "消耗积分"
        int status "状态"
    }
```

**关系说明**：
- 用户积分(USER_POINT)记录用户在活动中获得的积分总数和剩余可用积分
- 宝箱(TREASURE_BOX)定义了不同等级宝箱的积分需求和奖励内容
- 用户宝箱兑换(USER_BOX_EXCHANGE)记录用户兑换宝箱的历史和状态

### 3.4 排行榜模块实体

排行榜模块的核心实体关系：

```mermaid
erDiagram
    USER_POINT ||--|| LEADERBOARD : "计算排名"
    
    LEADERBOARD {
        bigint id PK "排行榜ID"
        bigint activity_id FK "活动ID"
        bigint user_id "用户ID"
        int rank "排名"
        int total_points "总积分"
        datetime last_updated "最后更新时间"
    }
```

**关系说明**：
- 排行榜(LEADERBOARD)基于用户积分(USER_POINT)计算排名
- 排行榜定期更新，记录用户在活动中的积分排名情况

## 4. 核心业务流程

### 4.1 游戏机会获取流程

```mermaid
sequenceDiagram
    participant User as 用户
    participant GiftService as 礼物服务
    participant GameService as 游戏服务
    participant DB as 数据库
    
    User->>GiftService: 赠送礼物
    GiftService->>GameService: 礼物事件通知
    GameService->>DB: 查询礼物配置
    GameService->>DB: 查询用户游戏进度
    GameService->>DB: 增加游戏机会
    GameService-->>User: 返回剩余游戏机会
```

**数据库操作影响**：
- 读取 GIFT_CHANCE_CONFIG 表，确定礼物对应的游戏机会数量
- 读取 USER_GAME_PROGRESS 表，获取用户当前游戏进度
- 更新 USER_GAME_PROGRESS 表，增加用户的剩余游戏机会(chances_remaining)

### 4.2 游戏进行流程

```mermaid
sequenceDiagram
    participant User as 用户
    participant GameService as 游戏服务
    participant DB as 数据库
    participant RewardService as 奖励服务
    
    User->>GameService: 使用游戏机会(N次)
    GameService->>GameService: 生成随机骰子点数
    GameService->>DB: 查询当前位置
    GameService->>GameService: 计算新位置
    GameService->>DB: 查询格子奖励
    GameService->>DB: 检查道具奖励是否已领取
    
    alt 道具奖励且未领取
        GameService->>DB: 记录道具奖励领取
        GameService->>RewardService: 发送道具奖励事件
    else 积分奖励或道具已领取
        GameService->>RewardService: 发送积分奖励事件
    end
    
    GameService->>DB: 更新游戏进度
    GameService->>DB: 记录游戏记录
    RewardService->>DB: 更新用户积分
    GameService-->>User: 返回游戏结果
```

**数据库操作影响**：
1. 读取 USER_GAME_PROGRESS 表，获取用户当前位置和剩余机会
2. 读取 GAME_GRID 表，获取落点格子的奖励信息
3. 如果是道具奖励，读取 USER_REWARD_RECORD 表，检查用户是否已领取过该道具
   - 如果未领取，插入 USER_REWARD_RECORD 表，记录道具领取
   - 如果已领取，使用 GAME_GRID 表中的 fallback_points 作为替代积分奖励
4. 更新 USER_GAME_PROGRESS 表：
   - 减少剩余游戏机会(chances_remaining)
   - 增加已使用游戏机会(daily_chances_used)
   - 更新当前位置(current_position)
   - 更新游戏日期(game_date)
5. 插入 GAME_RECORD 表，记录本次游戏详情，包括骰子结果和获得的奖励
6. 更新 USER_POINT 表，增加用户积分

### 4.3 宝箱兑换流程

```mermaid
sequenceDiagram
    participant User as 用户
    participant RewardService as 奖励服务
    participant DB as 数据库
    participant ExternalReward as 外部奖励服务
    
    User->>RewardService: 请求兑换宝箱
    RewardService->>DB: 查询用户积分
    RewardService->>DB: 查询宝箱信息
    
    alt 积分充足且未兑换
        RewardService->>DB: 扣减用户积分
        RewardService->>DB: 创建兑换记录
        RewardService->>ExternalReward: 请求发放奖励
        ExternalReward-->>RewardService: 返回发放结果
        RewardService->>DB: 更新兑换状态
        RewardService-->>User: 返回兑换成功
    else 积分不足或已兑换
        RewardService-->>User: 返回兑换失败
    end
```

**数据库操作影响**：
1. 读取 USER_POINT 表，检查用户剩余积分
2. 读取 TREASURE_BOX 表，获取宝箱所需积分和奖励信息
3. 读取 USER_BOX_EXCHANGE 表，检查用户是否已兑换过该宝箱
4. 更新 USER_POINT 表，减少用户剩余积分(points_remaining)和增加已消费积分(points_consumed)
5. 插入 USER_BOX_EXCHANGE 表，记录兑换信息

### 4.4 排行榜更新流程

```mermaid
flowchart TD
    A[定时任务触发] --> B[查询所有用户积分数据]
    B --> C[按积分排序]
    C --> D[更新排行榜数据]
    D --> E[缓存排行榜数据]
    
    F[用户查询排行榜] --> G{缓存是否存在?}
    G -->|是| H[返回缓存数据]
    G -->|否| I[查询数据库]
    I --> J[更新缓存]
    J --> H
```

**数据库操作影响**：
1. 读取 USER_POINT 表，获取所有用户的积分数据
2. 更新 LEADERBOARD 表，设置用户排名和积分
3. 活动结束时，根据 LEADERBOARD 表的排名发放排行榜奖励

## 5. 多活动支持与配置管理

### 5.1 多活动并存机制

系统设计支持多个活动并存，主要通过以下机制实现：

```mermaid
flowchart TD
    A[活动配置] --> B{是否有活动ID?}
    B -->|是| C[查询特定活动配置]
    B -->|否| D[查询当前活动配置]
    D --> E{多个活动进行中?}
    E -->|是| F[按优先级选择活动]
    E -->|否| G[返回唯一活动]
    C --> H[返回活动配置]
    F --> H
    G --> H
```

**多活动支持说明**：
1. 所有核心实体都通过 activity_id 关联到特定活动
2. 每个活动有独立的开始和结束时间，可以重叠或不重叠
3. 当多个活动同时进行时，系统可以通过优先级机制选择默认活动
4. 用户可以在多个活动间切换，每个活动有独立的游戏进度和积分

### 5.2 格子奖励配置机制

每个活动可以独立配置格子奖励，支持灵活的奖励策略：

```mermaid
erDiagram
    ACTIVITY ||--o{ GAME_GRID : "配置"
    GAME_GRID ||--o{ USER_REWARD_RECORD : "记录领取"
    
    ACTIVITY {
        bigint id PK "活动ID"
        int grid_count "格子总数"
    }
    
    GAME_GRID {
        bigint id PK "格子ID"
        bigint activity_id FK "活动ID"
        int position "格子位置"
        int reward_type "奖励类型：1-积分，2-道具"
        int reward_amount "奖励数量"
        varchar reward_item_id "奖励道具ID"
        int fallback_points "道具已领取后的积分"
    }
```

**格子奖励配置说明**：
1. 每个活动可以配置不同数量的格子(通过activity表的grid_count字段)
2. 格子奖励类型通过reward_type区分：
   - 1: 积分奖励 - 直接发放reward_amount数量的积分
   - 2: 道具奖励 - 发放reward_item_id指定的道具
3. 对于道具奖励，系统会检查用户是否已领取：
   - 如果未领取，发放道具并记录到USER_REWARD_RECORD表
   - 如果已领取，发放fallback_points数量的替代积分

### 5.3 奖励领取记录机制

系统通过USER_REWARD_RECORD表记录用户已领取的一次性奖励：

```mermaid
sequenceDiagram
    participant GameService as 游戏服务
    participant DB as 数据库
    participant RewardService as 奖励服务
    
    GameService->>DB: 查询格子奖励(GAME_GRID)
    
    alt 奖励类型为道具
        GameService->>DB: 查询是否已领取(USER_REWARD_RECORD)
        
        alt 未领取过
            GameService->>RewardService: 发送道具奖励事件
            RewardService-->>GameService: 奖励发放结果
            GameService->>DB: 记录奖励领取(USER_REWARD_RECORD)
        else 已领取过
            GameService->>RewardService: 发送替代积分奖励事件
        end
    else 奖励类型为积分
        GameService->>RewardService: 发送积分奖励事件
    end
```

**奖励领取记录说明**：
1. USER_REWARD_RECORD表记录用户在特定活动中已领取的道具奖励
2. 记录包含user_id、activity_id、grid_position和reward_item_id，用于唯一标识已领取的奖励
3. 系统在发放奖励前会检查该记录，确保一次性道具不会重复发放
4. 当道具已领取时，系统会自动发放替代积分奖励
