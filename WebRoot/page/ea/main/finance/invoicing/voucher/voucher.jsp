<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String filepath = request.getSession().getServletContext().getRealPath("/");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>凭证录入</title>
		<link href="<%=basePath%>css/ea/finance/voucher.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/formatMoney.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/voucher/voucher.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>	
		<link rel="STYLESHEET" type="text/css"
	    href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

</head>
<style type="text/css">
	img{
		height: 18px;
		position: relative;
		top: 3px;}
	span{
		text-align:left;display:block;}
	a:hover{
		cursor:pointer;}
		.sprs{
	display: block;
	width:100%;height:17px;}
</style>
<script type="text/javascript">
		var treeID = '<%=session.getAttribute("organizationID")%>';
		var treeNames="<%=session.getAttribute("organizationName")%>";
		var basePath = "<%=basePath%>";
		var notoken = 0;
		var sdate="${sdate}";
		var edate="${edate}";
		var endnumber = 0;
		//克隆物品行参数
		var select="";
	    var docNull;
	    //克隆凭证主表行参数
	    var billselect="";
	    var pagenum="";
	    var billdocNull;
	     //克隆凭证明细参数
	    var goodselect="";
	    var search2=0;
	    var original=0;
	    var orgId="${orgId}";
	    var leibie="${leibie}";
	    if(leibie=="nul")
	    alert("所选科目中没有对应类别，且不存在主账号");
</script>

