<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page import="hy.ea.bo.Company"%>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>公司正式员工汇总</title>
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
            <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/company/company_incumbent.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
            var basePath = "<%=basePath%>";
            var personvalue = "";
            var personurl = "";
            var staffName="";
            var ssearch='${search}';
            var pNumber ='${pageNumber}';
            var treeid = '<%=c.getCompanyID()%>';
            var treename = '<%=c.getCompanyName()%>';
        </script>  
    </head>
    <body >
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
                            档案编号
                        </th>
                         <th width="100" align="center">
                            员工工种
                        </th>
                        <th width="100" align="center">
                            人员姓名
                        </th>
                        <th width="100" align="center">
                            曾用名
                        </th>
                        <th width="80" align="center">
                            性别
                        </th>
                        <th width="100" align="center">
                            出生日期
                        </th>
                        <th width="80" align="center">
                            国籍 
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
                        <!--  
                      <th width="200" align="center">
                            身份证地址
                      </th>
					<th width="100" align="center">
                             电话
                      </th>
					<th width="100" align="center">
                       qq
                     </th>
                      <th width="100" align="center">
                              邮箱
                      </th>
                         <th width="100" align="center">
                               录入时间
                      </th>
                        <th width="100" align="center">
                            备注
                        </th>
                        -->
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${staffID}" class="${staffName}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
                            </td>
                            <td>
                                <span>${companyName}</span>
                            </td>
                            <td>
                                <span id="staffCode">${staffCode}</span>
                            </td>
                            <td>
                                <span id="recordCode">${recordCode}</span>
                            </td>
                             <td>
                                <span id="categoryname">${categoryname}</span>
                            </td>
                            <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="usedNmae">${usedNmae}</span>
                            </td>
                            <td>
                                <span id="sex">${sex}</span>
                            </td>
                            <td>
                                <span id="birthday" class="datas">${birthday}</span>
                            </td>
                             <td>
                                <span id="nationality">${nationality}</span>
                            </td>
                            <td>
                                <span id="nativePlace">${nativePlace}</span>
                            </td>
                            <td>
                                <span id="nation">${nation}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${staffIdentityCard}</span>
                                <span style="display:none" id="schedulingID">${schedulingID}</span>
                                <span style="display:none" id="staffKey">${staffKey}</span>
                                <span style="display:none" id="staffID">${staffID}</span>
                                <span style="display:none" id="photo">${photo}</span>
                            </td>
                            <!--  
                           <td >
								<span id="staffAddress">${staffAddress}</span>
                           </td>
						  <td >
								<span id="reference">${reference}</span>
                           </td>
							<td >
								<span id="referenceCode">${referenceCode}</span>
                            </td>
                             <td >
							<span id="referenceOrganization">${referenceOrganization}</span>
                             </td>
							<td >
								<span id="verifyTime" class="datas">${verifyTime}</span>
                             </td>
                            <td>
                                <span id="staffDesc">${staffDesc}</span>
                                <span style="display:none" id="address">${address}</span>
                            </td>
                            -->
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/soincumbent/ea_getCompanyListForIncumbent.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import> 
        </div>
             
       <iframe id="mainframe" src='' style="margin:0px;width:100%;height:0;" name="admin" scrolling="no" frameBorder="0"></iframe>  
        <div class="jqmWindow" style="width:700px;top:10%;left:15%;" id="jqModel">
         <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    员工详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
                    <tr>
                        <td width="103" height="27" align="right">
                            人员编号：
                        </td>
                        <td width="140">
                            <input name="cstaff.staffCode"  id="staffCode" size="20"/>
                        </td>
                        <td width="147" align="right">
                            档案编号：
                        </td>
                        <td width="148">
                            <input name="cstaff.recordCode" id="recordCode" size="20"/>
                        </td>
                        <td width="144" rowspan="5" align="center">
                            <img width="99" height="135" id="photo" />
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            姓名：
                        </td>
                        <td>
                            <input name="cstaff.staffName" id="staffName" size="20"/>
                        </td>
                        <td align="right">
                            曾用名：
                        </td>
                        <td>
                            <input name="cstaff.usedNmae" id="usedNmae" size="20"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            性别：
                        </td>
                        <td>
                            <select id='sex' name="cstaff.sex">
                            </select>
                        </td>
                        <td align="right">
                            出生日期：
                        </td>
                        <td>
                            <input name="cstaff.birthday" id="birthday" size="20" />
                        </td>
                        <td width="8">
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            民族：
                        </td>
                        <td>
                            <select id="nation" name="cstaff.nation">
                            </select>
                        </td>
                        <td align="right">
                            籍贯：
                        </td>
                        <td>
                            <select id="nativePlace" name="cstaff.nativePlace">
                            </select>
                        </td>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            身份证号码：
                        </td>
                        <td>
                            <input name="cstaff.staffIdentityCard" id="staffIdentityCard" size="20"/>
                        </td>
                        <td align="right">
                            国籍：
                        </td>
                        <td>
                            <select id="nationality" name="cstaff.nationality">
                            </select>
                        </td>
                        <td>
                        </td>
                    </tr>
                    <!-- 
                     <tr>
                        <td align="right">
                            电话：
                        </td>
                        <td>
                            <input name="cstaff.reference"  type="text" id="reference" size="20" />
                        </td>
                       <td align="right">邮箱：</td>
                                <td><input name="cstaff.referenceOrganization"  type="text" id="referenceOrganization" size="20" /></td>
                     </tr>
                        <tr>
                        <td height="27" align="right">
                            QQ：
                        </td>
                        <td>
                            <input name="cstaff.referenceCode" type="text" id="referenceCode" size="20" />
                        </td>
                        <td align="right">
                            录入时间：
                        </td>
                        <td>
                            <input name="cstaff.verifyTime" type="text"  id="verifyTime" size="20" />
                        </td>
                        <td>
                            <input name="photo" id="staffphoto"  contentEditable="false" type="file" /><input name="cstaff.photo" type="hidden" id="photo" size="14"/><input name="cstaff.staffID" id="staffID" type="hidden"/><input name="cstaff.staffKey" id="staffKey" type="hidden"/><input name="cstaff.schedulingID" id="schedulingID" type="hidden"/><input name="sub" value="${session_value}" type="hidden" /><!-- 代替token
                        </td>
                    </tr>
                    <tr>
                        <td height="40" align="right">
                            身份证地址：
                        </td>
                        <td class="JQueryaddress" colspan="7">
                            <input name="cstaff.address" id="address" type="hidden"/>
                            <select name="addressProvince" id="province" number='0' style="width:110px;">
                            </select>
                            <!-- <option>选择省</option>
                            <select name="addressCity" id="city" number='1' style="width:110px;">
                            </select>
                            <select name="addressCounty" id="county" number='2' style="width:110px;">
                            </select>
                            <select name="addressTown" id="addressTown" number='3' style="width:110px;">
                            </select>
                            <select name="addressVillage" id="addressVillage" number='4' style="width:110px;">
                            </select>
                            <select name="addressCommunity" id="addressCommunity" number='5' style="width:110px;">
                            </select>
                            <!-- <option>选择省</option>
                            <select name="addressFloor" id="addressFloor" number='6' style="width:110px;">
                            </select>
                            <select name="addressLayer" id="addressLayer" number='7' style="width:110px;">
                            </select>
                            <select name="addressSize" id="addressSize" number='8' style="width:110px;">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            备注：
                        </td>
                        <td colspan="3">
                            <textarea name="cstaff.staffDesc" cols="40" rows="5" id="staffDesc">
                            </textarea>
                        </td>
                    </tr>
                     -->
                </table>
                <div align="center">
                </div>
           </form>
        </div> 
        
        <!--搜索窗口 -->
         <form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%;" id="jqModelSearch">
           
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
                    </tr>
                    <tr>
                        <td>
                            人员编号：
                        </td>
                        <td>
                            <input name="cos.staffCode" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            人员姓名：
                        </td>
                        <td>
                            <input name="cos.staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            身份证：
                        </td>
                        <td>
                            <input name="cos.staffIdentityCard" />
                        </td>
                    </tr>
                     <tr>
                        <td>
                            公司名称：
                        </td>
                        <td>
                          <select id="companyID" name="cos.companyID">
								<option value="">
									请选择公司
								</option>
							</select>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchStaff" value="查询" /><input type="button" class="input-button JQueryreturn" value="取消" /><input name="search" type="hidden" value="search" />
                </div>
        </div> 
        </form>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 30 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 10 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 30 + "px"});
			$("#mainframe").css({"height": _height / 2 - 10 + "px"});
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