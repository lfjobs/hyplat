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
		<title>人员管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

		<script  type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>

		<script type="text/javascript">
   var relationID = '';
   var basePath='<%=basePath%>';           
   var pNumber =${pageNumber};  
   var token = 0;
   var notoken = 0;
   var search='${search}';
   var companyID = "${companyID}";
   var title = "${title}";
   var orgID = "${orgID}";
    $(function(){
      	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 150,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [ {
					name : '查看',
					bclass : 'edit',
					onpress : action
	
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				} ]
			});
	function action(com, grid) {
		switch (com) {
			
			case '查看' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								relationID = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				var personvalue = $("tr#"+relationID).find("#staffID").text();
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			
			case '查询' :
				$("#relations").children('option:eq(0)').attr("selected","selected");
				$("#jqModelSearch").jqmShow();
				break;
			
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/telmessage/eas_getPersonByDept.jspa?search="
						+ search+"&companyID="+companyID+"&title="+title+"&orgID="+orgID;
				numback(url);
				break;
		}
	}
    
    $(".JQueryflexme tr[id]").dblclick(function() {
		action("查看");
	});
		// 复选框选中物品
	$(".chx").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
    
    });
 
</script>
	</head>
	<body>
		<div class="main_main" >
			<table class="JQueryflexme">
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
						<th width="180" align="center">
							公司
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
						<th width="100" align="center">
							电话
						</th>
						<!--  
						<th width="250" align="center">
							身份证地址
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
								<input type="checkbox" class="chx JQuerypersonvalue"
									value="${staffID}" title="${staffID}" name="chbox" />
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
								<span id="companyName">${companyName}</span>
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
								<span id="relationKey" style="display: none">${relationKey}</span>
								<span style="display: none" id="photo">${photo}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${staffAddress}</span>
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
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/telmessage/eas_getPersonByDept.jspa?pageNumber=${pageNumber}&search=${search}&companyID=${companyID}&title=${title}&orgID=${orgID}&companyName=${companyName}">
				</c:param>
			</c:import>
			
		</div>
		<iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
		<span id="Relation" style="display: none"><s:property
				value="#session.tablesearch.relation" /></span>
		<span id="RecordCount" style="display: none"><s:property
				value="#session.RecordCount" /></span>

		<div class="jqmWindow" style="width: 350px; right: 45%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询往来个人
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							姓名：
						</td>
						<td width="261">
							<input name="contactUser.staffName" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							编号：
						</td>
						<td width="261">
							<input name="contactUser.staffCode" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">个人身份证号：</td>
                     <td><input name="contactUser.staffIdentityCard" /></td>
                    </tr>
                    <!--  
                    <tr>
                            <td align="right">个人身份证地址：</td>
                     <td><input name="contactUser.staffAddress" /></td>
                    </tr>
                    <tr>
                        <td align="right">个人电话：</td>
                     <td><input name="contactUser.reference" /></td>
                    </tr>
                    -->
                    <tr>
                        <td align="right">往来关系：</td>
                     <td>
                     <select id="relations"></select>
                     </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>

			

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			    $("#mainframe").css({"height": _height / 2 - 20 + "px"});
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