<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>损益表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<style>
.td1 {
	border-style: solid;
	border-width: 1px 1px 0px 0px;
	}
.td2 {
	border-style: solid;
	border-width: 1px 1px 0px ;
	background-color: #9CF;
	border-color: #000
	}
.td3 {
	border-style: solid;
	border-width: 1px 1px;
	background-color: #9CF;
	border-color: #000;
	height: 20px;
	}
.td4 {
	border-style: solid;
	border-width: 1px 1px 1px 0px;
	height: 20px;
	}
</style>
<style media="print">
    #but{
       display:none;
    }
</style>
<script type="text/javascript">
var basePath="<%=basePath%>";
var bt="${bt}";
var et="${et}";
var dw="${dw}";
var re="${re}";
	$(function(){
		$("#but").click(function(){
			var d="td="+et+"&dw="+dw+"&re="+re;
			if(bt=="B"){
				window.location.href=basePath+"ea/ccpbsgl/ea_syshowExcel.jspa?"+d;
			}else{
				window.location.href=basePath+"ea/ccpbsgl/ea_syzfshowExcel.jspa?"+d;
			}
		});
	});
</script>
</head>

<body>
<form action="">
<table width="80%" cellspacing="0" cellpadding="0">
  <tr>
        <td height="40" colspan="5" align="center"><h2><span>损&nbsp;&nbsp;益&nbsp;&nbsp;表</span></h2></td>
      </tr>
      <tr>
        <td colspan="5" height="20px">&nbsp;<input type="button" id="but" value="导出Ecxel"/></td>
      </tr>
      <tr>
        <td height="20px" align="left" colspan="3"><span style="font-size:12px">编制单位：${cname }</td>
        <td align="right"><span style="font-size:12px">年月:${et}</span></td>
        <td align="right"><span style="font-size:12px">单位：${dw}</span></td>
  </tr>
  <tr>
        <td colspan="5" height="20px">&nbsp;</td>
      </tr>
  <tr>
    <td width="25%" align="center" height="20px" class="td2"><span style="font-weight:bold;font-size:12px">项目</span></td>
    <td width="5%" align="center" height="20px" class="td1"><span style="font-weight:bold;font-size:12px">项次</span></td>
    <td  width="25%" align="center" height="20px" class="td1" style="background-color: #9CF;"><span style="font-weight:bold;font-size:12px">本月数</span></td>
    <td width="20%" align="center" height="20px" class="td1"><span style="font-weight:bold;font-size:12px">%</span></td>
    <td  width="25%" align="center" height="20px" class="td1" style="background-color: #9CF;"><span style="font-weight:bold;font-size:12px">年度累计数</span></td>
  </tr>
  <c:forEach var="arr" items="${objlist}">
  <tr>
    <td align="left" height="20px" class="td2"><span style="font-size:12px;font-size:10px;">&nbsp;${arr[1] }</span></td>
    <td align="left" height="20px" class="td1"><span style="font-size:12px;font-size:10px;">&nbsp;${arr[0] }</span></td>
    <td align="right" height="20px" class="td1" style="background-color: #9CF;"><span style="font-size:12px;font-size:10px;">${arr[3]==""||arr[3]==null?0.000:arr[3] }&nbsp;</span></td>
    <td align="right" height="20px" class="td1"><span style="font-size:12px">${arr[4]==""||arr[4]==null?0.00:arr[4] }&nbsp;</span></td>
    <td align="right" height="20px" class="td1" style="background-color: #9CF;"><span style="font-size:12px;font-size:10px;">${arr[5]==""||arr[5]==null?0.00:arr[5] }&nbsp;</span></td>
  </tr>
  </c:forEach>
  <%-- <tr>
    <td align="left" class="td2"><span style="font-weight:bold;font-size:12px">一、营业收入</span></td>
    <td  align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">减：营业成本</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">营业税金及附加</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">销售费用</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">管理费用</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">财务费用</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">资产减值损失</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">加：公允价值变动收益（损失以"-"号填列）</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">投资收益（损失以"-"号填列）</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">其中：对联营企业和合营企业的投资收益</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-weight:bold;font-size:12px">二、营业利润（亏损以"-"号填列）</span></td>
    <td align="right" class="td1"><span style="font-size:12px">-&nbsp;&nbsp;</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">加：营业外收入</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">减：营业外支出</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">其中：非流动资产处置损失</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-weight:bold;font-size:12px">三、利润总额（亏损总额以"-"号填列）</span></td>
    <td align="right" class="td1"><span style="font-size:12px">-&nbsp;&nbsp;</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">减：所得税费用</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-weight:bold;font-size:12px">四、净利润（净亏损以"-"号填列）</span></td>
    <td align="right" class="td1"><span style="font-size:12px">-&nbsp;&nbsp;</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-weight:bold;font-size:12px">五、每股收益：</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">（一）基本每股收益</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-size:12px">（二）稀释每股收益</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td2"><span style="font-weight:bold;font-size:12px">六、其他综合收益</span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
    <td align="right" class="td1"><span style="font-size:12px"></span></td>
  </tr>
  <tr>
    <td align="left" class="td3"><span style="font-weight:bold;font-size:12px">七、综合收益总额</span></td>
    <td align="right" class="td4"><span style="font-size:12px">-&nbsp;&nbsp;</span></td>
    <td align="right" class="td4"><span style="font-size:12px"></span></td>
  </tr> --%>
  <tr>
    <td align="left" class="td3"><span style="font-weight:bold;font-size:12px"></span></td>
    <td align="left" class="td4"><span style="font-weight:bold;font-size:12px"></span></td>
    <td align="right" class="td4"><span style="font-size:12px"></span></td>
    <td align="right" class="td4"><span style="font-size:12px"></span></td>
    <td align="right" class="td4"><span style="font-size:12px"></span></td>
  </tr> 
</table>
</form>
</body>
</html>