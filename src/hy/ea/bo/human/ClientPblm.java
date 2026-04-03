/**
 * LogBook
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Map;
/**
 * cxf
 * 客户问题解决办
 * @author Administrator
 */
public class ClientPblm implements BaseBean,ExcelBean,java.io.Serializable{
	private String  clientPblmKey;
	private String  clientPblmID;
	private String  companyID;
	private String 	organizationID;
	private String  clientCode;             //客户编号
	private String 	clientName;             //客户名称
	private String 	clientAddress;          //详细地址
	private String 	clientLinkman;          //联系人
	private String 	clientTelephone;        //联系电话
	private String 	clientEmail;            //联系邮箱
	private String 	clientQq;               //QQ
	private String 	productName;            //产品名称
	private String  productType; 	        //产品型号
	private String  productBrand;           //产品品牌
	private String  productSerial;          //产品序列号
	private String  productSay;             //设置配制说明
	private String  productPblmSay;         //现场问题说明
	private String  moveAndResult;          //采取的步骤和结果
	private String  causeAnalysis;          //原因分析
	private String  processingResult;       //处理结果
	private String  clientConceit ;         //客户意见
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "客户编号", "客户名称", "详细地址", "联系人", "联系电话", "联系邮箱",
				 "QQ", "产品名称","产品型号","产品品牌","产品序列号","设置配制说明","现场问题说明","采取的步骤和结果","原因分析","处理结果","客户意见"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { clientCode,clientName,clientAddress, 
				clientLinkman, clientTelephone, clientEmail,clientQq,productName,
				productType, productBrand,productSerial,productSay,productPblmSay,moveAndResult,oMap.get(causeAnalysis),processingResult,clientConceit};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getClientPblmKey() {
		return clientPblmKey;
	}
	public void setClientPblmKey(String clientPblmKey) {
		this.clientPblmKey = clientPblmKey;
	}
	public String getClientPblmID() {
		return clientPblmID;
	}
	public void setClientPblmID(String clientPblmID) {
		this.clientPblmID = clientPblmID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getClientLinkman() {
		return clientLinkman;
	}
	public void setClientLinkman(String clientLinkman) {
		this.clientLinkman = clientLinkman;
	}
	public String getClientTelephone() {
		return clientTelephone;
	}
	public void setClientTelephone(String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public String getClientQq() {
		return clientQq;
	}
	public void setClientQq(String clientQq) {
		this.clientQq = clientQq;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductSerial() {
		return productSerial;
	}
	public void setProductSerial(String productSerial) {
		this.productSerial = productSerial;
	}
	public String getProductSay() {
		return productSay;
	}
	public void setProductSay(String productSay) {
		this.productSay = productSay;
	}
	public String getProductPblmSay() {
		return productPblmSay;
	}
	public void setProductPblmSay(String productPblmSay) {
		this.productPblmSay = productPblmSay;
	}
	public String getMoveAndResult() {
		return moveAndResult;
	}
	public void setMoveAndResult(String moveAndResult) {
		this.moveAndResult = moveAndResult;
	}
	public String getCauseAnalysis() {
		return causeAnalysis;
	}
	public void setCauseAnalysis(String causeAnalysis) {
		this.causeAnalysis = causeAnalysis;
	}
	public String getProcessingResult() {
		return processingResult;
	}
	public void setProcessingResult(String processingResult) {
		this.processingResult = processingResult;
	}
	public String getClientConceit() {
		return clientConceit;
	}
	public void setClientConceit(String clientConceit) {
		this.clientConceit = clientConceit;
	}
	
}
