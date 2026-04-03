<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>帐号信息</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>



		<script type="text/javascript">
				
		var basePath = '<%=basePath%>';		
		var opertionID = "";
		var personurl = "";		
		var token =0;
		var retoken = 0;
		var notoken = 0;
		var photosizes = 0;
		var custype="${custype}";
		var ppageNumber = ${pageNumber};
		function gotoLogin(){
			document.location="<%=basePath%>/page/ea/not_login.jsp";
		}
$(function(){
	$(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话  
        overlay: 20 // 遮罩程度%  
    }).jqmAddClose('.close')// 添加触发关闭的selector  
//		.jqDrag('.drag');// 添加拖拽的selector
    $('.JQueryflexme').flexigrid({
        height: 220,
        width: 'auto',
        minwidth: 30,
        title: '人员列表',
        minheight: 80,
        buttons: [{
                  name: '查询',
                  bclass: 'mysearch',
                  onpress: action//当点击调用方法
              }, {
                  separator: true
              }, {
				  name: '设置每页显示条数',
				  bclass: 'mysearch',
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
			var url=basePath + "ea/goldticket/ea_getAccounts.jspa?custype="+custype;
				numback(url);
				break; 
		}
	}
   
	$(".JQueryflexme tr[id]").click(function(){
		opertionID=this.id;
		 if(personurl){
               $("#mainframe").attr("src",personurl + opertionID);
           }
		$("input.JQuerypersonvalue",$(this)).attr("checked","checked");
	});
	 //查询相关操作     
     $("#searchStaff").click(function() {		
 		$("#cstaffSearchForm").attr("action",basePath
 				+ "ea/goldticket/ea_getAccounts.jspa?pageNumber="
 				+ ppageNumber);			
 		document.cstaffSearchForm.submit.click();				
 	});
		
});
</script>

	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme" id="partnerSearchTable">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">选择</th>
						<th width="40" align="center">序号</th>
						<th width="100" align="center">微分金帐号</th>
						<th width="100" align="center">帐号级别</th>
						<th width="100" align="center">帐号属性</th>							
						<th width="100" align="center">人员姓名</th>
						<th width="100" align="center">曾用名</th>
						<th width="50" align="center">性别</th>
						<th width="100" align="center">出生日期</th>
						<th width="100" align="center">籍贯</th>
						<th width="100" align="center">国籍</th>
						<th width="100" align="center">民族</th>
						<th width="200" align="center">身份证</th>										
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${pageForm.list}" varStatus="num">
                        <tr id="${arr[0]}">
                            <td><input type="radio" name="a" class="JQuerypersonvalue" value="${arr[0]}" /></td>
                            <td><span>${num.index+1 }</span></td>                        
                            <td><span id="account">${arr[1]}</span></td>                           
                            <td>                           
	                            <c:if test="${arr[10] == 0}">
	                            	<span id="custype">平台</span>
	                            </c:if>
	                             <c:if test="${arr[10] == 1}">
	                            	<span id="custype">税务</span>
	                            </c:if>
	                             <c:if test="${arr[10] == 2}">
	                            	<span id="custype">公司</span>
	                            </c:if>
	                             <c:if test="${arr[10] == 3}">
	                            	<span id="custype">合伙创业</span>
	                            </c:if>
	                             <c:if test="${arr[10] == 4}">
	                            	<span id="custype">微分金</span>
	                            </c:if>
	                             <c:if test="${arr[10] == 5}">
	                            	<span id="custype">代理商</span>
	                            </c:if>
	                            <c:if test="${arr[10] == 6}">
	                            	<span id="custype">vip客户</span>
	                            </c:if>
	                             <c:if test="${arr[10] == 7}">
	                            	<span id="custype">普通客户</span>
	                            </c:if>                        
                            </td>   
                            <td>
                            	<c:if test="${arr[11] == 1}">
                            		<span id="custype">个人</span>
                            	</c:if>
                            	<c:if test="${arr[11] == 2}">
                            		<span id="custype">企业</span>
                            	</c:if>                           
                            </td>                                                                           
                            <td><span id="staffname">${arr[2]}</span></td>
                            <td><span id="username">${arr[3]}</span></td>
                            <td><span id="sex">${arr[4]}</span></td>
                            <td><span id="birthday">${arr[5]}</span></td>
                            <td><span id="nativeplace">${arr[6]}</span></td>
                            <td><span id="nationality">${arr[7]}</span></td>
                            <td><span id="nation">${arr[8]}</span></td>
                            <td><span id="staffidentitycard">${arr[9]}</span></td>                           
                           
                        </tr>                                           
                    </c:forEach>
				
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/goldticket/ea_getAccounts.jspa?custype=${custype}&pageNumber=${pageNumber}">
				</c:param>
			</c:import>
		</div>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
		<input type="submit" name="submit" style="display: none"/>
		<input  name="parameter" value="${parameter }" style="display: none"/>
			<div class="jqmWindow" style="width: 400px;right: 25%;top: 10%" id="jqModelSearch">
	                <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	                </div>
	                <table id="cataffSearchTable">
	                    <tr>
	                        <td  width="123" align="center">
	                            微分金帐号：
	                        </td>
	                        <td >
	                            <input name="account" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="123" align="center">
	                            帐号级别：
	                        </td>
	                        <td >
	                            <input name="custype" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="123" align="center">
	                           人员姓名：
	                        </td>
	                        <td >
	                            <input name="staffname" />
	                        </td>
	                    </tr>
	                </table>
	                <div align="center">
	                    <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
	                </div>
	        </div>
        </form>
	</body>
	
		
</html>