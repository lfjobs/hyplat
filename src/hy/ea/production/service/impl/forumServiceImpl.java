package hy.ea.production.service.impl;

import hy.ea.bo.finance.ProductComment;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.human.Staff;
import hy.ea.production.service.forumService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.ImageCut;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

@Service
public class forumServiceImpl implements forumService {

	@Resource
	private BaseBeanDao beandao;
	@Resource
	private UploadContentToFileService contentToFileService;
	@Resource
	private ServerService serverService;

	/**
	 * 分页
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            显示条数
	 * @param sql
	 *            sql语句
	 * @param sqlcount
	 *            总记录数SQL语句
	 * @param params
	 *            参数
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
			String sqlcount, Object[] params) {
		int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = beandao.getConutByBySqlAndParamsAndPage(
				sql, params, firstResult, maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	/**
	 * 根据txt URL 获取内容
	 * 
	 * @param url
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private String getContentFromFile(String filepath) {
		String path = ServletActionContext.getServletContext()
				.getRealPath("\\") + filepath;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			return contentToFileService.getContent(path);

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * @Title: 查询
	 * @Description: 获取当前用户信息
	 * @return 返回当前用户对象
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TEshopCusCom queryUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		return cus;
	}

	/**
	 * @Title: 存储
	 * @Description: 存储url
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void storageUrl() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("url");
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getHeader("Referer");
		session.setAttribute("url", url);
	}

	/**
	 * @Title: 查询
	 * @Description: 查询公司信息
	 * @return 返回公司信息
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object[] forumMessage(String ccompanyID) {

		Object obj = beandao.getObjectBySqlAndParams(
				"select y.companyname,y.logopath,m.compnay_id from Dtcontactcompany y,dt_ccom_com m where y.ccompanyid=m.ccompany_id and y.ccompanyid=?",
				new Object[] { ccompanyID });
		
		Object[] obj1 = (Object[]) obj;

		return obj1;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询公司帖子数量
	 * @return 返回公司帖子数量
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object invitation(Object companyID) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(p.ppid) from dt_productpackaging p ");
		sb.append("where p.companyid=? and p.type = ? ");
		sb.append("and p.delstatus= ? and p.fiveclear= ? ");
		Object invitationCount = beandao.getObjectBySqlAndParams(sb.toString(),
				new Object[] { companyID, "公司论坛", "00", "2" });

		return invitationCount;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询公司粉丝数
	 * @return 返回公司粉丝数
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object attention(Object companyID) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(s.jfid) from t_eshop_cuscom m ");
		sb.append("left join dtjoinfans s on m.sccid = s.zsccid ");
		sb.append("where m.companyId = ? and s.state = ? ");

		Object attentionCount = beandao.getObjectBySqlAndParams(sb.toString(),
				new Object[] { companyID, "00" });

		return attentionCount;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询公司帖子列表
	 * @return 返回公司帖子数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> invitationList(int pageNumber, int pageSize,
			String companyid, String commonEssence) {
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
		StringBuilder sb = new StringBuilder();
		sb.append("select ss.ppid,ss.staffname,ss.headimage,ss.goodsname,ss.url,to_char(ss.packagingdate,'YYYY-MM-DD HH24:MI:SS'),ss.sccid, ");
		sb.append("max(case ss.type when ? then ss.a else 0 end) b,max(case ss.type when ? then ss.a else 0 end) d,max(case ss.type when ? then ss.a else 0 end) e ");
		sb.append("from(select p.ppid, h.staffname, h.headimage, p.goodsname, f.url,c.type,p.packagingdate,p.sccid,count(c.type) a ");
		sb.append("from dt_productpackaging p ");
		sb.append("left join dt_hr_staff h on p.staffid = h.staffid left join dtGoodFunction f on p.goodsid = f.goodsid ");
		sb.append("left join dt_ProductComment c on p.ppid = c.ppid where p.companyid=? ");
		sb.append("and p.delstatus = ? and p.type = ? ");
		sb.append("group by p.ppid, h.staffname, h.headimage, p.goodsname, f.url,c.type,p.packagingdate,p.sccid) ss ");
		sb.append("group by ss.ppid,ss.staffname,ss.headimage,ss.goodsname,ss.url,ss.packagingdate,ss.sccid ");
		if (commonEssence != null && !commonEssence.equals("")) {
			if (commonEssence.equals("00")) {
				sb.append("order by ss.packagingDate desc ");
			} else if (commonEssence.equals("01")) {
				sb.append("order by b desc ");
			}
		}
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
				sb.toString(), "select count(*) from (" + sb.toString() + ")",
				new Object[] { "0", "1", "2", companyid, "00", "公司论坛" });
		StringBuilder imgUrl = new StringBuilder();
		List<String> imgList = new ArrayList<String>();
		List<String> textList = new ArrayList<String>();
		if (pageForm != null
				&& (pageForm.getList() != null || pageForm.getList().size() != 0)) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = pageForm.getList().get(i);
				Object[] obj1 = (Object[]) obj;
				String url = getContentFromFile(obj1[4].toString());
				String p = null;
				String u = null;
				if (url != null && !url.equals("")) {
					String[] url1 = url.split("article_img");
					for (int j = 1; j < url1.length; j++) {
						String url2 = url1[j];
						String jjPath = "";
						if (j == 1) {
							p = url2.substring(url2.indexOf("<p>")+3,
									url2.indexOf("</p>"));
						}
						if(j<4){
							url2 = url2.substring(url2.indexOf("upload_files/"),
									url2.indexOf("\" alt"));
							//判断原路径下是否有图片true:进入,false:跳过
				    		File file=new File(path+url2);
				    		jjPath = url2.split("\\.")[0] + "small." + url2.split("\\.")[1];
				    		if(file.exists()){
				    			//判断小图路径下是否有图片true:进入创建,false:跳过
				    			File file1=new File(path+jjPath);
				    			if(!file1.exists()){
				    				ImageCut.scale(path+url2, path+jjPath, 100, 100);
				    			}
				    		}
							url2 = jjPath + ",";
						}
						imgUrl.append(url2);
					}
					u = imgUrl.toString().substring(0,
							imgUrl.toString().length() - 1);
				}
				textList.add(i, p);
				imgList.add(i, u);
				imgUrl.delete( 0, imgUrl.length() );
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		map.put("imgList", imgList);
		map.put("textList", textList);
		return map;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询是否关注
	 * @return 返回判断
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> estimate(Object companyID) {
		TEshopCusCom cuscom = queryUser();
		boolean userExist;
		boolean hetherAttention;
		if (cuscom == null) {
			userExist = false;
			hetherAttention = false;
		} else {
			userExist = true;
			String hql = "select s from JoinFans s,TEshopCusCom m where s.fsccId=? and s.zsccId=m.sccId and m.companyId=? and s.state=?";
			JoinFans jf = (JoinFans) beandao.getBeanByHqlAndParams(hql,
					new Object[] { cuscom.getSccId(), companyID, "00" });
			if (jf == null) {
				hetherAttention = false;
			} else {
				hetherAttention = true;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userExist", userExist);
		map.put("hetherAttention", hetherAttention);
		return map;
	}

	/**
	 * @Title: 添加
	 * @Description: 加关注
	 * @return
	 */
	@Override
	@Transactional
	public void follow(String companyid) {
		TEshopCusCom cuscom = queryUser();
		String hql = "select s from JoinFans s,TEshopCusCom m where s.fsccId=? and s.zsccId=m.sccId and m.companyId=?";
		JoinFans jf = (JoinFans) beandao.getBeanByHqlAndParams(hql,
				new Object[] { cuscom.getSccId(), companyid });
		if (jf != null) {
			if (jf.getState().equals("00")) {
				jf.setState("01");
			} else {
				jf.setState("00");
			}
		} else {
			jf = new JoinFans();
			TEshopCusCom cc = (TEshopCusCom) beandao
					.getBeanByHqlAndParams(
							"from TEshopCusCom t where t.companyId=?",
							new Object[] { companyid });
			jf.setJfID(serverService.getServerID("jf"));
			jf.setState("00");
			jf.setSource("关注");
			jf.setZaccount(cc.getAccount());
			jf.setFaccount(cuscom.getAccount());
			jf.setZsccId(cc.getSccId());
			jf.setFsccId(cuscom.getSccId());
			jf.setStaffid(cc.getStaffid());
			jf.setFansDate(new Date());
		}
		beandao.saveOrUpdate(jf);
	}

