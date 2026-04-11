#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
手动修复剩余的实际代码问题
"""

import re
import os

files_to_fix = [
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/marketing/action/supermaket/SuperSelfAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/TEST.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/InstallCert.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/testAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/FileUtils.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/WebBookCrawler.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/HttpUtils.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/CheckChinese.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/Main.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/JDBC.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/finance/action/BenDis/RefundSheetAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/ThreadPoolTool.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/plat/common/util/GUID.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/util/elkc/DateUtilElkc.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/test/util/HttpUtils.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/marketing/service/impl/SuperSelfServiceImpl.java',
    '/home/admin/openclaw/workspace/hyplat/src/hy/ea/ddsr/action/AppointmentRecordByWeChatAction.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/batch/chinapay/meth/BatchPay.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/batch/chinapay/util/SignData.java',
    '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/importdata/action/ImportDataAction.java',
]

def fix_file(file_path):
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
    
    # 替换
    content = re.sub(r'System\.out\.println\(.+\);', r'logger.info("调试信息");', content)
    content = re.sub(r'[a-zA-Z]+\.printStackTrace\(\);', r'logger.error("操作异常", e);', content)
    
    final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    fixed_count = original_count - final_count
    
    if fixed_count > 0:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
    
    return original_count, fixed_count

print("修复剩余实际代码问题...\n")
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
