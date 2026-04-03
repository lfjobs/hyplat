<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
	String path = request.getContextPath();	
String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>

</title>

		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
		 <link href="<%=basePath%>css/ea/head_min.css" rel="stylesheet" type="text/css" />
         <link href="<%=basePath%>css/ea/tbsp.css" rel="stylesheet" type="text/css" />
         <link href="<%=basePath%>css/ea/publish_base.css" rel="stylesheet" type="text/css" />
		  <link href="<%=basePath%>css/ea/index-min.css" rel="stylesheet" type="text/css" />
	
		  <link href="<%=basePath%>css/ea/robot.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ea/asset_base.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ea/api.css" rel="stylesheet" type="text/css" />
        <link href="<%=basePath%>css/ea/index_min_s.css" rel="stylesheet" type="text/css" />
        <script src="<%=basePath%>js/ea/tao/add_baby.js"  type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>/page/utf8-jsp/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/page/ueditor/ueditor.all.min.js"></script>
	    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/page/ueditor/lang/zh-cn/zh-cn.js"></script>
	    
          <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />

<script type="text/javascript">
var basePath = "<%=basePath%>";
</script>
<style type="text/css">
    .uploadify-button {
        background-color: transparent;
        border: none;
        padding: 0;
    }
    .uploadify:hover .uploadify-button {
        background-color: transparent;
    }
</style>
</head>
<body>
<div id="page">
	<form name="mainform" id="J_MainForm" action="" method="post" enctype="multipart/form-data">


			<div id="item-publish" class="box">
				<span class="rc-tp"><span></span>
				</span>
				<div class="hd">
					<h3>填写宝贝基本信息</h3>
				</div>
				<div class="bd">
					<div class="bd-sub" data-spm="1000773">
						<div id="product-info">
							<h5>产品信息</h5>
							<div class="detail">
								<ul>
									<li>类目：女鞋&gt;&gt;帆布鞋 <input id="J_ReCategory"
										type="submit" data-nocheck="true"
										data-name="event_submit_do_re_select_category" value="编辑类目"
										class="J_DetectTrigger" data-detect="reEditCat">
									</li>
								</ul>
							</div>
							<div class="detail" id="J_product-info">
								<ul>
									<li></li>
								</ul>
							</div>
							<div class="detail" id="J_medical-info"></div>
						</div>
					</div>
					<!-- end bd-sub -->
					<div class="bd-main">





						<div>
							<span>
								<h5>1. 宝贝基本信息</h5> </span>

							<div class="form" data-spm="1000773">
								<ul id="J_form">
									<li class="hidden"><label>交易类型：</label> <span> <em>*</em>
											<ul id="J_trade-type" class="ul-radio">
												<li><input type="radio" class="radio"
													id="J_trade-type-b" checked=""><label
													for="J_trade-type-b">一口价</label>
												</li>
											</ul> </span></li>
									<li id="old_new"><label>宝贝类型：</label> <span> <em>*</em>
											<ul class="ul-radio">
												<li><input type="radio" class="radio" checked="checked"
													value="5" name="_fma.pu._0.stu" id="on1"> <label
													for="on1">全新</label></li>
												<input type="hidden" name="auctionTypeInfoPrint"
													value="editaVlue=false;canPublishNew=true;isOnlySecond=false;isRestAuction=;isBookStartLimit=false;isAcousticsLimit=false;isPrepayLimitCatAndSeller=false;is3GCatCanNewForSeller=;canPublishAlcoholNew=;noAgreement=;noPrepay=;shopSeller=true;isSellerShopeEnable=true;isShopRelease=;">
												<li class="used-desc"><input type="radio" class="radio"
													value="6" name="_fma.pu._0.stu" id="on6"> <label
													for="on6">二手</label></li>
											</ul> <input type="hidden" id="nav_stuffStatus"
											data-feed="err_nav_stuffStatus">
											<div id="err_nav_stuffStatus" style="display:none"></div> </span></li>
									<script type="text/javascript">
	Sell.Config.set('healthSpu', {
		healthCat:  false 	});
</script>
									<li id="descTemplateArea"><label>页面模板：</label> <span>
											<ul>
												<li><select id="auctionDescInnerSiteTemplate"
													name="_fma.pu._0.auctio" value="1123033928">
														<option value="1123033928" selected="">默认宝贝详情页</option>
												</select></li>
											</ul> </span></li>
									<script type="text/javascript">
