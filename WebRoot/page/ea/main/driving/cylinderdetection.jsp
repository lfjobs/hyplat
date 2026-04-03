<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钢瓶检测记录表</title>
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

<style type="text/css">
*{ margin:0; padding:0px;}
a, a:link { color: #222; text-decoration: none; }
a:visited {  }a:active, a:hover {}
a:focus { outline: none; }
.fl{ float:left;}
.fr{ float:right;}
.clear { diplay: block!important; float: none!important; clear: both; overflow: hidden; width: auto!important; height: 0!important; margin: 0 auto!important; padding: 0!important; font-size: 0; line-height: 0; }
.table_con{font-family: '宋体 Regular','宋体'; font-size:12px; float:left; width:1150px; margin:0; padding:0px; color:#000;margin-top:30px;  margin-left:10px;}
.table_con h1{ font-size:18px; margin:0px;}
.left_t{ width:17px; height:29px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/left_t_c.png) no-repeat left bottom;}
.cross{ width:400px; height:30px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cross.png) repeat-x center;}
.title_t{ width:310px; height:29px; line-height:30px;text-align:center;}
.right_t{ width:17px; height:29px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/right_t_c.png) no-repeat right bottom;}
.left{width:17px; height:880px; background:url(v/vertical.png) repeat-y left;}
.right{width:17px; height:880px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/vertical.png) repeat-y right;}
.left_b{ width:17px; height:30px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/left_b_c.png) no-repeat left top; margin-bottom:30px;}
.cross_b{ width:1116px; height:16px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cross.png) repeat-x 5px; margin-bottom:30px;}
.right_b{ width:17px; height:30px; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/right_b_c.png) no-repeat right top; margin-bottom:30px;}
._con{ width:1116px; float:left; height:880px;}
.number{ width:100%; height:40px; line-height:40px; margin-top:10px; font-size:14px; text-align:right;}
.number p{ color:#000; font-size:12px;}
.title_b{ width:50%; height:40px; line-height:40px; }
.title_b p{ width:50%; float:left;}
._con table{ float:left; line-height:30px;}
._con table tr td{ border:1px solid #3fb2ad; border-right:none; border-bottom:none; text-align:center;}
._con table tr .td_r{ border-right:1px solid #3fb2ad;}
._con table tr .td_l{ border-left:none;}
._con table tr .td_b{ border-bottom:1px solid #3fb2ad;}
._con table tr .td_t{ border-top:none;}
._con table tr td p{ line-height:14px;}
.bg_color{ background-color:#e3e3e3;}
._con table tr .align_l{ text-align:left}
.sec,.sec1{ cursor:pointer;}
.sec{ color:#FFFFFF; font-weight:bold; text-align:center; background-color:#3fb2ad;}
.sec1{ color:#3fb2ad; text-align:center; background-color:#FFFFFF;}
div{ line-height:22px;}
.big1{ border:1px solid #3fb2ad;}
select{width: 100%}
.littletext{width: 80px;border: no}
.diagram h3{ text-align:center; font-size:16px; color:#555;}
.diagram .sd{ width:988px; height:488px; margin:auto; background:url(<%=basePath%>page/ea/main/driving/signup/carlinderimg/cylinders.jpg) no-repeat ; margin-top:30px; position:relative;}
.diagram .note{ float:left; margin-top:10px; font-size:14px; width:100%; text-align:center;}
.diagram .floor{ width:100%; height:80px; margin-top:20px;}
.diagram .floor p{ width:50%; line-height:30px; float:left;}
.diagram .gongsi{ width:1016px; float:left; text-align:right;}
</style>
<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var carCylinderId = "";
			var token = 0;
			var select = 1;
			var pNumber = ${pageNumber}; 
			var notoken = 0;
			var search = '${search}';
</script>
<script language="javascript">
function $(id){
return document.getElementById(id);
}
function xxk(num,id,ii,iii)
{
var dq;
for(var i=1;i<=num;i++)
{
   if(i==id){
  dq=$(ii+i).className='sec big1'; //当前选项
  $(iii+i).style.display="block";
   }
   else
   {
   dq=$(ii+i).className='sec1 big1';
   $(iii+i).style.display="none";
   }
}
}
</script>
</head>
<body>
<br />
<table width="1000" border="0" cellpadding="4" cellspacing="0" class="big">
  <tr>
     <td width="250" class="sec big1" id="ts1" onclick="xxk(2,1,'ts','tts')">钢瓶记录表</td>
    <td width="250" bgcolor="#FFFFFF" id="ts2" class="sec1 big1" onclick="xxk(2,2,'ts','tts')">最低壁厚检测表</td>
	<td width="250" bgcolor="white">&nbsp;</td><td width="250" bgcolor="white">&nbsp;</td>
    
  </tr>
  <tr>
    <td colspan="4" bgcolor="#FFFFFF">
	<!-- 钢瓶记录表 -->
<div id="tts1">
<div class="table_con fl">
<div class="clear"></div>
<div class="left_t fl"></div>
<div class="cross fl"></div>
<h1 class="title_t fl">CNG钢制气瓶定期检验与评分记录表</h1>
<div class="right_t fr"></div>
<div class="cross fr"></div>
<div class="clear"></div>
<div class="left fl"></div>
<div class="_con">
   <h3 class="number fr"><p>检验编号: <s:if test='obj[40]==null'>暂未派工检测</s:if>
								<s:else>
									<span>${obj[40]}</span>
								</s:else></p><label></label></h3>
   <div class="clear"></div>
   <div class="title_b fl">送检单位 四川兰特清洁汽车安全评价检测有限公司</div>
   <div class="title_b fl"> <p>室温℃   <s:if test='obj[41]==null'>暂未派工检测</s:if>
								<s:else>
									<span>${obj[41]}</span>
								</s:else></p>
       <p>水温℃   <s:if test='obj[42]==null'>暂未派工检测</s:if>
								<s:else>
									<span>${obj[42]}</span>
								</s:else></p></div>
   <div class="clear"></div>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr style=" height:50px; line-height:50px;">
        <td class="bg_color" width="4%" rowspan="2"><p>原</p>
        <p>始</p>
        <p>记</p>
        <p>录</p>
        <p>登</p>
        <p>记</p></td>
        <td width="8%">车牌号码</td>
        <td width="8%">气瓶编号</td>
        <td width="8%">型号</td>
        <td width="8%">制造单位</td>
        <td width="8%">出厂日期</td>
        <td width="8%">容积（L）</td>
        <td width="8%">重量（KG）</td>
        <td width="8%"><p>设计壁厚</p>
        <p>（mm）</p></td>
        <td width="8%">类型</td>
        <td width="8%"><p>公称工作</p>
        <p>压力</p>
        <p>（Mpa）</p></td>
        <td width="8%"><p>水压试验</p>
        <p>压力</p>
        <p>（Mpa）</p></td>
        <td width="8%" class="td_r">投用日期</td>
      </tr>
      <tr style=" height:50px; line-height:50px;">
       <td>${obj[21]}</td>
        <td>${obj[6]}</td>
        <td>${obj[9]}</td>
        <td>${obj[10]}</td>
        <td> ${fn:substring(obj[11], 0, 10)}</td>
        <td>${obj[13]}</td>
        <td>${obj[12]}</td>
        <td>${obj[8]}</td>
        <td>${obj[7]}</td>
        <td>${obj[14]}</td>
        <td>${obj[15]}</td>
        <td class="td_r"> ${fn:substring(obj[31], 0, 10)}</td>
      </tr>
      <tr>
        <td class="bg_color" rowspan="8"><p>检</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
          <p>验</p>
          <p>&nbsp;</p>
          <p>&nbsp;</p>
        <p>记</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>录</p></td>
        <td colspan="5">外观检查</td>
        <td colspan="2">壁厚测量</td>
        <td colspan="5" class="td_r">&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="2">机械行损伤</td>
        <td rowspan="2">热损伤</td>
        <td rowspan="2">腐蚀</td>
        <td colspan="2">简体变形</td>
        <td rowspan="2">测量点数</td>
        <td rowspan="2"><p>简体实测</p>
        <p>最小壁厚</p>
        <p>（mm）</p></td>
        <td rowspan="2">音响检查</td>
        <td rowspan="2"><p>瓶口</p>
        <p>螺纹检查</p></td>
        <td rowspan="2">检查结果</td>
        <td rowspan="2">检查人</td>
        <td rowspan="2" class="td_r">&nbsp;</td>
      </tr>
      <tr>
        <td>圆度</td>
        <td>直线度</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="td_r">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="6">无损检测</td>
        <td colspan="3" rowspan="2">检查结果</td>
        <td colspan="2" rowspan="2">检查人</td>
        <td rowspan="2" class="td_r">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3">磁粉探伤</td>
        <td colspan="3">超声波探伤</td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
        <td colspan="3">&nbsp;</td>
        <td colspan="3">&nbsp;</td>
        <td colspan="2">&nbsp;</td>
        <td class="td_r">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="12" class="td_r td_l td_t">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="6">容积测定</td>
            <td colspan="10">水压试验（33.1MPa）</td>
            </tr>
          <tr>
            <td colspan="2">重量测定</td>
            <td width="7%" rowspan="2"><p>瓶水总</p>
              <p>重（kg)</p></td>
            <td width="4%" rowspan="2"><p>水重</p>
              <p>（kg)</p></td>
            <td width="7%" rowspan="2"><p>水容积</p>
              <p>系数</p>
              <p>（L/kg）</p></td>
            <td width="7%" rowspan="2"><p>实测</p>
              <p>容</p>
              <p>（kg)</p></td>
            <td width="6%" rowspan="2"><p>容积增大</p>
              <p>率（%）</p></td>
            <td width="3%" rowspan="2">βT</td>
            <td width="4%" rowspan="2"><p>Ph×</p>
              <p>βt</p></td>
            <td width="4%" rowspan="2"><p>A值</p>
              <p>（ml）</p></td>
            <td width="4%" rowspan="2"><p>B值</p>
              <p>（ml）</p></td>
            <td width="7%" rowspan="2"><p>残余</p>
              <p>变形值</p>
              <p>（ml）</p></td>
            <td width="4%" rowspan="2"><p>全变</p>
              <p>形值</p>
              <p>（ml）</p></td>
            <td width="7%" rowspan="2"><p>残余</p>
              <p>变形值</p>
              <p>（ml）</p></td>
            <td width="9%" rowspan="2">检查结果</td>
            <td width="8%" rowspan="2">检查人</td>
          </tr>
          <tr>
            <td width="9%"><p>实测重量</p>
              <p>（kg）</p>
              <p></p></td>
            <td width="10%"><p>重量损失</p>
              <p>率（100%）</p></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2" rowspan="2">内部干燥</td>
            <td colspan="5">瓶阀检验</td>
            <td colspan="4" rowspan="2">是否更换泄放装置</td>
            <td colspan="3" rowspan="2">气密性试验（MPa）</td>
            <td rowspan="2">检查结果</td>
            <td rowspan="2">检查人</td>
          </tr>
          <tr>
            <td colspan="3">飞轮T1阀</td>
            <td colspan="2">飞轮5T阀门</td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
            <td colspan="3">&nbsp;</td>
            <td colspan="2">&nbsp;</td>
            <td colspan="4">&nbsp;</td>
            <td colspan="3">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">检验标记</td>
            <td colspan="3">下次检验日期</td>
            <td colspan="2">涂敷</td>
            <td colspan="7">抽真空或置换</td>
            <td>检查结果</td>
            <td>检查人</td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
            <td colspan="3">&nbsp;</td>
            <td colspan="2">补漆</td>
            <td colspan="7">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7">气瓶拆装单位</td>
            <td colspan="7">检查结果</td>
            <td colspan="2">检查人</td>
            </tr>
          <tr>
            <td colspan="7">&nbsp;</td>
            <td colspan="7">&nbsp;</td>
            <td colspan="2">&nbsp;</td>
            </tr>
        </table>

        </td>
      </tr>
      <tr>
        <td class="td_b bg_color"><p>备</p>
        <p>&nbsp;</p>
        <p>注</p></td>
        <td colspan="12" class="td_b td_r td_t">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style=" line-height:30px;">
          <tr>
            <td rowspan="4" class="align_l td_l">
             <p style="line-height:30px; float:left; margin:0px; margin-left:20px; width:700px;"> 　　依据《气瓶安全监督规程》TSGR0009-2009《车用气瓶安全技术监察规程》、川质监办 [2009] 202号文和GB19533-2004《汽车用压缩天然气钢瓶定期检验与评定》制定此表。所用主要设备仪器有：空压机2V-1/250\磁粉探伤机CJW-5000Z\电动试压泵4DSY-22/63\瓶阀装卸机TJW-1\内窥镜SGJ\电子称TCS-500、测厚仪TT110           
              </p>
              <p style="line-height:30px; float:left; margin:0px; margin-left:20px; width:700px;">
              　　　　表中各检验项目，合格项打√，不合格项打×，不填项打／
              </p>
              </td>
            <td width="320" class="align_l">　检查结论：</td>
          </tr>
          <tr>
            <td class="align_l">　检 查 员：</td>
          </tr>
          <tr>
            <td class="align_l">　记 录 员：</td>
          </tr>
          <tr>
            <td class="align_l">　检验日期：</td>
          </tr>
        </table>
        </td>
      </tr>
    </table>
    
</div>
<div class="right fr"></div>
<div class="clear"></div>
<div class="left_b fl"></div>
<div class="cross_b fl"></div>
<div class="right_b fl"></div>
</div>




</div>
<!-- 最低壁厚检测表 -->
<div id="tts2" style="display:none;">
<p></p><p></p>

<div class="table_con fl">
<div class="clear"></div>
<div class="left_t fl"></div>
<div class="cross fl"></div>
<h1 class="title_t fl">车用CNG钢瓶最低壁厚检测记录</h1>
<div class="right_t fr"></div>
<div class="cross fr"></div>
<div class="clear"></div>
<div class="left fl"></div>
<div class="_con">
<div class="clear"></div>
<div class="diagram fl">
   <h3>钢瓶测厚点分布示意图</h3>
   <div class="clear"></div>
   <div class="sd">
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:50px; left:221px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:144px; left:70px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:249px; left:70px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:354px; left:70px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:144px; left:374px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:248px; left:374px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:354px; left:374px;"/>
   
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:50px; left:721px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:144px; left:566px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:249px; left:566px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:354px; left:566px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:144px; left:870px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:248px; left:870px;"/>
   <input type="text"  style=" border:none; line-height:20px; height:20px; width:40px; position:absolute; outline:none;top:354px; left:870px;"/>
   </div>
   <div class="clear"></div>
   <p class="note">注：测厚值单位为毫米，如某测点值小于设计壁厚，应在附近加测几点确认</p>
   <div class="clear"></div>
   <div class="floor fl">
   <p>检测人员</p>
   <p align="center">检验日期</p>   
</div>
   <div class="clear"></div>
   <p class="gongsi">四川兰特清洁汽车检测安全评价检测有限公司</p>
   </div>
</div>
<div class="right fr"></div>
<div class="clear"></div>
<div class="left_b fl"></div>
<div class="cross_b fl"></div>
<div class="right_b fl"></div>
</div>



</div>
</td>
</tr>
</table>


</body>
</html>
