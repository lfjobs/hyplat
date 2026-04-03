package hy.ea.human.service.impl;

import hy.ea.bo.human.Audition;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.question.*;
import hy.ea.human.service.QuestionManageService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuestionManageServiceImpl implements QuestionManageService {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private ServerService serverService;
	@Autowired
	private MobileMessage msage;

	public PageForm getQuestionBaseList(int pageNumber, int pageSize, String parameter,String companyID,String type){
             List<Object> params = new ArrayList<Object>();

         String hql = "from TotalQuestion  where companyID = ? and type = ? and status = ?";
         params.add(companyID);
         params.add(type);
		params.add("00");
         if(parameter!=null&&!parameter.equals("")){
         	 hql+=" and titleBase like ?";
			 params.add("%"+parameter+"%");
		 }
		hql+=" order by createDate desc";

         PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,params.toArray());

		return pageForm;
	}

	/**
	 *
	 * 分类
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 * @param companyID
	 * @return
	 */
	public PageForm getQuesTypePageList(int pageNumber, int pageSize, String parameter,String companyID){
		List<Object> params = new ArrayList<Object>();

		String hql = "from QuesBaseType  where companyID = ? and status = ?";
		params.add(companyID);
		params.add("00");
		if(parameter!=null&&!parameter.equals("")){
			hql+=" and typeName like ?";
			params.add("%"+parameter+"%");
		}
		hql+=" order by createDate desc";

		PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,params.toArray());

		return pageForm;

	}
	/**
	 *
	 * 修改获取详情
	 * @param tqID
	 * @return
	 */
	public TotalQuestion getTotalQues(String tqID){
		String hql = "from TotalQuestion where tqID = ?";
		TotalQuestion totalQuestion = (TotalQuestion)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{tqID});
		return totalQuestion;
	}


	/**
	 *
	 * 修改获取详情
	 * @param qbtId
	 * @return
	 */
	public  QuesBaseType getQuesTypeInfo(String qbtId){
		String hql = "from QuesBaseType where qbtId = ?";
		QuesBaseType quesBaseType = (QuesBaseType)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{qbtId});
		return quesBaseType;
	}


	/**
	 *
	 * 修改获取详情
	 * @param qrID
	 * @return
	 */
	public Questions getQuesInfo(String qrID){
		String hql = "from Questions where qrID = ?";
		Questions questions = (Questions)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{qrID});
		return questions;
	}
	/**
	 *
	 * 保存题库
	 * @param totalQuestion
	 * @return
	 */
	public void saveQueBase(TotalQuestion totalQuestion,String companyID,String staffID){
		Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});

		if(totalQuestion.getTqID()!=null&&!totalQuestion.getTqID().equals("")){
			TotalQuestion totalQuestion1 = (TotalQuestion)baseBeanDao.getBeanByHqlAndParams("from TotalQuestion where tqID = ?",new Object[]{totalQuestion.getTqID()});
			totalQuestion1.setStaffID(staffID);
            totalQuestion1.setStaffName(staff.getStaffName());
            totalQuestion1.setCreateDate(new Date());
            totalQuestion1.setTypeName(totalQuestion.getTypeName());
            totalQuestion1.setTitleBase(totalQuestion.getTitleBase());
            totalQuestion1.setQbtID(totalQuestion.getQbtID());
            totalQuestion1.setQualifiedSocre(totalQuestion.getQualifiedSocre());
            totalQuestion1.setDuration(totalQuestion.getDuration());
            baseBeanDao.update(totalQuestion1);

	       }else{
		   totalQuestion.setTqID(serverService.getServerID("tqid"));
		   totalQuestion.setCompanyID(companyID);
		   totalQuestion.setStaffID(staffID);
		   totalQuestion.setStaffName(staff.getStaffName());
		   totalQuestion.setCreateDate(new Date());
		   totalQuestion.setStatus("00");
		   baseBeanDao.save(totalQuestion);
	   }

	}


	/**
	 *
	 * 保存题库
	 * @param quesBaseType
	 * @return
	 */
	public void saveQueType(QuesBaseType quesBaseType, String companyID, String staffID){
		Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});

		if(quesBaseType.getQbtId()!=null&&!quesBaseType.getQbtId().equals("")){
			QuesBaseType quesBaseType1 = (QuesBaseType)baseBeanDao.getBeanByHqlAndParams("from QuesBaseType where qbtId = ?",new Object[]{quesBaseType.getQbtId()});
			quesBaseType1.setStaffID(staffID);
			quesBaseType1.setStaffName(staff.getStaffName());
			quesBaseType1.setTypeName(quesBaseType.getTypeName());
			quesBaseType1.setCreateDate(new Date());
			baseBeanDao.update(quesBaseType1);

		}else{
			quesBaseType.setQbtId(serverService.getServerID("tbtId"));
			quesBaseType.setCompanyID(companyID);
			quesBaseType.setStaffID(staffID);
			quesBaseType.setStaffName(staff.getStaffName());
			quesBaseType.setCreateDate(new Date());
			quesBaseType.setStatus("00");
			baseBeanDao.save(quesBaseType);
		}

	}

	/**
	 *
	 * 保存题库类型
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getQueTypeList(String companyID){

		String hql = "from QuesBaseType where companyID = ? and status = ? order by createDate desc";
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{companyID,"00"});

        return list;
	}

	/**
	 * 删除题库
	 * @param tqID
	 */
	public void delQues(String tqID){

           String hql = "update TotalQuestion set status = ?  where tqID = ?";
           baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{"99",tqID});
	}

	/**
	 *
	 * 删除题目分类
	 * @param qbtId
	 */

	public void delQuesType(String qbtId){
		String hql = "update QuesBaseType set status = ? where qbtId = ?";
		baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{"99",qbtId});


	}

	/**
	 *
	 * 删除题目
	 * @param qrID
	 */

	public void delQuestions(String qrID){
        List<BaseBean> beans = new ArrayList<BaseBean>();
 		Questions questions = (Questions)baseBeanDao.getBeanByHqlAndParams("from Questions where qrID = ?",new Object[]{qrID});
		questions.setStatus("99");
		beans.add(questions);

		TotalQuestion totalQuestion = (TotalQuestion)baseBeanDao.getBeanByHqlAndParams("from TotalQuestion where tqID = ?",new Object[]{questions.getTqID()});

		totalQuestion.setTotalQues(totalQuestion.getTotalQues()-1);
		totalQuestion.setTotalscore(totalQuestion.getTotalscore()-questions.getScore());

		beans.add(totalQuestion);

		baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);




	}

	/**
			*
			* 获取题
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 * @param tqID
	 * @return
			 */
	public PageForm getQuestionList(int pageNumber,int pageSize,String parameter,String tqID){

		List<Object> params = new ArrayList<Object>();
         String hql = "from Questions where tqID = ? and status = ? ";

		params.add(tqID);
		params.add("00");
		if(parameter!=null&&!parameter.equals("")){
			hql+=" and title like ?";
			params.add("%"+parameter+"%");
		}
		hql+=" order by seq desc";

		PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,params.toArray());

		return pageForm;
	}

	/**
	 *
	 * 保存题
	 * @param questions
	 * @param map
	 */
	public void saveQuestions(Questions questions, Map<Integer,QuestionsValue> map,String staffID,String companyID,String yscore){

         List<BaseBean>  beans = new ArrayList<BaseBean>();

		Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});
		String qrID = "";

		TotalQuestion totalQuestion = (TotalQuestion)baseBeanDao.getBeanByHqlAndParams("from TotalQuestion where tqID = ?",new Object[]{questions.getTqID()});

		if(questions.getQrID()!=null&&!questions.getQrID().equals("")){
			qrID = questions.getQrID();
             Questions questions1 = (Questions) baseBeanDao.getBeanByHqlAndParams("from Questions where qrID = ?",new Object[]{qrID});
             questions1.setSeq(questions.getSeq());
             questions1.setStaffID(staffID);
             questions1.setStaffName(staff.getStaffName());
             questions1.setScore(questions.getScore());
             questions1.setPicpath(questions.getPicpath());
             questions1.setCorrectAnswer(questions.getCorrectAnswer());
             questions1.setCreateDate(new Date());
             //questions1.setQuesType(); 修改不能修改题型
			 questions1.setTitle(questions.getTitle());
			beans.add(questions1);
			if(!questions.getQuesType().equals("03")) {
				for (QuestionsValue questionsValue : map.values()) {

					String hql = "from QuestionsValue where qrvID = ?";
					QuestionsValue questionsValue1 = (QuestionsValue) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{questionsValue.getQrvID()});
					questionsValue1.setCodeValue(questionsValue.getCodeValue());
					questionsValue1.setStaffID(staffID);
					beans.add(questionsValue1);
				}
			}
			totalQuestion.setTotalscore(totalQuestion.getTotalscore()-Integer.parseInt(yscore)+questions.getScore());
			beans.add(totalQuestion);
		 }else{


			totalQuestion.setTotalQues(totalQuestion.getTotalQues()+1);
			totalQuestion.setTotalscore(totalQuestion.getTotalscore()+questions.getScore());
			beans.add(totalQuestion);

			 questions.setStatus("00");
			 questions.setQrID(serverService.getServerID("qrid"));
			  qrID = questions.getQrID();
			 questions.setStaffID(staffID);
			 questions.setCompanyID(companyID);
			 questions.setStaffName(staff.getStaffName());
			 questions.setCreateDate(new Date());
			 beans.add(questions);

			if(!questions.getQuesType().equals("03")) {
				int i = 0;
				for (QuestionsValue questionsValue : map.values()) {
					i = i + 1;
					questionsValue.setQrvID(serverService.getServerID("qrvid"));
					questionsValue.setQrID(qrID);
					questionsValue.setSeq(i);
					questionsValue.setStaffID(staffID);
					beans.add(questionsValue);
				}
			}

		}



		baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
	}


	/**
	 *
	 * 获取答题选项
	 * @param qrID
	 * @return
	 */
	public List<BaseBean> getQueValueList(String qrID){

		String hql = "from QuestionsValue where qrID = ? order by seq";

		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{qrID});

		return list;
	}

	/**
	 *
	 * 获取答题选项
	 * @param qreID
	 * @return
	 */
	public List<BaseBean> getQueValueExamList(String qreID){

		String hql = "from QuestionsValueExam where qreID = ? order by seq";

		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{qreID});

		return list;
	}
	/**
	 *
	 * 获取答题选项
	 * @param qrID
	 * @return
	 */
	public List<BaseBean> getKsValueExamList(String qrID){

		String hql = "from QuestionsValue where qrID = ? order by seq";

		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{qrID});

		return list;
	}
	/**
	 *
	 * 获取上次填写的题目信息
	 * @param tqID
	 * @return
	 */
	public  Questions getQuesLastInfo(String tqID){
		String hql = "from Questions where tqID = ? and status = ? order by createDate desc";
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{tqID,"00"});
		Questions questions = null;
		if(list.size()>0){
			questions = (Questions)list.get(0);
		}

		return questions;
	}


	/**
	 *
	 * 获取考试
	 * @returnt.duration,t.totalscore,t.qualifiedSocre,t.totalQues,t.titleBase,e.isHg,e.totalScore,e.status
	 */
	public PageForm myQuesExam(int pageNumber,int pageSize,String staffID,String parameter){
		String  sql = "select t.tqID,t.duration,t.totalscore,t.qualifiedSocre,t.totalQues,t.titleBase,e.isHg,e.tscore,e.status,e.tqeID,to_char(e.startDate,'yyyy-mm-dd hh24:mi:ss'),e.erId from dt_q_TotalQuestionExam t,dt_q_ExamRelate e where e.staffID = ? and e.tqeID = t.tqeID";
        List<Object> params = new ArrayList<Object>();
		params.add(staffID);
		if(parameter!=null&&!parameter.equals("")){
			sql+=" and t.titleBase like ?";
		  params.add("%"+parameter+"%");
		}
        String sqlcount= "select count(*) from ("+sql+")";
         PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,
				pageSize, sql,sqlcount, params.toArray());
           List<String> strings = new ArrayList<String>();
         if(pageForm!=null){
         	List<BaseBean> list = pageForm.getList();
                for(int i = 0;i<list.size();i++){
                      Object obj = list.get(i);
                      Object[] objs = (Object[])obj;
					 String status = objs[8].toString();
					 if("01".equals(status)){
						 String sc = objs[10].toString();
						 Date start = Utilities.getDateFromString(sc,"yyyy-MM-dd HH:mm:ss");
						 int duration = Integer.parseInt(objs[1].toString());
						 Date now = new Date();

						 long diff = now.getTime()-start.getTime();
						 long minite = diff/1000/60;

						 if(minite>=duration){
							 strings.add(objs[11].toString());
						 }
					 }

				}
                 if(strings.size()>0){
					 String hqlupdate = "update ExamRelate set status = ? where erId in(";
					 List<Object> paramsd = new ArrayList<Object>();
					 paramsd.add("03");

					 for(int j = 0;j<strings.size();j++){
					 	if(j==strings.size()-1){
					 		hqlupdate+="?)";
						}else{
							hqlupdate+="?,";
						}
						 paramsd.add(strings.get(j));
					 }

                     baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hqlupdate},paramsd.toArray());

				 }

		 }

		return pageForm;

	}


	/*

正式考试
 */
	public PageForm examList(int pageNumber,int pageSize,String companyID,String parameter,String type){

		PageForm pageForm = null;
		if("00".equals(type)) {

			String sql = "select t.tqID,t.duration,t.totalscore,t.qualifiedSocre,t.totalQues,t.titleBase,e.isHg,e.tscore,e.status,e.tqeID,to_char(e.startDate,'yyyy-mm-dd hh24:mi:ss'),e.erId,e.staffName,e.card,e.telphone from dt_q_TotalQuestionExam t,dt_q_ExamRelate e where t.companyID = ? and e.tqeID = t.tqeID";
			List<Object> params = new ArrayList<Object>();
			params.add(companyID);
			if (parameter != null && !parameter.equals("")) {
				sql += " and (e.staffName  like ? or e.card  like ? or e.telphone like ?)";
				params.add("%" + parameter + "%");
				params.add("%" + parameter + "%");
				params.add("%" + parameter + "%");
			}

			sql += " order by CASE WHEN e.status = '02' THEN 0 ELSE 1 END,t.createDate desc";
			String sqlcount = "select count(*) from (" + sql + ")";


		    pageForm = baseBeanService.getPageFormBySQL(pageNumber,
					pageSize, sql, sqlcount, params.toArray());
			List<String> strings = new ArrayList<String>();
			if (pageForm != null) {
				List<BaseBean> list = pageForm.getList();
				for (int i = 0; i < list.size(); i++) {
					Object obj = list.get(i);
					Object[] objs = (Object[]) obj;
					String status = objs[8].toString();
					if ("01".equals(status)) {
						String sc = objs[10].toString();
						Date start = Utilities.getDateFromString(sc, "yyyy-MM-dd HH:mm:ss");
						int duration = Integer.parseInt(objs[1].toString());
						Date now = new Date();

						long diff = now.getTime() - start.getTime();
						long minite = diff / 1000 / 60;

						if (minite >= duration) {
							strings.add(objs[11].toString());
						}
					}

				}
				if (strings.size() > 0) {
					String hqlupdate = "update ExamRelate set status = ? where erId in(";
					List<Object> paramsd = new ArrayList<Object>();
					paramsd.add("03");

					for (int j = 0; j < strings.size(); j++) {
						if (j == strings.size() - 1) {
							hqlupdate += "?)";
						} else {
							hqlupdate += "?,";
						}
						paramsd.add(strings.get(j));
					}

					baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqlupdate}, paramsd.toArray());

				}

			}
		}else{

      ///
			String sql = "select t.tqID,t.duration,t.totalscore,t.qualifiedSocre,t.totalQues,t.titleBase,f.staffName,f.staffIdentityCard,f.reference,e.auditionPointk,e.auditionID  from dt_q_TotalQuestion t,dtAudition e,dt_hr_staff f where e.staffID = f.staffID and t.companyID = ? and e.mstID = t.tqID";
			List<Object> params = new ArrayList<Object>();
			params.add(companyID);
			if (parameter != null && !parameter.equals("")) {
				sql += " and (f.staffName  like ? or f.staffIdentityCard  like ? or f.reference like ?)";
				params.add("%" + parameter + "%");
				params.add("%" + parameter + "%");
				params.add("%" + parameter + "%");
			}

			sql += " order by e.registerDate desc";
			String sqlcount = "select count(*) from (" + sql + ")";


			pageForm = baseBeanService.getPageFormBySQL(pageNumber,
					pageSize, sql, sqlcount, params.toArray());
		}

		return pageForm;

	}


	/**
	 *
	 * 获取考试人相关
	 * @return
	 */
	public ExamRelate getExam(String staffID,String tqeID){

		String hql = "from ExamRelate where staffID = ? and tqeID = ?";
		ExamRelate examRelate = (ExamRelate)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffID,tqeID});

		return examRelate;


	}

	/**
	 *
	 * 修改获取详情
	 * @param tqeID
	 * @return
	 */
	public TotalQuestionExam getTotalQuesExam(String tqeID){
		String hql = "from TotalQuestionExam where tqeID = ?";
		TotalQuestionExam totalQuestionExam = (TotalQuestionExam)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{tqeID});
		return totalQuestionExam;
	}

	/**
	 *
	 * 获取题目
	 * @param pageNumber
	 * @param pageSize
	 * @param tqeID
	 * @param staffID
	 * @return
	 */
	public PageForm getQuestionExamList(int pageNumber, int pageSize, String tqeID, String staffID) {

		PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,"from QuestionsExam where staffID = ? and tqeID = ? order by quesType asc,seq asc",new Object[]{staffID,tqeID});

				return pageForm;
	}

	/**
	 *
	 *
	 * @param tqeID

	 * @return
	 */
	public PageForm getPyQusList(int pageNumber, int pageSize, String tqeID,String quesType){


		String sql = "select q.qreID,q.qrID,q.quesType,q.seq,nvl(w.answer,'noanswer'),q.tqeID,q.tqID,q.score,q.title,q.picpath,q.correctAnswer,w.isCorrect,w.score as sc from dt_q_QuestionsExam q left join dt_q_ExamAnswer w on q.qreID = w.qreID where q.tqeID = ? ";

		if(quesType.equals("00")){
			sql+=" and q.quesType!='03'";
		}else{
			sql+=" and q.quesType='03'";
		}

		sql+=" order by q.quesType asc,q.seq asc";

		PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql,"select count(*) from("+sql+")",new Object[]{tqeID});

		return pageForm;

	}

	/**
	 *
	 *
	 * @param tqID

	 * @return
	 */
	public PageForm getKsQusList(int pageNumber, int pageSize, String tqID){


		String sql = "select q.qreID,q.qrID,q.quesType,q.seq,q.tqID,q.score,q.title,q.picpath,q.correctAnswer from dt_q_Questions q  where q.tqID = ? ";

		sql+=" order by q.quesType asc,q.seq asc";

		PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql,"select count(*) from("+sql+")",new Object[]{tqID});

		return pageForm;

	}
	/**
	 *
	 * 人工审核
	 * @return
	 */
	public void manualAudit(String qreID,String tqeID,String staffID,int score){

		List<BaseBean> beans = new ArrayList<BaseBean>();
          String hql = "from ExamAnswer where qreID = ?";
		ExamAnswer examAnswer = (ExamAnswer)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{qreID});

		examAnswer.setScore(score);
		examAnswer.setAuditID(staffID);
		Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});
		examAnswer.setAuditName(staff.getStaffName());
		examAnswer.setAuditDate(new Date());

		beans.add(examAnswer);
		ExamRelate examRelate = (ExamRelate) baseBeanDao.getBeanByHqlAndParams("from ExamRelate where tqeID = ?",new Object[]{tqeID});

		examRelate.setTscore(examRelate.getTscore()+score);

		beans.add(examRelate);

		baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
	}

	/**
	 *
	 * 开始考试记录时间
	 * @param staffID
	 * @param tqeID
	 */
	public ExamRelate startRecord(String staffID,String tqeID){

		String hql = "from ExamRelate where staffID = ? and tqeID = ?";
		ExamRelate examRelate = (ExamRelate)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffID,tqeID});

		if("00".equals(examRelate.getStatus())){
			examRelate.setStartDate(new Date());
			examRelate.setStatus("01");
			baseBeanDao.update(examRelate);
		}


		return examRelate;
	}

	/**
	 *
	 * 收藏
	 * @param qrID
	 * @param qreID
	 */
	public void collectQuestion(String qrID,String qreID){
		String hql = "from QuestionsCollect where qrID  = ? and qreID = ?";
		QuestionsCollect questionCollect = 	(QuestionsCollect)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{qrID,qreID});
		if(questionCollect==null){
			questionCollect = new QuestionsCollect();
            questionCollect.setQcID(serverService.getServerID("qcid"));
            questionCollect.setCreateDate(new Date());
            questionCollect.setQreID(qreID);
            questionCollect.setQrID(qrID);
            baseBeanDao.save(questionCollect);
		}else{
			String hqldelete = "delete from QuestionsCollect where qrID = ? and qreID = ?";
			baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hqldelete},new Object[]{qrID,qreID});
		}

	}

	/**
	 *
	 * 交卷
	 * @param tqeID
	 * @param tqID
	 */
	public void handPaper(String tqeID,String tqID,String staffID) {

        List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "from ExamRelate where staffID = ? and tqeID = ? and tqID = ?";
		ExamRelate examRelate = (ExamRelate) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID, tqeID, tqID});
		examRelate.setEndDate(new Date());
		examRelate.setStatus("02");

		TotalQuestionExam totalQuestionExam = (TotalQuestionExam) baseBeanDao.getBeanByHqlAndParams("from TotalQuestionExam where staffID = ? and tqeID = ? and tqID = ?", new Object[]{staffID, tqeID, tqID});

		List<BaseBean> questlist = baseBeanDao.getListBeanByHqlAndParams("from QuestionsExam where staffID = ? and tqeID = ? and tqID = ?", new Object[]{staffID, tqeID, tqID});
		String hqlvalue = "from ExamAnswer where qreID = ? and qrID = ? and staffID = ?";
		String haveJd = "0";
		int totalScore = 0;
		int  qualifiedSocre = totalQuestionExam.getQualifiedSocre();
		for (int i = 0; i < questlist.size(); i++) {
			QuestionsExam questionsExam = (QuestionsExam) questlist.get(i);

			ExamAnswer examAnswer = (ExamAnswer) baseBeanDao.getBeanByHqlAndParams(hqlvalue, new Object[]{questionsExam.getQreID(), questionsExam.getQrID(), staffID});

			String quesType = questionsExam.getQuesType();

			//00 单选 01 多选 02 判断 03 简答题
			if (quesType.equals("03")) {
				haveJd = "1";
			} else {
				if (examAnswer.equals("00")) {
					totalScore += examAnswer.getScore();
				}
				beans.add(examAnswer);
			}

		}
		if(haveJd.equals("0")){ //如果没有简答题直接算成绩
			if(totalScore>=qualifiedSocre){
				examRelate.setIsHg("00");
			}else{
				examRelate.setIsHg("01");
			}

			examRelate.setTscore(totalScore);

		}
		beans.add(examRelate);

		baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
	}

	/**
	 *
	 * 记录答案
	 * @param staffID
	 * @param examAnswer
	 * @param pageNumber
	 * @return
	 */
	public String recordAnswer(String staffID, ExamAnswer examAnswer,int pageNumber){
      //String qreID,String qrID,String erId,String anwser
        try {
			List<BaseBean> beans = new ArrayList<BaseBean>();

			ExamAnswer examAnswer1 = (ExamAnswer) baseBeanDao.getBeanByHqlAndParams("from ExamAnswer where staffID = ? and qreID = ? and qrID = ? and erId = ?", new Object[]{staffID, examAnswer.getQreID(), examAnswer.getQrID(), examAnswer.getErId()});

			QuestionsExam questionsExam = (QuestionsExam) baseBeanDao.getBeanByHqlAndParams("from QuestionsExam where qreID = ? and qrID = ? and staffID = ?", new Object[]{examAnswer.getQreID(), examAnswer.getQrID(), staffID});

			questionsExam.setSeq(pageNumber);

			beans.add(questionsExam);


			//00 单选 01 多选 02 判断 03 简答题
			if (!questionsExam.getQuesType().equals("03")) {
				if (questionsExam.getCorrectAnswer().equals(examAnswer.getAnswer())) {
					examAnswer.setIsCorrect("00");
					examAnswer.setScore(questionsExam.getScore());
				} else {
					examAnswer.setIsCorrect("01");
					examAnswer.setScore(0);

				}
			}


			if (examAnswer1 != null) {//修改答案
				examAnswer1.setAnswer(examAnswer.getAnswer());
				if (!questionsExam.getQuesType().equals("03")) {
					examAnswer1.setIsCorrect(examAnswer.getIsCorrect());
					examAnswer1.setScore(examAnswer.getScore());
				}

				beans.add(examAnswer1);
			} else {

				examAnswer.setEaId(serverService.getServerID("eaid"));
				examAnswer.setStaffID(staffID);
				beans.add(examAnswer);
			}

			baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}catch (Exception e){
        	return "1";
		}
           return "0";

	 }

	/**
	 *
	 * 获取已答题未答题收藏情况
	 * @param tqeID
	 * @param staffID
	 * @return
	 */
	 public List<Object> getQusStatusList(String tqeID,String staffID){

		 String sql = "select q.qreID,q.qrID,q.seq,nvl(w.answer,'noanswer'),nvl(c.qcID,'nsc') from dt_q_QuestionsExam q left join dt_q_ExamAnswer w on q.qreID = w.qreID left join dt_q_QuestionsCollect c on q.qreID = c.qreID where q.staffID = ? and q.tqeID = ? order by q.quesType asc,q.seq asc";

        List<Object> list = baseBeanDao.getListObjectBySqlAndParams(sql,new Object[]{staffID,tqeID});

        return list;

	 }



	/**
	 * 获取用户答题
	 * @param staffID
	 * @param qreID
	 * @param qrID
	 * @return
	 */
	public  ExamAnswer getAnswer(String staffID,String qreID,String qrID){

		String  hql = "from ExamAnswer where qrID = ? and qreID = ? and staffID = ?";

		ExamAnswer examAnswer = (ExamAnswer)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{qrID,qreID,staffID});

		return examAnswer;
	}

	/**
	 * 获取是否收藏过
	 * @param qreID
	 * @param qrID
	 * @return
	 */
	public String getIsCollect(String qreID,String qrID){

		String hql = "from QuestionsCollect where qreID = ? and qrID = ?";
		QuestionsCollect questionsCollect = (QuestionsCollect)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{qreID,qrID});

		if(questionsCollect!=null){
			return "1";
		}
		return "0";
	}
	/**
	 *
	 * 退出后再次进入
	 * @param staffID
	 * @param tqeID
	 * @param tqID
	 */
	public void  addQuesLast(String staffID,String tqeID,String tqID,int pageNumber){
		String hql = "from QuestionsLast where staffID = ? and tqeID = ? and tqID = ?";

		QuestionsLast questionsLast1 = (QuestionsLast) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffID,tqeID,tqID});
		if(questionsLast1==null){
			QuestionsLast questionsLast = new QuestionsLast();
			questionsLast.setQlID(serverService.getServerID("qlid"));
			questionsLast.setStaffID(staffID);
			questionsLast.setPageNumber(pageNumber);
			questionsLast.setTqeID(tqeID);
			questionsLast.setTqID(tqID);
			questionsLast.setCreateDate(new Date());

			baseBeanDao.save(questionsLast);
		}else {
			questionsLast1.setPageNumber(pageNumber);
			questionsLast1.setCreateDate(new Date());
			baseBeanDao.update(questionsLast1);


		}



	}

	/**
	 *
	 * 获取最后的页面
	 * @param staffID
	 * @param tqeID
	 * @param tqID
	 * @return
	 */
	public int getPageLast(String staffID,String tqeID,String tqID){

		 ExamRelate examRelate = (ExamRelate)baseBeanDao.getBeanByHqlAndParams("from ExamRelate where tqeID = ? and tqID = ? and staffID = ?",new Object[]{tqeID,tqID,staffID});
          int pageNumber = 0;
		 if("00".equals(examRelate.getStatus())){
			 pageNumber = 0;//还没开始
		 }else if("01".equals(examRelate.getStatus())){
			 pageNumber = 1;//已经进入过考试
             String hql = "from QuestionsLast where  tqeID = ? and tqID = ? and staffID = ?";
             QuestionsLast questionsLast = (QuestionsLast) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{tqeID,tqID,staffID});
             if(questionsLast!=null){
                  pageNumber = questionsLast.getPageNumber();
			 }

		 }else if("02".equals(examRelate.getStatus())){
			 pageNumber = -2;//已经交卷
		 }

		 return  pageNumber;


	}

	/**
	 *
	 * 获取时长
	 * @param tqeID
	 * @return
	 */
	public int getDuration(String tqeID){
         String hql = "from TotalQuestionExam where tqeID = ?";
		TotalQuestionExam totalQuestionExam = (TotalQuestionExam)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{tqeID});
		return totalQuestionExam.getDuration();
	}


	/**
	 *
	 * 获取成绩
	 * @param erId
	 * @return
	 */
	public ExamRelate getExamResult(String erId){
		String hql = "from ExamRelate where erId = ?";
		ExamRelate examRelate = (ExamRelate)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{erId});

		return examRelate;
	}


	/**
	 *
	 * 上传成绩
	 * @param erId

	 */
	public void  uploadScore(String erId){
		ExamRelate examRelate = (ExamRelate)baseBeanDao.getBeanByHqlAndParams("from ExamRelate where erId = ?",new Object[]{erId});
		TotalQuestionExam totalQuestionExam = (TotalQuestionExam)baseBeanDao.getBeanByHqlAndParams("from TotalQuestionExam where tqeID = ? and tqID=? and staffID = ?",new Object[]{examRelate.getTqeID(),examRelate.getTqID(),examRelate.getStaffID()});
		int score = totalQuestionExam.getQualifiedSocre();

		String hlc = "from Audition where bstID = ? and staffID = ?";
		Audition audition = (Audition)baseBeanDao.getBeanByHqlAndParams(hlc,new Object[]{totalQuestionExam.getTqID(),examRelate.getStaffID()});


		List<BaseBean> beans = new ArrayList<BaseBean>();
		if(examRelate.getTscore()>=score){
			//合格
			examRelate.setIsHg("00");
			audition.setBsState("1");
            audition.setCommentionb("合格");
		}else{
			examRelate.setIsHg("01");
			audition.setBsState("2");
            audition.setCommentionb("不合格");
		}
		audition.setBsDate(new Date());
		audition.setAuditionPointb(examRelate.getTscore()+"");

		beans.add(examRelate);
		beans.add(audition);


		
         baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);

	}

}
