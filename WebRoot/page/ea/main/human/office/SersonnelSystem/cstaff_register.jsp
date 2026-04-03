<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>人员招聘列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/ea/validate.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/validate.css"/>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script src="<%=basePath%>js/ea/office_ea/information/TelMessageIndex.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/SersonnelSystem/cstaff_register.js"></script>
        
        <script type="text/javascript">
                 var basePath = "<%=basePath%>";
				 var auditionID="";
				 var pNumber =${pageNumber}; 
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
                        <th width="120" align="center">
                            人员姓名
                        </th>
                        <th width="150" align="center">
                            身份证
                        </th>
                        <th width="150" align="center">
                            应聘方向
                        </th>
                        <th width="150" align="center">
                           应聘岗位
                        </th>
                        <th width="150" align="center">
                            工作经验
                        </th>
                        <th width="100" align="center">
                            状态
                        </th>
                        <th width="100" align="center">
            QQ联系方式
                        </th>
                         <th width="100" align="center">
            	电话号码
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${auditionID}">
                            <td>
                                <input type="radio" name="a" class="JQueryauditionID" value="${auditionID}"/>
                            </td>
                            <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${staffIdentityCard}</span>
                            </td>
                            <td>
                                <span id="auditionDirection">${auditionDirection}</span>
                            </td>
                            <td>
                                <span id="auditionPost" >${auditionPost}</span>
                            </td>
                            <td>
                                <span id="experience">${experience}</span>
                            </td>
                            <td>
                                 <span id="status">${status=='00'?'待通知':'已通知'}</span>
                                <span style="display:none" id="auditionID">${auditionID}</span>
                                <span style="display:none" id="auditionKey">${auditionKey}</span>
                                <span style="display:none" id="staffID">${staffID}</span>
                                 <span style="display:none" id="photo">${photo}</span>
                            </td>
                             <td>
                                <span id="referenceCode">${referenceCode}</span>
                            </td>
                             <td>
                                <span id="reference">${reference}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/saudition/ea_getauditionList.jspa?pageNumber=${pageNumber}&status=3">
                </c:param>
            </c:import>
        </div>
            <form name="cstaffForm" id="cstaffForm" method="post" action="">
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
        
                <div class="drag">
                    员工招聘信息 
                    <div class="close">
                    </div>
                </div>
				   <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="263" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td  height="46" align="right">员工姓名：</td>
                         <td ><input type="text" id="staffName"/></td>
                         <td  align="right">身份证号：</td>
                         <td ><input type="text" id="staffIdentityCard"/></td>
                         
                       </tr>
                       <tr>
                         <td height="46"  align="right">应聘方向：</td>
                         <td ><input id="auditionDirection" type="text" class="input"  size="20"/></td>
                         <td  align="right">应聘岗位：</td>
                         <td ><input id="auditionPost" type="text" class="input"  size="20"/></td>
                       </tr>
                       <tr>
                        <td height="27" align="right">
                            工作经验：
                        </td>
                        <td colspan="3">
                            <textarea cols="61" rows="5" id="experience" >
                            </textarea>
                        </td>
                       </tr>
                        <tr>
                     </table></td>
				   </tr>
				</table>
                <div align="center">
                </div><s:token></s:token>
            
        </div>
        </form>
        
         <form name="employmentForm" id="employmentForm" method="post"  enctype="multipart/form-data">
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqMode2">
           
                <div class="drag">
                      面试通知 
                    <div class="close">
                    </div>
                </div>
				   <table width="699" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="263" border="0"  align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="90" height="30" align="right">员工姓名：</td>
                         <td width="189"><input type="text" id="staffName" readonly="readonly"/></td>
                         	
                         <td width="80" align="right">身份证号：</td>
                         <td width="189"><input type="text" id="staffIdentityCard" readonly="readonly"/></td>
                        
                       </tr>
                       <tr>
                         <td height="30"  align="right">应聘方向：</td>
                         <td ><input id="auditionDirection" name="audition.auditionDirection"  type="text" class="input"  size="20"/></td>
                         <td  align="right">应聘岗位：</td>
                         <td ><input id="auditionPost" name="audition.auditionPost"  type="text" class="input"  size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">面试地点：</td>
                         <td ><input id="place" name="audition.place" maxlength="50"  type="text" class="input"   size="20"/></td>
                         <td  align="right">面试部门：</td>
                         <td ><input id="auditionDept" name="audition.auditionDept"  type="text" class="input"  size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">面试时间：</td>
                         <td ><input id="auditionDate" name="audition.auditionDate"  type="text" onfocus="date(this);" class="input"  size="20"/></td>
                         <td  align="right">面试考官：</td>
                         <td ><input id="examiner" name="audition.examiner"  type="text" class="input"   size="20"/></td>
                       </tr>
                       <tr>
                         <td height="27" align="right"> 工作经验： </td>
                         <td colspan="4"><textarea cols="45" rows="5" maxlength="50" class="ckTextLength"   id="experience" name="audition.experience" >
                            </textarea>                         </td>
                       </tr>
                     </table></td>
				   </tr>
				</table><s:token></s:token>
				
                <div align="center">
                 <input type="button" class="input-button" id="auditionBC" value=" 保存 " />
                 <input type="button" class="input-button JQueryreturn" value="取消" />
                 <input type="hidden" name="audition.staffID" id="staffID"/>
                  <input type="hidden" name="audition.auditionID" id="auditionID"/>
                  <input type="hidden" name="audition.auditionKey" id="auditionKey"/>
                  <input type="hidden" name="audition.status" value="10"/>
                </div>         
        </div>
        </form>

		 <!--发送窗口 -->
	<div class="jqmWindow" style="width: 515px; left: 20%; top: 10%"
		id="jqModelSend">
		<form action="" name="mForm" id="mForm" method="post" enctype="multipart/form-data">
			<input name="submit" type="submit" style="display: none" />
			<div class="drag">
				发短信息
				<div class="close close1" id="closep"></div>
			</div>
			<table>
				<tr>
					<td rowspan="2"></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2">
						<div style="border: solid #eff 1px;">
							<%--<p>导入号码<input type="file" contentEditable="false" name="txtFile" />
									</p>
								--%>
							<p>
								手机号码
								 <input type="text" size="28" id="telPhotoNumber" class="telPhotoNumber" /> 
								 <input type="button" value="输入" class="ADDTEL" />
								 <input type="button" value="清空" class="RESET" />
								 <span style="color:red;font-size:12px;margin-left:100px">可支持群发</span>
							</p>
						</div> <textarea id="cumID" contentEditable="false" cols="80" rows="4"
							name="telNumber"></textarea>
						<div id="cData" style="display: none"></div>

						<p style="text-align: left">请在以下输入要发送的内容:</p> <textarea cols="80"
							rows="8" id="content" name="telMessage.content"></textarea>
						<p style="text-align: right;">
							<input id="companyName" name="telMessage.companyName"
								type="hidden" /> <input type="button" value="发送" class="SEND"
								disabled="ture" /> <input type="button" value="清空" 
								onclick="javascript:document.getElementById('content').value=''"/>
						</p></td>
				</tr>
			</table>
			<s:token></s:token>
		</form>
	</div>        
    </body>
</html>
