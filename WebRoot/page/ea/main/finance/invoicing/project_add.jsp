<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>项目管理</title>
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
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
			<link href="<%=basePath%>css/css.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/project_add.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
			
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />

		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
			
<style type="text/css">
#jqModel {
	display: none;
	overflow: auto;
	border: 1px solid #a8c7ce;
	width: 300px;
	height: 350px;
	position: absolute;
	top: 31%;
	left: 25%;
	z-index: 999999;
	background-color: #e1ecfc;
	filter: Alpha(opacity = 100);
}
a{
color:#0066CC;
}

input{

height:20px;
}

.selectedListrow{
color:#FFFFFF;
background-color:navy;


}

</style>

		<script type="text/javascript">
	 	var treeID = "<%=session.getAttribute("organizationID")%>";
	  	var treeNames="<%=session.getAttribute("organizationName")%>";
	var basePath = "<%=basePath%>";

	var token = 0;
	var pNumber="${pageNumber}";
	var search="${search}";
	var companyName="<%=c.getCompanyName()%>";
	var companyID = "<%=c.getCompanyID()%>";
	var notoken = 0;
	var xmtype = "${projectManage.xmtype}";
	var type="${type}";
	var view ="${view}";
	var status = "${projectManage.status}";
	var proID = "${projectManage.proID}";
	var pproID = "${projectManage.pproID}";





	
