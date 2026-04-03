<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>现金流量表</title>
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
span{
	font-size:10px;
	}
.span1{
	font-weight:bold;
	}
.td1 {
	border-style: solid;
	border-width: 1px 1px 0px 0px;
	height:15px
	}
.td2 {
	border-style: solid;
	border-width: 1px 1px 0px ;
	background-color: #9CF;
	border-color: #000;
	height:15px
	}
.td3 {
	border-style: solid;
	border-width: 1px 1px;
	background-color: #9CF;
	border-color: #000
	}
.td4 {
	border-style: solid;
	border-width: 1px 1px 1px 0px;
	}
.td5 {
	border-style: solid;
	border-width: 1px 1px 0px 0px;
	background-color: #9CF;
	border-color: #000;
	height:15px
	}
</style>
</head>

<body>
<table width="100%" cellspacing="0" cellpadding="0">
  <tr>
  	<td colspan="4">
    	<table width="100%">
          <tr>
            <td colspan="4" height="30px" align="center"><h3>现&nbsp;&nbsp;金&nbsp;&nbsp;流&nbsp;&nbsp;量&nbsp;&nbsp;表</h3></td>
          </tr>
          <tr>
            <td width="35%">&nbsp;</td>
            <td width="15%">&nbsp;</td>
            <td width="35%">&nbsp;</td>
            <td width="15%" align="right"><span>会计03表</span></td>
          </tr>
          <tr>
            <td height="15px" align="left"><span>编制单位：</span></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"><span>单位：元&nbsp;&nbsp;</span></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td width="35%" align="center" class="td2"><span>项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目</span></td>
    <td width="15%" align="center" class="td1" style="background-color: #9CF;"><span>金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额</span></td>
    <td width="35%" align="center" class="td1" style="background-color: #9CF;"><span>补&nbsp;&nbsp;充&nbsp;&nbsp;资&nbsp;&nbsp;料</span></td>
    <td width="15%" align="center" class="td1" style="background-color: #9CF;"><span>金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额</span></td>
  </tr>
  <tr>
    <td class="td2"><span class="span1">一、经营活动产生的现金流量：</span></td>
    <td class="td1"  align="right">&nbsp;</td>
    <td class="td5"><span class="span1">1、将净利润调节为经营活动现金流量：</span></td>
    <td class="td1"  align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销售商品、提供劳务收到的现金</span></td>
    <td class="td1"  align="right">-&nbsp;&nbsp;</td>
    <td class="td5" ><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;净利润</span></td>
    <td class="td1"  align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收到的税费返还</span></td>
    <td class="td1"  align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加：计提的资产减值准备</span></td>
    <td class="td1"  align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收到的其他与经营活动有关的现金</span></td>
    <td class="td1"  align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;固定资产折旧</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">现金流入小计</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无形资产摊销</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;购买商品、接受劳务支付的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;长期待摊费用摊销</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付给职工以及为职工支付的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;处置固定资产、无形资产和其他长期资产的损失（减：收益）</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付的各项税费</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;固定资产报废损失</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付的其他与经营活动有关的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公允价值变动损失（收益以"-"号填列）</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">现金流出小计</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;财务费用</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span class="span1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营活动产生的现金流量净额</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投资损失（减：收益）</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span class="span1">二、投资活动产生的现金流量：</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;递延所得税资产减少（增加以"-"号填列）</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收回投资所收到的现金</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;递延所得税负债增加（减少以"-"号填列）</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取得投资收益所收到的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存货的减少（减：增加）</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;处置固定资产、无形资产和其他长期资产所收回的现金净额</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营性应收项目的减少（减：增加）</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收到的其他与投资活动有关的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营性应付项目的增加（减：减少）</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">现金流入小计</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;购建固定资产、无形资产和其他长期资产所支付的现金</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5"><span class="span1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营活动产生的现金流量净额</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投资所支付的现金</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5">&nbsp;</td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付的其他与投资活动有关的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5">&nbsp;</td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">现金流出小计</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5">&nbsp;</td>
    <td class="td1" align="right">&nbsp;</td>
  </tr> 
  <tr>
    <td class="td2"><span class="span1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投资活动产生的现金流量净额</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span class="span1">2、不涉及现金收支的投资和筹资活动：</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span class="span1">三、筹资活动产生的现金流量：</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;债务转为资本</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;吸收投资所收到的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一年内到期的可转换公司债券</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;借款所收到的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;融资租入固定资产</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收到的其他与筹资活动有关的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5">&nbsp;</td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">现金流入小计</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5">&nbsp;</td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;偿还债务所支付的现金</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5">&nbsp;</td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分配股利、利润或偿付利息所支付的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span class="span1">3、现金及现金等价物净增加情况：</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付的其他与筹资活动有关的现金</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现金的期末余额</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">现金流出小计</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;减：现金的期初余额</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td align="center" class="td2"><span class="span1">筹资活动产生的现金流量净额</span></td>
    <td class="td1" align="right">-&nbsp;&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加：现金等价物的期末余额</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td2"><span class="span1">四、汇率变动对现金的影响</span></td>
    <td class="td1" align="right">&nbsp;</td>
    <td class="td5"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;减：现金等价物的期初余额</span></td>
    <td class="td1" align="right">&nbsp;</td>
  </tr>
  <tr>
    <td class="td3"><span class="span1">五、现金及现金等价物净增加额</span></td>
    <td class="td4" align="right">-&nbsp;&nbsp;</td>
    <td class="td4" style="background-color: #9CF;"><span class="span1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现金及现金等价物净增加额</span></td>
    <td class="td4" align="right">-&nbsp;&nbsp;</td>
  </tr>
</table>
</body>
</html>
