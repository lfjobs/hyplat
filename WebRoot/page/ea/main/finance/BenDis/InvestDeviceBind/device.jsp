<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>添加设备投资信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/finance/InvestDeviceBind/device.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>       
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />

<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<link rel="stylesheet"
	href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />	
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />	
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/InvestDeviceBind/style.css">
<%-- <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script> --%>
<script type="text/javascript">
		var basePath="<%=basePath%>";
	var notoken = 0;
	var token = 0;
	var search = '${search}';
	var companyID = '${account.companyID}';
	var companyName = '${account.companyName}';
	var pageNumber = '${pageNumber}';
	var staffid;
	var investType;//投资类别
</script>
</head>

<body>
	<div class="Products_" id="Products_">
    <div class="Products">
        <h4 class="tit">产品设备投资</h4>
        <div class="con">
            <div class="mil">
                <div class="title">
                    <h3><i></i><p>编号设备类</p></h3>
                    <button id="newG">选择</button>
                </div>
                <form id="baocun" method="post" name="baocun">
					<input type="submit" style="display:none;">
                <ul class="mil_con">
                    <li>
                        <p class="left">编号：</p>
                        <p class="right"><input type="text" name="code" value=""
					class="code" readonly></p>
                    </li>
                    <li>
                        <p class="left">二维码：</p>
                        <p class="right"><input type="text" name="twoCode" value=""
					class="twoCode" readonly></p>
                    </li>
                    <li>
                        <p class="left">条码：</p>
                        <p class="right"><input type="text" name="oneCode" value=""
					class="oneCode" readonly></p>
                    </li>
                    <li>
                        <p class="left">芯片号：</p>
                        <p class="right"><input type="text" name="chipCode" value=""
					class="chipCode" readonly></p>
                    </li>
                    <li>
                        <p class="left">投资类别：</p>
                        <p class="right"><input type="text" name="investType" value=""
					class="investType" readonly></p>
                    </li>
                    <li>
                        <p class="left">设备编号：</p>
                        <p class="right"><input type="text" name="deviceCode" value=""
					class="deviceCode" readonly></p>
                    </li>
                    <li>
                        <p class="left">设备名称：</p>
                        <p class="right"><input type="text" name="deviceName" value=""
					class="deviceName" readonly></p>
                    </li>
                    <li>
                        <p class="left">车牌号：</p>
                        <p class="right"><input type="text" name="carNum" value=""
					class="carNum" readonly></p>
                    </li>
                    <li>
                        <p class="left">公司：</p>
                        <p class="right"><input type="text" name="companyName" value=""
					class="companyName" readonly></p>
                    </li>
                    <li>
                        <p class="left">部门：</p>
                        <p class="right"><input type="text" name="department" value=""
					class="department" readonly></p>
                    </li>
                    <input type="hidden" name="carid" value="" class="carid" readonly>
					<input type="hidden" name="goodsid" value="" class="goodsid" readonly>
                </ul>
            </div>
            <div class="mil">
                <div class="title">
                    <h3><i style="background-color: #ff7538;"></i><p>人员信息</p></h3>
                </div>
                <ul class="mil_con">
                    <li>
                        <p class="left">管理责任人：</p>
                        <p class="right"><input type="text" name="manageResponse"
				value="" class="manageResponse" readonly></p>
                    </li>
                    <li>
                        <p class="left">投资责任人：</p>
                        <p class="right">
                        	<input type="text" name="investname" class="investname" readonly>
							<input type="hidden" name="investsccid" class="investsccid" readonly>
							<input type="hidden" name="investstaffid" class="investstaffid" readonly>
                        </p>
                        <input type="button" id="tzzr" value="选择"  class="btn_inp"/>
                        <!-- <button id="tzzr">选择</button> -->
                    </li>
                    <li>
                        <p class="left">投资人微分金账号：</p>
                        <p class="right"><input type="text" name="investaccount"
				class="investaccount" readonly></p>
                       <!--  <button>选择</button> -->
                    </li>
                    <!-- <li>
                        <p class="left">文件：</p>
                        <input type="button" class="file_btn">

                    </li>
                    <li>
                        <p class="left">挂靠设备：</p>
                        <p class="right"></p>
                        <button>选择</button>
                    </li>
                    <li>
                        <p class="left">关联业务员：</p>
                        <p class="right"></p>
                        <button>选择</button>
                    </li> -->
                </ul>
            </div>
        </div>
        <button class="Choose" id="bc">保存</button>
    </div>
</div>
	
