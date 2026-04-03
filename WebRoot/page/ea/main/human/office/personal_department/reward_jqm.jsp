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
		<title>责任人列表</title>
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>



		<script type="text/javascript">
		
		var pbasePath = '<%=basePath%>';
		var basePath = '<%=basePath%>';
		var ppageNumber = ${pageNumber};
		var psearch = '${search}';
		var pstaffID = '${staffID}';
		var guanxi='${guanxi}';
		var opertionID = "";
		var personurl = "";
		var personIdentityCard;
		var staffsize = 0 ;//后台验证身份证时应该查到的人数
		var token =0;
		var retoken = 0;
		var notoken = 0;
		var photosizes = 0;
		var comID = '${comID}';
		var staffName='${staffName}'
		
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
        title: '人员选择列表',
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
			var url=basePath + "ea/reward/ea_getSCO.jspa?search="+psearch+"&parameter=${parameter}&comID="+comID+"&staffName="+staffName+"&guanxi="+guanxi;
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
     $("#searchStaff").click(function(){
         $("#cstaffSearchForm").attr("action", basePath + "ea/reward/ea_getSCO.jspa?pageNumber="+ppageNumber+"&comID="+comID);
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
						<th width="40" align="center">
							选择
						</th>
						<th width="150" align="center">
							姓名
						</th>
						<th width="200" align="center">
							部门
						</th>
						<th width="150" align="center">
							职务
						</th>
						<th width="100" align="center">
							专兼岗
						</th>
					</tr>
				</thead>
				<tbody>

					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="arr">
						<tr id="<%=number%>">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[0]}" />
							</td>
							<td>
								<span id="staffname">${arr[3]}</span>
								<span id="staffid" style="display: none;">${arr[2]}</span>
							</td>
							<td>
								<span id="organizationname">${arr[5]}</span>
								<span id="organizationid" style="display: none;">${arr[4]}</span>
							</td>
							<td>
								<span id="postname">${arr[1]}</span>
								<span id="deppostid" style="display: none;">${arr[0]}</span>
							</td>
							<td>
								<span id="postname">
									<c:if test="${arr[6]=='01'}">专岗</c:if>
									<c:if test="${arr[6]=='00' }">兼岗</c:if>
								</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/reward/ea_getSCO.jspa?search=${search}&pageNumber=${pageNumber}&parameter=${parameter }&comID=${comID}&staffName=${staffName}&guanxi=${guanxi}">
				</c:param>
			</c:import>
		</div>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
		<input type="submit" name="submit" style="display: none"/>
			<div class="jqmWindow" style="width: 400px;right: 25%;top: 10%" id="jqModelSearch">
	                <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	                </div>
	                <table id="SearchTable">
	                    <tr>
	                        <td width="123" align="center">
	                            人员姓名：
	                        </td>
	                        <td >
	                            <input name="staffName" />
	                        </td>
	                    </tr>
	                </table>
	                <div align="center">
	                    <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
	                    <input name="search" type="hidden" value="search" />
	                </div>
	        </div>
        </form>
	</body>
</html>