<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<title>会计科目管理</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/plat/validate.css" rel="stylesheet" type="text/css" />
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
   $(function(){
	   var x = parent.tree.getLevel(parent.treeid)
	   var y = parent.subRule.rulesArray[x-1]
	   $("#subjectsNumbers").attr("maxlength", y);
	   var subjectsNumbers="";
	   var subjectsNames="";
	   var basePath="<%=basePath%>";
	   var treeid = window.parent.tree.getSelectedItemId();
	   var treename = window.parent.tree.getItemText(treeid);
	   var numbers="";
	   if(treeid!="002"){
	    	numbers= window.parent.tree.getUserData(treeid,"subjectsNumbers");
	   }
	   var oper='<%=request.getParameter("oper")%>';
	   $("#subjectsPNumbers").val(numbers).attr("readonly","readonly");
	   if(oper=="update"){
		    var subjectsID='<%=request.getParameter("subjectsID")%>';
		    var url=basePath+"/ea/csbjects/sajax_ea_editCsubejsts.jspa?subjectsID="+subjectsID+"&date="+new Date().toLocaleString();
		    
		         $.ajax({
		                        url: encodeURI(url),
		                        type: "get",
		                        async: true,
		                        dataType: "json",
		                        success: function cbf(data){
		                      
		          var member = eval("(" + data + ")");
		           var nologin = member.nologin;
		               if(nologin){
		               document.location.href ="<%=basePath%>page/ea/not_login.jsp";
		               }
		          var csbjects = member.csbjects;
				  $("#subjectsPID").attr("value", csbjects.subjectsPID);
				  subjectsNumbers=(csbjects.subjectsNumbers).replace(numbers,"");
				  subjectsNames=csbjects.subjectsName;
				  $("#subjectsID").attr("value", csbjects.subjectsID);
		          $("#subjectsKey").attr("value", csbjects.subjectsKey);
		          $("#subjectsStatus").attr("value", csbjects.subjectsStatus);
		          $("#subjectsNumbers").attr("value", subjectsNumbers);
		          $("#subjectsName").attr("value", csbjects.subjectsName);
		       },
		       error: function cbf(data){
		             alert("数据获取失败！")
		       }
		    });
   		}else{
         	$("#subjectsPID").val(treeid);
  		}
   
   function panduan(subnum){
        var b=true;
        var subjectsNum=$("#subjectsNumbers").val();
        var subjectsName=$("#subjectsName").val();
        var subjectsPID=$("#subjectsPID").val();
        if(subjectsNum.length!=y)
        {
          alert("请输入指定长度");
          return false;
        }
        if(!/^[0-9]+$/.test(subjectsNum))
        {
          alert("请输入数字！");
          return false;
        }
        if(subjectsNum.length!=0){
            if(subjectsNumbers!=subjectsNum){
              var url1="<%=basePath%>/ea/csbjects/sajax_ea_subjectsNumbersorNot.jspa?csbjects.subjectsNumbers="+subnum+"&date="+new Date().toLocaleString();
               $.ajax({
                        url: encodeURI(url1),
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function cbf(data){
			              var member = eval("(" + data + ")");
			              var nologin = member.nologin;
			              if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			              }
			              var c = member.count;
			              if(c!=0){
			                   alert("此编号已经存在！");
			                   b=false;
			               }
               			},
                       error: function cbf(data){
                           alert("数据获取失败！")
                       }
                 });
            }
          }else{
              alert("请输入编号！");
               b=false;
           }
           if(subjectsName.length!=0){
            if(subjectsNames!=subjectsName){
              var url1="<%=basePath%>/ea/csbjects/sajax_ea_ajaxCsubejstsName.jspa?csbjects.subjectsName="+subjectsName+"&csbjects.subjectsPID="+subjectsPID+"&date="+new Date().toLocaleString();
               $.ajax({
                        url: encodeURI(url1),
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function cbf(data){
			              var member = eval("(" + data + ")");
			              var nologin = member.nologin;
			              if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			              }
			              var c = member.count;
			              if(c!=0){
			                   alert("此科目名称已经存在！");
			                   b=false;
			               }
               			},
                       error: function cbf(data){
                           alert("数据获取失败！")
                       }
                 });
            }
          }else{
              alert("请输入科目名！");
               b=false;
           }
           return b;
   }
   
   
          $("#save").click(function(){
                var subnum=$("#subjectsPNumbers").val()+$("#subjectsNumbers").val();
               if(panduan(subnum)){
                 $("#subjectsNum").val(subnum);
                 $("#direction").val($("input[name='subRadio']:checked").val());
                 $("#category").val($(".category option:selected").val());
                 $("#editcsbjectsForm").attr("action", "<%=basePath%>/ea/csbjects/t_ea_saveCsubejsts.jspa?pageNumber=${pageNumber}&level="+x);
                 document.editcsbjectsForm.submit.click();
               }
          });
          
   });
