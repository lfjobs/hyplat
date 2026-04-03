<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2026-03-24
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>收付方式设置</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
        }
        body {
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            min-height: 100vh;
        }
        /* 主容器：限制最大宽度，居中显示，实现自适应 */
        .container {
            width: 100%;
            max-width: 48rem; /* PC端不超过手机宽度，更美观 */
            background-color: #fff;
            box-shadow: 0 0 10rem rgba(0,0,0,0.05);
        }
        /* 顶部导航栏 */
        .header {
            background-color: #f44336;
            color: white;
            height: 3rem;
            display: flex;
            align-items: center;
            padding: 0 0.8rem;
        }
        .back-btn {
            font-size: 2rem;
            text-decoration: none;
            color: white;
        }
        /* 页面标题 */
        .page-title {
            text-align: center;
            padding: 1rem 0;
            font-size: 1.5rem;
            color: #333;
        }
        /* 选项列表 */
        .option-list {
            padding: 0 1rem;
        }
        .option-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.8rem 0;
            border-bottom: 0.2rem solid #f0f0f0;
        }
        .option-label {
            font-size: 1.3rem;
            color: #f44336;
        }
        /* 开关样式 */
        .switch {
            position: relative;
            display: inline-block;
            width: 4rem;
            height: 2rem;
        }
        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }
        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            transition: .4s;
            border-radius: 1rem;
        }
        .slider:before {
            position: absolute;
            content: "";
            height: 1.5rem;
            width: 1.5rem;
            left: 0.2rem;
            bottom: 0.25rem;
            background-color: white;
            transition: .4s;
            border-radius: 50%;
        }
        input:checked + .slider {
            background-color: #2196F3;
        }
        input:checked + .slider:before {
            transform: translateX(2.1rem);
        }
    </style>
</head>
<body>
<!-- 新增自适应容器 -->
<div class="container">
    <!-- 顶部导航栏 -->
    <div class="header">
        <a href="javascript:history.back(-1);" class="back-btn">&lt;</a>
    </div>
    <!-- 页面标题 -->
    <div class="page-title">收付方式设置</div>
    <!-- 选项列表 -->
    <div class="option-list">
        <div class="option-item">
            <span class="option-label">自定义金额</span>
            <label class="switch">
                <input type="checkbox">
                <span class="slider"></span>
            </label>
        </div>
        <div class="option-item">
            <span class="option-label">明细</span>
            <label class="switch">
                <input type="checkbox" checked>
                <span class="slider"></span>
            </label>
        </div>
        <div class="option-item">
            <span class="option-label">导入</span>
            <label class="switch">
                <input type="checkbox">
                <span class="slider"></span>
            </label>
        </div>
        <div class="option-item">
            <span class="option-label">图片</span>
            <label class="switch">
                <input type="checkbox" checked>
                <span class="slider"></span>
            </label>
        </div>
        <div class="option-item">
            <span class="option-label">商城活动下单</span>
            <label class="switch">
                <input type="checkbox">
                <span class="slider"></span>
            </label>
        </div>
    </div>
</div>
</body>
</html>
