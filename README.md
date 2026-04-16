# 📚 Word Learning - 智能背单词系统

一个基于 Spring Boot 的个人单词学习与复习平台，帮助用户系统化地管理单词、跟踪学习进度，并通过科学的间隔复习机制提升记忆效率。

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0%2B-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-5.7%2B-blue)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.0%2B-green)](https://www.thymeleaf.org/)

## ✨ 功能特性

- **用户系统**：支持用户注册、登录、会话管理，区分普通用户和管理员角色。
- **单词学习**：根据用户的学习进度智能推荐下一个待学习的单词，支持先作答后展示例句的学习模式。
- **进度追踪**：记录每个单词的答题正确/错误次数，自动计算掌握程度（未学习/学习中/已掌握）。
- **智能复习**：基于答题表现动态调整下次复习时间（间隔从 1 小时逐步延长至 30 天），实现科学的间隔重复（Spaced Repetition）。
- **学习历史**：完整记录学习历程，支持按时间倒序查看，并展示正确率、掌握状态等统计信息。
- **仪表盘**：可视化展示已学单词、学习中单词、已掌握单词及学习天数等核心数据。
- **后台管理**：管理员可对用户及单词数据进行统一管理。

## 🛠️ 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.x |
| ORM 框架 | Spring Data JPA + Hibernate |
| 模板引擎 | Thymeleaf |
| 数据库 | MySQL |
| 前端样式 | Bootstrap |
| 构建工具 | Maven |

## 📁 项目结构

```
单词/
├── src/main/java/com/example/wordlearning/
│   ├── controller/          # 控制器层（学习、认证、仪表盘等）
│   ├── service/             # 业务逻辑层（单词服务、用户服务）
│   ├── repository/          # 数据访问层（JPA Repository）
│   ├── entity/              # 实体类（Word、User、UserWordProgress）
│   └── dto/                 # 数据传输对象
├── src/main/resources/
│   ├── templates/           # Thymeleaf 模板页面
│   │   ├── dashboard.html   # 用户仪表盘
│   │   ├── study.html       # 单词学习页面
│   │   ├── history.html     # 学习历史页面
│   │   ├── stats.html       # 学习统计页面
│   │   ├── login.html       # 登录页面
│   │   ├── register.html    # 注册页面
│   │   └── admin/           # 管理员后台页面
│   ├── static/              # 静态资源（CSS、JS、视频等）
│   └── application.yml      # 应用配置文件
├── pom.xml                  # Maven 依赖配置
└── word_learning.sql        # 数据库初始化脚本（可选）
```

## 🚀 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.6+
- MySQL 5.7+（或 MariaDB）

### 安装步骤

1. **克隆项目**

```bash
git clone https://github.com/21000-lwy/word_learning.git
cd word_learning/单词
```

2. **配置数据库**

修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/word_learning?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username   # 替换为你的数据库用户名
    password: your_password   # 替换为你的数据库密码
```

> 💡 项目配置了 `createDatabaseIfNotExist=true`，数据库会自动创建。首次启动时 Hibernate 会自动根据实体类创建表结构（`ddl-auto: update`）。

3. **构建并运行**

```bash
# 清理并打包
mvn clean package

# 运行应用
mvn spring-boot:run
```

或者直接运行生成的 jar 包：

```bash
java -jar target/wordlearning-*.jar
```

4. **访问应用**

启动成功后，打开浏览器访问：http://localhost:8080

- 注册新账号即可开始使用。
- 默认没有内置单词数据，可通过管理员后台或直接向数据库 `words` 表导入单词。

## 📖 使用指南

### 用户端功能

| 功能 | 路径 | 说明 |
|------|------|------|
| 注册 | `/auth/register` | 创建新账号 |
| 登录 | `/auth/login` | 登录系统 |
| 仪表盘 | `/dashboard` | 查看学习概览 |
| 开始学习 | `/learning/study` | 进入单词学习模式 |
| 学习历史 | `/learning/history` | 查看已学单词记录 |
| 学习统计 | `/learning/stats` | 查看详细统计数据 |
| 个人设置 | `/settings` | 自定义学习偏好 |

### 管理员功能

管理员账号登录后将自动跳转至 `/admin/dashboard` 后台管理页面，可进行用户管理和单词库维护。

### 单词数据结构

单词实体包含以下字段：

| 字段 | 类型 | 说明 |
|------|------|------|
| `english` | String | 英文单词（唯一） |
| `chinese` | String | 中文释义 |
| `pronunciation` | String | 音标/发音 |
| `example` | String | 例句 |
| `difficulty_level` | Integer | 难度等级（1-5） |

## 🔧 配置说明

`application.yml` 主要配置项：

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| `server.port` | 应用端口 | 8080 |
| `spring.datasource.url` | 数据库连接地址 | - |
| `spring.jpa.hibernate.ddl-auto` | 表结构自动更新策略 | update |
| `spring.jpa.show-sql` | 是否打印 SQL 语句 | true |
| `spring.thymeleaf.cache` | 模板缓存（开发时建议 false） | false |

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request
