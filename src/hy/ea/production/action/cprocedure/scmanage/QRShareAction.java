package hy.ea.production.action.cprocedure.scmanage;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.PosDevice;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WorkMessageService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.sft.ApplyParam;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpCompany;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.*;
import hy.ea.bo.production.GoodFunction;
import hy.ea.bo.production.scmanage.MaterialContent;
import hy.ea.bo.production.scmanage.MaterialGroup;
import hy.ea.bo.production.scmanage.MaterialMusic;
import hy.ea.office.service.CarManageService;
import hy.ea.production.service.CarSchoolService;
import hy.ea.production.service.MaterialManageService;
import hy.ea.service.MerchanetsRegisService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.Constant;
import hy.ea.util.FileUtil;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.ea.websitemall.card.service.QRCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.AuditRecord;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分享
 */
@Controller
@Scope("prototype")
public class QRShareAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private WorkMessageService workmService;
    @Resource
    private MaterialManageService scManageService;
    @Resource
    private UploadContentToFileService contentToFileService;
    private ProductPackaging productPackaging;

    private ApplyParam applyParam;
    @Resource
    private MerchanetsRegisService merchanetsRegisService;
    private PageForm pageForm;
    private String result;
    private int pageNumber;
    private MaterialGroup materialGroup;
    private MaterialContent materialContent;
    private String content;
    private String flag;
    private String companyID;
    private String staffID;

    private CarManageAudit  carManageAudit;

    // xgb
    @Resource
    private CarSchoolService csService;
    @Resource
    private QRCodeService qrCodeService;
    @Resource
    private CarManageService cmService;
    private Object obj;
    private String sccid;
    private GoodFunction goodFunction;
    private ContactCompany concom;
    private TEshopCusCom cuscom;
    private Logger logger = LoggerFactory.getLogger(QRShareAction.class);
    private String judge;// 判断分享页面跳转
    private String musicName;// 音乐名称/hash
    private MaterialMusic materialMusic;// 音乐记录
    private File picture; // 图片/视频
    private String pictureName;// 图片/视频名称
    private String skipJudge;//判断是从详情页01进入还是从列表页00进入
    private TbJpCompany tcompany; //驾校公司

    private String backtype;//判断返回 01返回中联园资讯列表  02返回公司新闻列表


    private String miniSystemJudge;//00:简介,01:文化,02:新闻,03:分享,

    private String androidJudge;//判断是否是从安卓进入的,00:安卓,01:共建平台,02:


    private CAccount caccount;
    private ContactCompany contactCompany;//往来单位
    private Certificate certificate;//单位证书管理
    private String auditSkip; //00:小系统进入,01:审批进入
    private String sourcePage; //00:银行卡页面进入,01:超级管理页面进入
    private AuditRecord auditRecord;//审批表

    private List<BaseBean> list;

    private CarManage carManage;//车辆管理表
    private VenueInformation vf;//场地信息表
    private String carmID;

    /**
     * 分享人信息
     *
     * @return
     */
    public String qrshareList() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (miniSystemJudge.equals("00") || miniSystemJudge.equals("01") || miniSystemJudge.equals("02")) {
            caccount = goCaccount();
            if (caccount == null) {
                return mobileLogin();
            }
            return "sharelist";
        } else if (miniSystemJudge.equals("03") || miniSystemJudge.equals("04")) {
            TEshopCusCom cuscom1 = null;
            if (androidJudge != null && androidJudge.equals("00")) {
                cuscom1 = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{cuscom.getSccId()});
            } else {
                if (androidJudge != null && androidJudge.equals("01")) {
                    session.setAttribute("androidJudge", "zzl");
                } else if (androidJudge != null && androidJudge.equals("02")) {
                    session.setAttribute("androidJudge", "xgb");
                }
                cuscom1 = csService.queryUser();
                if (cuscom1 == null) {
                    return goBackLogin();
                }
            }

            logger.error(cuscom1 == null ? "cuscom为空" : cuscom1.getSccId());
            obj = scManageService.qrshareList(cuscom1);

        }
        return "sharelist";
    }

    /**
     * 分享列表
     *
     * @return
     */
    public String memberShare() {
        pageForm = scManageService.memberShare(pageNumber, 8, sccid,companyID);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /***
     *
     * 添加编辑
     *
     * @return
     */
    public String qrshareEdit() {
        if (miniSystemJudge.equals("03") || miniSystemJudge.equals("04")) {
            cuscom = csService.queryUser();
            if (cuscom == null) {
                return goBackLogin();
            }
        } else if (miniSystemJudge.equals("00") || miniSystemJudge.equals("01") || miniSystemJudge.equals("02")) {
            caccount = goCaccount();
            if (caccount == null) {
                return mobileLogin();
            }
        }
        return "shareedit";
    }

    /**
     * 保存修改分享
     *
     * @return
     */
    public String saveShare() {
        productPackaging = scManageService.saveQRShare(productPackaging,
                content, miniSystemJudge);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ppk", productPackaging);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }
    
	/**
	 * 
	 * 处理视频数据
	 * @return
	 */
	public String dealVideoData(){
		
		scManageService.dealVideo();
	 	
	 	return "success";
		
	}
	

    /**
     * 分享预览详情
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String qrshareDetail() {
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getContextPath();
        String basePath = re.getScheme() + "://" + re.getServerName() + ":"
                + re.getServerPort() + path + "/";
        String hql = "from GoodFunction where goodsid = ?";
        goodFunction = (GoodFunction) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[]{productPackaging.getGoodsID()});
        try {
            String path1 = ServletActionContext.getServletContext()
                    .getRealPath("\\");
            content = contentToFileService.getContent(path1
                    + goodFunction.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 查询产品
        productPackaging = (ProductPackaging) baseBeanService
                .getBeanByHqlAndParams("from ProductPackaging where ppID = ?",
                        new Object[]{productPackaging.getPpID()});
        //如果产品没有sccid则添加发布人sccid
        if (productPackaging.getSccid() == null || productPackaging.getSccid().equals("")) {
            String sql = "select sccid from t_eshop_cuscom where staffid=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ";
            List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{productPackaging.getStaffID()});
            //如果发布人没有账号则添加公司账号的sccid
            if (list == null || list.size() == 0) {
                list = baseBeanService.getListBeanBySqlAndParams("select sccid from t_eshop_cuscom where companyId=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ", new Object[]{productPackaging.getCompanyID()});
            }
            Object o = list.get(0);
            productPackaging.setSccid((String) o);
        }

        if (miniSystemJudge.equals("00") || miniSystemJudge.equals("01") || miniSystemJudge.equals("02")) {
            // 判断是否是本公司人员登陆
            CAccount cacc = goCaccount();
            if (cacc != null) {
                if (cacc.getCompanyID().equals(productPackaging.getCompanyID())) {
                    judge = "02";
                }
            }
            cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
                    "from TEshopCusCom t where t.companyId=?",
                    new Object[]{productPackaging.getCompanyID()});
        } else if (miniSystemJudge.equals("03") || miniSystemJudge.equals("04")) {
            // 判断是否是自己登陆
            TEshopCusCom cuscom1 = csService.queryUser();
            if (cuscom1 != null) {
                if (cuscom1.getSccId().equals(productPackaging.getSccid())) {
                    judge = "02";
                }
            }
            // 个人会员二维码
            cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
                    "from TEshopCusCom t where t.sccId=?",
                    new Object[]{productPackaging.getSccid()});
        }
        if (cuscom.getQrcodePath() == null || cuscom.getQrcodePath().equals("")) {
            String logo = "images/ea/production/head2x.png";
            if (cuscom.getState().equals("1")) {
                Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                        "from Staff f where f.staffID = ?",
                        new Object[]{cuscom.getStaffid()});
                if (staff.getHeadimage() != null
                        && !staff.getHeadimage().equals("")) {
                    logo = staff.getHeadimage();
                }
            } else if (cuscom.getState().equals("2")) {
                ContactCompany ctc = (ContactCompany) baseBeanService
                        .getBeanByHqlAndParams(
                                "select y from CcomCom c,ContactCompany y where c.comanyId = ? and c.ccompanyId = y.ccompanyID",
                                new Object[]{cuscom.getCompanyId()});
                if (ctc.getLogoPath() != null && !ctc.getLogoPath().equals("")) {
                    logo = ctc.getLogoPath();
                }
            }
            String qrcodePath = qrCodeService.createQRCode(basePath
                            + "ea/wfjshop/ea_getjspzc.jspa?sccid=" + cuscom.getSccId(),
                    "png", "TEshopCusCom", logo);
            cuscom.setQrcodePath(qrcodePath);
            List<BaseBean> list = new ArrayList<BaseBean>();
            list.add(cuscom);
            baseBeanService.executeHqlsByParamsList(list, null, null);
        }

        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("select y from CcomCom m,ContactCompany y where m.comanyId=? and m.ccompanyId=y.ccompanyID", new Object[]{productPackaging.getCompanyID()});

        // 查询公司公众二维码
        concom = (ContactCompany) baseBeanService.getBeanByHqlAndParams(
                "from ContactCompany where ccompanyID = ?",
                new Object[]{"contactCompany20101230UB4U5884S30000000176"});
        String name = productPackaging.getStaffName();
        if (productPackaging.getCompanyID() != null&&!productPackaging.getCompanyID().equals("")) {
            name = contactCompany.getCompanyName();
        }
        HttpServletRequest req = ServletActionContext.getRequest();
        req.setAttribute("name", name);


        return "sharedetail";
    }

    /**
     * 获取素材包括图片或者视频
     *
     * @return
     */
    public String ajaxfindSclist() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String fileType = request.getParameter("fileType");
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        List<Object> params = new ArrayList<Object>();

        String hqlgroup = "from MaterialGroup where updateStaffID = ? and fileType = ?";

        params.add(cus.getStaffid());
        params.add(fileType);

        List<BaseBean> grouplist = baseBeanService.getListBeanByHqlAndParams(
                hqlgroup, params.toArray());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("grouplist", grouplist);
        int total = 0;
        for (int i = 0; i < grouplist.size(); i++) {
            MaterialGroup mg = (MaterialGroup) grouplist.get(i);
            total = total + Integer.parseInt(mg.getFilenum());

        }
        map.put("total", total);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 根据分组获取文件
     *
     * @return
     */
    public String findFilelist() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        List<Object> params = new ArrayList<Object>();

        String hqlcontent = "from MaterialContent where groupID like ? and fileType = ? and staffID = ?";
        params.add("%" + materialContent.getGroupID() + "%");
        params.add(materialContent.getFileType());
        params.add(cus.getStaffid());

        hqlcontent += " order by createDate desc ";
        pageForm = baseBeanService.getPageForm(
                (null != pageForm ? pageForm.getPageNumber() : 1), 15,
                hqlcontent, params.toArray());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    public String goBackLogin() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getHeader("Referer");
        session.setAttribute("url", url);
        return "login";
    }

    public String mobileLogin() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getHeader("Referer");
        session.setAttribute("url", url);
        return "login";
    }


    public CAccount goCaccount() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount cacc = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        if(cacc==null){
            Map<String, Object> session1 = ActionContext.getContext().getSession();
            cacc = (CAccount) session1.get("account");
        }

        return cacc;
    }


    /**
     * 新建分组
     *
     * @return
     */
    public String saveGroup() {

        HttpSession session = ServletActionContext.getRequest().getSession();

        SessionWrap sw = SessionWrap.getInstance();

        Staff staff = (Staff) sw.getObject(session, SessionWrap.KEY_STAFF);
        String companyID = scManageService
                .getStaffCompanyID(staff.getStaffID());

        materialGroup.setMgId(serverService.getServerID("mgId"));
        materialGroup.setCreateDate(new Date());
        materialGroup.setUpdateDate(new Date());
        materialGroup.setCompanyID(companyID);
        materialGroup.setGroupname(materialGroup.getGroupname());
        materialGroup.setGroupdesc(materialGroup.getGroupdesc());
        materialGroup
                .setGroupcover("/images/ea/production/scmanage/nocontent2x.png");
        materialGroup.setFileType("00");
        materialGroup.setState("00");
        materialGroup.setCreateStaffID(staff.getStaffID());
        materialGroup.setUpdateStaffID(staff.getStaffID());
        materialGroup.setFilenum("0");
        baseBeanService.save(materialGroup);

        return "success";

    }

    /**
     * 保存名称描述等
     *
     * @return
     */
    public String saveContent() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        Staff staff = (Staff) sw.getObject(session, SessionWrap.KEY_STAFF);
        scManageService.saveContent(materialContent, staff.getStaffID());

        return "success";

    }

    // xgb

    /**
     * 查询产品(自己下的产品优先)
     *
     * @return
     */
    public String inquireProduct() {
        pageForm = scManageService.inquireProduct(pageNumber, 10,
                productPackaging.getGoodsName(), cuscom.getStaffid());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 删除项目
     *
     * @return
     */
    public String deleteProject() {

        scManageService.deleteProject(productPackaging.getPpID(),
                goodFunction.getGoodsid(), pictureName);
        if (miniSystemJudge.equals("04")) {
            return "delete";
        } else {
//            HttpServletRequest request = ServletActionContext.getRequest();
//            HttpServletResponse response = ServletActionContext.getResponse();
//            String ccomIDPlatform = request.getParameter("ccomIDPlatform");
//            if(ccomIDPlatform!=null&&!ccomIDPlatform.equals("")){
//              return "sbqindex";
//            }
            return qrshareList();

        }
    }

    /**
     * 修改默认登录账号
     *
     * @return
     */
    public String updateLogin() {
        TEshopCusCom tcc = csService.queryUser();
        if (tcc == null) {
            return goBackLogin();
        }
        scManageService.updateLogin(sccid, tcc);

        return qrshareList();
    }

    /**
     * 删除临时保存图片
     *
     * @return
     */
    public String delTemporarySavePicture() {
        scManageService.delTemporarySavePicture(materialContent.getMcId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("true", true);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 查询音乐
     *
     * @return
     */
    public String defaultMusic() {
        // 查询历史音乐
        List<BaseBean> hmsc = scManageService.historyMusic(cuscom.getStaffid());
        // 查询默认音乐
        List<BaseBean> mmsc = scManageService.defaultMusic();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hmsc", hmsc);
        map.put("mmsc", mmsc);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 查询音乐列表
     */
    public String musicList() {
        String httpUrl = Constant.MUSIC_LIST_URL;
        String httpArg = "s=" + musicName + "&size=10&page=" + pageNumber;
        result = scManageService.request(httpUrl, httpArg);

        return "success";
    }

    /**
     * 查询音乐路径
     */
    public String musicPath() {
        String httpUrl = Constant.MUSIC_PATH_URL;
        String httpArg = "hash=" + musicName;
        result = scManageService.request(httpUrl, httpArg);

        return "success";
    }

    /**
     * 添加历史音乐
     */
    public String addRecord() {
        boolean Record = scManageService.addRecord(cuscom.getStaffid(),
                materialMusic);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addRecord", Record);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     * 新版添加图片视频
     */
    public String addMaterial() {
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getSession().getServletContext().getRealPath("/");
        Map<String, Object> map = new HashMap<String, Object>();
        pictureName = pictureName.toLowerCase();
        String share_path = scManageService.addMaterial(cuscom.getStaffid(),
                cuscom.getCompanyId(), picture, pictureName);
        String[] name = pictureName.split("\\.");
        if (!name[name.length - 1].equals("jpg") && !name[name.length - 1].equals("gif") && !name[name.length - 1].equals("jpeg") && !name[name.length - 1].equals("png")) {
            String img_path = share_path.substring(0, share_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg";
            String veido = share_path.substring(0, share_path.lastIndexOf(".")).replaceFirst("vedio", "file") + "veido.mp4";
            scManageService.transitionVeido(share_path, img_path, veido);
            File file = new File(path + img_path);
            map.put("imgpath", "/images/ea/production/qrshare/notAvailable.png");
            map.put("sharepath", share_path);
            for (int i = 0; i < 10; i++) {
                if (file.exists()) {
                    map.put("imgpath", img_path);

                    break;
                }
                try {
                    Thread.sleep(1000);//毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            scManageService.delTransformVideo(share_path);
            map.put("sharepath", veido);
        } else {
            //截取缩略图
            String jjPath = share_path.split("\\.")[0] + "small1." + share_path.split("\\.")[1];
            ImageCut.systemScale(path + share_path, path + jjPath, 200, 200);
            //判断缩略图是否生成
            BufferedImage src = null;
            try {
                src = ImageIO.read(new File(path + jjPath));
            } catch (IOException e) {
                logger.error("ios缩略图生成失败-----------------------------" + path + jjPath);
            }
            if (src != null) {
                int width = src.getWidth();
                int height = src.getHeight();
                int x = 0;
                int y = 0;
                if (width > height) {
                    x = width / 2 / 2;
                } else {
                    if (height > 200) {
                        y = (int) (height / 2 * 0.3);
                    }
                }
                //裁剪缩略图
                String jjPath1 = share_path.split("\\.")[0] + "small." + share_path.split("\\.")[1];
                ImageCut.abscut(path + jjPath, path + jjPath1, x, y, 200, 200);
                //删除临时保存的缩略图
                FileUtil.delete(path + jjPath);
            }
            map.put("sharepath", share_path);
        }

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 新版删除
     */
    public String delTransformVideo() {

        scManageService.delTransformVideo(pictureName);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", true);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /*****************************************公司简介/文化/新闻*****************************************/

    /**
     * 新闻/简介/文化列表
     *
     * @return
     */
    public String miniList() {
        pageForm = scManageService.miniList(pageNumber, 8, cuscom.getCompanyId(), miniSystemJudge);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }



    public String queryState() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if ("00".equals(auditSkip)) {
            caccount = goCaccount();
            if (caccount == null) {
                return mobileLogin();
            }
        }else if ("01".equals(auditSkip)) {

             caccount = (CAccount) ActionContext.getContext().getSession()
                    .get("account");

        }else{

            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            caccount = new CAccount();
            caccount.setCompanyID(cus.getCompanyId());
        }
        session.setAttribute("auditSkip", auditSkip);
        contactCompany = scManageService.queryMessage(caccount);
        if("03".equals(contactCompany.getAuthState())){
           AuditRecord auditRecord =  merchanetsRegisService.queryAudit(contactCompany.getCcompanyID());
           HttpServletRequest request = ServletActionContext.getRequest();
           if(auditRecord!=null) {
               request.setAttribute("auditComment",auditRecord.getAuditComment());
           }
        }
        companyID = caccount.getCompanyID();
        staffID = caccount.getStaffID();
        applyParam =  merchanetsRegisService.getApplyInfo(companyID);
        if ("00".equals(contactCompany.getAuthState())&& StringUtils.isBlank(sourcePage)) {
            return "mbapply_type";
        }
        //5L5C不同的功能进来
        if ("00".equals(sourcePage)) {//银行卡管理
            return "bank_type";
        }else if ("01".equals(sourcePage)){//超级管理
            return "super_type";
        }
        return "queryState";
    }

    /**
     * 跳转公司信息页面
     *
     * @return
     */
    public String perfectionMessage() {
        if (auditSkip.equals("00")||auditSkip.equals("03")) {
            contactCompany = scManageService.queryMessage(caccount);

            certificate = scManageService.queryCertificate(caccount);
        } else if (auditSkip.equals("01")) {

            contactCompany = scManageService.queryMessage(caccount);

            certificate = scManageService.queryCertificate(caccount);

            auditRecord = scManageService.queryRecord(auditRecord.getAuid());

            list = workmService.findAudiPeople(auditRecord.getBatchNum());


        }

        return "perfectionMessage";
    }

    /**
     * 添加公司信息
     *
     * @return
     */

    public String addMessage() {

        JSONObject jo = null;
        boolean b = scManageService.addMessage(caccount, contactCompany, certificate);

    //    Company compa = (Company) baseBeanService.getBeanByHqlAndParams(" from Company where companyID = ?", new Object[]{caccount.getCompanyID()});

        if (contactCompany.getIndustryType()!=null&&contactCompany.getIndustryType().contains("驾校")) {
            TbJpCompany tbJpCompany = (TbJpCompany) baseBeanService.getBeanByHqlAndParams(" from TbJpCompany where companyId = ?", new Object[]{caccount.getCompanyID()});
            if (tbJpCompany == null) {
                tbJpCompany = new TbJpCompany();
                tbJpCompany.setTbjpcompanyId(serverService.getServerID("tcomid"));
                tbJpCompany.setCompanyId(caccount.getCompanyID());
                tbJpCompany.setName(contactCompany.getCompanyName());
                tbJpCompany.setShortname(tcompany.getShortname());
                tbJpCompany.setBusiness(tcompany.getBusiness()); //
                tbJpCompany.setLicnum(tcompany.getLicnum());//
                tbJpCompany.setLicetime(tcompany.getLicetime());
                tbJpCompany.setAddress(contactCompany.getCompanyAddr());
                tbJpCompany.setLegal(contactCompany.getRepresentative());
                tbJpCompany.setCoachnumber(tcompany.getCoachnumber());
                tbJpCompany.setPraticefield(tcompany.getPraticefield());
                tbJpCompany.setFirstIssueDate(tcompany.getFirstIssueDate());
                tbJpCompany.setCreditcode(tcompany.getCreditcode());
                tbJpCompany.setPostcode(tcompany.getPostcode());
                tbJpCompany.setBusistatus(tcompany.getBusistatus());
                tbJpCompany.setBusisEndDate(tcompany.getBusisEndDate());
                tbJpCompany.setCreatedate(new Date());
                tbJpCompany.setDistrict(tcompany.getDistrict());
                tbJpCompany.setUpdatedate(new Date());
                tbJpCompany.setCreateperson(contactCompany.getCompanyName());
                tbJpCompany.setUpdateperson(contactCompany.getCompanyName());
                tbJpCompany.setSyncXlgjStatus("1");
                tbJpCompany.setSyncXlycStatus("1");
                tbJpCompany.setBusiscope(tcompany.getBusiscope());
                tbJpCompany.setInscode(tcompany.getInscode());
                tbJpCompany.setPhone(tcompany.getPhone());
                baseBeanService.save(tbJpCompany);


            } else {
                tbJpCompany.setCompanyId(caccount.getCompanyID());
                tbJpCompany.setName(contactCompany.getCompanyName());
                tbJpCompany.setShortname(tcompany.getShortname());
                tbJpCompany.setBusiness(tcompany.getBusiness()); //
                tbJpCompany.setLicnum(tcompany.getLicnum());//
                tbJpCompany.setLicetime(tcompany.getLicetime());
                tbJpCompany.setAddress(contactCompany.getCompanyAddr());
                tbJpCompany.setLegal(contactCompany.getRepresentative());
                tbJpCompany.setCoachnumber(tcompany.getCoachnumber());
                tbJpCompany.setPraticefield(tcompany.getPraticefield());
                tbJpCompany.setFirstIssueDate(tcompany.getFirstIssueDate());
                tbJpCompany.setCreditcode(tcompany.getCreditcode());
                tbJpCompany.setPostcode(tcompany.getPostcode());
                tbJpCompany.setBusistatus(tcompany.getBusistatus());
                tbJpCompany.setBusisEndDate(tcompany.getBusisEndDate());
                tbJpCompany.setCreatedate(new Date());
                tbJpCompany.setDistrict(tcompany.getDistrict());
                tbJpCompany.setUpdatedate(new Date());
                tbJpCompany.setCreateperson(contactCompany.getCompanyName());
                tbJpCompany.setUpdateperson(contactCompany.getCompanyName());
                tbJpCompany.setSyncXlgjStatus("1");
                tbJpCompany.setSyncXlycStatus("1");
                tbJpCompany.setBusiscope(tcompany.getBusiscope());
                tbJpCompany.setInscode(tcompany.getInscode());
                tbJpCompany.setTaxregcer(tcompany.getTaxregcer());
                tbJpCompany.setPhone(tcompany.getPhone());

                baseBeanService.update(tbJpCompany);

            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 审核公司信息
     *
     * @return
     */
    public String submitAudit() {

        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");

       if(caccount==null){
           caccount = new CAccount();
           caccount.setCompanyID(account.getCompanyID());
       }
        scManageService.alterAudit(caccount.getCompanyID(), auditRecord.getState());
        boolean b = false;
        if (auditRecord.getState().equals("02")) {
            b = workmService.agreeMessage(auditRecord.getAuid(), auditRecord.getAuditComment(), contactCompany.getCcompanyID());

        } else if (auditRecord.getState().equals("03")) {
            b = workmService.rejectedMessage(auditRecord.getAuid(), auditRecord.getAuditComment(), contactCompany.getCcompanyID());
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 处理数据
     *
     * @return
     */
    public String thumbnail() {
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getSession().getServletContext().getRealPath("/");
        String hql = "from ProductPackaging where delStatus=? and type in(?,?,?,?,?)";
        List<BaseBean> lsit = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"00", "公司简介", "公司文化", "公司新闻", "会员分享", "新闻"});
        for (int i = 0; i < lsit.size(); i++) {
            ProductPackaging ppk = (ProductPackaging) lsit.get(i);
            if (ppk.getImage() != null && !ppk.getImage().equals("")) {
                //判断原路径下是否有图片true:进入,false:跳过
                String urlPath = ppk.getImage();
                File file = new File(path + urlPath);
                if (file.exists()) {
                    String jjPath = urlPath.split("\\.")[0] + "small." + urlPath.split("\\.")[1];
                    //判断小图路径下是否有图片true:进入创建,false:跳过
                    File file1 = new File(path + jjPath);
                    if (!file1.exists()) {
                        ImageCut.scale(path + urlPath, path + jjPath, 100, 100);
                    }
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "成功");
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public String androidScreenshot() {
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getSession().getServletContext().getRealPath("/");
        String[] name = pictureName.split(",");
        for (int i = 0; i < name.length; i++) {
            //截取缩略图
            String jjPath = name[i].split("\\.")[0] + "small1." + name[i].split("\\.")[1];
            ImageCut.systemScale(path + name[i], path + jjPath, 200, 200);
            //判断缩略图是否生成
            BufferedImage src = null; //
            try {
                src = ImageIO.read(new File(path + jjPath));
            } catch (IOException e) {
                logger.error("安卓缩略图生成失败-----------------------------" + path + jjPath);
            }
            if (src != null) {
                int width = src.getWidth();
                int height = src.getHeight();
                int x = 0;
                int y = 0;
                if (width > height) {
                    x = width / 2 / 2;
                } else {
                    if (height > 200) {
                        y = (int) (height / 2 * 0.3);
                    }
                }
                //裁剪缩略图
                String jjPath1 = name[i].split("\\.")[0] + "small." + name[i].split("\\.")[1];
                ImageCut.abscut(path + jjPath, path + jjPath1, x, y, 200, 200);
                //删除临时保存的缩略图
                FileUtil.delete(path + jjPath);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "成功");
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 车辆管理列表跳转
     *
     * @return
     */
    public String jumpManagement() {
   	    HttpServletRequest request = ServletActionContext.getRequest();
   	    String posNum = request.getParameter("posNum");
   	    if(posNum!=null&&!posNum.equals("")){
   	    	String companyId = cmService.getCompanyByPosNum(posNum);
   	    	if(companyId!=null&&!companyId.equals("")){
   	    	  caccount = new CAccount();
              caccount.setCompanyID(companyId);
   	    	}
   	    }else{
            caccount = goCaccount();
   	    }
        if (caccount == null) {
            return mobileLogin();
        }
        return "jumpManagement";
    }

    /**
     * 车辆管理详情跳转
     *
     * @return
     */
    public String theVehicleDetails() {
    	 HttpServletRequest request = ServletActionContext.getRequest();
    	 String content = request.getParameter("content");
    	if(carManage.getCarmID()!=null&&!carManage.getCarmID().equals("")){

        StringBuilder sb = new StringBuilder();
        sb.append("select c.carmID,c.carNumber,v.sitename,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),");
        sb.append("to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.time,v.itslocation,c.carmNumber,c.serialNumber,c.money,c.chargeType,c.chargeState,v.companyId,e.equipmentNumber,c.status,v.siteId,c.auditStatus ");
        sb.append("from dt_venueinformation v, dt_equipmentinformation e, dt_carmanage c ");
        sb.append("where v.siteid = e.siteid and e.equipmentNumber=c.equipmentNumber ");
        sb.append("and c.carmID=?");

        obj = baseBeanService.getObjectBySqlAndParams(sb.toString(), new Object[]{carManage.getCarmID()});
         if(content!=null&&content.indexOf("二维码支付")!=-1){
             request.setAttribute("result", "1");
         }else {
             String sql = "select f.feecid,p.ppid from dt_carmanage c,dt_equipmentinformation e,dt_feescale f,dt_productpackaging p where c.carmID = ? and c.equipmentnumber = e.equipmentnumber and e.siteid=f.siteid and  f.goodsid = p.goodsid and f.startUsing=? and  f.carType = ? order by f.timeunits";
             String carNumber = carManage.getCarNumber();
             if (carNumber == null || carNumber.equals("")) {

                 String hql = "from CarManage where carmID = ?";
                 CarManage cm = (CarManage) baseBeanService.getBeanByHqlAndParams("from CarManage where carmID = ?", new Object[]{carManage.getCarmID()});
                 carNumber = cm.getCarNumber();
             }
             String carType = "p";
             if (carNumber.indexOf("学") != -1) {
                 carType = "c";
             }
             List<Object> objlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{carManage.getCarmID(), "00", carType});
             String ppid = "";
             Object judge = null;
             if (objlist.size() > 0) {
                 Object[] objs = (Object[]) objlist.get(0);


                 if (objs != null) {
                     judge = objs[0];
                     ppid = objs[1] != null ? objs[1].toString() : "";
                 }
             }

             request.setAttribute("judge", judge);
             request.setAttribute("ppid", ppid);

             ParkingSpace parking = (ParkingSpace) baseBeanService.getBeanByHqlAndParams("from ParkingSpace p where p.parksId = (select c.parksId from CarManage c where c.carmID = ?)", new Object[]{carManage.getCarmID()});
             if (parking != null) {
                 request.setAttribute("parkingCode", parking.getParkingCode());
             } else {
                 request.setAttribute("parkingCode", "未分配");
             }
         }
            try{
            Object[] objs2 = (Object[]) obj;
            CarFeeAudit carFeeAudit = (CarFeeAudit)baseBeanService.getBeanByHqlAndParams("from CarFeeAudit c where c.carNumber = ? and siteId = ?",new Object[]{objs2[1],objs2[15]});
            if(carFeeAudit!=null){
                request.setAttribute("isfAudit", "01");
            }else{
                request.setAttribute("isfAudit", "00");

            }
                if(carManage.getCarNumber()!=null&&!carManage.getCarNumber().equals("")) {
                    carManage.setCarNumber(objs2[1].toString());
                    request.setAttribute("auditStatus", objs2[16]==null?"":objs2[16].toString());
                }
         }catch (Exception e){
             e.printStackTrace();
             System.out.println(carManage.getCarmID());
            }
    	}else{

    		request.setAttribute("result", "1");
    	}


        return "theVehicleDetails";
    }

    /**
     * 查询车辆管理
     *
     * @return
     */
    public String queryTheVehicle() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String posNum = request.getParameter("posNum");

        PosDevice posDevice = null;
        if(posNum!=null&&!posNum.equals("")){
            posDevice = (PosDevice)baseBeanService.getBeanByHqlAndParams("from PosDevice where posNum = ?",new Object[]{posNum});

        }
        if(caccount!=null) {
//        String companyId1 = "company201009046vxdyzy4wg0000000015";//资阳胜威驾校
//        String companyId2 = "company201009046vxdyzy4wg0000000140";//绵阳胜威驾校
//        String companyId3 = "company201009046vxdyzy4wg0000000130";//成都胜威驾校
//        String companyId4 = "company201009046vxdyzy4wg0000000025";//天太世统有限公司
            //   if (caccount.getCompanyID().equals(companyId1) || caccount.getCompanyID().equals(companyId2) || caccount.getCompanyID().equals(companyId3) || caccount.getCompanyID().equals(companyId4)) {
            List<Object> st = new ArrayList<Object>();
            st.add(caccount.getCompanyID());
            StringBuilder sb = new StringBuilder();
            sb.append("select c.carmID,c.carNumber,v.sitename,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.time,c.status,c.auditStatus ");
            sb.append("from dt_venueinformation v, dt_equipmentinformation e, dt_carmanage c ");
            sb.append("where v.companyid = ? and v.siteid = e.siteid and e.equipmentNumber=c.equipmentNumber ");
            if (carManage.getCarNumber() != null && !carManage.getCarNumber().equals("")) {
                sb.append("and c.carNumber like ? ");
                st.add('%' + carManage.getCarNumber() + '%');
            }
            if (carManage.getStatus() != null && !carManage.getStatus().equals("")) {
                sb.append("and c.status = ? ");
                st.add(carManage.getStatus());
            }

            if (posDevice != null && posDevice.getSiteId() != null && !posDevice.getSiteId().equals("")) {
                sb.append("and v.siteid = ? ");
                st.add(posDevice.getSiteId());
            }
            sb.append("order by c.createdate desc ");
            pageForm = baseBeanService.getPageFormBySQL(pageForm.getPageNumber(), pageForm.getPageSize(), sb.toString(), "select count(*) from (" + sb.toString() + ")", st.toArray());
            //  }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 车辆异常跳转处理
     *
     * @return
     */
    public String abnormalJump() {
        StringBuilder sb = new StringBuilder();
        sb.append("select c.carmid, c.carmnumber,to_char(c.indate, 'YYYY-MM-DD HH24:MI:SS'),");
        sb.append("to_char(c.outdate, 'YYYY-MM-DD HH24:MI:SS'),v.sitenumber,v.sitename,c.equipmentnumber, ");
        sb.append("f.staffname,c.status,c.carNumber,c.time,v.itslocation,c.money,c.serialNumber,v.siteid,c.panorama,c.picture ");
        sb.append("from dt_carmanage c,dt_equipmentinformation e,dt_venueinformation v,dt_hr_staff f ");
        sb.append("where c.carmid = ? and c.equipmentnumber=e.equipmentnumber and e.siteid=v.siteid ");
        sb.append("and v.staffid=f.staffid order by c.indate desc ");
        Object obj = baseBeanService.getObjectBySqlAndParams(sb.toString(), new Object[]{carManage.getCarmID()});
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("obj", obj);
        return "abnormalJump";
    }

    /**
     * 移动端查询错误车牌对应的进入车辆信息 *
     *
     * @return
     */
    public String errorLicensePlateDetails() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String posNum = request.getParameter("posNum");
		if (posNum != null && !posNum.equals("")) {
			String companyId = cmService.getCompanyByPosNum(posNum);
			if (companyId != null && !companyId.equals("")) {
				caccount = new CAccount();
				caccount.setCompanyID(companyId);
			}
		} else {
			caccount = goCaccount();
		}

        List<Object> list = cmService.licensePlateComparison(caccount.getCompanyID(), vf.getSiteId(), carManage.getCarNumber());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 移动端添加/修改车辆停车信息 *
     *
     * @return
     */
    public String addOrUpdateVehicle() {
        caccount = goCaccount();
        boolean b = cmService.addOrUpdateVehicle(carManage, caccount.getCompanyID());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /**
     *
     * 获取微信jssdk权限信息
     * @return
     */
    public String getJssdkConfig(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String retUrl = request.getParameter("retUrl");
        Map<String, Object> map = scManageService.getJssdkConfig(retUrl);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     *
     * 获取审核信息
     * @return
     */
    public String getCarTimeInfo(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String cmaID = request.getParameter("cmaID");
         carManageAudit = (CarManageAudit)baseBeanService.getBeanByHqlAndParams( "from CarManageAudit where  cmaID = ? ",new Object[]{cmaID});

        return  "timeinfo";

    }

    /**
     *
     * 保存审核信息
     * @return
     */
    public String saveTimeInfo(){
        HttpServletRequest request = ServletActionContext.getRequest();

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        carManageAudit.setIndate(Utilities.getDateFromString(starttime,"yyyy-MM-dd HH:mm:ss"));
        carManageAudit.setOutdate(Utilities.getDateFromString(endtime,"yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<String, Object>();
        if(account!=null){
            String cmaID = scManageService.saveTimeInfo(carManageAudit,account.getStaffID(),account.getCompanyID());

            map.put("cmaID", cmaID);

        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     *
     * 获取设置免费时间页面数据如果有历史数据直接获取
     * * @return
     */
    public String getTimeHistory() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session1 = request.getSession();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        carManageAudit = scManageService.getTimeHistory(carmID);

        try {
            if(carManageAudit!=null&&carManageAudit.getStatus().equals("0")) {
                Date ts = carManageAudit.getOutdate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ts);
                calendar.add(Calendar.HOUR, 2); // 增加一个小时
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(calendar.getTime());
                request.setAttribute("outtime", formattedDate);
            }
            String posNum = request.getParameter("posNum");
            if (posNum != null && !posNum.equals("")) {

                SessionWrap sw = SessionWrap.getInstance();

                TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                        SessionWrap.KEY_SHOPCUSCOM);


                if(tc==null){
                    //说明用的终端机

                    String url = request.getHeader("Referer");
                    session1.setAttribute("preUrl", url);
                    return "login";
                }else{
                    String companyId = cmService.getCompanyByPosNum(posNum);

                    if(account==null){
                        account = new CAccount();
                    }
                    account.setCompanyID(companyId);
                    account.setStaffID(tc.getStaffid());
                    session1.setAttribute("account", account);
                    session1.setAttribute("preUrl","");

                }




            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return "feeset";
    }
    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public MaterialGroup getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(MaterialGroup materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MaterialContent getMaterialContent() {
        return materialContent;
    }

    public void setMaterialContent(MaterialContent materialContent) {
        this.materialContent = materialContent;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public GoodFunction getGoodFunction() {
        return goodFunction;
    }

    public void setGoodFunction(GoodFunction goodFunction) {
        this.goodFunction = goodFunction;
    }

    public ContactCompany getConcom() {
        return concom;
    }

    public void setConcom(ContactCompany concom) {
        this.concom = concom;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public TEshopCusCom getCuscom() {
        return cuscom;
    }

    public void setCuscom(TEshopCusCom cuscom) {
        this.cuscom = cuscom;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public MaterialMusic getMaterialMusic() {
        return materialMusic;
    }

    public void setMaterialMusic(MaterialMusic materialMusic) {
        this.materialMusic = materialMusic;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getBacktype() {
        return backtype;
    }

    public void setBacktype(String backtype) {
        this.backtype = backtype;
    }

    public String getSkipJudge() {
        return skipJudge;
    }

    public void setSkipJudge(String skipJudge) {
        this.skipJudge = skipJudge;
    }

    public String getMiniSystemJudge() {
        return miniSystemJudge;
    }

    public void setMiniSystemJudge(String miniSystemJudge) {
        this.miniSystemJudge = miniSystemJudge;
    }

    public CAccount getCaccount() {
        return caccount;
    }

    public void setCaccount(CAccount caccount) {
        this.caccount = caccount;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public String getAuditSkip() {
        return auditSkip;
    }

    public void setAuditSkip(String auditSkip) {
        this.auditSkip = auditSkip;
    }

    public AuditRecord getAuditRecord() {
        return auditRecord;
    }

    public void setAuditRecord(AuditRecord auditRecord) {
        this.auditRecord = auditRecord;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public String getAndroidJudge() {
        return androidJudge;
    }

    public void setAndroidJudge(String androidJudge) {
        this.androidJudge = androidJudge;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public CarManage getCarManage() {
        return carManage;
    }

    public void setCarManage(CarManage carManage) {
        this.carManage = carManage;
    }

    public VenueInformation getVf() {
        return vf;
    }

    public void setVf(VenueInformation vf) {
        this.vf = vf;
    }

    public TbJpCompany getTcompany() {
        return tcompany;
    }

    public void setTcompany(TbJpCompany tcompany) {
        this.tcompany = tcompany;
    }

    public ApplyParam getApplyParam() {
        return applyParam;
    }

    public void setApplyParam(ApplyParam applyParam) {
        this.applyParam = applyParam;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    public CarManageAudit getCarManageAudit() {
        return carManageAudit;
    }

    public void setCarManageAudit(CarManageAudit carManageAudit) {
        this.carManageAudit = carManageAudit;
    }

    public String getCarmID() {
        return carmID;
    }

    public void setCarmID(String carmID) {
        this.carmID = carmID;
    }
}
