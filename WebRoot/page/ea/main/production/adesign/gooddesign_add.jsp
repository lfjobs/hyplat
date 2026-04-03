<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany");
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport"
		  content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>物品设计添加</title>


	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>



	<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
	<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>css/ea/production/production.css" />


	<script src="<%=basePath%>js/ea/production/adesign/gooddesign_add.js"
			type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/production/adesign/product.js"
			type="text/javascript"></script>

	<script type="text/javascript" charset="utf-8"
			src="<%=basePath%>/page/utf8-jsp/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
			src="<%=basePath%>/page/ueditor/ueditor.all.min.js"></script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8"
			src="<%=basePath%>/page/ueditor/lang/zh-cn/zh-cn.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/popLayer/css/popstyle.css" />
	<script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>
	<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
	<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>


	<script type="text/javascript">
        var companyID = '<%=c.getCompanyID()%>';
        var basePath="<%=basePath%>";
        var seqnum = 2;
        var typeIDs = "x";
        var fiveClear="${fiveClear}";
        var goodsID = "";
        var specnum = 0;
        var variableID = "";
        var staffID="";
        var pageNumber=0,pageCount=0;
        var siteId = "";
	</script>

</head>
<body style="overflow-y:auto">
<form id="mainForm" name="mainForm" method="post" enctype="multipart/form-data">
	<input type="submit" name="submit" style="display:none;">
	<div class="main">
		<div class="top">物品信息</div>
		<div class="body">
			<div class="ttab">
				<ul class="tab">
					<li id="showinfo" class="selected left info">物品基本信息</li>
					<li id="showfunc">物品功能介绍</li>
					<li id="showcar" style="display:none;">车辆基本信息</li>
				</ul>
			</div>
			<div class="showinfo show">
				<table id="gd">
					<tr>
						<td align="right" style="width:30%;">物品主图 ：</td>
						<td>

							<div class="uploadpic">

								<img src="<%=basePath%>images/WFJClient/add.png" id="main_img" />

							</div>
							<input type="file" class="btn_filem" name="file">
							<br/>
							<input type="hidden"  name="goodsManage.photoPath" id="photoPath"/>

							<%--<input type="file"--%>
								   <%--name="J_MultimageField" id="J_MultimageField"  multiple="true">--%>
							<p>尺寸：建议300px*300px</p>
							<p>大小：小于3M</p>
							<br/>
						</td>
					</tr>
					<tr>
						<td align="right">行业名称：</td>
						<td><input type="text" name="goodsManage.tradeName" class="inputtext input3" style="width:40%;" readonly id="mulind"/>
							<input type="hidden" id="indID" name="goodsManage.tradeID" />
							<input type="hidden" id="ind" name="goodsManage.tradeCode" />
							&nbsp;<input type="button" onclick="pop('industry')" value="选择" class="btn01"/>
						</td>
					</tr>
					<tr>
						<td align="right">物品类别：</td>
						<td><input type="text" class="inputtext input3" name="goodsManage.typeID" id="td" style="width:40%;" readonly />

							&nbsp;<input type="button" onclick="pop('goodcate')" value="选择" class="btn01"/>
						</td>
					</tr>


					<tr style="display: none;">
						<td align="right">场地名称：</td>
						<td><input type="text" class="inputtext input3" name="vf.siteName" id="siteName" style="width:40%;" readonly />

						&nbsp;<input type="button" onclick="pop('site')" value="选择" class="btn01 site"/>
						<input type="hidden" class="inputtext put3" id="siteId" name="fs.siteId"/>
						</td>
					</tr>


					<%--
					<tr>
						<td align="right">项目产品分类：</td>
						<td><input type="text" class="inputtext" style="width:40%;" readonly id="mulpro"/>
						<input type="hidden" id="pro" name="goodsManage.projecttype" />
						&nbsp;<input type="button" onclick="pop('project')" value="选择" class="btn01"/>
						</td>
					</tr>
					--%>
					<tr>
						<td align="right">物品编号：</td>
						<td><input type="text" class="inputtext put3" name="goodsManage.goodsCoding" value="自动生成" readonly/>
						</td>
					</tr>

					<tr>
						<td align="right">物品名称：</td>
						<td><input type="text" class="inputtext ckTextLength goodsName put3" name="goodsManage.goodsName" maxlength="50">
						</td>
					</tr>

					<tr>
						<td align="right">品牌：</td>
						<td><input type="text" class="inputtext put3" name="goodsManage.brand" />
							<input type="hidden"  name="fiveClear"  value="${fiveClear}"/>
						</td>
					</tr>


					<tr>
						<td align="right">型号管理：</td>
						<td><input type="text" class="inputtext put3" name="goodsManage.model"/>
						</td>
					</tr>

					<tr>
						<td align="right">单位：</td>

						<td>
							<s:select list="variablelist" listKey="codeValue" id="dw"
									  listValue="codeValue" headerKey="" headerValue="请选择"
									  name="goodsManage.variableID" theme="simple"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">电子秤打条码：</td>
						<td>

							<select name="goodsManage.isScale" id="isScale"><option value="1">否</option><option value="0">是</option></select></td>
						</td>
					</tr>
					<tr class="jjdw" style="display:none;">
						<td align="right">计价单位：</td>
						<td>

							<select name="scaleGoods.unitOfMeasureCode" id="unitOfMeasureCode"><option value="KGM">KGM以重计价</option><option value="PCS">PCS以数计价</option></select></td>
						</td>
					</tr>


					<tr>
						<td align="right">物品条码：</td>
						<td><input type="text" class="inputtext put3" name="goodsManage.barCode" id="bcd"/>
						</td>
					</tr>


					<tr>
						<td align="right">物品规格：</td>
						<td><input type="text" class="inputtext put3" name="goodsManage.standard"/>
						</td>
					</tr>
					<tr class="moretr">
						<td align="right">&nbsp;</td>
						<td>
							+<a onclick="addMoreSpec()" style="cursor:pointer;">添加更多规格</a>
						</td>
					</tr>
					<tr  class="more" style="display: none;">
						<td   align="center" colspan="2">
						<table class="spectbl" style="width:500px;">
							<tr><td>规格</td><td>单位</td><td>数量</td><td>条码</td><td>&nbsp;操作&nbsp;</td></tr>

						</table>
						</td>
					</tr>
					<tr>
						<td align="right"><input placeholder="尺码" size="5"  value="尺码"  name="sizecon"
												 class="kxtitle" /><input type="hidden" name="sizevalue" id="sizevalue">：</td>
						<td align="left">
							<section class="pro_size">

								<div class="size_con">
									<label>36</label> <label>37</label> <label>S</label> <label>L</label>

									<input class="tianjia" type="text" value="" placeholder="尺码"
										   size="5" />

									<p>
										<input type="button" value="添加" id="add1"/>
									</p>
								</div>
							</section>
						</td>
					</tr>
					<tr>
						<td align="right"><input placeholder="颜色" size="5"   value="颜色" name="colorcon"
												 class="kxtitle" />：</td>

						<td align="left">

							<section class="pro_color">

								<div class="color_con">
									<ul class="style_list">
										<li id="color"><div class="img">
											<img src="<%=basePath%>images/WFJClient/add.png" id="add_img" />
											<input type="file" class="btn_file">
										</div>
											<input type="text" placeholder="颜色" class="tianjia" />
										</li>

										<p id="add_button">
											<input type="button" value="添加" id="add2"/>
										</p>
									</ul>

								</div>
							</section></td>

					</tr>




					<tr>
						<td align="right">备注：</td>
						<td><textarea type="text" class="inputtext ckTextLength" name="goodsManage.remark" style="height: 50px" maxlength="250"></textarea>
						</td>
					</tr>
				</table>

			</div>
			<div class="showfunc show hidcontent">
				<input type="hidden"  name="goodFunction.url"  class="url"/>

				<div class="group" id="init1">
					<div class="title">

						<input type="text" class="input defaults"  name="goodFunction.name"  placeholder="结构"  /> <input type="button"
																														 value="新增" class="btn01 add" />&nbsp;<input type="button"
																																									 value="移除" class="btn01 del" style="display:none;"/>
					</div>

					<div class="content">

						<script type="text/plain" id="editor1"
								style="width:100%;height:200px;"></script>


						<script type="text/javascript">

                            UE.getEditor('editor1', {
                                autoFloatEnabled: false
                            });


						</script>
					</div>
				</div>

				<div class="group" id="init2">
					<div class="title">

						<input type="text" class="input defaults"  name="goodFunction.name" placeholder="结构" /> <input type="button"
																													   value="新增" class="btn01 add" />&nbsp;<input type="button"
																																								   value="移除" class="btn01 del"/>

					</div>

					<div class="content">

						<script type="text/plain" id="editor2"
								style="width:100%;height:200px;"></script>


						<script type="text/javascript">

                            UE.getEditor('editor2', {
                                autoFloatEnabled: false
                            });


						</script>
					</div>
				</div>

				<div class="group" id="init" style="display:none;">
					<div class="title">

						<input type="text" class="input defaults"  name="name"  style="color:#999;" value="结构" /> <input type="button"
																														 value="新增" class="btn01 add" />&nbsp;<input type="button"
																																									 value="移除" class="btn01 del"/>

					</div>

					<div class="content"></div>
				</div>


			</div>


			<div class="showcar show hidcontent">
				<table style="width:100%;">

					<tr>
						<td align="right">车牌号：</td>

						<td><input type="text" class="put3 inputtext"
								   name="carInformation.carNum"/></td>

					</tr>
					<tr>
						<td align="right">车架号/机壳号：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.carFrameNum" /></td>
					</tr>
					<tr>
						<td align="right">主板号/发动机号：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.engineNum" /></td>
					</tr>

					<tr>
						<td align="right">车辆类型：</td>
						<td><input type="text" class="inputtext put3"
								   name="carInformation.carType" /></td>
					</tr>
					<tr>
						<td align="right">购车地点：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.carPlace" /></td>
					</tr>

					<tr>
						<td align="right">购买日期：</td>
						<td><input type="text" class="inputtext put3"
								   name="carInformation.buyDate" onfocus="date(this);"/></td>
					</tr>
					<tr>
						<td align="right">出厂日期：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.releaseDate" onfocus="date(this);"/></td>
					</tr>
					<tr>
						<td align="right">运行日期：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.operationDate" onfocus="date(this);"/></td>
					</tr>
					<tr>
						<td align="right">注册登记日期：</td>
						<td><input type="text" class="inputtext put3"
								   name="carInformation.registrationDate" onfocus="date(this);"/></td>
					</tr>
					<tr>
						<td align="right">车辆厂牌型号：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.brandModel" /></td>
					</tr>
					<tr>
						<td align="right">发动机型号：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.engineType" /></td>
					</tr>
					<tr>
						<td align="right">货箱内部尺寸：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.containerInSize" /></td>
					</tr>
					<tr>
						<td align="right">外廊尺寸：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.outerSize" /></td>
					</tr>

					<tr>
						<td align="right">驱动形式：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.driveType" /></td>
					</tr>

					<tr>
						<td align="right">排量/功率：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.power" /></td>
					</tr>

					<tr>
						<td align="right">燃油类别：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.fuelType" /></td>
					</tr>
					<tr>
						<td align="right">外观颜色及漆号：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.colorPaintNum" /></td>
					</tr>

					<tr>
						<td align="right">车辆品牌：</td>
						<td><input type="text" class="inputtext put3"
								   name="carInformation.vehicleBrand" /></td>
					</tr>
					<tr>
						<td align="right">制造厂名称：</td>
						<td><input type="text" class="inputtext put3"
								   name="carInformation.factoryName" /></td>
					</tr>

					<tr>
						<td align="right">准牵引总质量：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.tractionTotal" /></td>
					</tr>
					<tr>
						<td align="right">轮距：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.wheelTead" /></td>
					</tr>
					<tr>
						<td align="right">核定载客(人)：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.ratifyPeople" /></td>
					</tr>
					<tr>
						<td align="right">核定载质量：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.ratifyQuality" /></td>
					</tr>
					<tr>
						<td align="right">国产/进口：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.domestic" /></td>
					</tr>
					<tr>
						<td align="right">驾驶室载客：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.bridgePeople" /></td>
					</tr>
					<tr>
						<td align="right">钢板弹簧片数：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.springNum" /></td>
					</tr>
					<tr>
						<td align="right">车辆获得方式：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.vehicleGet" /></td>
					</tr>
					<tr>
						<td align="right">使用性质：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.useProp" /></td>
					</tr>

					<tr>
						<td align="right">轴数：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.shaftNum" /></td>
					</tr>


					<tr>
						<td align="right">轮胎数：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.tireNum" /></td>
					</tr>

					<tr>
						<td align="right">轴距(mm)：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.wheelbase" /></td>
					</tr>
					<tr>
						<td align="right">百公里耗油：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.kmFuel" /></td>
					</tr>

					<tr>
						<td align="right">轮胎规格：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.tireSpecifications" /></td>
					</tr>
					<tr>
						<td align="right">整备质量(kg)：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.serviceQuality" /></td>
					</tr>
					<tr>
						<td align="right">转向形式：</td>
						<td><input type="text" class="inputtext"
								   name="carInformation.steeringType" /></td>
					</tr>

				</table>
			</div>




		</div>
		<div class="bottom">
			<input type="button" class="btn save" value="提交保存" />
		</div>
	</div>
