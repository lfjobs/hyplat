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
        <title>用车申请</title>
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
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/administrative/CarApply.js"></script>
        <script>
            var basePath = "<%=basePath%>";
            var carid = "";
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
	     <input type="hidden" name="usecarapply.id" id="id" />
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
                          车牌号
                        </th>
                        <th width="120" align="center">
                           项目名称
                        </th>
                        <th width="120" align="center">
                           目的地
                        </th>
                        <th width="120" align="center">
                           用车人
                        </th>
                        <th width="120" align="center">
                           驾驶员
                        </th>
                        <th width="140" align="center">
                           用车时间
                        </th>
                        <th width="140" align="center">
                           计划还车时间
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
                                <span id="carcode">${carcode}</span>
                            </td>
                            <td>
                                <span id="carusereason">${carusereason}</span>
                            </td>
                            <td>
                                <span id="destination">${destination}</span>
                            </td>
                            <td>
                                <span id="carusername">${carusername}</span>
                            </td>
                            <td>
                                <span id="cardriver">${cardriver}</span>
                                <span id="cardriverid" style="display:none;">${cardriverid}</span>
                                <span id="cardriverorgid" style="display:none;">${cardriverorgid}</span>
                                <span id="cardriverorgname" style="display:none;">${cardriverorgname}</span>
                                <span id="cardrivercompanyid" style="display:none;">${cardrivercompanyid}</span>
                                <span id="cardrivercompanyname" style="display:none;">${cardrivercompanyname}</span>
                            </td>
                            <td>
                                <span id="Dvusetime">${fn:substring(carusetime,0,16)}</span>
                            </td>
                            <td>
                                <span id="Dvbacktime">${fn:substring(carbacktime,0,16)}</span>
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
                <c:param name="actionPath" value="ea/carapply/ea_getDtMyusecarapplyList.jspa?type=${param.type}&pageNumber=${pageNumber}">
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
							车牌号：
						</td>
						<td width="261">
							<input name="usecarapply.carcode" class="ckTextLength" maxlength="40" id="carcode"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							用车人：
						</td>
						<td>
						    <input name="usecarapply.carusername" class="ckTextLength" maxlength="40" id="carusername"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							用车时间：
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
		<div class="jqmWindow jqmWindowcss" style="top:10%;width:700px;z-index:20;" id="jqModel">
			 <form name="carapplyForm" id="carapplyForm" method="post" >
	        <div class="drag">用车申请
		    <div class="close"></div>
		    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			     <table width="700" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="carapplytable" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="80" height="37" align="right">单据编号：</td>
			        <td width="240" height="37">
			        <span id="serialNumber"></span>
			        </td>
			        <td width="90" height="37" align="right">用车人：</td>
			        <td width="290" height="37">
			         <span id="carusername">${StaffName}</span>
			        </td>
			      </tr>
			      <tr>
			        <td width="80" height="37" align="right">车牌号：</td>
			        <td width="240" height="37">
			         <input name="usecarapply.carcode" class="put3 ckTextLength" maxlength="40" type="text" id="carcode" size="20"/>
			        </td>
			        <td width="90" height="37" align="right">驾驶员：</td>
			        <td width="290" height="37">
			        <input name="usecarapply.cardriver" class="put3" type="text" id="cardriver" size="20" readonly/>
			        <input type="hidden" name="usecarapply.cardriverid" id="cardriverid" />
					<input type="hidden" name="usecarapply.cardriverorgid" id="cardriverorgid" />
					<input type="hidden" name="usecarapply.cardriverorgname" id="cardriverorgname" />
					<input type="hidden" name="usecarapply.cardrivercompanyid" id="cardrivercompanyid" />
					<input type="hidden" name="usecarapply.cardrivercompanyname" id="cardrivercompanyname" />
			        <span><a href="#" id="choosedriver">选择</a></span>
			        </td>
			      </tr>
			      <tr>
			        <td width="80" height="37"  align="right">项目名称：</td>
			        <td width="240" height="37" >
			        <input name="usecarapply.carusereason" class="put3 ckTextLength" maxlength="40" type="text" id="carusereason" size="20"/>
			        </td>
			        <td width="90" height="37" align="right">目的地：</td>
			        <td width="290" height="37">
			         <input name="usecarapply.destination" class="put3 ckTextLength" maxlength="40" type="text" id="destination" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="80" height="37" align="right">用车时间：</td>
			        <td width="240" height="37">
			        <input name="Dvusetime" id="Dvusetime" size="20" class="put3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			        </td>
			        <td width="90" height="37" align="right">计划还车时间：</td>
			        <td width="290" height="37">
			        <input name="Dvbacktime" id="Dvbacktime" size="20" class="put3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			        </td>
			      </tr>
			       <tr>
			        <td width="110" height="37" align="right" valign="top">备注：</td>
			        <td width="590" height="37" colspan="3">
			        <textarea name="usecarapply.remarks" id="remarks" class="put3 ckTextLength" maxlength="400" style="width:500px; height:80px; border:1px solid #C5D9F1;resize: block;"></textarea>
                    <input type="hidden" name="usecarapply.id" id="id" />
					<input type="hidden" name="usecarapply.key" id="key" />
					<input type="hidden" name="usecarapply.serialNumber" id="serialNumber" />
					<input type="hidden" name="usecarapply.addtime" id="addtime" />
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
			style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;z-index:10;"
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
	     <!--选择驾驶员窗口 -->
	    <form name="driverForm" id="driverForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 400px; height: 250px; right: 15%; top: 20%;"
			id="jqModeldriver">
			<input type="submit" name="submit" style="display: none" />
			<div class="drag">
				选择驾驶员
			</div>
			<center>
			<table width="100%" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right">公司：</td>
					<td align="left"><select id="receiverCompanyID"
						name="document.receiverCompanyID" onchange="changeCompanys(this);"
						style="width:200px;">
							<option value="">请选择公司</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">部门：</td>
					<td align="left"><select id="receiverDeptID"
						name="document.receiverDeptID" onchange="changeDepts(this);"
						style="width: 200px;">
							<option value="">请选择部门</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">人员：</td>
					<td align="left"><select name="document.receiverID"
						id='receiverID' style="width: 200px;">
							<option value="">请选择人员</option>
					</select></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="input-button" id="submitdriver" value=" 提交 " />
				<input type="button" class="input-button checkcloses" value=" 关闭 " /> 
			</div>
			</center>
		</div>
	    </form>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>