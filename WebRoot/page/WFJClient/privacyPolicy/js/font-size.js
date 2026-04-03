/**
 * Created by Administrator on 2016/11/28 0028.
 */


setFont();//运行设置HTML字体
window.onload = window.onresize = setFont;
function setFont(){
    //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
    //获取窗口的尺寸
    var clientWidth = document.documentElement.clientWidth;
    //通过屏幕宽度去设置不同的后台根字体的大小
    if(clientWidth<960) {
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
    }else{
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640*8 + 'px';

    }
}