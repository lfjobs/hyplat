<!--
---------本页说明-----------

开发人员集成调用SOAOffice 中间件请参考“精简示例代码”。

-->
<%@ page contentType="text/html; charset=gb2312" language="java" errorPage=""%>
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

	// 设置界面样式
	SOACtrl.MainStyle = 2;
	SOACtrl.Caption = "只读模式";
	SOACtrl.BorderStyle = 0;
	SOACtrl.Menubar = false;
	SOACtrl.Toolbars = false;
	SOACtrl.CanCopy = false;
	//SOACtrl.Titlebar = false;
	//SOACtrl.TitlebarColor = Color.decode("#FF0000");
	//SOACtrl.TitlebarTextColor = Color.decode("#FFFF00");
	//SOACtrl.Menubar = false;
	//SOACtrl.Toolbars = false;

	//设置保存文档的服务器页面
	SOACtrl.SaveDocURL = basePath
			+ "ea/documentflow/ea_saveWord.jspa";
	SOACtrl.SaveHtmlURL = "SaveHtml.jsp";

	// 打开文档
	String docPath = request.getParameter("docPath");
	String fileShowNameField = request
			.getParameter("fileShowNameField");
	String fileType = request.getParameter("fileType");
	String loads = request.getParameter("load");
	String prints = request.getParameter("print");
	String fileTypedoc = "";
	if (fileType == "W" || fileType.equals("W")) {
		fileTypedoc = "Word.Document";
	} else {
		fileTypedoc = "Excel.Sheet";

	}

	SOACtrl.webOpen(basePath + docPath, 2, "五", fileTypedoc);
	// -------- SOAOFFICE 服务器端编程结束 ------------------------ //
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>word只读模式</TITLE>
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META
			content="SOAOffice微软 Office 中间件，OFFICE文档在线编辑、在线保存的完美解决方案。强制痕迹保留,手写批注,全文批注,手写签名电子盖章(电子印章,电子签章,电子签名),word留痕,公文留痕,web文档控件,数字签名,支持全部OFFICE菜单,打印预览,手工批注,集成,开发,创建OA系统的必选中间件！联系电话：010-62969896"
			name="description">
		<META
			content="SOAOffice,微软 Office 中间件,痕迹保留,强制痕迹保留,全文批注,手写批注,OFFICE文档,电子印章,手写签名,word留痕,留痕,公文留痕,在线编辑,在线保存,办公自动化,OA,电子签名,数字签名,手工批注,打印预览"
			name="keywords">

		<script src="<%=basePath%>js/ea/office_ea/document/control.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<LINK href="images/template.css" type="text/css" rel="Stylesheet">
		<LINK href="images/css.css" type="text/css" rel="Stylesheet">
		<LINK href="images/css(1).css" type="text/css" rel="stylesheet">
		<META content="MSHTML 6.00.2800.1498" name="GENERATOR">
		<script type="text/javascript">
       
      	 var basePath='<%=basePath%>';     
     	  var fileTypee='<%=fileType%>'
     	</script>
	</HEAD>
	<BODY bgColor="#ffffff" leftMargin="0" topMargin="0">
		<form name="formData" method="post" action="" id="formData">
			<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 height=1
				bgcolor=#ffffff>
				<TR>
					<TD></TD>
				</TR>
			</table>
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
						<TD style="font-size: 10pt" width=800 nowrap>
							当前操作模式：
							<font color="#FF0000">阅读&nbsp;&nbsp;</font>文档显示名称：
							<font color="#FF0000"><%=fileShowNameField%>&nbsp;&nbsp;&nbsp;&nbsp;双击控件标题栏最大化运行</font>
						</TD>
						<TD style="font-size: 10pt" width="100%">
							&nbsp;&nbsp;
							<input type="hidden" value="<%=prints%>" id="prHid" />
							<input type="hidden" value="<%=loads%>" id="loHid" />
							<input type="hidden" value="<%=docPath%>" id="loHidDocPath" />
						</TD>
						<td width="5" bgcolor="#FFFFFF">
							&nbsp;
						</td>
					</TR>
				</TBODY>
			</TABLE>

			<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
				<TBODY>
					<TR vAlign="top">
						<TD style="OVERFLOW-X: hidden" width="10" height="100%"
							bgcolor="#FFFFFF">
							&nbsp;
						</TD>

						<TD width="100%">
							<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
								<TBODY>
									<TR vAlign="top" align="left">
										<td height="655">
											<!--**************   SOAOFFICE 客户端代码开始    ************************-->
											<SCRIPT language="JavaScript" event="OnInit()"
												for="SOAOfficeCtrl">
		// 控件打开文档前触发，用来初始化界面样式

		//  添加自定义工具条

		//SOAOfficeCtrl.AppendToolButton(1, "PDF模式", 0);
		//SOAOfficeCtrl.AppendToolButton(2, "Page模式", 0);
		//SOAOfficeCtrl.AppendToolButton(3, "-");
		//SOAOfficeCtrl.AppendToolButton(4, "打印");
		//SOAOfficeCtrl.AppendToolButton(12, "下载");
		//SOAOfficeCtrl.AppendToolButton(5, "-");
		SOAOfficeCtrl.AppendToolButton(6, "全屏/还原", 4);
	    SOAOfficeCtrl.AppendToolButton(7, "-");
		SOAOfficeCtrl.AppendToolButton(8, "验证公章/电子签名有效性", 0);
		SOAOfficeCtrl.AppendToolButton(10, "验证数字签名", 0);
	    //SOAOfficeCtrl.AppendToolButton(9, "分层显示手写批注");
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnDocumentOpened(str, obj)" for="SOAOfficeCtrl">
		// 控件打开文档后立即触发，添加自定义菜单，自定义工具栏，禁止打印，禁止另存，禁止保存等等
		bDocOpen = true;
	</SCRIPT>
											<SCRIPT language="JavaScript" event="OnDocumentClosed()"
												for="SOAOfficeCtrl">
		bDocOpen = false;
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnUserMenuClick(index, caption)" for="SOAOfficeCtrl">
		// 添加您的自定义菜单项事件响应
	</SCRIPT>
											<SCRIPT language="JavaScript"
												event="OnCustomToolBarClick(index, caption)"
												for="SOAOfficeCtrl">
		
		// 添加您的自定义工具栏按钮事件响应
		if(index == 1) jsPDF();
		if(index == 2) jsPage();
		
		if(index == 4) {
		  var prHid = document.getElementById("prHid").value;
		  if(prHid=="on")
		     SOAOfficeCtrl.ShowDialog(4);
		else  
		  alert("对不起，您无权限打印！");   
		}
		
		if(index == 6) SOAOfficeCtrl.FullScreen = !SOAOfficeCtrl.FullScreen;
		if(index == 8) jsValidateSeal();
		if(index == 9) SOAOfficeCtrl.ShowHandDrawDispBar();
		if(index == 10) jsValidateDigitalSignatures();
		if(index == 12) {
		var loHidDocPath = document.getElementById("loHidDocPath").value;
		var loHid = document.getElementById("prHid").value;
		if(loHid=="on")
		     loadFile(loHidDocPath); 
		else{ 
		   alert("对不起，您无权限下载！");   
		   
		   }
		  }
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
										</td>
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
							background="images/img_blanklogo_14.gif" align="left"></TD>
						<TD width="98%" background="images/img_blanklogo_15.gif"
							align="center"></TD>
						<TD width="12" background="images/img_blanklogo_16.gif"
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

