
$(function(){

	nav();
    //通讯消息
	$(".div-bottom li").eq(0).click(function() {
		if($(this).attr("class")==undefined){
			document.location.href = basePath+"/ea/mescom/ea_indexMesCom.jspa";
		}
		

	});
	//数字地球
	$(".div-bottom li").eq(1).click(function() {
		if($(this).attr("class")==undefined){
			document.location.href = basePath+"page/WFJClient/pc/message/com.jsp";
		}
	});
    //数字地球
	$(".div-bottom li").eq(2).click(function() {
		if($(this).attr("class")==undefined){
				document.location.href = basePath+"ea/earth/ea_earthIndex.jspa";
			
		}
	});
   
	//5L5C
	$(".div-bottom li").eq(3).click(function() {
		if($(this).attr("class")==undefined){
		document.location.href = basePath+"/ea/5l5c/ea_index5L5C.jspa";
		
		}
	});
	
	//我的中心
	$(".div-bottom li").eq(4).click(function() {
		if($(this).attr("class")==undefined){
		document.location.href = basePath+"ea/mycenter/ea_myIndex.jspa";
		}
	});
	
	//底部导航点击
	$('.div-bottom ul li').click(function(){
		
		if($(this).attr("class")==undefined||$(this).attr("class").indexOf("active")==-1){
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		}
	});

	// 微信小程序隐藏头部和底部
	const isWebview = localStorage.getItem('isWebview');
	const isWebviewBool = isWebview === 'true';
	if(isWebviewBool){
		$(".footer").hide();
		$("header").eq(0).hide();
	}
	
});


function nav(){
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	if (!isWeixin) {
		if(isAndroid) {

			try {
				var posNum = Android.forAndroidDeviceId();
				//安卓APP中隐藏导航

				$(".footer").hide();

			}catch(error) {
				//出错说明不在APP内
                 console.log();
			}
		}else if(isiOS){
			$(".footer").hide();
		}
	}
}

const TAG_CACHE_KEY = 'cachedNavTags';
const TAG_CACHE_MAX = 20; // 最多缓存 20 条历史记录，可调整

// 保存功能项
function saveFunctionTag(name, url) {
	if (!url || !name) return;
	if (name==='中国') return;
	let tags = JSON.parse(localStorage.getItem(TAG_CACHE_KEY) || '[]');

	// 去重（保留最新）
	tags = tags.filter(tag => tag.url !== url);
	console.log("name:"+name+"url:"+url);
	tags.push({ name, url });

	// 控制最大条数
	if (tags.length > TAG_CACHE_MAX) {
		tags = tags.slice(tags.length - TAG_CACHE_MAX);
	}

	localStorage.setItem(TAG_CACHE_KEY, JSON.stringify(tags));
	hasNew=true
}
let hasNew=true
// 恢复功能项（历史记录页面中使用）
function restoreFunctionTags(containerSelector = '#history-list') {
	console.log("开始加载标签");
	const tags = JSON.parse(localStorage.getItem(TAG_CACHE_KEY) || '[]');
	const container = document.querySelector(containerSelector);
	if (!container) return;
	if (hasNew){
		hasNew=false;
		tags.forEach(tag => {
			const a = document.createElement('a');
			a.href = tag.url;
			a.innerText = tag.name;
			a.className = 'history-link';  // ✅ 加上这行
			a.target = '_self'; // 当前页面跳转
			container.appendChild(a);
		});
	}
}

// 清空
function clearFunctionTags() {
	localStorage.removeItem(TAG_CACHE_KEY);
}

// 监听所有 <a href="..."> 的点击事件
function initTagCacheListener() {
	document.body.addEventListener('click', function (e) {
		const target = e.target.closest('a[href]');
		if (target) {
			const name = target.innerText.trim();
			const url = target.href;
			saveFunctionTag(name, url);
		}
	});
}

// 初始化入口
function initTagCache(containerSelector = '.div-top') {
	document.addEventListener('DOMContentLoaded', function () {
		initTagCacheListener();
	});
}

