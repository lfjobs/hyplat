
// 分页
function page(pageNumber) {
	if (pageNumber == 0) {
		alert("已经是首页");
		return;
	}
	if (pageNumber == Number(count) + 1) {
		alert("已经是最后一页");
		return;
	}
	document.location.href = basePath
			+ "ea/ptrack/ea_findProductTrackList.jspa?fiveClear=" + fiveClear
			+ "&pageForm.pageNumber=" + pageNumber + "&search=" + search;
}

// 查询
function toSearch() {
	$("#SearchForm").attr("action",
			basePath + "ea/ptrack/ea_toSearchBystrack.jspa");
	document.SearchForm.submit.click();
	token = 1;
}

// 导出
function toExcel() {
	window.open(basePath + "ea/ptrack/ea_toExcelBystrack.jspa?search=" + search
			+ "&fiveClear=" + fiveClear);
}
// 查看详情
function viewDetail(stId) {

	window.open(basePath
			+ "ea/ptrack/ea_findProductTrackPic.jspa?staffTrack.stId=" + stId);
}