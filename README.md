# 📋 企业员工考勤管理系统

基于 **SSM（Spring + Spring MVC + MyBatis）+ JSP** 的 Web 考勤管理系统。

## 🚀 功能一览

| 模块 | 功能 |
|------|------|
| 🔐 登录/注册 | 分管理员（admin）和员工（employee）两种角色 |
| 👥 员工管理 | 管理员增删员工 |
| 🏢 部门管理 | 管理员管理公司部门 |
| ⏰ 打卡签到 | 员工上班打卡、下班签退，自动判断是否迟到（9:00 为界） |
| 📝 请假申请 | 员工提交请假，管理员审批/拒绝 |
| 📊 考勤查看 | 员工看自己的记录，管理员看全员记录 |

## 🛠 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring + Spring MVC | 6.1.6 |
| ORM | MyBatis + mybatis-spring | 3.5.15 / 3.0.4 |
| 数据库 | MySQL | 8.0+ |
| 连接池 | Druid | 1.2.23 |
| 视图 | JSP + JSTL | 3.0 |
| 构建 | Maven | 3.9+ |
| 服务器 | Tomcat | 10.1+ / 11.x |
| JDK | Java 17+ | — |

## 📁 项目结构

```
src/main/java/
├── controller/          # Spring MVC 控制器
│   ├── LoginController.java      # 登录/注册/退出
│   ├── EmpController.java        # 员工管理
│   ├── DeptController.java       # 部门管理
│   ├── AttendanceController.java # 考勤打卡
│   └── LeaveController.java      # 请假审批
├── service/             # 业务逻辑接口
│   └── impl/            # 实现类（@Service）
├── mapper/              # MyBatis Mapper 接口
├── entity/              # 实体类（对应数据库表）
└── interceptor/         # 登录拦截器

src/main/resources/
├── applicationContext.xml  # Spring 核心配置
├── spring-mvc.xml          # Spring MVC 配置
├── mybatis-config.xml      # MyBatis 配置
├── db.properties.example   # 数据库配置模板
└── mapper/                 # MyBatis SQL 映射 XML

src/main/webapp/
├── login.jsp / register.jsp  # 登录/注册页
├── admin/                    # 管理员页面
├── employee/                 # 员工页面
├── static/css/common.css     # 全局样式
└── WEB-INF/
    ├── web.xml               # 部署描述符
    └── common/               # 公共 JSP 布局
```

## 🗄️ 数据库

导入 `src/main/resources/sql/` 下的 SQL 文件到 MySQL（库名：`employee_attendance`）。

表结构：`user` → `employee` → `department` / `attendance` / `leave_apply`

## ⚙️ 快速启动

### 1. 克隆项目

```bash
git clone https://github.com/MaZhaya/employee_attendance.git
cd employee_attendance
```

### 2. 配置数据库

```bash
# 复制模板并填写你的数据库信息
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

`db.properties` 内容：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/employee_attendance?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
jdbc.username=你的用户名
jdbc.password=你的密码
```

### 3. 导入建表 SQL

用 Navicat 或命令行导入资源目录下的 SQL 脚本。

### 4. 编译部署

```bash
mvn clean package
# 将 target/zuoye1-1.0-SNAPSHOT.war 部署到 Tomcat
```

或用 IntelliJ IDEA 直接配置 Tomcat 运行。

### 5. 访问

```
http://localhost:8080/zuoye1/login.jsp
```

## 🔑 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 员工 | emp001 | 123456 |

## 📖 配套文档

- [`代码解读文档.md`](代码解读文档.md) — 逐行代码解读，适合新手
- [`SSM改造方案文档.md`](SSM改造方案文档.md) — 从原生 Servlet 到 SSM 的改造过程

## 📄 License

仅供学习参考。
