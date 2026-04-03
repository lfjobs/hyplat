<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/20 0020
  Time: 18:25
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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <title> 扫二维码收款 </title>
    <style type="text/css">
        * {
            margin:0;
            padding:0;
        }
        #yj{ color:red; font-size:10px; margin:20px;}
        input.text{width:50px;height:20px;line-height:20px;text-align:center;}
        #qrcode{text-align:center}
        #qrcode img{
            margin: 50px auto 0 auto;
        }
    </style>
    <script src="<%=path%>/js/qrcode.js"></script>
    <script type="text/javascript" src="<%=path%>/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript">
        var basePath="<%=basePath%>";

        window.onload = function() {
            var qz = $("#yj").val();
            var s = $("#yj").val(parseInt(qz * 10000));
            var qrcode;
            var content;
            var size;
            var yj;

            if (qz != null) {
                dizhi();
                content = document.getElementById("content").value;
                size = document.getElementById("size").value;
                qrcode = new QRCode(document.getElementById("qrcode"), {
                    width: size,//设置宽高
                    height: size
                });
                qrcode.makeCode(document.getElementById("content").value);
                $("#sm").hide();
            }
            // 设置点击事件
            document.getElementById("send").onclick = function () {
                // 获取内容
                content = document.getElementById("content").value;
                content = content.replace(/(^\s*)|(\s*$)/g, "");
                size = document.getElementById("size").value;
                yj = document.getElementById("yj").value;

                // 检查佣金
                if (!/^([1-9]\d*|[0]{1,1})$/.test(yj)) {
                    alert('请输入正整数');
                    return false;
                }
                if (yj < 0 || yj > 50) {
                    alert('输入错误--佣金的范围在1～50');
                    window.location.reload();
                    return false;
                }
                if (yj != "") {
                    yongjin();
                }
                // 清除上一次的二维码
                if (qrcode) {
                    qrcode.clear();
                }
                // 创建二维码
                qrcode = new QRCode(document.getElementById("qrcode"), {
                    render  : "canvas",
                    width: size,//设置宽高
                    height: size
                });
                qrcode.makeCode(document.getElementById("content").value);
                $("#sm").hide();

            }


            function yongjin() {

                var data = $("#form1").serialize();
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: basePath + "ea/productslaunch/sajax_ea_productserweima.jspa",
                    data: data,
                    async: false,
                    success: function (result) {
                        var member = eval("(" + result + ")");
                        $("#sccId").val(member.sccId);
                        $("#goodsId").val(member.goodsId);
                        $("#ppId").val(member.ppId);
                        $("#content").val(basePath + "ea/restaurant/ea_scancode.jspa?scancode=01" + member.ppId+"&tj="+member.sccId);
                    }
                });
            }
        }
        function dizhi() {

            var ppId = $("#ppId").val();
            var goodsId=$("#goodsId").val();
            var sccId=$("#sccId").val();
            $("#content").val(basePath + "ea/restaurant/ea_scancode.jspa?scancode=01" + ppId+"&tj="+sccId);
        }

        function stamp(){

            $("#yongjin").hide();
            $("#yica").hide();
            $("#sm").show();
            window.print();

        }

    </script>


</head>
<body>


<form action="" id="form1" style="text-align: center;">


    <div  id="qrcode" >

    </div>

    <!--startprint //便于部分打印时，找到开始点-->
    <div id="printDivID">
    <img src="" id="image" style="display: none;"/>
    </div>
    <!--endprint //便于部分打印时，找到结束点-->
<input type="hidden" id="companyId" name="companyId" value="${companyId}"/>
<input type="hidden" id="goodsId" name="goodsManage.goodsID" value="${productPackaging.goodsID}"/>
<input type="hidden" id="ppId" name="productPackaging.ppID" value="${productPackaging.ppID}"/>
    <input type="hidden" id="sccId" name="tcc.sccId" value="${tcc.sccId}"/>
<input type="hidden" id="content" value="" />
<input type="hidden" id="size" value="150">
<div id="yongjin">业务佣金:<input  type="text"id="yj"  name="setup.brokerage" class="text"  value="${setup.brokerage}"/>%</div>

<p id="yica"><button onclick="stamp()">打印二维码</button>&nbsp; &nbsp; &nbsp; <button id="send" >生成二维码</button></p>
    <div style="margin-top: 50px"><p id="sm">扫码收款</p></div>


</form>

</body>
</html>