</script>

	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">

		<form name="ProjectForm" id="ProjectForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display:none;"/>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
							<input type="button"  class="menubtn JQuerySubmitgd grey"  value="保存" disabled="disabled"/><input type="button" class="menubtn add grey" name="button3" disabled="disabled"
								value="增加" /><input type="button"  class="menubtn updatesheet grey"  disabled="disabled"
								value="修改" /><input type="button"  class="menubtn deletesheet grey"  disabled="disabled"
								value="删除" /><input type="button" class="menubtn addline" name="button3" 
								value="帮助" /><input type="button" class="menubtn JQueryClose" 
								value="关闭" />
							
							</nobr>	
						</td>
					</tr>
				</table>
				<div id="maindiv">
		<div id="content1" style="border:1px solid #c5d9f1;margin-top:10px;padding-bottom:50px;">
			<table 
					class="linetable" id="table3"  >
					 <tr><td align="center" colspan="8" id="titlestyle" >新建项目</td></tr>
				<tr>
					<td align="right"><span class="STYLE1">公司：</span>
					</td>
					<td>
					<input type="text" class="inputbottom" style="width:180px;" name="projectManage.companyName" value="<%=c.getCompanyName() %>"/>
					<input type="hidden" value="${projectManage.organizationID}"   name="projectManage.organizationID" value="${projectManage.organizationID}"/>
			        <input type="hidden" value="${projectManage.companyID}"   name="projectManage.companyID" value="${projectManage.companyID}"/>
					
					</td>
					<td align="right">部门：</td>
					<td>
					<input type="text"  style="width:120px;" class="inputbottom" name="projectManage.organizationName" value="<%=session.getAttribute("organizationName")%>"/>
					</td>
					<td align="right"><span class="xx">*</span>责任人：</td>
					<td colspan="3">
					<%-- 获取责任人将姓名编号ID均保存 --%>
					 <input type="text" value="${projectManage.staffName}"  name="projectManage.staffName" id="staffname" class="model1 notnull inputbottom" readonly style="width:150px;display: inline;" />
					 <input type="button" class="btncon deptbtn" />
					 <input type="text" value="${projectManage.staffID}"  style="display:none;" name="projectManage.staffID" id="staffid"/>
					 <input type="text" value="${projectManage.staffCode}"  style="display:none;" name="projectManage.staffCode" id="staffcode"/>
					 <input type="text" value="${projectManage.createDate}"  style="display:none;" name="projectManage.createDate"/>
					</td>
				</tr>
				<tr>
					<td align="right"><span class="xx">*</span>项目分类：</td>

					<td style="vertical-align:middle;"><input  class="model1 inputbottom notnull" readonly style="display:inline;width:180px;"
						type="text" id="xmtypename" name="projectManage.xmtypename" value="${projectManage.xmtypename}"
						/>
						<input type="hidden" name="projectManage.xmtype" id="xmtype"  value="${projectManage.xmtype}"/>
						
						<input type="button" class="btncon projectbtn" />
						</td>
						
				    <td align="right"><span class="STYLE1"><span
							class="xx">*</span>上级项目：</span>
				    </td>
					<td>
					
					<input type="text" id="pprojectName"  name="projectManage.pprojectName"  value="${projectManage.pprojectName}"  class="model1 inputbottom notnull"   readonly  style="width:120px;display: inline;" /> 
					<input type="hidden" id="pproID"  name="projectManage.pproID" value="${projectManage.pproID}"/> 
					<input type="button" class="btncon projectbtn" />
				   </td>
                         
                    <td align="right"><span class="STYLE1"><span
							class="xx">*</span>项目名称：</span></td>
					<td ><input type="text" id="projectName" class="model1 inputbottom notnull"   style="width:150px;display: inline;" 
						name="projectManage.projectName"  value="${projectManage.projectName}"/> 
						 <!--key和ID用于更新 -->
						<input type="hidden" name="projectManage.proID" id="proID" value="${projectManage.proID}"/>
						<input type="hidden" name="projectManage.proKey" id="proKey" value="${projectManage.proKey}"/>
					</td>
					

					
				</tr>
				<tr>

					<td height="30" align="right"><span class="xx">*</span>起时间：</td>
					<td align="left" ><input type="text" id="startTime" style="width:120px;display: inline;" 
						onfocus="date(this);" name="projectManage.startDate" 
						class="notnull model1 inputbottom" value="${fn:substring(projectManage.startDate,0,10)}"/></td>
					<td align="right" >
							<span class="xx">*</span>止时间：</td>
					<td align="left"><input type="text" id="endTime"
						onfocus="date(this);" name="projectManage.endDate"  style="width:120px;display: inline;" 
						class="notnull model1 inputbottom"  value="${fn:substring(projectManage.endDate,0,10)}"/></td><%--

					<td height="30" align="right"><span class="STYLE1"><span
							class="xx">*</span>项目编号：</span></td>
					<td><input type="text" id="projectCode"   readonly class="inputbottom"
						name="projectManage.projectCode" style="width:180px;" value="${projectManage.projectCode}"/>
						
						</td>
					


					--%><td align="right">项目规划：</td>
					<td colspan="5">
					<input type="hidden" name="projectManage.docIds" 
								id="docIds"  value="${projectManage.docIds}"/>
							<input type="text" id="xmgh" name="projectManage.titles" class="model1 inputbottom " style="width:150px;display: inline;" 
								 readonly value="${projectManage.titles}"/>
							<input type="button" class="btncon" id="planxz"/>
						
							</a>
					</td>

				     </tr>

				<tr>
				<tr class="industry" style="display:none;">

					<td align="right">行业：</td>
					<td align="left" ><input  class="model1 xiaoguo" style="display:inline;"
						type="text" id="industry"  name="industryName" value="${pbyIndusty.industryName}"/>
						
						<input type="hidden"  name="industryID"  id="industryID" value="${pbyIndusty.industryID}"/>
						<!-- key和ID用于更新 -->
		
						</td>
					<td align="right" >
							业务员：
					</td>
					<td align="left" colspan="5">

                       <%-- 获取业务员将姓名编号ID均保存 --%>
					 <input type="text" name="salesman" value="${pbyIndusty.salesman}" id="salesman" class="querys model1 xiaoguo"  style="display: inline;" />
					 <input type="text" style="display:none;" name="salesmanID" id="salesmanid" value="${pbyIndusty.salesmanID}"/>
					 <input type="text" style="display:none;" name="salesmanCode" id="salesmancode" value="${pbyIndusty.salesmanCode}"/>
					</td>
				     </tr>
				

				<tr>
					<td height="30" align="right">应得分：</td>
					<td align="left"><input type="text" id="ydsocre" style="display: inline;" 
						name="projectManage.ydsocre" class="isNaN model1 inputbottom" value="${projectManage.ydsocre}"/>
					</td>
					<td align="right">奖分：</td>
					<td><input type="text" id="jfscsore" style="display: inline;" 
						name="projectManage.jfscore" class="isNaN model1 inputbottom" value="${projectManage.jfscore}"/>
					</td>
					<td align="right">扣分：</td>
					<td><input type="text" id="kfscore" class="isNaN model1 inputbottom" style="display: inline;" 
						name="projectManage.kfscore" value="${projectManage.kfscore}"/>
					</td>
					<td align="right">实得分：</td>
					<td><input type="text" id="sdscore" style="display: inline;" 
						name="projectManage.sdscore" class="isNaN model1 inputbottom" value="${projectManage.sdscore}"/>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
	           <tr >

						<td align="right" >
							<span class="xx">*</span>项目描述：
						</td>
						<td align="left" colspan="7">
							<textarea  rows="5" style="width:50%;" name="projectManage.content" class="put3 ckTextLength" maxlength="200" >${projectManage.content}</textarea>
						</td>
						
						
					</tr>

			</table>
			
             <!-- 车辆信息 -->
			<table width="99%" border="0"  id="cltabl"  align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table" style="display:none;">

				<tr>
					<td align="center" colspan="8"><strong>车辆信息</strong></td>
				</tr>

				<tr>

					<td align="right"><span class="xx">*</span>车辆编号：</td>
					<td align="left"><input type="text"  class="model1 querys xiaoguo" value="${projectMcar.carCode}"  name="carCode"  id="carCode" style="display: inline;" />
					
					</td>
					<td align="right">车牌号：</td>
					<td align="left"><input type="text"  class="model1 querys xiaoguo"  name="carNum"  value="${projectMcar.carNum}" id="carNum" style="display: inline;" /></td>
					<td align="right">车架号：</td>
					<td align="left"><input type="text"  class="model1 querys xiaoguo" name="carFrameNum" id="carFrameNum" value="${projectMcar.carFrameNum}" style="display: inline;" /></td>
					<td align="right">发动机号：</td>
					<td align="left"><input type="text"  class="model1 querys xiaoguo"  name="engineNum" id="engineNum" style="display: inline;" value="${projectMcar.engineNum}"/>
					
					<input type="hidden"   name="carID" id="carID"  value="${projectMcar.carID}"/>
					</td>

				</tr>
			</table>


          <!-- 招生信息 -->
			<table width="99%" border="0" id="recruiterTable" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table" style="display:none;">
				<tr>
					<td align="center" colspan="8"><strong>招生信息</strong>
					</td>
				</tr>
				<tr>
					<td align="right"><span class="STYLE1">分校\报名点：</span>
					</td>
					<td style="width:230px;">
					<select id="assignsID"  class="model1" style="display:none;"></select> 
					    <input type="hidden" id="applyPlaceName" name="applyPlaceName"
						value="${pbyIndusty.applyPlaceName}" /></td>
						<input type="hidden" id="applyPlaceID" name="applyPlaceID"
						value="${pbyIndusty.applyPlaceID}" /> 
						
					<td align="right"><span class="STYLE1">招生员：</span></td>
					<td>
						<input type="hidden" id="recruiterID" 
						name="recruiterID" value="${pbyIndusty.recruiterID}" />
						<input type="hidden" id="recruiterCode"   
						name="recruiterCode" value="${pbyIndusty.recruiterCode}" />
						<input type="text" id="recruiterName" class="querys model1"  style="display: inline;" 
						name="recruiterName" value="${pbyIndusty.recruiterName}" /></td>
					
					
					
					<td align="right"><span class="STYLE1">招生员联系方式：</span></td>
					<td style="width:150px;">
						<input id="phone" type="text" style="border:0px;" readonly
						name="phone" value="${pbyIndusty.phone}" /></td>
				</tr>
				<tr>
					<td height="30" align="right"><span class="STYLE1">招生员身份证号：</span>
					</td>
					<td><input id="identityCard" name="identityCard" style="border:0px;"
						value="${pbyIndusty.identityCard}" size="30"  readonly/>
					</td>
					<td align="right"><span class="STYLE1">邮箱：</span>
					</td>
					<td><input id="email" type="text" name="email" style="border:0px;"
						value="${pbyIndusty.email}" size="25" readonly/>
					</td>
					<td align="right"><span class="STYLE1">地址：</span>
					</td>
					<td colspan="3"><input id="address" type="text"  style="border:0px;"
						name="address" value="${pbyIndusty.address}" size="50"  readonly/>
				
					</td>

				</tr>
			</table>

		
				
		
			
			<s:token></s:token>
			</div>
			<div style="margin-top:10px;">
		备注：<input type="text" name="projectManage.remark" class="inputbottom" style="width:96%;" value="${projectManage.remark}" />
		<p>创建人：<input type="text" class="inputbottom" size="15" value="${staff.staffName}"/>&nbsp;&nbsp;&nbsp;创建日期：<input type="text" class="inputbottom" id="createD" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" size="20" >
		</p>
		</div>
			</div>
			</div>
			
				<input type="hidden" name="sub" value="${session_value}"/>
		</form>
		
		
		
		<!-- 查询 -->
    
	<div id="goodsQuery" style="white-space:wrap;display:none;width:950px;height:320px;background:#E4F1FA;border:#a8c7ce 1px solid">
	  <div class="divtx" style="height:30px;line-height:30px;">查询</div><div class="close closes"></div>
	  <div style="width:950px;height:270px;overflow:auto;background:#E4F1FA;">
		<table class="table" cellpadding="10"  width="100%">
		<thead id="goodthead">
			
			</thead>
			<tbody id="goodboy">
			
			
			</tbody>
		</table>
     </div>
  <center>
		<table id="querya">
			<tr>
				<td width="80"><a id="wpsyq" style="cursor:pointer;" title="0" >上一页</a></td>
				<td width="80"><a id="wpxyq"  title="0" style="cursor:pointer;">下一页</a></td>
				<td width="100"><a id="wpzyq">共&nbsp;&nbsp; <span
						style="color: red;" id="wpzycountq"></span>&nbsp;&nbsp;页 </a>
						<input type="hidden"  id="querys"/>
					    <input type="hidden"  id="types"/>
						</td>
			</tr>
		</table>
		</center>
	</div>
	<!-- 查询结束 -->
	
	
    <!-- 查询项目分类 -->
	<div id="jqModel">
		<div id="treeBoxs"></div>
	</div>
	
	
	<%------------------------------------选择项目规划------------------------------------%>
		<form name="selectplanForm" id="selectplanForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="planjqModel">
				<div class="content1" style="width: 100%; height: 400px">
					<div class="contentbannb">
						<div class="drag">
							选择项目规划
						</div>
						<div class="close"></div>
					</div>
					<table width="99%" height="33" id="plantbl" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="60" align="right">
								规划名称：
							</td>
							<td width="50">
								<input name="parameter" class="input" id="pp" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								规划类型：
							</td>
							<td width="100">
								<select id="module">
									<option value="">
										全部
									</option>
									<option value="cg">
										公司规划
									</option>
									<option value="dg">
										部门规划
									</option>
									<option value="pg">
										个人规划
									</option>
									<option value="jg">
										职业规划
									</option>
								</select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchplan"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="qdplan" name="button5"
									value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" value="" id="search1" />

							</td>
							<td width="50">
								<a id="plsy" title="0" style="cursor: default">上一页</a>
							</td>
							<td width="50">
								<a id="plxy" title="0" style="cursor: default">下一页</a>
							</td>
							<td width="70">
								<a id="plzy">共&nbsp;&nbsp; <span style="color: red"
									id="plzycount"></span>&nbsp;&nbsp; 页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 0px; height: 430px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02plan"
									style="margin-top: 2px; display: none; width: 100%; overflow: auto; height: 320px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		
			<%------------------------------------项目分类和项目------------------------------------%>
		
		<form name="selectxmForm" id="selectxmForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="xmjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							项目信息
						</div>
					</div>
					<table width="99%" height="33" id="searchxm"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								项目名称：
							</td>
							<td width="142">
								<input name="parameter" class="input" id="parameterxm"
									size="10" style="margin-left: 2px;" />
								<input  type="hidden" id="selectxm" />
								<input  type="hidden" id="selectxms" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchxmbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdxm" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzxm" name="button5"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								
			
							</td>
							<td width="80">
								<a id="xmsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="xmxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="xmzy">共&nbsp;&nbsp; <span style="color: red"
									id="xmzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="20%">
								<table width="100%" cellpadding="0" cellspacing="0">
								
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
										
											<div class="text_tree" id="treeg" 
												style="overflow: auto; z-index: 99; height: 280px;width:250px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttree.jsp?codeID=scode201410284shpd9x4fa0000000005&title=项目分类"   width="600" height="300"></iframe></div>
											<div class="mohuc text_tree" style="overflow: auto; z-index: 99; width:250px;height: 280px;display:none;"></div>
											<div style="margin-top:5px;float:right;">模糊查询<input type="text" size="10" id="inputmoc" /><input type="button" class="btncon" id="moc" ></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="80%" valign="top" align="left">
								<div
								style="margin-top: 2px;  height: 310px; width: 100%; overflow: auto;">
								<table width='98%' height='26' align='center' cellspacing='0'
									cellpadding='1' style='font-size:12px;' class='bannb_01'>
									<tr>
										<td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择上级项目</td>
									     <td height='24' align='left' valign='top' class='txt01'>(温馨提示：如不选择上级项目,视为添加新项目)</td>
									
									</tr>
								</table>
								<table width='99%' align='center' id='xmtable' cellpadding='0'
									cellspacing='0' class='table'>
									<thead>
										<th align='center' bgcolor='#E4F1FA' width='3%'>选择</th>
										<th align='center' bgcolor='#E4F1FA' width='3%'>序号</th>
										<th align='center' bgcolor='#E4F1FA' width='15%'>项目编号</th>
										<th align='center' bgcolor='#E4F1FA' width='20%'>项目名称</th>
										<th align='center' bgcolor='#E4F1FA' width='12%'>项目分类</th>
										<th align='center' bgcolor='#E4F1FA' width='6%'>负责人</th>
										<th align='center' bgcolor='#E4F1FA' width='6%'>创建人</th>
									</thead>
									<tbody id="body_02xm"></tbody>
								</table>
								</div>
									</td>
									</tr>
								</table>
							</div>
							</div>
			<s:token></s:token>
		</form>
		
		<%------------------------------------部门树和人 ------------------------------------%>
		<form name="selectdeptForm" id="selectdeptForm" method="post"
			enctype="multipart/form-data">
			
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="deptjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							组织机构
						</div>
					</div>
					<table width="99%" height="33" id="searchdept"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								员工姓名：
							</td>
							<td width="142">
								<input class="input" id="parameterrm"
									size="10" style="margin-left: 2px;" />
								<input type="hidden" id="selectdept"/>
								<input type="hidden" id="selectdeptname" />
								
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchdeptbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qddept" name="button5"
									value="确定" />
								
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									
			
							</td>
							<td width="80">
								<a id="dpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="dpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="dpzy">共&nbsp;&nbsp; <span style="color: red"
									id="dpzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="20%">
								<table width="100%" cellpadding="0" cellspacing="0">
								
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
										
											<div class="text_tree"
												style="overflow: auto; z-index: 99;width:170px; height: 280px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/organizationtree.jsp" width="250" height="270"></iframe></div>
											
										</td>
									</tr>
								</table>
							</td>
							<td width="80%" valign="top" align="left">
								<div 
									style="margin-top: 2px; height: 310px; width: 100%; overflow: auto;">
								<table width='98%' height='26' align='center' cellspacing='0'
									cellpadding='1' style='font-size:12px;' class='bannb_01'>
									<tr>
										<td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择员工</td>
									</tr>
								</table>
								<table width='99%' align='center' id='dptable' cellpadding='0'
									cellspacing='0' class='table'>
									<thead>
										<tr>
											<th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>序号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员编号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员姓名</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>性别</th>
											<th align='center' bgcolor='#E4F1FA' width='100'>出生日期</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>籍贯</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>手机号</th>
											<th align='center' bgcolor='#E4F1FA'>身份证</th>
										</tr>
									</thead>
									<tbody id="body_02dept"></tbody>
								</table>

							</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" height="0" width="0"></iframe>
</body>
</html>

