package hy.ea.office.service.impl;

import hy.ea.bo.office.CarInformation;
import hy.ea.office.dao.CheckCarDao;
import hy.ea.office.service.CheckCarService;
import hy.plat.CoreManagerImpl;
import hy.plat.bo.PageForm;
import hy.plat.common.hibernate3.Finder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckCarServiceImpl extends CoreManagerImpl<CarInformation>
		implements CheckCarService {

	@Autowired
	public void setDao(CheckCarDao dao) {
		super.setDao(dao);
	}

	protected CheckCarDao getDao() {
		return (CheckCarDao) super.getDao();
	}

	@Override
	public PageForm getPageForm(int pageNo, int pageSize, CarInformation car) {
		Finder finder = null;
		try {
			finder = new Finder(" from CarInformation as bean");
			finder.append(" where bean.companyID=:companyID").setParam(
					"companyID", car.getCompanyID());
			if (car.getCarNum() != null
					&& !car.getCarNum().equals("")) {
				finder.append(" and bean.carNum like :carNum")
						.setParam("carNum",
								"%"+car.getCarNum().trim()+"%");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPageForm(pageNo, pageSize, finder);
	}
	
	/**
	 * 
	 * 
	 */
	
	@Override
	public PageForm getPageFormByPoint(int pageNo, int pageSize, CarInformation car) {
		Finder finder = null;
		try {
			finder = new Finder(" from CarInfomation as bean");
			finder.append(" exists(select p.devicetypeid from CarCheckPoint p where p.companyid=:companyid and p.devicetypeid = bean.carID)").setParam(
					"companyid", car.getCompanyID());
			if (car.getCarNum() != null
					&& !car.getCarNum().equals("")) {
				finder.append(" and bean.carNum like :carNum")
						.setParam("carNum",
								"%"+car.getCarNum().trim()+"%");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPageForm(pageNo, pageSize, finder);
	}




	/**
	 * 按属性查找对象列表.
	 */
	public List<CarInformation> findByProperty(String property, Object value) {
		return getDao().findByProperty(property, value);
	}



}