</script>


									<li id="itemproperties" class="spubox clearfix"><label>宝贝属性：</label>
										<span>
											<div id="J_module-property"
												class="module-property module-form ">
												<div class="skin">
													<dl class="hint propertymsg">
														<dd>填错宝贝属性，可能会引起宝贝下架，影响您的正常销售。请认真准确填写</dd>
													</dl>
													<ul>
														<%-- <li name="keySpus">
				<label for="prop_20000" class=" label-title" id="ariaby-prop_20000">品牌：</label> 
				<span>
                <ul class="J_ul-single J_is-mainprop ul-select">
				   <li>
					<div class="kui-combobox" role="combobox">
						<div class="kui-dropdown-trigger">
							<label class="kui-placeholder">可直接输入内容</label>
							<input class="kui-combobox-caption" type="text" style="width:150px" 
							autocomplete="off" role="textbox" aria-autocomplete="list"
								aria-haspopup="true" name="cpi_20000"
								id="simulate-prop_20000"
							    aria-label="品牌： 上下键打开选项列表，回车选中选项，ESC关闭列表，关闭后TAB键跳转到其他选项">
						   <div class="kui-icon-dropdown"></div>
							</div>
							</div> 
							<select name="cp_20000" id="prop_20000"
							class="keyPropClass" data-transtype="combox"
															style="display: none; visibility: hidden;">
								<option class="J_empty" value=""></option>


								<option value="20000:218734616">100KM＆PF</option>

								<option value="20000:3435719">1357</option>

								<option value="20000:94208801">CaratCore/卡丽戈</option>

								<option value="20000:30117">Cartier/卡地亚</option>

								<option value="20000:10027658">Cartwheel/马车轮</option>

							</select>
							</li>
							</ul>
							<div id="disableSpu" class="alert alert-error hidden">不能发布或编辑此类宝贝，所属的宝贝模板已被屏蔽</div>
								<input type="hidden" id="nav_spuId"
									data-feed="err_nav_spuId">
								<div id="err_nav_spuId" style="display:none"></div> 
							</span> 
							<input
								type="hidden" id="nav_cp_20000"
															data-feed="err_NotNull_20000">
															<div id="err_NotNull_20000" style="display:none;"></div>
															<input type="hidden" class="cphelp" id="cphelp_20000"
															name="cphelp_20000"
															value="https://img.alicdn.com/imgextra/i5/T162SWXfRcXXXXXXXX.help">
															<dl class="hint propertymsg">
																<dd>
																	如果没有您需要的品牌，您可以<a target="blank"
																		href="//baike.taobao.com/brandApply.htm?catId=50012042">点此申请添加品牌</a>
																</dd>
															</dl> <span class="cphelp_content">填写前请务必详读“品牌属性发布提示通知”<a
																href="http://service.taobao.com/support/knowledge-1345124.htm"
																target="_blank">更多帮助...</a>
														</span>
														</li>
														<li id="J_product-check" style="display:none"><label
															class="label-title" id="ariaby-prop_140654160">信息确认：</label>
															<span class="product-check"> <!-- 发布默认选中check--> <input
																type="checkbox" checked="checked" id="supConfirm"
																name="_fma.pu._0.s" data="1" value="1" isedit="false">

																我明白平台会根据产品实际情况对左侧产品信息内容进行更新，并确定产品信息和 <br>发布的商品信息完全一致，如发布成功，产品信息将展示在商品详情页。
																<br>不, <a href="/" id="baike_edit_url"
																target="_blank">我要纠错&gt;&gt;</a> </span></li>
														<li name="keySpus"><label for="prop_13021751"
															class=" label-title">货号：</label> <span> <input
																type="text" id="prop_13021751" name="cp_13021751"
																class="text text-short" value=""> </span> <input
															type="hidden" id="nav_cp_13021751"
															data-feed="err_NotNull_13021751">
															<div id="err_NotNull_13021751" style="display:none;"></div>

														</li> --%>
														<s:iterator value="list">
															<li class="J_spu-property" id="spu_122216632" name="spus">
																<label class="label-title" id="ariaby-prop_122216632">${attriname}：</label>
																<span> <s:if test='controltype=="select"'>
																		<ul class="J_ul-single ul-select">

																			<li>
																				<div class="kui-combobox" role="combobox">
																					<div class="kui-dropdown-trigger">
																						<input class="kui-combobox-caption"
																							style="width:150px;" readonly="true"
																							role="textbox" aria-autocomplete="list"
																							aria-haspopup="true" id="simulate-prop_122216632"
																							aria-label="鞋制作工艺： 上下键打开选项列表，回车选中选项，ESC关闭列表，关闭后TAB键跳转到其他选项">
																						<div class="kui-icon-dropdown"></div>
																					</div>
																				</div> <select name="cp_122216632" id="prop_122216632"
																				data-transtype="dropbox" style="">
																					<option value=""></option>
																					<c:forEach var="obj" items="${mapvalue[acID]}">
																						<option value="${obj.avcID}">${obj.value}</option>
																					</c:forEach>
																			</select>
																			</li>
																		</ul>
																	</s:if> <s:if test='controltype=="input"'>
																		<input type="text" maxlength="30" id="prop_122640606"
																			name="cp_122640606" class="text text-short " value="">
																	</s:if> <s:if test='controltype=="checkbox"'>
																		<ul class="J_ul-multi ul-checkbox">
																			<c:forEach var="obj" items="${mapvalue[acID]}">
																				<li><input type="checkbox" name="cp_34272"
																					value="34272:115800" id="prop_${obj.avcID}"
																					class="checkbox"><label
																					for="prop_34272_115800">${obj.value}</label></li>
																			</c:forEach>
																		</ul>
																	</s:if> </span> <input type="hidden" id="nav_cp_122216632"
																data-feed="err_NotNull_122216632">
																<div id="err_NotNull_122216632" style="display:none;"></div>
															</li>



														</s:iterator>


													</ul>
												</div>
												<div id="J_module-Qualification"></div>
											</div> <!--发布助手--> <input type="hidden" id="nav_categoryProperty"
											data-feed="err_nav_categoryProperty">
											<div id="err_nav_categoryProperty" style="display:none"></div>
									</span></li>





									<li data-robot-helper="0">
										<div id="itemtitle" class="fieldset">
											<label for="TitleID" class="caption required">宝贝标题：</label>
											<div class="fields-box">
												<span class="add-on" id="ticketTitle" style="display:none">代买：</span>
												<span class="add-on" id="J_TitlePlugin"
													style="display: none;"></span> <input id="TitleID"
													data-autogen="true" class="text text-long"
													data-feed="err__fma.pu._0.ti" name="_fma.pu._0.ti"
													maxlength="60" size="30" value=""> <span
													id="J_TitleCountDownTip" class="prop-tips countdown-tip">还能输入<em
													class="counter">30</em>字</span> <input type="hidden"
													id="nav_title" data-feed="err_nav_title">
												<div id="err_nav_title" style="display:none"></div>

											</div>
											<div class="Tips_messageBox titleCheck hidden"></div>
										</div></li>
									<li>
										<div id="subheading" class="fieldset">
											<label for="SubheadingID" class="caption">宝贝卖点：</label>
											<div class="fields-box">
												<textarea id="SubheadingID" class="text-input"
													name="subTitle" value="" maxlength="150" rows="3" cols="77"
													size="150"></textarea>
												<span id="J_SubheadingCountDownTip"
													class="prop-tips countdown-tip">还能输入<em
													class="counter">150</em>字</span> <input type="hidden"
													id="nav_subTitle" data-feed="err_nav_subTitle">
												<div id="err_nav_subTitle" style="display:none"></div>
											</div>
											<div class="Tips_messageBox subheadingCheck hidden"></div>
										</div></li>
									<input type="hidden" name="_fma.pu._0.pay" value="">
									<input type="hidden" name="_fma.pu._0.payv" value="1">
									<li id="fixpriceOption2"><label for="buynow">一口价：</label>
										<span> <em>*</em> <input type="text"
											class="text text-short" id="buynow" maxlength="12" size="12"
											name="_fma.pu._0.m" value=""> 元 <span
											class="prop-tips hidden" id="J_SpecHintContainer"></span> <span
											class="prop-tips" id="catePriceLimit">本类目下，价格必须在10元-999999999元之间</span>
											<input type="hidden" id="nav_minimumBid"
											data-feed="err_nav_minimumBid">
											<div id="err_nav_minimumBid" style="display:none"></div> </span></li>

									<li class="fieldset" id="paytype">
										<div class="J_PreSale">
											<div class="tbc-presale">
												<div class="tbc-presale-title">预售设置：</div>
												<div class="tbc-presale-content">
													<ul class="ul-radio tbc-presale-type">
														<li><input id="J_TbcPreSaleType_1"
															class="radio J_TbcPreSaleType" type="radio"
															name="tbc-presale-type" value="0" checked=""> <label
															for="J_TbcPreSaleType_1">非预售</label></li>

														<li><input id="J_TbcPreSaleType_10000"
															class="radio J_TbcPreSaleType" type="radio"
															name="tbc-presale-type" value="10000"> <label
															for="J_TbcPreSaleType_10000">普通预售</label></li>

														<li><input id="J_TbcPreSaleType_20000"
															class="radio J_TbcPreSaleType" type="radio"
															name="tbc-presale-type" value="20000"> <label
															for="J_TbcPreSaleType_20000">定时预售</label></li>

													</ul>
													<div id="J_TbcPreSaleSetting" class="tbc-presale-setting"></div>
												</div>
											</div>
										</div> <input type="hidden" value="2" name="paytype"> <input
										type="hidden" id="nav_payment_step"
										data-feed="err_nav_payment_step">
										<div id="err_nav_payment_step" style="display:none"></div> <input
										type="hidden" id="nav_tbc-presale-type"
										data-feed="err_nav_tbc-presale-type">
										<div id="err_nav_tbc-presale-type" style="display:none"></div>
									</li>




									<input type="hidden" id="nav_measure"
										data-feed="err_nav_measure">
									<div id="err_nav_measure" style="display:none"></div>

									<li><script>
    	Sell.Config.set('ColorUploadImg', {
		    isNew: true,
    	    remoteURL: "//tadget.taobao.com/redaction/quickInsert.htm?showUpload=0&catid=50012042&small&no-tab&area-only&single-select&pageSize=10",
    	    proxyURL: "//upload.taobao.com/auction/publish/color_proxy.htm",
    	    uploadURL: "/auction/publish/uploadSingleImage.do"
    	});
    </script>
										<div class="sku-style" id="J_SellProperties">
											<label class="sku-title">宝贝规格：</label>
											<div class="sku-wrap">


												<div data-caption="颜色分类" data-pid="1627207"
													class="sku-group  " data-features="image edit ">
													<label class="sku-label ">颜色分类：</label>
													<div class="sku-box  sku-color" id="sku-color-wrap">

														<div id="sku-color-tab">
															<ul class="clearfix" id="sku-color-tab-header">
																<c:forEach var="colortype" items="${colortypelist}"
																	varStatus="status">

																	<li index="${status.count}"
																		<c:if test="${status.count==1}">class="selected"</c:if> >
																	<div class="rgb-box"
																		style="background-color: ${colortype.colorvalue}; background-position: initial initial; background-repeat: initial initial;"></div>
																	<div class="color-text">${colortype.colorname}</div>
									                            </li>

									                  </c:forEach>
								                      </ul>
								<div id="sku-color-tab-contents">
								<c:forEach var="colortype" items="${colortypelist}"  varStatus="c">
								<ul class="color-list clearfix" index="${c.count}" <c:if test="${c.count==1}">style="display:show;"</c:if>
								<c:if test="${c.count!=1}">style="display:none;"</c:if>
							 >
									
			                         <c:forEach var="color" items="${colorlist}"  varStatus="s">
			                       <c:if test="${color.colortype eq colortype.actID}"> 
										<li><label data-text="${color.colorname}"><input
												class="J_Checkbox" type="checkbox" data-color="${color.colorvalue}"
												data-text="${color.colorname}" name="cp_1627207" id="prop_1627207-28321"
												value="1627207:28321" data-path="" data-thumb=""><i
													class="color-box" color="${color.colorvalue}"
													style="background-color: ${color.colorvalue}; background-position: initial initial; background-repeat: initial initial;"></i>${color.colorname}
											
										</label>
										<span class="color-note-text" style="visibility: hidden;">备注</span><input
											type="text" class="color-note color-textbox text"
											name="cpv_note_1627207:28321" id="J_note_1627207-28321"
											maxlength="16" style="visibility: hidden;">
										</li>
										
									 </c:if>
									</c:forEach>
							
									</ul>
								
						     	</c:forEach>
							
						
								</div>
							</div>
							<ul class="clearfix" id="sku-color-custom">
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-2" data-path="" data-thumb=""
									id="prop_1627207--2"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-2" value="其它颜色" maxlength="16"
										id="J_note_1627207--2"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-3" data-path="" data-thumb=""
									id="prop_1627207--3"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-3" value="其它颜色" maxlength="16"
										id="J_note_1627207--3"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-4" data-path="" data-thumb=""
									id="prop_1627207--4"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-4" value="其它颜色" maxlength="16"
										id="J_note_1627207--4"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-5" data-path="" data-thumb=""
									id="prop_1627207--5"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-5" value="其它颜色" maxlength="16"
										id="J_note_1627207--5"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-6" data-path="" data-thumb=""
									id="prop_1627207--6"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-6" value="其它颜色" maxlength="16"
										id="J_note_1627207--6"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-7" data-path="" data-thumb=""
									id="prop_1627207--7"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-7" value="其它颜色" maxlength="16"
										id="J_note_1627207--7"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-8" data-path="" data-thumb=""
									id="prop_1627207--8"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-8" value="其它颜色" maxlength="16"
										id="J_note_1627207--8"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-9" data-path="" data-thumb=""
									id="prop_1627207--9"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-9" value="其它颜色" maxlength="16"
										id="J_note_1627207--9"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-10" data-path=""
									data-thumb="" id="prop_1627207--10"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-10" value="其它颜色" maxlength="16"
										id="J_note_1627207--10"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-11" data-path=""
									data-thumb="" id="prop_1627207--11"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-11" value="其它颜色" maxlength="16"
										id="J_note_1627207--11"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-12" data-path=""
									data-thumb="" id="prop_1627207--12"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-12" value="其它颜色" maxlength="16"
										id="J_note_1627207--12"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-13" data-path=""
									data-thumb="" id="prop_1627207--13"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-13" value="其它颜色" maxlength="16"
										id="J_note_1627207--13"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-14" data-path=""
									data-thumb="" id="prop_1627207--14"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-14" value="其它颜色" maxlength="16"
										id="J_note_1627207--14"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-15" data-path=""
									data-thumb="" id="prop_1627207--15"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-15" value="其它颜色" maxlength="16"
										id="J_note_1627207--15"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-16" data-path=""
									data-thumb="" id="prop_1627207--16"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-16" value="其它颜色" maxlength="16"
										id="J_note_1627207--16"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-17" data-path=""
									data-thumb="" id="prop_1627207--17"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-17" value="其它颜色" maxlength="16"
										id="J_note_1627207--17"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-18" data-path=""
									data-thumb="" id="prop_1627207--18"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-18" value="其它颜色" maxlength="16"
										id="J_note_1627207--18"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-19" data-path=""
									data-thumb="" id="prop_1627207--19"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-19" value="其它颜色" maxlength="16"
										id="J_note_1627207--19"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-20" data-path=""
									data-thumb="" id="prop_1627207--20"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-20" value="其它颜色" maxlength="16"
										id="J_note_1627207--20"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-21" data-path=""
									data-thumb="" id="prop_1627207--21"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-21" value="其它颜色" maxlength="16"
										id="J_note_1627207--21"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-22" data-path=""
									data-thumb="" id="prop_1627207--22"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-22" value="其它颜色" maxlength="16"
										id="J_note_1627207--22"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-23" data-path=""
									data-thumb="" id="prop_1627207--23"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-23" value="其它颜色" maxlength="16"
										id="J_note_1627207--23"><i class="icon"></i>
								</li>
								<li class="custom-list" style="display: none;"><input
									class="J_Checkbox" type="checkbox" data-color=""
									name="cp_1627207" value="1627207:-24" data-path=""
									data-thumb="" id="prop_1627207--24"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-24" value="其它颜色" maxlength="16"
										id="J_note_1627207--24"><i class="icon"></i>
								</li>
								<li class="custom-list" style=""><input class="J_Checkbox"
									type="checkbox" data-color="" name="cp_1627207"
									value="1627207:-1" data-path="" data-thumb=""
									id="prop_1627207--1"><input
										class="text color-text color-textbox" type="text"
										name="cp_intput_cpv_1627207_-1" value="其它颜色" maxlength="16"
										id="J_note_1627207--1"><i class="icon"></i>
								</li>
								<li class="custom-defaultText">没有合适的颜色？可以自己输入最多24个颜色</li>
							</ul>
							<div id="J_SKUColorWrapper" class="sku-wrapper"
								style="margin-left: 0px; display: none;"></div>
						</div>
						<input type="hidden" id="nav_cp_1627207"
							data-feed="err_NotNull_1627207">
							<div id="err_NotNull_1627207" style="display:none;"></div>
					</div>


					<div data-caption="尺码" data-pid="20549"
						class="sku-switchable sku-group sku-size " data-features=" edit ">
						<label class="sku-label required">尺码：</label>
						<div class="sku-box  ">

							<div class="sku-size-wrap">
								<svg width="0" height="0" style="display:none"> <defs>
								<polygon points="0,0 50,70 100,0 0,0" fill="black"
									id="svg_point"></polygon> </defs> </svg>
								<div class="size-select">
									<ul class="size-type">
								
									<c:forEach var="sizet" items="${sizetypelist}" varStatus="vs">
										<li><label><input class="type-radio" type="radio"
												name="sizeGroupType" value="${vs.count}" checked="">${sizet}
											
										</label>
										</li>
									</c:forEach>

									</ul>
									<div class="size-content">
									<c:forEach var="sizet" items="${sizetypelist}" varStatus="vs">
										
										<ul class="size-pannel" id="J_SizePannel_${vs.count}"  <c:if test="${vs.count==1}">style="display:show;"</c:if>
										<c:if test="${vs.count!=1}">style="display:none;"</c:if> >
										<c:forEach var="size" items="${sizelist}" varStatus="v">
										<c:if test="${size.astype eq sizet}">
											<li class="sku-item "><input type="checkbox"
												class="J_Checkbox" name="cp_20549" value="20549:444706729"
												id="prop_20549-444706729"> <label class="labelname"
													for="prop_20549-444706729" title="30">${size.asvalue}</label> <span
													class="size-note-text">备注</span> <input type="text"
													name="cpv_note_29148-women_shoes_20549:444706729"
													class="editbox text" maxlength="16" value="">
											</li>
										</c:if>
										</c:forEach>
										</ul>
										</c:forEach>


									</div>
								</div>
								<div class="size-diy">
									<ul>
										<li class="new"><input type="checkbox"
											class="J_Checkbox other-check" name="cp_20549"
											value="20549:-1"><label class="labelname hidden">其它尺码</label><input
												type="text" class="other-input text" placeholder="其它尺码"
												maxlength="16" name="cp_intput_cpv_20549_-1">
										</li>
									</ul>
									<span class="other-tip">没有合适的尺码？可以自己输入，最多可自定义 24 个尺码</span>
								</div>


							</div>
						</div>
						<input type="hidden" id="nav_cp_20549"
							data-feed="err_NotNull_20549">
							<div id="err_NotNull_20549" style="display:none;"></div>
					</div>
					
					
					
						
							
									
				</div>


                 <!-- 颜色属性图片上传表格 -->
				<div id="J_SKUColorWrapper" class="sku-wrapper"
					style="height: auto; width: auto; overflow-x: auto; overflow-y: hidden;">
					<table border="0" cellspacing="0"
						class="J_SKUImgTable img-table noImg">
						<caption>颜色属性图片上传表格</caption>
						<thead>
							<tr>
								<th>颜色分类</th>
								<th>图片（无图片可不填）</th>
							</tr>
						</thead>
						<tbody>
							<tr id="kelong" style="display:none;">
								<td class="tile"><i class="color-lump"
									style="background-color:#fffbf0;"></i><span
									class="J_Map_1627207-28321">乳白色</span>
								</td>
								<td class="image"><input type="hidden" class="J_ImgInput"
									name="cpvf_old_1627207:28321"><div class="img-btn">
											<input type="file" class="img-select"
												name="colorImg"  id="imgs" multiple="true">
												
										</div>
										<div class="preview">dfdf</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
                  <!-- 销售属性匹配表 -->
				<div class="sku-wrapper" id="sizeContainer" style="display:none;">
					<div class="sku-map" id="J_SKUMapContainer"
						style="height: auto; width: auto; overflow-x: auto; overflow-y: hidden;">
						<table border="0" cellspacing="0" style="visibility: visible;">
							<caption>销售属性匹配表</caption>
							<thead>
								<tr>
									<th class="J_Map_1627207"><span>颜色分类</span>
									</th>
									<th class="J_Map_20549"><span>尺码</span>
									</th>
									<th class="J_Map_0"><span class="required">价格</span>
									</th>
									<th class="J_Map_0"><span class="required">数量</span>
									</th>
									<th class="J_Map_0"><span class="">商家编码</span>
									</th>
									<th class="J_Map_0"><span class="">商品条形码</span>
									</th>
									<th class="J_Map_0"><span class="">批量操作</span>
									</th>
								</tr>
							</thead>
							<tbody>
								<tr id="kelongcsize" style="display:none;">
									<td rowspan="1"><span class="J_Map_color"></span>
									</td>
									<td rowspan="1"><span class="J_Map_size">37</span>
									</td>
									<td class="price"><input
										data-id="1627207-4950473_20549-72380707"
										id="J_SkuField_price_1627207-4950473_20549-72380707"
										class="J_MapPrice text" data-type="price" type="text" value="">
									</td>
									<td class="quantity"><input maxlength="9"
										data-id="1627207-4950473_20549-72380707"
										id="J_SkuField_quantity_1627207-4950473_20549-72380707"
										class="J_MapQuantity text" data-type="quantity" type="text"
										value="">
									</td>
									<td class="tsc"><input
										data-id="1627207-4950473_20549-72380707"
										class="J_MapProductid text" data-type="tsc" type="text"
										value="">
									</td>
									<td class="barcode"><input
										data-id="1627207-4950473_20549-72380707" class="text"
										data-type="barcode" type="text" value="">
									</td>
									<td class="batch"><i class="sku-batch"
										data-id="1627207-4950473_20549-72380707"></i>
									</td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>


				<input type="hidden" id="nav_skuTable" data-feed="err_nav_skuTable">
					<div id="err_nav_skuTable" style="display:none"></div>
					<div id="J_SKUTableWrapper"></div> <input type="hidden"
					id="J_PropTableData" name="J_PropTableData"> <input
						type="hidden" id="J_SKUTableData" name="J_SKUTableData"> <input
							type="hidden" id="J_TreeChecked" name="J_TreeChecked"> <input
								type="hidden" id="J_AvailableCustomCPV"
								name="J_AvailableCustomCPV"> <input type="hidden"
									id="J_HiddenSKUAtpanel" name="J_HiddenSKUAtpanel">
									
			</div>
			</li>


	<li id="quantity" class="fieldset">
		<label class="caption required" for="quantityId">宝贝数量：</label>
		<div class="fields-box">
			<div class="text-quantity">25</div>
							<input type="text" class="text text-shorter J_Quantity" name="_fma.pu._0.q" value="25" id="quantityId">
				件
				
					<i class="J_PopTip poptip-attention">
						(请如实填写，买家付款后72小时内未发货，根据
						<a href="//rule.taobao.com/detail-274.htm" target="_blank">淘宝规则</a>
						您可能被投诉和扣分，并赔偿金额给买家)
					</i>
																			    					<input type="hidden" id="nav_quantity" data-feed="err_nav_quantity">
		<div id="err_nav_quantity" style="display:none"></div>
				        </div>
	</li>
		<!--offerPreOrder : $offerPreOrder-->
