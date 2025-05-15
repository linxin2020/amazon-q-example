# 跳跳棋活动系统开发规范

## 目录

- [跳跳棋活动系统开发规范](#跳跳棋活动系统开发规范)
  - [目录](#目录)
  - [数据库规范](#数据库规范)
    - [1. 数据库命名规范](#1-数据库命名规范)
    - [2. 数据库设计规范](#2-数据库设计规范)
    - [3. 字段设计规范](#3-字段设计规范)
    - [4. 索引设计规范](#4-索引设计规范)
    - [5. SQL开发规范](#5-sql开发规范)
    - [6. 数据库操作规范](#6-数据库操作规范)
    - [7. 数据库安全规范](#7-数据库安全规范)
  - [代码开发规范](#代码开发规范)
    - [1. 项目结构规范](#1-项目结构规范)
      - [1.1 包结构规范](#11-包结构规范)
      - [1.2 技术栈规范](#12-技术栈规范)
        - [1.2.1 MyBatis XML配置规范](#121-mybatis-xml配置规范)
    - [2. 命名规范](#2-命名规范)
      - [2.1 数据访问层（DAO/Mapper）命名规范](#21-数据访问层daomapper命名规范)
        - [2.1.1 查询方法命名规范](#211-查询方法命名规范)
          - [单个对象查询](#单个对象查询)
          - [列表查询](#列表查询)
        - [2.1.2 增删改方法命名规范](#212-增删改方法命名规范)
          - [新增数据](#新增数据)
          - [修改数据](#修改数据)
          - [删除数据](#删除数据)
        - [2.1.3 其他规范](#213-其他规范)
      - [2.2 服务层（Service）命名规范](#22-服务层service命名规范)
        - [2.2.1 查询方法命名规范](#221-查询方法命名规范)
          - [单个对象查询](#单个对象查询-1)
          - [列表查询](#列表查询-1)
        - [2.2.2 增删改方法命名规范](#222-增删改方法命名规范)
          - [新增数据](#新增数据-1)
          - [修改数据](#修改数据-1)
          - [删除数据](#删除数据-1)
        - [2.2.3 参数使用规范](#223-参数使用规范)
      - [2.3 命名规范对比](#23-命名规范对比)
    - [3. 注释规范](#3-注释规范)
    - [4. 异常处理规范](#4-异常处理规范)
    - [5. 接口响应规范](#5-接口响应规范)
    - [6. 单元测试规范](#6-单元测试规范)
      - [6.1 测试框架](#61-测试框架)
      - [6.2 测试基类](#62-测试基类)
      - [6.3 Mapper层测试规范](#63-mapper层测试规范)
      - [6.4 Service层测试规范](#64-service层测试规范)
      - [6.2 测试基类](#62-测试基类-1)
      - [6.3 Mapper层测试规范](#63-mapper层测试规范-1)
      - [6.4 Service层测试规范](#64-service层测试规范-1)
      - [6.5 Controller层测试规范](#65-controller层测试规范)
      - [6.6 测试数据管理](#66-测试数据管理)
      - [6.7 测试命名与组织](#67-测试命名与组织)
    - [7. 常量与枚举规范](#7-常量与枚举规范)
      - [7.1 常量定义规范](#71-常量定义规范)
      - [7.2 枚举使用规范](#72-枚举使用规范)
      - [7.3 常量与枚举使用场景](#73-常量与枚举使用场景)
      - [7.4 常量与枚举维护规范](#74-常量与枚举维护规范)
  - [接口规范](#接口规范)
    - [1. RESTful API设计规范](#1-restful-api设计规范)
    - [2. 请求参数规范](#2-请求参数规范)
    - [3. 响应格式规范](#3-响应格式规范)
    - [4. 接口文档规范](#4-接口文档规范)
  - [缓存规范](#缓存规范)
    - [1. Redis缓存规范](#1-redis缓存规范)
    - [2. 本地缓存规范](#2-本地缓存规范)
  - [消息队列规范](#消息队列规范)
    - [1. AWS SQS使用规范](#1-aws-sqs使用规范)
    - [2. 消息处理规范](#2-消息处理规范)
  - [日志规范](#日志规范)
    - [1. 日志配置规范](#1-日志配置规范)
    - [2. 日志内容规范](#2-日志内容规范)
  - [测试规范](#测试规范)
    - [1. 单元测试规范](#1-单元测试规范)
    - [2. 集成测试规范](#2-集成测试规范)
    - [3. 测试用例设计规范](#3-测试用例设计规范)
  - [安全规范](#安全规范)
    - [1. 数据安全规范](#1-数据安全规范)
    - [2. 接口安全规范](#2-接口安全规范)
    - [3. 代码安全规范](#3-代码安全规范)
  - [部署规范](#部署规范)
    - [1. 环境规范](#1-环境规范)
    - [2. 部署流程规范](#2-部署流程规范)
    - [3. 版本管理规范](#3-版本管理规范)
  - [监控规范](#监控规范)
    - [1. 应用监控规范](#1-应用监控规范)
    - [2. 告警规范](#2-告警规范)

## 数据库规范

### 1. 数据库命名规范

- 数据库名称使用小写字母，单词间使用下划线分隔
- 表名统一使用 `demo_` 前缀，使用小写字母，单词间使用下划线分隔
- 字段名使用小写字母，单词间使用下划线分隔，避免使用数据库关键字
- 索引命名规则：
  - 主键索引：`PRIMARY KEY`
  - 唯一索引：`uk_字段名`
  - 普通索引：`idx_字段名`

### 2. 数据库设计规范

- 所有表必须使用 InnoDB 存储引擎
- 所有表必须包含主键，且主键名统一为 `id`，类型为 `bigint(20)`，自增长
- 所有表必须包含 `created_time` 和 `updated_time` 字段，类型为 `datetime`
  - `created_time` 默认值为 `CURRENT_TIMESTAMP`
  - `updated_time` 默认值为 `CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP`
- 不使用外键约束（Foreign Key），通过应用层保证数据一致性
- 所有表和字段必须添加注释，表注释使用 `COMMENT`，字段注释使用行内 `COMMENT`
- 字符集统一使用 `utf8mb4`，排序规则使用 `utf8mb4_general_ci`

### 3. 字段设计规范

- 所有字段应明确指定 `NOT NULL` 或允许为 `NULL`
- 用于计数的字段默认值设为 0，不要使用 NULL
- 状态字段使用 `tinyint` 类型，并在注释中明确说明每个状态值的含义
- 金额、积分等精确数值使用 `int` 或 `decimal` 类型，不使用 `float` 或 `double`
- 时间类型统一使用 `datetime`，不使用 `timestamp`

### 4. 索引设计规范

- 为经常作为查询条件的字段创建索引
- 为经常排序、分组的字段创建索引
- 联合索引遵循最左前缀原则，将选择性高的字段放在前面
- 索引字段数量控制在5个以内
- 避免冗余索引和重复索引
- 单表索引数量控制在5个以内

### 5. SQL开发规范

- 禁止使用 `SELECT *`，只查询需要的字段
- 避免大事务，保持事务短小
- 避免在循环中执行SQL语句
- 使用批量操作代替循环单条操作
- 避免使用存储过程和触发器
- 使用参数化查询，防止SQL注入
- 分页查询必须有合理的限制，防止大量数据查询
- 避免使用 `ORDER BY RAND()`

### 6. 数据库操作规范

- 线上环境禁止直接操作数据库，必须通过工具或脚本执行
- 重要数据变更必须有备份
- 大表结构变更必须在低峰期执行
- 批量数据导入导出必须控制并发和事务大小

### 7. 数据库安全规范

- 数据库账号遵循最小权限原则
- 生产环境数据库密码必须高强度且定期更换
- 敏感数据必须加密存储
- 定期备份数据库，并验证备份有效性
- 数据库服务器必须在内网，禁止直接暴露到公网

## 代码开发规范

### 1. 项目结构规范

- 采用标准的Spring Boot项目结构
- 包命名规范：`com.anycompany.demo.jumping.{module}`
- 主要模块划分：
  - `controller`: 控制器层，处理HTTP请求
  - `service`: 服务层，实现业务逻辑
  - `mapper`: 数据访问层，与数据库交互
  - `model`: 数据模型层，定义实体类
  - `common`: 通用组件，如异常处理、响应结果等
  - `message`: 消息处理相关组件
  - `generator`: 代码生成相关组件
  - `base`: 基础功能
  - `enumeration`: 枚举类
  - `constant`: 常量类

#### 1.1 包结构规范

- **基础功能**：放在基础包（base package）下的`base`子包中
  ```
  com.anycompany.demo.base
  ```

- **枚举类**：放在基础包下的`enumeration`子包中
  ```
  com.anycompany.demo.enumeration
  ```

- **常量类**：放在基础包下的`constant`子包中
  ```
  com.anycompany.demo.constant
  ```

- **模型类**：放在基础包下的`model`子包中
  ```
  com.anycompany.demo.model
  ```

- **数据访问层**：放在基础包下的`mapper`子包中
  ```
  com.anycompany.demo.mapper
  ```

- **服务层**：放在基础包下的`service`子包中
  ```
  com.anycompany.demo.service
  ```

- **控制器**：放在基础包下的`controller`子包中
  ```
  com.anycompany.demo.controller
  ```

#### 1.2 技术栈规范

- **核心框架**：Spring Boot
- **ORM框架**：MyBatis
- **缓存**：Redis
- **MyBatis配置**：使用XML形式的Mapper配置

##### 1.2.1 MyBatis XML配置规范

- XML映射文件应放置在resources目录下，与Mapper接口保持相同的路径结构
- 如对于`com.anycompany.demo.mapper.UserMapper`接口，其XML配置应位于：
  ```
  src/main/resources/com/anycompany/demo/mapper/UserMapper.xml
  ```

- XML文件的namespace应与Mapper接口全限定名一致：
  ```xml
  <mapper namespace="com.anycompany.demo.mapper.UserMapper">
      <!-- SQL映射内容 -->
  </mapper>
  ```

### 2. 命名规范

- 类名：使用大驼峰命名法（PascalCase），如`UserFeedbackController`
- 方法名：使用小驼峰命名法（camelCase），如`getFeedbackById`
- 变量名：使用小驼峰命名法，如`userFeedback`
- 常量名：全部大写，单词间用下划线分隔，如`ERROR_CODE`
- 包名：全部小写，如`com.anycompany.demo.jumping.controller`
- 接口名：使用大驼峰命名法，通常以"I"开头或以"able"、"Service"等结尾
- 测试类：以被测试类名 + `Test`作为后缀，如：`UserServiceTest`测试`UserService`
- 实现类：以接口名 + `Impl`作为后缀，如：`UserServiceImpl`实现`UserService`接口

#### 2.1 数据访问层（DAO/Mapper）命名规范

以`UserMapper`为例，数据访问层应遵循以下命名规范：

##### 2.1.1 查询方法命名规范

###### 单个对象查询
- 使用`getBy + 字段名`格式：
  - `getById` - 根据ID查询
  - `getByUsername` - 根据用户名查询
  - `getByEmail` - 根据邮箱查询

###### 列表查询
- 使用`find + 范围`格式：
  - `findAll` - 查询所有记录
  - `findList` - 条件查询列表
  - `findListWithPagination` - 分页查询（方法名包含功能特性）

##### 2.1.2 增删改方法命名规范

###### 新增数据
- 直接使用`insert`动词

###### 修改数据
- 直接使用`update`动词

###### 删除数据
- 单个删除：`deleteById` - 按ID删除
- 批量操作：`batchDelete` - 以`batch`前缀表示批量操作

##### 2.1.3 其他规范
- 参数命名采用小驼峰式命名法
- 使用`@Param`注解标注参数名
- 返回值类型明确（实体对象、List集合或影响行数）
- 每个方法都应有完整的JavaDoc注释，包含功能描述、参数说明和返回值说明

#### 2.2 服务层（Service）命名规范

以`UserService`为例，服务层应遵循以下命名规范：

##### 2.2.1 查询方法命名规范

###### 单个对象查询
- 使用`loadBy + 字段名`格式：
  - `loadUserById` - 根据ID加载
  - `loadUserByUsername` - 根据用户名加载
  - `loadUserByEmail` - 根据邮箱加载

###### 列表查询
- **通用搜索方法**：使用`search + 对象名复数`格式，并传入必要的查询参数
  - `searchUsers(String keyword, Date startDate, Date endDate, Integer status, int offset, int limit)` - 使用多个参数指定不同查询条件和分页信息

- **特定条件搜索**：仅在业务场景确实只需要按单一属性搜索时，可以使用`search + 对象名复数 + By + 字段名`格式
  - `searchUsersByUsername(String username, int offset, int limit)` - 专门用于按用户名搜索的场景
  - `searchUsersByEmail(String email, int offset, int limit)` - 专门用于按邮箱搜索的场景

##### 2.2.2 增删改方法命名规范

###### 新增数据
- 使用`create + 对象名`格式：
  - `createUser` - 创建用户
- **返回值规范**：应返回实体的ID
  ```java  
  // 推荐
  Long createUser(String username, String password, ...);
  ```

###### 修改数据
- 使用`update + 对象名`格式：
  - `updateUser` - 更新用户
- **返回值规范**：应返回实体的ID
  ```java  
  // 推荐
  Long updateUser(Long id, String username, ...);
  ```

###### 删除数据
- 单个删除：`delete + 对象名` - 如`deleteUser`
- 批量操作：`batchDelete + 对象名复数` - 如`batchDeleteUsers`
- **返回值规范**：
  - 单个删除应返回被删除实体的ID
  - 批量删除应返回被删除实体ID的数组或集合
  ```java  
  // 推荐
  Long deleteUser(Long id);
  Long[] batchDeleteUsers(Long[] ids);
  ```

##### 2.2.3 参数使用规范

- **不应使用Entity对象**：Service层API参数不应直接使用Entity实体对象
  ```java
  // 推荐
  Long createUser(String username, String password, String nickname, String email, String phone, String avatarUrl);
  ```

- **使用具体属性参数**：应明确列出需要的具体属性
  ```java
  // 推荐
  List<User> searchUsers(String keyword, Date startDate, Date endDate, Integer status, int offset, int limit);
  ```

- **参数过多时的处理**：当参数确实过多时，可考虑使用请求模型对象（非Entity实体）
  ```java
  // 请求模型对象（非Entity实体）
  public class UserSearchRequest {
      private String keyword;
      private Date startDate;
      private Date endDate;
      private Integer status;
      private int offset;
      private int limit;
      // getters and setters
  }
  
  // 使用请求模型对象的API
  List<User> searchUsers(UserSearchRequest request);
  ```

#### 2.3 命名规范对比

| 操作类型 | Mapper层 | Service层 |
|---------|---------|----------|
| 单个查询 | `getById` | `loadUserById` |
| 条件查询 | `getByUsername` | `loadUserByUsername` |
| 列表查询 | `findList` | `searchUsers(参数列表)` |
| 分页查询 | `findListWithPagination` | `searchUsers(参数列表, offset, limit)` |
| 新增 | `insert` | `createUser` → 返回ID |
| 更新 | `update` | `updateUser` → 返回ID |
| 删除 | `deleteById` | `deleteUser` → 返回ID |
| 批量删除 | `batchDelete` | `batchDeleteUsers` → 返回ID数组 |

### 3. 注释规范

- 类注释：每个类必须有类注释，说明类的功能和用途
- 方法注释：公共方法必须有方法注释，包括方法说明、参数说明和返回值说明
- 参数注释：复杂参数或不易理解的参数需要添加注释说明
- 代码注释：复杂逻辑需要添加行内注释说明
- 使用Javadoc标准注释格式，如`@param`、`@return`、`@throws`等

### 4. 异常处理规范

- 使用自定义异常类`BusinessException`处理业务异常
- 使用枚举类`ErrorCode`统一管理错误码和错误信息
- 使用全局异常处理器`GlobalExceptionHandler`统一处理异常并返回标准响应
- 异常分类：参数校验异常、业务逻辑异常、系统异常等
- 异常日志：业务异常使用WARN级别，系统异常使用ERROR级别

### 5. 接口响应规范

- 使用统一的响应结果类`ResponseResult<T>`封装接口响应
- 响应结构包含状态码`code`、消息`message`和数据`data`
- 成功响应：状态码为200，消息为"success"
- 失败响应：状态码为对应错误码，消息为对应错误信息
- 接口返回值统一使用`ResponseResult<T>`类型

### 6. 单元测试规范

#### 6.1 测试框架

- 使用Spring Boot Test进行单元测试
- 测试类应当使用`@SpringBootTest`注解

#### 6.2 测试基类

- **测试基类**：为所有测试创建基础抽象测试类（如`BaseTest`），提供通用功能和测试数据创建方法
- **测试类继承**：服务层和控制器层测试类必须继承基础测试类，确保测试一致性和可维护性
- **通用测试数据创建**：在基础测试类中提供创建测试数据的通用方法
- **通用测试依赖注入**：常用的服务依赖应在基础测试类中声明

#### 6.3 Mapper层测试规范

- **测试类组织**：每个Mapper接口应有对应的测试类
- **测试数据准备**：每个测试方法应自行准备所需的测试数据
- **增删改查测试**：对于每种数据库操作类型，验证其行为符合预期

#### 6.4 Service层测试规范

- **测试类组织**：每个Service接口应有对应的测试类，继承BaseTest

#### 6.2 测试基类

- **测试基类**：为所有测试创建基础抽象测试类（如`BaseTest`），提供通用功能和测试数据创建方法
- **测试类继承**：服务层和控制器层测试类必须继承基础测试类，确保测试一致性和可维护性
- **通用测试数据创建**：在基础测试类中提供创建测试数据的通用方法
- **通用测试依赖注入**：常用的服务依赖应在基础测试类中声明

#### 6.3 Mapper层测试规范

- **测试类组织**：每个Mapper接口应有对应的测试类
- **测试数据准备**：每个测试方法应自行准备所需的测试数据
- **增删改查测试**：对于每种数据库操作类型，验证其行为符合预期

#### 6.4 Service层测试规范

- **测试类组织**：每个Service接口应有对应的测试类，继承BaseTest
- **测试覆盖范围**：应覆盖Service中定义的所有公共方法
- **测试用例命名**：按照`test + 被测方法名`的方式命名
- **新增操作测试**：应验证数据确实可以读取到
- **更新操作测试**：应验证数据确实已被更新
- **删除操作测试**：应验证数据确实已不存在

#### 6.5 Controller层测试规范

- **测试类组织**：每个Controller应有对应的测试类，继承BaseTest，使用@AutoConfigureMockMvc注解
- **测试数据准备**：可使用BaseTest中的方法准备测试数据
- **HTTP请求构建**：使用MockMvc构建和发送HTTP请求
- **新增接口测试**：验证返回正确状态码和ID，并通过Service验证数据已正确保存
- **修改接口测试**：验证返回正确状态码和ID，并通过Service验证数据已正确更新
- **删除接口测试**：验证返回正确状态码和ID，并通过Service验证数据已被删除
- **查询接口测试**：验证返回正确状态码和数据，并通过Service检查返回数据的完整性和正确性

#### 6.6 测试数据管理

- **事务管理**：所有测试方法都应在事务中执行，并在测试完成后回滚
- **测试数据创建**：
  - 不能假设依赖的数据都存在，除非有特殊说明
  - 测试前应自行创建所需的测试数据
  - 可以创建基础类或工具方法来处理依赖数据的创建

#### 6.7 测试命名与组织

- 测试方法名应清晰表达测试目的和场景
- 可按功能或场景对测试方法进行分组
- 每个测试方法只测试一个功能点
- 测试类中的测试方法应按照被测类中的方法顺序排列

#### 6.8 时间戳（Timestamp）校验规范

- **插值法校验**：单元测试中对timestamp的校验必须使用插值法，而非固定值比较
- **响应时间分级**：
  - **快速响应接口**：对于响应很快的接口（如本地操作、简单查询），timestamp误差应控制在1秒以内
  - **标准响应接口**：对于标准响应时间的接口，timestamp误差可接受范围为3-5秒
  - **慢速响应接口**：对于涉及复杂计算、远程调用或批量处理的接口，应根据实际业务场景酌情设计误差范围
- **对于时间的单元测试**：只需要在10秒误差范围就行了，因为测试有耗时，不会那么精确
- **校验实现方式**：
  ```java
  // 快速响应接口时间戳校验示例
  long currentTime = System.currentTimeMillis();
  long timestamp = response.getTimestamp();
  assertTrue(Math.abs(currentTime - timestamp) < 1000, "时间戳误差应在1秒内");
  
  // 慢速响应接口时间戳校验示例
  long beforeCallTime = System.currentTimeMillis();
  Response response = service.slowOperation();
  long afterCallTime = System.currentTimeMillis();
  long timestamp = response.getTimestamp();
  assertTrue(timestamp >= beforeCallTime && timestamp <= afterCallTime, 
             "时间戳应在操作执行期间内生成");
  ```
- **测试环境考量**：在CI/CD环境中运行测试时，可能需要适当放宽时间戳误差范围，以适应不同执行环境的性能差异

### 7. 常量与枚举规范

#### 7.1 常量定义规范

- **常量使用原则**：所有非用户生成内容(非UGC)的固定数值和字符串必须使用常量或枚举定义，禁止在代码中直接使用字面量
- **常量命名**：全部大写，单词间用下划线分隔，如`MAX_RETRY_COUNT`、`DEFAULT_PAGE_SIZE`
- **常量分类**：
  - 全局常量：放在`com.anycompany.demo.constant.GlobalConstants`类中
  - 模块常量：放在对应模块的常量类中，如`com.anycompany.demo.constant.GameConstants`

#### 7.2 枚举使用规范

- **枚举使用原则**：所有具有固定取值范围的状态、类型等数据必须使用枚举定义
- **枚举定义位置**：所有枚举类放在`com.anycompany.demo.enumeration`包下
- **枚举命名**：使用大驼峰命名法，以具体业务名称开头，以`Enum`、`Type`、`Status`等结尾，如`GameStatusEnum`、`RewardType`
- **枚举结构**：
  ```java
  public enum GameStatusEnum {
      NOT_STARTED(0, "未开始"),
      IN_PROGRESS(1, "进行中"),
      FINISHED(2, "已结束"),
      CANCELLED(3, "已取消");
      
      private final int code;
      private final String desc;
      
      GameStatusEnum(int code, String desc) {
          this.code = code;
          this.desc = desc;
      }
      
      // getter方法
      public int getCode() {
          return code;
      }
      
      public String getDesc() {
          return desc;
      }
      
      // 根据code获取枚举实例的静态方法
      public static GameStatusEnum getByCode(int code) {
          for (GameStatusEnum status : values()) {
              if (status.getCode() == code) {
                  return status;
              }
          }
          return null;
      }
  }
  ```

#### 7.3 常量与枚举使用场景

- **配置常量**：系统配置参数、默认值等使用常量类定义
- **业务状态**：业务状态、流程状态等使用枚举定义
- **错误码**：系统错误码使用枚举定义
- **固定选项**：下拉选项、固定选择项等使用枚举定义
- **魔法数字**：代码中的魔法数字（如0、1、2表示状态）必须替换为常量或枚举

#### 7.4 常量与枚举维护规范

- **文档注释**：所有常量和枚举必须有完整的JavaDoc注释，说明其用途和取值含义
- **集中管理**：相关联的常量应集中在同一个常量类中管理
- **版本控制**：常量和枚举的变更必须经过代码评审，并记录变更原因
- **向后兼容**：已发布的常量和枚举值不得随意修改，如需新增值应保持向后兼容

## 接口规范

### 1. RESTful API设计规范

- 使用HTTP方法表示操作类型：
  - GET：查询资源
  - POST：创建资源
  - PUT：更新资源
  - DELETE：删除资源
- URL命名使用名词复数形式，如`/api/feedback`
- 使用URL参数传递资源标识，如`/api/feedback/{id}`
- 使用查询参数传递过滤条件，如`/api/feedback?status=1`
- 版本控制：在URL中包含版本信息，如`/api/v1/feedback`

### 2. 请求参数规范

- GET请求：使用查询参数传递参数
- POST/PUT请求：使用JSON格式的请求体传递参数
- 参数命名使用小驼峰命名法
- 必须参数和可选参数明确区分
- 使用`@Validated`和相关注解进行参数校验

### 3. 响应格式规范

- 响应格式统一使用JSON
- 响应结构遵循统一的格式：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {}
  }
  ```
- 分页响应结构：
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "list": [],
      "total": 0,
      "pageNum": 1,
      "pageSize": 10
    }
  }
  ```
- 日期时间格式统一使用ISO 8601标准：`yyyy-MM-dd'T'HH:mm:ss.SSSZ`

### 4. 接口文档规范

- 接口文档必须包含以下内容：
  - 接口名称和描述
  - 请求URL和方法
  - 请求参数说明
  - 响应结果说明
  - 错误码说明
  - 示例请求和响应
- 接口变更必须更新文档
- 接口文档应提供中英文双语说明

## 缓存规范

### 1. Redis缓存规范

- 键命名规范：`{application}:{module}:{function}:{id}`
- 缓存过期时间必须设置，避免缓存永不过期
- 缓存更新策略：先更新数据库，再删除缓存
- 防止缓存雪崩：设置不同的过期时间
- 防止缓存击穿：使用互斥锁或布隆过滤器
- 缓存预热：系统启动时加载热点数据到缓存

### 2. 本地缓存规范

- 适用于变更频率低的数据
- 设置合理的缓存容量和过期策略
- 提供缓存刷新机制
- 与分布式缓存结合使用，作为多级缓存

## 消息队列规范

### 1. AWS SQS使用规范

- 队列命名规范：`{application}-{module}-{function}-queue`
- 消息结构必须包含：消息类型、消息ID、消息内容、时间戳
- 消息处理失败后，使用死信队列（DLQ）处理
- 消息重试策略：指数退避算法，最大重试次数限制
- 消息幂等性处理：使用唯一标识符防止重复处理

### 2. 消息处理规范

- 异步处理：非核心流程使用异步消息处理
- 消息优先级：根据业务重要性设置消息优先级
- 消息监控：监控消息处理状态、延迟和失败率
- 消息追踪：记录消息处理的完整链路

## 日志规范

### 1. 日志配置规范

- 日志级别：
  - ERROR：系统错误，影响系统正常运行
  - WARN：警告信息，可能会影响系统运行
  - INFO：一般信息，记录系统正常运行状态
  - DEBUG：调试信息，仅在开发环境使用
- 日志输出格式：时间、级别、线程、类名、消息
- 日志文件滚动策略：按日期和大小滚动
- 生产环境默认使用INFO级别，开发环境可使用DEBUG级别

### 2. 日志内容规范

- 异常日志必须记录完整的异常堆栈
- 敏感信息（如密码、token）不得记录到日志
- 接口请求和响应日志应记录关键参数
- 业务操作日志应记录操作人、操作时间、操作内容
- 使用MDC记录请求ID，便于追踪完整调用链

## 测试规范

### 1. 单元测试规范

- 测试类命名：`{被测试类名}Test`
- 测试方法命名：`test{方法名}_{测试场景}`
- 测试覆盖率要求：核心业务逻辑覆盖率不低于80%
- 使用断言验证测试结果
- 测试数据准备：使用`@BeforeEach`或辅助方法创建测试数据
- 测试隔离：使用`@Transactional`注解保证测试数据回滚
- 不使用Mockito技术

### 2. 集成测试规范

- 使用`@SpringBootTest`进行集成测试
- 测试环境配置：使用测试专用配置文件
- 外部依赖模拟：使用Mock对象模拟外部服务
- 数据库测试：使用内存数据库或测试数据库
- API测试：使用`MockMvc`或`TestRestTemplate`测试REST接口
- 不使用Mockito技术

### 3. 测试用例设计规范

- 正常场景测试：验证功能在正常输入下的行为
- 边界条件测试：验证功能在边界值输入下的行为
- 异常场景测试：验证功能在异常输入下的行为
- 性能测试：验证功能在高负载下的性能表现

## 安全规范

### 1. 数据安全规范

- 敏感数据加密存储，如用户密码使用不可逆加密
- 传输数据加密，使用HTTPS协议
- 敏感数据脱敏展示，如手机号、邮箱部分字符用*代替
- 数据访问权限控制，遵循最小权限原则

### 2. 接口安全规范

- 接口访问认证：使用token或签名机制
- 接口访问频率限制：防止恶意请求
- 接口参数校验：防止非法参数
- CSRF防护：使用CSRF Token
- XSS防护：输入过滤，输出编码

### 3. 代码安全规范

- 避免硬编码敏感信息，使用配置文件或环境变量
- 避免SQL注入：使用参数化查询
- 避免命令注入：过滤特殊字符
- 避免日志注入：过滤日志内容中的特殊字符
- 定期进行代码安全审计

## 部署规范

### 1. 环境规范

- 环境分类：开发环境、测试环境、预发布环境、生产环境
- 环境隔离：不同环境使用不同的服务器和数据库
- 配置管理：使用配置中心管理不同环境的配置
- 环境标识：在日志和监控中明确标识当前环境

### 2. 部署流程规范

- 代码提交：提交到版本控制系统
- 代码构建：使用CI工具自动构建
- 单元测试：构建过程中自动运行单元测试
- 制品生成：生成可部署的制品（如JAR包）
- 环境部署：使用CD工具自动部署
- 冒烟测试：部署后自动运行冒烟测试

### 3. 版本管理规范

- 版本号格式：主版本号.次版本号.修订号
- 主版本号：不兼容的API变更
- 次版本号：向下兼容的功能性新增
- 修订号：向下兼容的问题修正
- 版本标记：使用Git标签标记发布版本

## 监控规范

### 1. 应用监控规范

- 健康检查：提供健康检查接口
- 性能指标：监控响应时间、吞吐量、错误率
- JVM监控：内存使用、GC情况、线程状态
- 接口监控：接口调用次数、响应时间、错误率
- 业务监控：核心业务指标监控

### 2. 告警规范

- 告警级别：严重、警告、提示
- 告警渠道：邮件、短信、即时通讯工具
- 告警内容：告警原因、影响范围、处理建议
- 告警收敛：相同告警在一定时间内只发送一次
- 告警升级：严重告警未处理时进行升级通知
