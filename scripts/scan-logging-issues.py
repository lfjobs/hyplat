#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
JavaWeb 项目日志问题扫描工具
生成详细的 Excel 风格报告，便于批量修复
"""

import os
import re
from datetime import datetime
from collections import defaultdict

def scan_directory(src_dir):
    """扫描目录中的所有 Java 文件"""
    problems = {
        'sysout': [],      # System.out.println
        'printstack': [],  # printStackTrace
        'system_err': []   # System.err.println
    }
    
    java_files = []
    for root, dirs, files in os.walk(src_dir):
        # 跳过 test 目录（测试代码可以保留）
        if 'test' in root.lower():
            continue
        for file in files:
            if file.endswith('.java'):
                java_files.append(os.path.join(root, file))
    
    print(f"找到 {len(java_files)} 个 Java 文件")
    
    for file_path in java_files:
        try:
            with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                lines = f.readlines()
                for line_num, line in enumerate(lines, 1):
                    # 跳过注释行
                    stripped = line.strip()
                    if stripped.startswith('//') or stripped.startswith('/*'):
                        continue
                    
                    # 检查 System.out.println
                    if 'System.out.println' in line:
                        problems['sysout'].append({
                            'file': file_path,
                            'line': line_num,
                            'content': line.strip()[:100]
                        })
                    
                    # 检查 printStackTrace
                    if 'printStackTrace()' in line:
                        problems['printstack'].append({
                            'file': file_path,
                            'line': line_num,
                            'content': line.strip()[:100]
                        })
                    
                    # 检查 System.err.println
                    if 'System.err.println' in line:
                        problems['system_err'].append({
                            'file': file_path,
                            'line': line_num,
                            'content': line.strip()[:100]
                        })
        except Exception as e:
            print(f"读取文件失败：{file_path}, 错误：{e}")
    
    return problems

def generate_report(problems, output_file):
    """生成详细报告"""
    
    # 按文件分组统计
    file_stats = defaultdict(lambda: {'sysout': 0, 'printstack': 0, 'system_err': 0})
    
    for p in problems['sysout']:
        file_stats[p['file']]['sysout'] += 1
    for p in problems['printstack']:
        file_stats[p['file']]['printstack'] += 1
    for p in problems['system_err']:
        file_stats[p['file']]['system_err'] += 1
    
    # 写入报告
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("=" * 80 + "\n")
        f.write("JavaWeb 项目日志问题详细报告\n")
        f.write(f"生成时间：{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
        f.write("=" * 80 + "\n\n")
        
        # 汇总统计
        f.write("【汇总统计】\n")
        f.write("-" * 80 + "\n")
        f.write(f"System.out.println:  {len(problems['sysout'])} 处\n")
        f.write(f"printStackTrace:     {len(problems['printstack'])} 处\n")
        f.write(f"System.err.println:  {len(problems['system_err'])} 处\n")
        f.write(f"总计问题数：         {len(problems['sysout']) + len(problems['printstack']) + len(problems['system_err'])} 处\n")
        f.write(f"涉及文件数：         {len(file_stats)} 个\n\n")
        
        # 按文件列出问题（按问题数量排序）
        f.write("【按文件统计】（按问题数量排序）\n")
        f.write("-" * 80 + "\n")
        f.write(f"{'文件路径':<60} {'System.out':<12} {'printStack':<12} {'System.err':<12} {'总计':<8}\n")
        f.write("-" * 80 + "\n")
        
        sorted_files = sorted(file_stats.items(), 
                             key=lambda x: x[1]['sysout'] + x[1]['printstack'] + x[1]['system_err'],
                             reverse=True)
        
        for file_path, stats in sorted_files[:50]:  # 只显示前 50 个
            total = stats['sysout'] + stats['printstack'] + stats['system_err']
            f.write(f"{file_path:<60} {stats['sysout']:<12} {stats['printstack']:<12} {stats['system_err']:<12} {total:<8}\n")
        
        if len(sorted_files) > 50:
            f.write(f"... 还有 {len(sorted_files) - 50} 个文件\n")
        
        f.write("\n")
        
        # 详细问题列表（前 100 条）
        f.write("【详细问题列表】（前 100 条）\n")
        f.write("-" * 80 + "\n\n")
        
        f.write("--- System.out.println ---\n")
        for i, p in enumerate(problems['sysout'][:50], 1):
            f.write(f"{i}. {p['file']}:{p['line']}\n")
            f.write(f"   内容：{p['content']}\n\n")
        
        f.write("--- printStackTrace ---\n")
        for i, p in enumerate(problems['printstack'][:50], 1):
            f.write(f"{i}. {p['file']}:{p['line']}\n")
            f.write(f"   内容：{p['content']}\n\n")
        
        # 修复指南
        f.write("=" * 80 + "\n")
        f.write("【修复指南】\n")
        f.write("=" * 80 + "\n\n")
        
        f.write("""
