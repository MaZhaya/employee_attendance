# 📋 项目升级方案：从 原生 Servlet → SSM + JSP

> **状态：✅ 改造已完成（2026-06-25）**
>
> 当前项目：原生 Servlet + 手写 MyBatis + JSP（内联样式 + Scriptlet）
>
> 目标项目：**SSM（Spring + Spring MVC + MyBatis）+ JSP（优化 UI）**

---

## 目录

1. [现有项目问题诊断](#1-现有项目问题诊断)
2. [SSM 是什么？和现在的区别](#2-ssm-是什么和现在的区别)
3. [改造后的架构一览](#3-改造后的架构一览)
4. [文件改动清单](#4-文件改动清单)
5. [pom.xml 依赖变化](#5-pomxml-依赖变化)
6. [Spring 配置文件（新增）](#6-spring-配置文件新增)
7. [Controller 层：Servlet → Spring MVC](#7-controller-层servlet--spring-mvc)
8. [Service 层：注入改造](#8-service-层注入改造)
9. [Mapper 层：整合 Spring](#9-mapper-层整合-spring)
10. [JSP 前端优化方案](#10-jsp-前端优化方案)
11. [删除的文件清单](#11-删除的文件清单)
12. [分步实施计划](#12-分步实施计划)
13. [改造前后对比速查表](#13-改造前后对比速查表)

---

## 1. 现有项目问题诊断

### 1.1 架构层面

| 问题 | 说明 |
|------|------|
| 🔴 手动管理对象 | 每个 Servlet/Service 都 `new` 对象，没有依赖注入 |
| 🔴 手动管理数据库会话 | `MyBatisUtil.getSqlSession()` 到处调用，没有统一事务管理 |
| 🔴 Servlet 路由混乱 | 靠 `action` 参数区分操作（`?action=list`），不规范 |
| 🔴 无统一异常处理 | 出错直接堆栈打印，用户体验差 |
| 🔴 代码重复 | 每个 Servlet 都在重复"检查登录 → 获取参数 → 调用 Service → 转发" |

### 1.2 前端 JSP 层面

| 问题 | 说明 |
|------|------|
| 🟡 内联 CSS | 每个页面把 `<style>` 写在 `<head>` 里，没有公共样式文件 |
| 🟡 Java Scriptlet 混合 | `<% for(...){ %>` 和 HTML 混在一起，可读性差 |
| 🟡 无公共布局 | 每个页面独立写戻按钮、导航栏，修改一处要改所有页面 |
| 🟡 样式不统一 | 登录页和后台页风格不一致 |
| 🟡 无响应式设计 | 手机端没法看 |
| 🟡 无前端校验 | 表单提交全靠后端，用户体验差 |
| 🟡 页面功能不完整 | 缺少编辑员工、编辑部门功能 |
| 🔴 JSP 与 Servlet 不一致 | 多个 JSP 使用的变量名/实体类名和 Servlet 传的不一样（见下文） |

### 1.3 已知的 JSP ↔ Servlet 不匹配 BUG

| 文件 | 问题 |
|------|------|
| `register.jsp` | form action 指向 `/registerServlet`，但实际是 `/register` |
| `employee/leaveApply.jsp` | 表单 action 值用 `"apply"`，但 Servlet 期望 `"addLeave"` |
| `employee/myAttendance.jsp` | 读取 `request.getAttribute("myAttList")`，但 Servlet 设置的是 `"attendanceList"` |
| `employee/myLeave.jsp` | import `entity.Leave`，但实体类叫 `LeaveApply`；读取 `"myLeaveList"` 但 Servlet 设置 `"leaveList"` |
| `admin/leaveAudit.jsp` | import `entity.Leave`，实体名不对；读取 `"leaveAllList"` 但 Servlet 设置 `"leaveList"` |
| `admin/leaveAudit.jsp` | 使用 `l.getStartDate()/getEndDate()/getStatus()`，但 `LeaveApply` 的方法叫 `getStartTime()/getEndTime()/getApproveStatus()` |

---

## 2. SSM 是什么？和现在的区别

### 2.1 概念一句话

| 字母 | 全称 | 作用 | 替代了现在的什么？ |
|------|------|------|-------------------|
| **S** | Spring | 容器 + 依赖注入 + 事务管理 | 替代手动 `new` 对象 + `MyBatisUtil` |
| **S** | Spring MVC | Web 层框架 | 替代所有 `Servlet` |
| **M** | MyBatis | 持久层框架 | 保持不变，但整合进 Spring 管理 |

### 2.2 核心变化示意

```
【改造前】
Servlet (@WebServlet)  →  手动 new Service  →  手动拿 Mapper  →  MyBatis → MySQL
  ↑ 自己管理对象生命周期           ↑ 每次调用 getSqlSession()

【改造后】
DispatcherServlet (Spring MVC 前端控制器)
       ↓
@Controller (@RequestMapping)  →  @Autowired 注入 Service  →  @Autowired 注入 Mapper  →  MyBatis → MySQL
       ↑ Spring IoC 容器统一管理所有对象，自动注入                                   ↑ SqlSession 由 Spring 管理
```

### 2.3 具体好处

```
改造前：                         改造后：
EmployeeServiceImpl empSvc       @Service  // Spring自动创建
    = new EmployeeServiceImpl(); public class EmployeeServiceImpl {
                                     @Autowired  // Spring自动注入
LoginServlet extends HttpServlet     private EmployeeMapper mapper;
    ↓ 每个Servlet都要写一遍          }
    User user = (User) session        ↓
        .getAttribute("loginUser");  @Controller
    if (user == null) { ... }       @RequestMapping("/emp")
                                    ↓ 用拦截器统一处理登录检查
                                    只需写业务逻辑
```

---

## 3. 改造后的架构一览

```
┌─────────────────────────────────────────────────────────────┐
│  web.xml (或 WebAppInitializer)                              │
│  配置 DispatcherServlet + Spring 监听器 + 编码过滤器          │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Spring MVC (spring-mvc.xml)                                │
│  • 扫描 @Controller                                         │
│  • 配置视图解析器（JSP路径前缀/后缀）                          │
│  • 配置拦截器（登录检查）                                     │
│  • 静态资源映射                                              │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  @Controller 层（替代 Servlet）                               │
│  • LoginController    — 登录/注册/退出                       │
│  • EmpController      — 员工 CRUD                           │
│  • DeptController     — 部门 CRUD                           │
│  • AttendanceController — 打卡/考勤查看                      │
│  • LeaveController    — 请假申请/审批                        │
└────────────────────────┬────────────────────────────────────┘
                         │ @Autowired
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  @Service 层（改动最小）                                      │
│  • EmployeeServiceImpl  — 加 @Service, @Autowired Mapper     │
│  • DepartmentServiceImpl                                    │
│  • AttendanceServiceImpl                                    │
│  • LeaveServiceImpl                                         │
│  • UserServiceImpl                                          │
└────────────────────────┬────────────────────────────────────┘
                         │ @Autowired
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Mapper 层（接口不变，配置方式变）                              │
│  • EmployeeMapper.java  + EmployeeMapper.xml                │
│  • 由 SqlSessionFactoryBean + MapperScannerConfigurer 管理   │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Spring 核心 (applicationContext.xml)                        │
│  • 数据源 (Druid/HikariCP 连接池)                            │
│  • SqlSessionFactoryBean（替代 MyBatisUtil）                 │
│  • MapperScannerConfigurer（自动扫描 Mapper 接口）            │
│  • 事务管理器 + 声明式事务 (@Transactional)                   │
└─────────────────────────────────────────────────────────────┘
```

---

## 4. 文件改动清单

### 4.1 不变的文件（只改包名或完全不改）

| 文件 | 改动 | 说明 |
|------|------|------|
| `entity/*.java` | ✅ 不变 | 实体类不受框架影响 |
| `mapper/*Mapper.xml` | ✅ 不变 | SQL 映射文件内容不动 |
| `mapper/*Mapper.java` | ✅ 接口不变 | 只需确保能被 Spring 扫描到 |
| `db.properties` | ✅ 基本不变 | 可能会增加连接池配置 |

### 4.2 需要修改的文件

| 文件 | 改动内容 |
|------|---------|
| `pom.xml` | ⭐ 大量新增依赖（Spring 全家桶）|
| `service/impl/*ServiceImpl.java` | 加 `@Service`、`@Autowired`，删掉 `MyBatisUtil.getSqlSession()` |
| `mybatis-config.xml` | 移除 `<environments>`（数据源交给 Spring）|
| 所有 JSP 文件 | 改进 UI，用 JSTL 替 Scriptlet，抽取公共布局 |

### 4.3 需要新增的文件

| 文件 | 作用 |
|------|------|
| `web.xml` ⭐ | 配置 DispatcherServlet、Spring 监听器、编码过滤器 |
| `applicationContext.xml` ⭐ | Spring 核心配置（数据源、事务、MyBatis 整合）|
| `spring-mvc.xml` ⭐ | Spring MVC 配置（视图解析、拦截器、静态资源）|
| `controller/LoginController.java` | 登录/注册/退出（替代 3 个 Servlet）|
| `controller/EmpController.java` | 员工 CRUD |
| `controller/DeptController.java` | 部门 CRUD |
| `controller/AttendanceController.java` | 打卡/考勤 |
| `controller/LeaveController.java` | 请假/审批 |
| `interceptor/LoginInterceptor.java` | 登录拦截器（统一的登录检查）|
| `css/common.css` | 公共样式文件 |
| `WEB-INF/common/header.jsp` | 公共头部（导航栏）|
| `WEB-INF/common/footer.jsp` | 公共底部 |

### 4.4 需要删除的文件

| 文件 | 原因 |
|------|------|
| `servlet/*.java`（全部 7 个）| 被 Spring MVC Controller 替代 |
| `utils/MyBatisUtil.java` | 被 Spring 的 SqlSessionFactoryBean 替代 |
| `filter/CharacterEncodingFilter.java` | 用 Spring 自带的 `CharacterEncodingFilter` |
| `dao/*.java`（全部 5 个）| 原本就没用到，且功能与 mapper 重复 |

---

## 5. pom.xml 依赖变化

### 5.1 需要新增的依赖

```xml
<!-- ⭐ Spring 核心（IoC 容器） -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>6.1.6</version>
</dependency>

<!-- ⭐ Spring MVC（Web 层框架） -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>6.1.6</version>
</dependency>

<!-- ⭐ Spring JDBC（事务管理） -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>6.1.6</version>
</dependency>

<!-- ⭐ MyBatis-Spring 整合包（核心！连接 Spring 和 MyBatis） -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>3.0.4</version>
</dependency>

<!-- ⭐ 数据库连接池（Druid，比 POOLED 更好） -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.23</version>
</dependency>

<!-- JSTL（JSP 标准标签库，替代 Scriptlet） -->
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>3.0.1</version>
</dependency>
```

> 📦 原来的 `mybatis`、`mysql-connector-j`、`jakarta.servlet-api`、`jsp-api` 继续保留。

---

## 6. Spring 配置文件（新增）

这是改造最核心的部分，三个 XML 配置文件是 SSM 的骨架。

### 6.1 web.xml — 入口配置

```xml
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" version="6.0">

    <!-- ① Spring 核心容器启动 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>

    <!-- ② 编码过滤器（用 Spring 自带的，替代手写的） -->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- ③ Spring MVC 前端控制器（替代所有 Servlet） -->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>  <!-- 拦截所有请求 -->
    </servlet-mapping>
</web-app>
```

### 6.2 applicationContext.xml — Spring 核心配置

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       ...>

    <!-- ① 加载数据库配置 -->
    <context:property-placeholder location="classpath:db.properties"/>

    <!-- ② 数据源（用 Druid 连接池，替代 MyBatis 自带的 POOLED） -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!-- ③ SqlSessionFactoryBean（替代 MyBatisUtil 工具类） -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!-- ④ Mapper 扫描器（自动创建 Mapper 接口的实现类） -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="mapper"/>    <!-- 扫描 mapper 包 -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>

    <!-- ⑤ 事务管理器 -->
    <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- ⑥ 开启注解式事务（@Transactional） -->
    <tx:annotation-driven transaction-manager="txManager"/>

    <!-- ⑦ 扫描 Service 和拦截器等组件 -->
    <context:component-scan base-package="service,interceptor"/>
</beans>
```

### 6.3 spring-mvc.xml — Spring MVC 配置

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       ...>

    <!-- ① 扫描 Controller -->
    <context:component-scan base-package="controller"/>

    <!-- ② 开启 MVC 注解驱动 -->
    <mvc:annotation-driven/>

    <!-- ③ 静态资源放行（css/js/图片不走 Controller） -->
    <mvc:resources mapping="/static/**" location="/static/"/>

    <!-- ④ 视图解析器（JSP 路径简化） -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/"/>   <!-- JSP 放在 WEB-INF 下更安全 -->
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- ⑤ 登录拦截器 -->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/login"/>       <!-- 登录页不拦截 -->
            <mvc:exclude-mapping path="/register"/>    <!-- 注册页不拦截 -->
            <mvc:exclude-mapping path="/static/**"/>   <!-- 静态资源不拦截 -->
            <bean class="interceptor.LoginInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
</beans>
```

---

## 7. Controller 层：Servlet → Spring MVC

这是改动的 **核心部分**。原来的每个 Servlet 变成一个被 `@Controller` 标注的类，
每个 `doGet/doPost` 中的分支变成一个被 `@RequestMapping` 标注的方法。

### 7.1 改造对照（以登录为例）

**改造前 — LoginServlet.java（~65 行）：**
```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();  // 手动 new

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User loginUser = userService.login(username, password, role);

        if (loginUser == null) {
            request.setAttribute("msg", "账号或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);
            if ("admin".equals(loginUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/employee/index.jsp");
            }
        }
    }
}
```

**改造后 — LoginController.java（~45 行）：**
```java
@Controller
public class LoginController {

    @Autowired  // ⭐ Spring 自动注入，不需要 new
    private UserService userService;

    // ⭐ 用方法注解替代 action 参数
    @PostMapping("/login")
    public String login(String username,    // ⭐ 参数名直接对应表单 name
                        String password,
                        String role,
                        HttpSession session,
                        Model model) {      // ⭐ Model 替代 request.setAttribute

        User loginUser = userService.login(username, password, role);

        if (loginUser == null) {
            model.addAttribute("msg", "账号或密码错误！");
            return "login";  // ⭐ 返回字符串 = 视图名，视图解析器拼成 /WEB-INF/login.jsp
        }

        session.setAttribute("loginUser", loginUser);

        if ("admin".equals(loginUser.getRole())) {
            return "redirect:/admin/index";   // ⭐ redirect: 前缀 = 重定向
        } else {
            return "redirect:/employee/index";
        }
    }

    @GetMapping("/login")   // GET 请求返回登录页
    public String loginPage() {
        return "login";
    }
}
```

### 7.2 所有 Controller 的映射设计

| 原 Servlet | 新 Controller | URL 映射 | 方法说明 |
|------------|--------------|----------|---------|
| `LoginServlet` | `LoginController` | `/login` POST | 登录验证 |
| — | — | `/login` GET | 登录页面 |
| `RegisterServlet` | — | `/register` POST | 注册 |
| — | — | `/register` GET | 注册页面 |
| `LogoutServlet` | — | `/logout` GET | 退出 |
| `EmpServlet` | `EmpController` | `/emp/list` GET | 员工列表 |
| — | — | `/emp/add` GET/POST | 新增员工 |
| — | — | `/emp/edit/{id}` GET/POST | 编辑员工（新功能）|
| — | — | `/emp/delete/{id}` GET | 删除员工 |
| `DeptServlet` | `DeptController` | `/dept/list` GET | 部门列表 |
| — | — | `/dept/add` GET/POST | 新增部门 |
| — | — | `/dept/delete/{id}` GET | 删除部门 |
| `AttendanceServlet` | `AttendanceController` | `/attendance/checkIn` POST | 上班打卡 |
| — | — | `/attendance/checkOut` POST | 下班签退 |
| — | — | `/attendance/my` GET | 我的考勤 |
| — | — | `/attendance/all` GET | 全员考勤（管理员）|
| `LeaveServlet` | `LeaveController` | `/leave/apply` POST | 提交请假 |
| — | — | `/leave/my` GET | 我的请假 |
| — | — | `/leave/all` GET | 所有请假（管理员）|
| — | — | `/leave/approve/{id}` POST | 审批通过 |
| — | — | `/leave/reject/{id}` POST | 审批拒绝 |

> 💡 **RESTful 风格**：URL 用名词表示资源，HTTP 方法表示操作（GET=查，POST=增/改）。对比原来的 `?action=list` 更加清晰规范。

---

## 8. Service 层：注入改造

Service 层的改动很小，只用加注解。

**改造前：**
```java
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper =
        MyBatisUtil.getSqlSession().getMapper(EmployeeMapper.class);  // 手动获取
    ...
}
```

**改造后：**
```java
@Service  // ⭐ 告诉 Spring："我是一个 Service，请管理我"
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired  // ⭐ 告诉 Spring："帮我注入一个 EmployeeMapper 进来"
    private EmployeeMapper employeeMapper;
    ...
}
```

> 就这么简单！加两个注解，删掉 MyBatisUtil 的调用。

---

## 9. Mapper 层：整合 Spring

### 9.1 mybatis-config.xml 的变化

**改造前**（包含数据源和事务配置）：
```xml
<configuration>
    <properties resource="db.properties"/>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <environments default="development">          <!-- ← 这部分删掉 -->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">...</dataSource>
        </environment>
    </environments>
    <mappers>...</mappers>                         <!-- ← 这部分也交给 Spring -->
</configuration>
```

**改造后**（只保留 settings 和类型别名等）：
```xml
<configuration>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <!-- 可选：开启二级缓存 -->
        <setting name="cacheEnabled" value="true"/>
        <!-- 可选：懒加载 -->
        <setting name="lazyLoadingEnabled" value="true"/>
    </settings>
</configuration>
```

> 💡 数据源、事务、Mapper 注册全部交给 Spring 的 `applicationContext.xml` 管理。

### 9.2 事务控制（新增）

在 Service 方法上加 `@Transactional`，自动管理事务：

```java
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    @Transactional  // ⭐ 这个方法自动在事务中执行，出错自动回滚
    public boolean approve(Integer id) {
        return leaveMapper.updateStatus(id, "已通过");
    }
}
```

> 💡 以前用 `MyBatisUtil` 手动 `openSession(true)`，事务管理很粗糙。现在 Spring 帮你自动管理。

---

## 10. JSP 前端优化方案

### 10.1 前端改造总览

```
改造前：                         改造后：
├── login.jsp (内联CSS)          ├── static/css/common.css     ← 公共样式
├── register.jsp (内联CSS)       ├── static/css/login.css      ← 登录页样式
├── admin/                       ├── WEB-INF/common/
│   ├── index.jsp (内联CSS)      │   ├── header.jsp           ← 公共导航栏
│   ├── emp/list.jsp (Scriptlet) │   └── sidebar.jsp           ← 公共侧边栏
│   └── ...                      └── WEB-INF/views/
└── employee/                        ├── login.jsp             ← JSTL + EL
    ├── index.jsp (内联CSS)          ├── register.jsp
    └── ...                          ├── admin/
                                     │   ├── emp/list.jsp      ← JSTL <c:forEach>
                                     │   └── ...
                                     └── employee/
                                         └── ...
```

### 10.2 具体优化点

#### ① 抽取公共布局

**新增 `WEB-INF/common/header.jsp`：**
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} - 考勤管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
</head>
<body>
<div class="wrapper">
    <div class="topbar">
        <span class="logo">考勤管理系统</span>
        <span class="user-info">
            ${loginUser.username}（${loginUser.role == 'admin' ? '管理员' : '员工'}）
            <a href="${pageContext.request.contextPath}/logout">退出</a>
        </span>
    </div>
    <div class="main">
        <c:if test="${loginUser.role == 'admin'}">
            <div class="sidebar">
                <a href="${pageContext.request.contextPath}/admin/index">首页</a>
                <a href="${pageContext.request.contextPath}/dept/list">部门管理</a>
                <a href="${pageContext.request.contextPath}/emp/list">员工管理</a>
                <a href="${pageContext.request.contextPath}/attendance/all">考勤记录</a>
                <a href="${pageContext.request.contextPath}/leave/all">请假审批</a>
            </div>
        </c:if>
        <div class="content">
```

**新增 `WEB-INF/common/footer.jsp`：**
```jsp
        </div><!-- .content -->
    </div><!-- .main -->
</div><!-- .wrapper -->
</body>
</html>
```

**使用方式（例如员工列表页）：**
```jsp
<%@ include file="/WEB-INF/common/header.jsp" %>

<h2>员工列表</h2>
<table>
    <tr><th>姓名</th><th>性别</th><th>部门</th><th>操作</th></tr>
    <c:forEach items="${empList}" var="emp">
        <tr>
            <td>${emp.empName}</td>
            <td>${emp.gender}</td>
            <td>${emp.deptId}</td>
            <td>
                <a href="${pageContext.request.contextPath}/emp/edit/${emp.id}">编辑</a>
                <a href="${pageContext.request.contextPath}/emp/delete/${emp.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/WEB-INF/common/footer.jsp" %>
```

#### ② Scriptlet → JSTL + EL

| 改造前（Scriptlet） | 改造后（JSTL + EL） |
|---------------------|---------------------|
| `<% List<Employee> list = (List<Employee>)request.getAttribute("empList"); %>` | 不用写了 |
| `<% if(list != null){ for(Employee e : list){ %>` | `<c:forEach items="${empList}" var="e">` |
| `<%= e.getEmpName() %>` | `${e.empName}` |
| `<% if(request.getAttribute("msg") != null){ %>` | `<c:if test="${not empty msg}">` |

#### ③ 新增外部 CSS 文件

**`static/css/common.css`** — 全局统一的布局、导航栏、表格、按钮样式：

```css
/* ========== 全局变量 ========== */
:root {
    --primary: #3498db;
    --success: #27ae60;
    --danger: #e74c3c;
    --bg: #f5f7fa;
    --card-bg: #fff;
    --text: #333;
    --border: #eee;
}

/* ========== 布局 ========== */
* { margin:0; padding:0; box-sizing:border-box; }
body { font-family:"Microsoft Yahei", sans-serif; background:var(--bg); color:var(--text); }

.topbar {
    display:flex; justify-content:space-between; align-items:center;
    background:#2c3e50; color:#fff; padding:0 2rem; height:56px;
}
.topbar .logo { font-size:18px; font-weight:bold; }
.topbar .user-info a { color:#ecf0f1; margin-left:1rem; }

.main { display:flex; min-height:calc(100vh - 56px); }

.sidebar {
    width:200px; background:#34495e; padding-top:1rem;
}
.sidebar a {
    display:block; color:#ecf0f1; padding:12px 24px; text-decoration:none;
}
.sidebar a:hover { background:#2c3e50; }

.content { flex:1; padding:2rem; }

/* ========== 表格 ========== */
table { width:100%; border-collapse:collapse; background:var(--card-bg);
        border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.06); }
th, td { padding:12px 16px; text-align:center; border-bottom:1px solid var(--border); }
th { background:#f8f9fa; font-weight:600; }
tr:hover { background:#f0f4ff; }

/* ========== 按钮 ========== */
.btn {
    display:inline-block; padding:8px 20px; border:none; border-radius:6px;
    color:#fff; cursor:pointer; text-decoration:none; font-size:14px;
}
.btn-primary { background:var(--primary); }
.btn-success { background:var(--success); }
.btn-danger { background:var(--danger); }
```

#### ④ 新增前端校验

```jsp
<!-- 在表单提交前用 JS 检查 -->
<script>
function validateForm() {
    var username = document.getElementById("username").value;
    if (username.trim() === "") {
        alert("用户名不能为空！");
        return false;  // 阻止表单提交
    }
    return true;
}
</script>
```

#### ⑤ 增加数据可视化（可选加分项）

- 管理员首页加一个考勤统计卡片（今日打卡人数、迟到人数）
- 用简单的 HTML/CSS 做统计面板

---

## 11. 删除的文件清单

```
删除的文件（7个Servlet + 5个Dao + 2个工具类）：

src/main/java/
├── servlet/
│   ├── LoginServlet.java          ✗ 被 LoginController 替代
│   ├── RegisterServlet.java       ✗ 被 LoginController 替代
│   ├── LogoutServlet.java         ✗ 被 LoginController 替代
│   ├── EmpServlet.java            ✗ 被 EmpController 替代
│   ├── DeptServlet.java           ✗ 被 DeptController 替代
│   ├── AttendanceServlet.java     ✗ 被 AttendanceController 替代
│   ├── LeaveServlet.java          ✗ 被 LeaveController 替代
│   └── MyAttendanceServlet.java   ✗ 空类，没用到
├── dao/                           ✗ 整个目录删除，功能与 mapper 重复
│   ├── EmployeeDao.java
│   ├── DepartmentDao.java
│   ├── AttendanceDao.java
│   ├── LeaveDao.java
│   └── UserDao.java
├── filter/
│   └── CharacterEncodingFilter.java  ✗ 用 Spring 自带的
└── utils/
    └── MyBatisUtil.java              ✗ 被 SqlSessionFactoryBean 替代
```

---

## 12. 分步实施计划

建议按以下 6 个步骤实施，每步做完测试再继续：

```
第 1 步：环境搭建（预计 30 分钟）
├── 修改 pom.xml，添加 Spring 全家桶依赖
├── 新建 web.xml
├── 新建 applicationContext.xml（数据源 + MyBatis 整合）
├── 新建 spring-mvc.xml（MVC 配置）
└── 验证：启动 Tomcat，无报错

第 2 步：改造 Mapper + Service 层（预计 20 分钟）
├── 精简 mybatis-config.xml（删掉数据源配置）
├── 给所有 ServiceImpl 加 @Service @Autowired
├── 删掉 utils/MyBatisUtil.java
└── 验证：写个测试，确认数据库能正常访问

第 3 步：创建 Controller（预计 60 分钟）
├── 新建 LoginController（登录/注册/退出）
├── 新建 EmpController（员工 CRUD）
├── 新建 DeptController（部门 CRUD）
├── 新建 AttendanceController（打卡/考勤）
├── 新建 LeaveController（请假/审批）
├── 新建 LoginInterceptor（登录拦截）
├── 删掉 servlet/ 目录
└── 验证：每个 URL 都能正常访问

第 4 步：改造 JSP 前端（预计 90 分钟）
├── 创建 static/css/common.css
├── 创建 WEB-INF/common/header.jsp + footer.jsp
├── 改造登录页、注册页
├── 改造管理员页面（首页、员工列表、部门列表、考勤、请假审批）
├── 改造员工页面（首页、打卡、考勤、请假）
├── 用 JSTL + EL 替换所有 Scriptlet
└── 验证：所有页面显示正常，无 404

第 5 步：修 Bug + 补充功能（预计 30 分钟）
├── 修复 JSP ↔ Controller 变量名不一致
├── 新增员工编辑功能
├── 新增部门编辑功能
└── 验证：全功能回归测试

第 6 步：优化打磨（预计 30 分钟，可选）
├── 加前端校验（JS 表单验证）
├── 加管理员首页统计面板
├── 加确认删除弹窗
└── 手机端响应式适配
```

---

## 13. 改造前后对比速查表

| 维度 | 改造前 | 改造后 |
|------|--------|--------|
| **Web 层** | 7 个 Servlet，靠 `?action=` 区分 | 5 个 Controller，RESTful URL |
| **对象管理** | 手动 `new`，到处调用 `MyBatisUtil` | Spring IoC 容器，`@Autowired` 自动注入 |
| **事务管理** | `openSession(true)` 自动提交 | `@Transactional` 声明式事务 |
| **数据库连接** | MyBatis 自带 POOLED | Druid 连接池（更稳定） |
| **编码处理** | 手写 Filter | Spring `CharacterEncodingFilter` |
| **登录检查** | 每个 Servlet 里写一遍 | 拦截器统一处理 |
| **异常处理** | 堆栈打印 | Spring 全局异常处理器 |
| **CSS** | 每页内联 `<style>` | 1 个公共 CSS + 按需补充 |
| **JSP 写法** | Java Scriptlet `<% for %>` | JSTL `<c:forEach>` + EL `${}` |
| **页面布局** | 每页独立写 | 公共 head.jsp + foot.jsp |
| **URL 风格** | `/leave?action=listAll` | `/leave/all` |
| **代码量** | Servlet 层约 350 行 | Controller 层约 300 行（但功能更全） |
| **可扩展性** | 差（加功能要改多处） | 好（分层清晰，改一处即可） |

---

## ⚠️ 改造注意事项

1. **不要一步到位**：先搭好骨架（step 1-2），确认能跑通，再继续。
2. **保留原文件**：改造前先备份或用 Git，出问题了可以回退。
3. **JSP 路径变化**：改造后 JSP 移到 `WEB-INF/views/` 下，浏览器无法直接访问（更安全），必须通过 Controller 跳转。
4. **web.xml 版本**：注意使用 Jakarta EE 的命名空间（`jakarta.ee`），不要用旧版 `java.sun.com`。
5. **包扫描路径**：`context:component-scan` 的 `base-package` 要写对，不然 `@Autowired` 会失败。
6. **MyBatis 版本**：当前使用 MyBatis 3.5.15，配合 `mybatis-spring` 3.0.x 版本没问题。
7. **Tomcat 版本**：由于使用 Jakarta Servlet 6.1，需要用 Tomcat 10.1+ 版本。

---

> 📅 文档生成日期：2026-06-25
>
> ✅ 改造完成日期：2026-06-25
>
> 🎯 改造目标：原生 Servlet → SSM + JSP
>
> 🛠 目标技术栈：Spring 6 + Spring MVC 6 + MyBatis 3.5 + MySQL + JSP + Maven

---

## 附录：改造完成总结

### 改造前后文件对比

| | 改造前 | 改造后 |
|------|--------|--------|
| Java 源文件 | 35 个 | 26 个 |
| Servlet | 8 个 | **0 个**（被 Controller 替代）|
| Controller | 0 个 | **5 个** |
| 配置文件 | 3 个 | **6 个**（新增 web.xml, applicationContext.xml, spring-mvc.xml）|
| JSP 页面 | 19 个 | 18 个（删除了 logout.jsp）|
| CSS 文件 | 0 个 | **1 个**（common.css）|
| 公共布局 | 0 个 | **2 个**（header.jsp, footer.jsp）|

### 已修复的 BUG

| 问题 | 修复方式 |
|------|---------|
| register.jsp → `/registerServlet`（路径不存在） | 改为 `/register`，匹配 Controller |
| employee/myLeave.jsp → import `entity.Leave`（类不存在） | 用 JSTL 替代 Scriptlet，无需 import |
| employee/myLeave.jsp → `myLeaveList`（属性名不对） | 改为 `leaveList`，匹配 Controller |
| employee/myAttendance.jsp → `myAttList`（属性名不对） | 改为 `attendanceList`，匹配 Controller |
| admin/leaveAudit.jsp → import `entity.Leave`（类不存在） | 用 JSTL 替代 Scriptlet |
| admin/leaveAudit.jsp → `leaveAllList`（属性名不对） | 改为 `leaveList`，匹配 Controller |
| admin/leaveAudit.jsp → `getStartDate()/getEndDate()/getStatus()`（方法不存在） | 改为 EL 表达式 `leave.startTime/endTime/approveStatus` |
| admin/attendance/allAttendance.jsp → `allAttList`（属性名不对） | 改为 `attList`，匹配 Controller |

### 启动前检查清单

- [ ] 确认 `db.properties` 存在且配置正确（已被 .gitignore 排除，需手动创建）
- [ ] 确认 MySQL 数据库 `employee_attendance` 已创建并导入建表 SQL
- [ ] 确认使用 **Tomcat 10.1+**（需要 Jakarta EE 支持）
- [ ] 确认 Maven 依赖已下载（`mvn clean compile`）
- [ ] 启动 Tomcat，访问 `http://localhost:8080/zuoye1/login.jsp`
- [ ] 测试账号：`admin / 123456`（管理员）或 `emp001 / 123456`（员工）
