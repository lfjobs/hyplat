//用于手机端头部显示
$(function(){
    var title = $(document).attr("title");
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
   try {
       if (isAndroid == true) {
           Android.changeTitle(title);

       } else if (isiOS == true) {


       }
   }catch(error){

    console.log("网页打开");
   }


});