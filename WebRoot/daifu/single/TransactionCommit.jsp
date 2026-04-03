<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		response.setCharacterEncoding("UTF-8");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8"/>
<title>交易信息</title>
<style type="text/css">
/*公共样式*/
html,body{
    -moz-user-select: none;
    -khtml-user-select: none;
    user-select: none;
}
body{margin:0 auto; background-color: #f0f0f0;width:100%;}
html,body,div,p,ul,li,dl,dt,dd,h1,h2,h3,h4,h5,h6,form,input,select,button,textarea,iframe,table,th,td{margin:0 0; padding:0px 0px; font-size:12px; color:#666; font-family:'宋体';}
img{ cursor:pointer;border:none;}
ul,li,ol{ list-style: none;}
a,a:link { text-decoration: none; }
a:active,a:hover { text-decoration: none; }
a:focus{ outline: none; }
.left{float:left;}
.right{float:right;}
/*公共样式*/
.wfj10_003{ width:100%; min-width:320px; max-width:800px; margin:0 auto; background-color:#FFF; }
.wfj10_003 .wfj10_003_title{ width:100%; height:40px; line-height:40px; background-color:#3C97F0;}
.wfj10_003 .wfj10_003_title .wfj10_003_width{ width:95%; margin:0 auto;}
.wfj10_003 .wfj10_003_title .wfj10_003_width div ul li{ float:left; font-size:18px; text-align:center; color:#FFF;font-weight:bold;}
.wfj10_003 .wfj10_003_title .wfj10_003_width div ul li a{font-size:24px; text-align:center; color:#FFF;}
.wfj10_003 .wfj10_003_content{ width:100%;}
.wfj10_003 .wfj10_003_content #info{ width:100%;}
.wfj10_003 .wfj10_003_content #info tr{ height:26px;}
.wfj10_003 .wfj10_003_content #info tr .td1{width:35%;text-align:right;}
.wfj10_003 .wfj10_003_content #info tr .td2{width:65%;text-align:left;}
.wfj10_003 .wfj10_003_content #info tr td input[type=text]{ width:80%; height:16px; background-color:#BFBFBF; color:#FFF;}
.wfj10_003 .wfj10_003_content #commit{ width:80%; margin:0 auto;}
.wfj10_003 .wfj10_003_content #commit tr td input{ width:100%; height:36px; line-height:36px; color:#FFF; border-radius:6px; margin-top:15px; font-size:16px; font-weight:bold; cursor:pointer; background-color:#FB6800;}
</style>
</head>

<body>
<form name="Response" action="<%=basePath %>ea/jifen/ea_tiXian.jspa" method="POST" >
<input type="hidden" name="staffId" value="${staffId }">
	<div class="wfj10_003">
    	<div class="wfj10_003_title">
        	<div class="wfj10_003_width">
            	<div>
                	<ul>
                    	<li style="width:10%;"><a href="javascript:;"><</a></li>
                    	<li style="width:80%;">交易请求信息</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="wfj10_003_content">
        	<table id="info">
<tr>
                	<td class="td1">商户号：</td>
   	    <td class="td2"><input type="text" value="${payInput.merId}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">商户日期：</td>
               	  <td class="td2"><input type="text" value="${payInput.merDate}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">流水号：</td>
               	  <td class="td2"><input type="text" value="${payInput.merSeqId}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">收款号：</td>
               	  <td class="td2"><input type="text" value="${payInput.cardNo}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">收款人姓名：</td>
               	  <td class="td2"><input type="text" value="${payInput.userName}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">开户银行：</td>
               	  <td class="td2"><input type="text" value="${payInput.openBank}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">省份：</td>
               	  <td class="td2"><input type="text" value="${payInput.prov}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">城市：</td>
               	  <td class="td2"><input type="text" value="${payInput.city}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">金额：</td>
               	  <td class="td2"><input type="text" value="${payInput.transAmt}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">用途：</td>
               	  <td class="td2"><input type="text" value="${payInput.purpose}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">支行：</td>
               	  <td class="td2"><input type="text" value="${payInput.subBank}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">付款标志：</td>
               	  <td class="td2"><input type="text" value="${payInput.flag}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">版本号：</td>
               	  <td class="td2"><input type="text" value="${payInput.version}" readonly="readonly" /></td>
              </tr>
            	<tr>
                	<td class="td1">渠道类型：</td>
               	  <td class="td2"><input type="text" value="${payInput.termType}" readonly="readonly" /></td>
              </tr>
            </table>
<table id="commit">
            	<tr>
                	<td><input type="submit" value="提交"></input></td>
                </tr>
            </table>
            <input type=hidden name="entity.merId" value="${payInput.merId}">
<input type=hidden name="entity.merDate" value="${payInput.merDate}">
<input type=hidden name="entity.merSeqId" value="${payInput.merSeqId}">
<input type=hidden name="entity.cardNo" value="${payInput.cardNo}">
<input type=hidden name="entity.userName" value="${payInput.userName}">
<input type=hidden name="entity.openBank" value="${payInput.openBank}">
<input type=hidden name="entity.prov" value="${payInput.prov}">
<input type=hidden name="entity.city" value="${payInput.city}">
<input type=hidden name="entity.transAmt" value="${payInput.transAmt}">
<input type=hidden name="entity.purpose" value="${payInput.purpose}">
<input type=hidden name="entity.subBank" value="${payInput.subBank}">
<input type=hidden name="entity.flag" value="${payInput.flag}">
<input type=hidden name="entity.version" value="${payInput.version}">
<input type=hidden name="entity.chkValue" value="${payInput.chkValue}">
<input type=hidden name="entity.termType" value="${payInput.termType}">
        </div>
    </div>
    </form>
</body>
</html>