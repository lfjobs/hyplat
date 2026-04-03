<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="touch-action" content="manipulation">
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <!-- 引入微信JSSDK -->
    <script src="https://res.wx.qq.com/open/js/jweixin-1.6.0.js"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/office/doubao/aiIndex.js" type="text/javascript" charset="utf-8"></script>
    <title>数字地球AI - 智能助手</title>
    <style>
        /* 重置样式 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: #f5f7fa;
            height: 100vh;
            display: flex;
            flex-direction: column;
            overflow: hidden;
            position: relative;
            -webkit-touch-callout: none;
            -webkit-user-select: none;
            user-select: none;
            /* 修复移动端滚动卡顿 */
            -webkit-overflow-scrolling: touch;
            overscroll-behavior: contain;
        }

        /* 头部样式 */
        .header {
            background-color: #ffffff;
            border-bottom: 1px solid #e5e7eb;
            padding: 12px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 1px 2px rgba(0,0,0,0.05);
            z-index: 10;
            flex-shrink: 0; /* 固定头部不压缩 */
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 18px;
            font-weight: 600;
            color: #212529;
        }

        .logo-icon {
            width: 32px;
            height: 32px;
            background-color: #4096ff;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 18px;
        }

        /* 功能导航区 */
        .function-nav {
            background-color: #ffffff;
            padding: 10px 20px;
            border-bottom: 1px solid #e5e7eb;
            overflow-x: auto;
            white-space: nowrap;
            flex-shrink: 0; /* 固定导航栏不压缩 */
        }

        .function-item {
            display: inline-block;
            padding: 8px 16px;
            margin-right: 8px;
            border-radius: 20px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.2s;
            border: 1px solid #e5e7eb;
        }

        .function-item.active {
            background-color: #4096ff;
            color: white;
            border-color: #4096ff;
        }

        .function-item:hover {
            background-color: #f0f7ff;
        }

        .function-item.active:hover {
            background-color: #338aff;
        }
  #voiceBtn1 img{
         width:2rem;
  }
        /* 对话区域 - 优化滚动 */
        .chat-container {
            flex: 1;
            padding: 20px;
            overflow-y: auto; /* 允许垂直滚动 */
            overflow-x: hidden; /* 禁止水平滚动 */
            height: calc(100vh - 180px); /* 基础高度计算 */
            min-height: 0; /* 修复flex滚动问题 */
            /* 优化移动端滚动体验 */
            -webkit-overflow-scrolling: touch;
            scroll-behavior: smooth;
            scrollbar-width: thin;
        }

        /* 面板展开时调整高度 */
        .chat-container.expanded {
            height: calc(100vh - 320px);
        }

        /* 消息容器基础样式 */
        .message {
            margin-bottom: 24px;
            width: 100%;
        }

        /* 用户消息 - 自适应宽度，右对齐 */
        .user-message {
            text-align: right;
        }

        .user-content {
            max-width: 80%;
            display: inline-block;
            background-color: #4096ff;
            color: white;
            padding: 12px 16px;
            border-radius: 12px;
            font-size: 15px;
            line-height: 1.6;
            text-align: left;
            position: relative;
            cursor: pointer; /* PC端添加指针样式 */
        }
        .user-content-file {




            padding: 8px 16px;
            margin: 0 16px;
            display: flex;
            flex-wrap: nowrap;

            min-height: 0;
            display: none;
            flex-shrink: 0;
            display: flex;
            height: auto;
            min-height: 80px;
            overflow-x: auto;
            white-space: nowrap;
            padding: 8px 0;
            width: 100%;
            overflow-y: hidden;
            padding-right: 26px !important;
            gap: 12px !important;
            padding-left: 0px !important;



        }
        .user-content-file-length{
            justify-content: flex-end; /* 右对齐核心属性 */
        }

        .user-content-file div{
            position: relative;
             width: 80px;
             height: 80px;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgb(0 0 0 / 10%);
            padding: 2px;
            cursor: pointer;
            display: inline-block;
            flex-shrink: 0 !important;
            line-height: 4rem;


            }
            .user-content-file .img-user{
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.2s ease;
            }

            /* 助手消息样式 */
        .assistant-content {
            display: inline-block;
            background-color: #ffffff;
            color: #333333;
            padding: 12px 16px;
            border-radius: 12px;
            font-size: 15px;
            line-height: 1.6;
            max-width: 80%;
            text-align: left;
            position: relative;
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
            cursor: pointer; /* PC端添加指针样式 */
        }

        /* 选中状态样式 */
        .content-selected {
            background-color: #e8f4ff !important;
            color: #1d6ef3 !important;
            box-shadow: 0 0 0 2px #4096ff !important;
        }

        /* 语音消息样式 */
        .voice-message {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 10px 16px;
            background-color: #4096ff;
            color: white;
            border-radius: 12px;
            max-width: 70%;
            cursor: pointer; /* PC端添加指针样式 */
        }

        .voice-duration {
            font-size: 12px;
            color: rgba(255,255,255,0.8);
        }

        .voice-play-btn {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            background-color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
        }

        /* 上传预览区域（核心）- 输入框上方左对齐 */
        .upload-preview {
            padding: 8px 16px;
            margin: 0 16px;
            display: flex;
            flex-wrap: nowrap;
            /* align-items: flex-start; */
            min-height: 0;
            display: none;
            flex-shrink: 0;
            display: flex;
            height: auto;
            min-height: 80px;
            overflow-x: auto;

            white-space: nowrap;
            padding: 8px 0;

            width: 100%;

            overflow-y: hidden;
            /* 核心修复：新增滚动补位 */
            padding-right: 26px !important;  /* 补位间距（和子项gap一致），给最后一个元素留空间 */
            gap: 12px !important;            /* 子项间距统一，避免margin计算偏差 */
            padding-left: 0px !important;   /* 左侧间距对称，不影响滚动 */


            }

            /* 预览项样式 - 确保删除按钮完整显示 */
        .preview-item {
            position: relative;
            /* width: 80px; */
            /* height: 80px; */
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgb(0 0 0 / 10%);
            padding: 2px;
            cursor: pointer;
            display: inline-block;
            flex-shrink: 0 !important;
            /* display: block; */

            }
        .add-item{
            box-shadow:none;
        }

            /* 图片预览 */
        .preview-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 6px;
            transition: transform 0.2s ease;
        }

        .preview-img:hover {
            transform: scale(1.02);
        }

        /* 文件预览 */
        .preview-file {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #f5f5f5;
            color: #666;
            font-size: 12px;
            padding: 8px;
            text-align: center;
            border-radius: 6px;
        }

        /* 预览项删除按钮 - 完整显示 */
        .preview-close {
            position: absolute;
            top: -10px;
            right: -10px;
            width: 26px;
            height: 26px;
            border-radius: 50%;
            background-color: #ff3b30;
            color: white;
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            box-shadow: 0 2px 6px rgba(0,0,0,0.2);
            z-index: 20;
            /* 确保按钮不被遮挡 */
            pointer-events: auto;
        }

        /* 图片放大查看层 */
        .image-viewer {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.9);
            z-index: 1000;
            display: none;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .image-viewer.active {
            display: flex;
        }

        .viewer-content {
            max-width: 90%;
            max-height: 90%;
            position: relative;
        }

        .viewer-image {
            max-width: 100%;
            max-height: 80vh;
            border-radius: 8px;
        }

        .viewer-close {
            position: absolute;
            top: -20px;
            right: -20px;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #ff3b30;
            color: white;
            font-size: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            border: none;
        }

        /* 语音录制状态提示 - 微信风格 */
        .voice-status {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: rgba(0,0,0,0.7);
            color: white;
            border-radius: 8px;
            padding: 20px 30px;
            font-size: 16px;
            z-index: 100;
            display: none;
            flex-direction: column;
            align-items: center;
            gap: 10px;
        }

        .voice-status.recording {
            display: flex;
        }

        .voice-status .mic-icon {
            font-size: 40px;
            color: #ff3b30;
            animation: pulse 1.5s infinite;
        }

        @keyframes pulse {
            0% { transform: scale(1); opacity: 1; }
            50% { transform: scale(1.2); opacity: 0.8; }
            100% { transform: scale(1); opacity: 1; }
        }

        .voice-status .cancel-tip {
            font-size: 14px;
            color: #ccc;
            margin-top: 8px;
        }

        .voice-duration-text {
            font-size: 14px;
            color: #fff;
        }

        /* 底部输入区域 - 图片样式 */
        .input-container {
            background-color: #ffffff;
            transition: all 0.3s ease;
            z-index: 20;
            padding: 0;
            flex-shrink: 0; /* 固定底部不压缩 */
        }

        /* 顶部输入条（无左侧相机图标） */
        .input-bar {
            display: flex;
            align-items: center;
            padding: 12px 16px;
            background-color: #ffffff;
            border-radius: 24px;
            margin: 12px 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        /* 输入框（占满空间） */
        #messageInput {
            flex: 1;
            border: none;
            outline: none;
            font-size: 16px;
            padding: 8px 0;
            background: transparent;
            color: #333;
            resize: none;
            min-height: 20px;
            max-height: 100px;
        }

        #messageInput::placeholder {
            color: #999;
        }

        /* 按钮容器 - 包含语音、加号按钮 */
        .btn-container {
            display: flex;
            align-items: center;
            gap: 8px;
            transition: all 0.2s ease;
        }

        /* 发送按钮容器 - 仅包含发送箭头 */
        .send-btn-container {
            display: none;
            align-items: center;
            transition: all 0.2s ease;
        }

        /* 按钮样式统一 */
        .bar-btn {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            background-color: #f5f5f5;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            flex-shrink: 0;
            transition: all 0.1s ease;
            border: none;
            touch-action: manipulation;
            margin-top: 0.2rem;
        }

        /* 语音按钮按住状态 */
        .bar-btn.voice.holding {
            background-color: #ff3b30;
            transform: scale(0.95);
        }

        .bar-btn.voice .icon {
            font-size: 20px;
            color: #666;
        }

        .bar-btn.voice.holding .icon {
            color: white;
        }

        .bar-btn.toggle .icon {
            font-size: 20px;
            color: #666;
        }

        /* 发送按钮样式 - 修改：向上箭头 + 蓝色背景 */
        .bar-btn.send {
            /* 默认不可点击状态 */
            background-color: #e5e9f0; /* 浅灰色背景 */
            opacity: 0.5;
            cursor: not-allowed;
        }

        .bar-btn.send .icon {
            font-size: 20px;
            color: #999; /* 灰色箭头 */
        }

        /* 发送按钮可点击状态 - 修改：蓝色背景 + 白色向上箭头 */
        .bar-btn.send.active {
            background-color: #4096ff; /* 蓝色背景 */
            opacity: 1;
            cursor: pointer;
        }

        .bar-btn.send.active .icon {
            color: white; /* 白色箭头 */
        }

        /* 底部功能面板 - 修改：从4列改为3列 */
        .bottom-menu {
            background-color: #ffffff;
            padding: 16px;
            display: grid;
            grid-template-columns: repeat(3, 1fr); /* 修改：3列布局 */
            gap: 12px;
            border-radius: 0;
            box-shadow: none;
            transform: translateY(0);
            transition: transform 0.3s ease-out;
            flex-shrink: 0;
        }

        /* 默认隐藏面板 */
        .bottom-menu.hidden {
            transform: translateY(100%);
            height: 0;
            padding: 0;
        }

        .menu-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            color: #666;
            font-size: 16px;
            gap: 8px;
            padding: 20px 0;
            background-color: #f5f5f5;
            border-radius: 12px;
        }

        .menu-icon {
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            background-color: transparent;
        }

        /* 滚动条美化 */
        ::-webkit-scrollbar {
            width: 6px;
            height: 6px;
        }

        ::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        ::-webkit-scrollbar-thumb {
            background: #c1c1c1;
            border: none;
            border-radius: 3px;
        }

        /* ========== 右键菜单样式 - 统一居中显示 ========== */
        .context-menu {
            position: fixed;
            width: 200px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
            z-index: 9999;
            display: none;
            overflow: hidden;
            border: 1px solid #e5e7eb;
            /* 统一居中基础样式 */
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .context-menu.active {
            display: block;
            animation: fadeIn 0.2s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translate(-50%, -50%) scale(0.95);
            }
            to {
                opacity: 1;
                transform: translate(-50%, -50%) scale(1);
            }
        }

        .context-menu-item {
            padding: 12px 20px;
            font-size: 15px;
            color: #333;
            cursor: pointer;
            transition: background-color 0.2s;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .context-menu-item:hover {
            background-color: #f5f7fa;
        }

        .context-menu-item:active {
            background-color: #e8f4ff;
        }

        .context-menu-item .icon {
            font-size: 18px;
            width: 20px;
            text-align: center;
        }

        .context-menu-divider {
            height: 1px;
            background-color: #e5e7eb;
            margin: 2px 0;
        }

        /* 遮罩层 */
        .menu-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.3);
            z-index: 9998;
            display: none;
        }

        .menu-overlay.active {
            display: block;
            animation: fadeOverlay 0.2s ease;
        }

        @keyframes fadeOverlay {
            from { opacity: 0; }
            to { opacity: 0.3; }
        }

        /* 响应式适配 */
        @media (max-width: 768px) {
            /* 优化移动端聊天容器 */
            .chat-container {
                padding: 15px 10px;
                height: calc(100vh - 160px);
                /* 增强移动端滚动体验 */
                -webkit-overflow-scrolling: touch;
                touch-action: pan-y;
            }

            .chat-container.expanded {
                height: calc(100vh - 300px);
            }

            .user-content, .assistant-content {
                max-width: 99%;
                font-size: 14px;
                padding: 10px 14px;
                cursor: default; /* 移动端移除指针样式 */
            }
            .voice img{
               width:2rem;

                -webkit-tap-highlight-color: transparent!important;
            }


            .voice-message {
                cursor: default; /* 移动端移除指针样式 */
            }

            .preview-item {
                width: 70px;
                height: 70px;
            }

            .preview-close {
                width: 20px;
                height: 20px;
                top: 0px;
                right: 0px;
            }

            .voice-status {
                padding: 15px 25px;
            }

            .voice-status .mic-icon {
                font-size: 35px;
            }
            .voice-text{
                 text-align:center;
                font-weight:bold;
            }
            .voice-text::placeholder {
                color: red;

            }

            .context-menu {
                width: 180px;
            }

            /* 调整输入框高度 */
            #messageInput {
                font-size: 15px;
            }

            /* 底部按钮间距调整 */
            .btn-container {
                gap: 6px;
            }

            .bar-btn {
                width: 34px;
                height: 34px;
            }

            .viewer-close {
                width: 30px;
                height: 30px;
                top: -15px;
                right: -15px;
                font-size: 16px;
            }
        }

        /* 图标样式 */
        .icon {
            font-family: "Arial Unicode MS", sans-serif;
        }

        /* 隐藏文件输入 */
        .file-input {
            display: none;
        }

        /* 权限提示框 */
        .permission-alert {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            background-color: #fff;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 12px 16px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            z-index: 1000;
            display: none;
        }

        .permission-alert.show {
            display: block;
            animation: fadeInUp 0.3s ease;
        }
        .loading-spinner {
            position: absolute;
            top: 37%;
            right: 37%;
            display: flex;
            align-items: center;
            justify-content: center;
            width: 16px;
            height: 16px;
            border: 2px solid #e0e0e0;
            border-top: 2px solid #999;
            border-radius: 50%;
            animation: spin 0.8s infinite linear;
            display: inline-block;

            }
        @keyframes spin {
            /* 动画开始时（0%进度）：旋转角度为0度 */
            0% { transform: rotate(0deg); }
            /* 动画结束时（100%进度）：旋转角度为360度 */
            100% { transform: rotate(360deg); }
        }
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translate(-50%, -20px);
                }
                to {
                    opacity: 1;
                    transform: translate(-50%, 0);
                }
            }
        </style>
