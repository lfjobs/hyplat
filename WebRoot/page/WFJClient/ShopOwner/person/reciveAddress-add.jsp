<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>个人中心添加地址省市级联</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>

</head>
<body>
	<div class="wfj11_017">
        <input type="hidden" id="dizhi" value=""/>
        <div id="address" >
        	<select  style="" id="province" name="province">
        	</select>
        	<select  style="" id="city" name="city">
        	</select>
        	<select style="" id="district" name="district">
        	</select>
        </div>
        
        <div class="wfj11_017_link"><span style="font-size:20px;">确定</span></div>
    </div>
    <script type="text/javascript">
    	var basePath='<%=basePath%>';
		var sccid="${param.sccid}";
		
    	$(document).ready(function(e) {
			$("body").css("height",$(window).height()) ;
			//修改字体大小
				$("#tops").find("li").attr("style","float:left;");
				$("#tops").find("li").eq(0).attr("style","width:15%;");
				$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			
			
            $(".wfj11_017_link").attr("style","width:30%;height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;text-align:center;cursor:pointer;color:#000;background-color:#F74C31;margin-left:69%;border-radius:5px;");
			$(".wfj11_017_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_017_top").css("lineHeight",$(window).height()*0.08+"px");
			$(".wfj11_017_link").css("font-size",$(window).height()*0.02+"px");

			
			//省
			$select = "<option selected='selected'>--请选择--</option>";
					var url = basePath
							+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&showType=weifenjin&districtPID="
							+ "&date1=" + new Date();
					$("#province").append($select);
					$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");						
							var distinctlist = member.distinctlist;
							
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("#province <option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province").append($op);
							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					
					});
					//市
					$("#province").change(function(){
						$("#city").empty();
						var change= $("#province").find("option:selected").val();

						
						$("#city").append($select);
							var url = basePath
								+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&districtPID="
								+ encodeURI(change) + "&date3=" + new Date();
								$.ajax({
									url : url,
									type : "get",
									async : true,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");						
										var distinctlist = member.distinctlist;
										for (var i = 0; i < distinctlist.length; i++) {
											$op = $("#city <option />");
											$op.attr("value", distinctlist[i].districtID)
													.attr("id", distinctlist[i].districtID)
													.text(distinctlist[i].districtName);
											$("#city").append($op);
										}
									},
									error : function cbf(data) {
										alert("数据获取失败！");
									}
									
								});
							});
					
					//区
					$("#city").change(function(){
						$("#district").empty();
						var change= $("#city").find("option:selected").val();

						
						$("#district").append($select);
							var url = basePath
								+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&districtPID="
								+ encodeURI(change) + "&date3=" + new Date();
								$.ajax({
									url : url,
									type : "get",
									async : true,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");						
										var distinctlist = member.distinctlist;
										for (var i = 0; i < distinctlist.length; i++) {
											$op = $("#district <option />");
											$op.attr("value", distinctlist[i].districtID)
													.attr("id", distinctlist[i].districtID)
													.text(distinctlist[i].districtName);
											$("#district").append($op);
										}
									},
									error : function cbf(data) {
										alert("数据获取失败！");
									}
					});
        });	    	
    	});
		$(".wfj11_017_link").click(function(){
		   	var p=$("#province").find("option:selected").text();
	    	var c=$("#city").find("option:selected").text();
	    	var d=$("#district").find("option:selected").text();
	    	var pcd=p+c+d;
	    	var ppid='${param.ppid}';
			if(p=="--请选择--"||c=="--请选择--"||d=="--请选择--"){
				alert("请重新选择");
				return ;
			}
			else{
				var posturl=new Array();
				posturl.push(basePath);
				posturl.push("/page/WFJClient/ShopOwner/person/reciveAdd.jsp?pcd=");posturl.push(pcd);
				posturl.push("&sccid=");posturl.push(sccid);
					open(posturl.join(""),"_self");
				}
		});
		
    </script>
</body>
</html>