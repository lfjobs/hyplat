package mobile.tiantai.android.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.StaffMeeting;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public interface QyrzService {
	/**
	 *
	 * 公司会员
	 * @return
	 */
	public List<BaseBean> getpk();

	/**
	 *
	 * 获取产品图文详情
	 * @return
	 */
	public String getProDetail(String goodsid,String realpath);

	/**
	 *
	 * 获取附近20个商铺的发布的动态，如果没有拿公共库
	 * @return
	 */
	public  List<BaseBean> getNewsList(String x,String y);


	/**
	 *
	 * 获取当前用户完成的认领任务
	 * @param staffID
	 * @return
	 */
	public PageForm getCompeleteBusi(int pageNumber,int pageSize,String date,String parameter,String gdcate,String staffID,String gdcate2);



	/**
	 *
	 *  未认领但发送过短信的公司
	 * @return
	 */
	public PageForm getCompanyZ(int pageNumber,int pageSize,String parameter,String gdcate,String jd,String wd,String yw);

	}
