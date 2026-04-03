<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>设备借用申请</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/administrative/DeviceBorrow.js"></script>
        <script>
            var basePath = "<%=basePath%>";
            var debid = "";
            var search="${search}";
            var pageNumber = ${pageNumber};
            var token=0;
            var notoken= 0;
            var submittype="";//提交类型（保存草稿或者提交审核）
            var colsetype=0;
            var type="${param.type}";
</script>
	</head>
	<body>
	     <form name="submitcheckForm" id="submitcheckForm" method="post"
			enctype="multipart/form-data">
	     <input type="submit" name="submit" style="display: none" />
	     <input type="hidden" name="deborrow.id" id="id" />
	     </form>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="50" align="center">
                            序号
                        </th>
                        <th width="200" align="center">
                         单据编号
                        </th>
                        <th width="120" align="center">
                          设备名称
                        </th>
                        <th width="120" align="center">
                           设备用途
                        </th>
                        <th width="120" align="center">
                           设备数量
                        </th>
                        <th width="120" align="center">
                            借用人
                        </th>
                        <th width="140" align="center">
                            借用时间
                        </th>
                        <th width="140" align="center">
                            计划归还时间
                        </th>
                        <th width="120" align="center">
                            制单人
                        </th>
                         <th width="120" align="center">
                             审核状态
                        </th>       
                        <th width="120" align="center">
                            备注
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${id}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${id}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                                <span id="serialNumber">${serialNumber}</span>
                            </td>
                            <td>
                                <span id="dvname">${dvname}</span>
                            </td>
                            <td>
                                <span id="dvuse">${dvuse}</span>
                            </td>
                            <td>
                                <span id="dvnum">${dvnum}</span>
                            </td>
                            <td>
                                <span id="dvusername">${dvusername}</span>
                            </td>
                            <td>
                                <span id="Dvusetime">${fn:substring(dvusetime,0,16)}</span>
                            </td>
                            <td>
                                <span id="Dvbacktime">${fn:substring(dvbacktime,0,16)}</span>
                            </td>
                            <td>
                                <span id="staffname">${staffname}</span>
                            </td>
                            <td>
                                <span id="status"><c:if test="${status eq 00}">草稿</c:if><c:if test="${status eq 01}">未审核</c:if><c:if test="${status eq 02}">已审核</c:if><c:if test="${status eq 03}">驳回</c:if><c:if test="${status eq 04}">办理中</c:if><c:if test="${status eq 05}">已办理</c:if></span>
                            </td>
                            <td>
                                <span id="remarks">${remarks}</span>
                                <span id="id" style="display:none">${id}</span>
                                <span id="key"  style="display:none">${key}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../ea/page_navigator.jsp">
                <c:param name="actionPath" value="ea/deborrow/ea_getDtMydeviceborrowList.jspa?type=${param.type}&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
         <!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							设备名称：
						</td>
						<td width="261">
							<input name="deborrow.dvname" class="ckTextLength" maxlength="40" id="dvname"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							借用人：
						</td>
						<td>
						    <input name="deborrow.dvusername" class="ckTextLength" maxlength="40" id="dvusername"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							借用时间：
						</td>
						<td>
						    <input name="Dvusetime" id="Dvusetime" style="width:180px;height:100%;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<!--添加 修改-->
		<div class="jqmWindow jqmWindowcss" style="top:10%;width:700px;" id="jqModel">
			 <form name="deviceborrowForm" id="deviceborrowForm" method="post" >
	        <div class="drag">设备借用申请
		    <div class="close"></div>
		    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			     <table width="700" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="deviceborrowtable" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="80" height="37" align="right">单据编号：</td>
			        <td width="240" height="37">
			        <span id="serialNumber"></span>
			        </td>
			        <td width="90" height="37" align="right">借用人：</td>
			        <td width="290" height="37">
			         <span id="dvusername">${StaffName}</span>
			        </td>
			      </tr>
			      <tr>
			        <td width="80" height="37" align="right">设备名称：</td>
			        <td width="620" height="37" colspan="3">
			         <input name="deborrow.dvname" class="put3 ckTextLength" maxlength="40" type="text" id="dvname" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="80" height="37"  align="right">数量：</td>
			        <td width="240" height="37" >
			        <input name="deborrow.dvnum" class="put3 ckTextLength positiveNum" maxlength="8" type="text" id="dvnum" size="20"/>
			        </td>
			        <td width="90" height="37" align="right">用途：</td>
			        <td width="290" height="37">
			        <input name="deborrow.dvuse" class="put3 ckTextLength" maxlength="40" type="text" id="dvuse" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="80" height="37" align="right">借用时间：</td>
			        <td width="240" height="37">
			        <input name="Dvusetime" id="Dvusetime" size="20" class="put3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			        </td>
			        <td width="90" height="37" align="right">计划归还时间：</td>
			        <td width="290" height="37">
			        <input name="Dvbacktime" id="Dvbacktime" size="20" class="put3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			        </td>
			      </tr>
			       <tr>
			        <td width="110" height="37" align="right" valign="top">备注：</td>
			        <td width="590" height="37" colspan="3">
			        <textarea name="deborrow.remarks" id="remarks" class="put3 ckTextLength" maxlength="400" style="width:500px; height:80px; border:1px solid #C5D9F1;resize: block;"></textarea>
                    <input type="hidden" name="deborrow.id" id="id" />
					<input type="hidden" name="deborrow.key" id="key" />
					<input type="hidden" name="deborrow.serialNumber" id="serialNumber" />
					<input type="hidden" name="deborrow.addtime" id="addtime" />
                    </td>
			      </tr>
			    </table>
			</div>
			    <s:token></s:token>
			    <div align="center">
			        <table>
			        <tr>
			        <td><input type="button" class="input-button" style="cursor:pointer;width:80px;" id="save1" onclick="tosave('savecheck');" value=" 提交审核 " /></td>
			        <td><input type="button" class="input-button" style="cursor:pointer;width:80px;"  id="save2" onclick="tosave('save');" value=" 保存草稿 " /></td>
			        <td><input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /></td>
			        </tr>
			        </table>	
                </div>
			</form>
	     </div>
	      <!--选择审核人员窗口 -->
	    <form name="SendForm" id="SendForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="drag">
				选择审核人
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right">审核人公司：</td>
					<td align="left"><select id="receiverCompanyID"
						name="document.receiverCompanyID" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核人部门：</td>
					<td align="left"><select id="receiverDeptID"
						name="document.receiverDeptID" onchange="changeDept(this);"
						style="width: 200px;">
							<option value="">请选择审核人部门</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核人姓名：</td>
					<td align="left"><select name="document.receiverID"
						id='receiverID' style="width: 200px;">
							<option value="">请选择审核人</option>
					</select></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="input-button" id="submitResult" value=" 提交 " />
				<input type="button" class="input-button checkclose" value=" 关闭 " /> 
			</div>
			</center>
		</div>
	    </form>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>