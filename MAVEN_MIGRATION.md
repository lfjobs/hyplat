# HyPlat 项目 Maven 迁移指南

## 📦 项目信息

- **项目名称**: hyplat
- **GroupId**: com.tiantai
- **Version**: 1.0-SNAPSHOT
- **Packaging**: war
- **JDK 版本**: 1.8

## 🏗️ 技术栈

| 框架 | 版本 | 说明 |
|------|------|------|
| Struts 2 | 2.5.16 | Web MVC 框架 |
| Spring | 3.1.0.RELEASE | IoC 容器 |
| Hibernate | 3.6.10.Final | ORM 框架 |
| Log4j2 | 2.11.1 | 日志框架 |
| FastJSON2 | 2.0.56 | JSON 处理 |
| POI | 3.9 | Excel 处理 |

## 📁 目录结构

```
hyplat/
├── pom.xml              # Maven 配置文件（新增）
├── build.xml            # Ant 配置文件（保留，可删除）
├── src/                 # Java 源代码
│   ├── com/            # 业务代码
│   ├── hy/             # 核心框架
│   └── *.xml           # 配置文件
├── WebRoot/            # Web 应用目录
│   ├── WEB-INF/
│   │   ├── lib/        # JAR 包（可删除）
│   │   └── web.xml
│   └── ...             # JSP、CSS、JS 等
└── hyplatlib/          # 额外 JAR 包（保留）
```

## 🚀 快速开始

### 1. 安装 Maven

```bash
# 检查 Maven 是否已安装
mvn -version

# 如果没有，下载安装：
# https://maven.apache.org/download.cgi
```

### 2. 首次构建

```bash
cd hyplat

# 清理并编译
mvn clean compile

# 打包 WAR
mvn clean package
```

### 3. 运行项目

编译后的 WAR 文件位于：
```
target/hyplat.war
```

部署到 Tomcat：
```bash
# 复制 WAR 到 Tomcat webapps
cp target/hyplat.war /path/to/tomcat/webapps/

# 重启 Tomcat
```

## 📋 常用 Maven 命令

```bash
# 编译
mvn clean compile

# 测试
mvn test

# 打包
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests

# 安装到本地仓库
mvn install

# 清理
mvn clean

# 查看依赖树
mvn dependency:tree

# 查看依赖冲突
mvn dependency:analyze

# 下载源码
mvn dependency:sources

# 下载文档
mvn dependency:resolve -Dclassifier=javadoc
```

## 🔧 迁移步骤

### 从 Ant 迁移到 Maven

1. **保留文件**
   - `pom.xml`（新增）
   - `src/`（源代码）
   - `WebRoot/`（Web 资源）

2. **可删除文件**
   - `build.xml`（Ant 配置）
   - `WebRoot/WEB-INF/lib/*.jar`（Maven 管理）
   - `hyplatlib/*.jar`（Maven 管理）

3. **IDE 配置**
   - IntelliJ: 右键 `pom.xml` → Add as Maven Project
   - Eclipse: 右键项目 → Configure → Convert to Maven Project

## ⚠️ 注意事项

### 1. JAR 包冲突

项目原有 265 个 JAR 包，可能存在版本冲突。Maven 会自动解决依赖冲突，但如果遇到问题：

```bash
# 查看依赖树
mvn dependency:tree

# 排除特定依赖
# 在 pom.xml 中使用 <exclusions>
```

### 2. 本地 JAR 包

部分第三方 SDK 可能不在 Maven 中央仓库，需要手动安装：

```bash
# 安装本地 JAR 到 Maven 仓库
mvn install:install-file \
  -Dfile=path/to/jar \
  -DgroupId=com.example \
  -DartifactId=example-sdk \
  -Dversion=1.0.0 \
  -Dpackaging=jar
```

### 3. 数据库驱动

Oracle JDBC 驱动需要手动安装：

```bash
mvn install:install-file \
  -Dfile=ojdbc14-10g.jar \
  -DgroupId=com.oracle \
  -DartifactId=ojdbc14 \
  -Dversion=10.2.0.4.0 \
  -Dpackaging=jar
```

### 4. 配置文件

确保以下配置文件正确：

- `src/hibernate.cfg.xml` - Hibernate 配置
- `src/struts.xml` - Struts 配置
- `WebRoot/WEB-INF/web.xml` - Web 配置
- `src/beans.xml` - Spring 配置

## 🐛 常见问题

### 问题 1: 编译错误 - 找不到包

**解决**: 确保所有依赖都已下载
```bash
mvn dependency:resolve
```

### 问题 2: WAR 包部署后无法访问

**解决**: 检查 `pom.xml` 中的 `warSourceDirectory`
```xml
<warSourceDirectory>WebRoot</warSourceDirectory>
```

### 问题 3: 类找不到 (ClassNotFoundException)

**解决**: 检查是否有本地 JAR 未添加到 Maven 仓库

### 问题 4: 依赖冲突

**解决**: 使用 `mvn dependency:tree` 查看冲突，使用 `<exclusions>` 排除

## 📝 下一步优化

- [ ] 迁移到 Spring Boot（现代化）
- [ ] 模块化重构（拆分子模块）
- [ ] 添加 CI/CD（GitHub Actions）
- [ ] 容器化（Docker）
- [ ] 升级框架版本（安全更新）

## 📞 支持

如有问题，请检查：
1. Maven 日志输出
2. `pom.xml` 依赖配置
3. 依赖树 `mvn dependency:tree`

---

**创建时间**: 2026-04-04
**作者**: yellow · 幽默轻松版
