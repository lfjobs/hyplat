package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CardInfo;
import hy.ea.bo.office.CardReader;
import hy.ea.bo.office.CardRecord;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 卡信息
 * */
@Controller
@Scope("prototype")
public class CardManageAction {
	private static final Logger logger = LoggerFactory.getLogger(CardManageAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private CardInfo cardInfo;
	private CardRecord cardRecord;
	private CarInformation carInformation;
	private String cardCode;
	private PageForm pageForm;
	private String search;
	private Company company;
	private String result;
	private int pageNumber;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// 根据条件查询卡信息单
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", cardInfo);
		return getCardInfoList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CardInfo.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			cardInfo = (CardInfo) session.get("tablesearch");
			if (!cardInfo.getCardCode().equals("")) {
				dc.add(Restrictions.like("cardCode", cardInfo.getCardCode(),
						MatchMode.ANYWHERE));
			}
			if (cardInfo.getValidityTime() > 0) {
				dc.add(Restrictions.eq("validityTime", cardInfo
						.getValidityTime()));
			}
			if (!cardInfo.getStatus().equals("")) {
				dc.add(Restrictions.eq("status", cardInfo.getStatus()));
			}
			if (!cardInfo.getStatesType().equals("")) {
				dc.add(Restrictions.eq("statesType", cardInfo.getStatesType()));
			}
			if (!cardInfo.getCardType().equals("")) {
				dc.add(Restrictions.like("cardType", cardInfo.getCardType(),
						MatchMode.ANYWHERE));
			}

		}
		return dc;
	}

	// 卡信息列表列表
	public String getCardInfoList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		if (pageForm != null) {
			String hql = "from CarInformation where carID = ?";
			for (BaseBean b : pageForm.getList()) {
				CardInfo cInfo = (CardInfo) b;
				CarInformation car = (CarInformation) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { cInfo
								.getCarInfomation() });
				if (car != null) {
					cInfo.setCarNum(car.getCarNum());
				}
			}
		}
		return "carBillslist";
	}

	// 卡信息保存

	public String saveCardInfo() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");

		if (cardInfo.getID() == null || "".equals(cardInfo.getID())) {
			cardInfo.setID(serverService.getServerID("cardInfo"));

		}
		cardInfo.setCompanyID(account.getCompanyID());
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cardInfo);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);
		return "success";
	}

	/*
	 * 卡记录列表
	 * 
	 * 
	 */

	public String toSearchRecord() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", cardRecord);
		if (cardCode != null) {
			return getCardRecordList();
		} else {
			return getCarGooutRecord();
		}
	}
 //停用
	private DetachedCriteria getListRecord() {
		Map<String, Object> sessions = ActionContext.getContext().getSession();
		CAccount account = (CAccount) sessions.get("account");
		String companyId = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CardRecord.class);
		dc.add(Restrictions.eq("companyId", companyId));
		dc.addOrder(Order.desc("enterTime"));
		if (search != null && search.equals("search")) {
			cardRecord = (CardRecord) sessions.get("tablesearch");
			if (!cardRecord.getReaderEnter().equals("")) {
				dc.add(Restrictions.like("readerEnter", cardRecord
						.getReaderEnter(), MatchMode.ANYWHERE));
			}
			if (!cardRecord.getCardCode().equals("")) {
				dc.add(Restrictions.like("cardCode", cardRecord.getCardCode(),
						MatchMode.ANYWHERE));
			}
			if (!cardRecord.getStartTime().equals("")
					&& !cardRecord.getEndTime().equals("")) {
				dc.add(Restrictions.between("enterTime", Utilities
						.getDateFromString(cardRecord.getStartTime(),
								"yyyy-MM-dd HH:mm"), Utilities
						.getDateFromString(cardRecord.getEndTime(),
								"yyyy-MM-dd HH:mm")));
			}

		}
		return dc;
	}

	// 车辆门禁记录查询
	private PageForm searchRecord(String companyID) {
		cardRecord = (CardRecord) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from CardRecord a where  a.companyId like ? and cardCode in (select cardCode from CardInfo c where carInfomation in(select carID from CarInformation i where c.carInfomation=i.carID and i.carNum like ?) and a.cardCode = c.cardCode) and";
		String hql2 = " a.cardCode like ? and a.readerEnter like ?";
		String hql3 = "and a.enterTime > ? and a.enterTime< ? ";
		String hql = hql1 + hql2;
		Date startT = null;
		Date endT = null;
		Object[] params = new Object[] { "%" + companyID + "%",
				"%" + cardRecord.getCarNum() + "%",
				"%" + cardRecord.getCardCode() + "%",
				"%" + cardRecord.getReaderEnter() + "%"

		};
		if (!cardRecord.getStartTime().equals("")
				&& !cardRecord.getEndTime().equals("")) {
			hql = hql1 + hql2 + hql3;
			startT = Utilities.getDateFromString(cardRecord.getStartTime(),
					"yyyy-MM-dd HH:mm");
			endT = Utilities.getDateFromString(cardRecord.getEndTime(),
					"yyyy-MM-dd HH:mm");
			params = new Object[] { "%" + companyID + "%",
					"%" + cardRecord.getCarNum() + "%",

					"%" + cardRecord.getCardCode() + "%",
					"%" + cardRecord.getReaderEnter() + "%", startT, endT };
		}

		try {
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql, params);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return pageForm;
	}

	// 卡记录列表列表(停用)
	public String getCardRecordList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListRecord());
		if (pageForm != null) {
			String hql = "from CardReader where code = ?";
			String hql2 = "from CardInfo where cardCode = ?";
			String hql3 = "from CarInformation where carID = ?";
			for (BaseBean b : pageForm.getList()) {
				CardRecord crecord = (CardRecord) b;
				CardReader creader = (CardReader) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { crecord
								.getReaderEnter() });
				if (creader != null) {
					crecord.setReaderEnterName(creader.getPositionName());
				}

				CardInfo cInfo = (CardInfo) baseBeanService
						.getBeanByHqlAndParams(hql2, new Object[] { crecord
								.getCardCode() });
				if (cInfo != null) {
					CarInformation carinfo = (CarInformation) baseBeanService
							.getBeanByHqlAndParams(hql3, new Object[] { cInfo
									.getCarInfomation() });
					if (carinfo != null) {
						crecord.setCarNum(carinfo.getCarNum());
					}
				}
			}
		}
		return "cardRecordlist";
	}

	/*
	 * 获取进场读卡器和出场读卡器
	 * 
	 */
	public String getCardReader() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String cardCode = request.getParameter("cardCode");
		Object[] param = new Object[] { cardCode, account.getCompanyID() };
		String hql = "";
		if (cardCode != null) {
			hql = "from CardRecord where cardCode = ? and companyID = ?";
		} else {
			hql = "from CardRecord where companyID = ?";
			param = new Object[] { account.getCompanyID() };

		}
		List<BaseBean> listb = baseBeanService.getListBeanByHqlAndParams(hql,
				param);
		List<CardRecord> listR = new ArrayList<CardRecord>();
		hql = "from CardReader where code = ?";
		for (BaseBean baseBean : listb) {
			CardRecord cardRecord = (CardRecord) baseBean;
			CardReader reader = (CardReader) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { cardRecord
							.getReaderEnter() });
			if (reader != null) {
				cardRecord.setReaderEnterName(reader.getPositionName());
			}
			listR.add(cardRecord);
		}
		List<String> enterReader = new ArrayList<String>();
		List<CardRecord> cre = new ArrayList<CardRecord>();
		for (CardRecord cr : listR) {
			if (!enterReader.contains(cr.getReaderEnter())) {
				enterReader.add(cr.getReaderEnter());
				cre.add(cr);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", cre);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 车辆出入记录
	 * 
	 * @return
	 */
	public String getCarGooutRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		if (search != null && search.equals("search")) {
			pageForm = searchRecord(account.getCompanyID());
		} else {
			String hql1 = "from CardRecord where companyID = ? order by enterTime Desc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql1, new Object[] { account.getCompanyID() });
		}
		if (pageForm != null) {
			String hql = "from CardReader where code = ?";
			String hql2 = "from CardInfo where cardCode = ?";
			String hql3 = "from CarInformation where carID = ?";
			for (BaseBean b : pageForm.getList()) {
				CardRecord crecord = (CardRecord) b;
				CardReader creader = (CardReader) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { crecord
								.getReaderEnter() });
				if (creader != null) {
					crecord.setReaderEnterName(creader.getPositionName());
				}

				CardInfo cInfo = (CardInfo) baseBeanService
						.getBeanByHqlAndParams(hql2, new Object[] { crecord
								.getCardCode() });
				if (cInfo != null) {
					CarInformation carinfo = (CarInformation) baseBeanService
							.getBeanByHqlAndParams(hql3, new Object[] { cInfo
									.getCarInfomation() });
					if (carinfo != null) {
						crecord.setCarNum(carinfo.getCarNum());
						crecord.setCreateUser(carinfo.getDriver());// 临时借用createUser用于显示driver;
					}
				}
			}
		}
		return "cardGooutlist";
	}

	/**
	 * 出入记录查看
	 * 
	 * @return
	 */
	public String getGooutView() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String ID = request.getParameter("ID");

		String hql = "from CardRecord where ID = ?";
		cardRecord = (CardRecord) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { ID });

		hql = "from CardReader where code = ?";
		CardReader cReader = (CardReader) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { cardRecord
						.getReaderEnter() });
		if (cReader != null) {
			cardRecord.setReaderEnterName(cReader.getPositionName());
		}

		hql = "from CardInfo where cardCode = ?";
		cardInfo = (CardInfo) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { cardRecord.getCardCode() });
		if (cardInfo != null) {
			hql = "from CarInformation where  carID = ?";
			carInformation = (CarInformation) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { cardInfo
							.getCarInfomation() });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardInfo", cardInfo);
		map.put("cardRecord", cardRecord);
		map.put("carInfo", carInformation);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

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

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public CardRecord getCardRecord() {
		return cardRecord;
	}

	public void setCardRecord(CardRecord cardRecord) {
		this.cardRecord = cardRecord;
	}

	public String getCode() {
		return cardCode;
	}

	public void setCardRecordID(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

}
