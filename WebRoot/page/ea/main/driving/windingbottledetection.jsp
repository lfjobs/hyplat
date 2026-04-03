<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>缠绕瓶检测记录表</title>
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
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/driving/windingbottledetection.js"></script>
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/> --%>
<style>
body {
	margin: 0;
	padding: 0px;
}

a,a:link {
	color: #222;
	text-decoration: none;
}

a:visited {
	
}

a:active,a:hover {
	
}

a:focus {
	outline: none;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.clear {
	diplay: block !important;
	float: none !important;
	clear: both;
	overflow: hidden;
	width: auto !important;
	height: 0 !important;
	margin: 0 auto !important;
	padding: 0 !important;
	font-size: 0;
	line-height: 0;
}

.table_con {
	font-family: '宋体 Regular', '宋体';
	font-size: 12px;
	float: left;
	width: 1150px;
	margin: 0;
	padding: 0px;
	color: #000;
	margin-left: 10px;
}

.title {
	width: 100%;
	height: 30px;
	line-height: 30px;
	text-align: center;
	margin-top: 50px;
	margin-bottom: 0px;
}

h1 {
	font-size: 18px;
	margin: 0px;
}

.left_t {
	width: 17px;
	height: 29px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/left_t_c.png)
		no-repeat left bottom;
}

.cross {
	width: 320px;
	height: 30px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cross.png)
		repeat-x center;
}

.title_t {
	width: 465px;
	height: 29px;
	line-height: 30px;
	text-align: center;
}

.right_t {
	width: 17px;
	height: 29px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/right_t_c.png)
		no-repeat right bottom;
}

.left {
	width: 17px;
	height: 960px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/vertical.png)
		repeat-y left;
}

.right {
	width: 17px;
	height: 960px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/vertical.png)
		repeat-y right;
}

.left_b {
	width: 17px;
	height: 30px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/left_b_c.png)
		no-repeat left top;
	margin-bottom: 30px;
}

.cross_b {
	width: 1116px;
	height: 16px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cross.png)
		repeat-x 5px;
	margin-bottom: 30px;
}

.right_b {
	width: 17px;
	height: 30px;
	background:
		url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/right_b_c.png)
		no-repeat right top;
	margin-bottom: 30px;
}

._con {
	width: 1116px;
	float: left; /*background:red;*/
	height: 960px;
}

._con h2 {
	width: 350px;
	height: 45px;
	line-height: 45px;
	margin: 0px;
	margin-top: 20px;
	font-size: 12px;
	color: #666;
}

._con h3 {
	width: 50%;
	height: 30px;
	line-height: 30px;
	margin: 0px;
	float: left;
	font-size: 12px;
	color: #666;
}

._con h3 p {
	width: 50%;
	height: 30px;
	line-height: 30px;
	margin: 0px;
	float: left;
	font-size: 12px;
	color: #666;
}

._con table {
	float: left;
}

._con table tr td {
	background-color: #fff;
	text-align: center;
}

._con .table_t {
	line-height: 50px;
}

._con p {
	margin: 0px;
}

.table_t tr td p {
	line-height: 14px;
	margin: 0px;
}

.align_l {
	text-align: left;
}

.tab1 tr td {
	border-bottom: 1px solid #3fb2ad;
	border-left: 1px solid #3fb2ad;
}

._con table tr .td_r {
	border-right: 1px solid #3fb2ad;
}

._con table tr .td_l {
	border-left: none;
}

._con table tr .td_b {
	border-bottom: none;
}

.floor {
	width: 100%;
	height: 50px;
	margin-top: 30px;
	text-align: center;
}

.floor input[type="button"] {
	margin-left: 20px;
}

.sec,.sec1 {
	cursor: pointer;
}

.sec {
	color: #FFFFFF;
	font-weight: bold;
	text-align: center;
	background-color: #3fb2ad;
}

.sec1 {
	color: #3fb2ad;
	text-align: center;
	background-color: #FFFFFF;
}

div {
	line-height: 22px;
}

.big1 {
	border: 1px solid #3fb2ad;
}

select {
	width: 100%;
	height: 100%
}

.littleClass {
	width: 100%;
	height: 100%;
	border: 0px;
}

.littletext {
	width: 100%;
	border: 0px;
	height: 100%
}

