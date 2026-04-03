package hy.ea.bo.company;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class GoodsManage implements BaseBean ,ExcelBean,java.io.Serializable{
	private String goodsKey;
	private String goodsID;
	private String companyID;
	private String goodsCoding;//品名编号
	private String goodsName;//品名名称
	private String typeID;//类型
	private String num;
	private String variableID;//单位
	private String num1;
	private String variable1ID;//单位
	private String num2;
	private String variable2ID;//单位
	private String num3;
	private String variable3ID;//单位
	private String num4;
	private String variable4ID;//单位
	private String guojiStorage;//国际条码管理;
	private String acquiesceStandard;//默认规格
	private String standard;//品牌规格
	private String mnemonicCode;//主板号/发动机号(品牌)
	private String model;//型号
	private String defaultStorage;//统一分类条码
	private String manufacturers;//车架号/机壳号
	private String goodsvariable;//换算单位
	private String goodsState;//物品状态(说明：00表示正常，01表示作废)
	private String photoPath;//图片路径
	private File filePhoto;//图片主题
	private String filePhotoFileName;//文件绝对路径
	private String filePhotoContentType;//文件类型
	private String logoPath;//LOGO路径
	private File fileLogo;//图片LOGO
	private String fileLogoFileName;
	private String fileLogoContentType;
	private String subjectsName;//科目名称
	private String subjectsID;//科目ID
	private String tradeCode;//行业类别
	private String bigClass;//人事物品元素，财务物品元素···
	private String fiveClear;//5C 1 _人事,2_办公室,3——财务,4——教务处,5-营销
	//为扫描仪器添加字段
	private String companyStorsge;//公司打印条码号
	private String twoCode;//二维码
	private String barCode;//条码号
	private static Map<String, String> oMap;
	
	//wk
	private String wupjj;//物品简介管理
	private String wupgn;//物品功能管理；
	private String wupyt;//物品用途管理
	private String wupxuhao;//物品序号
	private String fileshipName;//文件绝对路径
	private String fileshipContetype;//文件类型
	private String shippath;//物品视频路径
	private File   fileship;//物品视频
	
	private String staffID;
	private String staffName;
	//mz
	private String producttype;//项目产品分类
	private String remark;//备注
	private String tradeID;//行业
	private String tradeName;//全名
	private String brand;//品牌
	private Date createdate;// 创建时间
	private String carModel;//车型
	private String categoryId;//关联驾校产品分类表的分类id
	private String categoryName;//关联驾校产品分类表的分类名称


	private String isScale;//是否需要电子秤打印条码

	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getCompanyName() {
		String cName="";
		if(null!=oMap)
		{
			cName=oMap.get(companyID);
		}
		return cName;
	}
	
//	 public static String[] columnHeadings() {
//			String[] titles = { "序号","公司名称","品名编号","品名名称","科目","统一分类条码","主板号/发动机号","类型","单位换算","默认规格","品牌规格","型号","车架号/机壳号 "};
//			return titles;
//		}
//		
//	@Override
//	public String[] properties() {
//		
//		 String[] properties = {getCompanyName(),goodsCoding,goodsName,subjectsName,defaultStorage,mnemonicCode,typeID,goodsvariable,acquiesceStandard,standard,model,manufacturers};
//			return properties;
//	}
	
	
	 public static String[] columnHeadings() {
			String[] titles = { "序号","行业","物品类别","物品条码","物品编号","物品名称","品牌","物品规格","型号","单位","创建时间"};
			return titles;
		}
		
	 @Override
	public String[] properties() {
		
		 String[] properties = {tradeCode,typeID,barCode,goodsCoding,goodsName,brand,standard,model,variableID,String.format("%1$tF %1$tT", createdate)};
			return properties;
	}
	
	
	public String getGoodsvariable() {
		return goodsvariable;
	}
	public void setGoodsvariable(String goodsvariable) {
		this.goodsvariable = goodsvariable;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getNum1() {
		return num1;
	}
	public void setNum1(String num1) {
		this.num1 = num1;
	}
	public String getVariable1ID() {
		return variable1ID;
	}
	public void setVariable1ID(String variable1ID) {
		this.variable1ID = variable1ID;
	}
	public String getNum2() {
		return num2;
	}
	public void setNum2(String num2) {
		this.num2 = num2;
	}
	public String getVariable2ID() {
		return variable2ID;
	}
	public void setVariable2ID(String variable2ID) {
		this.variable2ID = variable2ID;
	}
	public String getNum3() {
		return num3;
	}
	public void setNum3(String num3) {
		this.num3 = num3;
	}
	public String getVariable3ID() {
		return variable3ID;
	}
	public void setVariable3ID(String variable3ID) {
		this.variable3ID = variable3ID;
	}
	public String getNum4() {
		return num4;
	}
	public void setNum4(String num4) {
		this.num4 = num4;
	}
	
	public String getVariable4ID() {
		return variable4ID;
	}
	public void setVariable4ID(String variable4ID) {
		this.variable4ID = variable4ID;
	}
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getFilePhotoContentType() {
		return filePhotoContentType;
	}
	public void setFilePhotoContentType(String filePhotoContentType) {
		this.filePhotoContentType = filePhotoContentType;
	}
	public File getFilePhoto() {
		return filePhoto;
	}
	public void setFilePhoto(File filePhoto) {
		this.filePhoto = filePhoto;
	}
	public String getGoodsKey() {
		return goodsKey;
	}
	public void setGoodsKey(String goodsKey) {
		this.goodsKey = goodsKey;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGoodsCoding() {
		return goodsCoding;
	}
	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTypeID() {
		
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getVariableID() {
		return variableID;
	}
	public void setVariableID(String variableID) {
		this.variableID = variableID;
	}
	public String getAcquiesceStandard() {
		return acquiesceStandard;
	}
	public void setAcquiesceStandard(String acquiesceStandard) {
		this.acquiesceStandard = acquiesceStandard;
	}
	public String getStandard() {
		return standard;
	}
	public File getFileship() {
		return fileship;
	}
	public void setFileship(File fileship) {
		this.fileship = fileship;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getMnemonicCode() {
		return mnemonicCode;
	}
	public void setMnemonicCode(String mnemonicCode) {
		this.mnemonicCode = mnemonicCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getFileshipName() {
		return fileshipName;
	}
	public void setFileshipName(String fileshipName) {
		this.fileshipName = fileshipName;
	}
	public String getFileshipContetype() {
		return fileshipContetype;
	}
	public void setFileshipContetype(String fileshipContetype) {
		this.fileshipContetype = fileshipContetype;
	}
	public String getDefaultStorage() {
		return defaultStorage;
	}
	public void setDefaultStorage(String defaultStorage) {
		this.defaultStorage = defaultStorage;
	}
	public String getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}
	public String getFilePhotoFileName() {
		return filePhotoFileName;
	}
	public void setFilePhotoFileName(String filePhotoFileName) {
		this.filePhotoFileName = filePhotoFileName;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getCompanyStorsge() {
		return companyStorsge;
	}
	public void setCompanyStorsge(String companyStorsge) {
		this.companyStorsge = companyStorsge;
	}
	public String getTwoCode() {
		return twoCode;
	}
	public void setTwoCode(String twoCode) {
		this.twoCode = twoCode;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public File getFileLogo() {
		return fileLogo;
	}
	
	public String getGuojiStorage() {
		return guojiStorage;
	}
	public void setGuojiStorage(String guojiStorage) {
		this.guojiStorage = guojiStorage;
	}
	public String getWupjj() {
		return wupjj;
	}
	public void setWupjj(String wupjj) {
		this.wupjj = wupjj;
	}
	public String getWupgn() {
		return wupgn;
	}
	public void setWupgn(String wupgn) {
		this.wupgn = wupgn;
	}
	public String getWupyt() {
		return wupyt;
	}
	public void setWupyt(String wupyt) {
		this.wupyt = wupyt;
	}
	public String getShippath() {
		return shippath;
	}
	public void setShippath(String shippath) {
		this.shippath = shippath;
	}
	public void setFileLogo(File fileLogo) {
		this.fileLogo = fileLogo;
	}
	public String getFileLogoContentType() {
		return fileLogoContentType;
	}
	public void setFileLogoContentType(String fileLogoContentType) {
		this.fileLogoContentType = fileLogoContentType;
	}
	public String getFileLogoFileName() {
		return fileLogoFileName;
	}
	public String getWupxuhao() {
		return wupxuhao;
	}
	public void setWupxuhao(String wupxuhao) {
		this.wupxuhao = wupxuhao;
	}
	public void setFileLogoFileName(String fileLogoFileName) {
		this.fileLogoFileName = fileLogoFileName;
	}
	public String getBigClass() {
		return bigClass;
	}
	public void setBigClass(String bigClass) {
		this.bigClass = bigClass;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTradeID() {
		return tradeID;
	}
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIsScale() {
		return isScale;
	}

	public void setIsScale(String isScale) {
		this.isScale = isScale;
	}

	@Override
	public String toString() {
		return "\"GoodsManage\":{" +
				"\"goodsKey\":\"" + goodsKey + '\"' +
				", \"goodsID\":\"" + goodsID + '\"' +
				", \"companyID\":\"" + companyID + '\"' +
				", \"goodsCoding\":\"" + goodsCoding + '\"' +
				", \"goodsName\":\"" + goodsName + '\"' +
				", \"typeID\":\"" + typeID + '\"' +
				", \"num\":\"" + num + '\"' +
				", \"variableID\":\"" + variableID + '\"' +
				", \"num1\":\"" + num1 + '\"' +
				", \"variable1ID\":\"" + variable1ID + '\"' +
				", \"num2\":\"" + num2 + '\"' +
				", \"variable2ID\":\"" + variable2ID + '\"' +
				", \"num3\":\"" + num3 + '\"' +
				", \"variable3ID\":\"" + variable3ID + '\"' +
				", \"num4\":\"" + num4 + '\"' +
				", \"variable4ID\":\"" + variable4ID + '\"' +
				", \"guojiStorage\":\"" + guojiStorage + '\"' +
				", \"acquiesceStandard\":\"" + acquiesceStandard + '\"' +
				", \"standard\":\"" + standard + '\"' +
				", \"mnemonicCode\":\"" + mnemonicCode + '\"' +
				", \"model\":\"" + model + '\"' +
				", \"defaultStorage\":\"" + defaultStorage + '\"' +
				", \"manufacturers\":\"" + manufacturers + '\"' +
				", \"goodsvariable\":\"" + goodsvariable + '\"' +
				", \"goodsState\":\"" + goodsState + '\"' +
				", \"photoPath\":\"" + photoPath + '\"' +
				", \"filePhoto\":" + filePhoto +
				", \"filePhotoFileName\":\"" + filePhotoFileName + '\"' +
				", \"filePhotoContentType\":\"" + filePhotoContentType + '\"' +
				", \"logoPath\":\"" + logoPath + '\"' +
				", \"fileLogo\":" + fileLogo +
				", \"fileLogoFileName\":\"" + fileLogoFileName + '\"' +
				", \"fileLogoContentType\":\"" + fileLogoContentType + '\"' +
				", \"subjectsName\":\"" + subjectsName + '\"' +
				", \"subjectsID\":\"" + subjectsID + '\"' +
				", \"tradeCode\":\"" + tradeCode + '\"' +
				", \"bigClass\":\"" + bigClass + '\"' +
				", \"fiveClear\":\"" + fiveClear + '\"' +
				", \"companyStorsge\":\"" + companyStorsge + '\"' +
				", \"twoCode\":\"" + twoCode + '\"' +
				", \"barCode\":\"" + barCode + '\"' +
				", \"wupjj\":\"" + wupjj + '\"' +
				", \"wupgn\":\"" + wupgn + '\"' +
				", \"wupyt\":\"" + wupyt + '\"' +
				", \"wupxuhao\":\"" + wupxuhao + '\"' +
				", \"fileshipName\":\"" + fileshipName + '\"' +
				", \"fileshipContetype\":\"" + fileshipContetype + '\"' +
				", \"shippath\":\"" + shippath + '\"' +
				", \"fileship\":" + fileship +
				", \"staffID\":\"" + staffID + '\"' +
				", \"staffName\":\"" + staffName + '\"' +
				", \"producttype\":\"" + producttype + '\"' +
				", \"remark\":\"" + remark + '\"' +
				", \"tradeID\":\"" + tradeID + '\"' +
				", \"tradeName\":\"" + tradeName + '\"' +
				", \"brand\":\"" + brand + '\"' +
				", \"createdate\":" + createdate +
				", \"carModel\":\"" + carModel + '\"' +
				", \"categoryId\":\"" + categoryId + '\"' +
				", \"categoryName\":\"" + categoryName + '\"' +
				", \"isScale\":\"" + isScale + '\"' +
				"},";
	}
}
