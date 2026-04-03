<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <title>活动展示</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/style.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0 style="display:none;">
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pageNumber = 0;
    </script>
    <style type="text/css">
        .alert_com .tab .tab-box .tr-1{
            width: 50px;
            position: relative;
        }
        .alert_com .tab .tab-box .tr-2{
            width: 50px;
        }
        .alert_com .tab .tab-box .tr-3{
            width: 200px;
        }
        .alert_com .tab .tab-box .tr-4{
            width: 220px;
        }
        .alert_com .tab .tab-box .tr-5{
            width: 200px;
        }
        .alert_com .tab .tab-box .tr-6{
            width: 200px;
        }
        .alert_com .tab .tab-box .tr-7{
            width: 80px;
        }
    </style>
</head>
<body>
<section class="commission des">
    <!--活动展示-->
    <div class="alert-com" style="display: block;">

        <div class="alert_com">
            <div class="head">
                <h3 style="align-content: center">活动展示</h3>
                <div class="search">
                    <input type="text" placeholder="请输入活动名称" id="searchs">
                    <img src="http://localhost:8080/images/ea/finance/brokerage/images/search.png" alt="" id="searchProduct">
                </div>
                <input type="button" value="关闭" id="close">
                <input type="button" value="打印" id="print">
                <div class="page">
                    <a>第<span id="currentPage">${pageForm.pageNumber}</span>页</a>
                    <a onclick="lastPage()">上一页</a>
                    <a onclick="nextPage()">下一页</a>
                    <a>共<span id="sumPage">${pageForm.pageCount}</span>页</a>
                    <input type="hidden" id="pageNumber" value="${pageForm.pageNumber}">
                    <input type="hidden" id="pageCount" value="${pageForm.pageCount}">
                </div>
            </div>
            <div class="tab">
                <table width="958" class="tit">
                    <thead>
                    <tr>
                        <th style="width: 50px;">选择</th>
                        <th style="width: 50px;">序号</th>
                        <th style="width: 200px;">活动名称</th>
                        <th style="width: 220px;">活动描述</th>
                        <th style="width: 200px;">活动起始时间</th>
                        <th style="width: 200px;">活动结束时间</th>
                        <th style="width: 80px;">活动类型</th>
                    </tr>
                    </thead>
                </table>
                <div class="tab-box tab-box2">
                    <table width="958">
                    <tbody id="activityList">
                    <% int number = 1; %>
                    <c:forEach var='arr' items="${pageForm.list}">
                        <tr>
                            <td class="tr-1"><input name="radio" type="radio" value="${arr[0]}"><label></label></td>
                            <td class="tr-2"><%=number%></td>
                            <td class="tr-3">${arr[1]}</td>
                            <td class="tr-4">${arr[2]}</td>
                            <td class="tr-5">${arr[3]}</td>
                            <td class="tr-6">${arr[4]}</td>
                            <c:if test="${arr[8]=='00'}">
                                <td class="tr-7">普通活动</td>
                            </c:if>
                            <c:if test="${arr[8]=='01'}">
                                <td class="tr-7">特价活动</td>
                            </c:if>

                        </tr>
                        <% number++; %>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
    </div>
    <!--产品佣金设计 end-->
