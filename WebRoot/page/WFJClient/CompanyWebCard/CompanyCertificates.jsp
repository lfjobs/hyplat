<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>公司证件信息</title>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycertificates.css" />
</head>

<body>
<div class="main">
    <header class="header">
    	<div class="back"><a href="<%=basePath%>ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}&user=${user}&editType=0"><img src="<%=basePath%>images/WFJClient/PersonalJoining/back.png" /></a></div>
        证件信息
        <div class="add"><img src="<%=basePath%>images/WFJClient/PersonalJoining/add.png" /></div><div class="clear"></div>
    </header>
		<article>
			<ul>				
				<li>
					<div class="pic">
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/one.png" />
					</div> 
					营业执照
					<div class="more">
					<c:forEach items="${companycertificates }" var="entity">
						<c:if test="${entity!=null&&entity.certificateType eq '营业执照' }">
							<input type="hidden" value="${entity.certificateID }"/>
						</c:if>
					</c:forEach>
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/more.png" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="pic">
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/two.png" />
					</div> 
					组织机构代码证
					<div class="more">
					<c:forEach items="${companycertificates }" var="entity">
						<c:if test="${entity!=null&&entity.certificateType eq '组织机构代码证' }">
							<input type="hidden" value="${entity.certificateID }"/>
						</c:if>
					</c:forEach>
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/more.png" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="pic">
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/three.png" />
					</div> 
					国税地税登记证
					<div class="more">
					<c:forEach items="${companycertificates }" var="entity">
						<c:if test="${entity!=null&&entity.certificateType eq '国税地税登记证' }">
							<input type="hidden" value="${entity.certificateID }"/>
						</c:if>
					</c:forEach>
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/more.png" />
					</div>
				</li>
				<li>
					<div class="pic">
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/four.png" />
					</div> 
					银行开户许可证
					<div class="more">
					<c:forEach items="${companycertificates }" var="entity">
						<c:if test="${entity!=null&&entity.certificateType eq '银行开户许可证' }">
							<input type="hidden" value="${entity.certificateID }"/>
						</c:if>
					</c:forEach>
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/more.png" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="pic">
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/five.png" />
					</div> 
					统计登记证
					<div class="more">
					<c:forEach items="${companycertificates }" var="entity">
						<c:if test="${entity!=null&&entity.certificateType eq '统计登记证' }">
							<input type="hidden" value="${entity.certificateID }"/>
						</c:if>
					</c:forEach>
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/more.png" />
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="pic">
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/six.png" />
					</div> 
					财政登记证
					<div class="more">
					<c:forEach items="${companycertificates }" var="entity">
						<c:if test="${entity!=null&&entity.certificateType eq '财政登记证' }">
							<input type="hidden" value="${entity.certificateID }"/>
						</c:if>
					</c:forEach>
						<img src="<%=basePath%>images/WFJClient/PersonalJoining/more.png" />
					</div>
					<div class="clear"></div>
				</li>
			</ul>
		</article>

		<div class="bottom"></div>
</div>



	<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var ccompanyId = '${ccompanyId}';
		var certificateID="";
		$(document).ready(function(e) {
			$("body").attr("style","width:" + $(window).width() + "px;height:"+ $(window).height() + "px;");
					if ($(window).width() > $(window).height()) {
						$(".main").attr("style","width:" + $(window).width() * 0.7+ "px;height:" + $(window).height()+ "px;");
					}
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
								$(".header").css("background",activities.describe);
							}
						},
						error : function cbf(data){
							alert("公司风格加载失败！");
						}
					});		
					
					
				});
		$("article").find("ul").find("li").live("click",function(){				
			if($(this).find("input").length>0)
				certificateID=$(this).find("input").val();
			var type=$(this).text().trim();
							window.location.href = basePath
									+ "ea/industry/ea_toAddCompanyCertificates.jspa?ccompanyId="+ccompanyId+"&certificateID="+certificateID+"&certificatetype="+type;
						});
	</script>

</body>
</html>
