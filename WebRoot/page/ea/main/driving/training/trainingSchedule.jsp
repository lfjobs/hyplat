<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@page import="hy.ea.bo.Company"%>
<%@page import="java.util.Date"%>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company) session.getAttribute("currentcompany");
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<title>学员培训进度表</title><!-- 此页面部分内容来至于产品设计-->
		
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"  type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
      
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/ea/driving/training/trainingSchedule.js"></script>
		
		

		<script type="text/javascript">
  	var treeID = "<%=session.getAttribute("organizationID")%>";
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var basePath = "<%=basePath%>";
	var token = 0;
	var pNumber="${pageNumber}";
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var journalNum = "";
	var notoken = 0;
	var select=${fn:length(productPackagingList)+1};
	var treeid="";
	var treename="";
	var depotPID="";
	var depotID="";
	var type="${type}";
	var status="${status}"
	var jumptype = "${jumptype}";
	var xmtype="${xmtype}";
	var xmtypename="${param.xmtypename}";
	var billID = "${billID}";
	var summary="${summary}";
	var zorg = "${zorg}";
	var zorgname = "${zorgname}";
	var cashierBillsID="${cashierBills.cashierBillsID}";
	var  billsType = "${billsType}";
	var zctype = "${zctype}";
	var placeholder="";//占位符
	var selects =${fn:length(productPriceCategoryList)+1};;//克隆行标识符
	var addOrEdit="add";//区别物品添加与修改操作
	var parentId="${productDesign.ppID}";
	var studentid="${cstaff.staffID}";
	

	


	</script>
	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
	
		<div id="apDiv1"></div>
		<form name="CostSheetForm" id="CostSheetForm" method="post"
			enctype="multipart/form-data">
			
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
							<input type="button" class="menubtn" name="button3"
								value="帮助" /><input type="button" class="menubtn closewindow" 
								value="关闭" />
								<input type="hidden" value="${jumptype}" name="jumptype"/> 
							</nobr>	
						</td>
					</tr>
				</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
		  <div id="content1" style="border: 1px solid #99bbe8;margin-top: 10px;">
		  <table class="linetable" id="table3" cellpadding="10" cellspacing="5" width="950px" >
					<tr>
						<td  align="center" colspan="5">学员培训进度表</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">学员编号：</td>
						<td width="23%"><span id="staffCode" class="inputbottom">${cstaff.staffCode }</span></td>
						<td width="12%" align="right">档案编号：</td>
						<td width="23%" done0="9" done1="9"><span id="recordCode" class="inputbottom">${cstaff.recordCode }</span></td>
						<td width="30%" rowspan="6" align="center" id="phototd" style="border:1px solid #000; width: 252px;height: 80px;overflow: hidden;background: url('<%=basePath%>${cstaff.photo }') no-repeat 0 0 ;background-size:271px 107px;">
							
						</td>
					</tr>
					<tr>
						<td  align="right">姓名：</td>
						<td done0="10" done1="10"><span id="staffName" class="inputbottom">${cstaff.staffName }</span></td>
						<td align="right">曾用名：</td>
						<td done0="11" done1="11"><span id="usedNmae" class="inputbottom">${cstaff.usedNmae }</span></td>
					</tr>
					<tr>
						<td  align="right">性别：</td>
						<td done0="12" done1="12"><span id="sex" class="inputbottom">${cstaff.sex }</span></td>
						<td align="right">出生日期：</td>
						<td done0="13" done1="13"><span id="birthday" class="inputbottom">${cstaff.birthday }</span></td>
					</tr>
					<tr>
						<td  align="right">民族：</td>
						<td><span id="nation" class="inputbottom">${cstaff.nation }</span></td>
						<td align="right" class="inputbottom">籍贯：</td>
						<td><span id="nativePlace" class="inputbottom">${cstaff.nativePlace }</span></td>
					</tr>
					<tr>
						<td align="right">国籍：</td>
						<td><span id="nationality" class="inputbottom">${cstaff.nationality }</span></td>
						<td  align="right">身份证号码：</td>
						<td><span id="staffIdentityCard" class="inputbottom">${cstaff.staffIdentityCard }</span></td>
					</tr>
					<tr>
						<td  align="right">电话：</td>
						<td><span id="nation" class="inputbottom">${cstaff.reference }</span></td>
						<td align="right">报考驾照：</td>
						<td><span id="nativePlace" class="inputbottom">${dtDrivingPrincipal.registrationcarname }</span></td>
					</tr>
				</table>
		</div>

		<div id="Layer1">
			<table class="table bg auto_arrange .goodtable2" id="goodtable" >
									<tr>
										<th align="left" height="20" bgcolor="#E4F1FA"   >
											<span >&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span> 
										</th>
										<th align="center" width="80" bgcolor="#E4F1FA" >
											<span > 项目编号</span>
										</th>
										<th align="center" width="80" bgcolor="#E4F1FA" >
											<span > 项目名称</span>
										</th>
										<th align="center" width="50"  bgcolor="#E4F1FA" >
											<span >  总学时</span>
										</th>
										<th align="center" width="50" bgcolor="#E4F1FA" >
											<span >  完成学时</span>
										</th>
										<th align="center" width="50" bgcolor="#E4F1FA" >
											<span > 剩余学时</span>
										</th>
										
										<th align="center"  bgcolor="#E4F1FA" >
											<span >  进度</span>
										</th>
									</tr>
									
									<c:forEach var='c' items="${productPackagingList}" varStatus="status">
											<tr id="kelong${status.index + 2 }" class="checkgoods">
												<!-- 层级 -->
													
												<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
													<span class="hierarchy" style="margin-left: 5px;"></span><img src="<%=basePath%>/images/ea/human/fy_03.gif" title="展开下级"  class="showOrHideChildren" style="cursor: pointer;"/>
												</td>
												<!-- 项目编号 -->
												
												<td align="center" bgcolor="#FFFFFF">
												<input type="hidden"  id="goodsID" value="${c[0]}"/>
													<div class="tdiv">
													<input id="goodsNum"  style="width:70%;border:0px;"  class="querys fou" value="${c[1]}"/>
													&nbsp;<input type="btn" class="shuju querybtn caz" /></div>
												</td>
												<!-- 项目名称 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="goodsName"  style="width:70%;border:0px;"  class="querys fou"  value="${c[2] }"
														/>&nbsp;<input type="btn" class="shuju querybtn caz" />
												</div>
												</td>
												<!-- 总学时 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="quantity"  style="width:90%;border:0px;"  class="  jisuan"  value="${c[3] }"
														/>
												</div>
												</td>
												<!-- 完成学时 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="time"  style="width:90%;border:0px;"  class="  jisuan"  value="${c[4] }"
														/>
												</div>
												</td>
												<!-- 剩余学时 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="remaining"  style="width:90%;border:0px;"  class="  jisuan"  value="${c[5] }"
														/>
												</div>
												</td>
												<td align="left" bgcolor="#FFFFFF">
														<div id="p" class="easyui-progressbar" style="width:500px;"></div>
														<input type="hidden" id="ppID"  value="${c[6] }"/>
														<input type="hidden" id="parentId"   value="${c[7] }"/>
												</td>
											</tr>
									</c:forEach>
									
								  <tr id="kelong" style="display:none;">
										
										<!-- 层级 -->
											
										<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
											<span class="hierarchy" style="margin-left: 5px;"></span><img src="<%=basePath%>/images/ea/human/fy_03.gif"  title="展开下级"  class="showOrHideChildren" style="cursor: pointer;"/>
										</td>
										<!-- 项目编号 -->
										<td align="center" bgcolor="#FFFFFF">
										<input type="hidden" name="goodsID" id="goodsID" />
											<div class="tdiv">
											<input id="goodsNum" name="goodsNum" style="width:70%;border:0px;"  class="querys fou"
												/>&nbsp;<input type="btn" class="shuju querybtn caz" /></div>
										</td>
										<!-- 项目名称 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="goodsName" name="goodsName" style="width:70%;border:0px;"  class="querys fou" 
												/>&nbsp;<input type="btn" class="shuju querybtn caz" />
										</div>
										</td>
										<!-- 总学时 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="quantity" name="quantity" style="width:90%;border:0px;"  class=" fou " 
												/>
										</div>
										</td>
										<!-- 完成学时 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="time" name="time" style="width:90%;border:0px;"  class=" fou " 
												/>
										</div>
										</td>
										<!-- 剩余学时 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="remaining" name="remaining" style="width:90%;border:0px;"  class=" fou " 
												/>
										</div>
										</td>
										<td align="left" bgcolor="#FFFFFF">
											 	<div id="p" class="easyui-progressbar" style="width:500px;"></div>
												<input type="hidden" id="ppID" name="ppID"  />
												<input type="hidden" id="parentId" name="parentId"  />
										</td>
									</tr>
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
							
		  				
		</div>
       </div>
			
			<input type="hidden" name="sub" value="${session_value}"/>
		</form>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>

</body>
</html>

