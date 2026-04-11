#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
优化日志文案质量
"""

import re

file_path = '/home/admin/openclaw/workspace/hyplat/src/hy/ea/office/action/CarManageAction.java'

with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# 优化日志文案
optimizations = [
    # (旧文案，新文案)
    (r'logger\.info\("离开"\);', r'logger.info("[车辆检测] 车辆离开，车牌号：{}", number);'),
    (r'logger\.info\("有进入记录"\);', r'logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);'),
    (r'logger\.info\("0 元和套餐直接成功"\);', r'logger.info("[费用结算] 0 元或套餐车辆直接放行，车牌号：{}", number);'),
    (r'logger\.info\("未支付不开门"\);', r'logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);'),
    (r'logger\.info\("说明抬过杆了出口"\);', r'logger.info("[道闸控制] 出口抬杆完成");'),
    (r'logger\.info\("说明抬过杆了入口"\);', r'logger.info("[道闸控制] 入口抬杆完成");'),
    (r'logger\.info\("录入车辆失败请根据下面设备编号查询账号是否存在--------设备编号:: {}"\);', r'logger.error("[车辆录入] 失败，设备编号：{}", equipmentNumber);'),
    (r'logger\.info\("triggerType: {}"\);', r'logger.info("[触发器] 触发类型：{}", triggerType);'),
    (r'logger\.info\("equipmentNumber: {}"\);', r'logger.info("[设备] 设备编号：{}", equipmentNumber);'),
]

for old_pattern, new_pattern in optimizations:
    content = re.sub(old_pattern, new_pattern, content)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("✅ CarManageAction.java 日志文案优化完成")
