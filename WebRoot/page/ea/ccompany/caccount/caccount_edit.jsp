<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="hy.ea.bo.Company"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/autoComplete/autoComplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>	
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>		

<title>子公司帐号管理</title>
</head>
<body>
<!--JS遮罩层-->   
<div id="fullbg" style="filter:Alpha(Opacity=100);background-color: #ECFFFF;"></div>   
<!--endJS遮罩层-->   
<!--对话框-->   
<div id="dialog" >   
<div id="dialog_content" >
 <br/><div align='center'><img  src="<%=basePath%>images/jdt.gif"/><div style="margin-top: 10px;">正在加载中...</div></div> </div>   
</div>   
<form  name="caccountEdit" method="post" id="caccountEdit">
<input type="submit" name="submit" style="display:none"/>
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">添加/修改</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
            <img src="<%=basePath%>images/ea/main/list_add.gif" width="8" height="8" /> <a href="<%=basePath%>ea/ccaccount/ea_getListCAccount.jspa?pageNumber=${pageNumber}&methodX=${methodX}&roleIDX=${roleIDX}&search=${search}&title=${title}&compid=${compid}" class="link02">返回caccount列表</a></td>
  		</tr>
</table>

		<table width="98%" height="217" align="center" style="border:#D1DDE9 1px solid;">
			<tr height="22">
              <td width="35%" height="33" align="right" class="txt02">账号所属公司：</td>
    		  <td width="40%">
    		  <select id="companyID" name="caccount.companyID"  ></select>
    		  <input name="caccount.companyID" type="hidden"  id="textfieldCompanyID" value="${caccount.companyID}" disabled="disabled"/>
			  <span style="color:#FF0000;">* ${fn:substring(caccount.companyID,0,7)=="company"?"(不可编辑)":""}</span>
    		  </td>
    		  <td></td>
		  </tr>
			<tr height="22">
              <td width="35%" height="33" align="right" class="txt02">账号责任人名称：</td>
    		  <td width="40%">
    		     <input name="caccount.accountName"  type="text" class="kuang put3" maxlength="50" id="textfield" size="30" value="${caccount.accountName}"/>
    		     <span style="color:#FF0000;">*</span>
    		  </td>
    		  <td></td>
		  </tr>
           <tr align="center" bgcolor="#FFFFFF" height="22" >
             <td height="33" align="right"><span class="txt02">登录账号：</span></td>
             <td align="left">
	             <input name="caccount.accountEmail"  type="text" class="kuang  notnull2 caccountEmail" maxlength="50" size="30"  id="caccountEmail" value="${caccount.accountEmail}"/>
	             <span style="color:#FF0000;">*</span>
             </td>
             <td align="left">&nbsp;</td>
           </tr> 
             <tr align="center" bgcolor="#FFFFFF" height="22" >
             <td height="33" align="right"><span class="txt02">账号密码：</span></td>
             <td align="left">
	             <input name="caccount.accountPassword"  type="password" class="kuang put3 enddate" maxlength="20"  size="30" value="${caccount.accountPassword}"/>
	             <span style="color:#FF0000;">*</span>
             </td>
             <td align="left">&nbsp;</td>
           </tr> 
           
           <tr align="center" bgcolor="#FFFFFF" height="22" >
              <td height="33" align="right"><span class="txt02">选择角色：</span></td>
              <td align="left">
	               <%--<s:select list="%{#request.roleList}"  listKey="roleID"  listValue="roleName"   name="caccount.roleID" theme="simple" id="caccountRoleID">
                   </s:select>
              --%>
              <select id="roleIDD" name="caccount.roleID" >
                           	<option value="">请选择公司</option>
                           </select>
              </td>
              <td align="left">&nbsp;</td>
           </tr> 
            <tr height="22" >
              <td height="33" align="right"><span class="txt02">账号状态：</span></td>
              <td>
                  <s:select list="#{'00':'正常','02':'停用'}"  name="caccount.accountStatus" theme="simple" >
                  </s:select>
              </td>
              <td>&nbsp;</td>
           </tr>
           <tr align="center" bgcolor="#FFFFFF" height="22">
						<td height="33" align="right">
							<span class="txt02">绑定员工：</span>
						</td>
						<td align="left">
							<input id="key" type="text" class="kuang put3"
								src="<%=basePath%>ea/ccaccount/sajax_ea_getSearchStaff.jspa"
								value="${caccount.staffName}" maxlength="50" />
							<%--<div id="autoKey"></div>--%>
							<input name="caccount.staffID" id="KeyID" type="hidden"
								value="${caccount.staffID}" />
							<%--
	              <span style="color:#FF0000;" onclick="reset()"><a href="#">取消绑定</a></span><span style="color:#FF0000;">*</span>
	              --%><span style="color: #FF0000;" onclick="getstafflist(1)" ><a
								href="#">查询员工</a><span style="color: #FF0000;">*</span>
							</span>
							<span style="color: #FF0000;" onclick="yijiao('show')" id="qxyj"><a
								href="#">权限移交</a><span style="color: #FF0000;">*</span>
							</span>
							
						</td>
						<td align="left">
							&nbsp;
						</td>
					</tr>
					<tr id="quxiao" style="display: none;">
						<td height="33" align="right">
							<span class="txt02">移交员工：</span>
						</td>
						<td align="left">
							<input id="afterKey" type="text" 
								src="<%=basePath%>ea/ccaccount/sajax_ea_getSearchStaff.jspa"
								maxlength="50" />
							<input name="caccount.afterStaffID" id="afterKeyID" type="hidden"
								 />
							<span style="color: #FF0000;" onclick="getstafflist(0)" id="qxyj"><a
								href="#">查询员工</a><span style="color: #FF0000;">*</span>
							</span>
							<span style="color: #FF0000;" onclick="yijiao('hide')"><a
								href="#">取消移交</a><span style="color: #FF0000;">*</span>
							</span>
						</td>
					</tr>
           <tr align="center" bgcolor="#FFFFFF" height="22" >
             <td height="33" align="right">&nbsp;</td>
             <td height="33" align="center">
                 <div class="submit" onclick="edit('${caccount.accountID}')"><a>保存</a></div><s:token/>
                 <input  name="caccount.accountKey"  type="hidden"  size="30"   value="${caccount.accountKey}"/>
		    	<input  name="caccount.accountOnLine"  type="hidden"  size="30"   value="${caccount.accountOnLine}" />
             </td>
             <td align="left">&nbsp;</td>
           </tr> 
		</table>
