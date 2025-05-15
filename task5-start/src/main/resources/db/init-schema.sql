-- 跳跳棋活动系统数据库初始化脚本
-- 符合规范要求：
-- - 使用InnoDB引擎
-- - 所有表使用demo_前缀
-- - 主键自增长，统一取名为id
-- - 不使用外键约束
-- - 所有表具有create_time和update_time字段

-- 活动配置表
CREATE TABLE IF NOT EXISTS `demo_activity_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动ID',
    `name` varchar(100) NOT NULL COMMENT '活动名称',
    `start_time` datetime NOT NULL COMMENT '活动开始时间',
    `end_time` datetime NOT NULL COMMENT '活动结束时间',
    `total_cells` int(11) NOT NULL DEFAULT 30 COMMENT '总格子数',
    `daily_game_limit` int(11) NOT NULL DEFAULT 120 COMMENT '每日游戏次数上限',
    `max_dice_per_time` int(11) NOT NULL DEFAULT 10 COMMENT '单次最大使用骰子数',
    `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '活动状态(0:未开始 1:进行中 2:已结束)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_status_time` (`status`, `start_time`, `end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='活动配置表';

-- 游戏格子表
CREATE TABLE IF NOT EXISTS `demo_game_cell` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '格子ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `cell_index` int(11) NOT NULL COMMENT '格子索引',
    `cell_type` varchar(50) NOT NULL COMMENT '格子类型',
    `reward_type` varchar(50) NOT NULL COMMENT '奖励类型(POINTS:积分 GIFT:礼物道具)',
    `reward_amount` int(11) NOT NULL DEFAULT 0 COMMENT '奖励数量',
    `reward_id` varchar(100) DEFAULT NULL COMMENT '礼物道具ID',
    `reward_desc` varchar(200) DEFAULT NULL COMMENT '奖励描述',
    `fallback_points` int(11) DEFAULT 0 COMMENT '礼物已领取时的替代积分',
    `icon_url` varchar(255) DEFAULT NULL COMMENT '图标URL',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_activity_cell` (`activity_id`, `cell_index`),
    KEY `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='游戏格子表';

-- 宝箱配置表
CREATE TABLE IF NOT EXISTS `demo_treasure_box_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '宝箱配置ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `box_level` varchar(50) NOT NULL COMMENT '宝箱等级',
    `box_name` varchar(100) NOT NULL COMMENT '宝箱名称',
    `required_points` int(11) NOT NULL COMMENT '所需积分',
    `reward_type` varchar(50) NOT NULL COMMENT '奖励类型',
    `reward_content` varchar(500) NOT NULL COMMENT '奖励内容',
    `reward_desc` varchar(200) DEFAULT NULL COMMENT '奖励描述',
    `box_icon_url` varchar(255) DEFAULT NULL COMMENT '宝箱图标URL',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_activity_level` (`activity_id`, `box_level`),
    KEY `idx_activity_points` (`activity_id`, `required_points`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='宝箱配置表';

-- 用户游戏数据表
CREATE TABLE IF NOT EXISTS `demo_user_game_data` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '游戏数据ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `current_position` int(11) NOT NULL DEFAULT 0 COMMENT '当前位置',
    `remaining_chances` int(11) NOT NULL DEFAULT 0 COMMENT '剩余游戏机会',
    `daily_used_chances` int(11) NOT NULL DEFAULT 0 COMMENT '当日已用机会',
    `total_points` int(11) NOT NULL DEFAULT 0 COMMENT '总积分',
    `last_game_time` datetime DEFAULT NULL COMMENT '最后游戏时间',
    `is_new_user` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否新用户(0:否 1:是)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_activity` (`user_id`, `activity_id`),
    KEY `idx_activity_points` (`activity_id`, `total_points` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户游戏数据表';

-- 游戏记录表
CREATE TABLE IF NOT EXISTS `demo_game_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '游戏记录ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `dice_count` int(11) NOT NULL COMMENT '使用骰子数',
    `dice_points` varchar(100) NOT NULL COMMENT '骰子点数(逗号分隔)',
    `start_position` int(11) NOT NULL COMMENT '起始位置',
    `end_position` int(11) NOT NULL COMMENT '结束位置',
    `reward_info` varchar(500) DEFAULT NULL COMMENT '奖励信息(JSON)',
    `gained_points` int(11) NOT NULL DEFAULT 0 COMMENT '获得积分',
    `game_time` datetime NOT NULL COMMENT '游戏时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_activity` (`user_id`, `activity_id`),
    KEY `idx_game_time` (`game_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='游戏记录表';

-- 用户礼物道具记录表
CREATE TABLE IF NOT EXISTS `demo_user_gift_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '礼物记录ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `cell_id` bigint(20) NOT NULL COMMENT '格子ID',
    `gift_id` varchar(100) NOT NULL COMMENT '礼物道具ID',
    `gift_type` varchar(50) NOT NULL COMMENT '礼物道具类型',
    `gift_amount` int(11) NOT NULL DEFAULT 1 COMMENT '道具数量',
    `receive_time` datetime NOT NULL COMMENT '领取时间',
    `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态(0:待发放 1:已发放)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_cell` (`user_id`, `activity_id`, `cell_id`),
    KEY `idx_user_activity` (`user_id`, `activity_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户礼物道具记录表';

-- 积分记录表
CREATE TABLE IF NOT EXISTS `demo_point_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '积分记录ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `points` int(11) NOT NULL COMMENT '积分变化',
    `operation_type` varchar(50) NOT NULL COMMENT '操作类型(GAME_REWARD:游戏奖励 FALLBACK_GIFT:礼物替代 BOX_EXCHANGE:宝箱兑换)',
    `operation_desc` varchar(200) DEFAULT NULL COMMENT '操作描述',
    `reference_id` bigint(20) DEFAULT NULL COMMENT '关联ID',
    `reference_type` varchar(50) DEFAULT NULL COMMENT '关联类型(CELL:格子 GIFT:礼物 BOX:宝箱)',
    `operation_time` datetime NOT NULL COMMENT '操作时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_activity` (`user_id`, `activity_id`),
    KEY `idx_operation_time` (`operation_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='积分记录表';

-- 宝箱兑换记录表
CREATE TABLE IF NOT EXISTS `demo_box_exchange_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '兑换记录ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `box_id` bigint(20) NOT NULL COMMENT '宝箱ID',
    `box_level` varchar(50) NOT NULL COMMENT '宝箱等级',
    `cost_points` int(11) NOT NULL COMMENT '消耗积分',
    `reward_info` varchar(500) DEFAULT NULL COMMENT '奖励信息(JSON)',
    `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态(0:处理中 1:成功 2:失败)',
    `exchange_time` datetime NOT NULL COMMENT '兑换时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_activity` (`user_id`, `activity_id`),
    KEY `idx_exchange_time` (`exchange_time`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='宝箱兑换记录表';

-- 排行榜快照表
CREATE TABLE IF NOT EXISTS `demo_leaderboard_snapshot` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '快照ID',
    `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
    `snapshot_type` varchar(50) NOT NULL COMMENT '快照类型(HOURLY:每小时 DAILY:每日)',
    `rank_data` text NOT NULL COMMENT '排名数据(JSON)',
    `snapshot_time` datetime NOT NULL COMMENT '快照时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_activity_type_time` (`activity_id`, `snapshot_type`, `snapshot_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='排行榜快照表';
