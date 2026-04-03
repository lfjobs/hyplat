<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工资级别升降级单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
 <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script  type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
       <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
       <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript">
var  treeID ="<%=session.getAttribute("organizationID")%>";
  	var select = 01;
    var addressID = '';
    var basePath='<%=basePath%>';
    var ppageNumber=${pageNumber};
    var psearch='${search}';
    var token = 0;
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/payGradeModulation.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>

</head>
<body >
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>
</form>
<div id="main_main" class="main_main">
  <table   class="address JQueryflexme">
  <thead>
	 	    <tr >
	 	    <th width="30" align="center">选择</th>
            <th width="170" align="center" >公司</th>
            <th width="93" align="center" >凭证号</th>
            <th width="93" align="center" >部门</th>
            <th width="100" align="center" >工资级别升降级责任人</th>
            <th width="120" align="center" >工资级别升降级日期</th>
			 <th width="93" align="center" >人员编号</th>
            <th width="93" align="center" >升降级别</th>
            <th width="100" align="center" >原级别名细</th>
            <th width="100" align="center" >起时间</th>
			<th width="93" align="center" >止时间</th>
            <th width="93" align="center" >现级别名细</th>
            <th width="100" align="center" >自我评定</th>
            <th width="100" align="center" >调薪类别</th>
			 <th width="93" align="center" >调薪原因</th>
            <th width="93" align="center" >意见</th>
            <th width="100" align="center" >附件</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${pgmID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
            <td class="td_bg01">
                <span id="companyName">${company.companyName}</span>
            <td class="td_bg01">
                <span id="voucherNomber">${voucherNomber}</span>
            <td class="td_bg01">
            	 <span></span><s:select list="%{#request.orgList}" listKey="organizationID" listValue="organizationName" name="deptID" theme="simple" disabled="true"></s:select>
            <td class="td_bg01">
               <span id="dutyOfficer">${dutyOfficer}</span>
				</td>
			<td class="td_bg01">
                <span id="addTime">${fn:substring(addTime,0,10)}</span>
            <td class="td_bg01">
               <span id="staffCode">${staffCode}</span>
            <td class="td_bg01">
               <span id="currentLevel">${currentLevel}</span>
				</td>
			<td class="td_bg01">
               <span id="primaryLevel">${primaryLevel}</span>
			</td>
			 <td class="td_bg01">
                <span id="startDate">${fn:substring(startDate,0,10)}</span>
            <td class="td_bg01">
               <span id="endDate">${fn:substring(endDate,0,10)}</span>
            <td class="td_bg01">
               <span id="levelDetail">${levelDetail}</span>
				</td>
			<td class="td_bg01">
               <span id="selfRating">${selfRating}</span>
				</td>
			<td class="td_bg01">
                <span id="classes0">${classes0}</span>
            <td class="td_bg01">
               <span id="reason" >${reason}</span>
			   </td>
            <td class="td_bg01">
               <span id="suggestion" >${suggestion}</span>
              <span id="supervisor"  style="display:none">${supervisor}</span>
					        <span id="pgmKey" style="display:none">${pgmKey}</span>  
					        <span id="pgmID" style="display:none">${pgmID}</span> 
					         <span id="managerName" style="display:none">${managerName}</span>
							<span id="voucherNomber" style="display:none">${voucherNomber}</span>  
					        <span id="affix" style="display:none">${affix}</span>
							<span id="auditer" style="display:none">${auditer}</span>  
					        <span id="accountant" style="display:none">${accountant}</span> 
							<span id="casher" style="display:none">${casher}</span>  
					        <span id="headManager" style="display:none">${headManager}</span>
							<span id="headSupervisor" style="display:none">${headSupervisor}</span>  
					        <span id="headAuditer" style="display:none">${headAuditer}</span> 
							<span id="headAccountant" style="display:none">${headAccountant}</span> 
							 <span id="deptID" style="display:none">${deptID}</span> 
					        <span id="headCasher" style="display:none">${headCasher}</span>
							<input type="hidden" name="payGradeModulation.pgmID" id="pgmID" value="${pgmID}"/>
			</td> 
			 <td >
			  <s:if test="affix==null||affix==''">无</s:if>
                             <s:else>
                             <span id="look"  onclick="lookImage('${affix}');"><a href="#">查看</a></span>
                             </s:else>
           </td>
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/payGradeModulation/ea_getListForPage.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;top:10%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
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
                    </tr >
                    <tr>
                        <td>
                          凭证号：
                        </td>
                        <td>
                           <input   name="payGradeModulation.voucherNomber" />
                        </td >
                    </tr>
					<tr>
                        <td>
                          人员编号：
                        </td>
                        <td>
                           <input   name="payGradeModulation.staffCode" />
                        </td >
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
	
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/payGradeModulation/t_ea_save.jspa?pageNumber=${pageNumber}" enctype="multipart/form-data">
			     	<!--查看 -->
	<div class="jqmWindow jqmWindowcss1" style="top:0%"  id="jqModel">
			       <div class="drag">工资级别升降级单
				    <div class="close"></div>
		       </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
	<table width="890" height="46" id="stafftable" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px;">
    <tr>
      <td width="53" align="right">公司：</td>
      <td width="162"><input name="company.companyName" value="${company.companyName}" readonly="readonly" class="input" id="companyName" size="30"/></td>
      <td width="118" align="right">凭证号：</td>
      <td width="182"><input name="payGradeModulation.voucherNomber" class="input put3"" id="voucherNomber" size="10" /></td>
      <td width="66" align="right">附件：</td>      
      <td colspan="2" width="328">
      <input name="payGradeModulation.affix"  class="fileNum"  type="hidden" id="affix" size="15"/>
       <input name="photo" type="file" class="input" size="15" contentEditable="false"/></td>
      </tr>
    <tr>
      <td align="right">部门：</td>
      <td><select id="deptID" name="payGradeModulation.deptID" ></select></td>
      <td align="right">责任人：</td>
      <td><input name="payGradeModulation.dutyOfficer" class="input" id="dutyOfficer" size="10"/></td>
      <td colspan="2" align="right">日期：</td>
      <td width="328" align="left"><input name="payGradeModulation.addTime" class="input" id="addTime" onfocus="date(this);" size="20"/>      </td>
    </tr>
  </table>
  <table width="890" height="320" border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td width="142" height="20" align="right"><span class="xx">*</span>人员编号：</td>
      <td width="191"><input name="payGradeModulation.staffCode" class="input" id="staffCode" size="20" style="margin-left:2px;"/></td>
      <td width="116" align="right"><span class="xx">*</span>升降级别：</td>
      <td width="157"><input name="payGradeModulation.currentLevel" class="input" id="currentLevel" size="20" style="margin-left:2px;"/></td>
      <td width="120" align="right"><span class="xx">*</span>原级别名细：</td>
      <td width="164"><input name="payGradeModulation.primaryLevel" class="input" id="primaryLevel" size="20" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td height="20" align="right"><span class="xx">*</span>起时间：</td>
      <td><input name="payGradeModulation.startDate" class="input" id="startDate" size="20" style="margin-left:2px;" onfocus="date(this);"/></td>
      <td align="right"><span class="xx">*</span>止时间：</td>
      <td><input name="payGradeModulation.endDate" class="input" id="endDate" size="20" style="margin-left:2px;" onfocus="date(this);"/></td>
      <td align="right">现级别名细：</td>
      <td><input name="payGradeModulation.levelDetail" class="input" id="levelDetail" size="20" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td height="50" align="right"><span class="xx">*</span>自我评定：</td>
      <td colspan="5"><textarea name="payGradeModulation.selfRating" cols="80" rows="4" class="input" id="selfRating" style="margin-left:2px;"></textarea></td>
    </tr>
    <tr>
      <td height="35" align="right"><span class="xx">*</span>调薪类别：</td>
      <td colspan="5">
      <input type="radio" name="p" class="box1" value="试用合同予以转正"/>试用合同予以转正
      <input type="radio" name="p" class="box1" value="晋升调薪"/>晋升调薪
      <input type="radio" name="p" class="box1" value="调职调薪"/>
      调职调薪
      <input type="radio" name="p" class="box1" value="年度调薪"/>
      年度调薪
	  <input type="radio" name="p" class="box1" value=""/>
      其他（请注明）
      <input  name="payGradeModulation.classes0" id="classes0"   size="20"/></td>
    </tr>
    <tr>
      <td height="30" align="right"><span class="xx">*</span>调薪原因：</td>
      <td colspan="5"><textarea name="payGradeModulation.reason" cols="80" rows="4" class="input" id="reason" style="margin-left:2px;"></textarea></td>
    </tr>
    <tr>
      <td height="60" align="right"><span class="xx">*</span>意见：</td>
      <td colspan="5"><textarea name="payGradeModulation.suggestion" cols="80" rows="4" class="input" id="suggestion" style="margin-left:2px;"></textarea></td>
    </tr>
  </table>
  <table width="890" height="77" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px; margin-bottom:2px;">
    <tr>
      <td width="97" align="right">公司经理：</td>
      <td width="87"><input name="payGradeModulation.managerName" class="input" id="managerName" size="10" style="margin-left:2px;"/></td>
      <td width="93" align="right">部门主管：</td>
      <td width="90"><input name="payGradeModulation.supervisor" class="input" id="supervisor" size="10" style="margin-left:2px;"/></td>
      <td width="107" align="right">审核：</td>
      <td width="99"><input name="payGradeModulation.auditer" class="input" id="auditer" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">会计：</td>
      <td width="87"><input name="payGradeModulation.accountant" class="input" id="accountant" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">出纳：</td>
      <td width="84"><input name="payGradeModulation.casher" class="input" id="casher" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td align="right">总部总经理：</td>
      <td><input name="payGradeModulation.headManager" class="input" id="headManager" size="10" style="margin-left:2px;"/></td>
      <td align="right">总部主管：</td>
      <td><input name="payGradeModulation.headSupervisor" class="input" id="headSupervisor" size="10" style="margin-left:2px;"/></td>
      <td align="right">财务部审核：</td>
      <td><input name="payGradeModulation.headAuditer" class="input" id="headAuditer" size="10" style="margin-left:2px;"/></td>
      <td align="right">总会计：</td>
      <td align="left"><input name="payGradeModulation.headAccountant" class="input" id="headAccountant" size="10" style="margin-left:2px;"/></td>
      <td align="right">总出纳：</td>
      <td align="left"><input name="payGradeModulation.headCasher" class="input" id="headCasher" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td colspan="10" align="center">
        <input type="button"  class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
        <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /></td>
	  							<input type="hidden" name="payGradeModulation.pgmKey" id="pgmKey" />
					            <input type="hidden" name="payGradeModulation.pgmID" id="pgmID" />
    </tr>
  </table>
			</div>
			</div>
			    <s:token></s:token>
			    
			</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
