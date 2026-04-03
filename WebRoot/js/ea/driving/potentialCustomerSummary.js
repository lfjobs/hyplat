$(document).ready(function() {
	
	$(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话  
        overlay: 20 // 遮罩程度%  
    }).jqmAddClose('.close');// 添加触发关闭的selector  
                
	$('.staffappraisal').flexigrid({
		//var type=$("#type").val();
		//alert("ss");
        height: 400,
        allDouble:true,
        title :type=='101'?'意向客户汇总&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000030&type=101">潜在客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000031&type=101">优质客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000032&type=101">本日可成交客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000033&type=101">两日可成交客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000034&type=101">本周可成交客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000035&type=101">本月可成交客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000036&type=101">本季可成交客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000037&type=101">本年可成交客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000038&type=101">大客户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000039&type=101">渠道客户</a>':type=='102'?'客户来源&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000041&type=102">400电话</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000042&type=102">短信</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000043&type=102">网络渠道</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000048&type=102">代理商</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000049&type=102">一般中介</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000050&type=102">户外广告</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000051&type=102">媒体</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000052&type=102">电视广告</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000053&type=102">报纸广告</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000054&type=102">直电</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000055&type=102">中心活动</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000056&type=102">网络注册</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000057&type=102">网络客户</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000058&type=102">总部活动</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000059&type=102">老学员推荐</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000060&type=102">外教推荐</a>&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000061&type=102">课程顾问自传</a>':'客户兴趣&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=00&type=103">有兴趣</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=02&type=103">一般有兴趣</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="ea_getPotentialListSummary.jspa?status1=01&type=103">特别有兴趣</a>',
        width: 'auto',
        minwidth: 30,
        minheight: 80,
		 buttons: [{
			name : '查询',
			bclass : 'mysearch',
			onpress : action
		}, {
			separator : true
		}, {
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            separator: true
        }, {
			name : '导出',
			bclass : 'excel',
			onpress : action
			// 当点击调用方法
        }	, {
			separator : true
		}]
    });
    function action(com, grid){
        switch (com) {
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		     case '设置每页显示条数':
			   	var url = basePath
						+ "/ea/customermanage/ea_getPotentialListSummary.jspa?status1="+status1+"&search="+search+"&type="+type;
				numback(url);
				break;
		     case '导出':
		    	 url = basePath
					+ "/ea/customermanage/ea_showExcelPotentialListSummary.jspa?status1="+status1+"&search="+search;
					open(url);
		     break;
        }
    }
    
    $(".close").click(function(){
    });
    
    // 查询按钮单据事件
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",
				basePath + "/ea/customermanage/ea_toSearchPotentialSummary.jspa?status1="+status1+"&pageNumber="+pNumber+"&type="+type);
		document.SearchForm.submit.click();
		document.SearchForm.reset();
	});
});