<!--offerPreOrder : $offerPreOrder-->
<li id="preorderInfoDiv" style="display:none">
  <label for="preorderOnline">在线预约：</label>
  <span>
	<input type="checkbox" class="checkbox" name="offerPreOrder" id="preorderOnline" value="1">
	    <label for="preorderOnline" style="float:none;text-align:left;">提供在线预约</label>
    <span class="prop-tips">勾选后再宝贝详情页将出现“免费预约”按钮，可以记录预约情况</span>
  </span>
</li>
<li id="preorderAmountCon" class="fenlei-info-preorder" style="display:none">
   
  <label for="preorderAmountIpt">预约名额：</label>
  <span id="preorderAmountIptCon">
	<em>*</em>
	<input type="text" class="text text-short J_Quantity" disable="disable" id="preorderAmountIpt" maxlength="12" size="12" name="_fma.pu._0.q" value="25"> 人
		 		<input type="hidden" id="nav_ofr_quantity" data-feed="err_nav_ofr_quantity">
		<div id="err_nav_ofr_quantity" style="display:none"></div>
			 		<input type="hidden" id="nav_quantity" data-feed="err_nav_quantity">
		<div id="err_nav_quantity" style="display:none"></div>
	  </span>
</li>


							
	    <script type="text/javascript">
		Sell.Config.set('OverseasMail',{
			overseas_mail_status: "4",
			overseas_mail_select:  "0" ,
			tax_package_status: "4",
			tax_package_select:  "0"       });
    </script>
	    <li id="stock-addr">
        <div class="fieldset">
            <label class="caption required">采购地：</label>
            <div class="fields-box">
                <div class="chktab">
                    <label>
                        <input type="radio" id="J_Internal" checked="true" name="is_global_stock" value="false">
                        国内
                    </label>
                    <label>
                        <input type="radio" id="J_Abroad" name="is_global_stock" value="true">
                        海外及港澳台
                    </label>
                    <i class="J_PopTip poptip-help">勾选则承诺商品为“海外商品”，
    					<a href="//bangpai.taobao.com/group/thread/70135-285839829.htm?spm=0.0.0.0.p7bbbG" target="_blank">
							海外商品标准及规范
                        </a>。
					</i>
                </div>
                <div id="J_AbroadStock" class="hint-popup hint-default block-box" style="display:none;">
                    <div class="fieldset">
                        <label class="caption required">地区/国家：</label>
                        <div class="fields-box">
                            <select name="gs_country" id="">
                                <option value="">--请选择地区/国家--</option>
    							    								<option value="美国">美国</option>
    							    								<option value="香港">香港</option>
    							    								<option value="日本">日本</option>
    							    								<option value="英国">英国</option>
    							    								<option value="新西兰">新西兰</option>
    							    								<option value="德国">德国</option>
    							    								<option value="韩国">韩国</option>
    							    								<option value="荷兰">荷兰</option>
    							    								<option value="澳洲">澳洲</option>
    							    								<option value="法国">法国</option>
    							    								<option value="意大利">意大利</option>
    							    								<option value="台湾">台湾</option>
    							    								<option value="澳门">澳门</option>
    							    								<option value="加拿大">加拿大</option>
    							    								<option value="瑞士">瑞士</option>
    							    								<option value="西班牙">西班牙</option>
    							    								<option value="泰国">泰国</option>
    							    								<option value="新加坡">新加坡</option>
    							    								<option value="马来西亚">马来西亚</option>
    							    								<option value="菲律宾">菲律宾</option>
    							    								<option value="其他">其他</option>
    							                            </select>
                        </div>
                    </div>
                    <div class="fieldset">
                        <label class="caption required">库存类型：</label>
                        <div class="fields-box">
                            <label>
                                <input type="radio" name="gs_type" value="1">
                                现货<span class="muted">（可快速发货）</span>
                            </label>
                            <label>
                                <input type="radio" name="gs_type" checked="true" value="2">
                                非现货<span class="muted">（无现货，需采购）</span>
                            </label>
                        </div>
                    </div>
                    <div class="arrow arrow-up">
                        <i class="outer"></i>
                        <i class="inner"></i>
                    </div>
                </div>
            </div>
    		    		<input type="hidden" id="nav_global_stock_module" data-feed="err_nav_global_stock_module">
    		<div id="err_nav_global_stock_module" style="display:none"></div>
        </div>
    </li>

