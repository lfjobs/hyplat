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
<meta http-equiv="cache-control" content="no-cache" />
<title>花名册</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript">
   var token = 0;
   var search= '${search}';
   var rosterid = '';
    var pbasePath = "<%=basePath%>";
	var pNumber = '${pageNumber}';
	var notoken = 0;
</script>
<script src="<%=basePath%>js/ea/human/rosterhuman.js"></script>
<style>
	input checkbox{
	width: 20px;
	height: 20px;
	}
</style>
</head>
<body>
	<form name="rosterForm" id="rosterForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<s:token></s:token>
		<div id="main_main" class="main_main">
			<table class="JQueryflexmepost">
				<thead>
					<tr>
						<th width="60" align="center">选择</th>
						<th width="200" align="center">名称</th>
						<th width="200" align="center">电话</th>
						<th width="300" align="center">邮箱</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>

					<s:iterator value="pageForm.list" var="lists">
						<tr id="${rosterid}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${rosterid}" /></td>
							<td><span id="name">${name}</span></td>
							<td><span id="phone">${phone}</span></td>
							<td>
								<span id="email">${email}</span>
								<span style="display: none" id="rosterid">${rosterid}</span>
								<span style="display: none" id="rosterkey">${rosterkey}</span>
								<span style="display: none" id="companyid">${companyid}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>

			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/roster/ea_getRosterAll.jspa?&pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<!--添加修改花名册信息  -->
	 <div class="jqmWindow" style="width: 310px;right: 35%;top: 10%;" id="jqModelAdd">
 <form name="postAddForm" id="postAddForm" method="post">
       
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag" >
                    	<div id="addupdateform"><span id="information">添加信息</span></div>
                    <div class="close">
                    </div>
                </div>
                 <table id="rosterAddTable">
                    
                    <tr>
                        <td>
                         	  部门名称：
                        </td>
                        <td>
                           <input id="name" class="put3" name="roster.name"  />
                        </td>
                    </tr>
                    <tr>
                         <td>
                          	电话：
                        </td>
                        <td>
                           <input id="phone" class="phone" name="roster.phone"   />
                        </td>
                    </tr>
                    <tr>
                        <td>
                         	 邮箱：
                        </td>
                        <td>
                           <input id="email"  class="isemail" name="roster.email"    />
                        </td>	
                    </tr>
                </table>
  
                <div align="center">
                	<input type="button"  class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="添加" id="addroster"/>
                </div>
        
         </form>
         </div>
         
         <!--打印花名册信息  -->
 <form name="postprintForm" id="postprintForm" method="post">
        <div class="contentbannb jqmWindow" style="width：400px;right: 45%;top: 10%;" id="jqModelprint">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    	打印花名册
                    <div class="close">
                    </div>
                </div>
                <table align="center" id="rosterPrintTable" style="text-align:center" cellpadding="0" cellspacing="0">
                    
                    <tr>
                        <td style="width:100px;">
                          					<label>姓名
                          <input type="checkbox" name="checkbox" class="checkbox" value="name" />
                       						 </label>
                           						</td>
                       
                        <td style="width:100px;">
                                               <label>性别 
                                                 <input type="checkbox" class="checkbox"  value = "sex"/></label>
                                               </td>
                        <td style="width:100px;">
                                                <label>部门
                                                  <input type="checkbox"  class="checkbox" value = "organizationName"/></label> 
                                                </td>
                    </tr>
                    <tr>
                        <td style="width:100px;">
                                                <label>岗位
                                                  <input type="checkbox"  class="checkbox" value = "postname"/> </label>
                                                </td>
                       
                        <td style="width:100px;">    
                                              <label>电话 
                                                 <input type="checkbox"  class="checkbox" value = "phone"/></label>
                                         </td>
                        <td style="width:100px;">
                                                <label>邮箱
                                                  <input type="checkbox"  class="checkbox" value = "email"/></label> 
                                                </td>
                    </tr>
          </table>
                <div align="center">
                    <input type="button" class="input-button" id="toprint" value=" 打印 " />
                </div>
        </div>
         </form>
         
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
