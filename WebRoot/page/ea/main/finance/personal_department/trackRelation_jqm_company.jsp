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
		<title>社会往来单位列表</title>
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
        title: '往来单位列表',
        minheight: 80,
        buttons: [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
			  }, {
					// 设置分割线
					separator : true
			  }, {
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
			case '添加':
				open(basePath+"/ea/contactcompany/ea_getListContactCompany.jspa");
				break;
			case '查询':
					$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数':
				var url=basePath + "/ea/companytrack/ea_getcompanyForCashier.jspa?search="+psearch+"&parameter=${parameter}";
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
         $("#cstaffSearchForm").attr("action", basePath + "/ea/companytrack/ea_getCompanySearch.jspa?pageNumber="+ppageNumber);
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
							请选择
						</th>
						<th width="250" align="center">
							单位名称
						</th>
						<th width="250" align="center">
							单位地址
						</th>
						<th width="100" align="center">
							单位电话
						</th>
						<th width="100" align="center">
							单位负责人
						</th>
						<th width="100" align="center">
							单位负责人电话
						</th>
						<th width="100" align="center">
							行业类别
						</th>
						<th width="100" align="center">
							单位备注
						</th>
						<th width="100" align="center">
							单位状态
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list">
						<tr id="${ccompanyID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${ccompanyID}" />
							</td>
							<td>
								<span id="companyName">${companyName}</span>
							</td>

							<td>
								<span id="companyAddr">${companyAddr}</span>
							</td>
							<td>
								<span id="companyTel">${companyTel}</span>
							</td>
							<td>
								<span id="cresponsible">${cresponsible}</span>
							</td>
							<td>
								<span id="responsibleTel">${responsibleTel}</span>
							</td>
							<td>
								<span id="industryType">${industryType}</span>
							</td>
							<td>
								<span id="remark">${remark}</span>
								<span id="address" style="display: none">${address}</span>
								<span id="ccompanyID" style="display: none">${ccompanyID}</span>
								<span id="ccompanyKey" style="display: none">${ccompanyKey}</span>
								<span id="dealIn" style="display: none">${dealIn}</span>
							</td>
							<td>
								<span id="custStatus"><c:if test="${custStatus == '01'}">未注册单位</c:if>
								                      <c:if test="${custStatus == '02'}">已注册单位</c:if></span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/companytrack/ea_getcompanyForCashier.jspa?search=${search}&pageNumber=${pageNumber}&parameter=${parameter }">
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
	                            单位姓名：
	                        </td>
	                        <td >
	                            <input name="contactCompany.companyName" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="123" align="center">
	                            单位地址：
	                        </td>
	                        <td >
	                            <input name="contactCompany.companyAddr" />
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
