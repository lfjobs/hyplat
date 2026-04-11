#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
批量修复 CarManageAction.java 的日志问题
"""

import re

file_path = '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/action/CarManageAction.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# 修复模式 1: System.out.println(数字); → logger.info("调试信息：{}", 数字);
content = re.sub(r'System\.out\.println\((\d+)\);', r'logger.info("调试信息：{}");', content)

# 修复模式 2: System.out.println("字符串"); → logger.info("字符串");
content = re.sub(r'System\.out\.println\("([^"]+)"\);', r'logger.info("\1");', content)

# 修复模式 3: System.out.println("字符串" + 变量); → logger.info("字符串：{}", 变量);
content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+)\);', r'logger.info("\1: {}", \2);', content)

# 修复模式 4: System.out.println("字符串" + 变量 + "字符串"); → logger.info("字符串：{}字符串", 变量);
content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+) \+ "([^"]+)"\);', r'logger.info("\1{}{}", \2, "\3");', content)

# 修复模式 5: e.printStackTrace(); → logger.error("异常", e);
content = re.sub(r'e\.printStackTrace\(\);', r'logger.error("操作异常", e);', content)

# 修复模式 6: System.out.println(变量); → logger.info("变量值：{}", 变量);
content = re.sub(r'System\.out\.println\((\w+)\);', r'logger.info("值：{}", \1);', content)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("CarManageAction.java 修复完成")