<body>
<div class="adiv" style="width: 100%;height:700px;border:0px solid red;">
        <div class="top_div">凭证录入</div>
	 <div>
            <form name="searchForms" id="searchForms" method="post"
			enctype="multipart/form-data">
		    <input type="submit" name="submit" style="display: none" />
		    <input type="hidden" name="search" value="search"/>
            <table class="acip">
                <tr>
                    <td class="div_bor JQuerySubmitgd" style="height: 20px"><a href="#" id="saveinvvoucher" class="div_b">保存</a></td>
					<td class="div_bor" style="height: 20px"><a href="#" id="deletetr" class="div_s">删除</a></td>
                    <td class="div_bor" style="height: 20px">
                    <a href="#" id="xzpj" class="div_ch">选择票据</a>
                    </td>
                    <td class="div_bor" style="height: 20px">
                    	<a href="#" id="inserttr" class="div_x">新增明细</a>
                    </td>
                    <td class="div_bor" style="height: 20px">
                    <a href="#" id="dyyl" class="div_ch">打印预览</a>
                    </td>
                    <td>凭证日期：</td>
                    <td><input type="text" id="sdate" name="sdate" onfocus="cher_ydm(this);" 
                    class="inputbottom date" style="width:80px;height:19px;"/></td>
                    <td>-</td>
                    <td><input type="text" id="edate" name="edate" onfocus="cher_ydm(this);" 
                    class="inputbottom date" style="width:80px;height:19px;"/></td>
                    <td>凭证号：</td>
                    <td><input type="text" id="sjournalnum" name="sjournalnum" class="inputbottom" style="width:90px;height:19px;"/></td>
                    <td>-</td>
                    <td><input type="text" id="ejournalnum" name="ejournalnum" class="inputbottom" style="width:90px;height:19px;"/></td>
                    <td><input type="button" class="input-button search" style="margin:0px;margin-left:5px;" value="查询" /></td>
                </tr>
            </table>
            </form>
        </div>
        <form name="InvVoucherForm" id="InvVoucherForm" method="post"
			enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
	<div>
		<div class="main_t" id="flex1">
                <table  class="border flexme11" id="dtinvvoucher">
                    <tr class="nav_s" id="kelongbill" style="display: none;">
                        <td width="50">
                        <input type="radio" id="radio" name="b" class="JQuerypersonvalue1" value="" />
                        </td>
                        <td width="100">
                        <input type="text" id="voucherdate" name="voucherdate" onfocus="cher_ydm(this);" 
                        class="notnull" value="1" style="width:100%;height:100%;border:0;"/>
                        </td>
                        <td width="80"><span>系统生成</span>
                        <input type="hidden" id="journalnum" name="journalnum" 
                        style="width:100%;height:100%;border:0;" readonly="readonly"/>
                        </td>
                        <td width="140"><span>系统生成</span></td>
                        <td width="100"><input type="hidden" id="voucherorigin" name="voucherorigin" 
                        value="凭证录入" style="width:100%;height:100%;border:0;" readonly="readonly"/>
                        <span>凭证录入</span>
                        </td>
                        <td width="150">
                        <input type="hidden" id="makepeople" name="makepeople" 
                        value="${ManStaffName}" style="width:100%;height:100%;border:0;" readonly="readonly"/>
                        <input type="hidden" id="makepeopleid" name="makepeopleid" 
                        value="${ManStaffId}" style="width:100%;height:100%;" readonly="readonly"/>
                        <span>${ManStaffName}</span>
                        </td>
                        <td width="100">
                           <input type="hidden" id="vouchercategory" name="vouchercategory" 
                           value="1" style="width:100%;height:100%;" readonly="readonly"/>
                           <input type="hidden" value="未审核" style="width:100%;height:100%;border:0;" readonly="readonly"/>
                        	<span>未审核</span>
                        </td>
                        <td>
                        	<input type="text" id="fudanshu" style="border: 0px;width: 100%;height: 100%;">
                        </td>
                    </tr>
                    <s:if test="#session.list!=null && search!=null">
                    <s:iterator value="#session.list">
                     <tr class="nav_s searchcheckbill">
                        <td width="50">
                        <input type="radio" id="radio" name="b" class="JQuerypersonvalue1" value="${voucherid}" />
                        </td>
                        <td width="100">
                        <span id="voucherdate">${voucherdate}</span>
                        </td>
                        <td width="80">
                        <span id="journalnum">${journalnum}</span>
                        </td>
                        <td width="140">
                        <span id="VTName">${VTName}</span>
                        </td>
                        <td width="100">
                         <span id="voucherorigin">${voucherorigin}</span>
                        </td>
                        <td width="150">
                         <span id="makepeople">${makepeople}</span>
                        </td>
                        <td width="100">
                          <span id="vouchercategory">${vouchercategory==1?'未审核':vouchercategory==2?'退审核':vouchercategory==3?'已审核':vouchercategory==4?'退记帐':vouchercategory==5?'记帐':''}</span> 
                        </td>
                    </tr>
					</s:iterator>
					</s:if>
                    <tr class="nav_s">
                        <td width="50"></td>
                        <td width="100"></td>
                        <td width="80">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="110">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="50"></td>
                    </tr>
                    <tr class="nav_s">
                        <td width="50"></td>
                        <td width="100"></td>
                        <td width="80">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="110">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="50"></td>
                    </tr>
                     <tr class="nav_s">
                        <td width="50"></td>
                        <td width="100"></td>
                        <td width="80">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="110">&nbsp;</td>
                        <td width="100">&nbsp;</td>
                        <td width="50"></td>
                    </tr>
                </table>
            </div>
              <div class="main_j" style="overflow: auto;height:120px;border: 0px;">
                <table width="200" border="1"  style="border-color: #F2F2F2;margin:0px;" id="jdcount" cellspacing="0">
                    <tr style="height:40px;">
                        <td style="width: 80px;" align="left">借方金额：</td>
                        <td><span id="jfmoney" style="text-align:right;display:block;"></span>
                        	<input type="hidden" id="jfmoney"></td>
                    </tr>
                    <tr style="height:39px;">
                        <td style="width: 80px;" align="left">贷方金额：</td>
                        <td><span id="dfmoney" style="text-align:right;display:block;"></span>
                        	<input type="hidden" id="dfmoney"></td>
                    </tr>
                    <tr style="height:39px;">
                        <td style="width: 80px;" align="left">余额：</td>
                        <td>
                        <input type="hidden" id="ymoney"/>
                        <span id="ymoney"  style="text-align:right;display:block;"></span>
                        </td>
                    </tr>
                </table>
            </div>
     </div>
     <br><br><br><br><br><br><br>
	<div id="dtinvvouchertitle" style="border:0px solid red;float:left;width:100%;clear: both; margin-top: 0px;margin-bottom: 4px; font-size: 14px; text-align: center;">
		<div class="fo_div" id="flex2" style="border:0px solid green;">
           <div style="overflow: auto;">
           <table id="dtinvvouchers" width="100%" border="1" cellspacing="1" cellpadding="1" class="border_t flexme12">
               <thead>
				<tr style="height:25px; width: auto;">
					<th align="center" width="30">
						选择
					</th>
					<th align="center" width="40">
						序号
					</th>
					<th align="center" width="100"">
						<span style="color:red;display:inline;">*</span>会计科目
					</th>
					<th align="center" width="50">
						借贷方
					</th>
					<th align="center" width="100">
						本位币金额
					</th>
					<th align="center" width="100">
						记账金额
					</th>
					<th align="center" width="60">
						币别
					</th>
					<th align="center" width="70">
						<span style="color:red;display:inline;">*</span>汇率
					</th>
					<th align="center" width="50" >
						乘除注记
					</th>
					<th align="center" width="70">
						数量
					</th>
					<th align="center" width="70">
						部门
					</th>
					<th align="center" width="120">
						<span style="color:red;display:inline;">*</span>摘要
					</th>
					<th align="center" width="110">
						<span style="color:red;display:inline;">*</span>供应商客户
					</th>
					<th align="center" width="50">
						操作
					</th>
					</tr>
			</thead>
			<tbody id="tbwid">
			   <tr id="kelong" style="display: none;height:26px;">
					 <td align="center" bgcolor="#FFFFFF">
					 <input type="radio" id="radio" name="b" class="JQuerypersonvalue2" value="" />
					 <input type="hidden" value="" id="casId" name="casId" />
					 <input type="hidden" value="" id="goodsid" name="goodsid" />
					 <input type="hidden" value="" id="goodsbillsid" name="goodsbillsid" />
					 </td>
                     <td align="center" bgcolor="#FFFFFF">
                     <input id="vouchersnum" name="vouchersnum" style="border:0px;width:100%;height:100%;" 
                   class="input" size="2" value="1" readonly/>
                     </td>
                     <td><input type="hidden" value="" id="subjectsid"
												name="subjectsid" />
						 <input type="hidden" value="" id="subjectscode" name="subjectscode">
					     <input type="text" readonly="readonly"
									value="" placeholder="点击选择科目" 
									class="panduan" style="width:90px;border:0;"
									id="tosubjects" name="subjectsname" />
					  </td>
                   <td>
                   <input type="hidden" value="" id="direction"
												name="direction" />
				   <span id="direction"></span>
                   </td>
                   <td>
                   <input type="hidden" value="" id="standardmoney" name="standardmoney"/>
                   <span id="standardmoney" style="text-align:right;display:block;"></span>
				   </td>
                   <td><input type="hidden" value="" id="accountingmoney" name="accountingmoney"/>
                   <span id="accountingmoney" style="text-align:right;display:block;"></span></td>
                   <td>
                   <s:select list="%{#request.currencylist}" cssStyle="width:100%;height:100%;border:0;"
										listKey="codeID" listValue="codeValue" id="logistics"
										name="currencyid" theme="simple"></s:select>
                   <td><input type="text" class="jisuan" value="" 
                   style="height:100%;border:0;" id="exchangerate" name="exchangerate" size="5"/></td>
                   <td>
                  <select name="mdannotation" id="mdannotation" style="width:100%;height:100%;border:0;">
                    <option value="M" checked>乘</option>
                    <option value="D">除</option>
                  </select>
                   </td>
                   <td><input type="hidden" value="" id="quantity" name="quantity"/>
                   <span id="quantity"></span>
                   </td>
                   <td><span>
                   		<input type="hidden" name="organizationId" id="organizationId" value="">
                   		<input type="text" name="organizationName" id="organizationName" value="">
                   		</span></td>
                   <td><input type="text" class="inputbottom" value="" size="5"
                   style="height:100%;border:0;" id="reasonthing"  name="reasonthing" /></td>
                   <td>
   
                   <input type="hidden" id="clientid"
								name="clientid"
								value="" />
				   <input type="text" id="ccompanyname"
								name="ccompanyName"
								value="" placeholder="点击选择供货商" 
								class="panduan" style="width: 120px;border:0;" readonly="readonly"/></td>
                   <td>&nbsp;</td>
				</tr>
				<tr id="kelongoldgood" style="display: none;height:26px;">
					 <td align="center" bgcolor="#FFFFFF">
					 <input type="radio" id="radio" name="b" class="JQuerypersonvalue2" value="" />
					 <input type="hidden" value="" id="voucherid" name="voucherid" />
					 <input type="hidden" value="" id="voucherskey" name="voucherskey" />
					 <input type="hidden" value="" id="vouchersid" name="vouchersid" />
					 <input type="hidden" value="" id="casId" name="casId" />
					 <input type="hidden" value="" id="goodsid" name="goodsid" />
					 <input type="hidden" value="" id="goodsbillsid" name="goodsbillsid" />
					 </td>
                     <td align="center" bgcolor="#FFFFFF">
                     <input id="vouchersnum" name="vouchersnum" style="border:0px;width:100%;height:100%;" 
                   class="input" size="2" value="1" readonly/>
                     </td>
                     <td><input type="hidden" value="" id="subjectsid"
												name="subjectsid" />
					     <input type="text" readonly="readonly" disabled="true"
									value="" placeholder="点击选择科目" 
									class="panduan" style="width:90px;border:0;"
									id="tosubjects" name="subjectsname" />
					  </td>
                   <td>
                   <input type="hidden" value="" id="direction"
												name="direction" />
				   <span id="direction"></span>
                   </td>
                   <td>
                   <input type="hidden" value="" id="standardmoney" name="standardmoney"/>
                   <span id="standardmoney" style="text-align:right;display:block;"></span>
				   </td>
                   <td><input type="hidden" value="" id="accountingmoney" name="accountingmoney"/>
                   <span id="accountingmoney" style="text-align:right;display:block;"></span></td>
                   <td>
                   <s:select list="%{#request.currencylist}" cssStyle="width:100%;height:100%;border:0;"
										listKey="codeID" listValue="codeValue" id="logistics" disabled="true" 
										name="currencyid" theme="simple"></s:select>
                   <td><input type="text" class="jisuan" value="" 
                   style="height:100%;border:0;" id="exchangerate" name="exchangerate" disabled="true" size="5"/></td>
                   <td>
                  <select name="mdannotation" id="mdannotation" disabled="true" style="width:100%;height:100%;border:0;">
                    <option value="M" checked>乘</option>
                    <option value="D">除</option>
                  </select>
                   </td>
                   <td><input type="hidden" value="" id="quantity" name="quantity"/>
                   <span id="quantity"></span>
                   </td>
                   <td><span>${organizationname}</span></td>
                   <td><input type="text" class=" inputbottom" value="" size="5" disabled="true" 
                   style="height:100%;border:0;" id="reasonthing" name="reasonthing" /></td>
                   <td>
                   <input type="hidden" id="clientid"
								name="clientid"
								value="" />
				   <input type="text" id="ccompanyname"
								name="ccompanyName" disabled="true"
								value="" placeholder="点击选择供货商" 
								class="panduan" style="width: 120px;border:0;" readonly="readonly"/></td>
                   <td>
                   <div style="display:block;" id="imgedit">
                   <a href="#" class="imgedit"><img
								src="<%=basePath%>images/admin_images/edit.gif"
								width="16" height="16" title="修改" border="0" /> </a>
				   </div>
				   <div style="display:none;" id="imgbc">
				   <a href="#" class="imgbc"><img
								src="<%=basePath%>images/admin_images/bc.png"
								width="16" height="16" title="保存" border="0" /> </a>
				  <%--  <a href="#" class="imgquxiao"><img
								src="<%=basePath%>images/admin_images/quxiao.gif"
								width="16" height="16" title="取消" border="0" /> </a> --%>
				   </div>
				  
				   </td>
				</tr>
               <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
                <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
                <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
                <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
                <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
                <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
                <tr style="height:26px;" id="titletr">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               <tr style="height:26px;">
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
                   <td>&nbsp;</td><td>&nbsp;</td>
               </tr>
               </tbody>
           </table>
           </div>
       </div>
	</div>
	</form>