</head>
<body>
<!-- 权限提示框 -->
<div class="permission-alert" id="permissionAlert">
    需要麦克风权限才能使用语音功能，请允许浏览器访问麦克风。
</div>

<!-- 微信风格语音录制提示 -->
<div class="voice-status" id="voiceStatus">
    <div class="mic-icon icon"><img src="<%=basePath%>images/WFJClient/pc/newimg/audio.png"></div>
    <div class="status-text">按住说话</div>
    <div class="voice-duration-text" id="voiceDuration">00:00</div>
    <div class="cancel-tip">上滑取消</div>
</div>

<!-- 新增：图片放大查看层 -->
<div class="image-viewer" id="imageViewer">
    <div class="viewer-content">
        <img src="" alt="预览图片" class="viewer-image" id="viewerImage">
        <button class="viewer-close" id="viewerCloseBtn">✕</button>
    </div>
</div>

<!-- 新增：遮罩层 -->
<div class="menu-overlay" id="menuOverlay"></div>

<!-- 右键/长按菜单 - 统一居中显示（移除了选取文字选项） -->
<div class="context-menu" id="contextMenu">
    <div class="context-menu-item" id="copyText">
        <span class="icon">📋</span>
        <span>复制文本</span>
    </div>
    <div class="context-menu-item" id="voiceld">
        <span class="icon"> <img src="<%=basePath%>/images/WFJClient/pc/newimg/voice.png" style="width: 1.2rem;"/>
        </span>
        <span>朗读文本</span>
    </div>
    <div class="context-menu-item" id="createdoc">
        <span class="icon"> <img src="<%=basePath%>/images/WFJClient/pc/newimg/WORD.png" style="width: 1.2rem;"/>
      </span>
        <span>创建word</span>
    </div>
    <div class="context-menu-divider"></div>
    <div class="context-menu-item" id="deleteMessage" style="color: #ff3b30;">
        <span class="icon">🗑️</span>
        <span>删除消息</span>
    </div>
</div>

<!-- 头部 -->
<div class="header">
    <div class="logo">
        <div class="logo-icon">
            <span class="icon">🌍</span>
        </div>
        <span>数字地球AI</span>
    </div>
    <div>
        <button style="padding: 4px 12px; border: 1px solid #e5e7eb; border-radius: 4px; background: white; cursor: pointer;">
            <span class="icon voice-auto" id="voiceauto"><img src="<%=basePath%>/images/WFJClient/pc/newimg/voice.png" style="width: 1.5rem;"/></span>
        </button>
        <button id="playBtn" style="display:none;padding:14px 28px;font-size:16px;">播放语音</button>
        <%--<textarea id="text" style="width:100%;height:120px;">--%>
