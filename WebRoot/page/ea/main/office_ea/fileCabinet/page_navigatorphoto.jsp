<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		  <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
			String actionPath = request.getParameter("actionPath").trim();
			//servletPath==getVideoPaginationServlet
			//判断传过来的servletPath是否有参数 如果有参数 在servletPath后面加上&num= 【getVideoPaginationServlet?id=001$num=】
			//如果传过来的servletPath没有参数 直接在servletPath后面加上?num=【getVideoPaginationServlet?num=】
			actionPath = actionPath.replace("pageForm.pageNumber","");
			actionPath = (actionPath.indexOf("?")==-1)?actionPath+"?pageForm.pageNumber=":actionPath+"&pageForm.pageNumber=";
		 %>
		 <style type="text/css">
				.papeborder{border:1px solid #dddddd;border-top-color:#7c7c7c;width:25px; height:12px;}
				.txt_00{ font-size:12px; color:#ff0000;vertical-align:middle;}
				.txt_01{font-size:14px; color:#333;text-decoration:none;}
				.link_01{
				font-size:12px;
				text-decoration:none;
				color:#333;}
				body {
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
			}
		</style>
		<script type="text/javascript">
			var pageNumber = 1;
		    function goPageNumber(){
		     	var pageNumber = $("#pageNumber").attr("value");
		     	document.location.href="<%=basePath%><%=actionPath%>" + pageNumber;
		    }
			function isNumber()
			{
				if(isNaN($("#pageNumber").attr("value"))){ 
				   alert('请输入数字！') 
				   $("#pageNumber").attr("value",1)
				} 
			}
		</script>
	</head>
	<body>
    <div style="width:99%;margin-top:1px;">
		<table width="100%" align="left">
			<tr>
	          <td align="center">
	          		<span class="txt_01">共</span><span class="txt_00">&nbsp;${pageForm.pageCount}&nbsp;</span><span class="txt_01">页</span>&nbsp;&nbsp;&nbsp;
	          		|&nbsp;&nbsp;&nbsp;&nbsp;<span class="txt_01">共</span><span class="txt_00">&nbsp;${pageForm.recordCount}&nbsp;</span><span class="txt_01">条</span>&nbsp;&nbsp;&nbsp; 
	          		|&nbsp;&nbsp;&nbsp;&nbsp;<span class="txt_01">第</span>&nbsp;<span class="txt_00">${pageForm.pageNumber}&nbsp;</span><span class="txt_01">页</span>&nbsp;&nbsp;&nbsp;&nbsp;
	          		<span class="txt_01">[</span>
	          		<a href="<%=basePath%><%=actionPath%>1" class="link_01">首页</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <c:choose>
			          <c:when test="${pageForm.pageNumber > 1}"><a href="<%=basePath%><%=actionPath%>${pageForm.pageNumber -1}" class="link_01">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				      </c:when>
						<c:otherwise><span class="link_01">上一页</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				      </c:otherwise>
				</c:choose>			
				<input name="pageForm.pageNumber" id="pageNumber" size="4" value="${pageForm.pageNumber}" onblur="isNumber()" class="papeborder" />
				<a href="#" onmouseup="javascript:goPageNumber();" id="goPageNumber" class="link_01">GO</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pageForm.pageNumber < pageForm.pageCount}">
							<a href="<%=basePath%><%=actionPath%>${pageForm.pageNumber + 1}" class="link_01">下一页</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						     
				      </c:when>
						<c:otherwise>
							<span class="link_01">下一页</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				      </c:otherwise>
					</c:choose>
				<a href="<%=basePath%><%=actionPath%>${pageForm.pageCount}" class="link_01">末页</a>
					<span class="txt_01">]</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 
		       </td>
          </tr>
		</table>
        </div>
	</body>
</html> 
