<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title>集团财务--财务树</title>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script src="<%=basePath%>js/ea/finance/invoicing/left_frame.js"></script>
<script type="text/javascript">
		 var basePath="<%=basePath%>";
	var jumptype = "${param.jumptype}";
	var type = "${param.type}";
</script>

<style type="text/css">
#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}
</style>
</head>
<body>
		<table style="width:100%;" border="0" cellspacing="0"
			cellpadding="0">
			<tr>

				<td id="qh_sw" style="width: 20%;margin-left: 5px;" valign="top">
					<div style="width: 100%;" class="qh_gg_nav">
					&nbsp;项目树管理
					</div> 
					<!--左边的树 --> 
					
						<ul id="tree" class="easyui-tree">
							<li><span class="folder" id="tit">项目预算招标比价流程</span>
								<ul>
									<li><span class="folder expand">项目录入</span>
										<ul>
											<li state="closed" id="rs"><span
												><a href="javascript:void(0);">（01）人事项目</a></span>
												<ul>
													<li id="rs1"><span></span><a href="javascript:void(0);">组织机构规划项目</a></span></li>
													<li id="rs2"><a href="javascript:void(0);"><span class="file">招聘规划项目</span> </a>
													</li>
													<li id="rs3"><a href="javascript:void(0);"><span class="file">在职员工项目</span> </a>
													</li>

													<li id="rs4"><a href="javascript:void(0);"><span>离职员工项目</span> </a>
													</li>
													<li id="rs5"><a href="javascript:void(0);"><span class="file">社会人力资源项目</span>
													</a></li>

												</ul></li>
											<li state="closed"><span class="folder" id="bg"><a href="javascript:void(0);">（02）办公室项目</a></span>
												<ul>
													<li id="bg1"><a href="javascript:void(0);"><span>公司规划项目</span>
													</a></li>
													<li id="bg2"><a href="javascript:void(0);"><span>行政办公项目</span>
													</a></li>
													<li id="bg3"><a href="javascript:void(0);"><span>信息管理项目</span>
															</span> </a></li>
													<li state="closed" id="bg4"><span 
														><a href="javascript:void(0);">后勤管理项目</a></span>

														<ul>
															<li id="bg401"><a href="javascript:void(0);"><span class="file" id="unex">接待管理</span>
															</a></li>
															<li id="bg402"><a href="javascript:void(0);"><span class="file" id="unex">场地管理</span>
															</a></li>
															<li id="bg403"><a href="javascript:void(0);"><span class="file" id="unex">资产库管</span>
															</a></li>
															<li id="bg404"><a href="javascript:void(0);"><span class="file" id="unex">安全管理</span>
															</a></li>
															<li state="closed" id="bg405"><span class="folder">
															<a href="javascript:void(0);">设备管理</a></span>

																<ul>
																	<li state="closed" id="bg4051"><span class="folder" ><a href="javascript:void(0);">
																		车管设备</a></span>
																		<ul>
																			<li id="bg40511"><span class="file"
																					><a href="javascript:void(0);">汽车	</a></span>
																		
																			</li>

																		</ul></li>
																	<li state="closed"  id="bg4052"><span class="folder" id="unex"><a href="javascript:void(0);">工程机械</a></span>
																		<ul>
																			<li id="bg40521"><span class="file"
																					><a href="javascript:void(0);">挖掘机</a></span>
																			
																			</li>
																			<li id="bg40522"><span class="file"
																					><a href="javascript:void(0);">装载机</a></span>
																			
																			</li>
																		</ul></li>
																	<li state="closed" id="bg4053"><span
																		class="folder" ><a href="javascript:void(0);">办公设备</a></span>

																		<ul>
																			<li id="bg40531"><span class="file"
																					id="unex"><a href="javascript:void(0);">电脑</a></span>
																		
																			</li>
																			<li id="bg40532"><span class="file"
																					id="unex"><a href="javascript:void(0);">打印机</a></span>
																			
																			</li>
																			<li id="bg40533"><span class="file"
																					id="unex"><a href="javascript:void(0);">通讯设备</a></span>
																			
																			</li>
																		</ul></li>
																	<li id="bg4054"><span class="file"
																			id="unex"><a href="javascript:void(0);">弱电设备</a></span> </li>


																</ul>
															</li>
															<li id="bg406"><a href="javascript:void(0);"><span class="file" id="unex">物流管理</span>
															</a></li>
															<li id="bg407"><a href="javascript:void(0);"><span class="file" id="unex">用水管理</span>
															</a></li>
															<li id="bg408"><a href="javascript:void(0);"><span class="file" id="unex">用电管理</span>
															</a></li>
															<li id="bg409"><a href="javascript:void(0);"><span class="file" id="unex">值班管理</span>
															</a></li>
															<li id="bg410"><a href="javascript:void(0);"><span class="file" id="unex">绿化管理</span>
															</a></li>
															<li id="bg411"><a href="javascript:void(0);"><span class="file" id="unex">食管管理</span>
															</a></li>
															<li id="bg412"><a href="javascript:void(0);"><span class="file" id="unex">住宿管理</span>
															</a></li>
															<li id="bg413"><a href="javascript:void(0);"><span class="file" id="unex">资产库存管理</span>
															</a></li>
															<li id="bg414"><a href="javascript:void(0);"><span class="file" id="unex">物品物料管理</span>
															</a></li>

														</ul>
													</li>
													<li id="bg5"><a href="javascript:void(0);">督察管理项目
															</a></li>

												</ul></li>

											<li state="closed" id="cw"><span class="folder" id="cw"><a href="javascript:void(0);">（03）财务项目</a></span>
												<ul>
													<li id="cw1"><a href="javascript:void(0);"><span >项目预算招标项目</span>
													</a></li>
													<li id="cw2"><a href="javascript:void(0);"><span>资金申请项目</span>
													</a></li>
													<li id="cw3"><a href="javascript:void(0);"><span>资金使用项目</span>
													</a></li>
													<li id="cw4"><a href="javascript:void(0);"><span>凭证管理项目</span>
													</a></li>
													<li id="cw5"><a href="javascript:void(0);"><span>总账管理项目
														</span> </a></li>

												</ul>
											</li>
											<li state="closed"><span class="folder" id="sc"><a href="javascript:void(0);">（04）生产项目</a></span>
												<ul>
													<li id="sc1"><a href="javascript:void(0);"><span >研发（教研）项目</span>
													</a></li>
													<li id="sc2"><a href="javascript:void(0);"><span >模拟项目</span>
													</a></li>
													<li id="sc3"><a href="javascript:void(0);"><span>综合办证项目</span>
													</a></li>
													<li id="sc4"><a href="javascript:void(0);"><span>生产（教务）项目</span>
													</a></li>
													<li id="sc5"><a href="javascript:void(0);"><span >检验成品（考核合格归档）项目
														</span> </a></li>

												</ul>
										   </li>
										<li state="closed" id="yx"><span><a
											href="javascript:void(0);">（05）营销项目</a></span>
										
											<ul>
												<li id="yx1"><a href="javascript:void(0);"><span>市场调查</span>
												</a></li>
												<li id="yx2"><a
													href="javascript:void(0);"><span>产品包装推广项目</span>
												</a></li>
												<li id="yx3"><a
													href="javascript:void(0);"><span>咨询收集客户项目</span>
												</a></li>
												<li id="yx4"><a
													href="javascript:void(0);"><span>成交客户项目</span>
												</a></li>
												<li id="yx5"><a
													href="javascript:void(0);"><span>售后跟踪项目</span>
												</a></li>

											</ul></li>

									</ul></li>
									<li><span class="folder" id="bbb">审核项目</span>
										<ul>
											<li state="closed"><span class="folder" id="unex"><a href="javascript:void(0);">未审核项目</a></span>
												<ul>
													<li state="closed"><span class="folder" id="rs1" ><a href="javascript:void(0);">人事项目</a></span>
														<ul>
															<li id="rs1"><a><span class="file">组织机构规划项目</span>
															</a></li>
															<li id="rs2"><a><span class="file">招聘规划项目</span>
															</a></li>
															<li id="rs3"><a><span class="file">在职员工项目</span>
															</a></li>

															<li id="rs4"><a><span class="file">离职员工项目</span>
															</a></li>
															<li id="rs5"><a><span class="file">社会人力资源项目</span>
															</a></li>

														</ul></li>
													<li state="closed"><span class="folder" id="bg">办公室项目</span>
														<ul>
															<li id="bg1"><a><span class="file" id="unex">公司规划项目</span>
															</a></li>
															<li id="bg2"><a><span class="file" id="ex">行政办公项目</span>
															</a></li>
															<li id="bg3"><a><span class="file" id="ex">信息管理项目</span>
																	</span> </a></li>
															<li state="closed" id="bg4"><span class="folder"
																id="ex">后勤管理项目</span>

																<ul>
																	<li id="bg401"><a><span class="file" id="unex">接待管理</span>
																	</a></li>
																	<li id="bg402"><a><span class="file" id="unex">场地管理</span>
																	</a></li>
																	<li id="bg403"><a><span class="file" id="unex">资产库管</span>
																	</a></li>
																	<li id="bg404"><a><span class="file" id="unex">安全管理</span>
																	</a></li>
																	<li state="closed" id="bg405"><span class="folder"
																		id="unex">设备管理</span>

																		<ul>
																			<li state="closed" id="bg4051"><span
																				class="folder" id="unex">车管设备</span>
																				<ul>
																					<li id="bg40511"><a><span class="file"
																							id="unex">汽车</span>
																					</a>
																					</li>

																				</ul></li>
																			<li state="closed" id="bg4052"><span
																				class="folder" id="unex">工程机械</span>
																				<ul>
																					<li id="bg40521"><a><span class="file"
																							id="unex">挖掘机</span>
																					</a>
																					</li>
																					<li id="bg40522"><a><span class="file"
																							id="unex">装载机</span>
																					</a>
																					</li>
																				</ul></li>
																			<li state="closed" id="bg4053"><span
																				class="folder" id="unex">办公设备</span>

																				<ul>
																					<li id="bg40531"><a><span class="file"
																							id="unex">电脑</span>
																					</a>
																					</li>
																					<li id="bg40532"><a><span class="file"
																							id="unex">打印机</span>
																					</a>
																					</li>
																					<li id="bg40533"><a><span class="file"
																							id="unex">通讯设备</span>
																					</a>
																					</li>
																				</ul></li>
																			<li id="bg4054"><a><span class="file"
																					id="unex">弱电设备</span> </a></li>


																		</ul>
																	</li>
																	<li id="bg406"><a><span class="file" id="unex">物流管理</span>
																	</a></li>
																	<li id="bg407"><a><span class="file" id="unex">用水管理</span>
																	</a></li>
																	<li id="bg408"><a><span class="file" id="unex">用电管理</span>
																	</a></li>
																	<li id="bg409"><a><span class="file" id="unex">值班管理</span>
																	</a></li>
																	<li id="bg410"><a><span class="file" id="unex">绿化管理</span>
																	</a></li>
																	<li id="bg411"><a><span class="file" id="unex">食管管理</span>
																	</a></li>
																	<li id="bg412"><a><span class="file" id="unex">住宿管理</span>
																	</a></li>
																	<li id="bg413"><a><span class="file" id="unex">资产库存管理</span>
																	</a></li>
																	<li id="bg414"><a><span class="file" id="unex">物品物料管理</span>
																	</a></li>

																</ul>
															</li>
															<li id="bg5"><a><span class="file" id="ex">督察管理项目</span>
																	</span> </a></li>

														</ul></li>

													<li state="closed"><span class="folder" id="cw">财务项目</span>
														<ul>
															<li id="sc1"><a><span class="file" id="unseal">项目预算招标项目</span>
															</a></li>
															<li id="sc2"><a><span class="file" id="seal">资金申请项目</span>
															</a></li>
															<li id="sc3"><a><span class="file" id="seal">资金使用项目</span>
															</a></li>
															<li id="sc4"><a><span class="file" id="seal">凭证管理项目</span>
															</a></li>
															<li id="sc5"><a><span class="file" id="seal">总账管理项目
																</span> </a></li>

														</ul>
													</li>
													<li state="closed"><span class="folder">生产项目</span>
														<ul>
															<li id="sc1"><a><span class="file" id="unseal">研发（教研）项目</span>
															</a></li>
															<li id="sc2"><a><span class="file" id="seal">模拟项目</span>
															</a></li>
															<li id="sc3"><a><span class="file" id="seal">综合办证项目</span>
															</a></li>
															<li id="sc4"><a><span class="file" id="seal">生产（教务）项目</span>
															</a></li>
															<li id="sc5"><a><span class="file" id="seal">检验成品（考核合格归档）项目
																</span> </a></li>

														</ul></li>
													<li state="closed"><span class="folder">营销项目</span>
														<ul>
															<li id="yx1"><a><span class="file" id="unread">市场调查</span>
															</a></li>
															<li id="yx2"><a><span class="file" id="readed">产品包装推广项目</span>
															</a></li>
															<li id="yx3"><a><span class="file" id="readed">咨询收集客户项目</span>
															</a></li>
															<li id="yx4"><a><span class="file" id="readed">成交客户项目</span>
															</a></li>
															<li id="yx5"><a><span class="file" id="readed">售后跟踪项目</span>
															</a></li>

														</ul></li>

												</ul></li>
											<li state="closed"><span class="folder" id="ex">已审核项目</span>
												<ul>
													<li state="closed"><span class="folder" id="rs1">人事项目</span>
														<ul>
															<li id="rs1"><a><span class="file">组织机构规划项目</span>
															</a></li>
															<li id="rs2"><a><span class="file">招聘规划项目</span>
															</a></li>
															<li id="rs3"><a><span class="file">在职员工项目</span>
															</a></li>

															<li id="rs4"><a><span class="file">离职员工项目</span>
															</a></li>
															<li id="rs5"><a><span class="file">社会人力资源项目</span>
															</a></li>

														</ul></li>
													<li state="closed"><span class="folder" id="bg">办公室项目</span>
														<ul>
															<li id="bg1"><a><span class="file" id="unex">公司规划项目</span>
															</a></li>
															<li id="bg2"><a><span class="file" id="ex">行政办公项目</span>
															</a></li>
															<li id="bg3"><a><span class="file" id="ex">信息管理项目</span>
																	</span> </a></li>
															<li state="closed" id="bg4"><span class="folder"
																id="ex">后勤管理项目</span>

																<ul>
																	<li id="bg401"><a><span class="file" id="unex">接待管理</span>
																	</a></li>
																	<li id="bg402"><a><span class="file" id="unex">场地管理</span>
																	</a></li>
																	<li id="bg403"><a><span class="file" id="unex">资产库管</span>
																	</a></li>
																	<li id="bg404"><a><span class="file" id="unex">安全管理</span>
																	</a></li>
																	<li state="closed" id="bg405"><span class="folder"
																		>设备管理</span>

																		<ul>
																			<li state="closed" id="bg4051"><span
																				class="folder" id="unex">车管设备</span>
																				<ul>
																					<li id="bg40511"><a><span class="file"
																							>汽车</span>
																					</a>
																					</li>

																				</ul></li>
																			<li state="closed" id="bg4052"><span
																				class="folder" >工程机械</span>
																				<ul>
																					<li id="bg40521"><a><span class="file"
																							>挖掘机</span>
																					</a>
																					</li>
																					<li id="bg40522"><a><span class="file"
																							>装载机</span>
																					</a>
																					</li>
																				</ul></li>
																			<li state="closed" id="bg4053"><span
																				class="folder" >办公设备</span>

																				<ul>
																					<li id="bg40531"><a><span class="file"
																							>电脑</span>
																					</a>
																					</li>
																					<li id="bg40532"><a><span class="file"
																							id="unex">打印机</span>
																					</a>
																					</li>
																					<li id="bg40533"><a><span class="file"
																							>通讯设备</span>
																					</a>
																					</li>
																				</ul></li>
																			<li id="bg4054"><a><span class="file"
																					>弱电设备</span> </a></li>


																		</ul>
																	</li>
																	<li id="bg406"><a><span class="file" id="unex">物流管理</span>
																	</a></li>
																	<li id="bg407"><a><span class="file" id="unex">用水管理</span>
																	</a></li>
																	<li id="bg408"><a><span class="file" id="unex">用电管理</span>
																	</a></li>
																	<li id="bg409"><a><span class="file" id="unex">值班管理</span>
																	</a></li>
																	<li id="bg410"><a><span class="file" id="unex">绿化管理</span>
																	</a></li>
																	<li id="bg411"><a><span class="file" id="unex">食管管理</span>
																	</a></li>
																	<li id="bg412"><a><span class="file" id="unex">住宿管理</span>
																	</a></li>
																	<li id="bg413"><a><span class="file" id="unex">资产库存管理</span>
																	</a></li>
																	<li id="bg414"><a><span class="file" id="unex">物品物料管理</span>
																	</a></li>

																</ul>
															</li>
															<li id="bg5"><a><span class="file" id="ex">督察管理项目</span>
																	</span> </a></li>

														</ul></li>

													<li state="closed"><span class="folder" id="cw">财务项目</span>
														<ul>
															<li id="sc1"><a><span class="file" id="unseal">项目预算招标项目</span>
															</a></li>
															<li id="sc2"><a><span class="file" id="seal">资金申请项目</span>
															</a></li>
															<li id="sc3"><a><span class="file" id="seal">资金使用项目</span>
															</a></li>
															<li id="sc4"><a><span class="file" id="seal">凭证管理项目</span>
															</a></li>
															<li id="sc5"><a><span class="file" id="seal">总账管理项目
																</span> </a></li>

														</ul>
													</li>
													<li state="closed"><span class="folder">生产项目</span>
														<ul>
															<li id="sc1"><a><span class="file" id="unseal">研发（教研）项目</span>
															</a></li>
															<li id="sc2"><a><span class="file" id="seal">模拟项目</span>
															</a></li>
															<li id="sc3"><a><span class="file" id="seal">综合办证项目</span>
															</a></li>
															<li id="sc4"><a><span class="file" id="seal">生产（教务）项目</span>
															</a></li>
															<li id="sc5"><a><span class="file" id="seal">检验成品（考核合格归档）项目
																</span> </a></li>

														</ul></li>
													<li state="closed"><span class="folder">营销项目</span>
														<ul>
															<li id="yx1"><a><span class="file" id="unread">市场调查</span>
															</a></li>
															<li id="yx2"><a><span class="file" id="readed">产品包装推广项目</span>
															</a></li>
															<li id="yx3"><a><span class="file" id="readed">咨询收集客户项目</span>
															</a></li>
															<li id="yx4"><a><span class="file" id="readed">成交客户项目</span>
															</a></li>
															<li id="yx5"><a><span class="file" id="readed">售后跟踪项目</span>
															</a></li>

														</ul></li>

												</ul></li>
										</ul></li>
									<li><span class="folder">招标比价</span>
										<ul>
											<li><a><span href="javascript:void(0);">未招标比价项目</span>
											</a>
											</li>
											<li><a><span href="javascript:void(0);">已招标比价物品</span>
											</a>
											</li>
										</ul></li>
							</li>
						</ul>
  
					</td>
				<td style="width: 80%;" valign="top"><iframe id="mainframe11" 
						style="margin:0px;width:100%;height:550px;" name="admin" src=""
						frameBorder="0"></iframe></td>
			</tr>
		</table>

	<script type="text/javascript">
		$(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#aadTree").height($(window).height()- 30);
                 $("#mainframe11").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#aadTree").height($(window).height()- 30);
         $("#mainframe11").css({"height" : $(window).height() - 5 + "px"}); 
     });
	</script>
</body>
</html>