<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/21 0021
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String ppID=request.getParameter("ppID");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>生成二维码 </title>
    <style type="text/css">

        input.text{width:50px;height:20px;line-height:20px;text-align:center;}
        #qrcode{text-align:center}
        #qrcode img{
            margin: 200px auto 0 auto;
        }
    </style>
    <script src="<%=path%>/js/jquery-2.0.0.min.js" type="text/javascript" ></script>
    <script src="<%=path%>/js/qrcode.js"></script>

    <script type="text/javascript">
        var basePath='<%=basePath%>';
        window.onload = function(){
            // 二维码对象
            var qrcode;
            // 默认设置
            var content;
            var size;
            // 设置点击事件

                // 获取内容
                content = document.getElementById("content").value;
                //alert(content);
                content = content.replace(/(^\s*)|(\s*$)/g, "");
                // 获取尺寸
                size = document.getElementById("size").value;
                // 检查内容
                if(content==''){

                    dizhi();

                }
                // 检查尺寸
                if(!/^[0-9]*[1-9][0-9]*$/.test(size)){
                    alert('请输入正整数');
                    return false;
                }
                if(size<100 || size>500){
                    alert('尺寸范围在100～500');
                    return false;
                }
                // 清除上一次的二维码
                if(qrcode){
                    qrcode.clear();
                }
                // 创建二维码
                qrcode = new QRCode(document.getElementById("qrcode"), {
                    width : size,//设置宽高
                    height : size
                });
                qrcode.makeCode(document.getElementById("content").value);
            }

        function dizhi() {

    var  ppId=$("#ppID").val();

    $("#content").val(basePath+"ea/restaurant/ea_scancode.jspa?scancode=02"+ppId);





}
    </script>
</head>

<body>
<input type="hidden" id="content" value="" />
<input type="hidden" id="ppID" name="productPackaging.ppID" value="${param.ppID}"/>
<input type="hidden" id="size" value="150"></p>
<!--startprint //便于部分打印时，找到开始点-->
<div id="qrcode"></div>
<p style="text-align: center;">${productPackaging.goodsName}</p>

</body>
</html>