<li class="oversea none" style="display: none;">
    <div class="fieldset">

        <label class="caption required">发货地：</label>
        <span>

            <ul>
                <li class="domestic">
                    <input type="radio" class="radio" id="J_Mail_China" name="gs_overseas_mail_select" checked="checked" value="0" disabled="disabled">
                    <label for="J_Mail_China">国内</label>
                </li><li>
                    <input type="radio" class="radio" id="J_Mail_Oversea" name="gs_overseas_mail_select" value="1" disabled="disabled">
                    <label for="J_Mail_Oversea">海外及港澳台（需加入海外直邮服务）</label>
                    <i class="J_PopTip poptip-help">
                         发货地为海外及港澳台需要先加入海外直邮服务，<a href="//service.taobao.com/support/seller/knowledge-5836928.htm" target="_blank">查看详情</a>。
                    </i>
                </li>
            </ul>

            <div class="hint-popup hint-default block-box none" style="display: none;">
                <div>
                    <dl class="pledges">
                        <dt>服务承诺</dt>
                        <dd>保障时效：交易关闭之日起15天内</dd>
                        <dd>发货地：海外国家及地区</dd>
                        <dd>违约赔付额：100元</dd>
                        <dd>物流方式：海外直邮 （必须使用宝贝地址为海外或港澳台的运费模板）</dd>
                    </dl>
                </div>
                <div class="arrow arrow-up">
                    <i class="outer"></i>
                    <i class="inner"></i>
                </div>
            </div>

            <input type="hidden" id="nav_overseas_mail" data-feed="err_nav_overseas_mail" disabled="disabled">
            <div id="err_nav_overseas_mail" style="display:none"></div>

        </span>
    </div>
