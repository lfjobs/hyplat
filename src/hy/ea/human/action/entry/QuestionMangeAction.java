package hy.ea.human.action.entry;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.question.*;
import hy.ea.human.service.QuestionManageService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题库相关
 * @author Administrator
 *
 */
public class QuestionMangeAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private QuestionManageService quesManageService;

	private QuesBaseType quesBaseType;

	private Questions questions;
	private Questions questionslast;
	private Map<Integer,QuestionsValue> valueMap;
	private PageForm pageForm;
	private int pageNumber;
	private int pageSize;
	private String result;
	private String parameter;
	private String type;
	private TotalQuestion totalQuestion;
	private TotalQuestionExam totalQuestionExam;

	private QuestionsExam questionsExam;

    private ExamRelate examRelate;

	private ExamAnswer examAnswer;

	private List<BaseBean> list;
	private File personImageInfo;
	private String personImageInfoFileName;
	HttpSession session = ServletActionContext.getRequest().getSession();
	CAccount account = (CAccount)session.getAttribute("account");
	HttpServletRequest request = ServletActionContext.getRequest();


	public  String  getQuestionBaseList(){
		if(account==null){
			return "login";
		}
		pageForm = quesManageService.getQuestionBaseList(pageNumber,30,parameter,account.getCompanyID(),type);
        String sajax = request.getParameter("sajax");
        if("sajax".equals(sajax)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jsonObject = JSONObject.fromObject(map);
			this.result = jsonObject.toString();
			return "success";
		}
		return "blist";
	}

	/**
	 *
	 *  题库
	 * @return
	 */
	public String getQuesBaseInfo(){
		if(account==null){
			return "login";
		}
      if(totalQuestion!=null&&totalQuestion.getTqID()!=null&&!totalQuestion.getTqID().equals("")){
		  totalQuestion = quesManageService.getTotalQues(totalQuestion.getTqID());
	  }
		List<BaseBean> queTypelist = quesManageService.getQueTypeList(account.getCompanyID());
		request.setAttribute("queTypelist",queTypelist);
		return "s_addQuestb";

	}

	/**
	 *
	 * 题目
	 * @return
	 */
	public String getQuesInfo(){
		if(questions!=null&&questions.getQrID()!=null&&!questions.getQrID().equals("")){
			questions = quesManageService.getQuesInfo(questions.getQrID());
			if(!questions.getQuesType().equals("03")) {
				List<BaseBean> valueList = quesManageService.getQueValueList(questions.getQrID());
				request.setAttribute("valueList",valueList);
			}
		}else{
			//如果是新增查询上一次填写的记录
			questionslast = quesManageService.getQuesLastInfo(questions.getTqID());
		}

		String view = request.getParameter("view");
		if("view".equals(view)){
			return "quesView";
		}


		return "addquestion";


	}

	/**
	 *
	 *  题库
	 * @return
	 */
	public String getQuesTypeInfo(){
		if(quesBaseType!=null&&quesBaseType.getQbtId()!=null&&!quesBaseType.getQbtId().equals("")){
			quesBaseType = quesManageService.getQuesTypeInfo(quesBaseType.getQbtId());
		}

		return "s_addQuestype";

	}


	/**
	 *
	 * 保存题库
	 * @return
	 */
	public String saveQueBase(){
		if(account==null){
			return "login";
		}
		quesManageService.saveQueBase(totalQuestion,account.getCompanyID(),account.getStaffID());
		request.setAttribute("message","11");


		return "success";
	}

	/**
	 *
	 * 保存题库类型
	 * @return
	 */
	public String saveQueType(){
		if(account==null){
			return "login";
		}
		quesManageService.saveQueType(quesBaseType,account.getCompanyID(),account.getStaffID());
		request.setAttribute("message","11");
		return "success";
	}


	/**
	 *
	 * 保存题目
	 * @return
	 */
	public String saveQuestion(){


		if (personImageInfo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");

			String photoPath = fileService
					.savePhoto(
							path,
							personImageInfoFileName,
							personImageInfo,
							account.getCompanyID(),
							"/human/question/"
									+ Utilities.getDateString(new Date(),
									"yyyy-MM-dd"));

			questions.setPicpath(photoPath);
		}
		String yscore = request.getParameter("yscore");
		quesManageService.saveQuestions(questions,valueMap,account.getStaffID(),account.getCompanyID(),yscore);
		request.setAttribute("message","11");


		return "success";
	}


	/**
	 *
	 * 获取题库分类
	 * @return
	 */
	public  String  getQueTypeList(){
		if(account==null){
			return "login";
		}
		pageForm = quesManageService.getQuesTypePageList(pageNumber,30,parameter,account.getCompanyID());
		String sajax = request.getParameter("sajax");
		if("sajax".equals(sajax)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jsonObject = JSONObject.fromObject(map);
			this.result = jsonObject.toString();
			return "success";
		}


		return "s_questypelist";
	}

	public String delQues(){

		quesManageService.delQues(totalQuestion.getTqID());
		return "success";
	}

	public String delQuesType(){

		quesManageService.delQuesType(quesBaseType.getQbtId());
		return "success";
	}
	public String delQuestion(){

		quesManageService.delQuestions(questions.getQrID());
		return "success";
	}

	/**
	 *
	 * 题的列表
	 * @return
	 */
	public String getQuestionsList(){
		if(account==null){
			return "login";
		}
		 pageForm = quesManageService.getQuestionList(pageNumber,30,parameter,totalQuestion.getTqID());
		String sajax = request.getParameter("sajax");
		if("sajax".equals(sajax)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jsonObject = JSONObject.fromObject(map);
			this.result = jsonObject.toString();
			return "success";
		}
		 return "toqueslist";
	}

	/**
	 *
	 * 获取我的笔试题
	 * @return
	 */
	public String myExam(){
         if(account==null){
         	return "login";
		 }
      pageForm = quesManageService.myQuesExam(pageNumber,30,account.getStaffID(),parameter);
		String sajax = request.getParameter("sajax");
		if("sajax".equals(sajax)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jsonObject = JSONObject.fromObject(map);
			this.result = jsonObject.toString();
			return "success";
		}
		 return "myexam";

	}

	/**
	 *
	 * 考试
	 * @return
	 */
	public String examList(){
		if(account==null){
			return "login";
		}
		pageForm = quesManageService.examList(pageNumber,30,account.getCompanyID(),parameter,totalQuestionExam.getType());
		String sajax = request.getParameter("sajax");
		if("sajax".equals(sajax)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jsonObject = JSONObject.fromObject(map);
			this.result = jsonObject.toString();
			return "success";
		}
		return "examlist";

	}


	/**
	 *
	 * 批阅
	 * @return
	 */
	public String getPyExamQuesList(){

		   //每道题题目 ，每道题 选项， 考生答案， 正确答案

			pageForm = quesManageService.getPyQusList(pageNumber, 20, totalQuestionExam.getTqeID(), questionsExam.getQuesType());
			Map<String,List<BaseBean>> m= new HashMap<>();
            if(pageForm!=null) {
				for (int i = 0; i < pageForm.getList().size(); i++) {

					Object obj = pageForm.getList().get(i);
					Object[] objs = (Object[]) obj;


					if (!objs[2].equals("03")) {
						List<BaseBean> valueList = quesManageService.getQueValueExamList(objs[0].toString());
						m.put(objs[0].toString(), valueList);
					}

				}
			}
			request.setAttribute("mapvalue",m);


		return "pyexam";
	}

	/**
	 *
	 * 口试查阅
	 * @return
	 */
	public String getKsExamQuesList(){

		//每道题题目 ，每道题 选项， 考生答案， 正确答案

		pageForm = quesManageService.getKsQusList(pageNumber, 20, totalQuestion.getTqID());
		Map<String,List<BaseBean>> m= new HashMap<>();
		if(pageForm!=null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {

				Object obj = pageForm.getList().get(i);
				Object[] objs = (Object[]) obj;


				if (!objs[2].equals("03")) {
					List<BaseBean> valueList = quesManageService.getKsValueExamList(objs[1].toString());
					m.put(objs[1].toString(), valueList);
				}

			}
		}

		request.setAttribute("mapvalue",m);


		return "ksexam";
	}

	/**
	 *
	 * 人工审核
	 * @return
	 */
	public  String manualAudit(){


		quesManageService.manualAudit(questionsExam.getQreID(),questionsExam.getTqeID(),account.getStaffID(),questionsExam.getScore());


		return "success";
	}

	/**
	 *
	 * 上传成绩
	 * @return
	 */
	public  String uploadScore(){


		quesManageService.uploadScore(examRelate.getErId());

		return "success";
	}

	/**
	 *
	 * 开始考试首页
	 * @return
	 */
	public  String startExam(){

           //获取学员身份证号 姓名  题库名称，
            examRelate = quesManageService.getExam(account.getStaffID(),totalQuestionExam.getTqeID());

		    totalQuestionExam = quesManageService.getTotalQuesExam(totalQuestionExam.getTqeID());

		    return "startexam";

	}
	/**
	 *
	 * 获取时间页
	 * @return
	 */
	public String getTimePage(){

		examRelate = quesManageService.startRecord(account.getStaffID(), totalQuestionExam.getTqeID());
			int duration = quesManageService.getDuration(totalQuestionExam.getTqeID());
			request.setAttribute("duration", duration);


		return "examframe";
	}
	/**
	 *
	 * 获取正式考试题目
	 * @return
	 */
	public String getExamPage(){
	      	String next = request.getParameter("next");
             if(!"next".equals(next)) {
				 pageNumber = quesManageService.getPageLast(account.getStaffID(), totalQuestionExam.getTqeID(), totalQuestionExam.getTqID());
			 }
		     if(pageNumber!=-2) {
				 examRelate = quesManageService.getExam(account.getStaffID(),totalQuestionExam.getTqeID());

				 int duration = quesManageService.getDuration(totalQuestionExam.getTqeID());
				 request.setAttribute("duration", duration);
				 if (examRelate.getStatus().equals("01"))

					 pageForm = quesManageService.getQuestionExamList(pageNumber, 1, totalQuestionExam.getTqeID(), account.getStaffID());
			     	 questionsExam = (QuestionsExam) pageForm.getList().get(0);


				 if (!questionsExam.getQuesType().equals("03")) {
					 List<BaseBean> valueList = quesManageService.getQueValueExamList(questionsExam.getQreID());
					 request.setAttribute("valueList", valueList);
				 }
				 examAnswer = quesManageService.getAnswer(account.getStaffID(), questionsExam.getQreID(), questionsExam.getQrID());
				 String isCollect = quesManageService.getIsCollect(questionsExam.getQreID(), questionsExam.getQrID());
				 request.setAttribute("isCollect", isCollect);
				 String source = request.getParameter("source");
				 if (!"backlook".equals(source)) {
					 quesManageService.addQuesLast(account.getStaffID(), questionsExam.getTqeID(), questionsExam.getTqID(), pageNumber);

				 }

			 }

             return "exampage";
	}

	/**
	 *
	 * 收藏和取消收藏
	 * @return
	 */
	public String collectQuestion(){


		quesManageService.collectQuestion(questionsExam.getQrID(),questionsExam.getQreID());

		return "success";

	}

	/**
	 *
	 * 交卷
	 * @return
	 */
	public String handPaper(){

		quesManageService.handPaper(questionsExam.getTqeID(),questionsExam.getTqID(),account.getStaffID());

		return "success";
	}

	/**
	 *
	 * 选择答案
	 * @return
	 */
	public String recordAnswer(){
		String r = quesManageService.recordAnswer(account.getStaffID(),examAnswer,pageNumber);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",r);
		JSONObject jsonObject = JSONObject.fromObject(map);
		this.result = jsonObject.toString();
		return "success";

	}

	/**
	 *
	 * 获取已答题未答题收藏情况
	 * @return
	 */
	public String getQusStatusList(){


		 List<Object> list = quesManageService.getQusStatusList(totalQuestionExam.getTqeID(),account.getStaffID());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		JSONObject jsonObject = JSONObject.fromObject(map);
		this.result = jsonObject.toString();
		return "success";
	}

	/**
	 *
	 * 获取成绩
	 * @return
	 */
	public  String getExamResult(){
		examRelate = quesManageService.getExamResult(examRelate.getErId());


		return "exresult";
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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public File getPersonImageInfo() {
		return personImageInfo;
	}

	public void setPersonImageInfo(File personImageInfo) {
		this.personImageInfo = personImageInfo;
	}

	public String getPersonImageInfoFileName() {
		return personImageInfoFileName;
	}

	public void setPersonImageInfoFileName(String personImageInfoFileName) {
		this.personImageInfoFileName = personImageInfoFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TotalQuestion getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(TotalQuestion totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public QuesBaseType getQuesBaseType() {
		return quesBaseType;
	}

	public void setQuesBaseType(QuesBaseType quesBaseType) {
		this.quesBaseType = quesBaseType;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	public Map<Integer, QuestionsValue> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<Integer, QuestionsValue> valueMap) {
		this.valueMap = valueMap;
	}

	public Questions getQuestionslast() {
		return questionslast;
	}

	public void setQuestionslast(Questions questionslast) {
		this.questionslast = questionslast;
	}

	public TotalQuestionExam getTotalQuestionExam() {
		return totalQuestionExam;
	}

	public void setTotalQuestionExam(TotalQuestionExam totalQuestionExam) {
		this.totalQuestionExam = totalQuestionExam;
	}

	public QuestionsExam getQuestionsExam() {
		return questionsExam;
	}

	public void setQuestionsExam(QuestionsExam questionsExam) {
		this.questionsExam = questionsExam;
	}

	public ExamRelate getExamRelate() {
		return examRelate;
	}

	public void setExamRelate(ExamRelate examRelate) {
		this.examRelate = examRelate;
	}

	public ExamAnswer getExamAnswer() {
		return examAnswer;
	}

	public void setExamAnswer(ExamAnswer examAnswer) {
		this.examAnswer = examAnswer;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}
}
