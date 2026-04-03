<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		 <%@page import="hy.ea.bo.Company"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			  Company c = (Company)session.getAttribute("currentcompany");
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>集团--人事生产-人员列表</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script
			src="<%=basePath%>js/ea/company/human/staffcos_company.js">
		</script>
		 <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var ppageNumber = '${pageNumber}';
            var search = '${search}';   
            var personvalue = "";
            var personIdentityCard;
            var personurl = "";
            var staffName="";
            var retoken = 0;
            var token = 0;
            var notoken = 0;
            var comID = '<%=c.getCompanyID()%>';
		 	var comName = '<%=c.getCompanyName()%>';
        </script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="200" align="center">
							公司名称
						</th>
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							员工工种
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							部门名称
						</th>
						<th width="100" align="center">
							岗位名称
						</th>
						<th width="80" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="80" align="center">
							籍贯
						</th>
						<th width="80" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="lists">
						<tr id="${lists[0]}" class="${lists[4]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${lists[0]}" />
							</td>
							<td>
								<span id="companyname">${lists[28]}</span>
							</td>
							<td>
								<span id="staffCode">${lists[1]}</span>
							</td>
							<td>
								<span id="categoryname">${lists[3]==null?"暂未分配":lists[3]}</span>
							</td>
							<td>
								<span id="staffName">${lists[4]}</span>
							</td>
							<td>
								<span id="organizationName">${lists[5]}</span>
							</td>
							<td>
								<span id="postName">${lists[6]}</span>
							</td>
							<td>
								<span id="sex">${lists[8]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${lists[9]}</span>
							</td>
							<td>
								<span id="nativePlace">${lists[11]}</span>
							</td>
							<td>
								<span id="nation">${lists[12]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${lists[13]}</span>
								<span style="display: none" id="schedulingID">${schedulingID}</span>
								<span style="display: none" id="staffKey">${lists[25]}</span>
								<span style="display: none" id="address">${lists[26]}</span>
								<span style="display: none" id="staffID">${lists[0]}</span>
								<span style="display: none" id="photo">${lists[27]}</span>
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
					value="ea/staffcoscompany/ea_getStaffCosList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import> 
		</div>
		<iframe src="" name="main" scrolling="no" style="width:100%;height:0;"
					marginwidth="0" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div class="jqmWindow jqmWindowcss3" style="width: 400px; top: 10% ;align:center"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="395" id="cataffSearchTable">
					<tr>
						<td width="122" align="right">
							人员编号：
						</td>
						<td width="261">
							<input name="cosStaff.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="cosStaff.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证：
						</td>
						<td>
							<input name="cosStaff.staffIdentityCard" />
						</td>
					</tr>
					<tr>
						<td align="right">
							公司：
						</td>
						<td>
							<select id="companyID" name="cosStaff.companyID">
								<option value="">
									请选择公司
								</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value="查询" />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
	
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