<%--你好，这个版本在微信里自动播放，24000采样率，正常语速，不卡顿，不重复，无杂音。--%>
<%--</textarea>--%>
    <%--<button style="padding: 4px 12px; border: 1px solid #e5e7eb; border-radius: 4px; background: white; cursor: pointer;">--%>
            <%--<span class="icon">📜</span> 历史--%>
        <%--</button>--%>
    </div>
</div>

<!-- 功能导航区 -->
<div class="function-nav">
    <div class="function-item active" data-type="chat">智能对话</div>
    <div class="function-item" data-type="image">图像生成</div>
    <div class="function-item" data-type="video">视频生成</div>
    <div class="function-item" data-type="doc">文档分析</div>
    <div class="function-item" data-type="translate">翻译</div>
</div>

<!-- 对话区域 -->
<div class="chat-container" id="chatContainer">
    <!-- 欢迎消息 -->
    <div class="message assistant-message">
        <div class="assistant-content">
            你好！我是数字地球AI，选择上方功能或直接输入问题，我会尽力解答。<br/>按住语音按钮说话，松手自动发送语音转文字结果。
        </div>
    </div>
</div>

<!-- 底部输入区域 -->
<div class="input-container" id="inputContainer">
    <!-- 上传预览区域（核心）- 输入框上方左对齐 -->
    <div class="upload-preview" id="uploadPreview">

        <div class="preview-item add-item" id="addFile"  style="display: none;" >
            <img class="preview-img" src="<%=basePath%>images/WFJClient/pc/newimg/AddF1.png" >
           </div>

    </div>

    <!-- 顶部输入条（无左侧相机图标） -->
    <div class="input-bar">
        <!-- 输入框（占满空间） -->
        <textarea id="messageInput" placeholder="输入消息..." spellcheck="false" style="line-height: 0.8rem"></textarea>


        <!-- 语音和加号按钮容器（默认显示） -->
        <div class="btn-container" id="normalBtnContainer">
            <!-- 右侧语音按钮 - 微信风格 -->
            <button class="bar-btn voice text" id="voiceBtn1">
                <span class="icon"><img src="<%=basePath%>images/WFJClient/pc/newimg/audio.png"></span>
            </button>

            <!-- 右侧开关按钮 -->
            <button class="bar-btn toggle" id="toggleBtn">
                <span class="icon"><img  style="width: 2rem;" src="<%=basePath%>images/WFJClient/pc/newimg/jiahao.png"></span>
            </button>
        </div>

        <!-- 发送按钮容器（默认隐藏） -->
        <div class="send-btn-container" id="sendBtnContainer">
            <!-- 发送按钮 -->
            <button class="bar-btn send" id="sendBtn">
                <span class="icon">⬆️</span>
            </button>
        </div>
    </div>

    <!-- 底部功能面板 - 移除电话选项 -->
    <div class="bottom-menu hidden" id="bottomMenu">
        <div class="menu-item" id="menuCamera">
            <div class="menu-icon">📷</div>
            <span>相机</span>
        </div>
        <div class="menu-item" id="menuAlbum">
            <div class="menu-icon">🖼️</div>
            <span>相册</span>
        </div>
        <div class="menu-item" id="menuFile">
            <div class="menu-icon">📎</div>
            <span>文件</span>
        </div>
    </div>

    <!-- 隐藏的文件输入控件 -->
    <input type="file" id="imageUpload" class="file-input" accept="image/*" capture="camera">
    <input type="file" id="albumUpload" class="file-input" accept="image/*" multiple>
    <input type="file" id="fileUpload" class="file-input" accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf,.txt,.csv,.rtf" multiple>
</div>

