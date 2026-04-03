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
			String filepath = request.getSession().getServletContext().getRealPath("/");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人员列表</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/ea/validate.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/ea/staff.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
		<script type="text/javascript" src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_info.js"></script>
<script type="text/javascript">
	var treeID = '<%=session.getAttribute("organizationID")%>';
	var pbasePath = '<%=basePath%>';
	var basePath = '<%=basePath%>';
	var ppageNumber = '${pageNumber}';
	var psearch = '${search}';
	var pstaffID = '${staffID}';
	var personvalue = "";
	var personurl = "";
	var personIdentityCard;
	var staffName;
	var staffsize = 0 ;//后台验证身份证时应该查到的人数sssddd
	var token =0;
	var retoken = 0;
	var notoken = 0;
	var photosizes = 0;
	var aa="${aa}";
	var maxNum = 0;
	var opaNum = 0; //往来关系传值number  
	var status = '${cstaff.status}';
	var titlename = "社会人力管理";
	var showType='<%=request.getParameter("showType")%>';
	if(status == "00"){
		titlename = "一般人物管理";
	}else if(status == "01"){
		titlename = "政界人物管理";
	}else if(status == "02"){
		titlename = "商界人物管理";
	}else if(status == "03"){
		titlename = "学术界人物管理";
	}else if(status == "04"){
		titlename = "艺术界人物管理";
	}else if(status == "05"){
		titlename = "科学界人物管理";
	}
	//判断flexigrid中的button
	var flexbutton = '<%=request.getParameter("flexbutton") %>';
    function gotoLogin(){
	    document.location="<%=basePath%> /page/ea/not_login.jsp";
	}
	
	var showType = "${showType}";
	var parameter = "${parameter}";

