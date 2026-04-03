/**
 * Created by Administrator on 2016/11/28 0028.
 */


setFont();//��������HTML����
window.onload = window.onresize = setFont;
function setFont(){
    //���壺�����ڼ�����ɺʹ��ڳߴ�仯��ʱ��ִ�д��������������
    //��ȡ���ڵĳߴ�
    var clientWidth = document.documentElement.clientWidth;
    //ͨ����Ļ���ȥ���ò�ͬ�ĺ�̨������Ĵ�С
    document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
}