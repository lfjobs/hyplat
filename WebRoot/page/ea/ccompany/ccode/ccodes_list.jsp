<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代码管理</title>
<link href="<%=basePath%>css/plat/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}
-->
</style>
</head>
<body >
<form method="post" name="sortchildren" id="sortchildren"><%-- 不告诉你--%><input type="submit" name="submit" style="display:none"/>
			<input id="oID" name="codeID" type="hidden"/>
	</form>
<form name="codeForm" method="post" id="codeForm"><input type="submit" name="submit" style="display:none"/>
        <input name="sub" value="${session_value}" type="hidden"/><!-- 代替token-->
<div>
  <table class="flexme11" width="600">
        				<thead>
							    	   	<tr>
							             	<th width="90" align="center">
												代码序号
											</th>
											<th width="330" align="center">
												代码名称
											</th>
											<th width="302" align="center">
												代码描述
											</th>
											<th width="80" align="center">
												状态
											</th>
											<th width="114" align="center">
												操作
											</th>
							            </tr>
						</thead>
								 <tbody>
							         	<s:iterator value="pageForm.list">
							    		<tr ondblclick="alee('<s:property value="codeID"/>')" >
							            	<td><s:property value="codeNumber" /></td>
										<td>
											<s:property value="codeValue" />
										</td>
										<td >
											<s:property value="codeDesc" />
										</td>
										<td>
										    <s:if test="codeStatus == 00">不可停用</s:if>
										    <s:if test="codeStatus == 01">可停用</s:if>
										    <s:if test="codeStatus == 98">已停用</s:if>
										</td>
										<td>
										   <a href="#" onclick="toedit('<s:property value="codeID"/>')"><img src="<%=basePath%>images/ea/main/edit.gif" width="16" height="16"  title="修改" border="0"/></a>
	                                       <a href="#" onclick="deleteid('<s:property value="codeID"/>','<s:property value="codeStatus"/>')"><img src="<%=basePath%>images/ea/main/gtk-del.png" width="16" height="16" title="删除" border="0"/></a>
										</td>
							            </tr>
							            </s:iterator>
							            </tbody>
							</table>
								<c:import url="../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/cccode/ea_getCodeListAll.jspa?pageNumber=${pageNumber}&codeID=${codeID}"></c:param>
						</c:import>
						<s:token />
			</div>
		</form>
<form name="editcodeForm" id="editcodeForm" method="post">		<input type="submit" name="submit" style="display:none"/>
	<div class="jqmWindow jqmWindowcss3"  style="width: 500px;top:10%;" id="jqModel" >
	<div class="drag">代码设置:
   	       <div class="close"></div>
	    </div>
	    <input style="display: none;"  type="text" class="input01" id="parmiter" size="14" />
		<input style="display: none;" name="code.codePID" value="${code.codePID}" type="text" class="input01" id="codePID" size="14"/>
		<input style="display: none; " name="code.codeID" value="${code.codeID}" type="text" class="input01" id="codeID" size="14"/>
		<input style="display: none;" name="code.codeKey" value="${code.codeKey}" type="text" class="input01" id="codeKey" size="14"/>
		<input style="display: none;" name="code.codeStatus" value="${code.codeStatus}" type="text" class="input01" id="codeStatus" size="14"/>
	     <div class="unitlib_list_right02" >
				  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"  >				        
				      <tr>
				      <td width="10%" height="44" align="right" class="td_bg">				      
				                 序号：
				      </td>
					  <td width="40%" class="td_bg"><input name="code.codeNumber" class="input01 " type="text" maxlength="3" size="20" id="codeNumber"/>
				      </td>
				      </tr>
				      <tr>
				        <td height="45" align="right" class="td_bg">代码名称：</td>
				        <td height="45" class="td_bg"><input name="code.codeValue" maxlength="50"  type="text" class="input01 " id="codeValue" size="20"/></td>
				      </tr>
				      <tr>
				        <td height="45" align="right" class="td_bg">代码描述：</td>
				        <td height="45" class="td_bg"><textarea style="height: 80px;"  class="input01"  id="desc"  name="code.codeDesc"></textarea></td>
				      </tr>
				      <tr>
				        <td height="39" align="center" class="td_bg">&nbsp;</td>
				        <td height="39" colspan="3" align="center" class="td_bg">&nbsp;</td>
				      </tr>
				  
				</table><s:token/>
				</div>
	     <div align="center">  
	              <input type="button"  id="save"  class="input-button JQuerySubmit" value=" 保存 " /><input  type="button" onclick="re()"  class="input-button JQuerySubmit" value="取消" />
	    </div> 
	</div>
