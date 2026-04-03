<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>后台组织机构</title>
		<!-- 总公司和子公司的组织机构 -->
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		
	</head>
	<body>
		<form method="post" name="sortchildren" id="sortchildren">
			<%-- 不告诉你--%>
			<input type="submit" name="submit" style="display: none" />
			<input id="oID" name="organizationID" type="hidden" />
			<input id="oName" name="organizationName" type="hidden" />
		</form>
		<form name="organizationform" id="organizationform" method="post">
			<input type="submit" name="submit" style="display: none" />

			<input name="porganization.organizationID" type="hidden"
				id="porganizationID" />
			<input name="porganization.organizationName" type="hidden"
				id="porganizationName" />
			<input name="organization.organizationName" type="hidden"
				id="organizationName" />
			<input name="organization.organizationID" type="hidden"
				id="organizationID" />
			<div class="main_main">

				<table class="flexme11">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="30" align="center">
								序号
							</th>
							<th width="100" align="center">
								机构编号
							</th>
							<th width="100" align="center">
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
							<th width="80" align="center">
								操作
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr ondblclick="alee('<s:property value="organizationID"/>')"
								id="<s:property value="organizationID"/>">
								<td>
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="<s:property value="organizationID"/>" />
								</td>
								<td><%=number%></td>
								<td>
									<span id="ocode"> <s:property value="ocode" />
									</span>
								</td>
								<td>
									<span id="organizationName"> <s:property
											value="organizationName" />
									</span>
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
											height="16" title="修改" border="0" />
									</a>
									<a href="#"
										onclick="deleteid('<s:property value="organizationID"/>')"><img
											src="<%=basePath%>images/ea/main/gtk-del.png" width="16"
											height="16" title="禁用" border="0" />
									</a>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/corganization/ea_getOrganizationListAll.jspa?pageNumber=${pageNumber}&organizationID=${organizationID}"></c:param>
				</c:import>
				<div style="height:220px;">
					<iframe src="" name="mainframe" marginwidth="0"
						scrolling="no" marginheight="0" frameborder="0" style="height:0;widht:100%;"
						id="mainframe" border="0" framespacing="0" noresize="noResize" ></iframe>
				</div>
				<s:token />
			</div>
		</form>
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 2%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm1" />
				<input style="display: none;" id="parm2" />
				<input style="display: none;" id="parm3" />
			</div>
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
var  personurl='';
var  organizationPID='';
var  basePath='<%=basePath%>';
var  companyName='';
var  search= '${search}';
var  pNumber='${pageNumber}';
var cor ='${corString}';
$(function(){
$(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector
	//保存或修改后,修正机构树
	var oid = '${organization.organizationID}';
//	var poid = '${organization.organizationPID}';
	var oname = '${organization.organizationName}';
	
		if(window.parent.tree.getItemText(oid) != '0'){
			window.parent.tree.setItemText(oid,oname,0);
		}else if(oid != "" ){
			window.parent.tree.insertNewChild(parent.treeid,oid,oname,0,0,0,0);
		}
	
	//保存或修改后,修正机构树
    $('.flexme11').flexigrid({
		height: 100,
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
        }
       
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
                    alert('不能禁用单位');
                    return;
                }
                if (confirm("禁用节点会丢失其下的数据？是否禁用？")) {
                	window.parent.tree.deleteItem(treeid);
					$("#organizationform").attr("action",basePath + "ea/corganization/t_ea_delOrganization.jspa?pageNumber=${pageNumber}&organizationID=" +
                    treeid +
                    "&selectedtreeID=" +
                    parent.treeid);
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
				$("#sortchildren").attr("action",basePath + "ea/corganization/ea_toSortChildOrganization.jspa?pageNumber=${pageNumber}");
				document.sortchildren.submit.click();
				break;
			case '导出':
			     if ("" == parent.tree.getSelectedItemId()) {
                    alert("请选择机构");
                    return;
                }
				treeid= parent.treeid;
   			    url=basePath + "ea/corganization/ea_showExcel.jspa?organizationID="+treeid;
				open(url);
				break;
			 case '设置每页显示条数':
			var url=basePath + "ea/corganization/ea_getOrganizationListAll.jspa?organizationID=${organizationID}";
				numback(url);
				break; 	
        }
    }
    
    if(cor.length >0){
    	if(cor == "88"){
    		alert("创建机构已修改！");
    	}else if(cor == "55"){
    		alert("创建机构成功！");
    	}else if(cor == "66"){
    		alert("机构重启成功！");
    	}else if(cor == "22"){
    		alert("机构下有子机构，不允许禁用！");
    	}else if(cor == "77"){
    		alert("机构禁用成功！");
    		window.parent.tree.deleteItem("<%=request.getParameter("organizationID")%>");
    	}
    	
    }
    
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
                         alert("请选择");
                         return;
                    }
					//弹出框的页面存在于span中才取得到
                    var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm2).text();
                    var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm3).text();
                    //alert(value1+"--"+parm1+"--" +myfrom )
	                if(parm1 != "")
	                   window.frames["mainframe"].$("#"+myfrom).find("#"+parm1).attr("value",value1);
                    if(parm2 != "")
                    	window.frames["mainframe"].$("#"+myfrom).find("#"+parm2).attr("value",value2);
                    if(parm3 != "")
                    	window.frames["mainframe"].$("#"+myfrom).find("#"+parm3).attr("value",value3);
                    $("#ifr").attr("src","");
                  $("#jqmWindow2").jqmHide();
              });
    $("#org").text(parent.treename);
    $(".tt").click(function(){
        $(".tt").css("background", "#FFFFFF");
        $(this).css("background", "#eaf1f7");
        
    });
	
	$(".flexme11").find("select").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
                    $o.insertAfter($s);
                });
           $(".flexme11 tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    organizationPID =this.id;
                    if(personurl){    
                    $("#mainframe").attr("src",personurl + organizationPID);
                    }       
                    companyName = $(this).find("span#organizationName").text();  
                });	
});
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
		 $("#organizationform").attr("action", basePath + "ea/corganization/t_ea_delOrganization.jspa?pageNumber=${pageNumber}&organizationID="
				+ treeid+"&selectedtreeID="+parent.treeid);
	document.organizationform.submit.click();
	}
}
function alee(organizationID) {
	open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
			 + parent.treeid + "&porganization.organizationName=" + parent.treename + "&organization.organizationID="
			 + organizationID);
}

//子窗口调用
function refresh(){
	window.location.reload();
}
</script>
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
			$("#mainframe").css({"height": _height / 2 - 10 + "px"});
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