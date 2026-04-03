<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>单位管理--文件管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<script>
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
 window.location.href= action+treeID;
 return;
 }
 window.location.href= action;
}
var type="<%=request.getParameter("type") %>";

$(document).ready(function(){
	if(type=="a")
	{$("#a").show();$("#b").hide();$("#c").hide();$("#d").hide();$("#e").hide();}
	if(type=="b")
	{$("#a").hide();$("#b").show();$("#c").hide();$("#d").hide();$("#e").hide();}
	if(type=="c")
	{$("#a").hide();$("#b").hide();$("#c").show();$("#d").hide();$("#e").hide();}
	if(type=="d")
	{$("#a").hide();$("#b").hide();$("#c").hide();$("#d").show();$("#e").hide();}
	if(type=="e")
	{$("#a").hide();$("#b").hide();$("#c").hide();$("#d").hide();$("#e").show();}
	
});
</script>
	</head>

	<body>
	<%--行政管理文书--%>
	<div id="a">
	<table border="0" cellpadding="1" cellspacing="0">
      <tr>
			<td rowspan="2">
				<div class="na_back_img_ks"></div>
				<div align="center"><strong>行政管理文书</strong></div></td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">通知</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">通告</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">通报</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">请示</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">批复</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">书函</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">会议通知</div></td>
				
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">会议记录</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">会议报告</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">会议总结</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">会议简报</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">计划</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">规划</div></td>
		</tr>
		<tr>
			<td rowspan="2">
				<div class="na_back_img_ks"></div>
				<div align="center"><strong>行政办公文书</strong></div></td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/payGradeModulation/ea_getListForPage.jspa?'"></div>
				<div class="center_a">工资级别单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getRankPublicreceipt.jspa?'"></div>
				<div class="center_a">员工级别单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getOverPublicreceipts.jspa?'"></div>
				<div class="center_a">加班申请单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/stafftransfer/ea_getstaffTransferList.jspa?'"></div>
				<div class="center_a">人事调令单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/informbills/ea_getInformBillsList.jspa?'"></div>
				<div class="center_a">通知单管理</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/completeAllter/ea_getCmpleteAllterList.jspa?'"></div>
				<div class="center_a">整改通知单</div></td>
				
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/tasknotice/ea_getTaskNoticeList.jspa?'"></div>
				<div class="center_a">任务通知单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getLeaveBillsList.jspa?'"></div>
				<div class="center_a">请假单管理</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">派车单管理</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getListRewardPunishment.jspa?type=1'"></div>
				<div class="center_a">奖罚单管理</div></td>
		</tr>
    </table>
    </div>
    <%--商务决策文书--%>
    <div id="b">
    <table border="0" cellpadding="1" cellspacing="0" >
		<tr>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
				<strong>商务决策文书</strong></div></td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">活动策划文书</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">市场调查报告</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">可行性研究报告</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">商务计划书</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">招标书</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">投标计划书</div></td>
		</tr>
	</table>
    </div>
	<%--商务活动文书--%>
	<div id="c">
    <table border="0" cellpadding="1" cellspacing="0" >
		<tr>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
				<strong>商务活动文书</strong></div></td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">商务书函</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">商务法律文书</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">仲裁诉讼法律文书</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">索赔理赔法律文书</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">授权委托书</div></td>
		</tr>
	</table>
    </div>
	<%--企业礼仪文书--%>
	<div id="d">
    <table border="0" cellpadding="1" cellspacing="0">
      <tr>
			<td rowspan="2">
				<div class="na_back_img_ks"></div>
				<div align="center"><strong>企业礼仪文书</strong></div></td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">欢迎词</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">欢送词</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">答谢词</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">祝贺词</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">请柬词</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">邀请信</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">感谢信</div></td>
				
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">慰问信</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">介绍信</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">证明信</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">推荐信</div></td>
		</tr>
	</table>
    </div>
	<%--演讲会议文书--%>
	<div id="e">
     <table border="0" cellpadding="1" cellspacing="0" >
		<tr>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
				<strong>演讲会议文书</strong></div></td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">竞聘演讲</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">就职演讲</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">即兴演讲</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">大会发言</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">领导发言稿</div></td>
		    <td><div class="na_back_img"></div>
				<div class="center_a">会议开幕词</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a">会议闭幕词</div></td>
		</tr>
	</table>
    </div>
</body>
</html>