</li>

<li class="oversea none" style="display: none;">
    <div class="fieldset">

        <label class="caption">卖家包税：</label>

        <span>
            <ul>
                <li>
                    <input type="checkbox" class="checkbox" id="J_DutyFree" name="gs_tax_package_select" disabled="disabled">
                    <label for="J_DutyFree">承诺该商品若产生关税将由卖家承担</label>
                    <i class="J_PopTip poptip-help">
                        商品在通关的过程中如果产生关税则税金将由卖家承担，<a href="//service.taobao.com/support/seller/knowledge-6528658.htm" target="_blank">查看详情</a>。
                    </i>
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;注意：包税商品与非包税商品不得使用同一个包裹发货</label>
                </li>
            </ul>
            <input type="hidden" id="nav_tax_package" data-feed="err_nav_tax_package" disabled="disabled">
            <div id="err_nav_tax_package" style="display:none"></div>
        </span>

    </div>
</li>



<li id="outerDiv" class="fieldset">
	<label class="caption" for="outerIdId">商家编码：</label>
	<div class="fields-box">
        <div class="text-outerid"></div>
		<input type="text" class="text text-short" maxlength="30" id="outerIdId" name="_fma.pu._0.ou" value="">
						<input type="hidden" id="nav_outerId" data-feed="err_nav_outerId">
		<div id="err_nav_outerId" style="display:none"></div>
		</div>
