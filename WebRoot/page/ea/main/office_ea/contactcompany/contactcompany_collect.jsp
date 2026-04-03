<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>往来单位管理</title>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
		type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/ea/office_ea/contactcompany/contactcompany_collect.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
	<script type="text/javascript"src="<%=basePath%>js/accifr/js/accift.js"></script>
	<script type="text/javascript">
		 var ccompanyID = '';
		 var companyName = '';
		 var basePath='<%=basePath%>';           
		 var pNumber ='${pageNumber}';
		 var personvalue='';
		 var search='${search}';
		 var token = 0 ;
		 var personurl='';
		 var notoken = 0;
		 var retoken=0;
		 var select=1;
		 var companyName;
		 var sdate="${sdate}";
		 var edate="${edate}";
		 var opaNum = 0; //客户类别传值number
		 
$(document).ready(function() {
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择");
			return;
		}
		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#companyName").text();
		var address=window.frames["daoRu"].$('tr#'+childopertionID).find("span#staffAddress").text();
		var url = basePath+"/ea/companytrack/sajax_ea_getCompanybyID.jspa?contactCompany.ccompanyID="+childopertionID;
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
						var co=member.co;
						if(co==1){
							alert("不能重复添加");
							return;
						}else{
							window.location.href=basePath+"/ea/companytrack/ea_saveCompanyTrack.jspa?contactCompany.ccompanyID="+childopertionID+"&contactCompany.companyAddr="+address+"&contactCompany.companyName"+no;
							$("#daoRu").attr("src","");
        					$("#bankJqm").jqmHide();
						}
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");

					}
				});
		
   });
   });
</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="200" align="center">
							单位名称
						</th>
						<th width="220" align="center">
							单位地址
						</th>
						<th width="100" align="center">
							单位电话
						</th>
						<th width="80" align="center">
							单位负责人
						</th>
						<th width="100" align="center">
							单位负责人电话
						</th>
						<th width="100" align="center">
							行业类别
						</th>
						<th width="80" align="center">
							单位状态
						</th>
						<th width="100" align="center">
							客户类别
						</th>
						<th width="100" align="center">
							单位备注
						</th>
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
								<span id="companyName">${arr[2] }</span>
							</td>

							<td>
								<span id="companyAddr">${arr[4] }</span>
							</td>
							<td>
								<span id="companyTel">${arr[5] }</span>
							</td>
							<td>
								<span id="cresponsible">${arr[6] }</span>
							</td>
							<td>
								<span id="responsibleTel">${arr[7] }</span>
							</td>
							<td>
								<span id="industryType">${arr[10] }</span>
							</td>
							
							<td>
								<span id="custStatus">
									<c:if test="${arr[11] == '01'}">未注册单位</c:if>
									<c:if test="${arr[11] == '02'}">已注册单位</c:if>
								</span>
							</td>
							<td>
								<span id="contactconnection">${arr[12] }</span>
							</td>
							<td>
								<span id="remark">${arr[8] }</span>
								<span id="address" style="display: none">${arr[3] }</span>
								<span id="ccompanyID" style="display: none">${arr[1] }</span>
								<span id="ccompanyKey" style="display: none">${arr[0] }</span>
								<span id="dealIn" style="display: none">${arr[9] }</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/companytrack/ea_getCompanyList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
				</c:param>
			</c:import>
		</div>
		<iframe src="" name="main" marginwidth="0"
					scrolling="no" style="width:100%;height:0;" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
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
		<!-- 下拉菜单 -->
		<div class="menu01">
			<ul>
				<li>
					<ul class="menu00" style="z-index: 1">
						<li>
							<a href="#" id="dm1">证件管理</a>
						</li>
						<li>
							<a href="#" id="dm2">联系方式</a>
						</li>
						<li>
							<a href="#" id="dm3">银行帐号</a>
						</li>
						<li>
							<a href="#" id="dm4">个人列表</a>
						</li>
						<li>
							<a href="#" id="dm5">经营范围</a>
						</li>
					</ul>
				</li>
			</ul>
		</div> 

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
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
</script>  		
	</body>
</html>
