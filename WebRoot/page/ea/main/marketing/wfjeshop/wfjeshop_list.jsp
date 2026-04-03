<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人加盟</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/wechat/wfjeshop.js"></script>

<script type="text/javascript">
	var basePath='<%=basePath%>';           
	var pNumber ='${pageNumber}';  
	var activityId="";
	var frameid = '<%=request.getParameter("frameid")%>';
	var mainheight = 0; //框架高度
	var inforType = '<%=request.getParameter("inforType") %>';
</script>
</head>
<body>
	<form style="display: none;" name="addressForm" id="addressForm" method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display:none"/>
	</form>
	<div id="main_main" class="main_main" >
		<table class="JQueryflexme">
			<thead>
				<tr>
				<th width="30" align="center">
				选择
				</th>
					<th width="30" align="center">
						序号
					</th>
					<th width="50" align="center">
						编号
					</th>
					
					<th width="100" align="center">
						店铺名称
					</th>
					<th width="80" align="center">
						店主
					</th>
					<th width="120" align="center">
						店主电话
					</th>
					<th width="100" align="center">
						行业类别
					</th>
					<th width="150" align="center">
						店铺地址
					</th>
					<th width="100" align="center">
						log
					</th>
					<th width="100" align="center">
						标题图
					</th>
					<th width="150" align="center">
						所属公司
					</th>
				</tr>			
				</thead>
				<tbody>
					<c:forEach var='arr' items="${pageForm.list}" varStatus="number">
					<tr>
					<td>
					<input type="radio" name="chbox" />
					</td>
							<td>
							<span id="id">${number.index+1 }</span>
							</td>
							<td>
								<span id="ocode">${arr[0]}</span>
							</td>
							<td>
								<span id="organizationname">${arr[1]}</span>
							</td>
							<td>
								<span id="owner">${arr[2]}</span>
							</td>
							<td>
								<span id="Telephone">${arr[3]}</span>
							</td>
							<td>
								<span id="tradename">${arr[4]}</span>
							</td>
							<td>
								<span id="Address">${arr[5]}</span>
							</td>
							<td>
								<div>
									<c:if test="${arr[6]!=null}">
										<img id="logo"src="<%=basePath%>${arr[6]}" alt="" width="20" height="20" style="float:left;margin-left:40%;"/>
									</c:if>
									<input type="hidden" id="logo"  style="width:90%;border:0px;" class=" fou " value="${arr[6]}"/>
								</div>
							</td>
							<td>
								<div class="tdiv">
									<c:if test="${arr[7]!=null}">
										<img id="image"  class="preview"  src="<%=basePath%>${arr[7]}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
									</c:if>
						        	<input type="hidden" id="image"  value="${arr[7] }"/> 
								</div> 
	 						</td> 
	 						<td>
								<span id="Address">${arr[8]}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
		</table>
			<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/marketingWfj/ea_getWfjshopaction.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}&orgDetail=${orgDetail}">
			</c:param>
		</c:import>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</div>
	<script type="text/javascript">
	 $(function(){
	       setTimeout(function(){ 
			   var _height = $(window).height();	
			   if($("#mainframe").height() > 0){
			       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			   }else{		    
			   	   $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			       $("#mainframe").css({"height": 0 + "px"});
			   }
			},100);
	    	
		    $(window).resize(function(){ 
				setTimeout(function(){ 
			    var _height = $(window).height();		
			    if($("#mainframe").height() > 0){
			        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
				    $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			    }else{		 
			   	    $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
					$("#mainframe").css({"height": 0 + "px"});
			    }
			},100);
		    }); 
	   });
	</script> 
</body>
</html>