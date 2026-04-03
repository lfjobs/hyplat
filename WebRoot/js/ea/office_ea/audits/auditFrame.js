$(document).ready(function() {  
	$("#navigation").treeview();   
	

});

function tonclick(id) {
	if (id == "unAudit") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/procedure/ea_getAuditList.jspa?data="
						+ Math.random());
	}
	if (id == "receivebox") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentinfo/ea_getReceiveBoxList.jspa?data="
						+ Math.random());
	}

	if (id == "yessenddraft") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentinfo/ea_getSendedDocList.jspa?data="
								+ Math.random());
	}
	if (id == "approvalno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examine&data="
								+ Math.random());

	}
	if (id == "aprrovalyes") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examineyes&data="
								+ Math.random());
	}
	if (id == "stampno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getSealDocList.jspa?finishType=seal&data="
								+ Math.random());
	}
	if (id == "stampyes") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getSealDocList.jspa?finishType=sealyes&data="
								+ Math.random());
	}
	if (id == "nosenddoc") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getPublishDocList.jspa?finishType=publish&data="
								+ Math.random());
	}
	if (id == "yessenddoc") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getPublishDocList.jspa?finishType=publishyes&data="
								+ Math.random());
	}
	if (id == "readno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getReadDocList.jspa?finishType=read&data="
								+ Math.random());
	}
	if (id== "readyes") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getReadDocList.jspa?finishType=readyes&data="
								+ Math.random());
	}
	if (id == "archive") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentinfo/ea_getArchivedList.jspa?data="
								+ Math.random());
	}
	if (id == "share") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentinfo/ea_getShareDocuments.jspa?data="
						+ Math.random());
	}
	if (id == "litter") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentcommon/ea_getRecycelBinList.jspa?data="
						+ Math.random());
	}
	if (id == "template") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documenttemplate/ea_getDocTemplateList.jspa?data="
								+ Math.random());
	}

}

