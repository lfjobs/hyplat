<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="<%=basePath%>js/jquery.js"
			type="text/javascript"></script>
		<title>菜单分配</title>
		<link href="<%=basePath%>css/ea/main.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript">
		
			var basePath="<%=basePath%>";
			var token = 0;
			function getMenu(eaID){
			         $('#competence_menu').html(null);
			         $('#competence_group').html(null);
			         //得到此EA下所有的菜单
				     var getListMenuUrl = basePath + "ea/cmenuallot/ajax_menuallot_ea_getListCMenuForAllot.jspa?parameter="+encodeURI(eaID)+"&date="+new Date().toLocaleString();
				    $.ajax({
                        url: getListMenuUrl,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
					           var member = eval("("+data+")");
					            var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
					           var mlist = member.mlist;
					           var sMenu = "<ul>";
					           for(var i=0;i<mlist.length;i++)
							   {
							   	sMenu += "<li class='txt01 menus' title='"+i+"' >" + mlist[i].menuName + "</li>";
							   }
							   sMenu += "</ul>";
					         
					           //显示此功能下的所有菜单
							   $('#competence_menu').html(sMenu);   
							     
							    //当点击某个菜单时将此菜单下已分配和未分配的BO显示出来
							    $(".menus").click(function(){
							        i = $(this).attr("title");
							     	eaID = mlist[i].eaID;
							    	menuID = mlist[i].menuID;
							    	var getListBoUrl = basePath+"ea/cmenuallot/ajax_menuallot_ea_getListSBOForAllot.jspa?parameter="+encodeURI(eaID+"-"+menuID)+"&date="+new Date().toLocaleString();
							    	 $.ajax({
					                        url: getListBoUrl,
					                        type: "get",
					                        async: true,
					                        dataType: "json",
					                        success: function cbf(data){
								           var member = eval("("+data+")");
								            var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
								           var bolist = member.bolist;
								           var sBO = "<ul>";
								           var Bo ="";
								           for(var i=0;i<bolist.length;i++)
										   {   
											   if(bolist[i].boStatus == "98"){
											   	sBO += "<li class='txt01' ><input id='gcheckbox"+i+"' name='gcheckbox' class='groupbox' type='checkbox'  value='"+bolist[i].boID+"' checked='true'/>" + bolist[i].boName + "</li>";
											    Bo += bolist[i].boID + ",";
											    continue;
											   }
											   sBO += "<li class='txt01' ><input id='gcheckbox"+i+"' name='gcheckbox' class='groupbox' type='checkbox' value='"+bolist[i].boID+"'  />" + bolist[i].boName + "</li>";
										   }
										   sBO += "</ul><div class='managementsubmit'>确定</div>";
										   $('#competence_group').html(sBO);   
										     
										     //当点击确定时则给此菜单保存所选中的BO
										    $(".managementsubmit").click(function()
										    {
										       if(token)return;
										       token = 1;
										       var paramsEMB = eaID + "-" + menuID + "-"; 
										       var boIDs = "";
										       for(var ii=0;ii<bolist.length;ii++)
										       {     
										            var  a =  "#gcheckbox"+ii;
										            if($(a).attr("checked")){
										       		  boIDs += $(a).attr("value") + ",";
										       		}
										       }
										       if(boIDs == Bo){
										              document.location.href = "ea/cmenuallot/ea_getListSeaForCMenuAllot.jspa";
										              return;
										       };
										       paramsEMB += boIDs;
										       $("#parameter").attr("value",paramsEMB)
										         $('#menu_allot').attr("action","<%=basePath%>ea/cmenuallot/t_ea_saveCEMB.jspa?");
		                                        document.menu_allot.submit.click();
		                                        alert("保存成功！");
										    });
										    
										  },
								         error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
							   });
								          
					        },
					         error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
			}
			</script>
	</head>
	<body>
		<form name="menu_allot" method="post" id="menu_allot">
		<input type="submit" name="submit" style="display:none"/>
		    <div class="main">
							<div class="management" style="width: 18%;float: left;">
								<div class="competencepic">
									SEA功能
								</div>
								<div class="competence_detail" style="height: 550px;overflow: auto;">
									<ul>
										<s:iterator value="seasForMenuAllot">
											<li class="txt01"
												onclick="getMenu('<s:property value="eaID"/>')">
												<s:property value="eaName" />
											</li>
										</s:iterator>
									</ul>
								</div>
							</div>
							<div class="management" style="width: 18%;float: left;">
								<div class="competencepic">
									菜单
								</div>
								<div class="competence_detail" id="competence_menu" style="height: 550px;overflow: auto;">
								</div>
							</div>
							<div class="managemento" style="height: 510px;width: 60%;float: left;">
							    <div class="competencepic">
									BO管理
								</div>
								<div class="competence_detail" id="competence_group" style="height: 470px;overflow: auto;">
								</div>
								<input name="parameter" id="parameter" type="hidden"/>
							</div>
							<s:token />
			</div>
		</form>
	</body>
</html>