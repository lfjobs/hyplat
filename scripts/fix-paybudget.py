#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
修复 PayBudgetSheetAction.java 的日志问题
1. 添加 Logger 导入和声明
2. 替换所有 System.out.println 和 printStackTrace
"""

file_path = '/home/admin/openclaw/workspace/hyplat/src/mobile/tiantai/android/action/storeProduction/budgetSheet/PayBudgetSheetAction.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# 步骤 1: 添加 Logger 导入（在 package 之后）
if 'import org.slf4j.Logger;' not in content:
    content = content.replace(
        'package mobile.tiantai.android.action.storeProduction.budgetSheet;',
        '''package mobile.tiantai.android.action.storeProduction.budgetSheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;'''
    )

# 步骤 2: 添加 Logger 声明（在类开头）
# 找到类定义
import re

# 添加 logger 实例
if 'private static final Logger logger' not in content:
    content = re.sub(
        r'(public class PayBudgetSheetAction \{)',
        r'\1\n\tprivate static final Logger logger = LoggerFactory.getLogger(PayBudgetSheetAction.class);',
        content
    )

# 步骤 3: 替换 System.out.println
content = re.sub(r'System\.out\.println\("([^"]+)"\);', r'logger.info("\1");', content)
content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+)\);', r'logger.info("\1: {}", \2);', content)
content = re.sub(r'e\.printStackTrace\(\);', r'logger.error("操作异常", e);', content)
content = re.sub(r'System\.out\.println\((\w+)\);', r'logger.info("值：{}", \1);', content)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("PayBudgetSheetAction.java 修复完成")