.biggesttext {
	width: 100%;
	border: 0px;
	height: 100%
}
</style>
<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var carCylinderId = "";
			var status = '${status}';
			var carwindingdetectionid = '${carwindingdetectionid}'
</script>

</head>
<body>
	<br />
	<table width="1000" border="0" cellpadding="4" cellspacing="0"
		class="big">
		<tr>
			<td width="250" class="sec big1" id="ts1"
				onclick="xxk(2,1,'ts','tts')">缠绕瓶记录表</td>
			<td width="250" bgcolor="#FFFFFF" id="ts2" class="sec1 big1"
				onclick="xxk(2,2,'ts','tts')">磁粉探伤记录表</td>
			<td width="250" bgcolor="white">&nbsp;</td>
			<td width="250" bgcolor="white">&nbsp;</td>

		</tr>
		<tr>
			<td colspan="4" bgcolor="#FFFFFF">
				<!-- 缠绕瓶 -->
				<form name="windingTestForm" id="windingTestForm" method="post">
				<input type="submit" name="submit" id="postSaveWindingForm" style="display: none"/>
				<input type="text" name="carwindingdetection.carCylinderId"  value="${obj[1]}"style="display: none"/>
				<input type="hidden" value ="Winding" name="bottleType"/>
				<input type="hidden" value ="seedetail" name="status"/>
					<div id="tts1">
						<div class="table_con fl">
							<h1 class="title fl">四川兰特清洁汽车安全评价检测有限公司</h1>
							<div class="clear"></div>
							<div class="left_t fl"></div>
							<div class="cross fl"></div>
							<h1 class="title_t fl">汽车用CNG金属内胆纤维缠绕瓶定期检查与评定记录表</h1>
							<div class="right_t fr"></div>
							<div class="cross fr"></div>
							<div class="clear"></div>
							<div class="left fl"></div>
							<div class="_con">
								<h2 class="fr">
									检验编号:
									<s:if test='obj[40]==null'>暂未派工检测</s:if>
									<s:else>
										<span>${obj[40]}</span>
									</s:else>
								</h2>
								<div class="clear"></div>
								<h3>送检单位：${obj[34]}</h3>
								<h3>
									<p>
										室温℃
										<s:if test='obj[41]==null'>暂未派工检测</s:if>
										<s:else>
											<span>${obj[41]}</span>
										</s:else>
									</p>
									<p>
										水温℃
										<s:if test='obj[42]==null'>暂未派工检测</s:if>
										<s:else>
											<span>${obj[42]}</span>
										</s:else>
									</p>
								</h3>
								<div class="clear"></div>
								<table class="table_t" width="100%" border="0" cellspacing="1"
									cellpadding="0" bgcolor="#3fb2ad">
									<tr>
										<td width="50" rowspan="2"><p>原</p>
											<p>始</p>
											<p>记</p>
											<p>录</p>
											<p>登</p>
											<p>记</p>
										</td>
										<td width="86">车牌号码</td>
										<td width="86">气瓶编号</td>
										<td width="86">型号</td>
										<td width="86">制造单位</td>
										<td width="86">出厂日期</td>
										<td width="86">重量（kg）</td>
										<td width="86"><p>容积</p>
											<p>（L）</p>
										</td>
										<td width="86">类型</td>
										<td width="86"><p>公称工作</p>
											<p>压力</p>
											<p>（Mpa）</p>
										</td>
										<td width="86"><p>水压试验</p>
											<p>压力</p>
											<p>（Mpa）</p>
										</td>
										<td width="106">投用日期</td>
									</tr>
									<tr>
										<td style="display: none"><span id="carCylinderId">${obj[1]}</span>
										<input type="hidden" value = "${obj[40]}" name = "carwindingdetection.checkoutNum"/ >
										</td>
										<td>${obj[21]}</td>
										<td>${obj[6]}</td>
										<td>${obj[9]}</td>
										<td>${obj[10]}</td>
										<td>${fn:substring(obj[11], 0, 10)}</td>
										<td>${obj[12]}</td>
										<td>${obj[13]}</td>
										<td>${obj[7]}</td>
										<td>${obj[14]}</td>
										<td>${obj[15]}</td>
										<td class="td_r">${fn:substring(obj[31], 0, 10)}</td>
									</tr>
								</table>
								<table width="100%" class="tab1" cellspacing="0" cellpadding="0">
									<tr>
										<td width="50"><p>检</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>验</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>水</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>记</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
											<p>录</p>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
										</td>
										<td class="align_l td_l td_b td_r">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="50"><p>外</p>
														<p>&nbsp;</p>
														<p>&nbsp;</p>
														<p>观</p>
														<p>&nbsp;</p>
														<p>&nbsp;</p>
														<p>检</p>
														<p>&nbsp;</p>
														<p>&nbsp;</p>
														<p>查</p>
													</td>
													<td style=" height:30px;">
														<div align="center">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0" style="line-height:30px;">
																<tr>
																	<td colspan="11">
																		<div align="center">缠绕层外观检验与评定</div>
																	</td>

																</tr>
																<tr>
																	<td><div align="center">
																			<p style="line-height:14px;">划伤擦伤</p>
																			<p style="line-height:14px;">凿伤</p>
																		</div>
																	</td>
																	<td><div align="center">热火损伤</div>
																	</td>
																	<td><div align="center">磨损</div>
																	</td>
																	<td><div align="center">气体泄漏</div>
																	</td>
																	<td><div align="center">
																			<p style="line-height:14px;">化学品</p>
																			<p style="line-height:14px;">腐蚀</p>
																		</div>
																	</td>
																	<td><div align="center">自然老化</div>
																	</td>
																	<td><div align="center">
																			<p align="center">
																				<p style="line-height:14px;">汽车事故</p>
																			<p style="line-height:14px;">着火</p>
																			<p style="line-height:14px;">受高热</p>
																		</div>
																	</td>
																	<td><div align="center">冲击力</div>
																	</td>
																	<td><div align="center">
																			<p align="center" style="line-height:14px;">应力腐蚀</p>
																			<p align="center" style="line-height:14px;">裂纹</p>
																		</div>
																	</td>
																	<td rowspan="4"><div align="center">检查结果</div>
																	</td>
																	<td rowspan="4"><div align="center">检&nbsp;&nbsp;查&nbsp;&nbsp;人</div>
																	</td>
																</tr>
																<tr>
																	<td>
																		<div align="center">
																			<select name="carwindingdetection.scratchScratchChisel">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.scratchScratchChisel=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.scratchScratchChisel=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.scratchScratchChisel=="03"}'><span>×</span></c:if>
																		</div></td>
																	<td><div align="center">
																			<select name="carwindingdetection.heatDamage">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.heatDamage=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.heatDamage=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.heatDamage=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.abrasion">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.abrasion=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.abrasion=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.abrasion=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.gasLeakage">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.gasLeakage=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.gasLeakage=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.gasLeakage=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.chemicalCorrosion">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.chemicalCorrosion=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.chemicalCorrosion=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.chemicalCorrosion=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.naturalAgeing">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.naturalAgeing=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.naturalAgeing=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.naturalAgeing=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.carAccidents">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.carAccidents=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.carAccidents=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.carAccidents=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.impactForce">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.impactForce=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.impactForce=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.impactForce=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.stressCorrosion">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.stressCorrosion=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.stressCorrosion=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.stressCorrosion=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																</tr>
																<tr>
																	<td colspan="9"><div align="center">外露金属部分外外观检查与评定</div>
																	</td>
																</tr>
																<tr>
																	<td><p align="center">划伤擦伤</p>
																		<p align="center">凿伤</p>
																	</td>
																	<td><div align="center">凸起</div>
																	</td>
																	<td><div align="center">凹陷</div>
																	</td>
																	<td><div align="center">点腐蚀</div>
																	</td>
																	<td><div align="center">线腐蚀</div>
																	</td>
																	<td><div align="center">面腐蚀</div>
																	</td>
																	<td colspan="2"><div align="center">缠绕层下的金属腐蚀</div>
																	</td>
																	<td><div align="center">瓶口螺纹检查</div>
																	</td>
																</tr>
																<tr>
																	<td><div align="center">
																			<select name="carwindingdetection.leakageMetalScratchScratchChis">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.leakageMetalScratchScratchChis=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.leakageMetalScratchScratchChis=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.leakageMetalScratchScratchChis=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.embossing">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.embossing=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.embossing=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.embossing=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.sunken">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.sunken=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.sunken=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.sunken=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.pittingCorrosion">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.pittingCorrosion=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.pittingCorrosion=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.pittingCorrosion=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.lineCorrosion">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.lineCorrosion=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.lineCorrosion=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.lineCorrosion=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.surfaceCorrosion">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.surfaceCorrosion=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.surfaceCorrosion=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.surfaceCorrosion=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td colspan="2"><div align="center">
																			<select name="carwindingdetection.metalCorrosionUnder">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.metalCorrosionUnder=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.metalCorrosionUnder=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.metalCorrosionUnder=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.threadInspection">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.threadInspection=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.threadInspection=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.threadInspection=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td><div align="center">
																			<select name="carwindingdetection.appearanceInspectionResults">
																				<option value="合格">合格</option>
																				<option value="不合格">不合格</option>
																			</select>
																			<span>${obj2.appearanceInspectionResults}</span>
																		</div>
																	</td>
																	<td><div align="right">
																			<img src="<%=basePath%>images/r_7_09.gif" />
																		</div>
																	</td>
																</tr>
																<tr>
																	<td colspan="4">裸露金属部分无损检查</td>
																	<td colspan="5">检查结果</td>
																	<td colspan="2">检查人</td>
																</tr>
																<tr>
																	<td colspan="4"><div align="center">
																			<select name="carwindingdetection.bareMetalPartsNondestructiveIn">
																				<option value="01">/</option>
																				<option value="02">√</option>
																				<option value="03">×</option>
																			</select>
																			<c:if test='${obj2.bareMetalPartsNondestructiveIn=="01"}'><span>/</span></c:if>
																			 <c:if test='${obj2.bareMetalPartsNondestructiveIn=="02"}'><span>√</span></c:if>
																			<c:if test='${obj2.bareMetalPartsNondestructiveIn=="03"}'><span>×</span></c:if>
																		</div>
																	</td>
																	<td colspan="5"><div align="center">
																			<select name="carwindingdetection.bareMetalTestResults">
																				<option value="合格">合格</option>
																				<option value="不合格">不合格</option>
																			</select>
																			<span>${obj2.bareMetalTestResults}</span>
																		</div>
																	</td>
																	<td colspan="2"><div align="right">
																			<img src="<%=basePath%>images/r_7_09.gif" />
																		</div>
																	</td>
																</tr>
																<tr>
																	<td colspan="2">实测瓶重(kg)</td>
																	<td colspan="2">瓶水总重(kg)</td>
																	<td colspan="1">水重(kg)</td>
																	<td colspan="2">水容积系数(L/kg)</td>
																	<td colspan="2">实测容积(L)</td>
																	<td>检查结果</td>
																	<td>检查人</td>
																</tr>
																<tr>
																	<td colspan="2" ><input type="text" 
																		class="littleClass" name="carwindingdetection.measuredBottleWeight" /><span>${obj2.measuredBottleWeight}</span>
																		</td>
																	<td colspan="2"><input type="text" 
																		class="littleClass" name="carwindingdetection.totalWeightBottlesWater"/><span>${obj2.totalWeightBottlesWater}</span>
																		
																	</td>
																	<td colspan="1"><input type="text" name="carwindingdetection.waterHeavy"
																		class="littleClass" /><span>${obj2.waterHeavy}</span>
																	</td>
																	<td colspan="2"><input type="text" name="carwindingdetection.waterVolumeCoefficient"
																		class="littleClass" /><span>${obj2.waterVolumeCoefficient}</span>
																	</td>
																	<td colspan="2"><input type="text" name="carwindingdetection.measuredVolume"
																		class="littleClass" /><span>${obj2.measuredVolume}</span>
																	</td>
																	<td><select  name="carwindingdetection.measuredResults">
																			<option value="合格">合格</option>
																			<option value="不合格">不合格</option>
																	</select>
																	<span>${obj2.measuredResults}</span>
																	</td>
																	<td><div align="right">
																			<img src="<%=basePath%>images/r_7_09.gif" />
																		</div>
																	</td>
																</tr>
															</table>
														</div></td>
												</tr>
												<tr>
													<td colspan="9">水压试验（30 M p a ）</td>
												</tr>
												<tr>
													<td class="td_l td_b" colspan="2"><table width="100%"
															border="0" cellspacing="0" cellpadding="0"
															style=" line-height:30px;">
															<tr>
																<td width="10%">Ph</td>
																<td width="10%">Ph*βT</td>
																<td width="10%"><p style="line-height:14px;">压入总水量</p>
																	<p style="line-height:14px;">A值（ml）</p>
																</td>
																<td width="10%"><p style="line-height:14px;">管道压入</p>
																	<p style="line-height:14px;">水量B值</p>
																	<p style="line-height:14px;">（ml）</p>
																</td>
																<td width="10%"><p style="line-height:14px;">残余变形</p>
																	<p style="line-height:14px;">值（ml）</p>
																</td>
																<td width="10%"><p style="line-height:14px;">全变形</p>
																	<p style="line-height:14px;">值（ml）</p>
																</td>
																<td width="10%"><p style="line-height:14px;">残余变形</p>
																	<p style="line-height:14px;">率（%）</p>
																</td>
																<td width="8%">检查结果</td>
																<td width="12%">检查人</td>
															</tr>
															<tr>
																<td><input type="text" class="littleClass" name="carwindingdetection.phValue"/><span>${obj2.phValue}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.phAndBt"/><span>${obj2.phAndBt}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.pushTotalWaterValue"/><span>${obj2.pushTotalWaterValue}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.waterPipePressureBvalue"/><span>${obj2.waterPipePressureBvalue}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.residualDeformationValue"/><span>${obj2.residualDeformationValue}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.wholeDeformationValue"/><span>${obj2.wholeDeformationValue}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.residualDeformationRate"/><span>${obj2.residualDeformationRate}</span>
																</td>
																<td><select  name="carwindingdetection.hydrostaticTestResults">
																		<option value="合格">合格</option>
																		<option value="不合格">不合格</option>
																</select>
																<span>${obj2.hydrostaticTestResults}</span>
																</td>
																<td><div align="right">
																		<img src="<%=basePath%>images/r_7_09.gif" />
																	</div>
																</td>
															</tr>
														</table>
														<div class="clear"></div>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" style=" line-height:30px;">
															<tr>
																<td width="25%">内部干燥</td>
																<td width="27%">瓶阀更换</td>
																<td width="25%">气密性试验（20Mpa）</td>
																<td width="11%">检查结果</td>
																<td width="12%">检查人</td>
															</tr>

															<tr>
																<td><input type="text" class="littleClass" name="carwindingdetection.internalDry"/><span>${obj2.internalDry}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.valveReplacement"/><span>${obj2.valveReplacement}</span>
																</td>
																<td><input type="text" class="littleClass" name="carwindingdetection.airTightTest"/><span>${obj2.airTightTest}</span>
																</td>
																<td><select  name="carwindingdetection.internalInspectionResult">
																		<option value="合格">合格</option>
																		<option value="不合格">不合格</option>
																</select>
																<span>${obj2.internalInspectionResult}</span>
																</td>
																<td><div align="right"><img src="<%=basePath%>images/r_7_09.gif" /></div>
																</td>
															</tr>
														</table>
														<div class="clear"></div>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" style=" line-height:30px;">
															<tr>
																<td width="26%">抽真空或置换</td>
																<td width="17%">检验标记</td>
																<td width="17%">下次检验日期</td>
																<td width="17%">涂敷</td>
																<td width="11%">检查结果</td>
																<td width="12%">检查人</td>
															</tr>
															<tr>
																<td><span>抽真空</span><input type = "hidden" name="carwindingdetection.vacuumReplacement" value="抽真空"></td>
																<td><span>打钢印</span><input type = "hidden" name="carwindingdetection.inspectionTag" value="打钢印"></td>
																<td><input type="text" class="littleClass" onfocus="date(this)" name="carwindingdetection.nextTestDate"/>
																<span>${fn:substring(obj2.nextTestDate, 0, 10)}</span>
																</td>
																<td><span>补漆</span><input type="hidden" name="carwindingdetection.spreading" value= "补漆"/></td>
																<td><select name="carwindingdetection.vacuumCoatingTestResults">
																		<option value="合格">合格</option>
																		<option value="不合格">不合格</option>
																</select>
																<span>${obj2.vacuumCoatingTestResults}</span>
																</td>
																<td><div align="right"><img src="<%=basePath%>images/r_7_09.gif" /></div>
																</td>
															</tr>
														</table>
														<div class="clear"></div>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0" style=" line-height:30px;">
															<tr>
																<td width="51%">气瓶拆装单位</td>
																<td width="33%">检查结果</td>
																<td width="16%">检查人</td>
															</tr>
															<tr>
																<td><span>自拆装</span><input type="hidden" name="carwindingdetection.gasCylindersDisassemblingUnits" value="自拆装"/></td>
																<td><select name="carwindingdetection.dismantlingUnitTestResults">
																		<option value="合格">合格</option>
																		<option value="不合格">不合格</option>
																</select>
																<span>${obj2.dismantlingUnitTestResults}</span>
																</td>
																<td><div align="right"><img src="<%=basePath%>images/r_7_09.gif" /></div>
																</td>
															</tr>
														</table></td>
												</tr>
											</table></td>
									</tr>
									<tr>
										<td><p>备</p>
											<p>&nbsp;</p>
											<p>注</p>
										</td>
										<td class="td_b">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" style=" line-height:30px;">
												<tr>
													<td rowspan="4" class="align_l td_l">
														<p
															style="line-height:30px; float:left; margin:0px; margin-left:20px; width:700px;">
															依据《气瓶安全监督规程》TSGR0009-2009《车用气瓶安全技术监察规程》、川质监办 [2009]
															202号文和GB19533-2004《汽车用压缩天然气钢瓶定期检验与评定》制定此表。所用主要设备仪器有：
															空压机2V-1/250、磁粉探伤机CJW-5000Z、电动试压泵4DSY-22/63、瓶阀装卸机TJW-1、内窥镜SGJ\电子称TCS-500、测厚仪TT110
														</p>
														<p
															style="line-height:30px; float:left; margin:0px; margin-left:20px; width:700px;">
															表中各检验项目，合格项打√，不合格项打×，不填项打／</p></td>
													<td width="320" class="align_l td_r">检查结论：<select
														style="width:142px;" name="carwindingdetection.resultInspection">
															<option value="合格">合格</option>
															<option value="不合格">不合格</option>
													</select>
													<span>${obj2.resultInspection}</span>
													</td>
												</tr>
												<tr>
													<td class="align_l td_r">检 查 员：<input type="text" name="carwindingdetection.inspector"/><span>${obj2.inspector}</span>
													</td>
												</tr>
												<tr>
													<td class="align_l td_r">记 录 员：<input type="text" name="carwindingdetection.recorder"/><span>${obj2.recorder}</span>
													</td>
												</tr>
												<tr>
													<td class="align_l td_r">检验日期：<input type="text" name="carwindingdetection.inspectionDate"
														onfocus="date(this)" /><span>${fn:substring(obj2.inspectionDate, 0, 10)}</span>
													</td>
												</tr>
											</table></td>
									</tr>
								</table>
								<div class="clear"></div>
								<div class="floor fl">
								<span id="tishiInformation" style="color:red;display:none">红色框为必填项！</span>
									<input type="button" value="保存" id="savewinding"/> <input
										type="reset" value="取消" class="sd" /> <input 
										type="button" value="返回" id="returnmain" /> <input 
										type="button" value="打印" id="printpage" />
								</div>

							</div>
							<div class="right fr"></div>
							<div class="clear"></div>
							<div class="left_b fl"></div>
							<div class="cross_b fl"></div>
							<div class="right_b fl"></div>
						</div>
					</div>
				</form>

