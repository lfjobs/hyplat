#!/bin/bash

# ============================================
# JavaWeb 项目日志规范化修复脚本
# ============================================
# 功能：
# 1. 扫描所有 Java 文件中的 System.out.println 和 printStackTrace
# 2. 生成详细的问题报告
# 3. 提供自动替换建议
#
# 使用方法：
#   ./fix-logging.sh [项目路径]
# ============================================

PROJECT_ROOT="${1:-.}"
SRC_DIR="$PROJECT_ROOT/src"
REPORT_FILE="$PROJECT_ROOT/logs_fix_report_$(date +%Y%m%d_%H%M%S).txt"

echo "=========================================="
echo "JavaWeb 项目日志规范化扫描"
echo "=========================================="
echo "项目路径：$PROJECT_ROOT"
echo "源码目录：$SRC_DIR"
echo "报告文件：$REPORT_FILE"
echo ""

# 初始化报告
cat > "$REPORT_FILE" << EOF
========================================
JavaWeb 项目日志规范化修复报告
生成时间：$(date '+%Y-%m-%d %H:%M:%S')
项目路径：$PROJECT_ROOT
========================================

EOF

# 统计文件数量
TOTAL_FILES=$(find "$SRC_DIR" -name "*.java" | wc -l)
PROBLEM_FILES=$(find "$SRC_DIR" -name "*.java" -exec grep -l "System.out.println\|printStackTrace" {} + 2>/dev/null | wc -l)

echo "总 Java 文件数：$TOTAL_FILES"
echo "存在日志问题的文件数：$PROBLEM_FILES"
echo ""

echo "总 Java 文件数：$TOTAL_FILES" >> "$REPORT_FILE"
echo "存在日志问题的文件数：$PROBLEM_FILES" >> "$REPORT_FILE"
echo "" >> "$REPORT_FILE"

# 按问题类型分类
SYSOUT_COUNT=0
PRINTSTACK_COUNT=0

echo "========================================" >> "$REPORT_FILE"
echo "第一部分：System.out.println 问题" >> "$REPORT_FILE"
echo "========================================" >> "$REPORT_FILE"
echo "" >> "$REPORT_FILE"

# 扫描 System.out.println
echo "【1】System.out.println 问题列表："
echo "【1】System.out.println 问题列表：" >> "$REPORT_FILE"

find "$SRC_DIR" -name "*.java" -exec grep -n "System.out.println" {} + 2>/dev/null | while IFS=: read -r file line content; do
    if [ -n "$file" ]; then
        echo "  $file:$line"
        echo "  $file:$line" >> "$REPORT_FILE"
        SYSOUT_COUNT=$((SYSOUT_COUNT + 1))
    fi
done

echo "" >> "$REPORT_FILE"
echo ""

echo "========================================" >> "$REPORT_FILE"
echo "第二部分：printStackTrace 问题" >> "$REPORT_FILE"
echo "========================================" >> "$REPORT_FILE"
echo "" >> "$REPORT_FILE"

# 扫描 printStackTrace
echo "【2】printStackTrace 问题列表："
echo "【2】printStackTrace 问题列表：" >> "$REPORT_FILE"

find "$SRC_DIR" -name "*.java" -exec grep -n "printStackTrace" {} + 2>/dev/null | while IFS=: read -r file line content; do
    if [ -n "$file" ]; then
        echo "  $file:$line"
        echo "  $file:$line" >> "$REPORT_FILE"
        PRINTSTACK_COUNT=$((PRINTSTACK_COUNT + 1))
    fi
done

echo "" >> "$REPORT_FILE"
echo ""

# 生成修复建议
echo "========================================" >> "$REPORT_FILE"
echo "第三部分：修复建议" >> "$REPORT_FILE"
echo "========================================" >> "$REPORT_FILE"
echo "" >> "$REPORT_FILE"

cat >> "$REPORT_FILE" << 'EOF'
【修复方案】

1. 添加 Logger 声明（在类开头）：
   原代码：
   public class MyClass {
   
   修改为：
   public class MyClass {
       private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

2. 替换 System.out.println：
   原代码：
   System.out.println("user id: " + userId);
   
   修改为：
   logger.info("user id: {}", userId);

3. 替换 printStackTrace：
   原代码：
   catch (Exception e) {
       e.printStackTrace();
   }
   
   修改为：
   catch (Exception e) {
       logger.error("操作失败", e);
   }

4. 使用 LoggerUtil 工具类（推荐）：
   原代码：
   System.out.println("message");
   
   修改为：
   LoggerUtil.info(logger, "message");
   或
   LoggerUtil.error(logger, "error message", e);

【批量替换命令（IDEA）】

1. 查找：System\.out\.println\("([^"]*)" \+ (\w+) \+ "([^"]*)"\)
   替换：logger.info("$1{}$3", $2)

2. 查找：\.printStackTrace\(\)
   替换：logger.error("异常", e)

【注意事项】

1. 先备份代码再执行替换
2. 优先替换业务逻辑代码
3. 测试代码可以保留 System.out.println
4. 调试代码建议改为 DEBUG 级别
5. 确保已添加 SLF4J 和 Logback 依赖

EOF

echo ""
echo "=========================================="
echo "扫描完成！"
echo "报告已保存到：$REPORT_FILE"
echo "=========================================="
