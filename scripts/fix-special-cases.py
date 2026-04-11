#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
修复剩余的复杂日志问题 - 特殊处理
"""

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
    
    if has_logger and 'private static final Logger logger' not in content and 'Logger logger' not in content:
        class_match = re.search(r'public class (\w+)', content)
        if class_match:
            content = re.sub(
                r'(public class \w+)(\s*\{)',
                r'\1\2\n\tprivate static final Logger logger = LoggerFactory.getLogger(' + class_match.group(1) + '.class);',
                content
            )
    
    # 特殊变量名的 printStackTrace
    patterns = [
        (r'e1\.printStackTrace\(\);', r'logger.error("操作异常", e1);'),
        (r'e2\.printStackTrace\(\);', r'logger.error("操作异常", e2);'),
        (r'e3\.printStackTrace\(\);', r'logger.error("操作异常", e3);'),
        (r'ef\.printStackTrace\(\);', r'logger.error("操作异常", ef);'),
        (r'ff\.printStackTrace\(\);', r'logger.error("操作异常", ff);'),
        (r'ec\.printStackTrace\(\);', r'logger.error("操作异常", ec);'),
        
        # 多行字符串拼接
        (r'System\.out\.println\("([^"]+)"\n\s*\+ "([^"]+)"\);', r'logger.info("\1 \2");'),
        
        # 空 println
        (r'System\.out\.println\(\);', r'logger.debug("");'),
        
        # 常量拼接
        (r'System\.out\.println\(([\w.]+)\);', r'logger.info("值：{}", \1);'),
    ]
    
    for pattern, replacement in patterns:
        content = re.sub(pattern, replacement, content)
    
    final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    fixed_count = original_count - final_count
    
    if fixed_count > 0:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
    
    return original_count, fixed_count

# 修复特定文件
files_to_fix = [
    '/home/admin/openclaw/workspace/hyplat/src/com/batch/chinapay/meth/BatchPay.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/batch/chinapay/util/SignData.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/daifu/chinapay/meth/SinglePay.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/importdata/action/ImportDataAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/wfj/front/NewPCendAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/wfj/front/WfjEshopProductAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/wfj/service/impl/GoldOrderServiceImpl.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/hx/comm/PropertiesUtils.java',
    '/home/admin/openclaw/workspace/hyplat/src/mobile/tiantai/android/util/FileUpLoad.java',
]

print("修复剩余复杂问题...\n")
total_fixed = 0
total_original = 0

for file_path in files_to_fix:
    original, fixed = fix_file(file_path)
    if fixed > 0:
        total_original += original
        total_fixed += fixed
        remaining = original - fixed
        print(f"✅ {os.path.basename(file_path):50} {original:3} → {remaining:3}")

print(f"\n{'='*60}")
print(f"修复完成！共修复 {total_fixed} 个问题")
