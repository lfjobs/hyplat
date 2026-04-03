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
        <title>中介调查列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/SersonnelSystem/coIntermediaryResear.js"></script>
        
   <script type="text/javascript">
   var intermediaryResearchID = "";
   var  basePath='<%=basePath%>';           
   var  search='${search}';  
   var  pNumber =${pageNumber};  
   var  token = 0; 
   var notoken = 0;
var retoken=0;     	
   </script>
       
    </head>
    <body>
        <script LANGUAGE="JavaScript">
        </script>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="30" align="center">
                            序号
                        </th>
                        <th width="170" align="center">
                            单位名称
                        </th>
                        <th width="270" align="center">
                            单位地点
                        </th>
                        <th width="160" align="center">
                            招聘岗位
                        </th>
                        <th width="100" align="center">
                            上班时间
                        </th>
                        <th width="100" align="center">
                            下班时间
                        </th>
                        <th width="150" align="center">
                            主要人才
                        </th>
                        <th width="200" align="center">
                            备注 
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${intermediaryResearchID}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${intermediaryResearchID}"/>
                            </td>
                            <td>
                                <span> <%=number%></span>
                            </td>
                            <td>
                                <span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span id="companyAddress">${companyAddress}</span>
                            </td>
                            <td>
                                <span id="invitePost">${invitePost}</span>
                            </td>
                            <td>
                                <span id="StartworkDate" class="datas">${StartworkDate}</span>
                            </td>
                            <td>
                                <span id="offdutyDate" class="datas">${offdutyDate}</span>
                            </td>
                            <td>
                                <span id="mainTalents">${mainTalents}</span>
                            </td>
                            <td>
                                <span id="remark">${remark}</span>
                                	<span   style="display:none" id="address">${address}</span>
                                <span style="display:none" id="intermediaryResearchID">${intermediaryResearchID}</span>
                                <span style="display:none" id="intermediaryResearchKey">${intermediaryResearchKey}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/intermediaryresearch/ea_getIntermediaryResearchList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
           
        </div>
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    中介调查详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
<tr>
                        <td width="147" height="27" align="right">
                            单位名称：                        </td>
<td width="196" >
<input name="intermediaryresearch.companyName" class="model3 notnull" id="companyName" size="20"/>                        </td>
                        
<td width="87" align="right">招聘岗位：</td>
<td width="260"><!-- 代替token-->                        <input name="intermediaryresearch.invitePost" class="model3 notnull" id="invitePost" size="20"/></td>
   </tr>
                    <tr>
                        
                        <td height="27" align="right">主要人才：</td>
          <td><input name="intermediaryresearch.mainTalents" class="model3" id="mainTalents" size="20"/></td>
                         <td height="27" align="right">上班时间：</td>
                        <td><input name="intermediaryresearch.StartworkDate" class="model3" id="StartworkDate" size="20"/></td>
                    </tr>
                    <tr>
                        <td height="27" align="right">下班时间：</td>
<td colspan="3"><input name="intermediaryresearch.offdutyDate" class="model3" id="offdutyDate" size="20" /></td>
                        <tr>
                        	<td height="93" align="right">备注：</td>
                          <td colspan="3"><textarea  name="intermediaryresearch.remark" cols="51" rows="5" class="model3" id="remark"></textarea>
                            <input name="intermediaryresearch.intermediaryResearchID" id="intermediaryResearchID" type="hidden"/>
                            <input name="intermediaryresearch.intermediaryResearchKey" id="intermediaryResearchKey" type="hidden"/>
                            <input name="sub" value="${session_value}" type="hidden" /></td>
                        </tr>
                  
                    <tr>
                     <td height="27" align="right">
                            单位地址：                        </td>
                        <td class="JQueryaddress" colspan="3">
                                      <input name="intermediaryresearch.address" id="address" type="hidden"/>
                            		 <input name="intermediaryresearch.companyAddress" id="companyAddress" type="hidden"/>
								     <select name="addressProvince" id="province" number='0' style="width:110px;" ></select><!-- <option>选择省</option>-->
								     <select name="addressCity" id="city" number='1' style="width:110px;"></select>
								     <select name="addressCounty" id="county" number='2'  style="width:110px;"></select>
								     <select name="addressTown" id="addressTown" number='3'  style="width:110px;"></select>
								     <select name="addressVillage" id="addressVillage" number='4'  style="width:110px;"></select>
								     <select name="addressCommunity" id="addressCommunity" number='5' style="width:110px;" ></select><!-- <option>选择省</option>-->
								     <select name="addressFloor" id="addressFloor" number='6' style="width:110px;"></select>
								     <select name="addressLayer" id="addressLayer" number='7'  style="width:110px;"></select>
								     <select name="addressSize" id="addressSize" number='8'  style="width:110px;"></select>                        </td>
                    </tr>
                </table>
               <div align="center">  
                     <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />  
                     <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /> 
    </div> 
    <s:token></s:token>
            </form>
        </div>
        <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 33%;top:10%" id="jqModelSearch">
            <form name="cstaffSearchForm" id="cstaffSearchForm" method="post"><input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" align="center">
                    <tr align="center">
                        <td align="center">
                            单位名称：
                        </td>
                        <td>
                            <input name="intermediaryresearch.companyName" />
                        </td>
                    </tr>
					 <tr align="center">
                        <td>
                            招聘岗位：
                        </td>
                        <td>
                            <input name="intermediaryresearch.invitePost" />
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                            单位地址：
                        </td>
                        <td>
                            <input name="intermediaryresearch.companyAddress" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchStaff" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
          <div class="jqmWindow" style="width: 400px;right: 25%;top:10%" id="newdistrict">
                                            <div class="drag">
                                                添加地域
                                            </div>
                                            <table>
                                                <tr>
                                                    <td >
                                                         城市名字：
                                                    </td>
                                                    <td >
                                                        <input id="districtNames" />&nbsp;&nbsp;<span style="color:red">*按地域区分组</span> 
                                                    </td>
                                                </tr>
                                            </table>
                                            <div align="center">
                                               <input type="button" class="input-button" id="savedistrict" value="确定"/>
                                               <input type="button" class="input-button JQueryreturn1" value="取消" />
                                            </div>
                                    </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>