<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
	<input type="submit" name="submit" style="display: none" />
	<div id="body_02" class="jqmWindow"
		style="width: 74%; height: 300px; color: #333; absolute; display: none; left: 50%; top: 30%;min-width: 825px;
    margin: 0 0 0 -37%;">
		<div class="drag">
			车辆查询
			<div class="close close2"></div>
		</div>

		<table width="100%" height="33" id="searchgood" border="0"
			align="center" cellpadding="0" cellspacing="0"
			style="margin-top: 5px; background: #FFFFFF;">
			<tr>
				<td  align="right">
					车牌号：
				</td>
				<td >
					<input name="carNum" class="input" id="carNum" 
						style="margin-left: 2px;" size="8"/>
				</td>
				<td  align="right">
					设备状态：
				</td>
				<td >
					<select name="deviceStatu" id="deviceStatu">
						<option value="">-请选择-</option>
						<option value="01">公司投资设备</option>
						<option value="00">挂靠设备</option>
					</select>
				</td>
				<td height="33">
				<input type="button" class="btn02" id="chaxun"
									name="button7" value="查询" />
				<input type="button" class="btn02" id="qdcar"
					name="buttonsure" value="确定" /> <input type="button" class="btn02"
					name="buttonclose" id="qdclose" value="关闭" /> <input type="hidden"
					name="parms" id="parms" /></td>
				<td width="60"><a id="wpsy" title="0">上一页</a></td>
				<td width="60"><a id="wpxy" title="0">下一页</a></td>
				<td width="80"><a id="wpzy">共 <span style="color: red"
						id="wpzycount"></span>页
				</a></td>
				<td width="80"><a id="wpdq">第<span style="color: red"
						id="wpdqcount"></span>页
				</a></td>
			</tr>
		</table>
		<table width='99%' align='center' id='gotable' cellpadding='0'
			cellspacing='0' class='table'>
			<thead>
				<tr>
					<th height='21' align='center' bgcolor='#E4F1FA'>选择</th>
					<th align='center' bgcolor='#E4F1FA'>二维码</th>
					<th align='center' bgcolor='#E4F1FA'>条码</th>
					<th align='center' bgcolor='#E4F1FA'>设备编号</th>
					<th align='center' bgcolor='#E4F1FA'>设备名称</th>
					<th align='center' bgcolor='#E4F1FA'>车牌号</th>
					<th align='center' bgcolor='#E4F1FA'>公司</th>
					<th align='center' bgcolor='#E4F1FA'>部门</th>
					<th align='center' bgcolor='#E4F1FA'>管理责任人</th>
					<th align='center' bgcolor='#E4F1FA'>设备</th>
				</tr>
			</thead>
			<tbody id="tbody2">
			</tbody>
		</table>
	</div>
</form>
<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
	<input type="submit" name="submit" style="display: none" />
	<div id="body_03" class="jqmWindow"
		style="width: 80%; height: 300px; color: #333; absolute; display: none; left: 50%; top: 30%;min-width: 825px;
    margin: 0 0 0 -40%;">
		<div class="drag">
			投资责任人查询
			<div class="close close2"></div>
		</div>
		<table width="100%" height="33" id="searchgood" border="0"
			align="center" cellpadding="0" cellspacing="0"
			style="margin-top: 5px; background: #FFFFFF;">
			<tr>
				<td  align="right">
					名称：
				</td>
				<td >
					<input name="tzName" class="input" id="tzName" 
						style="margin-left: 2px;" size="6"/>
				</td>
				<td  align="right">
					账号：
				</td>
				<td >
					<input name="tzAccount" class="input" id="tzAccount" 
						style="margin-left: 2px;" size="10"/>
				</td>
				<td  align="right">
					级别：
				</td>
				<td >
					<select name="tzCustype" id="tzCustype">
						<option value="">请选择</option>
						<option value="2">公司商城业主会员</option>
						<option value="3">经理商城业主会员</option>
						<option value="4">主管商城业主会员</option>
						<option value="5">业务商城业主会员</option>
						<option value="6">VIP客户</option>
						<option value="7">客户</option>
					</select>
				</td>
				<td height="33">
				<input type="button" class="btn02" id="chaxuntz"
									name="button7" value="查询" />
				<input type="button" class="btn02"
					id="tzResponse" name="buttonsure" value="确定" /> <input
					type="button" class="btn02" name="buttonclose" id="tzclose"
					value="关闭" /> <input type="hidden" name="parms" id="parms" /></td>
				<td width="60"><a id="tzsy" title="0">上一页</a></td>
				<td width="60"><a id="tzxy" title="0">下一页</a></td>
				<td width="80"><a id="tzzy">共 <span style="color: red"
						id="tzzycount"></span>页
				</a></td>
				<td width="80"><a id="tzdq">第<span style="color: red"
						id="tzdqcount"></span>页
				</a></td>
			</tr>
		</table>
		<table width='99%' align='center' id='gotabletz' cellpadding='0' cellspacing='0' class='table'>
			<thead>
				<tr>
					<th height='21' align='center' bgcolor='#E4F1FA'>选择</th>
					<th align='center' bgcolor='#E4F1FA'>微分金账号</th><th align='center' bgcolor='#E4F1FA'>会员级别</th>
					<th align='center' bgcolor='#E4F1FA'>人员编号</th>
					<th align='center' bgcolor='#E4F1FA'>人员名称</th></tr>
				</tr>
			</thead>
			<tbody id="tbody3"></tbody>
		</table>
	</div>

</form>
<iframe id="hidden" name="hidden" width="0" height="0"></iframe>

<div class="alert_ alert_2">
	<p style="width: 220px;height: 25px;line-height: 25px;margin-top: 25%;margin-left: 48%;text-align: center;color: #fff;font-size: 25px;">保存成功，请稍等...</p>
</div>
</body>
</html>