</section>
<script type="application/javascript">
    var search="";
    var LODOP;
    $(function () {


        $("#searchProduct").click(function () {
            search=$("#searchs").val();
            window.location.href=basePath+ "ea/activity/ea_selectActivityList.jspa?activityType=all&search="+search;
        });

        //关闭添加窗口
        $("#close").click(function () {
            if (confirm("您确定要关闭本页吗？")) {
                window.opener = null;
                window.open('', '_self');
                window.close();
            } else {
            }
        })
        /*ajax异步请求防止重复提交*/
        //设置一个对象来控制是否进入AJAX过程


        /*确定*/
        $(".alert_com .head #print").click(function () {
            //获取单选框选中的值[活动id]
            var activityVal = $('input:radio[name="radio"]:checked').val();
            if (activityVal == null || activityVal == '') {
                alert("什么也没选中!");
                return false;
            } else {
                printList(activityVal);
            }

        });

        $("#activityList").on("click", "tr", function () {
            $(this).find(".tr-1 input").prop("checked", true);
        });
    })



    //上一页
    function lastPage() {
        if ($("#pageNumber").val() > 1) {
            var pageNumber=$("#pageNumber").val()-1;
            window.location.href=basePath+ "ea/activity/ea_selectActivityList.jspa?activityType=all&search="+search+"&pageNumber="+pageNumber;
        }
    }

    //下一页
    function nextPage() {
        if (parseInt($("#pageNumber").val()) < parseInt($("#pageCount").val())) {
            var pageNumber=$("#pageNumber").val()-(-1);
            window.location.href=basePath+ "ea/activity/ea_selectActivityList.jspa?activityType=all&search="+search+"&pageNumber="+pageNumber;
        }
    }
    
    function printList(activityid) {
        var url = basePath+"/ea/productdesign/sajax_ea_getPrintLabelList.jspa";
        $.ajax({
            url:encodeURI(url),
            type:"get",
            async:false,
            dataType:"json",
            data:{
                "activityid":activityid
            },
            traditional: true,
            success:function (data) {
                var member = eval("("+data+")")
                var printList = member.printList;
                if(printList.length>0){
                    if(!confirm("是否打印？")){
                        return;
                    }
                    CreateAllPages(printList);
                }else {
                    alert("没有商品参加该活动")
                }
            }
        })
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

    function CreateAllPages(printList) {
        LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM1'));
        // var strBodyStyle="<style>"+document.getElementById("tag_css").innerHTML+"</style>";
        // var strFormHtml=strBodyStyle+"<body>"+document.getElementById("tag").innerHTML+"</body>";
        LODOP.PRINT_INIT("打印活动标签");
        LODOP.SET_PRINT_PAGESIZE(1,"100mm","40mm");
        // LODOP.SET_PRINT_STYLEA(0,"NotOnlyHighPrecision",true);
        //LODOP.SET_PRINT_STYLEA(0, "TransColor", "#FFFFFF");
        //LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","Full-Page");//"Width:32%;Height:30.6%"  Full-Page
        for (i = 0; i < printList.length; i++) {
            LODOP.NewPage();
            //LODOP.ADD_PRINT_IMAGE(0, 0, "100%", "100%", "<img style='z-index:-1; ' width='370px' height='150px' src='"+basePath+"/images/home/activity.png'/>");
            LODOP.ADD_PRINT_TEXT("2.5mm","52mm","50mm","8mm",printList[i][2]); // 商品名称
            LODOP.ADD_PRINT_TEXT("11mm","15mm","19mm","5mm",printList[i][4]); // 区域
            LODOP.ADD_PRINT_TEXT("11mm","41mm","19mm","5mm",printList[i][5]); // 货架
            LODOP.ADD_PRINT_TEXT("11mm","64.7mm","19mm","5mm",printList[i][6]); // 展位
            //LODOP.ADD_PRINT_TEXT("12.1mm","89mm","10mm","2.5mm",printList[i][2]); //产地
            LODOP.ADD_PRINT_TEXT("17.5mm","47mm","21mm","2.5mm",printList[i][7]); //规格
            var unit = printList[i][10];
            var price = printList[i][3];
            var actPrice=printList[i][11];

            if(unit == "千克" || unit=="KG"){
                unit = "斤";
                price = toDecimal2(price/2);
                actPrice=toDecimal2(actPrice/2);
            }else{
                price = toDecimal2(price);
                actPrice=toDecimal2(actPrice);
            }
            LODOP.ADD_PRINT_TEXT("23.3mm","52mm","15.5mm","2.5mm",unit); //计价单位
            LODOP.ADD_PRINT_TEXT("29mm","50.5mm","15.8mm","2.5mm",printList[i][8]); //行业分类
            //LODOP.ADD_PRINT_TEXT("35.5mm","47mm","18mm","2.5mm",printList[i][2]); //物料员
            LODOP.ADD_PRINT_BARCODE("22mm","7mm","35mm","11mm","128auto",printList[i][9]);//条码
            LODOP.ADD_PRINT_TEXT("18.2mm","71.5mm","21mm","2.5mm",price); //售价
            LODOP.ADD_PRINT_TEXT("26.8mm","70mm","31mm","11mm",actPrice);//活动价
            if(actPrice!=null&&actPrice!=""){
                LODOP.SET_PRINT_STYLEA(10*(i+1),"FontSize",28-((actPrice.toString().length-4)*3));
            }
            LODOP.SET_PRINT_STYLEA(10*(i+1),"FontName","微软雅黑");
            // $("#BarCode").attr("src",""+basePath+"/CreateBarCode?data=1231231231231&barType=TF25&height=90&headless=true&drawText=true&width=2")
            // $("#BarCodeText").text("1231231231231");
            // //LODOP.ADD_PRINT_HTML(0,0,"100%","100%",strFormHtml);
        }
        LODOP.PREVIEW();
        //LODOP.PRINT();//直接打印
    }
</script>

</body>
</html>