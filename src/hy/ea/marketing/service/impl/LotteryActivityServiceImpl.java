package hy.ea.marketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.lottery.*;
import hy.ea.marketing.service.LotteryActivitySerivce;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.*;
import hy.ea.util.bean.Bean;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 活动的发布，抽奖活动
 *
 * @author [mz]
 * @version [1.0, 2017-03-17]
 * @see
 * @since [抽奖模块]
 */
@Service
@Transactional
public class LotteryActivityServiceImpl implements LotteryActivitySerivce
{
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private GoldOrderService goldOrderService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private BaseBeanService baseBeanService;

    private Logger logger = Logger.getLogger(LotteryActivityServiceImpl.class);
    /**
     * 获取抽奖活动列表
     * @param pageNo 当前页数
     * @param pageSize 页数大小
     * @return 活动数据
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> getActivityList ( int pageNo , int pageSize)
    {
        if (pageNo>100)
        {
            pageNo=100;
        }
        Calendar tool_c = Calendar.getInstance();
        tool_c.add( Calendar.YEAR,-3-pageNo );//根据滚动次数增加年限查询上线
        Date parm_date = tool_c.getTime();
        List parms = new ArrayList();

        StringBuffer hql = new StringBuffer("select m from PrizeActivityBean m ");
        hql.append(" where m.status = ? and m.startingTime < ?");
        parms.add('0');
        parms.add(parm_date);
        int result_count = baseBeanDao.getConutByByHqlAndParams(
                "select count(*) from ("+hql.toString()+")",
                     parms.toArray());
        hql.append( " order by m.startingTime desc" );
        int [] fl =getPageFirstAndMax(pageNo,result_count,pageSize);
        List<BaseBean> result_list = baseBeanDao.getConutByByHqlAndParamsAndPage(hql.toString(),parms.toArray(),fl[0],fl[1]);

        return result_list;
    }

    /**
     * 分页
     * @param pageNumber
     * @param pageSize
     * @param flag
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getActivityPageForm (Integer pageNumber,Integer pageSize,String flag,String companyId)
    {
        PageForm pageForm = new PageForm();
        StringBuffer hql = new StringBuffer();
        hql.append(" from PrizeActivityBean");
        hql.append(" where trim(status) = ? and trim(activityType) = ? and companyId = ?");

        int count = baseBeanDao.getConutByByHqlAndParams(
                "select count(activityId)" + hql.toString(),
               new Object[]{"0",flag,companyId});

        hql.append(" order by startingTime desc");
        int [] f = getPageFirstAndMax(pageNumber,count,pageSize);
        List<BaseBean> beans = new ArrayList<BaseBean>();

        if (f != null){
          beans = baseBeanDao.getConutByByHqlAndParamsAndPage(hql.toString(),
                    new Object[]{"0",flag,companyId},f[0],f[1]);
        }
        pageForm.setList(beans);
        pageForm.setPageCount(f != null ? f[2] : 0);
        pageForm.setPageNumber(pageNumber);
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        return pageForm;
    }
    /**
     * 删除抽奖活动
     * @param activityId 活动id
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public boolean deletePrizeActivity( String activityId )
    {
        String hql = "update PrizeActivityBean m set m.status = ? where m.activityId = ? ";
        try {
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(
                    null,new String[]{hql},new Object[]{"9",activityId});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 保存抽奖活动设定
     * @param model 页面数据实体
     * @return 页面数据实体
     * @throws Exception 实体转换异常
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public  PrizeActivityModel savePrizeActivity( PrizeActivityModel model)
            throws Exception
    {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        StringBuffer hql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");

        if (model.getStrStartingTime() != null
                && model.getStrStartingTime().length() > 0)
        {
            model.setStartingTime(StringToTimestamp(model.getStrStartingTime()) );
        }
        if (model.getStrEndTime() != null
                && model.getStrEndTime().length() > 0)
        {
            model.setEndTime(StringToTimestamp(model.getStrEndTime()) );
        }

        PrizeActivityBean prize_bean = new PrizeActivityBean();
        if (model.getActivityId() != null && model.getActivityId().length() == 0)
        {
            model.setActivityKey(null);
            model.setActivityId(serverService.getServerID("activity"));
        }

        Bean.copyExistPropertis(model,prize_bean);
        if ( prize_bean.getStatus()!=null && prize_bean.getStatus().length()>0 )
        {
            prize_bean.setStatus(prize_bean.getStatus().trim());
        }
        if ( prize_bean.getActivityRange()!=null && prize_bean.getActivityRange().length()>0 )
        {
            prize_bean.setActivityRange(prize_bean.getActivityRange().trim());
        }
        //上传活动图片
        if (model.getActivityImg() != null
                && model.getActivityImg().length() > 0
                && model.getActivityImg().indexOf("base64") != -1)
        {
            String imagePath = Base64(model.getActivityImg(),model);
            String jjPath = imagePath.split("\\.")[0] + "small." + imagePath.split("\\.")[1];
            ImageCut.scale(path + imagePath, path + jjPath, 300, 331);
            prize_bean.setActivityImg(jjPath);
        }
        prize_bean.setStatus("0");
        beans.add(prize_bean);

        //奖品池与活动绑定
        if (model.getPoolId() != null && model.getPoolId().length() > 0)
        {
            String [] poolIds = model.getPoolId().split(",");
            hql.append("from PrizePoolBean where poolId in (");
            for (int i = 0; i < poolIds.length; i++)
            {
                hql.append("?,");
                params.add(poolIds[i]);
            }
            String temp_hql = hql.toString().substring(0,hql.toString().lastIndexOf(",")) + ")";
            List<BaseBean> list =baseBeanDao.getListBeanByHqlAndParams(temp_hql,params.toArray());

            for (int i = 0; i < list.size(); i++)
            {
                PrizePoolBean prizePoolBean = (PrizePoolBean) list.get(i);
                prizePoolBean.setActivityId(model.getActivityId());
                prizePoolBean.setStartingTime(model.getStartingTime());
                prizePoolBean.setEndTime(model.getEndTime());
                beans.add(prizePoolBean);
            }
        }

        //签到积分
        if (model.getScore() != null && model.getScore().length() > 0)
        {
            //判断该公司是否有积分
            if (model.getCompanyId() != null && model.getCompanyId().length() > 0)
            {
                TEshopCusCom cusCom = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(
                        "from TEshopCusCom where companyId = ?",
                        new Object[]{model.getCompanyId()});

                BonusPoints bonusPoints = (BonusPoints) baseBeanDao.getBeanByHqlAndParams(
                        "from BonusPoints where sccid = ?"
                        ,new Object[] {cusCom != null ? cusCom.getSccId() : ""}
                );
                if (bonusPoints == null)
                {
                    return null;
                }
            }

            SignScoreBean scoreBean = (SignScoreBean) baseBeanDao.getBeanByHqlAndParams(
                        "from SignScoreBean where activityId = ?",
                        new Object[] {model.getActivityId()});

            if (scoreBean == null)
            {
                scoreBean = new SignScoreBean();
                scoreBean.setActivityId(model.getActivityId());
                scoreBean.setScid(serverService.getServerID("signScore"));
            }
            scoreBean.setScore(Integer.valueOf(model.getScore()));
            if(model.getCount() != null && model.getCount().length() > 0){
                scoreBean.setCount(Integer.valueOf(model.getCount()));
            }
            beans.add(scoreBean);
        }
       /* if(model.getAddress() != null && model.getAddress().length() > 0){
            prize_bean.setAddress(model.getAddress());
        }*/
        //保存活动说明图片
        if (model.getImageDesc() != null && model.getImageDesc().length() > 0)
        {
            String [] images = model.getImageDesc().split("\\*\\*\\*");
            PrizeActivityDesc pad = null;
            for (int i = 0; i < images.length; i++)
            {
                pad = new PrizeActivityDesc();
                pad.setActivityDescId(serverService.getServerID("DescId"));
                pad.setActivityDescDate(new Date());
                pad.setActivityId(model.getActivityId());

                //压缩图片
                String image = Base64(images[i],model);
                String jjPath = image.split("\\.")[0] + "small." + image.split("\\.")[1];
                ImageCut.scale(path + image, path + jjPath, 414, 431);
                pad.setActivityDescImage(jjPath);
                beans.add(pad);
            }
        }
        baseBeanDao.executeHqlsByParmsList(beans,null,null);

