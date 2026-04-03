package hy.ea.company.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.Accommod;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.RoomNumber;
import hy.ea.bo.office.vo.HotelVO;
import hy.ea.bo.office.vo.RoomTypeVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
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

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 住宿管理
 * 
 * @author l_admin
 * 
 */
public class AccommodAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private List<BaseBean> beans;
	private String parameter;
	private Accommod accommod;
	private RoomNumber roomNumber;
	private String result;
	/**
	 * 酒店列表
	 */
	private List<HotelVO> hotelList;
	private List<RoomTypeVO> roomTypeListChind;
	private List<Accommod> accommodList;
	private List<RoomNumber> roomNumberList;
	/**
	 * 星级列表
	 */
	private List<CCode> starsList;
	/**
	 * 房间类别列表
	 */
	private List<CCode> roomTypeList;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	private String roomnumString;
	private String edit;
	private ContactCompany hoteln;

	private Map<String, RoomNumber> m;
	private Map<String, RoomNumber> dm;

	/**
	 * 加载修改
	 * 
	 * @return
	 */
	public String editHotel() {

		String hql1 = "from Accommod where accommodID = ?";
		accommod = (Accommod) baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[] { accommod.getAccommodID() });

		String hql = "from RoomNumber where accommodID = ?";
		roomNumberList = new ArrayList<RoomNumber>();
		beans = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { accommod.getAccommodID() });
		for (int i = 0; i < beans.size(); i++) {
			roomNumberList.add((RoomNumber) beans.get(i));
		}
		String hql2 = "from ContactCompany where ccompanyID = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql2,
				new Object[] { accommod.getHotelName() });
		ContactCompany cc = (ContactCompany) beans.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accommod", accommod);
		map.put("roomNumberList", roomNumberList);
		map.put("hoteln", cc);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 动态验证星级
	 * 
	 * @return
	 */
	public String valHotelStsByName() {

		String hql = "from Accommod a where a.hotelName = ?";
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { accommod.getHotelName() });
		accommod = new Accommod();
		if (beans != null) {
			accommod = (Accommod) beans.get(0);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accommod", accommod);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();

		return "success";
	}

	/**
	 * 根据 酒店名称 房间类别 查询 其他信息 及 房间号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String disHotelQiTi() {
		String sql = "select a.floor,a.roomprice,a.roomdisprice,a.roomagrprice,a.bednum,"
				+ "a.bedoccnum,a.remarks,a.accommodid from DT_Accommod a where a.hotelname = ? and"
				+ " a.roomtype = ?";
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanBySqlAndParams(sql, new Object[] {
				accommod.getHotelName(), accommod.getRoomType() });
		accommodList = new ArrayList<Accommod>();
		roomNumberList = new ArrayList<RoomNumber>();
		if (beans != null) {
			Object obj = (Object) beans.get(0);
			Object[] objs = (Object[]) obj;
			accommod = new Accommod();
			accommod.setFloor(objs[0].toString());
			accommod.setRoomPrice(objs[1].toString());
			accommod.setRoomDisPrice(objs[2].toString());
			accommod.setRoomAgrPrice(objs[3].toString());
			accommod.setBedNum(objs[4].toString());
			accommod.setBedOccNum(objs[5].toString());
			accommod.setRemarks(objs[6].toString());
			accommod.setAccommodID(objs[7].toString());
			accommodList.add(accommod);

			List<BaseBean> beans1 = new ArrayList<BaseBean>();
			String sql1 = "select r.roomnumid,r.roomnum,r.starts from dt_roomnumber r where r.accommodid = ?";
			beans1 = baseBeanService.getListBeanBySqlAndParams(sql1,
					new Object[] { objs[7].toString() });
			if (beans1 != null) {
				for (int i = 0; i < beans1.size(); i++) {
					Object obj1 = (Object) beans1.get(i);
					Object[] obj1s = (Object[]) obj1;
					roomNumber = new RoomNumber();
					roomNumber.setRoomNumID(obj1s[0].toString());
					roomNumber.setRoomNum(obj1s[1].toString());
					roomNumber.setStarts(obj1s[2].toString());
					roomNumberList.add(roomNumber);
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accommodList", accommodList);
		map.put("roomNumberList", roomNumberList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 根据酒店名称预设星级
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String disRoomType() {

		String sql = "select c.codevalue as stars,cc.codevalue"
				+ ",a.roomtype,a.stars from DT_Accommod a left join dtccode"
				+ " c on a.stars = c.codeid left join dtccode cc"
				+ " on a.roomtype = cc.codeid  where a.hotelname = ?";
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] { accommod.getHotelName() });
		roomTypeListChind = new ArrayList<RoomTypeVO>();
		if (beans != null) {
			for (int i = 0; i < beans.size(); i++) {
				Object obj = (Object) beans.get(i);
				Object[] objs = (Object[]) obj;
				RoomTypeVO hv = new RoomTypeVO();
				hv.setStarsName(objs[0].toString());
				hv.setCodeValue(objs[1].toString());
				hv.setRoomType(objs[2].toString());
				hv.setStars(objs[3].toString());
				roomTypeListChind.add(hv);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roomTypeListChind", roomTypeListChind);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 分配住宿
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String disRoom() {

		String sql = "select distinct c.companyname,a.hotelname"
				+ " from DT_Accommod a left join dtContactCompany"
				+ " c on a.hotelname = c.ccompanyid left join dtccode"
				+ " c on a.stars = c.codeid";
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanBySqlAndParams(sql, null);
		hotelList = new ArrayList<HotelVO>();
		if (beans != null) {
			for (int i = 0; i < beans.size(); i++) {
				Object obj = (Object) beans.get(i);
				Object[] objs = (Object[]) obj;
				HotelVO hv = new HotelVO();
				hv.setCompanyname(objs[0].toString());
				hv.setHotelname(objs[1].toString());
				hotelList.add(hv);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotelList", hotelList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 删除酒店信息
	 * 
	 * @return
	 */

	public String deleteHotel() {
		accommod = selectAccommod();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql = "delete Accommod where accommodID = ?";

		Object[] params = { accommod.getAccommodID() };
		beans = new ArrayList<BaseBean>();
		parameter = "删除酒店：(酒店ID：" + accommod.getHotelName() + ")";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		String hql1 = "delete RoomNumber where accommodID = ? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql1 }, params);

		return "success";
	}

	/**
	 * 酒店预设
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String addHotel() {

		// System.out.println(m.values()) ;

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		List<String> strs = new ArrayList<String>();
		// String[] str = roomnumString.split("_"); //房间数组
		if ( null == accommod.getAccommodID() || accommod.getAccommodID().equals("")) {
			accommod.setCompanyID(account.getCompanyID());
			accommod.setOrganizationID(organizationID);
			accommod.setCreateName(account.getAccountName());
			accommod.setCreateDate(new Date().toLocaleString());
			accommod.setAccommodID(serverService.getServerID("accommod"));

			if (accommod.getRoomDisPrice().equals("")) {
				accommod.setRoomDisPrice(accommod.getRoomPrice());
			}
			if (accommod.getRoomAgrPrice().equals("")) {
				accommod.setRoomAgrPrice("0");
			}
			if (accommod.getBedOccNum().equals("")) {
				accommod.setBedOccNum("0");
			}
			if (accommod.getRemarks().equals("")) {
				accommod.setRemarks("无");
			}
		} else {
			accommod.setUpdateName(account.getAccountName());
			accommod.setUpdateDate(new Date().toLocaleString());

		}

		if (null != m) {
			for (RoomNumber r : m.values()) {
				if (r.getRoomNumID().length() < 20) {
					r.setRoomNumID(serverService.getServerID("roomNum"));
					r.setAccommodID(accommod.getAccommodID());
					r.setStarts("00");
					r.setCompanyID(account.getCompanyID());
					beans.add(r);
				} else {

				}

			}
		}
		if (null != dm) {
			for (RoomNumber r : dm.values()) {
				if (r.getRoomNumID().length() > 20) {
					String hql = "delete RoomNumber where roomNumID = ?";
					paramsList.add(new String[] { r.getRoomNumID() });
					strs.add(hql);
					// beans.add(r);
				} else {

				}

			}
		}
		beans.add(accommod);
		baseBeanService.executeHqlsByParamsList(beans,
				strs.toArray(new String[] {}), paramsList);

		return "success";

	}

	public String toSearch() {
		ActionContext.getContext().getSession().put("accommod", accommod);
		return getAllList();
	}

	public String getAllList() {
		List<Object> list = allAccommod();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);

		return "getAccommodList";
	}

	private List<Object> allAccommod() {
		List<Object> results = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();

		String sql = "select c.companyname,c.codevalue as stars,cc.codevalue,a.roomprice"
				+ ",a.floor,a.bednum,a.bedoccnum,a.remarks,a.hotelname,a.accommodid,a.companyid"
				+ ",a.organizationid from DT_Accommod a left join dtContactCompany c on"
				+ " a.hotelname = c.ccompanyid left join dtccode c on a.stars = c.codeid left"
				+ " join dtccode cc on a.roomtype = cc.codeid";
		sql += " where 1=1";
		if (search != null && search.equals("search")) {
			if (accommod.getHotelName() != ""
					&& !accommod.getHotelName().equals("")) {
				sql += " and a.hotelname = ?";
				parms.add(accommod.getHotelName());
			}
			if (accommod.getRoomPrice() != ""
					&& !accommod.getRoomPrice().equals("")) {
				sql += " and a.roomPrice like ?";
				parms.add("%" + accommod.getRoomPrice() + "%");
			}
			if (accommod.getStars() != "" && !accommod.getStars().equals("")) {
				sql += " and a.stars = ?";
				parms.add(accommod.getStars());
			}
			if (accommod.getFloor() != "" && !accommod.getFloor().equals("")) {
				sql += " and a.floor like ?";
				parms.add("%" + accommod.getFloor() + "%");
			}
			if (accommod.getRoomType() != ""
					&& !accommod.getRoomType().equals("")) {
				sql += " and a.roomtype = ?";
				parms.add(accommod.getRoomType());
			}
		}
		sql += " order by c.companyname,c.codevalue desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	private void toPage() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();

		// 酒店星级列表

		starsList = codeService.getCCodeListByPID(companyID,
				"ccode20131231BCBZP5PNT60000000002");

		// 房间类别列表

		roomTypeList = codeService.getCCodeListByPID(companyID,
				"ccode20131231BCBZP5PNT60000000012");
		// 往来单位
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//
	}

	public Accommod selectAccommod() {
		String hql = " from Accommod where accommodID = ? ";
		Object[] params = { accommod.getAccommodID() };
		List<BaseBean> baseList = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		Accommod accommod = (Accommod) baseList.get(0);
		return accommod;
	}

	// 预设下拉菜单
	public String jumpHotel() {
		toPage();

		if (accommod != null) {

			edit = accommod.getAccommodID();
		} else {
			edit = "add";
		}

		return "toaccommod_edit";
	}

	// 预设下拉菜单
	public String jumpHotel1() {
		toPage();
		disRoom();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("starsList", starsList);
		map.put("roomTypeList", roomTypeList);
		map.put("hotelList", hotelList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public Accommod getAccommod() {
		return accommod;
	}

	public void setAccommod(Accommod accommod) {
		this.accommod = accommod;
	}

	public List<CCode> getStarsList() {
		return starsList;
	}

	public void setStarsList(List<CCode> starsList) {
		this.starsList = starsList;
	}

	public List<CCode> getRoomTypeList() {
		return roomTypeList;
	}

	public void setRoomTypeList(List<CCode> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRoomnumString() {
		return roomnumString;
	}

	public void setRoomnumString(String roomnumString) {
		this.roomnumString = roomnumString;
	}

	public RoomNumber getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(RoomNumber roomNumber) {
		this.roomNumber = roomNumber;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<HotelVO> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<HotelVO> hotelList) {
		this.hotelList = hotelList;
	}

	public List<RoomTypeVO> getRoomTypeListChind() {
		return roomTypeListChind;
	}

	public void setRoomTypeListChind(List<RoomTypeVO> roomTypeListChind) {
		this.roomTypeListChind = roomTypeListChind;
	}

	public List<Accommod> getAccommodList() {
		return accommodList;
	}

	public void setAccommodList(List<Accommod> accommodList) {
		this.accommodList = accommodList;
	}

	public List<RoomNumber> getRoomNumberList() {
		return roomNumberList;
	}

	public void setRoomNumberList(List<RoomNumber> roomNumberList) {
		this.roomNumberList = roomNumberList;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public ContactCompany getHoteln() {
		return hoteln;
	}

	public void setHoteln(ContactCompany hoteln) {
		this.hoteln = hoteln;
	}

	public Map<String, RoomNumber> getM() {
		return m;
	}

	public void setM(Map<String, RoomNumber> m) {
		this.m = m;
	}

	public Map<String, RoomNumber> getDm() {
		return dm;
	}

	public void setDm(Map<String, RoomNumber> dm) {
		this.dm = dm;
	}

}
