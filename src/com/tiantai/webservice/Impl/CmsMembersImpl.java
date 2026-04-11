package com.tiantai.webservice.Impl;

import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.human.Staff;
import hy.ea.service.CompanyService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tiantai.nwa.util.XMLReader;
import com.tiantai.webservice.CmsMembers;

/**
 * 此WebService是用于对接汽车联盟网会员管理功能
 * @author zhb
 *
 */
@WebService(targetNamespace = "http://service.impf2010.com", endpointInterface = "com.tiantai.webservice.CmsMembers", name = "CmsMembers", serviceName = "CmsMembers")
@Component(value = "webservice.CmsMembers")
public class CmsMembersImpl implements CmsMembers {
	
	private static Logger log = LoggerFactory.getLogger(CmsMembersImpl.class);
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CompanyService companyService;

	/**
	 * 将会员基本信息导入五层五清
	 * @param members 以xml格式包装会员信息
	 * @return 每个会员导入成功与否的xml格式字符串
	 */
	@SuppressWarnings("unused")
	@Override		
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String importMembers(@WebParam(name = "members", targetNamespace = "http://service.impf2010.com") String members) {
		
		//1、如果members为""，则返回""
		if (members==null || "".equals(members.trim())) return "";
		
		try {
			//2、将members转成document，获取rootElement的全部child
			Document doc = XMLReader.string2Doc(members);			
			@SuppressWarnings("unchecked")
			List<Element> children = doc.getRootElement().getChildren();
			if (children==null || children.isEmpty()) return "";
			//3、针对每一个child解析
			Iterator<Element> iter = children.iterator();
			//构造返回的DOM
			Document returnDoc = XMLReader.string2Doc("<customers></customers>");
			while (iter.hasNext())
			{
				Element elem = iter.next();
				Staff bean = new Staff();//社会人力
				ContactRelation cr = new ContactRelation();//往来关系
				makeStaff(bean,cr,elem);
				if (!elem.getChildText("userType").trim().equals("VIP会员")){
					cr = null;
				}
				
				try{
					//构造相关对象，插入到相关表
					List<BaseBean> tmpList = new ArrayList<BaseBean>();
					tmpList.add(bean);
					tmpList.add(cr);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(tmpList,null,null);
					//baseBeanService.update(bean);
					//if (cr!=null) baseBeanService.update(cr);
					//不管成功与否，则需要将用户id返回，并添加成功标志success或fail
					addCustomerToElem(elem,returnDoc,"success");
					
				}catch(Exception e)
				{
					addCustomerToElem(elem,returnDoc,"fail");
				}				
			}
			//将结果返回
			return XMLReader.doc2String(returnDoc);
		} catch (Exception e) {			
			logger.error("操作异常", e);
			return "";
		}		
	}
	
	/**
	 * 导入每个会员后，将会员id(网站端的)和导入成功标志构造成xml
	 * @param elem 接收到的报文中每一个会员的信息
	 * @param returnDoc 返回给网站端的结果报文
	 * @param flag 标志每个会员是否导入成功
	 */
	private void addCustomerToElem(Element elem,Document returnDoc,String flag){
		Element customer = new Element("customer");
		customer.addContent((new Element("id")).setText(elem.getChildText("id")));
		customer.addContent((new Element("flag")).setText(flag));
		
		returnDoc.getRootElement().addContent(customer);
	}
	
	/**
	 * 根据接收到的报文中每一个会员的信息，构造社会人力对象和社会人力关系对象
	 * @param bean 社会人力对象
	 * @param cr 社会人力关系对象
	 * @param elem 接收到的报文中每一个会员的信息
	 */
	private void makeStaff(Staff bean,ContactRelation cr, Element elem)
	{
		bean.setStaffKey(UUID.randomUUID().toString().replaceAll("-", ""));
		String staffID = serverService.getServerID("cstaff");
		bean.setStaffID(staffID);
		bean.setStaffStatus("00");//00表示正常
		bean.setSource("网站");
		bean.setStaffName(elem.getChildText("name"));
		bean.setSex(elem.getChildText("sex"));
		bean.setReference(elem.getChildText("phone"));
		bean.setStaffIdentityCard(elem.getChildText("idCard"));
		bean.setReferenceOrganization(elem.getChildText("email"));
		bean.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");//现在这里设置为天太胜威集团，如果后面判断userType为VIPVIP会员，在根据companyId获取groupCompanySn
		
		if (elem.getChildText("userType").trim().equals("VIP会员")) {
			String companyId = elem.getChildText("companyId").trim();
			Company comp = companyService.getCompanyByCompanyID(companyId);
			bean.setGroupCompanySn(comp.getGroupCompanySn());
			
			//cr = new ContactRelation();
			cr.setRelationKey(UUID.randomUUID().toString().replaceAll("-", ""));
			cr.setRelationID(serverService.getServerID("contactrelation"));
			cr.setStaffID(staffID);
			cr.setCompanyID(companyId);
			cr.setRelation("学员");
		}
	}
	
	

}
