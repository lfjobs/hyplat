<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="hy.ea.bo.company.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd"); 

java.util.Date currentTime = new java.util.Date();//得到当前系统时间 

String str_date1 = formatter.format(currentTime); //将日期时间格式化 


String str_date2 = currentTime.toString(); //将Date型日期时间转换成字符串形式 
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>bigPrintPage.jsp</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

  </head>
  <style type="text/css">
body {
	background-color: #999999;
	font-family: "新宋体";
	font-style: normal;
	height: 134px;
}

.biaoti {
	text-align: center;
	font-size: 12px;
	font-weight: bold;
}

.zhengmian {
	background-image: url('<%=basePath%>images/ea/main/bigzhengmian.jpg');
	background-position: left;
	width: 276px;
	height: 378px;
}
.fanmian {
	background-image:url('<%=basePath%>images/ea/main/bigfanmian.jpg');
	background-position: left;
	width: 276px;
	height: 378px;
	margin-left: 50px;
}
.waitable {
	margin-left: 20%;
}
.ziti {
	font-size: 12px;
	width: 250px;
	padding-left:25px;
}
.STYLE1 {
	font-size: 12px;
	font-weight: bold;
	margin-top: 15px;
}
.STYLE2 {
	font-size: 24px;
	font-weight: bold;
}
.STYLE3 {
	font-size: 12px;
	font-weight: bold;
	margin-left: 10px;
}
</style>
 <body>

		<OBJECT id="wb" height="0" width="0"
			classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb"></OBJECT>
		<%
			List list = (List) request.getAttribute("beans");
		%>
		<div>
		<%
			int left = 0;
			for (int i = 0; i < list.size(); i++) {
				PrintInformation bean = (PrintInformation) list.get(i);
		%>
		<table width="200" border="0" class="waitable" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div class="zhengmian" id="zhengmiang">
						<table height="364px" width="276px" cellpadding="0"
							cellspacing="0">
							<tr>
								<td colspan="2"><div align="center" class="STYLE1"><%=bean.getCredentialsTitle()%></div>
								</td>
							</tr>
							<tr>
								<td colspan="2"><div align="center" class="STYLE2"><%=bean.getCredentialsName()%></div>
								</td>
							</tr>
							<tr>
								<td colspan="2" height="135" ><div align="center">
										<img src="<%=basePath + bean.getPhoto()%>" width="100"
											height="134" />
									</div>
								</td>
							</tr>
							<tr>
								<td ><span class="STYLE3">姓&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;</span>
								</td>
								<td style="border-bottom: 1px solid #231815"><span
									class="STYLE3"><%=bean.getStaffName()%></span>
								</td>
							</tr>
							<tr>
								<td><span class="STYLE3">证件号码&nbsp;&nbsp;</span>
								</td>
								<td style="border-bottom: 1px solid #231815"><span
									class="STYLE3"><%=bean.getStaffIdentityCard()%></span>
								</td>
							</tr>
							<tr>
								<td><span class="STYLE3">服务方式&nbsp;&nbsp;</span>
								</td>
								<td style="border-bottom: 1px solid #231815"><span
									class="STYLE3"><%=bean.getServeWay()%></span>
								</td>
							</tr>
						</table>
					</div></td>
				<td>
					<div class="fanmian" id="fanmian">
						<table height="372" width="265" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="biaoti"><br />
								<br />持证须知<br /><br /></td>
							</tr>
							<tr>
								<td class="ziti">1.参加考试的学院员必须着装整洁，仪表端庄， 不得穿拖鞋、吊带装参加考试；<br>
									2.参加考试的学员在待考室内保持肃静，不得 高声喧哗；<br> 3.保持待考室内清洁卫生、不得在待考室内吸
									烟、不得从事于考试无关的或活动；<br> 4.服从车管所考试工作人员和驾校业务员的管 理和指挥；<br>
									5.不得携带与考试无关的物品进入考场；<br> 6.严禁携带任何通讯工具和无线发射仪器进入 考场。<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 凡不遵守以上规定的学员，取消当日考试资
									格或考试成绩；在考试过程中使用通讯工具、 通讯工具发出响声或使用无线发射仪器的，视 为作弊，按照公安部《123号令》规定，取消
									考试成绩，一年内不得参加考试。<br> 学员训练和考试必须佩带此证。</td>
							</tr>
							<tr>
								<td align="right"><font
									style="margin-right: 5px; font-weight:bold; font-size:12px"
									id="banbudate"><br />发证日期:<%=str_date1%><br/></font>
								</td>
							</tr>
						</table>
					</div></td>
			</tr>
		</table>
		<br>
		<br>
		<%
			}
		%>
	</div>
	<script>
			$(document).ready(function(){
		if($("#banbudate").val()==null){
		$("#banbudate").attr("value",<%=str_date1%>);
		}
    });
		</script>
	</body>