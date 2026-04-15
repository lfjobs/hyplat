package hy.ea.BuildPlatform.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.BuildPlatform.service.WfjIndustryPlatfromService;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.human.StaffPlatform;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
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
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WfjIndustryPlatfromServiceImpl
  implements WfjIndustryPlatfromService
{
  @Resource
  private BaseBeanService baseBeanService;
  @Resource
  private WfjJifenService wfjJifenService;
  @Resource
  private UpLoadFileService fileService;
  @Resource
  private ServerService serverService;
  @Autowired
  private MobileMessage msage;
  private Logger log = LoggerFactory.getLogger(WfjIndustryPlatfromServiceImpl.class);
  
  public String getQueryPlatfrom(String type, String ppid)
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    
    StringBuilder sql = new StringBuilder();
    sql.append("select dt.parentId,dt.ppid,dt.goodsName from dt_ProductPackaging dt where dt.parentid=?");
    List<BaseBean> list = this.baseBeanService.getListBeanBySqlAndParams(
      sql.toString(), new Object[] { ppid });
    request.setAttribute("list", list);
    
    String typeLe = null;
    if ("01".equals(type)) {
      typeLe = "01";
    } else if ("02".equals(type)) {
      typeLe = "02";
    } else if ("03".equals(type)) {
      typeLe = "03";
    } else if ("04".equals(type)) {
      typeLe = "04";
    }
    return typeLe;
  }
  
  public PageForm getQueryAjax(String ppid, String content, PageForm pageForm, String param)
  {
    StringBuilder sql = new StringBuilder();
    sql.append("select dt.parentId,dt.ppid,dt.goodsName from dt_ProductPackaging dt where ");
    List<Object> parms = new ArrayList();
    if ((content != null) && (content != ""))
    {
      sql.append(" dt.goodsName like ? and  dt.brand=?");
      parms.add("%" + content + "%");
      parms.add("中国");
    }
    else if ((param != null) && (!param.equals("")))
    {
      sql.append(" dt.brand=? and dt.standard = ?");
      parms.add("中国");
      parms.add(param);
    }
    else
    {
      sql.append(" dt.parentId = ?");
      parms.add(ppid);
    }
    pageForm = this.baseBeanService.getPageFormBySQL(
      pageForm != null ? pageForm.getPageNumber() : 1, 9, 
      sql.toString(), 
      "select count(*) from (" + sql.toString() + ")", 
      parms.toArray());
    
    return pageForm;
  }
  
  public List<BaseBean> getQueryIndustry(String codePID)
  {
    String hql = "from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";
    if ((codePID == null) || (codePID.equals(""))) {
      codePID = "scode20150815wygb79q82p0000000005";
    }
    Object[] params = { "company201009046vxdyzy4wg0000000025", codePID };
    List<BaseBean> industryList = this.baseBeanService.getListBeanByHqlAndParams(hql, params);
    
    return industryList;
  }
  
  public void getAdd(String ccmomtype, Company company, CDetail cdl, int typeNumber, String path, File pictureList, String pictureListFileName, String ppidUser)
  {
    HttpSession session = ServletActionContext.getRequest().getSession();
    
    String sccid = (String)session.getAttribute("sccid");

    if(sccid==null||"".equals(sccid)){
      TEshopCusCom tEshopCusCom = (TEshopCusCom)session.getAttribute("key_shop_cus_com");
      sccid = tEshopCusCom.getSccId();
    }
    
    StringBuilder hql2 = new StringBuilder();
    hql2.append(" from TEshopCusCom where sccid=?");
    this.log.error("====sccid" + sccid);
    TEshopCusCom tEshopCusCom = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams(hql2.toString(), new Object[] { sccid });
    if (pictureList != null)
    {
      String photopath = this.fileService.savePhoto(
        path, 
        pictureListFileName, 
        pictureList, 
        "", 
        "/logo/" + 
        Utilities.getDateString(new Date(), 
        "yyyy-MM-dd"));
      
      cdl.setLogo(photopath);
    }
    this.wfjJifenService.registerCompanyInfoZps("0", tEshopCusCom, company, cdl, ppidUser);
    this.log.error("====" + tEshopCusCom);
    
    pushMessage(tEshopCusCom, ccmomtype);
  }
  
  private void pushMessage(TEshopCusCom cus, String goodsname)
  {
    try
    {
      this.msage.setMobiles(cus.getAccount());
      this.msage.setMessage("恭喜您已成功创建" + goodsname + "\n组织机构名：" + goodsname + "\n账号：sa  \n密码： 123456");
      this.msage.sendMsg("【微分金平台】");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public String Verification(String ppid)
  {
    String hql = "from TEshopCusCom where ppid = ? and cusType = ?";
    TEshopCusCom tEshopCusCom = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams(hql.toString(), new Object[] { ppid, "0" });
    String typeLe = "02";
    if (tEshopCusCom != null) {
      typeLe = "01";
    }
    return typeLe;
  }
  
  public Map<String, Object> getQuery(String cumname)
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    Map<String, Object> map = new HashMap();
    HttpSession session = request.getSession();
    SessionWrap sw = SessionWrap.getInstance();
    String sccid = (String)session.getAttribute("sccid");
    StringBuilder sql = new StringBuilder();
    StringBuilder hql = new StringBuilder();
    StringBuilder hql2 = new StringBuilder();
    hql.append(" from TEshopCusCom where sccId=?");
    List<Object> parms = new ArrayList();
    TEshopCusCom CusCom = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams(hql.toString(), new Object[] { sccid });
    sw.setObject(session, "key_shop_cus_com", CusCom);
    hql2.append(" from TEshopCustomer where staffid=?");
    TEshopCustomer customer = (TEshopCustomer)this.baseBeanService.getBeanByHqlAndParams(hql2.toString(), new Object[] { CusCom.getStaffid() });
    sw.setObject(session, "key_customer", customer);
    sql.append("  select ctc.logopath, ca.pseudo_company_name, com.ccompany_id,ca.companyid,dt.goodsname from t_eshop_cuscom ca,");
    sql.append(" dt_ccom_com com,dt_ProductPackaging dt,dtcontactcompany ctc");
    sql.append(" where ca.companyid=com.compnay_id ");
    sql.append(" and ca.staffid = ? and ca.ppid=dt.ppid and ctc.ccompanyid=com.ccompany_id");
    parms.add(CusCom.getStaffid());
    if ((cumname != null) && (cumname != ""))
    {
      sql.append(" and ca.pseudo_company_name like ? ");
      parms.add("%" + cumname + "%");
    }
    List<BaseBean> lists = this.baseBeanService.getListBeanBySqlAndParams(sql.toString(), parms.toArray());
    if ((lists != null) && (lists.size() > 0)) {
      map.put("lists", lists);
    }
    StringBuilder sqls = new StringBuilder();
    List<Object> parmss = new ArrayList();
    
    sqls.append(" select w.company, w.zsccid,cc.companyid,yy.logopath,yy.companyname,yy.ccompanyid");
    sqls.append(" from dtjoinfans w, t_eshop_cuscom cc,dt_ccom_com ccn,Dtcontactcompany yy");
    sqls.append(" where cc.sccid = w.zsccid and ccn.compnay_id=cc.companyid and yy.ccompanyid=ccn.ccompany_id");
    sqls.append(" and w.fsccid = ? and w.state = ? ");
    parmss.add(CusCom.getSccId());
    parmss.add("00");
    List<BaseBean> list = this.baseBeanService.getListBeanBySqlAndParams(sqls.toString(), parmss.toArray());
    if ((list != null) && (list.size() > 0)) {
      map.put("list", list);
    }
    return map;
  }
  
  public String VerificationCompany(String company)
  {
    StringBuilder hql = new StringBuilder();
    hql.append(" from Company where companyIdentifier=?");
    Company com = (Company)this.baseBeanService.getBeanByHqlAndParams(hql.toString(), new Object[] { company });
    String typeLe = "02";
    if (com != null) {
      if (company.equals(com.getCompanyIdentifier())) {
        typeLe = "01";
      }
    }
    return typeLe;
  }
  
  public PageForm getQueryCompany(int pageNumber, String name,String ssid)
  {
    StringBuilder sql = new StringBuilder();
    List<Object> parms = new ArrayList();
    sql.append(" select a.companyname, a.logopath, a.sccid, s.state, a.ccompanyid");
    
    sql.append(" from (select y.logopath, y.companyname, t.sccid, y.ccompanyid");
    sql.append(" from dt_ccom_com m, t_eshop_cuscom t, Dtcontactcompany y");
    sql.append(" where m.compnay_id = t.companyid");
    sql.append(" and m.ccompany_id = y.ccompanyid");
    if ((name != null) && (name != ""))
    {
      sql.append(" and y.companyname like ? ");
      parms.add("%" + name + "%");
    }
    sql.append(" ) a left join dtjoinfans s on a.sccid = s.zsccid");
    //sql.append(" and s.fsccid = '32EE426C1E404EBA88D148D62F40356C'");
    sql.append(" and s.fsccid = ?");
    parms.add(ssid);
    PageForm pagefrom = this.baseBeanService.getPageFormBySQL(pageNumber, 10, sql.toString(), 
            "select count(*) from (" + sql.toString() + ")", parms.toArray());
          return pagefrom;
          
    /*if ((name != null) && (name != ""))
    {
      PageForm pagefrom = this.baseBeanService.getPageFormBySQL(pageNumber, 10, sql.toString(), 
        "select count(*) from (" + sql.toString() + ")", parms.toArray());
      return pagefrom;
    }else{
    	PageForm pagefrom = this.baseBeanService.getPageFormBySQL(pageNumber, 10, sql.toString(), 
    			"select count(*) from (" + sql.toString() + ")", null);
    	return pagefrom;
    }*/
  }

  @Override
  public List<BaseBean> findPlatForm(String staffID,String platFromName){
    StringBuilder sql = new StringBuilder();
    List<Object> parms = new ArrayList();
    sql.append("select ctc.logopath, ca.pseudo_company_name, com.ccompany_id,ca.companyid,dt.goodsname from t_eshop_cuscom ca,");
    sql.append(" dt_ccom_com com,dt_ProductPackaging dt,dtcontactcompany ctc");
    sql.append(" where ca.companyid=com.compnay_id ");
    sql.append(" and ctc.ccompanyid not in (select sp.ccompanyid from dt_staffplatform sp where sp.staffid = ?)");
    sql.append(" and ca.ppid=dt.ppid and ctc.ccompanyid=com.ccompany_id");
    parms.add(staffID);
    if(platFromName!=null && !"".equals(platFromName)){
      sql.append(" and (ca.pseudo_company_name like ? or dt.goodsname like ?)");
      parms.add("%"+platFromName+"%");
      parms.add("%"+platFromName+"%");
    }
    List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(),parms.toArray());
    return list;
  }

  @Override
  public void addOrDelPlatForm(String staffID, String ccompanyID, String platformID,String flag) {
    List<BaseBean> beans = new ArrayList<BaseBean>();
    List<Object[]> list = new ArrayList<Object[]>();
    if("del".equals(flag)){
      String sql = "delete from dt_staffplatform sp where sp.staffid = ? and sp.ccompanyid = ?";
      list.add(new Object[]{staffID,ccompanyID});
      baseBeanService.executeSqlsByParmsList(null, new String[] {sql }, list);
    }else if("add".equals(flag)){
      StaffPlatform staffPlatform  = new StaffPlatform();
      staffPlatform.setPlatformID(serverService.getServerID("staffPlatform"));
      staffPlatform.setStaffID(staffID);
      staffPlatform.setCcompanyID(ccompanyID);
      beans.add(staffPlatform);
      baseBeanService.executeSqlsByParmsList(beans, new String[] {null },null );
    }
  }

  @Override
  public List<BaseBean> platformByStaff(String staffID) {
    String sql = "select ctc.logopath, ca.pseudo_company_name, com.ccompany_id,ca.companyid,dt.goodsname,sp.platformid from t_eshop_cuscom ca,";
    sql +=" dt_ccom_com com,dt_ProductPackaging dt,dtcontactcompany ctc,dt_staffplatform sp";
    sql +=" where  ca.companyid=com.compnay_id";
    sql +=" and ctc.ccompanyid  = sp.ccompanyid";
    sql +=" and sp.staffid = ?";
    sql +=" and ca.ppid=dt.ppid and ctc.ccompanyid=com.ccompany_id";
    List<BaseBean> plat = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{staffID});
    return plat;
  }
}
