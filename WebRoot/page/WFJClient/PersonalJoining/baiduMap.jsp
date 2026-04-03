<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>地图展示</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <style>
        body,
        html,
        #container {
            overflow: hidden;
            width: 100%;
            height: 100%;
            margin: 0;
            font-family: "微软雅黑";
        }
        .info {
            z-index: 999;
            width: auto;
            min-width: 22rem;
            padding: .75rem 1.25rem;
            margin-left: 1.25rem;
            position: fixed;
            top: 1rem;
            background-color: #fff;
            border-radius: .25rem;
            font-size: 14px;
            color: #666;
            box-shadow: 0 2px 6px 0 rgba(27, 142, 236, 0.5);
        }
    </style>
    <script src="//api.map.baidu.com/api?type=webgl&v=1.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>
</head>
<body>
<%--<div class = "info">最新版GL地图命名空间为BMapGL, 可按住鼠标右键控制地图旋转、修改倾斜角度。</div>--%>
<div id="container"></div>
</body>
</html>
<script>
    var map = new BMapGL.Map('container'); // 创建Map实例
    map.centerAndZoom(new BMapGL.Point(116.404, 39.915), 12); // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
</script>
