<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>值班管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript">
    var select = '01';
    var addressID = '';
	var basePath='<%=basePath%>';
    var ppageNumber=${pageNumber};
    var psearch='${search}';
    var token = 0;
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/onduty.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body >
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr >
	 	    <th width="40" align="center">选择</th>
            <th width="130" align="center" >部门名称</th>
            <th width="130" align="center" >部门负责人</th>
            <th width="130" align="center" >员工姓名</th>
            <th width="130" align="center" >值班室</th>
			 <th width="130" align="center" >值班时间</th>
            <th width="200" align="center" >负责范围</th>
            <th width="150" align="center" >工作记录</th>
            <th width="300" align="center" >备注</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${ondutyID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
            <td class="td_bg01">
                <span id="departmentName" class="datas">${departmentName}</span>
            <td class="td_bg01">
               <span id="headOfDepartment" class="datas">${headOfDepartment}</span>
            <td class="td_bg01">
               <span id="staffName" class="datas">${staffName}</span>
				</td>
			<td class="td_bg01">
                <span id="dutyRoom" class="datas">${dutyRoom}</span>
            <td class="td_bg01">
               <span id="attendedTime" class="datas">${attendedTime}</span>
            <td class="td_bg01">
               <span id="scope" class="datas">${scope}</span>
				</td>
			<td class="td_bg01">
               <span id="jobRecord" class="datas">${jobRecord}</span>
			</td>
            <td class="td_bg01">
             <span id="remark">${remark}</span>
					        <span id="ondutyKey" style="display:none">${ondutyKey}</span> <input type="hidden" name="onduty.ondutyID" value="${ondutyID}"/>   
					        <span id="ondutyID" style="display:none">${ondutyID}</span>  
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/onduty/ea_getListForPage.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;" id="jqModelSearch">
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
                          员工姓名：
                        </td>
                        <td>
                           <input   name="coWater.staffName" />
                        </td >
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top:10%"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/onduty/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">值班管理
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table  border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table height="152" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="105"  align="right">部门名称：</td>
			        <td width="195" ><input name="onduty.departmentName" type="text" id="departmentName" size="20"/></td>
					<td width="105"  align="right">部门负责人：</td>
			        <td width="195" ><input name="onduty.headOfDepartment" type="text" id="headOfDepartment" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="105"  align="right">员工姓名：</td>
			        <td width="195" ><input name="onduty.staffName" type="text" id="staffName" size="20"/></td>
					<td width="105"  align="right">值班室：</td>
			        <td width="195" ><input name="onduty.dutyRoom" type="text" id="dutyRoom" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="105"  align="right">值班时间：</td>
			        <td width="195" ><input name="onduty.attendedTime" type="text" id="attendedTime" size="20" onfocus="date(this);"/></td>
			        <td width="105"  align="right">工作记录：</td>
			        <td width="195" ><input name="onduty.jobRecord" type="text" id="jobRecord" size="20" /></td>
		           </tr>
		           <tr>
		           	<td width="105"  align="right">负责范围：</td>
			        <td width="595" colspan="4"><input name="onduty.scope" type="text" id="scope" style="width: 451px"/></td>
		           </tr>
				   <tr>
			        
					<td width="105"  align="right">备注：</td>
			        <td width="595" colspan="4"><input name="onduty.remark" type="text" id="remark" style="width: 451px"/>
									 <input type="hidden" name="onduty.ondutyKey" id="ondutyKey" />
					          	     <input type="hidden" name="onduty.ondutyID" id="ondutyID" />
						</td>
		           </tr>
				   
			     
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;"  value=" 保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value=" 取消 " />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
