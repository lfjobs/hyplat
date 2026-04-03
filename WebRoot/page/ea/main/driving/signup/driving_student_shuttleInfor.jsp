<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接送信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/signup/driving_student_shuttleInfor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var drivingAllInformationID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${dtDrivingAllInformation.staffID}'; 
   var notoken = 0;
   var relationID='${dtDrivingAllInformation.relationID}';
   var mainheught = 0; //框架高度
   
   function getValueForParm(){ //打开页面
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe7").offsetHeight;
	  	parent.document.getElementById("mainframe7").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
	}
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe7").style.height = mainheught + 'px';
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffID").text();//弹出框的页面存在于span中才取得到
			var value4 = window.frames["ifr"].$('tr#'+value1).find("span#reference").text();//弹出框的页面存在于span中才取得到
			
			$("#"+drivingAllInformationID).find("#shuttleStaff").val(value2);
			$("#"+drivingAllInformationID).find("#shuttleStaffID").val(value3);
			$("#"+drivingAllInformationID).find("#shuttleStaffPhone").val(value4);
			$("#ifr").attr("src","");
			parent.document.getElementById("mainframe7").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>

  <body>
 		<form  name="contactForm" id="contactForm" method="post"><input type="submit" name="submit" style="display:none"/>
		<s:token></s:token>
<div id="main_main" class="main_main"> 
  <table class="contact">
  	<thead>
	    <tr>
	    <th width="30" align="center">选择</th>
	      <th width="100" align="center" title="接送地点">接送地点</th>
	      <th width="80" align="center" >接时间</th>
	      <th width="80" align="center" >送时间</th>
	      <th width="80" align="center" >接送责任人</th>
	      <th width="100" align="center" >接送责任人电话</th>
	      <th width="80" align="center" >接送车牌号</th>
	      <th width="80" align="center" >接送型号</th>
	      <th width="80" align="center" >乘坐人数</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td>
		          <input type="radio" name="a"  class="JQuerypersonvalue"  />
		      </td>
		      <td>
		      	  <input name="shuttleAddress" id="shuttleAddress" size="10"/>
		      </td>
		      <td class="td_bg01">
		      	  <input name="shuttleDate" id="shuttleDate"  size="10" onfocus="date(this);"/>
		      </td>
		      <td class="td_bg01">
		      	  <input name="endShuttleDate" id="endShuttleDate"  size="10" onfocus="date(this);"/>
		      </td>
		      <td>
		      	  <input name="shuttleStaff" id="shuttleStaff" size="10"/>
		      	  <input name="shuttleStaffID" id="shuttleStaffID" size="10" 	type="hidden"/>
		      	  <a  href="#" onclick="getValueForParm()">选择</a>
		      </td>
		      <td>
		      	  <input name="shuttleStaffPhone" id="shuttleStaffPhone" size="10"/>
		      </td>
		      <td>
		      	  <input name="shuttleCarNumber" id="shuttleCarNumber" size="10"/>
		      	  <a   id="newG" href="#" >选择</a>
		      </td>
		      <td>
		      	  <input name="shuttleCarXinHao" id="shuttleCarXinHao" size="10"/>
		      </td>
		      <td class="td_bg01">
		      	  <input name="shuttleSumPeople" id="shuttleSumPeople"  size="10" />
		      	  <input type="hidden" name="drivingAllInformationKey" id="drivingAllInformationKey"/>
				  <input type="hidden" name="drivingAllInformationID" id="drivingAllInformationID"/>
				  <input type="hidden" name="staffID" value="${dtDrivingAllInformation.staffID}" id="staffID"/>
				  <input type="hidden" name="relationID" value="${dtDrivingAllInformation.relationID}" id="relationID"/>
				  <input type="hidden" name="dataTitle" id="dataTitle" value="07"/>
		      </td>
		      
		      
	    </tr>
    <s:iterator value="beanList">
          <tr class="td_bg01 saveAjax trclass" id="${drivingAllInformationID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAllInformationID}"/>
	            	</td>
		            <td class="td_bg01"><span id="shuttleAddress">${shuttleAddress}</span>
      					<input class="model1" name="shuttleAddress"  value="${shuttleAddress}" size="10" />
      				</td>
      				  <td class="td_bg01">
				      <span id="shuttleDate" >${fn:substring(shuttleDate,0,11)}</span>
				      <input class="model1" name="shuttleDate" value="${shuttleDate}" id="shuttleDate" size="10" onfocus="date(this);"/>
      				</td>
				     <td class="td_bg01">
				      <span id="endShuttleDate" >${fn:substring(endShuttleDate,0,11)}</span>
				      <input class="model1" name="endShuttleDate" value="${endShuttleDate}" id="endShuttleDate" size="10" onfocus="date(this);"/>
				     </td>
				     
				     <td class="td_bg01"><span id="shuttleStaff">${shuttleStaff}</span>
      					<input class="model1" name="shuttleStaff" id="shuttleStaff" value="${shuttleStaff}" size="10" />
      					<a class="model1" href="#" onclick="getValueForParm()">选择</a>
      					<input class="model1" name="shuttleStaffID"  id="shuttleStaffID" value="${shuttleStaffID}" size="10" type="hidden"/>
      				</td>
      				<td class="td_bg01"><span id="shuttleStaffPhone">${shuttleStaffPhone}</span>
      					<input class="model1" name="shuttleStaffPhone" id="shuttleStaffPhone" value="${shuttleStaffPhone}" size="10" />
      				</td>
      				<td class="td_bg01"><span id="shuttleCarNumber">${shuttleCarNumber}</span>
      					<input class="model1" name="shuttleCarNumber" id="shuttleCarNumber" value="${shuttleCarNumber}" size="10" />
      					<a class="model1"  id="newG" href="#" >选择</a>
      				</td>
      				<td class="td_bg01"><span id="shuttleCarXinHao">${shuttleCarXinHao}</span>
      					<input class="model1" name="shuttleCarXinHao" id="shuttleCarXinHao" value="${shuttleCarXinHao}" size="10" />
      				</td>
				    <td class="td_bg01">
				      <span id="shuttleSumPeople">${shuttleSumPeople}</span>
      					<input class="model1" name="shuttleSumPeople" id="shuttleSumPeople"  value="${shuttleSumPeople}" size="10" />
				      
				      <input type="hidden"  name="drivingAllInformationKey" value="${drivingAllInformationKey}"  size="10"/>
				      <input type="hidden" name="drivingAllInformationID" value="${drivingAllInformationID}"/>
					   <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
					   <input type="hidden" name="relationID" id="relationID" value="${relationID}"/>
					   <input type="hidden" name="dataTitle" id="dataTitle" value="07"/>
				      </td>
    </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
</form>

<%-- 选择车辆信息  --%>
<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择车辆
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td  align="right">
								车牌号：
							</td>
							<td >
								<input name="carNum" class="input" id="carNum" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td  align="right">
								车架号：
							</td>
							<td >
								<input name="carFrameNum" class="input" id="carFrameNum" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td align="right">
								车型：
							</td>
							<td >
								<input name="carType" class="input" id="carType" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="chaxun"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="qdcar"
									name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturngoods" name="button4"
									value="关闭" />
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






<!-- 从当前部门的员工中选择责任人 -->
<div id="jqmWindow2" class="jqmWindow"
	style="width: 98%; height: 320px; absolute; display: none; left: 1%; top: 1%; background: #eff">
	<div align="center">
		<iframe name="ifr" id="ifr" width="100%" height="280px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
	</div>
 </div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){   
	setTimeout(function(){ 
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe7").offsetHeight-57+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe7").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>