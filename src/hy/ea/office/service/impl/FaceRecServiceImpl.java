package hy.ea.office.service.impl;

import hy.ea.bo.human.Staff;
import hy.ea.bo.office.FaceRec;
import hy.ea.office.action.response.PersonResponse;
import hy.ea.office.service.FaceRecService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FaceRecServiceImpl implements FaceRecService {

	private final Logger logger = LoggerFactory.getLogger(FaceRecServiceImpl.class);
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;


	/**
	 *
	 * 添加设备
	 */
	@Override
	public void addEquip(FaceRec faceRec,String staffID,String companyID){
		if (faceRec != null) {

			String hql = "from Staff where staffID = ?";
			//录入人员
			Staff input = (Staff) beandao.getBeanByHqlAndParams(hql, new Object[]{staffID});
			faceRec.setStaffID(staffID);
			faceRec.setStaffName(input.getStaffName());
			faceRec.setCreateDate(new Date());
			faceRec.setCompanyID(companyID);

			if (faceRec.getFrId()== null || faceRec.getFrId().equals("")) {
				faceRec.setFrId(serverService.getServerID("frid"));

			}

			beandao.update(faceRec);
		}

	}

	/**
	 *
	 * 删除设备
	 */
	@Override
	public void deleteEquip(String frkey){
         beandao.deleteBeanByKey(FaceRec.class,frkey);

	}

	/**
	 *
	 * 分页查询人脸闸机设备
	 * @param faceRec
	 * @param pageSize
	 * @param pageNumber
	 * @param companyID
	 * @return
	 */
	@Override
	public PageForm getListFaceRec(FaceRec faceRec, int pageSize, int pageNumber, String companyID){

		String hql = "from FaceRec where companyID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(companyID);
		if(faceRec!=null&&faceRec.getSn()!=null&&!faceRec.getSn().equals("")){
			hql+=" and sn = ?";
			params.add(faceRec.getSn());
		}

		PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,params.toArray());



		  return pageForm;
	}
	/**
	 * 设备号全网唯一
	 * @param sn
	 * @return
	 */
	public String checkRecSn(String sn,String frId){
		if(frId==null||frId.equals("")){
			frId= " ";
		}
		String hql = "from FaceRec where sn = ? and frId!=?";
		List<BaseBean> facelist = beandao.getListBeanByHqlAndParams(hql,new Object[]{sn,frId});
		if(facelist.size()==0){
			return "0";
		}else{
			return "1";
		}

	}

	@Override
	public Staff findStaffById(String staffId) {
		String hql = "from Staff where staffID = ?";
		return (Staff) beandao.getBeanByHqlAndParams(hql, new Object[]{staffId});

	}

	@Override
	public List<PersonResponse> findAll() {
		String sql = "select STAFFID,STAFFNAME,STAFFIDENTITYCARD,FACEPICTURE from Staff";
		/*List<Staff> staffList = beandao.findAll(sql);*/
		List<PersonResponse> responeList = new ArrayList<>();
		/*for (Staff staff : staffList) {
			PersonResponse personRespone = new PersonResponse();
			personRespone.setCmd("add");
			personRespone.setId(staff.getStaffID());
			personRespone.setIdCard(staff.getStaffIdentityCard());
			personRespone.setName(staff.getStaffName());
			personRespone.setFacePicture(staff.getFacePicture());
			responeList.add(personRespone);
		}*/
		return responeList;
	}

}