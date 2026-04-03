package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffFamilyMember;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class FMemberAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private List<StaffFamilyMember> memberList;
	private StaffFamilyMember member;
	@Resource
	private CCodeService codeService;
	private List<CCode> codeRelationshipList;
	private List<CCode> codePostTypeList;
	private String parameter;
	private Map<String, StaffFamilyMember> membermap;
	private List<BaseBean> beans;
	private List<BaseBean> familyList;
	
	public String saveFMember() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		member =new StaffFamilyMember();
		beans = new ArrayList<BaseBean>();
		if(null!=membermap){
			for(StaffFamilyMember members:membermap.values()){
				this.member.setStaffID(members.getStaffID());
				if (null == members.getMemberID() || "".equals(members.getMemberID())) {
					members.setMemberID(serverService.getServerID("member"));
					parameter = "添加家庭成员";
				}
				else
				{
					parameter = "修改家庭成员";
				}
				String hql2="from Staff where staffID=?";
				members.setCompanyID(account.getCompanyID());
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{members.getStaffID()});
				parameter += "(人员名称:"+staff.getStaffName()+")";
				
				beans.add(members);
			}
		    CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		    beans.add(logBook);
		    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		
		return "success";
	}

	public String delFMember() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		beans = new ArrayList<BaseBean>();
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{member.getStaffID()});
		CLogBook logBook = logBookService.saveCLogBook(null,"删除家庭成员(人员名称："+ staff.getStaffName()+")", account);
		beans.add(logBook);
		String hql1 = "delete StaffFamilyMember where staffID = ? and memberID = ? ";
		Object[] params = { member.getStaffID(),member.getMemberID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql1}, params);
		return "success";
	}
	public String getListFMember() {
		CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
		Object [] params = {member.getStaffID()};
		familyList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffFamilyMember where staffID = ? order by memberID desc", params);
		codeRelationshipList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20100423vw54xx7r4f0000000056");
		codePostTypeList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20100423vw54xx7r4f0000000019");
		return "list";
	}
	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, String> map = new HashMap<String, String>();
		codeRelationshipList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20100423vw54xx7r4f0000000056");
		codePostTypeList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20100423vw54xx7r4f0000000019");
		for (CCode b : codeRelationshipList) {
			map.put(b.getCodeID(),b.getCodeValue());
		}
		for (CCode b : codePostTypeList) {
			map.put(b.getCodeID(),b.getCodeValue());
		}
		StaffFamilyMember.setOMap(map);
		Object[] params = {member.getStaffID()};
		String hql =" from StaffFamilyMember where staffID = ? order by memberID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel( StaffFamilyMember
				.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出家庭成员", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	public List<CCode> getCodePostTypeList() {
		return codePostTypeList;
	}

	public void setCodePostTypeList(List<CCode> codePostTypeList) {
		this.codePostTypeList = codePostTypeList;
	}

	public Map<String, StaffFamilyMember> getMembermap() {
		return membermap;
	}

	public void setMembermap(Map<String, StaffFamilyMember> membermap) {
		this.membermap = membermap;
	}

	
	public List<BaseBean> getFamilyList() {
		return familyList;
	}

	public void setFamilyList(List<BaseBean> familyList) {
		this.familyList = familyList;
	}

	public List<CCode> getCodeRelationshipList() {
		return codeRelationshipList;
	}

	public void setCodeRelationshipList(List<CCode> codeRelationshipList) {
		this.codeRelationshipList = codeRelationshipList;
	}

	public List<StaffFamilyMember> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<StaffFamilyMember> memberList) {
		this.memberList = memberList;
	}

	public StaffFamilyMember getMember() {
		return member;
	}

	public void setMember(StaffFamilyMember member) {
		this.member = member;
	}
}
