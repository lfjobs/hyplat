<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>工作计划汇总列表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>

<style type="text/css"> 
body,td,th {
	font-size: 12px;
	height: 26px;
}

.table {
	border-collapse: collapse;
	border: 1px solid #a8c7ce;
	 
}

.table th {
	border: 1px solid #a8c7ce;
	color: #1E5494;
	background: #E4F1FA;text-align:center; 
}

.table td {
	border: 1px solid #a8c7ce;
	color: #333;
	style=font-size: 50%;
}
.table .center{ text-align:center;  line-height:26px;}
.table .contents{padding:5px;}
.container{width:703px; margin:40px auto 0;padding:5px 8px;} 
.item{
	height: 500px; 
} 
</style>
 <script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
	</head>
<body >
		<div class="container">
		<s:iterator value="maps" id="bean">
			<div style="height: 35px; border:#069"> 
			</div>
		 <div class="item"> 
		        <table width="701" border="0" cellpadding="0"
					cellspacing="0" height="35" style="margin-top: 15px;">
					<tr>
						<td width="70%" align="right"><strong style="font-size:22px">工作月计划汇总统计表 </strong></td>
						<td width="30%" align="right">&nbsp;</td>
					</tr>
				</table>
				<table width="700" height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="50" height="30" align="right">
							公司：
						</td>
						<td width="250">
							${bean[0][0] }
						</td>

						<td width="80" align="right">
							责任人：
						</td>
						<td width="80">
							${bean[0][3] }
						</td>
					</tr>
				</table>
				<div style="height: 222px; border:#069">
					<table width="701" cellpadding="0" cellspacing="0"
						class="table">
						<tr >
							<th width="15"  height="30"  >
								序号
							</th>
							<th width="45"  height="30" >
								起时间
							</th>
							<th width="45"  height="30" >
								止时间
							</th>
							<th width="190"  height="30"  >
								工作内容
							</th>
							<th width="30"  height="30" >
								工作类别
							</th>
							<th width="15"  height="30" >
								完成情况
							</th>
							<th width="15"  height="30" >
								应得分数
							</th>
							<th width="15"  height="30" >
								扣分
							</th>
							<th width="15"  height="30" >
								实际得分
							</th>
						</tr> 
						<s:iterator value="bean" status="st" id="item">
							<tr >
								<td class="center"  height="30" >
									${st.index + 1 } 
								</td>
								<td class="center" width="40"> 
									${fn:substring(item[4],0,10)} 
								</td>
								<td class="center" width="40"> 
										${fn:substring(item[5],0,10)} 
								</td>
								<td class="contents" width="200">
									${item[6] }
								</td>
								<td class="center" width="30">
									${item[7] }
								</td>
								<td width="15"></td>
								<td width="15"></td>
								<td width="15"></td>
								<td width="15"></td>
							</tr>
						</s:iterator>
					</table>
				</div>
				 
				<table width="702" border="0" cellpadding="0"  cellspacing="0" >
					<tr>
						<td width="86" ><p>公司经理：</p></td>
						<td width="58">&nbsp;</td>
						<td width="60">部门主管：</td>
						<td width="85">&nbsp;</td>
						<td width="56">人事处：</td>
						<td width="76">&nbsp;</td>
						<td width="86">财务审核：</td>
						<td width="48">&nbsp;</td>
						<td width="86">责任人签字：</td>
						<td width="61">&nbsp;</td>
					</tr>
				</table>  
				<table width="703" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80"> 总部总经理:</td>
						<td width="63">&nbsp;</td>
						<td width="85"> 总部部门主管：</td>
						<td width="61">&nbsp; </td>
						<td width="76"> 总部人事处： </td>
						<td width="56">&nbsp; </td>
						<td width="112"> 总财务审核：</td>
						<td width="45">&nbsp; </td>
						<td width="80">&nbsp; </td>
						<td width="45">&nbsp; </td>
					</tr>
				</table> 
			</div>
		</s:iterator> 
		</div>
	</body>
</html>