        return model;
    }

    /**
     * 查询活动
     * @param model
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PrizeActivityModel getPrizeActivity(PrizeActivityModel model) throws Exception {
        PrizeActivityBean bean = (PrizeActivityBean) baseBeanDao.getBeanByHqlAndParams(
                "from PrizeActivityBean where activityId = ? ",new Object[]{model.getActivityId()}
        );
        PrizeActivityModel prizeActivityModel = new PrizeActivityModel();
        Bean.copyExistPropertis(bean,prizeActivityModel);

        //奖品池
        List<BaseBean> prizePool = baseBeanDao.getListBeanByHqlAndParams(
                "from PrizePoolBean where activityId = ?",
                new Object[] {model.getActivityId()}
        );
        prizeActivityModel.setPrizePool(prizePool);

        //活动说明图片
        List<BaseBean> prizeDesc = baseBeanDao.getListBeanByHqlAndParams(
                "from PrizeActivityDesc where activityId = ?",
                new Object[] {model.getActivityId()}
        );
        prizeActivityModel.setPrizeDesc(prizeDesc);

        //签到积分
        SignScoreBean scoreBean = (SignScoreBean) baseBeanDao.getBeanByHqlAndParams(
                "from SignScoreBean where activityId = ?",
                new Object[] {model.getActivityId()}
        );
        prizeActivityModel.setScore(scoreBean != null ? scoreBean.getScore().toString():"");
        prizeActivityModel.setCount(scoreBean != null ? scoreBean.getCount().toString():"");
        //票券信息
        List<BaseBean> ticket = baseBeanDao.getListBeanByHqlAndParams(
                "from Ticket where activityId = ?",
                new Object[] {model.getActivityId()}
        );
        prizeActivityModel.setTicket(ticket);
        return prizeActivityModel;
    }

    /**
     * 保存奖品池
     * @param ppb
     * @param model
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public PrizePoolBean savePrizePool(PrizePoolBean ppb,PrizeActivityModel model)
    {
        ppb.setPoolId(serverService.getServerID("poolId"));
        ppb.setCompanyId(model.getCompanyId());
        ppb.setStatus("0");
        try{
            baseBeanDao.save(ppb);
            return ppb;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 查询产品
     * @param pageNumber
     * @param pageSize
     * @param companyId 公司id
     * @param search
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getProPageForm(Integer pageNumber,Integer pageSize,String companyId,String search) {
        PageForm pageForm = new PageForm();
        StringBuffer sql = new StringBuffer();
        sql.append("select pp.image,ps.RE_PRICE,pp.ppid,pp.goodsname");
        sql.append(" from dt_ProductPackaging pp,dt_pro_setup ps where ps.ppid = pp.ppID");
        sql.append(" and ps.COM_ID = ? and pp.type not in(?,?,?,?,?,?,?) ");

        List<Object> params = new ArrayList<Object>();
        params.add(companyId);
        String [] temp = {"个人会员","公司会员","省级城市","县级城市","乡镇城市","村级新城","佣金分配代理类别"};
        Collections.addAll(params, temp);
        if (search!=null &&search.length() > 0)
        {
            sql.append("and ps.ppname like ?");
            params.add("%" + search + "%");
        }
        /*sql.append(" and not exists (select 1 from dt_prize_pool pr where pr.product_id = pp.ppid)");*/

        int count = baseBeanDao.getConutByBySqlAndParams("select count(*) from (" + sql.toString() + ")"
                ,params.toArray());
        sql.append(" order by pp.packagingDate desc");

        int [] f = getPageFirstAndMax(pageNumber,count,pageSize);
        List<BaseBean> beans = new ArrayList<BaseBean>();

        if (f != null){
            beans = baseBeanDao.getConutByBySqlAndParamsAndPage(sql.toString(),
                    params.toArray(),f[0],f[1]);
        }
        pageForm.setList(beans);
        pageForm.setPageCount(f != null ? f[2] : 0);
        pageForm.setPageNumber(pageNumber);
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        return pageForm;
    }

    /**
     * 查询奖品等级
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> getPrizeProbaBean() {
        List<BaseBean> list = new ArrayList<BaseBean>();
        list = baseBeanDao.getListBeanByHqlAndParams("from PrizeProbaBean where lotteryIndex <> ?"
                ,new Object[]{new Integer(0).longValue()});
        return list;
    }

    /**
     * 查询优惠券
     * @param companyId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getPrizeCouponBean(String companyId) {
        return null;
    }

    /**
     * 新增优惠券
     * @param model
     * @return
     */
    @Override
    public PrizeCouponBean savePrizeCouponBean(PrizeCouponBean model) {
        return null;
    }

    /**
     * 删除优惠券
     * @param couponId
     * @return
     */
    @Override
    public Boolean deleteCoupon(String couponId) {
        return null;
    }

    /**
     * 删除活动说明图片
     * @param model
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public Boolean deletePrizeDescImage(PrizeActivityModel model) {
        if (model != null && model.getPoolId().length() > 0)
        {
            //请求头
            String path = ServletActionContext.getRequest()
                    .getSession().getServletContext().getRealPath("/");

            PrizeActivityDesc desc = (PrizeActivityDesc) baseBeanDao.getBeanByHqlAndParams(
              "from PrizeActivityDesc where activityDescId = ?",
                    new Object[] {model.getPoolId()}
            );

            //判断图片路径是否存在,存在则删除
            //删除缩略图
            String dir = path +  desc.getActivityDescImage();
            File file = new File(dir);
            if (file.exists()) {
                FileUtil.delete(dir);
            }
            //删除原图
            String [] temp = desc.getActivityDescImage().split("\\.");
            String ori_dir = path + temp[0].substring(0,temp[0].length() - 5) + "." + temp[1];
            File file_ori = new File(ori_dir);
            if (file_ori.exists())
            {
                FileUtil.delete(ori_dir);
            }
            baseBeanDao.deleteBeanByKey(PrizeActivityDesc.class,desc.getActivityDescKey());
            return true ;
        }
        return  false;
    }

    /**
     * base64上传图片
     * @param image 图片
     * @param model
     * @return
     */
    public String Base64(String image,PrizeActivityModel model) {


        if(image.indexOf("jpeg") != -1){
            image= image.replace("jpeg","jpg");
        }

        // 图片存储路径
        String photoPath = "upload_files\\activity\\"
                + model.getCompanyId() + "\\";
        // 重命名
        String upName = UUID.randomUUID().toString()
                + System.currentTimeMillis() + "." + image.substring(image.indexOf("image/") + 6, image.indexOf(";base64"));

        String path = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("/");
        String dir = path + photoPath;
        File fileLocation = new File(dir);
        // 判断上传路径是否存在，如果不存在就创建
        if (!fileLocation.exists()) {
            boolean isCreated = fileLocation.mkdirs();
            if (!isCreated) {
                return "";
            }
        }
        // 重命名
        FileOutputStream out;
        String iconBase64 = image.substring(22);
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
            out = new FileOutputStream(dir + "/" + upName);
            out.write(buffer);
            out.close();
        } catch (FileNotFoundException e) {
            logger.error("操作异常", e);
            return "";
        } catch (IOException e) {
            logger.error("操作异常", e);
            return "";
        }

        return photoPath + upName;
    }

    /**
     * 时间格式转换string转换Timestamp
     * @param dateString
     * @return
     */
    public Timestamp StringToTimestamp (String dateString)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);// 设定格式
        dateFormat.setLenient(false);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        // java.sql.Timestamp dateTime = new java.sql.Timestamp(date.getTime());
        return new Timestamp(date.getTime());// Timestamp类型,timeDate.getTime()返回一个long型
    }



    /**
     * 封装获取分页结果计算
     * @param pageNumber 当前页
     * @param count 查询出来的结果集
     * @param pageSize 页数
     * @return 【0】 结果开始页 【1】结果结束页
     */
    private int[] getPageFirstAndMax( int pageNumber, int count , int pageSize)
    {
        if (count == 0)
        {
            return null;
        }
        int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
        if (pageNumber < 1)
        {
            pageNumber = 1;
        }
        if (pageNumber > pageCount)
        {
            pageNumber = pageCount;
        }
        int firstResult = pageSize * (pageNumber - 1);
        int maxResult = Math.min(pageSize, count - firstResult);
        return new int [] {firstResult,maxResult,pageCount};
    }

    /**
     * 判断公司是否有抽奖活动
     * @param companyId
     * @return
     */
    @Override
    public PrizeActivityModel isDraw(String companyId) {
        StringBuffer hql = new StringBuffer();
        hql.append("from PrizeActivityBean where trim(activityType) = ?");
        hql.append(" and companyId = ? and trim(status) = ?");
        hql.append(" order by startingTime desc");
        List<BaseBean> baseBeanList = baseBeanDao.getListBeanByHqlAndParams(
                hql.toString(),new Object[] {"0",companyId,"0"}
        );

        //获取当前系统时间和  活动表中的开始时间和结束时间比较
        Date date = DateUtil.string2Time(DateUtil.getCurrentDate());
        if (null != baseBeanList && baseBeanList.size() > 0)
        {
            PrizeActivityBean bean = (PrizeActivityBean)baseBeanList.get(0);
            Date startDate = bean.getStartingTime();
            Date endDate = bean.getEndTime();
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0)
            {
                PrizeActivityModel model = new PrizeActivityModel();
                try {
                    Bean.copyExistPropertis(bean,model);
                } catch (Exception e) {
                }
                return model;
            }
        }
        return null;
    }

    /**
     * 免费抽奖次数
     * @param cusCom
     * @param activityId
     * @return
     */
    @Override
    public Integer freeLotteryTimes(TEshopCusCom cusCom,String activityId) {
        int count = baseBeanDao.getConutByByHqlAndParams(
          "select count (recordId) from LotteryRecordBean where activityId = ? and lotteryStyle = ? and lotteryMan = ?",
                new Object[] {activityId,"免费抽取",cusCom.getStaffid()}
        );
        return count;
    }

    /**
     * 抽奖资格判断
     * @param activityId
     * @return
     */
    @Override
    public String qualification(String activityId) {

        //权限验证
        StringBuffer sql = new StringBuffer();
        sql.append("select s.staffid,s.staffname,s.reference,s.position,s.headimage");
        sql.append(" from dtcos c,dt_hr_staff s");
        sql.append(" where c.staffid = s.staffid");
        sql.append(" and c.companyid = ? and s.staffid = ? and c.organizationid != ? and c.cosstatus = ? and c.status=?");

        PrizeActivityBean pab = (PrizeActivityBean) baseBeanDao.getBeanByHqlAndParams(
                "from PrizeActivityBean where activityId = ?",
                new Object[] {activityId}
        );

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sessionWrap.getObject(session,SessionWrap.KEY_SHOPCUSCOM);

        int count = baseBeanDao.getConutByBySqlAndParams("select count(*) from (" + sql.toString() + ")",
                new Object[] {pab != null ? pab.getCompanyId() : ""
                        ,cusCom != null ? cusCom.getStaffid() : ""
                        ,"90","50","01"});

        //活动权限为公司内部，判断用户是否属于该公司
        if (pab != null && pab.getShowStatus() != null && ("1").equals(pab.getShowStatus().trim()))
        {
            if (count == 0)
            {
                //不属于该公司内部
                return "no";
            }
        }

        //判断抽奖次数，每个账户在活动期间有1次免费抽奖机会，再抽奖需要积分
        int lottery_count = baseBeanDao.getConutByByHqlAndParams(
                "select count(recordId) from LotteryRecordBean where activityId = ? and lotteryStyle = ? and lotteryMan = ?",
                new Object[] {pab != null ? pab.getActivityId() : "","免费抽取",cusCom.getStaffid()}
        );

        if (lottery_count >= 1)
        {
            //判断该账户是否含有大于抽奖需要的积分
            BonusPoints bp = (BonusPoints) baseBeanDao.getBeanByHqlAndParams(
                    "from BonusPoints where sccid = ?", new Object[] {cusCom != null ? cusCom.getSccId() : ""});
            if (bp == null || org.apache.commons.lang3.StringUtils.isEmpty(bp.getBonusPointScore())
                    || (bp != null && Float.valueOf(bp.getBonusPointScore()) < (Objects.nonNull(pab.getBonusPoints()) ? pab.getBonusPoints() : 1000)))
            {
                return "less";
            }
            return "more";
        }
        return "free";
    }

    /**
     * 查询奖品池
     * @param activityId
     * @return
     */
    @Override
    public List<BaseBean> getPrizePoolBean(String activityId)
    {
        StringBuffer hql = new StringBuffer();
        hql.append("select new PrizePoolBean(pp.prizeLvl,pp.ppName) from PrizePoolBean pp ,PrizeProbaBean ba");
        hql.append(" where pp.prizeLvl = ba.prizeLevel and pp.activityId = ?  order by ba.lotteryIndex ");

        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(
                hql.toString(),
                new Object[] {activityId}
        );
        return list;
    }

    /**
     * 生成award类
     * @param activityId
     * @return
     */
    public List<Award> getAward (String activityId)
    {
        List<Award> awards = new ArrayList<Award>();
        StringBuffer sql = new StringBuffer();
        sql.append("select new PrizePoolBean(ba.lotteryIndex,pp.prizeNum,ba.probaRate) from PrizePoolBean pp ,PrizeProbaBean ba");
        sql.append(" where pp.prizeLvl = ba.prizeLevel and pp.activityId = ?  order by ba.lotteryIndex ");
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(sql.toString(),new Object[]{activityId});
        Award award = null;
        int count = 0;
        for (int i = 0; i < list.size(); i++)
        {
            PrizePoolBean bean = (PrizePoolBean) list.get(i);
            award = new Award(bean.getLotteryIndex().toString()
                    ,bean.getProbaRate().floatValue()
                    ,bean.getPrizeNum().intValue());

            count += bean.getPrizeNum().intValue();
            awards.add(award);
        }

        //谢谢参与数量 为其他奖品数量总和的0.8；
        PrizeProbaBean probaBean = (PrizeProbaBean) baseBeanDao.getBeanByHqlAndParams(
                "from PrizeProbaBean where lotteryIndex = ?",
                new Object[] {new Integer(0).longValue()}
        );

        awards.add(
                new Award(probaBean.getLotteryIndex().toString()
                        ,probaBean.getProbaRate().floatValue() + 0.002f
                        , (int) Math.ceil(new Double(count * 0.5))));
        return awards;
    }

    /**
     * 实现抽奖算法，整个算法很简单，基本思想是根据概率和剩余奖品数量来分配一个空间，
     * 再在这个空间中取随机数，判断随机数落在哪个区块方法获得抽奖结果。
     * @return
     * @param model
     */
    @Override
    public String lottery(PrizeActivityModel model)
    {
        List<Award> awards = getAward(model.getActivityId());

        //总的概率区间
       // float totalPro = 0f;
        float totalPro = 0.0F;
        //存储每个奖品新的概率区间
        List<Float> proSection = new ArrayList<Float>();
       // proSection.add(0f);
        proSection.add(Float.valueOf(0.0F));
        //遍历每个奖品，设置概率区间，总的概率区间为每个概率区间的总和
        for (Award award : awards)
        {
        	//每个概率区间为奖品概率乘以1000（把三位小数转换为整）再乘以剩余奖品数量
        /*	totalPro += award.probability * 1000 * award.count;
        	proSection.add(totalPro);*/
            totalPro += award.probability * 1000.0F * award.count;
            proSection.add(Float.valueOf(totalPro));
        }
        Random random = new Random();

        if(totalPro>=1){
            //获取总的概率区间中的随机数
            float randomPro= (float)random.nextInt((int)totalPro);
            //判断取到的随机数在哪个奖品的概率区间中
            for (int i = 0,size = proSection.size(); i < size; i++)
            {
                if(randomPro >= proSection.get(i)
                        && randomPro < proSection.get(i + 1))
                {
                    if (Integer.parseInt(awards.get(i).id) == 0)
                    {
                        return randomEven(awards.size() - 1).toString();
                    }
                    return awards.get(i).id;
                }
            }
        }else{
            return "end";
        }
        return null;
    }

    /**
     * 保存抽奖记录
     * @param flag
     * @param model
     * @param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LotteryRecordBean saveLotteryRecord (TEshopCusCom cusCom,String flag,PrizeActivityModel model)
    {
        //保存抽奖记录
        LotteryRecordBean recordBean = new LotteryRecordBean();
        recordBean.setLotteryMan(model.getStaffId());
        if ("free".equals(flag))
        {
            recordBean.setLotteryStyle("免费抽取");
        }
        else if ("more".equals(flag))
        {
            recordBean.setLotteryStyle("积分抽取");
            //收积分公司
            TEshopCusCom colCuscom = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(
                    "from TEshopCusCom where companyId = ?",
                    new Object[] {model.getCompanyId()}
            );
            logger.error("积分扣费sccId:" + cusCom.getSccId());
            logger.error("积分收费sccId:" + colCuscom.getSccId());
            //积分扣减
            PrizeActivityBean prizeActivityBean = (PrizeActivityBean) baseBeanDao.getBeanByHqlAndParams(
                    "from PrizeActivityBean where activityId = ?", new Object[] {model.getActivityId()}
            );
            Integer bonusPoints = 1000;
            if(Objects.nonNull(prizeActivityBean) && Objects.nonNull(prizeActivityBean.getBonusPoints())){
                bonusPoints = prizeActivityBean.getBonusPoints();
            }
            Integer money = bonusPoints.intValue() / 100;
            if(money == 0){
                money = 10;
            }
            goldOrderService.lotteryCharge(cusCom.getSccId(),colCuscom.getSccId(),new BigDecimal(money));
        }
        logger.error("数值"+model.getIndex());
        recordBean.setLotteryResult(Integer.valueOf(model.getIndex()) % 2 == 0 ? "未中" : "中奖");
        recordBean.setRecordDate(DateUtil.string2Time(DateUtil.getCurrentDate()));
        recordBean.setActivityId(model.getActivityId());
        baseBeanDao.save(recordBean);
        return  recordBean;
    }

    /**
     * 保存中奖记录
     * @param index
     * @param model
     * @param recordBean
     * @return
     */
    @Override
    public PrizePoolBean saveWinRecord (String index, PrizeActivityModel model, LotteryRecordBean recordBean)
    {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        StringBuffer hql = new StringBuffer();
        hql.append("select pp from PrizePoolBean pp ,PrizeProbaBean pa");
        hql.append(" where pp.prizeLvl = pa.prizeLevel and pp.activityId = ?");
        hql.append(" and pa.lotteryIndex = ?");

        PrizePoolBean poolBean = null;
        //中奖记录
        if ("中奖".equals(recordBean.getLotteryResult()))
        {
            WinningRecordBean winningRecordBean = new WinningRecordBean();
            winningRecordBean.setRecordId(recordBean.getRecordId());
            winningRecordBean.setOrderNum(" ");

            poolBean  = (PrizePoolBean) baseBeanDao.getBeanByHqlAndParams(
                    hql.toString()
                    ,new Object[]{model.getActivityId(),Long.valueOf(index)}
            );
            winningRecordBean.setPoolId(poolBean != null ? poolBean.getPoolId() : "");
            winningRecordBean.setIsDraws("0");
            winningRecordBean.setWinDate(DateUtil.string2Time(DateUtil.getCurrentDate()));

            //中奖后相应奖品池内数量变化
            if(poolBean.getPrizeNum()>0){
                poolBean.setPrizeNum((poolBean.getPrizeNum() - new Long (1).longValue()));
            }else{

            }
            beans.add(poolBean);
            beans.add(winningRecordBean);
            baseBeanDao.executeHqlsByParmsList(beans,null,null);

        }
        return poolBean;
    }

    /**
     * 查询中奖记录
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageForm pageFormWinRecords(Integer pageNumber,Integer pageSize,String lotteryMan,String activityId) {

        PageForm pageForm = new PageForm();
        StringBuffer sql = new StringBuffer();
        sql.append(" select w.record_id,p.pp_name,p.product_id,w.isdraws,w.winDate,replace(t.account,SUBSTR(t.account,4,4) ,'****') account");
        sql.append(" from dt_prize_pool p,dt_winning_record w,DT_LOTTERY_RECORD l,t_eshop_cuscom t");
        sql.append(" where p.pool_id = w.pool_id and l.record_id=w.record_id and l.lottery_man = t.staffid");
        sql.append(" and p.ACTIVITY_ID = ?");
        if(StringUtils.isNotBlank(lotteryMan)) {
            sql.append(" and l.lottery_man = ?");
        }
        sql.append(" and l.lottery_result='中奖'");

        int count = 0;
        if(StringUtils.isNotBlank(lotteryMan)) {
            count = baseBeanDao.getConutByBySqlAndParams("select count(*) from (" + sql.toString() + ")"
                    ,new Object[]{activityId,lotteryMan});
        }else {
            count = baseBeanDao.getConutByBySqlAndParams("select count(*) from (" + sql.toString() + ")"
                    ,new Object[]{activityId});
        }

        int [] f = getPageFirstAndMax(pageNumber,count,pageSize);
        sql.append(" order by winDate desc");
        List<BaseBean> beans = new ArrayList<BaseBean>();

        if (f != null)
        {
            if(StringUtils.isNotBlank(lotteryMan)) {
                beans = baseBeanDao.getConutByBySqlAndParamsAndPage(sql.toString(),new Object[]{activityId,lotteryMan},f[0],f[1]);
            }else {
                beans = baseBeanDao.getConutByBySqlAndParamsAndPage(sql.toString(),new Object[]{activityId},f[0],f[1]);
            }
        }

        pageForm.setList(beans);
        pageForm.setPageCount(f!=null ? f[2]: 0);
        pageForm.setPageNumber(pageNumber);
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        return pageForm;
    }

    /**
     * 滚动中奖纪录
     * @return
     */
    @Override
    public List<BaseBean> rollingRecords(String activityId) {

        StringBuffer sql = new StringBuffer();
        sql.append("select t.account, p.pp_name,w.winDate from dt_winning_record w,dt_lottery_record l,t_eshop_cuscom t,");
        sql.append(" dt_prize_pool p where w.record_id = l.record_id and w.pool_id = p.pool_id");
        sql.append(" and l.lottery_man = t.staffid" +
                " and rownum > 0 and rownum <= 10000" +
                " and l.lottery_result='中奖' and p.ACTIVITY_ID = ?" +
                " order by w.windate");

        List<BaseBean> list = new ArrayList<BaseBean>();
        list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),new Object[]{activityId});

        //将电话号码中间4位替换为150****4152
        for (int i = 0; i < list.size(); i++)
        {
            Object object = list.get(i);
            Object [] obj = (Object[]) object;
            StringBuffer sb = new StringBuffer(obj[0].toString());
            String temp = sb.replace(3,7,"****").toString();
            obj[0] = temp ;
        }
        return list;
    }

    /**
     * 中奖记录及订单保存
     * @param recordBean
     * @param cusCom
     * @param poolBean
     */
