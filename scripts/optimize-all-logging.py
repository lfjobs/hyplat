#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
批量优化所有文件的日志文案
"""

import re
import os

def optimize_file(file_path):
    """优化单个文件的日志文案"""
    if not os.path.exists(file_path):
        return 0
    
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_count = content.count('logger.info("')
    
    # 通用优化模式
    optimizations = [
        # 调用成功/失败 → 添加上下文
        (r'logger\.info\("调用成功"\);', r'logger.info("接口调用成功");'),
        (r'logger\.info\("调用失败"\);', r'logger.error("接口调用失败");'),
        
        # 简单的值输出 → 添加描述
        (r'logger\.info\("值：{}"\);', r'logger.debug("参数值：{}");'),
        
        # 删除明显的调试日志（改为 debug）
        (r'logger\.info\("调试信息：{}"\);', r'logger.debug("调试信息");'),
    ]
    
    for pattern, replacement in optimizations:
        content = re.sub(pattern, replacement, content)
    
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
    
    new_count = content.count('logger.info("')
    return original_count - new_count

# 优化核心文件
files_to_optimize = [
    '/home/admin/openclaw/workspace/hyplat/src/com/alipay/AlipayInterface.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/alipay/AlipayDemo.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/batch/chinapay/meth/BatchPay.java',
    '/home/admin/openclaw/workspace/hyplat/src/mobile/tiantai/android/action/storeProduction/budgetSheet/PayBudgetSheetAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/service/impl/CarManageServiceImpl.java',
]

print("开始批量优化日志文案...\n")
total_improved = 0

for file_path in files_to_optimize:
    improved = optimize_file(file_path)
    if improved > 0:
        total_improved += improved
        filename = os.path.basename(file_path)
        print(f"✅ {filename:50} 优化 {improved} 处")

print(f"\n{'='*60}")
print(f"优化完成！共改进 {total_improved} 处日志文案")
