<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>今日价格表</title>
		<%@ page language="java" pageEncoding="UTF-8" %>
		<%@ taglib uri="/struts-tags" prefix="s" %>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; %>
		<script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
		<script type="application/javascript" src="<%=basePath%>css/ea/collage/phl/slick/slick.js"></script>
		<link  type="text/css" rel="stylesheet"  href="<%=basePath%>css/ea/collage/phl/slick/slick.css">
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/todayPrice.css">
	</head>
	<body>
		<header>
			<div>
				<i id="btn"></i>
				<p>
					【<span id="dateShow">&nbsp;&nbsp;&nbsp;</span>】
				</p>
			</div>
		</header>
		<div>
			<table border="1" cellspacing="0" cellpadding="0">
				<tr>
					<th>序号</th>
					<th>条码</th>
					<th>商品名称</th>
					<th>时间</th>
					<th>最新价格</th>
				</tr>
			</table>
		</div>
		<div class="slider single-item" id="priceList">
		</div>
	</body>
	<script type="text/javascript">
		var pageNumber=1;
		var pageCount;
        var basePath="<%=basePath%>";
        var htl;
        var j=1;
		$(function () {
			$("#dateShow").text(getDate());
            beginSlide();//开始轮播
            setTimeout(reloadSlide,600000);//每隔十分钟重新加载页面一次600000
        })

        //轮播
		function beginSlide() {
            $('.single-item').slick({
                dots: false,//指示点
                autoplay: true,//自动播放
                autoplaySpeed: 1000,//自动播放时间
                onInit:init(),
                onAfterChange:function () {//下一次切换之前
                    if(pageNumber<pageCount){
                        pageNumber++;
                        init();
                        $('.single-item').slickAdd(htl);
                    }
                }
            });
            $('.single-item').slickAdd(htl);
            if(pageNumber<pageCount){0
                pageNumber++;
                init();
                $('.single-item').slickAdd(htl);
			}

        }
        //重新加载页面
		function reloadSlide() {
            window.location.reload();
            setTimeout(reloadSlide,600000)
        }
		function getDate() {
            var myDate = new Date();
            var month=myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
            var date=myDate.getDate();        //获取当前日(1-31)
            var str = "星期" + "日一二三四五六".charAt(new Date().getDay());
			if(month.toString().length<2){
			    month="0"+month;
			}
            if(date.toString().length<2){
                date="0"+date;
            }
           return month+"月"+date+"日"+" "+str;
        }


        var x=1;
        $("#btn").click(function(){
            if(x==1){
                $('.single-item').slickPause();
                alert("已暂停");
                x=2;
            }else{
                x=1;
                $('.single-item').slickPlay();
                alert("开始运行");
            }
        })


		function init() {
            var url = basePath+"/ea/productdesign/sajax_ea_todayPrice.jspa?pageNumber="+pageNumber;
            $.ajax({
                url:encodeURI(url),
                type:"get",
                async:false,
                dataType:"json",
                traditional: true,
				data:{
                    "companyid":"company20180510CQZCDKTT690000006064"
				},
                success:function (data) {
                    var member = eval("("+data+")");
                    var pageForm = member.pageForm;
                    var priceList=pageForm.list;
					if(priceList==null||priceList.length==0){
					    alert("数据获取失败");
					    return;
					}
                    pageCount=pageForm.pageCount;
                    htl="";
                    var price;
					htl+="<div><table border='1' cellspacing='0' cellpadding='0'>";
                    for(var i = 0;i<priceList.length;i++){
                        var priceType="";
                        //优先级活动价》会员价》零售价
                        if(priceList[i][8]!=""&&priceList[i][8]!=null){
                            price=toDecimal2(priceList[i][8]);
                            if(priceList[i][12]=="00"){
                                priceType="活动价";
							}else if (priceList[i][12]=="01"){
                                priceType="特价";
							}
                        }else if(priceList[i][9]!=""&&priceList[i][9]!=null){
                            price=toDecimal2(priceList[i][9]);
                            priceType="会员价";
                        }else if(priceList[i][3]!=""&&priceList[i][3]!=null){
                            price=toDecimal2(priceList[i][3]);
                        }else{
                            price="-----";
                        }
                        htl+="<tr><td>"+j+"</td><td>"+isNull(priceList[i][6])+"</td><td>"+isNull(priceList[i][2])+"</td>" +
							"<td><span class='startTime'>●"+isNull(priceList[i][10])+"</span>" +
							"<span class='endTime'>●"+isNull(priceList[i][11])+"</span></td>" +
							"<td><span class='priceType'>"+priceType+"</span>" +
							"<span class='price'>￥"+price+"</span></td></tr>";
                        j++;
                    }
                    htl+="</table></div>";
                }
            })
        }
        function isNull(n) {
            if(n==null){
                n="----------";
            }
            return n;
        }
        function toDecimal2(x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return false;
            }
            var f = Math.round(x*100)/100;
            var s = f.toString();
            var rs = s.indexOf('.');
            if (rs < 0) {
                rs = s.length;
                s += '.';
            }
            while (s.length <= rs + 2) {
                s += '0';
            }
            return s;
        }
	</script>
</html>