</div>
<div id="body_02" style="overflow:auto;margin-top: 2px;display: block;height: 150px;width: 100%">
</div>
</form>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var accountEmail = "${caccount.accountEmail}";
var token = 0;
var keyvalue;
var seled;
var methodX='${methodX}';
var roleIDX='${roleIDX}'; 
var companyID='${companyID}'; //总公司ID
var compid='${caccount.companyID}';//选中参数公司id
var roleID='${caccount.roleID}';
function edit(accountID){
        $("form :input:.put3").trigger("blur");
          if($("form .error").length)
          { 
            return;
          }     
          if($("#KeyID").attr("value")== ""){
              $("#KeyID").attr("value","company");
          }
         if($("#key").val()!= "")
          if($("#KeyID").attr("value")== "company"){
                  alert("该员工不存在！请从下面选择员工中！ 点击你要选择的员工姓名");
                 return;
          }
            if($("tr#quxiao").is(":visible")){
          	if($("input#afterKeyID").val()==""){
          		alert("移交员工不能为空!");
          		return;
          	}
          	if($.trim($("input#afterKeyID").val())==$.trim($("#KeyID").attr("value"))){
          		alert("绑定员工与移交员工不能为同一人!");
          		return;
          	}
          }
       $("#caccountEdit").attr("action","<%=basePath%>ea/ccaccount/t_ea_saveCAccount.jspa?pageNumber=${pageNumber}&caccount.accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX+"&search=${search}&title=${title}&compid=${compid}");
       document.caccountEdit.submit.click();
       alert("保存成功！");        
}
$("#key")
function getstafflist(arg){
		if(arg==1&&''!='${caccount.accountID}'&&''!='${caccount.staffName}'){
			alert("如需改变帐号使用权,请点击“权限移交”");
			return  false;
		}
    	seled=arg;
     	if(token){return;}
        token = 1;
        showBg('dialog','dialog_content');
        var searchurl=$("#key").attr("src");
        searchurl = searchurl + "?parameter="+encodeURI(seled==1?$("#key").val():$("#afterKey").val())+"&compid="+$("#companyID").find("option:selected").attr("value")+"&date="+new Date().toLocaleString();
        window.status = "正在加载数据";
         $.ajax({
                        url: searchurl,
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function cbf(data){
                           var member = eval("(" + data + ")");
                            var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
		                  var stafflist = member.stafflist;
		                  var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择员工</td></tr></table>";
		                  tabletr+="<table width='98%' height='83' align='center' cellpadding='1' cellspacing='0' class='table' id='stafftable' style='margin-bottom:5px; margin-top:5px;'>";
		                  tabletr+=" <tr><th height='21' align='center' bgcolor='#E4F1FA'>姓名</th><th align='center' bgcolor='#E4F1FA'>身份证</th></tr>";
		                  for(var i=0;i<stafflist.length;i++){
		                  tabletr+=" <tr onclick='checkstaff(\""+stafflist[i].staffID+"\",\""+stafflist[i].staffName+"\")'>";
		                  tabletr+="<td align='center'>"+stafflist[i].staffName+"</td>";
		                  tabletr+="<td align='center'>"+stafflist[i].staffIdentityCard+"</td>";
		                  tabletr+=" </tr>";
		                  }
		                  tabletr+=" </table>";
		                  $("#body_02").html(tabletr);
		                  window.status = "数据加载成功";
		                  $("#body_02").show();
		                  //if($.trim(seled==1?$("#key").val():$("#afterKey").val())==""){
	                	  closeBg();
	                  	  //};
		                  token = 0;
          
                        },
                        error: function cbf(data){
                           token = 0;
                           alert("数据获取失败！");
                        }
                    });
}
function yijiao(arg){
	if(arg=="show"){
		$("#quxiao").show();
	}else{
		$("#quxiao").hide();
		$("#afterKeyID").attr("value","");
		$("#afterKey").attr("value","");
	}
}
function checkstaff(staffID,staffName){
	if(seled){
  $("#KeyID").attr("value",staffID);
  $("#key").attr("value",staffName);
  }else{
  $("#afterKeyID").attr("value",staffID);
  $("#afterKey").attr("value",staffName);
  }
  $("#body_02").hide();
}
function reset(){
 $("#KeyID").attr("value","");
 $("#key").attr("value","");
}
$(document).ready(function(){
	if(''=='${caccount.accountID}'||''=='${caccount.staffName}'){
		$("span#qxyj").hide();
		}else{
		$("input#key").attr("readonly","readonly");
		}
		$("select#caccountRoleID").find("option[value='"+roleIDX+"']").attr("selected","selected");
	});
$(function() {
	var url = basePath
			+ "ea/company/sajax_n_ea_getCompanyList.jspa?date01="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var data1 = new Array();
					data1[0] = {
						id : "<%=c.getCompanyID()%>",
						pid : '-1',
						text : "<%=c.getCompanyName()%>"
					};
					for (var i = 0; i < companylist.length; i++) {
						data1[i + 1] = {
							id : companylist[i].companyID,
							pid : companylist[i].companyPID,
							text : companylist[i].companyName
						};
						
					}
					var ts3 = new TreeSelector($("#companyID")[0],
							data1, -1);
					ts3.createTree();
				},

				error : function cbf(data) {
					alert("机构数据获取失败！");
				}
			});
		$("#companyID").find("option[value='"+compid+"']").attr("selected","selected");
		changeButton($("#companyID").find("option:selected").attr("value"));
		$("#companyID").change(function(){
			$("#body_02").html("");
	    	changeButton(this.value);
	    });
});			
function changeButton(id){
	
	var companyID1=$("#textfieldCompanyID").attr("value");
	if(companyID1.substr(0,7)=="company"){
		$("#companyID").attr("disabled","disabled");
		$("#textfieldCompanyID").attr("disabled","");
	}
	
	 var url = basePath+"ea/ccaccount/sajax_ea_toseachto.jspa?compid="+id+"&date="+new Date(); 
				$.ajax({
				    url:encodeURI(url),
				    type: "get",
					async: false,
					dataType: "json",
					success: function cbf(data){
						var member = eval("(" + data + ")");
		                var oList = member.roleList;
		                var option_ = "";
						 for (var i = 0; i < oList.length; i++) {        
						 option_ += "<option value='"+oList[i].roleID+"''>"+oList[i].roleName+"</option>";
							 }
						$("#roleIDD").html(option_);
					},
					error: function cbf(data){
					alert("数据获取失败！");
					}
			}); 
	 $("#roleIDD").find("option[value='"+roleID+"']").attr("selected","selected");			
};
</script>
</body>
</html>