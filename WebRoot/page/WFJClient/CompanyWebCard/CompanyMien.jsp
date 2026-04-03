<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司风采</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
</head>
<body class="bgcolorFFF">
	<div class="wfj12_025">
        
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li class="wfj_return"><a href="<%=basePath %>ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}&user=${user}&editType=0" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>${contactCompany.companyName }</li>
            	<li><a href="javascript:;"></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
<%--         <div class="wfj12_025_title">
        	<div>
            	<table>
                	<tr>
                    	<td width="25%" align="center"><img class="wfj12_025_hyimg" src="<%=basePath%>${contactCompany.logoPath}" /></td>
                    	<td width="15%">${contactCompany.cresponsible }</td>
                    	<td width="20%"><img class="wfj12_025_ewm" src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_ewm_02.png" /></td>
                    	<td width="40%" align="right"><div><c:if test="${company.ccomtype eq 0 }">大型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 1 }">中型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 2 }">小型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 3 }">微型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 4 }">供应商企业平台管理商城</c:if></div></td>
                    </tr>
                </table>
            </div>
        </div> --%>
        
        
        <div class="wfj12_025_content">
        	<div class="wfj12_025_hidden">
            	<div class="wfj12_025_con">
                	<div class="wfj12_025_width">
                    	<div class="wfj12_025_con_title">风采展示</div>
                        <input type="hidden" value="${ccompanyId }" id="ccompanyId"/>
                        <c:forEach items="${companyMienList }" var="entity" varStatus="status">
                       	<c:if test="${status.index%2==0 }">
                        <div class="wfj12_025_con_left">
                        	<ul>
                        		
                            	<li class="wfj12_025_bigimg"><input type="hidden" value="${entity[2] }"/><img src="<%=basePath%>${entity[3]}" /></li>
                            	<li class="wfj12_025_deleteimg">
                            	<input type="hidden" value="${entity[0] }" id="materialId"/>
                            	<input type="hidden" value="${entity[4] }" id="mienstyleId"/>
                            	<img class="del" src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_delete_02.png" /></li>
                            </ul>
                        </div></c:if>
                        <c:if test="${status.index%2!=0 }">
                        <div class="wfj12_025_con_right">
                        	<ul>                        		
                            	<li class="wfj12_025_bigimg"><input type="hidden" value="${entity[2] }"/><img src="<%=basePath%>${entity[3]}" /></li>
                            	<li class="wfj12_025_deleteimg">
                            	<input type="hidden" value="${entity[0] }" id="materialId"/>
                            	<input type="hidden" value="${entity[4] }" id="mienstyleId"/>
                            	<img class="del" src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_delete_02.png" /></li>
                            </ul>
                        </div></c:if></c:forEach>                 
                    </div>
                </div>
                <div class="wfj12_025_bottom">
                    <div class="wfj12_025_bottom_width">添加展示我的风采</div>
                </div>
                        
                <div class="wfj12_025_popimg">
                    <div class="wfj12_025_pop_content">
                        <ul>
                            <li><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_member_show_01.png" /><p></p></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
        
        
    </div>
    
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
   
    <script type="text/javascript">
	var basePath='<%=basePath%>';
	var ccompanyId=$("#ccompanyId").val();
    	$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			
			if($(window).width()>$(window).height()){
				$(".wfj12_025").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
				$(".wfj12_025_popimg").attr("style","width:"+$(window).width()*0.7+"px;");
				$(".wfj12_025_deleteimg").find("img").attr("style"," top:-"+$(window).height()*0.01+"px; right:"+$(window).height()*0.03+"px;");
			}else{
				$(".wfj12_025").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
				$(".wfj12_025_popimg").attr("style","width:"+$(window).width()+"px;");
				$(".wfj12_025_deleteimg").find("img").attr("style"," top:-"+$(window).height()*0.01+"px; right:"+$(window).height()*0.01+"px;");
			}
			
			$(".wfj12_025_title").attr("style","height:"+$(window).height()*0.1+"px;line-height:"+$(window).height()*0.1+"px; margin-top:"+$(window).height()*0.0015+"px;");
			$(".wfj12_025_title").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_025_title").find("div").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; border-top-left-radius:"+$(window).height()*0.02+"px; border-bottom-left-radius:"+$(window).height()*0.02+"px;");
			$(".wfj12_025_hyimg").attr("style","height:"+$(window).height()*0.08+"px; width:auto;margin:"+$(window).height()*0.01+"px auto;");
			$(".wfj12_025_ewm").attr("style","height:"+$(window).height()*0.02+"px; width:auto;");
			$(".wfj12_025_con_title").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_025_con_left").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj12_025_con_right").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj12_025_bottom").attr("style"," margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj12_025_bottom_width").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");


			$(".wfj12_025_popimg").find("li").find("p").attr("style","font-size:"+$(window).height()*0.02+"px; height:"+$(window).height()*0.075+"px; line-height:"+$(window).height()*0.025+"px; bottom:"+$(window).height()*0.075+"px;");
			$(".wfj12_025 .wfj12_025_con .wfj12_025_width  ul li.wfj12_025_bigimg").css({"height":$(".wfj12_025_con_left").height()+"px"})
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
						$(".wfj_top").css("background",activities.describe);
						$(".wfj12_025_title").css("background",activities.describe);
						$(".wfj12_025_bottom_width").css("background",activities.describe);
					}
				},
				error : function cbf(data){
					alert("公司风格加载失败！");
				}
			});
			
			
		   
			
			 var flag = false;
			 $(".wfj12_025_bigimg").mousedown(function() {
				var $this=$(this);
                var stop = setTimeout(function() {//down 1s，才运行。
                    flag = true;
				
                },1500);
				 $(".wfj12_025_bigimg").mouseup(function() {//鼠标up时，判断down了多久，不足一秒，不执行down的代码。
				    if (!flag) {
                        $("#occlusion2").css("z-index",$(".wfj12_018").css("z-index")+1);
						$("#occlusion2").jqmShow();
						$(".wfj12_025_popimg").css("z-index",$("#occlusion2").css("z-index")+1);
						var elaborate=$(this).find("input").val();
						var picsrc=$(this).find("img").attr("src");
						$(".wfj12_025_popimg").find("li").find("img").attr("src",picsrc);
						$(".wfj12_025_popimg").find("li").find("p").html(elaborate);
						$(".wfj12_025_popimg").fadeIn(1000);
                    }else{
/*                     	if($(this).parent().find(".wfj12_025_deleteimg").find("img").css("display")=='none')
                    		$(this).parent().find(".wfj12_025_deleteimg").find("img").css("display","block");
                    	else */
                    		$(this).parent().find(".wfj12_025_deleteimg").find("img").css("display","block");
                    		$(".wfj12_025_bottom").attr("style","display:none;");
					}
					window.clearTimeout(stop);
                });
             });

			$(".jqmOverlay").live("click",function(){
				$(".wfj12_025_popimg").fadeOut();
				$("#occlusion2").jqmHide();
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
			
			
			$(".wfj12_025_content").attr("style"," width:"+$(".wfj12_025").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj12_025_title").height()-$(window).height()*0.0015)+"px;overflow:hidden;");
			
			var h = $(".wfj12_025_con").height()+$(".wfj12_025_bottom").height()+$(window).height()*0.03;
			
			if(h>$(".wfj12_025_content").height()){
				$(".wfj12_025_hidden").attr("style"," width:"+parseInt($(".wfj12_025_content").width()+17)+"px;height:"+$(".wfj12_025_content").height()+"px;overflow:auto;");
			}else{
				$(".wfj12_025_hidden").attr("style"," width:"+$(".wfj12_025_content").width()+"px;height:"+$(".wfj12_025_content").height()+"px;overflow:auto;");
			}
		  
			
			$(".wfj12_025_bottom").find("div").click(function(){
				window.open(basePath+"ea/industry/ea_toEditCompanyMien.jspa?ccompanyId="+ccompanyId,"_self");
			});
			
			
        });
    	$(".del").click(function(){
    		var materialId=$(this).parent().find("input").eq(0).val();
    		var mienstyleId=$(this).parent().find("input").eq(1).val();
    		var url=basePath+"ea/industry/sajax_ea_delCompanyMien.jspa?ccompanyId="+ccompanyId+"&materialId="+materialId+"&mienstyleId="+mienstyleId;
    		$.ajax({
    			url : encodeURI(url),
    			type : "post",
				async : true,
				data : "json",
				success : function cbf(data){
				var member =eval("("+data+")");
				var flag=member.flag;
				if(flag=='1'){
					window.location.href=basePath+"ea/industry/ea_getCompanyMien.jspa?ccompanyId="+ccompanyId;
				}
			},
				error : function cbf(data){
				alert("删除失败");
			}
    		});
    	})

    	
    </script>
</body>
</html>