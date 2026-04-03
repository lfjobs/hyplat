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
	<title>客户咨询</title>
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
		src="<%=basePath%>js/ea/office_ea/contactcompany/customerAsk.js"></script>
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
							咨询单位
						</th>
						<th width="200" align="center">
							单位联系人
						</th>
						<th width="100" align="center">
							联系人电话
						</th>
						<th width="100" align="center">
							联系人性别
						</th>
						<th width="100" align="center">
							联系人QQ
						</th>
						<th width="100" align="center">
							联系人微信
						</th>
						<th width="100" align="center">
							咨询问题
						</th>
						<th width="80" align="center">
							咨询时间
						</th>
						<th width="80" align="center">
							是否已处理
						</th>
						<th width="80" align="center">
							处理时间
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${customerAsk}">
						<tr id="${arr[1] }" >
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[1] }" />
							</td>
							<td>
								<span id="customerName">${arr[3] }</span>
							</td>
							<td>
								<span id="linkman">${arr[4] }</span>
							</td>
							<td>
								<span id="phone">${arr[5] }</span>
							</td>
							<td>
								<span id="sax">${arr[6] }</span>
							</td>
							<td>
								<span id="qq">${arr[7] }</span>
							</td>
							<td>
								<span id="wxNumber">${arr[8] }</span>
							</td>

							<td>
								<span id="content">${arr[9] }</span>
							</td>
							<td>
								<span id="createTime">${arr[10] }</span>
							</td>
							<td>
								<s:if test="arr[11]=='0'">
									<span id="askType">未处理</span>
								</s:if><s:else>
									<span id="askType">已处理</span>
								</s:else>
							</td>
							<td>
								<span id="answerTime">${arr[12] }</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/companytrack/ea_getCustomerAskList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
				</c:param>
			</c:import>
		</div>
		<iframe src="" name="main" marginwidth="0"
					scrolling="no" style="width:100%;height:0;" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
	
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
