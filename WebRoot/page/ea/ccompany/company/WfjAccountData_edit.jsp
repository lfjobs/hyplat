<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="hy.ea.bo.Company" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	Company com=(Company)session.getAttribute("currentcompany");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/main.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/css/table.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/js/autoComplete/autoComplete.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />

<title>编辑公司微分金帐号</title>

<script type="text/javascript">
var basePath = "<%=basePath%>/";
var token = 0;

var keyvalue=null;
var type="${type}";
var seled=null;
var methodX='${methodX}';
var roleIDX='${roleIDX}';
var  c=0;
var  d=1;
var  q=0;
var times=59;
function edit(accountID){
        $("form :input:.put3").trigger("blur");
          if($("form .error").length)
          { 
            return;
          }     
          if($("#KeyID").attr("value")== ""){
              $("#KeyID").attr("value","company")
          }
         if($("#key").val()!= "")
          if($("#KeyID").attr("value")== "company"){
                 alert("该员工不存在！请从下面选择员工中！ 点击你要选择的员工姓名")
                 return;
          }
          if($("tr#quxiao").is(":visible")){
          if($("input#afterKeyID").val()==""&&$("input#afterKey").val()!=""){
          		alert("请点击下面列表中存在人员，已进行确定选中!");
          		return;
          	}
          	if($("input#afterKeyID").val()==""||$("input#afterKey").val()==""){
          		alert("移交员工不能为空!");
          		return;
          	}
          	if($.trim($("input#afterKeyID").val())==$.trim($("#KeyID").attr("value"))){
          		alert("绑定员工与移交员工不能为同一人!");
          		return;
          	}
          }
       $("#caccountEdit").attr("action","<%=basePath%>/ea/caccount/t_ea_saveCAccount.jspa?pageNumber=${pageNumber}&caccount.accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX);
       document.caccountEdit.submit.click();
       alert("保存成功！");        
}
function getstafflist(arg){
		if(arg==1&&''!='${caccount.accountID}'&&''!='${caccount.staffName}'){
			alert("如需改变帐号使用权,请点击“权限移交”");
			return  false;
		}
		$("input#afterKeyID").attr("value","");
		$("input#afterKey").attr("style","background-color: red;");	
		seled=arg;
     	if(token)return;
        token = 1;
        
        showBg('dialog','dialog_content');
        
        var searchurl=$("#key").attr("src");
        searchurl = searchurl + "?parameter="+encodeURI(seled==1?$("#key").val():$("#afterKey").val())+"&date="+new Date().toLocaleString();
        //alert(encodeURI(seled==1?$("#key").val():$("#afterKey").val()));
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
			                  document.location.href ="<%=basePath%>/page/ea/not_login.jsp";
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
		                  //if($.trim(seled==1?$("#key").val():$("#afterKey").val())==""){
		                	  closeBg();
		                  //};
		                  window.status = "数据加载成功";
		                  $("#body_02").show();
		                  token = 0;
          
                        },
                        error: function cbf(data){
                           token = 0;
                           alert("数据获取失败！")
                        }
                    });
}
function yijiao(arg){
	if(arg=="show"){
		$("#quxiao").show()
	}else{
		$("#quxiao").hide()
		$("#afterKeyID").attr("value","")
		$("#afterKey").attr("value","")
	}
}
function checkstaff(staffID,staffName){
	if(seled){
  $("#KeyID").attr("value",staffID)
  $("#key").attr("value",staffName)
  }else{
  $("#afterKeyID").attr("value",staffID)
  $("#afterKey").attr("value",staffName)
  $("input#afterKey").attr("style","background-color:white;")
  }
  $("#body_02").hide();
}
function reset(){
 $("#KeyID").attr("value","")
 $("#key").attr("value","")
}
	$(document).ready(function(){
		if(''=='${caccount.accountID}'||''=='${caccount.staffName}'){
		$("span#qxyj").hide();
		}else{
		$("input#key").attr("readonly","readonly")
		}
		$("select#caccountRoleID").find("option[value='"+roleIDX+"']").attr("selected","selected")
	})
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">

	<!--JS遮罩层-->
	<div id="fullbg"
		style="filter:Alpha(Opacity=100);background-color: #ECFFFF;"></div>
	<!--endJS遮罩层-->
	<!--对话框-->
	<div id="dialog">
		<div id="dialog_content">
			<br />
			<div align='center'>
				<img src="<%=basePath%>images/jdt.gif" />
				<div style="margin-top: 10px;">正在加载中...</div>
			</div>
		</div>
	</div>

	<form name="caccountEdit" method="post" id="caccountEdit">
		<input type="submit" name="submit" style="display: none;" />
		<div class="main">
			<table width="98%" border="0" align="center" cellpadding="2"
				cellspacing="1" bgcolor="#D1DDE9" style="margin-top: 8px">
				<tr bgcolor="#E7E7E7">
					<td height="24" width="20%" align="left" bgcolor="#d8e6f4">
						&nbsp; &nbsp;</td>
					<td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
					<td width="20%" height="24" align="right" bgcolor="#d8e6f4"></td>
				</tr>
			</table>

			<table width="98%" height="217" align="center"
				style="border: #D1DDE9 1px solid;">
				<tr align="center" bgcolor="#FFFFFF" height="22">
					<td height="33" align="right"><span class="txt02">微分金登录账号：</span>
					</td>
					<td align="left"><input name="cusCom.account" 
						type="text" class="kuang lb  notnull2 " maxlength="50" id="wfjzh"
						size="30" value="${cusCom.account}" onblur="isshouji()"/> <span
						style="color: #FF0000;">*</span></td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FFFFFF" height="22" class="lb">
					<td height="33" align="right" ><input type="text" size="30"
								id="yzm" onblur="yanz()" class="kuang" maxlength="6" /></td>
					<td align="left"><div onclick="duanxin()" id="hq"
					 style="font-size:12px;height:20px;width:90px;line-height:20px;margin-top:3px;
					  border-radius:5px;margin-right: 70px">获取验证码</div></td>
				</tr>
				<tr align="center" bgcolor="#FFFFFF" height="22" class="lb" >
					<td height="33" align="right"><span class="txt02">微分金账号密码：</span>
					</td>
					<td align="left"><input name="caccount.accountPassword"
						type="password" class="kuang put3 notnull2" maxlength="20"
						size="30" value="" /> <span style="color: #FF0000;">*</span></td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr height="22">
					<td height="33" align="right"><span class="txt02">绑定公司：</span>
					</td>
					<td><span></span></td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FFFFFF" height="22">
					<td height="33" align="right"><span class="txt02">绑定员工：</span>
					</td>
					<td align="left"><input id="key" type="text"
						class="kuang put3 lb"
						src="<%=basePath%>/ea/wfjhandle/sajax_ea_getSearchStaff.jspa"
						value="${staff.staffName}" maxlength="50" />
						<input name="caccount.staffID" id="KeyID" type="hidden"
						value="${staff.staffID}" /> <span style="color: #FF0000;" class="lb"
						onclick="getstafflist(1)"><a href="#">查询员工</a><span
							style="color: #FF0000;">*</span> </span></td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FFFFFF" height="22" class="lb">>
					<td height="33" align="right">&nbsp;</td>
					<td height="33" align="center">
						<div class="submit" onclick="sut()">
							<a>保存</a>
						</div> <s:token /></td>
					<td align="left">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="body_02"
			style="overflow: auto; margin-top: 2px; display: none; height: 200px; width: 100%">
		</div>
	</form>
	<script type="text/javascript">
	$(function(){
		if(type=="list"){
			$("input.lb").attr("disabled","disabled");
			$("tr.lb").attr("style","display:none;");
			$("span.lb").attr("style","display:none;");
		}
	});
    function  yanz(){
                 if(q==0){
                 	alert("请先获取验证码");
                   return;
                 }
                 if($("#yzm").val().length<6){
                 		alert("验证码不能小于六位数");
                 		return;
                 }
                
      			if($("#yzm").val()==''){
	  				alert("请填写验证码");
	  				c=1;
	  				return;
  				}
  				
      		 	if($("#yzm").val()!=i){
   			     	alert("验证码不正确");
   			     	c=1;  
   			     	return;    			     	
   			     }else{
   			       	c=0;
   			     }
      
      }
      	function sut(){
      	
      			  if(c==1){
      			     alert("请先手机验证");
      			     return;
      			  }
      			  if($("#pw_pwd").val()==''){
      			    alert("请输入密码");
      			    return;
      			  }
      			  if($("#wfjzh").val()=='请输入注册手机号'){
      				  alert("请输入注册手机号");
      				  $("#wfjzh").focus();
      				  return;
      			  }
      			  if(d==1){
      			    if(c==1){
      			        alert("请先手机验证");
      			         return;
      			    }
      			  }
      			  if($("#pw_pwd").val().length<6){
   					  	alert("密码长度不安全");
   			 	 		return;
      			  }
      			 
      			   if($("#wfjzh").val()==''){
					   alert("账号为空");
					   $("#wfjzh").focus();
					   return;
					}      			
					
					$("#form").attr("action",basePath+"/ea/wfjshop/ea_seves.jspa");
					$("#sub").click();
					  
      			}
      
     function isshouji(){
     		var count=$("#wfjzh").val();
     		if(count!=''){
     			$.ajax({
  		    	  cache : true, 
  		    	  type :"POST",
  		    	  url : basePath+"/ea/android/sajax_ea_isacounnt.jspa?pahe="+count,
  		    	  async :false,
  		    	  dataType : "json",
  		    	  success :function(data){
  		    	   	var member = eval("(" + data + ")");
  		    		if(member.result==0){
  		    	   		 d=1;
  		    		}
  		    		else{
  		    		   alert("已被注册,请更换手机号码！");
  		    		   $("#wfjzh").focus();
  		    		   $("#wfjzh").val("");
  		    		   d=2;
  		    		}	
  		    	 }	    	   
      		  		}); 
     		}else{
     			$("#wfjzh").val("请输入注册手机号");
     		}
     }
	function duanxin(){
      		var count=$("#wfjzh").val();
		
		    
		      if(times<59){
					alert("请稍后");	
					return;
				}
		      if(count==""){
		          alert("手机号为空");
		      		return;
		      }
		      var reg=/^\d{11}$/;
		  
		      if(!reg.test(count)){
		        alert("手机号码输入错误");
		        return;
		      }
		   	update(times);
		   	q=1;
		     $.ajax({
		    	  cache : true, 
		    	  type :"POST",
		    	  url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+count,
		    	 	
		    	  async :false,
		    	  dataType : "json",
		    	  success :function(data){
		    		  var member = eval("(" + data + ")");
		    				i= member.returna;		
		    	 			  // alert(i);
		    	 	  }
    		  		});
				
		
     
     }
function update(num) {
			if(num>0){
				$("#hq").text("已发送（"+num+"）");
				$("#hq").css("cursor","not-allowed");
				$("#hq").css("backgroundColor","#999");
				$("#hq").css("color","#FFF");
		        times = num;
				setTimeout(function(){
						update(num-1);
				},1000);
			}else{
				$("#hq").css("cursor","pointer");
				$("#hq").css("backgroundColor","#F74C31");
				$("#hq").css("color","#FFF");
				$("#hq").text("获取验证码");
				
				times=59;
				
			}
		}
</script>
</body>

</html>