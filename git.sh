#!/bin/bash
echo "=== 项目构建脚本 ==="

# 1. 代码添加到暂存区
echo "🔄 代码添加到暂存区..."
git add .

# 2. 提交到本地仓库
echo "📦 正在提交本地仓库..."
read -p "请输入提交备注：" msg
git commit -m $msg
echo "提交成功"

# 3. 提交到远程仓库
echo "正在提交远程仓库..."
git push
echo "提交成功"