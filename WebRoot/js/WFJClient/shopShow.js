/**
 * Created by gudao on 2015/1/7.
 */


/*获取样式Css*/
function getCss(ele,attr){
    var temp=null;
    if(window.getComputedStyle){
        temp=getComputedStyle(ele,null)[attr];
    }else{
        temp=ele.currentStyle[attr];
    }
    return parseInt(temp);
}

/*简单的移动动画*/
function move(ele,attr,target,duration){
    var interval=13;
    var times=0;

    var begin=getCss(ele,attr);
    var change=target-begin;

    function step(){
        times+=interval;
        if(times<duration){
            ele.style[attr]=times/duration*change+begin+"px";
        }else{
            ele.style[attr]=target+"px";
            window.clearInterval(timer);
        }
    }
    var timer=window.setInterval(step,interval)
}