$(function() {
	$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : '行政建设管理',
				minheight :50,
				resizable: false,//是否伸缩
				buttons : [{
					name : '行政管理文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '商务决策文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '商务活动文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '企业礼仪文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '演讲会议文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '企业法规文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '现场会议文书',
					bclass : '',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '行政管理文书' :
				 window.parent.document.getElementById("mainframe2").src = basepath + "page/ea/main/office_ea/FileManager/Administration_Instruments.jsp";
				break;
			 case '商务决策文书' :
				 window.parent.document.getElementById("mainframe2").src = basepath+ "page/ea/main/office_ea/FileManager/Business_Decision_Instruments.jsp";
				break;
			 case '商务活动文书' :
				 window.parent.document.getElementById("mainframe2").src = basepath+"page/ea/main/office_ea/FileManager/Business_Activities_Instruments.jsp";
				break;
			case '企业礼仪文书' :
				window.parent.document.getElementById("mainframe2").src = basepath+"page/ea/main/office_ea/FileManager/Business_etiquette_Instruments.jsp";
				break;
			case '演讲会议文书' :
				window.parent.document.getElementById("mainframe2").src = basepath+"page/ea/main/office_ea/FileManager/Conference_presentations_Instruments.jsp";
				break;
			case '企业法规文书' :
				window.parent.document.getElementById("mainframe2").src = basepath+"page/ea/main/office_ea/FileManager/Business_regulations_Instruments.jsp";
				break;
			case '现场会议文书' :
				window.parent.document.getElementById("mainframe2").src = basepath+"page/ea/main/office_ea/FileManager/Business_conferenceorg_Instruments.jsp";
				
				break;
		}
	}

});