步骤 1: 在类中添加 Logger 声明
-----------------------------------
在类的开头（变量声明区域）添加：

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
    // ... 其他代码
}


步骤 2: 替换 System.out.println
-----------------------------------
原代码:
    System.out.println("user id: " + userId + ", name: " + name);

修改为:
    logger.info("user id: {}, name: {}", userId, name);


步骤 3: 替换 printStackTrace
-----------------------------------
原代码:
    catch (Exception e) {
        e.printStackTrace();
    }

修改为:
    catch (Exception e) {
        logger.error("操作失败，userId={}", userId, e);
    }


步骤 4: 使用 LoggerUtil 工具类（可选）
-----------------------------------
如果项目中有 LoggerUtil 工具类，可以使用：

import com.hyplat.common.logger.LoggerUtil;

LoggerUtil.info(logger, "消息内容");
LoggerUtil.error(logger, "错误信息", e);
LoggerUtil.warn(logger, "警告信息");
LoggerUtil.debug(logger, "调试信息");


日志级别选择指南:
-----------------------------------
- ERROR: 系统错误、异常，需要立即处理
- WARN:  警告信息，不影响系统运行但需要注意
- INFO:  关键业务流程、重要状态变更
- DEBUG: 调试信息，开发环境使用
- TRACE: 最详细的跟踪信息，仅在深度调试时使用


批量替换技巧 (IntelliJ IDEA):
-----------------------------------
1. 按 Ctrl+Shift+R 打开全局替换
2. 启用正则表达式模式
3. 查找：System\.out\.println\("([^"]*)" \+ (\w+) \+ "([^"]*)"\)
   替换：logger.info("$1{}$3", $2)
4. 逐个文件审查后确认替换


注意事项:
-----------------------------------
1. 先在测试环境验证
2. 优先修复业务核心代码
3. 工具类、测试代码可以暂缓
4. 确保已添加 SLF4J + Logback 依赖
5. 配置好 logback.xml 日志配置文件
""")
    
    print(f"报告已生成：{output_file}")

def main():
    project_root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
    src_dir = os.path.join(project_root, 'src')
    output_file = os.path.join(project_root, f'logs_detailed_report_{datetime.now().strftime("%Y%m%d_%H%M%S")}.txt')
    
    print("=" * 60)
    print("JavaWeb 项目日志问题扫描工具")
    print("=" * 60)
    print(f"源码目录：{src_dir}")
    print(f"输出文件：{output_file}")
    print()
    
    problems = scan_directory(src_dir)
    generate_report(problems, output_file)
    
    print()
    print("=" * 60)
    print("扫描完成！")
    print(f"System.out.println:  {len(problems['sysout'])} 处")
    print(f"printStackTrace:     {len(problems['printstack'])} 处")
    print(f"System.err.println:  {len(problems['system_err'])} 处")
    print("=" * 60)

if __name__ == '__main__':
    main()
