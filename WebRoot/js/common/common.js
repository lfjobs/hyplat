 /**
 *封装通用的方法
 * creater：zg
 */
 //单附件查看（在新窗口中打开）
 function  lookImage(imageNO){
		var imgurl =basePath+imageNO;
		newwin=window.open('about:blank','','width=800,height=600,left=0,top=0'); 
		var img = new Image();
		img.src=imgurl;
		if(img.height>600||img.width>800){
			newwin.document.write('<body  style="margin: 0px"><div align="center"><img id="img" height="600" width="800" src="'+imgurl+'"></div>');
		}else{
			newwin.document.write('<body  style="margin: 0px"><div align="center"><img id="img" src="'+imgurl+'"></div>');
		}
		newwin.resizeTo(screen.availWidth,screen.availHeight);
 }
/**
 * 封装分页方法
 * @param {} url
 */
 function numback(url){
    var pn_common = prompt("输入显示条数","请输入小于等于50的正整数");
    if(pn_common==null)
    	return;
    if(pn_common <0 || pn_common!=parseInt(pn_common)||pn_common>50 ||pn_common==0){ 
	    alert("请输入小于等于50的正整数！"); 
	    numback(url);
	}
	else{
		url = url+"&pageNumber=" +pn_common;
   		document.location.href = encodeURI(url);
	}
}

//获取页面高度
function GetPageSize(){
    var xScroll, yScroll;
    if (window.innerHeight  &&  window.scrollMaxY) { 
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else {
        xScroll = document.body.offsetWidth;
        yScroll = document.body.offsetHeight;
    }
    var windowWidth=0, windowHeight=0;
    if (self.innerHeight) {
        windowWidth = self.innerWidth;
        windowHeight = self.innerHeight;
    } else if (document.documentElement  &&  document.documentElement.clientHeight) {
        windowWidth = document.documentElement.clientWidth;
        windowHeight = document.documentElement.clientHeight;
    } else if (document.body) {
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
    } 
    if(yScroll < windowHeight){
        pageHeight = windowHeight;
    } else { 
        pageHeight = yScroll;
    }
    if(xScroll < windowWidth){ 
        pageWidth = windowWidth;
    } else {
        pageWidth = xScroll;
    }
    arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight); 
    return arrayPageSize;
}
function oMax(div,revert){
	div.style.top = div.style.left = 0;
	var arr=GetPageSize();
	div.style.width=arr[0]+"px";
	div.style.height=arr[1]+"px";
	$(div).find(".omax").hide();
	$(div).find(".omin").show();

}
function oRevert(div,toppx,leftpx,wpx,hpx){
	div.style.top =toppx;
	div.style.left = leftpx;
	div.style.width = wpx;
	div.style.height =hpx;
	$(div).find(".omax").show();
	$(div).find(".omin").hide();
}
//上拉加载方法
//area需要上拉的列表区域    target所需要对比屏幕的固定区域    pull:回调函数
function getHeight(area,target,pull){
	height = parseInt(Math.abs($(area).height()-($(window).height()-$(area).offset().top)));
	t=setTimeout("getHeight('"+area+"','"+target+"','"+pull+"')", 200);
	if(height<$(target).height()){
		if(pagenumber<pagecount){
			if (typeof(eval(pull)) == "function") {
            	eval(pull);
        	}
		}	
	}
}