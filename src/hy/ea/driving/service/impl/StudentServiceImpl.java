package hy.ea.driving.service.impl;

import hy.ea.bo.DrivingSchool.elyc.TbElycOrderRecord;
import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherEvaPic;
import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherEvaluate;
import hy.ea.driving.service.StudentService;
import hy.ea.util.ImageCut;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/9/20 0020.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
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
     *
     * @param teacherId 教练id
     * @param pageForm
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getStuAppraise(String teacherId , PageForm pageForm, int pageSize) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select tea.studentid,tea.teacherid,tea.totalscore,tea.servicescore,tea.teachlevescore, ");
        sb.append(" tea.evaluatecontent,tea.evaluatedate,tea.orderrecordid,pic.picpath from ");
        sb.append("  Tb_Elyc_TeacherEva_Pic pic, Tb_Elyc_Teacher_Evaluate tea  where tea.etid=pic.eacherevaluatetid and tea.teacherid= ? order by tea.evaluatedate");
        PageForm pg=getPageFormBySQL(null != pageForm ? pageForm.getPageNumber() : 1,pageSize == 0 ? 10 : pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")", new Object[]{teacherId});

        return pg;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getAppraise(String teacherId, PageForm pageForm, int pageSize, String evaluateType) {

        List<Object> list =new ArrayList<>();
        list.add(teacherId);
        StringBuilder sb = new StringBuilder();
        sb.append(" select tea.studentid,tea.teacherid,tea.totalscore,tea.servicescore,tea.teachlevescore, ");
        sb.append(" tea.evaluatecontent,to_char(tea.evaluatedate,'YYYY-MM-DD'),tea.orderrecordid,tea.etid from ");
        sb.append("  Tb_Elyc_Teacher_Evaluate tea  where tea.teacherid = ?" );

        if(evaluateType.equals("3")) {
            sb.append("  order by tea.evaluatedate desc");
        }else {
            sb.append(" and tea.evaluatetype= ? order by tea.evaluatedate");
            list.add(evaluateType);
        }
        PageForm pg=getPageFormBySQL(null != pageForm ? pageForm.getPageNumber() : 1,pageSize == 0 ? 10 : pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")", list.toArray());


        return pg;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Boolean AddAppraise(TbElycTeacherEvaluate tbly ,String photo) {
        Boolean bo=true;
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
            TbElycTeacherEvaluate tb=new TbElycTeacherEvaluate();
            tb.setEtId(serverService.getServerID("evaluate"));
            tb.setStudentId(tbly.getStudentId()); //学员id
            tb.setTeacherId(tbly.getTeacherId());  //教练id
            tb.setTotalScore((tbly.getServiceScore()+tbly.getTeachleveScore())/2);  //平均星星数
            tb.setServiceScore(tbly.getServiceScore());
            tb.setTeachleveScore(tbly.getTeachleveScore());
            tb.setEvaluateContent(tbly.getEvaluateContent());
            String type="";
            if(tbly.getEvaluateType().equals("好评")){
                type="0";
            }else if(tbly.getEvaluateType().equals("中评")){
                type="1";
            }else {
                type="2";
            }
            tb.setEvaluateType(type);
            tb.setEvaluateDate(new Date());
            baseBeanList.add(tb);
            //保存公司详情
            if(!photo.equals("") && photo != null) {
                if(photo.indexOf("lkilklk") != -1){
                    String [] photoStr=photo.split("lkilklk");
                    for (int i = 0; i < photoStr.length; i++) {
                        String str=Base64(photoStr[i]);
                        TbElycTeacherEvaPic tbElyc = new TbElycTeacherEvaPic();
                        tbElyc.setEtpId(serverService.getServerID("picId"));
                        tbElyc.setEacherEvaluatetId(tb.getEtId());
                        tbElyc.setPicpath(str);
                        baseBeanList.add(tbElyc);
                    }
                 }else{
                    String str=Base64(photo);
                    TbElycTeacherEvaPic tbElyc = new TbElycTeacherEvaPic();
                    tbElyc.setEtpId(serverService.getServerID("picId"));
                    tbElyc.setEacherEvaluatetId(tb.getEtId());
                    tbElyc.setPicpath(str);
                    baseBeanList.add(tbElyc);
                }
            }

            //修改记录
            String hql = "from TbElycOrderRecord where etoId = ?";
           TbElycOrderRecord record =  (TbElycOrderRecord)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{tbly.getOrderRecordId()});
            if(record!=null){
                record.setStatus("5");
                baseBeanList.add(record);
            }
            beandao.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);

        return bo;
    }

    @Override
    public Map<String, String> getPunLunShu(String teacherId) {
       Map<String,String> map=new HashMap<String,String>();
        int quanbu=  beandao.getConutByBySqlAndParams(" select count(te.etkey) from Tb_Elyc_Teacher_Evaluate te where  te.teacherid= ?",new Object[]{teacherId});
        int haoping=  beandao.getConutByBySqlAndParams(" select count(te.etkey) from Tb_Elyc_Teacher_Evaluate te where  te.teacherid= ? and te.evaluatetype='0'",new Object[]{teacherId});
     int zhongping= beandao.getConutByBySqlAndParams(" select count(te.etkey) from Tb_Elyc_Teacher_Evaluate te where  te.teacherid= ? and te.evaluatetype='1'",new Object[]{teacherId});
     int chaping=  beandao.getConutByBySqlAndParams(" select count(te.etkey) from Tb_Elyc_Teacher_Evaluate te where  te.teacherid= ? and te.evaluatetype='2'",new Object[]{teacherId});
        map.put("quanbu",String.valueOf(quanbu));
        map.put("haoping",String.valueOf(haoping));
        map.put("zhongping",String.valueOf(zhongping));
        map.put("chaping",String.valueOf(chaping));

        return map;
    }

    @Override
    public Map<String, List> getPicpath(PageForm pg) {
        Map<String,List> map=new HashMap<>();
        if(pg == null || pg.getList() == null){
            return null;
        }
        for (int i = 0; i < pg.getList().size(); i++) {
            Object obj=pg.getList().get(i);
            Object [] objects= (Object[]) obj;
            String etid= (String) objects[8];
            List<Object> pobject=  beandao.getListObjectBySqlAndParams(" select pp.picpath from Tb_Elyc_TeacherEva_Pic pp where pp.eacherevaluatetid= ? ",new Object[]{etid});
            if(pobject !=null && pobject.size() != 0  ){
                map.put(etid,pobject);
            }
        }
        return map;
    }

    /**
     * base64上传图片
     * @param image 图片
     * @param @model
     * @return
     */
    public static String Base64(String image) {


        if(image.indexOf("jpeg") != -1){
            image= image.replace("jpeg","jpg");
        }

        // 图片存储路径
        String photoPath = "upload_files/student/";
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
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return photoPath + upName;
    }
}
