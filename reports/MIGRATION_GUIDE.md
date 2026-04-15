# Hyplat 项目 Maven 迁移与日志规范化指南

**版本:** 1.0  
**更新日期:** 2026-04-11  
**状态:** 可执行

---

## 📋 目录

1. [快速开始](#快速开始)
2. [Maven 迁移步骤](#maven-迁移步骤)
3. [日志规范化修复](#日志规范化修复)
4. [验证与测试](#验证与测试)
5. [常见问题](#常见问题)

---

## 🚀 快速开始

### 前置条件

- [ ] JDK 11+ 已安装
- [ ] Maven 3.8+ 已安装
- [ ] 项目代码已备份

### 检查环境

```bash
# 检查 Java 版本
java -version

# 检查 Maven 版本
mvn -version
```

### 预计耗时

| 任务 | 预计时间 |
|------|----------|
| Maven 环境准备 | 10 分钟 |
| pom.xml 配置 | 已完成 ✓ |
| 日志规范修复 | 2-4 小时 |
| 编译测试 | 30 分钟 |
| **总计** | **3-5 小时** |

---

## 📦 Maven 迁移步骤

### 步骤 1: 确认 pom.xml 已就绪

✅ 已完成：`pom.xml` 已创建在项目根目录

包含的依赖：
- SLF4J + Logback (日志)
- Struts 2.5.30
- Spring 5.3.27
- Hibernate 5.6.15
- MySQL Connector 8.0.33
- Druid 连接池

### 步骤 2: 创建标准目录结构

```bash
cd /E/my-first-project/hyplat

# 创建 Maven 标准目录
mkdir -p src/main/java
mkdir -p src/main/resources
mkdir -p src/test/java
mkdir -p src/test/resources

# 移动现有源码（如需要）
# 当前 pom.xml 配置 sourceDirectory=src，可直接使用现有结构
```

✅ 已完成：目录结构已创建

### 步骤 3: 移动配置文件

```bash
# 将配置文件移动到 resources 目录
cp src/*.properties src/main/resources/
cp src/*.xml src/main/resources/
cp src/logback.xml src/main/resources/  # 新的日志配置
```

### 步骤 4: 首次编译测试

```bash
# 清理并编译
mvn clean compile

# 如果成功，打包 WAR
mvn clean package
```

### 步骤 5: 处理编译错误

可能遇到的问题：

1. **缺少依赖** - 在 pom.xml 中添加对应依赖
2. **版本冲突** - 检查依赖树 `mvn dependency:tree`
3. **代码不兼容** - 调整代码适配新版本 API

---

## 📝 日志规范化修复

### 问题统计

根据扫描结果：

| 问题类型 | 数量 | 优先级 |
|----------|------|--------|
| `System.out.println` | 1,365 处 | 🔴 高 |
| `printStackTrace` | 1,549 处 | 🔴 高 |
| `System.err.println` | 20 处 | 🟡 中 |
| **总计** | **2,934 处** | |

### 修复工具

已创建以下工具：

1. **扫描脚本** - `scripts/scan-logging-issues.py`
   ```bash
   python3 scripts/scan-logging-issues.py
   ```

2. **Shell 修复脚本** - `scripts/fix-logging.sh`
   ```bash
   ./scripts/fix-logging.sh
   ```

3. **详细报告** - `logs_detailed_report_*.txt`

### 修复步骤

#### 第一步：添加 Logger 依赖

在需要修复的类中添加：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
    // ...
}
```

#### 第二步：替换 System.out.println

**模式 1: 简单字符串**
```java
// 原代码
System.out.println("用户登录成功");

// 修改为
logger.info("用户登录成功");
```

**模式 2: 字符串拼接**
```java
// 原代码
System.out.println("用户 ID: " + userId + ", 姓名：" + name);

// 修改为
logger.info("用户 ID: {}, 姓名：{}", userId, name);
```

**模式 3: 调试信息**
```java
// 原代码
System.out.println("DEBUG: " + data);

// 修改为
logger.debug("数据：{}", data);
```

#### 第三步：替换 printStackTrace

```java
// 原代码
catch (Exception e) {
    e.printStackTrace();
}

// 修改为
catch (Exception e) {
    logger.error("操作失败，userId={}", userId, e);
}
```

#### 第四步：使用 LoggerUtil 工具类（推荐）

项目已创建 `LoggerUtil` 工具类：

```java
import com.hyplat.common.logger.LoggerUtil;

// 用法
LoggerUtil.info(logger, "消息内容");
LoggerUtil.error(logger, "错误信息", e);
LoggerUtil.warn(logger, "警告信息");
LoggerUtil.debug(logger, "调试信息");
```

### 批量修复技巧

#### IntelliJ IDEA

1. **全局替换** (Ctrl+Shift+R)
   
   查找:
   ```regex
   System\.out\.println\("([^"]*)" \+ (\w+) \+ "([^"]*)"\)
   ```
   
   替换:
   ```
   logger.info("$1{}$3", $2)
   ```

2. **实时模板**
   
   创建模板 `soutl` → `logger.info("$END$", $PARAM$);`

#### Eclipse

1. **查找/替换** (Ctrl+H)
2. **使用正则表达式**
3. **逐个文件审查**

### 修复优先级

| 优先级 | 模块 | 文件数 | 建议 |
|--------|------|--------|------|
| P0 | 支付模块 (alipay, wechatpay) | ~50 | 立即修复 |
| P0 | 核心业务 (hy, com) | ~200 | 立即修复 |
| P1 | 业务模块 (mysl, office) | ~100 | 本周完成 |
| P2 | 工具类 (batch, util) | ~80 | 下周完成 |
| P3 | 测试代码 | ~50 | 可保留 |

---

## ✅ 验证与测试

### 编译验证

```bash
# 清理编译
mvn clean compile

# 运行测试
mvn test

# 打包
mvn clean package
```

### 日志验证

启动应用后检查：

```bash
# 查看日志文件
tail -f logs/hyplat-info.log
tail -f logs/hyplat-error.log

# 验证日志格式
grep "ERROR" logs/hyplat-error.log | head -10
```

### 功能验证

1. 登录功能测试
2. 支付流程测试
3. 核心业务流程测试
4. 异常场景测试

---

## ❓ 常见问题

### Q1: 编译报错「找不到符号 Logger」

**解决:** 确保 pom.xml 中包含 SLF4J 依赖：

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
```

### Q2: 运行时没有日志输出

**解决:** 检查 logback.xml 配置：
- 确认配置文件在 classpath 中
- 检查 root logger 级别设置
- 确认 appender 配置正确

### Q3: 日志文件没有生成

**解决:**
```bash
# 检查日志目录权限
ls -la logs/

# 创建目录并授权
mkdir -p logs
chmod 755 logs
```

### Q4: 替换后代码行为异常

**解决:**
1. 检查参数化日志格式是否正确
2. 确认日志级别设置合理
3. 使用 git diff 对比修改内容

---

## 📞 支持

遇到问题时：

1. 查看详细报告：`logs_detailed_report_*.txt`
2. 检查编译日志：`mvn clean compile -X`
3. 联系项目维护者

---

## 📚 参考文档

- [SLF4J 官方文档](http://www.slf4j.org/)
- [Logback 配置手册](http://logback.qos.ch/manual/)
- [Maven 最佳实践](https://maven.apache.org/guides/)

---

**下一步行动:**

- [ ] 运行 `mvn clean compile` 验证编译
- [ ] 选择 5-10 个核心类进行日志修复试点
- [ ] 验证修复后的日志输出
- [ ] 制定批量修复计划
