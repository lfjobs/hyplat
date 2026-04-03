package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.office.CardReader;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 读卡器
 * */
@Controller
@Scope("prototype")
public class CardReaderAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private CardReader cardReader;
	private PageForm pageForm;
	private String search;
	private Company company;
	private int pageNumber;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// 根据条件查询读卡器单
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", cardReader);
		return getcardReaderList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CardReader.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			cardReader = (CardReader) session.get("tablesearch");
			if (!cardReader.getCode().equals("")) {
				dc.add(Restrictions.like("code",
						cardReader.getCode(), MatchMode.ANYWHERE));
			}
			if (!cardReader.getOrienType().equals("")) {
				dc.add(Restrictions.eq("orienType", cardReader.getOrienType()));
			}
			if (!cardReader.getPositionCode().equals("")) {
				dc.add(Restrictions.like("positionCode", cardReader.getPositionCode(),
						MatchMode.ANYWHERE));
			}
			if (!cardReader.getPositionName().equals("")) {
				dc.add(Restrictions.like("positionName", cardReader.getPositionName(),
						MatchMode.ANYWHERE));
			}
			
			
		}
		return dc;
	}

	// 读卡器列表列表
	public String getcardReaderList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "carBillslist";
	}

	
	// 读卡器保存

	public String savecardReader() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		
		if (cardReader.getCode()== null
				|| "".equals(cardReader.getCode())) {
			cardReader.setCode(serverService.getServerID("cardReader"));
			
		}
		cardReader.setCompanyID(account.getCompanyID());
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cardReader);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);
		return "success";
	}

	// 删除读卡器
	public String delCardReader() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(), cardReader.getCode() };
		String hql = "delete from CardReader where companyID=?  and cardReaderID=?";
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				new String[] { hql }, params);
		return "success";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}



	public CardReader getCardReader() {
		return cardReader;
	}

	public void setCardReader(CardReader cardReader) {
		this.cardReader = cardReader;
	}

}
