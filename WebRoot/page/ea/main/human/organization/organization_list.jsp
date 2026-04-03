<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>组织机构</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<!-- 后台当前公司组织机构 列表-->
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript">
var pNumber=${pageNumber};
var  personurl='';
var  organizationPID='';
var  basePath='<%=basePath%>';
var  companyName='';
var   search= '${search}'
var searchPID = '${organizationID}';
$(function(){
$(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close')// 添加触发关闭的selector
	//保存或修改后,修正机构树
	var oid = '${organization.organizationID}';
	var oname = '${organization.organizationName}';
	if(window.parent.tree.getItemText(oid) != '0'){
		window.parent.tree.setItemText(oid,oname,0);
	}else if(oid != ""){
		window.parent.tree.insertNewChild(parent.treeid,oid,oname,0,0,0,0);
	}
	//保存或修改后,修正机构树
    $('.flexme11').flexigrid({
    	allDouble:true,
		height: 140,
		width: 'auto',
		minwidth: 30,
		title: '当前机构--' +parent.tree.getSelectedItemText(),
		minheight: 80 ,
		  buttons: [ 
		 {
            name: '添加下级',
            bclass: 'add',
			onpress : action//当点击调用方法
        }, {
            // 设置分割线  
            separator: true
        }, {
            name: '禁用当前机构',
            bclass: 'delete', 
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改当前机构',
            bclass: 'edit',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '排序下属机构',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name:'查询',
            bclass:'mysearch',
            onpress : action//当点击调用方法
        },{
             separator: true
        },{
            name: '导出',
            bclass: 'excel',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }/*,{
            name: '机构银行帐号',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        },{
            name: '机构负责人',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }*/
        ]
	});
    function action(com, grid){
        switch (com) {
            case '添加下级':
                if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
               open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
					 + parent.treeid + "&porganization.organizationName=" + parent.treename + "&organization.organizationID=");
                break;
            case '禁用当前机构':
                if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择单位");
                    return;
                }
                var treeid = parent.treeid;
                var companyID = parent.companyID;
                if (treeid == companyID) {
                    alert('不能禁用单位')
                    return;
                }
                if (confirm("禁用节点会丢失其下的数据？是否禁用？")) {
                    window.parent.tree.deleteItem(treeid);
					$("#organizationform").attr("action","<%=basePath%>ea/organization/t_ea_delOrganization.jspa?pageNumber=${pageNumber}&organizationID=" +
                    treeid +
                    "&selectedtreeID=" +
                    parent.treeid)
					document.organizationform.submit.click();
                }
                break;
            case '修改当前机构':
                treeid = parent.treeid;
                companyID = parent.companyID;
                if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
                if (companyID == treeid) {
                    alert("不能修改单位");
                    return;
                }
              	parentid = parent.parentid;
                parentname = parent.parentname;
				open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
					 + parentid + "&porganization.organizationName=" + parentname + "&organization.organizationID="
					 + treeid);
                break;
			case '排序下属机构':
				if ("" == parent.tree.getSelectedItemId()) {
	                    alert("请选择机构");
	                    return;
	                }
				$("#oID").val(parent.treeid);
				$("#oName").val(parent.treename);
				$("#sortchildren").attr("action","<%=basePath%>ea/organization/ea_toSortChildOrganization.jspa?pageNumber=${pageNumber}")
				document.sortchildren.submit.click();
				break;
			case '查询':
			     $("#jqModelSearch").jqmShow();
			     $("form#SearchForm").find("input#organizationName").focus();
			break;
			case '导出':
			     if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
				treeid= parent.treeid;
   			    url="<%=basePath%>ea/organization/ea_showExcel.jspa?organizationID="+treeid;
				open(url)
				break;
			 case '设置每页显示条数':
			var url="<%=basePath%>ea/organization/ea_getOrganizationListAll.jspa?organizationID=${organizationID}";
				numback(url);
				break;
			case '机构银行帐号':            //机构设置
			
               if (organizationPID == "") {
                             alert("请选择具体单位！");
                              return;
                         }
               personurl = basePath + "ea/institutionsregistration/ea_getListInstitutionsRegistration.jspa?pageNumber="+pageNumber+"&institutionsRegistration.organizationPID=";
               $("#mainframe").css({"height":"auto"}).attr("src", basePath + "ea/institutionsregistration/ea_getListInstitutionsRegistration.jspa?institutionsRegistration.organizationPID="+organizationPID+"&pageNumber="+pageNumber);
               $(window).resize();
              break;
		case '机构负责人':            //机构设置
			
               if (organizationPID == "") {
                             alert("请选择具体单位！");
                              return;
                         }
               personurl = basePath + "ea/agencies/ea_getListAgencies.jspa?pageNumber="+0+"&agencies.organizationPID=";
               $("#mainframe").attr("src",basePath + "ea/agencies/ea_getListAgencies.jspa?pageNumber="+0+"&agencies.organizationPID="+organizationPID);
              break;		
        }
    }
    $("#tosearch").click(function () {
    	//alert($("#SearchForm").find("#organizationUrl").val())
    	//return 
		$("#SearchForm").attr("action",basePath + "/ea/organization/ea_getOrganizationListAll.jspa?pageNumber="+${pageNumber}+"&organizationID="+searchPID);
		document.SearchForm.submit.click();
	});
    $("#org").text(parent.treename);
    $(".tt").click(function(){
        $(".tt").css("background", "#FFFFFF");
        $(this).css("background", "#eaf1f7");
        
    })
				$("#isBack").click(function(){
                  $("#jqmWindow2").jqmHide();
              	});
				// 选择确定
                $("#isSubmit").click(function(){
                    var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
                    var parm1 = $("#parm1",$("#jqmWindow2")).attr("value");
                    var parm2 = $("#parm2",$("#jqmWindow2")).attr("value");
                    var parm3 = $("#parm3",$("#jqmWindow2")).attr("value");
					//弹出框的页面必须声明opertionID这个参数接收id
                    var value1 = window.frames["ifr"].opertionID;
                    if(value1 == ""){
                         alert("请选择")
                         return;
                    }
					//弹出框的页面存在于span中才取得到
                    var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm2).text();
                    var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm3).text();
                    //alert(value1+"--"+parm1+"--" +myfrom )
	                if(parm1 != "")
	                   window.frames["mainframe"].$("#"+myfrom).find("#"+parm1).attr("value",value1)
                    if(parm2 != "")
                    	window.frames["mainframe"].$("#"+myfrom).find("#"+parm2).attr("value",value2)
                    if(parm3 != "")
                    	window.frames["mainframe"].$("#"+myfrom).find("#"+parm3).attr("value",value3)
                    $("#ifr").attr("src","");
                  $("#jqmWindow2").jqmHide();
              });
	$(".flexme11").find("select").each(function(){
                    $s = $(this).hide()
                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
                    $o.insertAfter($s)
                })
                $(".flexme11 tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    organizationPID =this.id;
                    if(personurl){                              
                    $("#mainframe").attr("src",personurl + organizationPID);
                    }
                    companyName = $(this).find("span#organizationName").text();     
                })
	
});
//function toedit(organizationID) {
//	$("#organizationID").attr("value", organizationID);
//	$("#porganizationID").attr("value", parent.treeid);//后查不到就代表上级是单位.查到了就用查到的替换
//	$("#porganizationName").attr("value", parent.treename);
//	$("#organizationform").attr("action","<%=basePath%>ea/organization/ea_toAdd.jspa?pageNumber=${pageNumber}")
//	document.organizationform.submit.click();
//}
function toedit(organizationID) {
	open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
			 + parent.treeid + "&porganization.organizationName=" + parent.treename + "&organization.organizationID="
			 + organizationID);
}
function deleteid(treeid) {
	if ("" == treeid)
		treeid = parent.treeid;
	if ("" == treeid) {
		alert("请选择单位");
		return;
	}
	companyID = parent.companyID;
	if (treeid == companyID) {
		return;
	}
	if (confirm("禁用节点会丢失其下的数据？是否禁用？")) {
	     window.parent.tree.deleteItem(treeid);
		 $("#organizationform").attr("action", "<%=basePath%>ea/organization/t_ea_delOrganization.jspa?pageNumber=${pageNumber}&organizationID="
				+ treeid+"&selectedtreeID="+parent.treeid)
	document.organizationform.submit.click();
	}
}
//子窗口调用
function refresh(){
	window.location.reload();
}
</script>
</head>
	<body>
		<form method="post" name="sortchildren" id="sortchildren">
			<%-- 不告诉你--%>
			<input type="submit" name="submit" id="submit" style="display: none" />
			<input id="oID" name="organizationID" type="hidden" />
			<input id="oName" name="organizationName" type="hidden" />
		</form>

		<form name="organizationform" id="organizationform" method="post"
			action="">
			<input type="submit" name="submit" id="submit" style="display: none" />
			<input name="porganization.organizationID" type="hidden"
				id="porganizationID" />
			<input name="porganization.organizationName" type="hidden"
				id="porganizationName" />
			<input name="organization.organizationName" type="hidden"
				id="organizationName" />
			<input name="organization.organizationID" type="hidden"
				id="organizationID" />
			<div class="main_main">
				<div style="width: 100%">
					<table class="flexme11">
						<thead>
							<tr>
								<th width="60" align="center">
									选择
								</th>
								<th width="60" align="center">
									序号
								</th>
								<th width="100" align="center">
									机构编号
								</th>
								<th width="133" align="center">
									部门名称
								</th>
								<th width="100" align="center">
									岗位编号
								</th>
								<th width="100" align="center">
									岗位名称
								</th>
								<th width="100" align="center">
									职务编号
								</th>
								<th width="100" align="center">
									职务名称
								</th>
								<th width="120" align="center">
									负责工作范围
								</th>
								
								<th width="100" align="center">
									操作
								</th>
							</tr>
						</thead>
						<%
							int number = 1;
						%>
						<tbody>
							<s:iterator value="pageForm.list">
								<tr ondblclick="toedit('<s:property value="organizationID"/>')"
									id="<s:property value="organizationID"/>">
									<td>
										<input type="radio" name="a" class="JQuerypersonvalue"
											value="<s:property value="organizationID"/>" />
									</td>
									<td><%=number%></td>
									<td>
										<span id="ocode"> <s:property value="ocode" /> </span>
									</td>
									<td>
										<span id="organizationName"> <s:property
												value="organizationName" /> </span>
									</td>
									<td>
										<span id="opostCode"> <s:property value="opostCode" />
										</span>
									</td>
									<td>
										<span id="opostName"> <s:property value="opostName" />
										</span>
									</td>
									<td>
										<span id="odutiesID"> <s:property value="odutiesID" />
										</span>
									</td>
									<td>
										<span id="odutiesName"> <s:property value="odutiesName" />
										</span>
									</td>
									<td>
										<s:select list="%{#request.SInterfaceList}"
											listKey="interfaceUrl" listValue="interfaceName"
											name="organizationUrl" theme="simple" disabled="true">
										</s:select>
									</td>
									<td>
										<a href="#"
											onclick="toedit('<s:property value="organizationID"/>')"><img
												src="<%=basePath%>images/ea/main/edit.gif" width="16"
												height="16" title="修改" border="0" /> </a>
										<a href="#"
											onclick="deleteid('<s:property value="organizationID"/>')"><img
												src="<%=basePath%>images/ea/main/gtk-del.png" width="16"
												height="16" title="删除" border="0" /> </a>
									</td>
									<%
										number++;
									%>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<c:import url="../../../page_navigator.jsp">
						<c:param name="actionPath"
							value="ea/organization/ea_getOrganizationListAll.jspa?pageNumber=${pageNumber}&organizationID=${organizationID}"></c:param>
					</c:import>
				</div>
					<iframe src="" name="main" style="height:0;width:100%;" marginwidth="0"
						scrolling="no"  marginheight="0" frameborder="0"
						id="mainframe" border="0" framespacing="0" noresize="noResize"
						vspale="0"></iframe>
 				
				<s:token />
			</div>

		</form>
	<!--搜索窗口 -->
	<form name="SearchForm" id="SearchForm" method="post">
		<div class="jqmWindow" style="width: 400px;right: 25%;top:10%;"
			id="jqModelSearch">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table width="396" id="SearchTable">
				<tr>
					<td width="123" align="right">负责工作范围：</td>
					<td><s:select list="%{#request.SInterfaceList}"
					headerKey=""	 headerValue="请选择"	listKey="interfaceUrl" listValue="interfaceName"
							name="organization.organizationUrl" id="organizationUrl" theme="simple" >
						</s:select>
					</td>
				</tr>
				<tr>
					<td align="right">部门名称：</td>
					<td><input id="organizationName" style="width:195px"
						name="organization.organizationName" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " /><input
					name="search" type="hidden" value="search" />
			</div>
		</div>
	</form>
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none;  background: #eff;z-index: 10; top: 1%;left:1%;">
			<input style="display: none;" id="myform" />
			<input style="display: none;" id="parm1" />
			<input style="display: none;" id="parm2" />
			<input style="display: none;" id="parm3" />
			<iframe name="ifr" id="ifr" width="100%" height="380px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isBack" value="返回"
					style="cursor: hand" />
				<input type="button" class="input-button" id="isSubmit" value="确定"
					style="cursor: hand" />
			</div>
		</div>
		<script type="text/javascript">
		   setTimeout(function(){ 
				   var _height = $(window).height();		
				   if($("#mainframe").height() > 0){
				       $(".bDiv").css({"height": _height /2 - 30 - 26 - 45 + "px"});
				       $("#mainframe").css({"height": _height / 2 - 10 + "px"});
				   }else{		    
				       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 30 + "px"});
				       $("#mainframe").css({"height": 0 + "px"});
				   }
				},100);
		    
		    $(window).resize(function(){ 
				setTimeout(function(){ 
				   var _height = $(window).height();		
				   if($("#mainframe").height() > 0){
				       $(".bDiv").css({"height": _height /2 - 30 - 26 - 45 + "px"});
				       $("#mainframe").css({"height": _height / 2 - 10 + "px"});
				   }else{		    
				       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 30 + "px"});
				       $("#mainframe").css({"height": 0 + "px"});
				   }
				},100);
	        }); 
        </script>  
              
	</body>
</html>