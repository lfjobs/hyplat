package hy.ea.collage.action;

import com.aliyuncs.exceptions.ClientException;

import com.aliyuncs.vod.model.v20170321.*;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.scmanage.DSVideo;
import hy.ea.bo.production.scmanage.ProductOfVedio;
import hy.ea.collage.action.dto.*;
import hy.ea.collage.service.DspIndexSerivce;
import hy.ea.collage.service.impl.VODService;
import hy.ea.collage.service.impl.VideoResetService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 短视频首页
 */
@Controller
@Scope("prototype")
public class DspIndexAction {
    @Resource
    private DspIndexSerivce dspIndexSerivce;
    @Resource
    private VideoResetService videoResetService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private VODService vodService;
    private ProductOfVedio pvedio;
    private Object result;
    private String search;
    private String staffId;
    private String state = "00";
    private String parameter;//查询参数
    private String videoId;
    private String videoStaffId;
    private String type;
    private DSVideo video;
    private String priceType;
    private String sccId;

    private int pageNumber = 1;
    private int pageSize = 20;
    private int videoPlayInfoResetMethod = 1; // 视频播放信息重置方式 1 批量重置封面 2 并行批量重置封面+播放地址（仅限阿里云VOD）
    private PageForm pageForm;

    private String title;
    private String fileName;
    private Long fileSize;

    private String vodIds;

    HttpServletRequest request = ServletActionContext.getRequest();

    @Resource
    private EarthIndexService earthIndexService;

    public String getVideoUploadInfo() {
        try {
            CreateUploadVideoResponse createUploadVideoResponse = vodService.createUploadVideo(title, fileName, fileSize);

            JSONObject uploadInfo = new JSONObject();
            uploadInfo.accumulate("uploadAuth", createUploadVideoResponse.getUploadAuth());
            uploadInfo.accumulate("uploadAddress", createUploadVideoResponse.getUploadAddress());
            uploadInfo.accumulate("videoId", createUploadVideoResponse.getVideoId());

            result = uploadInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 短视频列表
     *
     * @return
     */
    public String getDsVediolist() {
        pageForm = dspIndexSerivce.getDsplist(pageNumber, pageSize, parameter, staffId, type);
        JSONObject jsonObjList = new JSONObject();
        List<Object> lists = new ArrayList<Object>();
        if (pageForm != null && pageForm.getList().size() > 0) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("videoid", isNull(obj[0]));
                jsonObj.accumulate("videourl", isNull(obj[1]));
                jsonObj.accumulate("titlename", isNull(obj[2]));
                jsonObj.accumulate("staffid", isNull(obj[3]));
                jsonObj.accumulate("staffname", isNull(obj[4]));
                jsonObj.accumulate("headimage", isNull(obj[5]));
                jsonObj.accumulate("date", isNull(obj[6]));
                jsonObj.accumulate("praisev", obj[7]);
                jsonObj.accumulate("plcountv", obj[8]);
                jsonObj.accumulate("sharev", obj[9]);
                jsonObj.accumulate("ispraise", obj[10]);
                jsonObj.accumulate("iscare", obj[11]);
                jsonObj.accumulate("coverImgUrl", isNull(obj[12]));
                jsonObj.accumulate("ppid", isNull(obj[13]));
                jsonObj.accumulate("image", isNull(obj[14]));
                jsonObj.accumulate("goodsname", isNull(obj[15]));
                lists.add(jsonObj);
            }
        }

        jsonObjList.accumulate("videolist", lists);

        result = jsonObjList.toString();
        return "success";
    }

    /**
     * 给视频点赞
     *
     * @return
     */
    public String addZan() {
        String ajax = request.getParameter("ajax");
        Map<String, Object> map = new HashMap<String, Object>();
        if ("ajax".equals(ajax)) {
            HttpSession httpSession = ServletActionContext.getRequest()
                    .getSession();
            SessionWrap sessionWrap = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(
                    httpSession, SessionWrap.KEY_SHOPCUSCOM);
            if (cus == null) {

                String url = request.getHeader("Referer");
                httpSession.setAttribute("url", url);
                map.put("login", "login");
                JSONObject jo = JSONObject.fromObject(map);
                this.result = jo;
                return "success";

            }
            if (staffId == null || staffId.equals("")) {
                staffId = cus.getStaffid();
            }
        }
        String r = dspIndexSerivce.addZan(videoId, staffId);

        map.put("result", r);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;
        return "success";
    }


