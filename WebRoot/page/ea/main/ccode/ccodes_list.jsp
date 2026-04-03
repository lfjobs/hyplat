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
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<!-- 后台管理 代码管理  -->
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}
-->
</style>
<script type="text/javascript">
   var basePath="<%=basePath%>";
   var treeID = "${code.codeID}";
   var treePID = "${code.codePID}";
   var codeValue = "${code.codeValue}";
   var codeSn="${code.codeSn}";
   var cd = "${result }";
   var maxNum = 0;
   if(treeID != ""){
	  if(window.parent.tree.getItemText(treeID) != '0'){
	  		if(codeSn=="project"||codeSn=="human"||codeSn=="office"||codeSn=="final"||codeSn=="product"||codeSn=="marking"||codeSn=="other"){
	  			codeSn="";
	  		}
		   window.parent.tree.setItemText(treeID,codeSn+codeValue);
		  }
	  else if(treeID ){
			window.parent.tree.insertNewChild(treePID,treeID,codeSn+codeValue,0,0,0,0);
		}
    }
    
    //序号自动生成
    var numurl = basePath + "ea/ccode/sajax_ea_getCodeNum.jspa";
    $.ajax({
            url: encodeURI(numurl),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
                if(nologin){
                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
                }
                maxNum = member.maxNum;
			},
			error: function cbf(data){
                alert("数据获取失败！")
            }
    });
    
	$(function(){ 
       $("#jqModel").jqm({
	        modal: true,// 限制输入（鼠标点击，按键）的对话  
	        overlay: 20 // 遮罩程度%  
	    }).jqmAddClose('.close')// 添加触发关闭的selector  
				.jqDrag('.drag');
	 $("#save").click(function(){
	 $(".put3").trigger("blur");
          if($("form .error").length)
          { 
            return;
          }  
	       parmiter = $("#parmiter").attr("value");
	       $('#editcodeForm').attr("action","<%=basePath%>ea/ccode/t_ea_saveCCode.jspa?pageNumber=${pageNumber}&parmiter="+parmiter);
		   document.editcodeForm.submit.click();
	    })
	    if(cd!="1"){
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
            name: '代码管理',
            bclass: 'mysearch',
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
	});}else{
		$('.flexme11').flexigrid({
		height: 300,
		width: 'auto',
		minwidth: 30,
		title: '当前代码--' +parent.tree.getSelectedItemText(),
		minheight: 80 ,
		  buttons: [ 
		  {
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
            name: '代码管理',
            bclass: 'mysearch',
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
	}
    function action(com, grid){
        switch (com) {
            case '添加下级':
            	if ("" == parent.treeid) {
                    alert("请选择代码");
                    return;
                }
                var repid = window.parent.tree.getParentId(parent.treeid); //往来关系、单位往来关系、客户类别可添加多级
                /* if(window.parent.tree.getLevel(parent.treeid) == 3 
                		&& repid != 'scode20110106hfjes5ucxp0000000017' //往来关系
                		&& repid != 'scode20110224xpd2t2jvda0000000002' //单位往来关系
                		&& repid != 'scode20121015uqn3qtck280000000003'){ //客户类别
                	alert("不能添加下一级");
                	return;
                } */
                $("#parmiter").attr("value", "");	
                $("#codePID").attr("value", "");
                $("#codeID").attr("value", "");
                $("#codeKey").attr("value", "");
                $("#codeStatus").attr("value", "");
                $("#codeNumber").attr("value", maxNum);
                $("#codeValue").attr("value", "");
                $("#codeSn").attr("value", "");
                $("#desc").attr("value", "");
                $("#codePID").attr("value", parent.treeid);
                
                $("#jqModel").jqmShow();
                $("#codeValue").focus();
                break;
           	 case '删除当前代码':
                 var  treeid= parent.treeid;
                 if ("001"== treeid || "002"== treeid) {
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
                 $('#codeForm').attr("action","<%=basePath%>ea/ccode/t_ea_delCCode.jspa?pageNumber=${pageNumber}&codeID="+treeid);
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
               codeStatus =  parent.tree.getUserData(codeID,"Status");
                if(codeStatus == '00'){
		           alert("不能修改");
		           return;
		        }
                var url = basePath + "ea/ccode/sajax_ea_editCCode.jspa?codeID="+encodeURI(codeID)+"&date="+new Date().toLocaleString();
				 $.ajax({
                        url: url,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
					var member = eval("(" + data + ")");
					 var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
					var code = member.code;
					$("#parmiter").attr("value", code.codeID);
					$("#codePID").attr("value", code.codePID);
                    $("#codeID").attr("value", code.codeID);
                    $("#codeKey").attr("value", code.codeKey);
                    $("#codeStatus").attr("value", code.codeStatus);
                    $("#codeNumber").attr("value", code.codeNumber);
                    $("#codeValue").attr("value", code.codeValue);
                    $("#codeSn").attr("value", code.codeSn);
                    $("#desc").attr("value", code.codeDesc);
                     $("#jqModel").jqmShow();
				},
				error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
                
                break;
			case '排序下属代码':
				$("#oID").val(parent.treeid);
				$("#oName").val(parent.treename);
				 $('#sortchildren').attr("action","<%=basePath%>ea/ccode/ea_toSortChildCode.jspa?pageNumber=${pageNumber}");
			      document.sortchildren.submit.click();
				break;
			case '代码管理':
				var url = "";
				if(treeID == "scode20141028whpjevz6ge0000000009"){
					//人事
					url = "<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000005";
				}else if(treeID=="scode20141028whpjevz6ge0000000010"){
					//办公室
					url = "<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000006";
				}else if(treeID=="scode20141028whpjevz6ge0000000011"){
					//财务
					url = "<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000026";
				}else if(treeID=="scode20141028whpjevz6ge0000000012"){
					//生产
					url = "<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000027";
				}else if(treeID=="scode20141028whpjevz6ge0000000013"){
					//营销
					url = "<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000028";
				}
				window.parent.document.location.href = url;
				break;
		    case '升级所有代码':
				if (confirm("确认升级?")) {
				    window.parent.tree.deleteChildItems("001");
				     window.parent.tree.deleteChildItems("002");
				     $('#codeForm').attr("action","<%=basePath%>ea/ccode/ea_resetCode.jspa?pageNumber=${pageNumber}");
			      document.codeForm.submit.click();
				}
				break;
				
			 case '设置每页显示条数':
			var url="<%=basePath%>ea/ccode/ea_getCodeListAll.jspa?codeID=${codeID}";
				numback(url);
				break;
        }
    }
    
    $("#codeValue").blur(function(){
    	var codeValue = $("#codeValue").val();
    	var url = "<%=basePath%>ea/ccode/sajax_ea_isCodeValue.jspa?code.codeValue="+codeValue;
    	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(cc) {
					var member = eval("(" + cc + ")");
					var log = member.log;
					if(log == 'log'){
						$("#codeValue").after("<span class=\"error\"><a class=\"tex\">此代码名称已存在</a></span>");
						$(".iscode").find(".corect").remove();
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
		});
    })
    
    
 })
 function toedit(codeID,codeStatus){
              if ("" == codeID) {
                    alert("请选择代码");
                    return;
                }
                if ("001"== codeID) {
                    alert("不能修改根节点");
                    return;
                }
                if(codeStatus == '00'){
		           alert("不可修改");
		           return;
		        }
                var url = basePath + "ea/ccode/sajax_ea_editCCode.jspa?codeID="+encodeURI(codeID)+"&date="+new Date().toLocaleString();
				 $.ajax({
                        url: url,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
					var member = eval("(" + data + ")");
					 var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
					var code = member.code;
					$("#codePID").attr("value", code.codePID);
                    $("#codeID").attr("value", code.codeID);
                    $("#codeKey").attr("value", code.codeKey);
                    $("#codeStatus").attr("value", code.codeStatus);
                    $("#codeNumber").attr("value", code.codeNumber);
                    $("#codeValue").attr("value", code.codeValue);
                    $("#codeSn").attr("value", code.codeSn);
                    $("#desc").attr("value", code.codeDesc);
                     $("#jqModel").jqmShow();
				}, error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
 }
function deleteid(codeID,codeStatus){
          //alert(codeStatus)
         if(codeStatus == '00'){
            alert("不可删除");
            return;
         }
         if (confirm("是否继续?")) {
                 window.parent.tree.deleteItem(codeID);
                  $('#codeForm').attr("action","<%=basePath%>ea/ccode/t_ea_delCCode.jspa?pageNumber=${pageNumber}&codeID="+codeID +"&parmiter="+parent.treeid);
			      document.codeForm.submit.click();
           }
}
function re(){
                    $("#parmiter").attr("value", "");
                    $("#codePID").attr("value", "");
                    $("#codeID").attr("value", "");
                    $("#codeKey").attr("value", "");
                    $("#codeStatus").attr("value", "");
                    $("#codeNumber").attr("value", "");
                    $("#codeValue").attr("value", "");
                    $("#codeSn").attr("value", "");
                    $("#desc").attr("value", "");
                    $("#jqModel").jqmHide();
}
function alee(codeID){
       toedit(codeID);
}
</script>
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
							<th width="90" align="center">
								代码编号
							</th>
							<th width="330" align="center">
								代码名称
							</th>
							 <s:if test="codeID=='scode201007306kdf8m76me0000000001'">
     						 <th width="130" align="center">
								得分类别
							</th>
   							 </s:if>
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
											<s:property value="codeSn" />
										</td>
										<td>
											<s:property value="codeValue" />
										</td>
										<s:if test="codePID=='scode201007306kdf8m76me0000000001'">
				     					<td width="130" align="center">
												${codeStatus=='00'?'系统预设':wageStatus==null?'未定义':wageStatus=='00'?'加分项':'减分项'}
										</td>
				   						</s:if>
										<td >
											<s:property value="codeDesc" />
										</td>
										<td>
										    <s:if test="codeStatus == 00">不可停用</s:if>
										    <s:if test="codeStatus == 01">可停用</s:if>
										    <s:if test="codeStatus == 98">已停用</s:if>
										</td>
										<td>
										   <a href="#" onclick="toedit('<s:property value="codeID"/>','<s:property value="codeStatus"/>')"><img src="<%=basePath%>images/ea/main/edit.gif" width="16" height="16"  title="修改" border="0"/></a>
	                                       <a href="#" onclick="deleteid('<s:property value="codeID"/>','<s:property value="codeStatus"/>')"><img src="<%=basePath%>images/ea/main/gtk-del.png" width="16" height="16" title="删除" border="0"/></a>
										</td>
							            </tr>
							            </s:iterator>
							            </tbody>
							</table>
								<c:import url="../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/ccode/ea_getCodeListAll.jspa?pageNumber=${pageNumber}&codeID=${codeID}"></c:param>
						</c:import>
						<s:token />
			</div>
		</form>
<form name="editcodeForm" id="editcodeForm" method="post">		<input type="submit" name="submit" style="display:none"/>
	<div class="jqmWindow jqmWindowcss3"  style="width: 600px;top:10%;" id="jqModel" >
	<div class="drag">代码设置:
   	       <div class="close"></div>
	    </div>
	     <div class="unitlib_list_right02" >
				  <table width="100%"　border="0" align="center" cellpadding="0" cellspacing="0"  >
				        <input style="display: none;"  type="text" class="input01" id="parmiter" size="14"/>
				        
				        <input style="display: none; " name="code.codeID" value="${code.codeID}" type="text" class="input01" id="codeID" size="14"/>
				        <input style="display: none;" name="code.codeKey" value="${code.codeKey}" type="text" class="input01" id="codeKey" size="14"/>
				        <input style="display: none;" name="code.codeStatus" value="${code.codeStatus}" type="text" class="input01" id="codeStatus" size="14"/>
				      <tr>
				      <td width="20%" height="44" align="right" class="td_bg">序号：</td>
					  <td width="80%" class="td_bg"><input readonly="readonly" name="code.codeNumber" class="input01" type="text" maxlength="3" size="20" id="codeNumber"/>
				      <tr>
				        <td height="45" align="right" class="td_bg"><font color="#ff0000">*</font>代码编号：</td>
				        <td height="45" class="td_bg"><input name="code.codeSn" maxlength="10"  type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" class="input01  put3" id="codeSn" size="20"/></td>
				      </tr>
				      <tr>
				        <td height="45" align="right" class="td_bg"><font color="#ff0000">*</font>代码名称：</td>
				        <td height="45" class="td_bg iscode"><input name="code.codeValue" maxlength="50"  type="text" class="input01  put3 ckTextLength" id="codeValue" size="20"/></td>
				      </tr>
				      <s:if test="codeID=='scode201007306kdf8m76me0000000001'">
				      	<tr>
				      		<td width="20%" height="44" align="right" class="td_bg">得分类别：</td>
				      		<td height="45" class="td_bg">
				      		<select name="code.wageStatus"  class="put3" id="wageStatus" >
				      			<option value="00">加分项</option>
				      			<option value="01">减分项</option>
				      		</select>
				      		</td>
				      	</tr>
				      </s:if>
				      <s:if test="codeID=='scode20101101dfs3uhdprp0000000029'||code.codePID=='scode20101101dfs3uhdprp0000000029'">
				      	<tr>
				      		<td width="20%" height="44" align="right" class="td_bg">父级代码：</td>
				      		<td height="45" class="td_bg"><!-- 
				      		<select name="code.codePID" class="put3" id="codePID" >
				      			<option value="scode20101101dfs3uhdprp0000000029">全部</option>
				      			<option value="scode20130104uyj3s8t4b50000000002">现金收入类</option>
				      			<option value="scode20130104uyj3s8t4b50000000003">现金支出类</option>
				      			<option value="scode20130104uyj3s8t4b50000000004">应收账款类</option>
				      			<option value="scode20130104uyj3s8t4b50000000005">应付账款类</option>
				      			<option value="scode20130104uyj3s8t4b50000000006">银行收入类</option>
				      			<option value="scode20130104uyj3s8t4b50000000007">银行支出类</option>
				      			<option value="scode201303255bfk6jsacr0000000002">现金收入银行支出类</option>
				      			<option value="scode201303255bfk6jsacr0000000003">现金支出银行收入类</option>
				      			<option value="scode2013032523n6id3d3p0000000002">其他类</option>
				      		</select> -->
				      		<s:select list="Codebill" listKey="codeID" listValue="codeValue" name="code.codePID" class="put3" id="codePID">
				      		<option value="scode20101101dfs3uhdprp0000000029">全部</option>
				      		</s:select>
				      		</td>
				      	</tr>
				      </s:if><s:else>
				      <input style="display: none;" name="code.codePID" value="${code.codePID}" type="text" class="input01" id="codePID" size="14"/>
				      </s:else>
				      <tr>
				        <td height="45" align="right" class="td_bg"><font color="#ff0000">*</font>代码描述：</td>
				        <td height="45" class="td_bg"><textarea style="height: 80px;" maxlength="250"  class="input01 ckTextLength"  id="desc"  name="code.codeDesc"></textarea>
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
    setTimeout(function(){ 
	    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    },100);
    
    $(window).resize(function(){ 
		setTimeout(function(){ 					    
		   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
		},100);
    }); 
</script>       
	</body>
</html>	    
							    
	