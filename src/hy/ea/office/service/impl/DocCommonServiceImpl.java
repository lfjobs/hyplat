package hy.ea.office.service.impl;

import com.stamp.Office2PdfUtil;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.*;
import hy.ea.bo.office.vo.*;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.ZOfficeService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class DocCommonServiceImpl implements DocCommonService {
	Logger log = LoggerFactory.getLogger(DocCommonServiceImpl.class);
	@Autowired
	private BaseBeanDao baseBeanDao;
	@Autowired
	private ServerService serverService;
	@Resource
	private ZOfficeService zOfficeService;
	@Resource
	private ContractService contractService;
	@Autowired
	private MobileMessage mobileMessage;//短信接口
	@Resource
	private BaseBeanService baseBeanService;
	/**
	 * 
	 * 获得编号 type:一个是编号docNum，一个是号numCode
	 * 
	 * @param companyID
	 * @param type
	 * @return
	 */
	public String getDocNum(String companyID, String type) {
		String hql = "from Document where companyID= ? and status!= 'D' ";
		String sql = "";
		if (type == "docNum" || type.equalsIgnoreCase("docNum")) {
			if(companyID.indexOf("staff")!=-1) {
				sql = " select max(to_number(docNum)) from DT_OA_DOCUMENT where drafterID= ? and companyID is null and status!='D' ";
			}else{
				sql = " select max(to_number(docNum)) from DT_OA_DOCUMENT where companyID= ? and status!='D' ";

			}
		} else {
			if(companyID.indexOf("staff")!=-1) {
				sql = " select max(to_number(numCode)) from DT_OA_DOCUMENT where drafterID= ? and companyID is null and status!='D' ";
			}else{
				sql = " select max(to_number(numCode)) from DT_OA_DOCUMENT where companyID= ? and status!='D' ";

			}
		}
		List<BaseBean> list = (List<BaseBean>) baseBeanDao
				.getListBeanByHqlAndParams(hql, new Object[] { companyID });
		if (type == "docNum" || type.equalsIgnoreCase("docNum")) {
			if (list.size() == 0) {
				return "0001";
			} else {
				int a = baseBeanDao.getConutByBySqlAndParams(sql,
						new Object[] { companyID });
				int nextnum = a + 1;
				String s2 = "";
				if (nextnum > 0 && nextnum <= 9) {
					s2 = "000" + nextnum;
				} else if (nextnum > 9 && nextnum <= 99) {
					s2 = "00" + nextnum;
				} else if (nextnum > 99 && nextnum <= 999) {
					s2 = "0" + nextnum;
				} else if (nextnum > 999) {
					s2 = "" + nextnum;
				}
				return s2;
			}
		} else {
			boolean bol = true;
			for (BaseBean lis : list) {
				Document d = (Document) lis;
				if (d.getNumCode() != null)
					bol = false;

			}
			if (bol == false) {
				int a = baseBeanDao.getConutByBySqlAndParams(sql,
						new Object[] { companyID });
				int nextnum = a + 1;
				String s2 = "";
				if (nextnum > 0 && nextnum <= 9) {
					s2 = "000" + nextnum;
				} else if (nextnum > 9 && nextnum <= 99) {
					s2 = "00" + nextnum;
				} else if (nextnum > 99 && nextnum <= 999) {
					s2 = "0" + nextnum;
				} else if (nextnum > 999) {
					s2 = "" + nextnum;
				}
				return s2;
			} else {
				return "0001";
			}

		}
	}

	/**
	 * 
	 * 
	 * 不能直接查数据库时分页，获得的数据是List数据时，进行分页方法。
	 * 
	 * @param list
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */

	public PageForm getPageForm(List<BaseBean> list, int pageNumber,
			int pageSize) {
		int count = 0;
		int pageCount = 0;
		int firstResult = 0;
		int maxResult = 0;
		count = list.size();
		if (count == 0) {
			return null;
		}
		pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		firstResult = pageSize * (pageNumber - 1);
		maxResult = Math.min(pageSize, count - firstResult);

		list = list.subList(firstResult, firstResult + maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(list);
		return pageForm;
	}

	/**
	 * 
	 * 
	 * 将Document补充完整
	 * 
	 * @param
	 * @return
	 */
	public List<BaseBean> getFullListBean(List<BaseBean> doclist) {
		try {
			if (doclist != null) {
				String hqlStaff = "from Staff where staffID=?";
				String hqlCompany = "from Company where companyID=?";
				String hqlOrg = "from  COrganization where organizationID=?";

				for (BaseBean baseBean : doclist) {
					Document doc = (Document) baseBean;

					String drafterID = doc.getDrafterID();
					String companyID = doc.getCompanyID();
					String organizationID = doc.getOrganizationID();
					String fromMemberID = doc.getFromMember();
					Staff drafter = null;
					Staff fromMember = null;
					Company companyofDrafter = null;
					COrganization deptofDraft = null;
					if (drafterID != null) {
						drafter = (Staff) baseBeanDao.getBeanByHqlAndParams(
								hqlStaff, new Object[] { drafterID });
					}
					if (fromMemberID != null) {

						fromMember = (Staff) baseBeanDao.getBeanByHqlAndParams(
								hqlStaff, new Object[] { fromMemberID });
					}
					if (companyID != null) {
						companyofDrafter = (Company) baseBeanDao
								.getBeanByHqlAndParams(hqlCompany,
										new Object[] { companyID });
					}
					if (organizationID != null) {
						deptofDraft = (COrganization) baseBeanDao
								.getBeanByHqlAndParams(hqlOrg,
										new Object[] { organizationID });
					}
					if (drafter != null) {
						doc.setDrafterName(drafter.getStaffName());
						String hqldepost = "from DepartmentPost d where d.depPostID in(select c.depPostID from COS c where c.staffID = ? and c.status = ?)";

						DepartmentPost dp = (DepartmentPost) baseBeanDao
								.getBeanByHqlAndParams(hqldepost, new Object[] {
										drafter.getStaffID(), "01" });
						if (dp != null) {
							doc.setPostName(dp.getPostName());
						} else {
							doc.setPostName("尚未分配岗位");
						}

					}
					if (fromMember != null) {
						doc.setFromMember(fromMember.getStaffName());
					} else {
						doc.setFromMember("");
					}
					if (deptofDraft != null) {
						doc.setDeptNameOfDraft(deptofDraft
								.getOrganizationName());
					}
					if (companyofDrafter != null) {
						doc.setCompanyName(companyofDrafter.getCompanyName());
					}
					if (companyofDrafter != null) {
						doc.setDocNum(companyofDrafter.getCompanyIdentifier()
								+ doc.getDocNum());
					}

					// ////////////////////////****以下对象可能为空*************/////////////////////////////

					String subscriberID = doc.getSubscriberID();
					String deptIDofSubscriber = doc.getDeptIDofSubscriber();
					String comIDofSubscriber = doc.getCompanyIDofSubscriber();

					Staff subscriber = (Staff) baseBeanDao
							.getBeanByHqlAndParams(hqlStaff,
									new Object[] { subscriberID });

					COrganization deptofSub = (COrganization) baseBeanDao
							.getBeanByHqlAndParams(hqlOrg,
									new Object[] { deptIDofSubscriber });
					Company companyofSub = (Company) baseBeanDao
							.getBeanByHqlAndParams(hqlCompany,
									new Object[] { comIDofSubscriber });
					if (subscriber != null) {
						doc.setSubscriberName(subscriber.getStaffName());
					}
					if (deptofSub != null) {
						doc.setDeptNameofSub(deptofSub.getOrganizationName());
					}
					if (companyofSub != null) {
						doc.setComNameofSub(companyofSub.getCompanyName());
					}
					String sealerID = doc.getSealerID();
					if (sealerID != null) {
						Staff sealer = (Staff) baseBeanDao
								.getBeanByHqlAndParams(hqlStaff,
										new Object[] { sealerID });
						doc.setSealerName(sealer.getStaffName());
					}

					String deptIDofSealer = doc.getDeptIDofSealer();
					if (deptIDofSealer != null) {
						COrganization deptofSealer = (COrganization) baseBeanDao
								.getBeanByHqlAndParams(hqlOrg,
										new Object[] { deptIDofSealer });
						doc.setDeptNameofSealer(deptofSealer
								.getOrganizationName());
					}

					String comIDofSealer = doc.getCompanyIDofSealer();
					if (comIDofSealer != null) {
						Company companyofSealer = (Company) baseBeanDao
								.getBeanByHqlAndParams(hqlCompany,
										new Object[] { comIDofSealer });
						doc
								.setComNameofSealer(companyofSealer
										.getCompanyName());
					}

					String publisherID = doc.getPublisherID();
					if (publisherID != null) {
						Staff publisher = (Staff) baseBeanDao
								.getBeanByHqlAndParams(hqlStaff,
										new Object[] { publisherID });
						doc.setPublisherName(publisher.getStaffName());
					}

					String deptIDofPublisher = doc.getDeptIDofPublisher();
					if (deptIDofPublisher != null) {
						COrganization deptofPublisher = (COrganization) baseBeanDao
								.getBeanByHqlAndParams(hqlOrg,
										new Object[] { deptIDofPublisher });
						doc.setDeptNameofPublisher(deptofPublisher
								.getOrganizationName());
					}
					String comIDofPublisher = doc.getCompanyIDofPublisher();
					if (comIDofPublisher != null) {
						Company companyofPublisher = (Company) baseBeanDao
								.getBeanByHqlAndParams(hqlCompany,
										new Object[] { comIDofPublisher });
						doc.setComNameofPublisher(companyofPublisher
								.getCompanyName());
					}

					// 处理正式编号
					String formalNum = doc.getFormalNum();
					if (formalNum != null && !formalNum.equals("")) {
						if (formalNum.lastIndexOf("第") == formalNum.length() - 1) {
							doc.setFormalNum("无");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return doclist;

	}

	/**
	 * 
	 * 
	 * 将Document补充完整
	 * 
	 * @param pageForm
	 * @return
	 */
	public PageForm getFullPageForm(PageForm pageForm) {
		try {
			if (pageForm != null) {
				String hqlStaff = "from Staff where staffID=?";
				String hqlCompany = "from Company where companyID=?";
				String hqlOrg = "from  COrganization where organizationID=?";
				String hqlccom = "from ContactCompany where ccompanyID = ?";

				for (BaseBean baseBean : pageForm.getList()) {
					Document doc = (Document) baseBean;

					String drafterID = doc.getDrafterID();
					String companyID = doc.getCompanyID();
					String organizationID = doc.getOrganizationID();
					String fromMemberID = doc.getFromMember();
					Staff drafter = null;
					Staff fromMember = null;
					Company companyofDrafter = null;
					COrganization deptofDraft = null;
					if (drafterID != null) {
						drafter = (Staff) baseBeanDao.getBeanByHqlAndParams(
								hqlStaff, new Object[] { drafterID });
					}
					if (fromMemberID != null) {

						fromMember = (Staff) baseBeanDao.getBeanByHqlAndParams(
								hqlStaff, new Object[] { fromMemberID });
					}
					if (companyID != null) {
						companyofDrafter = (Company) baseBeanDao
								.getBeanByHqlAndParams(hqlCompany,
										new Object[] { companyID });
					}
					if (organizationID != null) {
						deptofDraft = (COrganization) baseBeanDao
								.getBeanByHqlAndParams(hqlOrg,
										new Object[] { organizationID });
					}
					if (drafter != null) {
						doc.setDrafterName(drafter.getStaffName());
						String hqldepost = "from DepartmentPost d where d.depPostID in(select c.depPostID from COS c where c.staffID = ? and c.status = ?)";

						DepartmentPost dp = (DepartmentPost) baseBeanDao
								.getBeanByHqlAndParams(hqldepost, new Object[] {
										drafter.getStaffID(), "01" });
						if (dp != null) {
							doc.setPostName(dp.getPostName());
						} else {
							doc.setPostName("尚未分配岗位");
						}

					}
					if (fromMember != null) {
						doc.setFromMember(fromMember.getStaffName());
					} else {
						doc.setFromMember("");
					}
					if (deptofDraft != null) {
						doc.setDeptNameOfDraft(deptofDraft
								.getOrganizationName());
					}
					if (companyofDrafter != null) {
						doc.setCompanyName(companyofDrafter.getCompanyName());
					}
					if (companyofDrafter != null) {
						doc.setDocNum(companyofDrafter.getCompanyIdentifier()
								+ doc.getDocNum());
					}

					// ////////////////////////****以下对象可能为空*************/////////////////////////////

					String subscriberID = doc.getSubscriberID();
					String deptIDofSubscriber = doc.getDeptIDofSubscriber();
					String comIDofSubscriber = doc.getCompanyIDofSubscriber();

					Staff subscriber = (Staff) baseBeanDao
							.getBeanByHqlAndParams(hqlStaff,
									new Object[] { subscriberID });

					COrganization deptofSub = (COrganization) baseBeanDao
							.getBeanByHqlAndParams(hqlOrg,
									new Object[] { deptIDofSubscriber });
					Company companyofSub = (Company) baseBeanDao
							.getBeanByHqlAndParams(hqlCompany,
									new Object[] { comIDofSubscriber });
					if (subscriber != null) {
						doc.setSubscriberName(subscriber.getStaffName());
					}
					if (deptofSub != null) {
						doc.setDeptNameofSub(deptofSub.getOrganizationName());
					}
					if (companyofSub != null) {
						doc.setComNameofSub(companyofSub.getCompanyName());
					}
					String sealerID = doc.getSealerID();
					if (sealerID != null) {
						Staff sealer = (Staff) baseBeanDao
								.getBeanByHqlAndParams(hqlStaff,
										new Object[] { sealerID });
						doc.setSealerName(sealer.getStaffName());
					}

					String deptIDofSealer = doc.getDeptIDofSealer();
					if (deptIDofSealer != null) {
						COrganization deptofSealer = (COrganization) baseBeanDao
								.getBeanByHqlAndParams(hqlOrg,
										new Object[] { deptIDofSealer });
						doc.setDeptNameofSealer(deptofSealer
								.getOrganizationName());
					}

					String comIDofSealer = doc.getCompanyIDofSealer();
					if (comIDofSealer != null) {
						Company companyofSealer = (Company) baseBeanDao
								.getBeanByHqlAndParams(hqlCompany,
										new Object[] { comIDofSealer });
						doc
								.setComNameofSealer(companyofSealer
										.getCompanyName());
					}

					String publisherID = doc.getPublisherID();
					if (publisherID != null) {
						Staff publisher = (Staff) baseBeanDao
								.getBeanByHqlAndParams(hqlStaff,
										new Object[] { publisherID });
						doc.setPublisherName(publisher.getStaffName());
					}

					String deptIDofPublisher = doc.getDeptIDofPublisher();
					if (deptIDofPublisher != null) {
						COrganization deptofPublisher = (COrganization) baseBeanDao
								.getBeanByHqlAndParams(hqlOrg,
										new Object[] { deptIDofPublisher });
						doc.setDeptNameofPublisher(deptofPublisher
								.getOrganizationName());
					}
					String comIDofPublisher = doc.getCompanyIDofPublisher();
					if (comIDofPublisher != null) {
						Company companyofPublisher = (Company) baseBeanDao
								.getBeanByHqlAndParams(hqlCompany,
										new Object[] { comIDofPublisher });
						doc.setComNameofPublisher(companyofPublisher
								.getCompanyName());
					}

					// 处理正式编号
					String formalNum = doc.getFormalNum();
					if (formalNum != null && !formalNum.equals("")) {
						if (formalNum.lastIndexOf("第") == formalNum.length() - 1) {
							doc.setFormalNum("无");
						}
					}
					
					
					if(doc.getPartyA()!=null&&!doc.getPartyA().equals("")){
						if(doc.getPartyA().startsWith("cstaff")){
							Staff staffA = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlStaff,new Object[]{doc.getPartyA()});
							if(staffA!=null){
								doc.setPartyAName(staffA.getStaffName());
							}
						}else{
							ContactCompany ContactCompanyA = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hqlccom,new Object[]{doc.getPartyA()});
							if(ContactCompanyA!=null){
								doc.setPartyAName(ContactCompanyA.getCompanyName());
							}
						}
					}
					if(doc.getPartyB()!=null&&!doc.getPartyB().equals("")){
						if(doc.getPartyB().startsWith("cstaff")){
							Staff staffB = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlStaff,new Object[]{doc.getPartyB()});
							if(staffB!=null){
								doc.setPartyBName(staffB.getStaffName());
							}
						}else{
							ContactCompany ContactCompanyB = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hqlccom,new Object[]{doc.getPartyB()});
							if(ContactCompanyB!=null){
								doc.setPartyBName(ContactCompanyB.getCompanyName());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return pageForm;

	}

	/**
	 * 
	 * 获取岗位信息和甲方乙方
	 */
	public PageForm getPostName(PageForm pageForm, String type) {
		String hqldepost = "from DepartmentPost d where d.depPostID in(select c.depPostID from COS c where c.staffID = ? and c.status = ?)";

		DepartmentPost dp = null;
		String drafterID = "";
		ViewUnexaminedoc examine = null;
		ViewExaminedoc examineyes = null;
		ViewUnsealdoc seal = null;
		ViewSealdoc sealyes = null;
		ViewUnpublishdoc publish = null;
		ViewPublishdoc publishyes = null;
		ViewUnreaddoc read = null;
		if (pageForm != null) {
			for (BaseBean b : pageForm.getList()) {
				if (type.equals("examine")) {
					examine = (ViewUnexaminedoc) b;
					drafterID = examine.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						examine.setPostname(dp.getPostName());
					} else {
						examine.setPostname("尚未分配岗位");
					}
					
					continue;
				}
				if (type.equals("examineyes")) {
					examineyes = (ViewExaminedoc) b;
					drafterID = examineyes.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						examineyes.setPostname(dp.getPostName());
					} else {
						examineyes.setPostname("尚未分配岗位");
					}
					
					
					continue;
				}
				if (type.equals("seal")) {
					seal = (ViewUnsealdoc) b;
					drafterID = seal.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						seal.setPostname(dp.getPostName());
					} else {
						seal.setPostname("尚未分配岗位");
					}
					
					
					// 处理正式编号
					String formalNum = seal.getFormalnum();
					if (formalNum != null && !formalNum.equals("")) {
						if (formalNum.lastIndexOf("第") == formalNum.length() - 1) {
							seal.setFormalnum("无");
						}
					}else{
						seal.setFormalnum("无");
					}
					
					continue;
				}
				if (type.equals("sealyes")) {
					sealyes = (ViewSealdoc) b;
					drafterID = sealyes.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						sealyes.setPostname(dp.getPostName());
					} else {
						sealyes.setPostname("尚未分配岗位");
					}
					
					// 处理正式编号
					String formalNum = sealyes.getFormalnum();
					if (formalNum != null && !formalNum.equals("")) {
						if (formalNum.lastIndexOf("第") == formalNum.length() - 1) {
							sealyes.setFormalnum("无");
						}
					}else{
						sealyes.setFormalnum("无");
					}
					
					continue;
				}
				if (type.equals("publish")) {
					publish = (ViewUnpublishdoc) b;
					drafterID = publish.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						publish.setPostname(dp.getPostName());
					} else {
						publish.setPostname("尚未分配岗位");
					}
					
					
					continue;
				}
				if (type.equals("publishyes")) {
					publishyes = (ViewPublishdoc) b;
					drafterID = publishyes.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						publishyes.setPostname(dp.getPostName());
					} else {
						publishyes.setPostname("尚未分配岗位");
					}
					
					continue;
				}
				if (type.equals("read")) {
					read = (ViewUnreaddoc) b;
					drafterID = read.getDrafterid();
					dp = (DepartmentPost) baseBeanDao.getBeanByHqlAndParams(
							hqldepost, new Object[] { drafterID, "01" });
					if (dp != null) {
						read.setPostname(dp.getPostName());
					} else {
						read.setPostname("尚未分配岗位");
					}
					
					continue;
				}

			}
		}
		return pageForm;

	}

	/**
	 *
	 * 生成档案条码号
	 * @return
     */
	public String genArchiveBarcode(){

		DocBarCode docBarCode = new DocBarCode();
		docBarCode.setId(serverService.getServerID("barcode"));
		docBarCode.setCreateDate(new Date());



		String sql = " select max(to_number(seq)) from DT_OA_DocBarCode";
		String seq = "00001";

      try {
		  int a = baseBeanDao.getConutByBySqlAndParams(sql, null);

		  if (a>=1) {
			  int nextnum = a + 1;
			  if (nextnum > 0 && nextnum <= 9) {
				  seq = "0000" + nextnum;
			  } else if (nextnum > 9 && nextnum <= 99) {
				  seq = "000" + nextnum;
			  } else if (nextnum > 99 && nextnum <= 999) {
				  seq = "00" + nextnum;
			  } else if (nextnum > 999 && nextnum <= 9999) {
				  seq = "0" + nextnum;
			  }else if (nextnum > 9999) {
				  seq = "" + nextnum;
			  }
		  }
	  }catch (Exception e){

	  }
		docBarCode.setSeq(seq);

		docBarCode.setBarcode(Utilities.getDateString(new Date(),"yyyyMMdd")+seq);
		baseBeanDao.save(docBarCode);

		return docBarCode.getBarcode();
	}

	/**
	 *
	 * 保存
	 *
	 * @param document
	 * @return 返回这个Document
	 */
	public Document storeDocApp(Document document, CAccount account,
								Map<String, Object> session) {


		String hql = "from Staff where staffID = ?";
		String hql2 = "from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?) ";
		try {
			if(document.getDocId()==null||document.getDocId().equals("")){
				String docId = serverService.getServerID("docId");
				document.setDocId(docId);
				document.setDocNum(getDocNum(account.getCompanyID()==null?account.getStaffID():account.getCompanyID(), "docNum"));//公司可能为空
				document.setDrafterID(account.getStaffID());
				document.setCompanyID(account.getCompanyID());
				document.setStatus("I");
				if(document.getModule()==null||document.getModule().equals("")) {
					document.setModule((String) session.get("module"));
				}
				document.setOrganizationID((String) session
						.get("organizationID"));
				document.setDeptIDofDrafter(document.getOrganizationID());
				document.setDraftTime(new Date());
				document.setCreateTime(new Date());
				document.setUpdateTime(new Date());
				//生成档案条码
				document.setBarCode(genArchiveBarcode());

				//添加条码扫描数据

				ScanBarCode scanBarCode = new ScanBarCode();
				scanBarCode.setBarcode(document.getBarCode());
				scanBarCode.setId(serverService.getServerID("scanid"));
				scanBarCode.setUrls("ea/androiddoc/ea_scanDoc.jspa");
				baseBeanDao.save(scanBarCode);
                if(account.getCompanyID()!=null&&!account.getCompanyID().equals("")) {

					Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
					document.setArchiveCustodian(staff.getStaffName());

					ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql2,new Object[]{account.getCompanyID()});

                   if(contactCompany!=null&&contactCompany.getCompanyAddr()!=null){
					   document.setArchiveSite(contactCompany.getCompanyAddr());
				   }

				}
				baseBeanDao.save(document);




			}else{
				Document doc = (Document)baseBeanDao.getBeanByHqlAndParams("from Document where docId = ?",new Object[]{document.getDocId()});

				doc.setDraftTime(new Date());
				doc.setTitle(document.getTitle());
				doc.setTheme(document.getTheme());
				doc.setEmergencyType(document.getEmergencyType());
				doc.setDocType(document.getDocType());
				doc.setNumWord(document.getNumWord());
				doc.setNumTime(document.getNumTime());
				doc.setDraftTime(new Date());
				doc.setUpdateTime(new Date());
				doc.setPartyAName(document.getPartyAName());
				doc.setPartyA(document.getPartyA());
				doc.setPartyAstaff(document.getPartyAstaff());
				doc.setPartyAstaffnames(document.getPartyAstaffnames());
				doc.setStaffIdentityCardA(document.getStaffIdentityCardA());

				doc.setPartyBName(document.getPartyBName());
				doc.setPartyB(document.getPartyB());
				doc.setPartyBstaff(document.getPartyBstaff());
				doc.setPartyBstaffnames(document.getPartyBstaffnames());
				doc.setStaffIdentityCardA(document.getStaffIdentityCardB());

				doc.setStartValidity(document.getStartValidity());
				doc.setEndValidity(document.getEndValidity());

				if(doc.getBarCode()==null||doc.getBarCode().equals("")){
					doc.setBarCode(genArchiveBarcode());
				}

				if (account.getCompanyID() != null && !account.getCompanyID().equals("")) {
                   if(doc.getArchiveSite()==null||doc.getArchiveSite().equals("")) {

                       if(doc.getArchiveCustodian()==null||doc.getArchiveCustodian().equals("")) {
						   Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
						   doc.setArchiveCustodian(staff.getStaffName());
					   }
					   ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID()});

					   if (contactCompany != null && contactCompany.getCompanyAddr() != null) {
						   doc.setArchiveSite(contactCompany.getCompanyAddr());
					   }

				   }
			   }
				baseBeanDao.update(doc);
			}









		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return document;
	}


	/**
	 *
	 * 保存附件
	 *
	 * @param document
	 * @return 返回这个Document
	 */
	public void storeAttachDocApp(Document document,
								DocumentFileAttach docFileAttach, String staffID,String realPath) {

		try {

       if(docFileAttach.getFileId()==null||docFileAttach.getFileId().equals("")) {
		   docFileAttach.setFileId(serverService.getServerID("fileId"));
		   String filePath = docFileAttach.getFilePath();
		   String fileName = filePath
				   .substring(filePath.lastIndexOf("/") + 1);
		   String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		   docFileAttach.setFileName(fileName);
		   docFileAttach.setExt(ext);
		   docFileAttach.setCreatetime(new Date());
		   docFileAttach.setCreator(staffID);
		   docFileAttach.setFileShowName(document.getTitle());
		   docFileAttach.setDocument(document);


		   String  pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";

		   document.setPdfUrl(pdfPath);
		   baseBeanDao.update(document);
		   baseBeanDao.save(docFileAttach);

		   if(!"P".equals(docFileAttach.getFileType())) {
			   new Thread(new Runnable() {
				   public void run() {
					   Office2PdfUtil.office2Pdf(realPath + docFileAttach.getFilePath(), realPath + pdfPath);

				   }
			   }).start();

		   }
	   }else{
		   DocumentFileAttach documentFileAttach = (DocumentFileAttach)baseBeanDao.getBeanByHqlAndParams("from DocumentFileAttach where fileId = ?",new Object[]{docFileAttach.getFileId()});

		   documentFileAttach.setFileShowName(document.getTitle());

		   if(!"P".equals(documentFileAttach.getFileType())) {
			   String filePath = documentFileAttach.getFilePath();
			   String pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";

			   File file = new File(realPath + filePath);
			   long time = file.lastModified();//当前

			   long newtime = time / 1000L * 1000L;
			   Date date = documentFileAttach.getCreatetime();

			   long times = date.getTime();
			   if (newtime > times) {
				   //说明修改过 改过就重新生成
				   new Thread(new Runnable() {
					   public void run() {
						   Office2PdfUtil.office2Pdf(realPath + filePath, realPath + pdfPath);

					   }
				   }).start();

				   documentFileAttach.setCreatetime(new Date());
			   }


		   }
           baseBeanDao.update(documentFileAttach);
	      }

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

	}

//	/*
//	 * 
//	 * 将甲方乙方补充完整
//	 * @see hy.ea.office.service.DocCommonService#getPartyABName(java.lang.String)
//	 */
//	public String getPartyABName(String party){
//		
//		
//		String hqlStaff = "from Staff where staffID=?";
//		String hqlccom = "from ContactCompany where ccompanyID = ?";
//		if(party!=null&&!party.equals("")){
//			if(party.startsWith("cstaff")){
//				Staff staffA = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlStaff,new Object[]{party});
//				if(staffA!=null){
//					return staffA.getStaffName();
//				}
//			}else{
//				ContactCompany ContactCompanyA = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hqlccom,new Object[]{party});
//				if(ContactCompanyA!=null){
//					return ContactCompanyA.getCompanyName();
//				}
//			}
//		}
//		
//		return null;
//	}

	/**
	 * 
	 * 用于新建页面中保存公文数据包括office附件：第一次保存，更新保存，发送至审批前保存，传阅草稿保存，至信息平台前保存；
	 * 
	 * @param document
	 * @return 返回这个Document
	 */
	public Document storeDocData(Document document,
			DocumentFileAttach docFileAttach, CAccount account,
			Map<String, Object> session) {
		Document doc = null;
		String docId = "";
		String hql = "from Document where docId = ?";
		try {
			if (document.getDocId() != null && !document.getDocId().equals("")) {
				docId = document.getDocId();
				doc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
						new Object[] { docId });

				doc.setTitle(document.getTitle());
				doc.setTheme(document.getTheme());
				doc.setDocNum(document.getDocNum());
				doc.setNumWord(document.getNumWord());
				doc.setEmergencyType(document.getEmergencyType());
				doc.setDocType(document.getDocType());
				doc.setSpecificTemplate(document.getSpecificTemplate());
				doc.setStartValidity(document.getStartValidity());
				doc.setEndValidity(document.getEndValidity());
				doc.setAppendComment(document.getAppendComment());
				doc.setReceiverID(document.getReceiverID());
				doc.setReceiverDeptID(document.getReceiverDeptID());
				doc.setReceiverCompanyID(document.getReceiverCompanyID());
				doc.setSocial(document.getSocial());
				doc.setPartyA(document.getPartyA());
				doc.setPartyB(document.getPartyB());
				doc.setPartyAName(document.getPartyAName());//公司名称
				doc.setPartyBName(document.getPartyBName());//公司名称
				doc.setPartyAstaffnames(document.getPartyAstaffnames());
				doc.setPartyAstaff(document.getPartyA());
				doc.setPartyBstaffnames(document.getPartyBstaffnames());
				doc.setPartyBstaff(document.getPartyB());
				doc.setStaffIdentityCardA(document.getStaffIdentityCardA());
				doc.setStaffIdentityCardB(document.getStaffIdentityCardB());
				doc.setProjectName(document.getProjectName());
				doc.setJournalNum(document.getJournalNum());
				if(doc.getStatus().equals("d")){
					doc.setDocNum(getDocNum(account.getCompanyID(), "docNum"));
					doc.setDrafterID(account.getStaffID());
					doc.setCompanyID(account.getCompanyID());
					doc.setStatus("I");
					doc.setDraftTime(new Date());
					doc.setModule((String) session.get("module"));
					doc.setOrganizationID((String) session
							.get("organizationID"));
				}


				baseBeanDao.update(doc);

			} else {
				docId = serverService.getServerID("docId");
				document.setDocId(docId);
				document.setDocNum(getDocNum(account.getCompanyID(), "docNum"));
				document.setDrafterID(account.getStaffID());
				document.setCompanyID(account.getCompanyID());
				document.setStatus("I");
				document.setDraftTime(new Date());
				document.setModule((String) session.get("module"));
				document.setOrganizationID((String) session
						.get("organizationID"));

				baseBeanDao.save(document);
			}
			/*doc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
					new Object[] { docId });*/
			// 处理附件的更新
			String hqlAttach = "from DocumentFileAttach where fileId = ?";
			DocumentFileAttach fileAttach = (DocumentFileAttach) baseBeanDao
					.getBeanByHqlAndParams(hqlAttach,
							new Object[] { docFileAttach.getFileId() });
			if (fileAttach != null) {
				fileAttach.setFileShowName(document.getTitle());// 因为公文名称和附件名称一致
				fileAttach.setFilePath(docFileAttach.getFilePath());
				fileAttach.setFileType(docFileAttach.getFileType());
				fileAttach.setCreatetime(new Date());
				fileAttach.setCreator(account.getStaffID());
				fileAttach.setDocument(doc);
				baseBeanDao.update(fileAttach);
			} else {
				hqlAttach = "from DocumentFileAttach where document = ?";
				List<BaseBean> listAttach = baseBeanDao
						.getListBeanByHqlAndParams(hqlAttach,
								new Object[] { doc });
				if (listAttach.size() != 0) {
					for (BaseBean b : listAttach) {
						fileAttach = (DocumentFileAttach) b;
						baseBeanDao.deleteBeanByKey(DocumentFileAttach.class,
								fileAttach.getKey());
					}
				}
				docFileAttach.setFileId(serverService.getServerID("fileId"));
				docFileAttach.setFileShowName(document.getTitle());
				String filePath = docFileAttach.getFilePath();
				String fileName = filePath
						.substring(filePath.lastIndexOf("/") + 1);
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				docFileAttach.setFileName(fileName);
				docFileAttach.setExt(ext);
				docFileAttach.setCreatetime(new Date());
				docFileAttach.setCreator(account.getStaffID());
				docFileAttach.setDocument(doc);
				baseBeanDao.save(docFileAttach);

			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return doc;
	}

	/*
	 * 根据根公文获得office附件
	 * 
	 */
	public List<BaseBean> getOfficeAttach(Document document) {
		String hqlAttach = "from DocumentFileAttach where document = ?";
		List<BaseBean> attachlist = baseBeanDao.getListBeanByHqlAndParams(
				hqlAttach, new Object[] { document });
		if(attachlist.size()!=0){
		for (BaseBean b : attachlist) {
			DocumentFileAttach fileattach = (DocumentFileAttach) b;
			if (fileattach.getFilePath() != null) {
				fileattach.setFilePath(fileattach.getFilePath().replace("\\",
						"/"));
			}
	    	}
		}
		return attachlist;
	}

	/**
	 * 
	 * 补全修改document
	 */

	public Document getViewFullDoc(Document document) {
		String hqlStaff = "from Staff where staffID=?";
		String hqlCompany = "from Company where companyID=?";

		String hqlOrg = "from  COrganization where organizationID=?";
		Staff drafter = null;
		Staff subscriber = null;
		Company companyofDrafter = null;
		Company companyofSub = null;
		COrganization deptofSub = null;
		COrganization deptofDraft = null;

		String drafterID = document.getDrafterID();
		String companyID = document.getCompanyID();
		String organizationID = document.getOrganizationID();

		String subscriberID = document.getSubscriberID();
		String deptIDofSubscriber = document.getDeptIDofSubscriber();
		String comIDofSubscriber = document.getCompanyIDofSubscriber();
		if (drafterID != null) {
			drafter = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlStaff,
					new Object[] { drafterID });
		}
		if (subscriberID != null) {
			subscriber = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlStaff,
					new Object[] { subscriberID });
		}
		if (companyID != null) {
			companyofDrafter = (Company) baseBeanDao.getBeanByHqlAndParams(
					hqlCompany, new Object[] { companyID });
		}
		if (comIDofSubscriber != null) {
			companyofSub = (Company) baseBeanDao.getBeanByHqlAndParams(
					hqlCompany, new Object[] { comIDofSubscriber });
		}
		if (deptIDofSubscriber != null) {
			deptofSub = (COrganization) baseBeanDao.getBeanByHqlAndParams(
					hqlOrg, new Object[] { deptIDofSubscriber });
		}
		if (organizationID != null) {
			deptofDraft = (COrganization) baseBeanDao.getBeanByHqlAndParams(
					hqlOrg, new Object[] { organizationID });
		}
		if (drafter != null) {
			document.setDrafterName(drafter.getStaffName());
			String hqldepost = "from DepartmentPost d where d.depPostID in(select c.depPostID from COS c where c.staffID = ? and c.status = ?)";
			DepartmentPost dp = (DepartmentPost) baseBeanDao
					.getBeanByHqlAndParams(hqldepost, new Object[] {
							drafter.getStaffID(), "01" });
			if (dp != null) {
				document.setPostName(dp.getPostName());
			} else {
				document.setPostName("尚未分配岗位");
			}

		}
		if (deptofDraft != null) {
			document.setDeptNameOfDraft(deptofDraft.getOrganizationName());
		}
		if (companyofDrafter != null) {
			document.setCompanyName(companyofDrafter.getCompanyName());
			document.setDocNum(companyofDrafter.getCompanyIdentifier()
					+ document.getDocNum());
		}
		if (subscriber != null) {
			document.setSubscriberName(subscriber.getStaffName());
		}
		if (deptofSub != null) {
			document.setDeptNameofSub(deptofSub.getOrganizationName());
		}
		if (companyofSub != null) {
			document.setComNameofSub(companyofSub.getCompanyName());
		}

		// ////////////////////////****以下对象可能为空*************/////////////////////////////

		String sealerID = document.getSealerID();
		if (sealerID != null) {
			Staff sealer = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlStaff,
					new Object[] { sealerID });
			document.setSealerName(sealer.getStaffName());
		}

		String deptIDofSealer = document.getDeptIDofSealer();
		if (deptIDofSealer != null) {
			COrganization deptofSealer = (COrganization) baseBeanDao
					.getBeanByHqlAndParams(hqlOrg,
							new Object[] { deptIDofSealer });
			document.setDeptNameofSealer(deptofSealer.getOrganizationName());
		}

		String comIDofSealer = document.getCompanyIDofSealer();
		if (comIDofSealer != null) {
			Company companyofSealer = (Company) baseBeanDao
					.getBeanByHqlAndParams(hqlCompany,
							new Object[] { comIDofSealer });
			document.setComNameofSealer(companyofSealer.getCompanyName());
		}

		String publisherID = document.getPublisherID();
		if (publisherID != null) {
			Staff publisher = (Staff) baseBeanDao.getBeanByHqlAndParams(
					hqlStaff, new Object[] { publisherID });
			document.setPublisherName(publisher.getStaffName());
		}

		String deptIDofPublisher = document.getDeptIDofPublisher();
		if (deptIDofPublisher != null) {
			COrganization deptofPublisher = (COrganization) baseBeanDao
					.getBeanByHqlAndParams(hqlOrg,
							new Object[] { deptIDofPublisher });
			document.setDeptNameofPublisher(deptofPublisher
					.getOrganizationName());
		}
		String comIDofPublisher = document.getCompanyIDofPublisher();
		if (comIDofPublisher != null) {
			Company companyofPublisher = (Company) baseBeanDao
					.getBeanByHqlAndParams(hqlCompany,
							new Object[] { comIDofPublisher });
			document.setComNameofPublisher(companyofPublisher.getCompanyName());
		}
		// 处理正式编号
		String formalNum = document.getFormalNum();
		if (formalNum != null && !formalNum.equals("")) {
			if (formalNum.lastIndexOf("第") == formalNum.length() - 1) {
				document.setFormalNum("无");
			}
		}else{
			document.setFormalNum("无");
		}

		return document;
	}

	/*
	 * 添加公文操作记录
	 * 
	 */
	public String addTrackRecord(String docId, String operatorID,
			String deptIDofOperator, String compnayIDofOperator, String content) {
		DocumentTrack track = new DocumentTrack();
		track.setTrackId(serverService.getServerID("trackid"));
		track.setDocId(docId);
		track.setOperatorID(operatorID);
		track.setDeptIDofOperator(deptIDofOperator);
		track.setCompnayIDofOperator(compnayIDofOperator);
		track.setOperateContent(content);
		track.setOperaterTime(Utilities.getDateString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		baseBeanDao.save(track);

		return null;

	}

	/*
	 * 
	 * receiver:收信人;sender:发件人；content;短信内容；
	 */ 
	public String sendPhoneMessage(String receiverID, String receiverDeptID,String receiverCompanyID,
			String senderID,String companyID, String content, String title, String module,
			String type) {
		String hqlcom = "from Company where companyID = ?";
		String hql = "from Staff where staffID = ?";
		Staff receiver = (Staff) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { receiverID });
		Staff sender = (Staff) baseBeanDao.getBeanByHqlAndParams(hql,
				new Object[] { senderID });
		Company company1  = null;
		String comname = "";
		if(companyID!=null&&!companyID.equals("")){
			 company1  = (Company) baseBeanDao.getBeanByHqlAndParams(hqlcom,new Object[]{companyID});
			if(company1!=null){
				comname = company1.getCompanyName();
			}

		}
		String hqlorg = "from COrganization where organizationID = ?";
		COrganization org = null;
		if (receiverDeptID != null && !receiverDeptID.equals("")) {
			org = (COrganization) baseBeanDao.getBeanByHqlAndParams(hqlorg,
					new Object[] { receiverDeptID });
		}
		 if(module==null||module.equals("")){
			 module = "doc";
		 }

		Company company  = (Company) baseBeanDao.getBeanByHqlAndParams(hqlcom,new Object[]{receiverCompanyID});
		String orgName = "";
		if(org!=null){
			orgName = org.getOrganizationName();
		}
		String comName = "";
		if(company!=null){
			comName = company.getCompanyName();
		}
		String[] arraymodule = { "doc", "contract", "cg", "dg", "pg", "jg",
				"finace", "complaint", "InduReg", "regime", "AnnNoti", "news",
				"InterDis", "ExterDis", "bulletin", "CountReg", "person","MeetRecord" };
		String[] arrayname = { "公文", "合同", "公司规划管理", "部门规划管理", "个人规划管理",
				"职业规划管理", "财务Excel报表", "投诉处理", "行业法规管理", "制度管理", "公告通知管理", "新闻管理",
				"内部纠纷", "外部纠纷", "简报管理", "国家法规管理", "人事报表传输" ,"会议记录管理"};
		
		String modulename = "";
		
		for (int i = 0; i < arraymodule.length; i++) {
			if (arraymodule[i].equals(module)) {
				modulename = arrayname[i];
				break;
			}
		}
		
		String commonweb = "。登录地址为 http://www.impf2010.com/page/WFJClient/pc/pc_login.jsp。";

		if (type.equals("noexamine")) {
			content = "您好，您提交的公文《" + title + "》" + content + "," + commonweb;
		} else if (type.equals("infoplat")) {
			content = sender.getStaffName() + "给您发送了一条公文,(标题为《" + title + "》),"
					+ content;
		} else if (type.equals("position")) {
			content = content + "标题为<<" + title + ">>的公文转移至["+comName+"]-["
					+ orgName + "]-[" + modulename + "]下"
					+ commonweb;

		} else {
			String s = "";

			if("".equals(comname)){
				s = sender.getStaffName();
			}else{
				s = comname+"-"+sender.getStaffName();
			}
            if(receiverCompanyID!=null&&!receiverCompanyID.equals("")){
				content = s + "给您发送了一条公文,(标题为《" + title
						+ "》),至["+comName+"]-[" + orgName + "]-[" + modulename
						+ "]-" + content + commonweb;
			}else{
				content = s + "给您发送了一条公文,(标题为《" + title
						+ "》),至[我的]-[办公通]-" + content + commonweb;
			}


		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		
		String telnumber = ""; 
		String reStr = ""; // 消息发送返回的状态

		 if (receiver != null) {
			telnumber = receiver.getReference();
			if (telnumber == null || telnumber.equals("")) {
				telnumber = "15210904250";
				content = sender.getStaffName() + "系统中无手机号码";
			}
		} else if (receiverID != null && !receiverID.equals("")) {
			telnumber = receiverID;
		}

		try { 
			String d = telnumber.replaceAll("[^0-9-]+", " ").trim();
			String t = d.replaceAll("[ ]+", ","); // 第二个参数是短信发送需要的电话号码分割url。etc.136855,11255
			
			mobileMessage.setMessage(content);
			mobileMessage.setMobiles(t);
			reStr = mobileMessage.sendMsg();
		} catch (IOException e) {
			log.error("DocCommonServiceImpl.sendPhoneMessage():" + e.getMessage());
			logger.error("操作异常", e);
		}

		return reStr;
	}  
	
	/**
	 * 
	 * 打包Office
	 * @param docIds
	 * @return
	 */
	public String packZipOfOffice(String realpath, String docIds) {
		String[] array = docIds.split(",");
		String hql = "from Document where docId = ?";
		String hqlattach = "from DocumentFileAttach where document = ?";
		Document doc = null;
		List<BaseBean> listattach = null;
		List<String> docPaths = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			doc = (Document) baseBeanDao.getBeanByHqlAndParams(hql,
					new Object[] { array[i] });
			listattach = baseBeanDao.getListBeanByHqlAndParams(hqlattach,
					new Object[] { doc });
			DocumentFileAttach attach = null;
			File testfile = null;
			for (BaseBean b : listattach) {
				attach = (DocumentFileAttach) b;
				testfile = new File(realpath+attach.getFilePath());
				if(testfile.exists()){
				  docPaths.add(attach.getFilePath());
				}
			}

		}
		String strZipName = "";
		try {
			// 打包成zip

			byte[] buffer = new byte[50000];

			// 生成的ZIP文件名为office.zip

			strZipName = "document/office.zip";
			File filec = new File(realpath+strZipName);
			if(!filec.exists()){
				filec.createNewFile();
			}

			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					realpath+strZipName));
			List<File> file1 = new ArrayList<File>();
			File file = null;
			for (String docpath : docPaths) {
				file = new File(docpath);
				file1.add(file);
			}

			for (File f : file1) {

				FileInputStream fis = new FileInputStream(realpath+f);

				out.putNextEntry(new ZipEntry(f.getName()));

				int len;

				// 读入需要下载的文件的内容，打包到zip文件

				while ((len = fis.read(buffer)) > 0) {

					out.write(buffer, 0, len);

				}

				out.closeEntry();

				fis.close();

			}

			out.close();

