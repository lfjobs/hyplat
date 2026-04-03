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
        <title>会议组织机构管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <style type="text/css"> 
        .wenben{
        	border-color: #FFFFFF;
        	border-width: 0px;
        	background: url('../../../images/ea/office/brue.jpg')
        }
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js">
        </script>
         <script type="text/javascript">
         var treeID ="<%=session.getAttribute("organizationID")%>";
	     var prID = "";
		 var basePath='<%=basePath%>';
         var pNumber ='${pageNumber}';  
         var search='${search}';  
         var personurl = "";
         var token=0;
		 var companyID='${account.companyID}';
		 var parm = '';
		 var select = 1;
		 var notoken = 0;
		 var repid ; // 责任人文本框id
		 var cstffidd  ;//责任人
		 var conferenceorgid ="" ;
   		</script> 
        
        <script src="<%=basePath%>js/ea/office_ea/dtconferenceorg_list.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
		 
	</head>
	
	<body >
		<form name="mainForms" id="mainForms" method="post">
			<input type="submit" name="submit" style="display: none" />
	
        <div class="main_main" id="main_main">
            <table class="JQueryflexme" >
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            	请选择
                        </th>
                        <th width="100" align="center">
                            	序号
                        </th>
                        <th width="100" align="center">
                            	组织机构
                        </th>
                        <th width="100" align="center">
                            	责任人
                        </th> 
                         <th width="100" align="center">
                            	职务
                        </th>
                         <th width="100" align="center">
                            	电话
                        </th>
                        <th width="100" align="center">
                            	备注
                        </th>
                    </tr>
                    </thead>
                    <%
						int number = 1;
					%>
                    <tbody>
                     <c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[6] }" >
								<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[6] }" />
							</td>
							<td class="td_bg01">
								<span id=""><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="conorgname">${arr[0] }</span>
							</td>
							<td class="td_bg01">
								<span id="staffName">${arr[1] }</span>
							</td>
							<td class="td_bg01">
								<span id="jobname">${arr[2] }</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[3] }</span>
							</td>
							<td class="td_bg01">
								<span id="remarks">${arr[4] }</span>
								<span style="display:none" id="responsible">${arr[5]}</span>
								<span style="display:none" id="conferenceorgid">${arr[6]}</span>
								<span style="display:none" id="companyID">${arr[7]}</span>
								<span style="display:none" id="organizationID">${arr[8]}</span>
								<span style="display:none" id="conferenceorgekey">${arr[9]}</span>
								<span style="display:none" id="ctname">${arr[10]}</span>
								<span style="display:none" id="ctdate">${arr[11]}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</c:forEach>
                    </tbody>	
                </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/dtconferenceorg/ea_getAllDtconOrg.jspa?pageNumber=${pageNumber}&search=${search}&newState=${newState}">
                </c:param>
            </c:import>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <div style="width: 100%">
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
       </form>
         
            <!--添加窗口 -->
        <div class="jqmWindow" style="width: 450px;right: 33%;top:10%" id="jqModelSearch" align="center">
            <form name="dtconferenceorgForm" id="dtconferenceorgForm" method="post">
            <input type="submit" name="submit" style="display:none"/>
                <div class="drag" align="left">
                   会议组织机构
                    <div class="close">
                    </div>
                </div>
                <table id="dtconOrgTable" align="center" border="0">
                    <tr>
                        <td align="right">
                            组织机构：
                        </td>
                        <td  align="left" style="width:220px" >
                            <input name="dtconferenceorg.conorgname" id="conorgname" style="width:100px"   class=" put3"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            职务：
                        </td>
                        <td align="left" style="width:220px" >
                            <input name="dtconferenceorg.jobname"  id="jobname" style="width:100px"   class=" put3"/>
                        </td>
                    </tr>
                    <tr>
                    	<td align="right"  style="padding-top:3px">
                            责任人：
                        </td>
                        <td align="left">
							<input size="11" id="staffName" readonly="readonly"/>
							<input name="dtconferenceorg.responsible" style="display:none;" id="responsible" />
							<a href="#"  id="nameChoose" onclick="searchCoach('txtRow0','cstffid0');">选择</a>
							<input name="dtconferenceorg.companyid" style="display:none;" id="companyID" />
							<input name="dtconferenceorg.organizationid" style="display:none;" id="organizationID" />
							<input name="dtconferenceorg.conferenceorgid" style="display:none;" id="conferenceorgid" />
							<input name="dtconferenceorg.conferenceorgekey" style="display:none;" id="conferenceorgekey" />
							<input name="dtconferenceorg.ctname" style="display:none;" id="ctname" />
							<input name="dtconferenceorg.ctdate" style="display:none;" id="ctdate" />
                        </td>
                     </tr> 
                    <tr>
                        
                        <td align="right">
                            备注：
                        </td>
                        <td align="left" >
                           	<textarea name = "dtconferenceorg.remarks" id="remarks" cols="30" rows="5"  class="ckTextLength"  maxlength="250"></textarea>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="addDtconorg" value=" 保存 " />
                    <input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
        <!-- 查询添加责任人 -->
        <div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 420px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
				<input style="display: none;" id="parmNum"/>
				<input style="display: none;" id="reference"/>
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="380px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		 </div>
</html>