/*    public synchronized  void generatedRecords (LotteryRecordBean recordBean,TEshopCusCom cusCom,PrizePoolBean poolBean)
    {
        List<BaseBean> beans = new ArrayList<BaseBean>();

        //产品信息
        ProductPackaging ppk = new ProductPackaging();
        ppk = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams(
                "from ProductPackaging where ppid=?", new String[]{poolBean.getProductId()});

        //发货地址查询
        RefundAddress address = (RefundAddress) baseBeanDao
                .getBeanByHqlAndParams("from RefundAddress where companyId = ? and rtype = ? and status = ?",
                        new Object[]{ppk.getCompanyID(), 1, "00"});

        //收货人信息
        StaffAddress staffAddress = (StaffAddress) baseBeanDao.getBeanByHqlAndParams(
                "from StaffAddress where staffID = ? and isDefault = ?",
                    new Object[]{cusCom.getStaffid(),"是"});

        //奖品归属的公司
        Company company = (Company) baseBeanDao.getBeanByHqlAndParams(
                "from Company where companyID = ?"
                , new Object[] {ppk.getCompanyID()}
        );

        //奖品归属的公司往来单位id
        CcomCom ccomCom = (CcomCom) baseBeanDao.getBeanByHqlAndParams(
          "from CcomCom where comanyId = ?",
                new Object[] {company.getCompanyID()}
        );

        //销售库
        DepotManage wh = (DepotManage) baseBeanDao.getBeanByHqlAndParams(
                "from DepotManage where companyID = ? and depotName= ?"
                , new Object[]{ppk.getCompanyID(), "销售库"});

        CashierBills cb = new CashierBills();
        cb.setCashierBillsID(serverService.getServerID("CashierBills"));
        cb.setOrganizationID("organization20110425U539EJC3VF0000012237");
        cb.setCashierDate(new Date());
        cb.setStaffName("系统生成");
        cb.setInputName("系统生成");
        cb.setStatus("40");
        cb.setWfStatus2("00");
        cb.setFkStatus("00");//抽奖奖品已付款
        cb.setPriceSub("0");//抽奖奖品为0元
        cb.setDepartmentName("客户");
        cb.setZctype("cg");
        cb.setProjectName(ppk.getGoodsName());
        cb.setContactUserID(cusCom.getStaffid());
        cb.setBillsType("项目收入预算单");
        cb.setCompanyName(company.getCompanyName());
        cb.setCompanyID(ppk.getCompanyID());
        cb.setJournalNum(serverService.getBillID(cb.getCompanyID()));
        cb.setjNumOrder(cb.getJournalNum());
        cb.setStatusbill("04");
        cb.setCcompanyID(ccomCom.getCcompanyId());
        beans.add(cb);


        // 订单地址关联
        DtOrderBillAdd dl = new DtOrderBillAdd();
        dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
        dl.setOaComId(cb.getCompanyID());
        dl.setOaSccId(cusCom.getSccId());

        if (staffAddress != null)
        {
            dl.setReceivename(staffAddress.getConsignee());
            dl.setReceivecode(staffAddress.getPostCode());
            dl.setReceivetel(staffAddress.getPhone());
            dl.setReceiveaddress(staffAddress.getArea() + staffAddress.getAddressDetailed());
            dl.setShDate(new Date());
        }

        //发货信息
        if (address != null)
        {
            dl.setSendname(address.getRname());
            dl.setSendtel(address.getRphone());
            dl.setSendcode(address.getRpostcode());
            dl.setSendaddress(address.getRarea() + address.getRstreet());
            dl.setFhDate(new Date());
        }

        dl.setOaBillId(cb.getCashierBillsID());
        dl.setOaBillJounum(cb.getJournalNum());
        dl.setOaGysId(ppk.getCompanyID());
        dl.setXdDate(new Date());
        dl.setFkDate(new Date());
        beans.add(dl);

        GoodsBills gbs = new GoodsBills();
        gbs.setCashierBillsID(cb.getCashierBillsID());
        gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
        gbs.setCompanyID(ppk.getCompanyID());
        gbs.setTypeID(ppk.getType());
        gbs.setDepotID(wh.getDepotID());
        gbs.setDepotName(wh.getDepotName());
        gbs.setStartDate(new Date());
        gbs.setPrice("0");
        gbs.setMoney("0");
        gbs.setQuantity("1");
        gbs.setRealMoney("0");
        gbs.setGoodsID(ppk.getGoodsID());
        gbs.setGoodsName(ppk.getGoodsName());
        gbs.setStandard("");
        gbs.setPpID(ppk.getPpID());
        gbs.setBoxStandard(ppk.getModel());
        gbs.setEntryTime(new Date());
        gbs.setGoodsNum(ppk.getProductCode());
        gbs.setCostmoney("0");
        beans.add(gbs);
        baseBeanDao.executeSqlsByParmsList(beans,null,null);
    } */

    /**
     * 随机偶数
     * Math.random()*(n-m)+m);
     * 返回指定范围的随机数(m-n之间)的公式
     * @return
     */
    public Integer randomEven (int top)
    {
        Integer [] evens = {2,4,6,8,10,12};
        int number = (int)(Math.random() * top);
        return evens[number];
    }

    /**
     * 保存会议活动
     */
    public boolean saveMeetingActivity(PrizeActivityModel model,String tickets,String content ){
        boolean flag = true;
        try {
            //给PrizeActivity表添加数据
            List<BaseBean> beans = new ArrayList<BaseBean>();
            String path = ServletActionContext.getRequest()
                    .getSession().getServletContext().getRealPath("/");
            PrizeActivityBean pab = new PrizeActivityBean();
            if (model.getStrStartingTime() != null
                    && model.getStrStartingTime().length() > 0)
            {
                model.setStartingTime(StringToTimestamp(model.getStrStartingTime()));
                pab.setStartingTime(StringToTimestamp(model.getStrStartingTime()));
            }
            if (model.getStrEndTime() != null
                    && model.getStrEndTime().length() > 0)
            {
                pab.setEndTime(StringToTimestamp(model.getStrEndTime()) );
                model.setEndTime(StringToTimestamp(model.getStrEndTime()) );
            }
            String activityId = "";
            if (model.getActivityId() != null && model.getActivityId().length() > 0)
            {
                activityId = model.getActivityId();
                pab.setActivityKey(model.getActivityKey());
            }else{
                activityId = serverService.getServerID("activity");
            }
            pab.setActivityId(activityId);
            pab.setAddress(model.getAddress());
            pab.setActivityName(model.getActivityName());
            pab.setCompanyId(model.getCompanyId());
            pab.setActivityRange("0"); //全网
            pab.setActivityType("2"); //会议活动
            pab.setStatus("0");      //发布
            pab.setShowStatus("0");  //完全公开
            //上传活动图片
            if (model.getActivityImg() != null
                    && model.getActivityImg().length() > 0
                    && model.getActivityImg().indexOf("base64") != -1)
            {
                String imagePath = Base64(model.getActivityImg(),model);
                String jjPath = imagePath.split("\\.")[0] + "small." + imagePath.split("\\.")[1];
                ImageCut.scale(path + imagePath, path + jjPath, 300, 331);
                pab.setActivityImg(jjPath);
            }
            beans.add(pab);
            //给会议活动相关的票券表添加数据
            String[] strTicket = tickets.substring(0, tickets.length()-1).split("@");
            for (int i = 0; i < strTicket.length; i++) {
                String[] ss = strTicket[i].split(",");
                Ticket ticket = new Ticket();
                if(ss[0] != null && ss[0].length() == 0){
                    ticket.setTid(ss[0]);
                    ticket.setTid(ss[1]);
                }else{
                    ticket.setTid(serverService.getServerID("ticket"));
                }
                ticket.setTname(ss[2]);
                ticket.setTprice(ss[3]);
                ticket.setTcount(ss[4]);
                ticket.setActivityId(activityId);
                beans.add(ticket);
            }
            //给会议活动相关的活动描述表PrizeActivityDesc表添加信息
            PrizeActivityDesc prizeDesc = (PrizeActivityDesc)this.baseBeanService.getBeanByHqlAndParams("from PrizeActivityDesc where ActivityId=?", new Object[] { activityId });
            if (prizeDesc == null)
            {
                prizeDesc = new PrizeActivityDesc();
                prizeDesc.setActivityDescId(this.serverService.getServerID("DescId"));
                prizeDesc.setActivityId(activityId);
            }
            prizeDesc.setActivityDescDate(new Date());
            String url = saveContentToFile(content);
            prizeDesc.setActivityDescImage(url);
            beans.add(prizeDesc);
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
        }catch (Exception e){
            logger.error("操作异常", e);
            flag = false;
        }
        return flag;
    }

    //查询本公司所有没有作废的会议活动
    public Boolean selMeetingActivity(String companyId){
        Boolean flag = false;
        StringBuffer hql = new StringBuffer();
        hql.append("from PrizeActivityBean where trim(activityType) = ?");
        hql.append(" and companyId = ? and trim(status) = ?");

        List<BaseBean> bean =  baseBeanDao.getListBeanByHqlAndParams(
                hql.toString(),new Object[] {"2",companyId,"0"}
        );
        //获取当前系统时间和  活动表中的开始时间和结束时间比较
        Date date = DateUtil.string2Time(DateUtil.getCurrentDate());
        if (bean != null)
        {
            for(int i = 0 ; i < bean.size(); i++){
                Date startDate = ((PrizeActivityBean)bean.get(i)).getStartingTime();
                Date endDate = ((PrizeActivityBean)bean.get(i)).getEndTime();
                if (date.compareTo(startDate) <= 0 && date.compareTo(endDate) <= 0)
                {
                    flag = true;
                    return flag;
                }
            }
        }
        return flag;
    }
    //查询本公司抽奖活动有没有过期
    public Boolean selprizeActivity(String companyId){
        Boolean flag = false;
        StringBuffer hql = new StringBuffer();
        hql.append("from PrizeActivityBean where trim(activityType) = ?");
        hql.append(" and companyId = ? and trim(status) = ?");

        List<BaseBean> bean =  baseBeanDao.getListBeanByHqlAndParams(
                hql.toString(),new Object[] {"0",companyId,"0"}
        );
        //获取当前系统时间和  活动表中的开始时间和结束时间比较
        Date date = DateUtil.string2Time(DateUtil.getCurrentDate());
        if (bean != null)
        {
            for(int i = 0 ; i < bean.size(); i++){
                Date startDate = ((PrizeActivityBean)bean.get(i)).getStartingTime();
                Date endDate = ((PrizeActivityBean)bean.get(i)).getEndTime();
                if (date.compareTo(endDate) <= 0)
                {
                    flag = true;
                    return flag;
                }
            }
        }
        return flag;
    }

    @Override
    public Boolean signPrizeActivity(String companyId) {
        Boolean flag = false;
        StringBuffer hql = new StringBuffer();
        hql.append("from PrizeActivityBean where trim(activityType) = ?");
        hql.append(" and companyId = ? and trim(status) = ?");

        List<BaseBean> bean =  baseBeanDao.getListBeanByHqlAndParams(
                hql.toString(),new Object[] {"1",companyId,"0"}
        );
        //获取当前系统时间和  活动表中的开始时间和结束时间比较
        Date date = DateUtil.string2Time(DateUtil.getCurrentDate());
        if (bean != null)
        {
            for(int i = 0 ; i < bean.size(); i++){
                Date startDate = ((PrizeActivityBean)bean.get(i)).getStartingTime();
                Date endDate = ((PrizeActivityBean)bean.get(i)).getEndTime();
                if (date.compareTo(endDate) <= 0)
                {
                    flag = true;
                    return flag;
                }
            }
        }
        return flag;
    }

    //处理图像，视频，音乐等文件的方法
    private String saveContentToFile(String content) {
        String id = "meetingActivity" + RandomDatas.getUUID();
        String path = ServletActionContext.getServletContext().getRealPath("")
                + "/upload_files/meetingActivity/";
        try {
            contentToFileService.saveContent(id, content, path);
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        return "/upload_files/meetingActivity/" + id
                + UploadContentToFileService.suffix;
    }

    /**
     * 分页查询会议活动
     */
    public PageForm selMeetingPage(int pageNumber){
        int pageSize = 4;
        PageForm pageForm = null;
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select pa.activity_id,pa.activity_name,pa.activity_img,pa.starting_time,pa.activity_desc,pa.activity_address");
        sql.append(" from dt_prize_activity pa where trim(pa.activity_type) = ? ");
        sql.append(" and trim(pa.activity_range) = ? and trim(pa.status) = ?");
        sql.append(" and pa.starting_time > ? ");
        sql.append(" order by pa.starting_time desc");
        params.add("2");
        params.add("0");
        params.add("0");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date());
        params.add(Utilities.getDateFromString(date,"yyyy-MM-dd HH:ss:mm"));
        int count = baseBeanDao.getConutByBySqlAndParams("select count(*) from (" + sql.toString() + ")"
                ,params.toArray());
        int pageCount = count%pageSize==0?count/pageSize:(count/pageSize+1);
        if(pageNumber > pageCount|| pageNumber <= 0){
            pageForm = null;
        }else{
            pageForm = baseBeanService.
                    getPageFormBySQL(pageNumber,pageSize,sql.toString(),"select count(*) from ("+sql.toString()+")",params.toArray());
        }
        return pageForm;
    }

    /**
     * 查询会议活动的详情
     */
    public Object[] selMeetingDetail(String activityId){
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select pa.activity_id,pa.activity_img,pa.activity_name,pa.activity_address");
        sql.append(" ,c.companyname,pd.activitydescimage,pa.starting_time,pa.end_time");
        sql.append(" from dt_prize_activity pa,dt_prize_activitydesc pd,dtcompany c");
        sql.append(" where pa.activity_id = pd.activityid and pa.company_id = c.companyid and pa.activity_id = ?");
        params.add(activityId);
        List<Object> listMeeting = baseBeanService.getListBeanBySqlAndParams(sql.toString(),params.toArray());
        Object[] meetingDetail = new Object[9];
        Object[] o1 = (Object[])listMeeting.get(0);
        for(int i = 0; i< 6; i++){
            meetingDetail[i] = o1[i];
        }
        //根据地址获取内容
        meetingDetail[5] = getContentFromFile(o1[5]==null?"":o1[5].toString());
        //获取页面中需要的时间格式并转换为字符串
        Date[] date = new Date[2];
        date[0] = (Date)o1[6];
        date[1] = (Date)o1[7];
        String strDate = new SimpleDateFormat("MM/dd HH:ss").format(date[0]);
        String endDate = new SimpleDateFormat("MM/dd HH:ss").format(date[1]);
        meetingDetail[6] = strDate;
        meetingDetail[7] = endDate;
        sql.delete(0,sql.length());
        //获取价格值并转换为页面中需要的格式
        sql.append("select t.tprice,pa.activity_id from dt_ticket t,dt_prize_activity pa");
        sql.append(" where pa.activity_id = t.activityid and pa.activity_id = ?");
        List<Object> listTicket = baseBeanService.getListBeanBySqlAndParams(sql.toString(),params.toArray());
        int[] array = new int[listTicket.size()];
        for (int i = 0; i < listTicket.size() ; i++){
            Object[] o= (Object[])listTicket.get(i);
            array[i] = Integer.parseInt((String) o[0]);
        }
        int min = array[0];
        int max = array[0];
        for (int i = 0; i< array.length; i++){
            if(array[i] != 0){
                int temp = array[i];
                if(min >= temp){
                    min = temp;
                }
                if(max <= temp){
                    max = temp;
                }
            }
        }
        String  price = min+"-"+max;
        meetingDetail[8] = price;
        return meetingDetail;
    }

    /**
     * 查询票券活动详情
     */
    public List<Object> TicketDetail(String activityId){
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select pa.activity_id,pa.activity_name,t.tname,t.tprice,t.tcount from dt_ticket t,dt_prize_activity pa");
        sql.append(" where pa.activity_id = t.activityid and pa.activity_id = ?");
        params.add(activityId);
        List<Object> listTicket = baseBeanService.getListBeanBySqlAndParams(sql.toString(),params.toArray());
        return listTicket;
    }

    /*-----xgb-----*/
    /**
     * 根据txt URL 获取内容
     *
     * @param @url
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
            logger.error("操作异常", e);
            return "";
        }
    }

}
