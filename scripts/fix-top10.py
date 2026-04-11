#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
批量修复剩余 Top 文件
"""

import sys
import re
import os

def fix_file(file_path):
    """修复单个文件的日志问题"""
    
    if not os.path.exists(file_path):
        return 0, 0
    
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
        content = f.read()
    
    original_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    
    if original_count == 0:
        return 0, 0
    
    # 检查是否已有 Logger
    has_logger = 'import org.slf4j.Logger' in content
    
    # 1. 添加 Logger 导入
    if not has_logger:
        match = re.search(r'package ([\w.]+);', content)
        if match:
            package_line = f'package {match.group(1)};'
            content = content.replace(
                package_line,
                f'''{package_line}

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;'''
            )
            has_logger = True
    
    # 2. 添加 Logger 声明
    if has_logger and 'private static final Logger logger' not in content:
        class_match = re.search(r'public class (\w+)', content)
        if class_match:
            class_name = class_match.group(1)
            logger_decl = f'\n\tprivate static final Logger logger = LoggerFactory.getLogger({class_name}.class);'
            
            content = re.sub(
                r'(public class \w+)(\s*\{)',
                r'\1\2' + logger_decl,
                content
            )
    
    # 3. 替换日志语句
    content = re.sub(r'System\.out\.println\("([^"]+)"\);', r'logger.info("\1");', content)
    content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+)\);', r'logger.info("\1: {}", \2);', content)
    content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+) \+ "([^"]+)"\);', r'logger.info("\1{}{}", \2, "\3");', content)
    content = re.sub(r'e\.printStackTrace\(\);', r'logger.error("操作异常", e);', content)
    content = re.sub(r'ex\.printStackTrace\(\);', r'logger.error("操作异常", ex);', content)
    content = re.sub(r'System\.out\.println\((\w+)\);', r'logger.info("值：{}", \1);', content)
    
    final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    fixed_count = original_count - final_count
    
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
    
    return original_count, fixed_count

def main():
    # 剩余 Top 文件
    files_to_fix = [
        '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/service/impl/CarManageServiceImpl.java',
        '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/wfj/service/impl/GoldOrderServiceImpl.java',
        '/home/admin/openclaw/workspace/hyplat/src/hy/ea/marketing/action/supermaket/SuperSelfAction.java',
        '/home/admin/openclaw/workspace/hyplat/src/com/alipay/AlipayInterface.java',
        '/home/admin/openclaw/workspace/hyplat/src/hy/ea/marketing/service/impl/SuperSelfServiceImpl.java',
        '/home/admin/openclaw/workspace/hyplat/src/hy/ea/finance/action/BenDis/hy_jinbiAction.java',
        '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/wfj/service/impl/WfjJifenServiceImpl.java',
        '/home/admin/openclaw/workspace/hyplat/src/mobile/tiantai/android/action/AndroidAction.java',
        '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/action/CarMqttService.java',
        '/home/admin/openclaw/workspace/hyplat/src/com/stamp/JzqAPI.java',
    ]
    
    print("开始批量修复 Top 10 文件...")
    total_original = 0
    total_fixed = 0
    
    for file_path in files_to_fix:
        if os.path.exists(file_path):
            original, fixed = fix_file(file_path)
            total_original += original
            total_fixed += fixed
            remaining = original - fixed
            filename = os.path.basename(file_path)
            print(f"{filename:50} {original:4} → {remaining:4} (修复 {fixed})")
        else:
            print(f"跳过：{file_path}")
    
    print(f"\n总计：{total_original} 个问题，修复 {total_fixed} 个，剩余 {total_original-total_fixed} 个")
    print(f"修复率：{total_fixed/total_original*100:.1f}%" if total_original > 0 else "")

if __name__ == '__main__':
    main()
