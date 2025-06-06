# 跳跳棋活动系统

## 项目介绍

跳跳棋活动系统是一个营销活动微服务，旨在通过游戏化的方式提升用户在平台的活跃度和消费行为。该系统围绕"跳跳棋"游戏展开，用户通过赠送特定礼物获得游戏机会，参与跳跳棋游戏获取积分，并可以用积分兑换宝箱奖励。同时，系统维护用户积分排行榜以激励竞争。

核心功能包括：
- **跳跳棋游戏**：用户通过掷骰子在棋盘上前进，不同格子提供不同奖励
- **宝箱兑换**：用户可用累积积分兑换不同等级的宝箱获取奖励
- **积分排行榜**：展示活动期间积分最多的用户，前三名获额外奖励

本项目作为微服务集成到现有平台中，与用户服务、支付服务、礼物服务、奖励服务和通知服务等其他微服务协同工作。系统采用Spring Boot框架开发，使用MyBatis进行数据持久化处理，保证高并发下的数据一致性和可靠性。

## 文档说明

项目`doc/`目录下的文件作用：

- `requirement.md`: 详细的活动需求文档，包含活动概述、业务目标、活动规则详情和架构师注释。文档描述了跳跳棋游戏的核心玩法、宝箱兑换机制和排行榜设计，以及各部分实现的技术要点。

- `prototype.svg`: 活动原型示意图，展示了跳跳棋活动的界面设计和用户交互流程，为开发和UI实现提供参考。

- `design.md`: 系统架构设计文档，包含总体架构、数据库设计（ER图、宽表设计）、领域模型（类图）和核心业务流程（流程图、时序图）等内容。文档详细描述了系统的微服务架构、模块划分、数据存储结构和关键业务流程，为开发团队提供全面的技术实现指导。所有图表均使用Mermaid语法，并附有中文注释说明。

- `specification.md`: 数据库设计规范文档，详细说明了项目数据库设计的各项规范要求，包括存储引擎选择、表命名规范、主键设计原则、关联设计方式、时间戳字段要求、索引设计原则、数据类型选择标准以及其他数据库设计注意事项。

- `api.md`: API接口文档，详细描述系统提供的所有接口，包括用户API、游戏API、宝箱API、排行榜API和头像API等。每个接口都包含URL、请求方法、参数说明（含中文解释）、响应格式、错误码、业务逻辑和数据库操作的详细说明，为前后端开发人员提供了明确的接口规范。文档采用结构化方式组织，支持通过顶部链接快速导航，并提供完整的错误码体系。

- `testcase.md`: 测试用例文档，全面覆盖系统各模块的测试场景和步骤。文档按功能模块、业务流程、接口、性能、安全性、异常场景和兼容性进行分类，包含详细的测试目标、预置条件、测试步骤、预期结果和验收标准。特别关注边界条件和特殊场景测试，如连续掷骰子跨越终点、礼物道具重复获取的积分替代、宝箱兑换的并发控制等，同时涵盖了微服务交互异常、数据一致性和极端负载等各种异常情况的处理测试。

## 数据库脚本

项目包含以下SQL脚本:

- `src/main/resources/db/init-schema.sql`: 数据库表结构初始化脚本，包含了所有表的创建语句。遵循了规范文档中定义的设计标准，使用InnoDB引擎，带有demo_前缀，主键自增长，不使用外键约束，所有表具有创建和更新时间字段。

- `src/main/resources/db/gamedata.sql`: 游戏基础数据初始化脚本，包含了跳跳棋游戏所需的棋盘格子配置、宝箱奖励配置等初始数据。该脚本用于系统初始化时填充必要的游戏配置数据。
