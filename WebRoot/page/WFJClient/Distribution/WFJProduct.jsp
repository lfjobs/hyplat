<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
	<title>产品管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript"></script>
	<link href="<%=basePath%>/css/WFJClient/wfjhtmlStyle.css" rel="stylesheet" />
	<link href="<%=basePath%>css/WFJClient/wfjstyle.css" rel="stylesheet" />
	
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
	<script src="<%=basePath%>js/ea/wechat/wechartMenugrcp.js"></script>
	
	<script type="text/javascript">
		var basePath='<%=basePath%>';
		var token=0;
		var cpid="";
		var notoken=0;
	</script>
	</head>
		<body>
			<div class="wfjall">
				<div class="wfjtop">
					<ul>
						<li>
							<a href="<%=basePath%>/ea/wfjcustomer/ea_getDLgetlist.jspa?pagenull=1" class="left adds" style="color:white;">&nbsp;+&nbsp;代理添加</a>
							产品管理
							<a href="javascript:void(0);" class="right adds" onclick="addcp()" style="color:white;">+&nbsp;产品添加&nbsp;</a></li>
					</ul>
				</div>
				<div class="wfj_class">
					<ul>
						<li><div onclick="change_agencys(this)" name="agencysinfo">代理产品管理</div></li>
						<li class="change_bg"><div onclick="change_agencys(this)" name="agencysinfo" style="color: #2E63FB">产品管理</div></li>
					</ul>
				</div>
				<!--产品管理-->
				<div class="unagency" id="unagencys">
					<div class="PersonalGoods">
						<form method="post" id="cpform" >
							<!-- 个人产品出售物品信息 -->
							<c:forEach var="cartItem" items="${beans}" varStatus="idc">
								<div class="goods" id="${cartItem[0]}" onclick="dui(this)" >
								<div class="choice">
									<img id="images" name="images" src="<%=basePath%>/images/WFJClient/choice_blank.png" />
									<div class="aa" style="display:none;"><input  type="checkbox" id="cpid" class="chebox" name="checkbox"/></div>
								</div>
								
								<div class="product">
									<img src="${cartItem[6]}"/>
								</div>
								<div class="minute">
								<ul>
									<li class="pro_name">${cartItem[2]}</li>
									<li class="pro_money">￥${cartItem[5]}</li>
									<li>总数量:${cartItem[4]}</li>
								</ul>
								</div>
									<div class="details">     			
										<ul>
											<li>
												<c:if test="${cartItem[3]==0}">
											 		已下架
												</c:if>
												<c:if test="${cartItem[3]==1}">
												 	出售中
												</c:if>
											</li>
											<li><a href="<%=basePath%>/ea/wfjcustomer/ea_updatecpjsp.jspa?ppID=${cartItem[0]}">修改</a></li>
										</ul>
									</div>    
								</div>
							</c:forEach>
						<div class="pro_bottom">
						<div>
						<ul>
						<li onclick="del()"><div><a  href="javascript:void(0);" >删除</a></div></li>
						<li onclick="xiajia()"><div><a  href="javascript:void(0);">下架</a></div></li>
						<li onclick="huifu()"><div><a  href="javascript:void(0);" >出售</a></div></li>
						</ul>
						</div>
						</div>
						<div style="display:none;">
						<input type="submit" value="提交"/>
						<input type="text" name="chenpID" id="ID" />
						<iframe name="hidden" height="0" width="0"></iframe>
						<s:token></s:token>           	
						</div>       	
						</form>     
					</div>
				</div>
				<!--代理产品管理-->
				<div class="unagency" id="agency" style="display:none;">
					<div class="AgentProducts">
					<form method="post" id="cpform">
						<c:forEach var="s" items="${dailibeans}" varStatus="idc">
						<div class="agent_block"  id="${s[0]}">
							<div class="all_width">
								<div class="agent_title">
									<ul> 
										<li style="padding-left:10px;">${s[5]}</li>
									</ul>
								</div>
								<div class="agent_pro">
									<ul>
										<li><input type="checkbox" class="chebox"/></li>
										<li class="agent_img"><img src="${s[6]}"  width="60px" height="80px;"/></li>
										<li style="width:40%;">
											<div>
												<ul>
													<li>${s[2]}</li>
													<li class="colorf00">￥${s[3]}</li>
													<li>总数量：${s[7]}</li>
												</ul>
											</div>
										</li>
										<li class="agent_yong colorf00">代理费用:${s[4]}</li>
									</ul>
								</div>
							</div>
						</div>
					</c:forEach>
					
					<div style="display: none;">
						<input type="submit" value="提交"/>
						<input type="text" name="chenpID" id="chenpID" />
						<iframe name="hidden" height="0" width="0"></iframe>
						<s:token></s:token>           	
					</div>
					</form>
						<div class="pro_bottom">
							<div >
								<ul>
									<li style ="margin-bottom:20px;" onclick="quxiao()"><div>取消代理</div></li>                  
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</body>
	</html>