    /**
     * 关注用户
     *
     * @return
     */
    public String careUser() {
        String ajax = request.getParameter("ajax");
        Map<String, Object> map = new HashMap<String, Object>();
        if ("ajax".equals(ajax)) {
            HttpSession httpSession = ServletActionContext.getRequest()
                    .getSession();
            SessionWrap sessionWrap = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(
                    httpSession, SessionWrap.KEY_SHOPCUSCOM);
            if (cus == null) {

                String url = request.getHeader("Referer");
                httpSession.setAttribute("url", url);
                map.put("login", "login");
                JSONObject jo = JSONObject.fromObject(map);
                this.result = jo;
                return "success";

            }
            if (staffId == null || staffId.equals("")) {
                staffId = cus.getStaffid();
            }
        }

        String videoStaffId = request.getParameter("videoStaffId");
        String r = dspIndexSerivce.careUser(videoStaffId, staffId);

        map.put("result", r);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;
        return "success";

    }

    /**
     * 记录分享次数
     *
     * @return
     */
    public String addShare() {
        String result = "0";
        try {
            dspIndexSerivce.addShare(videoId);
        } catch (Exception e) {
            e.printStackTrace();
            result = "1";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;
        return "success";
    }

    /**
     * 评论点赞
     *
     * @return
     */
    public String addPlZan() {
        String pcID = request.getParameter("pcID");
        String r = dspIndexSerivce.addPlZan(pcID, staffId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", r);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;
        return "success";

    }

    /**
     * 增加评论
     *
     * @return
     */
    public String addPL() {
        // 评论内容
        String content = request.getParameter("content");

        String toCommentId = request.getParameter("toCommentId");
        String toStaffId = request.getParameter("toStaffId");

        String pcId = dspIndexSerivce.addPL(videoId, staffId, toCommentId, content, toStaffId);

        JSONObject jsonObj = new JSONObject();
        if (StringUtils.isBlank(toCommentId) && StringUtils.isBlank(toStaffId)) {
            Object[] obj = (Object[]) dspIndexSerivce.getPL(videoId, staffId, videoStaffId, pcId);
            jsonObj.accumulate("staffId", isNull(obj[0]));
            jsonObj.accumulate("staffName", isNull(obj[1]));
            jsonObj.accumulate("avatar", isNull(obj[2]));
            jsonObj.accumulate("createdDate", isNull(obj[3]));
            jsonObj.accumulate("content", isNull(obj[4]));
            jsonObj.accumulate("commentId", isNull(obj[5]));
            jsonObj.accumulate("likedCount", isNull(obj[6]));
            jsonObj.accumulate("isLiked", isNull(obj[7]));
            jsonObj.accumulate("authorIsLiked", obj[8]);
            jsonObj.accumulate("replyCount", obj[9]);
        } else {
            Object[] obj = (Object[]) dspIndexSerivce.getReplyPL(videoId, staffId, videoStaffId, toCommentId, pcId);
            jsonObj.accumulate("staffId", isNull(obj[0]));
            jsonObj.accumulate("staffName", isNull(obj[1]));
            jsonObj.accumulate("avatar", isNull(obj[2]));
            jsonObj.accumulate("createdDate", isNull(obj[3]));
            jsonObj.accumulate("content", isNull(obj[4]));
            jsonObj.accumulate("commentId", isNull(obj[5]));
            jsonObj.accumulate("likedCount", isNull(obj[6]));
            jsonObj.accumulate("isLiked", isNull(obj[7]));
            jsonObj.accumulate("authorIsLiked", obj[8]);
            jsonObj.accumulate("toStaffId", isNull(obj[9]));
            jsonObj.accumulate("toStaffName", isNull(obj[10]));
        }

        this.result = jsonObj;
        return "success";
    }

    /**
     * 查询一个视频的一级评论
     *
     * @return
     */
    public String searchPL() {
        //评论用户ID、昵称 、头像、 评论时间、 评论内容 、评论ID，被赞数，当前用户是否给点赞,作者是否赞过，回复该评论总数
        pageForm = dspIndexSerivce.searchPL(pageNumber, pageSize, videoId, staffId, videoStaffId);
        JSONObject jsonObjList = new JSONObject();
        List<Object> lists = new ArrayList<Object>();
        if (pageForm != null && pageForm.getList().size() > 0) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("staffId", isNull(obj[0]));
                jsonObj.accumulate("staffName", isNull(obj[1]));
                jsonObj.accumulate("avatar", isNull(obj[2]));
                jsonObj.accumulate("createdDate", isNull(obj[3]));
                jsonObj.accumulate("content", isNull(obj[4]));
                jsonObj.accumulate("commentId", isNull(obj[5]));
                jsonObj.accumulate("likedCount", isNull(obj[6]));
                jsonObj.accumulate("isLiked", isNull(obj[7]));
                jsonObj.accumulate("authorIsLiked", obj[8]);
                jsonObj.accumulate("replyCount", obj[9]);
                lists.add(jsonObj);
            }
        }

        jsonObjList.accumulate("content", lists);
        jsonObjList.accumulate("pageNumber", pageNumber);
        jsonObjList.accumulate("pageSize", pageSize);
        jsonObjList.accumulate("totalElements", pageForm != null ? pageForm.getRecordCount() : 0);
        jsonObjList.accumulate("totalPages", pageForm != null ? pageForm.getPageCount() : 0);

        result = jsonObjList;
        return "success";
    }

