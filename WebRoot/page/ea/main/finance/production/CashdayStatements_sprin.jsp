<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>单据现金银行日记账</title>
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
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 9px;
}

body {
	margin-left: 15px;
}

		 th, TH {
				font-size: 12px;
				border-color: #000000;
				height:18;
}
    </style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
	var sdate = "${sdate}";
	var tt = "${tt}";
	var balance2=0;
	var qcye=parseFloat("${qcye}");
	var num=0;
	var type="";
	var zz="${zz}"
	$(function() {
		var monsum = 0;
		$("span.money1").each(function() {
			var money = $(this).text();
			var id = $(this).parent().parent().attr("id");
			if (money != "" && money.length != 0) {
				monsum = parseFloat(money);
				DX(Math.abs(monsum), id, "a");
			}
		});
		$("span.money2").each(function() {
			var money = $(this).text();
			var id = $(this).parent().parent().attr("id");
			if (money != "" && money.length != 0) {
				monsum = parseFloat(money);
				DX(Math.abs(monsum), id, "b");
			}
		});
		$("span.PbillsTypeID").each(function() {
			var money = $(this).text();
			var id = $(this).parent().parent().attr("id");
			var yue=0;
			if($(this).parent().parent().find("span.yue").text()!=""){
				yue=parseFloat($(this).parent().parent().find("span.yue").text());
			}
			if(zz=="00"){
				if(num==0){
					if(money=="scode20130104uyj3s8t4b50000000002"||money=="scode201303255bfk6jsacr0000000002"){
						balance2=parseFloat(balance2)+qcye;
					}else if(money=="scode20130104uyj3s8t4b50000000003"||money=="scode201303255bfk6jsacr0000000003"){
						balance2=qcye-parseFloat(balance2);
					}
				}else{
					if(money=="scode20130104uyj3s8t4b50000000002"||money=="scode201303255bfk6jsacr0000000002"){
						balance2=parseFloat(balance2)+yue;
					}else if(money=="scode20130104uyj3s8t4b50000000003"||money=="scode201303255bfk6jsacr0000000003"){
						balance2=parseFloat(balance2)-yue;
					}
				}
			}else{
				if(num==0){
					if(money=="scode20130104uyj3s8t4b50000000006"||money=="scode201303255bfk6jsacr0000000003"){
						balance2=parseFloat(balance2)+qcye;
					}else if(money=="scode20130104uyj3s8t4b50000000007"||money=="scode201303255bfk6jsacr0000000002"){
						balance2=qcye-parseFloat(balance2);
					}
				}else{
					if(money=="scode20130104uyj3s8t4b50000000006"||money=="scode201303255bfk6jsacr0000000003"){
						balance2=parseFloat(balance2)+yue;
					}else if(money=="scode20130104uyj3s8t4b50000000007"||money=="scode201303255bfk6jsacr0000000002"){
						balance2=parseFloat(balance2)-yue;
					}
				}
			}
			DX(Math.abs(balance2), id, "c");
			num=num+1;
		}); 
		var url = basePath + "/ea/beginningbalance/sajax_ea_ajaxSava.jspa?zz="+zz+"&balance2="+balance2+"&sdate="+sdate+"&date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
				}
			});
		//主函数
		function DX(n, id, type) {
			if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
				return "数据非法";
			n = n.toFixed(2);
			var p = n.indexOf('.');
			if (p >= 0)
				n = n.substring(0, p) + n.substr(p + 1, 2);
			var j = n.length - 1;
			for ( var i = 0; i < n.length; i++) {
				var f = 9 - parseInt(i);
				if (type == "a")
					$("span#" + f, $("#" + id)).text(n.charAt(j));
				else if (type == "b")
					$("span#1" + f, $("#" + id)).text(n.charAt(j));
				else {
					f = 11 - parseInt(i);
					$("span#2" + f, $("#" + id)).text(n.charAt(j));
				}
				j = j - 1;
			}
		}
	});
