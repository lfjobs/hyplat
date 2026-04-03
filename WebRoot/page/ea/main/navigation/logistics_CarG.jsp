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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>办公室(生产)-后勤管理-车辆管理集团汇总</title>

		<style type="text/css">
		div{
		width:200;
		height: 60;
		}
		.img1{
		cursor:pointer;
		}
		body{
		font-size: 12px;
		font-family: cursive;
		}
		.bg{border:solid 2px #7AABCD;}
		.checkItem{display:none; position:absolute;top:25px;left:42px; width:500px;background-color: #FFFFFF;border:2px solid #7AABCD;padding:8px;}
		.checkItem li{ float:left; padding:0 16px; list-style: none; font-size:14px;}
		.checkItem li a{text-decoration: none;color:#666;}
		.checkItem li:hover{background-color:#7AABCD;}
		.checkItem li.curr a,.checkItem li a:hover{ text-decoration: underline; color:#FFFFFF;}
		</style>
<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>	
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<style type="text/css">

	body .center_a{
           height:30px;
		}
</style>
	</head>
<body>
<table width="1000" border="0">
  <tr>
    <td rowspan="3"><div class="na_back_img_ks"></div><div class="center_a">
      <strong>车辆管理办</strong></div></td>
    <td><div class="na_back_img_jt_xs"></div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/cargroup/ea_getCarInformationList.jspa?type=g'"/></div><div class="center_a"> 车辆信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/cardmanage/ea_getCarGooutRecord.jspa?type=g'"/></div><div class="center_a"> 车辆门禁
    </div></td>
    <td><div id="checkItem" style="position:relative;" align="center"><img src="<%=basePath%>images/04.gif" width="40" height="40"  class="img1"/><br/>安全巡检
    <ul class="checkItem" >
        
        <li><a href="<%=basePath%>ea/safetycheck/ea_point.jspa">检查点</a></li>
        <li><a href="<%=basePath%>ea/safetycheck/ea_pointitem.jspa">检查项</a></li>
        <li><a href="<%=basePath%>ea/safetycheck/ea_plan.jspa">巡检计划</a></li>
        <li><a href="<%=basePath%>ea/safetycheck/ea_task.jspa">巡检任务</a></li>
        <li><a href="<%=basePath%>ea/safetycheck/ea_results.jspa">检查结果</a></li>
      
        <%--<li><a href="<%=basePath%>ea/safetycheck/ea_chart.jspa">统计图</a></li>
    --%></ul>
    </div></td>
    <td><div class="na_back_img" 
	onclick="document.location.href='<%=basePath%>ea/carnum/ea_getListCarByCompanyID.jspa?type=g'"/></div><div class="center_a"> 车牌号维护
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carinvoice/ea_getCarInvoiceList.jspa?type=g'"/></div><div class="center_a"> 购车发票
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carpurchasetax/ea_getCarPurchaseTaxList.jspa?type=g'"/></div><div class="center_a"> 购置税发票
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carinsurance/ea_getCarInsuranceList.jspa?type=g'"/></div><div class="center_a"> 购置保险信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carareview/ea_getCarAReviewList.jspa?type=g'"/></div><div class="center_a"> 购置年检信息
    </div></td>
  </tr>
  <tr>
    <td><div align="center"></div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carcng/ea_getCarCNGList.jspa?type=g'"/></div><div class="center_a"> 车辆CNG信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carsafeinformation/ea_getCarsafeinformationList.jspa?type=g'"/></div><div class="center_a"> 车辆安全信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?type=g'"/></div><div class="center_a"> 车辆资产信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/employcondition/ea_getemployconditionList.jspa?type=g'"/></div><div class="center_a"> 车辆使用信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carmaintain/ea_getListCarMaintain.jspa?type=g'"/></div><div class="center_a"> 车辆维护信息
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/certificateatable/ea_getCertificateaTableList.jspa?type=g'"/></div><div class="center_a"> 相关证件子集
    </div></td>
    <td><div class="na_back_img"
	onClick="document.location.href='<%=basePath%>/ea/motorcar/ea_getMotorcarList.jspa?type=g'"/></div><div class="center_a"> 机动车行驶证
    </div></td>
    <td><div class="na_back_img"
	onClick="document.location.href='<%=basePath%>/ea/carroad/ea_getCarRoadList.jspa?type=g'"/></div><div class="center_a"> 道路运输证
    </div></td>
  </tr>
  <tr>
    <td><div class="na_back_img_jt_xx"></div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/bottle/ea_getBottleList.jspa?type=g'"/></div><div class="center_a"> 车用瓶使用证
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carpurchase/ea_getPurchaseList.jspa?type=g'"/></div><div class="center_a"> 车辆购置税证
    </div></td>
    <td><div class="na_back_img"
	onclick="document.location.href='<%=basePath%>/ea/carviolate/ea_getCarViolateList.jspa?type=g'"/></div><div class="center_a"> 车辆违章信息
    </div></td>
    <td><div class="na_back_img" 
	onclick="document.location.href='<%=basePath%>/ea/carquasi/ea_getCarseatList.jspa?type=g'"/></div><div class="center_a"> 车辆准载座位
    </div></td>
    <td><div class="na_back_img"
    onclick="document.location.href='<%=basePath%>/ea/carassectinformation/ea_getSafetyHealthList.jspa?type=g'"/></div><div class="center_a"> 安全卫生检查
    </div></td>
  </tr>
  <tr>
    <td><div class="na_back_img_ks"></div><div class="center_a">
      <strong>接送预约接<br/>送到达管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
    <td><div  class="na_back_img" 
	onclick="document.location.href='<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa?'"/></div><div class="center_a"> 接送预约<br/>信息管理
    </div></td>
    <td><div  class="na_back_img"  
	onclick="document.location.href='<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa?'"/></div><div class="center_a"> 接送预约信息<br/>报表管理
    </div></td>
    <td><div  class="na_back_img" 
	onclick="document.location.href='<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa?'"/></div><div class="center_a"> 接送基本<br/>信息管理
    </div></td>
    <td><div  class="na_back_img" 
	onclick="document.location.href='<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa?'"/></div><div class="center_a"> 接送信息<br/>报表管理
    </div></td>
    <td><div  class="na_back_img" 
	onclick="document.location.href='<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa?'"/></div><div class="center_a"> 接送到达基<br/>本信息管理
    </div></td>
    <td><div  class="na_back_img" 
	onclick="document.location.href='<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa?'"/></div><div class="center_a"> 接送到达信<br/>息报表管理
    </div></td>
  </tr>
</table>
<script type="text/javascript"> 
$(function(){
    $("#checkItem").hover(function(){
        $(this).addClass("bg").children("ul").show();
    },function(){
        $(this).removeClass("bg").children("ul").hide();
    });
});
</script>
</body>
</html>