<script>
    var  basePath　="<%=basePath%>";
    // 全局变量 - 移除menuCall相关定义
    const chatContainer = document.getElementById('chatContainer');
    const messageInput = document.getElementById('messageInput');
    const voiceBtn = document.getElementById('voiceBtn');
    const toggleBtn = document.getElementById('toggleBtn');
    const sendBtn = document.getElementById('sendBtn');
    const menuCamera = document.getElementById('menuCamera');
    const menuAlbum = document.getElementById('menuAlbum');
    const menuFile = document.getElementById('menuFile');
    const bottomMenu = document.getElementById('bottomMenu');
    const uploadPreview = document.getElementById('uploadPreview');

    const addFile = document.getElementById('addFile');

    const imageUpload = document.getElementById('imageUpload');
    const albumUpload = document.getElementById('albumUpload');
    const fileUpload = document.getElementById('fileUpload');
    const functionItems = document.querySelectorAll('.function-item');

    // 新增：图片查看器相关元素
    const imageViewer = document.getElementById('imageViewer');
    const viewerImage = document.getElementById('viewerImage');
    const viewerCloseBtn = document.getElementById('viewerCloseBtn');

    // 新增：按钮容器元素
    const normalBtnContainer = document.getElementById('normalBtnContainer');
    const sendBtnContainer = document.getElementById('sendBtnContainer');

    // 长按菜单相关变量
    const contextMenu = document.getElementById('contextMenu');
    const menuOverlay = document.getElementById('menuOverlay');
    const copyText = document.getElementById('copyText');
    const voiceld = document.getElementById('voiceld');
    const createdoc = document.getElementById('createdoc');

    const deleteMessage = document.getElementById('deleteMessage');
    let currentTarget = null; // 当前选中的消息元素

    // 设备检测变量
    const isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
    let touchTimer = null; // 移动端长按计时器
    let formateFile = "";

    // 语音功能相关变量（微信风格）
    const voiceStatus = document.getElementById('voiceStatus');
    const voiceDuration = document.getElementById('voiceDuration');
    const permissionAlert = document.getElementById('permissionAlert');
    let isVoice = true;

    // 状态变量
    let isMenuOpen = false;
    let recognition = null;
    let isRecording = false;
    let isHoldingVoiceBtn = false; // 是否按住语音按钮
    let isVoiceCanceled = false; // 是否取消语音
    let uploadedFiles = [];
    let currentFunctionType = 'chat';
    let recordingStartTime = 0;
    let recordingDuration = 0;
    let recordingTimer = null;
    let voiceTranscript = ''; // 存储语音转文字结果
    let touchStartY = 0; // 触摸起始Y坐标（用于上滑取消）
    const CANCEL_THRESHOLD = 50; // 上滑取消的阈值（像素）

    const functionPlaceholders = {
        chat: '输入消息...',
        image: '描述你想要生成的图像...',
        translate: '翻译【简体中文<->英语】',
        video: '描述你想要生成的视频...',
        code: '输入需要生成的代码...',
        doc: '上传文档并输入处理需求...'
    };

    // 初始化
    function init() {
        initVoiceRecognition();
        checkMicrophonePermission();
    //    initVoiceButtonEvents();
        initSendButton();
        initContextMenu();

        // 新增：初始化图片查看器
        initImageViewer();

        // 新增：监听输入框变化，切换按钮显示
        messageInput.addEventListener('input', toggleButtonDisplay);
        messageInput.addEventListener('change', toggleButtonDisplay);
        messageInput.addEventListener('keyup', toggleButtonDisplay);

        // 监听窗口大小变化，重新计算聊天区域高度
        window.addEventListener('resize', adjustChatContainerHeight);
        // 初始调整高度
        adjustChatContainerHeight();

        // 初始检查按钮显示状态
        toggleButtonDisplay();

        // 功能切换
        functionItems.forEach(item => {
            item.addEventListener('click', function() {
                functionItems.forEach(i => i.classList.remove('active'));
                this.classList.add('active');
                currentFunctionType = this.dataset.type;
                messageInput.placeholder = functionPlaceholders[currentFunctionType];
            });
        });

        // 开关面板
        toggleBtn.addEventListener('click', toggleMenu);

        // 功能按钮点击 - 移除电话相关事件
        menuCamera.addEventListener('click', () => {
            imageUpload.value = '';
            imageUpload.click();
        });

        menuAlbum.addEventListener('click', () => {
            albumUpload.value = '';
            albumUpload.click();
        });

        menuFile.addEventListener('click', () => {
            fileUpload.value = '';
            fileUpload.click();
        });
        addFile.addEventListener('click', () => {
            if(formateFile == "file"){
                fileUpload.value = '';
                fileUpload.click();
            }else{
                albumUpload.value = '';
                albumUpload.click();
            }

        });

        // 文件上传监听
        imageUpload.addEventListener('change', e => handleFileUpload(e.target.files, 'image'));
        albumUpload.addEventListener('change', e => handleFileUpload(e.target.files, 'image'));
        addFile.addEventListener('change', e => handleFileUpload(e.target.files, 'image'));
        fileUpload.addEventListener('change', e => handleFileUpload(e.target.files, 'file'));

        // 回车发送
        messageInput.addEventListener('keydown', e => {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                sendMessage();
            }
        });

        // 监听文件上传变化，更新发送按钮状态
        const observer = new MutationObserver(updateSendButtonStatus);
        observer.observe(uploadPreview, { childList: true });

        messageInput.focus();
    }

    // 新增：初始化图片查看器
    function initImageViewer() {
        // 关闭按钮点击事件
        viewerCloseBtn.addEventListener('click', closeImageViewer);

        // 点击遮罩层关闭
        imageViewer.addEventListener('click', function(e) {
            if (e.target === imageViewer) {
                closeImageViewer();
            }
        });

        // 键盘ESC关闭
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape' && imageViewer.classList.contains('active')) {
                closeImageViewer();
            }
        });
    }

    // 新增：打开图片查看器
    function openImageViewer(imageUrl) {
        viewerImage.src = imageUrl;
        imageViewer.classList.add('active');
        // 禁止页面滚动
        document.body.style.overflow = 'hidden';
    }

    // 新增：关闭图片查看器
    function closeImageViewer() {
        imageViewer.classList.remove('active');
        viewerImage.src = '';
        // 恢复页面滚动
        document.body.style.overflow = '';
    }

    // 新增：根据输入框内容切换按钮显示
    function toggleButtonDisplay() {
        const hasContent = messageInput.value.trim().length > 0 || uploadedFiles.length > 0;

        if (hasContent) {
            // 有内容：显示发送按钮，隐藏语音和加号按钮
           normalBtnContainer.style.display = 'none';
            sendBtnContainer.style.display = 'flex';
        } else {
            // 无内容：显示语音和加号按钮，隐藏发送按钮
            normalBtnContainer.style.display = 'flex';
            sendBtnContainer.style.display = 'none';
        }

        // 更新发送按钮状态
        updateSendButtonStatus();
    }

    // 调整聊天容器高度以适配屏幕
    function adjustChatContainerHeight() {
        const headerHeight = document.querySelector('.header').offsetHeight;
        const navHeight = document.querySelector('.function-nav').offsetHeight;
        const inputContainerHeight = document.querySelector('.input-container').offsetHeight;

        let chatHeight = window.innerHeight - headerHeight - navHeight - inputContainerHeight - 20;

        // 面板展开时调整
        if (isMenuOpen) {
            const bottomMenuHeight = document.querySelector('.bottom-menu').offsetHeight;
            chatHeight -= bottomMenuHeight;
        }

        chatContainer.style.height = `${chatHeight}px`;
    }

    // 初始化上下文菜单事件
    function initContextMenu() {
        // PC端：右键菜单触发
        if (!isMobile) {
            chatContainer.addEventListener('contextmenu', handleContextMenu);
        }

        // PC端：单击关闭菜单
        if (!isMobile) {
            document.addEventListener('click', e => {
                const target = e.target.closest('.context-menu, .user-content, .assistant-content, .voice-message');
                if (!target) {
                    closeContextMenu();
                }
            });
        }

        // 移动端：触摸事件
        if (isMobile) {
            chatContainer.addEventListener('touchstart', handleMessageTouchStart, { passive: true });
            chatContainer.addEventListener('touchend', handleMessageTouchEnd);
            chatContainer.addEventListener('touchmove', handleMessageTouchMove);
        }

        // 点击遮罩层关闭菜单
        menuOverlay.addEventListener('click', closeContextMenu);

        // 菜单选项点击事件（移除了选取文字相关代码）
        copyText.addEventListener('click', copyCurrentText);
        deleteMessage.addEventListener('click', deleteCurrentMessage);
        voiceld.addEventListener('click', ldText);
        createdoc.addEventListener('click', createWord);


    }

    // PC端处理右键菜单事件
    function handleContextMenu(e) {
        const target = e.target.closest('.user-content, .assistant-content, .voice-message');
        if (target) {
            e.preventDefault();

            // 设置当前选中目标
            currentTarget = target;

            // 显示菜单（统一居中）
            showContextMenu();
        }
    }

    // 移动端处理触摸开始事件（长按检测）
    function handleMessageTouchStart(e) {
        if (!isMobile) return;

        // 只处理消息内容区域
        const target = e.target.closest('.user-content, .assistant-content, .voice-message');
        if (!target) return;

        // 设置当前选中目标
        currentTarget = target;

        // 设置长按计时器（500ms）
        touchTimer = setTimeout(() => {
            showContextMenu();
        }, 500);
    }

    // 移动端处理触摸结束事件
    function handleMessageTouchEnd() {
        if (!isMobile) return;

        // 清除长按计时器（点击未达到长按时间）
        clearTimeout(touchTimer);
    }

    // 移动端处理触摸移动事件
    function handleMessageTouchMove() {
        if (!isMobile) return;

        // 触摸移动时清除长按计时器
        clearTimeout(touchTimer);
    }

    // 显示上下文菜单（统一居中）
    function showContextMenu() {
        if (!currentTarget) return;

        // 给当前选中的元素添加选中样式
        currentTarget.classList.add('content-selected');

        // 显示遮罩层
        menuOverlay.classList.add('active');

        // 统一居中显示（PC/移动端一致）
        contextMenu.classList.add('active');
    }

    // 关闭上下文菜单
    function closeContextMenu() {
        menuOverlay.classList.remove('active');
        contextMenu.classList.remove('active');

        // 移除选中样式
        if (currentTarget) {
            currentTarget.classList.remove('content-selected');
        }

        // 清除长按计时器
        clearTimeout(touchTimer);

        currentTarget = null;
    }

    // 复制文本功能
    function copyCurrentText() {
        if (!currentTarget) return;

        try {
            // 1. 获取纯文本内容（去除HTML标签和多余空格）
            let text = '';

            // 区分不同类型的消息
            if (currentTarget.classList.contains('voice-message')) {
                // 语音消息 - 提取文本内容
                const textElements = currentTarget.querySelectorAll('span:not(.voice-duration)');
                if (textElements.length > 0) {
                    text = textElements[0].textContent.trim();
                } else {
                    text = currentTarget.textContent.trim();
                }
            } else {
                // 普通文本消息
                text = currentTarget.textContent.trim();
            }

            // 空文本处理
            if (!text) {
                showToast('没有可复制的文本内容');
                closeContextMenu();
                return;
            }

            // 2. 多方案复制实现（确保兼容性）
            const copyPromises = [];

            // 方案1：现代浏览器 Clipboard API（首选）
            if (navigator.clipboard && window.isSecureContext) {
                copyPromises.push(
                    navigator.clipboard.writeText(text)
                        .then(() => ({ success: true, method: 'clipboard-api' }))
                        .catch(err => ({ success: false, method: 'clipboard-api', error: err }))
                );
            }

            // 方案2：execCommand 降级方案
            copyPromises.push(
                new Promise((resolve) => {
                    try {
                        // 创建临时文本区域
                        const textArea = document.createElement('textarea');
                        textArea.value = text;

                        // 确保textarea不在视口中但可选中
                        textArea.style.position = 'fixed';
                        textArea.style.top = '0';
                        textArea.style.left = '0';
                        textArea.style.width = '2em';
                        textArea.style.height = '2em';
                        textArea.style.padding = '0';
                        textArea.style.border = 'none';
                        textArea.style.outline = 'none';
                        textArea.style.boxShadow = 'none';
                        textArea.style.background = 'transparent';

                        document.body.appendChild(textArea);

                        // 选中并复制
                        textArea.focus();
                        textArea.select();
                        textArea.setSelectionRange(0, text.length); // 移动端兼容

                        const successful = document.execCommand('copy');

                        // 清理
                        document.body.removeChild(textArea);

                        resolve({
                            success: successful,
                            method: 'exec-command',
                            error: successful ? null : new Error('execCommand 返回 false')
                        });
                    } catch (err) {
                        resolve({ success: false, method: 'exec-command', error: err });
                    }
                })
            );

            // 3. 执行所有复制方案，只要有一个成功就视为复制成功
            Promise.all(copyPromises)
                .then(results => {
                    const successfulResult = results.find(r => r.success);

                    if (successfulResult) {
                        console.log(`复制成功，使用方法: ${successfulResult.method}`);
                        showToast('文本复制成功 ✅');
                    } else {
                        // 所有方案都失败
                        console.error('所有复制方案都失败:', results);
                        // 显示文本让用户手动复制
                        showToast(`复制失败，请手动复制：${text}`);
                    }
                })
                .catch(err => {
                    console.error('复制过程出错:', err);
                    showToast('复制失败，请手动复制');
                })
                .finally(() => {
                    closeContextMenu();
                });

        } catch (err) {
            console.error('复制失败:', err);
            showToast('复制失败，请手动复制');
            closeContextMenu();
        }
    }

    // 删除当前消息
    function deleteCurrentMessage() {
        if (!currentTarget) return;

        try {
            const messageContainer = currentTarget.closest('.message');
            if (messageContainer) {
                messageContainer.style.opacity = '0';
                messageContainer.style.transition = 'opacity 0.3s';

                setTimeout(() => {
                    messageContainer.remove();
                }, 300);
            }

            showToast('消息已删除 🗑️');
            closeContextMenu();
            let id = messageContainer.dataset.id;
            deleteRecord(id);
        } catch (err) {
            console.error('删除消息失败:', err);
            showToast('删除消息失败，请重试');
            closeContextMenu();
        }
    }
    function deleteRecord(id){
        const formData = new FormData();
        formData.append("doubaoSession.id", id);

        var url = basePath + "ea/ai/sajax_ea_deleteRecord.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            data:formData,
            processData: false,
            contentType: false,
            cache: true,
            success: function cbf(data) {


            },
            error: function () {

            }
        });




    }
    // 文本朗读
    function ldText() {
        if (!currentTarget) return;

        // 普通文本消息
        let text = currentTarget.textContent.trim();
        if (isPlaying) {
            // 🔥 正在播放 → 点击停止
            stopAll(() => {
                autoPlay(text);
            });
        }else{
            autoPlay(text);
        }
        closeContextMenu();
    }

    // 1. 点击按钮
    function createWord() {

        if (!currentTarget) return;

        // 普通文本消息
        let text = currentTarget.textContent.trim();
        try {
            const formData = new FormData();
            formData.append("doubaoSession.text", text);

            var url = basePath + "ea/ai/sajax_ea_createDoc.jspa";
            $.ajax({
                url: url,
                type: "post",
                async: true,
                dataType: "json",
                data:formData,
                processData: false,
                contentType: false,
                cache: true,
                success: function cbf(data) {
                    // 3. 关键：前端根据路径自动下载！
                    var member = eval("(" + data + ")");

                    var  fileUrl =  member.wordpath;
                  //  window.open(basePath + "/servlets/render?filename=" + encodeURI(fileUrl));
                    window.location.href = basePath + "/servlets/render?filename=" + encodeURI(fileUrl)

                },
                error: function () {

                }
            });


            closeContextMenu();
        } catch (e) {
            alert("下载失败");
            console.log(e);
        }
    }





    // 显示提示消息
    function showToast(message) {
        const toast = document.createElement('div');
        toast.style.position = 'fixed';
        toast.style.bottom = '40px';
        toast.style.left = '50%';
        toast.style.transform = 'translateX(-50%)';
        toast.style.backgroundColor = 'rgba(0,0,0,0.7)';
        toast.style.color = 'white';
        toast.style.padding = '10px 20px';
        toast.style.borderRadius = '6px';
        toast.style.fontSize = '15px';
        toast.style.zIndex = '9999';
        toast.style.opacity = '0';
        toast.style.transition = 'opacity 0.3s, transform 0.3s';
        toast.style.transform = 'translateX(-50%) translateY(20px)';
        toast.textContent = message;

        document.body.appendChild(toast);

        setTimeout(() => {
            toast.style.opacity = '1';
            toast.style.transform = 'translateX(-50%) translateY(0)';
        }, 10);

        setTimeout(() => {
            toast.style.opacity = '0';
            toast.style.transform = 'translateX(-50%) translateY(20px)';
            setTimeout(() => {
                document.body.removeChild(toast);
            }, 300);
        }, 2000);
    }

    // 初始化发送按钮
    function initSendButton() {
        sendBtn.addEventListener('click', sendMessage);
        updateSendButtonStatus();
    }

    // 更新发送按钮状态
    function updateSendButtonStatus() {
        const hasText = messageInput.value.trim().length > 0;
        const hasFiles = uploadedFiles.length > 0;

        if (hasText || hasFiles) {
            sendBtn.classList.add('active');
            sendBtn.disabled = false;
        } else {
            sendBtn.classList.remove('active');
            sendBtn.disabled = true;
        }
    }

    // 检查麦克风权限
    function checkMicrophonePermission() {
        if (navigator.permissions && navigator.permissions.query) {
            navigator.permissions.query({ name: 'microphone' })
                .then(permissionStatus => {
                    console.log('麦克风权限状态:', permissionStatus.state);
                    permissionStatus.onchange = () => {
                        console.log('麦克风权限变更:', permissionStatus.state);
                    };
                })
                .catch(err => {
                    console.error('检查麦克风权限失败:', err);
                });
        }
    }

    // 初始化语音识别
    function initVoiceRecognition() {
        const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
        if (!SpeechRecognition) {
            voiceBtn.title = '浏览器不支持语音识别';
            voiceBtn.removeEventListener('mousedown', startVoiceRecording);
            voiceBtn.addEventListener('click', () => {
                showPermissionAlert('你的浏览器不支持语音识别功能，请使用Chrome、Edge等现代浏览器。');
            });
            return;
        }

        recognition = new SpeechRecognition();
        recognition.lang = 'zh-CN';
        recognition.continuous = true;
        recognition.interimResults = true;
        recognition.maxAlternatives = 1;

        recognition.onresult = (e) => {
            if (isRecording && !isVoiceCanceled) {
                let transcript = '';
                for (let i = e.resultIndex; i < e.results.length; i++) {
                    transcript += e.results[i][0].transcript;
                }
                voiceTranscript = transcript;
                updateSendButtonStatus();
            }
        };

        recognition.onstart = () => {
            console.log('语音识别已开始');
            isRecording = true;
            recordingStartTime = Date.now();
            startRecordingTimer();

            voiceStatus.querySelector('.status-text').textContent = '正在录音...';
            voiceStatus.querySelector('.cancel-tip').textContent = '上滑取消';
            isVoiceCanceled = false;
        };

        recognition.onend = () => {
            console.log('语音识别已结束');
            stopRecordingTimer();

            if (!isVoiceCanceled && voiceTranscript.trim()) {
                finishVoiceRecording();
            }

            resetVoiceUI();
        };

        recognition.onerror = (e) => {
            console.error('语音识别错误:', e.error);
            stopRecordingTimer();

            let errorMsg = '语音识别出错，请重试';
            switch(e.error) {
                case 'no-speech':
                    errorMsg = '未检测到语音，请重新录制';
                    break;
                case 'audio-capture':
                    errorMsg = '无法访问麦克风，请检查权限设置';
                    showPermissionAlert(errorMsg);
                    break;
                case 'not-allowed':
                    errorMsg = '麦克风权限被拒绝，请允许浏览器访问麦克风';
                    showPermissionAlert(errorMsg);
                    break;
                case 'aborted':
                    errorMsg = '录音已取消';
                    break;
            }

            if (!isVoiceCanceled) {
                addMessage(`⚠️ ${errorMsg}`, 'assistant');
            }

            resetVoiceUI();
        };
    }

    // 开始语音录制
    function startVoiceRecording(e) {
        if (e.type !== 'touchstart') {
            e.preventDefault();
        }

        if (isRecording) return;

        if (isMenuOpen) {
            toggleMenu();
        }

        isHoldingVoiceBtn = true;
        isVoiceCanceled = false;
        voiceBtn.classList.add('holding');

        if (!recognition) {
            initVoiceRecognition();
            if (!recognition) return;
        }

        voiceTranscript = '';
        messageInput.value = '';
        recordingDuration = 0;
        voiceDuration.textContent = '00:00';

        voiceStatus.classList.add('recording');

        // 清空输入后更新按钮显示
        toggleButtonDisplay();

        if (e.type === 'touchstart') {
            touchStartY = e.touches[0].clientY;
        }

        try {
            recognition.start();
        } catch (err) {
            console.error('启动录音失败:', err);
            showPermissionAlert('无法启动录音，请检查麦克风权限');
            resetVoiceUI();
        }
    }

    // 停止语音录制
    function stopVoiceRecording(e) {
        if (!isHoldingVoiceBtn) return;

        if (isVoiceCanceled) {
            voiceTranscript = '';
            if (recognition && isRecording) {
                recognition.abort();
            }
            addMessage('✖️ 语音消息已取消', 'assistant');
        } else if (isRecording && recognition) {
            recognition.stop();
        }

        resetVoiceUI();
    }

    // 处理鼠标移动
    function handleMouseMove(e) {
        if (!isHoldingVoiceBtn || !isRecording) return;

        const mouseY = e.clientY;
        const btnRect = voiceBtn.getBoundingClientRect();
        const btnCenterY = btnRect.top + btnRect.height / 2;

        if (mouseY < btnCenterY - CANCEL_THRESHOLD) {
            isVoiceCanceled = true;
            voiceStatus.querySelector('.status-text').textContent = '松开取消';
            voiceStatus.querySelector('.cancel-tip').textContent = '松开手指取消发送';
        } else {
            isVoiceCanceled = false;
            voiceStatus.querySelector('.status-text').textContent = '正在录音...';
            voiceStatus.querySelector('.cancel-tip').textContent = '上滑取消';
        }
    }

    // 处理触摸开始
    function handleTouchStart(e) {
        startVoiceRecording(e);
    }

    // 处理触摸结束
    function handleTouchEnd(e) {
        stopVoiceRecording(e);
    }

    // 处理触摸移动
    function handleTouchMove(e) {
        if (!isHoldingVoiceBtn || !isRecording) return;

        e.preventDefault();

        const touchY = e.touches[0].clientY;
        const deltaY = touchStartY - touchY;

        if (deltaY > CANCEL_THRESHOLD) {
            isVoiceCanceled = true;
            voiceStatus.querySelector('.status-text').textContent = '松开取消';
            voiceStatus.querySelector('.cancel-tip').textContent = '松开手指取消发送';
        } else {
            isVoiceCanceled = false;
            voiceStatus.querySelector('.status-text').textContent = '正在录音...';
            voiceStatus.querySelector('.cancel-tip').textContent = '上滑取消';
        }
    }

    // 完成语音录制并发送
    function finishVoiceRecording() {
        if (voiceTranscript.trim()) {
            const durationText = voiceDuration.textContent;

            addVoiceMessage(voiceTranscript, durationText);

            messageInput.value = '';
            voiceTranscript = '';

            // 清空输入后更新按钮显示
            toggleButtonDisplay();
            updateSendButtonStatus();
        } else {
            addMessage('⚠️ 未检测到语音内容', 'assistant');
        }
    }

    // 开始录音计时器
    function startRecordingTimer() {
        stopRecordingTimer();

        recordingTimer = setInterval(() => {
            if (isRecording && !isVoiceCanceled) {
                recordingDuration = Math.floor((Date.now() - recordingStartTime) / 1000);
                const minutes = Math.floor(recordingDuration / 60).toString().padStart(2, '0');
                const seconds = (recordingDuration % 60).toString().padStart(2, '0');
                voiceDuration.textContent = `${minutes}:${seconds}`;
            }
        }, 1000);
    }

    // 停止录音计时器
    function stopRecordingTimer() {
        if (recordingTimer) {
            clearInterval(recordingTimer);
            recordingTimer = null;
        }
    }

    // 重置语音UI状态
    function resetVoiceUI() {
        isRecording = false;
        isHoldingVoiceBtn = false;

        voiceBtn.classList.remove('holding');

        voiceStatus.classList.remove('recording');

        stopRecordingTimer();
        recordingDuration = 0;
        voiceDuration.textContent = '00:00';
    }

    // 开关面板
    function toggleMenu() {
        isMenuOpen = !isMenuOpen;

        if (isMenuOpen) {
            bottomMenu.classList.remove('hidden');
            chatContainer.classList.add('expanded');
            toggleBtn.innerHTML = '<span class="icon">✕</span>';
        } else {
            bottomMenu.classList.add('hidden');
            chatContainer.classList.remove('expanded');
            toggleBtn.innerHTML = '<span class="icon">➕</span>';
        }

        adjustChatContainerHeight();
    }

    // 处理文件上传
    function handleFileUpload(files, type) {
        if (!files || files.length === 0) return;

        uploadPreview.style.display = 'flex';


        Array.from(files).forEach(file => {
            const fileId = Date.now() + '-' + Math.random().toString(36).substr(2, 9);
            const fileObj = {
                id: fileId,
                file: file,
                type: type,
                url: URL.createObjectURL(file),
                name: file.name,
                size: file.size
            };

            uploadedFiles.push(fileObj);

            const previewItem = document.createElement('div');
            previewItem.className = 'preview-item';
            previewItem.dataset.id = fileId;
            previewItem.title = file.name;

            if (type === 'image') {
                const img = document.createElement('img');
                img.className = 'preview-img';
               img.src = fileObj.url;
                img.alt = file.name;
                img.onerror = function() {
                    this.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iODAiIGhlaWdodD0iODAiIHZpZXdCb3g9IjAgMCA4MCA4MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGNpcmNsZSBjeD0iNDAiIGN5PSI0MCIgcj0iNDAiIGZpbGw9IiNGRkY5OTAiLz4KPHBhdGggZD0iTTQwIDI1QzQ3LjcxNDIgMjUgNTQgMzEuMjg1OCA1NCA0MEM1NCA0OC43MTQyIDQ3LjcxNDIgNTUgNDAgNTVDMzIuMjg1OCA1NSAyNiA0OC43MTQyIDI2IDQwQzI2IDMxLjI4NTggMzIuMjg1OCAyNSA0MCAyNVoiIGZpbGw9IiM2NjYiLz4KPHBhdGggZD0iTTIwIDYwQzE1LjU4MTcgNjAgMTIgNTYuNDE4MyAxMiA1MkMxMiA0Ny41ODE3IDE1LjU4MTcgNDQgMjAgNDRINDRINVZINDBDNDUgMzUuNTgxNyA0OC41ODE3IDMyIDUzIDMySDY4QzczLjQxODMgMzIgNzcgMzUuNTgxNyA3NyA0MEM3NyA0NC40MTgzIDczLjQxODMgNDggNjggNDhINVZINDhINVY0OEg1M0g0NEg0MFY0OEgyMEMxNS41ODE3IDQ4IDEyIDUxLjU4MTcgMTIgNTZDMTEuOTk5OCA1Ny4wNjA5IDEyLjE4OTEgNTguMDkwOCAxMi41MzUyIDU4Ljk3NUMxMi44ODEgNTkuODYwOCAxMy4zNzg3IDYwLjYxMjQgMTQgNjFIMjhIMjhIMjhIMjBIMjBaIiBmaWxsPSIjNjY2Ii8+CjxwYXRoIGQ9Ik00OCA0OEg1M0g0OFY0OEg0OFoiIGZpbGw9IiM2NjYiLz4KPC9zdmc+';
                };

                // 新增：点击图片预览放大
                img.addEventListener('click', function(e) {
                    e.stopPropagation();
                    openImageViewer(fileObj.url);
                });

                previewItem.appendChild(img);

                // 新增：预览项点击事件（防止删除按钮点击触发）
                previewItem.addEventListener('click', function(e) {
                    if (e.target === previewItem || e.target === img) {
                        openImageViewer(fileObj.url);
                    }
                });
            } else {
                const fileDiv = document.createElement('div');
                fileDiv.className = 'preview-file';
                const shortName = file.name.length > 10
                    ? file.name.substring(0, 10) + '...'
                    : file.name;
                fileDiv.innerHTML = `<span class="icon">📄</span><br><small>${shortName}</small>`;
                previewItem.appendChild(fileDiv);
            }

            const closeBtn = document.createElement('div');
            closeBtn.className = 'preview-close';
            closeBtn.innerHTML = '✕';
            closeBtn.title = '删除该文件';
            closeBtn.addEventListener('click', (e) => {
                e.stopPropagation();
                removePreviewItem(fileId);
                // 删除文件后更新按钮显示
                toggleButtonDisplay();
                updateSendButtonStatus();

            });
            previewItem.appendChild(closeBtn);



            const loading = document.createElement('div');
            loading.className = 'loading-spinner';
            loading.innerHTML = '';
            loading.title = '上传';

            previewItem.appendChild(loading);

           // uploadPreview.appendChild(previewItem);
            addFile.before(previewItem);
            formateFile = type;
            uploadServer(file,fileId);
        });

        // 上传文件后更新按钮显示
        toggleButtonDisplay();
        voiceBtn1.style.display = 'flex';
        updateSendButtonStatus();

        isMenuOpen = true;
        toggleMenu();
        addFile.style.display = 'flex';

    }

    function uploadServer(file,fileId){

        const formData = new FormData();
        formData.append("file", file); // 对应后端@RequestParam("imageFile")
        formData.append("fileFileName", file.name); // 可选参数
        var url = basePath + "ea/ai/sajax_ea_updateFile.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            beforeSend: function() {


            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");

                  var  filepath = member.filepath;
                const spinner = document.querySelector(".preview-item[data-id='"+fileId+"'] .loading-spinner");
                if (spinner) {
                    spinner.remove();
                }
                const  previewimg = document.querySelector(".preview-item[data-id='"+fileId+"'] img");




//                const fileToRemove = uploadedFiles.find(file => file.id === fileId);
//                URL.revokeObjectURL(fileToRemove.url);

              try{
                  previewimg.src =basePath+filepath;

                  URL.revokeObjectURL(previewimg.src);
              }catch(error){

              }



                const targetFileObj = uploadedFiles.find(item => item.id === fileId);
                if (targetFileObj) {
                    targetFileObj.url = basePath+filepath; // 同步更新数组中的 url
                    console.log("数组已同步更新：", targetFileObj);
                }

            },
            error: function () {

            }
        });

    }
    // 移除预览项
    function removePreviewItem(fileId) {
        console.log('原始fileId:', ${fileId}); // 打印原始值
        const fileToRemove = uploadedFiles.find(file => file.id === fileId);

        if (fileToRemove) {
            URL.revokeObjectURL(fileToRemove.url);
            uploadedFiles = uploadedFiles.filter(file => file.id !== fileId);



            const previewItem = document.querySelector(".preview-item[data-id='"+fileId+"']");
            if (previewItem) {
                previewItem.remove();
            }

            if (uploadedFiles.length === 0) {
                uploadPreview.style.display = 'none';
                isMenuOpen = true;
                toggleMenu();
            }
        }
    }

    // 添加语音消息
    function addVoiceMessage(text, duration) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'message user-message';

        const contentDiv = document.createElement('div');
        contentDiv.className = 'voice-message';

        const playBtn = document.createElement('div');
        playBtn.className = 'voice-play-btn';
        playBtn.innerHTML = '<span class="icon">▶️</span>';
        playBtn.addEventListener('click', function() {
            if ('speechSynthesis' in window) {
                const utterance = new SpeechSynthesisUtterance(text);
                utterance.lang = 'zh-CN';

                if (playBtn.querySelector('.icon').textContent === '▶️') {
                    window.speechSynthesis.speak(utterance);
                    playBtn.querySelector('.icon').textContent = '⏸️';
                } else {
                    window.speechSynthesis.pause();
                    playBtn.querySelector('.icon').textContent = '▶️';
                }
            } else {
                alert('你的浏览器不支持语音播放功能');
            }
        });

        const textSpan = document.createElement('span');
        textSpan.textContent = text;

        const durationSpan = document.createElement('span');
        durationSpan.className = 'voice-duration';
        durationSpan.textContent = duration;

        contentDiv.appendChild(playBtn);
        contentDiv.appendChild(textSpan);
        contentDiv.appendChild(durationSpan);
        messageDiv.appendChild(contentDiv);

        chatContainer.appendChild(messageDiv);
        chatContainer.scrollTop = chatContainer.scrollHeight;

        setTimeout(() => {
            chatContainer.scrollTop = chatContainer.scrollHeight;
        }, 100);

        setTimeout(() => {
            let reply = '';
            switch(currentFunctionType) {
                case 'chat':
                    reply = `你发送了语音消息：<br/>"${text}"<br/><br/>这是模拟回复，实际项目中会对接后端接口返回真实回答。`;
                    break;
                case 'image':
                    reply = `✅ 图像生成请求已接收：<br/>"${text}"<br/><br/>正在生成高清图像...`;
                    break;
                case 'translate':
                    reply = `🌐 翻译结果：<br/>原文：${text}<br/>译文：【模拟翻译】${text.split(' ').reverse().join(' ')}`;
                    break;
                case 'video':
                    reply = `🎬 视频生成请求已接收：<br/>"${text}"<br/><br/>正在生成视频...`;
                    break;
                case 'code':
                    // 修复：完整的代码生成回复文本
                    reply = `💻 代码生成结果：<br/><pre style="background:#f5f5f5;padding:8px;border-radius:4px;">console.log("${text}");</pre>`;
                    break;
                case 'doc':
                    reply = `📄 文档处理请求已接收：<br/>"${text}"<br/><br/>正在解析处理...`;
                    break;
            }
            addMessage(reply, 'assistant');
        }, 800);
    }

    // 发送文字/文件消息
    function sendMessage() {
        const text = messageInput.value.trim();

        if (!text && uploadedFiles.length === 0) return;

        let userContent = "";
        var  imageUrlList = new Array();
        if (uploadedFiles.length > 0) {

            uploadedFiles.forEach(file => {
                let imgurl = file.url;
                let type = file.type;
                try {

                    imageUrlList.push(imgurl);
                    var escapedImgUrl = imgurl ? imgurl.replace(/['"]/g, function (match) {
                        return match === "'" ? "\\'" : '\\"';
                    }) : '';

                    if(type=="image"){
                        userContent += '<div><img onclick="openImageViewer(\'' + escapedImgUrl + '\')" src =\'' + escapedImgUrl + '\' class=\'img-user\'></div>';

                    }else{
                        userContent += '<div class="preview-file"><span class="icon">📄</span><br><small></small></div>';



                    }

                } catch (e) {
                }
            });
            addMessage(userContent, 'user',true,"");


        }



        //T图片

        if(text!=""){
            addMessage(text, 'user',false,"");

        }




//        // 拼接 Struts2 接口地址（GET 方式传参）
//        const sseUrl = basePath+"ea/ai/stream?doubaoParam.text="+ encodeURIComponent(text);
//        const eventSource = new EventSource(sseUrl);
//
//        // 接收流式消息
//        eventSource.onmessage = function(e) {
//            // 监听结束标记
//            if (e.data === '[DONE]') {
//                eventSource.close();
//                return;
//            }
//            // 实时追加内容
////            responseArea.innerHTML += e.data;
//            Console.log(e.data);
//
//        }

 let reply = '';
        var url = basePath + "ea/ai/sajax_ea_routeMode.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            data: {
               type:currentFunctionType,
                "doubaoParam.text":text,
                "doubaoParam.flist":JSON.stringify(imageUrlList),
                "doubaoParam.formateFile":formateFile
            },
            beforeSend: function() {
                // 请求中加载状态
                let tip = "";
                if(currentFunctionType=="chat") {
                    tip = "正在思考中......";
                }else if(currentFunctionType=="image") {
                    tip = "正在生成图片......";
                }else if(currentFunctionType=="video"){
                    tip = "正在生成视频......";

                }else if(currentFunctionType=="translate"){
                    tip = "正在思考中......";

                }
                addMessage(tip, 'assistant',false,"");

            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");



                var message = member.message;
                uploadPreview.innerHTML = '';
                switch(currentFunctionType) {
                    case 'chat':
                  //      var reasoning = member.reasoning;

                    //    addMessage(reasoning, 'assistant');
                        addMessage(message, 'assistant',false,"");
                  if(isVoice) {
                      if (isPlaying) {
                          // 🔥 正在播放 → 点击停止
                          stopAll(() => {
                              autoPlay(message);
                          });
                      } else {
                          autoPlay(message);
                      }
                  }

                        break;
                    case 'image':

                       //  reply = `✅ 图像生成请求已接收：<br/>"${text}"<br/><br/>正在生成高清图像...`;

                        var escapedImgUrl = message ? message.replace(/['"]/g, function(match) {
                        return match === "'" ? "\\'" : '\\"';
                    }) : '';

                        var reply = '<img onclick="openImageViewer(\'' + escapedImgUrl + '\')" src="' + escapedImgUrl + '" style="width: 10rem;" />';
                        addMessage(reply, 'assistant',true,"")
                        break;
                    case 'video':
                        var escapedVideoUrl = message ? message.replace(/['"]/g, function(match) {
                            return match === "'" ? "\\'" : '\\"';
                        }) : '';


                        var reply = '<video style="width:100%;"class="modal-video" id="modalVideo" controls preload="metadata"><source src="' + escapedVideoUrl + '" type="video/mp4"></video>';
                        addMessage(reply, 'assistant',true,"")
                       // reply = `🎬 视频生成请求已接收：<br/>"${text}"<br/><br/>正在生成视频...`;
                        break;
                    case 'translate':


                       // reply = `🌐 翻译结果：<br/>原文：${text}<br/>译文：【模拟翻译】${text.split(' ').reverse().join(' ')}`;
                        addMessage(message, 'assistant',false,"");
                        break;

                    case 'code':
                        // 修复：完整的代码生成回复文本
                        reply = `💻 代码生成结果：<br/><pre style="background:#f5f5f5;padding:8px;border-radius:4px;">console.log("${text}");</pre>`;
                        break;
                    case 'doc':
                        reply = `📄 文档处理请求已接收：<br/>"${text}"<br/><br/>正在解析处理...`;
                        break;
                }

            },
            error: function () {

            }
        });

        messageInput.value = '';

        uploadedFiles.forEach(file => {
            try {
                URL.revokeObjectURL(file.url);
            } catch (e) {
                console.log('释放URL失败:', e);
            }
        });
        uploadedFiles = [];
        uploadPreview.style.display = 'none';
        const previewItems = uploadPreview.querySelectorAll('.preview-item');

        // 3. 遍历每个 preview-item 元素
        previewItems.forEach(item => {
            // 4. 判断：如果元素不包含 add-item 类，则删除；包含则保留
            if (!item.classList.contains('add-item')) {
                item.remove(); // 从 DOM 中移除该元素
            }
        });
        // 发送后更新按钮显示
        toggleButtonDisplay();
        updateSendButtonStatus();
    }

    // 添加通用消息
    function addMessage(text, type,isfile,id) {
        const div = document.createElement('div');
        const content = document.createElement('div');
        if(id==""){//说明是新增的
            addSessoin(text,type,isfile,div);

        }else{
            div.dataset.id = id;
        }




        if (type === 'user') {
            div.className = 'message user-message';

            if(isfile){
                if (uploadedFiles.length <=4) {
                    content.className = 'user-content-file user-content-file-length';
                }else{
                    content.className = 'user-content-file';
                }

            }else{
                content.className = 'user-content';

            }
        } else {
            div.className = 'message assistant-message';
            content.className = 'assistant-content';
        }
        content.innerHTML = text;
        div.appendChild(content);

        chatContainer.appendChild(div);
        chatContainer.scrollTop = chatContainer.scrollHeight;

        setTimeout(() => {
            chatContainer.scrollTop = chatContainer.scrollHeight;
        }, 100);

    }


    function addSessoin(text, type,isfile,div){
        const formData = new FormData();
        formData.append("doubaoSession.isfile", isfile);
        formData.append("doubaoSession.type", type);
        formData.append("doubaoSession.text", text);

        var url = basePath + "ea/ai/sajax_ea_addSession.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var id = member.id;
                div.dataset.id = id;

            },
            error: function () {

            }
        });


    }

    // 页面卸载时清理资源
    window.addEventListener('beforeunload', () => {
        if (recognition && isRecording) {
            recognition.stop();
        }

        uploadedFiles.forEach(file => {
            try {
                URL.revokeObjectURL(file.url);
            } catch (e) {}
        });

        if ('speechSynthesis' in window) {
            window.speechSynthesis.cancel();
        }
    });



    window.onload = init;
