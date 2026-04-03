<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误处理</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/error_list.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/bounced.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var basePath="<%=basePath%>";
var  ppageNumber = "${pageNumber}";
var status = '${cm.status}';
var carmID = "";
var model = "";

</script>
</head>
<body>
	<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
	异常处理
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="150" align="center">编号</th>
					<th width="80" align="center">车牌号</th>
					<th width="200" align="center">离开时间</th>
					<th width="200" align="center">场地编号</th>
					<th width="100" align="center">场地名称</th>
					<th width="150" align="center">设备编号</th>
					<th width="100" align="center">场地负责人</th>
					<th width="40" align="center">状态</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list" var="c">
					<tr id="${c[0]}" class="">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="${carmID}" /></td>

						<td><%=number%></td>
						<td><span id="carmNumber">${c[8]}</span></td>
						<td><span id="carNumber">${c[1]}</span></td>
						<td><span id="outdate">${c[9]}</span></td>
						<td><span id="siteNumber">${c[3]}</span></td>
                        <td><span id="sitename">${c[4]}</span></td>
                        <td><span id="equipmentNumber">${c[5]}</span></td>
                        <td><span id="staffname">${c[6]}</span></td>
						<td><span id="status">异常</span></td>
						<input type="hidden" value="${c[10] }" class="model">
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/carmanage/ea_getList.jspa?pageNumber=${pageNumber}&cm.carNumber=${cm.carNumber}&cm.status=${cm.status}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!--弹框-->
<div class="fees_" id="fees_" style="display: none;">
    <div class="fees">
        <h4 class="tit">车辆匹配<img src="<%=basePath%>/images/ico-delete.png" alt="" onclick="del()"></h4>
        <div class="con">
            <div class="mil">
                <h3><i></i>异常车辆</h3>
                <button id="revise">修改车牌</button>
                <button id="matching">查询匹配</button>
                <div class="tab">
                    <ul class="title">
                        <li class="order">序号</li>
                        <li class="number">编号</li>
                        <li class="license">车牌号</li>
                        <li class="departure">离场时间</li>
                        <li class="site_nub">场地编号</li>
                        <li class="site_name">场地名称</li>
                        <li class="site_nub">设备编号</li>
                        <li class="device_id">场地负责人</li>
                        <li class="state">状态</li>
                    </ul>
                    <div class="tab_con" id="error">
                        <ul class="">
                            <li class="order">1</li>
                            <li class="number"></li>
                            <li class="license"><div class=""><input type="text" class="carNumber" value="" readonly="true"></div></li>
                            <li class="departure"></li>
                            <li class="site_nub"></li>
                            <li class="site_name"></li>
                            <li class="site_nub"></li>
                            <li class="device_id"></li>
                            <li class="state">异常</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="mil mate matching">
                <h3><i></i>匹配车辆</h3>

                <!--无数据-->
                <div class="wu">
                    <img src="<%=basePath%>/images/wu.png">
                    <p>暂无数据，请修改车牌号匹配</p>
                </div>

                <!--有数据-->
                <div class="tab" style="display: none;">
                    <ul class="title">
                        <li class="xuan"></li>
                        <li class="order">序号</li>
                        <li class="number">编号</li>
                        <li class="license">车牌号</li>
                        <li class="departure">进场时间</li>
                        <li class="site_nub">场地编号</li>
                        <li class="site_name">场地名称</li>
                        <li class="site_nub">设备编号</li>
                        <li class="device_id">场地负责人</li>
                        <li class="img">图片</li>
                    </ul>
                    <div class="tab_con">
                        <!-- js拼接 -->
                    </div>
                </div>
            </div>
        </div>
        <button class="Choose">确认选择</button>
    </div>

    <div class="img_alert_">
        <div class="img_alert">
            <img src="<%=basePath%>/images/ico-delete.png" class="close">
            <div class="mil abnormal">
                <h3><i></i>异常车辆</h3>
                <ul class="img_con">
                    <li class="panorama">
                        <img src="">
                        <h5>全景图</h5>
                    </li>
                    <li class="plate picture">
                        <img src="">
                        <h5>车牌图</h5>
                    </li>
                </ul>
            </div>
            <div class="mil mate">
                <h3><i></i>匹配车辆</h3>
                <ul class="img_con">
                    <li class="panorama">
                        <img src="">
                        <h5>全景图</h5>
                    </li>
                    <li class="plate picture">
                        <img src="">
                        <h5>车牌图</h5>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript">
        function dian(){
            document.getElementById('fees_').style.display='block';
        }

        function del(){
            document.getElementById('fees_').style.display='none';
        }

        /*修改按钮*/
        $("#revise").live("click",function(){
            var t=$(".fees .con .mil .tab ul .license input").val();
            $(".fees .con .mil .tab ul .license input").focus().val(t);
            $(".fees .con .mil .tab ul .license div").addClass("circle");
            $('.fees .con .mil .tab ul .license input').attr("readonly",false);
        });
         /*选中匹配车型*/
        $(".fees .con .mate .tab ul").live("click",function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        /*匹配车型弹框*/
        $(".fees .con .mate .tab ul .img img").live("click",function(){
            $(".img_alert_").show();
        });
        $(".img_alert .close").live("click",function(){
            $(".img_alert_").hide();
        });
        $(".img_alert_").live("click",function(){
            $(".img_alert_").hide();
        }); 
		
</script>	
		
</body>
</html>