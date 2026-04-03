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
        <title>现场会议管理</title>
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
        <script src="<%=basePath%>js/ea/office_ea/dtconference_list.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
        <script type="text/javascript">
         var treeID ="<%=session.getAttribute("organizationID")%>";
         var treeNames="<%=session.getAttribute("organizationName")%>";
	     var prID = "";
		 var  basePath='<%=basePath%>';           
         var pNumber ='${pageNumber}';  
         var search='${search}';  
         var personurl = "";
         var token=0;
         var dtconferenceID = "";
		 var companyID='${account.companyID}';
		 var newState = "${newState}";
		 var parm = '';
   		</script>  
   		
	</head>
	<body >
		<form name="mainForms" id="mainForms" method="post">
			<input type="submit" name="submit" style="display: none" />
	
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="30" align="center">
                            	请选择
                        </th>
                         <th width="30" align="center">
                            	序号
                        </th>
                        <th width="70" align="center">
                            	日期
                        </th>
                        <th width="50" align="center">
                            	起止时间
                        </th>
                        <th width="50" align="center">
                            	终止时间
                        </th>
                         <th width="60" align="center">
                            	部门
                        </th>
                        <th width="70" align="center">
                            	岗位名称
                        </th>
                         <th width="70" align="center">
                            	职务名称
                        </th>
                        <th width="50" align="center">
                            	责任人
                        </th>
                        <th width="80" align="center">
                            	会议流程
                        </th>
                        <th width="170" align="center">
                            	会议内容
                        </th>
                        <th width="60" align="center">
                            	附件编号
                        </th>
                         <th width="100" align="center">
                            	附件名称
                        </th>
                         <th width="40" align="center">
                            	附件
                        </th>
     
                        
                    </tr>
                    </thead>
                    <c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0] }" >
								<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[0] }" />
							</td>
							<td class="td_bg01">
								<span id="serNum">${arr[17] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="condate">${arr[3] }</span>
								
							</td>
							
							<td class="td_bg01">
								<span id="startdate">${arr[4] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="enddate">${arr[5] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="orgname" style="display:none">${arr[6] }</span>
								<span id="oname">${arr[16] }</span>
							</td>
							<td class="td_bg01">
								<span id="postname">${arr[7] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="jobname">${arr[8] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="responsible">${arr[9] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="flowname">${arr[10] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="tcontent">${arr[11] }</span>
								
							</td>
							<td class="td_bg01">
							
								<span id="annexid">${arr[12] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="annexname">${arr[13] }</span>
								
							</td>
							<td class="td_bg01">
								
								<a href="#" class="accessoriesUrl">附件</a>
								<span><a href="#" class="jqedit"
									id="childedit" onclick="OpenWord('${arr[14]}','2')"></a></span>
								<input id="annexurl" type='hidden' value="${arr[14] }" />
								
								<span style="display:none" id="conferenceid">${arr[0] }</span>
								<span style="display:none" id="companyID">${arr[1] }</span>
								<span style="display:none" id="organizationID">${arr[2] }</span>
							</td>
							
						</tr>
					</c:forEach>
                </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/dtconference/ea_getDtconferenceList.jspa?pageNumber=${pageNumber}&search=${search}&newState=${newState}">
                </c:param>
            </c:import>
             
        </div>
       </form>
         <!--添加窗口 -->
        <div class="jqmWindow" style="width: 650px;right: 30%;top:10%" id="jqModelSearch" align="center">
            <form name="dtconferenceForm" id="dtconferenceForm" method="post">
            <input type="submit" name="submit" style="display:none"/>
                <div class="drag" align="left">
                   会议基本信息
                    <div class="close">
                    </div>
                </div>
                <table id="dtconferenceTable" align="center" border="0">
                	<tr>
                        <td align="right">
                            会议流程名称：
                        </td>
                        <td colspan="0" align="left">
                            <input name="dtconference.flowname" style="width:100px" id="flowname" 
                             class="ckTextLength put3" maxlength="50"/>
                        </td>
                         <td align="right">
                           序号：
                        </td>
                        <td colspan="0" align="left">
                            
                             <input  name="dtconference.serNum" class="put3" style="width:100px" id="serNum"/>
                             例:02-1
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            日期：
                        </td>
                        <td  align="left" style="width:220px" >
                            <input name="dtconference.condate" style="width:100px" id="condate"  class="put3"onfocus="date(this);"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            起止时间：
                        </td>
                        <td  align="left" style="width:220px" >
                            <input name="dtconference.startdate" style="width:100px" id="startdate"  class="timeformat put3" value="例如 9:00" onfocus="javascript:this.value=''" 
                            onblur="javascript:if(this.value==''){this.value='例如 9:00' , this.style.color='gray';}"/>
                        </td>
                        <td align="right">
                            终止时间：
                        </td>
                        <td align="left" style="width:220px" >
                            <input name="dtconference.enddate" style="width:100px" id="enddate"  class="timeformat put3" value="例如 9:00" onfocus="javascript:this.value=''" 
                            onblur="javascript:if(this.value==''){this.value='例如 9:00' , this.style.color='gray';}"/>
                        </td>
                    </tr>
                    <tr>
                    	 <td align="right">
                            部门：
                        </td>
                        <td  align="left">
                        	<select id="orgname" name="dtconference.orgname" style="margin-left:2px;width:100px"></select>
                        </td>
                    	<td align="right">
                            责任人：
                        </td>
                        <td align="left">
                            <input name="dtconference.responsible" style="width:100px" id="responsible" readonly="readonly" />
                             <a href="#"  id="nameChoose" onclick="searchCoach();">选择</a>
                        </td>
                       
                    </tr>
                    <tr>    
                        <td align="right">
                            岗位名称：
                        </td>
                        <td  align="left">
                            <input name="dtconference.postname" style="width:100px" id= "postname" readonly="readonly"/>
                        </td>
                        <td align="right">
                            职务名称：
                        </td>
                        <td align="left">
                            <input name="dtconference.jobname" style="width:100px" id="jobname"class="ckTextLength put3" maxlength="50"/>
                        </td>
                    </tr>
                    
                    <tr>
                        
                        <td align="right">
                            会议内容：
                        </td>
                        <td colspan="3" align="left" >
                           	<textarea  name = "dtconference.tcontent"cols="40" rows="5" id="tcontent" class="ckTextLength put3"  maxlength="250"></textarea>
                        	<input name="dtconference.conferenceid" type="hidden" id="conferenceid" />
                    		<input name="dtconference.companyid" type="hidden" id="companyid" />
                    		<input name="dtconference.organizationid" type="hidden" id="organizationid" />
                        </td>
                    </tr>
                    <tr>
                    	<td align="right">
                    		附件编号:
                    	</td>
                    	<td align="left">
							<input name="dtconference.annexid" style="width:100px" id="annexid"class="ckTextLength" maxlength="50"/>	
                    	</td>
                    	<td align="right">
                    		附件名称:
                    	</td>
                    	<td align="left" >
							<input name="dtconference.annexname" style="width:100px" id="annexname"class="ckTextLength" maxlength="50"/>
                    		<a href="#" class="accessoriesUrl1">附件</a>
							<span><a href="#" class="jqedit"
								id="childedit" onclick="OpenWord('${annexurl}','2')"></a></span>
							<input id="annexurl" type='hidden' name="dtconference.annexurl" value="${annexurl }" />
                    	</td>
                     </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="addDtconference" value=" 保存 " />
                    <input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
          <!--查询窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 40%;top:10%" id="findWindow" align="center">
            <form name="selectForm" id="selectForm" method="post">
            <input type="submit" name="submit" style="display:none"/>
                <div class="drag" align="left">
                   会议基本信息
                    <div class="close">
                    </div>
                </div>
                <table id="dtconferenceTable" align="center" border="0px">
                    <tr align="center">
                        <td  align="right">
                            日期：
                        </td>
                        <td style="width:220px" align="left">
                            <input name="dtconference.condate" style="width:100px"id="condate" onfocus="date(this);" />
                        </td>
                     </tr>
                     <tr>
                        <td  align="right">
                            起止时间：
                        </td>
                        <td style="width:220px" align="left">
                            <input name="dtconference.startdate" id="startdate"style="width:100px" class="timeformat"/>
                        </td>
                     </tr>
                     <tr>
                        <td  align="right">
                            终止时间：
                        </td>
                        <td style="width:220px" align="left">
                            <input name="dtconference.enddate" id="enddate"style="width:100px" class="timeformat"/>
                        </td>
                    </tr>
                  
                    <tr align="center">
                        
                        <td  align="right">
                            责任人：
                        </td>
                        <td style="width:220px" align="left">
                            <input name="dtconference.responsible" style="width:100px"  class="ckTextLength" maxlength="50"/>
                        </td>
                     </tr>
                     <tr>   
                        <td align="right">
                            会议流程名称：
                        </td>
                        <td style="width:220px" align="left">
                            <input name="dtconference.flowname" style="width:100px"  class="ckTextLength" maxlength="50"/>
                        </td>
                    </tr>
                    
                  
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="selectDtconference" value=" 查询 " />
                    <input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
       <div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 420px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
				<input style="display: none;" id="parmNum"/>
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
		 
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
      
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
    </body>
  
</html>