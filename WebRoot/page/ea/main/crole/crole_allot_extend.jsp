	<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="<%=basePath%>js/jquery.js"
			type="text/javascript"></script>
		<title>管理员  分配多个角色快速通道</title>
		<!-- 管理员  分配多个角色快速通道 -->
		<link href="<%=basePath%>css/ea/manage.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
		<script type="text/javascript">
var basePath="<%=basePath%>";
var check = "0";
var eaIDs = "${seaList[0].eaID}";
var rodeID = "${roleID}";
var notoken=0;
var token = 0;

if('' !=  eaIDs){
   getMENU(eaIDs,rodeID);
}
function getMENU(eaID,roleID){
				    //列出此SEA下的所有菜单，并将此菜单下已分配的所有的BO的Interface列出，参数有eaID、roleID
				    $(".selectTag").css("color","");
					showBg('dialog','dialog_content');
				    $("table").parent().hide();
				      	var getMIForAllotUrl = basePath+"ea/croleallot/ajax_roleallot_ea_getListCMIForAllot.jspa?parameter="+encodeURI(eaID+ "-" + roleID)+"&title=title&date="+new Date().toLocaleString();
					      $.ajax({
                        url: getMIForAllotUrl,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
						           var member = eval("("+data+")");
						            var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
						           var smilist = member.cmilist;
						           var smiInterface = "<div class='"+eaID+" quanXian'>";
						            smiInterface += "<table width='99%' border='0' cellspacing='0' cellpadding='0' ><input name='input17' class='oroupboxAll' type='checkbox' />全选";
						            var ary = new Array(smilist.length);
						            for(var i=0;i<smilist.length;i++)
								   {
						            	ary[i]=false;
								      smiInterface += "<tr id='tr"+i+"'>";
								      smiInterface += " <td width='1%'><img src='<%=basePath%>images/ea/human/opene.gif'  width='18' height='15' /></td>";
								      smiInterface += "<td colspan='3' class='left'><b>";
								      smiInterface += smilist[i].menuName;
								      smiInterface += "</b></td></tr>";
								       	for(var ii=0;ii<smilist[i].cbiList.length;ii++)
								      	{
								       		if(smilist[i].cbiList[ii].sinterfaceList.length>0){
												ary[i]=true;
								      	      smiInterface += "<tr><td width='2%'><span class='left'><input name='input17' class='oroupbox' type='checkbox' value='oroupbox"+ i+ii+"' /></span></td>";
								      	      smiInterface += "<td width='2%' align='center'><img src='<%=basePath%>images/ea/human/img-2.gif' width='4' height='8' /></td><td width='120'>";
								      	      smiInterface += smilist[i].cbiList[ii].boName;
								      	      smiInterface += "</td>";
								      	      smiInterface += "<td class='left'><span>";
								      	      for(var iii=0;iii<smilist[i].cbiList[ii].sinterfaceList.length;iii++){
									      	      if(smilist[i].cbiList[ii].sinterfaceList[iii].interfaceStatus == "98")
									   		      {
									      	          smiInterface +="<input id='ocheckbox" + i + iii + "' name='ocheckbox' class='oroupbox"+i + ii+"' type='checkbox'  value='" 
									   			 			  + smilist[i].menuID + "@" 
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceID + "@"
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceUrl + "@"
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceStatus
									   			 			  + "' checked='true'/>" 
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceName
									   			 	  continue;
									   			  }
									   			      smiInterface +="<input id='ocheckbox" + i + iii + "' name='ocheckbox' class='oroupbox"+i + ii+"' type='checkbox'  value='" 
									   			 			  + smilist[i].menuID + "@" 
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceID + "@"
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceUrl + "@"
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceStatus
									   			 			  + "'/>" 
									   			 			  + smilist[i].cbiList[ii].sinterfaceList[iii].interfaceName
									   			 	
								      	      }
								      	      smiInterface += "</span></td>";
								      	      smiInterface += "</tr>";
								       		}
								      	}
								   }
								   smiInterface += "</table>";
								   smiInterface += "</div>";
								   $("#con").show();
								   $("div.jueSe").show();
								   $("#"+eaID).css("color","red");
								   $("div.enter").html("<a href='#' class='managementsubmit'><span>确 定</span></a>");
								   $('#matter').html(smiInterface); 
								 for (var int = 0; int < ary.length; int++) {
										if(!ary[int]){
											$("div#matter").find("tr#tr"+int).remove();
										}
								}
								 $(".oroupboxAll").click(function()
								     { 
								       if($(this).attr("checked")){
								         $("input[type='checkbox']",$("div.quanXian")).each(function(){
								          $(this).attr("checked",true);
								         });
								       }
								       else{
								           $("input[type='checkbox']",$("div.quanXian")).each(function(){
								          $(this).attr("checked",false);
								         });
								       }
								     });
								 $(".oroupboxAllJueSe").click(function()
									     { 
									       if($(this).attr("checked")){
									         $("input[type='checkbox']",$("div.jueSe")).each(function(){
									          $(this).attr("checked",true);
									         });
									       }
									       else{
									           $("input[type='checkbox']",$("div.jueSe")).each(function(){
									          $(this).attr("checked",false);
									         });
									       }
									     });
								  closeBg();
								  $(".oroupbox").click(function()
								     { 
								       aaa = "."+$(this).attr("value");
								       if($(this).attr("checked")){
								        $(aaa).attr("checked",true);
								       }
								       else{
								        $(aaa).attr("checked",false);
								       }
								     });
								  $(".managementsubmit").click(function()
								    {
									  var  roleIDS="";
									  $("div.jueSe").find("input.jueSe").each(function(){
										  if($(this).is(':checked')){
											  roleIDS+=$.trim($(this).attr("value")) + ",";
										  }
									  });
								      var parameterREMI = roleID + "-" + eaID + "-" + roleIDS + "-"; 
								       var interfaceIDs = "  ";//选择  默认为一个空格方便处理数组
								       var interfaceIDsno="  ";//未选择
								       var arrChk=$("input[name='ocheckbox']"); 
										$(arrChk).each(function(){ 
										 if($(this).is(':checked')){
										      interfaceIDs += $(this).attr("value") + ",";
										 }else{
											 interfaceIDsno += $(this).attr("value") + ",";
										 }
										});
								       parameterREMI += interfaceIDs + "-" + interfaceIDsno;
								       if($.trim(interfaceIDs)==""&&$.trim(interfaceIDsno)==""){
								    	   alert("此机构下没有最新的权限项要分配，请切换至其他机构!!!");
								    	   return
								       }
								       if($.trim(roleIDS)==""){
								    	   alert("请选择责任人!!");
								    	   return
								       }
								       $("#parameter").attr("value",parameterREMI);
								        $("#role_allot").attr("action","<%=basePath%>/ea/croleallot/t_ea_saveMoreCREMI.jspa?pageNumber=${pageNumber}&title=title");
                                       document.role_allot.submit.click();
								    });  
							 },
					         error: function cbf(data){
                           alert("数据获取失败！");
                        }
                    });
}
</script>
	</head>
	<body>
		<form name="role_allot" id="role_allot" action="" method="post">
		<input type="submit" name="submit" style="display:none"/>
			<div id="con" style="display: none;">
				<ul id="tags" style="width: 100%">
				<s:iterator value="seaList">
				  <li class="selectTag"><a href="#" class="selectTag" id="${eaID}" onclick="getMENU('${eaID}','${roleID}')" ><s:property value="eaName" /></a> </li>
			    </s:iterator>
				</ul>
		      <div id="tagContent">
		          <div class="tagContent selectTag" id="tagContent0" >
		              <div id="matter" style="overflow: auto;">
		              </div>
		          </div>
		      </div>
		    </div>
		        <input name="parameter" id="parameter" type="hidden"/>
			<s:token />
		</form>
		<div class="content jueSe tagContent">
			<table width='99%'  cellspacing='2' cellpadding='2' >
				<tr>
				<td><input  class='oroupboxAllJueSe' type='checkbox' />全选</td>
				</tr>
				<tr>
				<td>
				角色列表&nbsp;:&nbsp;
				<s:iterator value="beans">
					<input type="checkbox" class='jueSe' id="jueSe" value="<s:property value="roleID"/>"/>&nbsp;<s:property value="roleName"/>
				</s:iterator>
				</td>
				</tr>
			</table>
		</div>
		<!--js操作 确定按钮的展示 解决多次触发click事件-->   
		<div class='enter'></div>
		<!--JS遮罩层-->   
		<div id="fullbg"></div>   
		<!--endJS遮罩层-->   
		<!--对话框-->   
		<div id="dialog">   
		<div id="dialog_content">
		 <br/><div align='center'><img  src="<%=basePath%>images/jdt.gif"/></div></div>   
		</div>   
		<!--JS遮罩层上方的对话框-->  
	</body>
</html>