</script>



<script>
    var  basePath = "<%=basePath%>";
    $(function() {
        // 核心变量
        let isHolding = false;
        let startY = 0;
        const cancelDistance = 30;
        let isCanceled = false;
        let localId = ''; // 微信语音本地ID

        // ========== 第一步：微信JSSDK鉴权（必须后端配合） ==========
        // 替换为你的后端接口，获取签名参数
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (isWeixin) {
            $(".art_bR").hide();

            var url = basePath
                + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";

            var retUrl = location.href.split('#')[0];
            $.ajax({
                url : url,
                type : "post",
                async : true,
                dataType : "json",
                data:{
                    retUrl:retUrl
                },
                success : function(data) {
                    var m = eval("("+data+")");

                    wx.config({
                        debug: false, // 调试时可开启true，查看错误信息
                        appId: m.appId, // 公众号AppID
                        timestamp: m.timestamp, // 后端生成的时间戳
                        nonceStr: m.nonceStr, // 后端生成的随机串
                        signature: m.signature, // 后端生成的签名
                        jsApiList: ['startRecord', 'stopRecord', 'translateVoice'] // 需调用的接口
                    });

                    // JSSDK初始化成功
                    wx.ready(function() {
                        bindVoiceEvent(); // 绑定语音事件
                    });

                    // JSSDK初始化失败
                    wx.error(function(err) {
                        alert("微信JSSDK初始化失败：" + JSON.stringify(err));
                    });




                }
            });

        }


        // ========== 第二步：绑定语音交互事件 ==========
        function bindVoiceEvent() {
            // 按住说话（touchstart）
            $(document).on('touchstart', '.voice-text', function(e) {
                alert("1111")
                e.preventDefault();
                isHolding = true;
                isCanceled = false;
                $(this).addClass('holding').text('松开结束 | 上移取消');
                startY = e.originalEvent.touches[0].clientY;

                // 启动微信语音录制
                wx.startRecord({
                    fail: function(res) {
                        alert("录制启动失败：" + res.errMsg);
                        isHolding = false;
                        $('#messageInput').removeClass('holding').text('按住说话');
                    }
                });
            });

            // 触摸移动（检测取消）
            $(document).on('touchmove', function(e) {
                if (!isHolding) return;
                const currentY = e.originalEvent.touches[0].clientY;
                const distance = startY - currentY;

                if (distance > cancelDistance) {
                    isCanceled = true;
                    $('#messageInput').text('松开取消');
                    // 取消录制
                    wx.stopRecord();
                }
            });

            // 松开结束（touchend）
            $(document).on('touchend touchcancel', function(e) {
                if (!isHolding) return;
                isHolding = false;

                if (isCanceled) {
                    // 取消识别，清空结果
                    $('#resultBox').val('');
                    $('#messageInput').removeClass('holding').text('已取消');
                    isCanceled = false;
                    setTimeout(() => {
                        $('#messageInput').text('按住说话');
                    }, 1000);
                    return;
                }

                // 停止录制并识别
                wx.stopRecord({
                    success: function(res) {
                        localId = res.localId; // 获取语音本地ID
                        // 调用微信语音转文字接口
                        wx.translateVoice({
                            localId: localId,
                            isShowProgressTips: 1,
                            success: function(res) {
                                const text = res.translateResult; // 识别结果

                                addMessage(text, 'assistant');

                                $('#voiceBtn').removeClass('holding').text('识别完成');
                                setTimeout(() => {
                                    $('#voiceBtn').text('按住说话');
                                }, 1000);
                            },
                            fail: function(res) {
                                alert("语音转文字失败：" + res.errMsg);
                                $('#voiceBtn').removeClass('holding').text('按住说话');
                            }
                        });
                    },
                    fail: function(res) {
                        alert("录制停止失败：" + res.errMsg);
                        $('#voiceBtn').removeClass('holding').text('按住说话');
                    }
                });
            });
        }

        $("#voiceBtn1").click(function(){
            if($(this).attr("class").indexOf("text") !== -1) {//切换语音
                $(this).find("img").attr("src", basePath + "images/WFJClient/pc/newimg/audio.png");
                $(this).removeClass("text");
                $("#messageInput").attr("readonly", true);
                $('#messageInput').text('按住说话');
                $("#messageInput").attr("placeholder", "按住说话");
                $("#messageInput").addClass("voice-text");
            }else{

                $(this).find("img").attr("src",basePath+"images/WFJClient/pc/newimg/keyboard.png");
                $(this).addClass("text");
                $('#messageInput').text('');
                $("#messageInput").attr("placeholder","输入消息......");
                $("#messageInput").attr("readonly",false);
                $("#messageInput").removeClass("voice-text");

            }
        });

        $("#voiceauto").click(function(){
            if($(this).attr("class").indexOf("voice-auto") !== -1) {//自动切换到不自动
                $(this).find("img").attr("src", basePath + "images/WFJClient/pc/newimg/jingyin.png");
                $(this).removeClass("voice-auto");
                isVoice = false;

            }else{

                $(this).find("img").attr("src",basePath+"images/WFJClient/pc/newimg/voice.png");
                $(this).addClass("voice-auto");
                isVoice = true;


            }
        });
    });