//			logger.info("生成office.zip成功");

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return strZipName;

	}
    /**
     *
     * 根据公司名称查询正常状态公司
     * @param companyName
     * @return
     */
    public List<BaseBean> getAllCompany(String companyName){
        String hql = "from Company where companyName like ? and companyStatus = ?";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{"%"+companyName+"%","00"});

        return list;

    }


    /**
     *
     * 根据人员姓名或者电话查询人以及部门
     * @param name
     * @return
     */
    public Map<String,Object> getAllPeople(String name,String companyID){
    	List<Object> params = new ArrayList<Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select f.staffname,f.staffid,f.reference,c.organizationid,o.organizationname,c.companyid,f.staffCode,m.companyname,f.headimage,f.staffIdentityCard from Dtcos c,dt_hr_staff f,Dtcorganization o,dtCompany m where c.companyid = m.companyid and  c.organizationid = o.organizationid and c.staffid = f.staffid and  (f.staffname = ? or f.reference = ?) and c.cosstatus = ?";

		params.add(name);
		params.add(name);
		//params.add("01");
		params.add("50");
        if(companyID!=null&&!companyID.equals("")){
			sql+=" and c.companyid=?";
			params.add(companyID);
		}

		List<Object> obj = baseBeanDao.getListObjectBySqlAndParams(sql,params.toArray());
		map.put("plist",obj);
        return map;

    }

	/**
	 *
	 * 补充信息
	 * @param document
	 * @return
	 */
	public Document bcInfo(Document document){
        String hql = "from Staff where staffID = ?";
		String hql2 = "from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?)";
		String hql3 = "from Company where companyID = ?";
		String hql4 = "from COrganization where organizationID = ?";
        int save = 0;
		if(document.getBarCode()==null||document.getBarCode().equals("")) {
			//条码处理
			document.setBarCode(genArchiveBarcode());


			//添加条码扫描数据

			ScanBarCode scanBarCode = new ScanBarCode();
			scanBarCode.setBarcode(document.getBarCode());
			scanBarCode.setId(serverService.getServerID("scanid"));
			scanBarCode.setUrls("ea/androiddoc/ea_scanDoc.jspa");
			baseBeanDao.save(scanBarCode);
			save = 1;
		}

       //档案地点保管人处理
		if (document.getCompanyID() != null && !document.getCompanyID().equals("")) {
			if(document.getArchiveSite()==null||document.getArchiveSite().equals("")) {

				if(document.getArchiveCustodian()==null||document.getArchiveCustodian().equals("")) {
					Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{document.getDrafterID()});
					document.setArchiveCustodian(staff.getStaffName());
					save = 1;
				}
				ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql2, new Object[]{document.getCompanyID()});

				if (contactCompany != null && contactCompany.getCompanyAddr() != null) {
					document.setArchiveSite(contactCompany.getCompanyAddr());
					save = 1;
				}

			}
		}
        if(save==1){
			baseBeanDao.update(document);
		}


		//拟稿人公司，拟稿人部门，拟稿人姓名，拟稿人电话


		//补充拟稿人信息

		if(document.getCompanyID()!=null&&!document.getCompanyID().equals("")) {
			Company company = (Company) baseBeanDao.getBeanByHqlAndParams(hql3, new Object[]{document.getCompanyID()});
			document.setCompanyName(company.getCompanyName());
		}
		if(document.getOrganizationID()!=null&&!document.getOrganizationID().equals("")) {
			COrganization organization = (COrganization) baseBeanDao.getBeanByHqlAndParams(hql4, new Object[]{document.getOrganizationID()});

			if (organization != null) {
				document.setDeptNameOfDraft(organization.getOrganizationName());
			}
		}

		Staff ff = (Staff)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{document.getDrafterID()});
		document.setDrafterName(ff.getStaffName());
		document.setTelphone(ff.getReference());

         //补充审批人信息


		if(document.getCompanyIDofSubscriber()!=null&&!document.getCompanyIDofSubscriber().equals("")) {
			Company companysub = (Company) baseBeanDao.getBeanByHqlAndParams(hql3, new Object[]{document.getCompanyIDofSubscriber()});
			document.setComNameofSub(companysub.getCompanyName());
		}
		if(document.getDeptIDofSubscriber()!=null&&!document.getDeptIDofSubscriber().equals("")) {
			COrganization organizationSub = (COrganization) baseBeanDao.getBeanByHqlAndParams(hql4, new Object[]{document.getDeptIDofSubscriber()});

			document.setDeptNameofSub(organizationSub.getOrganizationName());

		}
		if(document.getSubscriberID()!=null&&!document.getSubscriberID().equals("")) {
		 Staff ffsub = (Staff)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{document.getSubscriberID()});
		    document.setSubscriberName(ffsub.getStaffName());
		}
		//补充盖章人信息

		if(document.getCompanyIDofSealer()!=null&&!document.getCompanyIDofSealer().equals("")) {
			Company companyseal = (Company) baseBeanDao.getBeanByHqlAndParams(hql3, new Object[]{document.getCompanyIDofSealer()});
			document.setComNameofSealer(companyseal.getCompanyName());

		}
		if(document.getDeptIDofSealer()!=null&&!document.getDeptIDofSealer().equals("")) {
			   COrganization organizationSeal = (COrganization) baseBeanDao.getBeanByHqlAndParams(hql4, new Object[]{document.getDeptIDofSealer()});

				document.setDeptNameofSealer(organizationSeal.getOrganizationName());

		}

		if(document.getSealerID()!=null&&!document.getSealerID().equals("")) {
			Staff ffseal = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{document.getSealerID()});
			document.setSealerName(ffseal.getStaffName());
		}

		//补充分发人信息

		if(document.getCompanyIDofPublisher()!=null&&!document.getCompanyIDofPublisher().equals("")) {
			Company companypub = (Company) baseBeanDao.getBeanByHqlAndParams(hql3, new Object[]{document.getCompanyIDofPublisher()});
			document.setComNameofPublisher(companypub.getCompanyName());

		}
		if(document.getDeptIDofPublisher()!=null&&!document.getDeptIDofPublisher().equals("")) {
			COrganization organizationpub = (COrganization) baseBeanDao.getBeanByHqlAndParams(hql4, new Object[]{document.getDeptIDofPublisher()});

			document.setDeptNameofPublisher(organizationpub.getOrganizationName());

		}

		if(document.getPublisherID()!=null&&!document.getPublisherID().equals("")) {
			Staff ffpub = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{document.getPublisherID()});
			document.setPublisherName(ffpub.getStaffName());
		}


		return document;
	}

	/**
	 *
	 *
	 *    根据模板创建拟稿
	 *
	 * @param realpath
	 * @param companyID
	 * @param staff
	 * @return
	 */
	public String createDoc(String realpath,String companyID,String staffID,String contractTypes,Staff staff,String contractSource){
         List<BaseBean> beans  = new ArrayList<BaseBean>();
		String[] contractType = null;
		if(contractTypes!=null&&!contractTypes.equals("")) {
			    contractType = contractTypes.split(",");//最新的合同协议
		}

		String hqlall = "from DocStaff f where f.staffID = ? and f.companyID = ? and f.status = ? and contractSource=?";
        List<String> strsList = new ArrayList<String>();
		List<BaseBean> listdoc = baseBeanDao.getListBeanByHqlAndParams(hqlall, new Object[]{staff.getStaffID(), companyID,"00",contractSource});

        if(listdoc.size()==0){
        	//说明没有生成过合同，不管是新增还是修改

		}else{
        	//说明有合同
          //有合同就删除
			for(int j = 0;j<listdoc.size();j++){

				strsList.add(((DocStaff)listdoc.get(j)).getContractType());
			}
		}


		String hql = "from DocumentTemplate where companyId = ? and contractType in (";
		List<String> strsList2 = new ArrayList<String>();
		if(contractTypes!=null&&!contractTypes.equals("")){

			List<Object> params = new ArrayList<Object>();
			boolean contractBool = false;
			params.add(companyID);
			for(int i = 0;i<contractType.length;i++) {
				strsList2.add(contractType[i]);
				if (!strsList.contains(contractType[i])) {
					hql += "?,";
					params.add(contractType[i]);
					contractBool = true;
				}
			}
			hql = hql.substring(0, hql.length() - 1);
			hql+=")";

			String storepath = "document/" + companyID;
			String officePath = "";
			List<BaseBean> dtlist = new ArrayList<>();
			if (contractBool){
				dtlist = baseBeanDao
						.getListBeanByHqlAndParams(hql, params.toArray());
			}
			for(int j = 0;j<dtlist.size();j++){

				DocumentTemplate dt = (DocumentTemplate)dtlist.get(j);

				officePath = zOfficeService.createOffice(realpath, dt.getFileType(), dt
						.getTemplatePath(), storepath);

				Document document = new Document();
				document.setTitle(staff.getStaffName()+staff.getReference()+"的"+dt.getFileShowName());

				CAccount account = new CAccount();
				account.setCompanyID(companyID);
				account.setStaffID(staffID);
				String	checkOrgID = contractService.getCheckOrg(staffID, companyID);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("organizationID", checkOrgID);
				map.put("module",dt.getModule());

				Document doc = storeDocApp(document, account,
						map);
				DocumentFileAttach docFileAttach = new DocumentFileAttach();
				docFileAttach.setFileType(dt.getFileType());
				docFileAttach.setFilePath(officePath);
				storeAttachDocApp(doc,docFileAttach,staffID,realpath);


				DocStaff docStaff = new DocStaff();
				docStaff.setId(serverService.getServerID("id"));
				docStaff.setCompanyID(companyID);
				docStaff.setStaffID(staff.getStaffID());
				docStaff.setContractType(dt.getContractType());
				docStaff.setContractTypeName(dt.getContractTypeName());
				docStaff.setDocId(doc.getDocId());
				docStaff.setStatus("00");
                docStaff.setContractSource(contractSource);
				beans.add(docStaff);

			}
		  }

         //需要去除没选的
		List<String> hqls = new ArrayList<String>();
		List<Object[]> parms = new ArrayList<Object[]>();

		for(int k = 0;k<listdoc.size();k++){
				DocStaff docStaff = (DocStaff) listdoc.get(k);
			    if(!strsList2.contains(docStaff.getContractType())){
			    	//说明要删除
					hqls.add("update DocStaff set status = ? where id = ?");
					parms.add(new Object[]{"99",docStaff.getId()});
					hqls.add("update Document set status = ? where docId = ?");
					parms.add(new Object[]{"d",docStaff.getDocId()});
				}
			}

		baseBeanService.executeHqlsByParamsList(beans, hqls.toArray(new String[]{}) ,parms);



		return "";
	}
}