    /**
     * 查询一级评论的回复
     *
     * @return
     */
    public String searchReplyPL() {
        //评论用户ID、昵称 、头像、 评论时间、 评论内容 、评论ID，被赞数，当前用户是否给点赞,作者是否赞过，回复谁的ID，回复谁的姓名
        String commentId = request.getParameter("commentId");
        pageForm = dspIndexSerivce.searchReplyPL(pageNumber, pageSize, videoId, staffId, videoStaffId, commentId);
        JSONObject jsonObjList = new JSONObject();
        List<Object> lists = new ArrayList<Object>();
        if (pageForm != null && pageForm.getList().size() > 0) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("staffId", isNull(obj[0]));
                jsonObj.accumulate("staffName", isNull(obj[1]));
                jsonObj.accumulate("avatar", isNull(obj[2]));
                jsonObj.accumulate("createdDate", isNull(obj[3]));
                jsonObj.accumulate("content", isNull(obj[4]));
                jsonObj.accumulate("commentId", isNull(obj[5]));
                jsonObj.accumulate("likedCount", isNull(obj[6]));
                jsonObj.accumulate("isLiked", isNull(obj[7]));
                jsonObj.accumulate("authorIsLiked", obj[8]);
                jsonObj.accumulate("toStaffId", isNull(obj[9]));
                jsonObj.accumulate("toStaffName", isNull(obj[10]));
                lists.add(jsonObj);
            }
        }

        jsonObjList.accumulate("content", lists);
        jsonObjList.accumulate("pageNumber", pageNumber);
        jsonObjList.accumulate("pageSize", pageSize);
        jsonObjList.accumulate("totalElements", pageForm != null ? pageForm.getRecordCount() : 0);
        jsonObjList.accumulate("totalPages", pageForm != null ? pageForm.getPageCount() : 0);
        result = jsonObjList;
        return "success";
    }

    /**
     * 阅读量记录
     *
     * @return
     */
    public String addReadNum() {
        String r = dspIndexSerivce.addReadNum(staffId, videoId);
        addBrowseHistory();

        JSONObject result = new JSONObject();
        result.accumulate("result", r);

        this.result = result;
        return "success";
    }

    private void addBrowseHistory() {
        if (staffId != null && !staffId.equals("")) {
            try {
                TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{staffId});
                if (tc != null) {
                    earthIndexService.addBrowseHistory(tc.getSccId(), "视频", videoId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 个人主页自己看的
     *
     * @return
     */
    public String getPHomepage() {
        JSONObject jsonObjList = new JSONObject();
        if (pageNumber == 1) {
//            Staff staff = dspIndexSerivce.getBaseInfo(staffId);// 基本信息
//            int gznum = dspIndexSerivce.getJoinFans(staffId, "z");// 关注数
//            int fsnum = dspIndexSerivce.getJoinFans(staffId, "f");// 粉丝数
//
//            jsonObjList.accumulate("staffid", isNull(staff.getStaffID()));
//            jsonObjList.accumulate("staffname", isNull(staff.getStaffName()));
//            jsonObjList.accumulate("headimage", isNull(staff.getHeadimage()));
//            jsonObjList.accumulate("brief", isNull(staff.getBrief())); // 简介
//            jsonObjList.accumulate("gznum", gznum); // 关注数
//            jsonObjList.accumulate("fsnum", fsnum); // 粉丝数
        }
        PageForm pageForm = dspIndexSerivce.getMyVideoList(pageNumber, pageSize, staffId, "00");// 我的视频 //主页人ID

        List<Object> lists = new ArrayList<Object>();
        if (pageForm != null && pageForm.getList().size() > 0) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("videoid", isNull(obj[0]));// 视频ID
                jsonObj.accumulate("videourl", isNull(obj[1]));// 视频地址
                jsonObj.accumulate("coverImgUrl", isNull(obj[2])); // 视频明面
                jsonObj.accumulate("readnum", isNull(obj[3]));// 播放量
                jsonObj.accumulate("istop", isNull(obj[4]));// 是否置顶
                jsonObj.accumulate("state", isNull(obj[5]));// 权限
                lists.add(jsonObj);
            }
        }
        jsonObjList.accumulate("videolist", lists);

        result = jsonObjList;
        return "success";

    }

    /**
     * 点赞的视频
     *
     * @return
     */
    public String getlikeVideoList() {
        PageForm pageForm = dspIndexSerivce.getlikeVideoList(pageNumber, pageSize, staffId);//点赞的视频  //主页人ID
        JSONObject jsonObjList = new JSONObject();
        List<Object> lists = new ArrayList<Object>();
        if (pageForm != null && pageForm.getList().size() > 0) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("videoid", isNull(obj[0]));//视频ID
                jsonObj.accumulate("videourl", isNull(obj[1]));//视频地址
                jsonObj.accumulate("coverImgUrl", isNull(obj[2])); //视频明面
                jsonObj.accumulate("praisev", isNull(obj[3]));//点赞数
                jsonObj.accumulate("staffid", isNull(obj[4]));//发布人
                lists.add(jsonObj);
            }
        }

        jsonObjList.accumulate("videolist", lists);

        result = jsonObjList;

        return "success";

    }

    /**
     * 查询一个视频的展示信息
     *
     * @return
     */
    public String getVideoDetail() {
        Object objs = dspIndexSerivce.getVideoDetail(videoId, staffId);// 登录人ID
        Object[] obj = (Object[]) objs;

        JSONObject jsonObj = new JSONObject();
        jsonObj.accumulate("videoid", isNull(obj[0]));
        jsonObj.accumulate("videourl", isNull(obj[1]));
        jsonObj.accumulate("titlename", isNull(obj[2]));
        jsonObj.accumulate("staffid", isNull(obj[3]));
        jsonObj.accumulate("staffname", isNull(obj[4]));
        jsonObj.accumulate("headimage", isNull(obj[5]));
        jsonObj.accumulate("date", isNull(obj[6]));
        jsonObj.accumulate("praisev", obj[7]);
        jsonObj.accumulate("plcountv", obj[8]);
        jsonObj.accumulate("sharev", obj[9]);
        jsonObj.accumulate("ispraise", obj[10]);
        jsonObj.accumulate("iscare", obj[11]);
        jsonObj.accumulate("coverImgUrl", isNull(obj[12]));
        jsonObj.accumulate("ppid", isNull(obj[13]));
        jsonObj.accumulate("image", isNull(obj[14]));
        jsonObj.accumulate("goodsname", isNull(obj[15]));

        result = jsonObj;
        return "success";
    }

    /**
     * 发布视频
     *
     * @return
     */
    public String publishVideo() {
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);

        System.out.println("titleName: " + video.getTitleName());
        System.out.println("staffId: " + cus.getStaffid());
        System.out.println("pvedio: " + pvedio.getPpid());
        System.out.println("priceType: " + priceType);

        String r = dspIndexSerivce.pubVideo(video, cus.getStaffid(), pvedio.getPpid(), priceType);
        Map<String, Object> map = new HashMap<>();
        map.put("result", r);
        this.result = JSONObject.fromObject(map);
        return "success";
    }

    /**
     * 发布短视频商品
     *
     * @return
     */
    public String getDspProducts() {
        System.out.println(priceType);
        System.out.println(pageNumber);
        System.out.println(pageSize);
        System.out.println(parameter);
        System.out.println(sccId);

        pageForm = dspIndexSerivce.getDspProducts(priceType, pageNumber, pageSize, parameter, sccId);

        if (pageForm == null || pageForm.getList() == null) {
            PageInfo<GoodsDto> pageInfo = new PageInfo<>();
            result = JSONObject.fromObject(pageInfo);
            return "success";
        }

        List<GoodsDto> goodsList = new ArrayList<>();
        for (int i = 0; i < pageForm.getList().size(); i++) {
            Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
            GoodsDto dto = GoodsDto.fromVideoGoodsCols(obj);
            goodsList.add(dto);
        }

        PageInfo<GoodsDto> pageInfo = new PageInfo<>();
//        pageInfo.setPageNumber(pageForm.getPageNumber());
//        pageInfo.setPageSize(pageForm.getPageSize());
//        pageInfo.setTotalElements(pageForm.getRecordCount());
        pageInfo.setContent(goodsList);

        result = JSONObject.fromObject(pageInfo);
        return "success";

    }


    /**
     * 获取短视频商品
     *
     * @return
     */
    public String getVideoProductList() {
        pageForm = dspIndexSerivce.getDspProductsList(pageNumber, pageSize, videoId, (pvedio == null ? "" : pvedio.getPpid()));

        if (pageForm == null || pageForm.getList() == null) {
            PageInfo<GoodsDto> pageInfo = new PageInfo<>();
            result = JSONObject.fromObject(pageInfo);
            return "success";
        }

        List<GoodsDto> goodsList = new ArrayList<>();
        for (int i = 0; i < pageForm.getList().size(); i++) {
            Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
            GoodsDto dto = GoodsDto.fromVideoGoodsCols(obj);
            goodsList.add(dto);
        }

        PageInfo<GoodsDto> pageInfo = new PageInfo<>();
        pageInfo.setPageNumber(pageForm.getPageNumber());
        pageInfo.setPageSize(pageForm.getPageSize());
        pageInfo.setTotalElements(pageForm.getRecordCount());
        pageInfo.setContent(goodsList);

        result = JSONObject.fromObject(pageInfo);
        return "success";
    }

    private Object isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return tep;
        }
    }


    /////////////////////////////////////////////////////////网页版/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 发布视频
     *
     * @return
     */
    public String issue() {
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);
        return "issue";
    }

    /**
     * 全面屏
     */
    public String fullScreen() throws ClientException {
        prepareVideoList();
        return "fullscreen";
    }

    public void prepareVideoList() {
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);

        String staffId = cus == null ? "" : cus.getStaffid();

        List<BaseBean> recommendedList = dspIndexSerivce.getDsplist(staffId, "00", 1000);
        List<BaseBean> followedList = dspIndexSerivce.getDsplist(staffId, "", 1000);

        List<ViewVideoDto> recommendedDtoList = ViewVideoDto.fromRows(recommendedList);
        List<ViewVideoDto> followedDtoList = ViewVideoDto.fromRows(followedList);

        List<ViewVideoDto> finalRecommendedList = new ArrayList<>();
        List<ViewVideoDto> finalFollowedList = new ArrayList<>();

        Set<String> rVodIdSet = new HashSet<>();
        for (int i = 0; i < recommendedDtoList.size(); i++) {
            ViewVideoDto dto = recommendedDtoList.get(i);
            String vodId = dto.getVodId();
            if (StringUtils.isNotBlank(vodId) && rVodIdSet.contains(vodId)) {
                continue;
            }
            finalRecommendedList.add(dto);
            rVodIdSet.add(vodId);
        }

        Set<String> fVodIdSet = new HashSet<>();
        for (int i = 0; i < followedDtoList.size(); i++) {
            ViewVideoDto dto = followedDtoList.get(i);
            String vodId = dto.getVodId();
            if (StringUtils.isNotBlank(vodId) && fVodIdSet.contains(vodId)) {
                continue;
            }
            finalFollowedList.add(dto);
            fVodIdSet.add(vodId);
        }

        request.getSession().setAttribute("recommended", finalRecommendedList);
        request.getSession().setAttribute("followed", finalFollowedList);
    }

    /**
     * 短视频推荐列表
     *
     * @return
     */
    public String getFullScreenVideoList() throws ClientException, InterruptedException {
//        PageForm pageForm = dspIndexSerivce.getDsplist(pageNumber, pageSize, parameter, staffId, type);
        List<ViewVideoDto> randomVideoList = (List<ViewVideoDto>) request.getSession().getAttribute(getVideoSessionKey());

        int fromIndex = Math.min(pageNumber * pageSize, randomVideoList.size());
        int toIndex = Math.min((pageNumber + 1) * pageSize, randomVideoList.size());

        List<ViewVideoDto> viewVideoList = randomVideoList.subList(fromIndex, toIndex);
        videoResetService.resetVideoPlayInfos(viewVideoList);

        PageInfo<ViewVideoDto> pageInfo = new PageInfo<>();
        pageInfo.setContent(viewVideoList);
        pageInfo.setPageNumber(pageNumber);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalElements(randomVideoList.size());

        this.result = JSONObject.fromObject(pageInfo);

        return "success";
    }

    private String getVideoSessionKey() {
        if (type.equals("00")) {
            return "recommended";
        } else {
            return "followed";
        }
    }

    /**
     * 我的首页
     *
     * @return
     */
    public String getMyHomePage() {
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);
        TEshopCusCom tc = dspIndexSerivce.getTc(cus.getStaffid());// 获取微分金账号

        String staffId = cus.getStaffid();

        Staff staff = dspIndexSerivce.getBaseInfo(staffId);// 基本信息
        int gznum = dspIndexSerivce.getJoinFans(staffId, "z");// 关注数
        int fsnum = dspIndexSerivce.getJoinFans(staffId, "f");// 粉丝数

        JSONObject profile = new JSONObject();
        profile.accumulate("staffId", isNull(staff.getStaffID()));
        profile.accumulate("staffName", isNull(staff.getStaffName()));
        profile.accumulate("headImage", isNull(staff.getHeadimage()));
        profile.accumulate("brief", isNull(staff.getBrief())); // 简介
        profile.accumulate("sccId", isNull(tc.getSccId()));
        profile.accumulate("account", isNull(tc.getAccount()));
        profile.accumulate("gznum", gznum); // 关注数
        profile.accumulate("fsnum", fsnum); // 粉丝数

        request.setAttribute("profile", profile);
        return "my-homepage";
    }

    public String myVideos() throws ClientException, InterruptedException {
        // 如果当前查看的用户 是自己
        PageForm pageForm = dspIndexSerivce.getMyVideoList(pageNumber, pageSize, staffId, state);// 我的视频 //主页人ID
        Pageable<ViewVideoDto> pageInfo = ViewVideoDto.fromPageForm(pageForm);

        // 重置播放信息
        if (videoPlayInfoResetMethod == 1) {
            videoResetService.resetVideoPlayInfos(pageInfo.getContent());
        } else {
            videoResetService.resetVideoInfos(pageInfo.getContent());
        }

        this.result = JSONObject.fromObject(pageInfo);
        return "success";
    }

    public String myLikedVideos() throws ClientException, InterruptedException {
//        HttpSession httpSession = ServletActionContext.getRequest().getSession();
//        SessionWrap sessionWrap = SessionWrap.getInstance();
//        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);
//        String staffId = cus.getStaffid();

        PageForm pageForm = dspIndexSerivce.getlikeVideoList(pageNumber, pageSize, staffId);
        Pageable<ViewVideoDto> pageInfo = ViewVideoDto.fromPageForm(pageForm);
        // 重置播放信息
        if (videoPlayInfoResetMethod == 1) {
            videoResetService.resetVideoPlayInfos(pageInfo.getContent());
        } else {
            videoResetService.resetVideoInfos(pageInfo.getContent());
        }

        this.result = JSONObject.fromObject(pageInfo);

        return "success";
    }

    /**
     * 作者短视频首页
     *
     * @return
     */
    public String getAuthorHomePage() throws ClientException {
        TEshopCusCom tc = dspIndexSerivce.getTc(videoStaffId);// 获取微分金账号

        Staff staff = dspIndexSerivce.getBaseInfo(videoStaffId);// 基本信息
        int gznum = dspIndexSerivce.getJoinFans(videoStaffId, "z");// 关注数
        int fsnum = dspIndexSerivce.getJoinFans(videoStaffId, "f");// 粉丝数
        String iscare = dspIndexSerivce.isCare(videoStaffId, staffId);// 是否关注

        // 作者信息
        JSONObject profile = new JSONObject();
        profile.accumulate("staffId", isNull(staff.getStaffID()));
        profile.accumulate("staffName", isNull(staff.getStaffName()));
        profile.accumulate("headImage", isNull(staff.getHeadimage()));
        profile.accumulate("brief", isNull(staff.getBrief())); // 简介
        profile.accumulate("sccId", isNull(tc.getSccId()));
        profile.accumulate("account", isNull(tc.getAccount()));
        profile.accumulate("gznum", gznum); // 关注数
        profile.accumulate("fsnum", fsnum); // 粉丝数
        profile.accumulate("iscare", iscare); // 粉丝数

        request.setAttribute("profile", profile);
        return "author-homepage";
    }

    /**
     * 获取分享出去的视频 以及作者其他视频
     *
     * @return
     */
    public String getShareVideo() {
        String ajax = request.getParameter("ajax");
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);
        if (cus != null) {
            if (staffId == null || staffId.equals("")) {
                staffId = cus.getStaffid();
            }
        }
        if (staffId == null) {
            staffId = "";
        }
        Object objs = null;

        //作者的视频
        pageForm = dspIndexSerivce.getShareVideoLists(pageNumber, 20, staffId, videoStaffId);

        if ("ajax".equals(ajax)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo;
            return "success";
        } else {
            objs = dspIndexSerivce.getVideoDetail(videoId, staffId);// 登录人ID
            request.setAttribute("video", objs);
        }

        return "sharelist";
    }

    /**
     * 关注历史中，点击查看
     *
     * @return
     */
    public String getVideoViewListPage() {
        HttpSession httpSession = ServletActionContext.getRequest().getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(httpSession, SessionWrap.KEY_SHOPCUSCOM);
        return "view-list";
    }

    /**
     * 删除视频
     *
     * @return
     */
    public String deleteVideo() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            dspIndexSerivce.deleteVideo(videoId);
            map.put("result", "0");
        } catch (Exception e) {
            map.put("result", "1");
            e.printStackTrace();
        }

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;

        return "success";
    }

    /**
     * 修改标题
     *
     * @return
     */
    public String editVideo() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            dspIndexSerivce.editVideo(videoId, video.getTitleName());
            map.put("result", "0");
        } catch (Exception e) {
            map.put("result", "1");
            e.printStackTrace();
            ;
        }

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo;

        return "success";


    }


    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getVideoID() {
        return videoId;
    }

    public void setVideoID(String videoId) {
        this.videoId = videoId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoStaffId() {
        return videoStaffId;
    }

    public void setVideoStaffId(String videoStaffId) {
        this.videoStaffId = videoStaffId;
    }

    public DSVideo getVideo() {
        return video;
    }

    public void setVideo(DSVideo video) {
        this.video = video;
    }

    public String getPricetype() {
        return priceType;
    }

    public void setPricetype(String priceType) {
        this.priceType = priceType;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public ProductOfVedio getPvedio() {
        return pvedio;
    }

    public void setPvedio(ProductOfVedio pvedio) {
        this.pvedio = pvedio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVodIds() {
        return vodIds;
    }

    public void setVodIds(String vodIds) {
        this.vodIds = vodIds;
    }

    public int getVideoPlayInfoResetMethod() {
        return videoPlayInfoResetMethod;
    }

    public void setVideoPlayInfoResetMethod(int videoPlayInfoResetMethod) {
        this.videoPlayInfoResetMethod = videoPlayInfoResetMethod;
    }
}