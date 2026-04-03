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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人客户咨询建档</title>
<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css"
	type="text/css"></link>
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>	
<style>
div {
	width: 110px;
}

.colors {
	border-bottom: 1px dashed #FF0000;
}
</style>
</head>
<body>
	<div align="left">
		<table>
			<tr>
				<td>
					<div class="na_back_img_ks"></div>
					<div class="center_a">
						<strong>个人客户咨询建档</strong>
					</div></td>
				<td><div class="na_back_img_jt_hx"></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUserConsult.jspa?module_Identifier=customer_Consultation'"></div>	
					<div class="center_a">个人客户基本信息<br /></div></td>
				<td>
				<div class="na_back_img"
						onclick="document.location.href='<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?identifier=identifier'"></div>
				<div class="center_a">产品销售报表<br /></div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="na_back_img_ks"></div>
					<div class="center_a">
						<strong>潜在客户需求报表</strong>
					</div></td>
				<td><div class="na_back_img_jt_hx"></div></td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000030'"
						></div>	
					<div class="center_a">潜在客户</div></td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000031'"
						></div>
					<div class="center_a">优质客户</div></td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000032'"
						></div>
					<div class="center_a">本日可成交客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000033'"
						></div>
					<div class="center_a">俩日可成交客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000034'"
						></div>
					<div class="center_a">本周可成交客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000035'"
						></div>
					<div class="center_a">本月可成交客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000036'"
						></div>
					<div class="center_a">本季可成交客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000037'"
						></div>
					<div class="center_a">本年可成交客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000038'"
						></div>
					<div class="center_a">大客户</div></td>
					<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000039'"
						></div>
					<div class="center_a">渠道客户</div></td>			
			</tr>
			<tr>
				<td rowspan="2">
					<div class="na_back_img_ks"></div>
					<div class="center_a">
						<strong>客户来源管理报表</strong>
					</div></td>
					
				<td><div class="na_back_img_jt_xs"></div></td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000041'"
						></div>	
					<div class="center_a">400电话</div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000042'"
						></div>
					<div class="center_a">短信</div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000043'"
						></div>
					<div class="center_a">网络渠道</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000048'"
						></div>
					<div class="center_a">代理商</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000049'"
						></div>
					<div class="center_a">一般中介</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000050'"
						></div>
					<div class="center_a">户外广告</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000051'"
						></div>
					<div class="center_a">媒体</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000052'"
						></div>
					<div class="center_a">电视广告</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000053'"
						></div>
					<div class="center_a">报纸广告</div></td>		
			</tr>
			<tr>
				<td><div class="na_back_img_jt_xx"></div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000054'"
						></div>	
					<div class="center_a">直电</div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000055'"
						></div>
					<div class="center_a">中心活动</div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000056'"
						></div>
					<div class="center_a">网络注册</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000057'"
						></div>
					<div class="center_a">网络客户</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000058'"
						></div>
					<div class="center_a">总部活动</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000059'"
						></div>
					<div class="center_a">老学员推荐</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000060'"
						></div>
					<div class="center_a">外教推荐</div></td>
					<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000061'"
						></div>
					<div class="center_a">课程顾问自传</div></td>	
			</tr>
			<tr>
				<td>
					<div class="na_back_img_ks"></div>
					<div class="center_a">
						<strong>客户产品兴趣报表</strong>
					</div></td>
				<td><div class="na_back_img_jt_hx"></div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=00'"
						></div>	
					<div class="center_a">有兴趣</div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=02'"
						></div>
					<div class="center_a">一般有兴趣</div></td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=01'"
						></div>
					<div class="center_a">特别有兴趣</div></td>		
			</tr>
		</table>
	</div>
</body>
</html>