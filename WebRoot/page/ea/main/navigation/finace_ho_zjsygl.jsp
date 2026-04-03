<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人资金使用管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
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
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script src="<%=basePath%>/js/ea/finance/Navigation/finace_na.js"></script>

		<script type="text/javascript">
             var basePath='<%=basePath%>'; 
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
            	 						$("#mainframe").attr(
            	 								"src",
            	 								basePath + "/page/ea/main/navigation/finace_n.jsp");
            	 						
            	 		}
            	 	}
        </script>
		<style type="text/css">
#doctree {
	position: absoute;
	padding-top: 0cm;
	margin-top: 0px;
	width: 100%;
	background-color: #FFFFFF;
	float: left;
}

#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}

.numcss {
	color: red;
}
</style>
	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
					<td id="qh_sw" style="width: 15%" valign="top">
						<div class="qh_gg_nav">
							&nbsp;
							<span id="frametitle">资金使用管理</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 200px;"
							class="filetree">
							<li>
								<span class="folder" id="zjsygl">资金使用管理</span>
								    <ul id="zzgldd">
								    <li>
										<span class="folder" id="pzgl">凭证管理</span></a>
										<ul>
										<li>
											<span class="folder" id="szpz">收支凭证</span>
											<ul>
												<li>
													<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="szpzs">收支凭证</span></a>
												</li>
												<li>
													<a href="<%=basePath%>/ea/splitgoodsbill/ea_SsearchGood.jspa?zz=09&level=staff&sztype=s" target="mainframe">
													<span class="file" id="szmx">收支明细</span></a>
												</li>
												<li>
													<a href="javascript:void(0);" onclick="baobiao('1')">
													<span class="file" id="szye">收支余额</span></a>
												</li>
												<li>
													<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="szys">收支预算完成率</span></a>
												</li>
												<li>
													<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="sztz">收支调整完成率</span></a>
												</li>
											</ul>
										</li>
										<li>
										<span class="folder" id="srpz">收入凭证</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="srpzs">收入凭证</span></a>
											</li>
											<li>
												<a href="<%=basePath%>/ea/splitgoodsbill/ea_SsearchGood.jspa?zz=07&level=staff&sztype=s" target="mainframe">
												<span class="file" id="srmx">收入明细</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" onclick="baobiao('2')">
												<span class="file" id="srye">收入余额</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="srys">收入预算完成率</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="srtz">收入调整完成率</span></a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="zcpz">支出凭证</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="zcpzs">支出凭证</span></a>
											</li>
											<li>
												<a href="<%=basePath%>/ea/splitgoodsbill/ea_SsearchGood.jspa?zz=08&level=staff&sztype=s" target="mainframe">
												<span class="file" id="zcmx">支出明细</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" onclick="baobiao('3')">
												<span class="file" id="zcye">支出余额</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="zcys">支出预算完成率</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="zctz">支出调整完成率</span></a>
											</li>
										</ul>
									</li>
									</ul>
									</li>
									<li>
										<span class="folder" id="xjgl">现金管理</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xjrjz">现金日记账</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xjsrgl">现金收入</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xjzcgl">现金支出</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" onclick="panduan()">
												<span class="file" id="zzgl">总账</span></a>
											</li>
											<li>
												<a href="<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp" target="mainframe">
												<span class="file" id="kjkmgl">会计科目</span></a>
											</li>
											<li>
												<a href="<%=basePath%>/page/ea/main/navigation/finace_gr.jsp" target="mainframe">
												<span class="file" id="pjgl">票据</span></a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="yhzgl">银行帐管理</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="yhriz">银行日记帐</span>
												</a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="yhzsr">银行账收入</span>
												</a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="yhzzc">银行账支出</span>
												</a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="yhye">银行余额</span>
												</a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="yhjymx">银行交易明细</span>
												</a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
													<span class="file" id="yhzhgl">银行账户管理</span>
												</a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="kczgl">库存账管理</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xmwpcg">项目物品采购</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="yh">验货</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="rk">入库</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="ck">出库</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="kc">库存</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="chhs">存货核算</span></a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="gdzc">固定资产</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="gdzcs">固定资产</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="bs">报损</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="zczj">资产增加</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="zcjs">资产减少</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="zcbb">资产报表</span></a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="ysyf">应收应付管理</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="ys">应收</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="yf">应付</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="ys">预收</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xs">现收</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="ysmx">应收明细</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xsmx">现收明细</span></a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="gzgl">工资管理</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="ysgz">应收工资</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="yfgz">已付工资</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="gzbb">工资报表</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="gzft">工资分摊</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="jjgz">计件工资</span></a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="xsgl">销售管理</span>
										<ul>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="kh">客户</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xsdh">销售订货</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xsfh">销售发货</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xsth">销售退货</span></a>
											</li>
											<li>
												<a href="javascript:void(0);" target="mainframe">
												<span class="file" id="xsdb">销售调拨</span></a>
											</li>
										</ul>
									</li>
									</ul>
							</li>
						</ul>
					</td>
					<td style="width: 85%;" valign="top">
						<iframe id="mainframe" name="mainframe"
							frameborder="0" style="width: 100%;"></iframe>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#navigation").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height()+ "px"}); 
     });
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
	</body>


</html>
