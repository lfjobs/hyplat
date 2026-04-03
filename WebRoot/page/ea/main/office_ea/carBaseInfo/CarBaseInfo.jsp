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
	String filepath = request.getSession().getServletContext()
			.getRealPath("/");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>车辆基本信息</title>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/carBaseInfo/CarBaseInfo.js"></script>

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var carID = "${carInformation.carID}";
		var carNum = "${carInformation.carNum}";
		var staffID = "${carInformation.staffID}";
		var staffName = "${carInformation.staffName}";
		var personIdentityCard;
		var aa = '<%=request.getParameter("aa")%>';
		var showType ='${showType}'; 
		var select = 1;
    	var str="";
    	var temp = "";
    	var notoken = 0;
    	var pNumber = ${pageNumber};
    	var peopleid="";
    	var relationID="${contactrelation.relationID}";
    	var  search='${search}';
    	var module_title='${param.module_title}';  
    	
	
</script>
	</head>

	<body style="overflow: auto;">
		<div class="content" style="width: 860px; height: 100%;">
			<div class="contentbannb">
				<div class="divtx">
					&nbsp;车辆基本信息管理
				</div>
			</div>
			<table width="99%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="biaoti box1">
				<tr>
					<td height="27" class="txt03">
						车辆基本信息
					</td>
				</tr>
			</table>

			<div id="box1">
				<form name="box1Form" id="box1Form" method="post"
					enctype="multipart/form-data">
					<input type="submit" name="submit" style="display: none" />
					<table id="stafftable" width="99%" align="center" cellpadding="0"
						cellspacing="0" class="table">
						<tr class="trheight">
							<td width="12%" align="right">
								车牌号：
							</td>
							<td width="23%">
								<span id="carNum">${carInformation.carNum}</span>
							</td>
							<td width="12%" align="right">
								车辆类型：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="carType">${carInformation.carType}</span>
							</td>
							<td width="12%" align="right">
								发动机型号：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="engineType">${carInformation.engineType}</span>
							</td>

						</tr>
						<tr>
							<td align="right">
								车架号：
							</td>
							<td done0="10" done1="10">
								<span id="staffName">${carInformation.carFrameNum }</span>
							</td>
							<td align="right">
								注册时间：
							</td>
							<td done0="11" done1="11">
								<span id="registrationDate">${carInformation.registrationDate}</span>
							</td>
							<td width="12%" align="right">
								责任人：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="staffName">${carInformation.staffName}</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								购买日期：
							</td>
							<td done0="12" done1="12">
								<span id="buyDate">${carInformation.buyDate}</span>
							</td>
							<td align="right">
								出厂日期：
							</td>
							<td done0="13" done1="13">
								<span id="releaseDate">${carInformation.releaseDate}</span>
							</td>
							<td width="12%" align="right">
								运行日期：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="operationDate">${carInformation.operationDate }</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								车辆品牌：
							</td>
							<td>
								<span id="vehicleBrand">${carInformation.vehicleBrand}</span>
							</td>
							<td align="right">
								车辆厂牌型号：
							</td>
							<td>
								<span id="brandModel">${carInformation.brandModel}</span>
							</td>
							<td width="12%" align="right">
								货箱内部尺寸：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="containerInSize">${carInformation.containerInSize
									}</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								外廊尺寸：
							</td>
							<td>
								<span id="outerSize">${carInformation.outerSize }</span>
							</td>
							<td align="right">
								驱动形式：
							</td>
							<td>
								<span id="driveType">${carInformation.driveType }</span>
							</td>
							<td width="12%" align="right">
								排量/功率：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="power">${carInformation.power }</span>
							</td>
						</tr>

						<tr>
							<td align="right">
								燃油类别：
							</td>
							<td>
								<span id="fuelType">${carInformation.fuelType}</span>
							</td>
							<td align="right">
								外观颜色及漆号：
							</td>
							<td>
								<span id="colorPaintNum">${carInformation.colorPaintNum }</span>
							</td>
							<td width="12%" align="right">
								制造厂名称：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="factoryName">${carInformation.factoryName }</span>
							</td>
						</tr>

						<tr>
							<td align="right">
								准牵引总质量：
							</td>
							<td>
								<span id="tractionTotal">${carInformation.tractionTotal }</span>
							</td>
							<td align="right">
								轮距：
							</td>
							<td>
								<span id="wheelTead">${carInformation.wheelTead}</span>
							</td>
							<td width="12%" align="right">
								核定载客(人)：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="ratifyPeople">${carInformation.ratifyPeople}</span>
							</td>
						</tr>

						<tr>
							<td align="right">
								核定载质量：
							</td>
							<td>
								<span id="ratifyQuality">${carInformation.ratifyQuality}</span>
							</td>
							<td align="right">
								国产/进口：
							</td>
							<td>
								<span id="domestic">${carInformation.domestic }</span>
							</td>
							<td width="12%" align="right">
								驾驶室载客：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="bridgePeople">${carInformation.bridgePeople}</span>
							</td>
						</tr>

						<tr>
							<td align="right">
								钢板弹簧片数：
							</td>
							<td>
								<span id="springNum">${carInformation.springNum}</span>
							</td>
							<td align="right">
								车辆获得方式：
							</td>
							<td>
								<span id="vehicleGet">${carInformation.vehicleGet}</span>
							</td>
							<td width="12%" align="right">
								使用性质：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="useProp">${carInformation.useProp}</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								轴数：
							</td>
							<td>
								<span id="shaftNum">${carInformation.shaftNum}</span>
							</td>
							<td align="right">
								轮胎数：
							</td>
							<td>
								<span id="tireNum">${carInformation.tireNum}</span>
							</td>
							<td width="12%" align="right">
								轴距(mm)：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="wheelbase">${carInformation.wheelbase}</span>
							</td>
						</tr>

						<tr>
							<td align="right">
								百公里耗油：
							</td>
							<td>
								<span id="kmFuel">${carInformation.kmFuel}</span>
							</td>
							<td align="right">
								轮胎规格：
							</td>
							<td>
								<span id="tireSpecifications">${carInformation.tireSpecifications}</span>
							</td>
							<td width="12%" align="right">
								整备质量(kg)：
							</td>
							<td width="23%" done0="9" done1="9">
								<span id="serviceQuality">${carInformation.serviceQuality}</span>
							</td>
						</tr>

						<tr>
							<td align="right" rowspan="2">
								导航菜单：
							</td>
							<td align="left" colspan="5">
								<div class="navMenu">
									<input type="button" value="全部" class="all" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="5">
								<div class="navMenu">
								
									<input type="button" value="&nbsp;&nbsp;车牌号维护&nbsp;"
										class="box2" />
									<input type="button"
										value="&nbsp;&nbsp;&nbsp;购车发票&nbsp;&nbsp;&nbsp;" class="box11" />
									<input type="button" value="&nbsp;&nbsp;购置税发票&nbsp;"
										class="box51" />
									<input type="button" value="购置保险信息" class="box14" />


									<input type="button" value="购置年检信息" class="box53" />
									<input type="button" value="车辆CNG信息" class="box5" />
									<input type="button" value="车辆安全信息" class="box7" />
									<input type="button" value="车辆资产信息" class="box13" />

									<input type="button" value="车辆使用信息" class="box47" />
									<input type="button" value="车辆维护信息" class="box48" />
									<input type="button" value="相关证件子集" class="box8" />

									<input type="button" value="机动车行驶证" class="box9" />
									<input type="button" value="&nbsp;道路运输证&nbsp;&nbsp;"
										class="box10" />
									<input type="button" value="车用瓶使用证" class="box31" />
									<input type="button" value="车辆购置税证" class="box32" />
									<input type="button" value="车辆违章信息" class="box33" />
									<input type="button" value="车辆准载座位" class="box52" />
									<input type="button" value="  调离管理  " class="box15" />
									
									<input type="button" value="  车辆状态  " class="box16" />



								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- 车辆信息管理 -->
			<div style="height: 800px; overflow-y: scroll;" class="gdkd">
				
				<!-- 车牌号维护 -->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box2">
					<tr>
						<td height="27" class="txt03">
							车牌号维护
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box2',2,'edit')"
								id="mord2" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box2',2,'close')"
								id="mord2_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box2" style="height: 100%; display: none;">
					<form name="box2Form" id="box2Form" method="post">

						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="width: 100%;">
										<iframe
											url="ea/carnum/ea_getListCarByCompanyID.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe2" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 购车发票 -->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box11">
					<tr>
						<td height="27" class="txt03">
							购车发票
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box11',11,'edit')"
								id="mord11" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box11',11,'close')"
								id="mord11_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box11" style="height: 100%; display: none;">
					<form name="box11Form" id="box11Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
												url="ea/carinvoice/ea_getCarInvoiceList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe11" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 购置税发票 -->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box51">
					<tr>
						<td height="27" class="txt03">
							购置税发票
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box51',51,'edit')"
								id="mord51" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box51',51,'close')"
								id="mord51_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box51" style="height: 100%; display: none;">
					<form name="box51Form" id="box51Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carpurchasetax/ea_getCarPurchaseTaxList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe51" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 购置保险信息 -->

				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box14">
					<tr>
						<td height="27" class="txt03">
							购置保险信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box14',14,'edit')"
								id="mord14" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box14',14,'close')"
								id="mord14_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box14" style="height: 100%; display: none;">
					<form name="box14Form" id="box14Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe url="ea/carinsurance/ea_getCarInsuranceList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe14" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 购置年检信息 -->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box53">
					<tr>
						<td height="27" class="txt03">
							购置年检信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box53',53,'edit')"
								id="mord53" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box53',53,'close')"
								id="mord53_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box53" style="height: 100%; display: none;">
					<form name="box53Form" id="box53Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentCompany">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe url="ea/carareview/ea_getCarAReviewList.jspa?carInformation.carID="${carInformation.carID}" src=""
											name="mainframe53" width="100%" marginwidth="0" height="380"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe53" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆CNG信息 -->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box5">
					<tr>
						<td height="27" class="txt03">
							车辆CNG信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box5',5,'edit')"
								id="mord5" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box5',5,'close')"
								id="mord5_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box5" style="height: 100%; display: none;">
					<form name="box5Form" id="box5Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carcng/ea_getCarCNGList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe5" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆安全信息-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box7">
					<tr>
						<td height="27" class="txt03">
							车辆安全信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box7',7,'edit')"
								id="mord7" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box7',7,'close')"
								id="mord7_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box7" style="height: 100%; display: none;">
					<form name="box7Form" id="box7Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="vwidth: 100%;">
										<iframe
												url="ea/carsafeinformation/ea_getCarsafeinformationList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe7" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆资产信息-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box13">
					<tr>
						<td height="27" class="txt03">
							车辆资产信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box13',13,'edit')"
								id="mord13" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box13',13,'close')"
								id="mord13_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box13" style="height: 100%; display: none;">
					<form name="box13Form" id="box13Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe13" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆使用信息-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box47">
					<tr>
						<td height="27" class="txt03">
							车辆使用信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box47',47,'edit')"
								id="mord47" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box47',47,'close')"
								id="mord47_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box47" style="height: 100%; display: none;">
					<form name="box47Form" id="box47Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/employcondition/ea_getemployconditionList.jspa?carInformation.carID="${carInformation.carID}="
											src="" name="main" width="100%" marginwidth="0"
											style="overflow: scroll;" height="380" marginheight="0"
											frameborder="0" id="mainframe47" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆维护信息-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box48">
					<tr>
						<td height="27" class="txt03">
							车辆维护信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box48',48,'edit')"
								id="mord48" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box48',48,'close')"
								id="mord48_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box48" style="height: 100%; display: none;">
					<form name="box48Form" id="box48Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
												url="ea/carmaintain/ea_getListCarMaintain.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe48"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 相关证件子集-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box8">
					<tr>
						<td height="27" class="txt03">
							相关证件子集
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box8',8,'edit')"
								id="mord8" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box8',8,'close')"
								id="mord8_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box8" style="height: 100%; display: none;">
					<form name="box8Form" id="box8Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">

										<iframe
											url="/ea/certificateatable/ea_getCertificateaTableList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe8" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 机动车行驶证-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box9">
					<tr>
						<td height="27" class="txt03">
							机动车行驶证
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box9',9,'edit')"
								id="mord9" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box9',9,'close')"
								id="mord9_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box9" style="height: 100%; display: none;">
					<form name="box9Form" id="box9Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/motorcar/ea_getMotorcarList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe9" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 道路运输证-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box10">
					<tr>
						<td height="27" class="txt03">
							道路运输证
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box10',10,'edit')"
								id="mord10" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box10',10,'close')"
								id="mord10_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box10" style="height: 100%; display: none;">
					<form name="box10Form" id="box10Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carroad/ea_getCarRoadList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" scrolling="no" frameborder="0"
											id="mainframe10" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<!-- 车用瓶使用证-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box31">
					<tr>
						<td height="27" class="txt03">
							车用瓶使用证
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box31',31,'edit')"
								id="mord31" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box31',31,'close')"
								id="mord31_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box31" style="height: 100%; display: none;">
					<form name="box31Form" id="box31Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/bottle/ea_getBottleList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0"
											style="overflow: scroll;" height="380" marginheight="0"
											frameborder="0" id="mainframe31" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆购置税证-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box32">
					<tr>
						<td height="27" class="txt03">
							车辆购置税证
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box32',32,'edit')"
								id="mord32" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box32',32,'close')"
								id="mord32_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box32" style="height: 100%; display: none;">
					<form name="box32Form" id="box32Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carpurchase/ea_getPurchaseList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe32" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 车辆违章信息-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box33">
					<tr>
						<td height="27" class="txt03">
							车辆违章信息
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box33',33,'edit')"
								id="mord33" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box33',33,'close')"
								id="mord33_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box33" style="height: 100%; display: none;">
					<form name="box33Form" id="box33Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carviolate/ea_getCarViolateList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe33" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<!-- 车辆准载座位-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box52">
					<tr>
						<td height="27" class="txt03">
							车辆准载座位
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box52',52,'edit')"
								id="mord52" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box52',52,'close')"
								id="mord52_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box52" style="height: 100%; display: none;">
					<form name="box52Form" id="box52Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
												url="ea/carquasi/ea_getCarseatList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe52" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<!-- 调离管理-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box15">
					<tr>
						<td height="27" class="txt03">
							调离管理
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box15',15,'edit')"
								id="mord15" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box15',15,'close')"
								id="mord15_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box15" style="height: 100%; display: none;">
					<form name="box15Form" id="box15Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
											url="ea/carassectinformation/ea_getSafetyHealthList.jspa?docstatus=01&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid="
											src="" name="main" width="100%" marginwidth="0"
											height="500px" marginheight="0" frameborder="0"
											id="mainframe15" border="0" framespacing="0"
											noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				
				<!--车辆状态-->
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="biaoti box16">
					<tr>
						<td height="27" class="txt03">
							车辆状态
						</td>
						<td align="right">
							<a href="javascript:" onclick="changemenu('box16',16,'edit')"
								id="mord16" class="mord" style="color: #0066FF">修改</a>&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="changemenu('box16',16,'close')"
								id="mord16_close" class="mord isHide" style="color: #0066FF;">取消</a>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<div id="box16" style="height: 100%; display: none;">
					<form name="box16Form" id="box16Form" method="post">

						<table width="99%" border="0" align="center" cellpadding="0"
							cellspacing="0" class="table studentSharge">
							<tr>
								<td>
									<input type="submit" name="submit" style="display: none" />
									<div style="height: 100%; width: 100%;">
										<iframe
												url="ea/carstatus/ea_getCarStatusList.jspa?carInformation.carID="${carInformation.carID}"
											src="" name="main" width="100%" marginwidth="0" height="380"
											marginheight="0" frameborder="0" id="mainframe16" border="0"
											framespacing="0" noresize="noResize" vspale="0"> </iframe>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>

			</div>
		</div>
		<%------------------------------------社会人力选择------------------------------------%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择人员
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td align="right">
								人员姓名：
							</td>
							<td>
								<input name="staffName" class="input" id="staffName"
									style="margin-left: 2px;" size="5" />
							</td>
							<td align="right">
								身份证：
							</td>
							<td>
								<input name="staffIdentityCard" class="input"
									id="staffIdentityCard" style="margin-left: 2px;" size="5" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="chaxun" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdpeople" name="button5"
									value="确定" />
								<input type="button" class="btn02 JQueryreturngoods"
									name="button4" value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<%-----------------公司在职员工---------%>
		<form name="Staffform" id="Staffform" method="post">
			<div id="bankJqm" class="jqmWindow"
				style="width: 95%; height: 400px; absolute; display: none; left: 2.5%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
				<iframe name="daoRu" id="daoRu" width="100%" height="360px"
					frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
				<div align="center">
					<input type="button" class="input-button" id="DaoRuFanqd"
						value=" 确定 " style="cursor: hand; border: 0;" />
					<input type="button" class="input-button" id="DaoRuFan"
						value=" 关闭 " style="cursor: hand; border: 0;" />
				</div>

			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
		<script type="text/javascript">

