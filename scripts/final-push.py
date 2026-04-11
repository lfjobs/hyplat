#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
最终冲刺 - 修复所有剩余问题
"""

import re
import os

def fix_all_files(src_dir):
    """修复所有 Java 文件"""
    total_fixed = 0
    total_original = 0
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
                    else:
                        # 可能是接口或抽象类
                        content = re.sub(
                            r'(public interface \w+)(\s*\{)',
                            r'\1\2\n\tLogger logger = LoggerFactory.getLogger(\1.class);',
                            content
                        )
                
                # 替换各种日志模式
                patterns = [
                    (r'System\.out\.println\("([^"]+)"\);', r'logger.info("\1");'),
                    (r'System\.out\.println\("([^"]+)" \+ (\w+)\);', r'logger.info("\1: {}", \2);'),
                    (r'System\.out\.println\("([^"]+)" \+ (\w+) \+ "([^"]+)"\);', r'logger.info("\1{}{}", \2, "\3");'),
                    (r'System\.out\.println\("([^"]+)" \+ (\w+) \+ "([^"]+)" \+ (\w+)\);', r'logger.info("\1{}{}{}", \2, "\3", \4);'),
                    (r'e\.printStackTrace\(\);', r'logger.error("操作异常", e);'),
                    (r'ex\.printStackTrace\(\);', r'logger.error("操作异常", ex);'),
                    (r'exp\.printStackTrace\(\);', r'logger.error("操作异常", exp);'),
                    (r'System\.out\.println\((\w+)\);', r'logger.info("值：{}", \1);'),
                    (r'System\.err\.println\("([^"]+)"\);', r'logger.error("\1");'),
                ]
                
                for pattern, replacement in patterns:
                    content = re.sub(pattern, replacement, content)
                
                final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
                fixed_count = original_count - final_count
                
                if fixed_count > 0:
                    total_original += original_count
                    total_fixed += fixed_count
                    file_count += 1
                    
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.write(content)
                    
                    remaining = original_count - fixed_count
                    if remaining == 0:
                        print(f"✅ {os.path.relpath(file_path, src_dir):60} {original_count:3} →   0")
                    else:
                        print(f"🔧 {os.path.relpath(file_path, src_dir):60} {original_count:3} → {remaining:3}")
                
            except Exception as e:
                print(f"❌ 处理失败 {file_path}: {e}")
    
    return file_count, total_original, total_fixed

src_dir = '/home/admin/openclaw/workspace/hyplat/src'
print("开始最终冲刺修复...\n")
files, original, fixed = fix_all_files(src_dir)
print(f"\n{'='*70}")
print(f"最终冲刺完成！")
print(f"修复文件数：{files} 个")
print(f"修复问题数：{fixed} 个")
print(f"原始问题数：{original} 个")
if original > 0:
    print(f"修复率：{fixed/original*100:.1f}%")
