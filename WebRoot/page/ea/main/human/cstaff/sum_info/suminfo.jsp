<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>社会单位人员列表详细信息汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/sum_info/suminfo.js"></script>
		
<script type="text/javascript">
 var  basePath='<%=basePath%>';           
 var  pNumber =${pageNumber};  
 var  search='${search}';
 var  token = 0 ;
 var actionName='${actionName}';
 var actionNameExcel='${actionNameExcel}';
 var basicInfo='${basicInfo}';
 var conditions='${conditions}';
</script>
	</head>
	<body>
		<div>
		<form id="myform" name="myform" action="" method="post">
		<input type="submit" name="submit" style="display: none" />
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith" >
					<c:forEach items="${headerList}" var="headers" varStatus="status" >
						<th width="120" align="center">
							${headers}
						</th>
					</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="objs" items="${pageForm.list}">
						<tr id="${objs[0]}">
							<c:forEach items="${objs}" var="obj" >
							<td >
								<span>${obj}</span>
							</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</form>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/suminfo/ea_${actionName}.jspa?basicInfo=${basicInfo}&search=${search }&pageNumber=${pageNumber }&conditions=${conditions}">
				</c:param>
			</c:import>
		</div>
		<div class="jqmWindow" style="width: 460px;right: 25%;top: 10%" id="jqModelExcel">
          <form name="showExcelForm" id="showExcelForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                   选择导出信息
                    <div class="close">
                    	
                    </div>
            </div>
             <div  id="showTablefaild">
             </div>
            <div align="center"> 
              <input name="search" type="button" value="确定" id="showSure" class="input-button"/>
              <input name="search" type="button" value="取消" id="showReturn" class="input-button"/>
            </div>
            </form>
           
        </div>
        
         <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 350px;right: 35%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="350px" id="cataffSearchTable"> 
                    <tr>
                        <td align="right">
                            姓名：
						</td>
                        <td>
                        	<input name="conditions" id="conditions" />
                        </td>
                    </tr>
                  
                    
                </table>
            <div align="center"> 
              <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <s:select list="headerList" key="" value="" id="headerList" style="display:none"></s:select>
	</body>
</html>