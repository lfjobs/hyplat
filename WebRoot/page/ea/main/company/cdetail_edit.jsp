<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司基本信息管理</title>

		
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/company/cdetail_edit.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript"
	src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>/css/ea/register.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.spa{
	 position: relative;
	 right: 100px;
}
a:hover{
		cursor:pointer;}
.sprs{
	display: block;
	width:100%;height:17px;}
</style>
</head>
<script type="text/javascript">
var basePath="<%=basePath%>/";
var codeID="${code.codeID}";
var chipids = new Array();
var i = 0;
var verification="${verification}";
var subjectId2="${company.subjectsID}";
var notoken = 0;
var codeValue="";
var bl=false;
var search2=0;
var pagenum="";
</script>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<div>
<div class="unitlib_list_right03">
<form name="DetailForm" method="post" id="DetailForm" enctype="multipart/form-data">
<input type="submit" name="submit" style="display:none">
    <div>
         <div class="titlenav1"><span class="td21">基本资料注册</span><span class="td22">(以下各项为必填项)</span></div>
        <div class="conten">
          <ul>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>组织机构：</span>
                <input type="text" name="companyIdentifier" maxlength="50"  class="yname put1 error" style="color: red;" value="${companyIdentifier}" disabled="disabled"/>
              </li>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>单位名称：</span>
                <input type="text" name="companyName" id="units.companyName" maxlength="48" class="yname put1 error" value="${companyName}"/><font class="font"></font>
              </li>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>单位地址：</span>
                <input type="text" name="detail.companyAddress" id="detail.companyAddress" maxlength="50" class="yname put2 error" value="${detail.companyAddress}"/><font class="font"></font>
              </li>
              
              <li><div align="left"><span class="contenspan"> <span class="txt05">* </span>单位logo：</span><input type="file" name="photo" id="UploadFile" class="input error" style="width: 150px;"><input type="hidden" name="detail.logo" id="detail.logo" value="${detail.logo}"></div>
              	
              	
				

              </li>

                <li>
                	<span class="contenspan"><span class="txt05">* </span>负责人：</span>

                  	<input type="text" name="detail.companyManager" id="unitsDetail.companyManager" maxlength="50" class="yname put3 error" value="${detail.companyManager }"/>
               		<font class="font"></font>
                </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>电子邮箱：</span>
                    <input type="text" name="detail.companyEmail" id="unitsDetail.companyEmail" maxlength="50" class="yname email error" value="${detail.companyEmail }"/>
               		<font class="font"></font>
                </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>电话:</span>
                    <input type="text" name="detail.companyPhone"  id="unitsDetail.companyPhone" maxlength="50" class="yname phone error" value="${detail.companyPhone }" />
              		<font class="font"></font>        
                </li>
                <li>
					<span class="contenspan"> <span class="txt05">* </span>是否添加到微信${showwechat }：</span>
					<s:select list="#{'00':'不添加','01':'添加'}" name="showwechat" theme="simple" id="com.showwechat">
					</s:select>
				</li> 
				<li class="li2" style="display: none;">
                	<span class="contenspan">
                		<span class="txt05">* </span>货币类别：</span>
                	<%-- <s:select list="list" listValue="codeValue" class="crr" name="codeId"
                		listKey="codeID" value="codeValue" id="sel">
                	</s:select> --%>
                	<select class="crr" name="codeId"  id="sel">
                		<c:if test="${company.bookCurrency!=''}">
                			<option value="${company.codeID}">${company.bookCurrency}</option>
                		</c:if>
                		<c:forEach items="${list}" var="list">
                			<option value="${list.codeID}">${list.codeValue}</option>
                		</c:forEach>	
                	</select>
                </li>
                <li class="li2" style="display: none;">
                	
                    <span class="contenspan">
                    	<span class="txt05">* </span>累积盈亏科目:</span> 
                    <input type="hidden" id="subjectsID"
								name="subjectId" value="${company.subjectsID}" />
							<input type="text" readonly="readonly"
								placeholder="单击选择科目"	 class="notnull inputbottom tosubjects crr" style="width: 160px;"
									id="subjectsName" name="subjectName" value="${company.accumulated}" readonly="readonly"/>
                </li>
                 <li>
               	  <span class="contenspan">验证码：</span>
                   <img border="0" name="validateImage" onClick="this.src='<%=basePath%>/page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>/page/ea/security_code.jsp"/>          
                </li>
                <li>
               	  <span class="contenspan">
               	  	<span class="txt05">* </span>输入验证码：</span>
                  <input type="text"  class="yname validate error" name="verification" />
                </li>
                
                      <s:token/>
              </ul>
             </div>
		    
	</div>
	<input  name="detail.detailKey"  type="hidden" class="input01" id="detailKey" size="14" readonly="readonly" value="${detail.detailKey}"/>
	<input  name="detail.detailID"  type="hidden" class="input01" id="regionID" size="14" readonly="readonly" value="${detail.detailID}"/>