</script>
	</head>
<body >    
<form name="editcsbjectsForm" id="editcsbjectsForm" method="post">
  <input type="submit" name="submit" style="display:none"/>		
	<div  style="width:100%;top:20%;right:20%; border:#7F9DB9 solid 1px;">
	<div class="drag">科目设置:
	    </div>
	     <div class="unitlib_list_right02" >
				  <table width="100%"　border="0" align="center" cellpadding="0" cellspacing="0">
				        <input style="display: none;"  type="text" class="input01" id="parmiter" size="14"/>
				        <input style="display: none;" name="csbjects.subjectsPID" value="${csbjects.subjectsPID}" type="text" class="input01" id="subjectsPID" size="14"/>
				        <input style="display: none; " name="csbjects.subjectsID" value="${csbjects.subjectsID}" type="text" class="input01" id="subjectsID" size="14"/>
				        <input style="display: none;" name="csbjects.subjectsKey" value="${csbjects.subjectsKey}" type="text" class="input01" id="subjectsKey" size="14"/>
				        <input style="display: none;" name="csbjects.subjectsStatus" value="${csbjects.subjectsStatus}" type="text" class="input01" id="subjectsStatus" size="14"/>
				      <tr>
				      <td width="39%" height="44" align="right" class="td_bg">编号：</td>
					  <td width="61%" class="td_bg"><input id="subjectsPNumbers" type="text" size="10"/>
					  + 
					     <input class="input01 " type="text"  size="14" id="subjectsNumbers"/>
					     <input name="csbjects.subjectsNumbers" class="input01 " type="hidden"  size="14" id="subjectsNum" />
					   <!-- <s:textfield name="csbjects.subjectsNumbers" class="input01 " maxlength="" size="14" id="subjectsNumbers"></s:textfield> -->
				      <tr>
				        <td height="45" align="right" class="td_bg">科目名称：</td>
				        <td height="45" class="td_bg">
				        <!--<s:textfield name="csbjects.subjectsName" maxlength="50" class="input01" id="subjectsName" size="26"></s:textfield> -->
				         <input name="csbjects.subjectsName" maxlength="50"  type="text" class="input01" id="subjectsName" size="26"/>
				        </td> 
				      </tr>
				      <tr>
				        <td height="45" align="right" class="td_bg">科目借贷方向：</td>
				        <td height="45" class="td_bg">
				        <input type="radio" value="D" checked="checked" name="subRadio" id="subRadio"/>&nbsp;借&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="C" name="subRadio" id="subRadio"/>&nbsp; 贷 
				        <input type="hidden"  name="csbjects.subjectsDirection" id="direction"/>
				        </td>
				      </tr>
				      <tr>
				        <td height="45" align="right" class="td_bg">科目类别：</td>
				        <td height="45" class="td_bg">
				        <select class="category">
				        	<option value="银行" width ="180px">银行</option>
				        	<option value="现金">现金</option>
				        	<option value="固定资产">固定资产</option>
				        	<option value="其他">其他</option>
				        </select>
				        <input name="csbjects.subjectsCategory" id="category" type="hidden"/>
				        </td>
				      </tr>
				  
				</table><s:token/>
				</div>
      <div align="center">  
	              <input type="button"  id="save"  class="input-button JQuerySubmit" value=" 保存 " />
	              <input type="button" class="input-button" onclick="javascript:window.history.go(-1);" name="button2" value="返回" />
        </div> 
	</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>	    