</div>
    <%------------------------------------选择票据------------------------------------%>
		<form name="billForm" id="billForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;width:900px;"
				id="billjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择票据
						</div>
					</div>
					<table width="99%" height="33" id="searchbill" border="0"
						align="center" cellpadding="0" cellspacing="2"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="60" align="right">
								项目名：
							</td>
							<td width="60">
								<input name="projectName" class="input" id="projectName" size="8"
									style="margin-left: 2px;" />
								<%--<input  type="hidden" id="inputname" size="10"/>
								<input  type="hidden" id="inputid" size="10"/>
							--%></td>
							<td width="60" align="right">
								物品名：
							</td>
							<td width="60">
								<input name="goodsName" class="input" id="goodsName" size="8"
									style="margin-left: 2px;" />
							</td>
							<td height="33" width="320" rowspan="2">
							&nbsp;
								<input type="button" class="btn02" id="searchccbill" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdchoose" name="button5"
									value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50" rowspan="2">
								<a id="dwsybill" title="0">上一页</a>
							</td>
							<td width="50" rowspan="2">
								<a id="dwxybill" title="0">下一页</a>
							</td>
							<td width="90" rowspan="2"> 
								<div>共<b style="color: red"
									id="zycountbill"></b>页</div>
							</td>
						</tr>
						<tr>
							<td width="60" align="right">
								凭证号：
							</td>
							<td width="60">
								<input name="journalNum" class="input" id="journalNum" size="8"
									style="margin-left: 2px;" />
							</td>
							<td width="60" align="right">部门&nbsp;&nbsp;&nbsp;： </td>
							<td>
								<select style="position: relative;left: 2px;width:75px;border-color: #a8c7ce;" id="orgValue">
									<option value=""> </option>
								</select>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0" id="tableOne"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 320px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02ccbill"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 310px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
    <%------------------------------------选择往来单位(供货商)------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
								<input  type="hidden" id="inputname" size="10"/>
								<input  type="hidden" id="inputid" size="10"/>
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 320px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 310px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectsubjects">
			<div class="drag">
				选择
			</div>
			<table>
				<tr>
					<td>
						科目名字：
					</td>
					<td align="left" class="subjects">
						<select id="province" number='0' style="width: 110px;"></select>
						<select id="city" number='1' style="width: 110px;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
					<td>
						<a href="#"
							onclick="window.open('<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp')">新增</a>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savesubjects"
					value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
		<div class="jqmWindow jqmWindowcss2" id="subjectr" style="width: 550px;height:420px;top: 10%;background-color: #DAE7F6;">
	    	<div style="border: 1px #EBEBEB solid; background-color: #B0C4DE;height: 23px">
	    		<font size="3"><b>&nbsp;选择科目</b></font>
	    	</div>
	    	<div style="position:relative;left:250px; height: 50px;top: 2%">
	    		<input type="button" value="首页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	    		<input type="button" value="上一页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="下一页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="尾页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</div>
	    	<div style="width: 130px;border: 1px #F0F8FF solid;background-color:#F0F8FF;
	    		border-left:3px #F0F8FF inset;border-top:3px #F0F8FF inset;
	    			height:300px;position: relative;top: -10px;">
	    		<div style="height: 270px;overflow:auto;">
	    		<table id="kemuone" cellspacing="0px">  			
	    		</table></div><br>
	    	</div>
	    	<div style="border: 1px #F0F8FF dashed;width:410px; height:300px;
	    		border-left:3px #CFCFCF double;border-top:3px #F0F8FF inset;
	    		position: relative;left:135px;top: -314px;">
	    		<table style="background-color:#F0F8FF" id="kemutoo">
	    			<tr style="background-color: #CAE1FF">
	    				<td width="72px" align="center">科目编号</td>
	    				<td width="110px" align="center">科目名称</td>
	    				<td width="80px" align="center">科目类别</td>
	    				<td width="65px" align="center">借贷方向</td>
	    				<td width="75px" align="center">账号类型</td>
	    			</tr>			
	    		</table>
	    	</div>	
	    	<hr style="border: 1px red solid">
    	<div>
    		<b style="position: absolute;top: 380px;left:380px;">
	    		<input type="button" value="确定" id="determine" style="width: 42px;height: 25px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="取消" id="cancel" style="width: 42px;height: 25px"/> 
    		</b>
    	</div>
    </div>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
	</body>
</html>