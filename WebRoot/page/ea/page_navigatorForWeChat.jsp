<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				.papeborder{border:1px solid #dddddd;border-top-color:#7c7c7c;width:25px; height:12px;vertical-align:middle;}
				.txt_00{ font-size:12px; color:#ff0000;vertical-align:middle;}
				.txt_01{font-size:14px; color:#333;text-decoration:none;vertical-align:middle;}
				img.link_01 {vertical-align:middle}
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
				   alert('请输入数字！');
				   $("#pageNumber").attr("value",1);
				} 
			}
		</script> 
		<style>
		div.paging td{
				background: #b5cfd2 url('<%=basePath%>/images/ea/ddsr/cell-blue.jpg');
		}
		div.paging table{
			width: 100%;
		}
	</style>
    <div style="border:#c5cec9 1px solid;margin:1px 0 0 0px;width: 100%" align="center" class="paging">
		<table>
			<tr>
	          <td align="center"  >
	          		<%-- <span class="txt_01">共</span><span class="txt_00">&nbsp;${pageForm.pageCount}&nbsp;</span><span class="txt_01">页</span>&nbsp;&nbsp;&nbsp;
	          		|&nbsp;&nbsp;&nbsp;&nbsp;<span class="txt_01">共</span><span class="txt_00">&nbsp;${pageForm.recordCount}&nbsp;</span><span class="txt_01">条</span>&nbsp;&nbsp;&nbsp; 
	          		|&nbsp;&nbsp;&nbsp;&nbsp;<span class="txt_01">第</span>&nbsp;<span class="txt_00">${pageForm.pageNumber}&nbsp;</span><span class="txt_01">页</span>&nbsp;&nbsp;&nbsp;&nbsp;
	          		<span class="txt_01">[</span> --%>
	          	
			  <c:choose>
			     <c:when test="${pageForm.pageNumber > 1}">
			       <a href="<%=basePath%><%=actionPath%>1" ><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/Home01.gif"></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        </c:when>
						<c:otherwise>
						  <span ><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/Home02.gif"></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				      
						</c:otherwise>
				</c:choose>	
			  <c:choose>
			     <c:when test="${pageForm.pageNumber > 1}">
			       <a href="<%=basePath%><%=actionPath%>${pageForm.pageNumber -1}"><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/pre01.gif"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				      
			        </c:when>
						<c:otherwise>
						  <span ><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/pre02.gif"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				      
						</c:otherwise>
				</c:choose>			
				<span ><input name="pageForm.pageNumber" id="pageNumber" size="4" value="${pageForm.pageNumber}" onblur="isNumber()" class="papeborder" /></span>
				<a href="#" onmouseup="javascript:goPageNumber();" id="goPageNumber" ><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/go01.gif"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pageForm.pageNumber < pageForm.pageCount}">
							<a href="<%=basePath%><%=actionPath%>${pageForm.pageNumber + 1}"><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/next01.gif"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      </c:when>
						<c:otherwise>
							<span ><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/next02.gif"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      </c:otherwise>
					</c:choose>					
					<c:choose>
						<c:when test="${pageForm.pageNumber < pageForm.pageCount}">
						  <a href="<%=basePath%><%=actionPath%>${pageForm.pageCount}" ><img border="0" class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/Lpage01.gif"></a>   
				      </c:when>
						<c:otherwise>
							<span><img border="0"  class="link_01" src="<%=basePath%>js/jqModal/css/images_blue/Lpage02.gif"></span> 
				    </c:otherwise>
					</c:choose>					
					<%-- <span class="txt_01">]</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 
			  <span class="txt_01">每页显示</span><span class="txt_00">${pageForm.pageSize}</span><span class="txt_01">条</span>  --%>             </td>
          </tr>
		</table>
        </div>
