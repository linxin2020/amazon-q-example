# 跳跳棋活动系统API文档

## 目录

1. [通用说明](#1-通用说明)
   - [1.1 接口规范](#11-接口规范)
   - [1.2 通用响应格式](#12-通用响应格式)
   - [1.3 通用错误码](#13-通用错误码)
2. [API清单](#2-api清单)
   - [2.1 获取活动信息](#21-获取活动信息)
   - [2.2 获取游戏数据](#22-获取游戏数据)
   - [2.3 使用游戏机会](#23-使用游戏机会)
   - [2.4 获取宝箱列表](#24-获取宝箱列表)
   - [2.5 兑换宝箱](#25-兑换宝箱)
   - [2.6 获取积分排行榜](#26-获取积分排行榜)

## 1. 通用说明

### 1.1 接口规范

- 所有API请求均使用HTTPS协议
- 请求方法主要使用GET和POST
- 请求参数格式为JSON
- 响应格式统一为JSON
- 所有接口均需要进行用户身份验证，除特殊说明外
- **当前用户ID可以通过请求头(Header)中的USER_ID字段获取**。请注意，这仅是开发环境的简化验证方式，生产环境将通过登录Session获取用户身份信息。此处仅为了简化开发和测试流程。

### 1.2 通用响应格式

```json
{
  "code": 0,       // 响应码（response code），0表示成功，非0表示失败
  "message": "",   // 响应消息（response message），成功时为"success"，失败时为错误描述
  "data": {}       // 响应数据（response data），具体格式根据接口定义
}
```

### 1.3 通用错误码

| 错误码（Error Code） | 描述（Description）                       |
| --------------- | ------------------------------------- |
| 0               | 成功（Success）                           |
| 1000            | 系统错误（System Error）                    |
| 1001            | 参数错误（Parameter Error）                 |
| 1002            | 未授权访问（Unauthorized Access）            |
| 1003            | 资源不存在（Resource Not Found）             |
| 1004            | 操作频率超限（Operation Frequency Exceeded）  |
| 2001            | 活动未开始（Activity Not Started）           |
| 2002            | 活动已结束（Activity Ended）                 |
| 3001            | 游戏机会不足（Insufficient Game Chances）     |
| 3002            | 超出每日游戏次数限制（Daily Game Limit Exceeded） |
| 4001            | 积分不足（Insufficient Points）             |
| 4002            | 宝箱已兑换（Box Already Exchanged）          |

[返回顶部](#跳跳棋活动系统api文档)

## 2. API清单

### 2.1 获取活动信息

#### 接口描述

获取当前进行中的活动信息，包括活动规则、时间等基本信息。

#### 请求方式

`GET /api/activity/info`

#### 请求参数

| 参数名（Parameter） | 类型（Type） | 必填（Required） | 描述（Description）   |
| -------------- | -------- | ------------ | ----------------- |
| activityId     | long     | 是            | 活动ID（Activity ID） |

#### 响应参数

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "activityId": 1,           // 活动ID（Activity ID）
    "activityName": "跳跳棋欢乐季", // 活动名称（Activity Name）
    "startTime": "2025-01-01 00:00:00", // 开始时间（Start Time）
    "endTime": "2025-01-31 23:59:59",   // 结束时间（End Time）
    "status": 1               // 活动状态（Activity Status）：0-未开始，1-进行中，2-已结束
  }
}
```

#### 业务逻辑

1. 检查请求参数，是否传入activityId
2. 从数据库`demo_activity`表中查询活动信息
3. 返回活动详情数据

#### 可能的异常情况

- 错误码1003：请求的活动ID不存在
- 错误码1000：系统内部错误，无法获取活动信息

[返回顶部](#跳跳棋活动系统api文档)

### 2.2 获取游戏数据

#### 接口描述

获取用户在指定活动中的游戏数据，包括当前位置、剩余游戏机会、游戏格子信息等。

#### 请求方式

`GET /api/game/data`

#### 请求参数

| 参数名（Parameter） | 类型（Type） | 必填（Required） | 描述（Description）   |
| -------------- | -------- | ------------ | ----------------- |
| activityId     | long     | 是            | 活动ID（Activity ID） |

#### 响应参数

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "progress": {
      "userId": 10001,           // 用户ID（User ID）
      "activityId": 1,           // 活动ID（Activity ID）
      "currentPosition": 15,     // 当前位置（Current Position）
      "remainingChances": 8,     // 剩余游戏机会（Remaining Chances）
      "dailyChancesUsed": 12,    // 今日已使用机会（Daily Chances Used）
      "userPoints": 2500,        // 用户当前积分（User Points）
      "gameDate": "2025-01-15"   // 游戏日期（Game Date）
    },
    "cells": [
      {
        "cellIndex": 0,        // 格子索引（Cell Index）
        "cellType": 1,         // 格子类型（Cell Type）：1-普通格子，2-奖励格子，3-惩罚格子，4-机会格子，5-终点格子
        "rewardType": 1,       // 奖励类型（Reward Type）：1-积分，2-道具
        "rewardAmount": 10,    // 奖励数量（Reward Amount）
        "description": "起点"   // 格子描述（Cell Description）
      },
      {
        "cellIndex": 1,
        "cellType": 2,
        "rewardType": 1,
        "rewardAmount": 50,
        "description": "积分奖励"
      },
      // ... 更多格子
      {
        "cellIndex": 29,
        "cellType": 5,
        "rewardType": 2,
        "rewardAmount": 1,
        "description": "终点-特殊奖励"
      }
    ]
  }
}
```

#### 业务逻辑

1. 验证用户身份和活动有效性
2. 从数据库`demo_user_progress`表中查询用户游戏进度
3. 如果用户没有游戏进度记录，则创建初始记录
4. 检查游戏日期，如果不是当天，重置每日已使用游戏机会
5. 从数据库`demo_game_cell`表中查询活动的所有格子信息
6. 返回用户游戏进度和格子信息数据

#### 可能的异常情况

- 错误码1002：用户未登录或身份验证失败
- 错误码1003：请求的活动ID不存在
- 错误码2001：活动未开始
- 错误码2002：活动已结束

[返回顶部](#跳跳棋活动系统api文档)

### 2.3 使用游戏机会

#### 接口描述

用户使用游戏机会，掷骰子并移动棋子，获取奖励。

#### 请求方式

`POST /api/game/play`

#### 请求参数

| 参数名（Parameter） | 类型（Type） | 必填（Required） | 描述（Description）                 |
| -------------- | -------- | ------------ | ------------------------------- |
| activityId     | long     | 是            | 活动ID（Activity ID）               |
| chancesCount   | int      | 是            | 使用的游戏机会数量（Chances Count），范围1-10 |

#### 响应参数

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "gameRecords": [
      {
        "diceValue": 4,        // 骰子点数（Dice Value）
        "startPosition": 15,    // 起始位置（Start Position）
        "endPosition": 19,      // 结束位置（End Position）
        "rewardType": 1,        // 奖励类型（Reward Type）：1-积分，2-道具
        "rewardAmount": 50      // 奖励数量（Reward Amount）
      },
      {
        "diceValue": 6,
        "startPosition": 19,
        "endPosition": 25,
        "rewardType": 2,
        "rewardAmount": 1
      }
      // 如果使用多次机会，会有多条记录
    ],
    "totalRewardPoints": 150,   // 总获得积分（Total Reward Points）
    "rewardItems": [            // 获得的道具奖励（Reward Items）
      {
        "itemId": "special_avatar_1",  // 道具ID（Item ID）
        "itemName": "限定头像框",       // 道具名称（Item Name）
        "itemType": 2                  // 道具类型（Item Type）
      }
    ],
    "newPosition": 25,          // 新位置（New Position）
    "remainingChances": 6       // 剩余游戏机会（Remaining Chances）
  }
}
```

#### 业务逻辑

1. 验证用户身份和活动有效性
2. 检查用户剩余游戏机会是否足够
3. 检查用户当日游戏次数是否超限
4. 从数据库`demo_user_progress`表中获取用户当前位置
5. 循环执行以下步骤chancesCount次：
   - 生成随机骰子点数(1-6)
   - 计算新位置
   - 如果新位置超过终点，则停在终点并下次从起点开始
   - 从数据库`demo_game_cell`表中获取落点格子的奖励信息
   - 如果是道具奖励，查询`demo_user_reward`表检查是否已领取
   - 如果已领取道具，则发放替代积分
   - 记录游戏记录到`demo_game_record`表
   - 更新用户积分到`demo_user_points`表
   - 如果是道具奖励且未领取，记录到`demo_user_reward`表
6. 更新用户游戏进度到`demo_user_progress`表
7. 返回游戏结果数据

#### 可能的异常情况

- 错误码1001：请求参数错误，如chancesCount超出范围
- 错误码1002：用户未登录或身份验证失败
- 错误码2001：活动未开始
- 错误码2002：活动已结束
- 错误码3001：游戏机会不足
- 错误码3002：超出每日游戏次数限制

[返回顶部](#跳跳棋活动系统api文档)

### 2.4 获取宝箱列表

#### 接口描述

获取活动中可兑换的宝箱列表，包括所需积分、奖励内容等。

#### 请求方式

`GET /api/box/list`

#### 请求参数

| 参数名（Parameter） | 类型（Type） | 必填（Required） | 描述（Description）   |
| -------------- | -------- | ------------ | ----------------- |
| activityId     | long     | 是            | 活动ID（Activity ID） |

#### 响应参数

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "userPoints": 2500,        // 用户当前积分（User Points）
    "boxes": [
      {
        "boxId": 1,            // 宝箱ID（Box ID）
        "boxLevel": 1,         // 宝箱等级（Box Level）：1-普通，2-稀有，3-史诗，4-传说
        "boxName": "青铜宝箱",  // 宝箱名称（Box Name）
        "pointsRequired": 500, // 所需积分（Points Required）
        "rewardType": 2,       // 奖励类型（Reward Type）
        "rewardDesc": "1天VIP会员", // 奖励描述（Reward Description）
        "exchanged": true      // 是否已兑换（Exchanged）
      },
      {
        "boxId": 2,
        "boxLevel": 2,
        "boxName": "白银宝箱",
        "pointsRequired": 2000,
        "rewardType": 2,
        "rewardDesc": "7天VIP会员和普通头像框",
        "exchanged": false
      },
      {
        "boxId": 3,
        "boxLevel": 3,
        "boxName": "黄金宝箱",
        "pointsRequired": 5000,
        "rewardType": 2,
        "rewardDesc": "30天VIP会员和限定头像框",
        "exchanged": false
      }
    ]
  }
}
```

#### 业务逻辑

1. 验证用户身份和活动有效性
2. 从数据库`demo_treasure_box`表中查询活动的宝箱配置
3. 从数据库`demo_user_points`表中查询用户当前积分
4. 从数据库`demo_user_box_exchange`表中查询用户已兑换的宝箱
5. 返回宝箱列表和用户积分数据

#### 可能的异常情况

- 错误码1002：用户未登录或身份验证失败
- 错误码1003：请求的活动ID不存在
- 错误码2001：活动未开始
- 错误码2002：活动已结束

[返回顶部](#跳跳棋活动系统api文档)

### 2.5 兑换宝箱

#### 接口描述

用户使用积分兑换指定宝箱。

#### 请求方式

`POST /api/box/exchange`

#### 请求参数

| 参数名（Parameter） | 类型（Type） | 必填（Required） | 描述（Description）   |
| -------------- | -------- | ------------ | ----------------- |
| activityId     | long     | 是            | 活动ID（Activity ID） |
| boxId          | long     | 是            | 宝箱ID（Box ID）      |

#### 响应参数

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "exchangeId": 5001,        // 兑换记录ID（Exchange ID）
    "boxId": 2,                // 宝箱ID（Box ID）
    "boxName": "白银宝箱",      // 宝箱名称（Box Name）
    "pointsCost": 2000,        // 消耗积分（Points Cost）
    "rewardType": 2,           // 奖励类型（Reward Type）
    "rewardAmount": 7,         // 奖励数量（Reward Amount）
    "rewardDesc": "7天VIP会员和普通头像框", // 奖励描述（Reward Description）
    "remainingPoints": 500     // 兑换后剩余积分（Remaining Points）
  }
}
```

#### 业务逻辑

1. 验证用户身份和活动有效性
2. 从数据库`demo_treasure_box`表中查询宝箱信息
3. 从数据库`demo_user_points`表中查询用户积分
4. 从数据库`demo_user_box_exchange`表中查询用户是否已兑换该宝箱
5. 检查用户积分是否足够
6. 如果积分足够且未兑换过该宝箱：
   - 扣减用户积分，更新`demo_user_points`表
   - 创建兑换记录，插入`demo_user_box_exchange`表
   - 创建奖励记录，插入`demo_user_reward`表
7. 返回兑换结果数据

#### 可能的异常情况

- 错误码1001：请求参数错误，如boxId不存在
- 错误码1002：用户未登录或身份验证失败
- 错误码2001：活动未开始
- 错误码2002：活动已结束
- 错误码4001：积分不足
- 错误码4002：宝箱已兑换

[返回顶部](#跳跳棋活动系统api文档)

### 2.6 获取积分排行榜

#### 接口描述

获取活动的用户积分排行榜。

#### 请求方式

`GET /api/leaderboard/points`

#### 请求参数

| 参数名（Parameter） | 类型（Type） | 必填（Required） | 描述（Description）   |
| -------------- | -------- | ------------ | ----------------- |
| activityId     | long     | 是            | 活动ID（Activity ID） |

#### 响应参数

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "userRank": 15,            // 当前用户排名（User Rank）
    "userPoints": 2500,        // 当前用户积分（User Points）
    "records": [
      {
        "rank": 1,             // 排名（Rank）
        "userId": 10002,       // 用户ID（User ID）
        "userName": "用户A",    // 用户名称（User Name）
        "userAvatar": "http://example.com/avatar/10002.jpg", // 用户头像（User Avatar）
        "points": 15000        // 积分（Points）
      },
      {
        "rank": 2,
        "userId": 10005,
        "userName": "用户B",
        "userAvatar": "http://example.com/avatar/10005.jpg",
        "points": 12500
      },
      // 更多排行记录
    ]
  }
}
```

#### 业务逻辑

1. 验证用户身份和活动有效性
2. 从数据库`demo_leaderboard`表中分页查询排行榜数据
3. 查询当前用户的排名和积分
4. 返回排行榜数据

#### 可能的异常情况

- 错误码1001：请求参数错误，如pageSize超出范围
- 错误码1002：用户未登录或身份验证失败
- 错误码1003：请求的活动ID不存在
- 错误码2001：活动未开始
- 错误码2002：活动已结束

[返回顶部](#跳跳棋活动系统api文档)
