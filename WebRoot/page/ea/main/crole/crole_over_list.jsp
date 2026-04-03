<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限移交列表</title>
<!-- 后台管理 角色管理  -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var search='${search}';
$(function(){ 
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
		}).jqmAddClose('.close')//
    $('.flexme11').flexigrid({
		height: 300,
		width: 'auto',
		minwidth: 30,
		title: '权限移交列表',
		minheight: 80 , buttons: [ 
		 {
            name: '查询',
            bclass: 'mysearch',
			onpress : action //当点击调用方法
        }, {
            // 设置分割线  
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        },{
            name: '打印',
            bclass: 'delete',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
});
function action(com, grid){
        switch (com) {
            case '查询':
            $("#jqModelSearch").jqmShow();
                break;
            case '设置每页显示条数':
			var url="<%=basePath%>ea/crole/ea_getCRoleOverList.jspa?1=1&search="+search;
				numback(url);
				break;
			case '打印':
				window.open("<%=basePath%>ea/crole/ea_roleOverPrint.jspa?search="+search)	
				break;  
        }
    }
    $("tr[id]").click(function(){
    	roleID = this.id
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		if(ziUrl){
			$("#mainframe").attr("src",ziUrl+ roleID);		
		}
    })
    $("#tosearch").click(function(){
    	$("#SearchForm").attr("action","<%=basePath%>ea/crole/ea_searchCRoleOver.jspa?")
    	document.SearchForm.submit.click();
    })
    
 });
</script>
</head>
<body>
<form name="croleList" id="croleList" method="post"><input type="submit" name="submit" style="display:none"/>
<div class="main_main">
		<table class="flexme11">
								<thead>
							    	   	<tr>
							             	<th width="23" align="center">
												序号
											</th>
											<th width="180" align="center">
												公司名称
											</th>
											<th width="100" align="center">
												帐号名称
											</th>
											<th width="100"  align="center">
												起始时间
											</th>
											<th width="100"  align="center">
												结束时间
											</th>
											<th width="100" align="center">
												原权限责任人
											</th>
											<th width="300" align="center">
												移交权限责任人
											</th>
											
							            </tr>
							    </thead>
          <%int number =1;  %>
          <tbody align="center">
           <s:iterator value="pageForm.list" var="lis" >
            <tr id="<s:property value="lis[1]"/>" align="center" height="22" class="JQueryflexme">
	            <td><%=number%></td>
	            <td> ${lis[2]}</td>
	            <td> ${lis[3]}</td>
	            <td> ${lis[6]==null?"未知":lis[6]}</td>
	            <td> ${lis[7]==null?"未知":lis[7]}</td>
	            <td> ${lis[4]==null?"无":lis[4]}</td>
	             <td> ${lis[5]==null?"无":lis[5]}</td>
            <%number++; %>
          </tr>
          </s:iterator>
          </tbody>
        </table>
        <c:import url="../../page_navigator.jsp">
			<c:param name="actionPath" value="ea/crole/ea_getCRoleOverList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
		</c:import>
        <s:token/>
</div>
</form>
   <!--搜索窗口 -->
        <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow" style="width: 300px;left: 30%;top: 10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="300" id="cataffSearchTable">
                  <tr>
                        <td align="right">
                            操作帐号：  
                         </td>
                  <td>
                             <s:select id="accountID"  headerKey="" headerValue="请选择" list="caccountList" listKey="accountID" listValue="accountEmail" name="caccountRecords.accountID" theme="simple" ></s:select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                           原权限责任人：                        </td>
                  <td>
                             <s:select id="beforePeople"  headerKey="" headerValue="请选择" list="staffList" listKey="staffID" listValue="staffName" name="caccountRecords.beforePeople" theme="simple" ></s:select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            移交权限责任人：                        </td>
                  <td>
                            <s:select id="afterPeople"  headerKey="" headerValue="请选择" list="staffList" listKey="staffID" listValue="staffName" name="caccountRecords.afterPeople" theme="simple" ></s:select>
                        </td>
                    </tr>
                    <%--<tr>
                        <td align="right">
                            操作日期：                     
                       </td>
                  		<td>
                            <input name="sdate" id="sdate" onfocus="date(this);"/> 
                      至
                        <input name="edate" id="edate" onfocus="date(this);"/>
                        </td>
                    </tr>
                --%></table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
        </div>
          </form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>