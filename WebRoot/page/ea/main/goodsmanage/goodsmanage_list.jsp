<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>物品管理</title>
<!--tree-->
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/goodsmanage/goodsmanage_list.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>js/ea/ccompany/ccode/ccodes_manger.js"></script>
 --%><link rel="stylesheet" href="<%=basePath%>/css/css.css" />

<style type="text/css">

.xx{
	color:#FF0000;
	margin-right:2px;}
a:hover {
font-weight:bold;
font-size:12px; 
}

.text_tree{
width:148px;
height:300px;
border:solid 1px #a8c7ce;
margin-left:2px;
background-color: white;
}
</style>
<script type="text/javascript">
var basic="${basic}";
var basMap="${basMap}";
var basePath="<%=basePath%>";
var basicMap='${basicMap}';
var pNumber='${pageNumber}';
var codeHylb="${codeHylb}";
var date;
var iid='${iid}';
var iids='';
var treeid;
var treename;
var subjectsNumbers;
var tree;
var tree1;
var token = 0 ;
var basePath = "<%=basePath%>";
var personvalue = "";
var personurl = "";
var staffName;
var staffsize ;//后台验证身份证时应该查到的人数
var goodsID = '';
var ppageNumber =${pageNumber};
var  search='${search}';  
var cName='${result}';
var cID='${parameter}';
var pName='${pName}';
var chejiahao = ""; //车架号赋值
var zhubanhao = ""; //主板号赋值
var key2code = {65:"a",66:"b",67:"c",68:"d",69:"e",70:"f",71:"g",72:"h",73:"i",74:"j", 
75:"k",76:"l",77:"m",78:"n",79:"o",80:"p",81:"q",82:"r",83:"s",84:"t", 
85:"u",86:"v",87:"w",88:"x",89:"y",90:"z",49:"1",50:"2",51:"3",52:"4", 
53:"5",54:"6",55:"7",56:"8",57:"9",48:"0" 
}; 