</li>
<li id="barcode" class="fieldset">
    <label class="caption" for="barcode">商品条形码：</label>
    <div class="fields-box">
					<input type="text" class="text text-short" maxlength="30"
						id="itemBarCode" name="itemBarCode" value=""> <i
						class="J_PopTip poptip-help poptip-help-active">条码添加可使用手机千牛扫条码插件/淘宝助理/ERP批量，也在此手填。</i>
						<span class="prop-tips" id="J_AfterSaleTips">你家宝贝没条形码？那怎么抢<a
							href="//bbs.taobao.com/catalog/thread/1328096-266494914-1.htm"
							target="_blank">扫码新流量</a>！</span> <input type="hidden"
						id="nav_item_barcode" data-feed="err_nav_item_barcode">
							<div id="err_nav_item_barcode" style="display:none"></div>
				</div>
				</li>



									<li id="nav_item_pic" data-robot-helper="1">
										<div id="J_Multimage" class="fieldset multimage">
											<label class="caption ">宝贝图片：</label>
											<div class="fields-box">
												<div class="multimage-wrapper">
													
													<div class="multimage-panels">
														<div class="panel local-panel">
															<div class="upload-field">
							                               <label for="J_MultimageField">选择本地图片：</label> 
															       	<%-- <a 
																	 href="javascript:void(0);" class="btn btn-default">  
																	<span class="btn-txt">文件上传</span>  --%>
																	<input type="file"
																	name="J_MultimageField" id="J_MultimageField"  multiple="true"> 
																	 <!--  </a>  -->
															</div>
														<!-- 	<input id="J_MultimageField" name="file" type="file" multiple="true">  -->
											

															<div class="multimage-tips">
																<div class="tip-title">提示：</div>
																<ol>
																	<li>本地上传图片大小不能超过<strong class="bright">3M</strong>。</li>
																	<li>本类目下您最多可以上传<strong class="bright"> 5
																	</strong>张图片。</li>
																</ol>
															</div>
														</div>
														<div class="panel remote-panel remote-image"></div>
														<div class="panel remote-panel remote-video"></div>
													</div>
													<div class="multimage-info ">
														<div class="info-wrapper">
															<div class="msg">
																<span class="bright">700*700</span>
																以上的图片可以在宝贝详情页主图提供图片放大功能
															</div>
															<div class="multimage-gallery">
																<ul>
																	<li class="video" data-index="0">
																		<div class="preview">
																			<input type="hidden" class="hideimageurl"
																				name="videoAsPicThum" value=""> <input
																				type="hidden" class="hidevideoid"
																				name="videoAsPicId" value=""> <input
																				type="hidden" class="hidevideoduration"
																				name="videoAsPicDuration" value=""> <input
																				type="hidden" name="pisAsVideoStatus" value="">
																		</div>
																		<div class="info">主图视频</div>
																		<div class="examp">
																			<div class="desc">
																				视频长度<em class="bright">9秒</em>内
																			</div>
																		</div>
																		<div class="operate">
																			<span class="ph-tip">00:00</span> <i class="del">删除</i>
																		</div>
																	</li>
																	<li class=" primary " data-index="1"><input
																		name="image_pos" type="hidden" value="1">
																		<div class="preview">
																			<input type="hidden" class="hideimageurl"
																				name="picUrl1" value="">
																		</div>
																		<div class="info">
																			<span class="bright">*</span> 主图
																		</div>
																		<div class="operate">
																			<i class="toleft">左移</i> <i class="toright">右移</i> <i
																				class="del">删除</i>
																		</div>
																	</li>
																	<li class="" data-index="2"><input
																		name="image_pos" type="hidden" value="2">
																		<div class="preview">
																			<input type="hidden" class="hideimageurl"
																				name="picUrl2" value="">
																		</div>
																		<div class="operate">
																			<i class="toleft">左移</i> <i class="toright">右移</i> <i
																				class="del">删除</i>
																		</div>
																	</li>
																	<li class="" data-index="3"><input
																		name="image_pos" type="hidden" value="3">
																		<div class="preview">
																			<input type="hidden" class="hideimageurl"
																				name="picUrl3" value="">
																		</div>
																		<div class="operate">
																			<i class="toleft">左移</i> <i class="toright">右移</i> <i
																				class="del">删除</i>
																		</div>
																	</li>
																	<li class="" data-index="4"><input
																		name="image_pos" type="hidden" value="4">
																		<div class="preview">
																			<input type="hidden" class="hideimageurl"
																				name="picUrl4" value="">
																		</div>
																		<div class="operate">
																			<i class="toleft">左移</i> <i class="toright">右移</i> <i
																				class="del">删除</i>
																		</div>
																	</li>
																	<li class="" data-index="5"><input
																		name="image_pos" type="hidden" value="5">
																		<div class="preview">
																			<input type="hidden" class="hideimageurl"
																				name="picUrl5" value="">
																		</div>
																		<div class="operate">
																			<i class="toleft">左移</i> <i class="toright">右移</i> <i
																				class="del">删除</i>
																		</div>
																	</li>
																</ul>
															</div>
														</div>
													</div>
												</div>
												
											</div>
										
										</div></li>
									
                 <li id="desc" class="fieldset">
                <label class="caption">宝贝描述：</label>
                <div class="fields-box" id="J_DescEditor">

            <ul class="tab tab-nav">
	            <li class="selected"><a href="#J_ItemEditor" class="pc" data-spm-click="gostr=/tbsell;locaid=d201312051539">电脑端</a></li>
	           
	        </ul>
       		<div class="tab-content">
                   
                	<script type="text/plain" id="editor" style="width:640px;height:492px;"></script>
   		
                   
		            <script type="text/javascript">
    	//实例化编辑器
    	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
     	var ue = UE.getEditor('editor');
     	    //屏蔽自动高度
     	    UE.getEditor('editor', {
                  autoHeight: false
            });
            //读取内容 TODO
            var ue = UE.getContent();
              </script>
        	</div>
        	
            <div id="J_MobileEditor" class="tab-pannel mobileEditor" style="display: none;">
            		            	<ul class="desc-tab">
	            	<li class="selected">
	            		<a href="#J_EditorBox"><label><input type="radio" class="radio-desc" name="_fma.pu._0.w" value="0" checked="checked">文本编辑</label></a></li>
	            		<li class="">	
	            			<a href="#J_ShenbiWlBox"><label><input type="radio" class="radio-desc" name="_fma.pu._0.w" value="1">模板编辑</label><i class="new">new</i></a>
	            		</li>
	            		<li><i class="icon icon-tip"></i>两种方式编辑内容不混合，发布宝贝时只应用当前编辑器内容。<a class="link-blue" href="//bangpai.taobao.com/group/thread/16217698-305362542.htm" target="_blank">详情查看 </a></li>
	            	</ul>
            	            	<div class="tab-content-desc">
            		<div id="J_EditorBox" class="tab-pannel-desc editorBox">
			            <div class="mszie-tips"><span class="size-tip" id="J_SizeTip">图片大小不得超过<b>2560KB</b> <i>|</i> 字数不得超过<b>5000</b></span><span class="preview-mobile-detail"><span class="mtips-icon" id="mtips-icon">在手机上预览真实效果</span></span></div>
			            <div class="mdetail-left">
			                <div class="pannel">
			                    <p class="detail-title" id="J_MdetailTitle"><span class="detail-title1">图文详情</span><span class="import-detail">导入电脑端宝贝详情</span></p>
			                    <div class="content-edit">
			                        <div class="edit-area" id="J_EditSummaryArea"></div>
			                        <div class="control-panel"></div>
			                        <div class="edit-area" id="J_EditArea"></div>
			                    </div>
			                    			                    <textarea id="J_MobileDetail" name="_fma.pu._0.wi" style="display:none;"></textarea>
			                    		<input type="hidden" id="nav_wirelessDescription" data-feed="err_nav_wirelessDescription">
		<div id="err_nav_wirelessDescription" style="display:none"></div>
				                </div>
			                <div class="add-btn" id="J_AddBtn">
			                        <div class="jia">添加</div>
		                        	<ul class="btn-wrap none">
		                            	<li class="audio" title="点击添加音频，一个宝贝只能上传一个音频"><div class="btn-text"><i></i><p>音频</p></div><div class="overlay none">一个宝贝只能上传<b>1</b>个音频</div></li>
		                            	<li class="shortDesc" title="点击添加摘要，一个宝贝只能添加一段摘要"><div class="btn-text"><i></i><p>摘要</p></div><div class="overlay none">一个宝贝只能添加<b>1</b>段摘要</div></li>
		                            	<li class="image" title="点击添加图片"><div class="btn-text"><i></i><p>图片</p></div></li>
		                            	<li class="text" title="点击添加文字"><div class="btn-text"><i></i><p>文字</p></div></li>
		                        	</ul>
			                </div>
			            </div>
			            <div class="explain">



			            </div>
			            <div class="edit-area" id="J_EditIframeArea"></div>
			        </div>
			        		         		<div id="J_ShenbiWlBox" class="tab-pannel-desc shenbiBox" style="display:none;"><div class="magicpen-box" style="width: 320px; height: 498px;"><div class="magicpen-title"><a href="javascript:;" class="shenbi-close"></a><a href="javascript:;" style="display: inline;" id="J_shenbiUpdateshenbi_2" class="shenbi-update">修改</a>淘宝神笔宝贝详情编辑器</div><iframe id="J_shenbiFrame_2" width="100%" frameborder="0" src="//sell.xiangqing.taobao.com/sell/start.html?itemId=522147487657&amp;clientType=1&amp;actionStatus=0&amp;frameId=shenbi_2&amp;" style="height: 448px;"></iframe><iframe style="display:none;" id="J_shenbiFrame_pob_2" width="100%" frameborder="0" src="about:blank;"></iframe></div></div>
           			           		</div>
        	</div>
      
    </div></li>
    
	<li id="autoDiv1" class="hidden">
	<ul>
	<li>
		<label>自动发售：</label>
		<span>
			<div>
												<ul class="ul-radio">
					<li style="display:none">
						<input type="radio" class="radio" value="true" name="_fma.pu._0.aut" id="autoCC1">
						<label for="autoCC1">系统自动发售</label>
					</li>
					<li style="display:none">
						<input type="radio" class="radio" checked="checked" value="false" name="_fma.pu._0.aut" id="autoCC2">
						<label for="autoCC2">不采用系统自动发售</label>
					</li>
				</ul>
				<span style="font-size:12px; color:#F00;" id="checkHint">
					提示：请按照正确格式正确填写密码和卡号。
				</span>
								<div style="position:relative;zoom:1">
					<b>输入方式</b>
					<br>
					<span>
						1.请按照卡号+密码（即“卡号【空格】密码“）的格式正确填写，每行只能填入一条信息
						<a href="//service.taobao.com/support/knowledge-1119683.htm#1119683c">查看演示</a>
					</span>
					<span>
						2.卡号和密码不允许输入任何中文和其他特殊字符
					</span>
				</div>
				<span style="font-size:12px; color:#F00;">
									</span>
				<div style="position:relative;zoom:1">
					<textarea id="autoArea" name="_fma.pu._0.auto" style="width:360px;height:240px"></textarea>
					<i class="J_PopTip poptip-attention">
					<b>提示信息：</b>
					<br>
					<span>1.请不要随意使用非淘宝认可的第三方软件进行任何自动发货的相关操作，如有使用该类第三方软件引起卡密丢失或被盗，淘宝不承担任何责任。</span>
					<br>
					<span>2.为了避免突发事件给您带来的不必要损失，请您在输入卡密时做好相关卡密备份工作。如由您未及时备份造成的卡密意外损失，淘宝将概不负责!</span>
					</i>
				</div>
				<input type="button" id="autoCheck" name="autoCheck" value="校验格式">
				<br>
			</div>
								<input type="hidden" id="nav_autoArea" data-feed="err_nav_autoArea">
		<div id="err_nav_autoArea" style="display:none"></div>
			</span>
	</li>
	<li>
		<!--增加自动发货充值地址一栏-->
		<label for="autoConsignmentUrl">充值地址：</label>
		<span>
			<em>*</em>
			<div>
				<input id="autoConsignmentUrl" name="_fma.pu._0.autoc" value="" size="70" type="text" class="text">
				<span class="prop-tips">150字符以内</span>
							</div>
								<input type="hidden" id="nav_autoConsignmentUrl" data-feed="err_nav_autoConsignmentUrl">
		<div id="err_nav_autoConsignmentUrl" style="display:none"></div>
			</span>
	</li>
			</ul>
	</li>
		<li id="nav_shop_cat">
		<label>在店铺中所<br>属的分类：</label>
		<span>
			<!-- shop-cat-list -->
			<div class="shop-cat-list">
				<!-- feilv 2012-03-02 店铺分类新加钩子J_ShopCatList -->
				<ul class="J_ShopCatList">
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1093580805" name="shopCat" value="1093580805">
								<label for="shopCatId1093580805">帆布鞋</label>
													</li>
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1093580806" name="shopCat" value="1093580806">
								<label for="shopCatId1093580806">单鞋</label>
													</li>
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1093580807" name="shopCat" value="1093580807">
								<label for="shopCatId1093580807">高帮鞋</label>
													</li>
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1093580808" name="shopCat" value="1093580808">
								<label for="shopCatId1093580808">豆豆鞋</label>
													</li>
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1093580809" name="shopCat" value="1093580809">
								<label for="shopCatId1093580809">情侣鞋</label>
													</li>
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1093581255" name="shopCat" value="1093581255">
								<label for="shopCatId1093581255">棉靴</label>
													</li>
											<li>
															<input type="checkbox" class="checkbox" id="shopCatId1094545521" name="shopCat" value="1094545521">
								<label for="shopCatId1094545521">老北京布鞋</label>
													</li>
									</ul>
			</div>
			<!-- shop-cat-list -->
		</span>
	</li>
