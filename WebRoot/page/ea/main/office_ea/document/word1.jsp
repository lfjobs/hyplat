<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	request.setCharacterEncoding("gb2312");
%>
<%@page pageEncoding="GB2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	// -------- SOAOFFICE 服务器端编程开始 ------------------------ //
	SOAOfficeX.SOAOfficeCtrl SOACtrl = new SOAOfficeX.SOAOfficeCtrl(
			pageContext);

	// 设置SOAOFFICE中间件服务页面
	SOACtrl.ServerURL = basePath + "soaserv.doo";
	SOACtrl.CanCopy = true;//设置页面是否可以复制；
	// 设置界面样式
	SOACtrl.MainStyle = 2;
	SOACtrl.Caption = "公文";
	SOACtrl.BorderStyle = 0;

	//设置保存文档的服务器页面

	String fileShowNameField = request
			.getParameter("fileShowNameField");
	String fileId = request.getParameter("fileId");
	
	String numTime = request.getParameter("numTime");
	
	String module = request.getParameter("module");
	
	

	String theme = request.getParameter("theme");
	if (theme == null) {
		theme = "";
	}
	String eType = request.getParameter("eType");
	String Type = request.getParameter("Type");

	SOACtrl.SaveDocURL = basePath
			+ "ea/zoffice/ea_saveOffice.jspa?fileId=" + fileId;
	SOACtrl.SaveHtmlURL = "SaveHtml.jsp";

	// 打开文档
	String docPath = request.getParameter("docPath");
	String fileType = request.getParameter("fileType");
	String fileTypedoc = "";
	if (fileType == "W" || fileType.equals("W")) {
		fileTypedoc = "Word.Document";
	} else {
		fileTypedoc = "Excel.Sheet";

	}

	SOACtrl.webOpen(basePath + docPath, 2, "一", fileTypedoc);
	// -------- SOAOFFICE 服务器端编程结束 ------------------------ //
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>拟稿</TITLE>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/office_ea/document/control.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/document/controlnewdoc.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
			

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/css.css" />
		<META content="MSHTML 6.00.2800.1498" name="GENERATOR">

		<script type="text/javascript">
       
      	var basePath='<%=basePath%>'; 
      	var Type = '<%=Type%>';
      	var module = "<%=module%>";
      	
      	var obj = window.dialogArguments;

        $(function(){
          if(Type!=null&&Type!="direct"&&Type!=""){
         $("#numWord").val(obj.numWord);
         if(module=="contract"){
           $("#partyAName").val(obj.partyAName);
		   $("#partyBName").val(obj.partyBName);
		   $("#partyA").val(obj.partyA);
		   $("#partyB").val(obj.partyB);
		   $("#partyAstaffnames").val(obj.partyAstaffnames);
		   $("#partyBstaffnames").val(obj.partyBstaffnames);
		   $("#partyAstaff").val(obj.partyAstaff);
		   $("#partyBstaff").val(obj.artyBstaff);
		   $("#staffIdentityCardA").val(obj.staffIdentityCardA);
		   $("#staffIdentityCardB").val(obj.staffIdentityCardB);
		   $("#startDate").val(obj.startDate);
		   $("#endDate").val(obj.endDate);
		   $("#projectName").val(obj.projectName);
		   $("#journalNum").val(obj.journalNum);
		 }
		 //处理公文类型
		 var docType = obj.docType;
		 $(".docType").val(docType);
		 
		 //处理紧急情况
      	 var eType = obj.eType;
      	 $("#emergencyType").val(eType);
  }
      	  
      	 if(Type=="direct"){
           $("#formData TR.nodirect").hide();
           $("#formData TR.direct").show();
          } else{
           $("#formData TR.nodirect").show();
           $("#formData TR.direct").hide();
           }
      	 
      	 });
        if(obj.journalNum!=""&&obj.journalNum!=undefined){
           $("#projectName").val(obj.projectName);
		   $("#journalNum").val(obj.journalNum);
		   }
    </script>
    <style type="text/css">
   //  .info,.info td{
 //    border:1px solid green;
  //   border-collapse: collapse;
 //    }
    
    </style>
	</HEAD>

	<BODY bgColor="#ffffff" leftMargin="0" topMargin="10">
		<form name="formData" method="post" action="#" id="formData">
			<input type="hidden" id="docPath" value="result" />
			<TABLE class="info" height="21" cellSpacing="10" cellPadding="5" width="100%"
				bgcolor="#FFFFFF">
				<TBODY>
					<TR class="nodirect">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							<nobr>
								当前操作状态：
								<font color="#FF0000">拟稿状态</font>
							</nobr>
						</TD>
						<TD>
							<SPAN class="ltsep">|</SPAN>
						</TD>
						<TD style="font-size: 11pt" width="100%">
							文件标题：
							<input type="text" name="fileShowNameField"
								id="fileShowNameField" value="<%=fileShowNameField%>" />
							|&nbsp;主题词：
							<input type="text" id="themes" value="<%=theme%>" />
							|&nbsp;文件缓急：
							<select id="emergencyType" >
								<option value="普通" selected="selected">
									普通
								</option>
								<option value="急件">
									急件
								</option>
								<option value="特急">
									特急
								</option>
							</select>
							&nbsp;&nbsp;
							<font color="#FF0000">&nbsp;&nbsp;双击控件标题栏最大化运行</font>
						</TD>
	

					</TR>
					<TR class="nodirect">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" >
						  正式编号：
							<input type="text" id="numWord" class="input"
													title="请输入正式编号" name="document.numWord"
													value="${document.numWord}" />
												字【<span id="numTime"><%=numTime%></span>】第
												<input type="text" id="numCode" class="input borderStyle"
													name="document.numCode" value="${document.numCode}"
													title="无需输入,自动生成" readonly />
												号
												<input type="hidden" value="${document.numTime}" />
						        公文类型：
                              <select id="docType" name="document.docType" class="docType">
												<option value="" selected>
													请选择公文类型
												</option>
												<option value="aa">
													董事会会议决定文件
												</option>
												<option value="bb">
													董事长办公室文件
												</option>
												<option value="cc">
													总裁办公室文件
												</option>
												<option value="dd">
													总部人事处文件
												</option>
												<option value="ee">
													总部办公室文件
												</option>
												<option value="ff">
													总部财务处文件
												</option>
												<option value="gg">
													总部教务(生产)处文件
												</option>
												<option value="hh">
													总部营销处文件
												</option>
												<option value="ii">
													总部服务(创收)平台
												</option>
												<option value="jj">
													总部教务部文件
												</option>
											</select>
						</TD>
	

					</TR>
					<%
					if(module!=null&&module.equals("contract")){
					
					 %>
					
						<TR class="nodirect hetong">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" >
							<div>
							甲&nbsp;&nbsp;&nbsp;&nbsp;方：
												&nbsp;&nbsp;公司&nbsp;
												<input type="text" name="document.partyAName" id="partyAName"
													value="${document.partyAName}" class="input" readonly />
												<input type="hidden" name="document.partyA" id="partyA"
													value="${document.partyA}" />
												<a href="#"
													onclick="importGY2('ea/documentcommon/ea_getListContactCompany.jspa','partyAD')">选择</a>
												<a href="#" onclick="clears('partyAD');">清空</a>
											&nbsp;责任人
												<input type="text" name="document.partyAstaffnames"
													id="partyAstaffnames" value="${document.partyAstaffnames}"
													class="input" readonly />
											&nbsp;身份证号
												<input type="text" name="document.staffIdentityCardA"
													id="staffIdentityCardA" value="${document.staffIdentityCardA}"
													class="input" readonly />
												&nbsp;
												<a href="#"
													onclick="importGY2('ea/documentcommon/ea_getSocialInfoList.jspa','partyA')">选择</a>
												<a href="#" onclick="clears('partyA');">清空</a>
												
											</div>
											<input type="hidden" name="document.partyAstaff"
												id="partyAstaff" value="${document.partyAstaff}" />
						</TD>
	

					</TR>
					
					<TR class="nodirect hetong">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" >
							<div>
							乙&nbsp;&nbsp;&nbsp;&nbsp;方：
												
												&nbsp;&nbsp;公司&nbsp;
												<input type="text" name="document.partyBName" id="partyBName"
													value="${document.partyBName}" class="input" readonly />
												<input type="hidden" name="document.partyB" id="partyB"
													value="${document.partyB}" />
												<a href="#"
													onclick="importGY2('ea/documentcommon/ea_getListContactCompany.jspa','partyBD')">选择</a>
												<a href="#" onclick="clears('partyBD');">清空</a>
												&nbsp;责任人
												<input type="text" name="document.partyBstaffnames"
													id="partyBstaffnames" value="${document.partyBstaffnames}"
													class="input" readonly />
												&nbsp;身份证号
												<input type="text" name="document.staffIdentityCardB"
													id="staffIdentityCardB" value="${document.staffIdentityCardB}"
													class="input" readonly />
												&nbsp;
												<a href="#"
													onclick="importGY2('ea/documentcommon/ea_getSocialInfoList.jspa','partyB')">选择</a>
												<a href="#" onclick="clears('partyB');">清空</a>
												
											</div>
											<input type="hidden" name="document.partyBstaff"
												id="partyBstaff" value="${document.partyBstaff}" />
						</TD>
	

					</TR>
					
					<TR class="nodirect hetong">
						
						<TD style="font-size: 11pt" width=177 nowrap>
							
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt">
							有&nbsp;效&nbsp;&nbsp;期： 从
							<input type="text" name="document.startValidity" id="startDate"
								class="input"
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
								readonly value="${fn:substring(document.startValidity, 0, 10)}"
								title="请选择生效日期" />

							至
							<input type="text" name="document.endValidity" id="endDate"
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly class="input"
								value="${fn:substring(document.endValidity, 0, 10)}"
								title="请选择失效日期" />
							&nbsp;&nbsp;&nbsp;	
						    所属项目预算：<input type="text" name="document.projectName" value="${document.projectName}" id="projectName" readonly/><input type="hidden" name="document.journalNum" id ="journalNum" value="${document.journalNum}"/><a href="#" onclick="importGY2('ea/promanage/ea_getProjectList.jspa','project')">选择</a>&nbsp;<a href="#" onclick="clears('project');">清空</a>

						</TD>
					


					</TR>
					<% } %>
					<TR class="direct">
						<TD width="6" bgcolor="#FFFFFF">
							&nbsp;
						</TD>
						<TD style="font-size: 9pt" width=140 nowrap>
							&nbsp;&nbsp;
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 11pt" width=177 nowrap>
							<nobr>
								&nbsp;&nbsp;当前操作状态：
								<font color="#FF0000">拟稿状态</font>
							</nobr>
						</TD>
						<TD>
							<SPAN class="ltsep">|</SPAN>
						</TD>
						<TD style="font-size: 11pt" width="100%">
							&nbsp;&nbsp;文件标题：
							<span><%=fileShowNameField%></span>
							|&nbsp;主题词：
							<span><%=theme%></span>
							|&nbsp;文件缓急：
							<span><%=eType%></span>
							
							&nbsp;&nbsp;
							<font color="#FF0000">&nbsp;&nbsp;双击控件标题栏最大化运行</font>
						</TD>
						<TD width="6" bgcolor="#FFFFFF">

						</TD>

					</TR>
				</TBODY>
			</TABLE>

			<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
				<TBODY>
					<TR vAlign="top">
						<TD style="OVERFLOW-X: hidden" width="6" height="100%"
							bgcolor="#FFFFFF">
							&nbsp;
						</TD>
						<TD style="OVERFLOW-X: hidden" width="144" height="100%">
							<DIV class="mnpMenuTop" id="mnpMenuTop"
								style="OVERFLOW-X: hidden; WIDTH: 144px; height: 700px;">
								<DIV class="mnpMenuRow">
									部分功能列表
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsSave();">保存</A>
								</DIV>
								<DIV class=mnpMenuBorder style="WIDTH: 141px"></DIV>
								<%
									if (fileType == "W" || fileType.equals("W")) {
								%>
								<DIV class="menuItem">
									<A href="javascript:jsInsertImageFromScanner();">插入图片来自扫描仪</A>
								</DIV>
								<%
									}
								%>
								<DIV class="menuItem">
									<A href="javascript:jsInsertWebImage();">插入Web图片</A>
								</DIV>

								<%
									if (fileType == "W" || fileType.equals("W")) {
								%>
								<DIV class="menuItem">
									<A href="javascript:jsInsertVBATemplate();">VBA套红</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsApplyFileTemplate();">模板套红</A>
								</DIV>
								<%
									}
								%>
								<DIV class=mnpMenuBorder style="WIDTH: 141px"></DIV>
								<DIV class="menuItem">
									<A href="javascript:jsToggleTitlebar();">切换标题栏</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsToggleMenubar();">切换菜单栏</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsToggleToolbars();">切换工具栏</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsDocPageSetup();">页面设置</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsEnablePrint(false);">禁止打印</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsEnablePrint(true);">允许打印</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsEnableSaveAs(false);">禁止另存</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsEnableSaveAs(true);">允许另存</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsWordToText();">获取文档Txt正文</A>
								</DIV>
								<DIV class="menuItem">
									<A href="javascript:jsDisableDragAndDrop();">禁止/允许WORD拖曳</A>
								</DIV>
								<DIV class="mnpMenuBorder"
									style="MARGIN-BOTTOM: 0px; WIDTH: 143px"></DIV>
							</DIV>
						</TD>
						<TD width="100%">
							<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
								<TBODY>
									<TR vAlign="top" align="left">
										<TD height="655">
											<SCRIPT language="javascript">

	 //文档另存为Html，并发布到web服务器
	function SaveAsHtml()
	{
		if (bDocOpen)
		{
			document.all("SOAOfficeCtrl").WebSaveAsHtml();
			window.open("htmlDoc.jsp?Type=word&ID=<%=request.getParameter("ID")%>"); 
		} 
	}				
