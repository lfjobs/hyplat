#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
修复剩余复杂格式的日志问题
"""

import re
import os

def fix_complex_logging(file_path):
    """修复复杂格式的日志"""
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
    
    if has_logger and 'private static final Logger logger' not in content and 'Logger logger' not in content:
        class_match = re.search(r'public class (\w+)', content)
        if class_match:
            content = re.sub(
                r'(public class \w+)(\s*\{)',
                r'\1\2\n\tprivate static final Logger logger = LoggerFactory.getLogger(' + class_match.group(1) + '.class);',
                content
            )
    
    # 复杂模式匹配
    patterns = [
        # System.out.println("字符串" + 变量 + "字符串" + 变量);
        (r'System\.out\.println\("([^"]+)" \+ (\w+) \+ "([^"]+)" \+ (\w+)\);', r'logger.info("\1{}{}{}", \2, "\3", \4);'),
        
        # System.out.println("字符串" + 变量.method());
        (r'System\.out\.println\("([^"]+)" \+ (\w+\.\w+\([^)]*\))\);', r'logger.info("\1: {}", \2);'),
        
        # System.out.println(变量 + "字符串");
        (r'System\.out\.println\((\w+) \+ "([^"]+)"\);', r'logger.info("{}{}", \1, "\2");'),
        
        # System.out.println("字符串" + (类型) 变量);
        (r'System\.out\.println\("([^"]+)" \+ \((\w+)\) (\w+)\);', r'logger.info("\1: {}", \3);'),
        
        # System.out.println("字符串" + 变量 + 变量);
        (r'System\.out\.println\("([^"]+)" \+ (\w+) \+ (\w+)\);', r'logger.info("\1: {} {}", \2, \3);'),
        
        # e.printStackTrace(); / ex.printStackTrace(); / exp.printStackTrace();
        (r'e\.printStackTrace\(\);', r'logger.error("操作异常", e);'),
        (r'ex\.printStackTrace\(\);', r'logger.error("操作异常", ex);'),
        (r'exp\.printStackTrace\(\);', r'logger.error("操作异常", exp);'),
        (r't\.printStackTrace\(\);', r'logger.error("操作异常", t);'),
        
        # System.err.println
        (r'System\.err\.println\("([^"]+)"\);', r'logger.error("\1");'),
    ]
    
    for pattern, replacement in patterns:
        content = re.sub(pattern, replacement, content)
    
    final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    fixed_count = original_count - final_count
    
    if fixed_count > 0:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
    
    return original_count, fixed_count

def find_files_with_issues(src_dir, limit=50):
    """找到有问题最多的文件"""
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
    return issues[:limit]

src_dir = '/home/admin/openclaw/workspace/hyplat/src'
print("扫描剩余问题文件...\n")
files = find_files_with_issues(src_dir, limit=50)

print(f"找到 {len(files)} 个文件待修复\n")
print("开始修复...\n")

total_original = 0
total_fixed = 0
file_count = 0

for file_path, count in files:
    original, fixed = fix_complex_logging(file_path)
    if fixed > 0:
        total_original += original
        total_fixed += fixed
        file_count += 1
        remaining = original - fixed
        rel_path = os.path.relpath(file_path, '/home/admin/openclaw/workspace/hyplat')
        if remaining == 0:
            print(f"✅ {rel_path:60} {original:3} →   0")
        else:
            print(f"🔧 {rel_path:60} {original:3} → {remaining:3}")

print(f"\n{'='*70}")
print(f"修复完成！")
print(f"修复文件数：{file_count} 个")
print(f"修复问题数：{total_fixed} 个")
print(f"原始问题数：{total_original} 个")
if total_original > 0:
    print(f"修复率：{total_fixed/total_original*100:.1f}%")
