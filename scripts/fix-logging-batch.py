#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
批量修复 Java 文件的日志问题
用法：python3 fix-logging-batch.py [文件路径]
"""

import sys
import re
import os

def fix_file(file_path):
    """修复单个文件的日志问题"""
    
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
        content = f.read()
    
    original_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    
    if original_count == 0:
        return 0, 0
    
    # 检查是否已有 Logger
    has_logger = 'import org.slf4j.Logger' in content
    
    # 1. 添加 Logger 导入
    if not has_logger:
        # 找到 package 行
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
        # 提取类名
        class_match = re.search(r'public class (\w+)', content)
        if class_match:
            class_name = class_match.group(1)
            logger_decl = f'\n\tprivate static final Logger logger = LoggerFactory.getLogger({class_name}.class);'
            
            # 在类定义后添加
            content = re.sub(
                r'(public class \w+)(\s*\{)',
                r'\1\2' + logger_decl,
                content
            )
    
    # 3. 替换日志语句
    # 模式 1: System.out.println("字符串");
    content = re.sub(r'System\.out\.println\("([^"]+)"\);', r'logger.info("\1");', content)
    
    # 模式 2: System.out.println("字符串" + 变量);
    content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+)\);', r'logger.info("\1: {}", \2);', content)
    
    # 模式 3: System.out.println("字符串" + 变量 + "字符串");
    content = re.sub(r'System\.out\.println\("([^"]+)" \+ (\w+) \+ "([^"]+)"\);', r'logger.info("\1{}{}", \2, "\3");', content)
    
    # 模式 4: e.printStackTrace();
    content = re.sub(r'e\.printStackTrace\(\);', r'logger.error("操作异常", e);', content)
    
    # 模式 5: ex.printStackTrace();
    content = re.sub(r'ex\.printStackTrace\(\);', r'logger.error("操作异常", ex);', content)
    
    # 模式 6: System.out.println(变量);
    content = re.sub(r'System\.out\.println\((\w+)\);', r'logger.info("值：{}", \1);', content)
    
    # 统计修复后
    final_count = len(re.findall(r'System\.out\.println|printStackTrace', content))
    fixed_count = original_count - final_count
    
    # 保存文件
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
    
    return original_count, fixed_count

def main():
    if len(sys.argv) > 1:
        # 修复指定文件
        file_path = sys.argv[1]
        if os.path.exists(file_path):
            original, fixed = fix_file(file_path)
            print(f"{file_path}: 原始 {original} 个，修复 {fixed} 个，剩余 {original-fixed} 个")
        else:
            print(f"文件不存在：{file_path}")
    else:
        # 批量修复前 10 个最严重的文件
        files_to_fix = [
            '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/action/CarManageAction.java',
            '/home/admin/openclaw/workspace/hyplat/src/mobile/tiantai/android/action/storeProduction/budgetSheet/PayBudgetSheetAction.java',
            '/home/admin/openclaw/workspace/hyplat/src/com/tiantai/wfj/front/WfjEshopProductAction.java',
            '/home/admin/openclaw/workspace/hyplat/src/com/wechat/utils/HTTPV3.java',
            '/home/admin/openclaw/workspace/hyplat/src/com/batch/chinapay/meth/BatchPay.java',
        ]
        
        print("开始批量修复...")
        total_original = 0
        total_fixed = 0
        
        for file_path in files_to_fix:
            if os.path.exists(file_path):
                original, fixed = fix_file(file_path)
                total_original += original
                total_fixed += fixed
                remaining = original - fixed
                print(f"{os.path.basename(file_path)}: {original} → {remaining} (修复 {fixed})")
            else:
                print(f"跳过：{file_path}")
        
        print(f"\n总计：{total_original} 个问题，修复 {total_fixed} 个，剩余 {total_original-total_fixed} 个")

if __name__ == '__main__':
    main()
