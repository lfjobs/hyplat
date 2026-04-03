<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
			String filepath = request.getSession().getServletContext().getRealPath("/");
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人客户列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"	type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"	type="text/css" />
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>	
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>					
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
		<script type="text/javascript"src="<%=basePath%>js/accifr/js/accift.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/marketing/crmcustomer/crmcustomer_list.js"></script>
<script type="text/javascript">
	var pbasePath = '<%=basePath%>';
	var basePath = '<%=basePath%>';
	var ppageNumber = '${pageNumber}';
	var psearch = '${search}';
	var pstaffID = '${staffID}';
	var personvalue = "";//保存选中的个人客户的key键值
	var personurl = "";
	var personIdentityCard;
	var staffName;
	var staffsize = 0 ;//后台验证身份证时应该查到的人数sssddd
	var token =0;
	var retoken = 0;
	var notoken = 0;
	var photosizes = 0;
	
	var maxNum = 0;
	var opaNum = 0; //往来关系传值number  
	var status = '${cstaff.status}';
	var titlename = "社会人力管理";
	var showType='<%=request.getParameter("showType")%>';
	
	//判断flexigrid中的button
	var flexbutton = '<%=request.getParameter("flexbutton") %>';
</script>
<script>
//父页面必须用此方法，以供子页(弹出层返回数据)调用**********************
	function paret(res){
		//隐藏弹出层
	  	$("#accift").jqmHide();
		
		var val = res.split(',')[0];	  	
		var url = basePath
			+ "ea/marketingCrmCustomer/ea_getHrStaffByID.jspa?cstaffId="
			+ val + "&date=" + new Date().toLocaleString();		
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	};
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
						<th width="30" align="center">
							序号
						</th>
						<th width="70" align="center">
							客户编号
						</th>
						<th width="70" align="center">
							姓名
						</th>
						<th width="70" align="center">
							曾用名
						</th>
						<th width="40" align="center">
							性别
						</th>	
						<th width="70" align="center">
							证件
						</th>					
						<th width="170" align="center">
							身份证
						</th>
						<th width="110" align="center">
							意向产品
						</th>
						<th width="100" align="center">
							联系电话
						</th>
						<th width="70" align="center">
							推荐人
						</th>
						<th width="90" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							所属地区
						</th>						
						<th width="90" align="center">
							客户阶段
						</th>						
						<th width="100" align="center">
							地址
						</th>						
					</tr>
				</thead>
				<tbody>
				
					<s:iterator value="pageForm.list" id="customer" status="number">
						<tr id='<s:property value="#customer.customerid"/>'>
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value='<s:property value="#customer.customerid"/>'/>
							</td>
							<td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
							<td>
								<span id="customercode"><s:property value="#customer.customercode"/></span>
							</td>
							<td>
								<span id="customername"><s:property value="#customer.customername"/></span>
							</td>
							<td>
								<span id="usednmae"><s:property value="#customer.usednmae"/></span>
							</td>							
							<td>
								<span id="sex"><s:property value="#customer.sex"/></span>
							</td>
							<td>
								<span id="idtype"><s:property value="#customer.idtype"/></span>								
							</td>
							<td>
								<span id="identitycard" class="datas"><s:property value="#customer.identitycard"/></span>
							</td>
							<td>
								<span id="productid" class="datas"><s:property value="#customer.productid"/></span>
							</td>
							<td>
								<span id="reference"><s:property value="#customer.reference"/></span>
							</td>							
							<td>
								<span id="staffid"><s:property value="#customer.staffid"/></span>
							</td>
							<td>
								<span id="birthday"><s:property value="#customer.birthday"/></span>
							</td>
							<td>
								<span id="area"><s:property value="#customer.area"/></span>
							</td>
							<td>
								<span id="status"><s:property value="#customer.status"/></span>
							</td>
							<td>
								<span id="address"><s:property value="#customer.address"/></span>
							</td>
						</tr>						
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${search}&pageNumber=${pageNumber}&aa=${aa }&flexbutton=${param.flexbutton}&cstaff.status=${cstaff.status}&showType=${showType}">
				</c:param>
			</c:import> 
			
		</div>
		<form name="personForm" id="personForm" method="post">
		</form>
		<iframe src="" name="hidden"  id="mainframe"  frameborder="0" border="0"  framespacing="0" style="height:0;"></iframe>
					
	</body>
</html>