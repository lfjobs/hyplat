package hy.ea.human.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.human.dao.COrganizationDao;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SInterface;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SInterfaceService;
import hy.plat.service.ServerService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class COrganizationServiceImpl implements COrganizationService {
	@Resource
	private COrganizationDao organizationDao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private SInterfaceService sinterfaceService;
	@Resource
	private  ServerService serverService;
	private String parameter;
	private List<BaseBean> beans;
	@Resource
	private CLogBookService logBookService;
	@Override
	public void updateOrganizationByID(String organizationID, String companyID) {
		   String hql2 = " update COS set organizationID = '99' where companyID = ? and organizationID = ? ";
			String hql1 = " delete from COA where companyID = ?  and organizationID = ?";
			Object[] params = { companyID, organizationID };
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[] {hql1,hql2 }, params);
			organizationDao.updateOrganizationByID(organizationID, companyID);
	}

	@Override
	public List<COrganization> getOrganizationList(String organizationPID,
			String unitsID) {
		return organizationDao.getOrganizationList(organizationPID, unitsID);
	}

	/**
	 * lwt
	 */
	@Override
	public PageForm getPageForm(int pageNumber, int pageSize,
			String organizationID, String companyID) {
		List<COrganization> olist = new ArrayList<COrganization>();
		// olist.add(organizationDao.getOrganization(organizationID,
		// companyID));
		List<COrganization> listBaseBean = getOrganizationByID(organizationID,
				companyID, olist);

		if (listBaseBean.size() == 0) {
			return null;
		}

		if (pageNumber == 0) {
			pageNumber = 1;
		}

		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(listBaseBean.size());

		int pageCount = 0;
		pageCount = listBaseBean.size() / pageSize
				+ ((listBaseBean.size() % pageSize) == 0 ? 0 : 1);

		pageForm.setPageCount(pageCount);

		if (pageNumber > pageCount) {
			pageNumber = pageCount;
		}
		pageForm.setPageNumber(pageNumber);

		List<BaseBean> list = new ArrayList<BaseBean>();

		int lastNumber = pageNumber * pageSize - 1;
		if (lastNumber > listBaseBean.size() - 1) {
			lastNumber = listBaseBean.size() - 1;
		}
		for (int i = pageSize * (pageNumber - 1); i <= lastNumber; i++) {
			list.add(listBaseBean.get(i));
		}

		pageForm.setList(list);

		return pageForm;
	}

	/**
	 * lwt
	 */
	@Override
	public List<COrganization> getOrganizationByID(String organizationID,
			String companyID, List<COrganization> list) {
		List<COrganization> organizationList = organizationDao.getOrganizationList(organizationID, companyID);

		if (organizationList != null) {
			for (COrganization organization : organizationList) {
				list.add(organization);
				if (organizationDao.getOrganizationList(organization
						.getOrganizationID(), companyID) != null) {
					getOrganizationByID(organization.getOrganizationID(),
							companyID, list);
				}
			}
		}
		return list;
	}
	@Override
	public List<COrganization> findOrgByAcc(String accId,List<COrganization> orgLst){
		orgLst.addAll(organizationDao.findOrgByAcc(accId));
		return orgLst;
	}
	
	/**
	 * lwt
	 */
	@Override
	public COrganization getOrganization(String companyID,
			String organizationID) {

		return organizationDao.getOrganization(organizationID, companyID);
	}
	@Override
	public InputStream exportOrganization(List<COrganization> list) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx2Format = new WritableCellFormat(wf);
			// 垂直居中
			totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx2Format.setAlignment(Alignment.CENTRE);
			WritableSheet sheet = workbook.createSheet("机构信息", 0);
			sheet.addCell(new Label(0, 0, "序号", totalx2Format));
			sheet.addCell(new Label(1, 0, "下属机构编号", totalx2Format));
			sheet.addCell(new Label(2, 0, "部门名称", totalx2Format));
			sheet.addCell(new Label(3, 0, "机构负责人", totalx2Format));
			sheet.addCell(new Label(4, 0, "负责内容", totalx2Format));
			sheet.addCell(new Label(5, 0, "岗位编号", totalx2Format));
			sheet.addCell(new Label(6, 0, "岗位名称", totalx2Format));
			sheet.addCell(new Label(7, 0, "职务编号", totalx2Format));
			sheet.addCell(new Label(8, 0, "职务名称", totalx2Format));
			sheet.addCell(new Label(9, 0, "职责", totalx2Format));
			//sheet.addCell(new Label(6, 0, "岗位要求", totalx2Format));
			//sheet.addCell(new Label(7, 0, "工作地点", totalx2Format));
			
			
			//修改导出负责内容出错问题
			
			int i = 1;
			List<SInterface> sInterfaceList = sinterfaceService.getSInterfaceListByStatus();
			for (COrganization corganization : list) {
				 jxl.write.Number number = new jxl.write.Number(0,i, i);
				 sheet.addCell(number);
				sheet.addCell(new Label(1, i, corganization.getOcode()));
				sheet.addCell(new Label(2, i, corganization.getOrganizationName()));
				sheet.addCell(new Label(3, i, corganization.getOrganizationManager()));
				for(int j = 0 ;j < sInterfaceList.size() ; j++){
					if(sInterfaceList.get(j).getInterfaceUrl().equals(corganization.getOrganizationUrl())){
						sheet.addCell(new Label(4, i, sInterfaceList.get(j).getInterfaceName()));
					}
				}
				sheet.addCell(new Label(5, i, corganization.getOpostCode()));
				sheet.addCell(new Label(6, i, corganization.getOpostName()));
				sheet.addCell(new Label(7, i, corganization.getOdutiesID()));
				sheet.addCell(new Label(8, i, corganization.getOdutiesName()));
				sheet.addCell(new Label(9, i, corganization.getOrganizationDesc()));
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return new ByteArrayInputStream(os.toByteArray());
	}

	@Override
	public List<COrganization> getOrganizationListForTree(
			String organizationPID, String CompanyID, String RoleID) {
		return organizationDao.getOrganizationListForTree(organizationPID, CompanyID, RoleID);
	}

	@Override
	public List<COrganization> getCOrganizationListForOTree(
			String organizationPID, String RoleID) {
		return organizationDao.getCOrganizationListForOTree(organizationPID,RoleID);
	}

	@Override
	public COrganization getCoranizationForOTree(String organizationID) {
		return organizationDao.getCoranizationForOTree(organizationID);
	}

	@Override
	public List<COrganization> getOrganizationList(String companyID) {
		return organizationDao.getOrganizationList(companyID);
	}
    @Override
    public List<COrganization> getOrganizationListAll(String companyID) {
        return organizationDao.getOrganizationListAll(companyID);
    }

	@Override
	public List<BaseBean> getThenFiveDepartments(String companyId) {
		return organizationDao.getThenFiveDepartments(companyId);
	}

	@Override
	public String saveOrg(CAccount account, COrganization organization){
//添加
         beans = new ArrayList<BaseBean>();
		if(null == organization.getOrganizationID() || organization.getOrganizationID().equals("")){
			organization.setOrganizationID(serverService.getServerID("organization"));
			organization.setOrganizationCreateDate(new Date());
			organization.setCompanyID(account.getCompanyID());
			organization.setStatus("00");

			parameter = "添加机构(机构名称:"+organization.getOrganizationName()+")";
			beans.add(organization);
		}else{//修改
			COrganization org = (COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization where organizationID = ?",new Object[]{organization.getOrganizationID()});

			org.setOrganizationPID(organization.getOrganizationPID());
			org.setOrganizationName(organization.getOrganizationName());
			org.setOrganizationNumber(organization.getOrganizationNumber());
			org.setOcode(organization.getOcode());
			org.setOrganizationUrl(organization.getOrganizationUrl());
			beans.add(org);
			parameter= "修改机构(机构名称:"+organization.getOrganizationName()+")";
		}

		CLogBook logBook = logBookService.saveCLogBook(account.getCompanyID(), parameter, account);

		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);

		return null;

	}

	public  void delOrg(String companyID,String organizationID){

		beans = new ArrayList<BaseBean>();
		String hql1 = " delete from COA where companyID = ?  and organizationID = ?";
		String hql2 = " update COS set organizationID = '99' where companyID = ? and organizationID = ? ";
		String hql3 = " update  COrganization set Status = '98' where companyID = ? and organizationID = ? ";
		String hql4 = " delete from DepartmentPost where companyID = ? and organizationID = ?";
		Object[] params = { companyID, organizationID };

		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[] {hql1,hql2,hql3,hql4 }, params);



	}

}