</form>
	<script type="text/javascript">
   var basePath="<%=basePath%>";
   var treeID = "${code.codeID}";
   var treePID = "${code.codePID}";
   var codeValue = "${code.codeValue}";
	if(treeID != ""){
	  if(window.parent.tree.getItemText(treeID) != '0'){
		   window.parent.tree.setItemText(treeID,codeValue);
		  }
	  else if(treeID ){
			window.parent.tree.insertNewChild(treePID,treeID,codeValue,0,0,0,0);
		}
    }
$(function(){ 
       $("#jqModel").jqm({
	        modal: true,// 限制输入（鼠标点击，按键）的对话  
	        overlay: 20 // 遮罩程度%  
	    }).jqmAddClose('.close')// 添加触发关闭的selector  
				.jqDrag('.drag');
	 $("#save").click(function(){
	 	if($("#codeValue").attr("value")==""){
	 		alert("代码名称不能为空");
	 		return false;
	 	}
	       parmiter = $("#parmiter").attr("value");
	       $('#editcodeForm').attr("action",basePath + "ea/cccode/t_ea_saveCCode.jspa?pageNumber=${pageNumber}&parmiter="+parmiter);
		   document.editcodeForm.submit.click();
	    });
    $('.flexme11').flexigrid({
		height: 300,
		width: 'auto',
		minwidth: 30,
		title: '当前代码--' +parent.tree.getSelectedItemText(),
		minheight: 80 ,
		  buttons: [ 
		 {
            name: '添加下级',
            bclass: 'add',
			onpress : action //当点击调用方法
        }, {
            // 设置分割线  
            separator: true
        }, {
            name: '删除当前代码',
            bclass: 'delete', 
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改当前代码',
            bclass: 'edit',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '排序下属代码',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '升级所有代码',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
	});
    function action(com, grid){
        switch (com) {
            case '添加下级':
            if ("" == parent.treeid) {
                    alert("请选择代码");
                    return;
                }
                
                if(window.parent.tree.getLevel(parent.treeid) == 3){
                alert("不能添加下一级");
                return;
                }
                    $("#parmiter").attr("value", "");	
                    $("#codePID").attr("value", "");
                    $("#codeID").attr("value", "");
                    $("#codeKey").attr("value", "");
                    $("#codeStatus").attr("value", "");
                    $("#codeNumber").attr("value", "");
                    $("#codeValue").attr("value", "");
                    $("#desc").attr("value", "");
                $("#codePID").attr("value", parent.treeid);
                 $("#jqModel").jqmShow();
                break;
           	 case '删除当前代码':
                 var  treeid= parent.treeid;
                 if ("001"== treeid) {
                    alert("不能删除根节点");
                    return;
                }
                codeStatus =  parent.tree.getUserData(treeid,"Status");
                if(codeStatus == '00'){
		            alert("不可删除");
		            return;
		         }
                 if (confirm("是否继续?")) {
                 window.parent.tree.deleteItem(treeid);
                 parent.treeid = "";
                 $('#codeForm').attr("action",basePath + "ea/cccode/t_ea_delCCode.jspa?pageNumber=${pageNumber}&codeID="+treeid);
			      document.codeForm.submit.click();
                 }
               	 break;
            case '修改当前代码':
                codeID = parent.treeid;
                if ("" == codeID) {
                    alert("请选择代码");
                    return;
                }
                if ("001"== codeID) {
                    alert("不能修改根节点");
                    return;
                }
                var url = basePath + "ea/cccode/sajax_ea_editCCode.jspa?codeID="+encodeURI(codeID)+"&date="+new Date().toLocaleString();
				 $.ajax({
                        url: url,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
					var member = eval("(" + data + ")");
					 var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href =basePath + "page/ea/not_login.jsp";
			                  }
					var code = member.code;
					$("#parmiter").attr("value", code.codeID);
					$("#codePID").attr("value", code.codePID);
                    $("#codeID").attr("value", code.codeID);
                    $("#codeKey").attr("value", code.codeKey);
                    $("#codeStatus").attr("value", code.codeStatus);
                    $("#codeNumber").attr("value", code.codeNumber);
                    $("#codeValue").attr("value", code.codeValue);
                    $("#desc").attr("value", code.codeDesc);
                     $("#jqModel").jqmShow();
				},
				error: function cbf(data){
                           alert("数据获取失败！");
                        }
                    });
                
                break;
			case '排序下属代码':
				$("#oID").val(parent.treeid);
				$("#oName").val(parent.treename);
				 $('#sortchildren').attr("action",basePath + "ea/cccode/ea_toSortChildCode.jspa?pageNumber=${pageNumber}");
			      document.sortchildren.submit.click();
				break;
		    case '升级所有代码':
				if (confirm("是否继续?")) {
				    window.parent.tree.deleteChildItems("001");
				     window.parent.tree.deleteChildItems("002");
				     $('#codeForm').attr("action",basePath + "ea/cccode/ea_resetCode.jspa?pageNumber=${pageNumber}");
			         document.codeForm.submit.click();
				}
				break;
				
			 case '设置每页显示条数':
			var url= basePath + "ea/cccode/ea_getCodeListAll.jspa?codeID=${codeID}";
				numback(url);
				break;
			}
		}
	});

	function toedit(codeID) {
		if ("" == codeID) {
			alert("请选择代码");
			return;
		}
		if ("001" == codeID) {
			alert("不能修改根节点");
			return;
		}
		var url = basePath + "ea/cccode/sajax_ea_editCCode.jspa?codeID="
				+ encodeURI(codeID) + "&date=" + new Date().toLocaleString();
		$
				.ajax({
					url : url,
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath
									+ "page/ea/not_login.jsp";
						}
						var code = member.code;
						$("#codePID").attr("value", code.codePID);
						$("#codeID").attr("value", code.codeID);
						$("#codeKey").attr("value", code.codeKey);
						$("#codeStatus").attr("value", code.codeStatus);
						$("#codeNumber").attr("value", code.codeNumber);
						$("#codeValue").attr("value", code.codeValue);
						$("#desc").attr("value", code.codeDesc);
						$("#jqModel").jqmShow();
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
	}

	function deleteid(codeID, codeStatus) {
		if (codeStatus == '00') {
			alert("不可删除");
			return;
		}
		if (confirm("是否继续?")) {
			window.parent.tree.deleteItem(codeID);
			$('#codeForm')
					.attr(
							"action",
							basePath
									+ "ea/cccode/t_ea_delCCode.jspa?pageNumber=${pageNumber}&codeID="
									+ codeID + "&parmiter=" + parent.treeid);
			document.codeForm.submit.click();
		}
	}

	function re() {
		$("#parmiter").attr("value", "");
		$("#codePID").attr("value", "");
		$("#codeID").attr("value", "");
		$("#codeKey").attr("value", "");
		$("#codeStatus").attr("value", "");
		$("#codeNumber").attr("value", "");
		$("#codeValue").attr("value", "");
		$("#desc").attr("value", "");
		$("#jqModel").jqmHide();
	}
	function alee(codeID) {
		toedit(codeID);
	}
</script>
</body>
</html>	    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							    
							   
							   
<!--  <tr>
        <td width="5%" height="44" align="right" class="td_bg">所属上级：</td>
        <td width="40%" class="td_bg"><input disabled="disabled"   type="TEXT" class="input01" id="codePName" value="" size="14"/></td>
      </tr>
      <tr>
      <td width="5%" height="44" align="right" class="td_bg">代码序号：</td>
	  <td width="40%" class="td_bg"><input name="code.codeNumber" class="input01 " type="TEXT" maxlength="3" size="14" id="codeNumber"/>
      <tr>
        <td height="45" align="right" class="td_bg">代码名称：</td>
        <td height="45" class="td_bg"><input name="code.codeValue" maxlength="50"  type="TEXT" class="input01" id="codeValue" size="14"/></td>
      </tr>
      <tr>
        <td height="45" align="right" class="td_bg">代码描述：</td>
        <td height="45" class="td_bg"><textarea style="height: 80px;"  class="input01"  id="desc"  name="code.codeDesc"></textarea>
      </tr>
      <tr>
        <td height="47" colspan="2" align="center" class="td_bg">
        </td>
        <td>  <input type="button"  id="save"  class="ACT_btn" value=" 保存 " /><input  type="button" id="deleteid"  class="ACT_btn" value="删除" /></td>
      </tr>
      <tr>
        <td height="39" align="center" class="td_bg">&nbsp;</td>
        <td height="39" colspan="3" align="center" class="td_bg">&nbsp;</td>
      </tr>
      -->
  
