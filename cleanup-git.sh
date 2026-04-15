#!/bin/bash
# ======================
# hyplat Git 清理脚本
# ======================
# 用途：从 Git 历史中删除已提交的不必要文件
# 注意：文件会从 Git 仓库移除，但本地保留（--cached）
# ======================

set -e

echo "🔍 开始清理 Git 仓库中的不必要文件..."
echo ""

# 记录当前分支
CURRENT_BRANCH=$(git branch --show-current)
echo "📌 当前分支：$CURRENT_BRANCH"

# 创建备份分支（安全起见）
echo "📦 创建备份分支：backup-before-cleanup"
git branch -f backup-before-cleanup HEAD

echo ""
echo "🗑️  从 Git 缓存中移除以下文件/目录..."

# IDE 配置
echo "  - IntelliJ IDEA 配置 (.idea/, *.iml)"
git rm -r --cached .idea 2>/dev/null || true
git rm --cached *.iml 2>/dev/null || true
git rm --cached *.ipr 2>/dev/null || true
git rm --cached *.iws 2>/dev/null || true

echo "  - Eclipse 配置 (.settings/, .project, .classpath)"
git rm -r --cached .settings 2>/dev/null || true
git rm --cached .project 2>/dev/null || true
git rm --cached .classpath 2>/dev/null || true
git rm --cached .factorypath 2>/dev/null || true

# Hibernate 元数据
echo "  - Hibernate 元数据 (.myhibernatedata, .mymetadata, .myumldata)"
git rm --cached .myhibernatedata 2>/dev/null || true
git rm --cached .mymetadata 2>/dev/null || true
git rm --cached .myumldata 2>/dev/null || true

# 构建产物
echo "  - 构建产物 (build/)"
git rm -r --cached build 2>/dev/null || true
git rm -r --cached build/artifacts 2>/dev/null || true
git rm -r --cached target 2>/dev/null || true
git rm -r --cached target/artifacts 2>/dev/null || true

# JAR 包（如果有直接提交的）
echo "  - 直接提交的 JAR 包"
git rm --cached *.jar 2>/dev/null || true

# 临时文件
echo "  - 临时文件 (temp/, *.tmp)"
git rm -r --cached temp 2>/dev/null || true
git rm --cached *.tmp 2>/dev/null || true
git rm --cached *.swp 2>/dev/null || true
git rm --cached *~ 2>/dev/null || true

echo ""
echo "✅ 清理完成！"
echo ""
echo "📝 下一步操作："
echo "  1. 查看变更：git status"
echo "  2. 提交清理：git commit -m 'chore: 清理不必要的提交文件，添加 .gitignore'"
echo "  3. 推送到远程：git push"
echo ""
echo "⚠️  注意："
echo "  - 文件已从 Git 缓存移除，但本地文件保留（不会被删除）"
echo "  - 推送后，远程仓库将不再包含这些文件"
echo "  - 其他人 pull 后，这些文件会从他们的工作区消失（但 IDE 会重新生成）"
echo ""
echo "📋 如需恢复（在推送前）："
echo "  git reset HEAD~1"
echo "  或从备份分支恢复：git checkout backup-before-cleanup"
echo ""
