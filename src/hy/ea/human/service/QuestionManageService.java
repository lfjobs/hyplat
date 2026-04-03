package hy.ea.human.service;

import hy.ea.bo.human.question.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface QuestionManageService {

	public PageForm getQuestionBaseList(int pageNumber, int pageSize, String parameter, String companyID, String type);


	public List<BaseBean> getQueTypeList(String companyID);

	public PageForm getQuesTypePageList(int pageNumber, int pageSize, String parameter, String companyID);


	/**
	 * 修改获取详情
	 *
	 * @param tqID
	 * @return
	 */
	public TotalQuestion getTotalQues(String tqID);

	/**
	 * 类别
	 *
	 * @param qbtId
	 * @return
	 */
	public QuesBaseType getQuesTypeInfo(String qbtId);

	/**
	 * 保存题库
	 *
	 * @param totalQuestion
	 * @return
	 */
	public void saveQueBase(TotalQuestion totalQuestion, String companyID, String staffID);


	/**
	 * 保存题库
	 *
	 * @param quesBaseType
	 * @return
	 */
	public void saveQueType(QuesBaseType quesBaseType, String companyID, String staffID);


	/**
	 * 删除题目
	 *
	 * @param tqID
	 */
	public void delQues(String tqID);

	/**
	 * 删除题目分类
	 *
	 * @param qbtId
	 */

	public void delQuesType(String qbtId);


	/**
	 * 获取题
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 * @param tqID
	 * @return
	 */
	public PageForm getQuestionList(int pageNumber, int pageSize, String parameter, String tqID);

	/**
	 * 删除题目分类
	 *
	 * @param qrID
	 */

	public void delQuestions(String qrID);

	/**
	 * 保存题
	 *
	 * @param questions
	 * @param map
	 */
	public void saveQuestions(Questions questions, Map<Integer, QuestionsValue> map, String staffID, String companyID, String yscore);


	/**
	 * 修改获取详情
	 *
	 * @param qrID
	 * @return
	 */
	public Questions getQuesInfo(String qrID);

	/**
	 * 获取答题选项
	 *
	 * @param qrID
	 * @return
	 */
	public List<BaseBean> getQueValueList(String qrID);

	/**
	 * 获取上次填写的题目信息
	 *
	 * @param tqID
	 * @return
	 */
	public Questions getQuesLastInfo(String tqID);

	/**
	 * 答案
	 *
	 * @param qreID
	 * @return
	 */
	public List<BaseBean> getQueValueExamList(String qreID);
	/**
	 *
	 * 获取答题选项
	 * @param qrID
	 * @return
	 */
	public List<BaseBean> getKsValueExamList(String qrID);
	/**
	 * 获取考试
	 *
	 * @return
	 */
	public PageForm myQuesExam(int pageNumber, int pageSize, String staffID, String parameter);

	/*

    正式考试
     */
	public PageForm examList(int pageNumber, int pageSize, String companyID, String parameter, String type);

	/**
	 * @param tqeID
	 * @return
	 */
	public PageForm getPyQusList(int pageNumber, int pageSize, String tqeID, String quesType);


	/**
	 *
	 * 口试
	 * @param pageNumber
	 * @param pageSize
	 * @param tqID
	 * @return
	 */
	public PageForm getKsQusList(int pageNumber, int pageSize, String tqID);


	/**
	 * 人工审核
	 *
	 * @return
	 */
	public void manualAudit(String qreID, String tqeID, String staffID, int score);

	/**
	 * 获取考试人相关
	 *
	 * @return
	 */
	public ExamRelate getExam(String staffID, String tqeID);


	/**
	 * 修改获取详情
	 *
	 * @param tqeID
	 * @return
	 */
	public TotalQuestionExam getTotalQuesExam(String tqeID);


	public PageForm getQuestionExamList(int pageNumber, int pageSize, String tqeID, String staffID);

	/**
	 * 开始考试记录时间
	 *
	 * @param staffID
	 * @param tqeID
	 */
	public ExamRelate startRecord(String staffID, String tqeID);

	/**
	 * 收藏
	 *
	 * @param qrID
	 * @param qreID
	 */
	public void collectQuestion(String qrID, String qreID);

	/**
	 * 交卷
	 *
	 * @param tqeID
	 * @param tqID
	 */
	public void handPaper(String tqeID, String tqID, String staffID);


	/**
	 * 记录答案
	 *
	 * @param staffID
	 * @param examAnswer
	 * @param pageNumber
	 * @return
	 */
	public String recordAnswer(String staffID, ExamAnswer examAnswer, int pageNumber);


	/**
	 * 获取已答题未答题收藏情况
	 *
	 * @param tqeID
	 * @param staffID
	 * @return
	 */
	public List<Object> getQusStatusList(String tqeID, String staffID);


	/**
	 * 获取用户答题
	 *
	 * @param staffID
	 * @param qreID
	 * @param qrID
	 * @return
	 */
	public ExamAnswer getAnswer(String staffID, String qreID, String qrID);

	/**
	 * 获取是否收藏过
	 *
	 * @param qreID
	 * @param qrID
	 * @return
	 */
	public String getIsCollect(String qreID, String qrID);

	/**
	 * 退出后再次进入
	 *
	 * @param staffID
	 * @param tqeID
	 * @param tqID
	 */
	public void addQuesLast(String staffID, String tqeID, String tqID, int pageNumber);

	/**
	 * 获取最后的页面
	 *
	 * @param staffID
	 * @param tqeID
	 * @param tqID
	 * @return
	 */
	public int getPageLast(String staffID, String tqeID, String tqID);

	/**
	 * 获取时长
	 *
	 * @param tqeID
	 * @return
	 */
	public int getDuration(String tqeID);

	/**
	 * 获取成绩
	 *
	 * @param erId
	 * @return
	 */
	public ExamRelate getExamResult(String erId);
	/**
	 *
	 * 上传成绩
	 * @param erId

	 */
	public void  uploadScore(String erId);
}