var spell = {0xB0A1:"a", 0xB0A3:"ai", 0xB0B0:"an", 0xB0B9:"ang", 0xB0BC:"ao", 0xB0C5:"ba", 0xB0D7:"bai", 0xB0DF:"ban", 0xB0EE:"bang", 0xB0FA:"bao", 0xB1AD:"bei", 0xB1BC:"ben", 0xB1C0:"beng", 0xB1C6:"bi", 0xB1DE:"bian", 0xB1EA:"biao", 0xB1EE:"bie", 0xB1F2:"bin", 0xB1F8:"bing", 0xB2A3:"bo", 0xB2B8:"bu", 0xB2C1:"ca", 0xB2C2:"cai", 0xB2CD:"can", 0xB2D4:"cang", 0xB2D9:"cao", 0xB2DE:"ce", 0xB2E3:"ceng", 0xB2E5:"cha", 0xB2F0:"chai", 0xB2F3:"chan", 0xB2FD:"chang", 0xB3AC:"chao", 0xB3B5:"che", 0xB3BB:"chen", 0xB3C5:"cheng", 0xB3D4:"chi", 0xB3E4:"chong", 0xB3E9:"chou", 0xB3F5:"chu", 0xB4A7:"chuai", 0xB4A8:"chuan", 0xB4AF:"chuang", 0xB4B5:"chui", 0xB4BA:"chun", 0xB4C1:"chuo", 0xB4C3:"ci", 0xB4CF:"cong", 0xB4D5:"cou", 0xB4D6:"cu", 0xB4DA:"cuan", 0xB4DD:"cui", 0xB4E5:"cun", 0xB4E8:"cuo", 0xB4EE:"da", 0xB4F4:"dai", 0xB5A2:"dan", 0xB5B1:"dang", 0xB5B6:"dao", 0xB5C2:"de", 0xB5C5:"deng", 0xB5CC:"di", 0xB5DF:"dian", 0xB5EF:"diao", 0xB5F8:"die", 0xB6A1:"ding", 0xB6AA:"diu", 0xB6AB:"dong", 0xB6B5:"dou", 0xB6BC:"du", 0xB6CB:"duan", 0xB6D1:"dui", 0xB6D5:"dun", 0xB6DE:"duo", 0xB6EA:"e", 0xB6F7:"en", 0xB6F8:"er", 0xB7A2:"fa", 0xB7AA:"fan", 0xB7BB:"fang", 0xB7C6:"fei", 0xB7D2:"fen", 0xB7E1:"feng", 0xB7F0:"fo", 0xB7F1:"fou", 0xB7F2:"fu", 0xB8C1:"ga", 0xB8C3:"gai", 0xB8C9:"gan", 0xB8D4:"gang", 0xB8DD:"gao", 0xB8E7:"ge", 0xB8F8:"gei", 0xB8F9:"gen", 0xB8FB:"geng", 0xB9A4:"gong", 0xB9B3:"gou", 0xB9BC:"gu", 0xB9CE:"gua", 0xB9D4:"guai", 0xB9D7:"guan", 0xB9E2:"guang", 0xB9E5:"gui", 0xB9F5:"gun", 0xB9F8:"guo", 0xB9FE:"ha", 0xBAA1:"hai", 0xBAA8:"han", 0xBABB:"hang", 0xBABE:"hao", 0xBAC7:"he", 0xBAD9:"hei", 0xBADB:"hen", 0xBADF:"heng", 0xBAE4:"hong", 0xBAED:"hou", 0xBAF4:"hu", 0xBBA8:"hua", 0xBBB1:"huai", 0xBBB6:"huan", 0xBBC4:"huang", 0xBBD2:"hui", 0xBBE7:"hun", 0xBBED:"huo", 0xBBF7:"ji", 0xBCCE:"jia", 0xBCDF:"jian", 0xBDA9:"jiang", 0xBDB6:"jiao", 0xBDD2:"jie", 0xBDED:"jin", 0xBEA3:"jing", 0xBEBC:"jiong", 0xBEBE:"jiu", 0xBECF:"ju", 0xBEE8:"juan", 0xBEEF:"jue", 0xBEF9:"jun", 0xBFA6:"ka", 0xBFAA:"kai", 0xBFAF:"kan", 0xBFB5:"kang", 0xBFBC:"kao", 0xBFC0:"ke", 0xBFCF:"ken", 0xBFD3:"keng", 0xBFD5:"kong", 0xBFD9:"kou", 0xBFDD:"ku", 0xBFE4:"kua", 0xBFE9:"kuai", 0xBFED:"kuan", 0xBFEF:"kuang", 0xBFF7:"kui", 0xC0A4:"kun", 0xC0A8:"kuo", 0xC0AC:"la", 0xC0B3:"lai", 0xC0B6:"lan", 0xC0C5:"lang", 0xC0CC:"lao", 0xC0D5:"le", 0xC0D7:"lei", 0xC0E2:"leng", 0xC0E5:"li", 0xC1A9:"lia", 0xC1AA:"lian", 0xC1B8:"liang", 0xC1C3:"liao", 0xC1D0:"lie", 0xC1D5:"lin", 0xC1E1:"ling", 0xC1EF:"liu", 0xC1FA:"long", 0xC2A5:"lou", 0xC2AB:"lu", 0xC2BF:"lv", 0xC2CD:"luan", 0xC2D3:"lue", 0xC2D5:"lun", 0xC2DC:"luo", 0xC2E8:"ma", 0xC2F1:"mai", 0xC2F7:"man", 0xC3A2:"mang", 0xC3A8:"mao", 0xC3B4:"me", 0xC3B5:"mei", 0xC3C5:"men", 0xC3C8:"meng", 0xC3D0:"mi", 0xC3DE:"mian", 0xC3E7:"miao", 0xC3EF:"mie", 0xC3F1:"min", 0xC3F7:"ming", 0xC3FD:"miu", 0xC3FE:"mo", 0xC4B1:"mou", 0xC4B4:"mu", 0xC4C3:"na", 0xC4CA:"nai", 0xC4CF:"nan", 0xC4D2:"nang", 0xC4D3:"nao", 0xC4D8:"ne", 0xC4D9:"nei", 0xC4DB:"nen", 0xC4DC:"neng", 0xC4DD:"ni", 0xC4E8:"nian", 0xC4EF:"niang", 0xC4F1:"niao", 0xC4F3:"nie", 0xC4FA:"nin", 0xC4FB:"ning", 0xC5A3:"niu", 0xC5A7:"nong", 0xC5AB:"nu", 0xC5AE:"nv", 0xC5AF:"nuan", 0xC5B0:"nue", 0xC5B2:"nuo", 0xC5B6:"o", 0xC5B7:"ou", 0xC5BE:"pa", 0xC5C4:"pai", 0xC5CA:"pan", 0xC5D2:"pang", 0xC5D7:"pao", 0xC5DE:"pei", 0xC5E7:"pen", 0xC5E9:"peng", 0xC5F7:"pi", 0xC6AA:"pian", 0xC6AE:"piao", 0xC6B2:"pie", 0xC6B4:"pin", 0xC6B9:"ping", 0xC6C2:"po", 0xC6CB:"pu", 0xC6DA:"qi", 0xC6FE:"qia", 0xC7A3:"qian", 0xC7B9:"qiang", 0xC7C1:"qiao", 0xC7D0:"qie", 0xC7D5:"qin", 0xC7E0:"qing", 0xC7ED:"qiong", 0xC7EF:"qiu", 0xC7F7:"qu", 0xC8A6:"quan", 0xC8B1:"que", 0xC8B9:"qun", 0xC8BB:"ran", 0xC8BF:"rang", 0xC8C4:"rao", 0xC8C7:"re", 0xC8C9:"ren", 0xC8D3:"reng", 0xC8D5:"ri", 0xC8D6:"rong", 0xC8E0:"rou", 0xC8E3:"ru", 0xC8ED:"ruan", 0xC8EF:"rui", 0xC8F2:"run", 0xC8F4:"ruo", 0xC8F6:"sa", 0xC8F9:"sai", 0xC8FD:"san", 0xC9A3:"sang", 0xC9A6:"sao", 0xC9AA:"se", 0xC9AD:"sen", 0xC9AE:"seng", 0xC9AF:"sha", 0xC9B8:"shai", 0xC9BA:"shan", 0xC9CA:"shang", 0xC9D2:"shao", 0xC9DD:"she", 0xC9E9:"shen", 0xC9F9:"sheng", 0xCAA6:"shi", 0xCAD5:"shou", 0xCADF:"shu", 0xCBA2:"shua", 0xCBA4:"shuai", 0xCBA8:"shuan", 0xCBAA:"shuang", 0xCBAD:"shui", 0xCBB1:"shun", 0xCBB5:"shuo", 0xCBB9:"si", 0xCBC9:"song", 0xCBD1:"sou", 0xCBD4:"su", 0xCBE1:"suan", 0xCBE4:"sui", 0xCBEF:"sun", 0xCBF2:"suo", 0xCBFA:"ta", 0xCCA5:"tai", 0xCCAE:"tan", 0xCCC0:"tang", 0xCCCD:"tao", 0xCCD8:"te", 0xCCD9:"teng", 0xCCDD:"ti", 0xCCEC:"tian", 0xCCF4:"tiao", 0xCCF9:"tie", 0xCCFC:"ting", 0xCDA8:"tong", 0xCDB5:"tou", 0xCDB9:"tu", 0xCDC4:"tuan", 0xCDC6:"tui", 0xCDCC:"tun", 0xCDCF:"tuo", 0xCDDA:"wa", 0xCDE1:"wai", 0xCDE3:"wan", 0xCDF4:"wang", 0xCDFE:"wei", 0xCEC1:"wen", 0xCECB:"weng", 0xCECE:"wo", 0xCED7:"wu", 0xCEF4:"xi", 0xCFB9:"xia", 0xCFC6:"xian", 0xCFE0:"xiang", 0xCFF4:"xiao", 0xD0A8:"xie", 0xD0BD:"xin", 0xD0C7:"xing", 0xD0D6:"xiong", 0xD0DD:"xiu", 0xD0E6:"xu", 0xD0F9:"xuan", 0xD1A5:"xue", 0xD1AB:"xun", 0xD1B9:"ya", 0xD1C9:"yan", 0xD1EA:"yang", 0xD1FB:"yao", 0xD2AC:"ye", 0xD2BB:"yi", 0xD2F0:"yin", 0xD3A2:"ying", 0xD3B4:"yo", 0xD3B5:"yong", 0xD3C4:"you", 0xD3D9:"yu", 0xD4A7:"yuan", 0xD4BB:"yue", 0xD4C5:"yun", 0xD4D1:"za", 0xD4D4:"zai", 0xD4DB:"zan", 0xD4DF:"zang", 0xD4E2:"zao", 0xD4F0:"ze", 0xD4F4:"zei", 0xD4F5:"zen", 0xD4F6:"zeng", 0xD4FA:"zha", 0xD5AA:"zhai", 0xD5B0:"zhan", 0xD5C1:"zhang", 0xD5D0:"zhao", 0xD5DA:"zhe", 0xD5E4:"zhen", 0xD5F4:"zheng", 0xD6A5:"zhi", 0xD6D0:"zhong", 0xD6DB:"zhou", 0xD6E9:"zhu", 0xD7A5:"zhua", 0xD7A7:"zhuai", 0xD7A8:"zhuan", 0xD7AE:"zhuang", 0xD7B5:"zhui", 0xD7BB:"zhun", 0xD7BD:"zhuo", 0xD7C8:"zi", 0xD7D7:"zong", 0xD7DE:"zou", 0xD7E2:"zu", 0xD7EA:"zuan", 0xD7EC:"zui", 0xD7F0:"zun", 0xD7F2:"zuo"} 
var spellArray = new Array() 
var pn = "" 