</ul>
</div>								
																
																									
<h5 id="logisticsTit">
 2. 宝贝物流及安装服务 </h5>
<div data-spm="1000771" id="logisticsDiv" class="form">
<ul>
	<li id="transport" class="fieldset" data-robot-helper="2">
		<label class="caption"> 运费：</label>
		<div class="fields-box">
			
			                <div class="transport-item logis" id="J_Logistics">
                    
                    <select id="J_deliverTemplate" name="_fma.pu._0.po">
                    <option value="0">请选择运费模板</option><option value="1838535720">瑞安市康力鞋业有限公司浙江温州免运费</option><option value="1851122880">福建泉州申通免运费</option><option value="1878284391">福建宁德部分免运费</option><option value="1865157230">浙江温州免运费</option><option value="1876581820">福建 宁德免费云</option><option value="1849839191">河北保定汇通韵达</option><option value="1860844070">广东深圳中通免运费</option><option value="1893587711">浙江台州部分免运费</option><option value="1894781481">浙江温州部分地区免运费</option><option value="1809703270">免运费</option><option value="1838904861">瑞安市康力鞋业有限公司浙江温州汇通</option><option value="1853515670">四川成都免运费</option><option value="1809691231">广州深圳中通</option><option value="1876480231">河北保定汇通韵达部分包邮</option><option value="1849199520">河北保定汇通韵达免运费</option></select>
                    <a href="#test-link" class="btn btn-default" id="J_ManageLogisTpl">
                        <span class="btn-txt">新建运费模板</span>
                    </a>
                    <i class="J_PopTip poptip-attention">若无法成功新增运费模板，请您到卖家中心左侧物流工具设置新增运费模板后，再到该页面重新设置新增运费模板即可。</i>
                    <i class="J_PopTip poptip-help">为了让消费者更好的购物体验，淘宝将要求全网商品设置运费模板，运费模板使用<a href="//i.daxue.taobao.com/study/video/detail.htm?courseId=8977" target="_blank">教程</a></i>
                                        <div id="J_Freight" class="hint-popup" style="display: none;">
                        <div class="hint-contentbox  hint-default">
                            <div class="logis-switch">
                            </div>
                            <div class="logis-content">
                            </div>
                        </div>
                        <div class="deliver-warn">
                        </div>
                                            </div>
                    		<input type="hidden" id="nav_postageid" data-feed="err_nav_postageid">
		<div id="err_nav_postageid" style="display:none"></div>
	                   
                </div>
														<input type="hidden" id="nav_deliveryWay" data-feed="err_nav_deliveryWay">
		<div id="err_nav_deliveryWay" style="display:none"></div>
																
											</div>
	</li>
	
		
			
	
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        				    		<li class="extend-properties">
		<label class="extend-name ">物流参数：</label>
		 <div class="extend-info extend-border">
			 <div class="extend-box">
													 					 					 					 					 					 
					 					 					 												            																						    											
						    								<div data-child="sub-129" data-type="number" data-range="" class="extend-field">
																																								<label class="" for="field-129">物流体积(m3)：</label>
                <input name="field-129" id="field-129" class="text" type="text" value="">
							     </div>
    					
    					    								 	 	 
	 	 					            																						    											
						    								<div data-child="sub-128" data-type="number" data-range="" class="extend-field">
																																								<label class="" for="field-128">物流重量(Kg)：</label>
                <input name="field-128" id="field-128" class="text" type="text" value="">
							     </div>
    					
    					    								 	 	 
	 	 					            				 </div>
		 </div>
		 </li>
		 <input type="hidden" id="nav_30" data-feed="err_nav_30">
		 <div id="err_nav_30" style="display:none"></div>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                

</ul>
</div>																								    								    								<h5 id="saleServiceTit">
 3. 售后保障信息 </h5>
<div id="saleServicediv" class="form J_DetectTrigger" name="nav_afterSale" data-detect="afterSale">
					
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		    <li id="inv">
		<label>发票：</label>
		<span>
			<ul class="ul-radio">
									<li><input type="radio" class="radio" checked="checked" value="0" name="_fma.pu._0.ha" id="invoiceN"> <label for="invoiceN">无</label></li>
								<li><input type="radio" class="radio" value="1" name="_fma.pu._0.ha" id="invoiceY"> <label for="invoiceY">有</label></li>
			</ul>
								<input type="hidden" id="nav_haveInvoice" data-feed="err_nav_haveInvoice">
		<div id="err_nav_haveInvoice" style="display:none"></div>
			</span>
	</li>
	<li id="wy">
		<label>保修：</label>
		<span>
			<ul class="ul-radio">
				<div class="">
					<li><input type="radio" class="radio" checked="checked" value="0" name="_fma.pu._0.hav" id="warrantyN"> <label for="warrantyN">无</label></li>
				</div>
				<li><input type="radio" class="radio" value="1" name="_fma.pu._0.hav" id="warrantyY">
					<label for="warrantyY">有</label>
				</li>
			</ul>
								<input type="hidden" id="nav_haveGuarantee" data-feed="err_nav_haveGuarantee">
		<div id="err_nav_haveGuarantee" style="display:none"></div>
			</span>
	</li>
			<li id="mjcn">
			 <label for="J_sellPromise">退换货承诺：</label>
			 <span>
				<input id="J_sellPromise" value="1" name="_fma.pu._0.sel" type="checkbox" class="checkbox" checked="">
				凡使用支付宝服务付款购买本店商品，若存在质量问题或与描述不符，本店将主动提供退换货服务并承担来回邮费!
			 </span>
		</li>
			<li id="serviceAssurance">
		<label>服务保障：</label>
		<span>
			<input type="checkbox" id="J_serviceCheckbox" class="checkbox" checked="true" disabled="">
			<input type="hidden" id="J_newPrepayTag" name="new_prepay_tag" value="0">
			<small>
							该商品品类须支持“七天退货”服务；承诺更好服务可通过<a href="//xiaobao.taobao.com/contract/item_contract.htm?crtId=2" target="_blank">交易合约</a>设置
						</small>
		</span>
	</li>
	<script type="text/javascript">
		Sell.Config.set("serviceAssurance", {
			link:  "//xiaobao.taobao.com/contract/item_contract.htm?crtId=2"
		});
	</script>
						<li id="mjcn">
			 <label for="T_pledge">鞋类三包：</label>
			 <span>
				<input id="T_pledge" value="1" name="_fma.pu._0.t" class="checkbox" type="checkbox">
				<i class="J_PopTip poptip-attention&gt;" 鞋类三包<span="">(支持7天包退,15天包换,30天至120天保修)</i></span><i class="J_PopTip poptip-attention&gt;" 鞋类三包<span=""> <a href="#" target="_blank" onclick="return false">鞋类三包规则详情</a> <font color="red">　　New!</font></i>
			 
		</li>
			</div>
																								<h5 id="otherinfoTitle1" style="display:none">
 3. 其他信息 </h5>