</SCRIPT>

											<!--**************   SOAOFFICE 客户端代码开始    ************************-->
											<SCRIPT language="JavaScript" event="OnInit()"
												for="SOAOfficeCtrl">
		
		
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnDocumentOpened(str, obj)" for="SOAOfficeCtrl">
		// 控件打开文档后立即触发，添加自定义菜单，自定义工具栏，禁止打印，禁止另存，禁止保存等等
		bDocOpen = true;
		//  添加自定义菜单
		SOAOfficeCtrl.UserMenuCaption = "自定义菜单(&N)";
		SOAOfficeCtrl.AppendMenuItem(1, "显示痕迹(&S)", true);
		SOAOfficeCtrl.AppendMenuItem(2, "隐藏痕迹(&H)", true);
		SOAOfficeCtrl.AppendMenuItem(3, "-");
		SOAOfficeCtrl.AppendMenuItem(8, "保存");
		//SOAOfficeCtrl.AppendMenuItem(6, "数字签名");
		//SOAOfficeCtrl.AppendMenuItem(7, "验证数字签名");
		document.all("SOAOfficeCtrl").EnableFileCommand(1) = true;
		document.all("SOAOfficeCtrl").EnableFileCommand(3) = false;
		
		// 控件打开文档前触发，用来初始化界面样式
		SOAOfficeCtrl.AppendToolButton(1, "保存", 1);
	</SCRIPT>
											<SCRIPT language="JavaScript" event="OnDocumentClosed()"
												for="SOAOfficeCtrl">
		bDocOpen = false;
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnUserMenuClick(index, caption)" for="SOAOfficeCtrl">
		// 添加您的自定义菜单项事件响应
		if(index == 1) SOAOfficeCtrl.ShowRevisions = true;
		if(index == 2) SOAOfficeCtrl.ShowRevisions = false;
		//if(index == 4) alert("该菜单的标题是："+caption);
		//if(index == 5) jsInsertSeal();	
		if(index == 6) jsAddDigitalSignature();
		if(index == 7) jsValidateDigitalSignatures();
		if(index == 8) jsSave();
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnCustomToolBarClick(index, caption)"
												for="SOAOfficeCtrl">
		// 添加您的自定义工具栏按钮事件响应
		if(index == 1) jsSave();
	</SCRIPT>


											<!--   OBJECT标签，客户端控件引用    -->
											<OBJECT id="SOAOfficeCtrl"
												codeBase="<%=basePath%>js/cabs/ZSOffice.ocx#version=2,0,0,1"
												height="100%" width="100%"
												classid="clsid:AD06827C-D92F-4648-B880-138AF11E8A13" data=""
												VIEWASTEXT>
												<div align=center STYLE="color: red;">
													本机尚未安装卓正OFFICE组件，请安装浏览器上方黄色提示条或弹出提示框中的卓正OFFICE组件。
												</div>
											</OBJECT>
											<!--**************   SOAOFFICE 客户端代码结束    ************************-->
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
				</TBODY>
			</TABLE>


			<hr width="90%" size=1 color=black>
			<TABLE width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD width="100%" align="center" style="font-size: 15px;">
						<strong>京ICP备10034132号-2 版权所有 北京天太世统科技有限公司</strong>
						<br />
						Copyright 2009-2010 www.ttst2010.cn Corporation, All Rights
						Reserved
						<br />
						公司地址：北京市东直门外大街宇飞大厦801室 服务热线：64164005
					</TD>
				</TR>
			</TABLE>
		</form>
		
		
		<div id="socialJqm2" class="jqmWindow"
			style="width: 75%; height: 350px; absolute; display: none; left: 20%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="type2" value="" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="300px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm2();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 300px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm2();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</BODY>
</HTML>