	/**
	 * @Title: 查询
	 * @Description: 查询回复过的帖子
	 * @return 返回判断
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm reply(String staffid, int pageNumber, int pageSize) {

		StringBuilder sb = new StringBuilder();
		sb.append("select t.ppid,p.goodsname,f.headimage,f.staffname,to_char(t.commentdate,'YYYY-MM-DD HH24:MI:SS'),t.content,t.pcID,t.goodsid,p.sccid ");
		sb.append("from dt_ProductComment t,dt_productpackaging p,dt_hr_staff f ");
		sb.append("where t.ppid = p.ppid and t.staffid = f.staffid and t.type = ? and t.tostaffid = ? ");
		sb.append("and p.type = ? and p.delstatus = ? order by t.commentdate desc ");
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
				sb.toString(), "select count(*) from (" + sb.toString() + ")",
				new Object[] { "0",staffid, "公司论坛","00" });
		return pageForm;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询个人信息
	 * @return 返回map
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> myMessage(String sccid,String wsccid) {
		// 查询个人信息
		StringBuilder sb = new StringBuilder();
		sb.append("select b.staffname, b.headimage, b.sccid, b.e, b.g, count(n.jfid) ");
		sb.append("from (select a.staffname, a.headimage, a.sccid, a.e, count(s.jfid) g ");
		sb.append("from (select f.staffname, f.headimage, m.sccid, count(p.ppid) e ");
		sb.append("from t_eshop_cuscom m inner join dt_hr_staff f on m.staffid = f.staffid and m.sccid = ? ");
		sb.append("left join dt_productpackaging p on m.sccid = p.sccid ");
		sb.append("and p.delstatus = ? and p.fiveclear = ? and p.type = ? ");
		sb.append("group by f.staffname, f.headimage, m.sccid) a ");
		sb.append("left join dtjoinfans s on a.sccid = s.fsccid and s.state= ? and s.zsccid<>? ");
		sb.append("group by a.staffname, a.headimage, a.sccid, a.e) b ");
		sb.append("left join dtjoinfans n on b.sccid = n.zsccid and n.state=? and n.fsccid<>? ");
		sb.append("group by b.staffname, b.headimage, b.sccid, b.e, b.g ");
		Object message = beandao.getObjectBySqlAndParams(sb.toString(),
				new Object[] { sccid, "00", "2", "公司论坛","00",sccid,"00",sccid });
 
		sb.setLength(0);
		// 查询我的社区
		sb.append("select y.ccompanyid,y.companyname,y.logopath ");
		sb.append("from t_eshop_cuscom m,dtjoinfans s,t_eshop_cuscom c,dt_ccom_com o,dtcontactcompany y ");
		sb.append("where m.sccid = ? and m.sccid = s.fsccid and s.zsccid = c.sccid and s.state=? ");
		sb.append("and c.companyid=o.compnay_id and o.ccompany_id=y.ccompanyid");

		List<BaseBean> community = beandao.getListBeanBySqlAndParams(
				sb.toString(), new Object[] { sccid,"00" });
		
		//查询是否关注
		String hql = "from JoinFans where fsccId=? and zsccId=?";
		JoinFans jf = (JoinFans) beandao.getBeanByHqlAndParams(hql,
				new Object[] {wsccid,sccid  });
		boolean bl = true;
		if(jf==null || !jf.getState().equals("00")){
			bl = false;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("community", community);
		map.put("bl", bl);
		return map;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询我的帖子
	 * @return 返回集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm myiNvitation(String sccid, int pageNumber, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append("select s.ppid,s.goodsid,s.goodsname,s.image,to_char(s.packagingdate,'YYYY-MM-DD HH24:MI:SS'),");
		sb.append("max(case s.type when '0' then s.a else 0 end) b,max(case s.type when '1' then s.a else 0 end) d,");
		sb.append("max(case s.type when '2' then s.a else 0 end) e from ");
		sb.append("(select p.ppid,p.goodsid,p.goodsname,p.image,p.packagingdate,t.type,count(t.type) a from ");
		sb.append("dt_productpackaging p left join dt_ProductComment t on p.ppid=t.ppid ");
		sb.append("where p.sccid=? and p.delstatus=? and p.type=? ");
		sb.append("group by p.ppid,p.goodsid,p.goodsname,p.image,p.packagingdate,t.type) s ");
		sb.append("group by s.ppid,s.goodsid,s.goodsname,s.image,s.packagingdate order by s.packagingdate desc");
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
				sb.toString(), "select count(*) from (" + sb.toString() + ")",
				new Object[] { sccid, "00", "公司论坛" });
		return pageForm;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询关注
	 * @return 返回集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm myAttention(String sccid, int pageNumber, int pageSize,
			String community) {
		StringBuilder sb = new StringBuilder();
		Object[] obj = null;
		if (community.equals("00")) {
			obj = new Object[] { "00",sccid,sccid, "公司论坛", "00",sccid };
		} else if (community.equals("01")) {
			TEshopCusCom cuscom = queryUser();
			obj = new Object[] { "00",sccid,cuscom.getSccId(), "公司论坛", "00", cuscom.getSccId() };
		}
		sb.append("select d.sccid, d.staffname, d.headimage, d.b,d.jfid,t.state from ( ");
		sb.append("select a.sccid, a.staffname, a.headimage,a.b, count(n.jfid) jfid ");
		sb.append("from (select m.sccid, f.staffname, f.headimage,count(p.ppid) b ");
		sb.append("from dtjoinfans s inner join t_eshop_cuscom m on s.zsccid = m.sccid and s.state=? and s.fsccid = ? and m.sccid <> ?");
		sb.append("left join dt_hr_staff f on m.staffid = f.staffid ");
		sb.append("left join dt_productpackaging p on m.sccid=p.sccid ");
		sb.append("and p.type=? and p.delstatus=? group by m.sccid, f.staffname, f.headimage) a ");
		sb.append("left join dtjoinfans n on a.sccid = n.zsccid ");
		sb.append("group by a.sccid, a.staffname, a.headimage,a.b ");
		sb.append(") d left join dtjoinfans t on d.sccid=t.zsccid and t.fsccid=? ");
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
				sb.toString(), "select count(*) from (" + sb.toString() + ")",
				obj);
		return pageForm;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询粉丝
	 * @return 返回集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm myFans(String sccid, int pageNumber, int pageSize,String community) {
		StringBuilder sb = new StringBuilder();
		sb.append("select d.sccid, d.staffname, d.headimage, d.b, d.jfid, t.state ");
		sb.append("from (select a.sccid, a.staffname, a.headimage, a.b, count(n.jfid) jfid ");
		sb.append("from (select m.sccid, f.staffname, f.headimage, count(p.ppid) b ");
		sb.append("from dtjoinfans s inner join t_eshop_cuscom m on s.fsccid = m.sccid and s.zsccid = ? and s.state = ? and m.sccid <> ?");
		sb.append("inner join dt_hr_staff f on m.staffid = f.staffid left join dt_productpackaging p on m.sccid = p.sccid ");
		sb.append("and p.type = ? and p.delstatus = ? group by m.sccid, f.staffname, f.headimage) a ");
		sb.append("left join dtjoinfans n on a.sccid = n.zsccid and n.state = ? ");
		sb.append("group by a.sccid, a.staffname, a.headimage, a.b) d  ");
		sb.append("left join dtjoinfans t on d.sccid = t.zsccid and t.fsccid = ? ");
		Object[] obj = null;
		if (community.equals("00")) {
			obj = new Object[] { sccid,"00",sccid, "公司论坛", "00","00", sccid };
		} else if (community.equals("01")) {
			TEshopCusCom cuscom = queryUser();
			obj = new Object[] { sccid,"00", cuscom.getSccId(),"公司论坛", "00","00", cuscom.getSccId() };
		}
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
				sb.toString(), "select count(*) from (" + sb.toString() + ")",
				obj);
		return pageForm;
	}

	/**
	 * @Title: 添加/删除
	 * @Description: 添加/删除关注
	 * @return 返回判断
	 */
	@Override
	@Transactional
	public Map<String, Object> shift(String sccId) {
		TEshopCusCom cuscom = queryUser();
		boolean userExist;
		if (cuscom == null) {
			userExist = false;
		} else {
			userExist = true;
			String hql = "from JoinFans where fsccId=? and zsccId=?";
			JoinFans jf = (JoinFans) beandao.getBeanByHqlAndParams(hql,
					new Object[] { cuscom.getSccId(), sccId });
			if (jf == null) {
				jf = new JoinFans();
				TEshopCusCom cc = (TEshopCusCom) beandao.getBeanByHqlAndParams(
						"from TEshopCusCom where sccId=?", new Object[] { sccId });
				jf.setJfID(serverService.getServerID("jf"));
				jf.setState("00");
				jf.setSource("关注");
				jf.setZaccount(cc.getAccount());
				jf.setFaccount(cuscom.getAccount());
				jf.setZsccId(cc.getSccId());
				jf.setFsccId(cuscom.getSccId());
				jf.setStaffid(cc.getStaffid());
				jf.setFansDate(new Date());
			} else {
				if(jf.getState().equals("00")){
					jf.setState("01");
				}else{
					jf.setState("00");
				}
			}
			beandao.saveOrUpdate(jf);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userExist", userExist);
		return map;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询我的回复
	 * @return 返回集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object replyParticulars(String pcID) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.pcid,t.staffid,t.content,t.whichFloor,to_char(t.commentdate,'YYYY-MM-DD HH24:MI:SS'),t.ppid ");
		sb.append("from dt_productcomment t ");
		sb.append("start wITh  t.pcid=? ");
		sb.append("connect by  prior  t.pcpid = t.pcid order by t.commentdate ");
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{pcID});
		Object obj = list.get(0);
		Object[] obj1 = (Object[]) obj; 
		Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{obj1[1]});
		List<Object> list1 = new ArrayList<Object>();
		for (int i = 0; i < obj1.length; i++) {
			list1.add(obj1[i]);
		}
		list1.add(staff.getStaffName());
		list1.add(staff.getHeadimage());
		return list1;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询帖子的回复
	 * @return 返回集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm invitationReply(String pcID,int pageNumber, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.pcid,s.staffname sname,f.staffname fname,a.content,to_char(a.commentdate,'YYYY-MM-DD HH24:MI:SS'),a.staffid ");
		sb.append("from (select t.pcid,t.staffid,t.content,t.commentdate,t.pcpid,t.tostaffid from dt_productcomment t ");
		sb.append("start wITh t.pcid = ? connect by prior t.pcid = t.pcpid and t.type=? order by t.commentdate desc) a ");
		sb.append("left join dt_hr_staff s on  a.staffid = s.staffid left join dt_hr_staff f on a.tostaffid = f.staffid  where a.pcpid is not null ");
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize,
				sb.toString(), "select count(*) from (" + sb.toString() + ")",
				new Object[] {pcID,"0"});
		return pageForm;
	}

	/**
	 * @Title: 删除
	 * @Description: 删除二级回复
	 * @return 返回判断
	 */
	@Override
	@Transactional
	public boolean delReply(String pcID) {
		
		try {
			beandao.saveBeansListAndexecuteSqlsByParams(null, new String[]{"delete from dt_productcomment where pcid=?"}, new Object[]{pcID});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: 删除
	 * @Description: 删除一级回复
	 * @return 返回判断
	 */
	@Override
	@Transactional
	public boolean delMyReply(String pcID) {
		String sql = "delete from dt_productcomment where pcid=? or pcpid=?";
		try {
			beandao.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{pcID,pcID});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: 添加
	 * @Description: 添加用户回复
	 * @return 返回判断
	 */
	@Override
	@Transactional
	public Map<String, Object> userReply(TEshopCusCom cuscom, ProductComment pct) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bl = true;
		ProductComment pd = null;
		ProductComment pc = (ProductComment) beandao.getBeanByHqlAndParams("from ProductComment where pcID=?", new Object[]{pct.getPcID()});
		if(pc!=null){
			try {
				pd = new ProductComment();
				pd.setPcID(serverService.getServerID("pct"));
				pd.setPpid(pc.getPpid());
				pd.setGoodsId(pc.getGoodsId());
				pd.setStaffId(cuscom.getStaffid());
				pd.setContent(pct.getContent());
				pd.setPraise(0);
				pd.setCommentdate(new Date());
				pd.setCount(0);
				pd.setPcPID(pc.getPcID());
				pd.setType("0");
				pd.setIspraise("0");
				pd.setIscollect("0");
				pd.setIsread("0");
				pd.setToStaffId(pc.getStaffId());
				beandao.save(pd);
			} catch (Exception e) {
				bl = false;
			}
		}
		map.put("userReply", bl);
		map.put("pct", pd);
		return map;
	}
}
