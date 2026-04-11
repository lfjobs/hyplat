#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
继续修复 CarManageAction.java 的剩余日志问题
"""

import re

file_path = '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/action/CarManageAction.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# 修复模式："字符串"+变量 → logger.info("字符串：{}", 变量)
patterns = [
    (r'System\.out\.println\("diff"\+diff\);', r'logger.info("diff: {}", diff);'),
    (r'System\.out\.println\("minute"\+minute\);', r'logger.info("minute: {}", minute);'),
    (r'System\.out\.println\("romID"\+romID\);', r'logger.info("romID: {}", romID);'),
    (r'System\.out\.println\("Open"\+Open\);', r'logger.info("Open: {}", Open);'),
    (r'System\.out\.println\("channelNum"\+channelNum\);', r'logger.info("channelNum: {}", channelNum);'),
    (r'System\.out\.println\("cameraId"\+cameraId\);', r'logger.info("cameraId: {}", cameraId);'),
    (r'System\.out\.println\("number:"\+number\);', r'logger.info("number: {}", number);'),
    (r'System\.out\.println\("equipmentNumber"\+equipmentNumber\);', r'logger.info("equipmentNumber: {}", equipmentNumber);'),
    (r'System\.out\.println\("date1:"\+date1\);', r'logger.info("date1: {}", date1);'),
    (r'System\.out\.println\("date:"\+date\);', r'logger.info("date: {}", date);'),
    (r'System\.out\.println\("channelzz"\+channel\);', r'logger.info("channel: {}", channel);'),
    (r'System\.out\.println\("parkingCode"\+parkingCode\);', r'logger.info("parkingCode: {}", parkingCode);'),
    (r'System\.out\.println\("dates"\+dates\);', r'logger.info("dates: {}", dates);'),
    (r'System\.out\.println\("second"\+second\);', r'logger.info("second: {}", second);'),
    (r'System\.out\.println\("carmID"\+carmID\);', r'logger.info("carmID: {}", carmID);'),
    (r'System\.out\.println\("开门状态"\+Open\);', r'logger.info("开门状态：{}", Open);'),
    (r'System\.out\.println\("当前车辆："\+number\+"有未离开记录，直接更新进入时间。"\);', r'logger.info("当前车辆：{} 有未离开记录，直接更新进入时间。", number);'),
    (r'System\.out\.println\(photopath\+"\\""\+upName1\);', r'logger.info("照片路径：{}\\\\{}", photopath, upName1);'),
]

for pattern, replacement in patterns:
    content = re.sub(pattern, replacement, content)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("CarManageAction.java 第二轮修复完成")
