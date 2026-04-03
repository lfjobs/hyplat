<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String filepath = request.getSession().getServletContext().getRealPath("/");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>收集个人</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/human/staff_info.js"></script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/cstaff/cstaff_collect.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

		<script type="text/javascript"
			src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
		var pbasePath = '<%=basePath%>';
		var basePath = '<%=basePath%>';
		var ppageNumber = ${pageNumber};
		var search = '${search}';
		var pstaffID = '${staffID}';
		var personvalue = "";
		var personurl = "";
		var personIdentityCard;
		var staffsize = 0 ;//后台验证身份证时应该查到的人数
		var token =0;
		var staffName;
		var retoken = 0;
		var notoken = 0;
		var photosizes = 0;
		var sdate="${sdate}";
		var edate="${edate}";
		var address = "";//身份证地址
		var opaNum = 0; //客户类别传值number 
		
		</script>
		</head>
		<body>
		<p style="display: none;">
			<object classid="clsid:E6E0A751-541A-4855-9A8D-35EB7122C950" id="SynIDCard1" name="SynIDCard1" codeBase="<%=basePath %>WEB-INF/plug-in/SynIDCard.Cab#version=1,0,0,1" width="0" height="0">
			  <param name="_Version" value="65536"/>
			  <param name="_ExtentX" value="635"/>
			  <param name="_ExtentY" value="582"/>
			  <param name="_StockProps" value="0"/>
			</object>
			<textarea rows="17" name="S1" cols="82"></textarea>
		</p>
		<div class="main_main">
		
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="100" align="center">
							人员编号
						</th>

						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<!--  
						<th width="250" align="center">
							身份证地址
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="100" align="center">
							qq
						</th>
						<th width="100" align="center">
							邮箱
						</th>
						<th width="100" align="center">
							录入时间
						</th>
						<th width="100" align="center">
							政治面貌
						</th>
						<th width="100" align="center">
							文化程度
						</th>
						<th width="100" align="center">
							婚姻状况
						</th>
						<th width="100" align="center">
							健康状况
						</th>
						<th width="100" align="center">
							银行帐号
						</th>
						<th width="100" align="center">
							备注
						</th>
						<th width="100" align="center">
							客户类别
						</th>
						-->
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[1] }" >
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[1] }" />
							</td>
							<td>
								<span id="staffCode">${arr[3]}</span>
							</td>
							<td>
								<span id="recordCode">${arr[4]}</span>
							</td>
							<td>
								<span id="staffName">${arr[5]}</span>
							</td>
							<td>
								<span id="usedNmae">${arr[6]}</span>
							</td>
							<td>
								<span id="sex">${arr[7]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${arr[19]}</span>
							</td>
							<td>
								<span id="nativePlace">${arr[8]}</span>
							</td>
							<td>
								<span id="nationality">${arr[9]}</span>
							</td>
							<td>
								<span id="nation">${arr[11]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${arr[12]}</span>
								<span style="display: none" id="staffKey">${arr[0]}</span>
								<span style="display: none" id="staffID">${arr[1]}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${arr[10]}</span>
							</td>
							<td>
								<span id="reference">${arr[15]}</span>
							</td>
							<td>
								<span id="referenceCode">${arr[16]}</span>
							</td>
							<td>
								<span id="referenceOrganization">${arr[14]}</span>
							</td>
							<td>
								<span id="verifyTime" class="datas">${arr[17]}</span>
							</td>
							<td>
								<span id="politicsStatus">${arr[21]}</span>
							</td>
							<td>
								<span id="culturalDegree">${arr[22]}</span>
							</td>
							<td>
								<span id="marriage">${arr[23]}</span>
							</td>
							<td>
								<span id="health">${arr[24]}</span>
							</td>
							<td>
								<span id="bankNum">${arr[25]}</span>
							</td>
							<td>
								<span id="staffDesc">${arr[18]}</span>
								<span style="display: none" id="address">${arr[13]}</span>
							</td>
							<td>
								<span id="contactconnection">${arr[31]}</span>
							</td>
							-->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/stafftrack/ea_getStaffList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
				</c:param>
			</c:import>
			
		</div>
		<iframe src="" name="main" marginwidth="0" style="width:100%;height:0;" marginheight="0" scrolling="no" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize" ></iframe>
		<!-- 添加客户类别 -->
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectcode">
			<div class="drag">
				添加客户类别
			</div>
			<table>
				<tr>
					<td>
						客户类别：
					</td>
					<td align="left" class="code">
						<select id="province" number='0' style="width: 110px;"></select>
						<!-- <option>选择省</option>-->
						<select id="city" number='1' style="width: 110px; display: none;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<!-- <option>选择省</option>-->
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savecode" value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
	
		<!-- 添加客户类别 -->
		<form name="clientCodeForm" id="clientCodeForm" method="post">
			<div class="jqmWindow jqmWindowcss3" style="top: 10%" id="jqModelkf">
				<input type="submit" name="submit" style="display:none"/>
				<div class="content1">
					<div class="contentbannb">
						<div class="drag">
							添加客户类别
							<div class="close"></div>
						</div>
					</div>
					<table width="485" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
					        <td width="20%" height="44" align="right" class="td_bg">序号：</td>
						    <td width="80%" class="td_bg"><input readonly="readonly" class="input01" type="text" size="20" id="codeNumber"/>
					    <tr>
					        <td height="45" align="right" class="td_bg"><font color="#ff0000">*</font>代码名称：</td>
					        <td height="45" class="td_bg iscode"><input maxlength="50"  type="text" class="input01  put3 ckTextLength" id="codeValue" size="20"/></td>
					    </tr>
					    <tr>
					        <td height="45" align="right" class="td_bg">代码描述：</td>
					        <td height="45" class="td_bg"><textarea style="height: 80px;" maxlength="250"  class="input01 ckTextLength"  id="desc"></textarea>
					    </tr>
						<tr>
							<td colspan="2" align="center">
								<input type="hidden" id="codePID"/>
								<input type="hidden" id="parmiter"/>
								<input id="treenum" type="hidden" class="input" />
								<input type="button" class="input-button JQuerySubmitkf"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input type="button" class="input-button JQueryreturnkf"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		<div class="menu01">
			<ul>
				<li>
					<ul class="menu00" style="z-index: 1">
						<!--  
                                    <li>
                                        <a href="#" id="pdetails">人员基本信息</a>
                                    </li>
                                -->
						<li>
							<a href="#" id="Address">地址管理</a>
						</li>
						<li>
							<a href="#" id="contact">联系方式</a>
						</li>
						<li>
							<a href="#" id="education">学历学位</a>
						</li>
						<li>
							<a href="#" id="precord">个人履历</a>
						</li>
						<li>
							<a href="#" id="showfamily">家庭成员</a>
						</li>
						<li>
							<a href="#" id="showhealth">健康状况</a>
						</li>
						<li>
							<a href="#" id="political">政治面貌</a>
						</li>
						<li>
							<a href="#" id="award">奖励情况</a>
						</li>
						<li>
							<a href="#" id="punishment">处分情况</a>
						</li>
						<li>
							<a href="#" id="showassurance">社会保险</a>
						</li>
						<li>
							<a href="#" id="survey">调查情况</a>
						</li>
						<li>
							<a href="#" id="credentials">证件列表</a>
						</li>
						<li>
							<a href="#" id="documentation">资料列表</a>
						</li>
						<li>
							<a href="#" id="personalRecord">人事档案</a>
						</li>
						<li>
							<a href="#" id="bankAccount">银行帐号</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 45%; top: 10%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td width="100" align="right">
							人员姓名：
						</td>
						<td>
							<input name="cstaff.staffName" />
						</td>
					</tr>
					<tr>
						<td width="60" align="right">
							收集时间：
						</td>
						<td>
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />至<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
                       	<td align="right" align="right">
                          	客户类别：     
                        </td>
					   <td>
					   	    <select id="contactConnections"></select>
					   </td>
                    </tr>
                    <!--  
					<tr>
						<td height="40" align="right">
							身份证地址：
						</td>
						<td class="JQueryaddresssearch" id="JQueryaddresssearch">
							<input name="cstaff.address" id="address" type="hidden" />
							<input name="cstaff.staffAddress" id="staffAddress"
								type="hidden" />
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;"></select>
							<!-- <option>选择省</option>
							<select name="addressCity" id="city" number='1'
								style="width: 110px;"></select>
							<select name="addressCounty" id="county" number='2'
								style="width: 110px;"></select>
							<select name="addressTown" id="addressTown" number='3'
								style="width: 110px;"></select>
							<select name="addressVillage" id="addressVillage" number='4'
								style="width: 110px;"></select>
							<select name="addressCommunity" id="addressCommunity" number='5'
								style="width: 110px;"></select>
							<!-- <option>选择省</option>
							<select name="addressFloor" id="addressFloor" number='6'
								style="width: 110px;"></select>
							<select name="addressLayer" id="addressLayer" number='7'
								style="width: 110px;"></select>
							<select name="addressSize" id="addressSize" number='8'
								style="width: 110px;"></select>
						</td>
					</tr>
					-->
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
		</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 30 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			$("#mainframe").css({"height": _height / 2 - 30 + "px"});
		    }else{		    
			$(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>  
	</body>
</html>
