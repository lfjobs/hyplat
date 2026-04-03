<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>财务报表</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
    type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
        <script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<script type="text/javascript">
function baobiao(i){
		var urlaa='';
		if(i=='1'){
			urlaa='<%=basePath%>/page/ea/main/finance/production/Profit.jsp';
		}else if(i=='2'){
			urlaa='<%=basePath%>/page/ea/main/finance/production/cashFlow.jsp';
		}else if(i=='3'){
			urlaa='<%=basePath%>/page/ea/main/finance/production/AssetsLiabilities.jsp';
		}
			window.open(urlaa);
	}
</script>
</head>
<body>
    <br />
    <table style="width:100%;border:0; padding: 0;margin:0">
		<tr>
			<td>
				<!--报表管理-->
				<table>
					<tr>
						<td >
							<div align="center"></div><img src="<%=basePath%>images/sytemicon/06.png"></img>
							<div class="center_a">
								<strong>报表管理</strong>
							</div></td>
						<td >
							<div class="na_back_img_jt_hx"></div></td>
						<td><table >
                                    <tr>
                                    <td>
                                        <div  class="na_back_dh" align="center" onclick="baobiao('1')"><img src="<%=basePath%>images/sytemicon/zongzhangguanli.png"></img></div>
                                        <div class="center_a">利润表</div>
                                    </td>
                                        <td>
                                        <div class="na_back_dh" align="center" onclick="baobiao('2')"><img src="<%=basePath%>images/sytemicon/xianjinyianhangliushuizhang.png"></img></div>
                                        <div class="center_a">现金流量表</div></td>
                                    <td >
                                        <div class="na_back_dh" align="center" onclick="baobiao('3')"><img src="<%=basePath%>images/sytemicon/yinhangzhichuguanli.png"></img></div>
                                        <div class="center_a">资产负债表</div></td>
                                    </tr>
                                </table></td>
					</tr>
				</table>
			</td>
		</tr>
    </table>
</body>
</html>
