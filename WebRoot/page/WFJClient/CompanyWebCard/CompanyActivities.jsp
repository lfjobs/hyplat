<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司活动</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<body>
	<div class="wfj01_003">
    	<div class="wfj01_003_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="<%=basePath %>ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}&user=${user}&editType=0" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>公司活动</li>
            	<li class="wfj01_003_edit"><a href="javascript:;">编辑</a></li>
            </ul>
        </div>
        
        <div class="wfj01_003_content">
        	<div class="wfj01_003_hidden">
            	<div class="wfj01_003_con">
            		<input type="hidden" value="${ccompanyId }" id="ccompanyId"/>
            		<c:forEach items="${ActivitiesList }" var="entity">
                	<div class="wfj01_003_list">
                    	<table class="wfj01_003_click01" >
                        	<tr>                        		
                            	<td class="wfj01_003_uploadimg"><img onclick="edit('${entity.activitiesID}')" src="<%=basePath%>${entity.picture}" /></td>
                            	<td><span onclick="edit('${entity.activitiesID}')" >${entity.title }</span></td>
                            	<td><span>${entity.author }</span></td>
                            	<td><span><fmt:formatDate value="${entity.releaseTime }"/></span></td>
                            	<td class="wfj01_003_delete"><img onclick="toDel('${entity.activitiesID}')" src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_delete_04.png" /></td>
                            	<input type="hidden" value="${entity.activitiesID }"/>
                            </tr>                       	
                        </table>
                    </div></c:forEach>
                </div>
            </div>
        </div>
        <div class="wfj01_003_bottom">
        	<div id="add">添加更多活动</div>
        </div>
        
    </div>
    
    
    <script type="text/javascript">
    var basePath='<%=basePath%>';
    var ccompanyId='${ccompanyId}';
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$(".wfj01_003_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
		 
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;font-size:"+$(window).height()*0.02+"px;");
			
			
			$(".wfj01_003_list").find("table").attr("style"," padding:"+$(window).height()*0.015+"px 0; border-bottom:"+$(window).height()*0.003+"px solid #F0F0F0;");
			$(".wfj01_003_list").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_003_list").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
			$(".wfj01_003_bottom").find("div").attr("style","font-size:"+$(window).height()*0.03+"px; border-radius:"+$(window).height()*0.01+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			
			$(".wfj01_003_edit").click(function(){
				if($(this).find("a").text()=="编辑"){
					$(".wfj01_003_delete").fadeIn(1000);
					$(this).find("a").text("完成");
				}else{
					$(".wfj01_003_delete").fadeOut(500);
					$(this).find("a").text("编辑");
				}
			});
		
			if($(window).width()>$(window).height()){
				$(".wfj01_003").attr("style","width:70%;height:"+$(window).height()+"px;");
				$(".wfj01_003_content").attr("style","width:"+$(".wfj01_003").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_003_top").height())+"px;overflow:hidden;");
				if($(".wfj01_003_list").height()>$(".wfj01_003_content").height()){
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width()+17)+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}else{
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width())+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}
			}else{
				$(".wfj01_003").attr("style","width:100%;height:"+$(window).height()+"px;");
				$(".wfj01_003_content").attr("style","width:"+$(".wfj01_003").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_003_top").height())+"px;overflow:hidden;");
				if($(".wfj01_003_list").height()>$(".wfj01_003_content").height()){
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width()+17)+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}else{
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width())+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}
				
			}
			//添加
			$("#add").click(function(){
				window.open(basePath+"ea/industry/ea_toEdit.jspa?ccompanyId="+ccompanyId+"&activitiesId="+"&user=${user}","_self");
			});	
			
			//加载公司风格
			var url=basePath+"ea/industry/sajax_ea_CompanyStyle.jspa?ccompanyId=${ccompanyId}";
			$.ajax({
				url : url,
				type : "get",
				async : false,
				success : function cbf(data){
					var member=eval("("+data+")");
					var activities=member.activities;
					if(activities!=null){
						$(".wfj01_003_top").css("background",activities.describe);
						$("#add").css("background",activities.describe);
					}
				},
				error : function cbf(data){
					alert("公司风格加载失败！");
				}
			});
	
			
		});
    	//编辑
		function edit(i){				
			window.location.href=basePath+"ea/industry/ea_toEdit.jspa?activitiesId="+i+"&ccompanyId="+ccompanyId+"&user=${user}";
		}
		//删除
		function toDel(i){
			var url=basePath+"ea/industry/sajax_ea_delActivities.jspa?activitiesId="+i+"&user=${user}";
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				data : "json",
				success : function cbf(data){
					var member =eval("("+data+")");
					var flag=member.flag;
					if(flag=='1'){
						window.location.href=basePath+"ea/industry/ea_getCompanyActivitiesList.jspa?ccompanyId="+ccompanyId+"&user=${user}";
					}
				},
				error : function cbf(data){
					alert("删除失败");
				}
			});
		}
	</script>
</body>
</html>