<!--
---------本页说明-----------

开发人员集成调用SOAOffice 中间件请参考“精简示例代码”。

-->
<%@ page contentType="text/html; charset=gb2312" language="java"
	import="java.sql.*,java.util.*,java.net.*" errorPage=""%>
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
	SOACtrl.CanCopy = true;//设置页面是否可以复制；
	// 设置SOAOFFICE中间件服务页面
	SOACtrl.ServerURL = basePath + "soaserv.doo";

	// 设置界面样式
	SOACtrl.MainStyle = 2;
	SOACtrl.Caption = "文档";
	SOACtrl.BorderStyle = 0;
	//SOACtrl.Titlebar = false;
	//SOACtrl.TitlebarColor = Color.decode("#FF0000");
	//SOACtrl.TitlebarTextColor = Color.decode("#FFFF00");
	//SOACtrl.Menubar = false;
	//SOACtrl.Toolbars = false;

	//设置保存文档的服务器页面
	//String newkey = request.getParameter("newkey");
	String docPath = request.getParameter("docPath");
	String fileShowNameField = "天太胜威文档编辑";
	String fileId = request.getParameter("fileId");
	SOACtrl.SaveDocURL = basePath
			+ "ea/fileManage/ea_saveWord.jspa?docPath="+docPath;
	SOACtrl.SaveHtmlURL = "SaveHtml.jsp";

	// 打开文档
	
	String fileType = request.getParameter("fileType");
	String fileTypedoc = "";
	if (fileType.equals(".docx")||fileType.equals(".doc")) {
		fileTypedoc = "Word.Document";
	} else if(fileType.equals(".xlsx")||fileType.equals(".xls")){
		fileTypedoc = "Excel.Sheet";

	}else if(fileType.equals(".pptx")||fileType.equals(".ppt")){
		
		fileTypedoc = "PowerPoint.Show";
	
	}else if(fileType.equalsIgnoreCase(".VSD")){
		
		fileTypedoc = "Visio.Drawing";
		
	}
	SOACtrl.webOpen(basePath + docPath, 2, "张三", fileTypedoc);
	//-------- SOAOFFICE 服务器端编程结束 ------------------------ //
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>文档编辑</TITLE>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<script src="<%=basePath%>js/ea/office_ea/document/control.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/template.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/css.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/css(1).css" />
		<META content="MSHTML 6.00.2800.1498" name="GENERATOR">

		<script type="text/javascript">
       
      	var basePath='<%=basePath%>';
      	var fileId='<%=fileId%>';
     	</script>
	</HEAD>

	<BODY bgColor="#ffffff" leftMargin="0" topMargin="0">
		<form name="formData" method="post" action="" id="formData">
			<input type="hidden" id="docPath" value="result" />
			<TABLE height="21" cellSpacing="0" cellPadding="0" width="100%"
				border="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD width="6" bgcolor="#FFFFFF">
							&nbsp;
						</TD>
						<TD style="font-size: 9pt" width=140 nowrap>
							&nbsp;&nbsp;
						</TD>
						<TD>
							<SPAN class="ltsep"></SPAN>
						</TD>
						<TD style="font-size: 10pt" width=177 nowrap>
							
						</TD>
						<TD>
							
						</TD>
						<TD style="font-size: 10pt" width="100%">
							&nbsp;&nbsp;文档显示名称：
							<font color="#FF0000"><input type="hidden"
									name="fileShowNameField" id="fileShowNameField" value="" /><%=fileShowNameField%>&nbsp;&nbsp;&nbsp;&nbsp;双击控件标题栏最大化运行</font>
						</TD>
						<TD width="6" bgcolor="#FFFFFF">
							&nbsp;
						</TD>
					</TR>
				</TBODY>
			</TABLE>

			<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
				<TBODY>
					<TR vAlign="top">
						

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
		// 控件打开文档前触发，用来初始化界面样式
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnDocumentOpened(str, obj)" for="SOAOfficeCtrl">
		// 控件打开文档后立即触发，添加自定义菜单，自定义工具栏，禁止打印，禁止另存，禁止保存等等
		bDocOpen = true;
		//  添加自定义菜单
		SOAOfficeCtrl.UserMenuCaption = "自定义菜单(&N)";
		SOAOfficeCtrl.AppendMenuItem(1, "显示痕迹(&S)", false);
		SOAOfficeCtrl.AppendMenuItem(2, "隐藏痕迹(&H)", false);
		SOAOfficeCtrl.AppendMenuItem(3, "-");
		SOAOfficeCtrl.AppendMenuItem(8, "保存");
		SOAOfficeCtrl.AppendMenuItem(6, "数字签名");
		SOAOfficeCtrl.AppendMenuItem(7, "验证数字签名");
	    SOAOfficeCtrl.AppendToolButton(1, "保存", 1);
		document.all("SOAOfficeCtrl").EnableFileCommand(3) = false;//为什么不支持excel
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
		if(index == 4) alert("该菜单的标题是："+caption);
		if(index == 5) jsInsertSeal();	
		if(index == 6) jsAddDigitalSignature();
		if(index == 7) jsValidateDigitalSignatures();
		if(index == 8)  jsSave2();
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnCustomToolBarClick(index, caption)"
												for="SOAOfficeCtrl">
		// 添加您的自定义工具栏按钮事件响应
		if(index == 1)  jsSave2();
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
						<TD style="OVERFLOW-X: hidden" width="6" height="100%"
							bgcolor="#FFFFFF">
							&nbsp;
						</TD>
					</TR>
				</TBODY>
			</TABLE>

			<TABLE height="12" cellSpacing="0" cellPadding="0" width="100%"
				border="0" bgcolor="">
				<TBODY>
					<TR>
						<TD width="12" height="12"
							background="<%=basePath%>images/ea/office/document/img_blanklogo_14.gif"
							align="left"></TD>
						<TD width="98%"
							background="<%=basePath%>images/ea/office/document/img_blanklogo_15.gif"
							align="center"></TD>
						<TD width="12"
							background="<%=basePath%>images/ea/office/document/img_blanklogo_16.gif"
							align="right"></TD>
					</TR>
				</TBODY>
			</TABLE>

			<hr width="90%" size=1 color=black>
			<TABLE width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD width="100%" align="center" class="myduan">
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
	</BODY>
</HTML>