function gotoLogin(){
	document.location= basePath + "page/ea/not_login.jsp";
}


$(function(){   
	setTimeout(function(){ 
        $("div.gdkd").css({"height":GetPageSize()[3]-320+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.gdkd").css({"height":GetPageSize()[3]-320+"px"});
		 },100);
		 }); 	
});



	
var scrollPos="";
window.onscroll=function(){
	 
	if (typeof window.pageYOffset != 'undefined')    //针对Netscape 浏览器
    { 
         scrollPos = window.pageYOffset; 
     } 
     else if (typeof document.compatMode != 'undefined' &&   document.compatMode != 'BackCompat')
     { 
         scrollPos = document.documentElement.scrollTop; 
     } 
   else if (typeof document.body != 'undefined') 
    { 
        scrollPos = document.body.scrollTop; 
	} 
};

function GetPageSize(){
    var xScroll, yScroll;
    if (window.innerHeight  &&  window.scrollMaxY) { 
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else {
        xScroll = document.body.offsetWidth;
        yScroll = document.body.offsetHeight;
    }
    var windowWidth=0, windowHeight=0;
    if (self.innerHeight) {
        windowWidth = self.innerWidth;
        windowHeight = self.innerHeight;
    } else if (document.documentElement  &&  document.documentElement.clientHeight) {
        windowWidth = document.documentElement.clientWidth;
        windowHeight = document.documentElement.clientHeight;
    } else if (document.body) {
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
    } 
    if(yScroll < windowHeight){
        pageHeight = windowHeight;
    } else { 
        pageHeight = yScroll;
    }
    if(xScroll < windowWidth){ 
        pageWidth = windowWidth;
    } else {
        pageWidth = xScroll;
    }
    arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight); 
    return arrayPageSize;
}
</script>
	</body>
</html>