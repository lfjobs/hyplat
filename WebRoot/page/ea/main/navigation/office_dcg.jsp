<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>办公室督查-集团</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>

		<script type="text/javascript">
   
             var basePath='<%=basePath%>';  
             
             $(document).ready(function(){
            	 
            	 $("#navigation").treeview();

            	});
  
        </script>
		<style type="text/css">


#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}


</style>


	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
				<td id="qh_sw" style="width: 15%" valign="top">
					<div class="qh_gg_nav">
						&nbsp; <span id="frametitle">督查(审核)管理集团汇总</span>

					</div> <!--左边的树 -->

					<ul id="navigation" style="width: 180px;" class="filetree">
						<li><span class="folder">督查(审核)管理</span>

							<ul>
								<li><span class="folder">人事督查审核管理</span>
									<ul>
										<li><a target="mainframe" href="<%=basePath%>/ea/humanExamine/ea_getHumanList.jspa"><span class="file">人事督查管理</span> </a>
										</li>
										<li><a target="mainframe"  href="<%=basePath%>/ea/publicreceipts/ea_getListPublicreceipts.jspa?labelTag=00"><span class="file">主管审核管理</span> </a>
										</li>
										<li><a target="mainframe"  href="<%=basePath%>/ea/publicreceipts/ea_getListPublicreceipts.jspa?labelTag=01"><span class="file">人事审核管理</span> </a>
										</li>
									</ul>
								</li>
								<li><span class="folder">办公室督查审核管理</span>
									<ul>
										<li><a target="mainframe"  href="<%=basePath%>/ea/responsible/ea_getResponsibleList.jspa"><span class="file">办公室督查管理</span>
										</a>
										</li>

									</ul></li>

								<li><span class="folder">财务督查审核管理</span>
									<ul>
										<li><a  target="mainframe"  href="<%=basePath%>/ea/accountant/ea_getAccountantList.jspa" ><span
												class="file">会计审核管理</span>
										</a>
										</li>
										<li><a target="mainframe" href="<%=basePath%>ea/cashier/ea_getCashierList.jspa"><span class="file">出纳审核管理</span> </a>
										</li>
									</ul>
								</li>
								<li><span class="folder">生产督查审核管理</span>
									<ul>
										<li><a target="mainframe"  ><span
												class="file">生产督查管理</span>
										</a>
										</li>

									</ul>
								</li>
								<li><span class="folder">营销督查审核管理</span>
									<ul>
										<li><a target="mainframe"  href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa" ><span
												class="file">营销督查管理</span>
										</a>
										</li>

									</ul>
								</li>

							</ul>
						</li>
					</ul>
				</td>
				<td style="width: 85%;" valign="top"><iframe id="mainframe" name="mainframe"
						    src=""
							frameborder="0" style="width: 100%;"></iframe></td>

			</tr>
			</table>
		</div>
		<script type="text/javascript">
			$(function() {
				$(window).resize(function() {
					setTimeout(function() {
						$("#navigation").height($(window).height() - 30);
						// $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
					}, 100);
				});
				$("#navigation").height($(window).height() - 30);
				$("#mainframe").css({
					"height" : $(window).height() + "px"
				});
			});
		</script>
	</body>


</html>
