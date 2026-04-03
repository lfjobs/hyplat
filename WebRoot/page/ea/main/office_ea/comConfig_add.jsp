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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司配置添加</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/css/ea/comConfig_add.css" rel="stylesheet" type="text/css"/>



<script type="text/javascript">

   var token = 1;
   var ccompanyID= '${ccomConf.ccompanyId}';
    var ccomConfId= '${ccomConf.ccomConfId}';

   var basePath = '<%=basePath%>';

	$(function() {
		//修改时不不能修改类型
		if (ccomConfId != "") {
			$(".modalType").hide();
			$(".modalType").append("<option value='0' <c:if test="${ccomConf.modalType==0 }">selected='selected'</c:if>>公司简介</option>");
			$("#spantype").show();
		} else {
			$("#spantype").hide();
			$(".modalType").show();
		}

		/* 验证公司简介只有一个，保证查询往来单位时有公司简介的只有一条*/
		var url = basePath
				+ "/ea/ccomconf/sajax_n_ea_ajaxCheck.jspa?ccomConf.ccompanyId="
				+ ccompanyID;
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function(data) {
				var member = eval("(" + data + ")");
				var temp = member.flag;
				if (temp == "0") {
					$(".modalType").append("<option value='0'>公司简介</option>");
				}
			},
			error : function(data) {
				alert("数据加载失败！");
			}
		});

		//保存
		$(".saveConfig").click(function() {
				$("#ccomconfForm input").trigger("blur");
				$("#ccomconfForm textarea").trigger("blur");
				if($("#ccomconfForm .error").length>0){
				   alert("信息不完整");
				   return;
				}
				 if(ccomConfId==""){
				      if($(".pic").val()==""){
				      alert("信息不完整");
				      return;
				  }
				}
				
			
			  var reg=/^[1-9]$/;
		  
		      if(!reg.test($("#sn").val()))
		      {
		         alert("序号必须是1-9之间的整数");
		          return;
		      }
				
				$('#ccomconfForm').attr("target", "hidden").attr("action",
							basePath + "ea/ccomconf/ea_saveConfig.jspa");

					document.ccomconfForm.submit.click();
					token = 2;
				
				});

	});
	
	function re_load() {
		if (token)
			window.opener.location.href = window.opener.location.href
		window.close();
	}
</script>
</head>
<body><div class="all">
	<form name="ccomconfForm" id="ccomconfForm" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="ccomConf.ccomConfId"
			value="${ccomConf.ccomConfId}" />
		<input type="hidden" name="ccomConf.ccomConfKey"
			value="${ccomConf.ccomConfKey}" />
		<input type="hidden" name="ccomConf.ccompanyId"
			value="${ccomConf.ccompanyId}" />
		<input type="submit" name="submit" style="display:none" />
		<div id="main_main" class="main_main" style="">
			<div class="title"><span>添加信息</span></div>
			<table cellspacing="10" cellpadding="0" width="100%" >
				<tr>
					<td align="right" style="width:40%;">模块名称：</td>
					<td align="left"><input type="text" class="input3" name="ccomConf.modalName"
						value="${ccomConf.modalName}" /><span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td align="right">序号：</td>
					<td align="left" ><input type="text" name="ccomConf.sn"  id="sn" class="input3"
						value=<s:if test='ccomConf.sn!=null'>"${ccomConf.sn}"</s:if><s:else>1-9之间整数</s:else> onfocus="if(this.value=='1-9之间整数'){this.value='';}"  onblur="if(this.value==''){this.value='1-9之间整数';}"/><span style="color:red">*</span>
					</td>
				</tr>
				<tr>
					<td align="right">模块类型：</td>
					<td align="left"><select name="ccomConf.modalType"
						class="modalType">
							<option value="1"
								<c:if test="${ccomConf.modalType==1 }">selected="selected"</c:if>>关于公司</option>	
								<span style="color:red">*</span>
							<c:if test="${account.company.companyIdentifier eq 'fljybj' }">
								<option value="2"
									<c:if test="${ccomConf.modalType==2 }">selected="selected"</c:if>>中联园区头部</option>
								<option value="3"
									<c:if test="${ccomConf.modalType==3 }">selected="selected"</c:if>>中联园区中部</option>
								<option value="4"
									<c:if test="${ccomConf.modalType==4 }">selected="selected"</c:if>>中联园区底部</option>
							</c:if>
					</select> 
					<span id="spantype"> <c:if test="${ccomConf.modalType==0 }">公司简介</c:if>
							<c:if test="${ccomConf.modalType==1 }">关于公司</c:if> 
							<c:if test="${ccomConf.modalType==2 }">中联园区头部</c:if>
							<c:if test="${ccomConf.modalType==3 }">中联园区中部</c:if> 
							<c:if test="${ccomConf.modalType==4 }">中联园区底部</c:if> </span></td>
				</tr>
				<tr>
					<td align="right">上传图片：</td>
					<td align="left"><input type="file" name="ccomConf.photo"  class="pic"/><span style="color:red;">*</span>
						<input type="hidden" name="ccomConf.picPath"
						value="${ccomConf.picPath}" />
					</td>
				</tr>
				<tr>
					<td align="right">详细说明：</td>
					<td align="left"><textarea cols="20" rows="8" class="input3"
							name="ccomConf.modalRemark">${ccomConf.modalRemark}</textarea><span style="color:red">*</span>
					</td>
				</tr>

			
			</table>
			<div class="title"><input type="button" value="保存"
						class="input-button saveConfig" /></div>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
				framespacing="0" height="0"></iframe>
	</div>
	</form>
	</div>
</body>
</html>