/* function getPID() {
    	if(document.getElementById("jqModel2").style.display=="none"){
			$("#jqModel2").show();
		}else{
			$("#jqModel2").hide();
		}
} */
$(function(){
        $(window).resize(function(){
             setTimeout(function () {                 
                 $("#aadTree1").height($(window).height()- 150);
             },100);
         });
         $("#aadTree1").height($(window).height()- 150);
     });
</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="200" align="center">
							公司名称
						</th>
						<th width="100" align="center">
							品名编号
						</th>
						<th width="100" align="center">
							品名名称
						</th>
						<th width="100" align="center">
							科目
						</th>
						<th width="100" align="center">
							条码号
						</th>
						<th width="100" align="center">
							芯片号
						</th>
						<th width="100" align="center">
							主板号/发动机号
						</th>
						<th width="100" align="center">
							行业类别
						</th>
						<th width="100" align="center">
							物品分类
						</th>
						<th width="150" align="center">
							单位换算
						</th>
						<th width="80" align="center">
							默认规格
						</th>
						<th width="100" align="center">
							品牌规格
						</th>
						<th width="100" align="center">
							型号
						</th>
						<th width="100" align="center">
							车架号/机壳号
						</th>
						<th width="80" align="center">
							产品LOGO
						</th>
						<th width="80" align="center">
							产品主题
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${goodsID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${goodsID}" />
							</td>
							<td>
								<span id="companyName">${companyName}</span>
							</td>
							<td>
								<span id="goodsCoding">${goodsCoding}</span>
							</td>
							<td>
								<span id="goodsName">${goodsName}</span>
							</td>
							<td>
								<span id="subjectsName">${subjectsName}</span>
							</td>
							<td>
								<span id="barCode">${barCode}</span>
							</td>
                                  					<td>
								<span id="defaultStorage">${defaultStorage}</span>
							</td>
							<td>
								<span id="mnemonicCode">${mnemonicCode}</span>
							</td>
							<td>
								<span id="typeID">${typeID}</span>
							</td>
							<td>
								<span id="tradeCode">${tradeCode}</span>
							</td>
							<td>
								<span id="goodsvariable">
								${goodsvariable}
								</span>
							</td>
							<td>
								<span id="acquiesceStandard">${acquiesceStandard}</span>
							</td>
							<td>
								<span id="standard">${standard}</span>
							</td>
							<td>
								<span id="model">${model}</span>
							</td>
							<td>
								<span id="manufacturers" >${manufacturers}</span>
								<span id="num" style="display: none">${num}</span>
								<span id="variableID" style="display: none">${variableID}</span>
								<span id="num1" style="display: none">${num1}</span>
								<span id="variable1ID" style="display: none">${variable1ID}</span>
								<span id="num2" style="display: none">${num2}</span>
								<span id="variable2ID" style="display: none">${variable2ID}</span>
								<span id="num3" style="display: none">${num3}</span>
								<span id="variable3ID" style="display: none">${variable3ID}</span>
								<span id="num4" style="display: none">${num4}</span>
								<span id="variable4ID" style="display: none">${variable4ID}</span>
								<span id="goodsID" style="display: none">${goodsID}</span>
								<span id="goodsKey" style="display: none">${goodsKey}</span>
								<span id="photoPath" style="display: none">${photoPath}</span>
								<span id="logoPath" style="display: none">${logoPath}</span>
							</td>
							<td class="td_bg01">
								<span><s:if test="logoPath==null||logoPath==''">无</s:if> </span>
								<s:else>
									<span id="logo" onclick="lookImage('${logoPath}');"><a
										href="#">查看</a>
									</span>
								</s:else>


							</td>
							<td class="td_bg01">
								<span><s:if test="photoPath==null||photoPath==''">无</s:if> </span>
								<s:else>
									<span id="photo" onclick="lookImage('${photoPath}');"><a
										href="#">查看</a>
									</span>
								</s:else>


							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/goodsmanage/ea_getListGoodsManage.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<!--添加窗口 -->
		<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 5%;width: 1000px;"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							物品管理
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="1000" border="0" id="stafftable2" align="center"
									cellpadding="0" cellspacing="0"
									style="margin-top: 3px; margin-bottom: 3px;">
									<tr>
										<td height="35" align="right">
											条码号：
										</td>
										<td>
											<input name="goodsManage.barCode" type="text" maxlength="50"id="barCode" size="15" />
										</td>
										<td align="right">
											公司打印条码：
										</td>
										<td>
											<input name="goodsManage.companyStorsge" maxlength="50"
												id="companyStorsge" type="text" size="15" />
										</td>
									</tr>
									<tr>
										<td align="right">
											二维码：
										</td>
										<td>
											<input name="goodsManage.twoCode" maxlength="50"
												id="twoCode" type="text"  size="15" />
										</td>
										<td height="35" align="right">
											芯片号：
										</td>
										<td colspan="3">
											<input name="goodsManage.defaultStorage" type="text" maxlength="50"
												class="input chips" id="defaultStorage" size="15" />
											
												<input type="hidden" id="oldchipids" value="" />
											<input type="button" value="读取" class="input-button readchipid" style="width:50px;"/>
										
										</td>
									</tr>
									<tr>
										<td width="10%" height="35" align="right">
											品名编号：
										</td>
										<td width="35%" >
											系统自动生成
											<!-- <input name="goodsManage.goodsCoding" title="编号不能为空" type="text" value="系统自动生成"
												id="goodsCoding"  class="model3" value="" readonly="readonly"  size="15" /> -->
										</td>
										<td width="10%"  align="right">
											<span class="xx">*</span>品名名称：
										</td>
										<td width="35%" >
											<input name="goodsManage.goodsName" type="text" maxlength="25"
												id="goodsName" class="goodsName isremove put3"  size="15" />
										</td>
										<td width="10%"  rowspan="5">
											<div align="center">
												<img name="showlogo" height="135" width="135" id="showlogo" title="产品LOGO" />
												<br />
												<input name="goodsManage.logoPath" id="logoPath"
													class="input01" type="hidden" />
												<input name="goodsManage.fileLogo" type="file" contentEditable="false"
													class="input01" id="fileLogo" size="15" />
											</div>
										</td>
										<td width="10%"  rowspan="5">
											<div align="center">
												<img name="showphoto" height="135" width="135" id="showphoto" title="产品主题"/>
												<br />
												<input name="goodsManage.photoPath" id="photoPath"
													class="input01" type="hidden" />
												<input name="goodsManage.filePhoto" type="file" contentEditable="false"
													class="input01" id="filePhoto" size="15" />
											</div>
										</td>
									</tr>
									<tr>
										<td align="right">
											<span class="xx">*</span>类型：
										</td>
										<td id="typelist">
											<input type="text" maxlength="50" id="inputTypeID" name="goodsManage.typeID" readonly="readonly" class="put3"/>
											
										</td>
										<td align="right">
											行业类别：
										</td>
										<td>
											<input name="goodsManage.tradeCode"
												id="tradeCode" type="text" class="input isremove " onfocus="blue()" size="15" />
										</td>
									</tr>
									<tr>
										<td height="35" align="center">
											品牌规格：
										</td>
										<td>
											<s:select list="%{#request.standardslist}" id="standard"
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.standard" theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20101216zgkfwy4y8p0000000002','#standard','#cstaffForm')">新添</a>
										</td>
										<td align="right">
											默认规格：
										</td>
										<td>
											<input name="goodsManage.acquiesceStandard" maxlength="50"
												id="acquiesceStandard" type="text" class="input ckTextLength isremove " size="15" />
										</td>
										
									</tr>
									<tr>
										<td align="right">
											车架号/机壳号：
										</td>
										<td>
											<input name="goodsManage.manufacturers" type="text" maxlength="50"
												class="manufacturers ckTextLength isremove " id="manufacturers" size="15" />
										</td>
										<td align="right">
											型号：
										</td>
										<td>
											<input name="goodsManage.model" type="text" maxlength="50" class="input ckTextLength isremove "
												id="model" size="15" />
										</td>
										
									</tr>
									<tr>
									<td height="35" align="right">
											单位换算：
										</td>
										<td class="variables">
										<span id="num">
										    <input name="goodsManage.num" type="text" number="0"
												class="input isremove" id="num" style="width: 30px;" />
											<s:select list="%{#request.variablelist}"  id="variableID" headerKey="" number="0" headerValue="请选择" 
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.variableID" theme="simple"></s:select>
										</span>
										<span id="num1" style="display:none">=
										<input name="goodsManage.num1" type="text" number="1"
												class="input" id="num1" style="width: 30px;" />
											<s:select list="%{#request.variablelist}"  id="variable1ID" headerKey=""  number="1" headerValue="请选择" 
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.variable1ID" theme="simple"></s:select>
										</span>
										<span id="num2" style="display:none">=
									  <input name="goodsManage.num2" type="text"  number="2"
												class="input" id="num2" style="width: 30px;" />
											<s:select list="%{#request.variablelist}"  id="variable2ID" headerKey=""  number="2" headerValue="请选择" 
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.variable2ID" theme="simple"></s:select>
										</span>
										<span id="num3" style="display:none">=
										<input name="goodsManage.num3" type="text"  number="3"
												class="input" id="num3" style="width: 30px;" />
											<s:select list="%{#request.variablelist}"  id="variable3ID" headerKey=""  number="3" headerValue="请选择" 
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.variable3ID" theme="simple"></s:select>
										</span>
										<span  id="num4" style="display:none">=
										<input name="goodsManage.num4" type="text"  number="4"
												class="input" id="num4" style="width: 30px;" />
											<s:select list="%{#request.variablelist}"  id="variable4ID" headerKey=""  number="4" headerValue="请选择" 
												listKey="codeValue" listValue="codeValue"
												name="goodsManage.variable4ID" theme="simple"></s:select>
										</span>
											<a href="#"
												onclick="toCCode('scode20101014v5zed7cukk0000000003','#variableID','#cstaffForm')">新添</a>
										</td>
										<td height="35" align="right">
											<span class="xx" id="isShowes" style="display:none">*</span>主板号/发动机号：
										</td>
										<td>
											<input name="goodsManage.mnemonicCode" id="mnemonicCode" maxlength="50"
												type="text" class="mnemonicCode ckTextLength isremove " size="20" />
										</td>
									</tr>
									<tr id="isShow" style="display:none">
										<td colspan="5">
											<table width="100%" border="0" id="stafftable2" align="center"
												cellpadding="0" cellspacing="0"
												style="margin-top: 5px; margin-bottom: 5px;">
												<tr>
													<td width="95" height="30" align="right">
														车牌号：
													</td>
													<td>
														<input name="carInformation.carNum" id="carNum" class="carNum isremove" style="width:100px;"/>
													</td>
													
													<td width="90" align="right">
														<span class="xx">*</span>车辆类型：
													</td>
													<td>
														<input name="carInformation.carType" id="carType" class="carType isremove" style="width:100px;"/>
													</td>
													
													<td width="110" align="right">
														地点：
													</td>
													<td>
														<input name="carInformation.carPlace" id="carPlace" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														购买日期
													</td>
													<td>
														<input name="carInformation.buyDate" id="buyDate" onfocus="date(this);" class="isremove" style="width:100px;"/>
													</td>
													<td width="90" align="right">
														出厂日期：
													</td>
													<td>
														<input name="carInformation.releaseDate" id="releaseDate" class="isremove" onfocus="date(this);" style="width:100px;"/>
													</td>
												</tr>
												<tr>
													<td width="95" height="30" align="right">
														运行日期：
													</td>
													<td>
														<input name="carInformation.operationDate" id="operationDate" class="isremove" onfocus="date(this);" style="width:100px;"/>
													</td>
													<td width="90" align="right">
														注册登记日期
													</td>
													<td>
														<input name="carInformation.registrationDate" id="registrationDate" onfocus="date(this);" class="isremove" style="width:100px;"/>
													</td>
													<td width="95" height="30" align="right">
														车辆厂牌型号：
													</td>
													<td>
														<input name="carInformation.brandModel" id="brandModel" class="isremove" style="width:100px;"/>
													</td>
													<td width="90" align="right">
														发动机型号：
													</td>
													<td>
														<input name="carInformation.engineType" id="engineType" class="isremove" style="width:100px;"/>
													</td>
													<td width="110" align="right">
														货箱内部尺寸：
													</td>
													<td>
														<input name="carInformation.containerInSize" id="containerInSize" class="isremove" style="width:100px;"/>
													</td>
												</tr>	
												<tr>
													<td align="right">
														外廊尺寸：
													</td>
													<td>
														<input name="carInformation.outerSize" id="outerSize" class="isremove" style="width:100px;"/>
													</td>
													<td height="30" align="right">
														驱动形式：
													</td>
													<td>
														<input name="carInformation.driveType" id="driveType" class="isremove" style="width:100px;"/>
													</td>
													<td align="right">
														排量/功率：
													</td>
													<td>
														<input name="carInformation.power" id="power" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														燃油类别：
													</td>
													<td>
														<input name="carInformation.fuelType" id="fuelType" class="isremove" style="width:100px;"/>
													</td>
													
													<td  align="right">
														外观颜色及漆号：
													</td>
													<td>
														<input name="carInformation.colorPaintNum" id="colorPaintNum" class="isremove" style="width:100px;"/>
													</td>
												</tr>
												<tr>							
													<td height="30" align="right">
														<span class="xx">*</span>车辆品牌：
													</td>
													<td>
														<input name="carInformation.vehicleBrand" id="vehicleBrand" class="isremove vehicleBrand" style="width:100px;"/>
													</td>		
													<td align="right">
														制造厂名称：
													</td>
													<td>
														<input name="carInformation.factoryName" id="factoryName" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														准牵引总质量：
													</td>
													<td>
														<input name="carInformation.tractionTotal" id="tractionTotal" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														轮距：
													</td>
													<td>
														<input name="carInformation.wheelTead" id="wheelTead" class="isremove" style="width:100px;"/>
													</td>
													<td height="30" align="right">
														核定载客(人)：
													</td>
													<td>
														<input name="carInformation.ratifyPeople" id="ratifyPeople" class="isremove" style="width:100px;"/>
													</td>
												</tr>	
												
												<tr>	
													<td align="right">
														核定载质量：
													</td>
													<td>
														<input name="carInformation.ratifyQuality" id="ratifyQuality" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														国产/进口：
													</td>
													<td>
														<input name="carInformation.domestic" id="domestic" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														驾驶室载客：
													</td>
													<td>
														<input name="carInformation.bridgePeople" id="bridgePeople" class="isremove" style="width:100px;"/>
													</td>
													<td height="30" align="right">
														钢板弹簧片数：
													</td>
													<td>
														<input name="carInformation.springNum" id="springNum" class="isremove" style="width:100px;"/>
													</td>
													<td align="right">
														车辆获得方式：
													</td>
													<td>
														<input name="carInformation.vehicleGet" id="vehicleGet" class="isremove" style="width:100px;"/>
													</td>
												</tr>
												
												<tr>
													<td align="right">
														使用性质：
													</td>
													<td>
														<input name="carInformation.useProp" id="useProp" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														轴数：
													</td>
													<td>
														<input name="carInformation.shaftNum" id="shaftNum" class="isremove" style="width:100px;"/>
													</td>
													<td height="30" align="right">
														轮胎数：
													</td>
													<td>
														<input name="carInformation.tireNum" id="tireNum" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														轴距(mm)：
													</td>
													<td>
														<input name="carInformation.wheelbase" id="wheelbase" class="isremove" style="width:100px;"/>
													</td>
													
													<td align="right">
														百公里耗油：
													</td>
													<td>
														<input name="carInformation.kmFuel" id="kmFuel" class="isremove" style="width:100px;"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														轮胎规格：
													</td>
													<td>
														<input name="carInformation.tireSpecifications" id="tireSpecifications" class="isremove" style="width:100px;"/>
													</td>
													<td height="30" align="right">
														整备质量(kg)：
													</td>
													<td>
														<input name="carInformation.serviceQuality" id="serviceQuality" class="isremove" style="width:100px;"/>
													</td>
													<td align="right">
														转向形式：
													</td>
													<td >
													<input name="carInformation.steeringType" id="steeringType" class="isremove" style="width:100px;"/>
													<input type="hidden" name="carInformation.carKey" id="carKey" class="isremove" style="width:100px;"/>
													<input type="hidden" name="carInformation.carID" id="carID" class="isremove" style="width:100px;"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="30" colspan="5" align="center">
											<input name="goodsManage.goodsKey" id="goodsKey"
												type="hidden" class="input isremove" size="20" />
												<input name="goodsManage.goodsvariable" id="goodsvariable"
												type="hidden" class="input isremove" size="20" />
											<input name="goodsManage.goodsID" id="goodsID" type="hidden"
												class="input isremove" size="20" />
											<input type="button" class="input-button JQuerySubmit"
												style="cursor: pointer; width: 80px;" value="提交" />
											<input type="button" class="input-button JQueryreturn"
												style="cursor: pointer; width: 80px;" value="取消" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<iframe width="0" height="0" name="loadcab" id="loadcab"></iframe>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<!--搜索窗口 -->
        <div class="jqmWindow" style="width: 380px;right: 35%; top:20%" id="jqModelSearch2">
            <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                          公司名称：
                        </td>
                        <td>         
                          <select id="companyID" name="goodsManage.companyID">
                             <option value="">全部</option>
                          </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                           物品编号：
                        </td>
                        <td>         
                           <input   name="goodsManage.goodsCoding" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                           物品名称：
                        </td>
                        <td>         
                           <input   name="goodsManage.goodsName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                           物品类别：
                        </td>
                        <td>  
                        <input   name="goodsManage.typeID" />       
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		<input type="text" style="display: none;" id="treeid" />
		<input type="text" style="display: none;" id="parentid" />
		<input type="text" style="display: none;" id="treename" />
		<input type="text" style="display: none;" id="parentname" />
		<input type="text" style="display: none;" id="unitsID" />
		<div class="jqmWindow" style="min-width:200px;right: 35%; top:20%" id="jqModelSearch3">
            <form name="updateForm" id="updateForm" method="post">
            	<input type="submit" name="submit1" style="display:none"/>
                <div class="drag">
                    设置上级：
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable1">
                    <tr>
                        <td>
                        	<div id="aadTree1" style="overflow:auto;z-index:99;display:yes; border: 0px solid #000000;"></div> 
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="toupdate" value="设置 " />
                </div>
            </form>
        </div>
		<div class="jqmWindow" style="width: 400px; right: 25%;top:10%" id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCodes()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		<!-- 物品类别 -->
		<div div class="jqmWindow" style="width: 350px; height:350px; right: 45%;top:10%" id="jqModelType">
		<div class="drag">
			物品类别
			<!-- <div class="close" id="closes"></div> -->
		</div>
		<table height="100px" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td width="45%" align="left" valign="top">
						<div id="aadTree" class="text_tree"
							style="overflow: auto; z-index: 99; width: 150px"></div>
					</td>
					<!-- <td width="11%" align="center">
						<table>
						<tr><td><div class="right_dan" id="rightdan"></div></td></tr>
						<tr><td><div class="left_dan" id="leftdan"></div></td></tr>
						</table>
					</td> -->
					<td width="44%" align="left" valign="top">
						<div id="text_tree" class="text_tree"
							style="overflow: auto; z-index: 99; width: 150px"></div>
					</td>
				</tr>
			</table>
		</div>
		<!-- 行业类别  -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"  id="JQueryaddress">
			<form name="SearchForm1" id="SearchForm1" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					请选择行业类别
				</div>
				<table width="400" id="cataffSearchTable">										
						<tr class="trheight">						
						<td colspan="2" class="JQueryaddress">
							<select name="addressProvince" id="province" number='0'
								style="width: 150px;">
							</select>
							<select name="addressCity" id="city" number='1'
								style="width: 150px;">
							</select>
							
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch1" onclick="getAddress()"
						value="确定" />
						<input type="button" class="input-button JQueryreturn2" value="取消" />
				</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>