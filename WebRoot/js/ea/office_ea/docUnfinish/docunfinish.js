$(document).ready(function() {
	$("#tosearch").click(function() {
		$("#searchForm").attr("action",
				basePath + "ea/docunfinish/ea_toSearch.jspa");
		document.searchForm.submit.click();
	});

});