<iframe src="" name="hidden"  id="mainframe"  frameborder="0" border="0"  framespacing="0" style="height:0;"></iframe>

				<div id="tts2" style="display:none;">
					<p></p>
					<p></p>
					<div class="table_con fl">
						<div class="clear"></div>
						<div class="left_t fl"></div>
						<div class="cross fl"></div>
						<h1 class="title_t fl">车用压缩天然气缠绕瓶磁粉探伤记录</h1>
						<div class="right_t fr"></div>
						<div class="cross fr"></div>
						<div class="clear"></div>
						<div class="left fl"></div>
						<div class="_con">
							<table class="tb1" bgcolor="#3fb2ad" width="100%" border="0"
								cellspacing="1" cellpadding="1">
								<tr>
									<td width="17%" class="bg_color">检验编号</td>
									<td width="23%"><s:if test='obj[40]==null'>暂未派工检测</s:if>
									<s:else>
										<span>${obj[40]}</span>
									</s:else>
									</td>
									<td width="13%" class="bg_color">气瓶编号</td>
									<td width="16%">${obj[6]}
									</td>
									<td width="15%" class="bg_color">规格</td>
									<td width="16%"><input type="text" class="biggesttext" />
									</td>
								</tr>
								<tr>
									<td class="bg_color">热处理状态</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">表面状态</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">材质</td>
									<td><input type="text" class="biggesttext" />
									</td>
								</tr>
								<tr>
									<td class="bg_color"><p>探伤设备</p>
										<p>型号名称</p>
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">磁化方法</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">磁粉种类</td>
									<td><input type="text" class="biggesttext" />
									</td>
								</tr>
								<tr>
									<td class="bg_color">磁悬液种类</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">磁悬浮浓度</td>
									<td colspan="3"><input type="text" class="biggesttext" />
									</td>
								</tr>
								<tr>
									<td class="bg_color">灵敏度试片</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">磁化时间</td>
									<td colspan="3"><input type="text" onfocus="date(this)"
										class="biggesttext" />
									</td>
								</tr>
								<tr>
									<td class="bg_color"><p>磁化强度纵</p>
										<p>向（AC）安匝</p>
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color"><p>磁化强度周</p>
										<p>向（AC）安培</p>
									</td>
									<td colspan="3"><input type="text" class="biggesttext" />
									</td>
								</tr>
								<tr>
									<td class="bg_color">磁粉施加方法</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td class="bg_color">检测标准</td>
									<td colspan="3"><input type="text" class="biggesttext" />
									</td>
								</tr>
							</table>
							<div class="clear"></div>
							<h1>缠绕瓶示意图</h1>
							<div class="clear"></div>
							<div class="sd fl">
								<div class="sd_Onehalf fl">
									<img alt="" title=""
										src="<%=basePath%>page/ea/main/driving/signup/carlinderimg/crp_ds.jpg" />
								</div>
								<div class="sd_Onehalf fl">
									<p>&nbsp;</p>
									<p>注：</p>
									<p>S1：缺陷起始距离</p>
									<p>S2：缺陷终点距离</p>
									<p>S3：最长缺陷起始距离</p>
									<p>L：最长缺陷长度</p>
									<p>N：缺陷数量</p>
								</div>
							</div>
							<div class="clear"></div>
							<table class="tb2" bgcolor="#3fb2ad" width="100%" border="0"
								cellspacing="1" cellpadding="1">
								<tr>
									<td width="16%" class="bg_color">S1</td>
									<td width="14%" class="bg_color">S2</td>
									<td width="14%" class="bg_color">S3</td>
									<td width="14%" class="bg_color">L(mm)</td>
									<td width="14%" class="bg_color">N</td>
									<td width="14%" class="bg_color">缺陷性质</td>
									<td width="14%" class="bg_color">评定级别</td>
								</tr>
								<tr>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><select name="carwindingdetection"><option>Ⅰ</option>
											<option>Ⅱ</option>
											<option>Ⅲ</option>
									</select>
									</td>
								</tr>
								<tr>
									<td class="bg_color">结论</td>
									<td class="bg_color">操作员</td>
									<td class="bg_color">检验员</td>
									<td class="bg_color">检验日期</td>
									<td colspan="3" class="bg_color">备注</td>
								</tr>
								<tr>
									<td><select name="carwindingdetection."><option>合格</option>
											<option>不合格</option>
									</select>
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext" />
									</td>
									<td><input type="text" class="biggesttext"
										onfocus="date(this)" />
									</td>
									<td colspan="3"><input type="text" class="biggesttext" />
									</td>
								</tr>
							</table>
							<div class="floor fl">
								<input name="" type="button" value="保存" /> <input name=""
									type="button" value="取消" /> <input name="" type="button"
									value="返回" />
							</div>
						</div>
						<div class="right fr"></div>
						<div class="clear"></div>
						<div class="left_b fl"></div>
						<div class="cross_b fl"></div>
						<div class="right_b fl"></div>
					</div>



				</div></td>
		</tr>
	</table>


</body>
<script type="text/javascript">
	function getid(id) {
		return document.getElementById(id);
	}
	function xxk(num, id, ii, iii) {
		var dq;
		for ( var i = 1; i <= num; i++) {
			if (i == id) {
				dq = getid(ii + i).className = 'sec big1'; //当前选项
				getid(iii + i).style.display = "block";
			} else {
				dq = getid(ii + i).className = 'sec1 big1';
				getid(iii + i).style.display = "none";
			}
		}
	}
</script>
</html>
