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
<title>已删除管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
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
   var basePath="<%=basePath%>";
    var emailID="";
$(function(){ 
 $(".jqmWindow").jqm({
                    modal: true,// 
                    overlay: 20 // 
                }).jqmAddClose('.close')//
   $('.JQueryflexme').flexigrid({
                    height: 350,
                    width: 'auto',
                    minwidth: 30,
                    title: '已删除',
                    minheight: 80,
                    buttons: [{  
                        name: '转发',
                        bclass: 'add',
                        onpress: action//当点击调用方法
                    }, {
                        separator: true
                    }, {
                        name: '彻底删除',
                        bclass: 'edit',
                        onpress: action//当点击调用方法
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
	                    case '转发':
	                    var n=0;  
				var checkbs = document.getElementsByName("chss");
				for (var i = 0; i < checkbs.length; i++) {
					if (checkbs[i].checked) {
						n++;

					}

				}
				if(n==0){
				  alert("请选择");
					return;
				}
				if(n>1){
				 alert("只能转发一个");
				 return;
				}
				
	                       //    manageID = "";
	                        //   document.cstaffForm.reset();
	                       //    $("#jqModel").jqmShow();
	                       document.location.href=basePath+"ea/email/ea_getEmaiInfo.jspa?emailID="+emailID+"&zhType=z";
	                           break;
        
                        case '彻底删除':
                            // if(confirm("是否确定删除？")){
                             //    var reciveID="";
	                         //    $('input[name="chs"][checked]').each(function(i,tmp){
	                         //       var emID=$(this).val()+";";
	                         //       reciveID+=emID;
							//	  });
							//	if(reciveID!="")
							//	{
							//	  $("#reciveID").attr("value",reciveID);
							//	  $("#emailForm").attr("action", basePath+"ea/email/ea_delEmail.jspa?status='04'");
               				//      document.emailForm.submit.click();
								//}
                            //}
                            
                              var reciveID = "";
				var checkbs = document.getElementsByName("chss");
				for (var i = 0; i < checkbs.length; i++) {
					if (checkbs[i].checked) {
						reciveID += checkbs[i].value + ";";

					}

				}
				if(reciveID == ""){
					alert("请选择");
					return;
				}
				if (confirm("是否确定彻底删除？")) {
					if (reciveID != "") {
						$("#reciveID").attr("value", reciveID);
						$("#emailForm")
								.attr(
										"action",
										basePath
												+ "ea/email/ea_delEmail.jspa?status=04");
						document.emailForm.submit.click();
					} 
				}
                            break;
                       case '设置每页显示条数':
			var url=basePath+"ea/manage/ea_getEnterpriseManageList.jspa?1=1";
				numback(url);
							break; 
                    }
                }
                 $(".JQueryflexme tr[id]").click(function(){
                    $("input[name=chs]", $(this)).attr("checked", "checked");
                    emailID =this.id;
                })
                 $(".JQueryflexme tr[id]").dblclick(function(){
                          if (emailID == "") {
                                alert('请选择!')
                                return;
                            }
                            document.cstaffForm.reset();
                            $t = $("div#jqModel");
                            $p = $("tr#" + emailID);
                            $p.find("span[id]").each(function(){
                                $t.find(":input[name]#" + this.id).val($(this).text())
                            });
                             var urlattach = basePath
				+ "ea/email/sajax_ea_getAttachOfEmail.jspa?datesete="
				+ new Date();
		$.ajax({
					url : urlattach,
					type : "post",
					async : true,
					dataType : "json",
					data : {
						emailID : emailID
					},
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var attachlist = member.attachlist;
						var str = "";
						for (var i = 0; i < attachlist.length; i++) {
							var filepath = '"' + attachlist[i].filepath + '"';
							str += "<a style='text-decoration:none;' href='javascript:downLoadAttach("
									+ filepath + ");'>"
									+ attachlist[i].filename + "</a><br>";

						}

						$("#attach").html(str);

					},
					error : function cbf(data) {
						alert("数据获取失败！")
					}
				});
                            $("#jqModel").jqmShow();
                })
                $("#xz").live("click", function(event){
                    if($(this).attr("checked")){
								         $("input[type='checkbox']").each(function(){
								          $(this).attr("checked",true);
								         })
								       }
								       else{
								           $("input[type='checkbox']").each(function(){
								          $(this).attr("checked",false);
								         })
								       }
                })
 });  
  // 下载附件
function downLoadAttach(filePath) {
	window.open(basePath + "/servlets/render?filename=" + filePath);
} 
</script>
</head>
<body >    
<form method="post" id="emailForm" action="" name="emailForm"><%-- 不告诉你--%>
			<input id="reciveID" name="reciveID" type="hidden"/>
			<input id="listStatus" name="listStatus" value="delEmail" type="hidden"/>
			 <input type="submit" name="submit" style="display:none"/>
	</form>
  <div class="main_main">
  <table class="JQueryflexme">
        				<thead>
							    	   	<tr>
							             	<th width="90" align="center">
												<input type="checkbox" id="xz" />全选/反选
											</th>
											<th width="80" align="center">
												发件人
											</th>
											<th width="120" align="center">
												主题
											</th>
											<th width="400" align="center">
												内容
											</th>
											<th width="114" align="center">
												日期
											</th>
							            </tr>
						</thead>
								 <tbody>
							         	<s:iterator value="pageForm.list">
								    		<tr id="${emailID}" title="${addresseeStatus}">
									            <td><input name="chss" type="checkbox" value="${emailID}"/></td>
												<td><span id="addresserName">${addresserName}</span></td>
												<td><span id="title">${title}</span></td>
												<td><span id="content">${content}</span></td>
												<td><span id="emailDate">${emailDate}</span>
												<span id="emailID" style="display:none"> ${emailID}</span></td>
								            </tr>
							            </s:iterator>
							            </tbody>
							</table>
								<c:import url="../../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/email/ea_getEmailIndex.jspa?"></c:param>
						</c:import>	
			</div>
			 <form name="cstaffForm" id="cstaffForm" method="post"> 
         <input type="submit" name="submit" style="display:none"/>
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">     
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">个人邮箱
    <div class="close"></div>
    </div>
  </div>
				  <table width="450" border="0"  id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				  <tr>
				     <td>
				     <table width="430" height="340" border="0" id="stafftable2" align="left" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="100" height="60" align="right">发件人：</td>
                         <td width="330"><input type="text" id="addresserName" readonly="readonly"  name="addresserName" size="30" id="reciveName"/>
                         </td>
                       </tr>
                        <tr>
                         <td width="100" height="60" align="right">主题：</td>
                         <td width="330"><input type="text"   id="title" readonly="readonly" size="30" name="title"/></td>
                       </tr>
                        <tr>
                         <td width="100" height="60" align="right">附件：</td>
                         <td  colspan="5"><div style="width:300px;"id="attach"></div></td>
                           
                       </tr>
                       <tr>
                         <td width="100" height="200" align="right">正文：</td>
                         <td colspan="5"><textarea  name="content" readonly="readonly" cols="40" rows="8" id="content"></textarea></td>
                       </tr>
                     </table>
                     </td>
				   </tr>
				</table>
				</div>
        </div>
        	<s:token></s:token>
            </form>
	</body>
</html>	     