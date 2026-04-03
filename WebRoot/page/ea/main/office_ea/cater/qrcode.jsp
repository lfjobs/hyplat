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
    String boardNo=request.getParameter("boardNo");
    String sccId = request.getParameter("sccId");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>生成二维码 </title>
    <script src="<%=basePath%>/js/jquery-2.0.0.min.js" type="text/javascript" ></script>
    <script src="<%=basePath%>/js/qrcode.js">

    </script>
    <link rel="stylesheet" href="<%=basePath%>/css/css.css" />
    <script type="text/javascript">
        var basePath='<%=basePath%>';
        window.onload = function(){
            // 二维码对象
            var qrcode;
            // 默认设置
            var content;
            var size;
            // 设置点击事件
           /* document.getElementById("send").onclick =function(){*/
                // 获取内容
                content = document.getElementById("content").value;
                //alert(content);
                content = content.replace(/(^\s*)|(\s*$)/g, "");
                // 获取尺寸
                size = document.getElementById("size").value;
                // 检查内容
                if(content==''){
                    //alert('请输入内容！');
                    dizhi();
                    //return false;
                }
                // 检查尺寸

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
            var ppIDs = $("#ppID").val();
            var boaNo = $("#boaNo").val();
            var sccIDs = $("#sccID").val();
            $("#content").val(basePath+"ea/restaurant/ea_scancode.jspa?scancode=03"+ppIDs+"&canzuo="+boaNo+"&tj="+sccIDs);

}
    </script>

</head>

<body>
<input type="hidden" id="content" value="" />
<input type="hidden" id="ppID" value="${param.ppID}"/>
<input type="hidden" id="boaNo" value="${param.boardNo}"/>
<input type="hidden" id="sccID" value="${param.sccid}"/>
<input type="hidden" id="size" value="150"></p>
<div id="qrcode" style="width:151px;height: 151px ;margin: 250px auto 0px auto" ></div>
<p style="display: block; margin:20px auto; width: 150px;text-align:center;" >餐桌编号：<span id="boardNo" style="font-size:16px;color: red">${param.boardNo}</span></p>
</body>
</html>