package hy.ea.driving.service;

import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherEvaluate;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/20 0020.
 */
public interface StudentService {
    /**
     *
     * @param teacherId 教练id
     * @param pageForm
     * @param pageSize
     * @return
     */
    public PageForm getStuAppraise(String teacherId , PageForm pageForm, int pageSize) ;

    /**
     *
     * @param teacherId 教练id
     * @param pageForm
     * @param pageSize
     * @return
     */
    public PageForm getAppraise(String teacherId , PageForm pageForm, int pageSize,String evaluateType) ;

    /**
     * 添加评论
     * @param tbly
     * @param photo
     * @return
     */
    public Boolean AddAppraise(TbElycTeacherEvaluate tbly,String photo) ;

    /**
     * 获取教练评论数
     * @param teacherId
     * @return
     */
    public Map<String,String> getPunLunShu(String teacherId );

    /**
     * 获取图片集合
     */
    public Map<String,List> getPicpath(PageForm pg);

}



