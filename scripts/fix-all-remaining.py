#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
冲刺 70% - 批量修复尽可能多的文件
"""

import sys
import re
import os

def fix_file(file_path):
    """修复单个文件"""
    if not os.path.exists(file_path):
        return 0, 0
    
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
        content = f.read()
    
    original_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    if original_count == 0:
        return 0, 0
    
    # 添加 Logger
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

def find_all_java_files(src_dir):
    """找到所有有问题的 Java 文件"""
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
                        if count > 0:
                            issues.append((file_path, count))
                except:
                    pass
    
    issues.sort(key=lambda x: x[1], reverse=True)
    return issues

def main():
    src_dir = '/home/admin/openclaw/workspace/hyplat/src'
    
    print("扫描所有 Java 文件...")
    files = find_all_java_files(src_dir)
    
    print(f"找到 {len(files)} 个文件有待修复的问题\n")
    print("开始批量修复所有文件...\n")
    
    total_original = 0
    total_fixed = 0
    file_count = 0
    
    for file_path, count in files:
        original, fixed = fix_file(file_path)
        if fixed > 0:
            total_original += original
            total_fixed += fixed
            file_count += 1
            remaining = original - fixed
            rel_path = os.path.relpath(file_path, '/home/admin/openclaw/workspace/hyplat')
            if remaining == 0:
                print(f"✅ {rel_path:60} {original:3} →   0 (修复 {fixed})")
            else:
                print(f"🔧 {rel_path:60} {original:3} → {remaining:3} (修复 {fixed})")
    
    print(f"\n{'='*70}")
    print(f"修复完成！")
    print(f"修复文件数：{file_count} 个")
    print(f"总计：{total_original} 个问题，修复 {total_fixed} 个，剩余 {total_original-total_fixed} 个")
    if total_original > 0:
        print(f"修复率：{total_fixed/total_original*100:.1f}%")

if __name__ == '__main__':
    main()
