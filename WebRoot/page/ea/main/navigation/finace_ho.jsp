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
<title>个人部门财务管理</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
<style>
table tr td {
	height: 50px;
}
</style>
<script>
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
			window.location.href = action + treeID;
			return;
		}
		window.location.href = action;
	}
	
function panduan(){
var b=true;
	 var url1="<%=basePath%>/ea/csbjects/sajax_ea_ajaxStartTime.jspa?date="+new Date().toLocaleString();
	               $.ajax({
	                        url: encodeURI(url1),
	                        type: "get",
	                        async: false,
	                        dataType: "json",
	                        success: function cbf(data){
				              var member = eval("(" + data + ")");
				              var nologin = member.nologin;
				              if(nologin){
				                  document.location.href =basePath+"page/ea/not_login.jsp";
									}
									var c = member.count;
									if (c == 0) {
										alert("没有财务初始化，总账管理模块功能未开启");
										b = false;
									}
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
							});
					if(b){
						document.location.href='<%=basePath%>/page/ea/main/navigation/finace_n.jsp';
		}
	}
	function baobiao(i){
		var urlaa='';
		if(i=='1'){
			urlaa='<%=basePath%>/ea/splitbill/ea_toSprins.jspa?level=staff&zz=06';
		}else if(i=='2'){
			urlaa='<%=basePath%>/ea/splitbill/ea_toSprins.jspa?level=staff&zz=07';
		}else if(i=='3'){
			urlaa='<%=basePath%>/ea/splitbill/ea_toSprins.jspa?level=staff&zz=07';
		}
			window.open(urlaa);
		
	}
</script>
</head>
<body>
	<br />
	<table width="90%" cellspacing="0" cellpadding="5" align="center">
		<tr>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>财务数据设置</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td><div class="na_back_img"></div>
				<div>物品管理</div></td>
			<td><div class="na_back_img"></div>
				<div>仓库设置</div></td>
			<td><div class="na_back_img"></div>
				<div>往来单位</div></td>
			<td><div class="na_back_img"></div>
				<div>往来个人</div></td>
			<td><div class="na_back_img"></div>
				<div>期初设置</div></td>
		</tr>
		<tr>
			<td rowspan="2"><div align="center"></div>
				<img src="<%=basePath%>images/sytemicon/02.png"></img>
			<div>
					<strong>预算招标管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/shouru.png"></img>
				</div>
				<div class="center_a"> 项目预算招标录入</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/weiboshenqing.png"></img>
				</div>
				<div class="center_a">招标已申请未申请</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/r14_c7.gif"></img>
				</div>
				<div class="center_a">招标比价管理</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/changgui.png"></img>
				</div>
				<div class="center_a">常规标准运算</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/r17_c5.gif"></img>
				</div>
				<div class="center_a">项目已审批未审批</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/r14_c5.gif"></img>
				</div>
				<div class="center_a">项目物品费用未申请</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/r17_c7.gif"></img>
				</div>
				<div class="center_a">项目物品费用已审批</div></td>
		</tr>
		<tr>
			<td><div align="center"></div>
				<img src="<%=basePath%>images/sytemicon/01.png"></img>
				<div>
					<strong>资金申请管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/xianjinshenzhuan.png"></img>
				</div>
				<div class="center_a">现金申转对账管理</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/weiboshenqing.png"></img>
				</div>
				<div class="center_a">未拨现金申请</div></td>
			<td><div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/yiboshengqing.png"></img>
				</div>
				<div class="center_a">已拨现金申请</div></td>
		</tr>
		<tr>
			<td rowspan="10"><div align="center"></div>
				<img src="<%=basePath%>images/sytemicon/xianjinshiyongguanli.png"></img>

				<div class="center">
					<strong>资金使用管理</strong>
				</div></td>
			<td rowspan="3"><div class="na_back_img_jt_xs"></div></td>
			<td rowspan="3"><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>凭证管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_img"></div>
				<div class="center">收支凭证管理</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/splitgoodsbill/ea_SsearchGood.jspa?zz=09&level=staff&sztype=s'"></div>
				<div class="center">收支明细管理</div></td>
			<td>
				<div onclick="baobiao('1')">
					<img src="<%=basePath%>/images/sytemicon/r1_c9.gif"></img>
				</div>
				<div class="center_a" >收支余额管理</div>
			</td>
			<td><div class="na_back_img"></div>
				<div class="center">收支预算完成率</div></td>
			<td><div class="na_back_img"></div>
				<div class="center">收支调整完成率</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td>
				<div>
					<img src="<%=basePath%>/images/sytemicon/r1_c5.gif"></img>
				</div>
				<div class="center_a">收入凭证管理</div>
			</td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/splitgoodsbill/ea_SsearchGood.jspa?zz=07&level=staff&sztype=s'"></div>
				<div class="center">收入明细管理</div></td>
			<td><div class="na_back_img" onclick="baobiao('2')"></div>
				<div class="center">收入余额管理</div></td>
			<td>
				<div>
					<img src="<%=basePath%>/images/sytemicon/r1_c9.gif"></img>
				</div>
				<div class="center_a">收入预算完成率</div>
			</td>
			<td><div>
					<img src="<%=basePath%>/images/sytemicon/r1_c9.gif"></img>
				</div>
				<div class="center_a">收入调整完成率</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td>
				<div>
					<img src="<%=basePath%>/images/sytemicon/r1_c7.gif"></img>
				</div>
				<div class="center_a">支出凭证管理</div>
			</td>

			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/splitgoodsbill/ea_SsearchGood.jspa?zz=08&level=staff&sztype=s'"></div>
				<div class="center">支出明细管理</div></td>
			<td><div class="na_back_img" onclick="baobiao('3')"></div>
				<div class="center">支出余额管理</div></td>
			<td >
                 <div>
				<img src="<%=basePath%>/images/sytemicon/r1_c9.gif"></img>
				</div>
                <div class="center_a">支出预算完成率</div></td>
            <td >
                 <div>
				<img src="<%=basePath%>/images/sytemicon/r1_c9.gif"></img>
				</div>
                <div class="center_a">支出调整完成率</div></td>  
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div align="center"></div>
				<img src="<%=basePath%>images/sytemicon/xianjinshiyongguanli.png"></img>
				<div>
					<strong>现金管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/r1_c3.gif"></img>
				</div>
				<div class="center_a">现金日记账</div>
			</td>
			<td>
				<div class="na_back_dh">
					<img src="<%=basePath%>images/sytemicon/r1_c5.gif"></img>
				</div>
				<div class="center_a">现金收入管理</div>
			</td>
			<td>
				<div class="na_back_dh">
					<img src="<%=basePath%>images/sytemicon/r1_c7.gif"></img>
				</div>
				<div class="center_a">现金支出管理</div>
			</td>
			<td>
				<div class="na_back_dh" onclick="panduan()" align="center">
					<img src="<%=basePath%>images/sytemicon/zongzhangguanli.png"></img>
				</div>
				<div class="center_a">总账管理</div>
			</td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp'"
					align="center">
					<img src="<%=basePath%>images/sytemicon/kuaijikemuguanli.png"></img>
				</div>
				<div class="center_a">会计科目管理</div>
			</td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_gr.jsp'"
					align="center">
					<img src="<%=basePath%>images/sytemicon/05.png"></img>
				</div>
				<div class="center_a">票据管理</div>
			</td>
			<%-- <td>
										<div class="na_back_dh" align="center">
										<img src="<%=basePath%>images/sytemicon/r1_c9.gif"></img>
										</div>
										<div class="center">现金余额</div></td>
									<td >
										<div class="na_back_dh"
											onclick="document.location.href='<%=basePath%>/ea/statement/ea_toPage.jspa?zz=04'">
											<img src="<%=basePath%>images/sytemicon/r1_c11.gif"></img>
											</div>
										<div class="center">应收账款</div></td>
									<td >
										<div class="na_back_dh"
											onclick="document.location.href='<%=basePath%>/ea/statement/ea_toPage.jspa?zz=05'">
											<img src="<%=basePath%>images/sytemicon/r1_c13.gif"></img>
											</div>
										<div class="center">应付账款</div></td>  --%>
		</tr>
		<tr>
			<td rowspan="2"><div class="na_back_img_jt_hx"></div></td>
			<td><div align="center"></div>
				<img src="<%=basePath%>images/sytemicon/04.png"></img>
				<div class="center">
					<strong>银行帐管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td>
				<div class="na_back_dh"align="center">
					<img src="<%=basePath%>images/sytemicon/r3_c3.gif"></img>
				</div>

				<div class="center_a">银行日记账</div>
			</td>
			<td>
				<div align="center" class="na_back_dh">
					<img src="<%=basePath%>images/sytemicon/r3_c5.gif"></img>
				</div>

				<div class="center_a">银行账收入管理</div>
			</td>
			<td>
				<div align="center" class="na_back_dh">
					<img src="<%=basePath%>images/sytemicon/r3_c7.gif"></img>
				</div>
				<div class="center_a">银行账支出管理</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/r3_c9.gif"></img>
				</div>
				<div class="center_a">银行余额</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/yinhangjiaoyi.png"></img>
				</div>
				<div class="center_a">银行交易明细</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/yinhangzhanghuguanli.png"></img>
				</div>
				<div class="center_a">银行账户管理</div>
			</td>
		</tr>
		<tr>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>库存账管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/caigouguanli.png"></img>
				</div>
				<div class="center_a">项目物品采购管理</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/yanhuoguanli.png"></img>
				</div>
				<div class="center_a">验货管理</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/rukuguanli.png"></img>
				</div>
				<div class="center_a">入库管理</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/chukuguanli.png"></img>
				</div>
				<div class="center_a">出库管理</div>
			</td>
			<td>
				<div class="na_back_dh" align="center">
					<img src="<%=basePath%>images/sytemicon/kucunguanli.png"></img>
				</div>
				<div class="center_a">库存管理</div>
			</td>
			<td><div class="na_back_img"></div>
				<div>存货核算</div></td>

		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>固定资产</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><div class="na_back_img"></div>
				<div align="center">固定资产</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">报损管理</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">资产增加</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">资产减少</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">资产报表</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>应收应付管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><div class="na_back_img"></div>
				<div align="center">应收管理</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">应付管理</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">预收管理</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">现收管理</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">应收明细</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">应付明细</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>工资管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><div class="na_back_img"></div>
				<div align="center">应付工资</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">已付工资</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">工资报表</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">工资分摊</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">计件工资</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img_ks"></div>
				<div align="center">
					<strong>销售管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><div class="na_back_img"></div>
				<div align="center">客户管理</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">销售订货</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">销售发货</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">销售退货</div></td>
			<td><div class="na_back_img"></div>
				<div align="center">销售调拨</div></td>
		</tr>
	</table>
</body>
</html>