</script>


<script>
    const playBtn = document.getElementById('playBtn');
    const textEl = document.getElementById('text');

    let audioContext;
    let isPlaying = false;
    const SAMPLE_RATE = 24000;

    let audioBuffer = new Float32Array(0);
    let playIndex = 0;
    let gainNode, scriptNode, sourceNode, ws;
    let isEnd = false;
    let isUnlocked = false;

    // ==============================================
    // 微信自动播放解锁
    // ==============================================
    function unlockWechatAudio() {
        if (isUnlocked) return;
        document.addEventListener('WeixinJSBridgeReady', () => {
            initAudioContext();
            isUnlocked = true;
            setTimeout(() => autoPlay(), 500);
        }, false);

        document.addEventListener('touchstart', unlockOnce, { once: true });
        document.addEventListener('click', unlockOnce, { once: true });
    }

    function unlockOnce() {
        if (isUnlocked) return;
        initAudioContext();
        isUnlocked = true;
    }

    function initAudioContext() {
        if (audioContext) return;
        audioContext = new (window.AudioContext || window.webkitAudioContext)({
            sampleRate: SAMPLE_RATE
        });
        gainNode = audioContext.createGain();
        gainNode.gain.value = 3.0;
        gainNode.connect(audioContext.destination);
    }

    // ==============================================
    // 自动播放 / 手动播放
    // ==============================================
    function autoPlay(text) {
        if (!isUnlocked) {
            unlockOnce();
        }


        isPlaying = true;
        playBtn.textContent = "停止播放";
        playBtn.disabled = false;

      //  const text = textEl.value.trim();
        if (!text) { resetAll(); return; }

        if (audioContext.state === 'suspended') {
            audioContext.resume();
        }

        audioBuffer = new Float32Array(0);
        playIndex = 0;
        isEnd = false;

        initAudioStream();
        connectWebSocket(text);
    }

    // ==============================================
    // 🔥 停止播放（核心新增）
    // ==============================================
    function stopAll(callback) {
        isPlaying = false;
        playBtn.textContent = "播放语音";

        // 关闭音频
        if (scriptNode) scriptNode.disconnect();
        if (sourceNode) try { sourceNode.stop(); } catch (e) {}
        if (ws) ws.close();

        scriptNode = null;
        sourceNode = null;
        ws = null;

        // 等一小帧保证停止完成
        setTimeout(() => {
            if (callback) callback();
        }, 100);
    }

    // ==============================================
    // 音频流初始化
    // ==============================================
    function initAudioStream() {
        scriptNode = audioContext.createScriptProcessor(2048, 0, 1);
        scriptNode.onaudioprocess = (e) => {
            const out = e.outputBuffer.getChannelData(0);
            for (let i = 0; i < out.length; i++) {
                out[i] = (playIndex < audioBuffer.length) ? audioBuffer[playIndex++] : 0;
            }
        };
        sourceNode = audioContext.createBufferSource();
        sourceNode.connect(scriptNode);
        scriptNode.connect(gainNode);
        sourceNode.start();
    }

    // ==============================================
    // WebSocket
    // ==============================================
    function connectWebSocket(text) {
//        if (ws) ws.close();
        if (ws) {
            ws.onclose = null;
            ws.onerror = null;
            try { ws.close(); } catch (e) {}
            ws = null;
        }
        ws = new WebSocket('ws://192.168.2.192:8080/hyplat/tts');
        ws.onopen = () => ws.send(text);

        ws.onmessage = (evt) => {
            if (!isPlaying) return;
            const msg = evt.data;
            if (msg.startsWith('header:')) return;

            if (msg.startsWith('data:')) {
                const base64 = msg.replace('data:', '');
                const uint8 = base64ToUint8Array(base64);
                const f32 = pcm16ToFloat32(uint8);
                const newBuf = new Float32Array(audioBuffer.length + f32.length);
                newBuf.set(audioBuffer, 0);
                newBuf.set(f32, audioBuffer.length);
                audioBuffer = newBuf;
            }

            if (msg === 'end') {
                isEnd = true;
                checkPlayFinish();
            }
        };

        ws.onerror = resetAll;
        ws.onclose = resetAll;
    }

    // ==============================================
    // 工具函数
    // ==============================================
    function pcm16ToFloat32(bytes) {
        const len = bytes.length >> 1;
        const f32 = new Float32Array(len);
        const view = new DataView(bytes.buffer);
        for (let i = 0; i < len; i++) {
            f32[i] = view.getInt16(i * 2, true) / 32768.0;
        }
        return f32;
    }

    function base64ToUint8Array(base64) {
        const bin = atob(base64);
        const u8 = new Uint8Array(bin.length);
        for (let i = 0; i < bin.length; i++) {
            u8[i] = bin.charCodeAt(i);
        }
        return u8;
    }

    function checkPlayFinish() {
        const timer = setInterval(() => {
            if (isEnd && playIndex >= audioBuffer.length - 100) {
                clearInterval(timer);
                setTimeout(resetAll, 300);
            }
        }, 200);
    }

    function resetAll() {
        isPlaying = false;
        playBtn.textContent = "播放语音";
        playBtn.disabled = false;
        if (scriptNode) scriptNode.disconnect();
        if (sourceNode) try { sourceNode.stop(); } catch (e) {}
        if (ws) ws.close();
    }

    // ==============================================
    // 启动
    // ==============================================
//    window.onload = () => {
//        unlockWechatAudio();
//        playBtn.onclick = () => {
//            if (!isUnlocked) {
//                unlockOnce();
//                setTimeout(autoPlay, 200);
//            } else {
//                autoPlay();
//            }
//        };
//    };
</script>


<script>
    $(function(){

        var url = basePath + "ea/ai/sajax_ea_getListRecord.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            dataType: "json",
            processData: false,
            contentType: false,
            cache: true,
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                for(var i = 0;i<list.length;i++){
                    var obj = list[i];
                    var text = obj.text;
                    var type = obj.type;
                    var isfile = obj.isfile;
                    var id = obj.id;
                    addMessage(text, type,isfile,id);
                }



            },
            error: function () {

            }
        });


    })

</script>



</body>
</html>