</script>
</head>
<body onload="closeBg()">
		<!--JS遮罩层-->   
		<div id="fullbg" style="filter:Alpha(Opacity=100);background-color: #ECFFFF"></div>   
		<!--endJS遮罩层-->   
		<!--对话框-->   
		<div id="dialog" >   
		<div id="dialog_content" >
		 <br/><div align='center'><img  src="<%=basePath%>images/jdt.gif"/><div style="margin-top: 10px;">正在加载中...</div></div> </div>   
		</div>   
		<!--JS遮罩层上方的对话框--> 
		<script type="text/javascript">
			showBg('dialog','dialog_content');
		</script>
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
			<table class="JQueryflexme" cellpadding="0" cellspacing="0">
				<thead>
					<tr class="tablewith">
						<th width="30" align="center">
							选择
						</th>
						<th width="70" align="center">
							人员编号
						</th>
						<th width="70" align="center">
							人员姓名
						</th>
						<th width="40" align="center">
							性别
						</th>
						<th width="170" align="center">
							身份证
						</th>
						<th width="110" align="center">
							最常用联系方式
						</th>
						<th width="90" align="center">
							出生日期
						</th>
						<th width="50" align="center">
							民族
						</th>
						<th width="100" align="center">
							来源
						</th>
						<th width="70" align="center">
							录入人员
						</th>
						<th width="70" align="center">
							邀请人
						</th>
					</tr>
				</thead>
				<tbody>

					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${staffID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${staffID}" />
							</td>
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
								<span style="display: none" id="staffKey">${staffKey}</span>
								<span style="display: none" id="staffID">${staffID}</span>
								<span style="display: none" id="photo">${photo}</span>
								<span style="display: none" id="staus">${staus}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="source">${source}</span>
							</td>
							<td>
								<span id="source">${entryPersonnel}</span>
							</td>
							<td>
								<span id="accountName">${accountName}&nbsp;${accountID}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${search}&pageNumber=${pageNumber}&aa=${aa }&flexbutton=${param.flexbutton}&cstaff.status=${cstaff.status}&showType=${showType}&parameter=${parameter}">
				</c:param>
			</c:import> 
			
		</div>
		<iframe src="" name="main"  id="mainframe"  frameborder="0" border="0"  framespacing="0" style="height:0;"></iframe>
		
		
		<div class="jqmWindow" style="width: 500px; right: 40%;; top: 10%"  id="JQueryaddress">
			<form name="SearchForm1" id="SearchForm1" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					请选择地区
					<div class="close">
					</div>
				</div>
				<table width="500" id="cataffSearchTable">										
						<tr class="trheight">						
						<td colspan="4" class="JQueryaddress">
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;">
							</select>
							<select name="addressCity" id="city" number='1'
								style="width: 110px;">
							</select>
							<select name="addressCounty" id="county" number='2'
								style="width: 110px;">
							</select>
							<select name="addressTown" id="addressTown" number='3'
								style="width: 110px;">
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch1" onclick="getAddress()"
						value="确定" />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<form name="AuditionForm" id="AuditionForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow jqmWindowcss2" style="width: 700px; top: 10%"
				id="jqMode2">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					员工招聘信息
					<div class="close">
					</div>
				</div>
				<table width="642" border="0" id="stafftable" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td>
							<table width="699" height="263" border="0" id="stafftable2"
								align="center" cellpadding="0" cellspacing="0"
								style="margin-top: 5px; margin-bottom: 5px;">
								<tr>
									<td width="82" height="46" align="right">
										员工姓名：
									</td>
									<td width="168">
										<input type="text" id="staffName" readonly="readonly" />
									</td>
									<td width="109" align="right">
										身份证号：
									</td>
									<td width="181">
										<input type="text" id="staffIdentityCard" readonly="readonly" />
									</td>
									<td width="159" rowspan="3" align="center">
										<img id="photo" width="99" height="135" />
									</td>
								</tr>
								<tr>
									<td height="46" align="right">
										应聘方向：
									</td>
									<td>
										<input id="auditionDirection"
											name="audition.auditionDirection" type="text" class="input"
											size="20" />
									</td>
									<td align="right">
										应聘岗位：
									</td>
									<td>
										<input id="auditionPost" name="audition.auditionPost"
											type="text" class="input" size="20" />
									</td>
								</tr>
								<tr>
									<td height="27" align="right">
										工作经验：
									</td>
									<td colspan="3">
										<textarea class="ckTextLength" maxlength="250" cols="45"
											name="audition.experience" rows="5" id="experience"></textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="hidden" name="audition.staffID" id="staffID" />
					<input name="sub" value="${session_value}" type="hidden" />
					<!-- 代替token-->
					<input type="button" class="input-button" id="Auditiontj"
						value=" 提交 " />
					<input type="button" class="input-button JQueryreturn" value=" 取消 " />
				</div>
			</div>
		</form>
		
		<!-- 添加到往来个人 -->
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectcode">
			<div class="drag">
				添加往来个人
			</div>
			<table>
				<tr>
					<td>
						往来关系：
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
	
		<!-- 添加往来关系 -->
		<form name="cstaffCodeForm" id="cstaffCodeForm" method="post">
			<div class="jqmWindow jqmWindowcss3" style="top: 10%" id="jqModelkf">
				<input type="submit" name="submit" style="display:none"/>
				<div class="content1">
					<div class="contentbannb">
						<div class="drag">
							添加往来关系
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
					        <td height="45" class="td_bg iscode"><input maxlength="50"  type="text" class="input01 ckTextLength" id="codeValue" size="20"/></td>
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
		
				<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();	
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 80 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		   }else{		    
		   	   $(".bDiv").css({"height": _height - 31 - 30 - 26 - 70 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
			setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 80 + "px"});
			    $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		    }else{		 
		   	    $(".bDiv").css({"height": _height - 31 - 30 - 26 - 70 + "px"});
				$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
   function getAddress(){
		
		if($("#province ").find("option:selected").text()=="--请选择--"){
			$("#province ").text("");
			}
		 var province=$("#province ").find("option:selected").text();
		 if($("#city ").find("option:selected").text()=="--请选择--"){
			 $("#city ").text("");
			 }
		 var city=$("#city ").find("option:selected").text();
		 if($("#county ").find("option:selected").text()=="--请选择--"){
			 $("#county ").text("");
			 }
		 var county=$("#county ").find("option:selected").text();
		 if($("#addressTown ").find("option:selected").text()=="--请选择--"){
			 $("#addressTown ").text("");
			 }
		 var addressTown=$("#addressTown ").find("option:selected").text();
		 if(addressTown!=""){
			 address=addressTown;
		 }else if(county!="" && addressTown==""){
			 address=county;
	     }else if(city!="" && county=="" && addressTown==""){
			 address=city;
		 }else{
			 address=province;
			 }
		$("#JQueryaddress").jqmHide();
		$("#staffAddress").val(address);
	  }
</script> 
	</body>
</html>