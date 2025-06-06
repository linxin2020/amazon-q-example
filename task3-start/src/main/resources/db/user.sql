DROP TABLE IF EXISTS `demo_user`;
CREATE TABLE `demo_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像图片URL',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO `demo_user` (`username`, `password`, `nickname`, `email`, `phone`, `avatar_url`) VALUES 
('user1', 'e10adc3949ba59abbe56e057f20f883e', '跳跳虎', 'tiger78@gmail.com', '13567892341', '/avatar/user/1'),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '飞侠小子', 'peter_pan@163.com', '15823456789', '/avatar/user/2'),
('user3', 'e10adc3949ba59abbe56e057f20f883e', '萌企鹅', 'cool_penguin@qq.com', '13798765432', '/avatar/user/3'),
('user4', 'e10adc3949ba59abbe56e057f20f883e', '机器猫', 'doraemon2023@hotmail.com', '17612345678', '/avatar/user/4'),
('user5', 'e10adc3949ba59abbe56e057f20f883e', '樱桃', 'sweet_cherry@foxmail.com', '13234567890', '/avatar/user/5'),
('user6', 'e10adc3949ba59abbe56e057f20f883e', '小布丁^_^', 'pudding.lover@yeah.net', '18945678901', '/avatar/user/6'),
('user7', 'e10adc3949ba59abbe56e057f20f883e', '阳光猫咪', 'sunshine.cat@126.com', '13512345678', '/avatar/user/7'),
('user8', 'e10adc3949ba59abbe56e057f20f883e', '兔兔', 'bunny_jump@gmail.com', '15687654321', '/avatar/user/8'),
('user9', 'e10adc3949ba59abbe56e057f20f883e', '甜蜜熊宝宝', 'honey.bear@qq.com', '13998765432', '/avatar/user/9'),
('user10', 'e10adc3949ba59abbe56e057f20f883e', '飞象', 'flying.elephant@163.com', '17823456789', '/avatar/user/10'),
('user11', 'e10adc3949ba59abbe56e057f20f883e', '笑笑松鼠', 'happy-squirrel@outlook.com', '13387654321', '/avatar/user/11'),
('user12', 'e10adc3949ba59abbe56e057f20f883e', '梦幻泡泡', 'bubble.dream2023@gmail.com', '15698765432', '/avatar/user/12'),
('user13', 'e10adc3949ba59abbe56e057f20f883e', '星光闪闪', 'shining_star@foxmail.com', '13765432109', '/avatar/user/13'),
('user14', 'e10adc3949ba59abbe56e057f20f883e', '彩虹精灵', 'rainbow_elf@126.com', '18612345678', '/avatar/user/14'),
('user15', 'e10adc3949ba59abbe56e057f20f883e', '火箭', 'fast.rocket@qq.com', '13523456789', '/avatar/user/15'),
('user16', 'e10adc3949ba59abbe56e057f20f883e', '深海鲸鱼', 'deep_sea_whale@163.com', '17687654321', '/avatar/user/16'),
('user17', 'e10adc3949ba59abbe56e057f20f883e', '狮子王', 'king.lion@gmail.com', '13823456789', '/avatar/user/17'),
('user18', 'e10adc3949ba59abbe56e057f20f883e', '天使', 'angel_wings@hotmail.com', '15934567890', '/avatar/user/18'),
('user19', 'e10adc3949ba59abbe56e057f20f883e', '草莓蛋糕', 'strawberry.cake@sina.com', '13698765432', '/avatar/user/19'),
('user20', 'e10adc3949ba59abbe56e057f20f883e', '幸运草莓', 'lucky-strawberry@outlook.com', '18823456789', '/avatar/user/20'),
('user21', 'e10adc3949ba59abbe56e057f20f883e', '奇幻', 'fantasy2023@gmail.com', '13765432189', '/avatar/user/21'),
('user22', 'e10adc3949ba59abbe56e057f20f883e', '小丑鱼', 'nemo_fish@163.com', '15634567890', '/avatar/user/22'),
('user23', 'e10adc3949ba59abbe56e057f20f883e', '魔法师', 'magic_master@qq.com', '13887654321', '/avatar/user/23'),
('user24', 'e10adc3949ba59abbe56e057f20f883e', '探险家冒险王', 'adventure.hunter@126.com', '17923456789', '/avatar/user/24'),
('user25', 'e10adc3949ba59abbe56e057f20f883e', '熊猫', 'cute.panda@yeah.net', '13512987654', '/avatar/user/25'),
('user26', 'e10adc3949ba59abbe56e057f20f883e', '小蜜蜂嗡嗡', 'busy_bee@foxmail.com', '15887654321', '/avatar/user/26'),
('user27', 'e10adc3949ba59abbe56e057f20f883e', '海豚', 'smart.dolphin@gmail.com', '13623456789', '/avatar/user/27'),
('user28', 'e10adc3949ba59abbe56e057f20f883e', '向日葵', 'sunflower_smile@qq.com', '18787654321', '/avatar/user/28'),
('user29', 'e10adc3949ba59abbe56e057f20f883e', '炫酷火龙', 'cool-dragon@163.com', '13534567890', '/avatar/user/29'),
('user30', 'e10adc3949ba59abbe56e057f20f883e', '小博士', 'little_doctor@hotmail.com', '15687654321', '/avatar/user/30'),
('user31', 'e10adc3949ba59abbe56e057f20f883e', '风铃', 'wind_bell@126.com', '13987654321', '/avatar/user/31'),
('user32', 'e10adc3949ba59abbe56e057f20f883e', '猴子', 'monkey.king2023@gmail.com', '17823456789', '/avatar/user/32'),
('user33', 'e10adc3949ba59abbe56e057f20f883e', '雪花飘飘', 'snow_flake@foxmail.com', '13512345678', '/avatar/user/33'),
('user34', 'e10adc3949ba59abbe56e057f20f883e', '闪电侠', 'lightning_flash@qq.com', '15923456789', '/avatar/user/34'),
('user35', 'e10adc3949ba59abbe56e057f20f883e', '月亮女神', 'moon_goddess@163.com', '13687654321', '/avatar/user/35'),
('user36', 'e10adc3949ba59abbe56e057f20f883e', '蜗牛', 'slow.snail@outlook.com', '18623456789', '/avatar/user/36'),
('user37', 'e10adc3949ba59abbe56e057f20f883e', '音符精灵', 'music_notes@gmail.com', '13523456789', '/avatar/user/37'),
('user38', 'e10adc3949ba59abbe56e057f20f883e', '童话公主', 'fairy_princess@hotmail.com', '15787654321', '/avatar/user/38'),
('user39', 'e10adc3949ba59abbe56e057f20f883e', '科技迷', 'tech_geek@126.com', '13823456789', '/avatar/user/39'),
('user40', 'e10adc3949ba59abbe56e057f20f883e', '飞翔的小鸟', 'flying_bird@qq.com', '17687654321', '/avatar/user/40'),
('user41', 'e10adc3949ba59abbe56e057f20f883e', '狐狸', 'clever_fox@163.com', '13587654321', '/avatar/user/41'),
('user42', 'e10adc3949ba59abbe56e057f20f883e', '摇摇树', 'shake_tree@gmail.com', '15923456789', '/avatar/user/42'),
('user43', 'e10adc3949ba59abbe56e057f20f883e', '椰子', 'tropical_coconut@foxmail.com', '13712345678', '/avatar/user/43'),
('user44', 'e10adc3949ba59abbe56e057f20f883e', '糖果人', 'candy_man@qq.com', '18823456789', '/avatar/user/44'),
('user45', 'e10adc3949ba59abbe56e057f20f883e', '青草', 'green.grass@126.com', '13687654321', '/avatar/user/45'),
('user46', 'e10adc3949ba59abbe56e057f20f883e', '闪电火石', 'thunderbolt@yeah.net', '15623456789', '/avatar/user/46'),
('user47', 'e10adc3949ba59abbe56e057f20f883e', '海贝', 'sea_shell@gmail.com', '13823456789', '/avatar/user/47'),
('user48', 'e10adc3949ba59abbe56e057f20f883e', '梦想家', 'dreamer2023@hotmail.com', '17887654321', '/avatar/user/48'),
('user49', 'e10adc3949ba59abbe56e057f20f883e', '青蛙王子', 'frog_prince@163.com', '13534567890', '/avatar/user/49'),
('user50', 'e10adc3949ba59abbe56e057f20f883e', '彩云', 'colorful_cloud@qq.com', '15887654321', '/avatar/user/50'),
('user51', 'e10adc3949ba59abbe56e057f20f883e', '小棉花', 'cotton.candy@gmail.com', '13623456789', '/avatar/user/51'),
('user52', 'e10adc3949ba59abbe56e057f20f883e', '蓝天白云', 'blue_sky@126.com', '18734567890', '/avatar/user/52'),
('user53', 'e10adc3949ba59abbe56e057f20f883e', '书虫', 'bookworm@foxmail.com', '13987654321', '/avatar/user/53'),
('user54', 'e10adc3949ba59abbe56e057f20f883e', '袋鼠跳跳', 'jumping_kangaroo@163.com', '15623456789', '/avatar/user/54'),
('user55', 'e10adc3949ba59abbe56e057f20f883e', '金麦穗', 'golden_wheat@outlook.com', '13812345678', '/avatar/user/55'),
('user56', 'e10adc3949ba59abbe56e057f20f883e', '青春无敌', 'youth_power@gmail.com', '17923456789', '/avatar/user/56'),
('user57', 'e10adc3949ba59abbe56e057f20f883e', '探险达人', 'explorer_pro@qq.com', '13587654321', '/avatar/user/57'),
('user58', 'e10adc3949ba59abbe56e057f20f883e', '神奇魔术手', 'magic_hands@hotmail.com', '15823456789', '/avatar/user/58'),
('user59', 'e10adc3949ba59abbe56e057f20f883e', '甜饼干', 'sweet.cookie@126.com', '13698765432', '/avatar/user/59'),
('user60', 'e10adc3949ba59abbe56e057f20f883e', '雪人', 'snowman2023@163.com', '18687654321', '/avatar/user/60'),
('user61', 'e10adc3949ba59abbe56e057f20f883e', '蜜糖', 'honey_sweet@gmail.com', '13523456789', '/avatar/user/61'),
('user62', 'e10adc3949ba59abbe56e057f20f883e', '森林守护者', 'forest_guardian@foxmail.com', '15787654321', '/avatar/user/62'),
('user63', 'e10adc3949ba59abbe56e057f20f883e', '开心果', 'happy_nut@qq.com', '13823456789', '/avatar/user/63'),
('user64', 'e10adc3949ba59abbe56e057f20f883e', '彩虹糖', 'rainbow_candy@126.com', '17687654321', '/avatar/user/64'),
('user65', 'e10adc3949ba59abbe56e057f20f883e', '萌猫', 'cute_cat@163.com', '13534567890', '/avatar/user/65'),
('user66', 'e10adc3949ba59abbe56e057f20f883e', '魔术师', 'magician_great@yeah.net', '15923456789', '/avatar/user/66'),
('user67', 'e10adc3949ba59abbe56e057f20f883e', '星光', 'starlight@gmail.com', '13687654321', '/avatar/user/67'),
('user68', 'e10adc3949ba59abbe56e057f20f883e', '碧波', 'blue_waves@hotmail.com', '18823456789', '/avatar/user/68'),
('user69', 'e10adc3949ba59abbe56e057f20f883e', '火焰勇士', 'flame_warrior@qq.com', '13787654321', '/avatar/user/69'),
('user70', 'e10adc3949ba59abbe56e057f20f883e', '冰雪女王', 'ice_queen@163.com', '15623456789', '/avatar/user/70'),
('user71', 'e10adc3949ba59abbe56e057f20f883e', '木偶', 'puppet_show@126.com', '13923456789', '/avatar/user/71'),
('user72', 'e10adc3949ba59abbe56e057f20f883e', '丛林探险队', 'jungle_explorer@gmail.com', '17887654321', '/avatar/user/72'),
('user73', 'e10adc3949ba59abbe56e057f20f883e', '草莓派', 'strawberry_pie@foxmail.com', '13534567890', '/avatar/user/73'),
('user74', 'e10adc3949ba59abbe56e057f20f883e', '豆豆', 'little_bean@qq.com', '15887654321', '/avatar/user/74'),
('user75', 'e10adc3949ba59abbe56e057f20f883e', '太空人', 'space_walker@163.com', '13623456789', '/avatar/user/75'),
('user76', 'e10adc3949ba59abbe56e057f20f883e', '绿野仙踪', 'green_wonderland@outlook.com', '18723456789', '/avatar/user/76'),
('user77', 'e10adc3949ba59abbe56e057f20f883e', '花仙子', 'flower_fairy@gmail.com', '13987654321', '/avatar/user/77'),
('user78', 'e10adc3949ba59abbe56e057f20f883e', '海底总动员', 'undersea_world@hotmail.com', '15623456789', '/avatar/user/78'),
('user79', 'e10adc3949ba59abbe56e057f20f883e', '糖果雨', 'candy_rain@126.com', '13812345678', '/avatar/user/79'),
('user80', 'e10adc3949ba59abbe56e057f20f883e', '太阳花', 'sun_flower@qq.com', '17923456789', '/avatar/user/80'),
('user81', 'e10adc3949ba59abbe56e057f20f883e', '云朵朵', 'cloud_puff@163.com', '13587654321', '/avatar/user/81'),
('user82', 'e10adc3949ba59abbe56e057f20f883e', '铃铛', 'jingle_bell@gmail.com', '15823456789', '/avatar/user/82'),
('user83', 'e10adc3949ba59abbe56e057f20f883e', '宇宙之谜', 'cosmic_mystery@foxmail.com', '13698765432', '/avatar/user/83'),
('user84', 'e10adc3949ba59abbe56e057f20f883e', '钻石王', 'diamond_king@yeah.net', '18687654321', '/avatar/user/84'),
('user85', 'e10adc3949ba59abbe56e057f20f883e', '风车', 'windmill_spin@qq.com', '13523456789', '/avatar/user/85'),
('user86', 'e10adc3949ba59abbe56e057f20f883e', '旋风小子', 'tornado_kid@126.com', '15787654321', '/avatar/user/86'),
('user87', 'e10adc3949ba59abbe56e057f20f883e', '梦幻公主', 'dream_princess@163.com', '13823456789', '/avatar/user/87'),
('user88', 'e10adc3949ba59abbe56e057f20f883e', '彩虹马', 'rainbow_horse@gmail.com', '17687654321', '/avatar/user/88'),
('user89', 'e10adc3949ba59abbe56e057f20f883e', '叮当猫', 'ding_dong@hotmail.com', '13534567890', '/avatar/user/89'),
('user90', 'e10adc3949ba59abbe56e057f20f883e', '乌龟', 'slow_turtle@163.com', '15923456789', '/avatar/user/90'),
('user91', 'e10adc3949ba59abbe56e057f20f883e', '雨滴', 'rain_drop@126.com', '13687654321', '/avatar/user/91'),
('user92', 'e10adc3949ba59abbe56e057f20f883e', '萤火虫', 'firefly@qq.com', '18823456789', '/avatar/user/92'),
('user93', 'e10adc3949ba59abbe56e057f20f883e', '蒲公英', 'dandelion_fly@gmail.com', '13787654321', '/avatar/user/93'),
('user94', 'e10adc3949ba59abbe56e057f20f883e', '小王子', 'little_prince@foxmail.com', '15623456789', '/avatar/user/94'),
('user95', 'e10adc3949ba59abbe56e057f20f883e', '棒棒糖', 'lollipop_girl@163.com', '13923456789', '/avatar/user/95'),
('user96', 'e10adc3949ba59abbe56e057f20f883e', '水晶', 'crystal_clear@outlook.com', '17887654321', '/avatar/user/96'),
('user97', 'e10adc3949ba59abbe56e057f20f883e', '彩笔', 'color_pencil@gmail.com', '13534567890', '/avatar/user/97'),
('user98', 'e10adc3949ba59abbe56e057f20f883e', '音乐旋律', 'music_melody@qq.com', '15887654321', '/avatar/user/98'),
('user99', 'e10adc3949ba59abbe56e057f20f883e', '童话王国', 'fairy_kingdom@126.com', '13623456789', '/avatar/user/99'),
('user100', 'e10adc3949ba59abbe56e057f20f883e', '跳跳糖', 'popping_candy@163.com', '18723456789', '/avatar/user/100'); 