</script>
</head>
<body>
	<table width="100%" border="1" cellspacing="0" cellpadding="0">
		<tr>
			<th colspan="43" align="center" style="font-size: 20px"><s:if test="zz==00">现金收入</s:if>
			<s:elseif test="zz==03">现金支出</s:elseif>
			<s:elseif test="zz==04">应收账款</s:elseif>
			<s:elseif test="zz==05">应付账款</s:elseif>
			<s:elseif test="zz==01">银行日记账</s:elseif></th>
		</tr>
		<tr>
			<th width="4%" rowspan="2" align="center">公司</th>
			<th width="4%" rowspan="2" align="center">部门</th>
			<th width="4%" rowspan="2" align="center">责任人</th>
			<th width="4%" rowspan="2" align="center">银行账号</th>
			<th width="3%" rowspan="2" align="center">票据归档号</th>
			<th width="3%" rowspan="2" align="center">款源日期</th>
			<th width="3%" rowspan="2" align="center">入账日期</th>
			<th width="10%" rowspan="2" align="center">品名名称</th>
			<th width="4%" rowspan="2" align="center">事由</th>
			<th width="4%" rowspan="2" align="center">科目管理</th>
			<th width="3%" rowspan="2" align="center">费用类别</th>
			<th width="1%" rowspan="2">&nbsp;</th>
			<th colspan="9" align="center">借方金额</th>
			<th width="1%" rowspan="2" align="center">方向</th>
			<th colspan="9" align="center">贷方金额</th>
			<th width="1%" rowspan="2" align="center">&nbsp;</th>
			<th colspan="11" align="center">余额</th>
		</tr>
		<tr>
			<th width="2%">百</th>
			<th width="2%">十</th>
			<th width="2%">万</th>
			<th width="2%">千</th>
			<th width="2%">百</th>
			<th width="2%">十</th>
			<th width="2%">元</th>
			<th width="2%">角</th>
			<th width="2%">分</th>
			<th width="2%">百</th>
			<th width="2%">十</th>
			<th width="2%">万</th>
			<th width="2%">千</th>
			<th width="2%">百</th>
			<th width="2%">十</th>
			<th width="2%">元</th>
			<th width="2%">角</th>
			<th width="2%">分</th>
			<th width="2%">亿</th>
			<th width="2%">千</th>
			<th width="2%">百</th>
			<th width="2%">十</th>
			<th width="2%">万</th>
			<th width="2%">千</th>
			<th width="2%">百</th>
			<th width="2%">十</th>
			<th width="2%">元</th>
			<th width="2%">角</th>
			<th width="2%">分</th>

		</tr>
		<s:iterator value="CashierBillslists">
			<tr id="${goodsBillsID}">
				<td>${companyname}</td>
				<td>${departmentname}</td>
				<td>${staffname}</td>
				<td>${companyBankNum}</td>
				<td>${archivesNum}</td>
				<td>${startDate}</td>
				<td>${endDate}</td>
				<td>${goodsName}</td>
				<td>${reasonThing}</td>
				<td>${subjectsName}</td>
				<td>${costType}</td>
				<td>&nbsp;<span style="display: none;" class="money1">${loan}</span>
				</td>
				<td><span id='1'></span>
				</td>
				<td><span id='2'></span>
				</td>
				<td><span id='3'></span>
				</td>
				<td><span id='4'></span>
				</td>
				<td><span id='5'></span>
				</td>
				<td><span id='6'></span>
				</td>
				<td><span id='7'></span>
				</td>
				<td><span id='8'></span>
				</td>
				<td><span id='9'></span>
				</td>
				<td>${direction}<span style="display: none;" class="money2">${forLoan}</span>
				</td>
				<td><span id='11'></span>
				</td>
				<td><span id='12'></span>
				</td>
				<td><span id='13'></span>
				</td>
				<td><span id='14'></span>
				</td>
				<td><span id='15'></span>
				</td>
				<td><span id='16'></span>
				</td>
				<td><span id='17'></span>
				</td>
				<td><span id='18'></span>
				</td>
				<td><span id='19'></span>
				</td>
				<td><span style="display: none;" class="yue">${balance}</span>
				<span style="display: none;" class="PbillsTypeID">${PbillsTypeID}</span>
				</td>
				<td><span id='21'></span>
				</td>
				<td><span id='22'></span>
				</td>
				<td><span id='23'></span>
				</td>
				<td><span id='24'></span>
				</td>
				<td><span id='25'></span>
				</td>
				<td><span id='26'></span>
				</td>
				<td><span id='27'></span>
				</td>
				<td><span id='28'></span>
				</td>
				<td><span id='29'></span>
				</td>
				<td><span id='210'></span>
				</td>
				<td><span id='211'></span>
				</td>
			</tr></s:iterator>
	</table>
</body>
</html>
