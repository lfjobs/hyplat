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
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>社会人力列表</title>
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
		var opertionID = "";
		var personurl = "";
		var personIdentityCard;
		var staffsize = 0 ;//后台验证身份证时应该查到的人数
		var token =0;
		var retoken = 0;
		var notoken = 0;
		var photosizes = 0;
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
        title: '社会人力列表',
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
				var url=basePath + "/ea/stafftrack/ea_getStaffForCashier.jspa?search="+psearch+"&parameter=${parameter}";
					numback(url);
				break; 
		}
	}			
	// 点击选中物品
	//$(".JQueryflexme tr[id]").live("click", function(event) {
	//	var b = $("input.chx", $(this)).attr("checked");
	//	$("input.chx", $(this)).attr("checked", !b);
	//})
	$(".JQueryflexme tr[id]").click(function() {
		opertionID = this.id;
		//staffName = $(this).find("span#staffName").text();
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	// 复选框选中物品
	//$(".chx").live("click", function(event) {
	//	var b = $(this).attr("checked");
	//	$(this).attr("checked", !b);
	//})
	//$(".JQueryflexme tr[id]").click(function(){
		//opertionID=this.id;
		//alert(opertionID)
		 //if(personurl==""){
              // $("#mainframe").attr("src",personurl + opertionID);
          // }
		//$("input.JQuerypersonvalue",$(this)).attr("checked","checked");
	//});
	$("input#selectPerson").click(function(){
		opertionID="";
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
								opertionID += $(this).val() + ",";
							}
			});
	});
	 //查询相关操作
     $("#searchStaff").click(function(){
         $("#cstaffSearchForm").attr("action", basePath + "/ea/stafftrack/ea_getStaffSearch.jspa?pageNumber="+ppageNumber);
         document.cstaffSearchForm.submit.click();
     });
	
		
});

</script>
	</head>
	<body>
		<div class="main_main">
			<input type="button" id="selectPerson" style="display:none" name="button5" value="确定" />
			<table class="JQueryflexme" id="partnerSearchTable">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="80" align="center">
							往来关系
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<!-- 
						<th width="250" align="center">
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
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${staffID}">
							<td>
								<input type="radio" class=" JQuerypersonvalue"
									value="${staffID}" title="${staffID}" name=check />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="recordCode">${recordCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="relation">${relation}</span>
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
								<span id="nativePlace">${nativePlace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
								<span id="staffID" style="display: none">${staffID}</span>
								<span id="relationID" style="display: none">${relationID}</span>
								<span id="relationKey" style="display: none">${relationKey}</span>
								<span style="display: none" id="photo">${photo}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${staffAddress}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
							</td>
							<td>
								<span id="referenceCode">${referenceCode}</span>
							</td>
							<td>
								<span id="referenceOrganization">${referenceOrganization}</span>
							</td>
							<td>
								<span id="verifyTime" class="datas">${fn:substring(verifyTime,0,
									10)}</span>
							</td>
							<td>
								<span id="staffDesc">${staffDesc}</span>
							</td>
							-->
						</tr>
						<%
						number ++;
					%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/stafftrack/ea_getStaffForCashier.jspa?search=${search}&pageNumber=${pageNumber}&parameter=${parameter }">
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
	                        <td>
	                            查询条件
	                        </td>
	                    </tr>
	                    <tr>
	                        <td  width="123" align="center">
	                            人员编号：
	                        </td>
	                        <td >
	                            <input name="cstaff.staffCode" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="123" align="center">
	                            人员姓名：
	                        </td>
	                        <td >
	                            <input name="cstaff.staffName" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="123" align="center">
	                            身份证：
	                        </td>
	                        <td >
	                            <input name="cstaff.staffIdentityCard" />
	                        </td>
	                    </tr>
	                </table>
	                <div align="center">
	                    <input type="button" class="input-button" id="searchStaff" value=" 查询 " /><input name="search" type="hidden" value="search" />
	                </div>
	        </div>
        </form>
	</body>
</html>
