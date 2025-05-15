-- 活动配置表数据
INSERT INTO `demo_activity_config` (id, name, start_time, end_time, total_cells, daily_game_limit, max_dice_per_time, status, create_time, update_time) 
VALUES (100, '跳跳棋欢乐季_1746530339860','2025-05-05 19:19:00','2025-05-13 19:19:00',30,120,10,1,'2025-05-06 11:18:59','2025-05-06 11:19:00');
ALTER TABLE `demo_activity_config` AUTO_INCREMENT = 101;

-- 游戏格子表数据
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,0,'START','POINTS',0,NULL,'起点',0,'/images/cell_start.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,1,'GIFT','GIFT',1,'avatar-frame/space-traveler/558','avatar-frame - space-traveler',29,'/images/cell_gift_avatar-frame.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,2,'NORMAL','POINTS',17,NULL,'积分奖励',0,'/images/cell_normal.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,3,'GIFT','POINTS',42,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,4,'GIFT','POINTS',45,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,5,'BONUS','POINTS',30,NULL,'积分奖励',0,'/images/cell_bonus.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,6,'NORMAL','POINTS',37,NULL,'积分奖励',0,'/images/cell_normal.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,7,'GIFT','POINTS',48,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,8,'CHANCE','POINTS',18,NULL,'积分奖励',0,'/images/cell_chance.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,9,'BONUS','POINTS',35,NULL,'积分奖励',0,'/images/cell_bonus.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,10,'BONUS','POINTS',37,NULL,'积分奖励',0,'/images/cell_bonus.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,11,'GIFT','POINTS',24,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,12,'CHANCE','POINTS',14,NULL,'积分奖励',0,'/images/cell_chance.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,13,'GIFT','GIFT',1,'entry-effect/forest-guardian/341','entry-effect - forest-guardian',24,'/images/cell_gift_entry-effect.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,14,'SPECIAL','POINTS',30,NULL,'积分奖励',0,'/images/cell_special.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,15,'NORMAL','POINTS',14,NULL,'积分奖励',0,'/images/cell_normal.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,16,'SPECIAL','POINTS',34,NULL,'积分奖励',0,'/images/cell_special.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,17,'NORMAL','POINTS',10,NULL,'积分奖励',0,'/images/cell_normal.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,18,'CHANCE','POINTS',47,NULL,'积分奖励',0,'/images/cell_chance.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,19,'GIFT','POINTS',18,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,20,'NORMAL','POINTS',19,NULL,'积分奖励',0,'/images/cell_normal.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,21,'GIFT','GIFT',1,'avatar-frame/snake/198','avatar-frame - snake',44,'/images/cell_gift_avatar-frame.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,22,'GIFT','GIFT',1,'badge/tiger/190','badge - tiger',29,'/images/cell_gift_badge.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,23,'CHANCE','POINTS',23,NULL,'积分奖励',0,'/images/cell_chance.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,24,'BONUS','POINTS',47,NULL,'积分奖励',0,'/images/cell_bonus.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,25,'GIFT','POINTS',44,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,26,'SPECIAL','POINTS',14,NULL,'积分奖励',0,'/images/cell_special.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,27,'GIFT','POINTS',44,NULL,'积分奖励',0,'/images/cell_gift.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,28,'GIFT','GIFT',1,'title/ocean-explorer/168','title - ocean-explorer',33,'/images/cell_gift_title.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
INSERT INTO `demo_game_cell` (activity_id, cell_index, cell_type, reward_type, reward_amount, reward_id, reward_desc, fallback_points, icon_url, create_time, update_time) 
VALUES (100,29,'END','POINTS',100,NULL,'终点',0,'/images/cell_end.png','2025-05-06 11:19:00','2025-05-06 11:19:00');
