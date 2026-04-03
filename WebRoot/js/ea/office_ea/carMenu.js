$(document).ready(function() {
	$("#carNumweihu").click(function() {
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		personurl = basePath+ "ea/carnum/ea_getCarRoadList.jspa?pageNumber=4&carNumber.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carinvoice").click(function() {
		// 购车发票
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carinvoice/ea_getCarInvoiceList.jspa?pageNumber=4&carInvoice.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	
	$("#purchase").click(function() {
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carpurchase/ea_getPurchaseList.jspa?pageNumber=4&purchase.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carviolate").click(function() {
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carviolate/ea_getCarViolateList.jspa?pageNumber=4&carviolate.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carroad").click(function() {
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carroad/ea_getCarRoadList.jspa?pageNumber=4&carroad.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carquasi").click(function() {
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carquasi/ea_getCarQuasiList.jspa?pageNumber=4&carquasi.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#motorcar").click(function() {
		// 购车发票
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/motorcar/ea_getMotorcarList.jspa?pageNumber=4&motorcar.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	
	$("#carPurchaseTax").click(function() {
		// 购置税发票
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carpurchasetax/ea_getCarPurchaseTaxList.jspa?pageNumber=4&carPurchaseTax.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carInsurance").click(function() {
		// 车辆保险信息
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carinsurance/ea_getCarInsuranceList.jspa?pageNumber=4&carInsurance.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carAReview").click(function() {
		// 车辆年审信息
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carareview/ea_getCarAReviewList.jspa?pageNumber=4&carAReview.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#bottle").click(function() {
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/bottle/ea_getBottleList.jspa?pageNumber=4&bottle.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#carCNG").click(function() {
		// 车辆CNG信息
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carcng/ea_getCarCNGList.jspa?pageNumber=4&carCNG.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#MaintainManagerInformation").click(function() {
		// 车辆保养信息
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		
		personurl = basePath
				+ "ea/carmaintain/ea_getListCarMaintain.jspa?pageNumber=4&carMaintain.carID=";
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#CarSafeInformation").click(function() {
		// 车辆安全信息查询
		if (carID == "") {
			alert("请选择具体车辆！");
			return;
		}
		personurl = basePath
		+ "ea/carsafeinformation/ea_getCarsafeinformationList.jspa?pageNumber=4&carsafeinformation.carID=";
		
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#CarwagwInformation").click(function() {
		// 车辆资产信息查询
		if (carID == "") {
			alert("请选择一个车辆！");
			return;
		}
		personurl = basePath
		+ "ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?pageNumber=4&carassetcinformation.carID=";
		
		$("#mainframe").attr("src", personurl + carID);
	});
	
	$("#CarUserInformation").click(function() {
		// 车辆使用信息查询
		if (carID == "") {
			alert("请选择一个车辆！");
			return;
		}
		personurl = basePath
		+ "ea/employcondition/ea_getemployconditionList.jspa?pageNumber=4&employcondition.carID=";
		
		$("#mainframe").attr("src", personurl + carID);
	});
	$("#Certificatea").click(function() {
		// 车辆证件子集信息查询
		if (carID == "") {
			alert("请选择一个车辆！");
			return;
		}
		personurl = basePath
		+ "ea/certificateatable/ea_getCertificateaTableList.jspa?pageNumber=4&certificateatable.carID=";
		
		$("#mainframe").attr("src", personurl + carID);
	});
});