$(function (){

    $(".left li").click(function (){
        $(".left li").css({'background':'',"border-left":""});
        $(this).css({'background':'#ffffff',"border-left":"2px solid #f97b7b"});
        var cla = $(this).attr('alt');
        $('.right').css('display', 'none');
        $('.'+cla).css('display', 'block');
        var baoM=$(this).find("span:first-child").text()
        $(".rukou_text_tet").text(baoM)
    });

    $('.jia').click(function(){
            $old = $(this).prev('h5').html();
            if ($old == '99') {
                $new = $old;
            } else {
            $new = parseInt($old) + 1;
            if ($new == '99'){
                $(this).css({"border":"1px solid rgba(0,0,0,0.3)","color":"rgba(0,0,0,0.3)"})
            }
        }
        if($new > '0'){
            $(this).prev().css("display","block")
            $(this).prev().prev().css("display","block")
        }
        $(this).prev('h5').html($new);
        $(this).prev().prev(".jian").css({"border":"1px solid #ff4800","color":"#ff4800"})
        var num=0;
        $(".right.special h5").each(function(){
            num+=parseInt($(this).text());
        });
        $(".num1").text(num)
        if(num>0){
            $(".num1").css("display","block")
        }else if(num==0){
            $(".num1").css("display","none")
        }
        var num1=0
        $(".right.food h5").each(function(){
            num1+=parseInt($(this).text());
        });
        $(".num2").text(num1)
        if(num1>0){
            $(".num2").css("display","block")
        }else if(num1==0){
            $(".num2").css("display","none")
        }
        var num2=0
        $(".right.drink h5").each(function(){
            num2+=parseInt($(this).text());
        });
        $(".num3").text(num2)
        if(num2>0){
            $(".num3").css("display","block")
        }else if(num2==0){
            $(".num3").css("display","none")
        }
        var num3=0
        $(".right.cigaret h5").each(function(){
            num3+=parseInt($(this).text());
        });
        $(".num4").text(num3)
        if(num3>0){
            $(".num4").css("display","block")
        }else if(num3==0){
            $(".num4").css("display","none")
        }
        var num4=0
        $(".right.beer h5").each(function(){
            num4+=parseInt($(this).text());
        });
        $(".num5").text(num4)
        if(num4>0){
            $(".num5").css("display","block")
        }else if(num4==0){
            $(".num5").css("display","none")
        }
        var num5=0
        $(".right.daily h5").each(function(){
            num5+=parseInt($(this).text());
        });
        $(".num6").text(num5)
        if(num5>0){
            $(".num6").css("display","block")
        }else if(num5==0){
            $(".num6").css("display","none")
        }
        var num6=0
        $(".right.appeal h5").each(function(){
            num6+=parseInt($(this).text());
        });
        $(".num7").text(num6)
        if(num6>0){
            $(".num7").css("display","block")
        }else if(num6==0){
            $(".num7").css("display","none")
        }
        var mon=0
        $(".right h2 span").each(function(){
            mon+=parseFloat($(this).text()*$(this).parent().next().find("h5").text());
        });
        $(".money").text(mon)
        if(mon=="0"){
            $(".nomoney").css("display","block")
            $(".goshopping").css("display","none")
        }else if(mon!="0"){
            $(".nomoney").css("display","none")
            $(".goshopping").css("display","block")
        }
    });


    //下面JQuery点击事件
//点减号按钮就减去1
//    $(".jian").click(function() {      //获取按钮的class为wojian的点击事件
//        var vliang=$("#liang").val();  //获取文本框本身的值并且进行赋值
//        if (parseInt(vliang)==1) {return;};   //如果文本框的值为1就返回
//        $("#liang").val(parseInt(vliang)-1);   //将原来的数减去1并且返回 ， 注意：最好用parseInt函数转换成整型
//    });
////点加号就在原来的数值上加1
//    $(".jia").click(function() {      //获取按钮的class为wojia的点击事件
//        var vliang=$("#liang").val();  //获取文本框本身的值并且进行赋值
//        if (parseInt(vliang)==15 || parseInt(vliang)>15) {return};    //这里设定了文本框里面的最大值15，可自行设置
//        $("#liang").val(parseInt(vliang)+1);   //将原来的数加上1并且返回到文本框内，注意啊：一定要将vliang这个变量用parseInt函数进行转换，否则会以字符串的形式进行累加，如：本来值是1，那么，再加上1就是11了。。。切记
//    });
    $('.jian').click(function(){

        $old = $(this).next('h5').html();
        if ($old == '0') {
            $new = $old;
           $(this).attr('style', '');
        } else {
            $new = parseInt($old) - 1;
            if (parseInt($new) == 0) {
                $(this).attr('style', '');
            }
            /*$(".jian").css()*/
        }
        $(this).next('h5').html($new);

        if($new == '0'){
            $(this).next().css("display","none")
        }
        var num=0;
        var num1=0
        $(".right.special h5").each(function(){
            num+=parseInt($(this).text());
        });
        $(".num1").text(num)
        $(".right.food h5").each(function(){
            num1+=parseInt($(this).text());
        });
        $(".num2").text(num1)
        if(num>0){
            $(".num1").css("display","block")
        }else if(num==0){
            $(".num1").css("display","none")
        }
        if(num1>0){
            $(".num2").css("display","block")
        }else if(num1==0){
            $(".num2").css("display","none")
        }
        var num1=0
        $(".right.food h5").each(function(){
            num1+=parseInt($(this).text());
        });
        $(".num2").text(num1)
        if(num1>0){
            $(".num2").css("display","block")
        }else if(num1==0){
            $(".num2").css("display","none")
        }
        var num2=0
        $(".right.drink h5").each(function(){
            num2+=parseInt($(this).text());
        });
        $(".num3").text(num2)
        if(num2>0){
            $(".num3").css("display","block")
        }else if(num2==0){
            $(".num3").css("display","none")
        }
        var num3=0
        $(".right.cigaret h5").each(function(){
            num3+=parseInt($(this).text());
        });
        $(".num4").text(num3)
        if(num3>0){
            $(".num4").css("display","block")
        }else if(num3==0){
            $(".num4").css("display","none")
        }
        var num4=0
        $(".right.beer h5").each(function(){
            num4+=parseInt($(this).text());
        });
        $(".num5").text(num4)
        if(num4>0){
            $(".num5").css("display","block")
        }else if(num4==0){
            $(".num5").css("display","none")
        }
        var num5=0
        $(".right.daily h5").each(function(){
            num5+=parseInt($(this).text());
        });
        $(".num6").text(num5)
        if(num5>0){
            $(".num6").css("display","block")
        }else if(num5==0){
            $(".num6").css("display","none")
        }
        var num6=0
        $(".right.appeal h5").each(function(){
            num6+=parseInt($(this).text());
        });
        $(".num7").text(num6)
        if(num6>0){
            $(".num7").css("display","block")
        }else if(num6==0){
            $(".num7").css("display","none")
        }

        var mon=0
        $(".right h2 span").each(function(){
            mon+=parseFloat($(this).text()*$(this).parent().next().find("h5").text());
        });
        $(".money").text(mon)
        if(mon=="0"){
            $(".nomoney").css("display","block")
            $(".goshopping").css("display","none")
        }else if(mon!="0"){
            $(".nomoney").css("display","none")
            $(".goshopping").css("display","block")
        }
    });


    //$('.jia').click(addProduct);
    //function addProduct(event) {
    //    var offset = $('.carIcon').offset(),
    //        //flyer = $('<img class="flyShop" src="../images/shili.jpg"/>');
    //    flyer.fly({
    //        start: {
    //            left: event.pageX-100,
    //            top: event.pageY-80
    //        },
    //        end: {
    //            left: offset.left,
    //            top: offset.top,
    //            width: 20,
    //            height: 20
    //        }
    //    });
    //}
});