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
<style>
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
.tbst{width:100%;}
.bootbtnarea{
	align:center;
	text-align: center;
}
.bootbtnarea .trst{
	width:100%;
}
/*公共样式*/
/*wfj10_002*/
.wfj10_002{ width:100%; min-width:320px; max-width:800px; margin:0 auto; background-color:#FFF;}
.wfj10_002 .wfj10_002_title{ width:100%; height:40px; line-height:40px; background-color:#3C97F0;}
.wfj10_002 .wfj10_002_title .wfj10_002_width{ width:95%; margin:0 auto;}
.wfj10_002 .wfj10_002_title .wfj10_002_width div ul li{ float:left; font-size:18px; text-align:center; color:#FFF; font-weight:bold;}
.wfj10_002 .wfj10_002_title .wfj10_002_width div ul li a{font-size:24px; text-align:center; color:#FFF;}
.wfj10_002 .wfj10_002_content{ width:100%;}
.wfj10_002 .wfj10_002_content table{
	margin-top:10px;
	width:100%;
	text-align: center;
}
.wfj10_002 .wfj10_002_content table .td1{ color:#000;width:35%;align:right;text-align: right;}
.wfj10_002 .wfj10_002_content table .td1 span{ color:#F00;}
.wfj10_002 .wfj10_002_content table .td2{width:65%}
.wfj10_002 .wfj10_002_content table .td2 input{ width:90%; height:20px; text-align:left;}
.wfj10_002 .wfj10_002_content table .td3{ width:15%;}
.wfj10_002 .wfj10_002_content table .td4{ width:35%;}
.wfj10_002 .wfj10_002_content table .wfj10_002_button{width:80%; height:36px; line-height:36px; color:#FFF; border-radius:6px; margin-top:8px; font-size:16px; font-weight:bold; cursor:pointer;}
.bgcolorFB6800{ background-color:#FB6800}
.bgcolor3088E9{ background-color:#3088E9}

/*wfj10_002 end*/
</style>
<link rel="stylesheet" type="text/css" href="<%=basePath%>daifu/css/daifu.css"/>
<script type="text/javascript">

function checkForm(){
	var MerID=document.getElementById("merId");
	var OrdId=document.getElementById("merSeqId");
	var TransAmt=document.getElementById("transAmt");
	var TransDate=document.getElementById("merDate");
	var UserName=document.getElementById("userName");
	var Zone1=document.getElementById("zone1");
	var Zone2=document.getElementById("zone2");
	var CardNo=document.getElementById("cardNo");
	var Purpose=document.getElementById("purpose");
	var SubBank=document.getElementById("subBank");
	var OpenBank=document.getElementById("openBank");

	if(UserName.value.length ==0){
			alert("收款人姓名不能为空！");
			return false;
			UserName.onblur();
			}
	if(Zone1.value.length==0){
			alert("省份不能为空！");
			return false;
			Zone1.onblur();
			}
	if(Zone2.value.length==0){
			alert("城市不能为空！");
			return false;
			Zone2.onblur();
			}
	
	if(CardNo.value.length==0){
			alert("收款账号不能为空！");
			return false;
			CardNo.onblur();
			}	
	
	
	if(OrdId.value.length!==0){
			if(OrdId.value.length!==16){
			alert("交易流水号长度非法！");
			return false;
			OrdId.onblur();
			}
			}
	if(TransAmt.value.length==0){
			alert("交易金额不能为空！");
			return false;
			TransAmt.onblur();
			}
	if(TransDate.value.length!==0){
	        if(TransDate.value.length!==8){
			alert("日期长度非法！");
			return false;
			TransDate.onblur();
			}
			}
	if(Purpose.value.length==0){
			alert("用途不能为空！");
			return false;
			Purpose.onblur();
			}
	if(OpenBank.value.length==0){
			alert("开户银行不能为空！");
			return false;
			OpenBank.onblur();
			}	

	else{
	document.loginForm.submit();
	}	

}

</script>	
</head>

<body>
<form method="post" action="<%=basePath %>ea/jifen/ea_gecOrder.jspa" name="Transaction" onSubmit="return checkForm();">
<input type="hidden" name="staffId" value="${param.staffId }"/> 
	<div class="wfj10_002">
    	<div class="wfj10_002_title">
        	<div class="wfj10_002_width">
            	<div>
                	<ul>
                    	<li style="width:10%;"><a href="javascript:;"><</a></li>
                    	<li style="width:80%;">交易信息</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="wfj10_002_content">
        	<table class="tbst">
            	<tr>
                	<td class="td1">交易流水号：</td>
                	<td class="td2"><input type="text" id="merSeqId" name="entity.merSeqId"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>16位数字，不填由系统自动生成</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td class="td1"><span>*</span>收款账号：</td>
                	<td class="td2"><input type="text" id="cardNo" name="entity.cardNo" value="6225880153264495"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>银行卡号或者存折号</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td class="td1">收款人姓名：</td>
                	<td class="td2"><input type="text" id="userName" name="entity.userName" value="周刚"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>必须与开户时的姓名完全一致</td>
                </tr>
            </table>
			<table class="tbst">
            	<tr>
                	<td class="td1"><span>*</span>开户银行：</td>
                	<td class="td2"><input type="text" id="openBank" name="entity.openBank" value="招商银行"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>开户银行名称</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td class="td1"><span>*</span>省份：</td>
                	<td class="td2"><input type="text" id="zone1" name="entity.prov" value="北京"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>收款人开户行所在省</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td class="td1"><span>*</span>城市：</td>
                	<td class="td2"><input type="text" id="zone2" name="entity.city" value="北京"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>收款人开户行所在地区</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td class="td1">交易金额（元）：</td>
                	<td class="td2"><input type="text" id="transAmt" name="entity.transAmt" value="0"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>9位数字，不填默认金额为1元</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td  class="td1"><span>*</span>用途：</td>
                	<td class="td2"><input type="text" id="purpose" name="entity.purpose" value="测试"/></td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td  class="td1">支行：</td>
                	<td class="td2"><input type="text" id="subBank" name="entity.subBank" value="东直门支行"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>开户支行的名称，该字段可以不填</td>
                </tr>
            </table>
            
        	<table class="tbst">
            	<tr>
                	<td class="td1">付款标志：</td>
                	<td class="td3"><input type="radio" name="entity.flag" value="00" checked="checked"/>对私</td>
                	<td class="td3"><input type="radio" name="entity.flag" value="01"/>对公</td>
                	<td class="td4"></td>
                </tr>
            </table>
            
<table class="tbst">
            	<tr>
                	<td class="td1">接口版本：</td>
                	<td class="td2"><input type="text" name="entity.version" value="20150304"/></td>
                </tr>
            	<tr>
                	<td></td>
                	<td>8位数字，Ora接口的版本号为20050501</td>
                </tr>
            </table>
        	<table class="tbst">
            	<tr>
                	<td class="td1">渠道类型：</td>
                	<td class="td2"><select size="1" name="entity.termType">
                    <option  value="07">07：互联网</option>
                    <option  value="08" selected="selected">08：移动端</option>
					</select>必须</td>
                </tr>
            </table>
            <table class="bootbtnarea">
            	<tr>
                	<td class="trst"><input type="submit" class="wfj10_002_button bgcolorFB6800" value="提交"></input></td>
                </tr>
            	<tr>
                	<td class="trst"><input type="reset" class="wfj10_002_button bgcolor3088E9" value="重置"></input></td>
                </tr>
            </table>
        </div>
    </div>
    </form>
</body>
</html>