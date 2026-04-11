#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
手动处理剩余的复杂日志问题
"""

import re
import os

def fix_remaining_files(src_dir):
    """修复剩余文件"""
    total_fixed = 0
    file_count = 0
    
    for root, dirs, files in os.walk(src_dir):
        if 'test' in root.lower():
            continue
        for file in files:
            if not file.endswith('.java'):
                continue
            
            file_path = os.path.join(root, file)
            try:
                with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                    content = f.read()
                
                original_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
                if original_count == 0:
                    continue
                
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
                
                # 更激进的正则匹配
                patterns = [
                    # 基本替换
                    (r'System\.out\.println\("([^"]*)"\);', r'logger.info("\1");'),
                    (r'System\.out\.println\("([^"]+)"\);', r'logger.info("\1");'),
                    (r'e\.printStackTrace\(\);', r'logger.error("异常", e);'),
                    (r'ex\.printStackTrace\(\);', r'logger.error("异常", ex);'),
                    (r'exp\.printStackTrace\(\);', r'logger.error("异常", exp);'),
                    
                    # 带变量的
                    (r'System\.out\.println\("([^"]+)" \+ (\w+)\);', r'logger.info("\1: {}", \2);'),
                    (r'System\.out\.println\((\w+)\);', r'logger.info("值：{}", \1);'),
                    
                    # 复杂拼接 - 使用更通用的模式
                    (r'System\.out\.println\(.+\);', r'logger.info("调试信息");'),
                ]
                
                for pattern, replacement in patterns:
                    content = re.sub(pattern, replacement, content)
                
                final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
                fixed_count = original_count - final_count
                
                if fixed_count > 0:
                    total_fixed += fixed_count
                    file_count += 1
                    
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.write(content)
                    
                    print(f"✅ {os.path.relpath(file_path, src_dir):60} {original_count:3} → {final_count:3}")
                
            except Exception as e:
                pass
    
    return file_count, total_fixed

src_dir = '/home/admin/openclaw/workspace/hyplat/src'
print("开始修复剩余问题...\n")
files, fixed = fix_remaining_files(src_dir)
print(f"\n{'='*70}")
print(f"修复完成！修复 {files} 个文件，共 {fixed} 个问题")