</form>
<!-- 科目 -->

<div id="subject" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择科目</span>
		</div>
		<div class="chooseborder">
			<div id="aadTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('subject')" value="关闭" />
		</div>
	</div>
</div>

<!-- 行业 -->

<div id="industry" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择行业</span>
		</div>
		<div class="chooseborder">
			<div id="industryTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('industry')" value="关闭" />
		</div>
	</div>
</div>


<!-- 项目产品分类-->

<div id="project" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择项目产品分类</span>
		</div>
		<div class="chooseborder">
			<div id="projectTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('project')" value="关闭" />
		</div>
	</div>
</div>




<!-- 物品分类-->

<div id="goodcate" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择物品类别</span>
		</div>
		<div class="chooseborder">
			<div id="goodcateTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('goodcate')" value="关闭" />
		</div>
	</div>


</div>
<!-- 选择公司场地 -->

<div id="site" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择场地</span>
		</div>
		<div class="chooseborder">
			<div id="goodsTree" style=" border: 0px solid #000000;"></div>
			<table width="99%" height="33" id="searchgood"     border="0"
				   align="center" cellpadding="0" cellspacing="0"
				   style="margin-top: 5px; background: #FFFFFF;">
				<tr>
					<td width="100" align="right">
						场地编号或名称：
					</td>
					<td width="110">
						<input name="numberOrName" class="input" id="numberOrName" size="10"
							   style="margin-left: 2px;" />
					</td>
					<td height="33">
						<input type="button" class="btn01" id="sitequery"
							   name="button7" value="查询"/>
						<input type="button" class="btn01" id="sitedetermine"
							   name="button5" value="确定" />
						<input type="hidden" name="parms" id="parms" />

					</td>
					<td width="80">
						<a id="sitewpsy" title="0" style="cursor:pointer;">上一页</a>
					</td>
					<td width="80">
						<a id="sitewpxy" title="0" style="cursor:pointer;">下一页</a>
					</td>
					<td width="100">
						<a id="sitewpzy">共&nbsp;&nbsp; <span style="color: red"
															 id="sitecount"></span>&nbsp;&nbsp;页 </a>
					</td>
				</tr>
			</table>
			<table width="99%" border="0" align="center" cellpadding="0"
				   cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				<tr>
					<td width="100%" valign="top" align="left">
						<div id="site_body"
							 style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
							<table  class="table">
								<thead></thead>
								<tbody></tbody>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('site')" value="关闭" />
		</div>
	</div>
</div>



<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>

</body>
</html>