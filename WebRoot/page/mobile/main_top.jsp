<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>移动平台登陆</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/mobile/mobilecss.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript">
			var eaIDS= new Array(10);;
			var menuObj=new Object();
			$(document).ready(function(){
				$("#loginOut").click(function(){
					parent.location.href="<%=basePath%>ea/login/ea_logOut.jspa";
				});
				var num=0;
				$(".ahrefeaid").click(function(){
					var eaid=$(this).attr("eaid");
					for(z=0;z < num;z++){
						if(eaIDS[z] == eaid&&""!==eaIDS[z]){
							self.parent.frames["bodyFrame"].location="<%=basePath%>/page/ea/mobile_menu.jsp?eaid="+eaid+"&date="+new Date().toLocaleString();
							return;		      
						}    
					}
					num++;
					var eaname=$(this).attr("eaname");
					var url1 ="<%=basePath%>ea/ajax_login_ea_generateMenu.jspa?eaID="+eaid+"&date="+new Date().toLocaleString(); 
					$.ajax({
						url: encodeURI(url1),
						type: "get",
						async: false,
						dataType:"json",
						success: function(data) {
							menuObj[eaid]=data;
							self.parent.frames["bodyFrame"].location="<%=basePath%>/page/ea/mobile_menu.jsp?eaid="+eaid+"&date="+new Date().toLocaleString();
						},
						error:function(){
							alert("无法取得信息")
						}
					});
				});
			})
		</script>
	</head>

	<body bgcolor="#ffffff">
		<table width="320px;" height="26" align="left" cellspacing="0"
			cellpadding="0" style="font-size: 12px;" class="bannb_01">
			<tr>
				<td height="24" align="left" valign="top" class="txt01">
					<div class="daohang">
						<ul>
							<s:iterator value="seaList">
								<li>
									<a href="#" eaid="<s:property value="eaID"/>"
										eaName="<s:property value="eaName"/>" class="ahrefeaid"><s:property
											value="eaName" /> </a>
								</li>
								<li class="li_zj"></li>
							</s:iterator>
							<li>
								<a id="loginOut" href="#" style="color: #FFFFFF;">返回登录</a>
							</li>
							<li class="li_zj"></li>
							<li>
								<a style="color: #fffebb; font-weight: bold;"> 欢迎你
									${sessionScope.account.accountName}</a>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