</form>

	<div class="mainxuxian"></div>
    	   <div class="conten1">
    	   	<span class="spa">
    	   		<input type="button" onclick="sr()" class="button" value="开启财务模块" id="srv">
    	   	</span>
    	   	<span>
    	   		<input  type="button" onClick="save()" class="button" value="提 交" />
    	   	</span>           
   </div>

</div>
</div>
<div class="jqmWindow jqmWindowcss2" id="subjectr" style="width: 550px;height:420px;top: 10%;background-color: #DAE7F6;">
	    	<div style="border: 1px #EBEBEB solid; background-color: #B0C4DE;height: 23px">
	    		<font size="3"><b>&nbsp;选择科目</b></font>
	    	</div>
	    	<div style="position:relative;left:250px; height: 50px;top: 2%;">
	    		<input type="button" value="首页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	    		<input type="button" value="上一页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="下一页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="尾页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</div>
	    	<div style="width: 130px;border: 1px #F0F8FF solid;background-color:#F0F8FF;
	    		border-left:3px #F0F8FF inset;border-top:3px #F0F8FF inset;
	    			height:300px;position: relative;top: -10px;">
	    		<div style="height: 270px;overflow:auto;">
	    		<table id="kemuone" cellspacing="0px">  			
	    		</table></div><br>
	    	</div>
	    	<div style="border: 1px #F0F8FF dashed;width:410px; height:300px;
	    		border-left:3px #CFCFCF double;border-top:3px #F0F8FF inset;
	    		position: relative;left:135px;top: -314px;">
	    		<table style="background-color:#F0F8FF" id="kemutoo">
	    			<tr style="background-color: #CAE1FF">
	    				<td width="72px" align="center">科目编号</td>
	    				<td width="110px" align="center">科目名称</td>
	    				<td width="80px" align="center">科目类别</td>
	    				<td width="65px" align="center">借贷方向</td>
	    				<td width="75px" align="center">账号类型</td>
	    			</tr>			
	    		</table>
	    	</div>	
	    	<hr style="border: 1px red solid">
    	<div>
    		<b style="position: absolute;top: 380px;left:380px;">
	    		<input type="button" value="确定" id="determine" style="width: 42px;height: 25px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="取消" id="cancel" style="width: 42px;height: 25px"/> 
    		</b>
    	</div>
    </div>
 <script type="text/javascript">
 	$(function(){
	
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	//选择仓库关闭
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				//loadcab.window.closePort();// 关闭读数据端口
				chipids = new Array();
                i = 0;
			});
	/** **************************************科目管理******************************************* */
$(function(){
	$(".subrid").click(function(){
		if($(this).val()=="首页")
			subtrs("1",$(this).attr("id"));
		else if($(this).val()=="上一页")
			subtrs(parseInt(pagenum)-1,$(this).attr("id"));
		else if($(this).val()=="下一页")
			subtrs(parseInt(pagenum)+1,$(this).attr("id"));
		else if($(this).val()=="尾页")
			subtrs(Math.ceil(search2/14),$(this).attr("id"));
	});
	$(".subtr").live("click",function(){
		$(".subtr").each(function(){
			$(this).attr("style","background-color:#E0EEEE");
		});
		$(this).attr("style","background-color:#8DB6CD");
	});
	$("#determine").click(function(){
		$(".subtr").each(function(){
			if($(this).attr("style")=="background-color:#8DB6CD"){
				$("#subjectsID").val($(this).find("td").find("input").val());
				$("#subjectsName").val($(this).find("td").eq(1).text());
				$(".receivesubjects").removeClass("receivesubjects");
			}
		});
		$("#subjectr").jqmHide();	
	});
	$("#cancel").click(function(){
		$("#subjectr").jqmHide();		
	});
	$(".tosubjects").click(function(){
		$(this).parent().parent().addClass("receivesubjects");
		if(!$(".subNumber").text()){
		var subjecturl = basePath
			+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		$.ajax({
			url : encodeURI(subjecturl + "002&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjects = member.subjectsList;
				for(var i=0;i<subjects.length;i++){
					var tr;
					if(i==0){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line4.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'>" +
							subjects[i].subjectsName+"</td></tr>";
					}else if(i==(subjects.length-1)){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line2.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'>" +
							subjects[i].subjectsName+"</td></tr>";
					}else{
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line3.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'>" +
							subjects[i].subjectsName+"</td></tr>";
					}
					$("#kemuone").append(tr);
				}				
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		}
		$("#subjectr").jqmShow();	
	});
});
	})
 </script>
</body>
</html>