<h5 id="otherinfoTitle0">
 4. 其他信息 </h5>
<div data-spm="1000772" class="form J_DetectTrigger" id="nav_otherInfo" data-detect="otherInfo">
	<ul>
						<!-- xinpin: "" open-->
																																										<!-- "false" "true" "true"-->
				<li id="auctionPromoted2">
						<label>会员打折：</label>
			<span>
				<ul class="ul-radio">
					<li><input type="radio" class="radio" id="notAutoPromoted" value="false" name="_fma.pu._0.isop"><label for="notAutoPromoted">不参与会员打折</label></li>
					<li><input type="radio" class="radio" id="isAutoPromoted" checked="checked" value="true" name="_fma.pu._0.isop"><label for="isAutoPromoted">参与会员打折</label></li>
				</ul>
			</span>
		</li>
							    			<li id="subStockAtBuyDiv">
    				<label>库存计数：</label>
    				<span>
    					<ul class="ul-radio ul-radio-vertical">
    						<li><input type="radio" class="radio" name="_fma.pu._0.su" id="subStockAtBuy1" value="1" checked="checked "><label for="subStockAtBuy1">拍下减库存</label>
    							    							<i class="J_PopTip poptip-help">买家拍下商品即减少库存，存在<a target="blank" href="//service.taobao.com/support/knowledge-5104445.htm?spm=0.0.0.122.83ee7e">恶拍</a>风险。秒杀、超低价等热销商品，如需避免超卖可选此方式</i>
    						</li>
    						<li><input type="radio" class="radio" name="_fma.pu._0.su" id="subStockAtBuy0" value="0"><label for="subStockAtBuy0">付款减库存</label>
    							    							<i class="J_PopTip poptip-help"> 买家拍下并完成付款方减少库存，存在<a target="blank" href="//service.taobao.com/support/knowledge-1110945.htm?spm=a220z.1000885.0.103.c88ca1">超卖</a>风险。如需减少恶拍、提高回款效率，可选此方式</i>
    						</li>
    					</ul>
    							<input type="hidden" id="nav_subStockAtBuy" data-feed="err_nav_subStockAtBuy">
		<div id="err_nav_subStockAtBuy" style="display:none"></div>
	    				</span>
    			</li>
    									<li id="J_AuctionExpiry">
				<label>有效期：</label>
				<span>
    				<ul class="ul-radio">
    					<li><input type="radio" class="radio" id="durationId7" name="_fma.pu._0.du" value="7" checked="checked"><label for="durationId7">7天</label></li>
						<li>
							 <span class="msg" style="display:inline-block;">
								<span class="tips">即日起全网一口价宝贝的有效期统一为7天</span>
                             </span>
						</li>
						    				</ul>
												<input type="hidden" id="nav_duration" data-feed="err_nav_duration">
		<div id="err_nav_duration" style="display:none"></div>
					</span>
				</li>
				<li>
																															<label>开始时间：</label>
			<span id="J_publish-date">
				<input type="hidden" name="_fma.pu._0.sta" id="J_StartDate" value="">
				<input type="hidden" name="_fma.pu._0.auct" id="J_ItemStatus" value="0">
				<ul class="ul-radio ul-radio-vertical">
					<li><input type="radio" class="radio" name="_now" value="0" id="_now0" checked="checked"><label for="_now0">立刻</label></li>
					<li>
						<input type="radio" class="radio" name="_now" value="1" id="_now1">
						<label for="_now1">设定</label>
						<select name="_date" disabled="disabled"><option value="2015-9-21">2015年9月21日</option><option value="2015-9-22">2015年9月22日</option><option value="2015-9-23">2015年9月23日</option><option value="2015-9-24">2015年9月24日</option><option value="2015-9-25">2015年9月25日</option><option value="2015-9-26">2015年9月26日</option><option value="2015-9-27">2015年9月27日</option><option value="2015-9-28">2015年9月28日</option><option value="2015-9-29">2015年9月29日</option><option value="2015-9-30">2015年9月30日</option><option value="2015-10-1">2015年10月1日</option><option value="2015-10-2">2015年10月2日</option><option value="2015-10-3">2015年10月3日</option><option value="2015-10-4">2015年10月4日</option><option value="2015-10-5">2015年10月5日</option></select>
						<select name="_hour" disabled="disabled"><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option></select>
						时
						<select name="_minute" disabled="disabled"><option value="50">50</option><option value="55">55</option></select>
						分
												<i class="J_PopTip poptip-help" id="durainfo">您可以设定宝贝的正式开始销售时间</i>
		
					</li>
					<li><input type="radio" class="radio" name="_now" value="2" id="inStock"><label for="inStock">放入仓库</label></li>
				</ul>
						<input type="hidden" id="nav_startStr" data-feed="err_nav_startStr">
		<div id="err_nav_startStr" style="display:none"></div>
							<input type="hidden" id="nav_auctionStatus" data-feed="err_nav_auctionStatus">
		<div id="err_nav_auctionStatus" style="display:none"></div>
				</span>
		</li>
					<li id="secKillDiv">
				<label>秒杀商品：</label>
				<span>
					<ul class="ul-radio">
						<li><input type="checkbox" class="checkbox" name="_fma.pu._0.se" value="web" id="secKillWeb"><label for="secKillWeb">电脑用户</label></li>
						<li><input type="checkbox" class="checkbox" name="_fma.pu._0.se" value="wap" id="secKillWap"><label for="secKillWap">手机用户</label></li>
						<li>
							<span class="msg" id="secKillTipTrue" style="display:none;">
								<span class="tips">勾选后商品将无“购物车”功能，且单次只能购买一件，<a target="_blank" href="//bangpai.taobao.com/group/thread/15301123-291147729.htm">点此查看详情</a></span>
							</span>
							<span class="msg" id="secKillTipFalse" style="display:inline-block;">
																<i class="J_PopTip poptip-attention">若此商品参加秒杀活动，在此期间内必须设为秒杀商品，以防止作弊</i>
							</span>
						</li>
					</ul>
												<input type="hidden" id="nav_secKill" data-feed="err_nav_secKill">
		<div id="err_nav_secKill" style="display:none"></div>
					</span>
			</li>
						<li>
			<label>橱窗推荐：</label>
			<span>
				<ul class="ul-checkbox">
												<li>
								<input type="checkbox" class="checkbox" value="1" name="_fma.pu._0.prom" id="promoted" checked="checked">
								<label for="promoted">是</label>
								<span class="prop-tips">
									橱窗是提供给卖家的免费广告位，
									<a target="_blank" href="//bbs.taobao.com/catalog/thread/16029511-268018972-1.htm?spm=0.0.0.0.47l3Te ">了解如何获得更多橱窗位</a>
								</span>
							</li>
													</ul>
																																		<input type="hidden" id="nav_promotedStatus" data-feed="err_nav_promotedStatus">
		<div id="err_nav_promotedStatus" style="display:none"></div>
									</span>
								</li>
																<!-- 无名良品卖家设置支付宝的积分宝返点比例 -->
																								
																																								
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
																</ul>
								</div>														<div class="submit">
								<div class="float-submitbar">
																	<button type="submit" data-name="event_submit_do_publish" class="J_Submit btn btn-main-primary btn-submit" id="event_submit_do_publish" value="发布">
                                        <span class="btn-txt">发布</span>
                                    </button>
									<input type="hidden" id="J_EventHandler" value="anything">
			                    								</div>
							</div>
						</div>
					</div>
					</div>
					<div class="ft">
						<p class="form-tip"><em>*</em> 表示该项必填</p>
					</div>
					<span class="rc-bt"><span></span></span>
							                    	

			</div>
		</form>
</div>
</body>
</html>
