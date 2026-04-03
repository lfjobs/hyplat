package hy.ea.human.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.Company;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.question.*;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.human.service.EntryService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EntryServiceImpl implements EntryService {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private ServerService serverService;
	@Autowired
	private MobileMessage msage;

	public PageForm getlist(int pageNumber, int pageSize,String parameter,String status,String companyID) {

		String hql = "select new  Audition(s.staffName,s.photo,s.staffIdentityCard,s.reference,s.referenceCode,"
				+ "a.auditionKey, a.auditionID,a.companyID, a.staffID,"
				+ "a.auditionDirection, a.auditionPost, a.experience,"
				+ "a.place, a.examiner, a.auditionDate, a.auditionDept,"
				+ "a.commention, a.auditionPoint, a.registerDate,"
				+ "a.auditionPlace,a.becomesDate, a.remark, a.status,a.staffCategoryID,a.categoryName,a.bsState,a.ksState,a.zpState,a.ztState) "
				+ " from Staff s, Audition a where s.staffID = a.staffID and a.companyID=?";
		String hql2 = "select count(*) from Staff s, Audition a where s.staffID = a.staffID and a.companyID=?";




		List<Object> params = new ArrayList<Object>();
		params.add(companyID);

		 if(status.equals("2")){ //已通知
            //不做限制
		 }
		else if(status.equals("4")){ //面试登记，查询已经通知的数据


		}else if(status.equals("5")){ //未来的状态是01尚未登记
			 hql+=" and a.status like ? ";
			 hql2+=" and a.status like ? ";
			 params.add("01");

		 }else if(status.equals("6")){//已来的 状态不是初始00的都是来过的
			hql+=" and a.status not like ? ";
			hql2+=" and a.status not like ? ";
			params.add("00");

		}else if(status.equals("7")){//笔试
		   hql+=" and a.bsState is null and a.bstID is null";
	     	hql2+=" and a.bsState is null and a.bstID is null";

	     }else if(status.equals("8")){//笔试合格
			 hql+=" and a.bsState =  ? ";
			 hql2+=" and a.bsState =  ?";
			 params.add("1");

		 }else if(status.equals("9")){//笔试不合格
			 hql+=" and a.bsState =  ? ";
			 hql2+=" and a.bsState =  ?";
			 params.add("2");

		 }else if(status.equals("10")){//口试结果
			 hql+=" and a.ksState is null ";
			 hql2+=" and a.ksState is null";

		 }else if(status.equals("11")){//口试合格
			 hql+=" and a.ksState =  ? ";
			 hql2+=" and a.ksState =  ?";
			 params.add("1");

		 }else if(status.equals("12")){//可是不合格
			 hql+=" and a.ksState =  ? ";
			 hql2+=" and a.ksState =  ?";
			 params.add("2");

		 }else if(status.equals("13")){//综合评定
			 hql+=" and a.ksState is not null  and a.zpState is null ";
			 hql2+=" and a.ksState is not null  and a.zpState is null";

		 }else if(status.equals("14")||status.equals("16")){//综合评定合格，应该转通知的
			 hql+=" and zpState = '1' ";
			 hql2+=" and zpState = '1' ";
		 }else if(status.equals("15")){//综合评定不合格
			 hql+=" and zpState = '2' ";
			 hql2+=" and zpState = '2' ";

		 }else if(status.equals("17")){//已转通知入职

			 hql+=" and (ztState = '1' or ztState = '3') ";
			 hql2+=" and (ztState = '1' or ztState = '3')";
		 }else if(status.equals("18")){//未转通知入职

			 hql+=" and zpState = '1' and ztState is null";
			 hql2+=" and zpState = '1' and ztState is null";
		 }else if(status.equals("19")){//应通知入职

			 hql+=" and (ztState = '1' or ztState = '3')";
			 hql2+=" and (ztState = '1' or ztState = '3')";
		 }
		 else if(status.equals("20")){//未通知入职

			 hql+=" and ztState = '1'";
			 hql2+=" and ztState = '1'";
		 }
		 else if(status.equals("21")){//已通知入职

			 hql+=" and ztState = '3'";
			 hql2+=" and ztState = '3'";
		 }



		if (parameter != null && !"".equals(parameter)) {

				hql = hql + " and (s.staffName like ? or  s.staffIdentityCard like ? or  s.reference like ?)";
				hql2 = hql2 + " and (s.staffName like ? or  s.staffIdentityCard like ? or  s.reference like ?)";
		    	params.add("%"+parameter+"%");
		     	params.add("%"+parameter+"%");
		    	params.add("%"+parameter+"%");
		}
		hql = hql + " order by  a.auditionDate  desc nulls last";



		PageForm	pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql,
				hql2, params.toArray());
		return pageForm;
	}

	/**
	 *
	 * 获取详细
	 * @param auditionID
	 * @return
	 */
	public Audition getViewDetail(String auditionID){



		String hql = "select new  Audition(s.staffName,s.photo,s.staffIdentityCard,s.reference,s.referenceCode,"
				+ "a.auditionKey, a.auditionID,a.companyID, a.staffID,"
				+ "a.auditionDirection, a.auditionPost, a.experience,"
				+ "a.place, a.examiner, a.auditionDate, a.auditionDept,"
				+ "a.commention, a.auditionPoint, a.registerDate,"
				+ "a.auditionPlace,a.becomesDate, a.remark, a.status,a.staffCategoryID,a.categoryName,a.bsState,a.ksState,a.zpState,a.ztState) "
				+ " from Staff s, Audition a where s.staffID = a.staffID and a.auditionID = ?";




		Audition audition  = (Audition)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{auditionID});
		return audition;
	}
	/**
	 *
	 * 获取详细
	 * @param auditionID
	 * @return
	 */
	public Audition getViewBc(String auditionID){

		String hql = "from Audition where  auditionID = ?";

		Audition audition  = (Audition)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{auditionID});
		return audition;
	}
	/**
	 *
	 * 获取详细
	 * @param tpId
	 * @return
	 */
	public TalentPool getTalentPoolDetail(String tpId){
		TalentPool talentPool  = (TalentPool)baseBeanDao.getBeanByHqlAndParams("from TalentPool where tpId = ?",new Object[]{tpId});
		return talentPool;
	}
	/**
	 *
	 *面试笔试通过不通过
	 */
	public void passExam(String vm,Audition audition,String staffID){
		List<BaseBean> beans = new ArrayList<BaseBean>();

		String hql = "from Audition  where  auditionID=?";
	    Audition audition1 = (Audition)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{audition.getAuditionID()});
	    if(audition1!=null){
	    	if(vm.equals("b")){  //笔试
				audition1.setCommentionb(audition.getCommentionb());
				audition1.setAuditionPointb(audition.getAuditionPointb());
				audition1.setBsDate(new Date());//笔试评定时间
				audition1.setBsState(audition.getBsState());
				audition1.setBsstaffID(staffID);
			}else if(vm.equals("k")){//口试
				audition1.setCommentionk(audition.getCommentionk());
				audition1.setAuditionPointk(audition.getAuditionPointk());
				audition1.setMsDate(audition.getMsDate());
				audition1.setKsState(audition.getKsState());
				audition1.setMspdDate(new Date());//面试评定时间
				audition1.setMspdStaffID(staffID);
			}else if(vm.equals("z")){//综合评定
				audition1.setCommention(audition.getCommention());
				audition1.setAuditionPoint(audition.getAuditionPoint());
				audition1.setZpState(audition.getZpState());
				audition1.setZtState(audition.getZtState());
				audition1.setZpDate(new Date());
				audition1.setZpStaffID(staffID);

				if(audition.getZpState().equals("2")){
					//如果综合评定不合格，通知下应聘者，不用短信
					TalentPool talentPool = (TalentPool)baseBeanDao.getBeanByHqlAndParams("from TalentPool where tpId = ?",new Object[]{audition1.getTpId()});
					if(talentPool!=null){
						talentPool.setState("05");//没被录用
						beans.add(talentPool);
						noticeMessage(audition1,talentPool);
					}



				}
			}else if(vm.equals("zt")){//转通知
				audition1.setZtState("1");

			}
			beans.add(audition1);

			baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);

		}


	}

	/**
	 *
	 * 来面试先登记一下
	 * @param audition
	 */
	public void interviewReg(Audition audition){
         List<BaseBean> beans = new ArrayList<BaseBean>();
		Audition aud = (Audition)baseBeanDao.getBeanByHqlAndParams("from Audition where auditionID = ?",new Object[]{audition.getAuditionID()});
        if(aud!=null){
        	aud.setExaminer(audition.getExaminer());
        	aud.setRegisDate(new Date());
        	aud.setMstID(audition.getMstID());
        	aud.setBstID(audition.getBstID());
			aud.setMstName(audition.getMstName());
			aud.setBstName(audition.getBstName());
			aud.setExaminer(audition.getExaminer());
			aud.setStatus("00");
			beans.add(aud);
        	if(audition.getBstID()!=null&&!audition.getBstID().equals("")){

				Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{aud.getStaffID()});


				TotalQuestion totalQuestion = (TotalQuestion) baseBeanDao.getBeanByHqlAndParams("from TotalQuestion where tqID = ?",new Object[]{audition.getBstID()});
				TotalQuestionExam totalQuestionExam = new TotalQuestionExam();
				BeanUtils.copyProperties(totalQuestion, totalQuestionExam);
				totalQuestionExam.setTqKey(null);
				totalQuestionExam.setCreateDate(new Date());
				totalQuestionExam.setStaffID(aud.getStaffID());
				totalQuestionExam.setStaffName(staff.getStaffName());
				totalQuestionExam.setTqeID(serverService.getServerID("tqeid"));
				beans.add(totalQuestionExam);


				ExamRelate examRelate = new ExamRelate();
				examRelate.setErId(serverService.getServerID("erid"));
				examRelate.setStaffID(aud.getStaffID());
				examRelate.setStaffName(staff.getStaffName());
				examRelate.setTelphone(staff.getReference());
				examRelate.setCard(staff.getStaffIdentityCard());
				examRelate.setTqID(audition.getBstID());
				examRelate.setStatus("00");
				examRelate.setTqeID(totalQuestionExam.getTqeID());

				beans.add(examRelate);
				List<BaseBean> queslist = baseBeanDao.getListBeanByHqlAndParams("from Questions where status = ? and tqID = ?",new Object[]{"00",audition.getBstID()});
                  for(int i = 0;i<queslist.size();i++){
					  Questions questions = (Questions) queslist.get(i);
					  QuestionsExam questionsExam = new QuestionsExam();
					  BeanUtils.copyProperties(questions,questionsExam);
					  questionsExam.setQrKey(null);
					  questionsExam.setCreateDate(new Date());
					  questionsExam.setStaffID(aud.getStaffID());
					  questionsExam.setStaffName(staff.getStaffName());
					  questionsExam.setQreID(serverService.getServerID("qreid"));
					  questionsExam.setTqeID(totalQuestionExam.getTqeID());
					  beans.add(questionsExam);
					  if(!questions.getQuesType().equals("03")){
					  	List<BaseBean> valuelist = baseBeanDao.getListBeanByHqlAndParams("from QuestionsValue where  qrID = ?",new Object[]{questions.getQrID()});
						  for(int j = 0;j<valuelist.size();j++) {
							  QuestionsValue questionsValue = (QuestionsValue) valuelist.get(j);
							  QuestionsValueExam questionsValueExam = new QuestionsValueExam();
							  BeanUtils.copyProperties(questionsValue, questionsValueExam);
							  questionsValueExam.setQrvKey(null);
							  questionsValueExam.setStaffID(aud.getStaffID());
							  questionsValueExam.setQrveID(serverService.getServerID("qrveid"));
							  questionsValueExam.setQreID(questionsExam.getQreID());
							  beans.add(questionsValueExam);
						  }
					  }

				  }
        	}

          baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);

		}
	}
	/**
	 *
	 * 入职报到通知
	 * @param audition
	 */
	public void bdnotice(Audition audition){
		List<BaseBean> beans = new ArrayList<BaseBean>();
		Audition aud = (Audition)baseBeanDao.getBeanByHqlAndParams("from Audition where auditionID = ?",new Object[]{audition.getAuditionID()});
		if(aud!=null){
			aud.setBdcontactor(audition.getBdcontactor());
			aud.setBdcontactortel(audition.getBdcontactortel());
			aud.setRegisDate(audition.getRegisDate());
			aud.setAuditionPlace(audition.getAuditionPlace());//报到地点
			aud.setZtState("3");
			aud.setStatus("21");
			beans.add(aud);

			TalentPool talentPool = (TalentPool)baseBeanDao.getBeanByHqlAndParams("from TalentPool where tpId = ?",new Object[]{audition.getTpId()});
			if(talentPool!=null) {
				talentPool.setState("04");//没被录用
				beans.add(talentPool);
				noticeMessage(aud,talentPool);
			}


			baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);



		}
	}


	/**
	 *
	 * 通知
	 */
	private   void noticeMessage(Audition audition,TalentPool talentPool) {

		Staff ff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{audition.getStaffID()});
		Company company = (Company)baseBeanDao.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{talentPool.getCompanyId()});
		String	tel = ff.getReference();

		List<String> slist = new ArrayList<String>();//极光推送设备号
		String type="";
		String id = "";
		String body = "";
		StringBuilder  smscont = new StringBuilder();
		if(talentPool!=null){
           if(audition.getZpState().equals("2")){
			    type = "面试结果回复";
			    id = "msyq";
			  	body = "恭喜您面试通过";

			   smscont.append(ff.getStaffName()+"您好，经过评估，您的条件目前不完全符合"+audition.getAuditionPost()+"岗位需求。我们期待未来有机会再次合作。—"+company.getCompanyName());

		   }else{

           	type = "通知入职";
           	id = "msyq";
            body = "恭喜您面试通过";

			smscont.append(ff.getStaffName()+"您好！恭喜您，");
			smscont.append(company.getCompanyName()+"人力资源部确认您已成功通过");
			smscont.append(audition.getAuditionPost()+"岗位的面试。");
			smscont.append("请于："+ Utilities.getDateString(audition.getRegisDate(),"yyyy-MM-dd HH:mm")+",携带相关资料来公司报到.");
			smscont.append("面试地点："+audition.getAuditionPlace()+",\n");
			smscont.append("联系人："+audition.getBdcontactor()+",\n");
			smscont.append("联系人电话："+audition.getBdcontactortel()+"。\n");
			smscont.append("祝您在新的工作旅程中一切顺利！");

//			   try {
//				   msage.setMobiles(tel);
//				   msage.setMessage(smscont.toString());
//				   msage.sendMsg("【数字地球】");
//			   } catch (Exception e) {
//				   e.printStackTrace();
//			   }

		   }


		}


		//极光推送
		JushMain.sendjiguangMessage(smscont.toString(), type, body, id, slist);

	}
	public Staff getStaffInfo(String sccId){
		Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff  where sccId = ?",new Object[]{sccId});
		return staff;
	}

	/**
	 *
	 * 社会人力
	 * @param companyID
	 * @param parameter
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageForm getSocietHumanlist(String companyID,String parameter,int pageNumber,int pageSize){

		Company company = (Company)baseBeanDao.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{companyID});

		List prams = new ArrayList();
		String hql = " from Staff s where s.groupCompanySn = ? and s.staffName is not null and s.status!='99' ";
		prams.add(company.getGroupCompanySn());

		if(parameter!=null&&!parameter.equals("")){
			hql += " and (s.staffName like ? or s.staffIdentityCard like ? or s.reference like ?) or exists (select f.staffID from RelateStaff f where f.staffID = s.staffID and  f.companyID = ? and (s.staffName like ? or s.staffIdentityCard like ? or s.reference like ?))";
			prams.add("%"+parameter+"%");
			prams.add("%"+parameter+"%");
			prams.add("%"+parameter+"%");
			prams.add(companyID);
			prams.add("%"+parameter+"%");
			prams.add("%"+parameter+"%");
			prams.add("%"+parameter+"%");
		}else{
			 hql += "or exists (select f.staffID from RelateStaff f where f.staffID = s.staffID and  f.companyID = ?)";
			prams.add(companyID);
		}


		hql += " order by s.verifyTime desc";

		PageForm pageForm = baseBeanService.getPageForm(
				pageNumber,
				pageSize, hql, prams.toArray());

		return pageForm;
	}

	/**
	 *
	 * 从人力直接转到面试待登记，无需通知
	 * @return
	 */
	public String zmsRegister(String staffID,String companyID){

		 COS cos = (COS)baseBeanDao.getBeanByHqlAndParams("from COS where staffID = ? and companyID = ?",new Object[]{staffID,companyID});
         if(cos!=null){
         	return "1";
		 }else{
			 Audition audition1 = (Audition)baseBeanDao.getBeanByHqlAndParams("from Audition where staffID = ? and companyID = ?",new Object[]{staffID,companyID});
			 if(audition1!=null){
			 	return "2";
			 }else{
				 Audition audition = new Audition();
				 audition.setAuditionID(serverService.getServerID("auditionID"));
				 audition.setCompanyID(companyID);
				 audition.setStaffID(staffID);
				 audition.setStatus("01");//待登记
				 baseBeanDao.save(audition);
				 return "3";
			 }
		 }

	}

	/**
	 *
	 * 保存基本信息
	 * @param staff
	 * @param staffID
	 * @param companyID
	 */
	public void saveInfo(Staff staff,String staffID,String companyID){
		Staff input = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});

		Company y = (Company) baseBeanDao.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{companyID});

		if(staff.getStaffID()!=null&&!staff.getStaffID().equals("")){
            Staff sta = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staff.getStaffID()});
			sta.setVerifyTime(new Date());
			sta.setStaffName(staff.getStaffName());
			sta.setEntryPersonnel(input.getStaffName());
			sta.setStatus(staff.getStatus());
			sta.setSccid(staff.getSccid());
			sta.setNation(staff.getNation());
			sta.setReference(staff.getReference());
			sta.setNativePlace(staff.getNativePlace());
			sta.setStaffAddress(staff.getStaffAddress());
			sta.setStatus(staff.getStatus());
			sta.setStaffIdentityCard(staff.getStaffIdentityCard());
			sta.setSex(staff.getSex());
			sta.setBirthday(staff.getBirthday());
			sta.setSccid(staff.getSccid());
			if(sta.getStaffIdentityCard()!=null&&!sta.getStaffIdentityCard().equals("")){
				sta.setStaus("00");//00:确定人员信息 ；01：未确定人员信息
			}else{
				sta.setStaus("01");
			}

			sta.setPhoto(staff.getPhoto());


			baseBeanDao.update(sta);
		}else{
			staff.setGroupCompanySn(y.getGroupCompanySn());
			int pcount = baseBeanService.getConutByByHqlAndParams("select count(*) from Staff ", null);
			staff.setStaffCode("NO" + pcount);
			staff.setRecordCode("NO" + pcount);
			staff.setVerifyTime(new Date());
			staff.setStaffID(serverService.getServerID("cstaff"));
			staff.setStaffStatus("00");
			staff.setSource("系统");
			baseBeanDao.update(staff);
		}


	}

	/**
	 *
	 * 验证身份证号
	 * @param card
	 * @return
	 */
	public  String checkCard(String card,String staffID){
		String hql = "from Staff where staffIdentityCard = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(card);

		if(staffID!=null&&!staffID.equals("")){
			hql+="and staffID!=?";
			param.add(staffID);
		}
		Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql,param.toArray());
		if(staff==null){
			return "0";
		}else{
			return "1";
		}
	}


	/**
	 *
	 * 验证账号
	 * @param acc
	 * @return
	 */
	public  String checkWfjAccount(String acc){
		TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?",new Object[]{acc,"01"});
		if(tc!=null){
			return tc.getSccId();
		}else{
			return "";
		}


	}

	/**
	 *
	 * 获取题库
	 * @param type
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getQuestionBase(String type,String companyID){

		String hql  = "from TotalQuestion where type = ? and companyID = ? and status = ? order by createDate desc";
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{type,companyID,"00"});

		return list;
	}



}
