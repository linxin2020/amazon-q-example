-- 用户反馈模块数据库脚本

-- 反馈类别表
CREATE TABLE IF NOT EXISTS `demo_feedback_category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
    `name` varchar(50) NOT NULL COMMENT '类别名称',
    `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序顺序',
    `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(0:禁用 1:启用)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='反馈类别表';

-- 用户反馈表
CREATE TABLE IF NOT EXISTS `demo_user_feedback` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
    `category_id` bigint(20) NOT NULL COMMENT '类别ID',
    `title` varchar(200) NOT NULL COMMENT '反馈标题',
    `content` varchar(2000) NOT NULL COMMENT '反馈内容',
    `email` varchar(100) NOT NULL COMMENT '联系邮箱',
    `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态(0:未处理 1:已处理)',
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID(可选)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户反馈表';

-- 初始化反馈类别数据
INSERT INTO `demo_feedback_category` (`name`, `sort_order`, `status`) VALUES
('功能建议', 1, 1),
('问题反馈', 2, 1),
('使用咨询', 3, 1),
('其他', 4, 1); 