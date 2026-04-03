<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息管理</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>

<script  src="<%=basePath%>js/ea/office_ea/document/documentFrame.js"></script>



<script type="text/javascript">
    var module = 'AnnNoti'; 
             var journalNum = "";
             var projectName = "";
   
             var basePath='<%=basePath%>';  
        </script>
	</head>
	<body>
	
	
	
	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
		<tr>

			<td id="qh_sw" style="width: 15%;margin-left: 5px;" valign="top">
				<div style="width: 100%;" class="qh_gg_nav">&nbsp;信息管理</div> <!--左边的树 -->

				<ul id="tree" class="filetree" style="width:100%;">
					
						<ul>
							<li><span class="folder">网络管理</span>
								<ul>
									<li id="rs"><span class="file"><a  >(01)软件系统管理</a> </span>
										
									</li>
									<li><span class="file" id="bg"><a
											href="<%=basePath%>ea/addressip/ea_getListIPaddress.jspa?" target="admin1">(02)IP地址管理</a> </span>
									
									</li>

									<li state="closed" id="cw"><span class="file" id="cw"><a
											href="<%=basePath%>ea/telmessage/ea_goMessageIndex.jspa?type=1&orgDetail=office"  target="admin1">(03)短信管理</a> </span>
									</li>
									<li><span class="folder" id="sc"><a
											>(04)微信管理</a> </span>
										
									</li>
									<li state="closed" id="cw"><span class="file" id="cw"><a
											href="<%=basePath%>ea/qq/ea_getQqList.jspa?" target="admin1">(05)QQ管理</a> </span>
									</li>
									<li state="closed" id="cw"><span class="file" id="cw"><a
											href="<%=basePath%>ea/netdisk/ea_getNetDiskList.jspa?" target="admin1">(06)网络硬盘管理</a> </span>
									</li>
									<li state="closed" id="cw"><span class="file" id="cw"><a
											href="<%=basePath%>ea/domainname/ea_getListDomainname.jspa?" target="admin1">(07)域名管理</a> </span>
									</li>
									<li state="closed" id="cw"><span class="file" id="cw"><a
											href="<%=basePath%>ea/networkfax/ea_getListNetworkFax.jspa?" target="admin1">(08)网络传真管理</a> </span>
									</li>
									<li state="closed" id="cw"><span class="file" id="cw"><a
											href="<%=basePath%>/page/ea/main/telrec/telMain.jsp" target="admin1">(09)呼叫中心管理</a> </span>
									</li>	
								</ul>
								</li>
							</ul>
							</li>
							<li><span class="folder" id="bbb">公共信息管理</span>
								<ul>
									<li><span class="folder" id="unex"><a target="admin1"
											href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=AnnNoti&d=<%=Math.random()%>'">(01)公告通知管理</a> </span>
								    </li>

											
									</li>
									<%--<li><span class="folder"><a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=news&d=<%=Math.random()%>"
											 target="admin1">(02)新闻管理</a> </span>
									</li>--%>
									<li><span class="folder"><a href="<%=basePath%>/ea/activity/ea_activityList.jspa?inforType=01"
											 target="admin1">(02)公共传媒</a> </span>
									</li>
									<li><span class="file" id="unex"><a
											>(03)投票管理</a> </span>
										
									</li>
									<li><span class="file"><a
											href="<%=basePath%>ea/telephone/ea_getaTelephoneList.jspa?" target="admin1">(04)公共电话簿</a> </span>
									</li>
									<li><span class="folder" id="unex"><a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=bulletin&d=<%=Math.random()%>"
											 target="admin1">(05)简报管理</a> </span>
											
												
						
						            </li>
										
									</li>
									<li><span class="file"><a
											>(06)数据提醒管理</a> </span>
									</li>
									<li><span class="file" id="unex"><a
											href="<%=basePath%>ea/schedule/ea_getScheduleList.jspa?" target="admin1">(07)公共日程管理</a> </span>
										
									</li>
									<li><span class="file"><a
											>(08)公共日志管理</a> </span>
									</li>
								</ul>
							</li>
							<li><span class="folder">企业信息管理</span>
								<ul>
									<li><a><span class="file">企业信息管理</span>
									</a></li>
									<li><a><span class="file">企业信息推广</span>
									</a></li>
								</ul>
							</li>
							<li><span class="folder">网络安全管理</span>
								<ul>
									<li><a  href="<%=basePath%>ea/network/ea_getNetWork.jspa?" target="admin1"><span class="file">(01)网络加密管理</span>
									</a></li>
									<li><a  href="<%=basePath%>ea/netWorkAntiVirus/ea_getNetWorkAntiVirusList.jspa?" target="admin1"><span class="file">(02)网络杀毒管理</span>
									</a></li>
									<li><a><span class="file" >(03)网络建设1</span>
									</a></li>
									<li><a><span class="file" >(04)带宽建设2</span>
									</a></li>
									<li><a><span class="file" >(05)网络安全建设3</span>
									</a></li>
									<li><a><span class="file" >(06)服务器建设4</span>
									</a></li>
									<li><a><span class="file" >(07)终端建设</span>
									</a></li>
								</ul>
							</li>
							<li><span class="folder">消息管理</span>
								<ul>
									<li><a target="admin1" href='<%=basePath%>ea/remind/ea_getListRemind.jspa'><span class="file">消息提醒</span></a></li>
								</ul>
							</li>
					</li>
				</ul></td>
			<td style="width: 85%;" valign="top"><iframe id="mainframe1" src="<%=basePath%>ea/logo/ea_getEnterpriseLogoList.jspa?" 
					style="margin:0px;width:100%;height:550px;" name="admin1" 
					frameBorder="0"></iframe>
			</td>
		</tr>
	</table>

	<script type="text/javascript">
		$(function() {
			$("#tree").treeview({
				  persist: "location",
				  collapsed: true,
				  unique: true
			});
			$(window).resize(function() {
				setTimeout(function() {
					$("#aadTree").height($(window).height() - 30);
					$("#mainframe1").css({
						"height" : $(window).height() - 5 + "px"
					});
				}, 100);
			});
			$("#aadTree").height($(window).height() - 30);
			$("#mainframe1").css({
				"height" : $(window).height() - 5 + "px"
			});
		});
	</script>
	
	
	</body>
</html>