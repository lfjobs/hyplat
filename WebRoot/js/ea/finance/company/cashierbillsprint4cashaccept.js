$(function(){
	var str = '';
   if(st!="00" && st!=''){
         if(st=="10"||st=="30"){
             str ="<img src='"+basePath+"images/ea/finance/zuofei.png'/>";
         }else if(st=="04"||st=="07"||st=="45"){
            str ="<img src='"+basePath+"images/ea/finance/yishen.png'/>";
         }else{
            str ="<img src='"+basePath+"images/ea/finance/daishen.png'/>";
         }
   }
   
   var monsum=0;   
   var a=0;
   $("span.moneysum").each(function(){
       var money=$(this).text();
       if(money!=""&&money.length!=0){
    	   var num=money.indexOf("-");
    	   if(num!=-1){
    		   var money2=money.substring(num+1);
    		   money=money.substring(0,num);
    		   $(this).parent().parent().find("td").eq($(this).parent().index()+1).find("span").text(money);
    		   money=money-money2;
    	   }
	       monsum=monsum+parseFloat(money);
	       a=1;
       }
   });
   $("span#monsum").text(Math.round(monsum*100)/100);
   if(a){
	 DX(Math.abs(monsum));
   }
   
   /*$("div#tableprint").each(function(){
	   var gtableid=$(this).find(".goodclass").attr("id");
	   var numid=$(this).find("input:eq(0)").attr("id");
	   var num=numid.substring(3);
	   
	   var height = $(this).height();
	   $("#apDiv1"+num).html(str);
	   $("#apDiv1"+num).css({'top':height-250});
	   $("#apDiv1"+num).css({'left':500});
	   
	   var monsum=0;   
	   var a=0;
	   $("#"+gtableid).find("span.moneysum").each(function(){
		   var money=$(this).text();
	          monsum=monsum+parseFloat(money);
	          a=1;
	   });
	   $(this).find("span.monsum").text(Math.round(monsum*100)/100);
	   if(a){
		 DX(Math.abs(monsum),num);
	   }
   });*/
    //主函数
    function DX(n,c) {
    	if(c==null||c==""){
    		c=0;
    	}
    	/*var d=$("#"+c).next();*/
        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
            return "数据非法";
            var unit = "千百拾亿千百拾万千百拾元角分", str = "";
            n += "00";
        var p = n.indexOf('.');
        if (p >= 0)
            n = n.substring(0, p) + n.substr(p+1, 2);
            unit = unit.substr(unit.length - n.length);
            var j=n.length-1;
        for (var i=0; i < n.length; i++)
        {
              if(j>=6)
              {
                str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
                var mm=  str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").
                 replace(/(亿)万|壹(拾)/g, "$1$2");
                 j--;
                if(j==5)
                {
                  mm=mm.replace("万",""); 
                  $("span.6").text(mm);
                }
                 
              }else
              {  
            	  $("span#"+(n.length)).text('￥');
            	  $("span#"+j).text('零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)));
            	  //alert((n.length)+(c.toString(2)));
                 j=j-1;
              }
                 
        }
        }
});
