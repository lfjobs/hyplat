#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
批量修复更多文件 - 目标达到 40% 完成率
"""

import sys
import re
import os
from pathlib import Path

def fix_file(file_path):
    """修复单个文件"""
    if not os.path.exists(file_path):
        return 0, 0
    
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
        content = f.read()
    
    original_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    if original_count == 0:
        return 0, 0
    
    # 添加 Logger 导入和声明
    has_logger = 'import org.slf4j.Logger' in content
    
    if not has_logger:
        match = re.search(r'package ([\w.]+);', content)
        if match:
            content = content.replace(
                f'package {match.group(1)};',
                f'package {match.group(1)};\n\nimport org.slf4j.Logger;\nimport org.slf4j.LoggerFactory;'
            )
            has_logger = True
    
    if has_logger and 'private static final Logger logger' not in content:
        class_match = re.search(r'public class (\w+)', content)
        if class_match:
            content = re.sub(
                r'(public class \w+)(\s*\{)',
                r'\1\2\n\tprivate static final Logger logger = LoggerFactory.getLogger(' + class_match.group(1) + '.class);',
                content
            )
    
    # 替换日志
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

def find_java_files_with_issues(src_dir, limit=20):
    """找到问题最多的文件"""
    issues = []
    
    for root, dirs, files in os.walk(src_dir):
        if 'test' in root.lower():
            continue
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                        content = f.read()
                        count = len(re.findall(r'System\.out\.println|printStackTrace', content))
                        if count > 5:  # 只处理问题较多的文件
                            issues.append((file_path, count))
                except:
                    pass
    
    # 按问题数排序
    issues.sort(key=lambda x: x[1], reverse=True)
    return issues[:limit]

def main():
    src_dir = '/home/admin/openclaw/workspace/hyplat/src'
    
    print("扫描问题最多的文件...")
    files = find_java_files_with_issues(src_dir, limit=20)
    
    print(f"找到 {len(files)} 个文件待修复\n")
    print("开始批量修复...")
    
    total_original = 0
    total_fixed = 0
    
    for file_path, count in files:
        original, fixed = fix_file(file_path)
        if fixed > 0:
            total_original += original
            total_fixed += fixed
            remaining = original - fixed
            rel_path = os.path.relpath(file_path, '/home/admin/openclaw/workspace/hyplat')
            print(f"{rel_path:60} {original:3} → {remaining:3} (修复 {fixed})")
    
    print(f"\n{'='*60}")
    print(f"总计：{total_original} 个问题，修复 {total_fixed} 个，剩余 {total_original-total_fixed} 个")
    if total_original > 0:
        print(f"修复率：{total_fixed/total_original*100:.1f}%")

if __name__ == '__main__':
    main()
